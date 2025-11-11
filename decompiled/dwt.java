import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.world.level.ColorResolver;

public class dwt extends brx {
   private final Int2ObjectMap<aqa> a = new Int2ObjectOpenHashMap();
   private final dwu b;
   private final eae c;
   private final dwt.a d;
   private final dzv x;
   private final djz y = djz.C();
   private final List<dzj> z = Lists.newArrayList();
   private ddn A = new ddn();
   private final Map<String, cxx> B = Maps.newHashMap();
   private int C;
   private final Object2ObjectArrayMap<ColorResolver, dkp> D = x.a(new Object2ObjectArrayMap(3), var0 -> {
      var0.put(dzr.a, new dkp());
      var0.put(dzr.b, new dkp());
      var0.put(dzr.c, new dkp());
   });
   private final dwr E;

   public dwt(dwu var1, dwt.a var2, vj<brx> var3, chd var4, int var5, Supplier<anw> var6, eae var7, boolean var8, long var9) {
      super(_snowman, _snowman, _snowman, _snowman, true, _snowman, _snowman);
      this.b = _snowman;
      this.E = new dwr(this, _snowman);
      this.d = _snowman;
      this.c = _snowman;
      this.x = dzv.a(_snowman);
      this.b(new fx(8, 64, 8), 0.0F);
      this.Q();
      this.R();
   }

   public dzv a() {
      return this.x;
   }

   public void a(BooleanSupplier var1) {
      this.f().s();
      this.y();
      this.Z().a("blocks");
      this.E.a(_snowman);
      this.Z().c();
   }

   private void y() {
      this.a(this.u.e() + 1L);
      if (this.u.q().b(brt.j)) {
         this.b(this.u.f() + 1L);
      }
   }

   public void a(long var1) {
      this.d.a(_snowman);
   }

   public void b(long var1) {
      if (_snowman < 0L) {
         _snowman = -_snowman;
         this.V().a(brt.j).a(false, null);
      } else {
         this.V().a(brt.j).a(true, null);
      }

      this.d.b(_snowman);
   }

   public Iterable<aqa> b() {
      return this.a.values();
   }

   public void g() {
      anw _snowman = this.Z();
      _snowman.a("entities");
      ObjectIterator<Entry<aqa>> _snowmanx = this.a.int2ObjectEntrySet().iterator();

      while (_snowmanx.hasNext()) {
         Entry<aqa> _snowmanxx = (Entry<aqa>)_snowmanx.next();
         aqa _snowmanxxx = (aqa)_snowmanxx.getValue();
         if (!_snowmanxxx.br()) {
            _snowman.a("tick");
            if (!_snowmanxxx.y) {
               this.a(this::a, _snowmanxxx);
            }

            _snowman.c();
            _snowman.a("remove");
            if (_snowmanxxx.y) {
               _snowmanx.remove();
               this.d(_snowmanxxx);
            }

            _snowman.c();
         }
      }

      this.O();
      _snowman.c();
   }

   public void a(aqa var1) {
      if (!(_snowman instanceof bfw) && !this.n().a(_snowman)) {
         this.b(_snowman);
      } else {
         _snowman.g(_snowman.cD(), _snowman.cE(), _snowman.cH());
         _snowman.r = _snowman.p;
         _snowman.s = _snowman.q;
         if (_snowman.U || _snowman.a_()) {
            _snowman.K++;
            this.Z().a(() -> gm.S.b(_snowman.X()).toString());
            _snowman.j();
            this.Z().c();
         }

         this.b(_snowman);
         if (_snowman.U) {
            for (aqa _snowman : _snowman.cn()) {
               this.a(_snowman, _snowman);
            }
         }
      }
   }

   public void a(aqa var1, aqa var2) {
      if (_snowman.y || _snowman.ct() != _snowman) {
         _snowman.l();
      } else if (_snowman instanceof bfw || this.n().a(_snowman)) {
         _snowman.g(_snowman.cD(), _snowman.cE(), _snowman.cH());
         _snowman.r = _snowman.p;
         _snowman.s = _snowman.q;
         if (_snowman.U) {
            _snowman.K++;
            _snowman.ba();
         }

         this.b(_snowman);
         if (_snowman.U) {
            for (aqa _snowman : _snowman.cn()) {
               this.a(_snowman, _snowman);
            }
         }
      }
   }

