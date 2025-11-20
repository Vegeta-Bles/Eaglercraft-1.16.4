package net.minecraft.client.render.model;

import javax.annotation.Nullable;
import net.minecraft.client.render.model.json.ModelElementFace;
import net.minecraft.client.render.model.json.ModelElementTexture;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.AffineTransformation;
import net.minecraft.client.util.math.AffineTransformations;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3i;

public class BakedQuadFactory {
   private static final float MIN_SCALE = 1.0F / (float)Math.cos((float) (Math.PI / 8)) - 1.0F;
   private static final float MAX_SCALE = 1.0F / (float)Math.cos((float) (Math.PI / 4)) - 1.0F;

   public BakedQuadFactory() {
   }

   public BakedQuad bake(
      Vector3f from,
      Vector3f to,
      ModelElementFace face,
      Sprite texture,
      Direction side,
      ModelBakeSettings settings,
      @Nullable net.minecraft.client.render.model.json.ModelRotation rotation,
      boolean shade,
      Identifier modelId
   ) {
      ModelElementTexture _snowman = face.textureData;
      if (settings.isShaded()) {
         _snowman = uvLock(face.textureData, side, settings.getRotation(), modelId);
      }

      float[] _snowmanx = new float[_snowman.uvs.length];
      System.arraycopy(_snowman.uvs, 0, _snowmanx, 0, _snowmanx.length);
      float _snowmanxx = texture.getAnimationFrameDelta();
      float _snowmanxxx = (_snowman.uvs[0] + _snowman.uvs[0] + _snowman.uvs[2] + _snowman.uvs[2]) / 4.0F;
      float _snowmanxxxx = (_snowman.uvs[1] + _snowman.uvs[1] + _snowman.uvs[3] + _snowman.uvs[3]) / 4.0F;
      _snowman.uvs[0] = MathHelper.lerp(_snowmanxx, _snowman.uvs[0], _snowmanxxx);
      _snowman.uvs[2] = MathHelper.lerp(_snowmanxx, _snowman.uvs[2], _snowmanxxx);
      _snowman.uvs[1] = MathHelper.lerp(_snowmanxx, _snowman.uvs[1], _snowmanxxxx);
      _snowman.uvs[3] = MathHelper.lerp(_snowmanxx, _snowman.uvs[3], _snowmanxxxx);
      int[] _snowmanxxxxx = this.packVertexData(_snowman, texture, side, this.getPositionMatrix(from, to), settings.getRotation(), rotation, shade);
      Direction _snowmanxxxxxx = decodeDirection(_snowmanxxxxx);
      System.arraycopy(_snowmanx, 0, _snowman.uvs, 0, _snowmanx.length);
      if (rotation == null) {
         this.encodeDirection(_snowmanxxxxx, _snowmanxxxxxx);
      }

      return new BakedQuad(_snowmanxxxxx, face.tintIndex, _snowmanxxxxxx, texture, shade);
   }

   public static ModelElementTexture uvLock(ModelElementTexture texture, Direction orientation, AffineTransformation rotation, Identifier modelId) {
      Matrix4f _snowman = AffineTransformations.uvLock(rotation, orientation, () -> "Unable to resolve UVLock for model: " + modelId).getMatrix();
      float _snowmanx = texture.getU(texture.getDirectionIndex(0));
      float _snowmanxx = texture.getV(texture.getDirectionIndex(0));
      Vector4f _snowmanxxx = new Vector4f(_snowmanx / 16.0F, _snowmanxx / 16.0F, 0.0F, 1.0F);
      _snowmanxxx.transform(_snowman);
      float _snowmanxxxx = 16.0F * _snowmanxxx.getX();
      float _snowmanxxxxx = 16.0F * _snowmanxxx.getY();
      float _snowmanxxxxxx = texture.getU(texture.getDirectionIndex(2));
      float _snowmanxxxxxxx = texture.getV(texture.getDirectionIndex(2));
      Vector4f _snowmanxxxxxxxx = new Vector4f(_snowmanxxxxxx / 16.0F, _snowmanxxxxxxx / 16.0F, 0.0F, 1.0F);
      _snowmanxxxxxxxx.transform(_snowman);
      float _snowmanxxxxxxxxx = 16.0F * _snowmanxxxxxxxx.getX();
      float _snowmanxxxxxxxxxx = 16.0F * _snowmanxxxxxxxx.getY();
      float _snowmanxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxx;
      if (Math.signum(_snowmanxxxxxx - _snowmanx) == Math.signum(_snowmanxxxxxxxxx - _snowmanxxxx)) {
         _snowmanxxxxxxxxxxx = _snowmanxxxx;
         _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx;
      } else {
         _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx;
         _snowmanxxxxxxxxxxxx = _snowmanxxxx;
      }

      float _snowmanxxxxxxxxxxxxx;
      float _snowmanxxxxxxxxxxxxxx;
      if (Math.signum(_snowmanxxxxxxx - _snowmanxx) == Math.signum(_snowmanxxxxxxxxxx - _snowmanxxxxx)) {
         _snowmanxxxxxxxxxxxxx = _snowmanxxxxx;
         _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx;
      } else {
         _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx;
         _snowmanxxxxxxxxxxxxxx = _snowmanxxxxx;
      }

      float _snowmanxxxxxxxxxxxxxxx = (float)Math.toRadians((double)texture.rotation);
      Vector3f _snowmanxxxxxxxxxxxxxxxx = new Vector3f(MathHelper.cos(_snowmanxxxxxxxxxxxxxxx), MathHelper.sin(_snowmanxxxxxxxxxxxxxxx), 0.0F);
      Matrix3f _snowmanxxxxxxxxxxxxxxxxx = new Matrix3f(_snowman);
      _snowmanxxxxxxxxxxxxxxxx.transform(_snowmanxxxxxxxxxxxxxxxxx);
      int _snowmanxxxxxxxxxxxxxxxxxx = Math.floorMod(
         -((int)Math.round(Math.toDegrees(Math.atan2((double)_snowmanxxxxxxxxxxxxxxxx.getY(), (double)_snowmanxxxxxxxxxxxxxxxx.getX())) / 90.0)) * 90, 360
      );
      return new ModelElementTexture(new float[]{_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx}, _snowmanxxxxxxxxxxxxxxxxxx);
   }

