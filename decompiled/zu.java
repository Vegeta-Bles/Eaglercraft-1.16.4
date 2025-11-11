import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class zu implements AutoCloseable, zr.c {
   private static final Logger a = LogManager.getLogger();
   private final Map<aod<?>, zt<? extends Function<aod<afx>, ?>>> b;
   private final Set<aod<?>> c;
   private final aoe<aog.b> d;

   public zu(List<aod<?>> var1, Executor var2, int var3) {
      this.b = _snowman.stream().collect(Collectors.toMap(Function.identity(), var1x -> new zt<>(var1x.bj() + "_queue", _snowman)));
      this.c = Sets.newHashSet(_snowman);
      this.d = new aoe<>(new aog.a(4), _snowman, "sorter");
   }

   public static zu.a<Runnable> a(Runnable var0, long var1, IntSupplier var3) {
      return new zu.a<>(var1x -> () -> {
            _snowman.run();
            var1x.a(afx.a);
         }, _snowman, _snowman);
   }

   public static zu.a<Runnable> a(zr var0, Runnable var1) {
      return a(_snowman, _snowman.i().a(), _snowman::k);
   }

   public static zu.b a(Runnable var0, long var1, boolean var3) {
      return new zu.b(_snowman, _snowman, _snowman);
   }

   public <T> aod<zu.a<T>> a(aod<T> var1, boolean var2) {
      return this.d.<aod<zu.a<T>>>b(var3 -> new aog.b(0, () -> {
            this.b(_snowman);
            var3.a(aod.a("chunk priority sorter around " + _snowman.bj(), var3x -> this.a(_snowman, var3x.a, var3x.b, var3x.c, _snowman)));
         })).join();
   }

   public aod<zu.b> a(aod<Runnable> var1) {
      return this.d
         .<aod<zu.b>>b(var2 -> new aog.b(0, () -> var2.a(aod.a("chunk priority sorter around " + _snowman.bj(), var2x -> this.a(_snowman, var2x.b, var2x.a, var2x.c)))))
         .join();
   }

   @Override
   public void a(brd var1, IntSupplier var2, int var3, IntConsumer var4) {
      this.d.a(new aog.b(0, () -> {
         int _snowman = _snowman.getAsInt();
         this.b.values().forEach(var3x -> var3x.a(_snowman, _snowman, _snowman));
         _snowman.accept(_snowman);
      }));
   }

   private <T> void a(aod<T> var1, long var2, Runnable var4, boolean var5) {
      this.d.a(new aog.b(1, () -> {
         zt<Function<aod<afx>, T>> _snowman = this.b(_snowman);
         _snowman.a(_snowman, _snowman);
         if (this.c.remove(_snowman)) {
            this.a(_snowman, _snowman);
         }

         _snowman.run();
      }));
   }

   private <T> void a(aod<T> var1, Function<aod<afx>, T> var2, long var3, IntSupplier var5, boolean var6) {
      this.d.a(new aog.b(2, () -> {
         zt<Function<aod<afx>, T>> _snowman = this.b(_snowman);
         int _snowmanx = _snowman.getAsInt();
         _snowman.a(Optional.of(_snowman), _snowman, _snowmanx);
         if (_snowman) {
            _snowman.a(Optional.empty(), _snowman, _snowmanx);
         }

         if (this.c.remove(_snowman)) {
            this.a(_snowman, _snowman);
         }
      }));
   }

   private <T> void a(zt<Function<aod<afx>, T>> var1, aod<T> var2) {
      this.d.a(new aog.b(3, () -> {
         Stream<Either<Function<aod<afx>, T>, Runnable>> _snowman = _snowman.a();
         if (_snowman == null) {
            this.c.add(_snowman);
         } else {
            x.b(_snowman.<CompletableFuture>map(var1x -> (CompletableFuture)var1x.map(_snowman::b, var0x -> {
                  var0x.run();
                  return CompletableFuture.completedFuture(afx.a);
               })).collect(Collectors.toList())).thenAccept(var3x -> this.a(_snowman, _snowman));
         }
      }));
   }

   private <T> zt<Function<aod<afx>, T>> b(aod<T> var1) {
      zt<? extends Function<aod<afx>, ?>> _snowman = this.b.get(_snowman);
      if (_snowman == null) {
         throw (IllegalArgumentException)x.c(new IllegalArgumentException("No queue for: " + _snowman));
      } else {
         return (zt<Function<aod<afx>, T>>)_snowman;
      }
   }

   @VisibleForTesting
   public String a() {
      return this.b
            .entrySet()
            .stream()
            .map(
               var0 -> var0.getKey().bj()
                     + "=["
                     + var0.getValue().b().stream().map(var0x -> var0x + ":" + new brd(var0x)).collect(Collectors.joining(","))
                     + "]"
            )
            .collect(Collectors.joining(","))
         + ", s="
         + this.c.size();
   }

   @Override
   public void close() {
      this.b.keySet().forEach(aod::close);
   }

   public static final class a<T> {
      private final Function<aod<afx>, T> a;
      private final long b;
      private final IntSupplier c;

      private a(Function<aod<afx>, T> var1, long var2, IntSupplier var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }
   }

   public static final class b {
      private final Runnable a;
      private final long b;
      private final boolean c;

      private b(Runnable var1, long var2, boolean var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }
   }
}
