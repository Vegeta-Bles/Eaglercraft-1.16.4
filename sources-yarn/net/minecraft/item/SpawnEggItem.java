package net.minecraft.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
      World lv = context.getWorld();
      if (!(lv instanceof ServerWorld)) {
         return ActionResult.SUCCESS;
      } else {
         ItemStack lv2 = context.getStack();
         BlockPos lv3 = context.getBlockPos();
         Direction lv4 = context.getSide();
         BlockState lv5 = lv.getBlockState(lv3);
         if (lv5.isOf(Blocks.SPAWNER)) {
            BlockEntity lv6 = lv.getBlockEntity(lv3);
            if (lv6 instanceof MobSpawnerBlockEntity) {
               MobSpawnerLogic lv7 = ((MobSpawnerBlockEntity)lv6).getLogic();
               EntityType<?> lv8 = this.getEntityType(lv2.getTag());
               lv7.setEntityId(lv8);
               lv6.markDirty();
               lv.updateListeners(lv3, lv5, lv5, 3);
               lv2.decrement(1);
               return ActionResult.CONSUME;
            }
         }

         BlockPos lv9;
         if (lv5.getCollisionShape(lv, lv3).isEmpty()) {
            lv9 = lv3;
         } else {
            lv9 = lv3.offset(lv4);
         }

         EntityType<?> lv11 = this.getEntityType(lv2.getTag());
         if (lv11.spawnFromItemStack(
               (ServerWorld)lv, lv2, context.getPlayer(), lv9, SpawnReason.SPAWN_EGG, true, !Objects.equals(lv3, lv9) && lv4 == Direction.UP
            )
            != null) {
            lv2.decrement(1);
         }

         return ActionResult.CONSUME;
      }
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack lv = user.getStackInHand(hand);
      HitResult lv2 = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
      if (lv2.getType() != HitResult.Type.BLOCK) {
         return TypedActionResult.pass(lv);
      } else if (!(world instanceof ServerWorld)) {
         return TypedActionResult.success(lv);
      } else {
         BlockHitResult lv3 = (BlockHitResult)lv2;
         BlockPos lv4 = lv3.getBlockPos();
         if (!(world.getBlockState(lv4).getBlock() instanceof FluidBlock)) {
            return TypedActionResult.pass(lv);
         } else if (world.canPlayerModifyAt(user, lv4) && user.canPlaceOn(lv4, lv3.getSide(), lv)) {
            EntityType<?> lv5 = this.getEntityType(lv.getTag());
            if (lv5.spawnFromItemStack((ServerWorld)world, lv, user, lv4, SpawnReason.SPAWN_EGG, false, false) == null) {
               return TypedActionResult.pass(lv);
            } else {
               if (!user.abilities.creativeMode) {
                  lv.decrement(1);
               }

               user.incrementStat(Stats.USED.getOrCreateStat(this));
               return TypedActionResult.consume(lv);
            }
         } else {
            return TypedActionResult.fail(lv);
         }
      }
   }

   public boolean isOfSameEntityType(@Nullable CompoundTag tag, EntityType<?> type) {
      return Objects.equals(this.getEntityType(tag), type);
   }

   @Environment(EnvType.CLIENT)
   public int getColor(int num) {
      return num == 0 ? this.primaryColor : this.secondaryColor;
   }

   @Nullable
   @Environment(EnvType.CLIENT)
   public static SpawnEggItem forEntity(@Nullable EntityType<?> type) {
      return SPAWN_EGGS.get(type);
   }

   public static Iterable<SpawnEggItem> getAll() {
      return Iterables.unmodifiableIterable(SPAWN_EGGS.values());
   }

   public EntityType<?> getEntityType(@Nullable CompoundTag tag) {
      if (tag != null && tag.contains("EntityTag", 10)) {
         CompoundTag lv = tag.getCompound("EntityTag");
         if (lv.contains("id", 8)) {
            return EntityType.get(lv.getString("id")).orElse(this.type);
         }
      }

      return this.type;
   }

   public Optional<MobEntity> spawnBaby(PlayerEntity user, MobEntity arg2, EntityType<? extends MobEntity> arg3, ServerWorld arg4, Vec3d arg5, ItemStack arg6) {
      if (!this.isOfSameEntityType(arg6.getTag(), arg3)) {
         return Optional.empty();
      } else {
         MobEntity lv;
         if (arg2 instanceof PassiveEntity) {
            lv = ((PassiveEntity)arg2).createChild(arg4, (PassiveEntity)arg2);
         } else {
            lv = arg3.create(arg4);
         }

         if (lv == null) {
            return Optional.empty();
         } else {
            lv.setBaby(true);
            if (!lv.isBaby()) {
               return Optional.empty();
            } else {
               lv.refreshPositionAndAngles(arg5.getX(), arg5.getY(), arg5.getZ(), 0.0F, 0.0F);
               arg4.spawnEntityAndPassengers(lv);
               if (arg6.hasCustomName()) {
                  lv.setCustomName(arg6.getName());
               }

               if (!user.abilities.creativeMode) {
                  arg6.decrement(1);
               }

               return Optional.of(lv);
            }
         }
      }
   }
}
