import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class aup<U> {
   protected final List<aup.a<U>> a;
   private final Random b = new Random();

   public aup() {
      this(Lists.newArrayList());
   }

   private aup(List<aup.a<U>> var1) {
      this.a = Lists.newArrayList(_snowman);
   }

   public static <U> Codec<aup<U>> a(Codec<U> var0) {
      return aup.a.a(_snowman).listOf().xmap(aup::new, var0x -> var0x.a);
   }

   public aup<U> a(U var1, int var2) {
      this.a.add(new aup.a<>(_snowman, _snowman));
      return this;
   }

   public aup<U> a() {
      return this.a(this.b);
   }

   public aup<U> a(Random var1) {
      this.a.forEach(var1x -> var1x.a(_snowman.nextFloat()));
      this.a.sort(Comparator.comparingDouble(var0 -> var0.c()));
      return this;
   }

   public boolean b() {
      return this.a.isEmpty();
   }

   public Stream<U> c() {
      return this.a.stream().map(aup.a::a);
   }

   public U b(Random var1) {
      return this.a(_snowman).c().findFirst().orElseThrow(RuntimeException::new);
   }

   @Override
   public String toString() {
      return "WeightedList[" + this.a + "]";
   }

   public static class a<T> {
      private final T a;
      private final int b;
      private double c;

      private a(T var1, int var2) {
         this.b = _snowman;
         this.a = _snowman;
      }

      private double c() {
         return this.c;
      }

      private void a(float var1) {
         this.c = -Math.pow((double)_snowman, (double)(1.0F / (float)this.b));
      }

      public T a() {
         return this.a;
      }

      @Override
      public String toString() {
         return "" + this.b + ":" + this.a;
      }

      public static <E> Codec<aup.a<E>> a(final Codec<E> var0) {
         return new Codec<aup.a<E>>() {
            public <T> DataResult<Pair<aup.a<E>, T>> decode(DynamicOps<T> var1, T var2) {
               Dynamic<T> _snowman = new Dynamic(_snowman, _snowman);
               return _snowman.get("data").flatMap(_snowman::parse).map(var1x -> new aup.a(var1x, _snowman.get("weight").asInt(1))).map(var1x -> Pair.of(var1x, _snowman.empty()));
            }

            public <T> DataResult<T> a(aup.a<E> var1, DynamicOps<T> var2, T var3) {
               return _snowman.mapBuilder().add("weight", _snowman.createInt(_snowman.b)).add("data", _snowman.encodeStart(_snowman, _snowman.a)).build(_snowman);
            }
         };
      }
   }
}
