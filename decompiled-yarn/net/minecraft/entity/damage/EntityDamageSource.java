package net.minecraft.entity.damage;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Vec3d;

public class EntityDamageSource extends DamageSource {
   @Nullable
   protected final Entity source;
   private boolean thorns;

   public EntityDamageSource(String name, @Nullable Entity source) {
      super(name);
      this.source = source;
   }

   public EntityDamageSource setThorns() {
      this.thorns = true;
      return this;
   }

   public boolean isThorns() {
      return this.thorns;
   }

   @Nullable
   @Override
   public Entity getAttacker() {
      return this.source;
   }

   @Override
   public Text getDeathMessage(LivingEntity entity) {
      ItemStack _snowman = this.source instanceof LivingEntity ? ((LivingEntity)this.source).getMainHandStack() : ItemStack.EMPTY;
      String _snowmanx = "death.attack." + this.name;
      return !_snowman.isEmpty() && _snowman.hasCustomName()
         ? new TranslatableText(_snowmanx + ".item", entity.getDisplayName(), this.source.getDisplayName(), _snowman.toHoverableText())
         : new TranslatableText(_snowmanx, entity.getDisplayName(), this.source.getDisplayName());
   }

   @Override
   public boolean isScaledWithDifficulty() {
      return this.source != null && this.source instanceof LivingEntity && !(this.source instanceof PlayerEntity);
   }

   @Nullable
   @Override
   public Vec3d getPosition() {
      return this.source != null ? this.source.getPos() : null;
   }

   @Override
   public String toString() {
      return "EntityDamageSource (" + this.source + ")";
   }
}
