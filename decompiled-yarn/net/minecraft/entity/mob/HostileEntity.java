package net.minecraft.entity.mob;

import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public abstract class HostileEntity extends PathAwareEntity implements Monster {
   protected HostileEntity(EntityType<? extends HostileEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.experiencePoints = 5;
   }

   @Override
   public SoundCategory getSoundCategory() {
      return SoundCategory.HOSTILE;
   }

   @Override
   public void tickMovement() {
      this.tickHandSwing();
      this.updateDespawnCounter();
      super.tickMovement();
   }

   protected void updateDespawnCounter() {
      float _snowman = this.getBrightnessAtEyes();
      if (_snowman > 0.5F) {
         this.despawnCounter += 2;
      }
   }

   @Override
   protected boolean isDisallowedInPeaceful() {
      return true;
   }

   @Override
   protected SoundEvent getSwimSound() {
      return SoundEvents.ENTITY_HOSTILE_SWIM;
   }

   @Override
   protected SoundEvent getSplashSound() {
      return SoundEvents.ENTITY_HOSTILE_SPLASH;
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      return this.isInvulnerableTo(source) ? false : super.damage(source, amount);
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_HOSTILE_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_HOSTILE_DEATH;
   }

   @Override
   protected SoundEvent getFallSound(int distance) {
      return distance > 4 ? SoundEvents.ENTITY_HOSTILE_BIG_FALL : SoundEvents.ENTITY_HOSTILE_SMALL_FALL;
   }

   @Override
   public float getPathfindingFavor(BlockPos pos, WorldView world) {
      return 0.5F - world.getBrightness(pos);
   }

   public static boolean isSpawnDark(ServerWorldAccess _snowman, BlockPos pos, Random random) {
      if (_snowman.getLightLevel(LightType.SKY, pos) > random.nextInt(32)) {
         return false;
      } else {
         int _snowmanx = _snowman.toServerWorld().isThundering() ? _snowman.getLightLevel(pos, 10) : _snowman.getLightLevel(pos);
         return _snowmanx <= random.nextInt(8);
      }
   }

   public static boolean canSpawnInDark(EntityType<? extends HostileEntity> type, ServerWorldAccess _snowman, SpawnReason spawnReason, BlockPos pos, Random random) {
      return _snowman.getDifficulty() != Difficulty.PEACEFUL && isSpawnDark(_snowman, pos, random) && canMobSpawn(type, _snowman, spawnReason, pos, random);
   }

   public static boolean canSpawnIgnoreLightLevel(
      EntityType<? extends HostileEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random
   ) {
      return world.getDifficulty() != Difficulty.PEACEFUL && canMobSpawn(type, world, spawnReason, pos, random);
   }

   public static DefaultAttributeContainer.Builder createHostileAttributes() {
      return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_ATTACK_DAMAGE);
   }

   @Override
   protected boolean canDropLootAndXp() {
      return true;
   }

   @Override
   protected boolean shouldDropLoot() {
      return true;
   }

   public boolean isAngryAt(PlayerEntity player) {
      return true;
   }

   @Override
   public ItemStack getArrowType(ItemStack stack) {
      if (stack.getItem() instanceof RangedWeaponItem) {
         Predicate<ItemStack> _snowman = ((RangedWeaponItem)stack.getItem()).getHeldProjectiles();
         ItemStack _snowmanx = RangedWeaponItem.getHeldProjectile(this, _snowman);
         return _snowmanx.isEmpty() ? new ItemStack(Items.ARROW) : _snowmanx;
      } else {
         return ItemStack.EMPTY;
      }
   }
}
