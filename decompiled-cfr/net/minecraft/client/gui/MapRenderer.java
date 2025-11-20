/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.MaterialColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;

public class MapRenderer
implements AutoCloseable {
    private static final Identifier MAP_ICONS_TEXTURE = new Identifier("textures/map/map_icons.png");
    private static final RenderLayer field_21688 = RenderLayer.getText(MAP_ICONS_TEXTURE);
    private final TextureManager textureManager;
    private final Map<String, MapTexture> mapTextures = Maps.newHashMap();

    public MapRenderer(TextureManager textureManager) {
        this.textureManager = textureManager;
    }

    public void updateTexture(MapState mapState) {
        this.getMapTexture(mapState).updateTexture();
    }

    public void draw(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, MapState mapState, boolean bl, int n) {
        this.getMapTexture(mapState).draw(matrixStack, vertexConsumerProvider, bl, n);
    }

    private MapTexture getMapTexture(MapState mapState) {
        MapTexture mapTexture = this.mapTextures.get(mapState.getId());
        if (mapTexture == null) {
            mapTexture = new MapTexture(mapState);
            this.mapTextures.put(mapState.getId(), mapTexture);
        }
        return mapTexture;
    }

    @Nullable
    public MapTexture getTexture(String string) {
        return this.mapTextures.get(string);
    }

    public void clearStateTextures() {
        for (MapTexture mapTexture : this.mapTextures.values()) {
            mapTexture.close();
        }
        this.mapTextures.clear();
    }

    @Nullable
    public MapState getState(@Nullable MapTexture texture) {
        if (texture != null) {
            return texture.mapState;
        }
        return null;
    }

    @Override
    public void close() {
        this.clearStateTextures();
    }

    class MapTexture
    implements AutoCloseable {
        private final MapState mapState;
        private final NativeImageBackedTexture texture;
        private final RenderLayer field_21689;

        private MapTexture(MapState mapState) {
            this.mapState = mapState;
            this.texture = new NativeImageBackedTexture(128, 128, true);
            Identifier identifier = MapRenderer.this.textureManager.registerDynamicTexture("map/" + mapState.getId(), this.texture);
            this.field_21689 = RenderLayer.getText(identifier);
        }

        private void updateTexture() {
            for (int i = 0; i < 128; ++i) {
                for (_snowman = 0; _snowman < 128; ++_snowman) {
                    _snowman = _snowman + i * 128;
                    _snowman = this.mapState.colors[_snowman] & 0xFF;
                    if (_snowman / 4 == 0) {
                        this.texture.getImage().setPixelColor(_snowman, i, 0);
                        continue;
                    }
                    this.texture.getImage().setPixelColor(_snowman, i, MaterialColor.COLORS[_snowman / 4].getRenderColor(_snowman & 3));
                }
            }
            this.texture.upload();
        }

        private void draw(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, boolean bl, int n) {
            boolean bl2 = false;
            _snowman = false;
            float _snowman2 = 0.0f;
            Matrix4f _snowman3 = matrixStack.peek().getModel();
            VertexConsumer _snowman4 = vertexConsumerProvider.getBuffer(this.field_21689);
            _snowman4.vertex(_snowman3, 0.0f, 128.0f, -0.01f).color(255, 255, 255, 255).texture(0.0f, 1.0f).light(n).next();
            _snowman4.vertex(_snowman3, 128.0f, 128.0f, -0.01f).color(255, 255, 255, 255).texture(1.0f, 1.0f).light(n).next();
            _snowman4.vertex(_snowman3, 128.0f, 0.0f, -0.01f).color(255, 255, 255, 255).texture(1.0f, 0.0f).light(n).next();
            _snowman4.vertex(_snowman3, 0.0f, 0.0f, -0.01f).color(255, 255, 255, 255).texture(0.0f, 0.0f).light(n).next();
            int _snowman5 = 0;
            for (MapIcon mapIcon : this.mapState.icons.values()) {
                if (bl && !mapIcon.isAlwaysRendered()) continue;
                matrixStack.push();
                matrixStack.translate(0.0f + (float)mapIcon.getX() / 2.0f + 64.0f, 0.0f + (float)mapIcon.getZ() / 2.0f + 64.0f, -0.02f);
                matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)(mapIcon.getRotation() * 360) / 16.0f));
                matrixStack.scale(4.0f, 4.0f, 3.0f);
                matrixStack.translate(-0.125, 0.125, 0.0);
                byte by = mapIcon.getTypeId();
                float _snowman6 = (float)(by % 16 + 0) / 16.0f;
                float _snowman7 = (float)(by / 16 + 0) / 16.0f;
                float _snowman8 = (float)(by % 16 + 1) / 16.0f;
                float _snowman9 = (float)(by / 16 + 1) / 16.0f;
                Matrix4f _snowman10 = matrixStack.peek().getModel();
                float _snowman11 = -0.001f;
                VertexConsumer _snowman12 = vertexConsumerProvider.getBuffer(field_21688);
                _snowman12.vertex(_snowman10, -1.0f, 1.0f, (float)_snowman5 * -0.001f).color(255, 255, 255, 255).texture(_snowman6, _snowman7).light(n).next();
                _snowman12.vertex(_snowman10, 1.0f, 1.0f, (float)_snowman5 * -0.001f).color(255, 255, 255, 255).texture(_snowman8, _snowman7).light(n).next();
                _snowman12.vertex(_snowman10, 1.0f, -1.0f, (float)_snowman5 * -0.001f).color(255, 255, 255, 255).texture(_snowman8, _snowman9).light(n).next();
                _snowman12.vertex(_snowman10, -1.0f, -1.0f, (float)_snowman5 * -0.001f).color(255, 255, 255, 255).texture(_snowman6, _snowman9).light(n).next();
                matrixStack.pop();
                if (mapIcon.getText() != null) {
                    TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
                    Text _snowman13 = mapIcon.getText();
                    float _snowman14 = textRenderer.getWidth(_snowman13);
                    float f = 25.0f / _snowman14;
                    textRenderer.getClass();
                    float _snowman15 = MathHelper.clamp(f, 0.0f, 6.0f / 9.0f);
                    matrixStack.push();
                    matrixStack.translate(0.0f + (float)mapIcon.getX() / 2.0f + 64.0f - _snowman14 * _snowman15 / 2.0f, 0.0f + (float)mapIcon.getZ() / 2.0f + 64.0f + 4.0f, -0.025f);
                    matrixStack.scale(_snowman15, _snowman15, 1.0f);
                    matrixStack.translate(0.0, 0.0, -0.1f);
                    textRenderer.draw(_snowman13, 0.0f, 0.0f, -1, false, matrixStack.peek().getModel(), vertexConsumerProvider, false, Integer.MIN_VALUE, n);
                    matrixStack.pop();
                }
                ++_snowman5;
            }
        }

        @Override
        public void close() {
            this.texture.close();
        }
    }
}

