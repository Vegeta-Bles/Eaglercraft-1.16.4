package net.minecraft.entity.boss.dragon;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathMinHeap;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.phase.Phase;
import net.minecraft.entity.boss.dragon.phase.PhaseManager;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.EndPortalFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnderDragonEntity extends MobEntity implements Monster {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final TrackedData<Integer> PHASE_TYPE = DataTracker.registerData(EnderDragonEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TargetPredicate CLOSE_PLAYER_PREDICATE = new TargetPredicate().setBaseMaxDistance(64.0);
   public final double[][] segmentCircularBuffer = new double[64][3];
   public int latestSegment = -1;
   private final EnderDragonPart[] parts;
   public final EnderDragonPart partHead;
   private final EnderDragonPart partNeck;
   private final EnderDragonPart partBody;
   private final EnderDragonPart partTail1;
   private final EnderDragonPart partTail2;
   private final EnderDragonPart partTail3;
   private final EnderDragonPart partWingRight;
   private final EnderDragonPart partWingLeft;
   public float prevWingPosition;
   public float wingPosition;
   public boolean slowedDownByBlock;
   public int ticksSinceDeath;
   public float field_20865;
   @Nullable
   public EndCrystalEntity connectedCrystal;
   @Nullable
   private final EnderDragonFight fight;
   private final PhaseManager phaseManager;
   private int ticksUntilNextGrowl = 100;
   private int field_7029;
   private final PathNode[] pathNodes = new PathNode[24];
   private final int[] pathNodeConnections = new int[24];
   private final PathMinHeap pathHeap = new PathMinHeap();

   public EnderDragonEntity(EntityType<? extends EnderDragonEntity> _snowman, World _snowman) {
      super(EntityType.ENDER_DRAGON, _snowman);
      this.partHead = new EnderDragonPart(this, "head", 1.0F, 1.0F);
      this.partNeck = new EnderDragonPart(this, "neck", 3.0F, 3.0F);
      this.partBody = new EnderDragonPart(this, "body", 5.0F, 3.0F);
      this.partTail1 = new EnderDragonPart(this, "tail", 2.0F, 2.0F);
      this.partTail2 = new EnderDragonPart(this, "tail", 2.0F, 2.0F);
      this.partTail3 = new EnderDragonPart(this, "tail", 2.0F, 2.0F);
      this.partWingRight = new EnderDragonPart(this, "wing", 4.0F, 2.0F);
      this.partWingLeft = new EnderDragonPart(this, "wing", 4.0F, 2.0F);
      this.parts = new EnderDragonPart[]{
         this.partHead, this.partNeck, this.partBody, this.partTail1, this.partTail2, this.partTail3, this.partWingRight, this.partWingLeft
      };
      this.setHealth(this.getMaxHealth());
      this.noClip = true;
      this.ignoreCameraFrustum = true;
      if (_snowman instanceof ServerWorld) {
         this.fight = ((ServerWorld)_snowman).getEnderDragonFight();
      } else {
         this.fight = null;
      }

      this.phaseManager = new PhaseManager(this);
   }

   public static DefaultAttributeContainer.Builder createEnderDragonAttributes() {
      return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0);
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.getDataTracker().startTracking(PHASE_TYPE, PhaseType.HOVER.getTypeId());
   }

   public double[] getSegmentProperties(int segmentNumber, float tickDelta) {
      if (this.isDead()) {
         tickDelta = 0.0F;
      }

      tickDelta = 1.0F - tickDelta;
      int _snowman = this.latestSegment - segmentNumber & 63;
      int _snowmanx = this.latestSegment - segmentNumber - 1 & 63;
      double[] _snowmanxx = new double[3];
      double _snowmanxxx = this.segmentCircularBuffer[_snowman][0];
      double _snowmanxxxx = MathHelper.wrapDegrees(this.segmentCircularBuffer[_snowmanx][0] - _snowmanxxx);
      _snowmanxx[0] = _snowmanxxx + _snowmanxxxx * (double)tickDelta;
      _snowmanxxx = this.segmentCircularBuffer[_snowman][1];
      _snowmanxxxx = this.segmentCircularBuffer[_snowmanx][1] - _snowmanxxx;
      _snowmanxx[1] = _snowmanxxx + _snowmanxxxx * (double)tickDelta;
      _snowmanxx[2] = MathHelper.lerp((double)tickDelta, this.segmentCircularBuffer[_snowman][2], this.segmentCircularBuffer[_snowmanx][2]);
      return _snowmanxx;
   }

   @Override
   public void tickMovement() {
      if (this.world.isClient) {
         this.setHealth(this.getHealth());
         if (!this.isSilent()) {
            float _snowman = MathHelper.cos(this.wingPosition * (float) (Math.PI * 2));
            float _snowmanx = MathHelper.cos(this.prevWingPosition * (float) (Math.PI * 2));
            if (_snowmanx <= -0.3F && _snowman >= -0.3F) {
               this.world
                  .playSound(
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     SoundEvents.ENTITY_ENDER_DRAGON_FLAP,
                     this.getSoundCategory(),
                     5.0F,
                     0.8F + this.random.nextFloat() * 0.3F,
                     false
                  );
            }

            if (!this.phaseManager.getCurrent().isSittingOrHovering() && --this.ticksUntilNextGrowl < 0) {
               this.world
                  .playSound(
                     this.getX(),
                     this.getY(),
                     this.getZ(),
                     SoundEvents.ENTITY_ENDER_DRAGON_GROWL,
                     this.getSoundCategory(),
                     2.5F,
                     0.8F + this.random.nextFloat() * 0.3F,
                     false
                  );
               this.ticksUntilNextGrowl = 200 + this.random.nextInt(200);
            }
         }
      }

      this.prevWingPosition = this.wingPosition;
      if (this.isDead()) {
         float _snowmanxx = (this.random.nextFloat() - 0.5F) * 8.0F;
         float _snowmanxxx = (this.random.nextFloat() - 0.5F) * 4.0F;
         float _snowmanxxxx = (this.random.nextFloat() - 0.5F) * 8.0F;
         this.world
            .addParticle(ParticleTypes.EXPLOSION, this.getX() + (double)_snowmanxx, this.getY() + 2.0 + (double)_snowmanxxx, this.getZ() + (double)_snowmanxxxx, 0.0, 0.0, 0.0);
      } else {
         this.tickWithEndCrystals();
         Vec3d _snowmanxx = this.getVelocity();
         float _snowmanxxx = 0.2F / (MathHelper.sqrt(squaredHorizontalLength(_snowmanxx)) * 10.0F + 1.0F);
         _snowmanxxx *= (float)Math.pow(2.0, _snowmanxx.y);
         if (this.phaseManager.getCurrent().isSittingOrHovering()) {
            this.wingPosition += 0.1F;
         } else if (this.slowedDownByBlock) {
            this.wingPosition += _snowmanxxx * 0.5F;
         } else {
            this.wingPosition += _snowmanxxx;
         }

         this.yaw = MathHelper.wrapDegrees(this.yaw);
         if (this.isAiDisabled()) {
            this.wingPosition = 0.5F;
         } else {
            if (this.latestSegment < 0) {
               for (int _snowmanxxxx = 0; _snowmanxxxx < this.segmentCircularBuffer.length; _snowmanxxxx++) {
                  this.segmentCircularBuffer[_snowmanxxxx][0] = (double)this.yaw;
                  this.segmentCircularBuffer[_snowmanxxxx][1] = this.getY();
               }
            }

            if (++this.latestSegment == this.segmentCircularBuffer.length) {
               this.latestSegment = 0;
            }

            this.segmentCircularBuffer[this.latestSegment][0] = (double)this.yaw;
            this.segmentCircularBuffer[this.latestSegment][1] = this.getY();
            if (this.world.isClient) {
               if (this.bodyTrackingIncrements > 0) {
                  double _snowmanxxxx = this.getX() + (this.serverX - this.getX()) / (double)this.bodyTrackingIncrements;
                  double _snowmanxxxxx = this.getY() + (this.serverY - this.getY()) / (double)this.bodyTrackingIncrements;
                  double _snowmanxxxxxx = this.getZ() + (this.serverZ - this.getZ()) / (double)this.bodyTrackingIncrements;
                  double _snowmanxxxxxxx = MathHelper.wrapDegrees(this.serverYaw - (double)this.yaw);
                  this.yaw = (float)((double)this.yaw + _snowmanxxxxxxx / (double)this.bodyTrackingIncrements);
                  this.pitch = (float)((double)this.pitch + (this.serverPitch - (double)this.pitch) / (double)this.bodyTrackingIncrements);
                  this.bodyTrackingIncrements--;
                  this.updatePosition(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
                  this.setRotation(this.yaw, this.pitch);
               }

               this.phaseManager.getCurrent().clientTick();
            } else {
               Phase _snowmanxxxx = this.phaseManager.getCurrent();
               _snowmanxxxx.serverTick();
               if (this.phaseManager.getCurrent() != _snowmanxxxx) {
                  _snowmanxxxx = this.phaseManager.getCurrent();
                  _snowmanxxxx.serverTick();
               }

               Vec3d _snowmanxxxxx = _snowmanxxxx.getTarget();
               if (_snowmanxxxxx != null) {
                  double _snowmanxxxxxx = _snowmanxxxxx.x - this.getX();
                  double _snowmanxxxxxxx = _snowmanxxxxx.y - this.getY();
                  double _snowmanxxxxxxxx = _snowmanxxxxx.z - this.getZ();
                  double _snowmanxxxxxxxxx = _snowmanxxxxxx * _snowmanxxxxxx + _snowmanxxxxxxx * _snowmanxxxxxxx + _snowmanxxxxxxxx * _snowmanxxxxxxxx;
                  float _snowmanxxxxxxxxxx = _snowmanxxxx.getMaxYAcceleration();
                  double _snowmanxxxxxxxxxxx = (double)MathHelper.sqrt(_snowmanxxxxxx * _snowmanxxxxxx + _snowmanxxxxxxxx * _snowmanxxxxxxxx);
                  if (_snowmanxxxxxxxxxxx > 0.0) {
                     _snowmanxxxxxxx = MathHelper.clamp(_snowmanxxxxxxx / _snowmanxxxxxxxxxxx, (double)(-_snowmanxxxxxxxxxx), (double)_snowmanxxxxxxxxxx);
                  }

                  this.setVelocity(this.getVelocity().add(0.0, _snowmanxxxxxxx * 0.01, 0.0));
                  this.yaw = MathHelper.wrapDegrees(this.yaw);
                  double _snowmanxxxxxxxxxxxx = MathHelper.clamp(
                     MathHelper.wrapDegrees(180.0 - MathHelper.atan2(_snowmanxxxxxx, _snowmanxxxxxxxx) * 180.0F / (float)Math.PI - (double)this.yaw), -50.0, 50.0
                  );
                  Vec3d _snowmanxxxxxxxxxxxxx = _snowmanxxxxx.subtract(this.getX(), this.getY(), this.getZ()).normalize();
                  Vec3d _snowmanxxxxxxxxxxxxxx = new Vec3d(
                        (double)MathHelper.sin(this.yaw * (float) (Math.PI / 180.0)),
                        this.getVelocity().y,
                        (double)(-MathHelper.cos(this.yaw * (float) (Math.PI / 180.0)))
                     )
                     .normalize();
                  float _snowmanxxxxxxxxxxxxxxx = Math.max(((float)_snowmanxxxxxxxxxxxxxx.dotProduct(_snowmanxxxxxxxxxxxxx) + 0.5F) / 1.5F, 0.0F);
                  this.field_20865 *= 0.8F;
                  this.field_20865 = (float)((double)this.field_20865 + _snowmanxxxxxxxxxxxx * (double)_snowmanxxxx.method_6847());
                  this.yaw = this.yaw + this.field_20865 * 0.1F;
                  float _snowmanxxxxxxxxxxxxxxxx = (float)(2.0 / (_snowmanxxxxxxxxx + 1.0));
                  float _snowmanxxxxxxxxxxxxxxxxx = 0.06F;
                  this.updateVelocity(0.06F * (_snowmanxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxx + (1.0F - _snowmanxxxxxxxxxxxxxxxx)), new Vec3d(0.0, 0.0, -1.0));
                  if (this.slowedDownByBlock) {
                     this.move(MovementType.SELF, this.getVelocity().multiply(0.8F));
                  } else {
                     this.move(MovementType.SELF, this.getVelocity());
                  }

                  Vec3d _snowmanxxxxxxxxxxxxxxxxxx = this.getVelocity().normalize();
                  double _snowmanxxxxxxxxxxxxxxxxxxx = 0.8 + 0.15 * (_snowmanxxxxxxxxxxxxxxxxxx.dotProduct(_snowmanxxxxxxxxxxxxxx) + 1.0) / 2.0;
                  this.setVelocity(this.getVelocity().multiply(_snowmanxxxxxxxxxxxxxxxxxxx, 0.91F, _snowmanxxxxxxxxxxxxxxxxxxx));
               }
            }

            this.bodyYaw = this.yaw;
            Vec3d[] _snowmanxxxxx = new Vec3d[this.parts.length];

            for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < this.parts.length; _snowmanxxxxxxxxxxxx++) {
               _snowmanxxxxx[_snowmanxxxxxxxxxxxx] = new Vec3d(this.parts[_snowmanxxxxxxxxxxxx].getX(), this.parts[_snowmanxxxxxxxxxxxx].getY(), this.parts[_snowmanxxxxxxxxxxxx].getZ());
            }

            float _snowmanxxxxxxxxxxxx = (float)(this.getSegmentProperties(5, 1.0F)[1] - this.getSegmentProperties(10, 1.0F)[1]) * 10.0F * (float) (Math.PI / 180.0);
            float _snowmanxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxx);
            float _snowmanxxxxxxxxxxxxxx = MathHelper.sin(_snowmanxxxxxxxxxxxx);
            float _snowmanxxxxxxxxxxxxxxx = this.yaw * (float) (Math.PI / 180.0);
            float _snowmanxxxxxxxxxxxxxxxx = MathHelper.sin(_snowmanxxxxxxxxxxxxxxx);
            float _snowmanxxxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxxxxx);
            this.movePart(this.partBody, (double)(_snowmanxxxxxxxxxxxxxxxx * 0.5F), 0.0, (double)(-_snowmanxxxxxxxxxxxxxxxxx * 0.5F));
            this.movePart(this.partWingRight, (double)(_snowmanxxxxxxxxxxxxxxxxx * 4.5F), 2.0, (double)(_snowmanxxxxxxxxxxxxxxxx * 4.5F));
            this.movePart(this.partWingLeft, (double)(_snowmanxxxxxxxxxxxxxxxxx * -4.5F), 2.0, (double)(_snowmanxxxxxxxxxxxxxxxx * -4.5F));
            if (!this.world.isClient && this.hurtTime == 0) {
               this.launchLivingEntities(
                  this.world
                     .getOtherEntities(
                        this, this.partWingRight.getBoundingBox().expand(4.0, 2.0, 4.0).offset(0.0, -2.0, 0.0), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR
                     )
               );
               this.launchLivingEntities(
                  this.world
                     .getOtherEntities(
                        this, this.partWingLeft.getBoundingBox().expand(4.0, 2.0, 4.0).offset(0.0, -2.0, 0.0), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR
                     )
               );
               this.damageLivingEntities(
                  this.world.getOtherEntities(this, this.partHead.getBoundingBox().expand(1.0), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR)
               );
               this.damageLivingEntities(
                  this.world.getOtherEntities(this, this.partNeck.getBoundingBox().expand(1.0), EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR)
               );
            }

            float _snowmanxxxxxxxxxxxxxxxxxx = MathHelper.sin(this.yaw * (float) (Math.PI / 180.0) - this.field_20865 * 0.01F);
            float _snowmanxxxxxxxxxxxxxxxxxxx = MathHelper.cos(this.yaw * (float) (Math.PI / 180.0) - this.field_20865 * 0.01F);
            float _snowmanxxxxxxxxxxxxxxxxxxxx = this.method_6820();
            this.movePart(
               this.partHead,
               (double)(_snowmanxxxxxxxxxxxxxxxxxx * 6.5F * _snowmanxxxxxxxxxxxxx),
               (double)(_snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxx * 6.5F),
               (double)(-_snowmanxxxxxxxxxxxxxxxxxxx * 6.5F * _snowmanxxxxxxxxxxxxx)
            );
            this.movePart(
               this.partNeck,
               (double)(_snowmanxxxxxxxxxxxxxxxxxx * 5.5F * _snowmanxxxxxxxxxxxxx),
               (double)(_snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxx * 5.5F),
               (double)(-_snowmanxxxxxxxxxxxxxxxxxxx * 5.5F * _snowmanxxxxxxxxxxxxx)
            );
            double[] _snowmanxxxxxxxxxxxxxxxxxxxxx = this.getSegmentProperties(5, 1.0F);

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxx < 3; _snowmanxxxxxxxxxxxxxxxxxxxxxx++) {
               EnderDragonPart _snowmanxxxxxxxxxxxxxxxxxxxxxxx = null;
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxx == 0) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx = this.partTail1;
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxxx == 1) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx = this.partTail2;
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxxx == 2) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx = this.partTail3;
               }

               double[] _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = this.getSegmentProperties(12 + _snowmanxxxxxxxxxxxxxxxxxxxxxx * 2, 1.0F);
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = this.yaw * (float) (Math.PI / 180.0)
                  + this.wrapYawChange(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx[0] - _snowmanxxxxxxxxxxxxxxxxxxxxx[0]) * (float) (Math.PI / 180.0);
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.sin(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = MathHelper.cos(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 1.5F;
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 1) * 2.0F;
               this.movePart(
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxx,
                  (double)(-(_snowmanxxxxxxxxxxxxxxxx * 1.5F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * _snowmanxxxxxxxxxxxxx),
                  _snowmanxxxxxxxxxxxxxxxxxxxxxxxx[1] - _snowmanxxxxxxxxxxxxxxxxxxxxx[1] - (double)((_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + 1.5F) * _snowmanxxxxxxxxxxxxxx) + 1.5,
                  (double)((_snowmanxxxxxxxxxxxxxxxxx * 1.5F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * _snowmanxxxxxxxxxxxxx)
               );
            }

            if (!this.world.isClient) {
               this.slowedDownByBlock = this.destroyBlocks(this.partHead.getBoundingBox())
                  | this.destroyBlocks(this.partNeck.getBoundingBox())
                  | this.destroyBlocks(this.partBody.getBoundingBox());
               if (this.fight != null) {
                  this.fight.updateFight(this);
               }
            }

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxx < this.parts.length; _snowmanxxxxxxxxxxxxxxxxxxxxxx++) {
               this.parts[_snowmanxxxxxxxxxxxxxxxxxxxxxx].prevX = _snowmanxxxxx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].x;
               this.parts[_snowmanxxxxxxxxxxxxxxxxxxxxxx].prevY = _snowmanxxxxx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].y;
               this.parts[_snowmanxxxxxxxxxxxxxxxxxxxxxx].prevZ = _snowmanxxxxx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].z;
               this.parts[_snowmanxxxxxxxxxxxxxxxxxxxxxx].lastRenderX = _snowmanxxxxx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].x;
               this.parts[_snowmanxxxxxxxxxxxxxxxxxxxxxx].lastRenderY = _snowmanxxxxx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].y;
               this.parts[_snowmanxxxxxxxxxxxxxxxxxxxxxx].lastRenderZ = _snowmanxxxxx[_snowmanxxxxxxxxxxxxxxxxxxxxxx].z;
            }
         }
      }
   }

   private void movePart(EnderDragonPart _snowman, double dx, double dy, double dz) {
      _snowman.updatePosition(this.getX() + dx, this.getY() + dy, this.getZ() + dz);
   }

   private float method_6820() {
      if (this.phaseManager.getCurrent().isSittingOrHovering()) {
         return -1.0F;
      } else {
         double[] _snowman = this.getSegmentProperties(5, 1.0F);
         double[] _snowmanx = this.getSegmentProperties(0, 1.0F);
         return (float)(_snowman[1] - _snowmanx[1]);
      }
   }

   private void tickWithEndCrystals() {
      if (this.connectedCrystal != null) {
         if (this.connectedCrystal.removed) {
            this.connectedCrystal = null;
         } else if (this.age % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
            this.setHealth(this.getHealth() + 1.0F);
         }
      }

      if (this.random.nextInt(10) == 0) {
         List<EndCrystalEntity> _snowman = this.world.getNonSpectatingEntities(EndCrystalEntity.class, this.getBoundingBox().expand(32.0));
         EndCrystalEntity _snowmanx = null;
         double _snowmanxx = Double.MAX_VALUE;

         for (EndCrystalEntity _snowmanxxx : _snowman) {
            double _snowmanxxxx = _snowmanxxx.squaredDistanceTo(this);
            if (_snowmanxxxx < _snowmanxx) {
               _snowmanxx = _snowmanxxxx;
               _snowmanx = _snowmanxxx;
            }
         }

         this.connectedCrystal = _snowmanx;
      }
   }

   private void launchLivingEntities(List<Entity> entities) {
      double _snowman = (this.partBody.getBoundingBox().minX + this.partBody.getBoundingBox().maxX) / 2.0;
      double _snowmanx = (this.partBody.getBoundingBox().minZ + this.partBody.getBoundingBox().maxZ) / 2.0;

      for (Entity _snowmanxx : entities) {
         if (_snowmanxx instanceof LivingEntity) {
            double _snowmanxxx = _snowmanxx.getX() - _snowman;
            double _snowmanxxxx = _snowmanxx.getZ() - _snowmanx;
            double _snowmanxxxxx = Math.max(_snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx, 0.1);
            _snowmanxx.addVelocity(_snowmanxxx / _snowmanxxxxx * 4.0, 0.2F, _snowmanxxxx / _snowmanxxxxx * 4.0);
            if (!this.phaseManager.getCurrent().isSittingOrHovering() && ((LivingEntity)_snowmanxx).getLastAttackedTime() < _snowmanxx.age - 2) {
               _snowmanxx.damage(DamageSource.mob(this), 5.0F);
               this.dealDamage(this, _snowmanxx);
            }
         }
      }
   }

   private void damageLivingEntities(List<Entity> entities) {
      for (Entity _snowman : entities) {
         if (_snowman instanceof LivingEntity) {
            _snowman.damage(DamageSource.mob(this), 10.0F);
            this.dealDamage(this, _snowman);
         }
      }
   }

   private float wrapYawChange(double yawDegrees) {
      return (float)MathHelper.wrapDegrees(yawDegrees);
   }

   private boolean destroyBlocks(Box _snowman) {
      int _snowmanx = MathHelper.floor(_snowman.minX);
      int _snowmanxx = MathHelper.floor(_snowman.minY);
      int _snowmanxxx = MathHelper.floor(_snowman.minZ);
      int _snowmanxxxx = MathHelper.floor(_snowman.maxX);
      int _snowmanxxxxx = MathHelper.floor(_snowman.maxY);
      int _snowmanxxxxxx = MathHelper.floor(_snowman.maxZ);
      boolean _snowmanxxxxxxx = false;
      boolean _snowmanxxxxxxxx = false;

      for (int _snowmanxxxxxxxxx = _snowmanx; _snowmanxxxxxxxxx <= _snowmanxxxx; _snowmanxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxx = _snowmanxx; _snowmanxxxxxxxxxx <= _snowmanxxxxx; _snowmanxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxx = _snowmanxxx; _snowmanxxxxxxxxxxx <= _snowmanxxxxxx; _snowmanxxxxxxxxxxx++) {
               BlockPos _snowmanxxxxxxxxxxxx = new BlockPos(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx);
               BlockState _snowmanxxxxxxxxxxxxx = this.world.getBlockState(_snowmanxxxxxxxxxxxx);
               Block _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.getBlock();
               if (!_snowmanxxxxxxxxxxxxx.isAir() && _snowmanxxxxxxxxxxxxx.getMaterial() != Material.FIRE) {
                  if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && !BlockTags.DRAGON_IMMUNE.contains(_snowmanxxxxxxxxxxxxxx)) {
                     _snowmanxxxxxxxx = this.world.removeBlock(_snowmanxxxxxxxxxxxx, false) || _snowmanxxxxxxxx;
                  } else {
                     _snowmanxxxxxxx = true;
                  }
               }
            }
         }
      }

      if (_snowmanxxxxxxxx) {
         BlockPos _snowmanxxxxxxxxx = new BlockPos(
            _snowmanx + this.random.nextInt(_snowmanxxxx - _snowmanx + 1), _snowmanxx + this.random.nextInt(_snowmanxxxxx - _snowmanxx + 1), _snowmanxxx + this.random.nextInt(_snowmanxxxxxx - _snowmanxxx + 1)
         );
         this.world.syncWorldEvent(2008, _snowmanxxxxxxxxx, 0);
      }

      return _snowmanxxxxxxx;
   }

   public boolean damagePart(EnderDragonPart part, DamageSource source, float amount) {
      if (this.phaseManager.getCurrent().getType() == PhaseType.DYING) {
         return false;
      } else {
         amount = this.phaseManager.getCurrent().modifyDamageTaken(source, amount);
         if (part != this.partHead) {
            amount = amount / 4.0F + Math.min(amount, 1.0F);
         }

         if (amount < 0.01F) {
            return false;
         } else {
            if (source.getAttacker() instanceof PlayerEntity || source.isExplosive()) {
               float _snowman = this.getHealth();
               this.parentDamage(source, amount);
               if (this.isDead() && !this.phaseManager.getCurrent().isSittingOrHovering()) {
                  this.setHealth(1.0F);
                  this.phaseManager.setPhase(PhaseType.DYING);
               }

               if (this.phaseManager.getCurrent().isSittingOrHovering()) {
                  this.field_7029 = (int)((float)this.field_7029 + (_snowman - this.getHealth()));
                  if ((float)this.field_7029 > 0.25F * this.getMaxHealth()) {
                     this.field_7029 = 0;
                     this.phaseManager.setPhase(PhaseType.TAKEOFF);
                  }
               }
            }

            return true;
         }
      }
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (source instanceof EntityDamageSource && ((EntityDamageSource)source).isThorns()) {
         this.damagePart(this.partBody, source, amount);
      }

      return false;
   }

   protected boolean parentDamage(DamageSource source, float amount) {
      return super.damage(source, amount);
   }

   @Override
   public void kill() {
      this.remove();
      if (this.fight != null) {
         this.fight.updateFight(this);
         this.fight.dragonKilled(this);
      }
   }

   @Override
   protected void updatePostDeath() {
      if (this.fight != null) {
         this.fight.updateFight(this);
      }

      this.ticksSinceDeath++;
      if (this.ticksSinceDeath >= 180 && this.ticksSinceDeath <= 200) {
         float _snowman = (this.random.nextFloat() - 0.5F) * 8.0F;
         float _snowmanx = (this.random.nextFloat() - 0.5F) * 4.0F;
         float _snowmanxx = (this.random.nextFloat() - 0.5F) * 8.0F;
         this.world
            .addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getX() + (double)_snowman, this.getY() + 2.0 + (double)_snowmanx, this.getZ() + (double)_snowmanxx, 0.0, 0.0, 0.0);
      }

      boolean _snowman = this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT);
      int _snowmanx = 500;
      if (this.fight != null && !this.fight.hasPreviouslyKilled()) {
         _snowmanx = 12000;
      }

      if (!this.world.isClient) {
         if (this.ticksSinceDeath > 150 && this.ticksSinceDeath % 5 == 0 && _snowman) {
            this.awardExperience(MathHelper.floor((float)_snowmanx * 0.08F));
         }

         if (this.ticksSinceDeath == 1 && !this.isSilent()) {
            this.world.syncGlobalEvent(1028, this.getBlockPos(), 0);
         }
      }

      this.move(MovementType.SELF, new Vec3d(0.0, 0.1F, 0.0));
      this.yaw += 20.0F;
      this.bodyYaw = this.yaw;
      if (this.ticksSinceDeath == 200 && !this.world.isClient) {
         if (_snowman) {
            this.awardExperience(MathHelper.floor((float)_snowmanx * 0.2F));
         }

         if (this.fight != null) {
            this.fight.dragonKilled(this);
         }

         this.remove();
      }
   }

   private void awardExperience(int amount) {
      while (amount > 0) {
         int _snowman = ExperienceOrbEntity.roundToOrbSize(amount);
         amount -= _snowman;
         this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.getX(), this.getY(), this.getZ(), _snowman));
      }
   }

   public int getNearestPathNodeIndex() {
      if (this.pathNodes[0] == null) {
         for (int _snowman = 0; _snowman < 24; _snowman++) {
            int _snowmanx = 5;
            int _snowmanxx;
            int _snowmanxxx;
            if (_snowman < 12) {
               _snowmanxx = MathHelper.floor(60.0F * MathHelper.cos(2.0F * ((float) -Math.PI + (float) (Math.PI / 12) * (float)_snowman)));
               _snowmanxxx = MathHelper.floor(60.0F * MathHelper.sin(2.0F * ((float) -Math.PI + (float) (Math.PI / 12) * (float)_snowman)));
            } else if (_snowman < 20) {
               int var3 = _snowman - 12;
               _snowmanxx = MathHelper.floor(40.0F * MathHelper.cos(2.0F * ((float) -Math.PI + (float) (Math.PI / 8) * (float)var3)));
               _snowmanxxx = MathHelper.floor(40.0F * MathHelper.sin(2.0F * ((float) -Math.PI + (float) (Math.PI / 8) * (float)var3)));
               _snowmanx += 10;
            } else {
               int var7 = _snowman - 20;
               _snowmanxx = MathHelper.floor(20.0F * MathHelper.cos(2.0F * ((float) -Math.PI + (float) (Math.PI / 4) * (float)var7)));
               _snowmanxxx = MathHelper.floor(20.0F * MathHelper.sin(2.0F * ((float) -Math.PI + (float) (Math.PI / 4) * (float)var7)));
            }

            int _snowmanxxxx = Math.max(
               this.world.getSeaLevel() + 10, this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(_snowmanxx, 0, _snowmanxxx)).getY() + _snowmanx
            );
            this.pathNodes[_snowman] = new PathNode(_snowmanxx, _snowmanxxxx, _snowmanxxx);
         }

         this.pathNodeConnections[0] = 6146;
         this.pathNodeConnections[1] = 8197;
         this.pathNodeConnections[2] = 8202;
         this.pathNodeConnections[3] = 16404;
         this.pathNodeConnections[4] = 32808;
         this.pathNodeConnections[5] = 32848;
         this.pathNodeConnections[6] = 65696;
         this.pathNodeConnections[7] = 131392;
         this.pathNodeConnections[8] = 131712;
         this.pathNodeConnections[9] = 263424;
         this.pathNodeConnections[10] = 526848;
         this.pathNodeConnections[11] = 525313;
         this.pathNodeConnections[12] = 1581057;
         this.pathNodeConnections[13] = 3166214;
         this.pathNodeConnections[14] = 2138120;
         this.pathNodeConnections[15] = 6373424;
         this.pathNodeConnections[16] = 4358208;
         this.pathNodeConnections[17] = 12910976;
         this.pathNodeConnections[18] = 9044480;
         this.pathNodeConnections[19] = 9706496;
         this.pathNodeConnections[20] = 15216640;
         this.pathNodeConnections[21] = 13688832;
         this.pathNodeConnections[22] = 11763712;
         this.pathNodeConnections[23] = 8257536;
      }

      return this.getNearestPathNodeIndex(this.getX(), this.getY(), this.getZ());
   }

   public int getNearestPathNodeIndex(double x, double y, double z) {
      float _snowman = 10000.0F;
      int _snowmanx = 0;
      PathNode _snowmanxx = new PathNode(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
      int _snowmanxxx = 0;
      if (this.fight == null || this.fight.getAliveEndCrystals() == 0) {
         _snowmanxxx = 12;
      }

      for (int _snowmanxxxx = _snowmanxxx; _snowmanxxxx < 24; _snowmanxxxx++) {
         if (this.pathNodes[_snowmanxxxx] != null) {
            float _snowmanxxxxx = this.pathNodes[_snowmanxxxx].getSquaredDistance(_snowmanxx);
            if (_snowmanxxxxx < _snowman) {
               _snowman = _snowmanxxxxx;
               _snowmanx = _snowmanxxxx;
            }
         }
      }

      return _snowmanx;
   }

   @Nullable
   public Path findPath(int from, int to, @Nullable PathNode _snowman) {
      for (int _snowmanx = 0; _snowmanx < 24; _snowmanx++) {
         PathNode _snowmanxx = this.pathNodes[_snowmanx];
         _snowmanxx.visited = false;
         _snowmanxx.heapWeight = 0.0F;
         _snowmanxx.penalizedPathLength = 0.0F;
         _snowmanxx.distanceToNearestTarget = 0.0F;
         _snowmanxx.previous = null;
         _snowmanxx.heapIndex = -1;
      }

      PathNode _snowmanx = this.pathNodes[from];
      PathNode _snowmanxx = this.pathNodes[to];
      _snowmanx.penalizedPathLength = 0.0F;
      _snowmanx.distanceToNearestTarget = _snowmanx.getDistance(_snowmanxx);
      _snowmanx.heapWeight = _snowmanx.distanceToNearestTarget;
      this.pathHeap.clear();
      this.pathHeap.push(_snowmanx);
      PathNode _snowmanxxx = _snowmanx;
      int _snowmanxxxx = 0;
      if (this.fight == null || this.fight.getAliveEndCrystals() == 0) {
         _snowmanxxxx = 12;
      }

      while (!this.pathHeap.isEmpty()) {
         PathNode _snowmanxxxxx = this.pathHeap.pop();
         if (_snowmanxxxxx.equals(_snowmanxx)) {
            if (_snowman != null) {
               _snowman.previous = _snowmanxx;
               _snowmanxx = _snowman;
            }

            return this.getPathOfAllPredecessors(_snowmanx, _snowmanxx);
         }

         if (_snowmanxxxxx.getDistance(_snowmanxx) < _snowmanxxx.getDistance(_snowmanxx)) {
            _snowmanxxx = _snowmanxxxxx;
         }

         _snowmanxxxxx.visited = true;
         int _snowmanxxxxxx = 0;

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 24; _snowmanxxxxxxx++) {
            if (this.pathNodes[_snowmanxxxxxxx] == _snowmanxxxxx) {
               _snowmanxxxxxx = _snowmanxxxxxxx;
               break;
            }
         }

         for (int _snowmanxxxxxxxx = _snowmanxxxx; _snowmanxxxxxxxx < 24; _snowmanxxxxxxxx++) {
            if ((this.pathNodeConnections[_snowmanxxxxxx] & 1 << _snowmanxxxxxxxx) > 0) {
               PathNode _snowmanxxxxxxxxx = this.pathNodes[_snowmanxxxxxxxx];
               if (!_snowmanxxxxxxxxx.visited) {
                  float _snowmanxxxxxxxxxx = _snowmanxxxxx.penalizedPathLength + _snowmanxxxxx.getDistance(_snowmanxxxxxxxxx);
                  if (!_snowmanxxxxxxxxx.isInHeap() || _snowmanxxxxxxxxxx < _snowmanxxxxxxxxx.penalizedPathLength) {
                     _snowmanxxxxxxxxx.previous = _snowmanxxxxx;
                     _snowmanxxxxxxxxx.penalizedPathLength = _snowmanxxxxxxxxxx;
                     _snowmanxxxxxxxxx.distanceToNearestTarget = _snowmanxxxxxxxxx.getDistance(_snowmanxx);
                     if (_snowmanxxxxxxxxx.isInHeap()) {
                        this.pathHeap.setNodeWeight(_snowmanxxxxxxxxx, _snowmanxxxxxxxxx.penalizedPathLength + _snowmanxxxxxxxxx.distanceToNearestTarget);
                     } else {
                        _snowmanxxxxxxxxx.heapWeight = _snowmanxxxxxxxxx.penalizedPathLength + _snowmanxxxxxxxxx.distanceToNearestTarget;
                        this.pathHeap.push(_snowmanxxxxxxxxx);
                     }
                  }
               }
            }
         }
      }

      if (_snowmanxxx == _snowmanx) {
         return null;
      } else {
         LOGGER.debug("Failed to find path from {} to {}", from, to);
         if (_snowman != null) {
            _snowman.previous = _snowmanxxx;
            _snowmanxxx = _snowman;
         }

         return this.getPathOfAllPredecessors(_snowmanx, _snowmanxxx);
      }
   }

   private Path getPathOfAllPredecessors(PathNode unused, PathNode node) {
      List<PathNode> _snowman = Lists.newArrayList();
      PathNode _snowmanx = node;
      _snowman.add(0, node);

      while (_snowmanx.previous != null) {
         _snowmanx = _snowmanx.previous;
         _snowman.add(0, _snowmanx);
      }

      return new Path(_snowman, new BlockPos(node.x, node.y, node.z), true);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("DragonPhase", this.phaseManager.getCurrent().getType().getTypeId());
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("DragonPhase")) {
         this.phaseManager.setPhase(PhaseType.getFromId(tag.getInt("DragonPhase")));
      }
   }

   @Override
   public void checkDespawn() {
   }

   public EnderDragonPart[] getBodyParts() {
      return this.parts;
   }

   @Override
   public boolean collides() {
      return false;
   }

   @Override
   public SoundCategory getSoundCategory() {
      return SoundCategory.HOSTILE;
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_ENDER_DRAGON_HURT;
   }

   @Override
   protected float getSoundVolume() {
      return 5.0F;
   }

   public float method_6823(int segmentOffset, double[] segment1, double[] segment2) {
      Phase _snowman = this.phaseManager.getCurrent();
      PhaseType<? extends Phase> _snowmanx = _snowman.getType();
      double _snowmanxx;
      if (_snowmanx == PhaseType.LANDING || _snowmanx == PhaseType.TAKEOFF) {
         BlockPos _snowmanxxx = this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPortalFeature.ORIGIN);
         float _snowmanxxxx = Math.max(MathHelper.sqrt(_snowmanxxx.getSquaredDistance(this.getPos(), true)) / 4.0F, 1.0F);
         _snowmanxx = (double)((float)segmentOffset / _snowmanxxxx);
      } else if (_snowman.isSittingOrHovering()) {
         _snowmanxx = (double)segmentOffset;
      } else if (segmentOffset == 6) {
         _snowmanxx = 0.0;
      } else {
         _snowmanxx = segment2[1] - segment1[1];
      }

      return (float)_snowmanxx;
   }

   public Vec3d method_6834(float tickDelta) {
      Phase _snowman = this.phaseManager.getCurrent();
      PhaseType<? extends Phase> _snowmanx = _snowman.getType();
      Vec3d _snowmanxx;
      if (_snowmanx == PhaseType.LANDING || _snowmanx == PhaseType.TAKEOFF) {
         BlockPos _snowmanxxx = this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPortalFeature.ORIGIN);
         float _snowmanxxxx = Math.max(MathHelper.sqrt(_snowmanxxx.getSquaredDistance(this.getPos(), true)) / 4.0F, 1.0F);
         float _snowmanxxxxx = 6.0F / _snowmanxxxx;
         float _snowmanxxxxxx = this.pitch;
         float _snowmanxxxxxxx = 1.5F;
         this.pitch = -_snowmanxxxxx * 1.5F * 5.0F;
         _snowmanxx = this.getRotationVec(tickDelta);
         this.pitch = _snowmanxxxxxx;
      } else if (_snowman.isSittingOrHovering()) {
         float _snowmanxxx = this.pitch;
         float _snowmanxxxx = 1.5F;
         this.pitch = -45.0F;
         _snowmanxx = this.getRotationVec(tickDelta);
         this.pitch = _snowmanxxx;
      } else {
         _snowmanxx = this.getRotationVec(tickDelta);
      }

      return _snowmanxx;
   }

   public void crystalDestroyed(EndCrystalEntity crystal, BlockPos pos, DamageSource source) {
      PlayerEntity _snowman;
      if (source.getAttacker() instanceof PlayerEntity) {
         _snowman = (PlayerEntity)source.getAttacker();
      } else {
         _snowman = this.world.getClosestPlayer(CLOSE_PLAYER_PREDICATE, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ());
      }

      if (crystal == this.connectedCrystal) {
         this.damagePart(this.partHead, DamageSource.explosion(_snowman), 10.0F);
      }

      this.phaseManager.getCurrent().crystalDestroyed(crystal, pos, source, _snowman);
   }

   @Override
   public void onTrackedDataSet(TrackedData<?> data) {
      if (PHASE_TYPE.equals(data) && this.world.isClient) {
         this.phaseManager.setPhase(PhaseType.getFromId(this.getDataTracker().get(PHASE_TYPE)));
      }

      super.onTrackedDataSet(data);
   }

   public PhaseManager getPhaseManager() {
      return this.phaseManager;
   }

   @Nullable
   public EnderDragonFight getFight() {
      return this.fight;
   }

   @Override
   public boolean addStatusEffect(StatusEffectInstance effect) {
      return false;
   }

   @Override
   protected boolean canStartRiding(Entity entity) {
      return false;
   }

   @Override
   public boolean canUsePortals() {
      return false;
   }
}
