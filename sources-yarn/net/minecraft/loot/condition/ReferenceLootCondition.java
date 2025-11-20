package net.minecraft.loot.condition;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.JsonSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReferenceLootCondition implements LootCondition {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Identifier id;

   private ReferenceLootCondition(Identifier id) {
      this.id = id;
   }

   @Override
   public LootConditionType getType() {
      return LootConditionTypes.REFERENCE;
   }

   @Override
   public void validate(LootTableReporter reporter) {
      if (reporter.hasCondition(this.id)) {
         reporter.report("Condition " + this.id + " is recursively called");
      } else {
         LootCondition.super.validate(reporter);
         LootCondition lv = reporter.getCondition(this.id);
         if (lv == null) {
            reporter.report("Unknown condition table called " + this.id);
         } else {
            lv.validate(reporter.withTable(".{" + this.id + "}", this.id));
         }
      }
   }

   public boolean test(LootContext arg) {
      LootCondition lv = arg.getCondition(this.id);
      if (arg.addCondition(lv)) {
         boolean var3;
         try {
            var3 = lv.test(arg);
         } finally {
            arg.removeCondition(lv);
         }

         return var3;
      } else {
         LOGGER.warn("Detected infinite loop in loot tables");
         return false;
      }
   }

   public static class Serializer implements JsonSerializer<ReferenceLootCondition> {
      public Serializer() {
      }

      public void toJson(JsonObject jsonObject, ReferenceLootCondition arg, JsonSerializationContext jsonSerializationContext) {
         jsonObject.addProperty("name", arg.id.toString());
      }

      public ReferenceLootCondition fromJson(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) {
         Identifier lv = new Identifier(JsonHelper.getString(jsonObject, "name"));
         return new ReferenceLootCondition(lv);
      }
   }
}
