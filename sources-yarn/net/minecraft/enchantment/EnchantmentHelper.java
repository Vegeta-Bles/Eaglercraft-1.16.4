package net.minecraft.enchantment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Predicate;
import javax.annotation.Nullable;
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
      } else {
         Identifier lv = Registry.ENCHANTMENT.getId(enchantment);
         ListTag lv2 = stack.getEnchantments();

         for (int i = 0; i < lv2.size(); i++) {
            CompoundTag lv3 = lv2.getCompound(i);
            Identifier lv4 = Identifier.tryParse(lv3.getString("id"));
            if (lv4 != null && lv4.equals(lv)) {
               return MathHelper.clamp(lv3.getInt("lvl"), 0, 255);
            }
         }

         return 0;
      }
   }

   public static Map<Enchantment, Integer> get(ItemStack stack) {
      ListTag lv = stack.getItem() == Items.ENCHANTED_BOOK ? EnchantedBookItem.getEnchantmentTag(stack) : stack.getEnchantments();
      return fromTag(lv);
   }

   public static Map<Enchantment, Integer> fromTag(ListTag tag) {
      Map<Enchantment, Integer> map = Maps.newLinkedHashMap();

      for (int i = 0; i < tag.size(); i++) {
         CompoundTag lv = tag.getCompound(i);
         Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(lv.getString("id"))).ifPresent(enchantment -> {
            Integer var10000 = map.put(enchantment, lv.getInt("lvl"));
         });
      }

      return map;
   }

   public static void set(Map<Enchantment, Integer> enchantments, ItemStack stack) {
      ListTag lv = new ListTag();

      for (Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
         Enchantment lv2 = entry.getKey();
         if (lv2 != null) {
            int i = entry.getValue();
            CompoundTag lv3 = new CompoundTag();
            lv3.putString("id", String.valueOf(Registry.ENCHANTMENT.getId(lv2)));
            lv3.putShort("lvl", (short)i);
            lv.add(lv3);
            if (stack.getItem() == Items.ENCHANTED_BOOK) {
               EnchantedBookItem.addEnchantment(stack, new EnchantmentLevelEntry(lv2, i));
            }
         }
      }

      if (lv.isEmpty()) {
         stack.removeSubTag("Enchantments");
      } else if (stack.getItem() != Items.ENCHANTED_BOOK) {
         stack.putSubTag("Enchantments", lv);
      }
   }

   private static void forEachEnchantment(EnchantmentHelper.Consumer consumer, ItemStack stack) {
      if (!stack.isEmpty()) {
         ListTag lv = stack.getEnchantments();

         for (int i = 0; i < lv.size(); i++) {
            String string = lv.getCompound(i).getString("id");
            int j = lv.getCompound(i).getInt("lvl");
            Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(string)).ifPresent(enchantment -> consumer.accept(enchantment, j));
         }
      }
   }

   private static void forEachEnchantment(EnchantmentHelper.Consumer consumer, Iterable<ItemStack> stacks) {
      for (ItemStack lv : stacks) {
         forEachEnchantment(consumer, lv);
      }
   }

   public static int getProtectionAmount(Iterable<ItemStack> equipment, DamageSource source) {
      MutableInt mutableInt = new MutableInt();
      forEachEnchantment((enchantment, level) -> mutableInt.add(enchantment.getProtectionAmount(level, source)), equipment);
      return mutableInt.intValue();
   }

   public static float getAttackDamage(ItemStack stack, EntityGroup group) {
      MutableFloat mutableFloat = new MutableFloat();
      forEachEnchantment((enchantment, level) -> mutableFloat.add(enchantment.getAttackDamage(level, group)), stack);
      return mutableFloat.floatValue();
   }

   public static float getSweepingMultiplier(LivingEntity entity) {
      int i = getEquipmentLevel(Enchantments.SWEEPING, entity);
      return i > 0 ? SweepingEnchantment.getMultiplier(i) : 0.0F;
   }

   public static void onUserDamaged(LivingEntity user, Entity attacker) {
      EnchantmentHelper.Consumer lv = (enchantment, level) -> enchantment.onUserDamaged(user, attacker, level);
      if (user != null) {
         forEachEnchantment(lv, user.getItemsEquipped());
      }

      if (attacker instanceof PlayerEntity) {
         forEachEnchantment(lv, user.getMainHandStack());
      }
   }

   public static void onTargetDamaged(LivingEntity user, Entity target) {
      EnchantmentHelper.Consumer lv = (enchantment, level) -> enchantment.onTargetDamaged(user, target, level);
      if (user != null) {
         forEachEnchantment(lv, user.getItemsEquipped());
      }

      if (user instanceof PlayerEntity) {
         forEachEnchantment(lv, user.getMainHandStack());
      }
   }

   public static int getEquipmentLevel(Enchantment enchantment, LivingEntity entity) {
      Iterable<ItemStack> iterable = enchantment.getEquipment(entity).values();
      if (iterable == null) {
         return 0;
      } else {
         int i = 0;

         for (ItemStack lv : iterable) {
            int j = getLevel(enchantment, lv);
            if (j > i) {
               i = j;
            }
         }

         return i;
      }
   }

   public static int getKnockback(LivingEntity entity) {
      return getEquipmentLevel(Enchantments.KNOCKBACK, entity);
   }

   public static int getFireAspect(LivingEntity entity) {
      return getEquipmentLevel(Enchantments.FIRE_ASPECT, entity);
   }

   public static int getRespiration(LivingEntity entity) {
      return getEquipmentLevel(Enchantments.RESPIRATION, entity);
   }

   public static int getDepthStrider(LivingEntity entity) {
      return getEquipmentLevel(Enchantments.DEPTH_STRIDER, entity);
   }

   public static int getEfficiency(LivingEntity entity) {
      return getEquipmentLevel(Enchantments.EFFICIENCY, entity);
   }

   public static int getLuckOfTheSea(ItemStack stack) {
      return getLevel(Enchantments.LUCK_OF_THE_SEA, stack);
   }

   public static int getLure(ItemStack stack) {
      return getLevel(Enchantments.LURE, stack);
   }

   public static int getLooting(LivingEntity entity) {
      return getEquipmentLevel(Enchantments.LOOTING, entity);
   }

   public static boolean hasAquaAffinity(LivingEntity entity) {
      return getEquipmentLevel(Enchantments.AQUA_AFFINITY, entity) > 0;
   }

   public static boolean hasFrostWalker(LivingEntity entity) {
      return getEquipmentLevel(Enchantments.FROST_WALKER, entity) > 0;
   }

   public static boolean hasSoulSpeed(LivingEntity entity) {
      return getEquipmentLevel(Enchantments.SOUL_SPEED, entity) > 0;
   }

   public static boolean hasBindingCurse(ItemStack stack) {
      return getLevel(Enchantments.BINDING_CURSE, stack) > 0;
   }

   public static boolean hasVanishingCurse(ItemStack stack) {
      return getLevel(Enchantments.VANISHING_CURSE, stack) > 0;
   }

   public static int getLoyalty(ItemStack stack) {
      return getLevel(Enchantments.LOYALTY, stack);
   }

   public static int getRiptide(ItemStack stack) {
      return getLevel(Enchantments.RIPTIDE, stack);
   }

   public static boolean hasChanneling(ItemStack stack) {
      return getLevel(Enchantments.CHANNELING, stack) > 0;
   }

   @Nullable
   public static Entry<EquipmentSlot, ItemStack> chooseEquipmentWith(Enchantment enchantment, LivingEntity entity) {
      return chooseEquipmentWith(enchantment, entity, stack -> true);
   }

   @Nullable
   public static Entry<EquipmentSlot, ItemStack> chooseEquipmentWith(Enchantment enchantment, LivingEntity entity, Predicate<ItemStack> condition) {
      Map<EquipmentSlot, ItemStack> map = enchantment.getEquipment(entity);
      if (map.isEmpty()) {
         return null;
      } else {
         List<Entry<EquipmentSlot, ItemStack>> list = Lists.newArrayList();

         for (Entry<EquipmentSlot, ItemStack> entry : map.entrySet()) {
            ItemStack lv = entry.getValue();
            if (!lv.isEmpty() && getLevel(enchantment, lv) > 0 && condition.test(lv)) {
               list.add(entry);
            }
         }

         return list.isEmpty() ? null : list.get(entity.getRandom().nextInt(list.size()));
      }
   }

   public static int calculateRequiredExperienceLevel(Random random, int slotIndex, int bookshelfCount, ItemStack stack) {
      Item lv = stack.getItem();
      int k = lv.getEnchantability();
      if (k <= 0) {
         return 0;
      } else {
         if (bookshelfCount > 15) {
            bookshelfCount = 15;
         }

         int l = random.nextInt(8) + 1 + (bookshelfCount >> 1) + random.nextInt(bookshelfCount + 1);
         if (slotIndex == 0) {
            return Math.max(l / 3, 1);
         } else {
            return slotIndex == 1 ? l * 2 / 3 + 1 : Math.max(l, bookshelfCount * 2);
         }
      }
   }

   public static ItemStack enchant(Random random, ItemStack target, int level, boolean treasureAllowed) {
      List<EnchantmentLevelEntry> list = generateEnchantments(random, target, level, treasureAllowed);
      boolean bl2 = target.getItem() == Items.BOOK;
      if (bl2) {
         target = new ItemStack(Items.ENCHANTED_BOOK);
      }

      for (EnchantmentLevelEntry lv : list) {
         if (bl2) {
            EnchantedBookItem.addEnchantment(target, lv);
         } else {
            target.addEnchantment(lv.enchantment, lv.level);
         }
      }

      return target;
   }

   public static List<EnchantmentLevelEntry> generateEnchantments(Random random, ItemStack stack, int level, boolean treasureAllowed) {
      List<EnchantmentLevelEntry> list = Lists.newArrayList();
      Item lv = stack.getItem();
      int j = lv.getEnchantability();
      if (j <= 0) {
         return list;
      } else {
         level += 1 + random.nextInt(j / 4 + 1) + random.nextInt(j / 4 + 1);
         float f = (random.nextFloat() + random.nextFloat() - 1.0F) * 0.15F;
         level = MathHelper.clamp(Math.round((float)level + (float)level * f), 1, Integer.MAX_VALUE);
         List<EnchantmentLevelEntry> list2 = getPossibleEntries(level, stack, treasureAllowed);
         if (!list2.isEmpty()) {
            list.add(WeightedPicker.getRandom(random, list2));

            while (random.nextInt(50) <= level) {
               removeConflicts(list2, Util.getLast(list));
               if (list2.isEmpty()) {
                  break;
               }

               list.add(WeightedPicker.getRandom(random, list2));
               level /= 2;
            }
         }

         return list;
      }
   }

   public static void removeConflicts(List<EnchantmentLevelEntry> possibleEntries, EnchantmentLevelEntry pickedEntry) {
      Iterator<EnchantmentLevelEntry> iterator = possibleEntries.iterator();

      while (iterator.hasNext()) {
         if (!pickedEntry.enchantment.canCombine(iterator.next().enchantment)) {
            iterator.remove();
         }
      }
   }

   public static boolean isCompatible(Collection<Enchantment> existing, Enchantment candidate) {
      for (Enchantment lv : existing) {
         if (!lv.canCombine(candidate)) {
            return false;
         }
      }

      return true;
   }

   public static List<EnchantmentLevelEntry> getPossibleEntries(int power, ItemStack stack, boolean treasureAllowed) {
      List<EnchantmentLevelEntry> list = Lists.newArrayList();
      Item lv = stack.getItem();
      boolean bl2 = stack.getItem() == Items.BOOK;

      for (Enchantment lv2 : Registry.ENCHANTMENT) {
         if ((!lv2.isTreasure() || treasureAllowed) && lv2.isAvailableForRandomSelection() && (lv2.type.isAcceptableItem(lv) || bl2)) {
            for (int j = lv2.getMaxLevel(); j > lv2.getMinLevel() - 1; j--) {
               if (power >= lv2.getMinPower(j) && power <= lv2.getMaxPower(j)) {
                  list.add(new EnchantmentLevelEntry(lv2, j));
                  break;
               }
            }
         }
      }

      return list;
   }

   @FunctionalInterface
   interface Consumer {
      void accept(Enchantment enchantment, int level);
   }
}
