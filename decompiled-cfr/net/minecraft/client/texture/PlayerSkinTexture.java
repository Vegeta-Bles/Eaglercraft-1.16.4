/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.commons.io.FileUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.client.texture.TextureUtil;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerSkinTexture
extends ResourceTexture {
    private static final Logger LOGGER = LogManager.getLogger();
    @Nullable
    private final File cacheFile;
    private final String url;
    private final boolean convertLegacy;
    @Nullable
    private final Runnable loadedCallback;
    @Nullable
    private CompletableFuture<?> loader;
    private boolean loaded;

    public PlayerSkinTexture(@Nullable File cacheFile, String url, Identifier fallbackSkin, boolean convertLegacy, @Nullable Runnable callback) {
        super(fallbackSkin);
        this.cacheFile = cacheFile;
        this.url = url;
        this.convertLegacy = convertLegacy;
        this.loadedCallback = callback;
    }

    private void onTextureLoaded(NativeImage image) {
        if (this.loadedCallback != null) {
            this.loadedCallback.run();
        }
        MinecraftClient.getInstance().execute(() -> {
            this.loaded = true;
            if (!RenderSystem.isOnRenderThread()) {
                RenderSystem.recordRenderCall(() -> this.uploadTexture(image));
            } else {
                this.uploadTexture(image);
            }
        });
    }

    private void uploadTexture(NativeImage image) {
        TextureUtil.allocate(this.getGlId(), image.getWidth(), image.getHeight());
        image.upload(0, 0, 0, true);
    }

    @Override
    public void load(ResourceManager manager) throws IOException {
        NativeImage _snowman2;
        MinecraftClient.getInstance().execute(() -> {
            if (!this.loaded) {
                try {
                    super.load(manager);
                }
                catch (IOException iOException) {
                    LOGGER.warn("Failed to load texture: {}", (Object)this.location, (Object)iOException);
                }
                this.loaded = true;
            }
        });
        if (this.loader != null) {
            return;
        }
        if (this.cacheFile != null && this.cacheFile.isFile()) {
            LOGGER.debug("Loading http texture from local cache ({})", (Object)this.cacheFile);
            FileInputStream fileInputStream = new FileInputStream(this.cacheFile);
            _snowman2 = this.loadTexture(fileInputStream);
        } else {
            _snowman2 = null;
        }
        if (_snowman2 != null) {
            this.onTextureLoaded(_snowman2);
            return;
        }
        this.loader = CompletableFuture.runAsync(() -> {
            HttpURLConnection httpURLConnection = null;
            LOGGER.debug("Downloading http texture from {} to {}", (Object)this.url, (Object)this.cacheFile);
            try {
                InputStream _snowman2;
                httpURLConnection = (HttpURLConnection)new URL(this.url).openConnection(MinecraftClient.getInstance().getNetworkProxy());
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(false);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() / 100 != 2) {
                    return;
                }
                if (this.cacheFile != null) {
                    FileUtils.copyInputStreamToFile((InputStream)httpURLConnection.getInputStream(), (File)this.cacheFile);
                    _snowman2 = new FileInputStream(this.cacheFile);
                } else {
                    _snowman2 = httpURLConnection.getInputStream();
                }
                MinecraftClient.getInstance().execute(() -> {
                    NativeImage nativeImage = this.loadTexture(_snowman2);
                    if (nativeImage != null) {
                        this.onTextureLoaded(nativeImage);
                    }
                });
            }
            catch (Exception exception) {
                LOGGER.error("Couldn't download http texture", (Throwable)exception);
            }
            finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }
        }, Util.getMainWorkerExecutor());
    }

    @Nullable
    private NativeImage loadTexture(InputStream stream) {
        NativeImage nativeImage = null;
        try {
            nativeImage = NativeImage.read(stream);
            if (this.convertLegacy) {
                nativeImage = PlayerSkinTexture.remapTexture(nativeImage);
            }
        }
        catch (IOException _snowman2) {
            LOGGER.warn("Error while loading the skin texture", (Throwable)_snowman2);
        }
        return nativeImage;
    }

    private static NativeImage remapTexture(NativeImage image) {
        boolean bl;
        boolean bl2 = bl = image.getHeight() == 32;
        if (bl) {
            NativeImage nativeImage = new NativeImage(64, 64, true);
            nativeImage.copyFrom(image);
            image.close();
            image = nativeImage;
            image.fillRect(0, 32, 64, 32, 0);
            image.copyRect(4, 16, 16, 32, 4, 4, true, false);
            image.copyRect(8, 16, 16, 32, 4, 4, true, false);
            image.copyRect(0, 20, 24, 32, 4, 12, true, false);
            image.copyRect(4, 20, 16, 32, 4, 12, true, false);
            image.copyRect(8, 20, 8, 32, 4, 12, true, false);
            image.copyRect(12, 20, 16, 32, 4, 12, true, false);
            image.copyRect(44, 16, -8, 32, 4, 4, true, false);
            image.copyRect(48, 16, -8, 32, 4, 4, true, false);
            image.copyRect(40, 20, 0, 32, 4, 12, true, false);
            image.copyRect(44, 20, -8, 32, 4, 12, true, false);
            image.copyRect(48, 20, -16, 32, 4, 12, true, false);
            image.copyRect(52, 20, -8, 32, 4, 12, true, false);
        }
        PlayerSkinTexture.stripAlpha(image, 0, 0, 32, 16);
        if (bl) {
            PlayerSkinTexture.stripColor(image, 32, 0, 64, 32);
        }
        PlayerSkinTexture.stripAlpha(image, 0, 16, 64, 32);
        PlayerSkinTexture.stripAlpha(image, 16, 48, 48, 64);
        return image;
    }

    private static void stripColor(NativeImage image, int x1, int y1, int x2, int y2) {
        int n;
        for (n = x1; n < x2; ++n) {
            for (_snowman = y1; _snowman < y2; ++_snowman) {
                _snowman = image.getPixelColor(n, _snowman);
                if ((_snowman >> 24 & 0xFF) >= 128) continue;
                return;
            }
        }
        for (n = x1; n < x2; ++n) {
            for (_snowman = y1; _snowman < y2; ++_snowman) {
                image.setPixelColor(n, _snowman, image.getPixelColor(n, _snowman) & 0xFFFFFF);
            }
        }
    }

    private static void stripAlpha(NativeImage image, int x1, int y1, int x2, int y2) {
        for (int i = x1; i < x2; ++i) {
            for (_snowman = y1; _snowman < y2; ++_snowman) {
                image.setPixelColor(i, _snowman, image.getPixelColor(i, _snowman) | 0xFF000000);
            }
        }
    }
}

