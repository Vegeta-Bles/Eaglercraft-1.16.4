package net.minecraft.loot.function;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Nameable;

public class CopyNameLootFunction extends ConditionalLootFunction {
   private final CopyNameLootFunction.Source source;

   private CopyNameLootFunction(LootCondition[] conditions, CopyNameLootFunction.Source source) {
      super(conditions);
      this.source = source;
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.COPY_NAME;
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return ImmutableSet.of(this.source.parameter);
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      Object _snowman = context.get(this.source.parameter);
      if (_snowman instanceof Nameable) {
         Nameable _snowmanx = (Nameable)_snowman;
         if (_snowmanx.hasCustomName()) {
            stack.setCustomName(_snowmanx.getDisplayName());
         }
      }

      return stack;
   }

   public static ConditionalLootFunction.Builder<?> builder(CopyNameLootFunction.Source source) {
      return builder(conditions -> new CopyNameLootFunction(conditions, source));
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<CopyNameLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, CopyNameLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         _snowman.addProperty("source", _snowman.source.name);
      }

      public CopyNameLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         CopyNameLootFunction.Source _snowmanxxx = CopyNameLootFunction.Source.get(JsonHelper.getString(_snowman, "source"));
         return new CopyNameLootFunction(_snowman, _snowmanxxx);
      }
   }

   public static enum Source {
      THIS("this", LootContextParameters.THIS_ENTITY),
      KILLER("killer", LootContextParameters.KILLER_ENTITY),
      KILLER_PLAYER("killer_player", LootContextParameters.LAST_DAMAGE_PLAYER),
      BLOCK_ENTITY("block_entity", LootContextParameters.BLOCK_ENTITY);

      public final String name;
      public final LootContextParameter<?> parameter;

      private Source(String name, LootContextParameter<?> parameter) {
         this.name = name;
         this.parameter = parameter;
      }

      public static CopyNameLootFunction.Source get(String name) {
         for (CopyNameLootFunction.Source _snowman : values()) {
            if (_snowman.name.equals(name)) {
               return _snowman;
            }
         }

         throw new IllegalArgumentException("Invalid name source " + name);
      }
   }
}