   private void b(aqa var1) {
      if (_snowman.cl()) {
         this.Z().a("chunkCheck");
         int _snowman = afm.c(_snowman.cD() / 16.0);
         int _snowmanx = afm.c(_snowman.cE() / 16.0);
         int _snowmanxx = afm.c(_snowman.cH() / 16.0);
         if (!_snowman.U || _snowman.V != _snowman || _snowman.W != _snowmanx || _snowman.X != _snowmanxx) {
            if (_snowman.U && this.b(_snowman.V, _snowman.X)) {
               this.d(_snowman.V, _snowman.X).a(_snowman, _snowman.W);
            }

            if (!_snowman.ck() && !this.b(_snowman, _snowmanxx)) {
               if (_snowman.U) {
                  e.warn("Entity {} left loaded chunk area", _snowman);
               }

               _snowman.U = false;
            } else {
               this.d(_snowman, _snowmanxx).a(_snowman);
            }
         }

         this.Z().c();
      }
   }

   public void a(cgh var1) {
      this.m.addAll(_snowman.y().values());
      this.E.l().a(_snowman.g(), false);
   }

   public void e(int var1, int var2) {
      this.D.forEach((var2x, var3) -> var3.a(_snowman, _snowman));
   }

   public void i() {
      this.D.forEach((var0, var1) -> var1.a());
   }

   @Override
   public boolean b(int var1, int var2) {
      return true;
   }

   public int j() {
      return this.a.size();
   }

   public void a(int var1, dzj var2) {
      this.b(_snowman, _snowman);
      this.z.add(_snowman);
   }

   public void a(int var1, aqa var2) {
      this.b(_snowman, _snowman);
   }

   private void b(int var1, aqa var2) {
      this.d(_snowman);
      this.a.put(_snowman, _snowman);
      this.n().b(afm.c(_snowman.cD() / 16.0), afm.c(_snowman.cH() / 16.0), cga.m, true).a(_snowman);
   }

   public void d(int var1) {
      aqa _snowman = (aqa)this.a.remove(_snowman);
      if (_snowman != null) {
         _snowman.ad();
         this.d(_snowman);
      }
   }

   private void d(aqa var1) {
      _snowman.V();
      if (_snowman.U) {
         this.d(_snowman.V, _snowman.X).b(_snowman);
      }

      this.z.remove(_snowman);
   }

   public void b(cgh var1) {
      ObjectIterator var2 = this.a.int2ObjectEntrySet().iterator();

      while (var2.hasNext()) {
         Entry<aqa> _snowman = (Entry<aqa>)var2.next();
         aqa _snowmanx = (aqa)_snowman.getValue();
         int _snowmanxx = afm.c(_snowmanx.cD() / 16.0);
         int _snowmanxxx = afm.c(_snowmanx.cH() / 16.0);
         if (_snowmanxx == _snowman.g().b && _snowmanxxx == _snowman.g().c) {
            _snowman.a(_snowmanx);
         }
      }
   }

   @Nullable
   @Override
   public aqa a(int var1) {
      return (aqa)this.a.get(_snowman);
   }

   public void b(fx var1, ceh var2) {
      this.a(_snowman, _snowman, 19);
   }

   @Override
   public void S() {
      this.b.a().a(new of("multiplayer.status.quitting"));
   }

