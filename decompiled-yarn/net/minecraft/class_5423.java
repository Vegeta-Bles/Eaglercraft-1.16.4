package net.minecraft;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.EntityView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;

public interface class_5423 extends EntityView, WorldView, ModifiableTestableWorld {
   @Override
   default Stream<VoxelShape> getEntityCollisions(@Nullable Entity _snowman, Box _snowman, Predicate<Entity> _snowman) {
      return EntityView.super.getEntityCollisions(_snowman, _snowman, _snowman);
   }

   @Override
   default boolean intersectsEntities(@Nullable Entity entity, VoxelShape shape) {
      return EntityView.super.intersectsEntities(entity, shape);
   }

   @Override
   default BlockPos getTopPosition(Heightmap.Type heightmap, BlockPos pos) {
      return WorldView.super.getTopPosition(heightmap, pos);
   }

   DynamicRegistryManager getRegistryManager();

   default Optional<RegistryKey<Biome>> method_31081(BlockPos _snowman) {
      return this.getRegistryManager().get(Registry.BIOME_KEY).getKey(this.getBiome(_snowman));
   }
}
