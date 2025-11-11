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

public abstract class ip {
   private final Map<iq, List<ir>> a = Maps.newHashMap();

   public ip() {
   }

   protected void a(iq var1, List<ir> var2) {
      List<ir> _snowman = this.a.put(_snowman, _snowman);
      if (_snowman != null) {
         throw new IllegalStateException("Value " + _snowman + " is already defined");
      }
   }

   Map<iq, List<ir>> a() {
      this.c();
      return ImmutableMap.copyOf(this.a);
   }

   private void c() {
      List<cfj<?>> _snowman = this.b();
      Stream<iq> _snowmanx = Stream.of(iq.a());

      for (cfj<?> _snowmanxx : _snowman) {
         _snowmanx = _snowmanx.flatMap(var1x -> _snowman.c().map(var1x::a));
      }

      List<iq> _snowmanxx = _snowmanx.filter(var1x -> !this.a.containsKey(var1x)).collect(Collectors.toList());
      if (!_snowmanxx.isEmpty()) {
         throw new IllegalStateException("Missing definition for properties: " + _snowmanxx);
      }
   }

   abstract List<cfj<?>> b();

   public static <T1 extends Comparable<T1>> ip.a<T1> a(cfj<T1> var0) {
      return new ip.a<>(_snowman);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>> ip.b<T1, T2> a(cfj<T1> var0, cfj<T2> var1) {
      return new ip.b<>(_snowman, _snowman);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> ip.c<T1, T2, T3> a(cfj<T1> var0, cfj<T2> var1, cfj<T3> var2) {
      return new ip.c<>(_snowman, _snowman, _snowman);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> ip.d<T1, T2, T3, T4> a(
      cfj<T1> var0, cfj<T2> var1, cfj<T3> var2, cfj<T4> var3
   ) {
      return new ip.d<>(_snowman, _snowman, _snowman, _snowman);
   }

   public static <T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>> ip.e<T1, T2, T3, T4, T5> a(
      cfj<T1> var0, cfj<T2> var1, cfj<T3> var2, cfj<T4> var3, cfj<T5> var4
   ) {
      return new ip.e<>(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static class a<T1 extends Comparable<T1>> extends ip {
      private final cfj<T1> a;

      private a(cfj<T1> var1) {
         this.a = _snowman;
      }

      @Override
      public List<cfj<?>> b() {
         return ImmutableList.of(this.a);
      }

      public ip.a<T1> a(T1 var1, List<ir> var2) {
         iq _snowman = iq.a(this.a.b(_snowman));
         this.a(_snowman, _snowman);
         return this;
      }

      public ip.a<T1> a(T1 var1, ir var2) {
         return this.a(_snowman, Collections.singletonList(_snowman));
      }

      public ip a(Function<T1, ir> var1) {
         this.a.a().forEach(var2 -> this.a((T1)var2, _snowman.apply((T1)var2)));
         return this;
      }
   }

   public static class b<T1 extends Comparable<T1>, T2 extends Comparable<T2>> extends ip {
      private final cfj<T1> a;
      private final cfj<T2> b;

      private b(cfj<T1> var1, cfj<T2> var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      @Override
      public List<cfj<?>> b() {
         return ImmutableList.of(this.a, this.b);
      }

      public ip.b<T1, T2> a(T1 var1, T2 var2, List<ir> var3) {
         iq _snowman = iq.a(this.a.b(_snowman), this.b.b(_snowman));
         this.a(_snowman, _snowman);
         return this;
      }

      public ip.b<T1, T2> a(T1 var1, T2 var2, ir var3) {
         return this.a(_snowman, _snowman, Collections.singletonList(_snowman));
      }

      public ip a(BiFunction<T1, T2, ir> var1) {
         this.a.a().forEach(var2 -> this.b.a().forEach(var3 -> this.a((T1)var2, (T2)var3, _snowman.apply((T1)var2, (T2)var3))));
         return this;
      }

      public ip b(BiFunction<T1, T2, List<ir>> var1) {
         this.a.a().forEach(var2 -> this.b.a().forEach(var3 -> this.a((T1)var2, (T2)var3, _snowman.apply((T1)var2, (T2)var3))));
         return this;
      }
   }

   public static class c<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>> extends ip {
      private final cfj<T1> a;
      private final cfj<T2> b;
      private final cfj<T3> c;

      private c(cfj<T1> var1, cfj<T2> var2, cfj<T3> var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      @Override
      public List<cfj<?>> b() {
         return ImmutableList.of(this.a, this.b, this.c);
      }

      public ip.c<T1, T2, T3> a(T1 var1, T2 var2, T3 var3, List<ir> var4) {
         iq _snowman = iq.a(this.a.b(_snowman), this.b.b(_snowman), this.c.b(_snowman));
         this.a(_snowman, _snowman);
         return this;
      }

      public ip.c<T1, T2, T3> a(T1 var1, T2 var2, T3 var3, ir var4) {
         return this.a(_snowman, _snowman, _snowman, Collections.singletonList(_snowman));
      }

      public ip a(ip.h<T1, T2, T3, ir> var1) {
         this.a
            .a()
            .forEach(
               var2 -> this.b.a().forEach(var3 -> this.c.a().forEach(var4 -> this.a((T1)var2, (T2)var3, (T3)var4, _snowman.apply((T1)var2, (T2)var3, (T3)var4))))
            );
         return this;
      }
   }

   public static class d<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>> extends ip {
      private final cfj<T1> a;
      private final cfj<T2> b;
      private final cfj<T3> c;
      private final cfj<T4> d;

      private d(cfj<T1> var1, cfj<T2> var2, cfj<T3> var3, cfj<T4> var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      @Override
      public List<cfj<?>> b() {
         return ImmutableList.of(this.a, this.b, this.c, this.d);
      }

      public ip.d<T1, T2, T3, T4> a(T1 var1, T2 var2, T3 var3, T4 var4, List<ir> var5) {
         iq _snowman = iq.a(this.a.b(_snowman), this.b.b(_snowman), this.c.b(_snowman), this.d.b(_snowman));
         this.a(_snowman, _snowman);
         return this;
      }

      public ip.d<T1, T2, T3, T4> a(T1 var1, T2 var2, T3 var3, T4 var4, ir var5) {
         return this.a(_snowman, _snowman, _snowman, _snowman, Collections.singletonList(_snowman));
      }
   }

   public static class e<T1 extends Comparable<T1>, T2 extends Comparable<T2>, T3 extends Comparable<T3>, T4 extends Comparable<T4>, T5 extends Comparable<T5>>
      extends ip {
      private final cfj<T1> a;
      private final cfj<T2> b;
      private final cfj<T3> c;
      private final cfj<T4> d;
      private final cfj<T5> e;

      private e(cfj<T1> var1, cfj<T2> var2, cfj<T3> var3, cfj<T4> var4, cfj<T5> var5) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
      }

      @Override
      public List<cfj<?>> b() {
         return ImmutableList.of(this.a, this.b, this.c, this.d, this.e);
      }

      public ip.e<T1, T2, T3, T4, T5> a(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, List<ir> var6) {
         iq _snowman = iq.a(this.a.b(_snowman), this.b.b(_snowman), this.c.b(_snowman), this.d.b(_snowman), this.e.b(_snowman));
         this.a(_snowman, _snowman);
         return this;
      }

      public ip.e<T1, T2, T3, T4, T5> a(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, ir var6) {
         return this.a(_snowman, _snowman, _snowman, _snowman, _snowman, Collections.singletonList(_snowman));
      }
   }

   @FunctionalInterface
   public interface h<P1, P2, P3, R> {
      R apply(P1 var1, P2 var2, P3 var3);
   }
}
