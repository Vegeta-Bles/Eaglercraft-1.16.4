/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableFloat
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package net.minecraft.enchantment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.SweepingEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.collection.WeightedPicker;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;

public class EnchantmentHelper {
    public static int getLevel(Enchantment enchantment, ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        Identifier identifier = Registry.ENCHANTMENT.getId(enchantment);
        ListTag _snowman2 = stack.getEnchantments();
        for (int i = 0; i < _snowman2.size(); ++i) {
            CompoundTag compoundTag = _snowman2.getCompound(i);
            Identifier _snowman3 = Identifier.tryParse(compoundTag.getString("id"));
            if (_snowman3 == null || !_snowman3.equals(identifier)) continue;
            return MathHelper.clamp(compoundTag.getInt("lvl"), 0, 255);
        }
        return 0;
    }

    public static Map<Enchantment, Integer> get(ItemStack stack) {
        ListTag listTag = stack.getItem() == Items.ENCHANTED_BOOK ? EnchantedBookItem.getEnchantmentTag(stack) : stack.getEnchantments();
        return EnchantmentHelper.fromTag(listTag);
    }

    public static Map<Enchantment, Integer> fromTag(ListTag tag) {
        LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        for (int i = 0; i < tag.size(); ++i) {
            CompoundTag compoundTag = tag.getCompound(i);
            Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(compoundTag.getString("id"))).ifPresent(enchantment -> linkedHashMap.put(enchantment, compoundTag.getInt("lvl")));
        }
        return linkedHashMap;
    }

    public static void set(Map<Enchantment, Integer> enchantments, ItemStack stack) {
        ListTag listTag = new ListTag();
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            Enchantment enchantment = entry.getKey();
            if (enchantment == null) continue;
            int _snowman2 = entry.getValue();
            CompoundTag _snowman3 = new CompoundTag();
            _snowman3.putString("id", String.valueOf(Registry.ENCHANTMENT.getId(enchantment)));
            _snowman3.putShort("lvl", (short)_snowman2);
            listTag.add(_snowman3);
            if (stack.getItem() != Items.ENCHANTED_BOOK) continue;
            EnchantedBookItem.addEnchantment(stack, new EnchantmentLevelEntry(enchantment, _snowman2));
        }
        if (listTag.isEmpty()) {
            stack.removeSubTag("Enchantments");
        } else if (stack.getItem() != Items.ENCHANTED_BOOK) {
            stack.putSubTag("Enchantments", listTag);
        }
    }

    private static void forEachEnchantment(Consumer consumer, ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }
        ListTag listTag = stack.getEnchantments();
        for (int i = 0; i < listTag.size(); ++i) {
            String string = listTag.getCompound(i).getString("id");
            int _snowman2 = listTag.getCompound(i).getInt("lvl");
            Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(string)).ifPresent(enchantment -> consumer.accept((Enchantment)enchantment, _snowman2));
        }
    }

    private static void forEachEnchantment(Consumer consumer, Iterable<ItemStack> stacks) {
        for (ItemStack itemStack : stacks) {
            EnchantmentHelper.forEachEnchantment(consumer, itemStack);
        }
    }

    public static int getProtectionAmount(Iterable<ItemStack> equipment, DamageSource source) {
        MutableInt mutableInt = new MutableInt();
        EnchantmentHelper.forEachEnchantment((Enchantment enchantment, int level) -> mutableInt.add(enchantment.getProtectionAmount(level, source)), equipment);
        return mutableInt.intValue();
    }

    public static float getAttackDamage(ItemStack stack, EntityGroup group) {
        MutableFloat mutableFloat = new MutableFloat();
        EnchantmentHelper.forEachEnchantment((Enchantment enchantment, int level) -> mutableFloat.add(enchantment.getAttackDamage(level, group)), stack);
        return mutableFloat.floatValue();
    }

    public static float getSweepingMultiplier(LivingEntity entity) {
        int n = EnchantmentHelper.getEquipmentLevel(Enchantments.SWEEPING, entity);
        if (n > 0) {
            return SweepingEnchantment.getMultiplier(n);
        }
        return 0.0f;
    }

    public static void onUserDamaged(LivingEntity user, Entity attacker) {
        Consumer consumer = (enchantment, level) -> enchantment.onUserDamaged(user, attacker, level);
        if (user != null) {
            EnchantmentHelper.forEachEnchantment(consumer, user.getItemsEquipped());
        }
        if (attacker instanceof PlayerEntity) {
            EnchantmentHelper.forEachEnchantment(consumer, user.getMainHandStack());
        }
    }

    public static void onTargetDamaged(LivingEntity user, Entity target) {
        Consumer consumer = (enchantment, level) -> enchantment.onTargetDamaged(user, target, level);
        if (user != null) {
            EnchantmentHelper.forEachEnchantment(consumer, user.getItemsEquipped());
        }
        if (user instanceof PlayerEntity) {
            EnchantmentHelper.forEachEnchantment(consumer, user.getMainHandStack());
        }
    }

    public static int getEquipmentLevel(Enchantment enchantment, LivingEntity entity) {
        Collection<ItemStack> collection = enchantment.getEquipment(entity).values();
        if (collection == null) {
            return 0;
        }
        int _snowman2 = 0;
        for (ItemStack itemStack : collection) {
            int n = EnchantmentHelper.getLevel(enchantment, itemStack);
            if (n <= _snowman2) continue;
            _snowman2 = n;
        }
        return _snowman2;
    }

    public static int getKnockback(LivingEntity entity) {
        return EnchantmentHelper.getEquipmentLevel(Enchantments.KNOCKBACK, entity);
    }

    public static int getFireAspect(LivingEntity entity) {
        return EnchantmentHelper.getEquipmentLevel(Enchantments.FIRE_ASPECT, entity);
    }

    public static int getRespiration(LivingEntity entity) {
        return EnchantmentHelper.getEquipmentLevel(Enchantments.RESPIRATION, entity);
    }

    public static int getDepthStrider(LivingEntity entity) {
        return EnchantmentHelper.getEquipmentLevel(Enchantments.DEPTH_STRIDER, entity);
    }

    public static int getEfficiency(LivingEntity entity) {
        return EnchantmentHelper.getEquipmentLevel(Enchantments.EFFICIENCY, entity);
    }

    public static int getLuckOfTheSea(ItemStack stack) {
        return EnchantmentHelper.getLevel(Enchantments.LUCK_OF_THE_SEA, stack);
    }

    public static int getLure(ItemStack stack) {
        return EnchantmentHelper.getLevel(Enchantments.LURE, stack);
    }

    public static int getLooting(LivingEntity entity) {
        return EnchantmentHelper.getEquipmentLevel(Enchantments.LOOTING, entity);
    }

    public static boolean hasAquaAffinity(LivingEntity entity) {
        return EnchantmentHelper.getEquipmentLevel(Enchantments.AQUA_AFFINITY, entity) > 0;
    }

    public static boolean hasFrostWalker(LivingEntity entity) {
        return EnchantmentHelper.getEquipmentLevel(Enchantments.FROST_WALKER, entity) > 0;
    }

    public static boolean hasSoulSpeed(LivingEntity entity) {
        return EnchantmentHelper.getEquipmentLevel(Enchantments.SOUL_SPEED, entity) > 0;
    }

    public static boolean hasBindingCurse(ItemStack stack) {
        return EnchantmentHelper.getLevel(Enchantments.BINDING_CURSE, stack) > 0;
    }

    public static boolean hasVanishingCurse(ItemStack stack) {
        return EnchantmentHelper.getLevel(Enchantments.VANISHING_CURSE, stack) > 0;
    }

    public static int getLoyalty(ItemStack stack) {
        return EnchantmentHelper.getLevel(Enchantments.LOYALTY, stack);
    }

    public static int getRiptide(ItemStack stack) {
        return EnchantmentHelper.getLevel(Enchantments.RIPTIDE, stack);
    }

    public static boolean hasChanneling(ItemStack stack) {
        return EnchantmentHelper.getLevel(Enchantments.CHANNELING, stack) > 0;
    }

    @Nullable
    public static Map.Entry<EquipmentSlot, ItemStack> chooseEquipmentWith(Enchantment enchantment, LivingEntity entity) {
        return EnchantmentHelper.chooseEquipmentWith(enchantment, entity, stack -> true);
    }

    @Nullable
    public static Map.Entry<EquipmentSlot, ItemStack> chooseEquipmentWith(Enchantment enchantment, LivingEntity entity, Predicate<ItemStack> condition) {
        Map<EquipmentSlot, ItemStack> map = enchantment.getEquipment(entity);
        if (map.isEmpty()) {
            return null;
        }
        ArrayList _snowman2 = Lists.newArrayList();
        for (Map.Entry<EquipmentSlot, ItemStack> entry : map.entrySet()) {
            ItemStack itemStack = entry.getValue();
            if (itemStack.isEmpty() || EnchantmentHelper.getLevel(enchantment, itemStack) <= 0 || !condition.test(itemStack)) continue;
            _snowman2.add(entry);
        }
        return _snowman2.isEmpty() ? null : (Map.Entry)_snowman2.get(entity.getRandom().nextInt(_snowman2.size()));
    }

    public static int calculateRequiredExperienceLevel(Random random, int slotIndex, int bookshelfCount, ItemStack stack) {
        Item item = stack.getItem();
        int _snowman2 = item.getEnchantability();
        if (_snowman2 <= 0) {
            return 0;
        }
        if (bookshelfCount > 15) {
            bookshelfCount = 15;
        }
        int _snowman3 = random.nextInt(8) + 1 + (bookshelfCount >> 1) + random.nextInt(bookshelfCount + 1);
        if (slotIndex == 0) {
            return Math.max(_snowman3 / 3, 1);
        }
        if (slotIndex == 1) {
            return _snowman3 * 2 / 3 + 1;
        }
        return Math.max(_snowman3, bookshelfCount * 2);
    }

    public static ItemStack enchant(Random random, ItemStack target, int level, boolean treasureAllowed) {
        List<EnchantmentLevelEntry> list = EnchantmentHelper.generateEnchantments(random, target, level, treasureAllowed);
        boolean bl = _snowman = target.getItem() == Items.BOOK;
        if (_snowman) {
            target = new ItemStack(Items.ENCHANTED_BOOK);
        }
        for (EnchantmentLevelEntry enchantmentLevelEntry : list) {
            if (_snowman) {
                EnchantedBookItem.addEnchantment(target, enchantmentLevelEntry);
                continue;
            }
            target.addEnchantment(enchantmentLevelEntry.enchantment, enchantmentLevelEntry.level);
        }
        return target;
    }

    public static List<EnchantmentLevelEntry> generateEnchantments(Random random, ItemStack stack, int level, boolean treasureAllowed) {
        ArrayList arrayList = Lists.newArrayList();
        Item _snowman2 = stack.getItem();
        int _snowman3 = _snowman2.getEnchantability();
        if (_snowman3 <= 0) {
            return arrayList;
        }
        level += 1 + random.nextInt(_snowman3 / 4 + 1) + random.nextInt(_snowman3 / 4 + 1);
        float _snowman4 = (random.nextFloat() + random.nextFloat() - 1.0f) * 0.15f;
        List<EnchantmentLevelEntry> _snowman5 = EnchantmentHelper.getPossibleEntries(level = MathHelper.clamp(Math.round((float)level + (float)level * _snowman4), 1, Integer.MAX_VALUE), stack, treasureAllowed);
        if (!_snowman5.isEmpty()) {
            arrayList.add(WeightedPicker.getRandom(random, _snowman5));
            while (random.nextInt(50) <= level) {
                EnchantmentHelper.removeConflicts(_snowman5, (EnchantmentLevelEntry)Util.getLast(arrayList));
                if (_snowman5.isEmpty()) break;
                arrayList.add(WeightedPicker.getRandom(random, _snowman5));
                level /= 2;
            }
        }
        return arrayList;
    }

    public static void removeConflicts(List<EnchantmentLevelEntry> possibleEntries, EnchantmentLevelEntry pickedEntry) {
        Iterator<EnchantmentLevelEntry> iterator = possibleEntries.iterator();
        while (iterator.hasNext()) {
            if (pickedEntry.enchantment.canCombine(iterator.next().enchantment)) continue;
            iterator.remove();
        }
    }

    public static boolean isCompatible(Collection<Enchantment> existing, Enchantment candidate) {
        for (Enchantment enchantment : existing) {
            if (enchantment.canCombine(candidate)) continue;
            return false;
        }
        return true;
    }

    public static List<EnchantmentLevelEntry> getPossibleEntries(int power, ItemStack stack, boolean treasureAllowed) {
        ArrayList arrayList = Lists.newArrayList();
        Item _snowman2 = stack.getItem();
        boolean _snowman3 = stack.getItem() == Items.BOOK;
        block0: for (Enchantment enchantment : Registry.ENCHANTMENT) {
            if (enchantment.isTreasure() && !treasureAllowed || !enchantment.isAvailableForRandomSelection() || !enchantment.type.isAcceptableItem(_snowman2) && !_snowman3) continue;
            for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
                if (power < enchantment.getMinPower(i) || power > enchantment.getMaxPower(i)) continue;
                arrayList.add(new EnchantmentLevelEntry(enchantment, i));
                continue block0;
            }
        }
        return arrayList;
    }

    @FunctionalInterface
    static interface Consumer {
        public void accept(Enchantment var1, int var2);
    }
}

