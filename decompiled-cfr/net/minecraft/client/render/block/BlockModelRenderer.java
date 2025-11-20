/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap
 *  it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.client.render.block;

import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockRenderView;

public class BlockModelRenderer {
    private final BlockColors colorMap;
    private static final ThreadLocal<BrightnessCache> brightnessCache = ThreadLocal.withInitial(() -> new BrightnessCache());

    public BlockModelRenderer(BlockColors colorMap) {
        this.colorMap = colorMap;
    }

    public boolean render(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack matrix, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay) {
        boolean bl = MinecraftClient.isAmbientOcclusionEnabled() && state.getLuminance() == 0 && model.useAmbientOcclusion();
        Vec3d _snowman2 = state.getModelOffset(world, pos);
        matrix.translate(_snowman2.x, _snowman2.y, _snowman2.z);
        try {
            if (bl) {
                return this.renderSmooth(world, model, state, pos, matrix, vertexConsumer, cull, random, seed, overlay);
            }
            return this.renderFlat(world, model, state, pos, matrix, vertexConsumer, cull, random, seed, overlay);
        }
        catch (Throwable _snowman3) {
            CrashReport crashReport = CrashReport.create(_snowman3, "Tesselating block model");
            CrashReportSection _snowman4 = crashReport.addElement("Block model being tesselated");
            CrashReportSection.addBlockInfo(_snowman4, pos, state);
            _snowman4.add("Using AO", bl);
            throw new CrashException(crashReport);
        }
    }

    public boolean renderSmooth(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack buffer, VertexConsumer vertexConsumer, boolean cull, Random random, long seed, int overlay) {
        boolean _snowman5 = false;
        float[] _snowman2 = new float[Direction.values().length * 2];
        BitSet _snowman3 = new BitSet(3);
        AmbientOcclusionCalculator _snowman4 = new AmbientOcclusionCalculator();
        for (Direction direction : Direction.values()) {
            random.setSeed(seed);
            List<BakedQuad> list = model.getQuads(state, direction, random);
            if (list.isEmpty() || cull && !Block.shouldDrawSide(state, world, pos, direction)) continue;
            this.renderQuadsSmooth(world, state, pos, buffer, vertexConsumer, list, _snowman2, _snowman3, _snowman4, overlay);
            _snowman5 = true;
        }
        random.setSeed(seed);
        List<BakedQuad> list = model.getQuads(state, null, random);
        if (!list.isEmpty()) {
            this.renderQuadsSmooth(world, state, pos, buffer, vertexConsumer, list, _snowman2, _snowman3, _snowman4, overlay);
            _snowman5 = true;
        }
        return _snowman5;
    }

    public boolean renderFlat(BlockRenderView world, BakedModel model, BlockState state, BlockPos pos, MatrixStack buffer, VertexConsumer vertexConsumer, boolean cull, Random random, long l, int n) {
        boolean _snowman4 = false;
        BitSet _snowman2 = new BitSet(3);
        for (Direction direction : Direction.values()) {
            random.setSeed(l);
            List<BakedQuad> list = model.getQuads(state, direction, random);
            if (list.isEmpty() || cull && !Block.shouldDrawSide(state, world, pos, direction)) continue;
            int _snowman3 = WorldRenderer.getLightmapCoordinates(world, state, pos.offset(direction));
            this.renderQuadsFlat(world, state, pos, _snowman3, n, false, buffer, vertexConsumer, list, _snowman2);
            _snowman4 = true;
        }
        random.setSeed(l);
        _snowman = model.getQuads(state, null, random);
        if (!_snowman.isEmpty()) {
            this.renderQuadsFlat(world, state, pos, -1, n, true, buffer, vertexConsumer, _snowman, _snowman2);
            _snowman4 = true;
        }
        return _snowman4;
    }

    private void renderQuadsSmooth(BlockRenderView world, BlockState state, BlockPos pos, MatrixStack matrix, VertexConsumer vertexConsumer, List<BakedQuad> quads, float[] box, BitSet flags, AmbientOcclusionCalculator ambientOcclusionCalculator, int overlay) {
        for (BakedQuad bakedQuad : quads) {
            this.getQuadDimensions(world, state, pos, bakedQuad.getVertexData(), bakedQuad.getFace(), box, flags);
            ambientOcclusionCalculator.apply(world, state, pos, bakedQuad.getFace(), box, flags, bakedQuad.hasShade());
            this.renderQuad(world, state, pos, vertexConsumer, matrix.peek(), bakedQuad, ambientOcclusionCalculator.brightness[0], ambientOcclusionCalculator.brightness[1], ambientOcclusionCalculator.brightness[2], ambientOcclusionCalculator.brightness[3], ambientOcclusionCalculator.light[0], ambientOcclusionCalculator.light[1], ambientOcclusionCalculator.light[2], ambientOcclusionCalculator.light[3], overlay);
        }
    }

