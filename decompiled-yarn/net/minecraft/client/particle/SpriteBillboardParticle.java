package net.minecraft.client.particle;

import net.minecraft.client.texture.Sprite;
import net.minecraft.client.world.ClientWorld;

public abstract class SpriteBillboardParticle extends BillboardParticle {
   protected Sprite sprite;

   protected SpriteBillboardParticle(ClientWorld _snowman, double _snowman, double _snowman, double _snowman) {
      super(_snowman, _snowman, _snowman, _snowman);
   }

   protected SpriteBillboardParticle(ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
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
