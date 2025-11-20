package net.minecraft.advancement.criterion;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

public class InventoryChangedCriterion extends AbstractCriterion<InventoryChangedCriterion.Conditions> {
   private static final Identifier ID = new Identifier("inventory_changed");

   public InventoryChangedCriterion() {
   }

   @Override
   public Identifier getId() {
      return ID;
   }

   public InventoryChangedCriterion.Conditions conditionsFromJson(JsonObject _snowman, EntityPredicate.Extended _snowman, AdvancementEntityPredicateDeserializer _snowman) {
      JsonObject _snowmanxxx = JsonHelper.getObject(_snowman, "slots", new JsonObject());
      NumberRange.IntRange _snowmanxxxx = NumberRange.IntRange.fromJson(_snowmanxxx.get("occupied"));
      NumberRange.IntRange _snowmanxxxxx = NumberRange.IntRange.fromJson(_snowmanxxx.get("full"));
      NumberRange.IntRange _snowmanxxxxxx = NumberRange.IntRange.fromJson(_snowmanxxx.get("empty"));
      ItemPredicate[] _snowmanxxxxxxx = ItemPredicate.deserializeAll(_snowman.get("items"));
      return new InventoryChangedCriterion.Conditions(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
   }

   public void trigger(ServerPlayerEntity player, PlayerInventory inventory, ItemStack stack) {
      int _snowman = 0;
      int _snowmanx = 0;
      int _snowmanxx = 0;

      for (int _snowmanxxx = 0; _snowmanxxx < inventory.size(); _snowmanxxx++) {
         ItemStack _snowmanxxxx = inventory.getStack(_snowmanxxx);
         if (_snowmanxxxx.isEmpty()) {
            _snowmanx++;
         } else {
            _snowmanxx++;
            if (_snowmanxxxx.getCount() >= _snowmanxxxx.getMaxCount()) {
               _snowman++;
            }
         }
      }

      this.trigger(player, inventory, stack, _snowman, _snowmanx, _snowmanxx);
   }

   private void trigger(ServerPlayerEntity player, PlayerInventory inventory, ItemStack stack, int full, int empty, int occupied) {
      this.test(player, _snowmanxxxxx -> _snowmanxxxxx.matches(inventory, stack, full, empty, occupied));
   }

   public static class Conditions extends AbstractCriterionConditions {
      private final NumberRange.IntRange occupied;
      private final NumberRange.IntRange full;
      private final NumberRange.IntRange empty;
      private final ItemPredicate[] items;

      public Conditions(
         EntityPredicate.Extended player, NumberRange.IntRange occupied, NumberRange.IntRange full, NumberRange.IntRange empty, ItemPredicate[] items
      ) {
         super(InventoryChangedCriterion.ID, player);
         this.occupied = occupied;
         this.full = full;
         this.empty = empty;
         this.items = items;
      }

      public static InventoryChangedCriterion.Conditions items(ItemPredicate... items) {
         return new InventoryChangedCriterion.Conditions(
            EntityPredicate.Extended.EMPTY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, NumberRange.IntRange.ANY, items
         );
      }

      public static InventoryChangedCriterion.Conditions items(ItemConvertible... items) {
         ItemPredicate[] _snowman = new ItemPredicate[items.length];

         for (int _snowmanx = 0; _snowmanx < items.length; _snowmanx++) {
            _snowman[_snowmanx] = new ItemPredicate(
               null,
               items[_snowmanx].asItem(),
               NumberRange.IntRange.ANY,
               NumberRange.IntRange.ANY,
               EnchantmentPredicate.ARRAY_OF_ANY,
               EnchantmentPredicate.ARRAY_OF_ANY,
               null,
               NbtPredicate.ANY
            );
         }

         return items(_snowman);
      }

      @Override
      public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
         JsonObject _snowman = super.toJson(predicateSerializer);
         if (!this.occupied.isDummy() || !this.full.isDummy() || !this.empty.isDummy()) {
            JsonObject _snowmanx = new JsonObject();
            _snowmanx.add("occupied", this.occupied.toJson());
            _snowmanx.add("full", this.full.toJson());
            _snowmanx.add("empty", this.empty.toJson());
            _snowman.add("slots", _snowmanx);
         }

         if (this.items.length > 0) {
            JsonArray _snowmanx = new JsonArray();

            for (ItemPredicate _snowmanxx : this.items) {
               _snowmanx.add(_snowmanxx.toJson());
            }

            _snowman.add("items", _snowmanx);
         }

         return _snowman;
      }

      public boolean matches(PlayerInventory inventory, ItemStack stack, int full, int empty, int occupied) {
         if (!this.full.test(full)) {
            return false;
         } else if (!this.empty.test(empty)) {
            return false;
         } else if (!this.occupied.test(occupied)) {
            return false;
         } else {
            int _snowman = this.items.length;
            if (_snowman == 0) {
               return true;
            } else if (_snowman != 1) {
               List<ItemPredicate> _snowmanx = new ObjectArrayList(this.items);
               int _snowmanxx = inventory.size();

               for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
                  if (_snowmanx.isEmpty()) {
                     return true;
                  }

                  ItemStack _snowmanxxxx = inventory.getStack(_snowmanxxx);
                  if (!_snowmanxxxx.isEmpty()) {
                     _snowmanx.removeIf(_snowmanxxxxx -> _snowmanxxxxx.test(_snowman));
                  }
               }

               return _snowmanx.isEmpty();
            } else {
               return !stack.isEmpty() && this.items[0].test(stack);
            }
         }
      }
   }
}
