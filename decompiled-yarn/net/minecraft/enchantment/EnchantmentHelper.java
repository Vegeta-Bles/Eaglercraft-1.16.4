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
         Identifier _snowman = Registry.ENCHANTMENT.getId(enchantment);
         ListTag _snowmanx = stack.getEnchantments();

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
            CompoundTag _snowmanxxx = _snowmanx.getCompound(_snowmanxx);
            Identifier _snowmanxxxx = Identifier.tryParse(_snowmanxxx.getString("id"));
            if (_snowmanxxxx != null && _snowmanxxxx.equals(_snowman)) {
               return MathHelper.clamp(_snowmanxxx.getInt("lvl"), 0, 255);
            }
         }

         return 0;
      }
   }

   public static Map<Enchantment, Integer> get(ItemStack stack) {
      ListTag _snowman = stack.getItem() == Items.ENCHANTED_BOOK ? EnchantedBookItem.getEnchantmentTag(stack) : stack.getEnchantments();
      return fromTag(_snowman);
   }

   public static Map<Enchantment, Integer> fromTag(ListTag tag) {
      Map<Enchantment, Integer> _snowman = Maps.newLinkedHashMap();

      for (int _snowmanx = 0; _snowmanx < tag.size(); _snowmanx++) {
         CompoundTag _snowmanxx = tag.getCompound(_snowmanx);
         Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(_snowmanxx.getString("id"))).ifPresent(enchantment -> {
            Integer var10000 = _snowman.put(enchantment, _snowman.getInt("lvl"));
         });
      }

      return _snowman;
   }

   public static void set(Map<Enchantment, Integer> enchantments, ItemStack stack) {
      ListTag _snowman = new ListTag();

      for (Entry<Enchantment, Integer> _snowmanx : enchantments.entrySet()) {
         Enchantment _snowmanxx = _snowmanx.getKey();
         if (_snowmanxx != null) {
            int _snowmanxxx = _snowmanx.getValue();
            CompoundTag _snowmanxxxx = new CompoundTag();
            _snowmanxxxx.putString("id", String.valueOf(Registry.ENCHANTMENT.getId(_snowmanxx)));
            _snowmanxxxx.putShort("lvl", (short)_snowmanxxx);
            _snowman.add(_snowmanxxxx);
            if (stack.getItem() == Items.ENCHANTED_BOOK) {
               EnchantedBookItem.addEnchantment(stack, new EnchantmentLevelEntry(_snowmanxx, _snowmanxxx));
            }
         }
      }

      if (_snowman.isEmpty()) {
         stack.removeSubTag("Enchantments");
      } else if (stack.getItem() != Items.ENCHANTED_BOOK) {
         stack.putSubTag("Enchantments", _snowman);
      }
   }

   private static void forEachEnchantment(EnchantmentHelper.Consumer consumer, ItemStack stack) {
      if (!stack.isEmpty()) {
         ListTag _snowman = stack.getEnchantments();

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            String _snowmanxx = _snowman.getCompound(_snowmanx).getString("id");
            int _snowmanxxx = _snowman.getCompound(_snowmanx).getInt("lvl");
            Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(_snowmanxx)).ifPresent(enchantment -> consumer.accept(enchantment, _snowman));
         }
      }
   }

   private static void forEachEnchantment(EnchantmentHelper.Consumer consumer, Iterable<ItemStack> stacks) {
      for (ItemStack _snowman : stacks) {
         forEachEnchantment(consumer, _snowman);
      }
   }

   public static int getProtectionAmount(Iterable<ItemStack> equipment, DamageSource source) {
      MutableInt _snowman = new MutableInt();
      forEachEnchantment((enchantment, level) -> _snowman.add(enchantment.getProtectionAmount(level, source)), equipment);
      return _snowman.intValue();
   }

   public static float getAttackDamage(ItemStack stack, EntityGroup group) {
      MutableFloat _snowman = new MutableFloat();
      forEachEnchantment((enchantment, level) -> _snowman.add(enchantment.getAttackDamage(level, group)), stack);
      return _snowman.floatValue();
   }

   public static float getSweepingMultiplier(LivingEntity entity) {
      int _snowman = getEquipmentLevel(Enchantments.SWEEPING, entity);
      return _snowman > 0 ? SweepingEnchantment.getMultiplier(_snowman) : 0.0F;
   }

   public static void onUserDamaged(LivingEntity user, Entity attacker) {
      EnchantmentHelper.Consumer _snowman = (enchantment, level) -> enchantment.onUserDamaged(user, attacker, level);
      if (user != null) {
         forEachEnchantment(_snowman, user.getItemsEquipped());
      }

      if (attacker instanceof PlayerEntity) {
         forEachEnchantment(_snowman, user.getMainHandStack());
      }
   }

   public static void onTargetDamaged(LivingEntity user, Entity target) {
      EnchantmentHelper.Consumer _snowman = (enchantment, level) -> enchantment.onTargetDamaged(user, target, level);
      if (user != null) {
         forEachEnchantment(_snowman, user.getItemsEquipped());
      }

      if (user instanceof PlayerEntity) {
         forEachEnchantment(_snowman, user.getMainHandStack());
      }
   }

   public static int getEquipmentLevel(Enchantment enchantment, LivingEntity entity) {
      Iterable<ItemStack> _snowman = enchantment.getEquipment(entity).values();
      if (_snowman == null) {
         return 0;
      } else {
         int _snowmanx = 0;

         for (ItemStack _snowmanxx : _snowman) {
            int _snowmanxxx = getLevel(enchantment, _snowmanxx);
            if (_snowmanxxx > _snowmanx) {
               _snowmanx = _snowmanxxx;
            }
         }

         return _snowmanx;
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
      Map<EquipmentSlot, ItemStack> _snowman = enchantment.getEquipment(entity);
      if (_snowman.isEmpty()) {
         return null;
      } else {
         List<Entry<EquipmentSlot, ItemStack>> _snowmanx = Lists.newArrayList();

         for (Entry<EquipmentSlot, ItemStack> _snowmanxx : _snowman.entrySet()) {
            ItemStack _snowmanxxx = _snowmanxx.getValue();
            if (!_snowmanxxx.isEmpty() && getLevel(enchantment, _snowmanxxx) > 0 && condition.test(_snowmanxxx)) {
               _snowmanx.add(_snowmanxx);
            }
         }

         return _snowmanx.isEmpty() ? null : _snowmanx.get(entity.getRandom().nextInt(_snowmanx.size()));
      }
   }

   public static int calculateRequiredExperienceLevel(Random random, int slotIndex, int bookshelfCount, ItemStack stack) {
      Item _snowman = stack.getItem();
      int _snowmanx = _snowman.getEnchantability();
      if (_snowmanx <= 0) {
         return 0;
      } else {
         if (bookshelfCount > 15) {
            bookshelfCount = 15;
         }

         int _snowmanxx = random.nextInt(8) + 1 + (bookshelfCount >> 1) + random.nextInt(bookshelfCount + 1);
         if (slotIndex == 0) {
            return Math.max(_snowmanxx / 3, 1);
         } else {
            return slotIndex == 1 ? _snowmanxx * 2 / 3 + 1 : Math.max(_snowmanxx, bookshelfCount * 2);
         }
      }
   }

   public static ItemStack enchant(Random random, ItemStack target, int level, boolean treasureAllowed) {
      List<EnchantmentLevelEntry> _snowman = generateEnchantments(random, target, level, treasureAllowed);
      boolean _snowmanx = target.getItem() == Items.BOOK;
      if (_snowmanx) {
         target = new ItemStack(Items.ENCHANTED_BOOK);
      }

      for (EnchantmentLevelEntry _snowmanxx : _snowman) {
         if (_snowmanx) {
            EnchantedBookItem.addEnchantment(target, _snowmanxx);
         } else {
            target.addEnchantment(_snowmanxx.enchantment, _snowmanxx.level);
         }
      }

      return target;
   }

   public static List<EnchantmentLevelEntry> generateEnchantments(Random random, ItemStack stack, int level, boolean treasureAllowed) {
      List<EnchantmentLevelEntry> _snowman = Lists.newArrayList();
      Item _snowmanx = stack.getItem();
      int _snowmanxx = _snowmanx.getEnchantability();
      if (_snowmanxx <= 0) {
         return _snowman;
      } else {
         level += 1 + random.nextInt(_snowmanxx / 4 + 1) + random.nextInt(_snowmanxx / 4 + 1);
         float _snowmanxxx = (random.nextFloat() + random.nextFloat() - 1.0F) * 0.15F;
         level = MathHelper.clamp(Math.round((float)level + (float)level * _snowmanxxx), 1, Integer.MAX_VALUE);
         List<EnchantmentLevelEntry> _snowmanxxxx = getPossibleEntries(level, stack, treasureAllowed);
         if (!_snowmanxxxx.isEmpty()) {
            _snowman.add(WeightedPicker.getRandom(random, _snowmanxxxx));

            while (random.nextInt(50) <= level) {
               removeConflicts(_snowmanxxxx, Util.getLast(_snowman));
               if (_snowmanxxxx.isEmpty()) {
                  break;
               }

               _snowman.add(WeightedPicker.getRandom(random, _snowmanxxxx));
               level /= 2;
            }
         }

         return _snowman;
      }
   }

   public static void removeConflicts(List<EnchantmentLevelEntry> possibleEntries, EnchantmentLevelEntry pickedEntry) {
      Iterator<EnchantmentLevelEntry> _snowman = possibleEntries.iterator();

      while (_snowman.hasNext()) {
         if (!pickedEntry.enchantment.canCombine(_snowman.next().enchantment)) {
            _snowman.remove();
         }
      }
   }

   public static boolean isCompatible(Collection<Enchantment> existing, Enchantment candidate) {
      for (Enchantment _snowman : existing) {
         if (!_snowman.canCombine(candidate)) {
            return false;
         }
      }

      return true;
   }

   public static List<EnchantmentLevelEntry> getPossibleEntries(int power, ItemStack stack, boolean treasureAllowed) {
      List<EnchantmentLevelEntry> _snowman = Lists.newArrayList();
      Item _snowmanx = stack.getItem();
      boolean _snowmanxx = stack.getItem() == Items.BOOK;

      for (Enchantment _snowmanxxx : Registry.ENCHANTMENT) {
         if ((!_snowmanxxx.isTreasure() || treasureAllowed) && _snowmanxxx.isAvailableForRandomSelection() && (_snowmanxxx.type.isAcceptableItem(_snowmanx) || _snowmanxx)) {
            for (int _snowmanxxxx = _snowmanxxx.getMaxLevel(); _snowmanxxxx > _snowmanxxx.getMinLevel() - 1; _snowmanxxxx--) {
               if (power >= _snowmanxxx.getMinPower(_snowmanxxxx) && power <= _snowmanxxx.getMaxPower(_snowmanxxxx)) {
                  _snowman.add(new EnchantmentLevelEntry(_snowmanxxx, _snowmanxxxx));
                  break;
               }
            }
         }
      }

      return _snowman;
   }

   @FunctionalInterface
   interface Consumer {
      void accept(Enchantment enchantment, int level);
   }
}