    private void renderQuad(BlockRenderView world, BlockState state, BlockPos pos, VertexConsumer vertexConsumer, MatrixStack.Entry matrixEntry, BakedQuad quad, float brightness0, float brightness1, float brightness2, float brightness3, int light0, int light1, int light2, int light3, int overlay) {
        float _snowman4;
        float _snowman3;
        float _snowman2;
        if (quad.hasColor()) {
            int n = this.colorMap.getColor(state, world, pos, quad.getColorIndex());
            _snowman2 = (float)(n >> 16 & 0xFF) / 255.0f;
            _snowman3 = (float)(n >> 8 & 0xFF) / 255.0f;
            _snowman4 = (float)(n & 0xFF) / 255.0f;
        } else {
            _snowman2 = 1.0f;
            _snowman3 = 1.0f;
            _snowman4 = 1.0f;
        }
        vertexConsumer.quad(matrixEntry, quad, new float[]{brightness0, brightness1, brightness2, brightness3}, _snowman2, _snowman3, _snowman4, new int[]{light0, light1, light2, light3}, overlay, true);
    }

    private void getQuadDimensions(BlockRenderView world, BlockState state, BlockPos pos, int[] vertexData, Direction face, @Nullable float[] box, BitSet flags) {
        int _snowman2;
        float f = 32.0f;
        _snowman = 32.0f;
        _snowman = 32.0f;
        _snowman = -32.0f;
        _snowman = -32.0f;
        _snowman = -32.0f;
        for (_snowman2 = 0; _snowman2 < 4; ++_snowman2) {
            float f2 = Float.intBitsToFloat(vertexData[_snowman2 * 8]);
            _snowman = Float.intBitsToFloat(vertexData[_snowman2 * 8 + 1]);
            _snowman = Float.intBitsToFloat(vertexData[_snowman2 * 8 + 2]);
            f = Math.min(f, f2);
            _snowman = Math.min(_snowman, _snowman);
            _snowman = Math.min(_snowman, _snowman);
            _snowman = Math.max(_snowman, f2);
            _snowman = Math.max(_snowman, _snowman);
            _snowman = Math.max(_snowman, _snowman);
        }
        if (box != null) {
            box[Direction.WEST.getId()] = f;
            box[Direction.EAST.getId()] = _snowman;
            box[Direction.DOWN.getId()] = _snowman;
            box[Direction.UP.getId()] = _snowman;
            box[Direction.NORTH.getId()] = _snowman;
            box[Direction.SOUTH.getId()] = _snowman;
            _snowman2 = Direction.values().length;
            box[Direction.WEST.getId() + _snowman2] = 1.0f - f;
            box[Direction.EAST.getId() + _snowman2] = 1.0f - _snowman;
            box[Direction.DOWN.getId() + _snowman2] = 1.0f - _snowman;
            box[Direction.UP.getId() + _snowman2] = 1.0f - _snowman;
            box[Direction.NORTH.getId() + _snowman2] = 1.0f - _snowman;
            box[Direction.SOUTH.getId() + _snowman2] = 1.0f - _snowman;
        }
        _snowman = 1.0E-4f;
        f2 = 0.9999f;
        switch (face) {
            case DOWN: {
                flags.set(1, f >= 1.0E-4f || _snowman >= 1.0E-4f || _snowman <= 0.9999f || _snowman <= 0.9999f);
                flags.set(0, _snowman == _snowman && (_snowman < 1.0E-4f || state.isFullCube(world, pos)));
                break;
            }
            case UP: {
                flags.set(1, f >= 1.0E-4f || _snowman >= 1.0E-4f || _snowman <= 0.9999f || _snowman <= 0.9999f);
                flags.set(0, _snowman == _snowman && (_snowman > 0.9999f || state.isFullCube(world, pos)));
                break;
            }
            case NORTH: {
                flags.set(1, f >= 1.0E-4f || _snowman >= 1.0E-4f || _snowman <= 0.9999f || _snowman <= 0.9999f);
                flags.set(0, _snowman == _snowman && (_snowman < 1.0E-4f || state.isFullCube(world, pos)));
                break;
            }
            case SOUTH: {
                flags.set(1, f >= 1.0E-4f || _snowman >= 1.0E-4f || _snowman <= 0.9999f || _snowman <= 0.9999f);
                flags.set(0, _snowman == _snowman && (_snowman > 0.9999f || state.isFullCube(world, pos)));
                break;
            }
            case WEST: {
                flags.set(1, _snowman >= 1.0E-4f || _snowman >= 1.0E-4f || _snowman <= 0.9999f || _snowman <= 0.9999f);
                flags.set(0, f == _snowman && (f < 1.0E-4f || state.isFullCube(world, pos)));
                break;
            }
            case EAST: {
                flags.set(1, _snowman >= 1.0E-4f || _snowman >= 1.0E-4f || _snowman <= 0.9999f || _snowman <= 0.9999f);
                flags.set(0, f == _snowman && (_snowman > 0.9999f || state.isFullCube(world, pos)));
            }
        }
    }

