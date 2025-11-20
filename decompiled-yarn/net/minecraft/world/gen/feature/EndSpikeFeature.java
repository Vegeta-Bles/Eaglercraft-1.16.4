package net.minecraft.world.gen.feature;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PaneBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class EndSpikeFeature extends Feature<EndSpikeFeatureConfig> {
   private static final LoadingCache<Long, List<EndSpikeFeature.Spike>> CACHE = CacheBuilder.newBuilder()
      .expireAfterWrite(5L, TimeUnit.MINUTES)
      .build(new EndSpikeFeature.SpikeCache());

   public EndSpikeFeature(Codec<EndSpikeFeatureConfig> _snowman) {
      super(_snowman);
   }

   public static List<EndSpikeFeature.Spike> getSpikes(StructureWorldAccess world) {
      Random _snowman = new Random(world.getSeed());
      long _snowmanx = _snowman.nextLong() & 65535L;
      return (List<EndSpikeFeature.Spike>)CACHE.getUnchecked(_snowmanx);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, EndSpikeFeatureConfig _snowman) {
      List<EndSpikeFeature.Spike> _snowmanxxxxx = _snowman.getSpikes();
      if (_snowmanxxxxx.isEmpty()) {
         _snowmanxxxxx = getSpikes(_snowman);
      }

      for (EndSpikeFeature.Spike _snowmanxxxxxx : _snowmanxxxxx) {
         if (_snowmanxxxxxx.isInChunk(_snowman)) {
            this.generateSpike(_snowman, _snowman, _snowman, _snowmanxxxxxx);
         }
      }

      return true;
   }

   private void generateSpike(ServerWorldAccess world, Random random, EndSpikeFeatureConfig config, EndSpikeFeature.Spike spike) {
      int _snowman = spike.getRadius();

      for (BlockPos _snowmanx : BlockPos.iterate(
         new BlockPos(spike.getCenterX() - _snowman, 0, spike.getCenterZ() - _snowman), new BlockPos(spike.getCenterX() + _snowman, spike.getHeight() + 10, spike.getCenterZ() + _snowman)
      )) {
         if (_snowmanx.getSquaredDistance((double)spike.getCenterX(), (double)_snowmanx.getY(), (double)spike.getCenterZ(), false) <= (double)(_snowman * _snowman + 1)
            && _snowmanx.getY() < spike.getHeight()) {
            this.setBlockState(world, _snowmanx, Blocks.OBSIDIAN.getDefaultState());
         } else if (_snowmanx.getY() > 65) {
            this.setBlockState(world, _snowmanx, Blocks.AIR.getDefaultState());
         }
      }

      if (spike.isGuarded()) {
         int _snowmanxx = -2;
         int _snowmanxxx = 2;
         int _snowmanxxxx = 3;
         BlockPos.Mutable _snowmanxxxxx = new BlockPos.Mutable();

         for (int _snowmanxxxxxx = -2; _snowmanxxxxxx <= 2; _snowmanxxxxxx++) {
            for (int _snowmanxxxxxxx = -2; _snowmanxxxxxxx <= 2; _snowmanxxxxxxx++) {
               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx <= 3; _snowmanxxxxxxxx++) {
                  boolean _snowmanxxxxxxxxx = MathHelper.abs(_snowmanxxxxxx) == 2;
                  boolean _snowmanxxxxxxxxxx = MathHelper.abs(_snowmanxxxxxxx) == 2;
                  boolean _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx == 3;
                  if (_snowmanxxxxxxxxx || _snowmanxxxxxxxxxx || _snowmanxxxxxxxxxxx) {
                     boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxx == -2 || _snowmanxxxxxx == 2 || _snowmanxxxxxxxxxxx;
                     boolean _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxx == -2 || _snowmanxxxxxxx == 2 || _snowmanxxxxxxxxxxx;
                     BlockState _snowmanxxxxxxxxxxxxxx = Blocks.IRON_BARS
                        .getDefaultState()
                        .with(PaneBlock.NORTH, Boolean.valueOf(_snowmanxxxxxxxxxxxx && _snowmanxxxxxxx != -2))
                        .with(PaneBlock.SOUTH, Boolean.valueOf(_snowmanxxxxxxxxxxxx && _snowmanxxxxxxx != 2))
                        .with(PaneBlock.WEST, Boolean.valueOf(_snowmanxxxxxxxxxxxxx && _snowmanxxxxxx != -2))
                        .with(PaneBlock.EAST, Boolean.valueOf(_snowmanxxxxxxxxxxxxx && _snowmanxxxxxx != 2));
                     this.setBlockState(
                        world, _snowmanxxxxx.set(spike.getCenterX() + _snowmanxxxxxx, spike.getHeight() + _snowmanxxxxxxxx, spike.getCenterZ() + _snowmanxxxxxxx), _snowmanxxxxxxxxxxxxxx
                     );
                  }
               }
            }
         }
      }

      EndCrystalEntity _snowmanxx = EntityType.END_CRYSTAL.create(world.toServerWorld());
      _snowmanxx.setBeamTarget(config.getPos());
      _snowmanxx.setInvulnerable(config.isCrystalInvulnerable());
      _snowmanxx.refreshPositionAndAngles(
         (double)spike.getCenterX() + 0.5, (double)(spike.getHeight() + 1), (double)spike.getCenterZ() + 0.5, random.nextFloat() * 360.0F, 0.0F
      );
      world.spawnEntity(_snowmanxx);
      this.setBlockState(world, new BlockPos(spike.getCenterX(), spike.getHeight(), spike.getCenterZ()), Blocks.BEDROCK.getDefaultState());
   }

   public static class Spike {
      public static final Codec<EndSpikeFeature.Spike> CODEC = RecordCodecBuilder.create(
         _snowman -> _snowman.group(
                  Codec.INT.fieldOf("centerX").orElse(0).forGetter(_snowmanx -> _snowmanx.centerX),
                  Codec.INT.fieldOf("centerZ").orElse(0).forGetter(_snowmanx -> _snowmanx.centerZ),
                  Codec.INT.fieldOf("radius").orElse(0).forGetter(_snowmanx -> _snowmanx.radius),
                  Codec.INT.fieldOf("height").orElse(0).forGetter(_snowmanx -> _snowmanx.height),
                  Codec.BOOL.fieldOf("guarded").orElse(false).forGetter(_snowmanx -> _snowmanx.guarded)
               )
               .apply(_snowman, EndSpikeFeature.Spike::new)
      );
      private final int centerX;
      private final int centerZ;
      private final int radius;
      private final int height;
      private final boolean guarded;
      private final Box boundingBox;

      public Spike(int centerX, int centerZ, int radius, int height, boolean guarded) {
         this.centerX = centerX;
         this.centerZ = centerZ;
         this.radius = radius;
         this.height = height;
         this.guarded = guarded;
         this.boundingBox = new Box((double)(centerX - radius), 0.0, (double)(centerZ - radius), (double)(centerX + radius), 256.0, (double)(centerZ + radius));
      }

      public boolean isInChunk(BlockPos pos) {
         return pos.getX() >> 4 == this.centerX >> 4 && pos.getZ() >> 4 == this.centerZ >> 4;
      }

      public int getCenterX() {
         return this.centerX;
      }

      public int getCenterZ() {
         return this.centerZ;
      }

      public int getRadius() {
         return this.radius;
      }

      public int getHeight() {
         return this.height;
      }

      public boolean isGuarded() {
         return this.guarded;
      }

      public Box getBoundingBox() {
         return this.boundingBox;
      }
   }

   static class SpikeCache extends CacheLoader<Long, List<EndSpikeFeature.Spike>> {
      private SpikeCache() {
      }

      public List<EndSpikeFeature.Spike> load(Long _snowman) {
         List<Integer> _snowmanx = IntStream.range(0, 10).boxed().collect(Collectors.toList());
         Collections.shuffle(_snowmanx, new Random(_snowman));
         List<EndSpikeFeature.Spike> _snowmanxx = Lists.newArrayList();

         for (int _snowmanxxx = 0; _snowmanxxx < 10; _snowmanxxx++) {
            int _snowmanxxxx = MathHelper.floor(42.0 * Math.cos(2.0 * (-Math.PI + (Math.PI / 10) * (double)_snowmanxxx)));
            int _snowmanxxxxx = MathHelper.floor(42.0 * Math.sin(2.0 * (-Math.PI + (Math.PI / 10) * (double)_snowmanxxx)));
            int _snowmanxxxxxx = _snowmanx.get(_snowmanxxx);
            int _snowmanxxxxxxx = 2 + _snowmanxxxxxx / 3;
            int _snowmanxxxxxxxx = 76 + _snowmanxxxxxx * 3;
            boolean _snowmanxxxxxxxxx = _snowmanxxxxxx == 1 || _snowmanxxxxxx == 2;
            _snowmanxx.add(new EndSpikeFeature.Spike(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx));
         }

         return _snowmanxx;
      }
   }
}
