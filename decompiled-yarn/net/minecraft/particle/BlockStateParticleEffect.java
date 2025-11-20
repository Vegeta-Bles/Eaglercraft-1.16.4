package net.minecraft.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.command.argument.BlockArgumentParser;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.registry.Registry;

public class BlockStateParticleEffect implements ParticleEffect {
   public static final ParticleEffect.Factory<BlockStateParticleEffect> PARAMETERS_FACTORY = new ParticleEffect.Factory<BlockStateParticleEffect>() {
      public BlockStateParticleEffect read(ParticleType<BlockStateParticleEffect> _snowman, StringReader _snowman) throws CommandSyntaxException {
         _snowman.expect(' ');
         return new BlockStateParticleEffect(_snowman, new BlockArgumentParser(_snowman, false).parse(false).getBlockState());
      }

      public BlockStateParticleEffect read(ParticleType<BlockStateParticleEffect> _snowman, PacketByteBuf _snowman) {
         return new BlockStateParticleEffect(_snowman, Block.STATE_IDS.get(_snowman.readVarInt()));
      }
   };
   private final ParticleType<BlockStateParticleEffect> type;
   private final BlockState blockState;

   public static Codec<BlockStateParticleEffect> method_29128(ParticleType<BlockStateParticleEffect> _snowman) {
      return BlockState.CODEC.xmap(_snowmanxx -> new BlockStateParticleEffect(_snowman, _snowmanxx), _snowmanx -> _snowmanx.blockState);
   }

   public BlockStateParticleEffect(ParticleType<BlockStateParticleEffect> type, BlockState blockState) {
      this.type = type;
      this.blockState = blockState;
   }

   @Override
   public void write(PacketByteBuf buf) {
      buf.writeVarInt(Block.STATE_IDS.getRawId(this.blockState));
   }

   @Override
   public String asString() {
      return Registry.PARTICLE_TYPE.getId(this.getType()) + " " + BlockArgumentParser.stringifyBlockState(this.blockState);
   }

   @Override
   public ParticleType<BlockStateParticleEffect> getType() {
      return this.type;
   }

   public BlockState getBlockState() {
      return this.blockState;
   }
}
