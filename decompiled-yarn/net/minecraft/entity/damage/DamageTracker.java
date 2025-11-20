package net.minecraft.entity.damage;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class DamageTracker {
   private final List<DamageRecord> recentDamage = Lists.newArrayList();
   private final LivingEntity entity;
   private int ageOnLastDamage;
   private int ageOnLastAttacked;
   private int ageOnLastUpdate;
   private boolean recentlyAttacked;
   private boolean hasDamage;
   private String fallDeathSuffix;

   public DamageTracker(LivingEntity entity) {
      this.entity = entity;
   }

   public void setFallDeathSuffix() {
      this.clearFallDeathSuffix();
      Optional<BlockPos> _snowman = this.entity.getClimbingPos();
      if (_snowman.isPresent()) {
         BlockState _snowmanx = this.entity.world.getBlockState(_snowman.get());
         if (_snowmanx.isOf(Blocks.LADDER) || _snowmanx.isIn(BlockTags.TRAPDOORS)) {
            this.fallDeathSuffix = "ladder";
         } else if (_snowmanx.isOf(Blocks.VINE)) {
            this.fallDeathSuffix = "vines";
         } else if (_snowmanx.isOf(Blocks.WEEPING_VINES) || _snowmanx.isOf(Blocks.WEEPING_VINES_PLANT)) {
            this.fallDeathSuffix = "weeping_vines";
         } else if (_snowmanx.isOf(Blocks.TWISTING_VINES) || _snowmanx.isOf(Blocks.TWISTING_VINES_PLANT)) {
            this.fallDeathSuffix = "twisting_vines";
         } else if (_snowmanx.isOf(Blocks.SCAFFOLDING)) {
            this.fallDeathSuffix = "scaffolding";
         } else {
            this.fallDeathSuffix = "other_climbable";
         }
      } else if (this.entity.isTouchingWater()) {
         this.fallDeathSuffix = "water";
      }
   }

   public void onDamage(DamageSource damageSource, float originalHealth, float _snowman) {
      this.update();
      this.setFallDeathSuffix();
      DamageRecord _snowmanx = new DamageRecord(damageSource, this.entity.age, originalHealth, _snowman, this.fallDeathSuffix, this.entity.fallDistance);
      this.recentDamage.add(_snowmanx);
      this.ageOnLastDamage = this.entity.age;
      this.hasDamage = true;
      if (_snowmanx.isAttackerLiving() && !this.recentlyAttacked && this.entity.isAlive()) {
         this.recentlyAttacked = true;
         this.ageOnLastAttacked = this.entity.age;
         this.ageOnLastUpdate = this.ageOnLastAttacked;
         this.entity.enterCombat();
      }
   }

   public Text getDeathMessage() {
      if (this.recentDamage.isEmpty()) {
         return new TranslatableText("death.attack.generic", this.entity.getDisplayName());
      } else {
         DamageRecord _snowman = this.getBiggestFall();
         DamageRecord _snowmanx = this.recentDamage.get(this.recentDamage.size() - 1);
         Text _snowmanxx = _snowmanx.getAttackerName();
         Entity _snowmanxxx = _snowmanx.getDamageSource().getAttacker();
         Text _snowmanxxxx;
         if (_snowman != null && _snowmanx.getDamageSource() == DamageSource.FALL) {
            Text _snowmanxxxxx = _snowman.getAttackerName();
            if (_snowman.getDamageSource() == DamageSource.FALL || _snowman.getDamageSource() == DamageSource.OUT_OF_WORLD) {
               _snowmanxxxx = new TranslatableText("death.fell.accident." + this.getFallDeathSuffix(_snowman), this.entity.getDisplayName());
            } else if (_snowmanxxxxx != null && (_snowmanxx == null || !_snowmanxxxxx.equals(_snowmanxx))) {
               Entity _snowmanxxxxxx = _snowman.getDamageSource().getAttacker();
               ItemStack _snowmanxxxxxxx = _snowmanxxxxxx instanceof LivingEntity ? ((LivingEntity)_snowmanxxxxxx).getMainHandStack() : ItemStack.EMPTY;
               if (!_snowmanxxxxxxx.isEmpty() && _snowmanxxxxxxx.hasCustomName()) {
                  _snowmanxxxx = new TranslatableText("death.fell.assist.item", this.entity.getDisplayName(), _snowmanxxxxx, _snowmanxxxxxxx.toHoverableText());
               } else {
                  _snowmanxxxx = new TranslatableText("death.fell.assist", this.entity.getDisplayName(), _snowmanxxxxx);
               }
            } else if (_snowmanxx != null) {
               ItemStack _snowmanxxxxxx = _snowmanxxx instanceof LivingEntity ? ((LivingEntity)_snowmanxxx).getMainHandStack() : ItemStack.EMPTY;
               if (!_snowmanxxxxxx.isEmpty() && _snowmanxxxxxx.hasCustomName()) {
                  _snowmanxxxx = new TranslatableText("death.fell.finish.item", this.entity.getDisplayName(), _snowmanxx, _snowmanxxxxxx.toHoverableText());
               } else {
                  _snowmanxxxx = new TranslatableText("death.fell.finish", this.entity.getDisplayName(), _snowmanxx);
               }
            } else {
               _snowmanxxxx = new TranslatableText("death.fell.killer", this.entity.getDisplayName());
            }
         } else {
            _snowmanxxxx = _snowmanx.getDamageSource().getDeathMessage(this.entity);
         }

         return _snowmanxxxx;
      }
   }

   @Nullable
   public LivingEntity getBiggestAttacker() {
      LivingEntity _snowman = null;
      PlayerEntity _snowmanx = null;
      float _snowmanxx = 0.0F;
      float _snowmanxxx = 0.0F;

      for (DamageRecord _snowmanxxxx : this.recentDamage) {
         if (_snowmanxxxx.getDamageSource().getAttacker() instanceof PlayerEntity && (_snowmanx == null || _snowmanxxxx.getDamage() > _snowmanxxx)) {
            _snowmanxxx = _snowmanxxxx.getDamage();
            _snowmanx = (PlayerEntity)_snowmanxxxx.getDamageSource().getAttacker();
         }

         if (_snowmanxxxx.getDamageSource().getAttacker() instanceof LivingEntity && (_snowman == null || _snowmanxxxx.getDamage() > _snowmanxx)) {
            _snowmanxx = _snowmanxxxx.getDamage();
            _snowman = (LivingEntity)_snowmanxxxx.getDamageSource().getAttacker();
         }
      }

      return (LivingEntity)(_snowmanx != null && _snowmanxxx >= _snowmanxx / 3.0F ? _snowmanx : _snowman);
   }

   @Nullable
   private DamageRecord getBiggestFall() {
      DamageRecord _snowman = null;
      DamageRecord _snowmanx = null;
      float _snowmanxx = 0.0F;
      float _snowmanxxx = 0.0F;

      for (int _snowmanxxxx = 0; _snowmanxxxx < this.recentDamage.size(); _snowmanxxxx++) {
         DamageRecord _snowmanxxxxx = this.recentDamage.get(_snowmanxxxx);
         DamageRecord _snowmanxxxxxx = _snowmanxxxx > 0 ? this.recentDamage.get(_snowmanxxxx - 1) : null;
         if ((_snowmanxxxxx.getDamageSource() == DamageSource.FALL || _snowmanxxxxx.getDamageSource() == DamageSource.OUT_OF_WORLD)
            && _snowmanxxxxx.getFallDistance() > 0.0F
            && (_snowman == null || _snowmanxxxxx.getFallDistance() > _snowmanxxx)) {
            if (_snowmanxxxx > 0) {
               _snowman = _snowmanxxxxxx;
            } else {
               _snowman = _snowmanxxxxx;
            }

            _snowmanxxx = _snowmanxxxxx.getFallDistance();
         }

         if (_snowmanxxxxx.getFallDeathSuffix() != null && (_snowmanx == null || _snowmanxxxxx.getDamage() > _snowmanxx)) {
            _snowmanx = _snowmanxxxxx;
            _snowmanxx = _snowmanxxxxx.getDamage();
         }
      }

      if (_snowmanxxx > 5.0F && _snowman != null) {
         return _snowman;
      } else {
         return _snowmanxx > 5.0F && _snowmanx != null ? _snowmanx : null;
      }
   }

   private String getFallDeathSuffix(DamageRecord _snowman) {
      return _snowman.getFallDeathSuffix() == null ? "generic" : _snowman.getFallDeathSuffix();
   }

   public int getTimeSinceLastAttack() {
      return this.recentlyAttacked ? this.entity.age - this.ageOnLastAttacked : this.ageOnLastUpdate - this.ageOnLastAttacked;
   }

   private void clearFallDeathSuffix() {
      this.fallDeathSuffix = null;
   }

   public void update() {
      int _snowman = this.recentlyAttacked ? 300 : 100;
      if (this.hasDamage && (!this.entity.isAlive() || this.entity.age - this.ageOnLastDamage > _snowman)) {
         boolean _snowmanx = this.recentlyAttacked;
         this.hasDamage = false;
         this.recentlyAttacked = false;
         this.ageOnLastUpdate = this.entity.age;
         if (_snowmanx) {
            this.entity.endCombat();
         }

         this.recentDamage.clear();
      }
   }

   public LivingEntity getEntity() {
      return this.entity;
   }
}
