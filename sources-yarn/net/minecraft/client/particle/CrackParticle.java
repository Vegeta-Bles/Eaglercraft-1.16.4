package net.minecraft.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ItemStackParticleEffect;

@Environment(EnvType.CLIENT)
public class CrackParticle extends SpriteBillboardParticle {
   private final float sampleU;
   private final float sampleV;

   private CrackParticle(ClientWorld world, double x, double y, double z, double g, double h, double i, ItemStack stack) {
      this(world, x, y, z, stack);
      this.velocityX *= 0.1F;
      this.velocityY *= 0.1F;
      this.velocityZ *= 0.1F;
      this.velocityX += g;
      this.velocityY += h;
      this.velocityZ += i;
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

   @Environment(EnvType.CLIENT)
   public static class ItemFactory implements ParticleFactory<ItemStackParticleEffect> {
      public ItemFactory() {
      }

      public Particle createParticle(ItemStackParticleEffect arg, ClientWorld arg2, double d, double e, double f, double g, double h, double i) {
         return new CrackParticle(arg2, d, e, f, g, h, i, arg.getItemStack());
      }
   }

   @Environment(EnvType.CLIENT)
   public static class SlimeballFactory implements ParticleFactory<DefaultParticleType> {
      public SlimeballFactory() {
      }

      public Particle createParticle(DefaultParticleType arg, ClientWorld arg2, double d, double e, double f, double g, double h, double i) {
         return new CrackParticle(arg2, d, e, f, new ItemStack(Items.SLIME_BALL));
      }
   }

   @Environment(EnvType.CLIENT)
   public static class SnowballFactory implements ParticleFactory<DefaultParticleType> {
      public SnowballFactory() {
      }

      public Particle createParticle(DefaultParticleType arg, ClientWorld arg2, double d, double e, double f, double g, double h, double i) {
         return new CrackParticle(arg2, d, e, f, new ItemStack(Items.SNOWBALL));
      }
   }
}
