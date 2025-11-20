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
      Optional<BlockPos> optional = this.entity.getClimbingPos();
      if (optional.isPresent()) {
         BlockState lv = this.entity.world.getBlockState(optional.get());
         if (lv.isOf(Blocks.LADDER) || lv.isIn(BlockTags.TRAPDOORS)) {
            this.fallDeathSuffix = "ladder";
         } else if (lv.isOf(Blocks.VINE)) {
            this.fallDeathSuffix = "vines";
         } else if (lv.isOf(Blocks.WEEPING_VINES) || lv.isOf(Blocks.WEEPING_VINES_PLANT)) {
            this.fallDeathSuffix = "weeping_vines";
         } else if (lv.isOf(Blocks.TWISTING_VINES) || lv.isOf(Blocks.TWISTING_VINES_PLANT)) {
            this.fallDeathSuffix = "twisting_vines";
         } else if (lv.isOf(Blocks.SCAFFOLDING)) {
            this.fallDeathSuffix = "scaffolding";
         } else {
            this.fallDeathSuffix = "other_climbable";
         }
      } else if (this.entity.isTouchingWater()) {
         this.fallDeathSuffix = "water";
      }
   }

   public void onDamage(DamageSource damageSource, float originalHealth, float g) {
      this.update();
      this.setFallDeathSuffix();
      DamageRecord lv = new DamageRecord(damageSource, this.entity.age, originalHealth, g, this.fallDeathSuffix, this.entity.fallDistance);
      this.recentDamage.add(lv);
      this.ageOnLastDamage = this.entity.age;
      this.hasDamage = true;
      if (lv.isAttackerLiving() && !this.recentlyAttacked && this.entity.isAlive()) {
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
         DamageRecord lv = this.getBiggestFall();
         DamageRecord lv2 = this.recentDamage.get(this.recentDamage.size() - 1);
         Text lv3 = lv2.getAttackerName();
         Entity lv4 = lv2.getDamageSource().getAttacker();
         Text lv6;
         if (lv != null && lv2.getDamageSource() == DamageSource.FALL) {
            Text lv5 = lv.getAttackerName();
            if (lv.getDamageSource() == DamageSource.FALL || lv.getDamageSource() == DamageSource.OUT_OF_WORLD) {
               lv6 = new TranslatableText("death.fell.accident." + this.getFallDeathSuffix(lv), this.entity.getDisplayName());
            } else if (lv5 != null && (lv3 == null || !lv5.equals(lv3))) {
               Entity lv7 = lv.getDamageSource().getAttacker();
               ItemStack lv8 = lv7 instanceof LivingEntity ? ((LivingEntity)lv7).getMainHandStack() : ItemStack.EMPTY;
               if (!lv8.isEmpty() && lv8.hasCustomName()) {
                  lv6 = new TranslatableText("death.fell.assist.item", this.entity.getDisplayName(), lv5, lv8.toHoverableText());
               } else {
                  lv6 = new TranslatableText("death.fell.assist", this.entity.getDisplayName(), lv5);
               }
            } else if (lv3 != null) {
               ItemStack lv11 = lv4 instanceof LivingEntity ? ((LivingEntity)lv4).getMainHandStack() : ItemStack.EMPTY;
               if (!lv11.isEmpty() && lv11.hasCustomName()) {
                  lv6 = new TranslatableText("death.fell.finish.item", this.entity.getDisplayName(), lv3, lv11.toHoverableText());
               } else {
                  lv6 = new TranslatableText("death.fell.finish", this.entity.getDisplayName(), lv3);
               }
            } else {
               lv6 = new TranslatableText("death.fell.killer", this.entity.getDisplayName());
            }
         } else {
            lv6 = lv2.getDamageSource().getDeathMessage(this.entity);
         }

         return lv6;
      }
   }

   @Nullable
   public LivingEntity getBiggestAttacker() {
      LivingEntity lv = null;
      PlayerEntity lv2 = null;
      float f = 0.0F;
      float g = 0.0F;

      for (DamageRecord lv3 : this.recentDamage) {
         if (lv3.getDamageSource().getAttacker() instanceof PlayerEntity && (lv2 == null || lv3.getDamage() > g)) {
            g = lv3.getDamage();
            lv2 = (PlayerEntity)lv3.getDamageSource().getAttacker();
         }

         if (lv3.getDamageSource().getAttacker() instanceof LivingEntity && (lv == null || lv3.getDamage() > f)) {
            f = lv3.getDamage();
            lv = (LivingEntity)lv3.getDamageSource().getAttacker();
         }
      }

      return (LivingEntity)(lv2 != null && g >= f / 3.0F ? lv2 : lv);
   }

   @Nullable
   private DamageRecord getBiggestFall() {
      DamageRecord lv = null;
      DamageRecord lv2 = null;
      float f = 0.0F;
      float g = 0.0F;

      for (int i = 0; i < this.recentDamage.size(); i++) {
         DamageRecord lv3 = this.recentDamage.get(i);
         DamageRecord lv4 = i > 0 ? this.recentDamage.get(i - 1) : null;
         if ((lv3.getDamageSource() == DamageSource.FALL || lv3.getDamageSource() == DamageSource.OUT_OF_WORLD)
            && lv3.getFallDistance() > 0.0F
            && (lv == null || lv3.getFallDistance() > g)) {
            if (i > 0) {
               lv = lv4;
            } else {
               lv = lv3;
            }

            g = lv3.getFallDistance();
         }

         if (lv3.getFallDeathSuffix() != null && (lv2 == null || lv3.getDamage() > f)) {
            lv2 = lv3;
            f = lv3.getDamage();
         }
      }

      if (g > 5.0F && lv != null) {
         return lv;
      } else {
         return f > 5.0F && lv2 != null ? lv2 : null;
      }
   }

   private String getFallDeathSuffix(DamageRecord arg) {
      return arg.getFallDeathSuffix() == null ? "generic" : arg.getFallDeathSuffix();
   }

   public int getTimeSinceLastAttack() {
      return this.recentlyAttacked ? this.entity.age - this.ageOnLastAttacked : this.ageOnLastUpdate - this.ageOnLastAttacked;
   }

   private void clearFallDeathSuffix() {
      this.fallDeathSuffix = null;
   }

   public void update() {
      int i = this.recentlyAttacked ? 300 : 100;
      if (this.hasDamage && (!this.entity.isAlive() || this.entity.age - this.ageOnLastDamage > i)) {
         boolean bl = this.recentlyAttacked;
         this.hasDamage = false;
         this.recentlyAttacked = false;
         this.ageOnLastUpdate = this.entity.age;
         if (bl) {
            this.entity.endCombat();
         }

         this.recentDamage.clear();
      }
   }

   public LivingEntity getEntity() {
      return this.entity;
   }
}
