package net.minecraft.state.property;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.util.StringIdentifiable;

public class EnumProperty<T extends Enum<T> & StringIdentifiable> extends Property<T> {
   private final ImmutableSet<T> values;
   private final Map<String, T> byName = Maps.newHashMap();

   protected EnumProperty(String name, Class<T> type, Collection<T> values) {
      super(name, type);
      this.values = ImmutableSet.copyOf(values);

      for (T _snowman : values) {
         String _snowmanx = _snowman.asString();
         if (this.byName.containsKey(_snowmanx)) {
            throw new IllegalArgumentException("Multiple values have the same name '" + _snowmanx + "'");
         }

         this.byName.put(_snowmanx, _snowman);
      }
   }

   @Override
   public Collection<T> getValues() {
      return this.values;
   }

   @Override
   public Optional<T> parse(String name) {
      return Optional.ofNullable(this.byName.get(name));
   }

   public String name(T _snowman) {
      return _snowman.asString();
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else if (_snowman instanceof EnumProperty && super.equals(_snowman)) {
         EnumProperty<?> _snowmanx = (EnumProperty<?>)_snowman;
         return this.values.equals(_snowmanx.values) && this.byName.equals(_snowmanx.byName);
      } else {
         return false;
      }
   }

   @Override
   public int computeHashCode() {
      int _snowman = super.computeHashCode();
      _snowman = 31 * _snowman + this.values.hashCode();
      return 31 * _snowman + this.byName.hashCode();
   }

   public static <T extends Enum<T> & StringIdentifiable> EnumProperty<T> of(String name, Class<T> type) {
      return of(name, type, Predicates.alwaysTrue());
   }

   public static <T extends Enum<T> & StringIdentifiable> EnumProperty<T> of(String name, Class<T> type, Predicate<T> filter) {
      return of(name, type, Arrays.<T>stream(type.getEnumConstants()).filter(filter).collect(Collectors.toList()));
   }

   public static <T extends Enum<T> & StringIdentifiable> EnumProperty<T> of(String name, Class<T> type, T... values) {
      return of(name, type, Lists.newArrayList(values));
   }

   public static <T extends Enum<T> & StringIdentifiable> EnumProperty<T> of(String name, Class<T> type, Collection<T> values) {
      return new EnumProperty<>(name, type, values);
   }
}
