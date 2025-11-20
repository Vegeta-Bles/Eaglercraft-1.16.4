package net.minecraft.fluid;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public final class FluidState extends State<Fluid, FluidState> {
   public static final Codec<FluidState> CODEC = createCodec(Registry.FLUID, Fluid::getDefaultState).stable();

   public FluidState(Fluid fluid, ImmutableMap<Property<?>, Comparable<?>> propertiesMap, MapCodec<FluidState> _snowman) {
      super(fluid, propertiesMap, _snowman);
   }

   public Fluid getFluid() {
      return this.owner;
   }

   public boolean isStill() {
      return this.getFluid().isStill(this);
   }

   public boolean isEmpty() {
      return this.getFluid().isEmpty();
   }

   public float getHeight(BlockView world, BlockPos pos) {
      return this.getFluid().getHeight(this, world, pos);
   }

   public float getHeight() {
      return this.getFluid().getHeight(this);
   }

   public int getLevel() {
      return this.getFluid().getLevel(this);
   }

   public boolean method_15756(BlockView world, BlockPos pos) {
      for (int _snowman = -1; _snowman <= 1; _snowman++) {
         for (int _snowmanx = -1; _snowmanx <= 1; _snowmanx++) {
            BlockPos _snowmanxx = pos.add(_snowman, 0, _snowmanx);
            FluidState _snowmanxxx = world.getFluidState(_snowmanxx);
            if (!_snowmanxxx.getFluid().matchesType(this.getFluid()) && !world.getBlockState(_snowmanxx).isOpaqueFullCube(world, _snowmanxx)) {
               return true;
            }
         }
      }

      return false;
   }

   public void onScheduledTick(World world, BlockPos pos) {
      this.getFluid().onScheduledTick(world, pos, this);
   }

   public void randomDisplayTick(World world, BlockPos pos, Random random) {
      this.getFluid().randomDisplayTick(world, pos, this, random);
   }

   public boolean hasRandomTicks() {
      return this.getFluid().hasRandomTicks();
   }

   public void onRandomTick(World world, BlockPos pos, Random random) {
      this.getFluid().onRandomTick(world, pos, this, random);
   }

   public Vec3d getVelocity(BlockView world, BlockPos pos) {
      return this.getFluid().getVelocity(world, pos, this);
   }

   public BlockState getBlockState() {
      return this.getFluid().toBlockState(this);
   }

   @Nullable
   public ParticleEffect getParticle() {
      return this.getFluid().getParticle();
   }

   public boolean isIn(Tag<Fluid> tag) {
      return this.getFluid().isIn(tag);
   }

   public float getBlastResistance() {
      return this.getFluid().getBlastResistance();
   }

   public boolean canBeReplacedWith(BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
      return this.getFluid().canBeReplacedWith(this, world, pos, fluid, direction);
   }

   public VoxelShape getShape(BlockView world, BlockPos pos) {
      return this.getFluid().getShape(this, world, pos);
   }
}
