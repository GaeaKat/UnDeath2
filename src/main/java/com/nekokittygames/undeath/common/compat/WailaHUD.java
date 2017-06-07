package com.nekokittygames.undeath.common.compat;

import com.nekokittygames.undeath.common.IUndeadEntity;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaEntityAccessor;
import mcp.mobius.waila.api.IWailaEntityProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by katsw on 31/05/2017.
 */

public class WailaHUD implements IWailaEntityProvider {
    @Nullable
    @Override
    public Entity getWailaOverride(IWailaEntityAccessor iWailaEntityAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return null;
    }

    @Nonnull
    @Override
    public List<String> getWailaHead(Entity entity, List<String> list, IWailaEntityAccessor iWailaEntityAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return list;
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public List<String> getWailaBody(Entity entity, List<String> list, IWailaEntityAccessor iWailaEntityAccessor, IWailaConfigHandler iWailaConfigHandler) {
        if(entity instanceof IUndeadEntity)
        {
            IUndeadEntity undeadEntity= (IUndeadEntity) entity;
            list.add(I18n.format("undeath.waila.mode",I18n.format(undeadEntity.getEntityType())));
            list.add(I18n.format("undeath.waila.player",undeadEntity.getPlayerName()));
        }
        return list;
    }

    @Nonnull
    @Override
    public List<String> getWailaTail(Entity entity, List<String> list, IWailaEntityAccessor iWailaEntityAccessor, IWailaConfigHandler iWailaConfigHandler) {
        return list;
    }

    @Nonnull
    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP entityPlayerMP, Entity entity, NBTTagCompound nbtTagCompound, World world) {
        return nbtTagCompound;
    }



}
