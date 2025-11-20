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
      ServerWorld lv = (ServerWorld)this.skeletonHorse.world;
      LocalDifficulty lv2 = lv.getLocalDifficulty(this.skeletonHorse.getBlockPos());
      this.skeletonHorse.setTrapped(false);
      this.skeletonHorse.setTame(true);
      this.skeletonHorse.setBreedingAge(0);
      LightningEntity lv3 = EntityType.LIGHTNING_BOLT.create(lv);
      lv3.refreshPositionAfterTeleport(this.skeletonHorse.getX(), this.skeletonHorse.getY(), this.skeletonHorse.getZ());
      lv3.setCosmetic(true);
      lv.spawnEntity(lv3);
      SkeletonEntity lv4 = this.getSkeleton(lv2, this.skeletonHorse);
      lv4.startRiding(this.skeletonHorse);
      lv.spawnEntityAndPassengers(lv4);

      for (int i = 0; i < 3; i++) {
         HorseBaseEntity lv5 = this.getHorse(lv2);
         SkeletonEntity lv6 = this.getSkeleton(lv2, lv5);
         lv6.startRiding(lv5);
         lv5.addVelocity(this.skeletonHorse.getRandom().nextGaussian() * 0.5, 0.0, this.skeletonHorse.getRandom().nextGaussian() * 0.5);
         lv.spawnEntityAndPassengers(lv5);
      }
   }

   private HorseBaseEntity getHorse(LocalDifficulty localDifficulty) {
      SkeletonHorseEntity lv = EntityType.SKELETON_HORSE.create(this.skeletonHorse.world);
      lv.initialize((ServerWorld)this.skeletonHorse.world, localDifficulty, SpawnReason.TRIGGERED, null, null);
      lv.updatePosition(this.skeletonHorse.getX(), this.skeletonHorse.getY(), this.skeletonHorse.getZ());
      lv.timeUntilRegen = 60;
      lv.setPersistent();
      lv.setTame(true);
      lv.setBreedingAge(0);
      return lv;
   }

   private SkeletonEntity getSkeleton(LocalDifficulty localDifficulty, HorseBaseEntity vehicle) {
      SkeletonEntity lv = EntityType.SKELETON.create(vehicle.world);
      lv.initialize((ServerWorld)vehicle.world, localDifficulty, SpawnReason.TRIGGERED, null, null);
      lv.updatePosition(vehicle.getX(), vehicle.getY(), vehicle.getZ());
      lv.timeUntilRegen = 60;
      lv.setPersistent();
      if (lv.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
         lv.equipStack(EquipmentSlot.HEAD, new ItemStack(Items.IRON_HELMET));
      }

      lv.equipStack(
         EquipmentSlot.MAINHAND,
         EnchantmentHelper.enchant(
            lv.getRandom(),
            this.method_30768(lv.getMainHandStack()),
            (int)(5.0F + localDifficulty.getClampedLocalDifficulty() * (float)lv.getRandom().nextInt(18)),
            false
         )
      );
      lv.equipStack(
         EquipmentSlot.HEAD,
         EnchantmentHelper.enchant(
            lv.getRandom(),
            this.method_30768(lv.getEquippedStack(EquipmentSlot.HEAD)),
            (int)(5.0F + localDifficulty.getClampedLocalDifficulty() * (float)lv.getRandom().nextInt(18)),
            false
         )
      );
      return lv;
   }

   private ItemStack method_30768(ItemStack arg) {
      arg.removeSubTag("Enchantments");
      return arg;
   }
}
