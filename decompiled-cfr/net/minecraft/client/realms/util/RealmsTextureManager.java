/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture
 *  com.mojang.authlib.minecraft.MinecraftProfileTexture$Type
 *  com.mojang.util.UUIDTypeAdapter
 *  javax.annotation.Nullable
 *  org.apache.commons.codec.binary.Base64
 *  org.apache.commons.io.IOUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.realms.util;

import com.google.common.collect.Maps;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.util.UUIDTypeAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.realms.util.RealmsUtil;
import net.minecraft.client.realms.util.SkinProcessor;
import net.minecraft.client.texture.TextureUtil;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.util.Identifier;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsTextureManager {
    private static final Map<String, RealmsTexture> textures = Maps.newHashMap();
    private static final Map<String, Boolean> skinFetchStatus = Maps.newHashMap();
    private static final Map<String, String> fetchedSkins = Maps.newHashMap();
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Identifier field_22730 = new Identifier("textures/gui/presets/isles.png");

    public static void bindWorldTemplate(String id, @Nullable String image) {
        if (image == null) {
            MinecraftClient.getInstance().getTextureManager().bindTexture(field_22730);
            return;
        }
        int n = RealmsTextureManager.getTextureId(id, image);
        RenderSystem.bindTexture(n);
    }

    public static void withBoundFace(String uuid, Runnable r) {
        RenderSystem.pushTextureAttributes();
        try {
            RealmsTextureManager.bindFace(uuid);
            r.run();
        }
        finally {
            RenderSystem.popAttributes();
        }
    }

    private static void bindDefaultFace(UUID uuid) {
        MinecraftClient.getInstance().getTextureManager().bindTexture(DefaultSkinHelper.getTexture(uuid));
    }

    private static void bindFace(String uuid) {
        UUID uUID = UUIDTypeAdapter.fromString((String)uuid);
        if (textures.containsKey(uuid)) {
            RenderSystem.bindTexture(textures.get(uuid).textureId);
            return;
        }
        if (skinFetchStatus.containsKey(uuid)) {
            if (!skinFetchStatus.get(uuid).booleanValue()) {
                RealmsTextureManager.bindDefaultFace(uUID);
            } else if (fetchedSkins.containsKey(uuid)) {
                int n = RealmsTextureManager.getTextureId(uuid, fetchedSkins.get(uuid));
                RenderSystem.bindTexture(n);
            } else {
                RealmsTextureManager.bindDefaultFace(uUID);
            }
            return;
        }
        skinFetchStatus.put(uuid, false);
        RealmsTextureManager.bindDefaultFace(uUID);
        Thread _snowman2 = new Thread("Realms Texture Downloader", uuid){
            final /* synthetic */ String field_20257;
            {
                this.field_20257 = string2;
                super(string);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            public void run() {
                block17: {
                    block16: {
                        ByteArrayOutputStream _snowman5;
                        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = RealmsUtil.getTextures(this.field_20257);
                        if (!map.containsKey(MinecraftProfileTexture.Type.SKIN)) break block16;
                        MinecraftProfileTexture _snowman2 = map.get(MinecraftProfileTexture.Type.SKIN);
                        String _snowman3 = _snowman2.getUrl();
                        HttpURLConnection _snowman4 = null;
                        RealmsTextureManager.method_21557().debug("Downloading http texture from {}", (Object)_snowman3);
                        try {
                            BufferedImage bufferedImage;
                            _snowman4 = (HttpURLConnection)new URL(_snowman3).openConnection(MinecraftClient.getInstance().getNetworkProxy());
                            _snowman4.setDoInput(true);
                            _snowman4.setDoOutput(false);
                            _snowman4.connect();
                            if (_snowman4.getResponseCode() / 100 != 2) {
                                RealmsTextureManager.method_21562().remove(this.field_20257);
                                return;
                            }
                            try {
                                bufferedImage = ImageIO.read(_snowman4.getInputStream());
                            }
                            catch (Exception exception) {
                                RealmsTextureManager.method_21562().remove(this.field_20257);
                                if (_snowman4 != null) {
                                    _snowman4.disconnect();
                                }
                                return;
                            }
                            finally {
                                IOUtils.closeQuietly((InputStream)_snowman4.getInputStream());
                            }
                            bufferedImage = new SkinProcessor().process(bufferedImage);
                            _snowman5 = new ByteArrayOutputStream();
                        }
                        catch (Exception exception) {
                            RealmsTextureManager.method_21557().error("Couldn't download http texture", (Throwable)exception);
                            RealmsTextureManager.method_21562().remove(this.field_20257);
                        }
                        finally {
                            if (_snowman4 != null) {
                                _snowman4.disconnect();
                            }
                        }
                        ImageIO.write((RenderedImage)bufferedImage, "png", _snowman5);
                        RealmsTextureManager.method_21565().put(this.field_20257, new Base64().encodeToString(_snowman5.toByteArray()));
                        RealmsTextureManager.method_21562().put(this.field_20257, true);
                        break block17;
                    }
                    RealmsTextureManager.method_21562().put(this.field_20257, true);
                }
            }
        };
        _snowman2.setDaemon(true);
        _snowman2.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static int getTextureId(String id, String image) {
        int _snowman4;
        int _snowman2;
        Object _snowman6;
        if (textures.containsKey(id)) {
            _snowman6 = textures.get(id);
            if (((RealmsTexture)_snowman6).image.equals(image)) {
                return ((RealmsTexture)_snowman6).textureId;
            }
            RenderSystem.deleteTexture(((RealmsTexture)_snowman6).textureId);
            _snowman2 = ((RealmsTexture)_snowman6).textureId;
        } else {
            _snowman2 = GlStateManager.genTextures();
        }
        _snowman6 = null;
        int _snowman3 = 0;
        _snowman4 = 0;
        try {
            BufferedImage bufferedImage;
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new Base64().decode(image));
            try {
                bufferedImage = ImageIO.read(byteArrayInputStream);
            }
            finally {
                IOUtils.closeQuietly((InputStream)byteArrayInputStream);
            }
            _snowman3 = bufferedImage.getWidth();
            _snowman4 = bufferedImage.getHeight();
            int[] _snowman5 = new int[_snowman3 * _snowman4];
            bufferedImage.getRGB(0, 0, _snowman3, _snowman4, _snowman5, 0, _snowman3);
            _snowman6 = ByteBuffer.allocateDirect(4 * _snowman3 * _snowman4).order(ByteOrder.nativeOrder()).asIntBuffer();
            ((IntBuffer)_snowman6).put(_snowman5);
            ((IntBuffer)_snowman6).flip();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        RenderSystem.activeTexture(33984);
        RenderSystem.bindTexture(_snowman2);
        TextureUtil.uploadImage((IntBuffer)_snowman6, _snowman3, _snowman4);
        textures.put(id, new RealmsTexture(image, _snowman2));
        return _snowman2;
    }

    static /* synthetic */ Logger method_21557() {
        return LOGGER;
    }

    static /* synthetic */ Map method_21562() {
        return skinFetchStatus;
    }

    static /* synthetic */ Map method_21565() {
        return fetchedSkins;
    }

    public static class RealmsTexture {
        private final String image;
        private final int textureId;

        public RealmsTexture(String image, int textureId) {
            this.image = image;
            this.textureId = textureId;
        }
    }
}