   private int[] packVertexData(
      ModelElementTexture texture,
      Sprite sprite,
      Direction direction,
      float[] positionMatrix,
      AffineTransformation orientation,
      @Nullable net.minecraft.client.render.model.json.ModelRotation rotation,
      boolean shaded
   ) {
      int[] _snowman = new int[32];

      for (int _snowmanx = 0; _snowmanx < 4; _snowmanx++) {
         this.packVertexData(_snowman, _snowmanx, direction, texture, positionMatrix, sprite, orientation, rotation, shaded);
      }

      return _snowman;
   }

   private float[] getPositionMatrix(Vector3f from, Vector3f to) {
      float[] _snowman = new float[Direction.values().length];
      _snowman[CubeFace.DirectionIds.WEST] = from.getX() / 16.0F;
      _snowman[CubeFace.DirectionIds.DOWN] = from.getY() / 16.0F;
      _snowman[CubeFace.DirectionIds.NORTH] = from.getZ() / 16.0F;
      _snowman[CubeFace.DirectionIds.EAST] = to.getX() / 16.0F;
      _snowman[CubeFace.DirectionIds.UP] = to.getY() / 16.0F;
      _snowman[CubeFace.DirectionIds.SOUTH] = to.getZ() / 16.0F;
      return _snowman;
   }

   private void packVertexData(
      int[] vertices,
      int cornerIndex,
      Direction direction,
      ModelElementTexture texture,
      float[] positionMatrix,
      Sprite sprite,
      AffineTransformation orientation,
      @Nullable net.minecraft.client.render.model.json.ModelRotation rotation,
      boolean shaded
   ) {
      CubeFace.Corner _snowman = CubeFace.getFace(direction).getCorner(cornerIndex);
      Vector3f _snowmanx = new Vector3f(positionMatrix[_snowman.xSide], positionMatrix[_snowman.ySide], positionMatrix[_snowman.zSide]);
      this.rotateVertex(_snowmanx, rotation);
      this.transformVertex(_snowmanx, orientation);
      this.packVertexData(vertices, cornerIndex, _snowmanx, sprite, texture);
   }

   private void packVertexData(int[] vertices, int cornerIndex, Vector3f position, Sprite _snowman, ModelElementTexture _snowman) {
      int _snowmanxx = cornerIndex * 8;
      vertices[_snowmanxx] = Float.floatToRawIntBits(position.getX());
      vertices[_snowmanxx + 1] = Float.floatToRawIntBits(position.getY());
      vertices[_snowmanxx + 2] = Float.floatToRawIntBits(position.getZ());
      vertices[_snowmanxx + 3] = -1;
      vertices[_snowmanxx + 4] = Float.floatToRawIntBits(_snowman.getFrameU((double)_snowman.getU(cornerIndex)));
      vertices[_snowmanxx + 4 + 1] = Float.floatToRawIntBits(_snowman.getFrameV((double)_snowman.getV(cornerIndex)));
   }

