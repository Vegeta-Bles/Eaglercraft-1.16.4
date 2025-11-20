package net.minecraft.client.render.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
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

@Environment(EnvType.CLIENT)
public class FluidRenderer {
   private final Sprite[] lavaSprites = new Sprite[2];
   private final Sprite[] waterSprites = new Sprite[2];
   private Sprite waterOverlaySprite;

   public FluidRenderer() {
   }

   protected void onResourceReload() {
      this.lavaSprites[0] = MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(Blocks.LAVA.getDefaultState()).getSprite();
      this.lavaSprites[1] = ModelLoader.LAVA_FLOW.getSprite();
      this.waterSprites[0] = MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(Blocks.WATER.getDefaultState()).getSprite();
      this.waterSprites[1] = ModelLoader.WATER_FLOW.getSprite();
      this.waterOverlaySprite = ModelLoader.WATER_OVERLAY.getSprite();
   }

   private static boolean isSameFluid(BlockView world, BlockPos pos, Direction side, FluidState state) {
      BlockPos lv = pos.offset(side);
      FluidState lv2 = world.getFluidState(lv);
      return lv2.getFluid().matchesType(state.getFluid());
   }

   private static boolean method_29710(BlockView arg, Direction arg2, float f, BlockPos arg3, BlockState arg4) {
      if (arg4.isOpaque()) {
         VoxelShape lv = VoxelShapes.cuboid(0.0, 0.0, 0.0, 1.0, (double)f, 1.0);
         VoxelShape lv2 = arg4.getCullingShape(arg, arg3);
         return VoxelShapes.isSideCovered(lv, lv2, arg2);
      } else {
         return false;
      }
   }

   private static boolean isSideCovered(BlockView world, BlockPos pos, Direction direction, float maxDeviation) {
      BlockPos lv = pos.offset(direction);
      BlockState lv2 = world.getBlockState(lv);
      return method_29710(world, direction, maxDeviation, lv, lv2);
   }

   private static boolean method_29709(BlockView arg, BlockPos arg2, BlockState arg3, Direction arg4) {
      return method_29710(arg, arg4.getOpposite(), 1.0F, arg2, arg3);
   }

   public static boolean method_29708(BlockRenderView arg, BlockPos arg2, FluidState arg3, BlockState arg4, Direction arg5) {
      return !method_29709(arg, arg2, arg4, arg5) && !isSameFluid(arg, arg2, arg5, arg3);
   }

