package net.minecraft.client.render.entity;

import java.util.Random;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

public class LightningEntityRenderer extends EntityRenderer<LightningEntity> {
   public LightningEntityRenderer(EntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(LightningEntity _snowman, float _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman) {
      float[] _snowmanxxxxxx = new float[8];
      float[] _snowmanxxxxxxx = new float[8];
      float _snowmanxxxxxxxx = 0.0F;
      float _snowmanxxxxxxxxx = 0.0F;
      Random _snowmanxxxxxxxxxx = new Random(_snowman.seed);

      for (int _snowmanxxxxxxxxxxx = 7; _snowmanxxxxxxxxxxx >= 0; _snowmanxxxxxxxxxxx--) {
         _snowmanxxxxxx[_snowmanxxxxxxxxxxx] = _snowmanxxxxxxxx;
         _snowmanxxxxxxx[_snowmanxxxxxxxxxxx] = _snowmanxxxxxxxxx;
         _snowmanxxxxxxxx += (float)(_snowmanxxxxxxxxxx.nextInt(11) - 5);
         _snowmanxxxxxxxxx += (float)(_snowmanxxxxxxxxxx.nextInt(11) - 5);
      }

      VertexConsumer _snowmanxxxxxxxxxxx = _snowman.getBuffer(RenderLayer.getLightning());
      Matrix4f _snowmanxxxxxxxxxxxx = _snowman.peek().getModel();

      for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxxx++) {
         Random _snowmanxxxxxxxxxxxxxx = new Random(_snowman.seed);

         for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < 3; _snowmanxxxxxxxxxxxxxxx++) {
            int _snowmanxxxxxxxxxxxxxxxx = 7;
            int _snowmanxxxxxxxxxxxxxxxxx = 0;
            if (_snowmanxxxxxxxxxxxxxxx > 0) {
               _snowmanxxxxxxxxxxxxxxxx = 7 - _snowmanxxxxxxxxxxxxxxx;
            }

            if (_snowmanxxxxxxxxxxxxxxx > 0) {
               _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx - 2;
            }

            float _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx[_snowmanxxxxxxxxxxxxxxxx] - _snowmanxxxxxxxx;
            float _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx[_snowmanxxxxxxxxxxxxxxxx] - _snowmanxxxxxxxxx;

            for (int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxx >= _snowmanxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxx--) {
               float _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx;
               float _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx;
               if (_snowmanxxxxxxxxxxxxxxx == 0) {
                  _snowmanxxxxxxxxxxxxxxxxxx += (float)(_snowmanxxxxxxxxxxxxxx.nextInt(11) - 5);
                  _snowmanxxxxxxxxxxxxxxxxxxx += (float)(_snowmanxxxxxxxxxxxxxx.nextInt(11) - 5);
               } else {
                  _snowmanxxxxxxxxxxxxxxxxxx += (float)(_snowmanxxxxxxxxxxxxxx.nextInt(31) - 15);
                  _snowmanxxxxxxxxxxxxxxxxxxx += (float)(_snowmanxxxxxxxxxxxxxx.nextInt(31) - 15);
               }

               float _snowmanxxxxxxxxxxxxxxxxxxxxxxx = 0.5F;
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0.45F;
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = 0.45F;
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.5F;
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.1F + (float)_snowmanxxxxxxxxxxxxx * 0.2F;
               if (_snowmanxxxxxxxxxxxxxxx == 0) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)((double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * ((double)_snowmanxxxxxxxxxxxxxxxxxxxx * 0.1 + 1.0));
               }

               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.1F + (float)_snowmanxxxxxxxxxxxxx * 0.2F;
               if (_snowmanxxxxxxxxxxxxxxx == 0) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx *= (float)(_snowmanxxxxxxxxxxxxxxxxxxxx - 1) * 0.1F + 1.0F;
               }

               method_23183(
                  _snowmanxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx,
                  0.45F,
                  0.45F,
                  0.5F,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  false,
                  false,
                  true,
                  false
               );
               method_23183(
                  _snowmanxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx,
                  0.45F,
                  0.45F,
                  0.5F,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  true,
                  false,
                  true,
                  true
               );
               method_23183(
                  _snowmanxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx,
                  0.45F,
                  0.45F,
                  0.5F,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  true,
                  true,
                  false,
                  true
               );
               method_23183(
                  _snowmanxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx,
                  0.45F,
                  0.45F,
                  0.5F,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                  false,
                  true,
                  false,
                  false
               );
            }
         }
      }
   }

   private static void method_23183(
      Matrix4f _snowman,
      VertexConsumer _snowman,
      float _snowman,
      float _snowman,
      int _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      boolean _snowman,
      boolean _snowman,
      boolean _snowman,
      boolean _snowman
   ) {
      _snowman.vertex(_snowman, _snowman + (_snowman ? _snowman : -_snowman), (float)(_snowman * 16), _snowman + (_snowman ? _snowman : -_snowman)).color(_snowman, _snowman, _snowman, 0.3F).next();
      _snowman.vertex(_snowman, _snowman + (_snowman ? _snowman : -_snowman), (float)((_snowman + 1) * 16), _snowman + (_snowman ? _snowman : -_snowman)).color(_snowman, _snowman, _snowman, 0.3F).next();
      _snowman.vertex(_snowman, _snowman + (_snowman ? _snowman : -_snowman), (float)((_snowman + 1) * 16), _snowman + (_snowman ? _snowman : -_snowman)).color(_snowman, _snowman, _snowman, 0.3F).next();
      _snowman.vertex(_snowman, _snowman + (_snowman ? _snowman : -_snowman), (float)(_snowman * 16), _snowman + (_snowman ? _snowman : -_snowman)).color(_snowman, _snowman, _snowman, 0.3F).next();
   }

   public Identifier getTexture(LightningEntity _snowman) {
      return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
   }
}
