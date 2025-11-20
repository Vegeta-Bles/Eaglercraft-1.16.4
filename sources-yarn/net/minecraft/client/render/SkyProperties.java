package net.minecraft.client.render;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

@Environment(EnvType.CLIENT)
public abstract class SkyProperties {
   private static final Object2ObjectMap<Identifier, SkyProperties> BY_IDENTIFIER = Util.make(new Object2ObjectArrayMap(), object2ObjectArrayMap -> {
      SkyProperties.Overworld lv = new SkyProperties.Overworld();
      object2ObjectArrayMap.defaultReturnValue(lv);
      object2ObjectArrayMap.put(DimensionType.OVERWORLD_ID, lv);
      object2ObjectArrayMap.put(DimensionType.THE_NETHER_ID, new SkyProperties.Nether());
      object2ObjectArrayMap.put(DimensionType.THE_END_ID, new SkyProperties.End());
   });
   private final float[] rgba = new float[4];
   private final float cloudsHeight;
   private final boolean alternateSkyColor;
   private final SkyProperties.SkyType skyType;
   private final boolean brightenLighting;
   private final boolean darkened;

   public SkyProperties(float cloudsHeight, boolean alternateSkyColor, SkyProperties.SkyType skyType, boolean brightenLighting, boolean darkened) {
      this.cloudsHeight = cloudsHeight;
      this.alternateSkyColor = alternateSkyColor;
      this.skyType = skyType;
      this.brightenLighting = brightenLighting;
      this.darkened = darkened;
   }

   public static SkyProperties byDimensionType(DimensionType arg) {
      return (SkyProperties)BY_IDENTIFIER.get(arg.getSkyProperties());
   }

   @Nullable
   public float[] getFogColorOverride(float skyAngle, float tickDelta) {
      float h = 0.4F;
      float i = MathHelper.cos(skyAngle * (float) (Math.PI * 2)) - 0.0F;
      float j = -0.0F;
      if (i >= -0.4F && i <= 0.4F) {
         float k = (i - -0.0F) / 0.4F * 0.5F + 0.5F;
         float l = 1.0F - (1.0F - MathHelper.sin(k * (float) Math.PI)) * 0.99F;
         l *= l;
         this.rgba[0] = k * 0.3F + 0.7F;
         this.rgba[1] = k * k * 0.7F + 0.2F;
         this.rgba[2] = k * k * 0.0F + 0.2F;
         this.rgba[3] = l;
         return this.rgba;
      } else {
         return null;
      }
   }

   public float getCloudsHeight() {
      return this.cloudsHeight;
   }

   public boolean isAlternateSkyColor() {
      return this.alternateSkyColor;
   }

   public abstract Vec3d adjustFogColor(Vec3d color, float sunHeight);

   public abstract boolean useThickFog(int camX, int camY);

   public SkyProperties.SkyType getSkyType() {
      return this.skyType;
   }

   public boolean shouldBrightenLighting() {
      return this.brightenLighting;
   }

   public boolean isDarkened() {
      return this.darkened;
   }

   @Environment(EnvType.CLIENT)
   public static class End extends SkyProperties {
      public End() {
         super(Float.NaN, false, SkyProperties.SkyType.END, true, false);
      }

      @Override
      public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
         return color.multiply(0.15F);
      }

      @Override
      public boolean useThickFog(int camX, int camY) {
         return false;
      }

      @Nullable
      @Override
      public float[] getFogColorOverride(float skyAngle, float tickDelta) {
         return null;
      }
   }

   @Environment(EnvType.CLIENT)
   public static class Nether extends SkyProperties {
      public Nether() {
         super(Float.NaN, true, SkyProperties.SkyType.NONE, false, true);
      }

      @Override
      public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
         return color;
      }

      @Override
      public boolean useThickFog(int camX, int camY) {
         return true;
      }
   }

   @Environment(EnvType.CLIENT)
   public static class Overworld extends SkyProperties {
      public Overworld() {
         super(128.0F, true, SkyProperties.SkyType.NORMAL, false, false);
      }

      @Override
      public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
         return color.multiply((double)(sunHeight * 0.94F + 0.06F), (double)(sunHeight * 0.94F + 0.06F), (double)(sunHeight * 0.91F + 0.09F));
      }

      @Override
      public boolean useThickFog(int camX, int camY) {
         return false;
      }
   }

   @Environment(EnvType.CLIENT)
   public static enum SkyType {
      NONE,
      NORMAL,
      END;

      private SkyType() {
      }
   }
}
