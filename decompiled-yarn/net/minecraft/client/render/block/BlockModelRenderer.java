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
   private static final ThreadLocal<BlockModelRenderer.BrightnessCache> brightnessCache = ThreadLocal.withInitial(
      () -> new BlockModelRenderer.BrightnessCache()
   );

   public BlockModelRenderer(BlockColors colorMap) {
      this.colorMap = colorMap;
   }

   public boolean render(
      BlockRenderView world,
      BakedModel model,
      BlockState state,
      BlockPos pos,
      MatrixStack matrix,
      VertexConsumer vertexConsumer,
      boolean cull,
      Random random,
      long seed,
      int overlay
   ) {
      boolean _snowman = MinecraftClient.isAmbientOcclusionEnabled() && state.getLuminance() == 0 && model.useAmbientOcclusion();
      Vec3d _snowmanx = state.getModelOffset(world, pos);
      matrix.translate(_snowmanx.x, _snowmanx.y, _snowmanx.z);

      try {
         return _snowman
            ? this.renderSmooth(world, model, state, pos, matrix, vertexConsumer, cull, random, seed, overlay)
            : this.renderFlat(world, model, state, pos, matrix, vertexConsumer, cull, random, seed, overlay);
      } catch (Throwable var17) {
         CrashReport _snowmanxx = CrashReport.create(var17, "Tesselating block model");
         CrashReportSection _snowmanxxx = _snowmanxx.addElement("Block model being tesselated");
         CrashReportSection.addBlockInfo(_snowmanxxx, pos, state);
         _snowmanxxx.add("Using AO", _snowman);
         throw new CrashException(_snowmanxx);
      }
   }

   public boolean renderSmooth(
      BlockRenderView world,
      BakedModel model,
      BlockState state,
      BlockPos pos,
      MatrixStack buffer,
      VertexConsumer vertexConsumer,
      boolean cull,
      Random random,
      long seed,
      int overlay
   ) {
      boolean _snowman = false;
      float[] _snowmanx = new float[Direction.values().length * 2];
      BitSet _snowmanxx = new BitSet(3);
      BlockModelRenderer.AmbientOcclusionCalculator _snowmanxxx = new BlockModelRenderer.AmbientOcclusionCalculator();

      for (Direction _snowmanxxxx : Direction.values()) {
         random.setSeed(seed);
         List<BakedQuad> _snowmanxxxxx = model.getQuads(state, _snowmanxxxx, random);
         if (!_snowmanxxxxx.isEmpty() && (!cull || Block.shouldDrawSide(state, world, pos, _snowmanxxxx))) {
            this.renderQuadsSmooth(world, state, pos, buffer, vertexConsumer, _snowmanxxxxx, _snowmanx, _snowmanxx, _snowmanxxx, overlay);
            _snowman = true;
         }
      }

      random.setSeed(seed);
      List<BakedQuad> _snowmanxxxxx = model.getQuads(state, null, random);
      if (!_snowmanxxxxx.isEmpty()) {
         this.renderQuadsSmooth(world, state, pos, buffer, vertexConsumer, _snowmanxxxxx, _snowmanx, _snowmanxx, _snowmanxxx, overlay);
         _snowman = true;
      }

      return _snowman;
   }

   public boolean renderFlat(
      BlockRenderView world,
      BakedModel model,
      BlockState state,
      BlockPos pos,
      MatrixStack buffer,
      VertexConsumer vertexConsumer,
      boolean cull,
      Random random,
      long _snowman,
      int _snowman
   ) {
      boolean _snowmanxx = false;
      BitSet _snowmanxxx = new BitSet(3);

      for (Direction _snowmanxxxx : Direction.values()) {
         random.setSeed(_snowman);
         List<BakedQuad> _snowmanxxxxx = model.getQuads(state, _snowmanxxxx, random);
         if (!_snowmanxxxxx.isEmpty() && (!cull || Block.shouldDrawSide(state, world, pos, _snowmanxxxx))) {
            int _snowmanxxxxxx = WorldRenderer.getLightmapCoordinates(world, state, pos.offset(_snowmanxxxx));
            this.renderQuadsFlat(world, state, pos, _snowmanxxxxxx, _snowman, false, buffer, vertexConsumer, _snowmanxxxxx, _snowmanxxx);
            _snowmanxx = true;
         }
      }

      random.setSeed(_snowman);
      List<BakedQuad> _snowmanxxxxx = model.getQuads(state, null, random);
      if (!_snowmanxxxxx.isEmpty()) {
         this.renderQuadsFlat(world, state, pos, -1, _snowman, true, buffer, vertexConsumer, _snowmanxxxxx, _snowmanxxx);
         _snowmanxx = true;
      }

      return _snowmanxx;
   }

   private void renderQuadsSmooth(
      BlockRenderView world,
      BlockState state,
      BlockPos pos,
      MatrixStack matrix,
      VertexConsumer vertexConsumer,
      List<BakedQuad> quads,
      float[] box,
      BitSet flags,
      BlockModelRenderer.AmbientOcclusionCalculator ambientOcclusionCalculator,
      int overlay
   ) {
      for (BakedQuad _snowman : quads) {
         this.getQuadDimensions(world, state, pos, _snowman.getVertexData(), _snowman.getFace(), box, flags);
         ambientOcclusionCalculator.apply(world, state, pos, _snowman.getFace(), box, flags, _snowman.hasShade());
         this.renderQuad(
            world,
            state,
            pos,
            vertexConsumer,
            matrix.peek(),
            _snowman,
            ambientOcclusionCalculator.brightness[0],
            ambientOcclusionCalculator.brightness[1],
            ambientOcclusionCalculator.brightness[2],
            ambientOcclusionCalculator.brightness[3],
            ambientOcclusionCalculator.light[0],
            ambientOcclusionCalculator.light[1],
            ambientOcclusionCalculator.light[2],
            ambientOcclusionCalculator.light[3],
            overlay
         );
      }
   }

   private void renderQuad(
      BlockRenderView world,
      BlockState state,
      BlockPos pos,
      VertexConsumer vertexConsumer,
      MatrixStack.Entry matrixEntry,
      BakedQuad quad,
      float brightness0,
      float brightness1,
      float brightness2,
      float brightness3,
      int light0,
      int light1,
      int light2,
      int light3,
      int overlay
   ) {
      float _snowman;
      float _snowmanx;
      float _snowmanxx;
      if (quad.hasColor()) {
         int _snowmanxxx = this.colorMap.getColor(state, world, pos, quad.getColorIndex());
         _snowman = (float)(_snowmanxxx >> 16 & 0xFF) / 255.0F;
         _snowmanx = (float)(_snowmanxxx >> 8 & 0xFF) / 255.0F;
         _snowmanxx = (float)(_snowmanxxx & 0xFF) / 255.0F;
      } else {
         _snowman = 1.0F;
         _snowmanx = 1.0F;
         _snowmanxx = 1.0F;
      }

      vertexConsumer.quad(
         matrixEntry,
         quad,
         new float[]{brightness0, brightness1, brightness2, brightness3},
         _snowman,
         _snowmanx,
         _snowmanxx,
         new int[]{light0, light1, light2, light3},
         overlay,
         true
      );
   }

   private void getQuadDimensions(BlockRenderView world, BlockState state, BlockPos pos, int[] vertexData, Direction face, @Nullable float[] box, BitSet flags) {
      float _snowman = 32.0F;
      float _snowmanx = 32.0F;
      float _snowmanxx = 32.0F;
      float _snowmanxxx = -32.0F;
      float _snowmanxxxx = -32.0F;
      float _snowmanxxxxx = -32.0F;

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 4; _snowmanxxxxxx++) {
         float _snowmanxxxxxxx = Float.intBitsToFloat(vertexData[_snowmanxxxxxx * 8]);
         float _snowmanxxxxxxxx = Float.intBitsToFloat(vertexData[_snowmanxxxxxx * 8 + 1]);
         float _snowmanxxxxxxxxx = Float.intBitsToFloat(vertexData[_snowmanxxxxxx * 8 + 2]);
         _snowman = Math.min(_snowman, _snowmanxxxxxxx);
         _snowmanx = Math.min(_snowmanx, _snowmanxxxxxxxx);
         _snowmanxx = Math.min(_snowmanxx, _snowmanxxxxxxxxx);
         _snowmanxxx = Math.max(_snowmanxxx, _snowmanxxxxxxx);
         _snowmanxxxx = Math.max(_snowmanxxxx, _snowmanxxxxxxxx);
         _snowmanxxxxx = Math.max(_snowmanxxxxx, _snowmanxxxxxxxxx);
      }

      if (box != null) {
         box[Direction.WEST.getId()] = _snowman;
         box[Direction.EAST.getId()] = _snowmanxxx;
         box[Direction.DOWN.getId()] = _snowmanx;
         box[Direction.UP.getId()] = _snowmanxxxx;
         box[Direction.NORTH.getId()] = _snowmanxx;
         box[Direction.SOUTH.getId()] = _snowmanxxxxx;
         int _snowmanxxxxxx = Direction.values().length;
         box[Direction.WEST.getId() + _snowmanxxxxxx] = 1.0F - _snowman;
         box[Direction.EAST.getId() + _snowmanxxxxxx] = 1.0F - _snowmanxxx;
         box[Direction.DOWN.getId() + _snowmanxxxxxx] = 1.0F - _snowmanx;
         box[Direction.UP.getId() + _snowmanxxxxxx] = 1.0F - _snowmanxxxx;
         box[Direction.NORTH.getId() + _snowmanxxxxxx] = 1.0F - _snowmanxx;
         box[Direction.SOUTH.getId() + _snowmanxxxxxx] = 1.0F - _snowmanxxxxx;
      }

      float _snowmanxxxxxx = 1.0E-4F;
      float _snowmanxxxxxxx = 0.9999F;
      switch (face) {
         case DOWN:
            flags.set(1, _snowman >= 1.0E-4F || _snowmanxx >= 1.0E-4F || _snowmanxxx <= 0.9999F || _snowmanxxxxx <= 0.9999F);
            flags.set(0, _snowmanx == _snowmanxxxx && (_snowmanx < 1.0E-4F || state.isFullCube(world, pos)));
            break;
         case UP:
            flags.set(1, _snowman >= 1.0E-4F || _snowmanxx >= 1.0E-4F || _snowmanxxx <= 0.9999F || _snowmanxxxxx <= 0.9999F);
            flags.set(0, _snowmanx == _snowmanxxxx && (_snowmanxxxx > 0.9999F || state.isFullCube(world, pos)));
            break;
         case NORTH:
            flags.set(1, _snowman >= 1.0E-4F || _snowmanx >= 1.0E-4F || _snowmanxxx <= 0.9999F || _snowmanxxxx <= 0.9999F);
            flags.set(0, _snowmanxx == _snowmanxxxxx && (_snowmanxx < 1.0E-4F || state.isFullCube(world, pos)));
            break;
         case SOUTH:
            flags.set(1, _snowman >= 1.0E-4F || _snowmanx >= 1.0E-4F || _snowmanxxx <= 0.9999F || _snowmanxxxx <= 0.9999F);
            flags.set(0, _snowmanxx == _snowmanxxxxx && (_snowmanxxxxx > 0.9999F || state.isFullCube(world, pos)));
            break;
         case WEST:
            flags.set(1, _snowmanx >= 1.0E-4F || _snowmanxx >= 1.0E-4F || _snowmanxxxx <= 0.9999F || _snowmanxxxxx <= 0.9999F);
            flags.set(0, _snowman == _snowmanxxx && (_snowman < 1.0E-4F || state.isFullCube(world, pos)));
            break;
         case EAST:
            flags.set(1, _snowmanx >= 1.0E-4F || _snowmanxx >= 1.0E-4F || _snowmanxxxx <= 0.9999F || _snowmanxxxxx <= 0.9999F);
            flags.set(0, _snowman == _snowmanxxx && (_snowmanxxx > 0.9999F || state.isFullCube(world, pos)));
      }
   }

   private void renderQuadsFlat(
      BlockRenderView world,
      BlockState state,
      BlockPos pos,
      int light,
      int overlay,
      boolean useWorldLight,
      MatrixStack matrices,
      VertexConsumer _snowman,
      List<BakedQuad> quads,
      BitSet flags
   ) {
      for (BakedQuad _snowmanx : quads) {
         if (useWorldLight) {
            this.getQuadDimensions(world, state, pos, _snowmanx.getVertexData(), _snowmanx.getFace(), null, flags);
            BlockPos _snowmanxx = flags.get(0) ? pos.offset(_snowmanx.getFace()) : pos;
            light = WorldRenderer.getLightmapCoordinates(world, state, _snowmanxx);
         }

         float _snowmanxx = world.getBrightness(_snowmanx.getFace(), _snowmanx.hasShade());
         this.renderQuad(world, state, pos, _snowman, matrices.peek(), _snowmanx, _snowmanxx, _snowmanxx, _snowmanxx, _snowmanxx, light, light, light, light, overlay);
      }
   }

   public void render(MatrixStack.Entry _snowman, VertexConsumer _snowman, @Nullable BlockState _snowman, BakedModel _snowman, float _snowman, float _snowman, float _snowman, int _snowman, int _snowman) {
      Random _snowmanxxxxxxxxx = new Random();
      long _snowmanxxxxxxxxxx = 42L;

      for (Direction _snowmanxxxxxxxxxxx : Direction.values()) {
         _snowmanxxxxxxxxx.setSeed(42L);
         renderQuad(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman.getQuads(_snowman, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx), _snowman, _snowman);
      }

      _snowmanxxxxxxxxx.setSeed(42L);
      renderQuad(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman.getQuads(_snowman, null, _snowmanxxxxxxxxx), _snowman, _snowman);
   }

   private static void renderQuad(MatrixStack.Entry _snowman, VertexConsumer _snowman, float _snowman, float _snowman, float _snowman, List<BakedQuad> _snowman, int _snowman, int _snowman) {
      for (BakedQuad _snowmanxxxxxxxx : _snowman) {
         float _snowmanxxxxxxxxx;
         float _snowmanxxxxxxxxxx;
         float _snowmanxxxxxxxxxxx;
         if (_snowmanxxxxxxxx.hasColor()) {
            _snowmanxxxxxxxxx = MathHelper.clamp(_snowman, 0.0F, 1.0F);
            _snowmanxxxxxxxxxx = MathHelper.clamp(_snowman, 0.0F, 1.0F);
            _snowmanxxxxxxxxxxx = MathHelper.clamp(_snowman, 0.0F, 1.0F);
         } else {
            _snowmanxxxxxxxxx = 1.0F;
            _snowmanxxxxxxxxxx = 1.0F;
            _snowmanxxxxxxxxxxx = 1.0F;
         }

         _snowman.quad(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowman, _snowman);
      }
   }

   public static void enableBrightnessCache() {
      brightnessCache.get().enable();
   }

   public static void disableBrightnessCache() {
      brightnessCache.get().disable();
   }

   class AmbientOcclusionCalculator {
      private final float[] brightness = new float[4];
      private final int[] light = new int[4];

      public AmbientOcclusionCalculator() {
      }

      public void apply(BlockRenderView world, BlockState state, BlockPos pos, Direction direction, float[] box, BitSet flags, boolean _snowman) {
         BlockPos _snowmanx = flags.get(0) ? pos.offset(direction) : pos;
         BlockModelRenderer.NeighborData _snowmanxx = BlockModelRenderer.NeighborData.getData(direction);
         BlockPos.Mutable _snowmanxxx = new BlockPos.Mutable();
         BlockModelRenderer.BrightnessCache _snowmanxxxx = BlockModelRenderer.brightnessCache.get();
         _snowmanxxx.set(_snowmanx, _snowmanxx.faces[0]);
         BlockState _snowmanxxxxx = world.getBlockState(_snowmanxxx);
         int _snowmanxxxxxx = _snowmanxxxx.getInt(_snowmanxxxxx, world, _snowmanxxx);
         float _snowmanxxxxxxx = _snowmanxxxx.getFloat(_snowmanxxxxx, world, _snowmanxxx);
         _snowmanxxx.set(_snowmanx, _snowmanxx.faces[1]);
         BlockState _snowmanxxxxxxxx = world.getBlockState(_snowmanxxx);
         int _snowmanxxxxxxxxx = _snowmanxxxx.getInt(_snowmanxxxxxxxx, world, _snowmanxxx);
         float _snowmanxxxxxxxxxx = _snowmanxxxx.getFloat(_snowmanxxxxxxxx, world, _snowmanxxx);
         _snowmanxxx.set(_snowmanx, _snowmanxx.faces[2]);
         BlockState _snowmanxxxxxxxxxxx = world.getBlockState(_snowmanxxx);
         int _snowmanxxxxxxxxxxxx = _snowmanxxxx.getInt(_snowmanxxxxxxxxxxx, world, _snowmanxxx);
         float _snowmanxxxxxxxxxxxxx = _snowmanxxxx.getFloat(_snowmanxxxxxxxxxxx, world, _snowmanxxx);
         _snowmanxxx.set(_snowmanx, _snowmanxx.faces[3]);
         BlockState _snowmanxxxxxxxxxxxxxx = world.getBlockState(_snowmanxxx);
         int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxx.getInt(_snowmanxxxxxxxxxxxxxx, world, _snowmanxxx);
         float _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxx.getFloat(_snowmanxxxxxxxxxxxxxx, world, _snowmanxxx);
         _snowmanxxx.set(_snowmanx, _snowmanxx.faces[0]).move(direction);
         boolean _snowmanxxxxxxxxxxxxxxxxx = world.getBlockState(_snowmanxxx).getOpacity(world, _snowmanxxx) == 0;
         _snowmanxxx.set(_snowmanx, _snowmanxx.faces[1]).move(direction);
         boolean _snowmanxxxxxxxxxxxxxxxxxx = world.getBlockState(_snowmanxxx).getOpacity(world, _snowmanxxx) == 0;
         _snowmanxxx.set(_snowmanx, _snowmanxx.faces[2]).move(direction);
         boolean _snowmanxxxxxxxxxxxxxxxxxxx = world.getBlockState(_snowmanxxx).getOpacity(world, _snowmanxxx) == 0;
         _snowmanxxx.set(_snowmanx, _snowmanxx.faces[3]).move(direction);
         boolean _snowmanxxxxxxxxxxxxxxxxxxxx = world.getBlockState(_snowmanxxx).getOpacity(world, _snowmanxxx) == 0;
         float _snowmanxxxxxxxxxxxxxxxxxxxxx;
         int _snowmanxxxxxxxxxxxxxxxxxxxxxx;
         if (!_snowmanxxxxxxxxxxxxxxxxxxx && !_snowmanxxxxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx;
            _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx;
         } else {
            _snowmanxxx.set(_snowmanx, _snowmanxx.faces[0]).move(_snowmanxx.faces[2]);
            BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxx = world.getBlockState(_snowmanxxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxx.getFloat(_snowmanxxxxxxxxxxxxxxxxxxxxxxx, world, _snowmanxxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxx.getInt(_snowmanxxxxxxxxxxxxxxxxxxxxxxx, world, _snowmanxxx);
         }

         float _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
         if (!_snowmanxxxxxxxxxxxxxxxxxxxx && !_snowmanxxxxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx;
         } else {
            _snowmanxxx.set(_snowmanx, _snowmanxx.faces[0]).move(_snowmanxx.faces[3]);
            BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = world.getBlockState(_snowmanxxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxx.getFloat(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, world, _snowmanxxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxx.getInt(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, world, _snowmanxxx);
         }

         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx;
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx;
         if (!_snowmanxxxxxxxxxxxxxxxxxxx && !_snowmanxxxxxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx;
         } else {
            _snowmanxxx.set(_snowmanx, _snowmanxx.faces[1]).move(_snowmanxx.faces[2]);
            BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = world.getBlockState(_snowmanxxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxx.getFloat(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, world, _snowmanxxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxx.getInt(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, world, _snowmanxxx);
         }

         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx;
         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
         if (!_snowmanxxxxxxxxxxxxxxxxxxxx && !_snowmanxxxxxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx;
         } else {
            _snowmanxxx.set(_snowmanx, _snowmanxx.faces[1]).move(_snowmanxx.faces[3]);
            BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = world.getBlockState(_snowmanxxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxx.getFloat(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, world, _snowmanxxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxx.getInt(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, world, _snowmanxxx);
         }

         int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxx.getInt(state, world, pos);
         _snowmanxxx.set(pos, direction);
         BlockState _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = world.getBlockState(_snowmanxxx);
         if (flags.get(0) || !_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.isOpaqueFullCube(world, _snowmanxxx)) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxx.getInt(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, world, _snowmanxxx);
         }

         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = flags.get(0)
            ? _snowmanxxxx.getFloat(world.getBlockState(_snowmanx), world, _snowmanx)
            : _snowmanxxxx.getFloat(world.getBlockState(pos), world, pos);
         BlockModelRenderer.Translation _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = BlockModelRenderer.Translation.getTranslations(direction);
         if (flags.get(1) && _snowmanxx.nonCubicWeight) {
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxx + _snowmanxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.25F;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxx + _snowmanxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.25F;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxx + _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.25F;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
               * 0.25F;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = box[_snowmanxx.field_4192[0].shape] * box[_snowmanxx.field_4192[1].shape];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = box[_snowmanxx.field_4192[2].shape] * box[_snowmanxx.field_4192[3].shape];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = box[_snowmanxx.field_4192[4].shape] * box[_snowmanxx.field_4192[5].shape];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = box[_snowmanxx.field_4192[6].shape] * box[_snowmanxx.field_4192[7].shape];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = box[_snowmanxx.field_4185[0].shape] * box[_snowmanxx.field_4185[1].shape];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = box[_snowmanxx.field_4185[2].shape] * box[_snowmanxx.field_4185[3].shape];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = box[_snowmanxx.field_4185[4].shape] * box[_snowmanxx.field_4185[5].shape];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = box[_snowmanxx.field_4185[6].shape] * box[_snowmanxx.field_4185[7].shape];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = box[_snowmanxx.field_4180[0].shape] * box[_snowmanxx.field_4180[1].shape];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = box[_snowmanxx.field_4180[2].shape] * box[_snowmanxx.field_4180[3].shape];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = box[_snowmanxx.field_4180[4].shape] * box[_snowmanxx.field_4180[5].shape];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = box[_snowmanxx.field_4180[6].shape] * box[_snowmanxx.field_4180[7].shape];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = box[_snowmanxx.field_4188[0].shape] * box[_snowmanxx.field_4188[1].shape];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = box[_snowmanxx.field_4188[2].shape] * box[_snowmanxx.field_4188[3].shape];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = box[_snowmanxx.field_4188[4].shape] * box[_snowmanxx.field_4188[5].shape];
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = box[_snowmanxx.field_4188[6].shape] * box[_snowmanxx.field_4188[7].shape];
            this.brightness[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.firstCorner] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            this.brightness[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.secondCorner] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            this.brightness[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.thirdCorner] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                  * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            this.brightness[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.fourthCorner] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                  * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
               + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getAmbientOcclusionBrightness(
               _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getAmbientOcclusionBrightness(
               _snowmanxxxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getAmbientOcclusionBrightness(
               _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.getAmbientOcclusionBrightness(
               _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.light[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.firstCorner] = this.getBrightness(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.light[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.secondCorner] = this.getBrightness(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.light[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.thirdCorner] = this.getBrightness(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.light[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.fourthCorner] = this.getBrightness(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
         } else {
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxx + _snowmanxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.25F;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxx + _snowmanxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.25F;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxx + _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.25F;
            float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
               * 0.25F;
            this.light[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.firstCorner] = this.getAmbientOcclusionBrightness(
               _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.light[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.secondCorner] = this.getAmbientOcclusionBrightness(
               _snowmanxxxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.light[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.thirdCorner] = this.getAmbientOcclusionBrightness(
               _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.light[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.fourthCorner] = this.getAmbientOcclusionBrightness(
               _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
            );
            this.brightness[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.firstCorner] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            this.brightness[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.secondCorner] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            this.brightness[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.thirdCorner] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
            this.brightness[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.fourthCorner] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
         }

         float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = world.getBrightness(direction, _snowman);

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < this.brightness.length; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
            this.brightness[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] = this.brightness[_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx] * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
         }
      }

      private int getAmbientOcclusionBrightness(int _snowman, int _snowman, int _snowman, int _snowman) {
         if (_snowman == 0) {
            _snowman = _snowman;
         }

         if (_snowman == 0) {
            _snowman = _snowman;
         }

         if (_snowman == 0) {
            _snowman = _snowman;
         }

         return _snowman + _snowman + _snowman + _snowman >> 2 & 16711935;
      }

      private int getBrightness(int _snowman, int _snowman, int _snowman, int _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
         int _snowmanxxxxxxxx = (int)((float)(_snowman >> 16 & 0xFF) * _snowman + (float)(_snowman >> 16 & 0xFF) * _snowman + (float)(_snowman >> 16 & 0xFF) * _snowman + (float)(_snowman >> 16 & 0xFF) * _snowman) & 0xFF;
         int _snowmanxxxxxxxxx = (int)((float)(_snowman & 0xFF) * _snowman + (float)(_snowman & 0xFF) * _snowman + (float)(_snowman & 0xFF) * _snowman + (float)(_snowman & 0xFF) * _snowman) & 0xFF;
         return _snowmanxxxxxxxx << 16 | _snowmanxxxxxxxxx;
      }
   }

   static class BrightnessCache {
      private boolean enabled;
      private final Long2IntLinkedOpenHashMap intCache = Util.make(() -> {
         Long2IntLinkedOpenHashMap _snowman = new Long2IntLinkedOpenHashMap(100, 0.25F) {
            protected void rehash(int _snowman) {
            }
         };
         _snowman.defaultReturnValue(Integer.MAX_VALUE);
         return _snowman;
      });
      private final Long2FloatLinkedOpenHashMap floatCache = Util.make(() -> {
         Long2FloatLinkedOpenHashMap _snowman = new Long2FloatLinkedOpenHashMap(100, 0.25F) {
            protected void rehash(int _snowman) {
            }
         };
         _snowman.defaultReturnValue(Float.NaN);
         return _snowman;
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

      public int getInt(BlockState state, BlockRenderView _snowman, BlockPos pos) {
         long _snowmanx = pos.asLong();
         if (this.enabled) {
            int _snowmanxx = this.intCache.get(_snowmanx);
            if (_snowmanxx != Integer.MAX_VALUE) {
               return _snowmanxx;
            }
         }

         int _snowmanxx = WorldRenderer.getLightmapCoordinates(_snowman, state, pos);
         if (this.enabled) {
            if (this.intCache.size() == 100) {
               this.intCache.removeFirstInt();
            }

            this.intCache.put(_snowmanx, _snowmanxx);
         }

         return _snowmanxx;
      }

      public float getFloat(BlockState state, BlockRenderView blockView, BlockPos pos) {
         long _snowman = pos.asLong();
         if (this.enabled) {
            float _snowmanx = this.floatCache.get(_snowman);
            if (!Float.isNaN(_snowmanx)) {
               return _snowmanx;
            }
         }

         float _snowmanx = state.getAmbientOcclusionLightLevel(blockView, pos);
         if (this.enabled) {
            if (this.floatCache.size() == 100) {
               this.floatCache.removeFirstFloat();
            }

            this.floatCache.put(_snowman, _snowmanx);
         }

         return _snowmanx;
      }
   }

   public static enum NeighborData {
      DOWN(
         new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH},
         0.5F,
         true,
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.FLIP_WEST,
            BlockModelRenderer.NeighborOrientation.SOUTH,
            BlockModelRenderer.NeighborOrientation.FLIP_WEST,
            BlockModelRenderer.NeighborOrientation.FLIP_SOUTH,
            BlockModelRenderer.NeighborOrientation.WEST,
            BlockModelRenderer.NeighborOrientation.FLIP_SOUTH,
            BlockModelRenderer.NeighborOrientation.WEST,
            BlockModelRenderer.NeighborOrientation.SOUTH
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.FLIP_WEST,
            BlockModelRenderer.NeighborOrientation.NORTH,
            BlockModelRenderer.NeighborOrientation.FLIP_WEST,
            BlockModelRenderer.NeighborOrientation.FLIP_NORTH,
            BlockModelRenderer.NeighborOrientation.WEST,
            BlockModelRenderer.NeighborOrientation.FLIP_NORTH,
            BlockModelRenderer.NeighborOrientation.WEST,
            BlockModelRenderer.NeighborOrientation.NORTH
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.FLIP_EAST,
            BlockModelRenderer.NeighborOrientation.NORTH,
            BlockModelRenderer.NeighborOrientation.FLIP_EAST,
            BlockModelRenderer.NeighborOrientation.FLIP_NORTH,
            BlockModelRenderer.NeighborOrientation.EAST,
            BlockModelRenderer.NeighborOrientation.FLIP_NORTH,
            BlockModelRenderer.NeighborOrientation.EAST,
            BlockModelRenderer.NeighborOrientation.NORTH
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.FLIP_EAST,
            BlockModelRenderer.NeighborOrientation.SOUTH,
            BlockModelRenderer.NeighborOrientation.FLIP_EAST,
            BlockModelRenderer.NeighborOrientation.FLIP_SOUTH,
            BlockModelRenderer.NeighborOrientation.EAST,
            BlockModelRenderer.NeighborOrientation.FLIP_SOUTH,
            BlockModelRenderer.NeighborOrientation.EAST,
            BlockModelRenderer.NeighborOrientation.SOUTH
         }
      ),
      UP(
         new Direction[]{Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH},
         1.0F,
         true,
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.EAST,
            BlockModelRenderer.NeighborOrientation.SOUTH,
            BlockModelRenderer.NeighborOrientation.EAST,
            BlockModelRenderer.NeighborOrientation.FLIP_SOUTH,
            BlockModelRenderer.NeighborOrientation.FLIP_EAST,
            BlockModelRenderer.NeighborOrientation.FLIP_SOUTH,
            BlockModelRenderer.NeighborOrientation.FLIP_EAST,
            BlockModelRenderer.NeighborOrientation.SOUTH
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.EAST,
            BlockModelRenderer.NeighborOrientation.NORTH,
            BlockModelRenderer.NeighborOrientation.EAST,
            BlockModelRenderer.NeighborOrientation.FLIP_NORTH,
            BlockModelRenderer.NeighborOrientation.FLIP_EAST,
            BlockModelRenderer.NeighborOrientation.FLIP_NORTH,
            BlockModelRenderer.NeighborOrientation.FLIP_EAST,
            BlockModelRenderer.NeighborOrientation.NORTH
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.WEST,
            BlockModelRenderer.NeighborOrientation.NORTH,
            BlockModelRenderer.NeighborOrientation.WEST,
            BlockModelRenderer.NeighborOrientation.FLIP_NORTH,
            BlockModelRenderer.NeighborOrientation.FLIP_WEST,
            BlockModelRenderer.NeighborOrientation.FLIP_NORTH,
            BlockModelRenderer.NeighborOrientation.FLIP_WEST,
            BlockModelRenderer.NeighborOrientation.NORTH
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.WEST,
            BlockModelRenderer.NeighborOrientation.SOUTH,
            BlockModelRenderer.NeighborOrientation.WEST,
            BlockModelRenderer.NeighborOrientation.FLIP_SOUTH,
            BlockModelRenderer.NeighborOrientation.FLIP_WEST,
            BlockModelRenderer.NeighborOrientation.FLIP_SOUTH,
            BlockModelRenderer.NeighborOrientation.FLIP_WEST,
            BlockModelRenderer.NeighborOrientation.SOUTH
         }
      ),
      NORTH(
         new Direction[]{Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST},
         0.8F,
         true,
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.UP,
            BlockModelRenderer.NeighborOrientation.FLIP_WEST,
            BlockModelRenderer.NeighborOrientation.UP,
            BlockModelRenderer.NeighborOrientation.WEST,
            BlockModelRenderer.NeighborOrientation.FLIP_UP,
            BlockModelRenderer.NeighborOrientation.WEST,
            BlockModelRenderer.NeighborOrientation.FLIP_UP,
            BlockModelRenderer.NeighborOrientation.FLIP_WEST
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.UP,
            BlockModelRenderer.NeighborOrientation.FLIP_EAST,
            BlockModelRenderer.NeighborOrientation.UP,
            BlockModelRenderer.NeighborOrientation.EAST,
            BlockModelRenderer.NeighborOrientation.FLIP_UP,
            BlockModelRenderer.NeighborOrientation.EAST,
            BlockModelRenderer.NeighborOrientation.FLIP_UP,
            BlockModelRenderer.NeighborOrientation.FLIP_EAST
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.DOWN,
            BlockModelRenderer.NeighborOrientation.FLIP_EAST,
            BlockModelRenderer.NeighborOrientation.DOWN,
            BlockModelRenderer.NeighborOrientation.EAST,
            BlockModelRenderer.NeighborOrientation.FLIP_DOWN,
            BlockModelRenderer.NeighborOrientation.EAST,
            BlockModelRenderer.NeighborOrientation.FLIP_DOWN,
            BlockModelRenderer.NeighborOrientation.FLIP_EAST
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.DOWN,
            BlockModelRenderer.NeighborOrientation.FLIP_WEST,
            BlockModelRenderer.NeighborOrientation.DOWN,
            BlockModelRenderer.NeighborOrientation.WEST,
            BlockModelRenderer.NeighborOrientation.FLIP_DOWN,
            BlockModelRenderer.NeighborOrientation.WEST,
            BlockModelRenderer.NeighborOrientation.FLIP_DOWN,
            BlockModelRenderer.NeighborOrientation.FLIP_WEST
         }
      ),
      SOUTH(
         new Direction[]{Direction.WEST, Direction.EAST, Direction.DOWN, Direction.UP},
         0.8F,
         true,
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.UP,
            BlockModelRenderer.NeighborOrientation.FLIP_WEST,
            BlockModelRenderer.NeighborOrientation.FLIP_UP,
            BlockModelRenderer.NeighborOrientation.FLIP_WEST,
            BlockModelRenderer.NeighborOrientation.FLIP_UP,
            BlockModelRenderer.NeighborOrientation.WEST,
            BlockModelRenderer.NeighborOrientation.UP,
            BlockModelRenderer.NeighborOrientation.WEST
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.DOWN,
            BlockModelRenderer.NeighborOrientation.FLIP_WEST,
            BlockModelRenderer.NeighborOrientation.FLIP_DOWN,
            BlockModelRenderer.NeighborOrientation.FLIP_WEST,
            BlockModelRenderer.NeighborOrientation.FLIP_DOWN,
            BlockModelRenderer.NeighborOrientation.WEST,
            BlockModelRenderer.NeighborOrientation.DOWN,
            BlockModelRenderer.NeighborOrientation.WEST
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.DOWN,
            BlockModelRenderer.NeighborOrientation.FLIP_EAST,
            BlockModelRenderer.NeighborOrientation.FLIP_DOWN,
            BlockModelRenderer.NeighborOrientation.FLIP_EAST,
            BlockModelRenderer.NeighborOrientation.FLIP_DOWN,
            BlockModelRenderer.NeighborOrientation.EAST,
            BlockModelRenderer.NeighborOrientation.DOWN,
            BlockModelRenderer.NeighborOrientation.EAST
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.UP,
            BlockModelRenderer.NeighborOrientation.FLIP_EAST,
            BlockModelRenderer.NeighborOrientation.FLIP_UP,
            BlockModelRenderer.NeighborOrientation.FLIP_EAST,
            BlockModelRenderer.NeighborOrientation.FLIP_UP,
            BlockModelRenderer.NeighborOrientation.EAST,
            BlockModelRenderer.NeighborOrientation.UP,
            BlockModelRenderer.NeighborOrientation.EAST
         }
      ),
      WEST(
         new Direction[]{Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH},
         0.6F,
         true,
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.UP,
            BlockModelRenderer.NeighborOrientation.SOUTH,
            BlockModelRenderer.NeighborOrientation.UP,
            BlockModelRenderer.NeighborOrientation.FLIP_SOUTH,
            BlockModelRenderer.NeighborOrientation.FLIP_UP,
            BlockModelRenderer.NeighborOrientation.FLIP_SOUTH,
            BlockModelRenderer.NeighborOrientation.FLIP_UP,
            BlockModelRenderer.NeighborOrientation.SOUTH
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.UP,
            BlockModelRenderer.NeighborOrientation.NORTH,
            BlockModelRenderer.NeighborOrientation.UP,
            BlockModelRenderer.NeighborOrientation.FLIP_NORTH,
            BlockModelRenderer.NeighborOrientation.FLIP_UP,
            BlockModelRenderer.NeighborOrientation.FLIP_NORTH,
            BlockModelRenderer.NeighborOrientation.FLIP_UP,
            BlockModelRenderer.NeighborOrientation.NORTH
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.DOWN,
            BlockModelRenderer.NeighborOrientation.NORTH,
            BlockModelRenderer.NeighborOrientation.DOWN,
            BlockModelRenderer.NeighborOrientation.FLIP_NORTH,
            BlockModelRenderer.NeighborOrientation.FLIP_DOWN,
            BlockModelRenderer.NeighborOrientation.FLIP_NORTH,
            BlockModelRenderer.NeighborOrientation.FLIP_DOWN,
            BlockModelRenderer.NeighborOrientation.NORTH
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.DOWN,
            BlockModelRenderer.NeighborOrientation.SOUTH,
            BlockModelRenderer.NeighborOrientation.DOWN,
            BlockModelRenderer.NeighborOrientation.FLIP_SOUTH,
            BlockModelRenderer.NeighborOrientation.FLIP_DOWN,
            BlockModelRenderer.NeighborOrientation.FLIP_SOUTH,
            BlockModelRenderer.NeighborOrientation.FLIP_DOWN,
            BlockModelRenderer.NeighborOrientation.SOUTH
         }
      ),
      EAST(
         new Direction[]{Direction.DOWN, Direction.UP, Direction.NORTH, Direction.SOUTH},
         0.6F,
         true,
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.FLIP_DOWN,
            BlockModelRenderer.NeighborOrientation.SOUTH,
            BlockModelRenderer.NeighborOrientation.FLIP_DOWN,
            BlockModelRenderer.NeighborOrientation.FLIP_SOUTH,
            BlockModelRenderer.NeighborOrientation.DOWN,
            BlockModelRenderer.NeighborOrientation.FLIP_SOUTH,
            BlockModelRenderer.NeighborOrientation.DOWN,
            BlockModelRenderer.NeighborOrientation.SOUTH
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.FLIP_DOWN,
            BlockModelRenderer.NeighborOrientation.NORTH,
            BlockModelRenderer.NeighborOrientation.FLIP_DOWN,
            BlockModelRenderer.NeighborOrientation.FLIP_NORTH,
            BlockModelRenderer.NeighborOrientation.DOWN,
            BlockModelRenderer.NeighborOrientation.FLIP_NORTH,
            BlockModelRenderer.NeighborOrientation.DOWN,
            BlockModelRenderer.NeighborOrientation.NORTH
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.FLIP_UP,
            BlockModelRenderer.NeighborOrientation.NORTH,
            BlockModelRenderer.NeighborOrientation.FLIP_UP,
            BlockModelRenderer.NeighborOrientation.FLIP_NORTH,
            BlockModelRenderer.NeighborOrientation.UP,
            BlockModelRenderer.NeighborOrientation.FLIP_NORTH,
            BlockModelRenderer.NeighborOrientation.UP,
            BlockModelRenderer.NeighborOrientation.NORTH
         },
         new BlockModelRenderer.NeighborOrientation[]{
            BlockModelRenderer.NeighborOrientation.FLIP_UP,
            BlockModelRenderer.NeighborOrientation.SOUTH,
            BlockModelRenderer.NeighborOrientation.FLIP_UP,
            BlockModelRenderer.NeighborOrientation.FLIP_SOUTH,
            BlockModelRenderer.NeighborOrientation.UP,
            BlockModelRenderer.NeighborOrientation.FLIP_SOUTH,
            BlockModelRenderer.NeighborOrientation.UP,
            BlockModelRenderer.NeighborOrientation.SOUTH
         }
      );

      private final Direction[] faces;
      private final boolean nonCubicWeight;
      private final BlockModelRenderer.NeighborOrientation[] field_4192;
      private final BlockModelRenderer.NeighborOrientation[] field_4185;
      private final BlockModelRenderer.NeighborOrientation[] field_4180;
      private final BlockModelRenderer.NeighborOrientation[] field_4188;
      private static final BlockModelRenderer.NeighborData[] field_4190 = Util.make(new BlockModelRenderer.NeighborData[6], _snowman -> {
         _snowman[Direction.DOWN.getId()] = DOWN;
         _snowman[Direction.UP.getId()] = UP;
         _snowman[Direction.NORTH.getId()] = NORTH;
         _snowman[Direction.SOUTH.getId()] = SOUTH;
         _snowman[Direction.WEST.getId()] = WEST;
         _snowman[Direction.EAST.getId()] = EAST;
      });

      private NeighborData(
         Direction[] var3,
         float var4,
         boolean var5,
         BlockModelRenderer.NeighborOrientation[] var6,
         BlockModelRenderer.NeighborOrientation[] var7,
         BlockModelRenderer.NeighborOrientation[] var8,
         BlockModelRenderer.NeighborOrientation[] var9
      ) {
         this.faces = _snowman;
         this.nonCubicWeight = _snowman;
         this.field_4192 = _snowman;
         this.field_4185 = _snowman;
         this.field_4180 = _snowman;
         this.field_4188 = _snowman;
      }

      public static BlockModelRenderer.NeighborData getData(Direction _snowman) {
         return field_4190[_snowman.getId()];
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

      private NeighborOrientation(Direction var3, boolean var4) {
         this.shape = _snowman.getId() + (_snowman ? Direction.values().length : 0);
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
      private static final BlockModelRenderer.Translation[] VALUES = Util.make(new BlockModelRenderer.Translation[6], _snowman -> {
         _snowman[Direction.DOWN.getId()] = DOWN;
         _snowman[Direction.UP.getId()] = UP;
         _snowman[Direction.NORTH.getId()] = NORTH;
         _snowman[Direction.SOUTH.getId()] = SOUTH;
         _snowman[Direction.WEST.getId()] = WEST;
         _snowman[Direction.EAST.getId()] = EAST;
      });

      private Translation(int firstCorner, int secondCorner, int thirdCorner, int fourthCorner) {
         this.firstCorner = firstCorner;
         this.secondCorner = secondCorner;
         this.thirdCorner = thirdCorner;
         this.fourthCorner = fourthCorner;
      }

      public static BlockModelRenderer.Translation getTranslations(Direction _snowman) {
         return VALUES[_snowman.getId()];
      }
   }
}