    private void renderQuadsFlat(BlockRenderView world, BlockState state, BlockPos pos, int light, int overlay, boolean useWorldLight, MatrixStack matrices, VertexConsumer vertexConsumer, List<BakedQuad> quads, BitSet flags) {
        for (BakedQuad bakedQuad2 : quads) {
            BakedQuad bakedQuad2;
            if (useWorldLight) {
                this.getQuadDimensions(world, state, pos, bakedQuad2.getVertexData(), bakedQuad2.getFace(), null, flags);
                BlockPos blockPos = flags.get(0) ? pos.offset(bakedQuad2.getFace()) : pos;
                light = WorldRenderer.getLightmapCoordinates(world, state, blockPos);
            }
            float _snowman2 = world.getBrightness(bakedQuad2.getFace(), bakedQuad2.hasShade());
            this.renderQuad(world, state, pos, vertexConsumer, matrices.peek(), bakedQuad2, _snowman2, _snowman2, _snowman2, _snowman2, light, light, light, light, overlay);
        }
    }

    public void render(MatrixStack.Entry entry, VertexConsumer vertexConsumer, @Nullable BlockState blockState, BakedModel bakedModel, float f, float f2, float f3, int n, int n2) {
        Random random = new Random();
        long _snowman2 = 42L;
        for (Direction direction : Direction.values()) {
            random.setSeed(42L);
            BlockModelRenderer.renderQuad(entry, vertexConsumer, f, f2, f3, bakedModel.getQuads(blockState, direction, random), n, n2);
        }
        random.setSeed(42L);
        BlockModelRenderer.renderQuad(entry, vertexConsumer, f, f2, f3, bakedModel.getQuads(blockState, null, random), n, n2);
    }

    private static void renderQuad(MatrixStack.Entry entry, VertexConsumer vertexConsumer2, float f, float f2, float f3, List<BakedQuad> list, int n, int n2) {
        for (BakedQuad bakedQuad : list) {
            VertexConsumer vertexConsumer2;
            float f4;
            if (bakedQuad.hasColor()) {
                f4 = MathHelper.clamp(f, 0.0f, 1.0f);
                _snowman = MathHelper.clamp(f2, 0.0f, 1.0f);
                _snowman = MathHelper.clamp(f3, 0.0f, 1.0f);
            } else {
                f4 = 1.0f;
                _snowman = 1.0f;
                _snowman = 1.0f;
            }
            vertexConsumer2.quad(entry, bakedQuad, f4, _snowman, _snowman, n, n2);
        }
    }

    public static void enableBrightnessCache() {
        brightnessCache.get().enable();
    }

    public static void disableBrightnessCache() {
        brightnessCache.get().disable();
    }

