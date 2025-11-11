import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ayt extends azb<aqn> {
   private final Long2LongMap a = new Long2LongOpenHashMap();
   private int b;
   private long c;

   public ayt() {
      super(20);
   }

   @Override
   public Set<ayd<?>> a() {
      return ImmutableSet.of(ayd.w);
   }

   protected void a(aag var1, aqn var2) {
      if (_snowman.w_()) {
         this.b = 0;
         this.c = _snowman.T() + (long)_snowman.u_().nextInt(20);
         azo _snowman = _snowman.y();
         Predicate<fx> _snowmanx = var1x -> {
            long _snowmanxx = var1x.a();
            if (this.a.containsKey(_snowmanxx)) {
               return false;
            } else if (++this.b >= 5) {
               return false;
            } else {
               this.a.put(_snowmanxx, this.c + 40L);
               return true;
            }
         };
         Stream<fx> _snowmanxx = _snowman.a(azr.r.c(), _snowmanx, _snowman.cB(), 48, azo.b.c);
         cxd _snowmanxxx = _snowman.x().a(_snowmanxx, azr.r.d());
         if (_snowmanxxx != null && _snowmanxxx.j()) {
            fx _snowmanxxxx = _snowmanxxx.m();
            Optional<azr> _snowmanxxxxx = _snowman.c(_snowmanxxxx);
            if (_snowmanxxxxx.isPresent()) {
               _snowman.cJ().a(ayd.w, _snowmanxxxx);
            }
         } else if (this.b < 5) {
            this.a.long2LongEntrySet().removeIf(var1x -> var1x.getLongValue() < this.c);
         }
      }
   }
}
