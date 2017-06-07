package com.nekokittygames.undeath.client;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

/**
 * Created by katsw on 25/05/2017.
 */
@SideOnly(Side.CLIENT)
public class ClientUtils {


    public static GameProfile fillProfile(GameProfile profile)
    {
        return Minecraft.getMinecraft().getSessionService().fillProfileProperties(profile,false);
    }

    public static Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(GameProfile profile)
    {
        return Minecraft.getMinecraft().getSessionService().getTextures(profile,false);
    }


}
