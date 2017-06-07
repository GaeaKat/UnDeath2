package com.nekokittygames.undeath.client.render;

import com.nekokittygames.undeath.client.render.layers.LayerRot;
import com.nekokittygames.undeath.common.entities.EntityPlayerPigZombie;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Created by katsw on 31/05/2017.
 */
public class RenderPlayerPigZombie extends RenderBiped<EntityPlayerPigZombie> {
    public RenderPlayerPigZombie(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelZombie(), 0.5F);
        LayerRot layerRot=new LayerRot(this)
        {
            @Override
            protected void initRot() {
                modelRot=new ModelZombie(0.01f,true);
            }
        };
        layerRot.addOverlay(new ResourceLocation("undeath","textures/entities/playerpig.png"));
        this.addLayer(layerRot);
        this.addLayer(new LayerHeldItem(this));
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelZombie(0.5F, true);
                this.modelArmor = new ModelZombie(1.0F, true);
            }
        };
        this.addLayer(layerbipedarmor);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityPlayerPigZombie entity) {
        return entity.getLocationSkins();
    }
}
