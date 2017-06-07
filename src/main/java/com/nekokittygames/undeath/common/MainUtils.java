package com.nekokittygames.undeath.common;

import com.mojang.authlib.GameProfile;
import com.nekokittygames.undeath.common.entities.EntityPlayerZombie;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * Created by katsw on 31/05/2017.
 */
public class MainUtils {

    public static void makeEntity(EnumCreatureType type, World world, BlockPos pos, EntityPlayer player) {
        ResourceLocation resourceLocation = null;
        switch (type) {
            case ZOMBIE:
                resourceLocation = new ResourceLocation("undeath", "playerZombie");
                break;
            case SKELLINGTON:
                resourceLocation=new ResourceLocation("undeath","playerSkell");
                break;
            case PIGZOMBIE:
                resourceLocation=new ResourceLocation("undeath","playerPig");
                break;
            case SLIME:
                resourceLocation=new ResourceLocation("undeath","playerSlime");

        }
        //Undeath.logger.info("Making of type: "+resourceLocation);

        Entity entity = EntityList.createEntityByIDFromName(resourceLocation, world);
        EntityLiving entityliving = (EntityLiving) entity;
        entity.setLocationAndAngles((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 0.0F);
        entityliving.rotationYawHead = entityliving.rotationYaw;
        entityliving.renderYawOffset = entityliving.rotationYaw;
        entityliving.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entityliving)), (IEntityLivingData) null);
        ((IUndeadEntity) entity).setupEntity(player);
        world.spawnEntity(entity);
        entityliving.playLivingSound();
    }
}

