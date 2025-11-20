package net.minecraft.predicate.item;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.predicate.NumberRange;
import net.minecraft.tag.ServerTagManagerHolder;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;

public class ItemPredicate {
   public static final ItemPredicate ANY = new ItemPredicate();
   @Nullable
   private final Tag<Item> tag;
   @Nullable
   private final Item item;
   private final NumberRange.IntRange count;
   private final NumberRange.IntRange durability;
   private final EnchantmentPredicate[] enchantments;
   private final EnchantmentPredicate[] storedEnchantments;
   @Nullable
   private final Potion potion;
   private final NbtPredicate nbt;

   public ItemPredicate() {
      this.tag = null;
      this.item = null;
      this.potion = null;
      this.count = NumberRange.IntRange.ANY;
      this.durability = NumberRange.IntRange.ANY;
      this.enchantments = EnchantmentPredicate.ARRAY_OF_ANY;
      this.storedEnchantments = EnchantmentPredicate.ARRAY_OF_ANY;
      this.nbt = NbtPredicate.ANY;
   }

   public ItemPredicate(
      @Nullable Tag<Item> tag,
      @Nullable Item item,
      NumberRange.IntRange count,
      NumberRange.IntRange durability,
      EnchantmentPredicate[] enchantments,
      EnchantmentPredicate[] storedEnchantments,
      @Nullable Potion potion,
      NbtPredicate nbt
   ) {
      this.tag = tag;
      this.item = item;
      this.count = count;
      this.durability = durability;
      this.enchantments = enchantments;
      this.storedEnchantments = storedEnchantments;
      this.potion = potion;
      this.nbt = nbt;
   }

   public boolean test(ItemStack stack) {
      if (this == ANY) {
         return true;
      } else if (this.tag != null && !this.tag.contains(stack.getItem())) {
         return false;
      } else if (this.item != null && stack.getItem() != this.item) {
         return false;
      } else if (!this.count.test(stack.getCount())) {
         return false;
      } else if (!this.durability.isDummy() && !stack.isDamageable()) {
         return false;
      } else if (!this.durability.test(stack.getMaxDamage() - stack.getDamage())) {
         return false;
      } else if (!this.nbt.test(stack)) {
         return false;
      } else {
         if (this.enchantments.length > 0) {
            Map<Enchantment, Integer> _snowman = EnchantmentHelper.fromTag(stack.getEnchantments());

            for (EnchantmentPredicate _snowmanx : this.enchantments) {
               if (!_snowmanx.test(_snowman)) {
                  return false;
               }
            }
         }

         if (this.storedEnchantments.length > 0) {
            Map<Enchantment, Integer> _snowman = EnchantmentHelper.fromTag(EnchantedBookItem.getEnchantmentTag(stack));

            for (EnchantmentPredicate _snowmanxx : this.storedEnchantments) {
               if (!_snowmanxx.test(_snowman)) {
                  return false;
               }
            }
         }

         Potion _snowman = PotionUtil.getPotion(stack);
         return this.potion == null || this.potion == _snowman;
      }
   }

   public static ItemPredicate fromJson(@Nullable JsonElement el) {
      if (el != null && !el.isJsonNull()) {
         JsonObject _snowman = JsonHelper.asObject(el, "item");
         NumberRange.IntRange _snowmanx = NumberRange.IntRange.fromJson(_snowman.get("count"));
         NumberRange.IntRange _snowmanxx = NumberRange.IntRange.fromJson(_snowman.get("durability"));
         if (_snowman.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
         } else {
            NbtPredicate _snowmanxxx = NbtPredicate.fromJson(_snowman.get("nbt"));
            Item _snowmanxxxx = null;
            if (_snowman.has("item")) {
               Identifier _snowmanxxxxx = new Identifier(JsonHelper.getString(_snowman, "item"));
               _snowmanxxxx = Registry.ITEM.getOrEmpty(_snowmanxxxxx).orElseThrow(() -> new JsonSyntaxException("Unknown item id '" + _snowman + "'"));
            }

            Tag<Item> _snowmanxxxxx = null;
            if (_snowman.has("tag")) {
               Identifier _snowmanxxxxxx = new Identifier(JsonHelper.getString(_snowman, "tag"));
               _snowmanxxxxx = ServerTagManagerHolder.getTagManager().getItems().getTag(_snowmanxxxxxx);
               if (_snowmanxxxxx == null) {
                  throw new JsonSyntaxException("Unknown item tag '" + _snowmanxxxxxx + "'");
               }
            }

            Potion _snowmanxxxxxx = null;
            if (_snowman.has("potion")) {
               Identifier _snowmanxxxxxxx = new Identifier(JsonHelper.getString(_snowman, "potion"));
               _snowmanxxxxxx = Registry.POTION.getOrEmpty(_snowmanxxxxxxx).orElseThrow(() -> new JsonSyntaxException("Unknown potion '" + _snowman + "'"));
            }

            EnchantmentPredicate[] _snowmanxxxxxxx = EnchantmentPredicate.deserializeAll(_snowman.get("enchantments"));
            EnchantmentPredicate[] _snowmanxxxxxxxx = EnchantmentPredicate.deserializeAll(_snowman.get("stored_enchantments"));
            return new ItemPredicate(_snowmanxxxxx, _snowmanxxxx, _snowmanx, _snowmanxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxx, _snowmanxxx);
         }
      } else {
         return ANY;
      }
   }

