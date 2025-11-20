package net.minecraft.client.render.block;

import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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
      boolean bl2 = MinecraftClient.isAmbientOcclusionEnabled() && state.getLuminance() == 0 && model.useAmbientOcclusion();
      Vec3d lv = state.getModelOffset(world, pos);
      matrix.translate(lv.x, lv.y, lv.z);

      try {
         return bl2
            ? this.renderSmooth(world, model, state, pos, matrix, vertexConsumer, cull, random, seed, overlay)
            : this.renderFlat(world, model, state, pos, matrix, vertexConsumer, cull, random, seed, overlay);
      } catch (Throwable var17) {
         CrashReport lv2 = CrashReport.create(var17, "Tesselating block model");
         CrashReportSection lv3 = lv2.addElement("Block model being tesselated");
         CrashReportSection.addBlockInfo(lv3, pos, state);
         lv3.add("Using AO", bl2);
         throw new CrashException(lv2);
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
      boolean bl2 = false;
      float[] fs = new float[Direction.values().length * 2];
      BitSet bitSet = new BitSet(3);
      BlockModelRenderer.AmbientOcclusionCalculator lv = new BlockModelRenderer.AmbientOcclusionCalculator();

      for (Direction lv2 : Direction.values()) {
         random.setSeed(seed);
         List<BakedQuad> list = model.getQuads(state, lv2, random);
         if (!list.isEmpty() && (!cull || Block.shouldDrawSide(state, world, pos, lv2))) {
            this.renderQuadsSmooth(world, state, pos, buffer, vertexConsumer, list, fs, bitSet, lv, overlay);
            bl2 = true;
         }
      }

      random.setSeed(seed);
      List<BakedQuad> list2 = model.getQuads(state, null, random);
      if (!list2.isEmpty()) {
         this.renderQuadsSmooth(world, state, pos, buffer, vertexConsumer, list2, fs, bitSet, lv, overlay);
         bl2 = true;
      }

      return bl2;
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
      long l,
      int i
   ) {
      boolean bl2 = false;
      BitSet bitSet = new BitSet(3);

      for (Direction lv : Direction.values()) {
         random.setSeed(l);
         List<BakedQuad> list = model.getQuads(state, lv, random);
         if (!list.isEmpty() && (!cull || Block.shouldDrawSide(state, world, pos, lv))) {
            int j = WorldRenderer.getLightmapCoordinates(world, state, pos.offset(lv));
            this.renderQuadsFlat(world, state, pos, j, i, false, buffer, vertexConsumer, list, bitSet);
            bl2 = true;
         }
      }

      random.setSeed(l);
      List<BakedQuad> list2 = model.getQuads(state, null, random);
      if (!list2.isEmpty()) {
         this.renderQuadsFlat(world, state, pos, -1, i, true, buffer, vertexConsumer, list2, bitSet);
         bl2 = true;
      }

      return bl2;
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
      for (BakedQuad lv : quads) {
         this.getQuadDimensions(world, state, pos, lv.getVertexData(), lv.getFace(), box, flags);
         ambientOcclusionCalculator.apply(world, state, pos, lv.getFace(), box, flags, lv.hasShade());
         this.renderQuad(
            world,
            state,
            pos,
            vertexConsumer,
            matrix.peek(),
            lv,
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
      float p;
      float q;
      float r;
      if (quad.hasColor()) {
         int o = this.colorMap.getColor(state, world, pos, quad.getColorIndex());
         p = (float)(o >> 16 & 0xFF) / 255.0F;
         q = (float)(o >> 8 & 0xFF) / 255.0F;
         r = (float)(o & 0xFF) / 255.0F;
      } else {
         p = 1.0F;
         q = 1.0F;
         r = 1.0F;
      }

      vertexConsumer.quad(
         matrixEntry, quad, new float[]{brightness0, brightness1, brightness2, brightness3}, p, q, r, new int[]{light0, light1, light2, light3}, overlay, true
      );
   }

   private void getQuadDimensions(BlockRenderView world, BlockState state, BlockPos pos, int[] vertexData, Direction face, @Nullable float[] box, BitSet flags) {
      float f = 32.0F;
      float g = 32.0F;
      float h = 32.0F;
      float i = -32.0F;
      float j = -32.0F;
      float k = -32.0F;

      for (int l = 0; l < 4; l++) {
         float m = Float.intBitsToFloat(vertexData[l * 8]);
         float n = Float.intBitsToFloat(vertexData[l * 8 + 1]);
         float o = Float.intBitsToFloat(vertexData[l * 8 + 2]);
         f = Math.min(f, m);
         g = Math.min(g, n);
         h = Math.min(h, o);
         i = Math.max(i, m);
         j = Math.max(j, n);
         k = Math.max(k, o);
      }

      if (box != null) {
         box[Direction.WEST.getId()] = f;
         box[Direction.EAST.getId()] = i;
         box[Direction.DOWN.getId()] = g;
         box[Direction.UP.getId()] = j;
         box[Direction.NORTH.getId()] = h;
         box[Direction.SOUTH.getId()] = k;
         int p = Direction.values().length;
         box[Direction.WEST.getId() + p] = 1.0F - f;
         box[Direction.EAST.getId() + p] = 1.0F - i;
         box[Direction.DOWN.getId() + p] = 1.0F - g;
         box[Direction.UP.getId() + p] = 1.0F - j;
         box[Direction.NORTH.getId() + p] = 1.0F - h;
         box[Direction.SOUTH.getId() + p] = 1.0F - k;
      }

      float q = 1.0E-4F;
      float r = 0.9999F;
      switch (face) {
         case DOWN:
            flags.set(1, f >= 1.0E-4F || h >= 1.0E-4F || i <= 0.9999F || k <= 0.9999F);
            flags.set(0, g == j && (g < 1.0E-4F || state.isFullCube(world, pos)));
            break;
         case UP:
            flags.set(1, f >= 1.0E-4F || h >= 1.0E-4F || i <= 0.9999F || k <= 0.9999F);
            flags.set(0, g == j && (j > 0.9999F || state.isFullCube(world, pos)));
            break;
         case NORTH:
            flags.set(1, f >= 1.0E-4F || g >= 1.0E-4F || i <= 0.9999F || j <= 0.9999F);
            flags.set(0, h == k && (h < 1.0E-4F || state.isFullCube(world, pos)));
            break;
         case SOUTH:
            flags.set(1, f >= 1.0E-4F || g >= 1.0E-4F || i <= 0.9999F || j <= 0.9999F);
            flags.set(0, h == k && (k > 0.9999F || state.isFullCube(world, pos)));
            break;
         case WEST:
            flags.set(1, g >= 1.0E-4F || h >= 1.0E-4F || j <= 0.9999F || k <= 0.9999F);
            flags.set(0, f == i && (f < 1.0E-4F || state.isFullCube(world, pos)));
            break;
         case EAST:
            flags.set(1, g >= 1.0E-4F || h >= 1.0E-4F || j <= 0.9999F || k <= 0.9999F);
            flags.set(0, f == i && (i > 0.9999F || state.isFullCube(world, pos)));
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
      VertexConsumer arg5,
      List<BakedQuad> quads,
      BitSet flags
   ) {
      for (BakedQuad lv : quads) {
         if (useWorldLight) {
            this.getQuadDimensions(world, state, pos, lv.getVertexData(), lv.getFace(), null, flags);
            BlockPos lv2 = flags.get(0) ? pos.offset(lv.getFace()) : pos;
            light = WorldRenderer.getLightmapCoordinates(world, state, lv2);
         }

         float f = world.getBrightness(lv.getFace(), lv.hasShade());
         this.renderQuad(world, state, pos, arg5, matrices.peek(), lv, f, f, f, f, light, light, light, light, overlay);
      }
   }

   public void render(MatrixStack.Entry arg, VertexConsumer arg2, @Nullable BlockState arg3, BakedModel arg4, float f, float g, float h, int i, int j) {
      Random random = new Random();
      long l = 42L;

      for (Direction lv : Direction.values()) {
         random.setSeed(42L);
         renderQuad(arg, arg2, f, g, h, arg4.getQuads(arg3, lv, random), i, j);
      }

      random.setSeed(42L);
      renderQuad(arg, arg2, f, g, h, arg4.getQuads(arg3, null, random), i, j);
   }

   private static void renderQuad(MatrixStack.Entry arg, VertexConsumer arg2, float f, float g, float h, List<BakedQuad> list, int i, int j) {
      for (BakedQuad lv : list) {
         float k;
         float l;
         float m;
         if (lv.hasColor()) {
            k = MathHelper.clamp(f, 0.0F, 1.0F);
            l = MathHelper.clamp(g, 0.0F, 1.0F);
            m = MathHelper.clamp(h, 0.0F, 1.0F);
         } else {
            k = 1.0F;
            l = 1.0F;
            m = 1.0F;
         }

         arg2.quad(arg, lv, k, l, m, i, j);
      }
   }

   public static void enableBrightnessCache() {
      brightnessCache.get().enable();
   }

   public static void disableBrightnessCache() {
      brightnessCache.get().disable();
   }

   @Environment(EnvType.CLIENT)
   class AmbientOcclusionCalculator {
      private final float[] brightness = new float[4];
      private final int[] light = new int[4];

      public AmbientOcclusionCalculator() {
      }

      public void apply(BlockRenderView world, BlockState state, BlockPos pos, Direction direction, float[] box, BitSet flags, boolean bl) {
         BlockPos lv = flags.get(0) ? pos.offset(direction) : pos;
         BlockModelRenderer.NeighborData lv2 = BlockModelRenderer.NeighborData.getData(direction);
         BlockPos.Mutable lv3 = new BlockPos.Mutable();
         BlockModelRenderer.BrightnessCache lv4 = BlockModelRenderer.brightnessCache.get();
         lv3.set(lv, lv2.faces[0]);
         BlockState lv5 = world.getBlockState(lv3);
         int i = lv4.getInt(lv5, world, lv3);
         float f = lv4.getFloat(lv5, world, lv3);
         lv3.set(lv, lv2.faces[1]);
         BlockState lv6 = world.getBlockState(lv3);
         int j = lv4.getInt(lv6, world, lv3);
         float g = lv4.getFloat(lv6, world, lv3);
         lv3.set(lv, lv2.faces[2]);
         BlockState lv7 = world.getBlockState(lv3);
         int k = lv4.getInt(lv7, world, lv3);
         float h = lv4.getFloat(lv7, world, lv3);
         lv3.set(lv, lv2.faces[3]);
         BlockState lv8 = world.getBlockState(lv3);
         int l = lv4.getInt(lv8, world, lv3);
         float m = lv4.getFloat(lv8, world, lv3);
         lv3.set(lv, lv2.faces[0]).move(direction);
         boolean bl2 = world.getBlockState(lv3).getOpacity(world, lv3) == 0;
         lv3.set(lv, lv2.faces[1]).move(direction);
         boolean bl3 = world.getBlockState(lv3).getOpacity(world, lv3) == 0;
         lv3.set(lv, lv2.faces[2]).move(direction);
         boolean bl4 = world.getBlockState(lv3).getOpacity(world, lv3) == 0;
         lv3.set(lv, lv2.faces[3]).move(direction);
         boolean bl5 = world.getBlockState(lv3).getOpacity(world, lv3) == 0;
         float p;
         int q;
         if (!bl4 && !bl2) {
            p = f;
            q = i;
         } else {
            lv3.set(lv, lv2.faces[0]).move(lv2.faces[2]);
            BlockState lv9 = world.getBlockState(lv3);
            p = lv4.getFloat(lv9, world, lv3);
            q = lv4.getInt(lv9, world, lv3);
         }

         float t;
         int u;
         if (!bl5 && !bl2) {
            t = f;
            u = i;
         } else {
            lv3.set(lv, lv2.faces[0]).move(lv2.faces[3]);
            BlockState lv10 = world.getBlockState(lv3);
            t = lv4.getFloat(lv10, world, lv3);
            u = lv4.getInt(lv10, world, lv3);
         }

         float x;
         int y;
         if (!bl4 && !bl3) {
            x = f;
            y = i;
         } else {
            lv3.set(lv, lv2.faces[1]).move(lv2.faces[2]);
            BlockState lv11 = world.getBlockState(lv3);
            x = lv4.getFloat(lv11, world, lv3);
            y = lv4.getInt(lv11, world, lv3);
         }

         float ab;
         int ac;
         if (!bl5 && !bl3) {
            ab = f;
            ac = i;
         } else {
            lv3.set(lv, lv2.faces[1]).move(lv2.faces[3]);
            BlockState lv12 = world.getBlockState(lv3);
            ab = lv4.getFloat(lv12, world, lv3);
            ac = lv4.getInt(lv12, world, lv3);
         }

         int ad = lv4.getInt(state, world, pos);
         lv3.set(pos, direction);
         BlockState lv13 = world.getBlockState(lv3);
         if (flags.get(0) || !lv13.isOpaqueFullCube(world, lv3)) {
            ad = lv4.getInt(lv13, world, lv3);
         }

         float ae = flags.get(0) ? lv4.getFloat(world.getBlockState(lv), world, lv) : lv4.getFloat(world.getBlockState(pos), world, pos);
         BlockModelRenderer.Translation lv14 = BlockModelRenderer.Translation.getTranslations(direction);
         if (flags.get(1) && lv2.nonCubicWeight) {
            float aj = (m + f + t + ae) * 0.25F;
            float ak = (h + f + p + ae) * 0.25F;
            float al = (h + g + x + ae) * 0.25F;
            float am = (m + g + ab + ae) * 0.25F;
            float an = box[lv2.field_4192[0].shape] * box[lv2.field_4192[1].shape];
            float ao = box[lv2.field_4192[2].shape] * box[lv2.field_4192[3].shape];
            float ap = box[lv2.field_4192[4].shape] * box[lv2.field_4192[5].shape];
            float aq = box[lv2.field_4192[6].shape] * box[lv2.field_4192[7].shape];
            float ar = box[lv2.field_4185[0].shape] * box[lv2.field_4185[1].shape];
            float as = box[lv2.field_4185[2].shape] * box[lv2.field_4185[3].shape];
            float at = box[lv2.field_4185[4].shape] * box[lv2.field_4185[5].shape];
            float au = box[lv2.field_4185[6].shape] * box[lv2.field_4185[7].shape];
            float av = box[lv2.field_4180[0].shape] * box[lv2.field_4180[1].shape];
            float aw = box[lv2.field_4180[2].shape] * box[lv2.field_4180[3].shape];
            float ax = box[lv2.field_4180[4].shape] * box[lv2.field_4180[5].shape];
            float ay = box[lv2.field_4180[6].shape] * box[lv2.field_4180[7].shape];
            float az = box[lv2.field_4188[0].shape] * box[lv2.field_4188[1].shape];
            float ba = box[lv2.field_4188[2].shape] * box[lv2.field_4188[3].shape];
            float bb = box[lv2.field_4188[4].shape] * box[lv2.field_4188[5].shape];
            float bc = box[lv2.field_4188[6].shape] * box[lv2.field_4188[7].shape];
            this.brightness[lv14.firstCorner] = aj * an + ak * ao + al * ap + am * aq;
            this.brightness[lv14.secondCorner] = aj * ar + ak * as + al * at + am * au;
            this.brightness[lv14.thirdCorner] = aj * av + ak * aw + al * ax + am * ay;
            this.brightness[lv14.fourthCorner] = aj * az + ak * ba + al * bb + am * bc;
            int bd = this.getAmbientOcclusionBrightness(l, i, u, ad);
            int be = this.getAmbientOcclusionBrightness(k, i, q, ad);
            int bf = this.getAmbientOcclusionBrightness(k, j, y, ad);
            int bg = this.getAmbientOcclusionBrightness(l, j, ac, ad);
            this.light[lv14.firstCorner] = this.getBrightness(bd, be, bf, bg, an, ao, ap, aq);
            this.light[lv14.secondCorner] = this.getBrightness(bd, be, bf, bg, ar, as, at, au);
            this.light[lv14.thirdCorner] = this.getBrightness(bd, be, bf, bg, av, aw, ax, ay);
            this.light[lv14.fourthCorner] = this.getBrightness(bd, be, bf, bg, az, ba, bb, bc);
         } else {
            float af = (m + f + t + ae) * 0.25F;
            float ag = (h + f + p + ae) * 0.25F;
            float ah = (h + g + x + ae) * 0.25F;
            float ai = (m + g + ab + ae) * 0.25F;
            this.light[lv14.firstCorner] = this.getAmbientOcclusionBrightness(l, i, u, ad);
            this.light[lv14.secondCorner] = this.getAmbientOcclusionBrightness(k, i, q, ad);
            this.light[lv14.thirdCorner] = this.getAmbientOcclusionBrightness(k, j, y, ad);
            this.light[lv14.fourthCorner] = this.getAmbientOcclusionBrightness(l, j, ac, ad);
            this.brightness[lv14.firstCorner] = af;
            this.brightness[lv14.secondCorner] = ag;
            this.brightness[lv14.thirdCorner] = ah;
            this.brightness[lv14.fourthCorner] = ai;
         }

         float bh = world.getBrightness(direction, bl);

         for (int bi = 0; bi < this.brightness.length; bi++) {
            this.brightness[bi] = this.brightness[bi] * bh;
         }
      }

      private int getAmbientOcclusionBrightness(int i, int j, int k, int l) {
         if (i == 0) {
            i = l;
         }

         if (j == 0) {
            j = l;
         }

         if (k == 0) {
            k = l;
         }

         return i + j + k + l >> 2 & 16711935;
      }

      private int getBrightness(int i, int j, int k, int l, float f, float g, float h, float m) {
         int n = (int)((float)(i >> 16 & 0xFF) * f + (float)(j >> 16 & 0xFF) * g + (float)(k >> 16 & 0xFF) * h + (float)(l >> 16 & 0xFF) * m) & 0xFF;
         int o = (int)((float)(i & 0xFF) * f + (float)(j & 0xFF) * g + (float)(k & 0xFF) * h + (float)(l & 0xFF) * m) & 0xFF;
         return n << 16 | o;
      }
   }

   @Environment(EnvType.CLIENT)
   static class BrightnessCache {
      private boolean enabled;
      private final Long2IntLinkedOpenHashMap intCache = Util.make(() -> {
         Long2IntLinkedOpenHashMap long2IntLinkedOpenHashMap = new Long2IntLinkedOpenHashMap(100, 0.25F) {
            protected void rehash(int i) {
            }
         };
         long2IntLinkedOpenHashMap.defaultReturnValue(Integer.MAX_VALUE);
         return long2IntLinkedOpenHashMap;
      });
      private final Long2FloatLinkedOpenHashMap floatCache = Util.make(() -> {
         Long2FloatLinkedOpenHashMap long2FloatLinkedOpenHashMap = new Long2FloatLinkedOpenHashMap(100, 0.25F) {
            protected void rehash(int i) {
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

      public int getInt(BlockState state, BlockRenderView arg2, BlockPos pos) {
         long l = pos.asLong();
         if (this.enabled) {
            int i = this.intCache.get(l);
            if (i != Integer.MAX_VALUE) {
               return i;
            }
         }

         int j = WorldRenderer.getLightmapCoordinates(arg2, state, pos);
         if (this.enabled) {
            if (this.intCache.size() == 100) {
               this.intCache.removeFirstInt();
            }

            this.intCache.put(l, j);
         }

         return j;
      }

      public float getFloat(BlockState state, BlockRenderView blockView, BlockPos pos) {
         long l = pos.asLong();
         if (this.enabled) {
            float f = this.floatCache.get(l);
            if (!Float.isNaN(f)) {
               return f;
            }
         }

         float g = state.getAmbientOcclusionLightLevel(blockView, pos);
         if (this.enabled) {
            if (this.floatCache.size() == 100) {
               this.floatCache.removeFirstFloat();
            }

            this.floatCache.put(l, g);
         }

         return g;
      }
   }

   @Environment(EnvType.CLIENT)
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
      private static final BlockModelRenderer.NeighborData[] field_4190 = Util.make(new BlockModelRenderer.NeighborData[6], args -> {
         args[Direction.DOWN.getId()] = DOWN;
         args[Direction.UP.getId()] = UP;
         args[Direction.NORTH.getId()] = NORTH;
         args[Direction.SOUTH.getId()] = SOUTH;
         args[Direction.WEST.getId()] = WEST;
         args[Direction.EAST.getId()] = EAST;
      });

      private NeighborData(
         Direction[] args,
         float f,
         boolean bl,
         BlockModelRenderer.NeighborOrientation[] args2,
         BlockModelRenderer.NeighborOrientation[] args3,
         BlockModelRenderer.NeighborOrientation[] args4,
         BlockModelRenderer.NeighborOrientation[] args5
      ) {
         this.faces = args;
         this.nonCubicWeight = bl;
         this.field_4192 = args2;
         this.field_4185 = args3;
         this.field_4180 = args4;
         this.field_4188 = args5;
      }

      public static BlockModelRenderer.NeighborData getData(Direction arg) {
         return field_4190[arg.getId()];
      }
   }

   @Environment(EnvType.CLIENT)
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

      private NeighborOrientation(Direction arg, boolean bl) {
         this.shape = arg.getId() + (bl ? Direction.values().length : 0);
      }
   }

   @Environment(EnvType.CLIENT)
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
      private static final BlockModelRenderer.Translation[] VALUES = Util.make(new BlockModelRenderer.Translation[6], args -> {
         args[Direction.DOWN.getId()] = DOWN;
         args[Direction.UP.getId()] = UP;
         args[Direction.NORTH.getId()] = NORTH;
         args[Direction.SOUTH.getId()] = SOUTH;
         args[Direction.WEST.getId()] = WEST;
         args[Direction.EAST.getId()] = EAST;
      });

      private Translation(int firstCorner, int secondCorner, int thirdCorner, int fourthCorner) {
         this.firstCorner = firstCorner;
         this.secondCorner = secondCorner;
         this.thirdCorner = thirdCorner;
         this.fourthCorner = fourthCorner;
      }

      public static BlockModelRenderer.Translation getTranslations(Direction arg) {
         return VALUES[arg.getId()];
      }
   }
}
