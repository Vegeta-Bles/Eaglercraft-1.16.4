package net.minecraft.potion;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class PotionUtil {
   private static final MutableText field_25817 = new TranslatableText("effect.none").formatted(Formatting.GRAY);

   public static List<StatusEffectInstance> getPotionEffects(ItemStack stack) {
      return getPotionEffects(stack.getTag());
   }

   public static List<StatusEffectInstance> getPotionEffects(Potion potion, Collection<StatusEffectInstance> custom) {
      List<StatusEffectInstance> _snowman = Lists.newArrayList();
      _snowman.addAll(potion.getEffects());
      _snowman.addAll(custom);
      return _snowman;
   }

   public static List<StatusEffectInstance> getPotionEffects(@Nullable CompoundTag tag) {
      List<StatusEffectInstance> _snowman = Lists.newArrayList();
      _snowman.addAll(getPotion(tag).getEffects());
      getCustomPotionEffects(tag, _snowman);
      return _snowman;
   }

   public static List<StatusEffectInstance> getCustomPotionEffects(ItemStack stack) {
      return getCustomPotionEffects(stack.getTag());
   }

   public static List<StatusEffectInstance> getCustomPotionEffects(@Nullable CompoundTag tag) {
      List<StatusEffectInstance> _snowman = Lists.newArrayList();
      getCustomPotionEffects(tag, _snowman);
      return _snowman;
   }

   public static void getCustomPotionEffects(@Nullable CompoundTag tag, List<StatusEffectInstance> list) {
      if (tag != null && tag.contains("CustomPotionEffects", 9)) {
         ListTag _snowman = tag.getList("CustomPotionEffects", 10);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            CompoundTag _snowmanxx = _snowman.getCompound(_snowmanx);
            StatusEffectInstance _snowmanxxx = StatusEffectInstance.fromTag(_snowmanxx);
            if (_snowmanxxx != null) {
               list.add(_snowmanxxx);
            }
         }
      }
   }

   public static int getColor(ItemStack stack) {
      CompoundTag _snowman = stack.getTag();
      if (_snowman != null && _snowman.contains("CustomPotionColor", 99)) {
         return _snowman.getInt("CustomPotionColor");
      } else {
         return getPotion(stack) == Potions.EMPTY ? 16253176 : getColor(getPotionEffects(stack));
      }
   }

   public static int getColor(Potion potion) {
      return potion == Potions.EMPTY ? 16253176 : getColor(potion.getEffects());
   }

   public static int getColor(Collection<StatusEffectInstance> effects) {
      int _snowman = 3694022;
      if (effects.isEmpty()) {
         return 3694022;
      } else {
         float _snowmanx = 0.0F;
         float _snowmanxx = 0.0F;
         float _snowmanxxx = 0.0F;
         int _snowmanxxxx = 0;

         for (StatusEffectInstance _snowmanxxxxx : effects) {
            if (_snowmanxxxxx.shouldShowParticles()) {
               int _snowmanxxxxxx = _snowmanxxxxx.getEffectType().getColor();
               int _snowmanxxxxxxx = _snowmanxxxxx.getAmplifier() + 1;
               _snowmanx += (float)(_snowmanxxxxxxx * (_snowmanxxxxxx >> 16 & 0xFF)) / 255.0F;
               _snowmanxx += (float)(_snowmanxxxxxxx * (_snowmanxxxxxx >> 8 & 0xFF)) / 255.0F;
               _snowmanxxx += (float)(_snowmanxxxxxxx * (_snowmanxxxxxx >> 0 & 0xFF)) / 255.0F;
               _snowmanxxxx += _snowmanxxxxxxx;
            }
         }

         if (_snowmanxxxx == 0) {
            return 0;
         } else {
            _snowmanx = _snowmanx / (float)_snowmanxxxx * 255.0F;
            _snowmanxx = _snowmanxx / (float)_snowmanxxxx * 255.0F;
            _snowmanxxx = _snowmanxxx / (float)_snowmanxxxx * 255.0F;
            return (int)_snowmanx << 16 | (int)_snowmanxx << 8 | (int)_snowmanxxx;
         }
      }
   }

   public static Potion getPotion(ItemStack stack) {
      return getPotion(stack.getTag());
   }

   public static Potion getPotion(@Nullable CompoundTag compound) {
      return compound == null ? Potions.EMPTY : Potion.byId(compound.getString("Potion"));
   }

   public static ItemStack setPotion(ItemStack stack, Potion potion) {
      Identifier _snowman = Registry.POTION.getId(potion);
      if (potion == Potions.EMPTY) {
         stack.removeSubTag("Potion");
      } else {
         stack.getOrCreateTag().putString("Potion", _snowman.toString());
      }

      return stack;
   }

   public static ItemStack setCustomPotionEffects(ItemStack stack, Collection<StatusEffectInstance> effects) {
      if (effects.isEmpty()) {
         return stack;
      } else {
         CompoundTag _snowman = stack.getOrCreateTag();
         ListTag _snowmanx = _snowman.getList("CustomPotionEffects", 9);

         for (StatusEffectInstance _snowmanxx : effects) {
            _snowmanx.add(_snowmanxx.toTag(new CompoundTag()));
         }

         _snowman.put("CustomPotionEffects", _snowmanx);
         return stack;
      }
   }

   public static void buildTooltip(ItemStack stack, List<Text> list, float _snowman) {
      List<StatusEffectInstance> _snowmanx = getPotionEffects(stack);
      List<Pair<EntityAttribute, EntityAttributeModifier>> _snowmanxx = Lists.newArrayList();
      if (_snowmanx.isEmpty()) {
         list.add(field_25817);
      } else {
         for (StatusEffectInstance _snowmanxxx : _snowmanx) {
            MutableText _snowmanxxxx = new TranslatableText(_snowmanxxx.getTranslationKey());
            StatusEffect _snowmanxxxxx = _snowmanxxx.getEffectType();
            Map<EntityAttribute, EntityAttributeModifier> _snowmanxxxxxx = _snowmanxxxxx.getAttributeModifiers();
            if (!_snowmanxxxxxx.isEmpty()) {
               for (Entry<EntityAttribute, EntityAttributeModifier> _snowmanxxxxxxx : _snowmanxxxxxx.entrySet()) {
                  EntityAttributeModifier _snowmanxxxxxxxx = _snowmanxxxxxxx.getValue();
                  EntityAttributeModifier _snowmanxxxxxxxxx = new EntityAttributeModifier(
                     _snowmanxxxxxxxx.getName(), _snowmanxxxxx.adjustModifierAmount(_snowmanxxx.getAmplifier(), _snowmanxxxxxxxx), _snowmanxxxxxxxx.getOperation()
                  );
                  _snowmanxx.add(new Pair(_snowmanxxxxxxx.getKey(), _snowmanxxxxxxxxx));
               }
            }

            if (_snowmanxxx.getAmplifier() > 0) {
               _snowmanxxxx = new TranslatableText("potion.withAmplifier", _snowmanxxxx, new TranslatableText("potion.potency." + _snowmanxxx.getAmplifier()));
            }

            if (_snowmanxxx.getDuration() > 20) {
               _snowmanxxxx = new TranslatableText("potion.withDuration", _snowmanxxxx, StatusEffectUtil.durationToString(_snowmanxxx, _snowman));
            }

            list.add(_snowmanxxxx.formatted(_snowmanxxxxx.getType().getFormatting()));
         }
      }

      if (!_snowmanxx.isEmpty()) {
         list.add(LiteralText.EMPTY);
         list.add(new TranslatableText("potion.whenDrank").formatted(Formatting.DARK_PURPLE));

         for (Pair<EntityAttribute, EntityAttributeModifier> _snowmanxxx : _snowmanxx) {
            EntityAttributeModifier _snowmanxxxxxxx = (EntityAttributeModifier)_snowmanxxx.getSecond();
            double _snowmanxxxxxxxx = _snowmanxxxxxxx.getValue();
            double _snowmanxxxxxxxxx;
            if (_snowmanxxxxxxx.getOperation() != EntityAttributeModifier.Operation.MULTIPLY_BASE
               && _snowmanxxxxxxx.getOperation() != EntityAttributeModifier.Operation.MULTIPLY_TOTAL) {
               _snowmanxxxxxxxxx = _snowmanxxxxxxx.getValue();
            } else {
               _snowmanxxxxxxxxx = _snowmanxxxxxxx.getValue() * 100.0;
            }

            if (_snowmanxxxxxxxx > 0.0) {
               list.add(
                  new TranslatableText(
                        "attribute.modifier.plus." + _snowmanxxxxxxx.getOperation().getId(),
                        ItemStack.MODIFIER_FORMAT.format(_snowmanxxxxxxxxx),
                        new TranslatableText(((EntityAttribute)_snowmanxxx.getFirst()).getTranslationKey())
                     )
                     .formatted(Formatting.BLUE)
               );
            } else if (_snowmanxxxxxxxx < 0.0) {
               _snowmanxxxxxxxxx *= -1.0;
               list.add(
                  new TranslatableText(
                        "attribute.modifier.take." + _snowmanxxxxxxx.getOperation().getId(),
                        ItemStack.MODIFIER_FORMAT.format(_snowmanxxxxxxxxx),
                        new TranslatableText(((EntityAttribute)_snowmanxxx.getFirst()).getTranslationKey())
                     )
                     .formatted(Formatting.RED)
               );
            }
         }
      }
   }
}
