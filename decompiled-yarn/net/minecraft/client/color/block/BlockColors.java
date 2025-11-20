package net.minecraft.client.color.block;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MaterialColor;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.block.StemBlock;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.state.property.Property;
import net.minecraft.util.collection.IdList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.World;

public class BlockColors {
   private final IdList<BlockColorProvider> providers = new IdList<>(32);
   private final Map<Block, Set<Property<?>>> properties = Maps.newHashMap();

   public BlockColors() {
   }

   public static BlockColors create() {
      BlockColors _snowman = new BlockColors();
      _snowman.registerColorProvider(
         (state, world, pos, tintIndex) -> world != null && pos != null
               ? BiomeColors.getGrassColor(world, state.get(TallPlantBlock.HALF) == DoubleBlockHalf.UPPER ? pos.down() : pos)
               : -1,
         Blocks.LARGE_FERN,
         Blocks.TALL_GRASS
      );
      _snowman.registerColorProperty(TallPlantBlock.HALF, Blocks.LARGE_FERN, Blocks.TALL_GRASS);
      _snowman.registerColorProvider(
         (state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0.5, 1.0),
         Blocks.GRASS_BLOCK,
         Blocks.FERN,
         Blocks.GRASS,
         Blocks.POTTED_FERN
      );
      _snowman.registerColorProvider((state, world, pos, tintIndex) -> FoliageColors.getSpruceColor(), Blocks.SPRUCE_LEAVES);
      _snowman.registerColorProvider((state, world, pos, tintIndex) -> FoliageColors.getBirchColor(), Blocks.BIRCH_LEAVES);
      _snowman.registerColorProvider(
         (state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getDefaultColor(),
         Blocks.OAK_LEAVES,
         Blocks.JUNGLE_LEAVES,
         Blocks.ACACIA_LEAVES,
         Blocks.DARK_OAK_LEAVES,
         Blocks.VINE
      );
      _snowman.registerColorProvider(
         (state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getWaterColor(world, pos) : -1,
         Blocks.WATER,
         Blocks.BUBBLE_COLUMN,
         Blocks.CAULDRON
      );
      _snowman.registerColorProvider((state, world, pos, tintIndex) -> RedstoneWireBlock.getWireColor(state.get(RedstoneWireBlock.POWER)), Blocks.REDSTONE_WIRE);
      _snowman.registerColorProperty(RedstoneWireBlock.POWER, Blocks.REDSTONE_WIRE);
      _snowman.registerColorProvider((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : -1, Blocks.SUGAR_CANE);
      _snowman.registerColorProvider((state, world, pos, tintIndex) -> 14731036, Blocks.ATTACHED_MELON_STEM, Blocks.ATTACHED_PUMPKIN_STEM);
      _snowman.registerColorProvider((state, world, pos, tintIndex) -> {
         int _snowmanx = state.get(StemBlock.AGE);
         int _snowmanx = _snowmanx * 32;
         int _snowmanxx = 255 - _snowmanx * 8;
         int _snowmanxxx = _snowmanx * 4;
         return _snowmanx << 16 | _snowmanxx << 8 | _snowmanxxx;
      }, Blocks.MELON_STEM, Blocks.PUMPKIN_STEM);
      _snowman.registerColorProperty(StemBlock.AGE, Blocks.MELON_STEM, Blocks.PUMPKIN_STEM);
      _snowman.registerColorProvider((state, world, pos, tintIndex) -> world != null && pos != null ? 2129968 : 7455580, Blocks.LILY_PAD);
      return _snowman;
   }

   public int getColor(BlockState state, World world, BlockPos pos) {
      BlockColorProvider _snowman = this.providers.get(Registry.BLOCK.getRawId(state.getBlock()));
      if (_snowman != null) {
         return _snowman.getColor(state, null, null, 0);
      } else {
         MaterialColor _snowmanx = state.getTopMaterialColor(world, pos);
         return _snowmanx != null ? _snowmanx.color : -1;
      }
   }

   public int getColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tint) {
      BlockColorProvider _snowman = this.providers.get(Registry.BLOCK.getRawId(state.getBlock()));
      return _snowman == null ? -1 : _snowman.getColor(state, world, pos, tint);
   }

   public void registerColorProvider(BlockColorProvider provider, Block... blocks) {
      for (Block _snowman : blocks) {
         this.providers.set(provider, Registry.BLOCK.getRawId(_snowman));
      }
   }

   private void registerColorProperties(Set<Property<?>> properties, Block... blocks) {
      for (Block _snowman : blocks) {
         this.properties.put(_snowman, properties);
      }
   }

   private void registerColorProperty(Property<?> property, Block... blocks) {
      this.registerColorProperties(ImmutableSet.of(property), blocks);
   }

   public Set<Property<?>> getProperties(Block block) {
      return this.properties.getOrDefault(block, ImmutableSet.of());
   }
}
