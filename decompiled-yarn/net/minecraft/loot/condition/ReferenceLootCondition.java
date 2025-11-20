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
         LootCondition _snowman = reporter.getCondition(this.id);
         if (_snowman == null) {
            reporter.report("Unknown condition table called " + this.id);
         } else {
            _snowman.validate(reporter.withTable(".{" + this.id + "}", this.id));
         }
      }
   }

   public boolean test(LootContext _snowman) {
      LootCondition _snowmanx = _snowman.getCondition(this.id);
      if (_snowman.addCondition(_snowmanx)) {
         boolean var3;
         try {
            var3 = _snowmanx.test(_snowman);
         } finally {
            _snowman.removeCondition(_snowmanx);
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

      public void toJson(JsonObject _snowman, ReferenceLootCondition _snowman, JsonSerializationContext _snowman) {
         _snowman.addProperty("name", _snowman.id.toString());
      }

      public ReferenceLootCondition fromJson(JsonObject _snowman, JsonDeserializationContext _snowman) {
         Identifier _snowmanxx = new Identifier(JsonHelper.getString(_snowman, "name"));
         return new ReferenceLootCondition(_snowmanxx);
      }
   }
}
