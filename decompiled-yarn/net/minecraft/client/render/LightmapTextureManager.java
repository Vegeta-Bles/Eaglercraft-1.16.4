package net.minecraft.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

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

      for (int _snowman = 0; _snowman < 16; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
            this.image.setPixelColor(_snowmanx, _snowman, -1);
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
      float _snowman = 0.00390625F;
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
         ClientWorld _snowman = this.client.world;
         if (_snowman != null) {
            float _snowmanx = _snowman.method_23783(1.0F);
            float _snowmanxx;
            if (_snowman.getLightningTicksLeft() > 0) {
               _snowmanxx = 1.0F;
            } else {
               _snowmanxx = _snowmanx * 0.95F + 0.05F;
            }

            float _snowmanxxx = this.client.player.getUnderwaterVisibility();
            float _snowmanxxxx;
            if (this.client.player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
               _snowmanxxxx = GameRenderer.getNightVisionStrength(this.client.player, delta);
            } else if (_snowmanxxx > 0.0F && this.client.player.hasStatusEffect(StatusEffects.CONDUIT_POWER)) {
               _snowmanxxxx = _snowmanxxx;
            } else {
               _snowmanxxxx = 0.0F;
            }

            Vector3f _snowmanxxxxx = new Vector3f(_snowmanx, _snowmanx, 1.0F);
            _snowmanxxxxx.lerp(new Vector3f(1.0F, 1.0F, 1.0F), 0.35F);
            float _snowmanxxxxxx = this.field_21528 + 1.5F;
            Vector3f _snowmanxxxxxxx = new Vector3f();

            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < 16; _snowmanxxxxxxxx++) {
               for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < 16; _snowmanxxxxxxxxx++) {
                  float _snowmanxxxxxxxxxx = this.getBrightness(_snowman, _snowmanxxxxxxxx) * _snowmanxx;
                  float _snowmanxxxxxxxxxxx = this.getBrightness(_snowman, _snowmanxxxxxxxxx) * _snowmanxxxxxx;
                  float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx * ((_snowmanxxxxxxxxxxx * 0.6F + 0.4F) * 0.6F + 0.4F);
                  float _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx * (_snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx * 0.6F + 0.4F);
                  _snowmanxxxxxxx.set(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
                  if (_snowman.getSkyProperties().shouldBrightenLighting()) {
                     _snowmanxxxxxxx.lerp(new Vector3f(0.99F, 1.12F, 1.0F), 0.25F);
                  } else {
                     Vector3f _snowmanxxxxxxxxxxxxxx = _snowmanxxxxx.copy();
                     _snowmanxxxxxxxxxxxxxx.scale(_snowmanxxxxxxxxxx);
                     _snowmanxxxxxxx.add(_snowmanxxxxxxxxxxxxxx);
                     _snowmanxxxxxxx.lerp(new Vector3f(0.75F, 0.75F, 0.75F), 0.04F);
                     if (this.renderer.getSkyDarkness(delta) > 0.0F) {
                        float _snowmanxxxxxxxxxxxxxxx = this.renderer.getSkyDarkness(delta);
                        Vector3f _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxx.copy();
                        _snowmanxxxxxxxxxxxxxxxx.multiplyComponentwise(0.7F, 0.6F, 0.6F);
                        _snowmanxxxxxxx.lerp(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
                     }
                  }

                  _snowmanxxxxxxx.clamp(0.0F, 1.0F);
                  if (_snowmanxxxx > 0.0F) {
                     float _snowmanxxxxxxxxxxxxxxx = Math.max(_snowmanxxxxxxx.getX(), Math.max(_snowmanxxxxxxx.getY(), _snowmanxxxxxxx.getZ()));
                     if (_snowmanxxxxxxxxxxxxxxx < 1.0F) {
                        float _snowmanxxxxxxxxxxxxxxxx = 1.0F / _snowmanxxxxxxxxxxxxxxx;
                        Vector3f _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx.copy();
                        _snowmanxxxxxxxxxxxxxxxxx.scale(_snowmanxxxxxxxxxxxxxxxx);
                        _snowmanxxxxxxx.lerp(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxx);
                     }
                  }

                  float _snowmanxxxxxxxxxxxxxxx = (float)this.client.options.gamma;
                  Vector3f _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxx.copy();
                  _snowmanxxxxxxxxxxxxxxxx.modify(this::method_23795);
                  _snowmanxxxxxxx.lerp(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
                  _snowmanxxxxxxx.lerp(new Vector3f(0.75F, 0.75F, 0.75F), 0.04F);
                  _snowmanxxxxxxx.clamp(0.0F, 1.0F);
                  _snowmanxxxxxxx.scale(255.0F);
                  int _snowmanxxxxxxxxxxxxxxxxx = 255;
                  int _snowmanxxxxxxxxxxxxxxxxxx = (int)_snowmanxxxxxxx.getX();
                  int _snowmanxxxxxxxxxxxxxxxxxxx = (int)_snowmanxxxxxxx.getY();
                  int _snowmanxxxxxxxxxxxxxxxxxxxx = (int)_snowmanxxxxxxx.getZ();
                  this.image.setPixelColor(_snowmanxxxxxxxxx, _snowmanxxxxxxxx, 0xFF000000 | _snowmanxxxxxxxxxxxxxxxxxxxx << 16 | _snowmanxxxxxxxxxxxxxxxxxxx << 8 | _snowmanxxxxxxxxxxxxxxxxxx);
               }
            }

            this.texture.upload();
            this.client.getProfiler().pop();
         }
      }
   }

   private float method_23795(float _snowman) {
      float _snowmanx = 1.0F - _snowman;
      return 1.0F - _snowmanx * _snowmanx * _snowmanx * _snowmanx;
   }

   private float getBrightness(World world, int _snowman) {
      return world.getDimension().method_28516(_snowman);
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
