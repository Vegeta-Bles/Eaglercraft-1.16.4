/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ElderGuardianEntityRenderer;
import net.minecraft.client.render.entity.model.GuardianEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class ElderGuardianAppearanceParticle
extends Particle {
    private final Model model = new GuardianEntityModel();
    private final RenderLayer LAYER = RenderLayer.getEntityTranslucent(ElderGuardianEntityRenderer.TEXTURE);

    private ElderGuardianAppearanceParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
        this.gravityStrength = 0.0f;
        this.maxAge = 30;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.CUSTOM;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        float f = ((float)this.age + tickDelta) / (float)this.maxAge;
        _snowman = 0.05f + 0.5f * MathHelper.sin(f * (float)Math.PI);
        MatrixStack _snowman2 = new MatrixStack();
        _snowman2.multiply(camera.getRotation());
        _snowman2.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(150.0f * f - 60.0f));
        _snowman2.scale(-1.0f, -1.0f, 1.0f);
        _snowman2.translate(0.0, -1.101f, 1.5);
        VertexConsumerProvider.Immediate _snowman3 = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer _snowman4 = _snowman3.getBuffer(this.LAYER);
        this.model.render(_snowman2, _snowman4, 0xF000F0, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, _snowman);
        _snowman3.draw();
    }

    public static class Factory
    implements ParticleFactory<DefaultParticleType> {
        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
            return new ElderGuardianAppearanceParticle(clientWorld, d, d2, d3);
        }
    }
}

