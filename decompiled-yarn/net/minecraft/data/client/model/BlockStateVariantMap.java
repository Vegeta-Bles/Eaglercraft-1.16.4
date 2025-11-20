package net.minecraft.data.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.state.property.Property;

public abstract class BlockStateVariantMap {
   private final Map<PropertiesMap, List<BlockStateVariant>> variants = Maps.newHashMap();

   public BlockStateVariantMap() {
   }

   protected void register(PropertiesMap condition, List<BlockStateVariant> possibleVariants) {
      List<BlockStateVariant> _snowman = this.variants.put(condition, possibleVariants);
      if (_snowman != null) {
         throw new IllegalStateException("Value " + condition + " is already defined");
      }
   }

   Map<PropertiesMap, List<BlockStateVariant>> getVariants() {
      this.checkAllPropertyDefinitions();
      return ImmutableMap.copyOf(this.variants);
   }

   private void checkAllPropertyDefinitions() {
      List<Property<?>> _snowman = this.getProperties();
      Stream<PropertiesMap> _snowmanx = Stream.of(PropertiesMap.empty());

      for (Property<?> _snowmanxx : _snowman) {
         _snowmanx = _snowmanx.flatMap(_snowmanxxx -> _snowman.stream().map(_snowmanxxx::method_25819));
      }

      List<PropertiesMap> _snowmanxx = _snowmanx.filter(_snowmanxxx -> !this.variants.containsKey(_snowmanxxx)).collect(Collectors.toList());
      if (!_snowmanxx.isEmpty()) {
         throw new IllegalStateException("Missing definition for properties: " + _snowmanxx);
      }
   }

   abstract List<Property<?>> getProperties();