    public static enum NeighborData {
        DOWN(new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH}, 0.5f, true, new NeighborOrientation[]{NeighborOrientation.FLIP_WEST, NeighborOrientation.SOUTH, NeighborOrientation.FLIP_WEST, NeighborOrientation.FLIP_SOUTH, NeighborOrientation.WEST, NeighborOrientation.FLIP_SOUTH, NeighborOrientation.WEST, NeighborOrientation.SOUTH}, new NeighborOrientation[]{NeighborOrientation.FLIP_WEST, NeighborOrientation.NORTH, NeighborOrientation.FLIP_WEST, NeighborOrientation.FLIP_NORTH, NeighborOrientation.WEST, NeighborOrientation.FLIP_NORTH, NeighborOrientation.WEST, NeighborOrientation.NORTH}, new NeighborOrientation[]{NeighborOrientation.FLIP_EAST, NeighborOrientation.NORTH, NeighborOrientation.FLIP_EAST, NeighborOrientation.FLIP_NORTH, NeighborOrientation.EAST, NeighborOrientation.FLIP_NORTH, NeighborOrientation.EAST, NeighborOrientation.NORTH}, new NeighborOrientation[]{NeighborOrientation.FLIP_EAST, NeighborOrientation.SOUTH, NeighborOrientation.FLIP_EAST, NeighborOrientation.FLIP_SOUTH, NeighborOrientation.EAST, NeighborOrientation.FLIP_SOUTH, NeighborOrientation.EAST, NeighborOrientation.SOUTH}),
        UP(new Direction[]{Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH}, 1.0f, true, new NeighborOrientation[]{NeighborOrientation.EAST, NeighborOrientation.SOUTH, NeighborOrientation.EAST, NeighborOrientation.FLIP_SOUTH, NeighborOrientation.FLIP_EAST, NeighborOrientation.FLIP_SOUTH, NeighborOrientation.FLIP_EAST, NeighborOrientation.SOUTH}, new NeighborOrientation[]{NeighborOrientation.EAST, NeighborOrientation.NORTH, NeighborOrientation.EAST, NeighborOrientation.FLIP_NORTH, NeighborOrientation.FLIP_EAST, NeighborOrientation.FLIP_NORTH, NeighborOrientation.FLIP_EAST, NeighborOrientation.NORTH}, new NeighborOrientation[]{NeighborOrientation.WEST, NeighborOrientation.NORTH, NeighborOrientation.WEST, NeighborOrientation.FLIP_NORTH, NeighborOrientation.FLIP_WEST, NeighborOrientation.FLIP_NORTH, NeighborOrientation.FLIP_WEST, NeighborOrientation.NORTH}, new NeighborOrientation[]{NeighborOrientation.WEST, NeighborOrientation.SOUTH, NeighborOrientation.WEST, NeighborOrientation.FLIP_SOUTH, NeighborOrientation.FLIP_WEST, NeighborOrientation.FLIP_SOUTH, NeighborOrientation.FLIP_WEST, NeighborOrientation.SOUTH}),
        NORTH(new Direction[]{Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST}, 0.8f, true, new NeighborOrientation[]{NeighborOrientation.UP, NeighborOrientation.FLIP_WEST, NeighborOrientation.UP, NeighborOrientation.WEST, NeighborOrientation.FLIP_UP, NeighborOrientation.WEST, NeighborOrientation.FLIP_UP, NeighborOrientation.FLIP_WEST}, new NeighborOrientation[]{NeighborOrientation.UP, NeighborOrientation.FLIP_EAST, NeighborOrientation.UP, NeighborOrientation.EAST, NeighborOrientation.FLIP_UP, NeighborOrientation.EAST, NeighborOrientation.FLIP_UP, NeighborOrientation.FLIP_EAST}, new NeighborOrientation[]{NeighborOrientation.DOWN, NeighborOrientation.FLIP_EAST, NeighborOrientation.DOWN, NeighborOrientation.EAST, NeighborOrientation.FLIP_DOWN, NeighborOrientation.EAST, NeighborOrientation.FLIP_DOWN, NeighborOrientation.FLIP_EAST}, new NeighborOrientation[]{NeighborOrientation.DOWN, NeighborOrientation.FLIP_WEST, NeighborOrientation.DOWN, NeighborOrientation.WEST, NeighborOrientation.FLIP_DOWN, NeighborOrientation.WEST, NeighborOrientation.FLIP_DOWN, NeighborOrientation.FLIP_WEST}),
        SOUTH(new Direction[]{Direction.WEST, Direction.EAST, Direction.DOWN, Direction.UP}, 0.8f, true, new NeighborOrientation[]{NeighborOrientation.UP, NeighborOrientation.FLIP_WEST, NeighborOrientation.FLIP_UP, NeighborOrientation.FLIP_WEST, NeighborOrientation.FLIP_UP, NeighborOrientation.WEST, NeighborOrientation.UP, NeighborOrientation.WEST}, new NeighborOrientation[]{NeighborOrientation.DOWN, NeighborOrientation.FLIP_WEST, NeighborOrientation.FLIP_DOWN, NeighborOrientation.FLIP_WEST, NeighborOrientation.FLIP_DOWN, NeighborOrientation.WEST, NeighborOrientation.DOWN, NeighborOrientation.WEST}, new NeighborOrientation[]{NeighborOrientation.DOWN, NeighborOrientation.FLIP_EAST, NeighborOrientation.FLIP_DOWN, NeighborOrientation.FLIP_EAST, NeighborOrientation.FLIP_DOWN, NeighborOrientation.EAST, NeighborOrientation.DOWN, NeighborOrientation.EAST}, new NeighborOrientation[]{NeighborOrientation.UP, NeighborOrientation.FLIP_EAST, NeighborOrientation.FLIP_UP, NeighborOrientation.FLIP_EAST, NeighborOrientation.FLIP_UP, NeighborOrientation.EAST, NeighborOrientation.UP, NeighborOrientation.EAST}),
        WEST(new Direction[]{Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH}, 0.6f, true, new NeighborOrientation[]{NeighborOrientation.UP, NeighborOrientation.SOUTH, NeighborOrientation.UP, NeighborOrientation.FLIP_SOUTH, NeighborOrientation.FLIP_UP, NeighborOrientation.FLIP_SOUTH, NeighborOrientation.FLIP_UP, NeighborOrientation.SOUTH}, new NeighborOrientation[]{NeighborOrientation.UP, NeighborOrientation.NORTH, NeighborOrientation.UP, NeighborOrientation.FLIP_NORTH, NeighborOrientation.FLIP_UP, NeighborOrientation.FLIP_NORTH, NeighborOrientation.FLIP_UP, NeighborOrientation.NORTH}, new NeighborOrientation[]{NeighborOrientation.DOWN, NeighborOrientation.NORTH, NeighborOrientation.DOWN, NeighborOrientation.FLIP_NORTH, NeighborOrientation.FLIP_DOWN, NeighborOrientation.FLIP_NORTH, NeighborOrientation.FLIP_DOWN, NeighborOrientation.NORTH}, new NeighborOrientation[]{NeighborOrientation.DOWN, NeighborOrientation.SOUTH, NeighborOrientation.DOWN, NeighborOrientation.FLIP_SOUTH, NeighborOrientation.FLIP_DOWN, NeighborOrientation.FLIP_SOUTH, NeighborOrientation.FLIP_DOWN, NeighborOrientation.SOUTH}),
        EAST(new Direction[]{Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH}, 0.6f, true, new NeighborOrientation[]{NeighborOrientation.FLIP_DOWN, NeighborOrientation.SOUTH, NeighborOrientation.FLIP_DOWN, NeighborOrientation.FLIP_SOUTH, NeighborOrientation.DOWN, NeighborOrientation.FLIP_SOUTH, NeighborOrientation.DOWN, NeighborOrientation.SOUTH}, new NeighborOrientation[]{NeighborOrientation.FLIP_DOWN, NeighborOrientation.NORTH, NeighborOrientation.FLIP_DOWN, NeighborOrientation.FLIP_NORTH, NeighborOrientation.DOWN, NeighborOrientation.FLIP_NORTH, NeighborOrientation.DOWN, NeighborOrientation.NORTH}, new NeighborOrientation[]{NeighborOrientation.FLIP_UP, NeighborOrientation.NORTH, NeighborOrientation.FLIP_UP, NeighborOrientation.FLIP_NORTH, NeighborOrientation.UP, NeighborOrientation.FLIP_NORTH, NeighborOrientation.UP, NeighborOrientation.NORTH}, new NeighborOrientation[]{NeighborOrientation.FLIP_UP, NeighborOrientation.SOUTH, NeighborOrientation.FLIP_UP, NeighborOrientation.FLIP_SOUTH, NeighborOrientation.UP, NeighborOrientation.FLIP_SOUTH, NeighborOrientation.UP, NeighborOrientation.SOUTH});

