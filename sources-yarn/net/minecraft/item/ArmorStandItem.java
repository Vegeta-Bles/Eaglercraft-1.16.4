package net.minecraft.item;

import java.util.Random;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.EulerAngle;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ArmorStandItem extends Item {
   public ArmorStandItem(Item.Settings arg) {
      super(arg);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      Direction lv = context.getSide();
      if (lv == Direction.DOWN) {
         return ActionResult.FAIL;
      } else {
         World lv2 = context.getWorld();
         ItemPlacementContext lv3 = new ItemPlacementContext(context);
         BlockPos lv4 = lv3.getBlockPos();
         ItemStack lv5 = context.getStack();
         Vec3d lv6 = Vec3d.ofBottomCenter(lv4);
         Box lv7 = EntityType.ARMOR_STAND.getDimensions().method_30231(lv6.getX(), lv6.getY(), lv6.getZ());
         if (lv2.isSpaceEmpty(null, lv7, arg -> true) && lv2.getOtherEntities(null, lv7).isEmpty()) {
            if (lv2 instanceof ServerWorld) {
               ServerWorld lv8 = (ServerWorld)lv2;
               ArmorStandEntity lv9 = EntityType.ARMOR_STAND.create(lv8, lv5.getTag(), null, context.getPlayer(), lv4, SpawnReason.SPAWN_EGG, true, true);
               if (lv9 == null) {
                  return ActionResult.FAIL;
               }

               lv8.spawnEntityAndPassengers(lv9);
               float f = (float)MathHelper.floor((MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
               lv9.refreshPositionAndAngles(lv9.getX(), lv9.getY(), lv9.getZ(), f, 0.0F);
               this.setRotations(lv9, lv2.random);
               lv2.spawnEntity(lv9);
               lv2.playSound(null, lv9.getX(), lv9.getY(), lv9.getZ(), SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F);
            }

            lv5.decrement(1);
            return ActionResult.success(lv2.isClient);
         } else {
            return ActionResult.FAIL;
         }
      }
   }

   private void setRotations(ArmorStandEntity stand, Random random) {
      EulerAngle lv = stand.getHeadRotation();
      float f = random.nextFloat() * 5.0F;
      float g = random.nextFloat() * 20.0F - 10.0F;
      EulerAngle lv2 = new EulerAngle(lv.getPitch() + f, lv.getYaw() + g, lv.getRoll());
      stand.setHeadRotation(lv2);
      lv = stand.getBodyRotation();
      f = random.nextFloat() * 10.0F - 5.0F;
      lv2 = new EulerAngle(lv.getPitch(), lv.getYaw() + f, lv.getRoll());
      stand.setBodyRotation(lv2);
   }
}
