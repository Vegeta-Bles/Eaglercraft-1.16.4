import io.netty.util.internal.ThreadLocalRandom;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class arj {
   private static final Logger a = LogManager.getLogger();
   private final double b;
   private final arj.a c;
   private final Supplier<String> d;
   private final UUID e;

   public arj(String var1, double var2, arj.a var4) {
      this(afm.a(ThreadLocalRandom.current()), () -> _snowman, _snowman, _snowman);
   }

   public arj(UUID var1, String var2, double var3, arj.a var5) {
      this(_snowman, () -> _snowman, _snowman, _snowman);
   }

   public arj(UUID var1, Supplier<String> var2, double var3, arj.a var5) {
      this.e = _snowman;
      this.d = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public UUID a() {
      return this.e;
   }

   public String b() {
      return this.d.get();
   }

   public arj.a c() {
      return this.c;
   }

   public double d() {
      return this.b;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         arj _snowman = (arj)_snowman;
         return Objects.equals(this.e, _snowman.e);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.e.hashCode();
   }

   @Override
   public String toString() {
      return "AttributeModifier{amount=" + this.b + ", operation=" + this.c + ", name='" + this.d.get() + '\'' + ", id=" + this.e + '}';
   }

   public md e() {
      md _snowman = new md();
      _snowman.a("Name", this.b());
      _snowman.a("Amount", this.b);
      _snowman.b("Operation", this.c.a());
      _snowman.a("UUID", this.e);
      return _snowman;
   }

   @Nullable
   public static arj a(md var0) {
      try {
         UUID _snowman = _snowman.a("UUID");
         arj.a _snowmanx = arj.a.a(_snowman.h("Operation"));
         return new arj(_snowman, _snowman.l("Name"), _snowman.k("Amount"), _snowmanx);
      } catch (Exception var3) {
         a.warn("Unable to create attribute: {}", var3.getMessage());
         return null;
      }
   }

   public static enum a {
      a(0),
      b(1),
      c(2);

      private static final arj.a[] d = new arj.a[]{a, b, c};
      private final int e;

      private a(int var3) {
         this.e = _snowman;
      }

      public int a() {
         return this.e;
      }

      public static arj.a a(int var0) {
         if (_snowman >= 0 && _snowman < d.length) {
            return d[_snowman];
         } else {
            throw new IllegalArgumentException("No operation with value " + _snowman);
         }
      }
   }
}
