package com.nekokittygames.undeath.client;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.nekokittygames.undeath.client.render.RenderPlayerPigZombie;
import com.nekokittygames.undeath.client.render.RenderPlayerSkellington;
import com.nekokittygames.undeath.client.render.RenderPlayerSlime;
import com.nekokittygames.undeath.client.render.RenderPlayerZombie;
import com.nekokittygames.undeath.common.CommonProxy;
import com.nekokittygames.undeath.common.Undeath;
import com.nekokittygames.undeath.common.entities.EntityPlayerPigZombie;
import com.nekokittygames.undeath.common.entities.EntityPlayerSkellington;
import com.nekokittygames.undeath.common.entities.EntityPlayerSlime;
import com.nekokittygames.undeath.common.entities.EntityPlayerZombie;
import com.nekokittygames.undeath.common.items.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;
import java.util.UUID;

/**
 * Created by katsw on 24/05/2017.
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityPlayerZombie.class, new IRenderFactory<EntityPlayerZombie>() {
            @Override
            public Render<? super EntityPlayerZombie> createRenderFor(RenderManager manager) {
                return new RenderPlayerZombie(manager);
            }
        });

        RenderingRegistry.registerEntityRenderingHandler(EntityPlayerSkellington.class, new IRenderFactory<EntityPlayerSkellington>() {
            @Override
            public Render<? super EntityPlayerSkellington> createRenderFor(RenderManager manager) {
                return new RenderPlayerSkellington(manager);
            }
        });

        RenderingRegistry.registerEntityRenderingHandler(EntityPlayerPigZombie.class, new IRenderFactory<EntityPlayerPigZombie>() {
            @Override
            public Render<? super EntityPlayerPigZombie> createRenderFor(RenderManager manager) {
                return new RenderPlayerPigZombie(manager);
            }
        });

        RenderingRegistry.registerEntityRenderingHandler(EntityPlayerSlime.class, new IRenderFactory<EntityPlayerSlime>() {
            @Override
            public Render<? super EntityPlayerSlime> createRenderFor(RenderManager manager) {
                return new RenderPlayerSlime(manager);
            }
        });


    }

    @Override
    public void registerItemRenderer() {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ModItems.spawner,0,new ModelResourceLocation("undeath:undeadspawner","inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ModItems.spawner,1,new ModelResourceLocation("undeath:undeadspawner","inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ModItems.spawner,2,new ModelResourceLocation("undeath:undeadspawner","inventory"));
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(ModItems.spawner,3,new ModelResourceLocation("undeath:undeadspawner","inventory"));
    }
}
