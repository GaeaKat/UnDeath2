package com.nekokittygames.undeath.common.entities;

import com.google.common.base.Optional;
import com.mojang.authlib.GameProfile;
import com.nekokittygames.undeath.common.Config;
import com.nekokittygames.undeath.common.IUndeadEntity;
import com.nekokittygames.undeath.common.Undeath;
import com.nekokittygames.undeath.common.entities.components.InventoryComponent;
import com.nekokittygames.undeath.common.network.InventoryPacket;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.UUID;

/**
 * Created by katsw on 06/06/2017.
 */
public class EntityPlayerSlime extends EntitySlime implements IUndeadEntity{

    private static final DataParameter<String> OLD_PLAYER_NAME= EntityDataManager.<String>createKey(EntityPlayerSlime.class, DataSerializers.STRING);
    private static final DataParameter<Optional<UUID>> OLD_PLAYER_UUID=EntityDataManager.<Optional<UUID>>createKey(EntityPlayerSlime.class,DataSerializers.OPTIONAL_UNIQUE_ID);
    GameProfile oldProfile;
    public InventoryComponent inventoryComponent;
    public EntityPlayerSlime(World worldIn) {
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
    public void setupEntity(EntityPlayer player) {
        oldProfile=player.getGameProfile();
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
        setSlimeSize(4,true);
        dataChanged();
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
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

        ItemStack head=new ItemStack(Items.SKULL,1,3);
        NBTTagCompound cmp=head.getTagCompound();
        if(cmp==null)
        {
            cmp=new NBTTagCompound();
        }
        cmp.setTag("SkullOwner", NBTUtil.writeGameProfile(new NBTTagCompound(), oldProfile));
        head.setTagCompound(cmp);
        this.entityDropItem(head,0);
    }

    @Override
    protected void setSlimeSize(int size, boolean p_70799_2_) {
        super.setSlimeSize(size, p_70799_2_);
    }

    @Override
    public ResourceLocation getEntityID() {
        return new ResourceLocation("undeath","playerSlime");
    }


    @Override
    public InventoryComponent getInventoryComponent() {
        return inventoryComponent;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        NBTTagCompound profile=new NBTTagCompound();
        NBTUtil.writeGameProfile(profile,oldProfile);
        compound.setTag("profile",profile);

    }
    private void dataChanged() {
        if(!world.isRemote) {
            EntityTracker tracker = ((WorldServer)world).getEntityTracker();
            InventoryPacket message = new InventoryPacket(inventoryComponent,this.getEntityId());

            //for (EntityPlayer entityPlayer : tracker.getTrackingPlayers(this)) {
                Undeath.NETWORK_WRAPPER.sendToAll(message);//(EntityPlayerMP)entityPlayer);
            //}
        }
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
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound=super.writeToNBT(compound);
        NBTTagCompound cmp=new NBTTagCompound();
        inventoryComponent.writeToNBT(cmp);
        compound.setTag("inventory",cmp);
        return  compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventoryComponent.readFromNBT(compound.getCompoundTag("inventory"));
    }

    @Override
    public String getPlayerName() {
        return this.dataManager.get(OLD_PLAYER_NAME);
    }

    @Override
    public String getEntityType() {
        return "entity.Slime.name";
    }
}