        private final Direction[] faces;
        private final boolean nonCubicWeight;
        private final NeighborOrientation[] field_4192;
        private final NeighborOrientation[] field_4185;
        private final NeighborOrientation[] field_4180;
        private final NeighborOrientation[] field_4188;
        private static final NeighborData[] field_4190;

        private NeighborData(Direction[] directionArray, float f, boolean bl, NeighborOrientation[] neighborOrientationArray, NeighborOrientation[] neighborOrientationArray2, NeighborOrientation[] neighborOrientationArray3, NeighborOrientation[] neighborOrientationArray4) {
            this.faces = directionArray;
            this.nonCubicWeight = bl;
            this.field_4192 = neighborOrientationArray;
            this.field_4185 = neighborOrientationArray2;
            this.field_4180 = neighborOrientationArray3;
            this.field_4188 = neighborOrientationArray4;
        }

        public static NeighborData getData(Direction direction) {
            return field_4190[direction.getId()];
        }

        static {
            field_4190 = Util.make(new NeighborData[6], neighborDataArray -> {
                neighborDataArray[Direction.DOWN.getId()] = DOWN;
                neighborDataArray[Direction.UP.getId()] = UP;
                neighborDataArray[Direction.NORTH.getId()] = NORTH;
                neighborDataArray[Direction.SOUTH.getId()] = SOUTH;
                neighborDataArray[Direction.WEST.getId()] = WEST;
                neighborDataArray[Direction.EAST.getId()] = EAST;
            });
        }
    }

    public static enum NeighborOrientation {
        DOWN(Direction.DOWN, false),
        UP(Direction.UP, false),
        NORTH(Direction.NORTH, false),
        SOUTH(Direction.SOUTH, false),
        WEST(Direction.WEST, false),
        EAST(Direction.EAST, false),
        FLIP_DOWN(Direction.DOWN, true),
        FLIP_UP(Direction.UP, true),
        FLIP_NORTH(Direction.NORTH, true),
        FLIP_SOUTH(Direction.SOUTH, true),
        FLIP_WEST(Direction.WEST, true),
        FLIP_EAST(Direction.EAST, true);

        private final int shape;

        private NeighborOrientation(Direction direction, boolean bl) {
            this.shape = direction.getId() + (bl ? Direction.values().length : 0);
        }
    }

    class AmbientOcclusionCalculator {
        private final float[] brightness = new float[4];
        private final int[] light = new int[4];

        public void apply(BlockRenderView world, BlockState state, BlockPos pos, Direction direction, float[] box, BitSet flags, boolean bl) {
            float f;
            int _snowman27;
            float _snowman26;
            int _snowman25;
            float _snowman24;
            int _snowman23;
            float _snowman22;
            int _snowman21;
            float _snowman20;
            BlockPos blockPos = flags.get(0) ? pos.offset(direction) : pos;
            NeighborData _snowman2 = NeighborData.getData(direction);
            BlockPos.Mutable _snowman3 = new BlockPos.Mutable();
            BrightnessCache _snowman4 = (BrightnessCache)brightnessCache.get();
            _snowman3.set(blockPos, _snowman2.faces[0]);
            BlockState _snowman5 = world.getBlockState(_snowman3);
            int _snowman6 = _snowman4.getInt(_snowman5, world, _snowman3);
            float _snowman7 = _snowman4.getFloat(_snowman5, world, _snowman3);
            _snowman3.set(blockPos, _snowman2.faces[1]);
            BlockState _snowman8 = world.getBlockState(_snowman3);
            int _snowman9 = _snowman4.getInt(_snowman8, world, _snowman3);
            float _snowman10 = _snowman4.getFloat(_snowman8, world, _snowman3);
            _snowman3.set(blockPos, _snowman2.faces[2]);
            BlockState _snowman11 = world.getBlockState(_snowman3);
            int _snowman12 = _snowman4.getInt(_snowman11, world, _snowman3);
            float _snowman13 = _snowman4.getFloat(_snowman11, world, _snowman3);
            _snowman3.set(blockPos, _snowman2.faces[3]);
            BlockState _snowman14 = world.getBlockState(_snowman3);
            int _snowman15 = _snowman4.getInt(_snowman14, world, _snowman3);
            float _snowman16 = _snowman4.getFloat(_snowman14, world, _snowman3);
            _snowman3.set(blockPos, _snowman2.faces[0]).move(direction);
            boolean _snowman17 = world.getBlockState(_snowman3).getOpacity(world, _snowman3) == 0;
            _snowman3.set(blockPos, _snowman2.faces[1]).move(direction);
            boolean _snowman18 = world.getBlockState(_snowman3).getOpacity(world, _snowman3) == 0;
            _snowman3.set(blockPos, _snowman2.faces[2]).move(direction);
            boolean _snowman19 = world.getBlockState(_snowman3).getOpacity(world, _snowman3) == 0;
            _snowman3.set(blockPos, _snowman2.faces[3]).move(direction);
            boolean bl2 = _snowman = world.getBlockState(_snowman3).getOpacity(world, _snowman3) == 0;
            if (_snowman19 || _snowman17) {
                _snowman3.set(blockPos, _snowman2.faces[0]).move(_snowman2.faces[2]);
                BlockState blockState = world.getBlockState(_snowman3);
                _snowman20 = _snowman4.getFloat(blockState, world, _snowman3);
                _snowman21 = _snowman4.getInt(blockState, world, _snowman3);
            } else {
                _snowman20 = _snowman7;
                _snowman21 = _snowman6;
            }
            if (_snowman || _snowman17) {
                _snowman3.set(blockPos, _snowman2.faces[0]).move(_snowman2.faces[3]);
                blockState = world.getBlockState(_snowman3);
                _snowman22 = _snowman4.getFloat(blockState, world, _snowman3);
                _snowman23 = _snowman4.getInt(blockState, world, _snowman3);
            } else {
                _snowman22 = _snowman7;
                _snowman23 = _snowman6;
            }
            if (_snowman19 || _snowman18) {
                _snowman3.set(blockPos, _snowman2.faces[1]).move(_snowman2.faces[2]);
                blockState = world.getBlockState(_snowman3);
                _snowman24 = _snowman4.getFloat(blockState, world, _snowman3);
                _snowman25 = _snowman4.getInt(blockState, world, _snowman3);
            } else {
                _snowman24 = _snowman7;
                _snowman25 = _snowman6;
            }
            if (_snowman || _snowman18) {
                _snowman3.set(blockPos, _snowman2.faces[1]).move(_snowman2.faces[3]);
                blockState = world.getBlockState(_snowman3);
                _snowman26 = _snowman4.getFloat(blockState, world, _snowman3);
                _snowman27 = _snowman4.getInt(blockState, world, _snowman3);
            } else {
                _snowman26 = _snowman7;
                _snowman27 = _snowman6;
            }
            int n = _snowman4.getInt(state, world, pos);
            _snowman3.set(pos, direction);
            BlockState _snowman28 = world.getBlockState(_snowman3);
            if (flags.get(0) || !_snowman28.isOpaqueFullCube(world, _snowman3)) {
                n = _snowman4.getInt(_snowman28, world, _snowman3);
            }
            float _snowman29 = flags.get(0) ? _snowman4.getFloat(world.getBlockState(blockPos), world, blockPos) : _snowman4.getFloat(world.getBlockState(pos), world, pos);
            Translation _snowman30 = Translation.getTranslations(direction);
            if (!flags.get(1) || !_snowman2.nonCubicWeight) {
                f = (_snowman16 + _snowman7 + _snowman22 + _snowman29) * 0.25f;
                _snowman = (_snowman13 + _snowman7 + _snowman20 + _snowman29) * 0.25f;
                _snowman = (_snowman13 + _snowman10 + _snowman24 + _snowman29) * 0.25f;
                _snowman = (_snowman16 + _snowman10 + _snowman26 + _snowman29) * 0.25f;
                this.light[((Translation)_snowman30).firstCorner] = this.getAmbientOcclusionBrightness(_snowman15, _snowman6, _snowman23, n);
                this.light[((Translation)_snowman30).secondCorner] = this.getAmbientOcclusionBrightness(_snowman12, _snowman6, _snowman21, n);
                this.light[((Translation)_snowman30).thirdCorner] = this.getAmbientOcclusionBrightness(_snowman12, _snowman9, _snowman25, n);
                this.light[((Translation)_snowman30).fourthCorner] = this.getAmbientOcclusionBrightness(_snowman15, _snowman9, _snowman27, n);
                this.brightness[((Translation)_snowman30).firstCorner] = f;
                this.brightness[((Translation)_snowman30).secondCorner] = _snowman;
                this.brightness[((Translation)_snowman30).thirdCorner] = _snowman;
                this.brightness[((Translation)_snowman30).fourthCorner] = _snowman;
            } else {
                f = (_snowman16 + _snowman7 + _snowman22 + _snowman29) * 0.25f;
                _snowman = (_snowman13 + _snowman7 + _snowman20 + _snowman29) * 0.25f;
                _snowman = (_snowman13 + _snowman10 + _snowman24 + _snowman29) * 0.25f;
                _snowman = (_snowman16 + _snowman10 + _snowman26 + _snowman29) * 0.25f;
                _snowman = box[_snowman2.field_4192[0].shape] * box[_snowman2.field_4192[1].shape];
                _snowman = box[_snowman2.field_4192[2].shape] * box[_snowman2.field_4192[3].shape];
                _snowman = box[_snowman2.field_4192[4].shape] * box[_snowman2.field_4192[5].shape];
                _snowman = box[_snowman2.field_4192[6].shape] * box[_snowman2.field_4192[7].shape];
                _snowman = box[_snowman2.field_4185[0].shape] * box[_snowman2.field_4185[1].shape];
                _snowman = box[_snowman2.field_4185[2].shape] * box[_snowman2.field_4185[3].shape];
                _snowman = box[_snowman2.field_4185[4].shape] * box[_snowman2.field_4185[5].shape];
                _snowman = box[_snowman2.field_4185[6].shape] * box[_snowman2.field_4185[7].shape];
                _snowman = box[_snowman2.field_4180[0].shape] * box[_snowman2.field_4180[1].shape];
                _snowman = box[_snowman2.field_4180[2].shape] * box[_snowman2.field_4180[3].shape];
                _snowman = box[_snowman2.field_4180[4].shape] * box[_snowman2.field_4180[5].shape];
                _snowman = box[_snowman2.field_4180[6].shape] * box[_snowman2.field_4180[7].shape];
                _snowman = box[_snowman2.field_4188[0].shape] * box[_snowman2.field_4188[1].shape];
                _snowman = box[_snowman2.field_4188[2].shape] * box[_snowman2.field_4188[3].shape];
                _snowman = box[_snowman2.field_4188[4].shape] * box[_snowman2.field_4188[5].shape];
                _snowman = box[_snowman2.field_4188[6].shape] * box[_snowman2.field_4188[7].shape];
                this.brightness[((Translation)_snowman30).firstCorner] = f * _snowman + _snowman * _snowman + _snowman * _snowman + _snowman * _snowman;
                this.brightness[((Translation)_snowman30).secondCorner] = f * _snowman + _snowman * _snowman + _snowman * _snowman + _snowman * _snowman;
                this.brightness[((Translation)_snowman30).thirdCorner] = f * _snowman + _snowman * _snowman + _snowman * _snowman + _snowman * _snowman;
                this.brightness[((Translation)_snowman30).fourthCorner] = f * _snowman + _snowman * _snowman + _snowman * _snowman + _snowman * _snowman;
                int _snowman31 = this.getAmbientOcclusionBrightness(_snowman15, _snowman6, _snowman23, n);
                int _snowman32 = this.getAmbientOcclusionBrightness(_snowman12, _snowman6, _snowman21, n);
                int _snowman33 = this.getAmbientOcclusionBrightness(_snowman12, _snowman9, _snowman25, n);
                int _snowman34 = this.getAmbientOcclusionBrightness(_snowman15, _snowman9, _snowman27, n);
                this.light[((Translation)_snowman30).firstCorner] = this.getBrightness(_snowman31, _snowman32, _snowman33, _snowman34, _snowman, _snowman, _snowman, _snowman);
                this.light[((Translation)_snowman30).secondCorner] = this.getBrightness(_snowman31, _snowman32, _snowman33, _snowman34, _snowman, _snowman, _snowman, _snowman);
                this.light[((Translation)_snowman30).thirdCorner] = this.getBrightness(_snowman31, _snowman32, _snowman33, _snowman34, _snowman, _snowman, _snowman, _snowman);
                this.light[((Translation)_snowman30).fourthCorner] = this.getBrightness(_snowman31, _snowman32, _snowman33, _snowman34, _snowman, _snowman, _snowman, _snowman);
            }
            f = world.getBrightness(direction, bl);
            int n2 = 0;
            while (n2 < this.brightness.length) {
                int n3 = n2++;
                this.brightness[n3] = this.brightness[n3] * f;
            }
        }

        private int getAmbientOcclusionBrightness(int n, int n2, int n3, int n4) {
            if (n == 0) {
                n = n4;
            }
            if (n2 == 0) {
                n2 = n4;
            }
            if (n3 == 0) {
                n3 = n4;
            }
            return n + n2 + n3 + n4 >> 2 & 0xFF00FF;
        }

        private int getBrightness(int n, int n2, int n3, int n4, float f, float f2, float f3, float f4) {
            int n5 = (int)((float)(n >> 16 & 0xFF) * f + (float)(n2 >> 16 & 0xFF) * f2 + (float)(n3 >> 16 & 0xFF) * f3 + (float)(n4 >> 16 & 0xFF) * f4) & 0xFF;
            _snowman = (int)((float)(n & 0xFF) * f + (float)(n2 & 0xFF) * f2 + (float)(n3 & 0xFF) * f3 + (float)(n4 & 0xFF) * f4) & 0xFF;
            return n5 << 16 | _snowman;
        }
    }

    static class BrightnessCache {
        private boolean enabled;
        private final Long2IntLinkedOpenHashMap intCache = Util.make(() -> {
            Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap = new Long2IntLinkedOpenHashMap(this, 100, 0.25f){
                final /* synthetic */ BrightnessCache field_19323;
                {
                    this.field_19323 = brightnessCache;
                    super(n, f);
                }

                protected void rehash(int n) {
                }
            };
            long2IntLinkedOpenHashMap.defaultReturnValue(Integer.MAX_VALUE);
            return long2IntLinkedOpenHashMap;
        });
        private final Long2FloatLinkedOpenHashMap floatCache = Util.make(() -> {
            Long2FloatLinkedOpenHashMap long2FloatLinkedOpenHashMap = new Long2FloatLinkedOpenHashMap(this, 100, 0.25f){
                final /* synthetic */ BrightnessCache field_19324;
                {
                    this.field_19324 = brightnessCache;
                    super(n, f);
                }

                protected void rehash(int n) {
                }
            };
            long2FloatLinkedOpenHashMap.defaultReturnValue(Float.NaN);
            return long2FloatLinkedOpenHashMap;
        });

        private BrightnessCache() {
        }

        public void enable() {
            this.enabled = true;
        }

        public void disable() {
            this.enabled = false;
            this.intCache.clear();
            this.floatCache.clear();
        }

        public int getInt(BlockState state, BlockRenderView blockRenderView, BlockPos pos) {
            long l = pos.asLong();
            if (this.enabled && (_snowman2 = this.intCache.get(l)) != Integer.MAX_VALUE) {
                return _snowman2;
            }
            int _snowman2 = WorldRenderer.getLightmapCoordinates(blockRenderView, state, pos);
            if (this.enabled) {
                if (this.intCache.size() == 100) {
                    this.intCache.removeFirstInt();
                }
                this.intCache.put(l, _snowman2);
            }
            return _snowman2;
        }

        public float getFloat(BlockState state, BlockRenderView blockView, BlockPos pos) {
            long l = pos.asLong();
            if (this.enabled && !Float.isNaN(_snowman2 = this.floatCache.get(l))) {
                return _snowman2;
            }
            float _snowman2 = state.getAmbientOcclusionLightLevel(blockView, pos);
            if (this.enabled) {
                if (this.floatCache.size() == 100) {
                    this.floatCache.removeFirstFloat();
                }
                this.floatCache.put(l, _snowman2);
            }
            return _snowman2;
        }
    }

    static enum Translation {
        DOWN(0, 1, 2, 3),
        UP(2, 3, 0, 1),
        NORTH(3, 0, 1, 2),
        SOUTH(0, 1, 2, 3),
        WEST(3, 0, 1, 2),
        EAST(1, 2, 3, 0);

        private final int firstCorner;
        private final int secondCorner;
        private final int thirdCorner;
        private final int fourthCorner;
        private static final Translation[] VALUES;

        private Translation(int firstCorner, int secondCorner, int thirdCorner, int fourthCorner) {
            this.firstCorner = firstCorner;
            this.secondCorner = secondCorner;
            this.thirdCorner = thirdCorner;
            this.fourthCorner = fourthCorner;
        }

        public static Translation getTranslations(Direction direction) {
            return VALUES[direction.getId()];
        }

        static {
            VALUES = Util.make(new Translation[6], translationArray -> {
                translationArray[Direction.DOWN.getId()] = DOWN;
                translationArray[Direction.UP.getId()] = UP;
                translationArray[Direction.NORTH.getId()] = NORTH;
                translationArray[Direction.SOUTH.getId()] = SOUTH;
                translationArray[Direction.WEST.getId()] = WEST;
                translationArray[Direction.EAST.getId()] = EAST;
            });
        }
    }
}

