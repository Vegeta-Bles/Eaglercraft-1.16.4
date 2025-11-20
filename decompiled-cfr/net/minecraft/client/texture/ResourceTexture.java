/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.Closeable;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.resource.metadata.TextureResourceMetadata;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.TextureUtil;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceTexture
extends AbstractTexture {
    private static final Logger LOGGER = LogManager.getLogger();
    protected final Identifier location;

    public ResourceTexture(Identifier location) {
        this.location = location;
    }

    @Override
    public void load(ResourceManager manager) throws IOException {
        boolean bl;
        TextureData textureData = this.loadTextureData(manager);
        textureData.checkException();
        TextureResourceMetadata _snowman2 = textureData.getMetadata();
        if (_snowman2 != null) {
            bl = _snowman2.shouldBlur();
            _snowman = _snowman2.shouldClamp();
        } else {
            bl = false;
            _snowman = false;
        }
        NativeImage _snowman3 = textureData.getImage();
        if (!RenderSystem.isOnRenderThreadOrInit()) {
            RenderSystem.recordRenderCall(() -> this.upload(_snowman3, bl, _snowman));
        } else {
            this.upload(_snowman3, bl, _snowman);
        }
    }

    private void upload(NativeImage nativeImage, boolean blur, boolean clamp) {
        TextureUtil.allocate(this.getGlId(), 0, nativeImage.getWidth(), nativeImage.getHeight());
        nativeImage.upload(0, 0, 0, 0, 0, nativeImage.getWidth(), nativeImage.getHeight(), blur, clamp, false, true);
    }

    protected TextureData loadTextureData(ResourceManager resourceManager) {
        return TextureData.load(resourceManager, this.location);
    }

    public static class TextureData
    implements Closeable {
        @Nullable
        private final TextureResourceMetadata metadata;
        @Nullable
        private final NativeImage image;
        @Nullable
        private final IOException exception;

        public TextureData(IOException exception) {
            this.exception = exception;
            this.metadata = null;
            this.image = null;
        }

        public TextureData(@Nullable TextureResourceMetadata metadata, NativeImage image) {
            this.exception = null;
            this.metadata = metadata;
            this.image = image;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static TextureData load(ResourceManager resourceManager, Identifier identifier) {
            try (Resource resource = resourceManager.getResource(identifier);){
                NativeImage _snowman2 = NativeImage.read(resource.getInputStream());
                TextureResourceMetadata _snowman3 = null;
                try {
                    _snowman3 = resource.getMetadata(TextureResourceMetadata.READER);
                }
                catch (RuntimeException _snowman4) {
                    LOGGER.warn("Failed reading metadata of: {}", (Object)identifier, (Object)_snowman4);
                }
                TextureData textureData = new TextureData(_snowman3, _snowman2);
                return textureData;
            }
            catch (IOException iOException) {
                return new TextureData(iOException);
            }
        }

        @Nullable
        public TextureResourceMetadata getMetadata() {
            return this.metadata;
        }

        public NativeImage getImage() throws IOException {
            if (this.exception != null) {
                throw this.exception;
            }
            return this.image;
        }

        @Override
        public void close() {
            if (this.image != null) {
                this.image.close();
            }
        }

        public void checkException() throws IOException {
            if (this.exception != null) {
                throw this.exception;
            }
        }
    }
}

