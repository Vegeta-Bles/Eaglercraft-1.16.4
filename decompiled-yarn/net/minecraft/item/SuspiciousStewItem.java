package net.minecraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.World;

public class SuspiciousStewItem extends Item {
   public SuspiciousStewItem(Item.Settings _snowman) {
      super(_snowman);
   }

   public static void addEffectToStew(ItemStack stew, StatusEffect effect, int duration) {
      CompoundTag _snowman = stew.getOrCreateTag();
      ListTag _snowmanx = _snowman.getList("Effects", 9);
      CompoundTag _snowmanxx = new CompoundTag();
      _snowmanxx.putByte("EffectId", (byte)StatusEffect.getRawId(effect));
      _snowmanxx.putInt("EffectDuration", duration);
      _snowmanx.add(_snowmanxx);
      _snowman.put("Effects", _snowmanx);
   }

   @Override
   public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
      ItemStack _snowman = super.finishUsing(stack, world, user);
      CompoundTag _snowmanx = stack.getTag();
      if (_snowmanx != null && _snowmanx.contains("Effects", 9)) {
         ListTag _snowmanxx = _snowmanx.getList("Effects", 10);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
            int _snowmanxxxx = 160;
            CompoundTag _snowmanxxxxx = _snowmanxx.getCompound(_snowmanxxx);
            if (_snowmanxxxxx.contains("EffectDuration", 3)) {
               _snowmanxxxx = _snowmanxxxxx.getInt("EffectDuration");
            }

            StatusEffect _snowmanxxxxxx = StatusEffect.byRawId(_snowmanxxxxx.getByte("EffectId"));
            if (_snowmanxxxxxx != null) {
               user.addStatusEffect(new StatusEffectInstance(_snowmanxxxxxx, _snowmanxxxx));
            }
         }
      }

      return user instanceof PlayerEntity && ((PlayerEntity)user).abilities.creativeMode ? _snowman : new ItemStack(Items.BOWL);
   }
}