   private void rotateVertex(Vector3f vector, @Nullable net.minecraft.client.render.model.json.ModelRotation rotation) {
      if (rotation != null) {
         Vector3f _snowman;
         Vector3f _snowmanx;
         switch (rotation.axis) {
            case X:
               _snowman = new Vector3f(1.0F, 0.0F, 0.0F);
               _snowmanx = new Vector3f(0.0F, 1.0F, 1.0F);
               break;
            case Y:
               _snowman = new Vector3f(0.0F, 1.0F, 0.0F);
               _snowmanx = new Vector3f(1.0F, 0.0F, 1.0F);
               break;
            case Z:
               _snowman = new Vector3f(0.0F, 0.0F, 1.0F);
               _snowmanx = new Vector3f(1.0F, 1.0F, 0.0F);
               break;
            default:
               throw new IllegalArgumentException("There are only 3 axes");
         }

         Quaternion _snowman = new Quaternion(_snowman, rotation.angle, true);
         if (rotation.rescale) {
            if (Math.abs(rotation.angle) == 22.5F) {
               _snowmanx.scale(MIN_SCALE);
            } else {
               _snowmanx.scale(MAX_SCALE);
            }

            _snowmanx.add(1.0F, 1.0F, 1.0F);
         } else {
            _snowmanx.set(1.0F, 1.0F, 1.0F);
         }

         this.transformVertex(vector, rotation.origin.copy(), new Matrix4f(_snowman), _snowmanx);
      }
   }

   public void transformVertex(Vector3f vertex, AffineTransformation transformation) {
      if (transformation != AffineTransformation.identity()) {
         this.transformVertex(vertex, new Vector3f(0.5F, 0.5F, 0.5F), transformation.getMatrix(), new Vector3f(1.0F, 1.0F, 1.0F));
      }
   }

   private void transformVertex(Vector3f vertex, Vector3f origin, Matrix4f transformationMatrix, Vector3f scale) {
      Vector4f _snowman = new Vector4f(vertex.getX() - origin.getX(), vertex.getY() - origin.getY(), vertex.getZ() - origin.getZ(), 1.0F);
      _snowman.transform(transformationMatrix);
      _snowman.multiplyComponentwise(scale);
      vertex.set(_snowman.getX() + origin.getX(), _snowman.getY() + origin.getY(), _snowman.getZ() + origin.getZ());
   }

   public static Direction decodeDirection(int[] rotationMatrix) {
      Vector3f _snowman = new Vector3f(Float.intBitsToFloat(rotationMatrix[0]), Float.intBitsToFloat(rotationMatrix[1]), Float.intBitsToFloat(rotationMatrix[2]));
      Vector3f _snowmanx = new Vector3f(Float.intBitsToFloat(rotationMatrix[8]), Float.intBitsToFloat(rotationMatrix[9]), Float.intBitsToFloat(rotationMatrix[10]));
      Vector3f _snowmanxx = new Vector3f(Float.intBitsToFloat(rotationMatrix[16]), Float.intBitsToFloat(rotationMatrix[17]), Float.intBitsToFloat(rotationMatrix[18]));
      Vector3f _snowmanxxx = _snowman.copy();
      _snowmanxxx.subtract(_snowmanx);
      Vector3f _snowmanxxxx = _snowmanxx.copy();
      _snowmanxxxx.subtract(_snowmanx);
      Vector3f _snowmanxxxxx = _snowmanxxxx.copy();
      _snowmanxxxxx.cross(_snowmanxxx);
      _snowmanxxxxx.normalize();
      Direction _snowmanxxxxxx = null;
      float _snowmanxxxxxxx = 0.0F;

      for (Direction _snowmanxxxxxxxx : Direction.values()) {
         Vec3i _snowmanxxxxxxxxx = _snowmanxxxxxxxx.getVector();
         Vector3f _snowmanxxxxxxxxxx = new Vector3f((float)_snowmanxxxxxxxxx.getX(), (float)_snowmanxxxxxxxxx.getY(), (float)_snowmanxxxxxxxxx.getZ());
         float _snowmanxxxxxxxxxxx = _snowmanxxxxx.dot(_snowmanxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxx >= 0.0F && _snowmanxxxxxxxxxxx > _snowmanxxxxxxx) {
            _snowmanxxxxxxx = _snowmanxxxxxxxxxxx;
            _snowmanxxxxxx = _snowmanxxxxxxxx;
         }
      }

      return _snowmanxxxxxx == null ? Direction.UP : _snowmanxxxxxx;
   }

