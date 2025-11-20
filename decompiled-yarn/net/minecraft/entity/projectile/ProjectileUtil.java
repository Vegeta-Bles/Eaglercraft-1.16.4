package net.minecraft.entity.projectile;

import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
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
   public static HitResult getCollision(Entity _snowman, Predicate<Entity> _snowman) {
      Vec3d _snowmanxx = _snowman.getVelocity();
      World _snowmanxxx = _snowman.world;
      Vec3d _snowmanxxxx = _snowman.getPos();
      Vec3d _snowmanxxxxx = _snowmanxxxx.add(_snowmanxx);
      HitResult _snowmanxxxxxx = _snowmanxxx.raycast(new RaycastContext(_snowmanxxxx, _snowmanxxxxx, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, _snowman));
      if (_snowmanxxxxxx.getType() != HitResult.Type.MISS) {
         _snowmanxxxxx = _snowmanxxxxxx.getPos();
      }

      HitResult _snowmanxxxxxxx = getEntityCollision(_snowmanxxx, _snowman, _snowmanxxxx, _snowmanxxxxx, _snowman.getBoundingBox().stretch(_snowman.getVelocity()).expand(1.0), _snowman);
      if (_snowmanxxxxxxx != null) {
         _snowmanxxxxxx = _snowmanxxxxxxx;
      }

      return _snowmanxxxxxx;
   }

   @Nullable
   public static EntityHitResult raycast(Entity _snowman, Vec3d _snowman, Vec3d _snowman, Box _snowman, Predicate<Entity> _snowman, double _snowman) {
      World _snowmanxxxxxx = _snowman.world;
      double _snowmanxxxxxxx = _snowman;
      Entity _snowmanxxxxxxxx = null;
      Vec3d _snowmanxxxxxxxxx = null;

      for (Entity _snowmanxxxxxxxxxx : _snowmanxxxxxx.getOtherEntities(_snowman, _snowman, _snowman)) {
         Box _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.getBoundingBox().expand((double)_snowmanxxxxxxxxxx.getTargetingMargin());
         Optional<Vec3d> _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.raycast(_snowman, _snowman);
         if (_snowmanxxxxxxxxxxx.contains(_snowman)) {
            if (_snowmanxxxxxxx >= 0.0) {
               _snowmanxxxxxxxx = _snowmanxxxxxxxxxx;
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxx.orElse(_snowman);
               _snowmanxxxxxxx = 0.0;
            }
         } else if (_snowmanxxxxxxxxxxxx.isPresent()) {
            Vec3d _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.get();
            double _snowmanxxxxxxxxxxxxxx = _snowman.squaredDistanceTo(_snowmanxxxxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxxxx < _snowmanxxxxxxx || _snowmanxxxxxxx == 0.0) {
               if (_snowmanxxxxxxxxxx.getRootVehicle() == _snowman.getRootVehicle()) {
                  if (_snowmanxxxxxxx == 0.0) {
                     _snowmanxxxxxxxx = _snowmanxxxxxxxxxx;
                     _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxxx;
                  }
               } else {
                  _snowmanxxxxxxxx = _snowmanxxxxxxxxxx;
                  _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxxxx;
                  _snowmanxxxxxxx = _snowmanxxxxxxxxxxxxxx;
               }
            }
         }
      }

      return _snowmanxxxxxxxx == null ? null : new EntityHitResult(_snowmanxxxxxxxx, _snowmanxxxxxxxxx);
   }

   @Nullable
   public static EntityHitResult getEntityCollision(World _snowman, Entity _snowman, Vec3d _snowman, Vec3d _snowman, Box _snowman, Predicate<Entity> _snowman) {
      double _snowmanxxxxxx = Double.MAX_VALUE;
      Entity _snowmanxxxxxxx = null;

      for (Entity _snowmanxxxxxxxx : _snowman.getOtherEntities(_snowman, _snowman, _snowman)) {
         Box _snowmanxxxxxxxxx = _snowmanxxxxxxxx.getBoundingBox().expand(0.3F);
         Optional<Vec3d> _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.raycast(_snowman, _snowman);
         if (_snowmanxxxxxxxxxx.isPresent()) {
            double _snowmanxxxxxxxxxxx = _snowman.squaredDistanceTo(_snowmanxxxxxxxxxx.get());
            if (_snowmanxxxxxxxxxxx < _snowmanxxxxxx) {
               _snowmanxxxxxxx = _snowmanxxxxxxxx;
               _snowmanxxxxxx = _snowmanxxxxxxxxxxx;
            }
         }
      }

      return _snowmanxxxxxxx == null ? null : new EntityHitResult(_snowmanxxxxxxx);
   }

   public static final void method_7484(Entity _snowman, float _snowman) {
      Vec3d _snowmanxx = _snowman.getVelocity();
      if (_snowmanxx.lengthSquared() != 0.0) {
         float _snowmanxxx = MathHelper.sqrt(Entity.squaredHorizontalLength(_snowmanxx));
         _snowman.yaw = (float)(MathHelper.atan2(_snowmanxx.z, _snowmanxx.x) * 180.0F / (float)Math.PI) + 90.0F;
         _snowman.pitch = (float)(MathHelper.atan2((double)_snowmanxxx, _snowmanxx.y) * 180.0F / (float)Math.PI) - 90.0F;

         while (_snowman.pitch - _snowman.prevPitch < -180.0F) {
            _snowman.prevPitch -= 360.0F;
         }

         while (_snowman.pitch - _snowman.prevPitch >= 180.0F) {
            _snowman.prevPitch += 360.0F;
         }

         while (_snowman.yaw - _snowman.prevYaw < -180.0F) {
            _snowman.prevYaw -= 360.0F;
         }

         while (_snowman.yaw - _snowman.prevYaw >= 180.0F) {
            _snowman.prevYaw += 360.0F;
         }

         _snowman.pitch = MathHelper.lerp(_snowman, _snowman.prevPitch, _snowman.pitch);
         _snowman.yaw = MathHelper.lerp(_snowman, _snowman.prevYaw, _snowman.yaw);
      }
   }

   public static Hand getHandPossiblyHolding(LivingEntity entity, Item item) {
      return entity.getMainHandStack().getItem() == item ? Hand.MAIN_HAND : Hand.OFF_HAND;
   }

   public static PersistentProjectileEntity createArrowProjectile(LivingEntity entity, ItemStack stack, float damageModifier) {
      ArrowItem _snowman = (ArrowItem)(stack.getItem() instanceof ArrowItem ? stack.getItem() : Items.ARROW);
      PersistentProjectileEntity _snowmanx = _snowman.createArrow(entity.world, stack, entity);
      _snowmanx.applyEnchantmentEffects(entity, damageModifier);
      if (stack.getItem() == Items.TIPPED_ARROW && _snowmanx instanceof ArrowEntity) {
         ((ArrowEntity)_snowmanx).initFromStack(stack);
      }

      return _snowmanx;
   }
}
