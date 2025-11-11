import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public interface eag {
   static eag.a a(dfh var0) {
      return a(ImmutableMap.of(), _snowman);
   }

   static eag.a a(Map<eao, dfh> var0, dfh var1) {
      return new eag.a(_snowman, _snowman);
   }

   dfq getBuffer(eao var1);

   public static class a implements eag {
      protected final dfh a;
      protected final Map<eao, dfh> b;
      protected Optional<eao> c = Optional.empty();
      protected final Set<dfh> d = Sets.newHashSet();

      protected a(dfh var1, Map<eao, dfh> var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      @Override
      public dfq getBuffer(eao var1) {
         Optional<eao> _snowman = _snowman.B();
         dfh _snowmanx = this.b(_snowman);
         if (!Objects.equals(this.c, _snowman)) {
            if (this.c.isPresent()) {
               eao _snowmanxx = this.c.get();
               if (!this.b.containsKey(_snowmanxx)) {
                  this.a(_snowmanxx);
               }
            }

            if (this.d.add(_snowmanx)) {
               _snowmanx.a(_snowman.x(), _snowman.w());
            }

            this.c = _snowman;
         }

         return _snowmanx;
      }

      private dfh b(eao var1) {
         return this.b.getOrDefault(_snowman, this.a);
      }

      public void a() {
         this.c.ifPresent(var1 -> {
            dfq _snowman = this.getBuffer(var1);
            if (_snowman == this.a) {
               this.a(var1);
            }
         });

         for (eao _snowman : this.b.keySet()) {
            this.a(_snowman);
         }
      }

      public void a(eao var1) {
         dfh _snowman = this.b(_snowman);
         boolean _snowmanx = Objects.equals(this.c, _snowman.B());
         if (_snowmanx || _snowman != this.a) {
            if (this.d.remove(_snowman)) {
               _snowman.a(_snowman, 0, 0, 0);
               if (_snowmanx) {
                  this.c = Optional.empty();
               }
            }
         }
      }
   }
}
