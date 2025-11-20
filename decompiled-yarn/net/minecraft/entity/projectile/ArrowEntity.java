package net.minecraft.entity.projectile;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ArrowEntity extends PersistentProjectileEntity {
   private static final TrackedData<Integer> COLOR = DataTracker.registerData(ArrowEntity.class, TrackedDataHandlerRegistry.INTEGER);
   private Potion potion = Potions.EMPTY;
   private final Set<StatusEffectInstance> effects = Sets.newHashSet();
   private boolean colorSet;

   public ArrowEntity(EntityType<? extends ArrowEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public ArrowEntity(World world, double x, double y, double z) {
      super(EntityType.ARROW, x, y, z, world);
   }

   public ArrowEntity(World world, LivingEntity owner) {
      super(EntityType.ARROW, owner, world);
   }

   public void initFromStack(ItemStack stack) {
      if (stack.getItem() == Items.TIPPED_ARROW) {
         this.potion = PotionUtil.getPotion(stack);
         Collection<StatusEffectInstance> _snowman = PotionUtil.getCustomPotionEffects(stack);
         if (!_snowman.isEmpty()) {
            for (StatusEffectInstance _snowmanx : _snowman) {
               this.effects.add(new StatusEffectInstance(_snowmanx));
            }
         }

         int _snowmanx = getCustomPotionColor(stack);
         if (_snowmanx == -1) {
            this.initColor();
         } else {
            this.setColor(_snowmanx);
         }
      } else if (stack.getItem() == Items.ARROW) {
         this.potion = Potions.EMPTY;
         this.effects.clear();
         this.dataTracker.set(COLOR, -1);
      }
   }

   public static int getCustomPotionColor(ItemStack stack) {
      CompoundTag _snowman = stack.getTag();
      return _snowman != null && _snowman.contains("CustomPotionColor", 99) ? _snowman.getInt("CustomPotionColor") : -1;
   }

   private void initColor() {
      this.colorSet = false;
      if (this.potion == Potions.EMPTY && this.effects.isEmpty()) {
         this.dataTracker.set(COLOR, -1);
      } else {
         this.dataTracker.set(COLOR, PotionUtil.getColor(PotionUtil.getPotionEffects(this.potion, this.effects)));
      }
   }

   public void addEffect(StatusEffectInstance effect) {
      this.effects.add(effect);
      this.getDataTracker().set(COLOR, PotionUtil.getColor(PotionUtil.getPotionEffects(this.potion, this.effects)));
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(COLOR, -1);
   }

   @Override
   public void tick() {
      super.tick();
      if (this.world.isClient) {
         if (this.inGround) {
            if (this.inGroundTime % 5 == 0) {
               this.spawnParticles(1);
            }
         } else {
            this.spawnParticles(2);
         }
      } else if (this.inGround && this.inGroundTime != 0 && !this.effects.isEmpty() && this.inGroundTime >= 600) {
         this.world.sendEntityStatus(this, (byte)0);
         this.potion = Potions.EMPTY;
         this.effects.clear();
         this.dataTracker.set(COLOR, -1);
      }
   }

   private void spawnParticles(int _snowman) {
      int _snowmanx = this.getColor();
      if (_snowmanx != -1 && _snowman > 0) {
         double _snowmanxx = (double)(_snowmanx >> 16 & 0xFF) / 255.0;
         double _snowmanxxx = (double)(_snowmanx >> 8 & 0xFF) / 255.0;
         double _snowmanxxxx = (double)(_snowmanx >> 0 & 0xFF) / 255.0;

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman; _snowmanxxxxx++) {
            this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), _snowmanxx, _snowmanxxx, _snowmanxxxx);
         }
      }
   }

   public int getColor() {
      return this.dataTracker.get(COLOR);
   }

   private void setColor(int color) {
      this.colorSet = true;
      this.dataTracker.set(COLOR, color);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      if (this.potion != Potions.EMPTY && this.potion != null) {
         tag.putString("Potion", Registry.POTION.getId(this.potion).toString());
      }

      if (this.colorSet) {
         tag.putInt("Color", this.getColor());
      }

      if (!this.effects.isEmpty()) {
         ListTag _snowman = new ListTag();

         for (StatusEffectInstance _snowmanx : this.effects) {
            _snowman.add(_snowmanx.toTag(new CompoundTag()));
         }

         tag.put("CustomPotionEffects", _snowman);
      }
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("Potion", 8)) {
         this.potion = PotionUtil.getPotion(tag);
      }

      for (StatusEffectInstance _snowman : PotionUtil.getCustomPotionEffects(tag)) {
         this.addEffect(_snowman);
      }

      if (tag.contains("Color", 99)) {
         this.setColor(tag.getInt("Color"));
      } else {
         this.initColor();
      }
   }

   @Override
   protected void onHit(LivingEntity target) {
      super.onHit(target);

      for (StatusEffectInstance _snowman : this.potion.getEffects()) {
         target.addStatusEffect(
            new StatusEffectInstance(_snowman.getEffectType(), Math.max(_snowman.getDuration() / 8, 1), _snowman.getAmplifier(), _snowman.isAmbient(), _snowman.shouldShowParticles())
         );
      }

      if (!this.effects.isEmpty()) {
         for (StatusEffectInstance _snowman : this.effects) {
            target.addStatusEffect(_snowman);
         }
      }
   }

   @Override
   protected ItemStack asItemStack() {
      if (this.effects.isEmpty() && this.potion == Potions.EMPTY) {
         return new ItemStack(Items.ARROW);
      } else {
         ItemStack _snowman = new ItemStack(Items.TIPPED_ARROW);
         PotionUtil.setPotion(_snowman, this.potion);
         PotionUtil.setCustomPotionEffects(_snowman, this.effects);
         if (this.colorSet) {
            _snowman.getOrCreateTag().putInt("CustomPotionColor", this.getColor());
         }

         return _snowman;
      }
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 0) {
         int _snowman = this.getColor();
         if (_snowman != -1) {
            double _snowmanx = (double)(_snowman >> 16 & 0xFF) / 255.0;
            double _snowmanxx = (double)(_snowman >> 8 & 0xFF) / 255.0;
            double _snowmanxxx = (double)(_snowman >> 0 & 0xFF) / 255.0;

            for (int _snowmanxxxx = 0; _snowmanxxxx < 20; _snowmanxxxx++) {
               this.world.addParticle(ParticleTypes.ENTITY_EFFECT, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), _snowmanx, _snowmanxx, _snowmanxxx);
            }
         }
      } else {
         super.handleStatus(status);
      }
   }
}
