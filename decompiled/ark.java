import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class ark {
   private final Map<arg, arh> a;

   public ark(Map<arg, arh> var1) {
      this.a = ImmutableMap.copyOf(_snowman);
   }

   private arh d(arg var1) {
      arh _snowman = this.a.get(_snowman);
      if (_snowman == null) {
         throw new IllegalArgumentException("Can't find attribute " + gm.af.b(_snowman));
      } else {
         return _snowman;
      }
   }

   public double a(arg var1) {
      return this.d(_snowman).f();
   }

   public double b(arg var1) {
      return this.d(_snowman).b();
   }

   public double a(arg var1, UUID var2) {
      arj _snowman = this.d(_snowman).a(_snowman);
      if (_snowman == null) {
         throw new IllegalArgumentException("Can't find modifier " + _snowman + " on attribute " + gm.af.b(_snowman));
      } else {
         return _snowman.d();
      }
   }

   @Nullable
   public arh a(Consumer<arh> var1, arg var2) {
      arh _snowman = this.a.get(_snowman);
      if (_snowman == null) {
         return null;
      } else {
         arh _snowmanx = new arh(_snowman, _snowman);
         _snowmanx.a(_snowman);
         return _snowmanx;
      }
   }

   public static ark.a a() {
      return new ark.a();
   }

   public boolean c(arg var1) {
      return this.a.containsKey(_snowman);
   }

   public boolean b(arg var1, UUID var2) {
      arh _snowman = this.a.get(_snowman);
      return _snowman != null && _snowman.a(_snowman) != null;
   }

   public static class a {
      private final Map<arg, arh> a = Maps.newHashMap();
      private boolean b;

      public a() {
      }

      private arh b(arg var1) {
         arh _snowman = new arh(_snowman, var2x -> {
            if (this.b) {
               throw new UnsupportedOperationException("Tried to change value for default attribute instance: " + gm.af.b(_snowman));
            }
         });
         this.a.put(_snowman, _snowman);
         return _snowman;
      }

      public ark.a a(arg var1) {
         this.b(_snowman);
         return this;
      }

      public ark.a a(arg var1, double var2) {
         arh _snowman = this.b(_snowman);
         _snowman.a(_snowman);
         return this;
      }

      public ark a() {
         this.b = true;
         return new ark(this.a);
      }
   }
}
