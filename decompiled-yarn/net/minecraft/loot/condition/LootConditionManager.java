package net.minecraft.loot.condition;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.loot.LootGsons;
import net.minecraft.loot.LootTableReporter;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootConditionManager extends JsonDataLoader {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Gson GSON = LootGsons.getConditionGsonBuilder().create();
   private Map<Identifier, LootCondition> conditions = ImmutableMap.of();

   public LootConditionManager() {
      super(GSON, "predicates");
   }

   @Nullable
   public LootCondition get(Identifier id) {
      return this.conditions.get(id);
   }

   protected void apply(Map<Identifier, JsonElement> _snowman, ResourceManager _snowman, Profiler _snowman) {
      Builder<Identifier, LootCondition> _snowmanxxx = ImmutableMap.builder();
      _snowman.forEach((_snowmanxxxxxxx, _snowmanxxxxxx) -> {
         try {
            if (_snowmanxxxxxx.isJsonArray()) {
               LootCondition[] _snowmanxxx = (LootCondition[])GSON.fromJson(_snowmanxxxxxx, LootCondition[].class);
               _snowman.put(_snowmanxxxxxxx, new LootConditionManager.AndCondition(_snowmanxxx));
            } else {
               LootCondition _snowmanxxx = (LootCondition)GSON.fromJson(_snowmanxxxxxx, LootCondition.class);
               _snowman.put(_snowmanxxxxxxx, _snowmanxxx);
            }
         } catch (Exception var4x) {
            LOGGER.error("Couldn't parse loot table {}", _snowmanxxxxxxx, var4x);
         }
      });
      Map<Identifier, LootCondition> _snowmanxxxx = _snowmanxxx.build();
      LootTableReporter _snowmanxxxxx = new LootTableReporter(LootContextTypes.GENERIC, _snowmanxxxx::get, _snowmanxxxxxx -> null);
      _snowmanxxxx.forEach((_snowmanxxxxxx, _snowmanxxxxxxx) -> _snowmanxxxxxxx.validate(_snowman.withCondition("{" + _snowmanxxxxxx + "}", _snowmanxxxxxx)));
      _snowmanxxxxx.getMessages().forEach((_snowmanxxxxxx, _snowmanxxxxxxx) -> LOGGER.warn("Found validation problem in " + _snowmanxxxxxx + ": " + _snowmanxxxxxxx));
      this.conditions = _snowmanxxxx;
   }

   public Set<Identifier> getIds() {
      return Collections.unmodifiableSet(this.conditions.keySet());
   }

   static class AndCondition implements LootCondition {
      private final LootCondition[] terms;
      private final Predicate<LootContext> predicate;

      private AndCondition(LootCondition[] elements) {
         this.terms = elements;
         this.predicate = LootConditionTypes.joinAnd(elements);
      }

      public final boolean test(LootContext _snowman) {
         return this.predicate.test(_snowman);
      }

      @Override
      public void validate(LootTableReporter reporter) {
         LootCondition.super.validate(reporter);

         for (int _snowman = 0; _snowman < this.terms.length; _snowman++) {
            this.terms[_snowman].validate(reporter.makeChild(".term[" + _snowman + "]"));
         }
      }

      @Override
      public LootConditionType getType() {
         throw new UnsupportedOperationException();
      }
   }
}
