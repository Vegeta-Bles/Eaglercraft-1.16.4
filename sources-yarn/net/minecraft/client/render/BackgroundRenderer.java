package net.minecraft.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;

@Environment(EnvType.CLIENT)
public class BackgroundRenderer {
   private static float red;
   private static float green;
   private static float blue;
   private static int waterFogColor = -1;
   private static int nextWaterFogColor = -1;
   private static long lastWaterFogColorUpdateTime = -1L;

   public static void render(Camera camera, float tickDelta, ClientWorld world, int i, float g) {
      FluidState lv = camera.getSubmergedFluidState();
      if (lv.isIn(FluidTags.WATER)) {
         long l = Util.getMeasuringTimeMs();
         int j = world.getBiome(new BlockPos(camera.getPos())).getWaterFogColor();
         if (lastWaterFogColorUpdateTime < 0L) {
            waterFogColor = j;
            nextWaterFogColor = j;
            lastWaterFogColorUpdateTime = l;
         }

         int k = waterFogColor >> 16 & 0xFF;
         int m = waterFogColor >> 8 & 0xFF;
         int n = waterFogColor & 0xFF;
         int o = nextWaterFogColor >> 16 & 0xFF;
         int p = nextWaterFogColor >> 8 & 0xFF;
         int q = nextWaterFogColor & 0xFF;
         float h = MathHelper.clamp((float)(l - lastWaterFogColorUpdateTime) / 5000.0F, 0.0F, 1.0F);
         float r = MathHelper.lerp(h, (float)o, (float)k);
         float s = MathHelper.lerp(h, (float)p, (float)m);
         float t = MathHelper.lerp(h, (float)q, (float)n);
         red = r / 255.0F;
         green = s / 255.0F;
         blue = t / 255.0F;
         if (waterFogColor != j) {
            waterFogColor = j;
            nextWaterFogColor = MathHelper.floor(r) << 16 | MathHelper.floor(s) << 8 | MathHelper.floor(t);
            lastWaterFogColorUpdateTime = l;
         }
      } else if (lv.isIn(FluidTags.LAVA)) {
         red = 0.6F;
         green = 0.1F;
         blue = 0.0F;
         lastWaterFogColorUpdateTime = -1L;
      } else {
         float u = 0.25F + 0.75F * (float)i / 32.0F;
         u = 1.0F - (float)Math.pow((double)u, 0.25);
         Vec3d lv2 = world.method_23777(camera.getBlockPos(), tickDelta);
         float v = (float)lv2.x;
         float w = (float)lv2.y;
         float x = (float)lv2.z;
         float y = MathHelper.clamp(MathHelper.cos(world.getSkyAngle(tickDelta) * (float) (Math.PI * 2)) * 2.0F + 0.5F, 0.0F, 1.0F);
         BiomeAccess lv3 = world.getBiomeAccess();
         Vec3d lv4 = camera.getPos().subtract(2.0, 2.0, 2.0).multiply(0.25);
         Vec3d lv5 = CubicSampler.sampleColor(
            lv4, (ix, jx, k) -> world.getSkyProperties().adjustFogColor(Vec3d.unpackRgb(lv3.getBiomeForNoiseGen(ix, jx, k).getFogColor()), y)
         );
         red = (float)lv5.getX();
         green = (float)lv5.getY();
         blue = (float)lv5.getZ();
         if (i >= 4) {
            float z = MathHelper.sin(world.getSkyAngleRadians(tickDelta)) > 0.0F ? -1.0F : 1.0F;
            Vector3f lv6 = new Vector3f(z, 0.0F, 0.0F);
            float aa = camera.getHorizontalPlane().dot(lv6);
            if (aa < 0.0F) {
               aa = 0.0F;
            }

            if (aa > 0.0F) {
               float[] fs = world.getSkyProperties().getFogColorOverride(world.getSkyAngle(tickDelta), tickDelta);
               if (fs != null) {
                  aa *= fs[3];
                  red = red * (1.0F - aa) + fs[0] * aa;
                  green = green * (1.0F - aa) + fs[1] * aa;
                  blue = blue * (1.0F - aa) + fs[2] * aa;
               }
            }
         }

         red = red + (v - red) * u;
         green = green + (w - green) * u;
         blue = blue + (x - blue) * u;
         float ab = world.getRainGradient(tickDelta);
         if (ab > 0.0F) {
            float ac = 1.0F - ab * 0.5F;
            float ad = 1.0F - ab * 0.4F;
            red *= ac;
            green *= ac;
            blue *= ad;
         }

         float ae = world.getThunderGradient(tickDelta);
         if (ae > 0.0F) {
            float af = 1.0F - ae * 0.5F;
            red *= af;
            green *= af;
            blue *= af;
         }

         lastWaterFogColorUpdateTime = -1L;
      }

      double d = camera.getPos().y * world.getLevelProperties().getHorizonShadingRatio();
      if (camera.getFocusedEntity() instanceof LivingEntity && ((LivingEntity)camera.getFocusedEntity()).hasStatusEffect(StatusEffects.BLINDNESS)) {
         int ag = ((LivingEntity)camera.getFocusedEntity()).getStatusEffect(StatusEffects.BLINDNESS).getDuration();
         if (ag < 20) {
            d *= (double)(1.0F - (float)ag / 20.0F);
         } else {
            d = 0.0;
         }
      }

      if (d < 1.0 && !lv.isIn(FluidTags.LAVA)) {
         if (d < 0.0) {
            d = 0.0;
         }

         d *= d;
         red = (float)((double)red * d);
         green = (float)((double)green * d);
         blue = (float)((double)blue * d);
      }

      if (g > 0.0F) {
         red = red * (1.0F - g) + red * 0.7F * g;
         green = green * (1.0F - g) + green * 0.6F * g;
         blue = blue * (1.0F - g) + blue * 0.6F * g;
      }

      if (lv.isIn(FluidTags.WATER)) {
         float ah = 0.0F;
         if (camera.getFocusedEntity() instanceof ClientPlayerEntity) {
            ClientPlayerEntity lv7 = (ClientPlayerEntity)camera.getFocusedEntity();
            ah = lv7.getUnderwaterVisibility();
         }

         float ai = Math.min(1.0F / red, Math.min(1.0F / green, 1.0F / blue));
         red = red * (1.0F - ah) + red * ai * ah;
         green = green * (1.0F - ah) + green * ai * ah;
         blue = blue * (1.0F - ah) + blue * ai * ah;
      } else if (camera.getFocusedEntity() instanceof LivingEntity && ((LivingEntity)camera.getFocusedEntity()).hasStatusEffect(StatusEffects.NIGHT_VISION)) {
         float aj = GameRenderer.getNightVisionStrength((LivingEntity)camera.getFocusedEntity(), tickDelta);
         float ak = Math.min(1.0F / red, Math.min(1.0F / green, 1.0F / blue));
         red = red * (1.0F - aj) + red * ak * aj;
         green = green * (1.0F - aj) + green * ak * aj;
         blue = blue * (1.0F - aj) + blue * ak * aj;
      }

      RenderSystem.clearColor(red, green, blue, 0.0F);
   }

