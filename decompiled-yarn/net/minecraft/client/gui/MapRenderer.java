package net.minecraft.client.gui;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.block.MaterialColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;

public class MapRenderer implements AutoCloseable {
   private static final Identifier MAP_ICONS_TEXTURE = new Identifier("textures/map/map_icons.png");
   private static final RenderLayer field_21688 = RenderLayer.getText(MAP_ICONS_TEXTURE);
   private final TextureManager textureManager;
   private final Map<String, MapRenderer.MapTexture> mapTextures = Maps.newHashMap();

   public MapRenderer(TextureManager _snowman) {
      this.textureManager = _snowman;
   }

   public void updateTexture(MapState _snowman) {
      this.getMapTexture(_snowman).updateTexture();
   }

   public void draw(MatrixStack _snowman, VertexConsumerProvider _snowman, MapState _snowman, boolean _snowman, int _snowman) {
      this.getMapTexture(_snowman).draw(_snowman, _snowman, _snowman, _snowman);
   }

   private MapRenderer.MapTexture getMapTexture(MapState _snowman) {
      MapRenderer.MapTexture _snowmanx = this.mapTextures.get(_snowman.getId());
      if (_snowmanx == null) {
         _snowmanx = new MapRenderer.MapTexture(_snowman);
         this.mapTextures.put(_snowman.getId(), _snowmanx);
      }

      return _snowmanx;
   }

   @Nullable
   public MapRenderer.MapTexture getTexture(String _snowman) {
      return this.mapTextures.get(_snowman);
   }

   public void clearStateTextures() {
      for (MapRenderer.MapTexture _snowman : this.mapTextures.values()) {
         _snowman.close();
      }

      this.mapTextures.clear();
   }

   @Nullable
   public MapState getState(@Nullable MapRenderer.MapTexture texture) {
      return texture != null ? texture.mapState : null;
   }

   @Override
   public void close() {
      this.clearStateTextures();
   }

   class MapTexture implements AutoCloseable {
      private final MapState mapState;
      private final NativeImageBackedTexture texture;
      private final RenderLayer field_21689;

      private MapTexture(MapState var2) {
         this.mapState = _snowman;
         this.texture = new NativeImageBackedTexture(128, 128, true);
         Identifier _snowman = MapRenderer.this.textureManager.registerDynamicTexture("map/" + _snowman.getId(), this.texture);
         this.field_21689 = RenderLayer.getText(_snowman);
      }

      private void updateTexture() {
         for (int _snowman = 0; _snowman < 128; _snowman++) {
            for (int _snowmanx = 0; _snowmanx < 128; _snowmanx++) {
               int _snowmanxx = _snowmanx + _snowman * 128;
               int _snowmanxxx = this.mapState.colors[_snowmanxx] & 255;
               if (_snowmanxxx / 4 == 0) {
                  this.texture.getImage().setPixelColor(_snowmanx, _snowman, 0);
               } else {
                  this.texture.getImage().setPixelColor(_snowmanx, _snowman, MaterialColor.COLORS[_snowmanxxx / 4].getRenderColor(_snowmanxxx & 3));
               }
            }
         }

         this.texture.upload();
      }

