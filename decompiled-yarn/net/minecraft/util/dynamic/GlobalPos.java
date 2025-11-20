package net.minecraft.util.dynamic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public final class GlobalPos {
   public static final Codec<GlobalPos> CODEC = RecordCodecBuilder.create(
      _snowman -> _snowman.group(World.CODEC.fieldOf("dimension").forGetter(GlobalPos::getDimension), BlockPos.CODEC.fieldOf("pos").forGetter(GlobalPos::getPos))
            .apply(_snowman, GlobalPos::create)
   );
   private final RegistryKey<World> dimension;
   private final BlockPos pos;

   private GlobalPos(RegistryKey<World> dimension, BlockPos pos) {
      this.dimension = dimension;
      this.pos = pos;
   }

   public static GlobalPos create(RegistryKey<World> dimension, BlockPos pos) {
      return new GlobalPos(dimension, pos);
   }

   public RegistryKey<World> getDimension() {
      return this.dimension;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         GlobalPos _snowman = (GlobalPos)o;
         return Objects.equals(this.dimension, _snowman.dimension) && Objects.equals(this.pos, _snowman.pos);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.dimension, this.pos);
   }

   @Override
   public String toString() {
      return this.dimension.toString() + " " + this.pos;
   }
}
