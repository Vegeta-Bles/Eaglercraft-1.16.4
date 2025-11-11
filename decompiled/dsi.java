import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dsi extends dot {
   private static final Logger a = LogManager.getLogger();
   private static final Object2IntMap<vj<brx>> b = x.a(new Object2IntOpenCustomHashMap(x.k()), var0 -> {
      var0.put(brx.g, -13408734);
      var0.put(brx.h, -10075085);
      var0.put(brx.i, -8943531);
      var0.defaultReturnValue(-2236963);
   });
   private final BooleanConsumer c;
   private final aoi p;

   @Nullable
   public static dsi a(djz var0, BooleanConsumer var1, DataFixer var2, cyg.a var3, boolean var4) {
      gn.b _snowman = gn.b();

      try (djz.b _snowmanx = _snowman.a(_snowman, djz::a, djz::a, false, _snowman)) {
         cyn _snowmanxx = _snowmanx.c();
         _snowman.a(_snowman, _snowmanxx);
         ImmutableSet<vj<brx>> _snowmanxxx = _snowmanxx.A().f();
         return new dsi(_snowman, _snowman, _snowman, _snowmanxx.I(), _snowman, _snowmanxxx);
      } catch (Exception var22) {
         a.warn("Failed to load datapacks, can't optimize world", var22);
         return null;
      }
   }

   private dsi(BooleanConsumer var1, DataFixer var2, cyg.a var3, bsa var4, boolean var5, ImmutableSet<vj<brx>> var6) {
      super(new of("optimizeWorld.title", _snowman.a()));
      this.c = _snowman;
      this.p = new aoi(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected void b() {
      super.b();
      this.a((dlj)(new dlj(this.k / 2 - 100, this.l / 4 + 150, 200, 20, nq.d, var1 -> {
         this.p.a();
         this.c.accept(false);
      })));
   }

   @Override
   public void d() {
      if (this.p.b()) {
         this.c.accept(true);
      }
   }

   @Override
   public void at_() {
      this.c.accept(false);
   }

   @Override
   public void e() {
      this.p.a();
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.d, this.k / 2, 20, 16777215);
      int _snowman = this.k / 2 - 150;
      int _snowmanx = this.k / 2 + 150;
      int _snowmanxx = this.l / 4 + 100;
      int _snowmanxxx = _snowmanxx + 10;
      a(_snowman, this.o, this.p.h(), this.k / 2, _snowmanxx - 9 - 2, 10526880);
      if (this.p.e() > 0) {
         a(_snowman, _snowman - 1, _snowmanxx - 1, _snowmanx + 1, _snowmanxxx + 1, -16777216);
         b(_snowman, this.o, new of("optimizeWorld.info.converted", this.p.f()), _snowman, 40, 10526880);
         b(_snowman, this.o, new of("optimizeWorld.info.skipped", this.p.g()), _snowman, 40 + 9 + 3, 10526880);
         b(_snowman, this.o, new of("optimizeWorld.info.total", this.p.e()), _snowman, 40 + (9 + 3) * 2, 10526880);
         int _snowmanxxxx = 0;
         UnmodifiableIterator var10 = this.p.c().iterator();

         while (var10.hasNext()) {
            vj<brx> _snowmanxxxxx = (vj<brx>)var10.next();
            int _snowmanxxxxxx = afm.d(this.p.a(_snowmanxxxxx) * (float)(_snowmanx - _snowman));
            a(_snowman, _snowman + _snowmanxxxx, _snowmanxx, _snowman + _snowmanxxxx + _snowmanxxxxxx, _snowmanxxx, b.getInt(_snowmanxxxxx));
            _snowmanxxxx += _snowmanxxxxxx;
         }

         int _snowmanxxxxx = this.p.f() + this.p.g();
         a(_snowman, this.o, _snowmanxxxxx + " / " + this.p.e(), this.k / 2, _snowmanxx + 2 * 9 + 2, 10526880);
         a(_snowman, this.o, afm.d(this.p.d() * 100.0F) + "%", this.k / 2, _snowmanxx + (_snowmanxxx - _snowmanxx) / 2 - 9 / 2, 10526880);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