      private void draw(MatrixStack _snowman, VertexConsumerProvider _snowman, boolean _snowman, int _snowman) {
         int _snowmanxxxx = 0;
         int _snowmanxxxxx = 0;
         float _snowmanxxxxxx = 0.0F;
         Matrix4f _snowmanxxxxxxx = _snowman.peek().getModel();
         VertexConsumer _snowmanxxxxxxxx = _snowman.getBuffer(this.field_21689);
         _snowmanxxxxxxxx.vertex(_snowmanxxxxxxx, 0.0F, 128.0F, -0.01F).color(255, 255, 255, 255).texture(0.0F, 1.0F).light(_snowman).next();
         _snowmanxxxxxxxx.vertex(_snowmanxxxxxxx, 128.0F, 128.0F, -0.01F).color(255, 255, 255, 255).texture(1.0F, 1.0F).light(_snowman).next();
         _snowmanxxxxxxxx.vertex(_snowmanxxxxxxx, 128.0F, 0.0F, -0.01F).color(255, 255, 255, 255).texture(1.0F, 0.0F).light(_snowman).next();
         _snowmanxxxxxxxx.vertex(_snowmanxxxxxxx, 0.0F, 0.0F, -0.01F).color(255, 255, 255, 255).texture(0.0F, 0.0F).light(_snowman).next();
         int _snowmanxxxxxxxxx = 0;

         for (MapIcon _snowmanxxxxxxxxxx : this.mapState.icons.values()) {
            if (!_snowman || _snowmanxxxxxxxxxx.isAlwaysRendered()) {
               _snowman.push();
               _snowman.translate((double)(0.0F + (float)_snowmanxxxxxxxxxx.getX() / 2.0F + 64.0F), (double)(0.0F + (float)_snowmanxxxxxxxxxx.getZ() / 2.0F + 64.0F), -0.02F);
               _snowman.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)(_snowmanxxxxxxxxxx.getRotation() * 360) / 16.0F));
               _snowman.scale(4.0F, 4.0F, 3.0F);
               _snowman.translate(-0.125, 0.125, 0.0);
               byte _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.getTypeId();
               float _snowmanxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxx % 16 + 0) / 16.0F;
               float _snowmanxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxx / 16 + 0) / 16.0F;
               float _snowmanxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxx % 16 + 1) / 16.0F;
               float _snowmanxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxx / 16 + 1) / 16.0F;
               Matrix4f _snowmanxxxxxxxxxxxxxxxx = _snowman.peek().getModel();
               float _snowmanxxxxxxxxxxxxxxxxx = -0.001F;
               VertexConsumer _snowmanxxxxxxxxxxxxxxxxxx = _snowman.getBuffer(MapRenderer.field_21688);
               _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxxxxxxxx, -1.0F, 1.0F, (float)_snowmanxxxxxxxxx * -0.001F)
                  .color(255, 255, 255, 255)
                  .texture(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx)
                  .light(_snowman)
                  .next();
               _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxxxxxxxx, 1.0F, 1.0F, (float)_snowmanxxxxxxxxx * -0.001F)
                  .color(255, 255, 255, 255)
                  .texture(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx)
                  .light(_snowman)
                  .next();
               _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxxxxxxxx, 1.0F, -1.0F, (float)_snowmanxxxxxxxxx * -0.001F)
                  .color(255, 255, 255, 255)
                  .texture(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)
                  .light(_snowman)
                  .next();
               _snowmanxxxxxxxxxxxxxxxxxx.vertex(_snowmanxxxxxxxxxxxxxxxx, -1.0F, -1.0F, (float)_snowmanxxxxxxxxx * -0.001F)
                  .color(255, 255, 255, 255)
                  .texture(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)
                  .light(_snowman)
                  .next();
               _snowman.pop();
               if (_snowmanxxxxxxxxxx.getText() != null) {
                  TextRenderer _snowmanxxxxxxxxxxxxxxxxxxx = MinecraftClient.getInstance().textRenderer;
                  Text _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx.getText();
                  float _snowmanxxxxxxxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxxxxxxxxx.getWidth(_snowmanxxxxxxxxxxxxxxxxxxxx);
                  float _snowmanxxxxxxxxxxxxxxxxxxxxxx = MathHelper.clamp(25.0F / _snowmanxxxxxxxxxxxxxxxxxxxxx, 0.0F, 6.0F / 9.0F);
                  _snowman.push();
                  _snowman.translate(
                     (double)(0.0F + (float)_snowmanxxxxxxxxxx.getX() / 2.0F + 64.0F - _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxx / 2.0F),
                     (double)(0.0F + (float)_snowmanxxxxxxxxxx.getZ() / 2.0F + 64.0F + 4.0F),
                     -0.025F
                  );
                  _snowman.scale(_snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, 1.0F);
                  _snowman.translate(0.0, 0.0, -0.1F);
                  _snowmanxxxxxxxxxxxxxxxxxxx.draw(_snowmanxxxxxxxxxxxxxxxxxxxx, 0.0F, 0.0F, -1, false, _snowman.peek().getModel(), _snowman, false, Integer.MIN_VALUE, _snowman);
                  _snowman.pop();
               }

               _snowmanxxxxxxxxx++;
            }
         }
      }

      @Override
      public void close() {
         this.texture.close();
      }
   }
}
