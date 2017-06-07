package com.nekokittygames.undeath.client.render;

import com.nekokittygames.undeath.client.render.layers.LayerRot;
import com.nekokittygames.undeath.common.entities.EntityPlayerZombie;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;

/**
 * Created by katsw on 24/05/2017.
 */
public class RenderPlayerZombie extends RenderLiving<EntityPlayerZombie> {


    public RenderPlayerZombie(RenderManager renderManagerIn)
    {
        super(renderManagerIn, new ModelZombie(), 0.5F);
        LayerRot layerRot=new LayerRot(this)
        {
            @Override
            protected void initRot() {
                modelRot=new ModelZombie(0.01f,true);
            }
        };
        layerRot.addOverlay(new ResourceLocation("undeath","textures/entities/playerzombie.png"));
        this.addLayer(layerRot);
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
        {
            protected void initArmor()
            {
                this.modelLeggings = new ModelZombie(0.5F, true);
                this.modelArmor = new ModelZombie(1.0F, true);
            }
        };
        this.addLayer(layerbipedarmor);
        this.addLayer(new LayerHeldItem(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityPlayerZombie entity)
    {
        return entity.getLocationSkins();
    }
}
