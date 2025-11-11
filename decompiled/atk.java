import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.longs.Long2LongMap;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class atk extends arv<aqm> {
   private final float b;
   private final Long2LongMap c = new Long2LongOpenHashMap();
   private int d;
   private long e;

   public atk(float var1) {
      super(ImmutableMap.of(ayd.m, aye.b, ayd.b, aye.b));
      this.b = _snowman;
   }

   @Override
   protected boolean a(aag var1, aqm var2) {
      if (_snowman.T() - this.e < 20L) {
         return false;
      } else {
         aqu _snowman = (aqu)_snowman;
         azo _snowmanx = _snowman.y();
         Optional<fx> _snowmanxx = _snowmanx.d(azr.r.c(), _snowman.cB(), 48, azo.b.c);
         return _snowmanxx.isPresent() && !(_snowmanxx.get().j(_snowman.cB()) <= 4.0);
      }
   }

   @Override
   protected void a(aag var1, aqm var2, long var3) {
      this.d = 0;
      this.e = _snowman.T() + (long)_snowman.u_().nextInt(20);
      aqu _snowman = (aqu)_snowman;
      azo _snowmanx = _snowman.y();
      Predicate<fx> _snowmanxx = var1x -> {
         long _snowmanxxx = var1x.a();
         if (this.c.containsKey(_snowmanxxx)) {
            return false;
         } else if (++this.d >= 5) {
            return false;
         } else {
            this.c.put(_snowmanxxx, this.e + 40L);
            return true;
         }
      };
      Stream<fx> _snowmanxxx = _snowmanx.a(azr.r.c(), _snowmanxx, _snowman.cB(), 48, azo.b.c);
      cxd _snowmanxxxx = _snowman.x().a(_snowmanxxx, azr.r.d());
      if (_snowmanxxxx != null && _snowmanxxxx.j()) {
         fx _snowmanxxxxx = _snowmanxxxx.m();
         Optional<azr> _snowmanxxxxxx = _snowmanx.c(_snowmanxxxxx);
         if (_snowmanxxxxxx.isPresent()) {
            _snowman.cJ().a(ayd.m, new ayf(_snowmanxxxxx, this.b, 1));
            rz.c(_snowman, _snowmanxxxxx);
         }
      } else if (this.d < 5) {
         this.c.long2LongEntrySet().removeIf(var1x -> var1x.getLongValue() < this.e);
      }
   }
}
