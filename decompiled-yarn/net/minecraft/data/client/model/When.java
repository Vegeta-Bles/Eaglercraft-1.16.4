package net.minecraft.data.client.model;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;

public interface When extends Supplier<JsonElement> {
   void validate(StateManager<?, ?> stateManager);

   static When.PropertyCondition create() {
      return new When.PropertyCondition();
   }

   static When anyOf(When... conditions) {
      return new When.LogicalCondition(When.LogicalOperator.OR, Arrays.asList(conditions));
   }

   public static class LogicalCondition implements When {
      private final When.LogicalOperator operator;
      private final List<When> components;

      private LogicalCondition(When.LogicalOperator operator, List<When> components) {
         this.operator = operator;
         this.components = components;
      }

      @Override
      public void validate(StateManager<?, ?> stateManager) {
         this.components.forEach(_snowmanx -> _snowmanx.validate(stateManager));
      }

      public JsonElement get() {
         JsonArray _snowman = new JsonArray();
         this.components.stream().map(Supplier::get).forEach(_snowman::add);
         JsonObject _snowmanx = new JsonObject();
         _snowmanx.add(this.operator.name, _snowman);
         return _snowmanx;
      }
   }

   public static enum LogicalOperator {
      AND("AND"),
      OR("OR");

      private final String name;

      private LogicalOperator(String name) {
         this.name = name;
      }
   }

   public static class PropertyCondition implements When {
      private final Map<Property<?>, String> properties = Maps.newHashMap();

      public PropertyCondition() {
      }

      private static <T extends Comparable<T>> String name(Property<T> property, Stream<T> valueStream) {
         return valueStream.map(property::name).collect(Collectors.joining("|"));
      }

      private static <T extends Comparable<T>> String name(Property<T> property, T value, T[] otherValues) {
         return name(property, Stream.concat(Stream.of(value), Stream.of(otherValues)));
      }

      private <T extends Comparable<T>> void set(Property<T> property, String value) {
         String _snowman = this.properties.put(property, value);
         if (_snowman != null) {
            throw new IllegalStateException("Tried to replace " + property + " value from " + _snowman + " to " + value);
         }
      }

      public final <T extends Comparable<T>> When.PropertyCondition set(Property<T> property, T value) {
         this.set(property, property.name(value));
         return this;
      }

      @SafeVarargs
      public final <T extends Comparable<T>> When.PropertyCondition set(Property<T> property, T value, T... otherValues) {
         this.set(property, name(property, value, otherValues));
         return this;
      }

      public JsonElement get() {
         JsonObject _snowman = new JsonObject();
         this.properties.forEach((_snowmanx, _snowmanxx) -> _snowman.addProperty(_snowmanx.getName(), _snowmanxx));
         return _snowman;
      }

      @Override
      public void validate(StateManager<?, ?> stateManager) {
         List<Property<?>> _snowman = this.properties.keySet().stream().filter(_snowmanx -> stateManager.getProperty(_snowmanx.getName()) != _snowmanx).collect(Collectors.toList());
         if (!_snowman.isEmpty()) {
            throw new IllegalStateException("Properties " + _snowman + " are missing from " + stateManager);
         }
      }
   }
}
