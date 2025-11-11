import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class azo extends chb<azq> {
   private final azo.a a;
   private final LongSet b = new LongOpenHashSet();

   public azo(File var1, DataFixer var2, boolean var3) {
      super(_snowman, azq::a, azq::new, _snowman, aga.j, _snowman);
      this.a = new azo.a();
   }

   public void a(fx var1, azr var2) {
      this.e(gp.a(_snowman).s()).a(_snowman, _snowman);
   }

   public void a(fx var1) {
      this.e(gp.a(_snowman).s()).a(_snowman);
   }

   public long a(Predicate<azr> var1, fx var2, int var3, azo.b var4) {
      return this.c(_snowman, _snowman, _snowman, _snowman).count();
   }

   public boolean a(azr var1, fx var2) {
      Optional<azr> _snowman = this.e(gp.a(_snowman).s()).d(_snowman);
      return _snowman.isPresent() && _snowman.get().equals(_snowman);
   }

   public Stream<azp> b(Predicate<azr> var1, fx var2, int var3, azo.b var4) {
      int _snowman = Math.floorDiv(_snowman, 16) + 1;
      return brd.a(new brd(_snowman), _snowman).flatMap(var3x -> this.a(_snowman, var3x, _snowman)).filter(var2x -> {
         fx _snowmanx = var2x.f();
         return Math.abs(_snowmanx.u() - _snowman.u()) <= _snowman && Math.abs(_snowmanx.w() - _snowman.w()) <= _snowman;
      });
   }

   public Stream<azp> c(Predicate<azr> var1, fx var2, int var3, azo.b var4) {
      int _snowman = _snowman * _snowman;
      return this.b(_snowman, _snowman, _snowman, _snowman).filter(var2x -> var2x.f().j(_snowman) <= (double)_snowman);
   }

   public Stream<azp> a(Predicate<azr> var1, brd var2, azo.b var3) {
      return IntStream.range(0, 16).boxed().map(var2x -> this.d(gp.a(_snowman, var2x).s())).filter(Optional::isPresent).flatMap(var2x -> var2x.get().a(_snowman, _snowman));
   }

   public Stream<fx> a(Predicate<azr> var1, Predicate<fx> var2, fx var3, int var4, azo.b var5) {
      return this.c(_snowman, _snowman, _snowman, _snowman).map(azp::f).filter(_snowman);
   }

   public Stream<fx> b(Predicate<azr> var1, Predicate<fx> var2, fx var3, int var4, azo.b var5) {
      return this.a(_snowman, _snowman, _snowman, _snowman, _snowman).sorted(Comparator.comparingDouble(var1x -> var1x.j(_snowman)));
   }

   public Optional<fx> c(Predicate<azr> var1, Predicate<fx> var2, fx var3, int var4, azo.b var5) {
      return this.a(_snowman, _snowman, _snowman, _snowman, _snowman).findFirst();
   }

   public Optional<fx> d(Predicate<azr> var1, fx var2, int var3, azo.b var4) {
      return this.c(_snowman, _snowman, _snowman, _snowman).map(azp::f).min(Comparator.comparingDouble(var1x -> var1x.j(_snowman)));
   }

   public Optional<fx> a(Predicate<azr> var1, Predicate<fx> var2, fx var3, int var4) {
      return this.c(_snowman, _snowman, _snowman, azo.b.a).filter(var1x -> _snowman.test(var1x.f())).findFirst().map(var0 -> {
         var0.b();
         return var0.f();
      });
   }

   public Optional<fx> a(Predicate<azr> var1, Predicate<fx> var2, azo.b var3, fx var4, int var5, Random var6) {
      List<azp> _snowman = this.c(_snowman, _snowman, _snowman, _snowman).collect(Collectors.toList());
      Collections.shuffle(_snowman, _snowman);
      return _snowman.stream().filter(var1x -> _snowman.test(var1x.f())).findFirst().map(azp::f);
   }

   public boolean b(fx var1) {
      return this.e(gp.a(_snowman).s()).c(_snowman);
   }

   public boolean a(fx var1, Predicate<azr> var2) {
      return this.d(gp.a(_snowman).s()).map(var2x -> var2x.a(_snowman, _snowman)).orElse(false);
   }

   public Optional<azr> c(fx var1) {
      azq _snowman = this.e(gp.a(_snowman).s());
      return _snowman.d(_snowman);
   }

   public int a(gp var1) {
      this.a.a();
      return this.a.c(_snowman.s());
   }

   private boolean f(long var1) {
      Optional<azq> _snowman = this.c(_snowman);
      return _snowman == null ? false : _snowman.<Boolean>map(var0 -> var0.a(azr.b, azo.b.b).count() > 0L).orElse(false);
   }

   @Override
   public void a(BooleanSupplier var1) {
      super.a(_snowman);
      this.a.a();
   }

   @Override
   protected void a(long var1) {
      super.a(_snowman);
      this.a.b(_snowman, this.a.b(_snowman), false);
   }

   @Override
   protected void b(long var1) {
      this.a.b(_snowman, this.a.b(_snowman), false);
   }

   public void a(brd var1, cgi var2) {
      gp _snowman = gp.a(_snowman, _snowman.g() >> 4);
      x.a(this.d(_snowman.s()), var3x -> var3x.a(var3xx -> {
            if (a(_snowman)) {
               this.a(_snowman, _snowman, var3xx);
            }
         }), () -> {
         if (a(_snowman)) {
            azq _snowmanx = this.e(_snowman.s());
            this.a(_snowman, _snowman, _snowmanx::a);
         }
      });
   }

   private static boolean a(cgi var0) {
      return _snowman.a(azr.x::contains);
   }

   private void a(cgi var1, gp var2, BiConsumer<fx, azr> var3) {
      _snowman.t().forEach(var2x -> {
         ceh _snowman = _snowman.a(gp.b(var2x.u()), gp.b(var2x.v()), gp.b(var2x.w()));
         azr.b(_snowman).ifPresent(var2xx -> _snowman.accept(var2x, var2xx));
      });
   }

   public void a(brz var1, fx var2, int var3) {
      gp.b(new brd(_snowman), Math.floorDiv(_snowman, 16))
         .map(var1x -> Pair.of(var1x, this.d(var1x.s())))
         .filter(var0 -> !((Optional)var0.getSecond()).<Boolean>map(azq::a).orElse(false))
         .map(var0 -> ((gp)var0.getFirst()).r())
         .filter(var1x -> this.b.add(var1x.a()))
         .forEach(var1x -> _snowman.a(var1x.b, var1x.c, cga.a));
   }

   final class a extends aac {
      private final Long2ByteMap b = new Long2ByteOpenHashMap();

      protected a() {
         super(7, 16, 256);
         this.b.defaultReturnValue((byte)7);
      }

      @Override
      protected int b(long var1) {
         return azo.this.f(_snowman) ? 0 : 7;
      }

      @Override
      protected int c(long var1) {
         return this.b.get(_snowman);
      }

      @Override
      protected void a(long var1, int var3) {
         if (_snowman > 6) {
            this.b.remove(_snowman);
         } else {
            this.b.put(_snowman, (byte)_snowman);
         }
      }

      public void a() {
         super.b(Integer.MAX_VALUE);
      }
   }

   public static enum b {
      a(azp::d),
      b(azp::e),
      c(var0 -> true);

      private final Predicate<? super azp> d;

      private b(Predicate<? super azp> var3) {
         this.d = _snowman;
      }

      public Predicate<? super azp> a() {
         return this.d;
      }
   }
}
