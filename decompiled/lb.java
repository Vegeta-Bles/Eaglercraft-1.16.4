import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class lb {
   private static final Logger a = LogManager.getLogger();
   private final fx b;
   private final aag c;
   private final ll d;
   private final int e;
   private final List<lf> f = Lists.newArrayList();
   private final Map<lf, fx> g = Maps.newHashMap();
   private final List<Pair<la, Collection<lf>>> h = Lists.newArrayList();
   private lp i;
   private int j = 0;
   private fx.a k;

   public lb(Collection<la> var1, fx var2, bzm var3, aag var4, ll var5, int var6) {
      this.k = _snowman.i();
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      _snowman.forEach(var3x -> {
         Collection<lf> _snowman = Lists.newArrayList();

         for (lu _snowmanx : var3x.b()) {
            lf _snowmanxx = new lf(_snowmanx, _snowman, _snowman);
            _snowman.add(_snowmanxx);
            this.f.add(_snowmanxx);
         }

         this.h.add(Pair.of(var3x, _snowman));
      });
   }

   public List<lf> a() {
      return this.f;
   }

   public void b() {
      this.a(0);
   }

   private void a(int var1) {
      this.j = _snowman;
      this.i = new lp();
      if (_snowman < this.h.size()) {
         Pair<la, Collection<lf>> _snowman = this.h.get(this.j);
         la _snowmanx = (la)_snowman.getFirst();
         Collection<lf> _snowmanxx = (Collection<lf>)_snowman.getSecond();
         this.a(_snowmanxx);
         _snowmanx.a(this.c);
         String _snowmanxxx = _snowmanx.a();
         a.info("Running test batch '" + _snowmanxxx + "' (" + _snowmanxx.size() + " tests)...");
         _snowmanxx.forEach(var1x -> {
            this.i.a(var1x);
            this.i.a(new lg() {
               @Override
               public void a(lf var1) {
               }

               @Override
               public void c(lf var1) {
                  lb.this.a(_snowman);
               }
            });
            fx _snowmanxxxx = this.g.get(var1x);
            li.a(var1x, _snowmanxxxx, this.d);
         });
      }
   }

   private void a(lf var1) {
      if (this.i.i()) {
         this.a(this.j + 1);
      }
   }

   private void a(Collection<lf> var1) {
      int _snowman = 0;
      dci _snowmanx = new dci(this.k);

      for (lf _snowmanxx : _snowman) {
         fx _snowmanxxx = new fx(this.k);
         cdj _snowmanxxxx = lq.a(_snowmanxx.s(), _snowmanxxx, _snowmanxx.t(), 2, this.c, true);
         dci _snowmanxxxxx = lq.a(_snowmanxxxx);
         _snowmanxx.a(_snowmanxxxx.o());
         this.g.put(_snowmanxx, new fx(this.k));
         _snowmanx = _snowmanx.b(_snowmanxxxxx);
         this.k.e((int)_snowmanxxxxx.b() + 5, 0, 0);
         if (_snowman++ % this.e == this.e - 1) {
            this.k.e(0, 0, (int)_snowmanx.d() + 6);
            this.k.o(this.b.u());
            _snowmanx = new dci(this.k);
         }
      }
   }
}
