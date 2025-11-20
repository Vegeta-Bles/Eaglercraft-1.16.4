package net.minecraft.client.particle;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ItemStackParticleEffect;

public class CrackParticle extends SpriteBillboardParticle {
   private final float sampleU;
   private final float sampleV;

   private CrackParticle(ClientWorld world, double x, double y, double z, double _snowman, double _snowman, double _snowman, ItemStack stack) {
      this(world, x, y, z, stack);
      this.velocityX *= 0.1F;
      this.velocityY *= 0.1F;
      this.velocityZ *= 0.1F;
      this.velocityX += _snowman;
      this.velocityY += _snowman;
      this.velocityZ += _snowman;
   }

   @Override
   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.TERRAIN_SHEET;
   }

   protected CrackParticle(ClientWorld world, double x, double y, double z, ItemStack stack) {
      super(world, x, y, z, 0.0, 0.0, 0.0);
      this.setSprite(MinecraftClient.getInstance().getItemRenderer().getHeldItemModel(stack, world, null).getSprite());
      this.gravityStrength = 1.0F;
      this.scale /= 2.0F;
      this.sampleU = this.random.nextFloat() * 3.0F;
      this.sampleV = this.random.nextFloat() * 3.0F;
   }

   @Override
   protected float getMinU() {
      return this.sprite.getFrameU((double)((this.sampleU + 1.0F) / 4.0F * 16.0F));
   }

   @Override
   protected float getMaxU() {
      return this.sprite.getFrameU((double)(this.sampleU / 4.0F * 16.0F));
   }

   @Override
   protected float getMinV() {
      return this.sprite.getFrameV((double)(this.sampleV / 4.0F * 16.0F));
   }

   @Override
   protected float getMaxV() {
      return this.sprite.getFrameV((double)((this.sampleV + 1.0F) / 4.0F * 16.0F));
   }

   public static class ItemFactory implements ParticleFactory<ItemStackParticleEffect> {
      public ItemFactory() {
      }

      public Particle createParticle(ItemStackParticleEffect _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         return new CrackParticle(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman.getItemStack());
      }
   }

   public static class SlimeballFactory implements ParticleFactory<DefaultParticleType> {
      public SlimeballFactory() {
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         return new CrackParticle(_snowman, _snowman, _snowman, _snowman, new ItemStack(Items.SLIME_BALL));
      }
   }

   public static class SnowballFactory implements ParticleFactory<DefaultParticleType> {
      public SnowballFactory() {
      }

      public Particle createParticle(DefaultParticleType _snowman, ClientWorld _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman, double _snowman) {
         return new CrackParticle(_snowman, _snowman, _snowman, _snowman, new ItemStack(Items.SNOWBALL));
      }
   }
}
