/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.particle;

import net.minecraft.client.particle.BillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;

public abstract class SpriteBillboardParticle
extends BillboardParticle {
    protected Sprite sprite;

    protected SpriteBillboardParticle(ClientWorld clientWorld, double d, double d2, double d3) {
        super(clientWorld, d, d2, d3);
    }

    protected SpriteBillboardParticle(ClientWorld clientWorld, double d, double d2, double d3, double d4, double d5, double d6) {
        super(clientWorld, d, d2, d3, d4, d5, d6);
    }

    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    protected float getMinU() {
        return this.sprite.getMinU();
    }

    @Override
    protected float getMaxU() {
        return this.sprite.getMaxU();
    }

    @Override
    protected float getMinV() {
        return this.sprite.getMinV();
    }

    @Override
    protected float getMaxV() {
        return this.sprite.getMaxV();
    }

    public void setSprite(SpriteProvider spriteProvider) {
        this.setSprite(spriteProvider.getSprite(this.random));
    }

    public void setSpriteForAge(SpriteProvider spriteProvider) {
        this.setSprite(spriteProvider.getSprite(this.age, this.maxAge));
    }
}

