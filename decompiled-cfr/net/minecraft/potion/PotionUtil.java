/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Pair
 *  javax.annotation.Nullable
 */
package net.minecraft.potion;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
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
        return PotionUtil.getPotionEffects(stack.getTag());
    }

    public static List<StatusEffectInstance> getPotionEffects(Potion potion, Collection<StatusEffectInstance> custom) {
        ArrayList arrayList = Lists.newArrayList();
        arrayList.addAll(potion.getEffects());
        arrayList.addAll(custom);
        return arrayList;
    }

    public static List<StatusEffectInstance> getPotionEffects(@Nullable CompoundTag tag) {
        ArrayList arrayList = Lists.newArrayList();
        arrayList.addAll(PotionUtil.getPotion(tag).getEffects());
        PotionUtil.getCustomPotionEffects(tag, arrayList);
        return arrayList;
    }

    public static List<StatusEffectInstance> getCustomPotionEffects(ItemStack stack) {
        return PotionUtil.getCustomPotionEffects(stack.getTag());
    }

    public static List<StatusEffectInstance> getCustomPotionEffects(@Nullable CompoundTag tag) {
        ArrayList arrayList = Lists.newArrayList();
        PotionUtil.getCustomPotionEffects(tag, arrayList);
        return arrayList;
    }

    public static void getCustomPotionEffects(@Nullable CompoundTag tag, List<StatusEffectInstance> list) {
        if (tag != null && tag.contains("CustomPotionEffects", 9)) {
            ListTag listTag = tag.getList("CustomPotionEffects", 10);
            for (int i = 0; i < listTag.size(); ++i) {
                CompoundTag compoundTag = listTag.getCompound(i);
                StatusEffectInstance _snowman2 = StatusEffectInstance.fromTag(compoundTag);
                if (_snowman2 == null) continue;
                list.add(_snowman2);
            }
        }
    }

    public static int getColor(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null && compoundTag.contains("CustomPotionColor", 99)) {
            return compoundTag.getInt("CustomPotionColor");
        }
        return PotionUtil.getPotion(stack) == Potions.EMPTY ? 0xF800F8 : PotionUtil.getColor(PotionUtil.getPotionEffects(stack));
    }

    public static int getColor(Potion potion) {
        return potion == Potions.EMPTY ? 0xF800F8 : PotionUtil.getColor(potion.getEffects());
    }

    public static int getColor(Collection<StatusEffectInstance> effects) {
        int n;
        int n2 = 3694022;
        if (effects.isEmpty()) {
            return 3694022;
        }
        float _snowman2 = 0.0f;
        float _snowman3 = 0.0f;
        float _snowman4 = 0.0f;
        n = 0;
        for (StatusEffectInstance statusEffectInstance : effects) {
            if (!statusEffectInstance.shouldShowParticles()) continue;
            int n3 = statusEffectInstance.getEffectType().getColor();
            _snowman = statusEffectInstance.getAmplifier() + 1;
            _snowman2 += (float)(_snowman * (n3 >> 16 & 0xFF)) / 255.0f;
            _snowman3 += (float)(_snowman * (n3 >> 8 & 0xFF)) / 255.0f;
            _snowman4 += (float)(_snowman * (n3 >> 0 & 0xFF)) / 255.0f;
            n += _snowman;
        }
        if (n == 0) {
            return 0;
        }
        _snowman2 = _snowman2 / (float)n * 255.0f;
        _snowman3 = _snowman3 / (float)n * 255.0f;
        _snowman4 = _snowman4 / (float)n * 255.0f;
        return (int)_snowman2 << 16 | (int)_snowman3 << 8 | (int)_snowman4;
    }

    public static Potion getPotion(ItemStack stack) {
        return PotionUtil.getPotion(stack.getTag());
    }

    public static Potion getPotion(@Nullable CompoundTag compound) {
        if (compound == null) {
            return Potions.EMPTY;
        }
        return Potion.byId(compound.getString("Potion"));
    }

    public static ItemStack setPotion(ItemStack stack, Potion potion) {
        Identifier identifier = Registry.POTION.getId(potion);
        if (potion == Potions.EMPTY) {
            stack.removeSubTag("Potion");
        } else {
            stack.getOrCreateTag().putString("Potion", identifier.toString());
        }
        return stack;
    }

    public static ItemStack setCustomPotionEffects(ItemStack stack, Collection<StatusEffectInstance> effects) {
        if (effects.isEmpty()) {
            return stack;
        }
        CompoundTag compoundTag = stack.getOrCreateTag();
        ListTag _snowman2 = compoundTag.getList("CustomPotionEffects", 9);
        for (StatusEffectInstance statusEffectInstance : effects) {
            _snowman2.add(statusEffectInstance.toTag(new CompoundTag()));
        }
        compoundTag.put("CustomPotionEffects", _snowman2);
        return stack;
    }

    public static void buildTooltip(ItemStack stack, List<Text> list, float f) {
        Object object;
        List<StatusEffectInstance> list2 = PotionUtil.getPotionEffects(stack);
        ArrayList _snowman2 = Lists.newArrayList();
        if (list2.isEmpty()) {
            list.add(field_25817);
        } else {
            for (StatusEffectInstance statusEffectInstance2 : list2) {
                StatusEffectInstance statusEffectInstance2;
                object = new TranslatableText(statusEffectInstance2.getTranslationKey());
                StatusEffect _snowman3 = statusEffectInstance2.getEffectType();
                Map<EntityAttribute, EntityAttributeModifier> _snowman4 = _snowman3.getAttributeModifiers();
                if (!_snowman4.isEmpty()) {
                    for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : _snowman4.entrySet()) {
                        EntityAttributeModifier entityAttributeModifier = entry.getValue();
                        _snowman = new EntityAttributeModifier(entityAttributeModifier.getName(), _snowman3.adjustModifierAmount(statusEffectInstance2.getAmplifier(), entityAttributeModifier), entityAttributeModifier.getOperation());
                        _snowman2.add(new Pair((Object)entry.getKey(), (Object)_snowman));
                    }
                }
                if (statusEffectInstance2.getAmplifier() > 0) {
                    object = new TranslatableText("potion.withAmplifier", object, new TranslatableText("potion.potency." + statusEffectInstance2.getAmplifier()));
                }
                if (statusEffectInstance2.getDuration() > 20) {
                    object = new TranslatableText("potion.withDuration", object, StatusEffectUtil.durationToString(statusEffectInstance2, f));
                }
                list.add(object.formatted(_snowman3.getType().getFormatting()));
            }
        }
        if (!_snowman2.isEmpty()) {
            list.add(LiteralText.EMPTY);
            list.add(new TranslatableText("potion.whenDrank").formatted(Formatting.DARK_PURPLE));
            for (StatusEffectInstance statusEffectInstance2 : _snowman2) {
                object = (EntityAttributeModifier)statusEffectInstance2.getSecond();
                double _snowman5 = ((EntityAttributeModifier)object).getValue();
                double _snowman6 = ((EntityAttributeModifier)object).getOperation() == EntityAttributeModifier.Operation.MULTIPLY_BASE || ((EntityAttributeModifier)object).getOperation() == EntityAttributeModifier.Operation.MULTIPLY_TOTAL ? ((EntityAttributeModifier)object).getValue() * 100.0 : ((EntityAttributeModifier)object).getValue();
                if (_snowman5 > 0.0) {
                    list.add(new TranslatableText("attribute.modifier.plus." + ((EntityAttributeModifier)object).getOperation().getId(), ItemStack.MODIFIER_FORMAT.format(_snowman6), new TranslatableText(((EntityAttribute)statusEffectInstance2.getFirst()).getTranslationKey())).formatted(Formatting.BLUE));
                    continue;
                }
                if (!(_snowman5 < 0.0)) continue;
                list.add(new TranslatableText("attribute.modifier.take." + ((EntityAttributeModifier)object).getOperation().getId(), ItemStack.MODIFIER_FORMAT.format(_snowman6 *= -1.0), new TranslatableText(((EntityAttribute)statusEffectInstance2.getFirst()).getTranslationKey())).formatted(Formatting.RED));
            }
        }
    }
}

