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
   public ArmorStandItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      Direction _snowman = context.getSide();
      if (_snowman == Direction.DOWN) {
         return ActionResult.FAIL;
      } else {
         World _snowmanx = context.getWorld();
         ItemPlacementContext _snowmanxx = new ItemPlacementContext(context);
         BlockPos _snowmanxxx = _snowmanxx.getBlockPos();
         ItemStack _snowmanxxxx = context.getStack();
         Vec3d _snowmanxxxxx = Vec3d.ofBottomCenter(_snowmanxxx);
         Box _snowmanxxxxxx = EntityType.ARMOR_STAND.getDimensions().method_30231(_snowmanxxxxx.getX(), _snowmanxxxxx.getY(), _snowmanxxxxx.getZ());
         if (_snowmanx.isSpaceEmpty(null, _snowmanxxxxxx, _snowmanxxxxxxx -> true) && _snowmanx.getOtherEntities(null, _snowmanxxxxxx).isEmpty()) {
            if (_snowmanx instanceof ServerWorld) {
               ServerWorld _snowmanxxxxxxx = (ServerWorld)_snowmanx;
               ArmorStandEntity _snowmanxxxxxxxx = EntityType.ARMOR_STAND
                  .create(_snowmanxxxxxxx, _snowmanxxxx.getTag(), null, context.getPlayer(), _snowmanxxx, SpawnReason.SPAWN_EGG, true, true);
               if (_snowmanxxxxxxxx == null) {
                  return ActionResult.FAIL;
               }

               _snowmanxxxxxxx.spawnEntityAndPassengers(_snowmanxxxxxxxx);
               float _snowmanxxxxxxxxx = (float)MathHelper.floor((MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
               _snowmanxxxxxxxx.refreshPositionAndAngles(_snowmanxxxxxxxx.getX(), _snowmanxxxxxxxx.getY(), _snowmanxxxxxxxx.getZ(), _snowmanxxxxxxxxx, 0.0F);
               this.setRotations(_snowmanxxxxxxxx, _snowmanx.random);
               _snowmanx.spawnEntity(_snowmanxxxxxxxx);
               _snowmanx.playSound(null, _snowmanxxxxxxxx.getX(), _snowmanxxxxxxxx.getY(), _snowmanxxxxxxxx.getZ(), SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F);
            }

            _snowmanxxxx.decrement(1);
            return ActionResult.success(_snowmanx.isClient);
         } else {
            return ActionResult.FAIL;
         }
      }
   }

   private void setRotations(ArmorStandEntity stand, Random random) {
      EulerAngle _snowman = stand.getHeadRotation();
      float _snowmanx = random.nextFloat() * 5.0F;
      float _snowmanxx = random.nextFloat() * 20.0F - 10.0F;
      EulerAngle _snowmanxxx = new EulerAngle(_snowman.getPitch() + _snowmanx, _snowman.getYaw() + _snowmanxx, _snowman.getRoll());
      stand.setHeadRotation(_snowmanxxx);
      _snowman = stand.getBodyRotation();
      _snowmanx = random.nextFloat() * 10.0F - 5.0F;
      _snowmanxxx = new EulerAngle(_snowman.getPitch(), _snowman.getYaw() + _snowmanx, _snowman.getRoll());
      stand.setBodyRotation(_snowmanxxx);
   }
}
