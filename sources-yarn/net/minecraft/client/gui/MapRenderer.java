package net.minecraft.client.gui;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class MapRenderer implements AutoCloseable {
   private static final Identifier MAP_ICONS_TEXTURE = new Identifier("textures/map/map_icons.png");
   private static final RenderLayer field_21688 = RenderLayer.getText(MAP_ICONS_TEXTURE);
   private final TextureManager textureManager;
   private final Map<String, MapRenderer.MapTexture> mapTextures = Maps.newHashMap();

   public MapRenderer(TextureManager arg) {
      this.textureManager = arg;
   }

   public void updateTexture(MapState arg) {
      this.getMapTexture(arg).updateTexture();
   }

   public void draw(MatrixStack arg, VertexConsumerProvider arg2, MapState arg3, boolean bl, int i) {
      this.getMapTexture(arg3).draw(arg, arg2, bl, i);
   }

   private MapRenderer.MapTexture getMapTexture(MapState arg) {
      MapRenderer.MapTexture lv = this.mapTextures.get(arg.getId());
      if (lv == null) {
         lv = new MapRenderer.MapTexture(arg);
         this.mapTextures.put(arg.getId(), lv);
      }

      return lv;
   }

   @Nullable
   public MapRenderer.MapTexture getTexture(String string) {
      return this.mapTextures.get(string);
   }

   public void clearStateTextures() {
      for (MapRenderer.MapTexture lv : this.mapTextures.values()) {
         lv.close();
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

   @Environment(EnvType.CLIENT)
   class MapTexture implements AutoCloseable {
      private final MapState mapState;
      private final NativeImageBackedTexture texture;
      private final RenderLayer field_21689;

      private MapTexture(MapState arg2) {
         this.mapState = arg2;
         this.texture = new NativeImageBackedTexture(128, 128, true);
         Identifier lv = MapRenderer.this.textureManager.registerDynamicTexture("map/" + arg2.getId(), this.texture);
         this.field_21689 = RenderLayer.getText(lv);
      }

      private void updateTexture() {
         for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 128; j++) {
               int k = j + i * 128;
               int l = this.mapState.colors[k] & 255;
               if (l / 4 == 0) {
                  this.texture.getImage().setPixelColor(j, i, 0);
               } else {
                  this.texture.getImage().setPixelColor(j, i, MaterialColor.COLORS[l / 4].getRenderColor(l & 3));
               }
            }
         }

         this.texture.upload();
      }

      private void draw(MatrixStack arg, VertexConsumerProvider arg2, boolean bl, int i) {
         int j = 0;
         int k = 0;
         float f = 0.0F;
         Matrix4f lv = arg.peek().getModel();
         VertexConsumer lv2 = arg2.getBuffer(this.field_21689);
         lv2.vertex(lv, 0.0F, 128.0F, -0.01F).color(255, 255, 255, 255).texture(0.0F, 1.0F).light(i).next();
         lv2.vertex(lv, 128.0F, 128.0F, -0.01F).color(255, 255, 255, 255).texture(1.0F, 1.0F).light(i).next();
         lv2.vertex(lv, 128.0F, 0.0F, -0.01F).color(255, 255, 255, 255).texture(1.0F, 0.0F).light(i).next();
         lv2.vertex(lv, 0.0F, 0.0F, -0.01F).color(255, 255, 255, 255).texture(0.0F, 0.0F).light(i).next();
         int l = 0;

         for (MapIcon lv3 : this.mapState.icons.values()) {
            if (!bl || lv3.isAlwaysRendered()) {
               arg.push();
               arg.translate((double)(0.0F + (float)lv3.getX() / 2.0F + 64.0F), (double)(0.0F + (float)lv3.getZ() / 2.0F + 64.0F), -0.02F);
               arg.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion((float)(lv3.getRotation() * 360) / 16.0F));
               arg.scale(4.0F, 4.0F, 3.0F);
               arg.translate(-0.125, 0.125, 0.0);
               byte b = lv3.getTypeId();
               float g = (float)(b % 16 + 0) / 16.0F;
               float h = (float)(b / 16 + 0) / 16.0F;
               float m = (float)(b % 16 + 1) / 16.0F;
               float n = (float)(b / 16 + 1) / 16.0F;
               Matrix4f lv4 = arg.peek().getModel();
               float o = -0.001F;
               VertexConsumer lv5 = arg2.getBuffer(MapRenderer.field_21688);
               lv5.vertex(lv4, -1.0F, 1.0F, (float)l * -0.001F).color(255, 255, 255, 255).texture(g, h).light(i).next();
               lv5.vertex(lv4, 1.0F, 1.0F, (float)l * -0.001F).color(255, 255, 255, 255).texture(m, h).light(i).next();
               lv5.vertex(lv4, 1.0F, -1.0F, (float)l * -0.001F).color(255, 255, 255, 255).texture(m, n).light(i).next();
               lv5.vertex(lv4, -1.0F, -1.0F, (float)l * -0.001F).color(255, 255, 255, 255).texture(g, n).light(i).next();
               arg.pop();
               if (lv3.getText() != null) {
                  TextRenderer lv6 = MinecraftClient.getInstance().textRenderer;
                  Text lv7 = lv3.getText();
                  float p = (float)lv6.getWidth(lv7);
                  float q = MathHelper.clamp(25.0F / p, 0.0F, 6.0F / 9.0F);
                  arg.push();
                  arg.translate(
                     (double)(0.0F + (float)lv3.getX() / 2.0F + 64.0F - p * q / 2.0F), (double)(0.0F + (float)lv3.getZ() / 2.0F + 64.0F + 4.0F), -0.025F
                  );
                  arg.scale(q, q, 1.0F);
                  arg.translate(0.0, 0.0, -0.1F);
                  lv6.draw(lv7, 0.0F, 0.0F, -1, false, arg.peek().getModel(), arg2, false, Integer.MIN_VALUE, i);
                  arg.pop();
               }

               l++;
            }
         }
      }

      @Override
      public void close() {
         this.texture.close();
      }
   }
}
