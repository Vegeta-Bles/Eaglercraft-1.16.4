package net.minecraft.entity.damage;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ProjectileDamageSource extends EntityDamageSource {
   private final Entity attacker;

   public ProjectileDamageSource(String name, Entity projectile, @Nullable Entity attacker) {
      super(name, projectile);
      this.attacker = attacker;
   }

   @Nullable
   @Override
   public Entity getSource() {
      return this.source;
   }

   @Nullable
   @Override
   public Entity getAttacker() {
      return this.attacker;
   }

   @Override
   public Text getDeathMessage(LivingEntity entity) {
      Text _snowman = this.attacker == null ? this.source.getDisplayName() : this.attacker.getDisplayName();
      ItemStack _snowmanx = this.attacker instanceof LivingEntity ? ((LivingEntity)this.attacker).getMainHandStack() : ItemStack.EMPTY;
      String _snowmanxx = "death.attack." + this.name;
      String _snowmanxxx = _snowmanxx + ".item";
      return !_snowmanx.isEmpty() && _snowmanx.hasCustomName()
         ? new TranslatableText(_snowmanxxx, entity.getDisplayName(), _snowman, _snowmanx.toHoverableText())
         : new TranslatableText(_snowmanxx, entity.getDisplayName(), _snowman);
   }
}
