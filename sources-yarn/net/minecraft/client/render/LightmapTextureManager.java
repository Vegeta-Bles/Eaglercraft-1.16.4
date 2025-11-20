package net.minecraft.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class LightmapTextureManager implements AutoCloseable {
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

      for (int i = 0; i < 16; i++) {
         for (int j = 0; j < 16; j++) {
            this.image.setPixelColor(j, i, -1);
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
      float f = 0.00390625F;
      RenderSystem.scalef(0.00390625F, 0.00390625F, 0.00390625F);
      RenderSystem.translatef(8.0F, 8.0F, 8.0F);
      RenderSystem.matrixMode(5888);
      this.client.getTextureManager().bindTexture(this.textureIdentifier);
      RenderSystem.texParameter(3553, 10241, 9729);
      RenderSystem.texParameter(3553, 10240, 9729);
      RenderSystem.texParameter(3553, 10242, 10496);
      RenderSystem.texParameter(3553, 10243, 10496);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableTexture();
      RenderSystem.activeTexture(33984);
   }

   public void update(float delta) {
      if (this.dirty) {
         this.dirty = false;
         this.client.getProfiler().push("lightTex");
         ClientWorld lv = this.client.world;
         if (lv != null) {
            float g = lv.method_23783(1.0F);
            float h;
            if (lv.getLightningTicksLeft() > 0) {
               h = 1.0F;
            } else {
               h = g * 0.95F + 0.05F;
            }

            float j = this.client.player.getUnderwaterVisibility();
            float k;
            if (this.client.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
               k = GameRenderer.getNightVisionStrength(this.client.player, delta);
            } else if (j > 0.0F && this.client.player.hasStatusEffect(StatusEffects.CONDUIT_POWER)) {
               k = j;
            } else {
               k = 0.0F;
            }

            Vector3f lv2 = new Vector3f(g, g, 1.0F);
            lv2.lerp(new Vector3f(1.0F, 1.0F, 1.0F), 0.35F);
            float n = this.field_21528 + 1.5F;
            Vector3f lv3 = new Vector3f();

            for (int o = 0; o < 16; o++) {
               for (int p = 0; p < 16; p++) {
                  float q = this.getBrightness(lv, o) * h;
                  float r = this.getBrightness(lv, p) * n;
                  float t = r * ((r * 0.6F + 0.4F) * 0.6F + 0.4F);
                  float u = r * (r * r * 0.6F + 0.4F);
                  lv3.set(r, t, u);
                  if (lv.getSkyProperties().shouldBrightenLighting()) {
                     lv3.lerp(new Vector3f(0.99F, 1.12F, 1.0F), 0.25F);
                  } else {
                     Vector3f lv4 = lv2.copy();
                     lv4.scale(q);
                     lv3.add(lv4);
                     lv3.lerp(new Vector3f(0.75F, 0.75F, 0.75F), 0.04F);
                     if (this.renderer.getSkyDarkness(delta) > 0.0F) {
                        float v = this.renderer.getSkyDarkness(delta);
                        Vector3f lv5 = lv3.copy();
                        lv5.multiplyComponentwise(0.7F, 0.6F, 0.6F);
                        lv3.lerp(lv5, v);
                     }
                  }

                  lv3.clamp(0.0F, 1.0F);
                  if (k > 0.0F) {
                     float w = Math.max(lv3.getX(), Math.max(lv3.getY(), lv3.getZ()));
                     if (w < 1.0F) {
                        float x = 1.0F / w;
                        Vector3f lv6 = lv3.copy();
                        lv6.scale(x);
                        lv3.lerp(lv6, k);
                     }
                  }

                  float y = (float)this.client.options.gamma;
                  Vector3f lv7 = lv3.copy();
                  lv7.modify(this::method_23795);
                  lv3.lerp(lv7, y);
                  lv3.lerp(new Vector3f(0.75F, 0.75F, 0.75F), 0.04F);
                  lv3.clamp(0.0F, 1.0F);
                  lv3.scale(255.0F);
                  int z = 255;
                  int aa = (int)lv3.getX();
                  int ab = (int)lv3.getY();
                  int ac = (int)lv3.getZ();
                  this.image.setPixelColor(p, o, 0xFF000000 | ac << 16 | ab << 8 | aa);
               }
            }

            this.texture.upload();
            this.client.getProfiler().pop();
         }
      }
   }

   private float method_23795(float f) {
      float g = 1.0F - f;
      return 1.0F - g * g * g * g;
   }

   private float getBrightness(World world, int i) {
      return world.getDimension().method_28516(i);
   }

   public static int pack(int block, int sky) {
      return block << 4 | sky << 20;
   }

   public static int getBlockLightCoordinates(int light) {
      return light >> 4 & 65535;
   }

   public static int getSkyLightCoordinates(int light) {
      return light >> 20 & 65535;
   }
}