   public static <T1 extends Comparable<T1>> BlockStateVariantMap.SingleProperty<T1> create(Property<T1> _snowman) {
      return new BlockStateVariantMap.SingleProperty<>(_snowman);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> BlockStateVariantMap.DoubleProperty<T1, T2> create(Property<T1> _snowman, Property<T2> _snowman) {
      return new BlockStateVariantMap.DoubleProperty<>(_snowman, _snowman);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> BlockStateVariantMap.TripleProperty<T1, T2, T3> create(
      Property<T1> _snowman, Property<T2> _snowman, Property<T3> _snowman
   ) {
      return new BlockStateVariantMap.TripleProperty<>(_snowman, _snowman, _snowman);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> BlockStateVariantMap.QuadrupleProperty<T1, T2, T3, T4> create(
      Property<T1> _snowman, Property<T2> _snowman, Property<T3> _snowman, Property<T4> _snowman
   ) {
      return new BlockStateVariantMap.QuadrupleProperty<>(_snowman, _snowman, _snowman, _snowman);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> BlockStateVariantMap.QuintupleProperty<T1, T2, T3, T4, T5> create(
      Property<T1> _snowman, Property<T2> _snowman, Property<T3> _snowman, Property<T4> _snowman, Property<T5> _snowman
   ) {
      return new BlockStateVariantMap.QuintupleProperty<>(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static class DoubleProperty<T1 extends Comparable<T1>, T2 extends Comparable<T2>> extends BlockStateVariantMap {
      private final Property<T1> first;
      private final Property<T2> second;

      private DoubleProperty(Property<T1> _snowman, Property<T2> _snowman) {
         this.first = _snowman;
         this.second = _snowman;
      }

      @Override
      public List<Property<?>> getProperties() {
         return ImmutableList.of(this.first, this.second);
      }

      public BlockStateVariantMap.DoubleProperty<T1, T2> register(T1 _snowman, T2 _snowman, List<BlockStateVariant> _snowman) {
         PropertiesMap _snowmanxxx = PropertiesMap.method_25821(this.first.createValue(_snowman), this.second.createValue(_snowman));
         this.register(_snowmanxxx, _snowman);
         return this;
      }

      public BlockStateVariantMap.DoubleProperty<T1, T2> register(T1 _snowman, T2 _snowman, BlockStateVariant _snowman) {
         return this.register(_snowman, _snowman, Collections.singletonList(_snowman));
      }

      public BlockStateVariantMap register(BiFunction<T1, T2, BlockStateVariant> variantFactory) {
         this.first.getValues().forEach(_snowmanx -> this.second.getValues().forEach(_snowmanxx -> this.register((T1)_snowmanx, (T2)_snowmanxx, variantFactory.apply((T1)_snowmanx, (T2)_snowmanxx))));
         return this;
      }

      public BlockStateVariantMap registerVariants(BiFunction<T1, T2, List<BlockStateVariant>> variantsFactory) {
         this.first.getValues().forEach(_snowmanx -> this.second.getValues().forEach(_snowmanxx -> this.register((T1)_snowmanx, (T2)_snowmanxx, variantsFactory.apply((T1)_snowmanx, (T2)_snowmanxx))));
         return this;
      }
   }

   public static class QuadrupleProperty<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>>
      extends BlockStateVariantMap {
      private final Property<T1> first;
      private final Property<T2> second;
      private final Property<T3> third;
      private final Property<T4> fourth;

      private QuadrupleProperty(Property<T1> _snowman, Property<T2> _snowman, Property<T3> _snowman, Property<T4> _snowman) {
         this.first = _snowman;
         this.second = _snowman;
         this.third = _snowman;
         this.fourth = _snowman;
      }

      @Override
      public List<Property<?>> getProperties() {
         return ImmutableList.of(this.first, this.second, this.third, this.fourth);
      }

      public BlockStateVariantMap.QuadrupleProperty<T1, T2, T3, T4> register(T1 _snowman, T2 _snowman, T3 _snowman, T4 _snowman, List<BlockStateVariant> _snowman) {
         PropertiesMap _snowmanxxxxx = PropertiesMap.method_25821(
            this.first.createValue(_snowman), this.second.createValue(_snowman), this.third.createValue(_snowman), this.fourth.createValue(_snowman)
         );
         this.register(_snowmanxxxxx, _snowman);
         return this;
      }

      public BlockStateVariantMap.QuadrupleProperty<T1, T2, T3, T4> register(T1 _snowman, T2 _snowman, T3 _snowman, T4 _snowman, BlockStateVariant _snowman) {
         return this.register(_snowman, _snowman, _snowman, _snowman, Collections.singletonList(_snowman));
      }
   }

   public static class QuintupleProperty<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>>
      extends BlockStateVariantMap {
      private final Property<T1> first;
      private final Property<T2> second;
      private final Property<T3> third;
      private final Property<T4> fourth;
      private final Property<T5> fifth;

      private QuintupleProperty(Property<T1> _snowman, Property<T2> _snowman, Property<T3> _snowman, Property<T4> _snowman, Property<T5> _snowman) {
         this.first = _snowman;
         this.second = _snowman;
         this.third = _snowman;
         this.fourth = _snowman;
         this.fifth = _snowman;
      }

      @Override
      public List<Property<?>> getProperties() {
         return ImmutableList.of(this.first, this.second, this.third, this.fourth, this.fifth);
      }

      public BlockStateVariantMap.QuintupleProperty<T1, T2, T3, T4, T5> register(T1 _snowman, T2 _snowman, T3 _snowman, T4 _snowman, T5 _snowman, List<BlockStateVariant> _snowman) {
         PropertiesMap _snowmanxxxxxx = PropertiesMap.method_25821(
            this.first.createValue(_snowman), this.second.createValue(_snowman), this.third.createValue(_snowman), this.fourth.createValue(_snowman), this.fifth.createValue(_snowman)
         );
         this.register(_snowmanxxxxxx, _snowman);
         return this;
      }

      public BlockStateVariantMap.QuintupleProperty<T1, T2, T3, T4, T5> register(T1 _snowman, T2 _snowman, T3 _snowman, T4 _snowman, T5 _snowman, BlockStateVariant _snowman) {
         return this.register(_snowman, _snowman, _snowman, _snowman, _snowman, Collections.singletonList(_snowman));
      }
   }

   public static class SingleProperty<T1 extends Comparable<T1>> extends BlockStateVariantMap {
      private final Property<T1> property;

      private SingleProperty(Property<T1> _snowman) {
         this.property = _snowman;
      }

      @Override
      public List<Property<?>> getProperties() {
         return ImmutableList.of(this.property);
      }

      public BlockStateVariantMap.SingleProperty<T1> register(T1 value, List<BlockStateVariant> variants) {
         PropertiesMap _snowman = PropertiesMap.method_25821(this.property.createValue(value));
         this.register(_snowman, variants);
         return this;
      }

      public BlockStateVariantMap.SingleProperty<T1> register(T1 value, BlockStateVariant variant) {
         return this.register(value, Collections.singletonList(variant));
      }

      public BlockStateVariantMap register(Function<T1, BlockStateVariant> variantFactory) {
         this.property.getValues().forEach(_snowmanx -> this.register((T1)_snowmanx, variantFactory.apply((T1)_snowmanx)));
         return this;
      }
   }

   @FunctionalInterface
   public interface TriFunction<P1, P2, P3, R> {
      R apply(P1 var1, P2 var2, P3 var3);
   }

   public static class TripleProperty<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> extends BlockStateVariantMap {
      private final Property<T1> first;
      private final Property<T2> second;
      private final Property<T3> third;

      private TripleProperty(Property<T1> _snowman, Property<T2> _snowman, Property<T3> _snowman) {
         this.first = _snowman;
         this.second = _snowman;
         this.third = _snowman;
      }

      @Override
      public List<Property<?>> getProperties() {
         return ImmutableList.of(this.first, this.second, this.third);
      }

      public BlockStateVariantMap.TripleProperty<T1, T2, T3> register(T1 _snowman, T2 _snowman, T3 _snowman, List<BlockStateVariant> _snowman) {
         PropertiesMap _snowmanxxxx = PropertiesMap.method_25821(this.first.createValue(_snowman), this.second.createValue(_snowman), this.third.createValue(_snowman));
         this.register(_snowmanxxxx, _snowman);
         return this;
      }

      public BlockStateVariantMap.TripleProperty<T1, T2, T3> register(T1 _snowman, T2 _snowman, T3 _snowman, BlockStateVariant _snowman) {
         return this.register(_snowman, _snowman, _snowman, Collections.singletonList(_snowman));
      }

      public BlockStateVariantMap register(BlockStateVariantMap.TriFunction<T1, T2, T3, BlockStateVariant> _snowman) {
         this.first
            .getValues()
            .forEach(
               _snowmanx -> this.second
                     .getValues()
                     .forEach(
                        _snowmanxxx -> this.third.getValues().forEach(_snowmanxxxxxx -> this.register((T1)_snowmanx, (T2)_snowmanxxx, (T3)_snowmanxxxxxx, _snowman.apply((T1)_snowmanx, (T2)_snowmanxxx, (T3)_snowmanxxxxxx)))
                     )
            );
         return this;
      }
   }
}
