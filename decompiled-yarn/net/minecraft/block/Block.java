package net.minecraft.block;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.collection.IdList;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.explosion.Explosion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Block extends AbstractBlock implements ItemConvertible {
   protected static final Logger LOGGER = LogManager.getLogger();
   public static final IdList<BlockState> STATE_IDS = new IdList<>();
   private static final LoadingCache<VoxelShape, Boolean> FULL_CUBE_SHAPE_CACHE = CacheBuilder.newBuilder()
      .maximumSize(512L)
      .weakKeys()
      .build(new CacheLoader<VoxelShape, Boolean>() {
         public Boolean load(VoxelShape _snowman) {
            return !VoxelShapes.matchesAnywhere(VoxelShapes.fullCube(), _snowman, BooleanBiFunction.NOT_SAME);
         }
      });
   protected final StateManager<Block, BlockState> stateManager;
   private BlockState defaultState;
   @Nullable
   private String translationKey;
   @Nullable
   private Item cachedItem;
   private static final ThreadLocal<Object2ByteLinkedOpenHashMap<Block.NeighborGroup>> FACE_CULL_MAP = ThreadLocal.withInitial(() -> {
      Object2ByteLinkedOpenHashMap<Block.NeighborGroup> _snowman = new Object2ByteLinkedOpenHashMap<Block.NeighborGroup>(2048, 0.25F) {
         protected void rehash(int _snowman) {
         }
      };
      _snowman.defaultReturnValue((byte)127);
      return _snowman;
   });

   public static int getRawIdFromState(@Nullable BlockState state) {
      if (state == null) {
         return 0;
      } else {
         int _snowman = STATE_IDS.getRawId(state);
         return _snowman == -1 ? 0 : _snowman;
      }
   }

   public static BlockState getStateFromRawId(int stateId) {
      BlockState _snowman = STATE_IDS.get(stateId);
      return _snowman == null ? Blocks.AIR.getDefaultState() : _snowman;
   }

   public static Block getBlockFromItem(@Nullable Item item) {
      return item instanceof BlockItem ? ((BlockItem)item).getBlock() : Blocks.AIR;
   }

   public static BlockState pushEntitiesUpBeforeBlockChange(BlockState from, BlockState to, World world, BlockPos pos) {
      VoxelShape _snowman = VoxelShapes.combine(from.getCollisionShape(world, pos), to.getCollisionShape(world, pos), BooleanBiFunction.ONLY_SECOND)
         .offset((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());

      for (Entity _snowmanx : world.getOtherEntities(null, _snowman.getBoundingBox())) {
         double _snowmanxx = VoxelShapes.calculateMaxOffset(Direction.Axis.Y, _snowmanx.getBoundingBox().offset(0.0, 1.0, 0.0), Stream.of(_snowman), -1.0);
         _snowmanx.requestTeleport(_snowmanx.getX(), _snowmanx.getY() + 1.0 + _snowmanxx, _snowmanx.getZ());
      }

      return to;
   }

   public static VoxelShape createCuboidShape(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
      return VoxelShapes.cuboid(xMin / 16.0, yMin / 16.0, zMin / 16.0, xMax / 16.0, yMax / 16.0, zMax / 16.0);
   }

   public boolean isIn(Tag<Block> tag) {
      return tag.contains(this);
   }

   public boolean is(Block block) {
      return this == block;
   }

   public static BlockState postProcessState(BlockState state, WorldAccess world, BlockPos pos) {
      BlockState _snowman = state;
      BlockPos.Mutable _snowmanx = new BlockPos.Mutable();

      for (Direction _snowmanxx : FACINGS) {
         _snowmanx.set(pos, _snowmanxx);
         _snowman = _snowman.getStateForNeighborUpdate(_snowmanxx, world.getBlockState(_snowmanx), world, pos, _snowmanx);
      }

      return _snowman;
   }

   public static void replace(BlockState state, BlockState newState, WorldAccess world, BlockPos pos, int flags) {
      replace(state, newState, world, pos, flags, 512);
   }

   public static void replace(BlockState state, BlockState newState, WorldAccess world, BlockPos pos, int flags, int maxUpdateDepth) {
      if (newState != state) {
         if (newState.isAir()) {
            if (!world.isClient()) {
               world.breakBlock(pos, (flags & 32) == 0, null, maxUpdateDepth);
            }
         } else {
            world.setBlockState(pos, newState, flags & -33, maxUpdateDepth);
         }
      }
   }

   public Block(AbstractBlock.Settings _snowman) {
      super(_snowman);
      StateManager.Builder<Block, BlockState> _snowmanx = new StateManager.Builder<>(this);
      this.appendProperties(_snowmanx);
      this.stateManager = _snowmanx.build(Block::getDefaultState, BlockState::new);
      this.setDefaultState(this.stateManager.getDefaultState());
   }

   public static boolean cannotConnect(Block block) {
      return block instanceof LeavesBlock
         || block == Blocks.BARRIER
         || block == Blocks.CARVED_PUMPKIN
         || block == Blocks.JACK_O_LANTERN
         || block == Blocks.MELON
         || block == Blocks.PUMPKIN
         || block.isIn(BlockTags.SHULKER_BOXES);
   }

   public boolean hasRandomTicks(BlockState state) {
      return this.randomTicks;
   }

   public static boolean shouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction facing) {
      BlockPos _snowman = pos.offset(facing);
      BlockState _snowmanx = world.getBlockState(_snowman);
      if (state.isSideInvisible(_snowmanx, facing)) {
         return false;
      } else if (_snowmanx.isOpaque()) {
         Block.NeighborGroup _snowmanxx = new Block.NeighborGroup(state, _snowmanx, facing);
         Object2ByteLinkedOpenHashMap<Block.NeighborGroup> _snowmanxxx = FACE_CULL_MAP.get();
         byte _snowmanxxxx = _snowmanxxx.getAndMoveToFirst(_snowmanxx);
         if (_snowmanxxxx != 127) {
            return _snowmanxxxx != 0;
         } else {
            VoxelShape _snowmanxxxxx = state.getCullingFace(world, pos, facing);
            VoxelShape _snowmanxxxxxx = _snowmanx.getCullingFace(world, _snowman, facing.getOpposite());
            boolean _snowmanxxxxxxx = VoxelShapes.matchesAnywhere(_snowmanxxxxx, _snowmanxxxxxx, BooleanBiFunction.ONLY_FIRST);
            if (_snowmanxxx.size() == 2048) {
               _snowmanxxx.removeLastByte();
            }

            _snowmanxxx.putAndMoveToFirst(_snowmanxx, (byte)(_snowmanxxxxxxx ? 1 : 0));
            return _snowmanxxxxxxx;
         }
      } else {
         return true;
      }
   }

   public static boolean hasTopRim(BlockView world, BlockPos pos) {
      return world.getBlockState(pos).isSideSolid(world, pos, Direction.UP, SideShapeType.RIGID);
   }

   public static boolean sideCoversSmallSquare(WorldView world, BlockPos pos, Direction side) {
      BlockState _snowman = world.getBlockState(pos);
      return side == Direction.DOWN && _snowman.isIn(BlockTags.UNSTABLE_BOTTOM_CENTER) ? false : _snowman.isSideSolid(world, pos, side, SideShapeType.CENTER);
   }

   public static boolean isFaceFullSquare(VoxelShape shape, Direction side) {
      VoxelShape _snowman = shape.getFace(side);
      return isShapeFullCube(_snowman);
   }

   public static boolean isShapeFullCube(VoxelShape shape) {
      return (Boolean)FULL_CUBE_SHAPE_CACHE.getUnchecked(shape);
   }

   public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
      return !isShapeFullCube(state.getOutlineShape(world, pos)) && state.getFluidState().isEmpty();
   }

   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
   }

   public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
   }

   public static List<ItemStack> getDroppedStacks(BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity) {
      LootContext.Builder _snowman = new LootContext.Builder(world)
         .random(world.random)
         .parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
         .parameter(LootContextParameters.TOOL, ItemStack.EMPTY)
         .optionalParameter(LootContextParameters.BLOCK_ENTITY, blockEntity);
      return state.getDroppedStacks(_snowman);
   }

   public static List<ItemStack> getDroppedStacks(
      BlockState state, ServerWorld world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity, ItemStack stack
   ) {
      LootContext.Builder _snowman = new LootContext.Builder(world)
         .random(world.random)
         .parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos))
         .parameter(LootContextParameters.TOOL, stack)
         .optionalParameter(LootContextParameters.THIS_ENTITY, entity)
         .optionalParameter(LootContextParameters.BLOCK_ENTITY, blockEntity);
      return state.getDroppedStacks(_snowman);
   }

   public static void dropStacks(BlockState state, World world, BlockPos pos) {
      if (world instanceof ServerWorld) {
         getDroppedStacks(state, (ServerWorld)world, pos, null).forEach(stack -> dropStack(world, pos, stack));
         state.onStacksDropped((ServerWorld)world, pos, ItemStack.EMPTY);
      }
   }

   public static void dropStacks(BlockState state, WorldAccess world, BlockPos pos, @Nullable BlockEntity blockEntity) {
      if (world instanceof ServerWorld) {
         getDroppedStacks(state, (ServerWorld)world, pos, blockEntity).forEach(stack -> dropStack((ServerWorld)world, pos, stack));
         state.onStacksDropped((ServerWorld)world, pos, ItemStack.EMPTY);
      }
   }

   public static void dropStacks(BlockState state, World world, BlockPos pos, @Nullable BlockEntity blockEntity, Entity entity, ItemStack stack) {
      if (world instanceof ServerWorld) {
         getDroppedStacks(state, (ServerWorld)world, pos, blockEntity, entity, stack).forEach(_snowmanxx -> dropStack(world, pos, _snowmanxx));
         state.onStacksDropped((ServerWorld)world, pos, stack);
      }
   }

   public static void dropStack(World world, BlockPos pos, ItemStack stack) {
      if (!world.isClient && !stack.isEmpty() && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
         float _snowman = 0.5F;
         double _snowmanx = (double)(world.random.nextFloat() * 0.5F) + 0.25;
         double _snowmanxx = (double)(world.random.nextFloat() * 0.5F) + 0.25;
         double _snowmanxxx = (double)(world.random.nextFloat() * 0.5F) + 0.25;
         ItemEntity _snowmanxxxx = new ItemEntity(world, (double)pos.getX() + _snowmanx, (double)pos.getY() + _snowmanxx, (double)pos.getZ() + _snowmanxxx, stack);
         _snowmanxxxx.setToDefaultPickupDelay();
         world.spawnEntity(_snowmanxxxx);
      }
   }

   protected void dropExperience(ServerWorld world, BlockPos pos, int size) {
      if (world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
         while (size > 0) {
            int _snowman = ExperienceOrbEntity.roundToOrbSize(size);
            size -= _snowman;
            world.spawnEntity(new ExperienceOrbEntity(world, (double)pos.getX() + 0.5, (double)pos.getY() + 0.5, (double)pos.getZ() + 0.5, _snowman));
         }
      }
   }

   public float getBlastResistance() {
      return this.resistance;
   }

   public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
   }

   public void onSteppedOn(World world, BlockPos pos, Entity entity) {
   }

   @Nullable
   public BlockState getPlacementState(ItemPlacementContext ctx) {
      return this.getDefaultState();
   }

   public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
      player.incrementStat(Stats.MINED.getOrCreateStat(this));
      player.addExhaustion(0.005F);
      dropStacks(state, world, pos, blockEntity, player, stack);
   }

   public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
   }

   public boolean canMobSpawnInside() {
      return !this.material.isSolid() && !this.material.isLiquid();
   }

   public MutableText getName() {
      return new TranslatableText(this.getTranslationKey());
   }

   public String getTranslationKey() {
      if (this.translationKey == null) {
         this.translationKey = Util.createTranslationKey("block", Registry.BLOCK.getId(this));
      }

      return this.translationKey;
   }

   public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
      entity.handleFallDamage(distance, 1.0F);
   }

   public void onEntityLand(BlockView world, Entity entity) {
      entity.setVelocity(entity.getVelocity().multiply(1.0, 0.0, 1.0));
   }

   public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
      return new ItemStack(this);
   }

   public void addStacksForDisplay(ItemGroup group, DefaultedList<ItemStack> list) {
      list.add(new ItemStack(this));
   }

   public float getSlipperiness() {
      return this.slipperiness;
   }

   public float getVelocityMultiplier() {
      return this.velocityMultiplier;
   }

   public float getJumpVelocityMultiplier() {
      return this.jumpVelocityMultiplier;
   }

   public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
      world.syncWorldEvent(player, 2001, pos, getRawIdFromState(state));
      if (this.isIn(BlockTags.GUARDED_BY_PIGLINS)) {
         PiglinBrain.onGuardedBlockInteracted(player, false);
      }
   }

   public void rainTick(World world, BlockPos pos) {
   }

   public boolean shouldDropItemsOnExplosion(Explosion explosion) {
      return true;
   }

   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
   }

   public StateManager<Block, BlockState> getStateManager() {
      return this.stateManager;
   }

   protected final void setDefaultState(BlockState state) {
      this.defaultState = state;
   }

   public final BlockState getDefaultState() {
      return this.defaultState;
   }

   public BlockSoundGroup getSoundGroup(BlockState state) {
      return this.soundGroup;
   }

   @Override
   public Item asItem() {
      if (this.cachedItem == null) {
         this.cachedItem = Item.fromBlock(this);
      }

      return this.cachedItem;
   }

   public boolean hasDynamicBounds() {
      return this.dynamicBounds;
   }

   @Override
   public String toString() {
      return "Block{" + Registry.BLOCK.getId(this) + "}";
   }

   public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
   }

   @Override
   protected Block asBlock() {
      return this;
   }

   public static final class NeighborGroup {
      private final BlockState self;
      private final BlockState other;
      private final Direction facing;

      public NeighborGroup(BlockState self, BlockState other, Direction facing) {
         this.self = self;
         this.other = other;
         this.facing = facing;
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof Block.NeighborGroup)) {
            return false;
         } else {
            Block.NeighborGroup _snowman = (Block.NeighborGroup)o;
            return this.self == _snowman.self && this.other == _snowman.other && this.facing == _snowman.facing;
         }
      }

      @Override
      public int hashCode() {
         int _snowman = this.self.hashCode();
         _snowman = 31 * _snowman + this.other.hashCode();
         return 31 * _snowman + this.facing.hashCode();
      }
   }
}
