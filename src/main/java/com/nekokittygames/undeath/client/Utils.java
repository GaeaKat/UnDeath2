package com.nekokittygames.undeath.client;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.nekokittygames.undeath.common.Undeath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by katsw on 25/05/2017.
 */
public class Utils {
    public static Map<UUID,ResourceLocation> cache=new HashMap<UUID, ResourceLocation>();;

    public static void init()
    {
        cache=new HashMap<UUID, ResourceLocation>();
    }
@SideOnly(Side.CLIENT)
    public static ResourceLocation getSkinURL(GameProfile profile)
    {
        profile=TileEntitySkull.updateGameprofile(profile);
        ResourceLocation resourcelocation=null;
        Minecraft minecraft = Minecraft.getMinecraft();
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(profile);

        if (map.containsKey(MinecraftProfileTexture.Type.SKIN))
        {
            resourcelocation = minecraft.getSkinManager().loadSkin((MinecraftProfileTexture)map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
        }
        else
        {
            UUID uuid = EntityPlayer.getUUID(profile);
            resourcelocation = DefaultPlayerSkin.getDefaultSkin(uuid);
        }
        return  resourcelocation;
    }
}
