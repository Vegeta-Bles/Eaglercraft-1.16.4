import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class cfj<T extends Comparable<T>> {
   private final Class<T> a;
   private final String b;
   private Integer c;
   private final Codec<T> d = Codec.STRING
      .comapFlatMap(
         var1x -> this.b(var1x)
               .<DataResult>map(DataResult::success)
               .orElseGet(() -> DataResult.error("Unable to read property: " + this + " with value: " + var1x)),
         this::a
      );
   private final Codec<cfj.a<T>> e = this.d.xmap(this::b, cfj.a::b);

   protected cfj(String var1, Class<T> var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public cfj.a<T> b(T var1) {
      return new cfj.a<>(this, _snowman);
   }

   public cfj.a<T> a(cej<?, ?> var1) {
      return new cfj.a<>(this, _snowman.c(this));
   }

   public Stream<cfj.a<T>> c() {
      return this.a().stream().map(this::b);
   }

   public Codec<cfj.a<T>> e() {
      return this.e;
   }

   public String f() {
      return this.b;
   }

   public Class<T> g() {
      return this.a;
   }

   public abstract Collection<T> a();

   public abstract String a(T var1);

   public abstract Optional<T> b(String var1);

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this).add("name", this.b).add("clazz", this.a).add("values", this.a()).toString();
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof cfj)) {
         return false;
      } else {
         cfj<?> _snowman = (cfj<?>)_snowman;
         return this.a.equals(_snowman.a) && this.b.equals(_snowman.b);
      }
   }

   @Override
   public final int hashCode() {
      if (this.c == null) {
         this.c = this.b();
      }

      return this.c;
   }

   public int b() {
      return 31 * this.a.hashCode() + this.b.hashCode();
   }

   public static final class a<T extends Comparable<T>> {
      private final cfj<T> a;
      private final T b;

      private a(cfj<T> var1, T var2) {
         if (!_snowman.a().contains(_snowman)) {
            throw new IllegalArgumentException("Value " + _snowman + " does not belong to property " + _snowman);
         } else {
            this.a = _snowman;
            this.b = _snowman;
         }
      }

      public cfj<T> a() {
         return this.a;
      }

      public T b() {
         return this.b;
      }

      @Override
      public String toString() {
         return this.a.f() + "=" + this.a.a(this.b);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (!(_snowman instanceof cfj.a)) {
            return false;
         } else {
            cfj.a<?> _snowman = (cfj.a<?>)_snowman;
            return this.a == _snowman.a && this.b.equals(_snowman.b);
         }
      }

      @Override
      public int hashCode() {
         int _snowman = this.a.hashCode();
         return 31 * _snowman + this.b.hashCode();
      }
   }
}
