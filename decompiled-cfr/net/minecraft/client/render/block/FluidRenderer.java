/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.render.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;

public class FluidRenderer {
    private final Sprite[] lavaSprites = new Sprite[2];
    private final Sprite[] waterSprites = new Sprite[2];
    private Sprite waterOverlaySprite;

    protected void onResourceReload() {
        this.lavaSprites[0] = MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(Blocks.LAVA.getDefaultState()).getSprite();
        this.lavaSprites[1] = ModelLoader.LAVA_FLOW.getSprite();
        this.waterSprites[0] = MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(Blocks.WATER.getDefaultState()).getSprite();
        this.waterSprites[1] = ModelLoader.WATER_FLOW.getSprite();
        this.waterOverlaySprite = ModelLoader.WATER_OVERLAY.getSprite();
    }

    private static boolean isSameFluid(BlockView world, BlockPos pos, Direction side, FluidState state) {
        BlockPos blockPos = pos.offset(side);
        FluidState _snowman2 = world.getFluidState(blockPos);
        return _snowman2.getFluid().matchesType(state.getFluid());
    }

    private static boolean method_29710(BlockView blockView, Direction direction, float f, BlockPos blockPos, BlockState blockState) {
        if (blockState.isOpaque()) {
            VoxelShape voxelShape = VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, f, 1.0);
            _snowman = blockState.getCullingShape(blockView, blockPos);
            return VoxelShapes.isSideCovered(voxelShape, _snowman, direction);
        }
        return false;
    }

    private static boolean isSideCovered(BlockView world, BlockPos pos, Direction direction, float maxDeviation) {
        BlockPos blockPos = pos.offset(direction);
        BlockState _snowman2 = world.getBlockState(blockPos);
        return FluidRenderer.method_29710(world, direction, maxDeviation, blockPos, _snowman2);
    }

    private static boolean method_29709(BlockView blockView, BlockPos blockPos, BlockState blockState, Direction direction) {
        return FluidRenderer.method_29710(blockView, direction.getOpposite(), 1.0f, blockPos, blockState);
    }

    public static boolean method_29708(BlockRenderView blockRenderView, BlockPos blockPos, FluidState fluidState, BlockState blockState, Direction direction) {
        return !FluidRenderer.method_29709(blockRenderView, blockPos, blockState, direction) && !FluidRenderer.isSameFluid(blockRenderView, blockPos, direction, fluidState);
    }

    public boolean render(BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, FluidState state) {
        boolean _snowman34;
        float _snowman27;
        float _snowman26;
        float _snowman25;
        float _snowman24;
        float _snowman23;
        float _snowman22;
        float _snowman20;
        boolean bl = state.isIn(FluidTags.LAVA);
        Sprite[] _snowman2 = bl ? this.lavaSprites : this.waterSprites;
        BlockState _snowman3 = world.getBlockState(pos);
        int _snowman4 = bl ? 0xFFFFFF : BiomeColors.getWaterColor(world, pos);
        float _snowman5 = (float)(_snowman4 >> 16 & 0xFF) / 255.0f;
        float _snowman6 = (float)(_snowman4 >> 8 & 0xFF) / 255.0f;
        float _snowman7 = (float)(_snowman4 & 0xFF) / 255.0f;
        _snowman = !FluidRenderer.isSameFluid(world, pos, Direction.UP, state);
        _snowman = FluidRenderer.method_29708(world, pos, state, _snowman3, Direction.DOWN) && !FluidRenderer.isSideCovered(world, pos, Direction.DOWN, 0.8888889f);
        _snowman = FluidRenderer.method_29708(world, pos, state, _snowman3, Direction.NORTH);
        _snowman = FluidRenderer.method_29708(world, pos, state, _snowman3, Direction.SOUTH);
        _snowman = FluidRenderer.method_29708(world, pos, state, _snowman3, Direction.WEST);
        _snowman = FluidRenderer.method_29708(world, pos, state, _snowman3, Direction.EAST);
        if (!(_snowman || _snowman || _snowman || _snowman || _snowman || _snowman)) {
            return false;
        }
        _snowman34 = false;
        float _snowman8 = world.getBrightness(Direction.DOWN, true);
        float _snowman9 = world.getBrightness(Direction.UP, true);
        float _snowman10 = world.getBrightness(Direction.NORTH, true);
        float _snowman11 = world.getBrightness(Direction.WEST, true);
        float _snowman12 = this.getNorthWestCornerFluidHeight(world, pos, state.getFluid());
        float _snowman13 = this.getNorthWestCornerFluidHeight(world, pos.south(), state.getFluid());
        float _snowman14 = this.getNorthWestCornerFluidHeight(world, pos.east().south(), state.getFluid());
        float _snowman15 = this.getNorthWestCornerFluidHeight(world, pos.east(), state.getFluid());
        double _snowman16 = pos.getX() & 0xF;
        double _snowman17 = pos.getY() & 0xF;
        double _snowman18 = pos.getZ() & 0xF;
        float _snowman19 = 0.001f;
        float f = _snowman = _snowman ? 0.001f : 0.0f;
        if (_snowman && !FluidRenderer.isSideCovered(world, pos, Direction.UP, Math.min(Math.min(_snowman12, _snowman13), Math.min(_snowman14, _snowman15)))) {
            float _snowman31;
            float _snowman30;
            float _snowman29;
            float _snowman28;
            float _snowman21;
            _snowman34 = true;
            _snowman12 -= 0.001f;
            _snowman13 -= 0.001f;
            _snowman14 -= 0.001f;
            _snowman15 -= 0.001f;
            Vec3d vec3d = state.getVelocity(world, pos);
            if (vec3d.x == 0.0 && vec3d.z == 0.0) {
                Sprite sprite = _snowman2[0];
                _snowman20 = sprite.getFrameU(0.0);
                _snowman21 = sprite.getFrameV(0.0);
                _snowman22 = _snowman20;
                _snowman23 = sprite.getFrameV(16.0);
                _snowman24 = sprite.getFrameU(16.0);
                _snowman25 = _snowman23;
                _snowman26 = _snowman24;
                _snowman27 = _snowman21;
            } else {
                sprite = _snowman2[1];
                _snowman28 = (float)MathHelper.atan2(vec3d.z, vec3d.x) - 1.5707964f;
                _snowman29 = MathHelper.sin(_snowman28) * 0.25f;
                _snowman30 = MathHelper.cos(_snowman28) * 0.25f;
                _snowman31 = 8.0f;
                _snowman20 = sprite.getFrameU(8.0f + (-_snowman30 - _snowman29) * 16.0f);
                _snowman21 = sprite.getFrameV(8.0f + (-_snowman30 + _snowman29) * 16.0f);
                _snowman22 = sprite.getFrameU(8.0f + (-_snowman30 + _snowman29) * 16.0f);
                _snowman23 = sprite.getFrameV(8.0f + (_snowman30 + _snowman29) * 16.0f);
                _snowman24 = sprite.getFrameU(8.0f + (_snowman30 + _snowman29) * 16.0f);
                _snowman25 = sprite.getFrameV(8.0f + (_snowman30 - _snowman29) * 16.0f);
                _snowman26 = sprite.getFrameU(8.0f + (_snowman30 - _snowman29) * 16.0f);
                _snowman27 = sprite.getFrameV(8.0f + (-_snowman30 - _snowman29) * 16.0f);
            }
            float f2 = (_snowman20 + _snowman22 + _snowman24 + _snowman26) / 4.0f;
            _snowman28 = (_snowman21 + _snowman23 + _snowman25 + _snowman27) / 4.0f;
            _snowman29 = (float)_snowman2[0].getWidth() / (_snowman2[0].getMaxU() - _snowman2[0].getMinU());
            _snowman30 = (float)_snowman2[0].getHeight() / (_snowman2[0].getMaxV() - _snowman2[0].getMinV());
            _snowman31 = 4.0f / Math.max(_snowman30, _snowman29);
            _snowman20 = MathHelper.lerp(_snowman31, _snowman20, f2);
            _snowman22 = MathHelper.lerp(_snowman31, _snowman22, f2);
            _snowman24 = MathHelper.lerp(_snowman31, _snowman24, f2);
            _snowman26 = MathHelper.lerp(_snowman31, _snowman26, f2);
            _snowman21 = MathHelper.lerp(_snowman31, _snowman21, _snowman28);
            _snowman23 = MathHelper.lerp(_snowman31, _snowman23, _snowman28);
            _snowman25 = MathHelper.lerp(_snowman31, _snowman25, _snowman28);
            _snowman27 = MathHelper.lerp(_snowman31, _snowman27, _snowman28);
            int _snowman32 = this.getLight(world, pos);
            _snowman39 = _snowman9 * _snowman5;
            _snowman40 = _snowman9 * _snowman6;
            _snowman41 = _snowman9 * _snowman7;
            this.vertex(vertexConsumer, _snowman16 + 0.0, _snowman17 + (double)_snowman12, _snowman18 + 0.0, _snowman39, _snowman40, _snowman41, _snowman20, _snowman21, _snowman32);
            this.vertex(vertexConsumer, _snowman16 + 0.0, _snowman17 + (double)_snowman13, _snowman18 + 1.0, _snowman39, _snowman40, _snowman41, _snowman22, _snowman23, _snowman32);
            this.vertex(vertexConsumer, _snowman16 + 1.0, _snowman17 + (double)_snowman14, _snowman18 + 1.0, _snowman39, _snowman40, _snowman41, _snowman24, _snowman25, _snowman32);
            this.vertex(vertexConsumer, _snowman16 + 1.0, _snowman17 + (double)_snowman15, _snowman18 + 0.0, _snowman39, _snowman40, _snowman41, _snowman26, _snowman27, _snowman32);
            if (state.method_15756(world, pos.up())) {
                this.vertex(vertexConsumer, _snowman16 + 0.0, _snowman17 + (double)_snowman12, _snowman18 + 0.0, _snowman39, _snowman40, _snowman41, _snowman20, _snowman21, _snowman32);
                this.vertex(vertexConsumer, _snowman16 + 1.0, _snowman17 + (double)_snowman15, _snowman18 + 0.0, _snowman39, _snowman40, _snowman41, _snowman26, _snowman27, _snowman32);
                this.vertex(vertexConsumer, _snowman16 + 1.0, _snowman17 + (double)_snowman14, _snowman18 + 1.0, _snowman39, _snowman40, _snowman41, _snowman24, _snowman25, _snowman32);
                this.vertex(vertexConsumer, _snowman16 + 0.0, _snowman17 + (double)_snowman13, _snowman18 + 1.0, _snowman39, _snowman40, _snowman41, _snowman22, _snowman23, _snowman32);
            }
        }
        if (_snowman) {
            _snowman20 = _snowman2[0].getMinU();
            _snowman22 = _snowman2[0].getMaxU();
            _snowman24 = _snowman2[0].getMinV();
            _snowman26 = _snowman2[0].getMaxV();
            int _snowman33 = this.getLight(world, pos.down());
            _snowman23 = _snowman8 * _snowman5;
            _snowman25 = _snowman8 * _snowman6;
            _snowman27 = _snowman8 * _snowman7;
            this.vertex(vertexConsumer, _snowman16, _snowman17 + (double)_snowman, _snowman18 + 1.0, _snowman23, _snowman25, _snowman27, _snowman20, _snowman26, _snowman33);
            this.vertex(vertexConsumer, _snowman16, _snowman17 + (double)_snowman, _snowman18, _snowman23, _snowman25, _snowman27, _snowman20, _snowman24, _snowman33);
            this.vertex(vertexConsumer, _snowman16 + 1.0, _snowman17 + (double)_snowman, _snowman18, _snowman23, _snowman25, _snowman27, _snowman22, _snowman24, _snowman33);
            this.vertex(vertexConsumer, _snowman16 + 1.0, _snowman17 + (double)_snowman, _snowman18 + 1.0, _snowman23, _snowman25, _snowman27, _snowman22, _snowman26, _snowman33);
            _snowman34 = true;
        }
        for (int _snowman35 = 0; _snowman35 < 4; ++_snowman35) {
            boolean _snowman37;
            Direction _snowman36;
            if (_snowman35 == 0) {
                _snowman22 = _snowman12;
                _snowman24 = _snowman15;
                double d = _snowman16;
                _snowman = _snowman16 + 1.0;
                _snowman = _snowman18 + (double)0.001f;
                _snowman = _snowman18 + (double)0.001f;
                _snowman36 = Direction.NORTH;
                _snowman37 = _snowman;
            } else if (_snowman35 == 1) {
                _snowman22 = _snowman14;
                _snowman24 = _snowman13;
                d = _snowman16 + 1.0;
                _snowman = _snowman16;
                _snowman = _snowman18 + 1.0 - (double)0.001f;
                _snowman = _snowman18 + 1.0 - (double)0.001f;
                _snowman36 = Direction.SOUTH;
                _snowman37 = _snowman;
            } else if (_snowman35 == 2) {
                _snowman22 = _snowman13;
                _snowman24 = _snowman12;
                d = _snowman16 + (double)0.001f;
                _snowman = _snowman16 + (double)0.001f;
                _snowman = _snowman18 + 1.0;
                _snowman = _snowman18;
                _snowman36 = Direction.WEST;
                _snowman37 = _snowman;
            } else {
                _snowman22 = _snowman15;
                _snowman24 = _snowman14;
                d = _snowman16 + 1.0 - (double)0.001f;
                _snowman = _snowman16 + 1.0 - (double)0.001f;
                _snowman = _snowman18;
                _snowman = _snowman18 + 1.0;
                _snowman36 = Direction.EAST;
                _snowman37 = _snowman;
            }
            if (!_snowman37 || FluidRenderer.isSideCovered(world, pos, _snowman36, Math.max(_snowman22, _snowman24))) continue;
            _snowman34 = true;
            BlockPos blockPos = pos.offset(_snowman36);
            Sprite _snowman38 = _snowman2[1];
            if (!bl && ((_snowman = world.getBlockState(blockPos).getBlock()) instanceof TransparentBlock || _snowman instanceof LeavesBlock)) {
                _snowman38 = this.waterOverlaySprite;
            }
            float _snowman39 = _snowman38.getFrameU(0.0);
            float _snowman40 = _snowman38.getFrameU(8.0);
            float _snowman41 = _snowman38.getFrameV((1.0f - _snowman22) * 16.0f * 0.5f);
            float _snowman42 = _snowman38.getFrameV((1.0f - _snowman24) * 16.0f * 0.5f);
            float _snowman43 = _snowman38.getFrameV(8.0);
            int _snowman44 = this.getLight(world, blockPos);
            float _snowman45 = _snowman35 < 2 ? _snowman10 : _snowman11;
            float _snowman46 = _snowman9 * _snowman45 * _snowman5;
            float _snowman47 = _snowman9 * _snowman45 * _snowman6;
            float _snowman48 = _snowman9 * _snowman45 * _snowman7;
            this.vertex(vertexConsumer, d, _snowman17 + (double)_snowman22, _snowman, _snowman46, _snowman47, _snowman48, _snowman39, _snowman41, _snowman44);
            this.vertex(vertexConsumer, _snowman, _snowman17 + (double)_snowman24, _snowman, _snowman46, _snowman47, _snowman48, _snowman40, _snowman42, _snowman44);
            this.vertex(vertexConsumer, _snowman, _snowman17 + (double)_snowman, _snowman, _snowman46, _snowman47, _snowman48, _snowman40, _snowman43, _snowman44);
            this.vertex(vertexConsumer, d, _snowman17 + (double)_snowman, _snowman, _snowman46, _snowman47, _snowman48, _snowman39, _snowman43, _snowman44);
            if (_snowman38 == this.waterOverlaySprite) continue;
            this.vertex(vertexConsumer, d, _snowman17 + (double)_snowman, _snowman, _snowman46, _snowman47, _snowman48, _snowman39, _snowman43, _snowman44);
            this.vertex(vertexConsumer, _snowman, _snowman17 + (double)_snowman, _snowman, _snowman46, _snowman47, _snowman48, _snowman40, _snowman43, _snowman44);
            this.vertex(vertexConsumer, _snowman, _snowman17 + (double)_snowman24, _snowman, _snowman46, _snowman47, _snowman48, _snowman40, _snowman42, _snowman44);
            this.vertex(vertexConsumer, d, _snowman17 + (double)_snowman22, _snowman, _snowman46, _snowman47, _snowman48, _snowman39, _snowman41, _snowman44);
        }
        return _snowman34;
    }

    private void vertex(VertexConsumer vertexConsumer, double x, double y, double z, float red, float green, float blue, float u, float v, int light) {
        vertexConsumer.vertex(x, y, z).color(red, green, blue, 1.0f).texture(u, v).light(light).normal(0.0f, 1.0f, 0.0f).next();
    }

    private int getLight(BlockRenderView world, BlockPos pos) {
        int n = WorldRenderer.getLightmapCoordinates(world, pos);
        _snowman = WorldRenderer.getLightmapCoordinates(world, pos.up());
        _snowman = n & 0xFF;
        _snowman = _snowman & 0xFF;
        _snowman = n >> 16 & 0xFF;
        _snowman = _snowman >> 16 & 0xFF;
        return (_snowman > _snowman ? _snowman : _snowman) | (_snowman > _snowman ? _snowman : _snowman) << 16;
    }

    private float getNorthWestCornerFluidHeight(BlockView world, BlockPos pos, Fluid fluid) {
        int n = 0;
        float _snowman2 = 0.0f;
        for (_snowman = 0; _snowman < 4; ++_snowman) {
            BlockPos blockPos = pos.add(-(_snowman & 1), 0, -(_snowman >> 1 & 1));
            if (world.getFluidState(blockPos.up()).getFluid().matchesType(fluid)) {
                return 1.0f;
            }
            FluidState _snowman3 = world.getFluidState(blockPos);
            if (_snowman3.getFluid().matchesType(fluid)) {
                float f = _snowman3.getHeight(world, blockPos);
                if (f >= 0.8f) {
                    _snowman2 += f * 10.0f;
                    n += 10;
                    continue;
                }
                _snowman2 += f;
                ++n;
                continue;
            }
            if (world.getBlockState(blockPos).getMaterial().isSolid()) continue;
            ++n;
        }
        return _snowman2 / (float)n;
    }
}

