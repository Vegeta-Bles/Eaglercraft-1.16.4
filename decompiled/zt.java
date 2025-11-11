import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class zt<T> {
   public static final int a = zs.a + 2;
   private final List<Long2ObjectLinkedOpenHashMap<List<Optional<T>>>> b = IntStream.range(0, a)
      .mapToObj(var0 -> new Long2ObjectLinkedOpenHashMap())
      .collect(Collectors.toList());
   private volatile int c = a;
   private final String d;
   private final LongSet e = new LongOpenHashSet();
   private final int f;

   public zt(String var1, int var2) {
      this.d = _snowman;
      this.f = _snowman;
   }

   protected void a(int var1, brd var2, int var3) {
      if (_snowman < a) {
         Long2ObjectLinkedOpenHashMap<List<Optional<T>>> _snowman = this.b.get(_snowman);
         List<Optional<T>> _snowmanx = (List<Optional<T>>)_snowman.remove(_snowman.a());
         if (_snowman == this.c) {
            while (this.c < a && this.b.get(this.c).isEmpty()) {
               this.c++;
            }
         }

         if (_snowmanx != null && !_snowmanx.isEmpty()) {
            ((List)this.b.get(_snowman).computeIfAbsent(_snowman.a(), var0 -> Lists.newArrayList())).addAll(_snowmanx);
            this.c = Math.min(this.c, _snowman);
         }
      }
   }

   protected void a(Optional<T> var1, long var2, int var4) {
      ((List)this.b.get(_snowman).computeIfAbsent(_snowman, var0 -> Lists.newArrayList())).add(_snowman);
      this.c = Math.min(this.c, _snowman);
   }

   protected void a(long var1, boolean var3) {
      for (Long2ObjectLinkedOpenHashMap<List<Optional<T>>> _snowman : this.b) {
         List<Optional<T>> _snowmanx = (List<Optional<T>>)_snowman.get(_snowman);
         if (_snowmanx != null) {
            if (_snowman) {
               _snowmanx.clear();
            } else {
               _snowmanx.removeIf(var0 -> !var0.isPresent());
            }

            if (_snowmanx.isEmpty()) {
               _snowman.remove(_snowman);
            }
         }
      }

      while (this.c < a && this.b.get(this.c).isEmpty()) {
         this.c++;
      }

      this.e.remove(_snowman);
   }

   private Runnable a(long var1) {
      return () -> this.e.add(_snowman);
   }

   @Nullable
   public Stream<Either<T, Runnable>> a() {
      if (this.e.size() >= this.f) {
         return null;
      } else if (this.c >= a) {
         return null;
      } else {
         int _snowman = this.c;
         Long2ObjectLinkedOpenHashMap<List<Optional<T>>> _snowmanx = this.b.get(_snowman);
         long _snowmanxx = _snowmanx.firstLongKey();
         List<Optional<T>> _snowmanxxx = (List<Optional<T>>)_snowmanx.removeFirst();

         while (this.c < a && this.b.get(this.c).isEmpty()) {
            this.c++;
         }

         return _snowmanxxx.stream().map(var3x -> var3x.map(Either::left).orElseGet(() -> Either.right(this.a(_snowman))));
      }
   }

   @Override
   public String toString() {
      return this.d + " " + this.c + "...";
   }

   @VisibleForTesting
   LongSet b() {
      return new LongOpenHashSet(this.e);
   }
}
