package com.nekokittygames.undeath.client.render.layers;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by katsw on 25/05/2017.
 */
@SideOnly(Side.CLIENT)
public class LayerRot implements LayerRenderer<EntityLivingBase> {

    public ResourceLocation overlay;
    protected final RenderLivingBase<?> renderer;
    protected ModelBiped modelRot;
    public LayerRot(RenderLivingBase<?> rendererIn)
    {
        this.renderer = rendererIn;
        initRot();
    }

    public void addOverlay(ResourceLocation overlay)
    {
        this.overlay=overlay;
    }

    protected void initRot() {
        modelRot= new ModelBiped(1.01F);
    }

    @Override
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
         this.renderer.bindTexture(overlay);
         modelRot.setModelAttributes(renderer.getMainModel());
         modelRot.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
         modelRot.render(entitylivingbaseIn,limbSwing,limbSwingAmount,ageInTicks,netHeadYaw,headPitch,scale);

    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
