package net.minecraft.data.client.model;

import com.google.common.collect.ImmutableList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.state.property.Property;

public final class PropertiesMap {
   private static final PropertiesMap EMPTY = new PropertiesMap(ImmutableList.of());
   private static final Comparator<Property.Value<?>> COMPARATOR = Comparator.comparing(_snowman -> _snowman.getProperty().getName());
   private final List<Property.Value<?>> propertyValues;

   public PropertiesMap method_25819(Property.Value<?> _snowman) {
      return new PropertiesMap(ImmutableList.builder().addAll(this.propertyValues).add(_snowman).build());
   }

   public PropertiesMap with(PropertiesMap _snowman) {
      return new PropertiesMap(ImmutableList.builder().addAll(this.propertyValues).addAll(_snowman.propertyValues).build());
   }

   private PropertiesMap(List<Property.Value<?>> _snowman) {
      this.propertyValues = _snowman;
   }

   public static PropertiesMap empty() {
      return EMPTY;
   }

   public static PropertiesMap method_25821(Property.Value<?>... _snowman) {
      return new PropertiesMap(ImmutableList.copyOf(_snowman));
   }

   @Override
   public boolean equals(Object _snowman) {
      return this == _snowman || _snowman instanceof PropertiesMap && this.propertyValues.equals(((PropertiesMap)_snowman).propertyValues);
   }

   @Override
   public int hashCode() {
      return this.propertyValues.hashCode();
   }

   public String asString() {
      return this.propertyValues.stream().sorted(COMPARATOR).map(Property.Value::toString).collect(Collectors.joining(","));
   }

   @Override
   public String toString() {
      return this.asString();
   }
}
