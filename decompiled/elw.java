import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.commons.lang3.tuple.Pair;

public class elw implements elo {
   private final List<Pair<Predicate<ceh>, elo>> g;
   protected final boolean a;
   protected final boolean b;
   protected final boolean c;
   protected final ekc d;
   protected final ebm e;
   protected final ebk f;
   private final Map<ceh, BitSet> h = new Object2ObjectOpenCustomHashMap(x.k());

   public elw(List<Pair<Predicate<ceh>, elo>> var1) {
      this.g = _snowman;
      elo _snowman = (elo)_snowman.iterator().next().getRight();
      this.a = _snowman.a();
      this.b = _snowman.b();
      this.c = _snowman.c();
      this.d = _snowman.e();
      this.e = _snowman.f();
      this.f = _snowman.g();
   }

   @Override
   public List<eba> a(@Nullable ceh var1, @Nullable gc var2, Random var3) {
      if (_snowman == null) {
         return Collections.emptyList();
      } else {
         BitSet _snowman = this.h.get(_snowman);
         if (_snowman == null) {
            _snowman = new BitSet();

            for (int _snowmanx = 0; _snowmanx < this.g.size(); _snowmanx++) {
               Pair<Predicate<ceh>, elo> _snowmanxx = this.g.get(_snowmanx);
               if (((Predicate)_snowmanxx.getLeft()).test(_snowman)) {
                  _snowman.set(_snowmanx);
               }
            }

            this.h.put(_snowman, _snowman);
         }

         List<eba> _snowmanxx = Lists.newArrayList();
         long _snowmanxxx = _snowman.nextLong();

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.length(); _snowmanxxxx++) {
            if (_snowman.get(_snowmanxxxx)) {
               _snowmanxx.addAll(((elo)this.g.get(_snowmanxxxx).getRight()).a(_snowman, _snowman, new Random(_snowmanxxx)));
            }
         }

         return _snowmanxx;
      }
   }

   @Override
   public boolean a() {
      return this.a;
   }

   @Override
   public boolean b() {
      return this.b;
   }

   @Override
   public boolean c() {
      return this.c;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public ekc e() {
      return this.d;
   }

   @Override
   public ebm f() {
      return this.e;
   }

   @Override
   public ebk g() {
      return this.f;
   }

   public static class a {
      private final List<Pair<Predicate<ceh>, elo>> a = Lists.newArrayList();

      public a() {
      }

      public void a(Predicate<ceh> var1, elo var2) {
         this.a.add(Pair.of(_snowman, _snowman));
      }

      public elo a() {
         return new elw(this.a);
      }
   }
}
