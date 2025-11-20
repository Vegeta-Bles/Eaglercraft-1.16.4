package net.minecraft.loot.function;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnchantRandomlyLootFunction extends ConditionalLootFunction {
   private static final Logger LOGGER = LogManager.getLogger();
   private final List<Enchantment> enchantments;

   private EnchantRandomlyLootFunction(LootCondition[] conditions, Collection<Enchantment> enchantments) {
      super(conditions);
      this.enchantments = ImmutableList.copyOf(enchantments);
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.ENCHANT_RANDOMLY;
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      Random _snowman = context.getRandom();
      Enchantment _snowmanx;
      if (this.enchantments.isEmpty()) {
         boolean _snowmanxx = stack.getItem() == Items.BOOK;
         List<Enchantment> _snowmanxxx = Registry.ENCHANTMENT
            .stream()
            .filter(Enchantment::isAvailableForRandomSelection)
            .filter(_snowmanxxxx -> _snowman || _snowmanxxxx.isAcceptableItem(stack))
            .collect(Collectors.toList());
         if (_snowmanxxx.isEmpty()) {
            LOGGER.warn("Couldn't find a compatible enchantment for {}", stack);
            return stack;
         }

         _snowmanx = _snowmanxxx.get(_snowman.nextInt(_snowmanxxx.size()));
      } else {
         _snowmanx = this.enchantments.get(_snowman.nextInt(this.enchantments.size()));
      }

      return method_26266(stack, _snowmanx, _snowman);
   }

   private static ItemStack method_26266(ItemStack _snowman, Enchantment _snowman, Random _snowman) {
      int _snowmanxxx = MathHelper.nextInt(_snowman, _snowman.getMinLevel(), _snowman.getMaxLevel());
      if (_snowman.getItem() == Items.BOOK) {
         _snowman = new ItemStack(Items.ENCHANTED_BOOK);
         EnchantedBookItem.addEnchantment(_snowman, new EnchantmentLevelEntry(_snowman, _snowmanxxx));
      } else {
         _snowman.addEnchantment(_snowman, _snowmanxxx);
      }

      return _snowman;
   }

   public static ConditionalLootFunction.Builder<?> builder() {
      return builder(conditions -> new EnchantRandomlyLootFunction(conditions, ImmutableList.of()));
   }

   public static class Builder extends ConditionalLootFunction.Builder<EnchantRandomlyLootFunction.Builder> {
      private final Set<Enchantment> enchantments = Sets.newHashSet();

      public Builder() {
      }

      protected EnchantRandomlyLootFunction.Builder getThisBuilder() {
         return this;
      }

      public EnchantRandomlyLootFunction.Builder add(Enchantment enchantment) {
         this.enchantments.add(enchantment);
         return this;
      }

      @Override
      public LootFunction build() {
         return new EnchantRandomlyLootFunction(this.getConditions(), this.enchantments);
      }
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<EnchantRandomlyLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, EnchantRandomlyLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         if (!_snowman.enchantments.isEmpty()) {
            JsonArray _snowmanxxx = new JsonArray();

            for (Enchantment _snowmanxxxx : _snowman.enchantments) {
               Identifier _snowmanxxxxx = Registry.ENCHANTMENT.getId(_snowmanxxxx);
               if (_snowmanxxxxx == null) {
                  throw new IllegalArgumentException("Don't know how to serialize enchantment " + _snowmanxxxx);
               }

               _snowmanxxx.add(new JsonPrimitive(_snowmanxxxxx.toString()));
            }

            _snowman.add("enchantments", _snowmanxxx);
         }
      }

      public EnchantRandomlyLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         List<Enchantment> _snowmanxxx = Lists.newArrayList();
         if (_snowman.has("enchantments")) {
            for (JsonElement _snowmanxxxx : JsonHelper.getArray(_snowman, "enchantments")) {
               String _snowmanxxxxx = JsonHelper.asString(_snowmanxxxx, "enchantment");
               Enchantment _snowmanxxxxxx = Registry.ENCHANTMENT
                  .getOrEmpty(new Identifier(_snowmanxxxxx))
                  .orElseThrow(() -> new JsonSyntaxException("Unknown enchantment '" + _snowman + "'"));
               _snowmanxxx.add(_snowmanxxxxxx);
            }
         }

         return new EnchantRandomlyLootFunction(_snowman, _snowmanxxx);
      }
   }
}
