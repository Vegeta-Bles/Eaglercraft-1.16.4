/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class LightmapTextureManager
implements AutoCloseable {
    private final NativeImageBackedTexture texture;
    private final NativeImage image;
    private final Identifier textureIdentifier;
    private boolean dirty;
    private float field_21528;
    private final GameRenderer renderer;
    private final MinecraftClient client;

    public LightmapTextureManager(GameRenderer renderer, MinecraftClient client) {
        this.renderer = renderer;
        this.client = client;
        this.texture = new NativeImageBackedTexture(16, 16, false);
        this.textureIdentifier = this.client.getTextureManager().registerDynamicTexture("light_map", this.texture);
        this.image = this.texture.getImage();
        for (int i = 0; i < 16; ++i) {
            for (_snowman = 0; _snowman < 16; ++_snowman) {
                this.image.setPixelColor(_snowman, i, -1);
            }
        }
        this.texture.upload();
    }

    @Override
    public void close() {
        this.texture.close();
    }

    public void tick() {
        this.field_21528 = (float)((double)this.field_21528 + (Math.random() - Math.random()) * Math.random() * Math.random() * 0.1);
        this.field_21528 = (float)((double)this.field_21528 * 0.9);
        this.dirty = true;
    }

    public void disable() {
        RenderSystem.activeTexture(33986);
        RenderSystem.disableTexture();
        RenderSystem.activeTexture(33984);
    }

    public void enable() {
        RenderSystem.activeTexture(33986);
        RenderSystem.matrixMode(5890);
        RenderSystem.loadIdentity();
        float f = 0.00390625f;
        RenderSystem.scalef(0.00390625f, 0.00390625f, 0.00390625f);
        RenderSystem.translatef(8.0f, 8.0f, 8.0f);
        RenderSystem.matrixMode(5888);
        this.client.getTextureManager().bindTexture(this.textureIdentifier);
        RenderSystem.texParameter(3553, 10241, 9729);
        RenderSystem.texParameter(3553, 10240, 9729);
        RenderSystem.texParameter(3553, 10242, 10496);
        RenderSystem.texParameter(3553, 10243, 10496);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableTexture();
        RenderSystem.activeTexture(33984);
    }

    public void update(float delta) {
        if (!this.dirty) {
            return;
        }
        this.dirty = false;
        this.client.getProfiler().push("lightTex");
        ClientWorld clientWorld = this.client.world;
        if (clientWorld == null) {
            return;
        }
        float _snowman2 = clientWorld.method_23783(1.0f);
        float _snowman3 = clientWorld.getLightningTicksLeft() > 0 ? 1.0f : _snowman2 * 0.95f + 0.05f;
        float _snowman4 = this.client.player.getUnderwaterVisibility();
        float _snowman5 = this.client.player.hasStatusEffect(StatusEffects.NIGHT_VISION) ? GameRenderer.getNightVisionStrength(this.client.player, delta) : (_snowman4 > 0.0f && this.client.player.hasStatusEffect(StatusEffects.CONDUIT_POWER) ? _snowman4 : 0.0f);
        Vector3f _snowman6 = new Vector3f(_snowman2, _snowman2, 1.0f);
        _snowman6.lerp(new Vector3f(1.0f, 1.0f, 1.0f), 0.35f);
        float _snowman7 = this.field_21528 + 1.5f;
        Vector3f _snowman8 = new Vector3f();
        for (int i = 0; i < 16; ++i) {
            for (_snowman = 0; _snowman < 16; ++_snowman) {
                Vector3f _snowman9;
                float f = this.getBrightness(clientWorld, i) * _snowman3;
                _snowman = _snowman = this.getBrightness(clientWorld, _snowman) * _snowman7;
                _snowman = _snowman * ((_snowman * 0.6f + 0.4f) * 0.6f + 0.4f);
                _snowman = _snowman * (_snowman * _snowman * 0.6f + 0.4f);
                _snowman8.set(_snowman, _snowman, _snowman);
                if (clientWorld.getSkyProperties().shouldBrightenLighting()) {
                    _snowman8.lerp(new Vector3f(0.99f, 1.12f, 1.0f), 0.25f);
                } else {
                    Vector3f vector3f = _snowman6.copy();
                    vector3f.scale(f);
                    _snowman8.add(vector3f);
                    _snowman8.lerp(new Vector3f(0.75f, 0.75f, 0.75f), 0.04f);
                    if (this.renderer.getSkyDarkness(delta) > 0.0f) {
                        float f2 = this.renderer.getSkyDarkness(delta);
                        _snowman9 = _snowman8.copy();
                        _snowman9.multiplyComponentwise(0.7f, 0.6f, 0.6f);
                        _snowman8.lerp(_snowman9, f2);
                    }
                }
                _snowman8.clamp(0.0f, 1.0f);
                if (_snowman5 > 0.0f && (_snowman = Math.max(_snowman8.getX(), Math.max(_snowman8.getY(), _snowman8.getZ()))) < 1.0f) {
                    f2 = 1.0f / _snowman;
                    _snowman9 = _snowman8.copy();
                    _snowman9.scale(f2);
                    _snowman8.lerp(_snowman9, _snowman5);
                }
                _snowman = (float)this.client.options.gamma;
                Vector3f vector3f = _snowman8.copy();
                vector3f.modify(this::method_23795);
                _snowman8.lerp(vector3f, _snowman);
                _snowman8.lerp(new Vector3f(0.75f, 0.75f, 0.75f), 0.04f);
                _snowman8.clamp(0.0f, 1.0f);
                _snowman8.scale(255.0f);
                int _snowman10 = 255;
                int _snowman11 = (int)_snowman8.getX();
                int _snowman12 = (int)_snowman8.getY();
                int _snowman13 = (int)_snowman8.getZ();
                this.image.setPixelColor(_snowman, i, 0xFF000000 | _snowman13 << 16 | _snowman12 << 8 | _snowman11);
            }
        }
        this.texture.upload();
        this.client.getProfiler().pop();
    }

    private float method_23795(float f) {
        _snowman = 1.0f - f;
        return 1.0f - _snowman * _snowman * _snowman * _snowman;
    }

    private float getBrightness(World world, int n) {
        return world.getDimension().method_28516(n);
    }

    public static int pack(int block, int sky) {
        return block << 4 | sky << 20;
    }

    public static int getBlockLightCoordinates(int light) {
        return light >> 4 & 0xFFFF;
    }

    public static int getSkyLightCoordinates(int light) {
        return light >> 20 & 0xFFFF;
    }
}

