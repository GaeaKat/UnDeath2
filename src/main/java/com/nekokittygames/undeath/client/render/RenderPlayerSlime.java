package com.nekokittygames.undeath.client.render;

import com.nekokittygames.undeath.client.render.layers.LayerPlayerSlime;
import com.nekokittygames.undeath.client.render.model.ModelPlayerSlime;
import com.nekokittygames.undeath.common.entities.EntityPlayerSlime;
import com.nekokittygames.undeath.common.entities.components.InventoryComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerSlimeGel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by katsw on 06/06/2017.
 */
public class RenderPlayerSlime extends RenderLiving<EntityPlayerSlime> {
    private static final ResourceLocation SLIME_TEXTURES = new ResourceLocation("textures/entity/slime/slime.png");

    private RenderItem itemRenderer;
    private Random rand;
    private static float[][] posShifts = { { -0.75F, 1.5F, 0.5F },{ 0F, 1.5F, 0.5F },{ 0.75F, 1.5F, 0.5F },
            {-0.75F, 1.5F, 0F },{ 0F, 1.5F, 0F },{ 0.75F, 1.5F, 0F },
            {-0.75F, 1.5F, -0.5F },{ 0F, 1.5F, -0.5F },{ 0.75F, 1.5F, -0.5F },

            { -0.75F, 1.0F, 0.5F },{ 0F, 1F, 0.5F },{ 0.75F, 1F, 0.5F },
            {-0.75F, 1F, 0F },{ 0F, 1F, 0F },{ 0.75F, 1F, 0F },
            {-0.75F, 1F, -0.5F },{ 0F, 1F, -0.5F },{ 0.75F, 1F, -0.5F },

            { -0.75F, 0.25F, 0.5F },{ 0F, 0.25F, 0.5F },{ 0.75F, 0.25F, 0.5F },
            {-0.75F, 0.25F, 0F },{ 0F, 0.25F, 0F },{ 0.75F, 0.25F, 0F },
            {-0.75F, 0.25F, -0.5F },{ 0F, 0.25F, -0.5F },{ 0.75F, 0.25F, -0.5F },};

    public RenderPlayerSlime(RenderManager rendermanagerIn) {
        super(rendermanagerIn, new ModelPlayerSlime(), 0.25F);
        this.addLayer(new LayerPlayerSlime(this));

        itemRenderer=Minecraft.getMinecraft().getRenderItem();
        rand=new Random();
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityPlayerSlime entityPlayerSlime) {
        return new ResourceLocation("undeath","textures/entities/playerslime.png");
    }

    @Override
    public void doRender(EntityPlayerSlime entity, double x, double y, double z, float entityYaw, float partialTicks) {
        this.shadowSize = 0.25F * (float)entity.getSlimeSize();

        InventoryComponent inv=entity.inventoryComponent;
        rand.setSeed(1337L);
        float shiftX;
        float shiftY;
        float shiftZ;
        int shift = 0;
        float blockScale = 1f ;//*(1f/pSlime.getSlimeSize());
        float timeDelta=(float)(360.0*(double)(System.currentTimeMillis() & 0x3FFFL)/(double)0x3FFFL);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslatef((float)x, (float)y, (float)z);
        for(ItemStack item:inv.getInventory())
        {
            if(item.isEmpty())
                continue;
            if(shift>=posShifts.length)
                break;
            if(item==null)
            {
                shift++;
                continue;
            }
            shiftX=posShifts[shift][0];
            shiftY=posShifts[shift][1];
            shiftZ=posShifts[shift][2];
            shift++;
            GL11.glPushMatrix();
            GL11.glTranslatef(shiftX, shiftY, shiftZ);
            GL11.glRotatef(timeDelta, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(blockScale, blockScale, blockScale);
            itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.GROUND);
            GL11.glPopMatrix();
        }
        GL11.glRotatef(entityYaw,0.0f,0.0f,1.0f);
        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        super.doRender(entity, x, y, z, entityYaw, partialTicks);

    }

    @Override
    protected void preRenderCallback(EntityPlayerSlime entitylivingbaseIn, float partialTickTime) {
        float f = 0.999F;
        GlStateManager.scale(0.999F, 0.999F, 0.999F);
        float f1 = (float)entitylivingbaseIn.getSlimeSize();
        float f2 = (entitylivingbaseIn.prevSquishFactor + (entitylivingbaseIn.squishFactor - entitylivingbaseIn.prevSquishFactor) * partialTickTime) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);
        GlStateManager.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }
}