   public boolean render(BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, FluidState state) {
      boolean bl = state.isIn(FluidTags.LAVA);
      Sprite[] lvs = bl ? this.lavaSprites : this.waterSprites;
      BlockState lv = world.getBlockState(pos);
      int i = bl ? 16777215 : BiomeColors.getWaterColor(world, pos);
      float f = (float)(i >> 16 & 0xFF) / 255.0F;
      float g = (float)(i >> 8 & 0xFF) / 255.0F;
      float h = (float)(i & 0xFF) / 255.0F;
      boolean bl2 = !isSameFluid(world, pos, Direction.UP, state);
      boolean bl3 = method_29708(world, pos, state, lv, Direction.DOWN) && !isSideCovered(world, pos, Direction.DOWN, 0.8888889F);
      boolean bl4 = method_29708(world, pos, state, lv, Direction.NORTH);
      boolean bl5 = method_29708(world, pos, state, lv, Direction.SOUTH);
      boolean bl6 = method_29708(world, pos, state, lv, Direction.WEST);
      boolean bl7 = method_29708(world, pos, state, lv, Direction.EAST);
      if (!bl2 && !bl3 && !bl7 && !bl6 && !bl4 && !bl5) {
         return false;
      } else {
         boolean bl8 = false;
         float j = world.getBrightness(Direction.DOWN, true);
         float k = world.getBrightness(Direction.UP, true);
         float l = world.getBrightness(Direction.NORTH, true);
         float m = world.getBrightness(Direction.WEST, true);
         float n = this.getNorthWestCornerFluidHeight(world, pos, state.getFluid());
         float o = this.getNorthWestCornerFluidHeight(world, pos.south(), state.getFluid());
         float p = this.getNorthWestCornerFluidHeight(world, pos.east().south(), state.getFluid());
         float q = this.getNorthWestCornerFluidHeight(world, pos.east(), state.getFluid());
         double d = (double)(pos.getX() & 15);
         double e = (double)(pos.getY() & 15);
         double r = (double)(pos.getZ() & 15);
         float s = 0.001F;
         float t = bl3 ? 0.001F : 0.0F;
         if (bl2 && !isSideCovered(world, pos, Direction.UP, Math.min(Math.min(n, o), Math.min(p, q)))) {
            bl8 = true;
            n -= 0.001F;
            o -= 0.001F;
            p -= 0.001F;
            q -= 0.001F;
            Vec3d lv2 = state.getVelocity(world, pos);
            float u;
            float w;
            float y;
            float aa;
            float v;
            float x;
            float z;
            float ab;
            if (lv2.x == 0.0 && lv2.z == 0.0) {
               Sprite lv3 = lvs[0];
               u = lv3.getFrameU(0.0);
               v = lv3.getFrameV(0.0);
               w = u;
               x = lv3.getFrameV(16.0);
               y = lv3.getFrameU(16.0);
               z = x;
               aa = y;
               ab = v;
            } else {
               Sprite lv4 = lvs[1];
               float ac = (float)MathHelper.atan2(lv2.z, lv2.x) - (float) (Math.PI / 2);
               float ad = MathHelper.sin(ac) * 0.25F;
               float ae = MathHelper.cos(ac) * 0.25F;
               float af = 8.0F;
               u = lv4.getFrameU((double)(8.0F + (-ae - ad) * 16.0F));
               v = lv4.getFrameV((double)(8.0F + (-ae + ad) * 16.0F));
               w = lv4.getFrameU((double)(8.0F + (-ae + ad) * 16.0F));
               x = lv4.getFrameV((double)(8.0F + (ae + ad) * 16.0F));
               y = lv4.getFrameU((double)(8.0F + (ae + ad) * 16.0F));
               z = lv4.getFrameV((double)(8.0F + (ae - ad) * 16.0F));
               aa = lv4.getFrameU((double)(8.0F + (ae - ad) * 16.0F));
               ab = lv4.getFrameV((double)(8.0F + (-ae - ad) * 16.0F));
            }

            float ao = (u + w + y + aa) / 4.0F;
            float ap = (v + x + z + ab) / 4.0F;
            float aq = (float)lvs[0].getWidth() / (lvs[0].getMaxU() - lvs[0].getMinU());
            float ar = (float)lvs[0].getHeight() / (lvs[0].getMaxV() - lvs[0].getMinV());
            float as = 4.0F / Math.max(ar, aq);
            u = MathHelper.lerp(as, u, ao);
            w = MathHelper.lerp(as, w, ao);
            y = MathHelper.lerp(as, y, ao);
            aa = MathHelper.lerp(as, aa, ao);
            v = MathHelper.lerp(as, v, ap);
            x = MathHelper.lerp(as, x, ap);
            z = MathHelper.lerp(as, z, ap);
            ab = MathHelper.lerp(as, ab, ap);
            int at = this.getLight(world, pos);
            float au = k * f;
            float av = k * g;
            float aw = k * h;
            this.vertex(vertexConsumer, d + 0.0, e + (double)n, r + 0.0, au, av, aw, u, v, at);
            this.vertex(vertexConsumer, d + 0.0, e + (double)o, r + 1.0, au, av, aw, w, x, at);
            this.vertex(vertexConsumer, d + 1.0, e + (double)p, r + 1.0, au, av, aw, y, z, at);
            this.vertex(vertexConsumer, d + 1.0, e + (double)q, r + 0.0, au, av, aw, aa, ab, at);
            if (state.method_15756(world, pos.up())) {
               this.vertex(vertexConsumer, d + 0.0, e + (double)n, r + 0.0, au, av, aw, u, v, at);
               this.vertex(vertexConsumer, d + 1.0, e + (double)q, r + 0.0, au, av, aw, aa, ab, at);
               this.vertex(vertexConsumer, d + 1.0, e + (double)p, r + 1.0, au, av, aw, y, z, at);
               this.vertex(vertexConsumer, d + 0.0, e + (double)o, r + 1.0, au, av, aw, w, x, at);
            }
         }

         if (bl3) {
            float ax = lvs[0].getMinU();
            float ay = lvs[0].getMaxU();
            float az = lvs[0].getMinV();
            float ba = lvs[0].getMaxV();
            int bb = this.getLight(world, pos.down());
            float bc = j * f;
            float bd = j * g;
            float be = j * h;
            this.vertex(vertexConsumer, d, e + (double)t, r + 1.0, bc, bd, be, ax, ba, bb);
            this.vertex(vertexConsumer, d, e + (double)t, r, bc, bd, be, ax, az, bb);
            this.vertex(vertexConsumer, d + 1.0, e + (double)t, r, bc, bd, be, ay, az, bb);
            this.vertex(vertexConsumer, d + 1.0, e + (double)t, r + 1.0, bc, bd, be, ay, ba, bb);
            bl8 = true;
         }

         for (int bf = 0; bf < 4; bf++) {
            float bg;
            float bh;
            double bi;
            double bk;
            double bj;
            double bm;
            Direction lv5;
            boolean bl9;
            if (bf == 0) {
               bg = n;
               bh = q;
               bi = d;
               bj = d + 1.0;
               bk = r + 0.001F;
               bm = r + 0.001F;
               lv5 = Direction.NORTH;
               bl9 = bl4;
            } else if (bf == 1) {
               bg = p;
               bh = o;
               bi = d + 1.0;
               bj = d;
               bk = r + 1.0 - 0.001F;
               bm = r + 1.0 - 0.001F;
               lv5 = Direction.SOUTH;
               bl9 = bl5;
            } else if (bf == 2) {
               bg = o;
               bh = n;
               bi = d + 0.001F;
               bj = d + 0.001F;
               bk = r + 1.0;
               bm = r;
               lv5 = Direction.WEST;
               bl9 = bl6;
            } else {
               bg = q;
               bh = p;
               bi = d + 1.0 - 0.001F;
               bj = d + 1.0 - 0.001F;
               bk = r;
               bm = r + 1.0;
               lv5 = Direction.EAST;
               bl9 = bl7;
            }

            if (bl9 && !isSideCovered(world, pos, lv5, Math.max(bg, bh))) {
               bl8 = true;
               BlockPos lv9 = pos.offset(lv5);
               Sprite lv10 = lvs[1];
               if (!bl) {
                  Block lv11 = world.getBlockState(lv9).getBlock();
                  if (lv11 instanceof TransparentBlock || lv11 instanceof LeavesBlock) {
                     lv10 = this.waterOverlaySprite;
                  }
               }

               float cf = lv10.getFrameU(0.0);
               float cg = lv10.getFrameU(8.0);
               float ch = lv10.getFrameV((double)((1.0F - bg) * 16.0F * 0.5F));
               float ci = lv10.getFrameV((double)((1.0F - bh) * 16.0F * 0.5F));
               float cj = lv10.getFrameV(8.0);
               int ck = this.getLight(world, lv9);
               float cl = bf < 2 ? l : m;
               float cm = k * cl * f;
               float cn = k * cl * g;
               float co = k * cl * h;
               this.vertex(vertexConsumer, bi, e + (double)bg, bk, cm, cn, co, cf, ch, ck);
               this.vertex(vertexConsumer, bj, e + (double)bh, bm, cm, cn, co, cg, ci, ck);
               this.vertex(vertexConsumer, bj, e + (double)t, bm, cm, cn, co, cg, cj, ck);
               this.vertex(vertexConsumer, bi, e + (double)t, bk, cm, cn, co, cf, cj, ck);
               if (lv10 != this.waterOverlaySprite) {
                  this.vertex(vertexConsumer, bi, e + (double)t, bk, cm, cn, co, cf, cj, ck);
                  this.vertex(vertexConsumer, bj, e + (double)t, bm, cm, cn, co, cg, cj, ck);
                  this.vertex(vertexConsumer, bj, e + (double)bh, bm, cm, cn, co, cg, ci, ck);
                  this.vertex(vertexConsumer, bi, e + (double)bg, bk, cm, cn, co, cf, ch, ck);
               }
            }
         }

         return bl8;
      }
   }

