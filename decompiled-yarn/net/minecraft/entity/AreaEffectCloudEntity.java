package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.command.argument.ParticleArgumentType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AreaEffectCloudEntity extends Entity {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final TrackedData<Float> RADIUS = DataTracker.registerData(AreaEffectCloudEntity.class, TrackedDataHandlerRegistry.FLOAT);
   private static final TrackedData<Integer> COLOR = DataTracker.registerData(AreaEffectCloudEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private static final TrackedData<Boolean> WAITING = DataTracker.registerData(AreaEffectCloudEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
   private static final TrackedData<ParticleEffect> PARTICLE_ID = DataTracker.registerData(AreaEffectCloudEntity.class, TrackedDataHandlerRegistry.PARTICLE);
   private Potion potion = Potions.EMPTY;
   private final List<StatusEffectInstance> effects = Lists.newArrayList();
   private final Map<Entity, Integer> affectedEntities = Maps.newHashMap();
   private int duration = 600;
   private int waitTime = 20;
   private int reapplicationDelay = 20;
   private boolean customColor;
   private int durationOnUse;
   private float radiusOnUse;
   private float radiusGrowth;
   private LivingEntity owner;
   private UUID ownerUuid;

   public AreaEffectCloudEntity(EntityType<? extends AreaEffectCloudEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
      this.noClip = true;
      this.setRadius(3.0F);
   }

   public AreaEffectCloudEntity(World world, double x, double y, double z) {
      this(EntityType.AREA_EFFECT_CLOUD, world);
      this.updatePosition(x, y, z);
   }

   @Override
   protected void initDataTracker() {
      this.getDataTracker().startTracking(COLOR, 0);
      this.getDataTracker().startTracking(RADIUS, 0.5F);
      this.getDataTracker().startTracking(WAITING, false);
      this.getDataTracker().startTracking(PARTICLE_ID, ParticleTypes.ENTITY_EFFECT);
   }

   public void setRadius(float radius) {
      if (!this.world.isClient) {
         this.getDataTracker().set(RADIUS, radius);
      }
   }

   @Override
   public void calculateDimensions() {
      double _snowman = this.getX();
      double _snowmanx = this.getY();
      double _snowmanxx = this.getZ();
      super.calculateDimensions();
      this.updatePosition(_snowman, _snowmanx, _snowmanxx);
   }

   public float getRadius() {
      return this.getDataTracker().get(RADIUS);
   }

   public void setPotion(Potion potion) {
      this.potion = potion;
      if (!this.customColor) {
         this.updateColor();
      }
   }

   private void updateColor() {
      if (this.potion == Potions.EMPTY && this.effects.isEmpty()) {
         this.getDataTracker().set(COLOR, 0);
      } else {
         this.getDataTracker().set(COLOR, PotionUtil.getColor(PotionUtil.getPotionEffects(this.potion, this.effects)));
      }
   }

   public void addEffect(StatusEffectInstance effect) {
      this.effects.add(effect);
      if (!this.customColor) {
         this.updateColor();
      }
   }

   public int getColor() {
      return this.getDataTracker().get(COLOR);
   }

   public void setColor(int rgb) {
      this.customColor = true;
      this.getDataTracker().set(COLOR, rgb);
   }

   public ParticleEffect getParticleType() {
      return this.getDataTracker().get(PARTICLE_ID);
   }

   public void setParticleType(ParticleEffect particle) {
      this.getDataTracker().set(PARTICLE_ID, particle);
   }

   protected void setWaiting(boolean waiting) {
      this.getDataTracker().set(WAITING, waiting);
   }

   public boolean isWaiting() {
      return this.getDataTracker().get(WAITING);
   }

   public int getDuration() {
      return this.duration;
   }

   public void setDuration(int duration) {
      this.duration = duration;
   }

   @Override
   public void tick() {
      super.tick();
      boolean _snowman = this.isWaiting();
      float _snowmanx = this.getRadius();
      if (this.world.isClient) {
         ParticleEffect _snowmanxx = this.getParticleType();
         if (_snowman) {
            if (this.random.nextBoolean()) {
               for (int _snowmanxxx = 0; _snowmanxxx < 2; _snowmanxxx++) {
                  float _snowmanxxxx = this.random.nextFloat() * (float) (Math.PI * 2);
                  float _snowmanxxxxx = MathHelper.sqrt(this.random.nextFloat()) * 0.2F;
                  float _snowmanxxxxxx = MathHelper.cos(_snowmanxxxx) * _snowmanxxxxx;
                  float _snowmanxxxxxxx = MathHelper.sin(_snowmanxxxx) * _snowmanxxxxx;
                  if (_snowmanxx.getType() == ParticleTypes.ENTITY_EFFECT) {
                     int _snowmanxxxxxxxx = this.random.nextBoolean() ? 16777215 : this.getColor();
                     int _snowmanxxxxxxxxx = _snowmanxxxxxxxx >> 16 & 0xFF;
                     int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx >> 8 & 0xFF;
                     int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx & 0xFF;
                     this.world
                        .addImportantParticle(
                           _snowmanxx,
                           this.getX() + (double)_snowmanxxxxxx,
                           this.getY(),
                           this.getZ() + (double)_snowmanxxxxxxx,
                           (double)((float)_snowmanxxxxxxxxx / 255.0F),
                           (double)((float)_snowmanxxxxxxxxxx / 255.0F),
                           (double)((float)_snowmanxxxxxxxxxxx / 255.0F)
                        );
                  } else {
                     this.world.addImportantParticle(_snowmanxx, this.getX() + (double)_snowmanxxxxxx, this.getY(), this.getZ() + (double)_snowmanxxxxxxx, 0.0, 0.0, 0.0);
                  }
               }
            }
         } else {
            float _snowmanxxxx = (float) Math.PI * _snowmanx * _snowmanx;

            for (int _snowmanxxxxx = 0; (float)_snowmanxxxxx < _snowmanxxxx; _snowmanxxxxx++) {
               float _snowmanxxxxxx = this.random.nextFloat() * (float) (Math.PI * 2);
               float _snowmanxxxxxxx = MathHelper.sqrt(this.random.nextFloat()) * _snowmanx;
               float _snowmanxxxxxxxx = MathHelper.cos(_snowmanxxxxxx) * _snowmanxxxxxxx;
               float _snowmanxxxxxxxxx = MathHelper.sin(_snowmanxxxxxx) * _snowmanxxxxxxx;
               if (_snowmanxx.getType() == ParticleTypes.ENTITY_EFFECT) {
                  int _snowmanxxxxxxxxxx = this.getColor();
                  int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx >> 16 & 0xFF;
                  int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx >> 8 & 0xFF;
                  int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx & 0xFF;
                  this.world
                     .addImportantParticle(
                        _snowmanxx,
                        this.getX() + (double)_snowmanxxxxxxxx,
                        this.getY(),
                        this.getZ() + (double)_snowmanxxxxxxxxx,
                        (double)((float)_snowmanxxxxxxxxxxx / 255.0F),
                        (double)((float)_snowmanxxxxxxxxxxxx / 255.0F),
                        (double)((float)_snowmanxxxxxxxxxxxxx / 255.0F)
                     );
               } else {
                  this.world
                     .addImportantParticle(
                        _snowmanxx,
                        this.getX() + (double)_snowmanxxxxxxxx,
                        this.getY(),
                        this.getZ() + (double)_snowmanxxxxxxxxx,
                        (0.5 - this.random.nextDouble()) * 0.15,
                        0.01F,
                        (0.5 - this.random.nextDouble()) * 0.15
                     );
               }
            }
         }
      } else {
         if (this.age >= this.waitTime + this.duration) {
            this.remove();
            return;
         }

         boolean _snowmanxx = this.age < this.waitTime;
         if (_snowman != _snowmanxx) {
            this.setWaiting(_snowmanxx);
         }

         if (_snowmanxx) {
            return;
         }

         if (this.radiusGrowth != 0.0F) {
            _snowmanx += this.radiusGrowth;
            if (_snowmanx < 0.5F) {
               this.remove();
               return;
            }

            this.setRadius(_snowmanx);
         }

         if (this.age % 5 == 0) {
            Iterator<Entry<Entity, Integer>> _snowmanxxxx = this.affectedEntities.entrySet().iterator();

            while (_snowmanxxxx.hasNext()) {
               Entry<Entity, Integer> _snowmanxxxxxx = _snowmanxxxx.next();
               if (this.age >= _snowmanxxxxxx.getValue()) {
                  _snowmanxxxx.remove();
               }
            }

            List<StatusEffectInstance> _snowmanxxxxxx = Lists.newArrayList();

            for (StatusEffectInstance _snowmanxxxxxxx : this.potion.getEffects()) {
               _snowmanxxxxxx.add(
                  new StatusEffectInstance(
                     _snowmanxxxxxxx.getEffectType(), _snowmanxxxxxxx.getDuration() / 4, _snowmanxxxxxxx.getAmplifier(), _snowmanxxxxxxx.isAmbient(), _snowmanxxxxxxx.shouldShowParticles()
                  )
               );
            }

            _snowmanxxxxxx.addAll(this.effects);
            if (_snowmanxxxxxx.isEmpty()) {
               this.affectedEntities.clear();
            } else {
               List<LivingEntity> _snowmanxxxxxxx = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox());
               if (!_snowmanxxxxxxx.isEmpty()) {
                  for (LivingEntity _snowmanxxxxxxxx : _snowmanxxxxxxx) {
                     if (!this.affectedEntities.containsKey(_snowmanxxxxxxxx) && _snowmanxxxxxxxx.isAffectedBySplashPotions()) {
                        double _snowmanxxxxxxxxx = _snowmanxxxxxxxx.getX() - this.getX();
                        double _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.getZ() - this.getZ();
                        double _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx * _snowmanxxxxxxxxx + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx;
                        if (_snowmanxxxxxxxxxxx <= (double)(_snowmanx * _snowmanx)) {
                           this.affectedEntities.put(_snowmanxxxxxxxx, this.age + this.reapplicationDelay);

                           for (StatusEffectInstance _snowmanxxxxxxxxxxxx : _snowmanxxxxxx) {
                              if (_snowmanxxxxxxxxxxxx.getEffectType().isInstant()) {
                                 _snowmanxxxxxxxxxxxx.getEffectType().applyInstantEffect(this, this.getOwner(), _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx.getAmplifier(), 0.5);
                              } else {
                                 _snowmanxxxxxxxx.addStatusEffect(new StatusEffectInstance(_snowmanxxxxxxxxxxxx));
                              }
                           }

                           if (this.radiusOnUse != 0.0F) {
                              _snowmanx += this.radiusOnUse;
                              if (_snowmanx < 0.5F) {
                                 this.remove();
                                 return;
                              }

                              this.setRadius(_snowmanx);
                           }

                           if (this.durationOnUse != 0) {
                              this.duration = this.duration + this.durationOnUse;
                              if (this.duration <= 0) {
                                 this.remove();
                                 return;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void setRadiusOnUse(float radius) {
      this.radiusOnUse = radius;
   }

   public void setRadiusGrowth(float growth) {
      this.radiusGrowth = growth;
   }

   public void setWaitTime(int ticks) {
      this.waitTime = ticks;
   }

   public void setOwner(@Nullable LivingEntity owner) {
      this.owner = owner;
      this.ownerUuid = owner == null ? null : owner.getUuid();
   }

   @Nullable
   public LivingEntity getOwner() {
      if (this.owner == null && this.ownerUuid != null && this.world instanceof ServerWorld) {
         Entity _snowman = ((ServerWorld)this.world).getEntity(this.ownerUuid);
         if (_snowman instanceof LivingEntity) {
            this.owner = (LivingEntity)_snowman;
         }
      }

      return this.owner;
   }

   @Override
   protected void readCustomDataFromTag(CompoundTag tag) {
      this.age = tag.getInt("Age");
      this.duration = tag.getInt("Duration");
      this.waitTime = tag.getInt("WaitTime");
      this.reapplicationDelay = tag.getInt("ReapplicationDelay");
      this.durationOnUse = tag.getInt("DurationOnUse");
      this.radiusOnUse = tag.getFloat("RadiusOnUse");
      this.radiusGrowth = tag.getFloat("RadiusPerTick");
      this.setRadius(tag.getFloat("Radius"));
      if (tag.containsUuid("Owner")) {
         this.ownerUuid = tag.getUuid("Owner");
      }

      if (tag.contains("Particle", 8)) {
         try {
            this.setParticleType(ParticleArgumentType.readParameters(new StringReader(tag.getString("Particle"))));
         } catch (CommandSyntaxException var5) {
            LOGGER.warn("Couldn't load custom particle {}", tag.getString("Particle"), var5);
         }
      }

      if (tag.contains("Color", 99)) {
         this.setColor(tag.getInt("Color"));
      }

      if (tag.contains("Potion", 8)) {
         this.setPotion(PotionUtil.getPotion(tag));
      }

      if (tag.contains("Effects", 9)) {
         ListTag _snowman = tag.getList("Effects", 10);
         this.effects.clear();

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            StatusEffectInstance _snowmanxx = StatusEffectInstance.fromTag(_snowman.getCompound(_snowmanx));
            if (_snowmanxx != null) {
               this.addEffect(_snowmanxx);
            }
         }
      }
   }

   @Override
   protected void writeCustomDataToTag(CompoundTag tag) {
      tag.putInt("Age", this.age);
      tag.putInt("Duration", this.duration);
      tag.putInt("WaitTime", this.waitTime);
      tag.putInt("ReapplicationDelay", this.reapplicationDelay);
      tag.putInt("DurationOnUse", this.durationOnUse);
      tag.putFloat("RadiusOnUse", this.radiusOnUse);
      tag.putFloat("RadiusPerTick", this.radiusGrowth);
      tag.putFloat("Radius", this.getRadius());
      tag.putString("Particle", this.getParticleType().asString());
      if (this.ownerUuid != null) {
         tag.putUuid("Owner", this.ownerUuid);
      }

      if (this.customColor) {
         tag.putInt("Color", this.getColor());
      }

      if (this.potion != Potions.EMPTY && this.potion != null) {
         tag.putString("Potion", Registry.POTION.getId(this.potion).toString());
      }

      if (!this.effects.isEmpty()) {
         ListTag _snowman = new ListTag();

         for (StatusEffectInstance _snowmanx : this.effects) {
            _snowman.add(_snowmanx.toTag(new CompoundTag()));
         }

         tag.put("Effects", _snowman);
      }
   }

   @Override
   public void onTrackedDataSet(TrackedData<?> data) {
      if (RADIUS.equals(data)) {
         this.calculateDimensions();
      }

      super.onTrackedDataSet(data);
   }

   @Override
   public PistonBehavior getPistonBehavior() {
      return PistonBehavior.IGNORE;
   }

   @Override
   public Packet<?> createSpawnPacket() {
      return new EntitySpawnS2CPacket(this);
   }

   @Override
   public EntityDimensions getDimensions(EntityPose pose) {
      return EntityDimensions.changing(this.getRadius() * 2.0F, 0.5F);
   }
}
