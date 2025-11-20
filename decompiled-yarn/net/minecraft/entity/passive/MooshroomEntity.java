package net.minecraft.entity.passive;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.apache.commons.lang3.tuple.Pair;

public class MooshroomEntity extends CowEntity implements Shearable {
   private static final TrackedData<String> TYPE = DataTracker.registerData(MooshroomEntity.class, TrackedDataHandlerRegistry.STRING);
   private StatusEffect stewEffect;
   private int stewEffectDuration;
   private UUID lightningId;

   public MooshroomEntity(EntityType<? extends MooshroomEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   public float getPathfindingFavor(BlockPos pos, WorldView world) {
      return world.getBlockState(pos.down()).isOf(Blocks.MYCELIUM) ? 10.0F : world.getBrightness(pos) - 0.5F;
   }

   public static boolean canSpawn(EntityType<MooshroomEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
      return world.getBlockState(pos.down()).isOf(Blocks.MYCELIUM) && world.getBaseLightLevel(pos, 0) > 8;
   }

   @Override
   public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
      UUID _snowman = lightning.getUuid();
      if (!_snowman.equals(this.lightningId)) {
         this.setType(this.getMooshroomType() == MooshroomEntity.Type.RED ? MooshroomEntity.Type.BROWN : MooshroomEntity.Type.RED);
         this.lightningId = _snowman;
         this.playSound(SoundEvents.ENTITY_MOOSHROOM_CONVERT, 2.0F, 1.0F);
      }
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(TYPE, MooshroomEntity.Type.RED.name);
   }

   @Override
   public ActionResult interactMob(PlayerEntity player, Hand hand) {
      ItemStack _snowman = player.getStackInHand(hand);
      if (_snowman.getItem() == Items.BOWL && !this.isBaby()) {
         boolean _snowmanx = false;
         ItemStack _snowmanxx;
         if (this.stewEffect != null) {
            _snowmanx = true;
            _snowmanxx = new ItemStack(Items.SUSPICIOUS_STEW);
            SuspiciousStewItem.addEffectToStew(_snowmanxx, this.stewEffect, this.stewEffectDuration);
            this.stewEffect = null;
            this.stewEffectDuration = 0;
         } else {
            _snowmanxx = new ItemStack(Items.MUSHROOM_STEW);
         }

         ItemStack _snowmanxxx = ItemUsage.method_30270(_snowman, player, _snowmanxx, false);
         player.setStackInHand(hand, _snowmanxxx);
         SoundEvent _snowmanxxxx;
         if (_snowmanx) {
            _snowmanxxxx = SoundEvents.ENTITY_MOOSHROOM_SUSPICIOUS_MILK;
         } else {
            _snowmanxxxx = SoundEvents.ENTITY_MOOSHROOM_MILK;
         }

         this.playSound(_snowmanxxxx, 1.0F, 1.0F);
         return ActionResult.success(this.world.isClient);
      } else if (_snowman.getItem() == Items.SHEARS && this.isShearable()) {
         this.sheared(SoundCategory.PLAYERS);
         if (!this.world.isClient) {
            _snowman.damage(1, player, _snowmanxxx -> _snowmanxxx.sendToolBreakStatus(hand));
         }

         return ActionResult.success(this.world.isClient);
      } else if (this.getMooshroomType() == MooshroomEntity.Type.BROWN && _snowman.getItem().isIn(ItemTags.SMALL_FLOWERS)) {
         if (this.stewEffect != null) {
            for (int _snowmanxxx = 0; _snowmanxxx < 2; _snowmanxxx++) {
               this.world
                  .addParticle(
                     ParticleTypes.SMOKE,
                     this.getX() + this.random.nextDouble() / 2.0,
                     this.getBodyY(0.5),
                     this.getZ() + this.random.nextDouble() / 2.0,
                     0.0,
                     this.random.nextDouble() / 5.0,
                     0.0
                  );
            }
         } else {
            Optional<Pair<StatusEffect, Integer>> _snowmanxxx = this.getStewEffectFrom(_snowman);
            if (!_snowmanxxx.isPresent()) {
               return ActionResult.PASS;
            }

            Pair<StatusEffect, Integer> _snowmanxxxx = _snowmanxxx.get();
            if (!player.abilities.creativeMode) {
               _snowman.decrement(1);
            }

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < 4; _snowmanxxxxx++) {
               this.world
                  .addParticle(
                     ParticleTypes.EFFECT,
                     this.getX() + this.random.nextDouble() / 2.0,
                     this.getBodyY(0.5),
                     this.getZ() + this.random.nextDouble() / 2.0,
                     0.0,
                     this.random.nextDouble() / 5.0,
                     0.0
                  );
            }

            this.stewEffect = (StatusEffect)_snowmanxxxx.getLeft();
            this.stewEffectDuration = (Integer)_snowmanxxxx.getRight();
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_EAT, 2.0F, 1.0F);
         }

