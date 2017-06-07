package com.nekokittygames.undeath.common.items;

import com.nekokittygames.undeath.common.Config;
import com.nekokittygames.undeath.common.IUndeadEntity;
import com.nekokittygames.undeath.common.entities.EntityPlayerZombie;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.minecraft.item.ItemMonsterPlacer.spawnCreature;

/**
 * Created by katsw on 25/05/2017.
 */
public class ItemSpawnUndead extends Item {

    public static List<ResourceLocation> classes = new ArrayList<ResourceLocation>();

    public ItemSpawnUndead() {
        this.setCreativeTab(CreativeTabs.MISC);
        classes.add(new ResourceLocation("undeath", "playerZombie"));
        classes.add(new ResourceLocation("undeath", "playerSkell"));
        classes.add(new ResourceLocation("undeath", "playerPig"));
        classes.add(new ResourceLocation("undeath", "playerSlime"));
        setHasSubtypes(true);
        setMaxDamage(classes.size());
        setUnlocalizedName("undeadSpawner");
        setRegistryName("undeadSpawner");

    }


    protected double getYOffset(World p_190909_1_, BlockPos p_190909_2_) {
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(p_190909_2_)).addCoord(0.0D, -1.0D, 0.0D);
        List<AxisAlignedBB> list = p_190909_1_.getCollisionBoxes((Entity) null, axisalignedbb);

        if (list.isEmpty()) {
            return 0.0D;
        } else {
            double d0 = axisalignedbb.minY;

            for (AxisAlignedBB axisalignedbb1 : list) {
                d0 = Math.max(axisalignedbb1.maxY, d0);
            }

            return d0 - (double) p_190909_2_.getY();
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(player.isSneaking())
        {
            ItemStack itm=player.getActiveItemStack();
            itm.setItemDamage((itm.getItemDamage()+1) % classes.size());
            return EnumActionResult.SUCCESS;
        }
        if (worldIn.isRemote)
            return EnumActionResult.PASS;

        ItemStack itemstack = player.getHeldItem(hand);

        if (worldIn.isRemote) {
            return EnumActionResult.SUCCESS;
        } else if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) {
            return EnumActionResult.FAIL;
        } else {
            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            BlockPos blockpos = pos.offset(facing);
            double d0 = this.getYOffset(worldIn, blockpos);
            //Entity entity = spawnCreature(worldIn, , , , );
            Entity entity = EntityList.createEntityByIDFromName(classes.get(itemstack.getItemDamage()), worldIn);

            if (entity instanceof EntityLiving)
            {
                EntityLiving entityliving = (EntityLiving)entity;
                entity.setLocationAndAngles((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + d0, (double) blockpos.getZ() + 0.5D, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
                entityliving.rotationYawHead = entityliving.rotationYaw;
                entityliving.renderYawOffset = entityliving.rotationYaw;
                entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)), (IEntityLivingData)null);
                ((IUndeadEntity) entity).setupEntity(player);
                worldIn.spawnEntity(entity);
                entityliving.playLivingSound();
            }
            if (entity != null) {
                if (entity instanceof EntityLivingBase && itemstack.hasDisplayName()) {
                    entity.setCustomNameTag(itemstack.getDisplayName());
                }

                if (!player.capabilities.isCreativeMode) {
                    itemstack.shrink(1);
                }
            }

            return EnumActionResult.SUCCESS;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        //super.getSubItems(itemIn, tab, subItems);
        subItems.add(new ItemStack(this,1,0));
        subItems.add(new ItemStack(this,1,1));
        subItems.add(new ItemStack(this,1,2));
        if(Config.deathChest)
            subItems.add(new ItemStack(this,1,3));

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        switch (stack.getItemDamage())
        {
            case 0:
                tooltip.add(I18n.format("undeath.zombieSpawn"));
                break;
            case 1:
                tooltip.add(I18n.format("undeath.skellSpawn"));
                break;
            case 2:
                tooltip.add(I18n.format("undeath.pigZombieSpawn"));
                break;
            case 3:
                tooltip.add(I18n.format("undeath.slimeSpawn"));
                break;
        }
    }
}
