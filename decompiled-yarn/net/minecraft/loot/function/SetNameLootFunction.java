package net.minecraft.loot.function;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Set;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetNameLootFunction extends ConditionalLootFunction {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Text name;
   @Nullable
   private final LootContext.EntityTarget entity;

   private SetNameLootFunction(LootCondition[] conditions, @Nullable Text name, @Nullable LootContext.EntityTarget entity) {
      super(conditions);
      this.name = name;
      this.entity = entity;
   }

   @Override
   public LootFunctionType getType() {
      return LootFunctionTypes.SET_NAME;
   }

   @Override
   public Set<LootContextParameter<?>> getRequiredParameters() {
      return this.entity != null ? ImmutableSet.of(this.entity.getParameter()) : ImmutableSet.of();
   }

   public static UnaryOperator<Text> applySourceEntity(LootContext context, @Nullable LootContext.EntityTarget sourceEntity) {
      if (sourceEntity != null) {
         Entity _snowman = context.get(sourceEntity.getParameter());
         if (_snowman != null) {
            ServerCommandSource _snowmanx = _snowman.getCommandSource().withLevel(2);
            return textComponent -> {
               try {
                  return Texts.parse(_snowman, textComponent, _snowman, 0);
               } catch (CommandSyntaxException var4) {
                  LOGGER.warn("Failed to resolve text component", var4);
                  return textComponent;
               }
            };
         }
      }

      return textComponent -> textComponent;
   }

   @Override
   public ItemStack process(ItemStack stack, LootContext context) {
      if (this.name != null) {
         stack.setCustomName(applySourceEntity(context, this.entity).apply(this.name));
      }

      return stack;
   }

   public static class Serializer extends ConditionalLootFunction.Serializer<SetNameLootFunction> {
      public Serializer() {
      }

      public void toJson(JsonObject _snowman, SetNameLootFunction _snowman, JsonSerializationContext _snowman) {
         super.toJson(_snowman, _snowman, _snowman);
         if (_snowman.name != null) {
            _snowman.add("name", Text.Serializer.toJsonTree(_snowman.name));
         }

         if (_snowman.entity != null) {
            _snowman.add("entity", _snowman.serialize(_snowman.entity));
         }
      }

      public SetNameLootFunction fromJson(JsonObject _snowman, JsonDeserializationContext _snowman, LootCondition[] _snowman) {
         Text _snowmanxxx = Text.Serializer.fromJson(_snowman.get("name"));
         LootContext.EntityTarget _snowmanxxxx = JsonHelper.deserialize(_snowman, "entity", null, _snowman, LootContext.EntityTarget.class);
         return new SetNameLootFunction(_snowman, _snowmanxxx, _snowmanxxxx);
      }
   }
}
