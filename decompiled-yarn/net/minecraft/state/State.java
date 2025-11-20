package net.minecraft.state;

import com.google.common.collect.ArrayTable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.state.property.Property;

public abstract class State<O, S> {
   private static final Function<Entry<Property<?>, Comparable<?>>, String> PROPERTY_MAP_PRINTER = new Function<Entry<Property<?>, Comparable<?>>, String>() {
      public String apply(@Nullable Entry<Property<?>, Comparable<?>> _snowman) {
         if (_snowman == null) {
            return "<NULL>";
         } else {
            Property<?> _snowmanx = _snowman.getKey();
            return _snowmanx.getName() + "=" + this.nameValue(_snowmanx, _snowman.getValue());
         }
      }

      private <T extends Comparable<T>> String nameValue(Property<T> property, Comparable<?> value) {
         return property.name((T)value);
      }
   };
   protected final O owner;
   private final ImmutableMap<Property<?>, Comparable<?>> entries;
   private Table<Property<?>, Comparable<?>, S> withTable;
   protected final MapCodec<S> codec;

   protected State(O owner, ImmutableMap<Property<?>, Comparable<?>> entries, MapCodec<S> codec) {
      this.owner = owner;
      this.entries = entries;
      this.codec = codec;
   }

   public <T extends Comparable<T>> S cycle(Property<T> property) {
      return this.with(property, getNext(property.getValues(), this.get(property)));
   }

   protected static <T> T getNext(Collection<T> values, T value) {
      Iterator<T> _snowman = values.iterator();

      while (_snowman.hasNext()) {
         if (_snowman.next().equals(value)) {
            if (_snowman.hasNext()) {
               return _snowman.next();
            }

            return values.iterator().next();
         }
      }

      return _snowman.next();
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder();
      _snowman.append(this.owner);
      if (!this.getEntries().isEmpty()) {
         _snowman.append('[');
         _snowman.append(this.getEntries().entrySet().stream().map(PROPERTY_MAP_PRINTER).collect(Collectors.joining(",")));
         _snowman.append(']');
      }

      return _snowman.toString();
   }

   public Collection<Property<?>> getProperties() {
      return Collections.unmodifiableCollection(this.entries.keySet());
   }

   public <T extends Comparable<T>> boolean contains(Property<T> property) {
      return this.entries.containsKey(property);
   }

   public <T extends Comparable<T>> T get(Property<T> property) {
      Comparable<?> _snowman = (Comparable<?>)this.entries.get(property);
      if (_snowman == null) {
         throw new IllegalArgumentException("Cannot get property " + property + " as it does not exist in " + this.owner);
      } else {
         return property.getType().cast(_snowman);
      }
   }

   public <T extends Comparable<T>> Optional<T> method_28500(Property<T> property) {
      Comparable<?> _snowman = (Comparable<?>)this.entries.get(property);
      return _snowman == null ? Optional.empty() : Optional.of(property.getType().cast(_snowman));
   }

   public <T extends Comparable<T>, V extends T> S with(Property<T> property, V value) {
      Comparable<?> _snowman = (Comparable<?>)this.entries.get(property);
      if (_snowman == null) {
         throw new IllegalArgumentException("Cannot set property " + property + " as it does not exist in " + this.owner);
      } else if (_snowman == value) {
         return (S)this;
      } else {
         S _snowmanx = (S)this.withTable.get(property, value);
         if (_snowmanx == null) {
            throw new IllegalArgumentException("Cannot set property " + property + " to " + value + " on " + this.owner + ", it is not an allowed value");
         } else {
            return _snowmanx;
         }
      }
   }

   public void createWithTable(Map<Map<Property<?>, Comparable<?>>, S> states) {
      if (this.withTable != null) {
         throw new IllegalStateException();
      } else {
         Table<Property<?>, Comparable<?>, S> _snowman = HashBasedTable.create();
         UnmodifiableIterator var3 = this.entries.entrySet().iterator();

         while (var3.hasNext()) {
            Entry<Property<?>, Comparable<?>> _snowmanx = (Entry<Property<?>, Comparable<?>>)var3.next();
            Property<?> _snowmanxx = _snowmanx.getKey();

            for (Comparable<?> _snowmanxxx : _snowmanxx.getValues()) {
               if (_snowmanxxx != _snowmanx.getValue()) {
                  _snowman.put(_snowmanxx, _snowmanxxx, states.get(this.toMapWith(_snowmanxx, _snowmanxxx)));
               }
            }
         }

         this.withTable = (Table<Property<?>, Comparable<?>, S>)(_snowman.isEmpty() ? _snowman : ArrayTable.create(_snowman));
      }
   }

   private Map<Property<?>, Comparable<?>> toMapWith(Property<?> property, Comparable<?> value) {
      Map<Property<?>, Comparable<?>> _snowman = Maps.newHashMap(this.entries);
      _snowman.put(property, value);
      return _snowman;
   }

   public ImmutableMap<Property<?>, Comparable<?>> getEntries() {
      return this.entries;
   }

   protected static <O, S extends State<O, S>> Codec<S> createCodec(Codec<O> _snowman, Function<O, S> ownerToStateFunction) {
      return _snowman.dispatch("Name", _snowmanx -> _snowmanx.owner, _snowmanxxx -> {
         S _snowmanxx = ownerToStateFunction.apply((O)_snowmanxxx);
         return _snowmanxx.getEntries().isEmpty() ? Codec.unit(_snowmanxx) : _snowmanxx.codec.fieldOf("Properties").codec();
      });
   }
}
