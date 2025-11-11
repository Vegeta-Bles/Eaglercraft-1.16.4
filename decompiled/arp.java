import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class arp extends arv<aqu> {
   private final azr b;
   private final ayd<gf> c;
   private final boolean d;
   private final Optional<Byte> e;
   private long f;
   private final Long2ObjectMap<arp.a> g = new Long2ObjectOpenHashMap();

   public arp(azr var1, ayd<gf> var2, ayd<gf> var3, boolean var4, Optional<Byte> var5) {
      super(a(_snowman, _snowman));
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   public arp(azr var1, ayd<gf> var2, boolean var3, Optional<Byte> var4) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private static ImmutableMap<ayd<?>, aye> a(ayd<gf> var0, ayd<gf> var1) {
      Builder<ayd<?>, aye> _snowman = ImmutableMap.builder();
      _snowman.put(_snowman, aye.b);
      if (_snowman != _snowman) {
         _snowman.put(_snowman, aye.b);
      }

      return _snowman.build();
   }

   protected boolean a(aag var1, aqu var2) {
      if (this.d && _snowman.w_()) {
         return false;
      } else if (this.f == 0L) {
         this.f = _snowman.l.T() + (long)_snowman.t.nextInt(20);
         return false;
      } else {
         return _snowman.T() >= this.f;
      }
   }

   protected void a(aag var1, aqu var2, long var3) {
      this.f = _snowman + 20L + (long)_snowman.u_().nextInt(20);
      azo _snowman = _snowman.y();
      this.g.long2ObjectEntrySet().removeIf(var2x -> !((arp.a)var2x.getValue()).b(_snowman));
      Predicate<fx> _snowmanx = var3x -> {
         arp.a _snowmanxx = (arp.a)this.g.get(var3x.a());
         if (_snowmanxx == null) {
            return true;
         } else if (!_snowmanxx.c(_snowman)) {
            return false;
         } else {
            _snowmanxx.a(_snowman);
            return true;
         }
      };
      Set<fx> _snowmanxx = _snowman.b(this.b.c(), _snowmanx, _snowman.cB(), 48, azo.b.a).limit(5L).collect(Collectors.toSet());
      cxd _snowmanxxx = _snowman.x().a(_snowmanxx, this.b.d());
      if (_snowmanxxx != null && _snowmanxxx.j()) {
         fx _snowmanxxxx = _snowmanxxx.m();
         _snowman.c(_snowmanxxxx).ifPresent(var5x -> {
            _snowman.a(this.b.c(), var1x -> var1x.equals(_snowman), _snowman, 1);
            _snowman.cJ().a(this.c, gf.a(_snowman.Y(), _snowman));
            this.e.ifPresent(var2x -> _snowman.a(_snowman, var2x));
            this.g.clear();
            rz.c(_snowman, _snowman);
         });
      } else {
         for (fx _snowmanxxxx : _snowmanxx) {
            this.g.computeIfAbsent(_snowmanxxxx.a(), var3x -> new arp.a(_snowman.l.t, _snowman));
         }
      }
   }

   static class a {
      private final Random a;
      private long b;
      private long c;
      private int d;

      a(Random var1, long var2) {
         this.a = _snowman;
         this.a(_snowman);
      }

      public void a(long var1) {
         this.b = _snowman;
         int _snowman = this.d + this.a.nextInt(40) + 40;
         this.d = Math.min(_snowman, 400);
         this.c = _snowman + (long)this.d;
      }

      public boolean b(long var1) {
         return _snowman - this.b < 400L;
      }

      public boolean c(long var1) {
         return _snowman >= this.c;
      }

      @Override
      public String toString() {
         return "RetryMarker{, previousAttemptAt=" + this.b + ", nextScheduledAttemptAt=" + this.c + ", currentDelay=" + this.d + '}';
      }
   }
}
