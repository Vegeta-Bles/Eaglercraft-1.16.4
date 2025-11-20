package net.minecraft.predicate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.State;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.StringIdentifiable;

public class StatePredicate {
   public static final StatePredicate ANY = new StatePredicate(ImmutableList.of());
   private final List<StatePredicate.Condition> conditions;

   private static StatePredicate.Condition createPredicate(String key, JsonElement json) {
      if (json.isJsonPrimitive()) {
         String _snowman = json.getAsString();
         return new StatePredicate.ExactValueCondition(key, _snowman);
      } else {
         JsonObject _snowman = JsonHelper.asObject(json, "value");
         String _snowmanx = _snowman.has("min") ? asNullableString(_snowman.get("min")) : null;
         String _snowmanxx = _snowman.has("max") ? asNullableString(_snowman.get("max")) : null;
         return (StatePredicate.Condition)(_snowmanx != null && _snowmanx.equals(_snowmanxx)
            ? new StatePredicate.ExactValueCondition(key, _snowmanx)
            : new StatePredicate.RangedValueCondition(key, _snowmanx, _snowmanxx));
      }
   }

   @Nullable
   private static String asNullableString(JsonElement json) {
      return json.isJsonNull() ? null : json.getAsString();
   }

   private StatePredicate(List<StatePredicate.Condition> testers) {
      this.conditions = ImmutableList.copyOf(testers);
   }

   public <S extends State<?, S>> boolean test(StateManager<?, S> stateManager, S container) {
      for (StatePredicate.Condition _snowman : this.conditions) {
         if (!_snowman.test(stateManager, container)) {
            return false;
         }
      }

      return true;
   }

   public boolean test(BlockState state) {
      return this.test(state.getBlock().getStateManager(), state);
   }

   public boolean test(FluidState state) {
      return this.test(state.getFluid().getStateManager(), state);
   }

   public void check(StateManager<?, ?> factory, Consumer<String> reporter) {
      this.conditions.forEach(_snowmanxx -> _snowmanxx.reportMissing(factory, reporter));
   }

   public static StatePredicate fromJson(@Nullable JsonElement json) {
      if (json != null && !json.isJsonNull()) {
         JsonObject _snowman = JsonHelper.asObject(json, "properties");
         List<StatePredicate.Condition> _snowmanx = Lists.newArrayList();

         for (Entry<String, JsonElement> _snowmanxx : _snowman.entrySet()) {
            _snowmanx.add(createPredicate(_snowmanxx.getKey(), _snowmanxx.getValue()));
         }

         return new StatePredicate(_snowmanx);
      } else {
         return ANY;
      }
   }

   public JsonElement toJson() {
      if (this == ANY) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         if (!this.conditions.isEmpty()) {
            this.conditions.forEach(_snowmanx -> _snowman.add(_snowmanx.getKey(), _snowmanx.toJson()));
         }

         return _snowman;
      }
   }

   public static class Builder {
      private final List<StatePredicate.Condition> conditions = Lists.newArrayList();

      private Builder() {
      }

      public static StatePredicate.Builder create() {
         return new StatePredicate.Builder();
      }

      public StatePredicate.Builder exactMatch(Property<?> property, String valueName) {
         this.conditions.add(new StatePredicate.ExactValueCondition(property.getName(), valueName));
         return this;
      }

      public StatePredicate.Builder exactMatch(Property<Integer> property, int value) {
         return this.exactMatch(property, Integer.toString(value));
      }

      public StatePredicate.Builder exactMatch(Property<Boolean> property, boolean value) {
         return this.exactMatch(property, Boolean.toString(value));
      }

      public <T extends Comparable<T> & StringIdentifiable> StatePredicate.Builder exactMatch(Property<T> property, T value) {
         return this.exactMatch(property, value.asString());
      }

      public StatePredicate build() {
         return new StatePredicate(this.conditions);
      }
   }

   abstract static class Condition {
      private final String key;

      public Condition(String key) {
         this.key = key;
      }

      public <S extends State<?, S>> boolean test(StateManager<?, S> stateManager, S state) {
         Property<?> _snowman = stateManager.getProperty(this.key);
         return _snowman == null ? false : this.test(state, _snowman);
      }

      protected abstract <T extends Comparable<T>> boolean test(State<?, ?> state, Property<T> property);

      public abstract JsonElement toJson();

      public String getKey() {
         return this.key;
      }

      public void reportMissing(StateManager<?, ?> factory, Consumer<String> reporter) {
         Property<?> _snowman = factory.getProperty(this.key);
         if (_snowman == null) {
            reporter.accept(this.key);
         }
      }
   }

   static class ExactValueCondition extends StatePredicate.Condition {
      private final String value;

      public ExactValueCondition(String key, String value) {
         super(key);
         this.value = value;
      }

      @Override
      protected <T extends Comparable<T>> boolean test(State<?, ?> state, Property<T> property) {
         T _snowman = state.get(property);
         Optional<T> _snowmanx = property.parse(this.value);
         return _snowmanx.isPresent() && _snowman.compareTo(_snowmanx.get()) == 0;
      }

      @Override
      public JsonElement toJson() {
         return new JsonPrimitive(this.value);
      }
   }

   static class RangedValueCondition extends StatePredicate.Condition {
      @Nullable
      private final String min;
      @Nullable
      private final String max;

      public RangedValueCondition(String key, @Nullable String min, @Nullable String max) {
         super(key);
         this.min = min;
         this.max = max;
      }

      @Override
      protected <T extends Comparable<T>> boolean test(State<?, ?> state, Property<T> property) {
         T _snowman = state.get(property);
         if (this.min != null) {
            Optional<T> _snowmanx = property.parse(this.min);
            if (!_snowmanx.isPresent() || _snowman.compareTo(_snowmanx.get()) < 0) {
               return false;
            }
         }

         if (this.max != null) {
            Optional<T> _snowmanx = property.parse(this.max);
            if (!_snowmanx.isPresent() || _snowman.compareTo(_snowmanx.get()) > 0) {
               return false;
            }
         }

         return true;
      }

      @Override
      public JsonElement toJson() {
         JsonObject _snowman = new JsonObject();
         if (this.min != null) {
            _snowman.addProperty("min", this.min);
         }

         if (this.max != null) {
            _snowman.addProperty("max", this.max);
         }

         return _snowman;
      }
   }
}
