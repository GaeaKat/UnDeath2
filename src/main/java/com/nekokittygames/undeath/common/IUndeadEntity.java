package com.nekokittygames.undeath.common;

import com.mojang.authlib.GameProfile;
import com.nekokittygames.undeath.common.entities.components.InventoryComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by katsw on 24/05/2017.
 */
public interface IUndeadEntity {

    void setupEntity(EntityPlayer player);
    ResourceLocation getEntityID();
    String getPlayerName();
    String getEntityType();
    InventoryComponent getInventoryComponent();
}