   public static void method_23792() {
      RenderSystem.fogDensity(0.0F);
      RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
   }

   public static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog) {
      FluidState lv = camera.getSubmergedFluidState();
      Entity lv2 = camera.getFocusedEntity();
      if (lv.isIn(FluidTags.WATER)) {
         float g = 1.0F;
         g = 0.05F;
         if (lv2 instanceof ClientPlayerEntity) {
            ClientPlayerEntity lv3 = (ClientPlayerEntity)lv2;
            g -= lv3.getUnderwaterVisibility() * lv3.getUnderwaterVisibility() * 0.03F;
            Biome lv4 = lv3.world.getBiome(lv3.getBlockPos());
            if (lv4.getCategory() == Biome.Category.SWAMP) {
               g += 0.005F;
            }
         }

         RenderSystem.fogDensity(g);
         RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
      } else {
         float h;
         float i;
         if (lv.isIn(FluidTags.LAVA)) {
            if (lv2 instanceof LivingEntity && ((LivingEntity)lv2).hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
               h = 0.0F;
               i = 3.0F;
            } else {
               h = 0.25F;
               i = 1.0F;
            }
         } else if (lv2 instanceof LivingEntity && ((LivingEntity)lv2).hasStatusEffect(StatusEffects.BLINDNESS)) {
            int l = ((LivingEntity)lv2).getStatusEffect(StatusEffects.BLINDNESS).getDuration();
            float m = MathHelper.lerp(Math.min(1.0F, (float)l / 20.0F), viewDistance, 5.0F);
            if (fogType == BackgroundRenderer.FogType.FOG_SKY) {
               h = 0.0F;
               i = m * 0.8F;
            } else {
               h = m * 0.25F;
               i = m;
            }
         } else if (thickFog) {
            h = viewDistance * 0.05F;
            i = Math.min(viewDistance, 192.0F) * 0.5F;
         } else if (fogType == BackgroundRenderer.FogType.FOG_SKY) {
            h = 0.0F;
            i = viewDistance;
         } else {
            h = viewDistance * 0.75F;
            i = viewDistance;
         }

         RenderSystem.fogStart(h);
         RenderSystem.fogEnd(i);
         RenderSystem.fogMode(GlStateManager.FogMode.LINEAR);
         RenderSystem.setupNvFogDistance();
      }
   }

   public static void setFogBlack() {
      RenderSystem.fog(2918, red, green, blue, 1.0F);
   }

   @Environment(EnvType.CLIENT)
   public static enum FogType {
      FOG_SKY,
      FOG_TERRAIN;

      private FogType() {
      }
   }
}
