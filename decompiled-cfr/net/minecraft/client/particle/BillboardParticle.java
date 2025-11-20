/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;

public abstract class BillboardParticle
extends Particle {
    protected float scale;

    protected BillboardParticle(ClientWorld clientWorld, double d, double d2, double d3) {
        super(clientWorld, d, d2, d3);
        this.scale = 0.1f * (this.random.nextFloat() * 0.5f + 0.5f) * 2.0f;
    }

    protected BillboardParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
        super(clientWorld, d, d2, d3, d4, d5, d6);
        this.scale = 0.1f * (this.random.nextFloat() * 0.5f + 0.5f) * 2.0f;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Quaternion quaternion;
        Vec3d vec3d = camera.getPos();
        float _snowman2 = (float)(MathHelper.lerp((double)tickDelta, this.prevPosX, this.x) - vec3d.getX());
        float _snowman3 = (float)(MathHelper.lerp((double)tickDelta, this.prevPosY, this.y) - vec3d.getY());
        float _snowman4 = (float)(MathHelper.lerp((double)tickDelta, this.prevPosZ, this.z) - vec3d.getZ());
        if (this.angle == 0.0f) {
            quaternion = camera.getRotation();
        } else {
            quaternion = new Quaternion(camera.getRotation());
            float _snowman5 = MathHelper.lerp(tickDelta, this.prevAngle, this.angle);
            quaternion.hamiltonProduct(Vector3f.POSITIVE_Z.getRadialQuaternion(_snowman5));
        }
        Vector3f vector3f = new Vector3f(-1.0f, -1.0f, 0.0f);
        vector3f.rotate(quaternion);
        Vector3f[] _snowman6 = new Vector3f[]{new Vector3f(-1.0f, -1.0f, 0.0f), new Vector3f(-1.0f, 1.0f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector3f(1.0f, -1.0f, 0.0f)};
        float _snowman7 = this.getSize(tickDelta);
        for (int i = 0; i < 4; ++i) {
            Vector3f vector3f2 = _snowman6[i];
            vector3f2.rotate(quaternion);
            vector3f2.scale(_snowman7);
            vector3f2.add(_snowman2, _snowman3, _snowman4);
        }
        float f = this.getMinU();
        _snowman = this.getMaxU();
        _snowman = this.getMinV();
        _snowman = this.getMaxV();
        int _snowman8 = this.getColorMultiplier(tickDelta);
        vertexConsumer.vertex(_snowman6[0].getX(), _snowman6[0].getY(), _snowman6[0].getZ()).texture(_snowman, _snowman).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(_snowman8).next();
        vertexConsumer.vertex(_snowman6[1].getX(), _snowman6[1].getY(), _snowman6[1].getZ()).texture(_snowman, _snowman).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(_snowman8).next();
        vertexConsumer.vertex(_snowman6[2].getX(), _snowman6[2].getY(), _snowman6[2].getZ()).texture(f, _snowman).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(_snowman8).next();
        vertexConsumer.vertex(_snowman6[3].getX(), _snowman6[3].getY(), _snowman6[3].getZ()).texture(f, _snowman).color(this.colorRed, this.colorGreen, this.colorBlue, this.colorAlpha).light(_snowman8).next();
    }

    public float getSize(float tickDelta) {
        return this.scale;
    }

    @Override
    public Particle scale(float scale) {
        this.scale *= scale;
        return super.scale(scale);
    }

    protected abstract float getMinU();

    protected abstract float getMaxU();

    protected abstract float getMinV();

    protected abstract float getMaxV();
}