         return ActionResult.success(this.world.isClient);
      } else {
         return super.interactMob(player, hand);
      }
   }

   @Override
   public void sheared(SoundCategory shearedSoundCategory) {
      this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_MOOSHROOM_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
      if (!this.world.isClient()) {
         ((ServerWorld)this.world).spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getBodyY(0.5), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
         this.remove();
         CowEntity _snowman = EntityType.COW.create(this.world);
         _snowman.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch);
         _snowman.setHealth(this.getHealth());
         _snowman.bodyYaw = this.bodyYaw;
         if (this.hasCustomName()) {
            _snowman.setCustomName(this.getCustomName());
            _snowman.setCustomNameVisible(this.isCustomNameVisible());
         }

         if (this.isPersistent()) {
            _snowman.setPersistent();
         }

         _snowman.setInvulnerable(this.isInvulnerable());
         this.world.spawnEntity(_snowman);

         for (int _snowmanx = 0; _snowmanx < 5; _snowmanx++) {
            this.world
               .spawnEntity(
                  new ItemEntity(this.world, this.getX(), this.getBodyY(1.0), this.getZ(), new ItemStack(this.getMooshroomType().mushroom.getBlock()))
               );
         }
      }
   }

   @Override
   public boolean isShearable() {
      return this.isAlive() && !this.isBaby();
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putString("Type", this.getMooshroomType().name);
      if (this.stewEffect != null) {
         tag.putByte("EffectId", (byte)StatusEffect.getRawId(this.stewEffect));
         tag.putInt("EffectDuration", this.stewEffectDuration);
      }
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.setType(MooshroomEntity.Type.fromName(tag.getString("Type")));
      if (tag.contains("EffectId", 1)) {
         this.stewEffect = StatusEffect.byRawId(tag.getByte("EffectId"));
      }

      if (tag.contains("EffectDuration", 3)) {
         this.stewEffectDuration = tag.getInt("EffectDuration");
      }
   }

   private Optional<Pair<StatusEffect, Integer>> getStewEffectFrom(ItemStack flower) {
      Item _snowman = flower.getItem();
      if (_snowman instanceof BlockItem) {
         Block _snowmanx = ((BlockItem)_snowman).getBlock();
         if (_snowmanx instanceof FlowerBlock) {
            FlowerBlock _snowmanxx = (FlowerBlock)_snowmanx;
            return Optional.of(Pair.of(_snowmanxx.getEffectInStew(), _snowmanxx.getEffectInStewDuration()));
         }
      }

      return Optional.empty();
   }

   private void setType(MooshroomEntity.Type type) {
      this.dataTracker.set(TYPE, type.name);
   }

   public MooshroomEntity.Type getMooshroomType() {
      return MooshroomEntity.Type.fromName(this.dataTracker.get(TYPE));
   }

   public MooshroomEntity createChild(ServerWorld _snowman, PassiveEntity _snowman) {
      MooshroomEntity _snowmanxx = EntityType.MOOSHROOM.create(_snowman);
      _snowmanxx.setType(this.chooseBabyType((MooshroomEntity)_snowman));
      return _snowmanxx;
   }

   private MooshroomEntity.Type chooseBabyType(MooshroomEntity mooshroom) {
      MooshroomEntity.Type _snowman = this.getMooshroomType();
      MooshroomEntity.Type _snowmanx = mooshroom.getMooshroomType();
      MooshroomEntity.Type _snowmanxx;
      if (_snowman == _snowmanx && this.random.nextInt(1024) == 0) {
         _snowmanxx = _snowman == MooshroomEntity.Type.BROWN ? MooshroomEntity.Type.RED : MooshroomEntity.Type.BROWN;
      } else {
         _snowmanxx = this.random.nextBoolean() ? _snowman : _snowmanx;
      }

      return _snowmanxx;
   }

   public static enum Type {
      RED("red", Blocks.RED_MUSHROOM.getDefaultState()),
      BROWN("brown", Blocks.BROWN_MUSHROOM.getDefaultState());

      private final String name;
      private final BlockState mushroom;

      private Type(String name, BlockState mushroom) {
         this.name = name;
         this.mushroom = mushroom;
      }

      public BlockState getMushroomState() {
         return this.mushroom;
      }

      private static MooshroomEntity.Type fromName(String name) {
         for (MooshroomEntity.Type _snowman : values()) {
            if (_snowman.name.equals(name)) {
               return _snowman;
            }
         }

         return RED;
      }
   }
}