   private void vertex(VertexConsumer vertexConsumer, double x, double y, double z, float red, float green, float blue, float u, float v, int light) {
      vertexConsumer.vertex(x, y, z).color(red, green, blue, 1.0F).texture(u, v).light(light).normal(0.0F, 1.0F, 0.0F).next();
   }

   private int getLight(BlockRenderView world, BlockPos pos) {
      int i = WorldRenderer.getLightmapCoordinates(world, pos);
      int j = WorldRenderer.getLightmapCoordinates(world, pos.up());
      int k = i & 0xFF;
      int l = j & 0xFF;
      int m = i >> 16 & 0xFF;
      int n = j >> 16 & 0xFF;
      return (k > l ? k : l) | (m > n ? m : n) << 16;
   }

   private float getNorthWestCornerFluidHeight(BlockView world, BlockPos pos, Fluid fluid) {
      int i = 0;
      float f = 0.0F;

      for (int j = 0; j < 4; j++) {
         BlockPos lv = pos.add(-(j & 1), 0, -(j >> 1 & 1));
         if (world.getFluidState(lv.up()).getFluid().matchesType(fluid)) {
            return 1.0F;
         }

         FluidState lv2 = world.getFluidState(lv);
         if (lv2.getFluid().matchesType(fluid)) {
            float g = lv2.getHeight(world, lv);
            if (g >= 0.8F) {
               f += g * 10.0F;
               i += 10;
            } else {
               f += g;
               i++;
            }
         } else if (!world.getBlockState(lv).getMaterial().isSolid()) {
            i++;
         }
      }

      return f / (float)i;
   }
}
