package net.minecraft.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
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

public class BackgroundRenderer {
   private static float red;
   private static float green;
   private static float blue;
   private static int waterFogColor = -1;
   private static int nextWaterFogColor = -1;
   private static long lastWaterFogColorUpdateTime = -1L;

   public static void render(Camera camera, float tickDelta, ClientWorld world, int _snowman, float _snowman) {
      FluidState _snowmanxx = camera.getSubmergedFluidState();
      if (_snowmanxx.isIn(FluidTags.WATER)) {
         long _snowmanxxx = Util.getMeasuringTimeMs();
         int _snowmanxxxx = world.getBiome(new BlockPos(camera.getPos())).getWaterFogColor();
         if (lastWaterFogColorUpdateTime < 0L) {
            waterFogColor = _snowmanxxxx;
            nextWaterFogColor = _snowmanxxxx;
            lastWaterFogColorUpdateTime = _snowmanxxx;
         }

         int _snowmanxxxxx = waterFogColor >> 16 & 0xFF;
         int _snowmanxxxxxx = waterFogColor >> 8 & 0xFF;
         int _snowmanxxxxxxx = waterFogColor & 0xFF;
         int _snowmanxxxxxxxx = nextWaterFogColor >> 16 & 0xFF;
         int _snowmanxxxxxxxxx = nextWaterFogColor >> 8 & 0xFF;
         int _snowmanxxxxxxxxxx = nextWaterFogColor & 0xFF;
         float _snowmanxxxxxxxxxxx = MathHelper.clamp((float)(_snowmanxxx - lastWaterFogColorUpdateTime) / 5000.0F, 0.0F, 1.0F);
         float _snowmanxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxxxxx, (float)_snowmanxxxxxxxx, (float)_snowmanxxxxx);
         float _snowmanxxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxxxxx, (float)_snowmanxxxxxxxxx, (float)_snowmanxxxxxx);
         float _snowmanxxxxxxxxxxxxxx = MathHelper.lerp(_snowmanxxxxxxxxxxx, (float)_snowmanxxxxxxxxxx, (float)_snowmanxxxxxxx);
         red = _snowmanxxxxxxxxxxxx / 255.0F;
         green = _snowmanxxxxxxxxxxxxx / 255.0F;
         blue = _snowmanxxxxxxxxxxxxxx / 255.0F;
         if (waterFogColor != _snowmanxxxx) {
            waterFogColor = _snowmanxxxx;
            nextWaterFogColor = MathHelper.floor(_snowmanxxxxxxxxxxxx) << 16 | MathHelper.floor(_snowmanxxxxxxxxxxxxx) << 8 | MathHelper.floor(_snowmanxxxxxxxxxxxxxx);
            lastWaterFogColorUpdateTime = _snowmanxxx;
         }
      } else if (_snowmanxx.isIn(FluidTags.LAVA)) {
         red = 0.6F;
         green = 0.1F;
         blue = 0.0F;
         lastWaterFogColorUpdateTime = -1L;
      } else {
         float _snowmanxxxxx = 0.25F + 0.75F * (float)_snowman / 32.0F;
         _snowmanxxxxx = 1.0F - (float)Math.pow((double)_snowmanxxxxx, 0.25);
         Vec3d _snowmanxxxxxx = world.method_23777(camera.getBlockPos(), tickDelta);
         float _snowmanxxxxxxx = (float)_snowmanxxxxxx.x;
         float _snowmanxxxxxxxx = (float)_snowmanxxxxxx.y;
         float _snowmanxxxxxxxxx = (float)_snowmanxxxxxx.z;
         float _snowmanxxxxxxxxxx = MathHelper.clamp(MathHelper.cos(world.getSkyAngle(tickDelta) * (float) (Math.PI * 2)) * 2.0F + 0.5F, 0.0F, 1.0F);
         BiomeAccess _snowmanxxxxxxxxxxx = world.getBiomeAccess();
         Vec3d _snowmanxxxxxxxxxxxx = camera.getPos().subtract(2.0, 2.0, 2.0).multiply(0.25);
         Vec3d _snowmanxxxxxxxxxxxxx = CubicSampler.sampleColor(
            _snowmanxxxxxxxxxxxx,
            (_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx) -> world.getSkyProperties()
                  .adjustFogColor(Vec3d.unpackRgb(_snowman.getBiomeForNoiseGen(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx).getFogColor()), _snowman)
         );
         red = (float)_snowmanxxxxxxxxxxxxx.getX();
         green = (float)_snowmanxxxxxxxxxxxxx.getY();
         blue = (float)_snowmanxxxxxxxxxxxxx.getZ();
         if (_snowman >= 4) {
            float _snowmanxxxxxxxxxxxxxx = MathHelper.sin(world.getSkyAngleRadians(tickDelta)) > 0.0F ? -1.0F : 1.0F;
            Vector3f _snowmanxxxxxxxxxxxxxxx = new Vector3f(_snowmanxxxxxxxxxxxxxx, 0.0F, 0.0F);
            float _snowmanxxxxxxxxxxxxxxxx = camera.getHorizontalPlane().dot(_snowmanxxxxxxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxxxxxx < 0.0F) {
               _snowmanxxxxxxxxxxxxxxxx = 0.0F;
            }

            if (_snowmanxxxxxxxxxxxxxxxx > 0.0F) {
               float[] _snowmanxxxxxxxxxxxxxxxxx = world.getSkyProperties().getFogColorOverride(world.getSkyAngle(tickDelta), tickDelta);
               if (_snowmanxxxxxxxxxxxxxxxxx != null) {
                  _snowmanxxxxxxxxxxxxxxxx *= _snowmanxxxxxxxxxxxxxxxxx[3];
                  red = red * (1.0F - _snowmanxxxxxxxxxxxxxxxx) + _snowmanxxxxxxxxxxxxxxxxx[0] * _snowmanxxxxxxxxxxxxxxxx;
                  green = green * (1.0F - _snowmanxxxxxxxxxxxxxxxx) + _snowmanxxxxxxxxxxxxxxxxx[1] * _snowmanxxxxxxxxxxxxxxxx;
                  blue = blue * (1.0F - _snowmanxxxxxxxxxxxxxxxx) + _snowmanxxxxxxxxxxxxxxxxx[2] * _snowmanxxxxxxxxxxxxxxxx;
               }
            }
         }

         red = red + (_snowmanxxxxxxx - red) * _snowmanxxxxx;
         green = green + (_snowmanxxxxxxxx - green) * _snowmanxxxxx;
         blue = blue + (_snowmanxxxxxxxxx - blue) * _snowmanxxxxx;
         float _snowmanxxxxxxxxxxxxxxxxx = world.getRainGradient(tickDelta);
         if (_snowmanxxxxxxxxxxxxxxxxx > 0.0F) {
            float _snowmanxxxxxxxxxxxxxxxxxx = 1.0F - _snowmanxxxxxxxxxxxxxxxxx * 0.5F;
            float _snowmanxxxxxxxxxxxxxxxxxxx = 1.0F - _snowmanxxxxxxxxxxxxxxxxx * 0.4F;
            red *= _snowmanxxxxxxxxxxxxxxxxxx;
            green *= _snowmanxxxxxxxxxxxxxxxxxx;
            blue *= _snowmanxxxxxxxxxxxxxxxxxxx;
         }

         float _snowmanxxxxxxxxxxxxxxxxxx = world.getThunderGradient(tickDelta);
         if (_snowmanxxxxxxxxxxxxxxxxxx > 0.0F) {
            float _snowmanxxxxxxxxxxxxxxxxxxx = 1.0F - _snowmanxxxxxxxxxxxxxxxxxx * 0.5F;
            red *= _snowmanxxxxxxxxxxxxxxxxxxx;
            green *= _snowmanxxxxxxxxxxxxxxxxxxx;
            blue *= _snowmanxxxxxxxxxxxxxxxxxxx;
         }

         lastWaterFogColorUpdateTime = -1L;
      }

      double _snowmanxxxxxxxxxxxxxxxxxx = camera.getPos().y * world.getLevelProperties().getHorizonShadingRatio();
      if (camera.getFocusedEntity() instanceof LivingEntity && ((LivingEntity)camera.getFocusedEntity()).hasStatusEffect(StatusEffects.BLINDNESS)) {
         int _snowmanxxxxxxxxxxxxxxxxxxx = ((LivingEntity)camera.getFocusedEntity()).getStatusEffect(StatusEffects.BLINDNESS).getDuration();
         if (_snowmanxxxxxxxxxxxxxxxxxxx < 20) {
            _snowmanxxxxxxxxxxxxxxxxxx *= (double)(1.0F - (float)_snowmanxxxxxxxxxxxxxxxxxxx / 20.0F);
         } else {
            _snowmanxxxxxxxxxxxxxxxxxx = 0.0;
         }
      }

      if (_snowmanxxxxxxxxxxxxxxxxxx < 1.0 && !_snowmanxx.isIn(FluidTags.LAVA)) {
         if (_snowmanxxxxxxxxxxxxxxxxxx < 0.0) {
            _snowmanxxxxxxxxxxxxxxxxxx = 0.0;
         }

         _snowmanxxxxxxxxxxxxxxxxxx *= _snowmanxxxxxxxxxxxxxxxxxx;
         red = (float)((double)red * _snowmanxxxxxxxxxxxxxxxxxx);
         green = (float)((double)green * _snowmanxxxxxxxxxxxxxxxxxx);
         blue = (float)((double)blue * _snowmanxxxxxxxxxxxxxxxxxx);
      }

      if (_snowman > 0.0F) {
         red = red * (1.0F - _snowman) + red * 0.7F * _snowman;
         green = green * (1.0F - _snowman) + green * 0.6F * _snowman;
         blue = blue * (1.0F - _snowman) + blue * 0.6F * _snowman;
      }

      if (_snowmanxx.isIn(FluidTags.WATER)) {
         float _snowmanxxxxxxxxxxxxxxxxxxx = 0.0F;
         if (camera.getFocusedEntity() instanceof ClientPlayerEntity) {
            ClientPlayerEntity _snowmanxxxxxxxxxxxxxxxxxxxx = (ClientPlayerEntity)camera.getFocusedEntity();
            _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx.getUnderwaterVisibility();
         }

         float _snowmanxxxxxxxxxxxxxxxxxxxx = Math.min(1.0F / red, Math.min(1.0F / green, 1.0F / blue));
         red = red * (1.0F - _snowmanxxxxxxxxxxxxxxxxxxx) + red * _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx;
         green = green * (1.0F - _snowmanxxxxxxxxxxxxxxxxxxx) + green * _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx;
         blue = blue * (1.0F - _snowmanxxxxxxxxxxxxxxxxxxx) + blue * _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx;
      } else if (camera.getFocusedEntity() instanceof LivingEntity && ((LivingEntity)camera.getFocusedEntity()).hasStatusEffect(StatusEffects.NIGHT_VISION)) {
         float _snowmanxxxxxxxxxxxxxxxxxxx = GameRenderer.getNightVisionStrength((LivingEntity)camera.getFocusedEntity(), tickDelta);
         float _snowmanxxxxxxxxxxxxxxxxxxxx = Math.min(1.0F / red, Math.min(1.0F / green, 1.0F / blue));
         red = red * (1.0F - _snowmanxxxxxxxxxxxxxxxxxxx) + red * _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx;
         green = green * (1.0F - _snowmanxxxxxxxxxxxxxxxxxxx) + green * _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx;
         blue = blue * (1.0F - _snowmanxxxxxxxxxxxxxxxxxxx) + blue * _snowmanxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxx;
      }

      RenderSystem.clearColor(red, green, blue, 0.0F);
   }

   public static void method_23792() {
      RenderSystem.fogDensity(0.0F);
      RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
   }

   public static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog) {
      FluidState _snowman = camera.getSubmergedFluidState();
      Entity _snowmanx = camera.getFocusedEntity();
      if (_snowman.isIn(FluidTags.WATER)) {
         float _snowmanxx = 1.0F;
         _snowmanxx = 0.05F;
         if (_snowmanx instanceof ClientPlayerEntity) {
            ClientPlayerEntity _snowmanxxx = (ClientPlayerEntity)_snowmanx;
            _snowmanxx -= _snowmanxxx.getUnderwaterVisibility() * _snowmanxxx.getUnderwaterVisibility() * 0.03F;
            Biome _snowmanxxxx = _snowmanxxx.world.getBiome(_snowmanxxx.getBlockPos());
            if (_snowmanxxxx.getCategory() == Biome.Category.SWAMP) {
               _snowmanxx += 0.005F;
            }
         }

         RenderSystem.fogDensity(_snowmanxx);
         RenderSystem.fogMode(GlStateManager.FogMode.EXP2);
      } else {
         float _snowmanxx;
         float _snowmanxxx;
         if (_snowman.isIn(FluidTags.LAVA)) {
            if (_snowmanx instanceof LivingEntity && ((LivingEntity)_snowmanx).hasStatusEffect(StatusEffects.FIRE_RESISTANCE)) {
               _snowmanxx = 0.0F;
               _snowmanxxx = 3.0F;
            } else {
               _snowmanxx = 0.25F;
               _snowmanxxx = 1.0F;
            }
         } else if (_snowmanx instanceof LivingEntity && ((LivingEntity)_snowmanx).hasStatusEffect(StatusEffects.BLINDNESS)) {
            int _snowmanxxxx = ((LivingEntity)_snowmanx).getStatusEffect(StatusEffects.BLINDNESS).getDuration();
            float _snowmanxxxxx = MathHelper.lerp(Math.min(1.0F, (float)_snowmanxxxx / 20.0F), viewDistance, 5.0F);
            if (fogType == BackgroundRenderer.FogType.FOG_SKY) {
               _snowmanxx = 0.0F;
               _snowmanxxx = _snowmanxxxxx * 0.8F;
            } else {
               _snowmanxx = _snowmanxxxxx * 0.25F;
               _snowmanxxx = _snowmanxxxxx;
            }
         } else if (thickFog) {
            _snowmanxx = viewDistance * 0.05F;
            _snowmanxxx = Math.min(viewDistance, 192.0F) * 0.5F;
         } else if (fogType == BackgroundRenderer.FogType.FOG_SKY) {
            _snowmanxx = 0.0F;
            _snowmanxxx = viewDistance;
         } else {
            _snowmanxx = viewDistance * 0.75F;
            _snowmanxxx = viewDistance;
         }

         RenderSystem.fogStart(_snowmanxx);
         RenderSystem.fogEnd(_snowmanxxx);
         RenderSystem.fogMode(GlStateManager.FogMode.LINEAR);
         RenderSystem.setupNvFogDistance();
      }
   }

   public static void setFogBlack() {
      RenderSystem.fog(2918, red, green, blue, 1.0F);
   }

   public static enum FogType {
      FOG_SKY,
      FOG_TERRAIN;

      private FogType() {
      }
   }
}
