package com.nekokittygames.undeath.client;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by katsw on 25/05/2017.
 */
public class LayeredTexture extends SimpleTexture {
    List<ResourceLocation> layers;
    BufferedImage bufferedImage;
    boolean textureUploaded=false;
    public LayeredTexture(ResourceLocation baseTexture,ResourceLocation ... extraLayers) {
        super(baseTexture);
        layers= Lists.newArrayList(extraLayers);
    }

    private void checkTextureUploaded()
    {
        if (!this.textureUploaded)
        {
            if (this.bufferedImage != null)
            {
                if (this.textureLocation != null)
                {
                    this.deleteGlTexture();
                }

                TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
                this.textureUploaded = true;
            }
        }
    }

    @Override
    public int getGlTextureId() {
        checkTextureUploaded();
        return super.getGlTextureId();
    }

    @Override
    public void loadTexture(IResourceManager resourceManager) throws IOException {
        super.loadTexture(resourceManager);
        //if (this.bufferedImage == null && this.textureLocation != null)
        //{
        //    super.loadTexture(resourceManager);
        //}
        ThreadDownloadImageData baseSkin=(ThreadDownloadImageData)Minecraft.getMinecraft().getTextureManager().getTexture(textureLocation);


        IResource base=resourceManager.getResource(textureLocation);
        InputStream baseStream=base.getInputStream();
        BufferedImage baseImage= ImageIO.read(baseStream);
        for(ResourceLocation layer:layers)
        {
            IResource layerResource=resourceManager.getResource(layer);
            InputStream layerStream=layerResource.getInputStream();
            BufferedImage layerImage= ImageIO.read(layerStream);
            baseImage.getGraphics().drawImage(layerImage,0,0,null);
        }
        baseImage=new ImageBufferDownload().parseUserSkin(baseImage);
        bufferedImage=baseImage;
    }
}
