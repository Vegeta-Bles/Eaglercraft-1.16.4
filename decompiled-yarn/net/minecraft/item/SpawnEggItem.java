package net.minecraft.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class SpawnEggItem extends Item {
   private static final Map<EntityType<?>, SpawnEggItem> SPAWN_EGGS = Maps.newIdentityHashMap();
   private final int primaryColor;
   private final int secondaryColor;
   private final EntityType<?> type;

   public SpawnEggItem(EntityType<?> type, int primaryColor, int secondaryColor, Item.Settings settings) {
      super(settings);
      this.type = type;
      this.primaryColor = primaryColor;
      this.secondaryColor = secondaryColor;
      SPAWN_EGGS.put(type, this);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      World _snowman = context.getWorld();
      if (!(_snowman instanceof ServerWorld)) {
         return ActionResult.SUCCESS;
      } else {
         ItemStack _snowmanx = context.getStack();
         BlockPos _snowmanxx = context.getBlockPos();
         Direction _snowmanxxx = context.getSide();
         BlockState _snowmanxxxx = _snowman.getBlockState(_snowmanxx);
         if (_snowmanxxxx.isOf(Blocks.SPAWNER)) {
            BlockEntity _snowmanxxxxx = _snowman.getBlockEntity(_snowmanxx);
            if (_snowmanxxxxx instanceof MobSpawnerBlockEntity) {
               MobSpawnerLogic _snowmanxxxxxx = ((MobSpawnerBlockEntity)_snowmanxxxxx).getLogic();
               EntityType<?> _snowmanxxxxxxx = this.getEntityType(_snowmanx.getTag());
               _snowmanxxxxxx.setEntityId(_snowmanxxxxxxx);
               _snowmanxxxxx.markDirty();
               _snowman.updateListeners(_snowmanxx, _snowmanxxxx, _snowmanxxxx, 3);
               _snowmanx.decrement(1);
               return ActionResult.CONSUME;
            }
         }

         BlockPos _snowmanxxxxx;
         if (_snowmanxxxx.getCollisionShape(_snowman, _snowmanxx).isEmpty()) {
            _snowmanxxxxx = _snowmanxx;
         } else {
            _snowmanxxxxx = _snowmanxx.offset(_snowmanxxx);
         }

         EntityType<?> _snowmanxxxxxx = this.getEntityType(_snowmanx.getTag());
         if (_snowmanxxxxxx.spawnFromItemStack(
               (ServerWorld)_snowman, _snowmanx, context.getPlayer(), _snowmanxxxxx, SpawnReason.SPAWN_EGG, true, !Objects.equals(_snowmanxx, _snowmanxxxxx) && _snowmanxxx == Direction.UP
            )
            != null) {
            _snowmanx.decrement(1);
         }

         return ActionResult.CONSUME;
      }
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack _snowman = user.getStackInHand(hand);
      HitResult _snowmanx = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
      if (_snowmanx.getType() != HitResult.Type.BLOCK) {
         return TypedActionResult.pass(_snowman);
      } else if (!(world instanceof ServerWorld)) {
         return TypedActionResult.success(_snowman);
      } else {
         BlockHitResult _snowmanxx = (BlockHitResult)_snowmanx;
         BlockPos _snowmanxxx = _snowmanxx.getBlockPos();
         if (!(world.getBlockState(_snowmanxxx).getBlock() instanceof FluidBlock)) {
            return TypedActionResult.pass(_snowman);
         } else if (world.canPlayerModifyAt(user, _snowmanxxx) && user.canPlaceOn(_snowmanxxx, _snowmanxx.getSide(), _snowman)) {
            EntityType<?> _snowmanxxxx = this.getEntityType(_snowman.getTag());
            if (_snowmanxxxx.spawnFromItemStack((ServerWorld)world, _snowman, user, _snowmanxxx, SpawnReason.SPAWN_EGG, false, false) == null) {
               return TypedActionResult.pass(_snowman);
            } else {
               if (!user.abilities.creativeMode) {
                  _snowman.decrement(1);
               }

               user.incrementStat(Stats.USED.getOrCreateStat(this));
               return TypedActionResult.consume(_snowman);
            }
         } else {
            return TypedActionResult.fail(_snowman);
         }
      }
   }

   public boolean isOfSameEntityType(@Nullable CompoundTag tag, EntityType<?> type) {
      return Objects.equals(this.getEntityType(tag), type);
   }

   public int getColor(int num) {
      return num == 0 ? this.primaryColor : this.secondaryColor;
   }

   @Nullable
   public static SpawnEggItem forEntity(@Nullable EntityType<?> type) {
      return SPAWN_EGGS.get(type);
   }

   public static Iterable<SpawnEggItem> getAll() {
      return Iterables.unmodifiableIterable(SPAWN_EGGS.values());
   }

   public EntityType<?> getEntityType(@Nullable CompoundTag tag) {
      if (tag != null && tag.contains("EntityTag", 10)) {
         CompoundTag _snowman = tag.getCompound("EntityTag");
         if (_snowman.contains("id", 8)) {
            return EntityType.get(_snowman.getString("id")).orElse(this.type);
         }
      }

      return this.type;
   }

   public Optional<MobEntity> spawnBaby(PlayerEntity user, MobEntity _snowman, EntityType<? extends MobEntity> _snowman, ServerWorld _snowman, Vec3d _snowman, ItemStack _snowman) {
      if (!this.isOfSameEntityType(_snowman.getTag(), _snowman)) {
         return Optional.empty();
      } else {
         MobEntity _snowmanxxxxx;
         if (_snowman instanceof PassiveEntity) {
            _snowmanxxxxx = ((PassiveEntity)_snowman).createChild(_snowman, (PassiveEntity)_snowman);
         } else {
            _snowmanxxxxx = _snowman.create(_snowman);
         }

         if (_snowmanxxxxx == null) {
            return Optional.empty();
         } else {
            _snowmanxxxxx.setBaby(true);
            if (!_snowmanxxxxx.isBaby()) {
               return Optional.empty();
            } else {
               _snowmanxxxxx.refreshPositionAndAngles(_snowman.getX(), _snowman.getY(), _snowman.getZ(), 0.0F, 0.0F);
               _snowman.spawnEntityAndPassengers(_snowmanxxxxx);
               if (_snowman.hasCustomName()) {
                  _snowmanxxxxx.setCustomName(_snowman.getName());
               }

               if (!user.abilities.creativeMode) {
                  _snowman.decrement(1);
               }

               return Optional.of(_snowmanxxxxx);
            }
         }
      }
   }
}