   public JsonElement toJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         if (this.item != null) {
            _snowman.addProperty("item", Registry.ITEM.getId(this.item).toString());
         }

         if (this.tag != null) {
            _snowman.addProperty("tag", ServerTagManagerHolder.getTagManager().getItems().getTagId(this.tag).toString());
         }

         _snowman.add("count", this.count.toJson());
         _snowman.add("durability", this.durability.toJson());
         _snowman.add("nbt", this.nbt.toJson());
         if (this.enchantments.length > 0) {
            JsonArray _snowmanx = new JsonArray();

            for (EnchantmentPredicate _snowmanxx : this.enchantments) {
               _snowmanx.add(_snowmanxx.serialize());
            }

            _snowman.add("enchantments", _snowmanx);
         }

         if (this.storedEnchantments.length > 0) {
            JsonArray _snowmanx = new JsonArray();

            for (EnchantmentPredicate _snowmanxx : this.storedEnchantments) {
               _snowmanx.add(_snowmanxx.serialize());
            }

            _snowman.add("stored_enchantments", _snowmanx);
         }

         if (this.potion != null) {
            _snowman.addProperty("potion", Registry.POTION.getId(this.potion).toString());
         }

         return _snowman;
      }
   }

   public static ItemPredicate[] deserializeAll(@Nullable JsonElement el) {
      if (el != null && !el.isJsonNull()) {
         JsonArray _snowman = JsonHelper.asArray(el, "items");
         ItemPredicate[] _snowmanx = new ItemPredicate[_snowman.size()];

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length; _snowmanxx++) {
            _snowmanx[_snowmanxx] = fromJson(_snowman.get(_snowmanxx));
         }

         return _snowmanx;
      } else {
         return new ItemPredicate[0];
      }
   }

   public static class Builder {
      private final List<EnchantmentPredicate> enchantments = Lists.newArrayList();
      private final List<EnchantmentPredicate> storedEnchantments = Lists.newArrayList();
      @Nullable
      private Item item;
      @Nullable
      private Tag<Item> tag;
      private NumberRange.IntRange count = NumberRange.IntRange.ANY;
      private NumberRange.IntRange durability = NumberRange.IntRange.ANY;
      @Nullable
      private Potion potion;
      private NbtPredicate nbt = NbtPredicate.ANY;

      private Builder() {
      }

      public static ItemPredicate.Builder create() {
         return new ItemPredicate.Builder();
      }

      public ItemPredicate.Builder item(ItemConvertible item) {
         this.item = item.asItem();
         return this;
      }

      public ItemPredicate.Builder tag(Tag<Item> tag) {
         this.tag = tag;
         return this;
      }

      public ItemPredicate.Builder nbt(CompoundTag nbt) {
         this.nbt = new NbtPredicate(nbt);
         return this;
      }

      public ItemPredicate.Builder enchantment(EnchantmentPredicate enchantment) {
         this.enchantments.add(enchantment);
         return this;
      }

      public ItemPredicate build() {
         return new ItemPredicate(
            this.tag,
            this.item,
            this.count,
            this.durability,
            this.enchantments.toArray(EnchantmentPredicate.ARRAY_OF_ANY),
            this.storedEnchantments.toArray(EnchantmentPredicate.ARRAY_OF_ANY),
            this.potion,
            this.nbt
         );
      }
   }
}
