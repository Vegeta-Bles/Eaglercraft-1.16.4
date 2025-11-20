package net.minecraft.loot.condition;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;
import net.minecraft.util.registry.Registry;

public class TableBonusLootCondition implements LootCondition {
   private final Enchantment enchantment;
   private final float[] chances;

   private TableBonusLootCondition(Enchantment enchantment, float[] chances) {
      this.enchantment = enchantment;
      this.chances = chances;
   }

   @Override
   public LootConditionType getType() {
      return LootConditionTypes.TABLE_BONUS;
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(LootContextParameters.TOOL);
   }

   public boolean test(LootContext _snowman) {
      ItemStack _snowmanx = _snowman.get(LootContextParameters.TOOL);
      int _snowmanxx = _snowmanx != null ? EnchantmentHelper.getLevel(this.enchantment, _snowmanx) : 0;
      float _snowmanxxx = this.chances[Math.min(_snowmanxx, this.chances.length - 1)];
      return _snowman.getRandom().nextFloat() < _snowmanxxx;
   }

   public static LootCondition.Builder builder(Enchantment enchantment, float... chances) {
      return () -> new TableBonusLootCondition(enchantment, chances);
   }

   public static class Serializer implements JsonSerializer<TableBonusLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, TableBonusLootCondition _snowman, JsonSerializationContext _snowman) {
         _snowman.addProperty("enchantment", Registry.ENCHANTMENT.getId(_snowman.enchantment).toString());
         _snowman.add("chances", _snowman.serialize(_snowman.chances));
      }

      public TableBonusLootCondition fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         Identifier _snowmanxx = new Identifier(JsonHelper.getString(_snowman, "enchantment"));
         Enchantment _snowmanxxx = Registry.ENCHANTMENT.getOrEmpty(_snowmanxx).orElseThrow(() -> new JsonParseException("Invalid enchantment id: " + _snowman));
         float[] _snowmanxxxx = JsonHelper.deserialize(_snowman, "chances", _snowman, float[].class);
         return new TableBonusLootCondition(_snowmanxxx, _snowmanxxxx);
      }
   }
}
