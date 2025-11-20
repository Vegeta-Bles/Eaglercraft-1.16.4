package net.minecraft.state;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.MapCodec;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.state.property.Property;

public class StateManager<O, S extends State<O, S>> {
   private static final Pattern VALID_NAME_PATTERN = Pattern.compile("^[a-z0-9_]+$");
   private final O owner;
   private final ImmutableSortedMap<String, Property<?>> properties;
   private final ImmutableList<S> states;

   protected StateManager(Function<O, S> _snowman, O _snowman, StateManager.Factory<O, S> factory, Map<String, Property<?>> propertiesMap) {
      this.owner = _snowman;
      this.properties = ImmutableSortedMap.copyOf(propertiesMap);
      Supplier<S> _snowmanxx = () -> _snowman.apply(_snowman);
      MapCodec<S> _snowmanxxx = MapCodec.of(Encoder.empty(), Decoder.unit(_snowmanxx));
      UnmodifiableIterator var7 = this.properties.entrySet().iterator();

      while (var7.hasNext()) {
         Entry<String, Property<?>> _snowmanxxxx = (Entry<String, Property<?>>)var7.next();
         _snowmanxxx = method_30040(_snowmanxxx, _snowmanxx, _snowmanxxxx.getKey(), _snowmanxxxx.getValue());
      }

      MapCodec<S> _snowmanxxxx = _snowmanxxx;
      Map<Map<Property<?>, Comparable<?>>, S> _snowmanxxxxx = Maps.newLinkedHashMap();
      List<S> _snowmanxxxxxx = Lists.newArrayList();
      Stream<List<Pair<Property<?>, Comparable<?>>>> _snowmanxxxxxxx = Stream.of(Collections.emptyList());
      UnmodifiableIterator var11 = this.properties.values().iterator();

      while (var11.hasNext()) {
         Property<?> _snowmanxxxxxxxx = (Property<?>)var11.next();
         _snowmanxxxxxxx = _snowmanxxxxxxx.flatMap(_snowmanxxxxxxxxx -> _snowman.getValues().stream().map(_snowmanxxxxxxxxxx -> {
               List<Pair<Property<?>, Comparable<?>>> _snowmanxxxxxxxxxxx = Lists.newArrayList(_snowmanxxx);
               _snowmanxxxxxxxxxxx.add(Pair.of(_snowman, _snowmanxxxxxxxxxx));
               return _snowmanxxxxxxxxxxx;
            }));
      }

      _snowmanxxxxxxx.forEach(_snowmanxxxxxxxx -> {
         ImmutableMap<Property<?>, Comparable<?>> _snowmanxxxxxxxxx = _snowmanxxxxxxxx.stream().collect(ImmutableMap.toImmutableMap(Pair::getFirst, Pair::getSecond));
         S _snowmanxxxxxxxxxx = factory.create(_snowman, _snowmanxxxxxxxxx, _snowman);
         _snowman.put(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
         _snowman.add(_snowmanxxxxxxxxxx);
      });

      for (S _snowmanxxxxxxxx : _snowmanxxxxxx) {
         _snowmanxxxxxxxx.createWithTable(_snowmanxxxxx);
      }

      this.states = ImmutableList.copyOf(_snowmanxxxxxx);
   }

   private static <S extends State<?, S>, T extends Comparable<T>> MapCodec<S> method_30040(MapCodec<S> _snowman, Supplier<S> _snowman, String _snowman, Property<T> _snowman) {
      return Codec.mapPair(_snowman, _snowman.getValueCodec().fieldOf(_snowman).setPartial(() -> _snowman.createValue(_snowman.get())))
         .xmap(
            _snowmanxxxxx -> (State)((State)_snowmanxxxxx.getFirst()).with(_snowman, ((Property.Value)_snowmanxxxxx.getSecond()).getValue()),
            _snowmanxxxxx -> Pair.of(_snowmanxxxxx, _snowman.createValue(_snowmanxxxxx))
         );
   }

   public ImmutableList<S> getStates() {
      return this.states;
   }

   public S getDefaultState() {
      return (S)this.states.get(0);
   }

   public O getOwner() {
      return this.owner;
   }

   public Collection<Property<?>> getProperties() {
      return this.properties.values();
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this)
         .add("block", this.owner)
         .add("properties", this.properties.values().stream().map(Property::getName).collect(Collectors.toList()))
         .toString();
   }

   @Nullable
   public Property<?> getProperty(String name) {
      return (Property<?>)this.properties.get(name);
   }

   public static class Builder<O, S extends State<O, S>> {
      private final O owner;
      private final Map<String, Property<?>> namedProperties = Maps.newHashMap();

      public Builder(O owner) {
         this.owner = owner;
      }

      public StateManager.Builder<O, S> add(Property<?>... properties) {
         for (Property<?> _snowman : properties) {
            this.validate(_snowman);
            this.namedProperties.put(_snowman.getName(), _snowman);
         }

         return this;
      }

      private <T extends Comparable<T>> void validate(Property<T> property) {
         String _snowman = property.getName();
         if (!StateManager.VALID_NAME_PATTERN.matcher(_snowman).matches()) {
            throw new IllegalArgumentException(this.owner + " has invalidly named property: " + _snowman);
         } else {
            Collection<T> _snowmanx = property.getValues();
            if (_snowmanx.size() <= 1) {
               throw new IllegalArgumentException(this.owner + " attempted use property " + _snowman + " with <= 1 possible values");
            } else {
               for (T _snowmanxx : _snowmanx) {
                  String _snowmanxxx = property.name(_snowmanxx);
                  if (!StateManager.VALID_NAME_PATTERN.matcher(_snowmanxxx).matches()) {
                     throw new IllegalArgumentException(this.owner + " has property: " + _snowman + " with invalidly named value: " + _snowmanxxx);
                  }
               }

               if (this.namedProperties.containsKey(_snowman)) {
                  throw new IllegalArgumentException(this.owner + " has duplicate property: " + _snowman);
               }
            }
         }
      }

      public StateManager<O, S> build(Function<O, S> ownerToStateFunction, StateManager.Factory<O, S> factory) {
         return new StateManager<>(ownerToStateFunction, this.owner, factory, this.namedProperties);
      }
   }

   public interface Factory<O, S> {
      S create(O owner, ImmutableMap<Property<?>, Comparable<?>> entries, MapCodec<S> var3);
   }
}
