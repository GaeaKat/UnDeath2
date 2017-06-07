package com.nekokittygames.undeath.common.entities;

import com.google.common.base.Optional;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.nekokittygames.undeath.client.LayeredTexture;
import com.nekokittygames.undeath.client.Utils;
import com.nekokittygames.undeath.common.Config;
import com.nekokittygames.undeath.common.IUndeadEntity;
import com.nekokittygames.undeath.common.Undeath;
import com.nekokittygames.undeath.common.entities.components.InventoryComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.server.FMLServerHandler;
import scala.tools.cmd.Opt;
import scala.tools.nsc.doc.model.Def;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Map;
import java.util.UUID;

/**
 * Created by katsw on 24/05/2017.
 */
public class EntityPlayerZombie extends EntityZombie implements IUndeadEntity
{
    private static final DataParameter<String> OLD_PLAYER_NAME=EntityDataManager.<String>createKey(EntityPlayerZombie.class,DataSerializers.STRING);
    private static final DataParameter<Optional<UUID>> OLD_PLAYER_UUID=EntityDataManager.<Optional<UUID>>createKey(EntityPlayerZombie.class,DataSerializers.OPTIONAL_UNIQUE_ID);
    GameProfile oldProfile;
    public InventoryComponent inventoryComponent;

    public EntityPlayerZombie(World worldIn) {
        super(worldIn);
        inventoryComponent=new InventoryComponent();

    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(OLD_PLAYER_NAME,"");
        this.dataManager.register(OLD_PLAYER_UUID, Optional.<UUID>absent());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound=super.writeToNBT(compound);
        NBTTagCompound cmp=new NBTTagCompound();
        inventoryComponent.writeToNBT(cmp);
        compound.setTag("inventory",cmp);
        return compound;

    }
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventoryComponent.readFromNBT(compound.getCompoundTag("inventory"));
    }

    @Override
    public InventoryComponent getInventoryComponent() {
        return inventoryComponent;
    }
    @Override
    public void setupEntity(EntityPlayer player) {
        oldProfile = player.getGameProfile();
        this.dataManager.set(OLD_PLAYER_UUID, Optional.of(oldProfile.getId()));
        this.dataManager.set(OLD_PLAYER_NAME, oldProfile.getName());
        if(Config.deathChest && !this.world.getGameRules().getBoolean("keepInventory")) {
            this.enablePersistence();
            inventoryComponent.CopyInventory(player);
            player.inventory.clear();
            for(int i = 0; i < inventoryComponent.getInventory().size(); ++i) {
                ItemStack itemstack = inventoryComponent.getInventory().get(i);
                if(!itemstack.isEmpty() && EnchantmentHelper.hasVanishingCurse(itemstack)) {
                    inventoryComponent.getInventory().set(i,ItemStack.EMPTY);
                }
            }
            for(int i = 0; i < inventoryComponent.getArmor().size(); ++i) {
                ItemStack itemstack = inventoryComponent.getArmor().get(i);
                if(!itemstack.isEmpty() && EnchantmentHelper.hasVanishingCurse(itemstack)) {
                    inventoryComponent.getArmor().set(i,ItemStack.EMPTY);
                }
            }
            for(int i = 0; i < inventoryComponent.getOffhand().size(); ++i) {
                ItemStack itemstack = inventoryComponent.getOffhand().get(i);
                if(!itemstack.isEmpty() && EnchantmentHelper.hasVanishingCurse(itemstack)) {
                    inventoryComponent.getOffhand().set(i,ItemStack.EMPTY);
                }
            }
        }
        if(Config.useEquipment)
        {
            this.setItemStackToSlot(EntityEquipmentSlot.HEAD,player.getItemStackFromSlot(EntityEquipmentSlot.HEAD));
            this.setItemStackToSlot(EntityEquipmentSlot.CHEST,player.getItemStackFromSlot(EntityEquipmentSlot.CHEST));
            this.setItemStackToSlot(EntityEquipmentSlot.LEGS,player.getItemStackFromSlot(EntityEquipmentSlot.LEGS));
            this.setItemStackToSlot(EntityEquipmentSlot.FEET,player.getItemStackFromSlot(EntityEquipmentSlot.FEET));
            this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND,player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND));
            this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND,player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND));
        }

    }


    @Override
    protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {
        if(Config.deathChest)
        {
            for(ItemStack item:inventoryComponent.getInventory())
            {
                this.entityDropItem(item,0);
            }
            for(ItemStack item:inventoryComponent.getArmor())
            {
                this.entityDropItem(item,0);
            }
            for(ItemStack item:inventoryComponent.getOffhand())
            {
                this.entityDropItem(item,0);
            }
        }
    }



    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return inventoryComponent.getArmor();
    }


    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    public ResourceLocation getEntityID() {
        return new ResourceLocation("undeath","playerZombie");
    }

    @Override
    public String getCustomNameTag() {
        return getCorruptedName();
    }

    public String getCorruptedName()
    {
        return dataManager.get(OLD_PLAYER_NAME).replace("e", "\u00A7ke\u00A7r").replace("a", "\u00A7ka\u00A7r").replace("i", "\u00A7ki\u00A7r").replace("o", "\u00A7ko\u00A7r").replace("u", "\u00A7ku\u00A7r");
    }

    @Override
    public boolean hasCustomName() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation getLocationSkins()
    {
        if(oldProfile==null)
        {
            oldProfile= new GameProfile(this.dataManager.get(OLD_PLAYER_UUID).get(),this.dataManager.get(OLD_PLAYER_NAME));
        }
        return Utils.getSkinURL(oldProfile);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        NBTTagCompound profile=new NBTTagCompound();
        NBTUtil.writeGameProfile(profile,oldProfile);
        compound.setTag("profile",profile);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        oldProfile = NBTUtil.readGameProfileFromNBT(compound.getCompoundTag("profile"));
        if (!world.isRemote) {
            this.dataManager.set(OLD_PLAYER_NAME, oldProfile.getName());
            this.dataManager.set(OLD_PLAYER_UUID, Optional.of(oldProfile.getId()));
        }


    }
    @Override
    public String getPlayerName() {
        return this.dataManager.get(OLD_PLAYER_NAME);
    }

    @Override
    public String getEntityType() {
        return "entity.Zombie.name";
    }

}
