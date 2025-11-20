/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.render.model;

import javax.annotation.Nullable;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.CubeFace;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.json.ModelElementFace;
import net.minecraft.client.render.model.json.ModelElementTexture;
import net.minecraft.client.render.model.json.ModelRotation;
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
    private static final float MIN_SCALE = 1.0f / (float)Math.cos(0.3926991f) - 1.0f;
    private static final float MAX_SCALE = 1.0f / (float)Math.cos(0.7853981852531433) - 1.0f;

    public BakedQuad bake(Vector3f from, Vector3f to, ModelElementFace face, Sprite texture, Direction side, ModelBakeSettings settings, @Nullable ModelRotation rotation, boolean shade, Identifier modelId) {
        ModelElementTexture modelElementTexture = face.textureData;
        if (settings.isShaded()) {
            modelElementTexture = BakedQuadFactory.uvLock(face.textureData, side, settings.getRotation(), modelId);
        }
        float[] _snowman2 = new float[modelElementTexture.uvs.length];
        System.arraycopy(modelElementTexture.uvs, 0, _snowman2, 0, _snowman2.length);
        float _snowman3 = texture.getAnimationFrameDelta();
        float _snowman4 = (modelElementTexture.uvs[0] + modelElementTexture.uvs[0] + modelElementTexture.uvs[2] + modelElementTexture.uvs[2]) / 4.0f;
        float _snowman5 = (modelElementTexture.uvs[1] + modelElementTexture.uvs[1] + modelElementTexture.uvs[3] + modelElementTexture.uvs[3]) / 4.0f;
        modelElementTexture.uvs[0] = MathHelper.lerp(_snowman3, modelElementTexture.uvs[0], _snowman4);
        modelElementTexture.uvs[2] = MathHelper.lerp(_snowman3, modelElementTexture.uvs[2], _snowman4);
        modelElementTexture.uvs[1] = MathHelper.lerp(_snowman3, modelElementTexture.uvs[1], _snowman5);
        modelElementTexture.uvs[3] = MathHelper.lerp(_snowman3, modelElementTexture.uvs[3], _snowman5);
        int[] _snowman6 = this.packVertexData(modelElementTexture, texture, side, this.getPositionMatrix(from, to), settings.getRotation(), rotation, shade);
        Direction _snowman7 = BakedQuadFactory.decodeDirection(_snowman6);
        System.arraycopy(_snowman2, 0, modelElementTexture.uvs, 0, _snowman2.length);
        if (rotation == null) {
            this.encodeDirection(_snowman6, _snowman7);
        }
        return new BakedQuad(_snowman6, face.tintIndex, _snowman7, texture, shade);
    }

    public static ModelElementTexture uvLock(ModelElementTexture texture, Direction orientation, AffineTransformation rotation, Identifier modelId) {
        float f;
        float f2;
        Matrix4f matrix4f = AffineTransformations.uvLock(rotation, orientation, () -> "Unable to resolve UVLock for model: " + modelId).getMatrix();
        float _snowman2 = texture.getU(texture.getDirectionIndex(0));
        float _snowman3 = texture.getV(texture.getDirectionIndex(0));
        Vector4f _snowman4 = new Vector4f(_snowman2 / 16.0f, _snowman3 / 16.0f, 0.0f, 1.0f);
        _snowman4.transform(matrix4f);
        float _snowman5 = 16.0f * _snowman4.getX();
        float _snowman6 = 16.0f * _snowman4.getY();
        float _snowman7 = texture.getU(texture.getDirectionIndex(2));
        float _snowman8 = texture.getV(texture.getDirectionIndex(2));
        Vector4f _snowman9 = new Vector4f(_snowman7 / 16.0f, _snowman8 / 16.0f, 0.0f, 1.0f);
        _snowman9.transform(matrix4f);
        float _snowman10 = 16.0f * _snowman9.getX();
        float _snowman11 = 16.0f * _snowman9.getY();
        if (Math.signum(_snowman7 - _snowman2) == Math.signum(_snowman10 - _snowman5)) {
            f2 = _snowman5;
            _snowman = _snowman10;
        } else {
            f2 = _snowman10;
            _snowman = _snowman5;
        }
        if (Math.signum(_snowman8 - _snowman3) == Math.signum(_snowman11 - _snowman6)) {
            f = _snowman6;
            _snowman = _snowman11;
        } else {
            f = _snowman11;
            _snowman = _snowman6;
        }
        float f3 = (float)Math.toRadians(texture.rotation);
        Vector3f _snowman12 = new Vector3f(MathHelper.cos(f3), MathHelper.sin(f3), 0.0f);
        Matrix3f _snowman13 = new Matrix3f(matrix4f);
        _snowman12.transform(_snowman13);
        int _snowman14 = Math.floorMod(-((int)Math.round(Math.toDegrees(Math.atan2(_snowman12.getY(), _snowman12.getX())) / 90.0)) * 90, 360);
        return new ModelElementTexture(new float[]{f2, f, _snowman, _snowman}, _snowman14);
    }

    private int[] packVertexData(ModelElementTexture texture, Sprite sprite, Direction direction, float[] positionMatrix, AffineTransformation orientation, @Nullable ModelRotation rotation, boolean shaded) {
        int[] nArray = new int[32];
        for (int i = 0; i < 4; ++i) {
            this.packVertexData(nArray, i, direction, texture, positionMatrix, sprite, orientation, rotation, shaded);
        }
        return nArray;
    }

    private float[] getPositionMatrix(Vector3f from, Vector3f to) {
        float[] fArray = new float[Direction.values().length];
        fArray[CubeFace.DirectionIds.WEST] = from.getX() / 16.0f;
        fArray[CubeFace.DirectionIds.DOWN] = from.getY() / 16.0f;
        fArray[CubeFace.DirectionIds.NORTH] = from.getZ() / 16.0f;
        fArray[CubeFace.DirectionIds.EAST] = to.getX() / 16.0f;
        fArray[CubeFace.DirectionIds.UP] = to.getY() / 16.0f;
        fArray[CubeFace.DirectionIds.SOUTH] = to.getZ() / 16.0f;
        return fArray;
    }

    private void packVertexData(int[] vertices, int cornerIndex, Direction direction, ModelElementTexture texture, float[] positionMatrix, Sprite sprite, AffineTransformation orientation, @Nullable ModelRotation rotation, boolean shaded) {
        CubeFace.Corner corner = CubeFace.getFace(direction).getCorner(cornerIndex);
        Vector3f _snowman2 = new Vector3f(positionMatrix[corner.xSide], positionMatrix[corner.ySide], positionMatrix[corner.zSide]);
        this.rotateVertex(_snowman2, rotation);
        this.transformVertex(_snowman2, orientation);
        this.packVertexData(vertices, cornerIndex, _snowman2, sprite, texture);
    }

    private void packVertexData(int[] vertices, int cornerIndex, Vector3f position, Sprite sprite, ModelElementTexture modelElementTexture) {
        int n = cornerIndex * 8;
        vertices[n] = Float.floatToRawIntBits(position.getX());
        vertices[n + 1] = Float.floatToRawIntBits(position.getY());
        vertices[n + 2] = Float.floatToRawIntBits(position.getZ());
        vertices[n + 3] = -1;
        vertices[n + 4] = Float.floatToRawIntBits(sprite.getFrameU(modelElementTexture.getU(cornerIndex)));
        vertices[n + 4 + 1] = Float.floatToRawIntBits(sprite.getFrameV(modelElementTexture.getV(cornerIndex)));
    }

    private void rotateVertex(Vector3f vector, @Nullable ModelRotation rotation) {
        Vector3f vector3f;
        if (rotation == null) {
            return;
        }
        switch (rotation.axis) {
            case X: {
                vector3f = new Vector3f(1.0f, 0.0f, 0.0f);
                _snowman = new Vector3f(0.0f, 1.0f, 1.0f);
                break;
            }
            case Y: {
                vector3f = new Vector3f(0.0f, 1.0f, 0.0f);
                _snowman = new Vector3f(1.0f, 0.0f, 1.0f);
                break;
            }
            case Z: {
                vector3f = new Vector3f(0.0f, 0.0f, 1.0f);
                _snowman = new Vector3f(1.0f, 1.0f, 0.0f);
                break;
            }
            default: {
                throw new IllegalArgumentException("There are only 3 axes");
            }
        }
        Quaternion _snowman2 = new Quaternion(vector3f, rotation.angle, true);
        if (rotation.rescale) {
            if (Math.abs(rotation.angle) == 22.5f) {
                _snowman.scale(MIN_SCALE);
            } else {
                _snowman.scale(MAX_SCALE);
            }
            _snowman.add(1.0f, 1.0f, 1.0f);
        } else {
            _snowman.set(1.0f, 1.0f, 1.0f);
        }
        this.transformVertex(vector, rotation.origin.copy(), new Matrix4f(_snowman2), _snowman);
    }

    public void transformVertex(Vector3f vertex, AffineTransformation transformation) {
        if (transformation == AffineTransformation.identity()) {
            return;
        }
        this.transformVertex(vertex, new Vector3f(0.5f, 0.5f, 0.5f), transformation.getMatrix(), new Vector3f(1.0f, 1.0f, 1.0f));
    }

    private void transformVertex(Vector3f vertex, Vector3f origin, Matrix4f transformationMatrix, Vector3f scale) {
        Vector4f vector4f = new Vector4f(vertex.getX() - origin.getX(), vertex.getY() - origin.getY(), vertex.getZ() - origin.getZ(), 1.0f);
        vector4f.transform(transformationMatrix);
        vector4f.multiplyComponentwise(scale);
        vertex.set(vector4f.getX() + origin.getX(), vector4f.getY() + origin.getY(), vector4f.getZ() + origin.getZ());
    }

    public static Direction decodeDirection(int[] rotationMatrix) {
        Vector3f vector3f = new Vector3f(Float.intBitsToFloat(rotationMatrix[0]), Float.intBitsToFloat(rotationMatrix[1]), Float.intBitsToFloat(rotationMatrix[2]));
        _snowman = new Vector3f(Float.intBitsToFloat(rotationMatrix[8]), Float.intBitsToFloat(rotationMatrix[9]), Float.intBitsToFloat(rotationMatrix[10]));
        _snowman = new Vector3f(Float.intBitsToFloat(rotationMatrix[16]), Float.intBitsToFloat(rotationMatrix[17]), Float.intBitsToFloat(rotationMatrix[18]));
        _snowman = vector3f.copy();
        _snowman.subtract(_snowman);
        _snowman = _snowman.copy();
        _snowman.subtract(_snowman);
        _snowman = _snowman.copy();
        _snowman.cross(_snowman);
        _snowman.normalize();
        Direction _snowman2 = null;
        float _snowman3 = 0.0f;
        for (Direction direction : Direction.values()) {
            Vec3i vec3i = direction.getVector();
            Vector3f _snowman4 = new Vector3f(vec3i.getX(), vec3i.getY(), vec3i.getZ());
            float _snowman5 = _snowman.dot(_snowman4);
            if (!(_snowman5 >= 0.0f) || !(_snowman5 > _snowman3)) continue;
            _snowman3 = _snowman5;
            _snowman2 = direction;
        }
        if (_snowman2 == null) {
            return Direction.UP;
        }
        return _snowman2;
    }

    private void encodeDirection(int[] rotationMatrix, Direction direction) {
        int[] nArray = new int[rotationMatrix.length];
        System.arraycopy(rotationMatrix, 0, nArray, 0, rotationMatrix.length);
        float[] _snowman2 = new float[Direction.values().length];
        _snowman2[CubeFace.DirectionIds.WEST] = 999.0f;
        _snowman2[CubeFace.DirectionIds.DOWN] = 999.0f;
        _snowman2[CubeFace.DirectionIds.NORTH] = 999.0f;
        _snowman2[CubeFace.DirectionIds.EAST] = -999.0f;
        _snowman2[CubeFace.DirectionIds.UP] = -999.0f;
        _snowman2[CubeFace.DirectionIds.SOUTH] = -999.0f;
        for (int i = 0; i < 4; ++i) {
            i = 8 * i;
            float f = Float.intBitsToFloat(nArray[i]);
            _snowman = Float.intBitsToFloat(nArray[i + 1]);
            _snowman3 = Float.intBitsToFloat(nArray[i + 2]);
            if (f < _snowman2[CubeFace.DirectionIds.WEST]) {
                _snowman2[CubeFace.DirectionIds.WEST] = f;
            }
            if (_snowman < _snowman2[CubeFace.DirectionIds.DOWN]) {
                _snowman2[CubeFace.DirectionIds.DOWN] = _snowman;
            }
            if (_snowman3 < _snowman2[CubeFace.DirectionIds.NORTH]) {
                _snowman2[CubeFace.DirectionIds.NORTH] = _snowman3;
            }
            if (f > _snowman2[CubeFace.DirectionIds.EAST]) {
                _snowman2[CubeFace.DirectionIds.EAST] = f;
            }
            if (_snowman > _snowman2[CubeFace.DirectionIds.UP]) {
                _snowman2[CubeFace.DirectionIds.UP] = _snowman;
            }
            if (!(_snowman3 > _snowman2[CubeFace.DirectionIds.SOUTH])) continue;
            _snowman2[CubeFace.DirectionIds.SOUTH] = _snowman3;
        }
        CubeFace cubeFace = CubeFace.getFace(direction);
        for (int i = 0; i < 4; ++i) {
            _snowman = 8 * i;
            CubeFace.Corner corner = cubeFace.getCorner(i);
            float _snowman3 = _snowman2[corner.xSide];
            float _snowman4 = _snowman2[corner.ySide];
            float _snowman5 = _snowman2[corner.zSide];
            rotationMatrix[_snowman] = Float.floatToRawIntBits(_snowman3);
            rotationMatrix[_snowman + 1] = Float.floatToRawIntBits(_snowman4);
            rotationMatrix[_snowman + 2] = Float.floatToRawIntBits(_snowman5);
            for (int j = 0; j < 4; ++j) {
                _snowman = 8 * j;
                float f = Float.intBitsToFloat(nArray[_snowman]);
                _snowman = Float.intBitsToFloat(nArray[_snowman + 1]);
                _snowman = Float.intBitsToFloat(nArray[_snowman + 2]);
                if (!MathHelper.approximatelyEquals(_snowman3, f) || !MathHelper.approximatelyEquals(_snowman4, _snowman) || !MathHelper.approximatelyEquals(_snowman5, _snowman)) continue;
                rotationMatrix[_snowman + 4] = nArray[_snowman + 4];
                rotationMatrix[_snowman + 4 + 1] = nArray[_snowman + 4 + 1];
            }
        }
    }
}

