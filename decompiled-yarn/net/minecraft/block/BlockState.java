package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.state.property.Property;
import net.minecraft.util.registry.Registry;

public class BlockState extends AbstractBlock.AbstractBlockState {
   public static final Codec<BlockState> CODEC = createCodec(Registry.BLOCK, Block::getDefaultState).stable();

   public BlockState(Block _snowman, ImmutableMap<Property<?>, Comparable<?>> _snowman, MapCodec<BlockState> _snowman) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected BlockState asBlockState() {
      return this;
   }
}
