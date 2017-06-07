package com.nekokittygames.undeath.client.render.layers;

import com.nekokittygames.undeath.client.render.RenderPlayerSlime;
import com.nekokittygames.undeath.common.entities.EntityPlayerSlime;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by katsw on 07/06/2017.
 */
@SideOnly(Side.CLIENT)
public class LayerPlayerSlime implements LayerRenderer<EntityPlayerSlime> {

    private final RenderPlayerSlime slimeRenderer;
    private final ModelBase slimeModel = new ModelSlime(0);

    public LayerPlayerSlime(RenderPlayerSlime slimeRendererIn) {
        this.slimeRenderer = slimeRendererIn;
    }
    @Override
    public void doRenderLayer(EntityPlayerSlime entityPlayerSlime, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if(!entityPlayerSlime.isInvisible()) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
            this.slimeModel.render(entityPlayerSlime, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
}
