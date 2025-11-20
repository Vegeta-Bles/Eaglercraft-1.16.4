package net.minecraft.client.render.block.entity;

import java.util.List;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;

public class BeaconBlockEntityRenderer extends BlockEntityRenderer<BeaconBlockEntity> {
   public static final Identifier BEAM_TEXTURE = new Identifier("textures/entity/beacon_beam.png");

   public BeaconBlockEntityRenderer(BlockEntityRenderDispatcher _snowman) {
      super(_snowman);
   }

   public void render(BeaconBlockEntity _snowman, float _snowman, MatrixStack _snowman, VertexConsumerProvider _snowman, int _snowman, int _snowman) {
      long _snowmanxxxxxx = _snowman.getWorld().getTime();
      List<BeaconBlockEntity.BeamSegment> _snowmanxxxxxxx = _snowman.getBeamSegments();
      int _snowmanxxxxxxxx = 0;

      for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxxxx.size(); _snowmanxxxxxxxxx++) {
         BeaconBlockEntity.BeamSegment _snowmanxxxxxxxxxx = _snowmanxxxxxxx.get(_snowmanxxxxxxxxx);
         render(_snowman, _snowman, _snowman, _snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx == _snowmanxxxxxxx.size() - 1 ? 1024 : _snowmanxxxxxxxxxx.getHeight(), _snowmanxxxxxxxxxx.getColor());
         _snowmanxxxxxxxx += _snowmanxxxxxxxxxx.getHeight();
      }
   }

   private static void render(MatrixStack _snowman, VertexConsumerProvider _snowman, float _snowman, long _snowman, int _snowman, int _snowman, float[] _snowman) {
      renderLightBeam(_snowman, _snowman, BEAM_TEXTURE, _snowman, 1.0F, _snowman, _snowman, _snowman, _snowman, 0.2F, 0.25F);
   }

   public static void renderLightBeam(
      MatrixStack _snowman, VertexConsumerProvider _snowman, Identifier _snowman, float _snowman, float _snowman, long _snowman, int _snowman, int _snowman, float[] _snowman, float _snowman, float _snowman
   ) {
      int _snowmanxxxxxxxxxxx = _snowman + _snowman;
      _snowman.push();
      _snowman.translate(0.5, 0.0, 0.5);
      float _snowmanxxxxxxxxxxxx = (float)Math.floorMod(_snowman, 40L) + _snowman;
      float _snowmanxxxxxxxxxxxxx = _snowman < 0 ? _snowmanxxxxxxxxxxxx : -_snowmanxxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxx = MathHelper.fractionalPart(_snowmanxxxxxxxxxxxxx * 0.2F - (float)MathHelper.floor(_snowmanxxxxxxxxxxxxx * 0.1F));
      float _snowmanxxxxxxxxxxxxxxx = _snowman[0];
      float _snowmanxxxxxxxxxxxxxxxx = _snowman[1];
      float _snowmanxxxxxxxxxxxxxxxxx = _snowman[2];
      _snowman.push();
      _snowman.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(_snowmanxxxxxxxxxxxx * 2.25F - 45.0F));
      float _snowmanxxxxxxxxxxxxxxxxxx = 0.0F;
      float _snowmanxxxxxxxxxxxxxxxxxxx = 0.0F;
      float _snowmanxxxxxxxxxxxxxxxxxxxx = -_snowman;
      float _snowmanxxxxxxxxxxxxxxxxxxxxx = 0.0F;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0.0F;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxx = -_snowman;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0.0F;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = 1.0F;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = -1.0F + _snowmanxxxxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)_snowman * _snowman * (0.5F / _snowman) + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx;
      method_22741(
         _snowman,
         _snowman.getBuffer(RenderLayer.getBeaconBeam(_snowman, false)),
         _snowmanxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxx,
         1.0F,
         _snowman,
         _snowmanxxxxxxxxxxx,
         0.0F,
         _snowman,
         _snowman,
         0.0F,
         _snowmanxxxxxxxxxxxxxxxxxxxx,
         0.0F,
         0.0F,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxx,
         0.0F,
         1.0F,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx
      );
      _snowman.pop();
      _snowmanxxxxxxxxxxxxxxxxxx = -_snowman;
      float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = -_snowman;
      _snowmanxxxxxxxxxxxxxxxxxxx = -_snowman;
      _snowmanxxxxxxxxxxxxxxxxxxxx = -_snowman;
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0.0F;
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = 1.0F;
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = -1.0F + _snowmanxxxxxxxxxxxxxx;
      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)_snowman * _snowman + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx;
      method_22741(
         _snowman,
         _snowman.getBuffer(RenderLayer.getBeaconBeam(_snowman, true)),
         _snowmanxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxx,
         0.125F,
         _snowman,
         _snowmanxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         _snowman,
         _snowmanxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxx,
         _snowman,
         _snowman,
         _snowman,
         0.0F,
         1.0F,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx,
         _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx
      );
      _snowman.pop();
   }

   private static void method_22741(
      MatrixStack _snowman,
      VertexConsumer _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      int _snowman,
      int _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman
   ) {
      MatrixStack.Entry _snowmanxxxxxxxxxxxxxxxxxxxx = _snowman.peek();
      Matrix4f _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx.getModel();
      Matrix3f _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx.getNormal();
      method_22740(_snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      method_22740(_snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      method_22740(_snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      method_22740(_snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private static void method_22740(
      Matrix4f _snowman,
      Matrix3f _snowman,
      VertexConsumer _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      int _snowman,
      int _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman,
      float _snowman
   ) {
      method_23076(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      method_23076(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      method_23076(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      method_23076(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private static void method_23076(Matrix4f _snowman, Matrix3f _snowman, VertexConsumer _snowman, float _snowman, float _snowman, float _snowman, float _snowman, int _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      _snowman.vertex(_snowman, _snowman, (float)_snowman, _snowman).color(_snowman, _snowman, _snowman, _snowman).texture(_snowman, _snowman).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(_snowman, 0.0F, 1.0F, 0.0F).next();
   }

   public boolean rendersOutsideBoundingBox(BeaconBlockEntity _snowman) {
      return true;
   }
}
