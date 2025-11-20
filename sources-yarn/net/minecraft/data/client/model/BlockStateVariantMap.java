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
      List<BlockStateVariant> list2 = this.variants.put(condition, possibleVariants);
      if (list2 != null) {
         throw new IllegalStateException("Value " + condition + " is already defined");
      }
   }

   Map<PropertiesMap, List<BlockStateVariant>> getVariants() {
      this.checkAllPropertyDefinitions();
      return ImmutableMap.copyOf(this.variants);
   }

   private void checkAllPropertyDefinitions() {
      List<Property<?>> list = this.getProperties();
      Stream<PropertiesMap> stream = Stream.of(PropertiesMap.empty());

      for (Property<?> lv : list) {
         stream = stream.flatMap(arg2 -> lv.stream().map(arg2::method_25819));
      }

      List<PropertiesMap> list2 = stream.filter(arg -> !this.variants.containsKey(arg)).collect(Collectors.toList());
      if (!list2.isEmpty()) {
         throw new IllegalStateException("Missing definition for properties: " + list2);
      }
   }

   abstract List<Property<?>> getProperties();

   public static <T1 extends Comparable<T1>> BlockStateVariantMap.SingleProperty<T1> create(Property<T1> arg) {
      return new BlockStateVariantMap.SingleProperty<>(arg);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> BlockStateVariantMap.DoubleProperty<T1, T2> create(Property<T1> arg, Property<T2> arg2) {
      return new BlockStateVariantMap.DoubleProperty<>(arg, arg2);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> BlockStateVariantMap.TripleProperty<T1, T2, T3> create(
      Property<T1> arg, Property<T2> arg2, Property<T3> arg3
   ) {
      return new BlockStateVariantMap.TripleProperty<>(arg, arg2, arg3);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> BlockStateVariantMap.QuadrupleProperty<T1, T2, T3, T4> create(
      Property<T1> arg, Property<T2> arg2, Property<T3> arg3, Property<T4> arg4
   ) {
      return new BlockStateVariantMap.QuadrupleProperty<>(arg, arg2, arg3, arg4);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> BlockStateVariantMap.QuintupleProperty<T1, T2, T3, T4, T5> create(
      Property<T1> arg, Property<T2> arg2, Property<T3> arg3, Property<T4> arg4, Property<T5> arg5
   ) {
      return new BlockStateVariantMap.QuintupleProperty<>(arg, arg2, arg3, arg4, arg5);
   }

   public static class DoubleProperty<T1 extends Comparable<T1>, T2 extends Comparable<T2>> extends BlockStateVariantMap {
      private final Property<T1> first;
      private final Property<T2> second;

      private DoubleProperty(Property<T1> arg, Property<T2> arg2) {
         this.first = arg;
         this.second = arg2;
      }

      @Override
      public List<Property<?>> getProperties() {
         return ImmutableList.of(this.first, this.second);
      }

      public BlockStateVariantMap.DoubleProperty<T1, T2> register(T1 comparable, T2 comparable2, List<BlockStateVariant> list) {
         PropertiesMap lv = PropertiesMap.method_25821(this.first.createValue(comparable), this.second.createValue(comparable2));
         this.register(lv, list);
         return this;
      }

      public BlockStateVariantMap.DoubleProperty<T1, T2> register(T1 comparable, T2 comparable2, BlockStateVariant arg) {
         return this.register(comparable, comparable2, Collections.singletonList(arg));
      }

      public BlockStateVariantMap register(BiFunction<T1, T2, BlockStateVariant> variantFactory) {
         this.first
            .getValues()
            .forEach(
               comparable -> this.second
                     .getValues()
                     .forEach(comparable2 -> this.register((T1)comparable, (T2)comparable2, variantFactory.apply((T1)comparable, (T2)comparable2)))
            );
         return this;
      }

      public BlockStateVariantMap registerVariants(BiFunction<T1, T2, List<BlockStateVariant>> variantsFactory) {
         this.first
            .getValues()
            .forEach(
               comparable -> this.second
                     .getValues()
                     .forEach(comparable2 -> this.register((T1)comparable, (T2)comparable2, variantsFactory.apply((T1)comparable, (T2)comparable2)))
            );
         return this;
      }
   }

   public static class QuadrupleProperty<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>>
      extends BlockStateVariantMap {
      private final Property<T1> first;
      private final Property<T2> second;
      private final Property<T3> third;
      private final Property<T4> fourth;

      private QuadrupleProperty(Property<T1> arg, Property<T2> arg2, Property<T3> arg3, Property<T4> arg4) {
         this.first = arg;
         this.second = arg2;
         this.third = arg3;
         this.fourth = arg4;
      }

      @Override
      public List<Property<?>> getProperties() {
         return ImmutableList.of(this.first, this.second, this.third, this.fourth);
      }

      public BlockStateVariantMap.QuadrupleProperty<T1, T2, T3, T4> register(
         T1 comparable, T2 comparable2, T3 comparable3, T4 comparable4, List<BlockStateVariant> list
      ) {
         PropertiesMap lv = PropertiesMap.method_25821(
            this.first.createValue(comparable), this.second.createValue(comparable2), this.third.createValue(comparable3), this.fourth.createValue(comparable4)
         );
         this.register(lv, list);
         return this;
      }

      public BlockStateVariantMap.QuadrupleProperty<T1, T2, T3, T4> register(
         T1 comparable, T2 comparable2, T3 comparable3, T4 comparable4, BlockStateVariant arg
      ) {
         return this.register(comparable, comparable2, comparable3, comparable4, Collections.singletonList(arg));
      }
   }

   public static class QuintupleProperty<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>>
      extends BlockStateVariantMap {
      private final Property<T1> first;
      private final Property<T2> second;
      private final Property<T3> third;
      private final Property<T4> fourth;
      private final Property<T5> fifth;

      private QuintupleProperty(Property<T1> arg, Property<T2> arg2, Property<T3> arg3, Property<T4> arg4, Property<T5> arg5) {
         this.first = arg;
         this.second = arg2;
         this.third = arg3;
         this.fourth = arg4;
         this.fifth = arg5;
      }

      @Override
      public List<Property<?>> getProperties() {
         return ImmutableList.of(this.first, this.second, this.third, this.fourth, this.fifth);
      }

      public BlockStateVariantMap.QuintupleProperty<T1, T2, T3, T4, T5> register(
         T1 comparable, T2 comparable2, T3 comparable3, T4 comparable4, T5 comparable5, List<BlockStateVariant> list
      ) {
         PropertiesMap lv = PropertiesMap.method_25821(
            this.first.createValue(comparable),
            this.second.createValue(comparable2),
            this.third.createValue(comparable3),
            this.fourth.createValue(comparable4),
            this.fifth.createValue(comparable5)
         );
         this.register(lv, list);
         return this;
      }

      public BlockStateVariantMap.QuintupleProperty<T1, T2, T3, T4, T5> register(
         T1 comparable, T2 comparable2, T3 comparable3, T4 comparable4, T5 comparable5, BlockStateVariant arg
      ) {
         return this.register(comparable, comparable2, comparable3, comparable4, comparable5, Collections.singletonList(arg));
      }
   }

   public static class SingleProperty<T1 extends Comparable<T1>> extends BlockStateVariantMap {
      private final Property<T1> property;

      private SingleProperty(Property<T1> arg) {
         this.property = arg;
      }

      @Override
      public List<Property<?>> getProperties() {
         return ImmutableList.of(this.property);
      }

      public BlockStateVariantMap.SingleProperty<T1> register(T1 value, List<BlockStateVariant> variants) {
         PropertiesMap lv = PropertiesMap.method_25821(this.property.createValue(value));
         this.register(lv, variants);
         return this;
      }

      public BlockStateVariantMap.SingleProperty<T1> register(T1 value, BlockStateVariant variant) {
         return this.register(value, Collections.singletonList(variant));
      }

      public BlockStateVariantMap register(Function<T1, BlockStateVariant> variantFactory) {
         this.property.getValues().forEach(comparable -> this.register((T1)comparable, variantFactory.apply((T1)comparable)));
         return this;
      }
   }

   @FunctionalInterface
   public interface TriFunction<P1, P2, P3, R> {
      R apply(P1 object, P2 object2, P3 object3);
   }

   public static class TripleProperty<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> extends BlockStateVariantMap {
      private final Property<T1> first;
      private final Property<T2> second;
      private final Property<T3> third;

      private TripleProperty(Property<T1> arg, Property<T2> arg2, Property<T3> arg3) {
         this.first = arg;
         this.second = arg2;
         this.third = arg3;
      }

      @Override
      public List<Property<?>> getProperties() {
         return ImmutableList.of(this.first, this.second, this.third);
      }

      public BlockStateVariantMap.TripleProperty<T1, T2, T3> register(T1 comparable, T2 comparable2, T3 comparable3, List<BlockStateVariant> list) {
         PropertiesMap lv = PropertiesMap.method_25821(
            this.first.createValue(comparable), this.second.createValue(comparable2), this.third.createValue(comparable3)
         );
         this.register(lv, list);
         return this;
      }

      public BlockStateVariantMap.TripleProperty<T1, T2, T3> register(T1 comparable, T2 comparable2, T3 comparable3, BlockStateVariant arg) {
         return this.register(comparable, comparable2, comparable3, Collections.singletonList(arg));
      }

      public BlockStateVariantMap register(BlockStateVariantMap.TriFunction<T1, T2, T3, BlockStateVariant> arg) {
         this.first
            .getValues()
            .forEach(
               comparable -> this.second
                     .getValues()
                     .forEach(
                        comparable2 -> this.third
                              .getValues()
                              .forEach(
                                 comparable3 -> this.register(
                                       (T1)comparable, (T2)comparable2, (T3)comparable3, arg.apply((T1)comparable, (T2)comparable2, (T3)comparable3)
                                    )
                              )
                     )
            );
         return this;
      }
   }
}