   public void c(int var1, int var2, int var3) {
      int _snowman = 32;
      Random _snowmanx = new Random();
      boolean _snowmanxx = false;
      if (this.y.q.l() == bru.c) {
         for (bmb _snowmanxxx : this.y.s.bn()) {
            if (_snowmanxxx.b() == bup.go.h()) {
               _snowmanxx = true;
               break;
            }
         }
      }

      fx.a _snowmanxxxx = new fx.a();

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < 667; _snowmanxxxxx++) {
         this.a(_snowman, _snowman, _snowman, 16, _snowmanx, _snowmanxx, _snowmanxxxx);
         this.a(_snowman, _snowman, _snowman, 32, _snowmanx, _snowmanxx, _snowmanxxxx);
      }
   }

   public void a(int var1, int var2, int var3, int var4, Random var5, boolean var6, fx.a var7) {
      int _snowman = _snowman + this.t.nextInt(_snowman) - this.t.nextInt(_snowman);
      int _snowmanx = _snowman + this.t.nextInt(_snowman) - this.t.nextInt(_snowman);
      int _snowmanxx = _snowman + this.t.nextInt(_snowman) - this.t.nextInt(_snowman);
      _snowman.d(_snowman, _snowmanx, _snowmanxx);
      ceh _snowmanxxx = this.d_(_snowman);
      _snowmanxxx.b().a(_snowmanxxx, this, _snowman, _snowman);
      cux _snowmanxxxx = this.b(_snowman);
      if (!_snowmanxxxx.c()) {
         _snowmanxxxx.a(this, _snowman, _snowman);
         hf _snowmanxxxxx = _snowmanxxxx.h();
         if (_snowmanxxxxx != null && this.t.nextInt(10) == 0) {
            boolean _snowmanxxxxxx = _snowmanxxx.d(this, _snowman, gc.a);
            fx _snowmanxxxxxxx = _snowman.c();
            this.a(_snowmanxxxxxxx, this.d_(_snowmanxxxxxxx), _snowmanxxxxx, _snowmanxxxxxx);
         }
      }

      if (_snowman && _snowmanxxx.a(bup.go)) {
         this.a(hh.c, (double)_snowman + 0.5, (double)_snowmanx + 0.5, (double)_snowmanxx + 0.5, 0.0, 0.0, 0.0);
      }

      if (!_snowmanxxx.r(this, _snowman)) {
         this.v(_snowman).o().ifPresent(var2x -> {
            if (var2x.a(this.t)) {
               this.a(var2x.a(), (double)_snowman.u() + this.t.nextDouble(), (double)_snowman.v() + this.t.nextDouble(), (double)_snowman.w() + this.t.nextDouble(), 0.0, 0.0, 0.0);
            }
         });
      }
   }

   private void a(fx var1, ceh var2, hf var3, boolean var4) {
      if (_snowman.m().c()) {
         ddh _snowman = _snowman.k(this, _snowman);
         double _snowmanx = _snowman.c(gc.a.b);
         if (_snowmanx < 1.0) {
            if (_snowman) {
               this.a((double)_snowman.u(), (double)(_snowman.u() + 1), (double)_snowman.w(), (double)(_snowman.w() + 1), (double)(_snowman.v() + 1) - 0.05, _snowman);
            }
         } else if (!_snowman.a(aed.W)) {
            double _snowmanxx = _snowman.b(gc.a.b);
            if (_snowmanxx > 0.0) {
               this.a(_snowman, _snowman, _snowman, (double)_snowman.v() + _snowmanxx - 0.05);
            } else {
               fx _snowmanxxx = _snowman.c();
               ceh _snowmanxxxx = this.d_(_snowmanxxx);
               ddh _snowmanxxxxx = _snowmanxxxx.k(this, _snowmanxxx);
               double _snowmanxxxxxx = _snowmanxxxxx.c(gc.a.b);
               if (_snowmanxxxxxx < 1.0 && _snowmanxxxx.m().c()) {
                  this.a(_snowman, _snowman, _snowman, (double)_snowman.v() - 0.05);
               }
            }
         }
      }
   }

   private void a(fx var1, hf var2, ddh var3, double var4) {
      this.a((double)_snowman.u() + _snowman.b(gc.a.a), (double)_snowman.u() + _snowman.c(gc.a.a), (double)_snowman.w() + _snowman.b(gc.a.c), (double)_snowman.w() + _snowman.c(gc.a.c), _snowman, _snowman);
   }

   private void a(double var1, double var3, double var5, double var7, double var9, hf var11) {
      this.a(_snowman, afm.d(this.t.nextDouble(), _snowman, _snowman), _snowman, afm.d(this.t.nextDouble(), _snowman, _snowman), 0.0, 0.0, 0.0);
   }

   public void m() {
      ObjectIterator<Entry<aqa>> _snowman = this.a.int2ObjectEntrySet().iterator();

      while (_snowman.hasNext()) {
         Entry<aqa> _snowmanx = (Entry<aqa>)_snowman.next();
         aqa _snowmanxx = (aqa)_snowmanx.getValue();
         if (_snowmanxx.y) {
            _snowman.remove();
            this.d(_snowmanxx);
         }
      }
   }

   @Override
   public m a(l var1) {
      m _snowman = super.a(_snowman);
      _snowman.a("Server brand", () -> this.y.s.B());
      _snowman.a("Server type", () -> this.y.H() == null ? "Non-integrated multiplayer server" : "Integrated singleplayer server");
      return _snowman;
   }

   @Override
   public void a(@Nullable bfw var1, double var2, double var4, double var6, adp var8, adr var9, float var10, float var11) {
      if (_snowman == this.y.s) {
         this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, false);
      }
   }

   @Override
   public void a(@Nullable bfw var1, aqa var2, adp var3, adr var4, float var5, float var6) {
      if (_snowman == this.y.s) {
         this.y.W().a((emt)(new eml(_snowman, _snowman, _snowman)));
      }
   }

   public void a(fx var1, adp var2, adr var3, float var4, float var5, boolean var6) {
      this.a((double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(double var1, double var3, double var5, adp var7, adr var8, float var9, float var10, boolean var11) {
      double _snowman = this.y.h.k().b().c(_snowman, _snowman, _snowman);
      emp _snowmanx = new emp(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman && _snowman > 100.0) {
         double _snowmanxx = Math.sqrt(_snowman) / 40.0;
         this.y.W().a(_snowmanx, (int)(_snowmanxx * 20.0));
      } else {
         this.y.W().a(_snowmanx);
      }
   }

   @Override
   public void a(double var1, double var3, double var5, double var7, double var9, double var11, @Nullable md var13) {
      this.y.f.a(new dxv.e(this, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.y.f, _snowman));
   }

   @Override
   public void a(oj<?> var1) {
      this.b.a(_snowman);
   }

   @Override
   public bor o() {
      return this.b.d();
   }

   public void a(ddn var1) {
      this.A = _snowman;
   }

   @Override
   public bso<buo> J() {
      return brm.b();
   }

   @Override
   public bso<cuw> I() {
      return brm.b();
   }

   public dwr n() {
      return this.E;
   }

   @Nullable
   @Override
   public cxx a(String var1) {
      return this.B.get(_snowman);
   }

   @Override
   public void a(cxx var1) {
      this.B.put(_snowman.d(), _snowman);
   }

   @Override
   public int t() {
      return 0;
   }

   @Override
   public ddn G() {
      return this.A;
   }

   @Override
   public aen p() {
      return this.b.k();
   }

   @Override
   public gn r() {
      return this.b.o();
   }

   @Override
   public void a(fx var1, ceh var2, ceh var3, int var4) {
      this.c.a(this, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void b(fx var1, ceh var2, ceh var3) {
      this.c.a(_snowman, _snowman, _snowman);
   }

   public void d(int var1, int var2, int var3) {
      this.c.a(_snowman, _snowman, _snowman);
   }

   @Override
   public void a(int var1, fx var2, int var3) {
      this.c.b(_snowman, _snowman, _snowman);
   }

   @Override
   public void b(int var1, fx var2, int var3) {
      this.c.a(_snowman, _snowman, _snowman);
   }

   @Override
   public void a(@Nullable bfw var1, int var2, fx var3, int var4) {
      try {
         this.c.a(_snowman, _snowman, _snowman, _snowman);
      } catch (Throwable var8) {
         l _snowman = l.a(var8, "Playing level event");
         m _snowmanx = _snowman.a("Level event being played");
         _snowmanx.a("Block coordinates", m.a(_snowman));
         _snowmanx.a("Event source", _snowman);
         _snowmanx.a("Event type", _snowman);
         _snowmanx.a("Event data", _snowman);
         throw new u(_snowman);
      }
   }

   @Override
   public void a(hf var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      this.c.a(_snowman, _snowman.b().c(), _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void a(hf var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
      this.c.a(_snowman, _snowman.b().c() || _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void b(hf var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      this.c.a(_snowman, false, true, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public void b(hf var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
      this.c.a(_snowman, _snowman.b().c() || _snowman, true, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public List<dzj> x() {
      return this.z;
   }

   @Override
   public bsv a(int var1, int var2, int var3) {
      return this.r().b(gm.ay).d(btb.b);
   }

   public float g(float var1) {
      float _snowman = this.f(_snowman);
      float _snowmanx = 1.0F - (afm.b(_snowman * (float) (Math.PI * 2)) * 2.0F + 0.2F);
      _snowmanx = afm.a(_snowmanx, 0.0F, 1.0F);
      _snowmanx = 1.0F - _snowmanx;
      _snowmanx = (float)((double)_snowmanx * (1.0 - (double)(this.d(_snowman) * 5.0F) / 16.0));
      _snowmanx = (float)((double)_snowmanx * (1.0 - (double)(this.b(_snowman) * 5.0F) / 16.0));
      return _snowmanx * 0.8F + 0.2F;
   }

   public dcn a(fx var1, float var2) {
      float _snowman = this.f(_snowman);
      float _snowmanx = afm.b(_snowman * (float) (Math.PI * 2)) * 2.0F + 0.5F;
      _snowmanx = afm.a(_snowmanx, 0.0F, 1.0F);
      bsv _snowmanxx = this.v(_snowman);
      int _snowmanxxx = _snowmanxx.a();
      float _snowmanxxxx = (float)(_snowmanxxx >> 16 & 0xFF) / 255.0F;
      float _snowmanxxxxx = (float)(_snowmanxxx >> 8 & 0xFF) / 255.0F;
      float _snowmanxxxxxx = (float)(_snowmanxxx & 0xFF) / 255.0F;
      _snowmanxxxx *= _snowmanx;
      _snowmanxxxxx *= _snowmanx;
      _snowmanxxxxxx *= _snowmanx;
      float _snowmanxxxxxxx = this.d(_snowman);
      if (_snowmanxxxxxxx > 0.0F) {
         float _snowmanxxxxxxxx = (_snowmanxxxx * 0.3F + _snowmanxxxxx * 0.59F + _snowmanxxxxxx * 0.11F) * 0.6F;
         float _snowmanxxxxxxxxx = 1.0F - _snowmanxxxxxxx * 0.75F;
         _snowmanxxxx = _snowmanxxxx * _snowmanxxxxxxxxx + _snowmanxxxxxxxx * (1.0F - _snowmanxxxxxxxxx);
         _snowmanxxxxx = _snowmanxxxxx * _snowmanxxxxxxxxx + _snowmanxxxxxxxx * (1.0F - _snowmanxxxxxxxxx);
         _snowmanxxxxxx = _snowmanxxxxxx * _snowmanxxxxxxxxx + _snowmanxxxxxxxx * (1.0F - _snowmanxxxxxxxxx);
      }

      float _snowmanxxxxxxxx = this.b(_snowman);
      if (_snowmanxxxxxxxx > 0.0F) {
         float _snowmanxxxxxxxxx = (_snowmanxxxx * 0.3F + _snowmanxxxxx * 0.59F + _snowmanxxxxxx * 0.11F) * 0.2F;
         float _snowmanxxxxxxxxxx = 1.0F - _snowmanxxxxxxxx * 0.75F;
         _snowmanxxxx = _snowmanxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxx * (1.0F - _snowmanxxxxxxxxxx);
         _snowmanxxxxx = _snowmanxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxx * (1.0F - _snowmanxxxxxxxxxx);
         _snowmanxxxxxx = _snowmanxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxx * (1.0F - _snowmanxxxxxxxxxx);
      }

      if (this.C > 0) {
         float _snowmanxxxxxxxxx = (float)this.C - _snowman;
         if (_snowmanxxxxxxxxx > 1.0F) {
            _snowmanxxxxxxxxx = 1.0F;
         }

         _snowmanxxxxxxxxx *= 0.45F;
         _snowmanxxxx = _snowmanxxxx * (1.0F - _snowmanxxxxxxxxx) + 0.8F * _snowmanxxxxxxxxx;
         _snowmanxxxxx = _snowmanxxxxx * (1.0F - _snowmanxxxxxxxxx) + 0.8F * _snowmanxxxxxxxxx;
         _snowmanxxxxxx = _snowmanxxxxxx * (1.0F - _snowmanxxxxxxxxx) + 1.0F * _snowmanxxxxxxxxx;
      }

      return new dcn((double)_snowmanxxxx, (double)_snowmanxxxxx, (double)_snowmanxxxxxx);
   }

   public dcn h(float var1) {
      float _snowman = this.f(_snowman);
      float _snowmanx = afm.b(_snowman * (float) (Math.PI * 2)) * 2.0F + 0.5F;
      _snowmanx = afm.a(_snowmanx, 0.0F, 1.0F);
      float _snowmanxx = 1.0F;
      float _snowmanxxx = 1.0F;
      float _snowmanxxxx = 1.0F;
      float _snowmanxxxxx = this.d(_snowman);
      if (_snowmanxxxxx > 0.0F) {
         float _snowmanxxxxxx = (_snowmanxx * 0.3F + _snowmanxxx * 0.59F + _snowmanxxxx * 0.11F) * 0.6F;
         float _snowmanxxxxxxx = 1.0F - _snowmanxxxxx * 0.95F;
         _snowmanxx = _snowmanxx * _snowmanxxxxxxx + _snowmanxxxxxx * (1.0F - _snowmanxxxxxxx);
         _snowmanxxx = _snowmanxxx * _snowmanxxxxxxx + _snowmanxxxxxx * (1.0F - _snowmanxxxxxxx);
         _snowmanxxxx = _snowmanxxxx * _snowmanxxxxxxx + _snowmanxxxxxx * (1.0F - _snowmanxxxxxxx);
      }

      _snowmanxx *= _snowmanx * 0.9F + 0.1F;
      _snowmanxxx *= _snowmanx * 0.9F + 0.1F;
      _snowmanxxxx *= _snowmanx * 0.85F + 0.15F;
      float _snowmanxxxxxx = this.b(_snowman);
      if (_snowmanxxxxxx > 0.0F) {
         float _snowmanxxxxxxx = (_snowmanxx * 0.3F + _snowmanxxx * 0.59F + _snowmanxxxx * 0.11F) * 0.2F;
         float _snowmanxxxxxxxx = 1.0F - _snowmanxxxxxx * 0.95F;
         _snowmanxx = _snowmanxx * _snowmanxxxxxxxx + _snowmanxxxxxxx * (1.0F - _snowmanxxxxxxxx);
         _snowmanxxx = _snowmanxxx * _snowmanxxxxxxxx + _snowmanxxxxxxx * (1.0F - _snowmanxxxxxxxx);
         _snowmanxxxx = _snowmanxxxx * _snowmanxxxxxxxx + _snowmanxxxxxxx * (1.0F - _snowmanxxxxxxxx);
      }

      return new dcn((double)_snowmanxx, (double)_snowmanxxx, (double)_snowmanxxxx);
   }

   public float i(float var1) {
      float _snowman = this.f(_snowman);
      float _snowmanx = 1.0F - (afm.b(_snowman * (float) (Math.PI * 2)) * 2.0F + 0.25F);
      _snowmanx = afm.a(_snowmanx, 0.0F, 1.0F);
      return _snowmanx * _snowmanx * 0.5F;
   }

   public int s() {
      return this.C;
   }

   @Override
   public void c(int var1) {
      this.C = _snowman;
   }

   @Override
   public float a(gc var1, boolean var2) {
      boolean _snowman = this.a().e();
      if (!_snowman) {
         return _snowman ? 0.9F : 1.0F;
      } else {
         switch (_snowman) {
            case a:
               return _snowman ? 0.9F : 0.5F;
            case b:
               return _snowman ? 0.9F : 1.0F;
            case c:
            case d:
               return 0.8F;
            case e:
            case f:
               return 0.6F;
            default:
               return 1.0F;
         }
      }
   }

   @Override
   public int a(fx var1, ColorResolver var2) {
      dkp _snowman = (dkp)this.D.get(_snowman);
      return _snowman.a(_snowman, () -> this.b(_snowman, _snowman));
   }

   public int b(fx var1, ColorResolver var2) {
      int _snowman = djz.C().k.F;
      if (_snowman == 0) {
         return _snowman.getColor(this.v(_snowman), (double)_snowman.u(), (double)_snowman.w());
      } else {
         int _snowmanx = (_snowman * 2 + 1) * (_snowman * 2 + 1);
         int _snowmanxx = 0;
         int _snowmanxxx = 0;
         int _snowmanxxxx = 0;
         ga _snowmanxxxxx = new ga(_snowman.u() - _snowman, _snowman.v(), _snowman.w() - _snowman, _snowman.u() + _snowman, _snowman.v(), _snowman.w() + _snowman);
         fx.a _snowmanxxxxxx = new fx.a();

         while (_snowmanxxxxx.a()) {
            _snowmanxxxxxx.d(_snowmanxxxxx.b(), _snowmanxxxxx.c(), _snowmanxxxxx.d());
            int _snowmanxxxxxxx = _snowman.getColor(this.v(_snowmanxxxxxx), (double)_snowmanxxxxxx.u(), (double)_snowmanxxxxxx.w());
            _snowmanxx += (_snowmanxxxxxxx & 0xFF0000) >> 16;
            _snowmanxxx += (_snowmanxxxxxxx & 0xFF00) >> 8;
            _snowmanxxxx += _snowmanxxxxxxx & 0xFF;
         }

         return (_snowmanxx / _snowmanx & 0xFF) << 16 | (_snowmanxxx / _snowmanx & 0xFF) << 8 | _snowmanxxxx / _snowmanx & 0xFF;
      }
   }

   public fx u() {
      fx _snowman = new fx(this.u.a(), this.u.b(), this.u.c());
      if (!this.f().a(_snowman)) {
         _snowman = this.a(chn.a.e, new fx(this.f().a(), 0.0, this.f().b()));
      }

      return _snowman;
   }

   public float v() {
      return this.u.d();
   }

   public void b(fx var1, float var2) {
      this.u.a(_snowman, _snowman);
   }

   @Override
   public String toString() {
      return "ClientLevel";
   }

   public dwt.a w() {
      return this.d;
   }

   public static class a implements cyo {
      private final boolean a;
      private final brt b;
      private final boolean c;
      private int d;
      private int e;
      private int f;
      private float g;
      private long h;
      private long i;
      private boolean j;
      private aor k;
      private boolean l;

      public a(aor var1, boolean var2, boolean var3) {
         this.k = _snowman;
         this.a = _snowman;
         this.c = _snowman;
         this.b = new brt();
      }

      @Override
      public int a() {
         return this.d;
      }

      @Override
      public int b() {
         return this.e;
      }

      @Override
      public int c() {
         return this.f;
      }

      @Override
      public float d() {
         return this.g;
      }

      @Override
      public long e() {
         return this.h;
      }

      @Override
      public long f() {
         return this.i;
      }

      @Override
      public void b(int var1) {
         this.d = _snowman;
      }

      @Override
      public void c(int var1) {
         this.e = _snowman;
      }

      @Override
      public void d(int var1) {
         this.f = _snowman;
      }

      @Override
      public void a(float var1) {
         this.g = _snowman;
      }

      public void a(long var1) {
         this.h = _snowman;
      }

      public void b(long var1) {
         this.i = _snowman;
      }

      @Override
      public void a(fx var1, float var2) {
         this.d = _snowman.u();
         this.e = _snowman.v();
         this.f = _snowman.w();
         this.g = _snowman;
      }

      @Override
      public boolean i() {
         return false;
      }

      @Override
      public boolean k() {
         return this.j;
      }

      @Override
      public void b(boolean var1) {
         this.j = _snowman;
      }

      @Override
      public boolean n() {
         return this.a;
      }

      @Override
      public brt q() {
         return this.b;
      }

      @Override
      public aor s() {
         return this.k;
      }

      @Override
      public boolean t() {
         return this.l;
      }

      @Override
      public void a(m var1) {
         cyo.super.a(_snowman);
      }

      public void a(aor var1) {
         this.k = _snowman;
      }

      public void a(boolean var1) {
         this.l = _snowman;
      }

      public double g() {
         return this.c ? 0.0 : 63.0;
      }

      public double h() {
         return this.c ? 1.0 : 0.03125;
      }
   }
}
