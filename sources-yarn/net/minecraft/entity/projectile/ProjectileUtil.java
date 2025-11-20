package net.minecraft.entity.projectile;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public final class ProjectileUtil {
   public static HitResult getCollision(Entity arg, Predicate<Entity> predicate) {
      Vec3d lv = arg.getVelocity();
      World lv2 = arg.world;
      Vec3d lv3 = arg.getPos();
      Vec3d lv4 = lv3.add(lv);
      HitResult lv5 = lv2.raycast(new RaycastContext(lv3, lv4, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, arg));
      if (lv5.getType() != HitResult.Type.MISS) {
         lv4 = lv5.getPos();
      }

      HitResult lv6 = getEntityCollision(lv2, arg, lv3, lv4, arg.getBoundingBox().stretch(arg.getVelocity()).expand(1.0), predicate);
      if (lv6 != null) {
         lv5 = lv6;
      }

      return lv5;
   }

   @Nullable
   @Environment(EnvType.CLIENT)
   public static EntityHitResult raycast(Entity arg, Vec3d arg2, Vec3d arg3, Box arg4, Predicate<Entity> predicate, double d) {
      World lv = arg.world;
      double e = d;
      Entity lv2 = null;
      Vec3d lv3 = null;

      for (Entity lv4 : lv.getOtherEntities(arg, arg4, predicate)) {
         Box lv5 = lv4.getBoundingBox().expand((double)lv4.getTargetingMargin());
         Optional<Vec3d> optional = lv5.raycast(arg2, arg3);
         if (lv5.contains(arg2)) {
            if (e >= 0.0) {
               lv2 = lv4;
               lv3 = optional.orElse(arg2);
               e = 0.0;
            }
         } else if (optional.isPresent()) {
            Vec3d lv6 = optional.get();
            double f = arg2.squaredDistanceTo(lv6);
            if (f < e || e == 0.0) {
               if (lv4.getRootVehicle() == arg.getRootVehicle()) {
                  if (e == 0.0) {
                     lv2 = lv4;
                     lv3 = lv6;
                  }
               } else {
                  lv2 = lv4;
                  lv3 = lv6;
                  e = f;
               }
            }
         }
      }

      return lv2 == null ? null : new EntityHitResult(lv2, lv3);
   }

   @Nullable
   public static EntityHitResult getEntityCollision(World arg, Entity arg2, Vec3d arg3, Vec3d arg4, Box arg5, Predicate<Entity> predicate) {
      double d = Double.MAX_VALUE;
      Entity lv = null;

      for (Entity lv2 : arg.getOtherEntities(arg2, arg5, predicate)) {
         Box lv3 = lv2.getBoundingBox().expand(0.3F);
         Optional<Vec3d> optional = lv3.raycast(arg3, arg4);
         if (optional.isPresent()) {
            double e = arg3.squaredDistanceTo(optional.get());
            if (e < d) {
               lv = lv2;
               d = e;
            }
         }
      }

      return lv == null ? null : new EntityHitResult(lv);
   }

   public static final void method_7484(Entity arg, float f) {
      Vec3d lv = arg.getVelocity();
      if (lv.lengthSquared() != 0.0) {
         float g = MathHelper.sqrt(Entity.squaredHorizontalLength(lv));
         arg.yaw = (float)(MathHelper.atan2(lv.z, lv.x) * 180.0F / (float)Math.PI) + 90.0F;
         arg.pitch = (float)(MathHelper.atan2((double)g, lv.y) * 180.0F / (float)Math.PI) - 90.0F;

         while (arg.pitch - arg.prevPitch < -180.0F) {
            arg.prevPitch -= 360.0F;
         }

         while (arg.pitch - arg.prevPitch >= 180.0F) {
            arg.prevPitch += 360.0F;
         }

         while (arg.yaw - arg.prevYaw < -180.0F) {
            arg.prevYaw -= 360.0F;
         }

         while (arg.yaw - arg.prevYaw >= 180.0F) {
            arg.prevYaw += 360.0F;
         }

         arg.pitch = MathHelper.lerp(f, arg.prevPitch, arg.pitch);
         arg.yaw = MathHelper.lerp(f, arg.prevYaw, arg.yaw);
      }
   }

   public static Hand getHandPossiblyHolding(LivingEntity entity, Item item) {
      return entity.getMainHandStack().getItem() == item ? Hand.MAIN_HAND : Hand.OFF_HAND;
   }

   public static PersistentProjectileEntity createArrowProjectile(LivingEntity entity, ItemStack stack, float damageModifier) {
      ArrowItem lv = (ArrowItem)(stack.getItem() instanceof ArrowItem ? stack.getItem() : Items.ARROW);
      PersistentProjectileEntity lv2 = lv.createArrow(entity.world, stack, entity);
      lv2.applyEnchantmentEffects(entity, damageModifier);
      if (stack.getItem() == Items.TIPPED_ARROW && lv2 instanceof ArrowEntity) {
         ((ArrowEntity)lv2).initFromStack(stack);
      }

      return lv2;
   }
}
