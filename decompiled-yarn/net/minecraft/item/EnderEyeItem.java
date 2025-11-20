package net.minecraft.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.StructureFeature;

public class EnderEyeItem extends Item {
   public EnderEyeItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      World _snowman = context.getWorld();
      BlockPos _snowmanx = context.getBlockPos();
      BlockState _snowmanxx = _snowman.getBlockState(_snowmanx);
      if (!_snowmanxx.isOf(Blocks.END_PORTAL_FRAME) || _snowmanxx.get(EndPortalFrameBlock.EYE)) {
         return ActionResult.PASS;
      } else if (_snowman.isClient) {
         return ActionResult.SUCCESS;
      } else {
         BlockState _snowmanxxx = _snowmanxx.with(EndPortalFrameBlock.EYE, Boolean.valueOf(true));
         Block.pushEntitiesUpBeforeBlockChange(_snowmanxx, _snowmanxxx, _snowman, _snowmanx);
         _snowman.setBlockState(_snowmanx, _snowmanxxx, 2);
         _snowman.updateComparators(_snowmanx, Blocks.END_PORTAL_FRAME);
         context.getStack().decrement(1);
         _snowman.syncWorldEvent(1503, _snowmanx, 0);
         BlockPattern.Result _snowmanxxxx = EndPortalFrameBlock.getCompletedFramePattern().searchAround(_snowman, _snowmanx);
         if (_snowmanxxxx != null) {
            BlockPos _snowmanxxxxx = _snowmanxxxx.getFrontTopLeft().add(-3, 0, -3);

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 3; _snowmanxxxxxx++) {
               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 3; _snowmanxxxxxxx++) {
                  _snowman.setBlockState(_snowmanxxxxx.add(_snowmanxxxxxx, 0, _snowmanxxxxxxx), Blocks.END_PORTAL.getDefaultState(), 2);
               }
            }

            _snowman.syncGlobalEvent(1038, _snowmanxxxxx.add(1, 0, 1), 0);
         }

         return ActionResult.CONSUME;
      }
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack _snowman = user.getStackInHand(hand);
      HitResult _snowmanx = raycast(world, user, RaycastContext.FluidHandling.NONE);
      if (_snowmanx.getType() == HitResult.Type.BLOCK && world.getBlockState(((BlockHitResult)_snowmanx).getBlockPos()).isOf(Blocks.END_PORTAL_FRAME)) {
         return TypedActionResult.pass(_snowman);
      } else {
         user.setCurrentHand(hand);
         if (world instanceof ServerWorld) {
            BlockPos _snowmanxx = ((ServerWorld)world)
               .getChunkManager()
               .getChunkGenerator()
               .locateStructure((ServerWorld)world, StructureFeature.STRONGHOLD, user.getBlockPos(), 100, false);
            if (_snowmanxx != null) {
               EyeOfEnderEntity _snowmanxxx = new EyeOfEnderEntity(world, user.getX(), user.getBodyY(0.5), user.getZ());
               _snowmanxxx.setItem(_snowman);
               _snowmanxxx.initTargetPos(_snowmanxx);
               world.spawnEntity(_snowmanxxx);
               if (user instanceof ServerPlayerEntity) {
                  Criteria.USED_ENDER_EYE.trigger((ServerPlayerEntity)user, _snowmanxx);
               }

               world.playSound(
                  null,
                  user.getX(),
                  user.getY(),
                  user.getZ(),
                  SoundEvents.ENTITY_ENDER_EYE_LAUNCH,
                  SoundCategory.NEUTRAL,
                  0.5F,
                  0.4F / (RANDOM.nextFloat() * 0.4F + 0.8F)
               );
               world.syncWorldEvent(null, 1003, user.getBlockPos(), 0);
               if (!user.abilities.creativeMode) {
                  _snowman.decrement(1);
               }

               user.incrementStat(Stats.USED.getOrCreateStat(this));
               user.swingHand(hand, true);
               return TypedActionResult.success(_snowman);
            }
         }

         return TypedActionResult.consume(_snowman);
      }
   }
}
