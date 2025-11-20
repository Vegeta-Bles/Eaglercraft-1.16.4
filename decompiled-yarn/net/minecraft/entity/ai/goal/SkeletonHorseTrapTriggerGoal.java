package net.minecraft.entity.ai.goal;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.LocalDifficulty;

public class SkeletonHorseTrapTriggerGoal extends Goal {
   private final SkeletonHorseEntity skeletonHorse;

   public SkeletonHorseTrapTriggerGoal(SkeletonHorseEntity skeletonHorse) {
      this.skeletonHorse = skeletonHorse;
   }

   @Override
   public boolean canStart() {
      return this.skeletonHorse.world.isPlayerInRange(this.skeletonHorse.getX(), this.skeletonHorse.getY(), this.skeletonHorse.getZ(), 10.0);
   }

   @Override
   public void tick() {
      ServerWorld _snowman = (ServerWorld)this.skeletonHorse.world;
      LocalDifficulty _snowmanx = _snowman.getLocalDifficulty(this.skeletonHorse.getBlockPos());
      this.skeletonHorse.setTrapped(false);
      this.skeletonHorse.setTame(true);
      this.skeletonHorse.setBreedingAge(0);
      LightningEntity _snowmanxx = EntityType.LIGHTNING_BOLT.create(_snowman);
      _snowmanxx.refreshPositionAfterTeleport(this.skeletonHorse.getX(), this.skeletonHorse.getY(), this.skeletonHorse.getZ());
      _snowmanxx.setCosmetic(true);
      _snowman.spawnEntity(_snowmanxx);
      SkeletonEntity _snowmanxxx = this.getSkeleton(_snowmanx, this.skeletonHorse);
      _snowmanxxx.startRiding(this.skeletonHorse);
      _snowman.spawnEntityAndPassengers(_snowmanxxx);

      for (int _snowmanxxxx = 0; _snowmanxxxx < 3; _snowmanxxxx++) {
         HorseBaseEntity _snowmanxxxxx = this.getHorse(_snowmanx);
         SkeletonEntity _snowmanxxxxxx = this.getSkeleton(_snowmanx, _snowmanxxxxx);
         _snowmanxxxxxx.startRiding(_snowmanxxxxx);
         _snowmanxxxxx.addVelocity(this.skeletonHorse.getRandom().nextGaussian() * 0.5, 0.0, this.skeletonHorse.getRandom().nextGaussian() * 0.5);
         _snowman.spawnEntityAndPassengers(_snowmanxxxxx);
      }
   }

   private HorseBaseEntity getHorse(LocalDifficulty localDifficulty) {
      SkeletonHorseEntity _snowman = EntityType.SKELETON_HORSE.create(this.skeletonHorse.world);
      _snowman.initialize((ServerWorld)this.skeletonHorse.world, localDifficulty, SpawnReason.TRIGGERED, null, null);
      _snowman.updatePosition(this.skeletonHorse.getX(), this.skeletonHorse.getY(), this.skeletonHorse.getZ());
      _snowman.timeUntilRegen = 60;
      _snowman.setPersistent();
      _snowman.setTame(true);
      _snowman.setBreedingAge(0);
      return _snowman;
   }

   private SkeletonEntity getSkeleton(LocalDifficulty localDifficulty, HorseBaseEntity vehicle) {
      SkeletonEntity _snowman = EntityType.SKELETON.create(vehicle.world);
      _snowman.initialize((ServerWorld)vehicle.world, localDifficulty, SpawnReason.TRIGGERED, null, null);
      _snowman.updatePosition(vehicle.getX(), vehicle.getY(), vehicle.getZ());
      _snowman.timeUntilRegen = 60;
      _snowman.setPersistent();
      if (_snowman.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
         _snowman.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
      }

      _snowman.equipStack(
         EquipmentSlot.MAINHAND,
         EnchantmentHelper.enchant(
            _snowman.getRandom(),
            this.method_30768(_snowman.getMainHandStack()),
            (int)(5.0F + localDifficulty.getClampedLocalDifficulty() * (float)_snowman.getRandom().nextInt(18)),
            false
         )
      );
      _snowman.equipStack(
         EquipmentSlot.HEAD,
         EnchantmentHelper.enchant(
            _snowman.getRandom(),
            this.method_30768(_snowman.getEquippedStack(EquipmentSlot.HEAD)),
            (int)(5.0F + localDifficulty.getClampedLocalDifficulty() * (float)_snowman.getRandom().nextInt(18)),
            false
         )
      );
      return _snowman;
   }

   private ItemStack method_30768(ItemStack _snowman) {
      _snowman.removeSubTag("Enchantments");
      return _snowman;
   }
}