   private void encodeDirection(int[] rotationMatrix, Direction direction) {
      int[] _snowman = new int[rotationMatrix.length];
      System.arraycopy(rotationMatrix, 0, _snowman, 0, rotationMatrix.length);
      float[] _snowmanx = new float[Direction.values().length];
      _snowmanx[CubeFace.DirectionIds.WEST] = 999.0F;
      _snowmanx[CubeFace.DirectionIds.DOWN] = 999.0F;
      _snowmanx[CubeFace.DirectionIds.NORTH] = 999.0F;
      _snowmanx[CubeFace.DirectionIds.EAST] = -999.0F;
      _snowmanx[CubeFace.DirectionIds.UP] = -999.0F;
      _snowmanx[CubeFace.DirectionIds.SOUTH] = -999.0F;

      for (int _snowmanxx = 0; _snowmanxx < 4; _snowmanxx++) {
         int _snowmanxxx = 8 * _snowmanxx;
         float _snowmanxxxx = Float.intBitsToFloat(_snowman[_snowmanxxx]);
         float _snowmanxxxxx = Float.intBitsToFloat(_snowman[_snowmanxxx + 1]);
         float _snowmanxxxxxx = Float.intBitsToFloat(_snowman[_snowmanxxx + 2]);
         if (_snowmanxxxx < _snowmanx[CubeFace.DirectionIds.WEST]) {
            _snowmanx[CubeFace.DirectionIds.WEST] = _snowmanxxxx;
         }

         if (_snowmanxxxxx < _snowmanx[CubeFace.DirectionIds.DOWN]) {
            _snowmanx[CubeFace.DirectionIds.DOWN] = _snowmanxxxxx;
         }

         if (_snowmanxxxxxx < _snowmanx[CubeFace.DirectionIds.NORTH]) {
            _snowmanx[CubeFace.DirectionIds.NORTH] = _snowmanxxxxxx;
         }

         if (_snowmanxxxx > _snowmanx[CubeFace.DirectionIds.EAST]) {
            _snowmanx[CubeFace.DirectionIds.EAST] = _snowmanxxxx;
         }

         if (_snowmanxxxxx > _snowmanx[CubeFace.DirectionIds.UP]) {
            _snowmanx[CubeFace.DirectionIds.UP] = _snowmanxxxxx;
         }

         if (_snowmanxxxxxx > _snowmanx[CubeFace.DirectionIds.SOUTH]) {
            _snowmanx[CubeFace.DirectionIds.SOUTH] = _snowmanxxxxxx;
         }
      }

      CubeFace _snowmanxx = CubeFace.getFace(direction);

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 4; _snowmanxxxxxxx++) {
         int _snowmanxxxxxxxx = 8 * _snowmanxxxxxxx;
         CubeFace.Corner _snowmanxxxxxxxxx = _snowmanxx.getCorner(_snowmanxxxxxxx);
         float _snowmanxxxxxxxxxx = _snowmanx[_snowmanxxxxxxxxx.xSide];
         float _snowmanxxxxxxxxxxx = _snowmanx[_snowmanxxxxxxxxx.ySide];
         float _snowmanxxxxxxxxxxxx = _snowmanx[_snowmanxxxxxxxxx.zSide];
         rotationMatrix[_snowmanxxxxxxxx] = Float.floatToRawIntBits(_snowmanxxxxxxxxxx);
         rotationMatrix[_snowmanxxxxxxxx + 1] = Float.floatToRawIntBits(_snowmanxxxxxxxxxxx);
         rotationMatrix[_snowmanxxxxxxxx + 2] = Float.floatToRawIntBits(_snowmanxxxxxxxxxxxx);

         for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxxx++) {
            int _snowmanxxxxxxxxxxxxxx = 8 * _snowmanxxxxxxxxxxxxx;
            float _snowmanxxxxxxxxxxxxxxx = Float.intBitsToFloat(_snowman[_snowmanxxxxxxxxxxxxxx]);
            float _snowmanxxxxxxxxxxxxxxxx = Float.intBitsToFloat(_snowman[_snowmanxxxxxxxxxxxxxx + 1]);
            float _snowmanxxxxxxxxxxxxxxxxx = Float.intBitsToFloat(_snowman[_snowmanxxxxxxxxxxxxxx + 2]);
            if (MathHelper.approximatelyEquals(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)
               && MathHelper.approximatelyEquals(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx)
               && MathHelper.approximatelyEquals(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx)) {
               rotationMatrix[_snowmanxxxxxxxx + 4] = _snowman[_snowmanxxxxxxxxxxxxxx + 4];
               rotationMatrix[_snowmanxxxxxxxx + 4 + 1] = _snowman[_snowmanxxxxxxxxxxxxxx + 4 + 1];
            }
         }
      }
   }
}
