package net.minecraft.world.gen.feature;

import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class BonusChestFeature extends Feature<DefaultFeatureConfig> {
   public BonusChestFeature(Codec<DefaultFeatureConfig> _snowman) {
      super(_snowman);
   }

   public boolean generate(StructureWorldAccess _snowman, ChunkGenerator _snowman, Random _snowman, BlockPos _snowman, DefaultFeatureConfig _snowman) {
      ChunkPos _snowmanxxxxx = new ChunkPos(_snowman);
      List<Integer> _snowmanxxxxxx = IntStream.rangeClosed(_snowmanxxxxx.getStartX(), _snowmanxxxxx.getEndX()).boxed().collect(Collectors.toList());
      Collections.shuffle(_snowmanxxxxxx, _snowman);
      List<Integer> _snowmanxxxxxxx = IntStream.rangeClosed(_snowmanxxxxx.getStartZ(), _snowmanxxxxx.getEndZ()).boxed().collect(Collectors.toList());
      Collections.shuffle(_snowmanxxxxxxx, _snowman);
      BlockPos.Mutable _snowmanxxxxxxxx = new BlockPos.Mutable();

      for (Integer _snowmanxxxxxxxxx : _snowmanxxxxxx) {
         for (Integer _snowmanxxxxxxxxxx : _snowmanxxxxxxx) {
            _snowmanxxxxxxxx.set(_snowmanxxxxxxxxx, 0, _snowmanxxxxxxxxxx);
            BlockPos _snowmanxxxxxxxxxxx = _snowman.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, _snowmanxxxxxxxx);
            if (_snowman.isAir(_snowmanxxxxxxxxxxx) || _snowman.getBlockState(_snowmanxxxxxxxxxxx).getCollisionShape(_snowman, _snowmanxxxxxxxxxxx).isEmpty()) {
               _snowman.setBlockState(_snowmanxxxxxxxxxxx, Blocks.CHEST.getDefaultState(), 2);
               LootableContainerBlockEntity.setLootTable(_snowman, _snowman, _snowmanxxxxxxxxxxx, LootTables.SPAWN_BONUS_CHEST);
               BlockState _snowmanxxxxxxxxxxxx = Blocks.TORCH.getDefaultState();

               for (Direction _snowmanxxxxxxxxxxxxx : Direction.Type.HORIZONTAL) {
                  BlockPos _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.offset(_snowmanxxxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxxx.canPlaceAt(_snowman, _snowmanxxxxxxxxxxxxxx)) {
                     _snowman.setBlockState(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 2);
                  }
               }

               return true;
            }
         }
      }

      return false;
   }
}
