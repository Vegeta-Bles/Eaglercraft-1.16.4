import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class brx implements bry, AutoCloseable {
   protected static final Logger e = LogManager.getLogger();
   public static final Codec<vj<brx>> f = vk.a.xmap(vj.b(gm.L), vj::a);
   public static final vj<brx> g = vj.a(gm.L, new vk("overworld"));
   public static final vj<brx> h = vj.a(gm.L, new vk("the_nether"));
   public static final vj<brx> i = vj.a(gm.L, new vk("the_end"));
   private static final gc[] a = gc.values();
   public final List<ccj> j = Lists.newArrayList();
   public final List<ccj> k = Lists.newArrayList();
   protected final List<ccj> l = Lists.newArrayList();
   protected final List<ccj> m = Lists.newArrayList();
   private final Thread b;
   private final boolean c;
   private int d;
   protected int n = new Random().nextInt();
   protected final int o = 1013904223;
   protected float p;
   protected float q;
   protected float r;
   protected float s;
   public final Random t = new Random();
   private final chd x;
   protected final cyo u;
   private final Supplier<anw> y;
   public final boolean v;
   protected boolean w;
   private final cfu z;
   private final bsx A;
   private final vj<brx> B;

   protected brx(cyo var1, vj<brx> var2, final chd var3, Supplier<anw> var4, boolean var5, boolean var6, long var7) {
      this.y = _snowman;
      this.u = _snowman;
      this.x = _snowman;
      this.B = _snowman;
      this.v = _snowman;
      if (_snowman.f() != 1.0) {
         this.z = new cfu() {
            @Override
            public double a() {
               return super.a() / _snowman.f();
            }

            @Override
            public double b() {
               return super.b() / _snowman.f();
            }
         };
      } else {
         this.z = new cfu();
      }

      this.b = Thread.currentThread();
      this.A = new bsx(this, _snowman, _snowman.m());
      this.c = _snowman;
   }

   @Override
   public boolean s_() {
      return this.v;
   }

   @Nullable
   public MinecraftServer l() {
      return null;
   }

   public static boolean k(fx var0) {
      return !m(_snowman) && D(_snowman);
   }

   public static boolean l(fx var0) {
      return !d(_snowman.v()) && D(_snowman);
   }

   private static boolean D(fx var0) {
      return _snowman.u() >= -30000000 && _snowman.w() >= -30000000 && _snowman.u() < 30000000 && _snowman.w() < 30000000;
   }

   private static boolean d(int var0) {
      return _snowman < -20000000 || _snowman >= 20000000;
   }

   public static boolean m(fx var0) {
      return b(_snowman.v());
   }

   public static boolean b(int var0) {
      return _snowman < 0 || _snowman >= 256;
   }

   public cgh n(fx var1) {
      return this.d(_snowman.u() >> 4, _snowman.w() >> 4);
   }

   public cgh d(int var1, int var2) {
      return (cgh)this.a(_snowman, _snowman, cga.m);
   }

   @Override
   public cfw a(int var1, int var2, cga var3, boolean var4) {
      cfw _snowman = this.H().a(_snowman, _snowman, _snowman, _snowman);
      if (_snowman == null && _snowman) {
         throw new IllegalStateException("Should always be able to create a chunk!");
      } else {
         return _snowman;
      }
   }

   @Override
   public boolean a(fx var1, ceh var2, int var3) {
      return this.a(_snowman, _snowman, _snowman, 512);
   }

   @Override
   public boolean a(fx var1, ceh var2, int var3, int var4) {
      if (m(_snowman)) {
         return false;
      } else if (!this.v && this.ab()) {
         return false;
      } else {
         cgh _snowman = this.n(_snowman);
         buo _snowmanx = _snowman.b();
         ceh _snowmanxx = _snowman.a(_snowman, _snowman, (_snowman & 64) != 0);
         if (_snowmanxx == null) {
            return false;
         } else {
            ceh _snowmanxxx = this.d_(_snowman);
            if ((_snowman & 128) == 0 && _snowmanxxx != _snowmanxx && (_snowmanxxx.b((brc)this, _snowman) != _snowmanxx.b((brc)this, _snowman) || _snowmanxxx.f() != _snowmanxx.f() || _snowmanxxx.e() || _snowmanxx.e())) {
               this.Z().a("queueCheckLight");
               this.H().l().a(_snowman);
               this.Z().c();
            }

            if (_snowmanxxx == _snowman) {
               if (_snowmanxx != _snowmanxxx) {
                  this.b(_snowman, _snowmanxx, _snowmanxxx);
               }

               if ((_snowman & 2) != 0 && (!this.v || (_snowman & 4) == 0) && (this.v || _snowman.u() != null && _snowman.u().a(zr.b.c))) {
                  this.a(_snowman, _snowmanxx, _snowman, _snowman);
               }

               if ((_snowman & 1) != 0) {
                  this.a(_snowman, _snowmanxx.b());
                  if (!this.v && _snowman.j()) {
                     this.c(_snowman, _snowmanx);
                  }
               }

               if ((_snowman & 16) == 0 && _snowman > 0) {
                  int _snowmanxxxx = _snowman & -34;
                  _snowmanxx.b(this, _snowman, _snowmanxxxx, _snowman - 1);
                  _snowman.a((bry)this, _snowman, _snowmanxxxx, _snowman - 1);
                  _snowman.b(this, _snowman, _snowmanxxxx, _snowman - 1);
               }

               this.a(_snowman, _snowmanxx, _snowmanxxx);
            }

            return true;
         }
      }
   }

   public void a(fx var1, ceh var2, ceh var3) {
   }

   @Override
   public boolean a(fx var1, boolean var2) {
      cux _snowman = this.b(_snowman);
      return this.a(_snowman, _snowman.g(), 3 | (_snowman ? 64 : 0));
   }

   @Override
   public boolean a(fx var1, boolean var2, @Nullable aqa var3, int var4) {
      ceh _snowman = this.d_(_snowman);
      if (_snowman.g()) {
         return false;
      } else {
         cux _snowmanx = this.b(_snowman);
         if (!(_snowman.b() instanceof bue)) {
            this.c(2001, _snowman, buo.i(_snowman));
         }

         if (_snowman) {
            ccj _snowmanxx = _snowman.b().q() ? this.c(_snowman) : null;
            buo.a(_snowman, this, _snowman, _snowmanxx, _snowman, bmb.b);
         }

         return this.a(_snowman, _snowmanx.g(), 3, _snowman);
      }
   }

   public boolean a(fx var1, ceh var2) {
      return this.a(_snowman, _snowman, 3);
   }

   public abstract void a(fx var1, ceh var2, ceh var3, int var4);

   public void b(fx var1, ceh var2, ceh var3) {
   }

   public void b(fx var1, buo var2) {
      this.a(_snowman.f(), _snowman, _snowman);
      this.a(_snowman.g(), _snowman, _snowman);
      this.a(_snowman.c(), _snowman, _snowman);
      this.a(_snowman.b(), _snowman, _snowman);
      this.a(_snowman.d(), _snowman, _snowman);
      this.a(_snowman.e(), _snowman, _snowman);
   }

   public void a(fx var1, buo var2, gc var3) {
      if (_snowman != gc.e) {
         this.a(_snowman.f(), _snowman, _snowman);
      }

      if (_snowman != gc.f) {
         this.a(_snowman.g(), _snowman, _snowman);
      }

      if (_snowman != gc.a) {
         this.a(_snowman.c(), _snowman, _snowman);
      }

      if (_snowman != gc.b) {
         this.a(_snowman.b(), _snowman, _snowman);
      }

      if (_snowman != gc.c) {
         this.a(_snowman.d(), _snowman, _snowman);
      }

      if (_snowman != gc.d) {
         this.a(_snowman.e(), _snowman, _snowman);
      }
   }

   public void a(fx var1, buo var2, fx var3) {
      if (!this.v) {
         ceh _snowman = this.d_(_snowman);

         try {
            _snowman.a(this, _snowman, _snowman, _snowman, false);
         } catch (Throwable var8) {
            l _snowmanx = l.a(var8, "Exception while updating neighbours");
            m _snowmanxx = _snowmanx.a("Block being updated");
            _snowmanxx.a("Source block type", () -> {
               try {
                  return String.format("ID #%s (%s // %s)", gm.Q.b(_snowman), _snowman.i(), _snowman.getClass().getCanonicalName());
               } catch (Throwable var2x) {
                  return "ID #" + gm.Q.b(_snowman);
               }
            });
            m.a(_snowmanxx, _snowman, _snowman);
            throw new u(_snowmanx);
         }
      }
   }

   @Override
   public int a(chn.a var1, int var2, int var3) {
      int _snowman;
      if (_snowman >= -30000000 && _snowman >= -30000000 && _snowman < 30000000 && _snowman < 30000000) {
         if (this.b(_snowman >> 4, _snowman >> 4)) {
            _snowman = this.d(_snowman >> 4, _snowman >> 4).a(_snowman, _snowman & 15, _snowman & 15) + 1;
         } else {
            _snowman = 0;
         }
      } else {
         _snowman = this.t_() + 1;
      }

      return _snowman;
   }

   @Override
   public cuo e() {
      return this.H().l();
   }

   @Override
   public ceh d_(fx var1) {
      if (m(_snowman)) {
         return bup.la.n();
      } else {
         cgh _snowman = this.d(_snowman.u() >> 4, _snowman.w() >> 4);
         return _snowman.d_(_snowman);
      }
   }

   @Override
   public cux b(fx var1) {
      if (m(_snowman)) {
         return cuy.a.h();
      } else {
         cgh _snowman = this.n(_snowman);
         return _snowman.b(_snowman);
      }
   }

   public boolean M() {
      return !this.k().n() && this.d < 4;
   }

   public boolean N() {
      return !this.k().n() && !this.M();
   }

   @Override
   public void a(@Nullable bfw var1, fx var2, adp var3, adr var4, float var5, float var6) {
      this.a(_snowman, (double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, _snowman, _snowman, _snowman, _snowman);
   }

   public abstract void a(@Nullable bfw var1, double var2, double var4, double var6, adp var8, adr var9, float var10, float var11);

   public abstract void a(@Nullable bfw var1, aqa var2, adp var3, adr var4, float var5, float var6);

   public void a(double var1, double var3, double var5, adp var7, adr var8, float var9, float var10, boolean var11) {
   }

   @Override
   public void a(hf var1, double var2, double var4, double var6, double var8, double var10, double var12) {
   }

   public void a(hf var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
   }

   public void b(hf var1, double var2, double var4, double var6, double var8, double var10, double var12) {
   }

   public void b(hf var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
   }

   public float a(float var1) {
      float _snowman = this.f(_snowman);
      return _snowman * (float) (Math.PI * 2);
   }

   public boolean a(ccj var1) {
      if (this.w) {
         e.error("Adding block entity while ticking: {} @ {}", new org.apache.logging.log4j.util.Supplier[]{() -> gm.W.b(_snowman.u()), _snowman::o});
      }

      boolean _snowman = this.j.add(_snowman);
      if (_snowman && _snowman instanceof cdm) {
         this.k.add(_snowman);
      }

      if (this.v) {
         fx _snowmanx = _snowman.o();
         ceh _snowmanxx = this.d_(_snowmanx);
         this.a(_snowmanx, _snowmanxx, _snowmanxx, 2);
      }

      return _snowman;
   }

   public void a(Collection<ccj> var1) {
      if (this.w) {
         this.l.addAll(_snowman);
      } else {
         for (ccj _snowman : _snowman) {
            this.a(_snowman);
         }
      }
   }

   public void O() {
      anw _snowman = this.Z();
      _snowman.a("blockEntities");
      if (!this.m.isEmpty()) {
         this.k.removeAll(this.m);
         this.j.removeAll(this.m);
         this.m.clear();
      }

      this.w = true;
      Iterator<ccj> _snowmanx = this.k.iterator();

      while (_snowmanx.hasNext()) {
         ccj _snowmanxx = _snowmanx.next();
         if (!_snowmanxx.q() && _snowmanxx.n()) {
            fx _snowmanxxx = _snowmanxx.o();
            if (this.H().a(_snowmanxxx) && this.f().a(_snowmanxxx)) {
               try {
                  _snowman.a(() -> String.valueOf(cck.a(_snowman.u())));
                  if (_snowmanxx.u().a(this.d_(_snowmanxxx).b())) {
                     ((cdm)_snowmanxx).aj_();
                  } else {
                     _snowmanxx.w();
                  }

                  _snowman.c();
               } catch (Throwable var8) {
                  l _snowmanxxxx = l.a(var8, "Ticking block entity");
                  m _snowmanxxxxx = _snowmanxxxx.a("Block entity being ticked");
                  _snowmanxx.a(_snowmanxxxxx);
                  throw new u(_snowmanxxxx);
               }
            }
         }

         if (_snowmanxx.q()) {
            _snowmanx.remove();
            this.j.remove(_snowmanxx);
            if (this.C(_snowmanxx.o())) {
               this.n(_snowmanxx.o()).d(_snowmanxx.o());
            }
         }
      }

      this.w = false;
      _snowman.b("pendingBlockEntities");
      if (!this.l.isEmpty()) {
         for (int _snowmanxxx = 0; _snowmanxxx < this.l.size(); _snowmanxxx++) {
            ccj _snowmanxxxx = this.l.get(_snowmanxxx);
            if (!_snowmanxxxx.q()) {
               if (!this.j.contains(_snowmanxxxx)) {
                  this.a(_snowmanxxxx);
               }

               if (this.C(_snowmanxxxx.o())) {
                  cgh _snowmanxxxxx = this.n(_snowmanxxxx.o());
                  ceh _snowmanxxxxxx = _snowmanxxxxx.d_(_snowmanxxxx.o());
                  _snowmanxxxxx.a(_snowmanxxxx.o(), _snowmanxxxx);
                  this.a(_snowmanxxxx.o(), _snowmanxxxxxx, _snowmanxxxxxx, 3);
               }
            }
         }

         this.l.clear();
      }

      _snowman.c();
   }

   public void a(Consumer<aqa> var1, aqa var2) {
      try {
         _snowman.accept(_snowman);
      } catch (Throwable var6) {
         l _snowman = l.a(var6, "Ticking entity");
         m _snowmanx = _snowman.a("Entity being ticked");
         _snowman.a(_snowmanx);
         throw new u(_snowman);
      }
   }

   public brp a(@Nullable aqa var1, double var2, double var4, double var6, float var8, brp.a var9) {
      return this.a(_snowman, null, null, _snowman, _snowman, _snowman, _snowman, false, _snowman);
   }

   public brp a(@Nullable aqa var1, double var2, double var4, double var6, float var8, boolean var9, brp.a var10) {
      return this.a(_snowman, null, null, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public brp a(@Nullable aqa var1, @Nullable apk var2, @Nullable brq var3, double var4, double var6, double var8, float var10, boolean var11, brp.a var12) {
      brp _snowman = new brp(this, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.a();
      _snowman.a(true);
      return _snowman;
   }

   public String P() {
      return this.H().e();
   }

   @Nullable
   @Override
   public ccj c(fx var1) {
      if (m(_snowman)) {
         return null;
      } else if (!this.v && Thread.currentThread() != this.b) {
         return null;
      } else {
         ccj _snowman = null;
         if (this.w) {
            _snowman = this.E(_snowman);
         }

         if (_snowman == null) {
            _snowman = this.n(_snowman).a(_snowman, cgh.a.a);
         }

         if (_snowman == null) {
            _snowman = this.E(_snowman);
         }

         return _snowman;
      }
   }

   @Nullable
   private ccj E(fx var1) {
      for (int _snowman = 0; _snowman < this.l.size(); _snowman++) {
         ccj _snowmanx = this.l.get(_snowman);
         if (!_snowmanx.q() && _snowmanx.o().equals(_snowman)) {
            return _snowmanx;
         }
      }

      return null;
   }

   public void a(fx var1, @Nullable ccj var2) {
      if (!m(_snowman)) {
         if (_snowman != null && !_snowman.q()) {
            if (this.w) {
               _snowman.a(this, _snowman);
               Iterator<ccj> _snowman = this.l.iterator();

               while (_snowman.hasNext()) {
                  ccj _snowmanx = _snowman.next();
                  if (_snowmanx.o().equals(_snowman)) {
                     _snowmanx.al_();
                     _snowman.remove();
                  }
               }

               this.l.add(_snowman);
            } else {
               this.n(_snowman).a(_snowman, _snowman);
               this.a(_snowman);
            }
         }
      }
   }

   public void o(fx var1) {
      ccj _snowman = this.c(_snowman);
      if (_snowman != null && this.w) {
         _snowman.al_();
         this.l.remove(_snowman);
      } else {
         if (_snowman != null) {
            this.l.remove(_snowman);
            this.j.remove(_snowman);
            this.k.remove(_snowman);
         }

         this.n(_snowman).d(_snowman);
      }
   }

   public boolean p(fx var1) {
      return m(_snowman) ? false : this.H().b(_snowman.u() >> 4, _snowman.w() >> 4);
   }

   public boolean a(fx var1, aqa var2, gc var3) {
      if (m(_snowman)) {
         return false;
      } else {
         cfw _snowman = this.a(_snowman.u() >> 4, _snowman.w() >> 4, cga.m, false);
         return _snowman == null ? false : _snowman.d_(_snowman).a(this, _snowman, _snowman, _snowman);
      }
   }

   public boolean a(fx var1, aqa var2) {
      return this.a(_snowman, _snowman, gc.b);
   }

   public void Q() {
      double _snowman = 1.0 - (double)(this.d(1.0F) * 5.0F) / 16.0;
      double _snowmanx = 1.0 - (double)(this.b(1.0F) * 5.0F) / 16.0;
      double _snowmanxx = 0.5 + 2.0 * afm.a((double)afm.b(this.f(1.0F) * (float) (Math.PI * 2)), -0.25, 0.25);
      this.d = (int)((1.0 - _snowmanxx * _snowman * _snowmanx) * 11.0);
   }

   public void b(boolean var1, boolean var2) {
      this.H().a(_snowman, _snowman);
   }

   protected void R() {
      if (this.u.k()) {
         this.q = 1.0F;
         if (this.u.i()) {
            this.s = 1.0F;
         }
      }
   }

   @Override
   public void close() throws IOException {
      this.H().close();
   }

   @Nullable
   @Override
   public brc c(int var1, int var2) {
      return this.a(_snowman, _snowman, cga.m, false);
   }

   @Override
   public List<aqa> a(@Nullable aqa var1, dci var2, @Nullable Predicate<? super aqa> var3) {
      this.Z().c("getEntities");
      List<aqa> _snowman = Lists.newArrayList();
      int _snowmanx = afm.c((_snowman.a - 2.0) / 16.0);
      int _snowmanxx = afm.c((_snowman.d + 2.0) / 16.0);
      int _snowmanxxx = afm.c((_snowman.c - 2.0) / 16.0);
      int _snowmanxxxx = afm.c((_snowman.f + 2.0) / 16.0);
      cfz _snowmanxxxxx = this.H();

      for (int _snowmanxxxxxx = _snowmanx; _snowmanxxxxxx <= _snowmanxx; _snowmanxxxxxx++) {
         for (int _snowmanxxxxxxx = _snowmanxxx; _snowmanxxxxxxx <= _snowmanxxxx; _snowmanxxxxxxx++) {
            cgh _snowmanxxxxxxxx = _snowmanxxxxx.a(_snowmanxxxxxx, _snowmanxxxxxxx, false);
            if (_snowmanxxxxxxxx != null) {
               _snowmanxxxxxxxx.a(_snowman, _snowman, _snowman, _snowman);
            }
         }
      }

      return _snowman;
   }

   public <T extends aqa> List<T> a(@Nullable aqe<T> var1, dci var2, Predicate<? super T> var3) {
      this.Z().c("getEntities");
      int _snowman = afm.c((_snowman.a - 2.0) / 16.0);
      int _snowmanx = afm.f((_snowman.d + 2.0) / 16.0);
      int _snowmanxx = afm.c((_snowman.c - 2.0) / 16.0);
      int _snowmanxxx = afm.f((_snowman.f + 2.0) / 16.0);
      List<T> _snowmanxxxx = Lists.newArrayList();

      for (int _snowmanxxxxx = _snowman; _snowmanxxxxx < _snowmanx; _snowmanxxxxx++) {
         for (int _snowmanxxxxxx = _snowmanxx; _snowmanxxxxxx < _snowmanxxx; _snowmanxxxxxx++) {
            cgh _snowmanxxxxxxx = this.H().a(_snowmanxxxxx, _snowmanxxxxxx, false);
            if (_snowmanxxxxxxx != null) {
               _snowmanxxxxxxx.a(_snowman, _snowman, _snowmanxxxx, _snowman);
            }
         }
      }

      return _snowmanxxxx;
   }

   @Override
   public <T extends aqa> List<T> a(Class<? extends T> var1, dci var2, @Nullable Predicate<? super T> var3) {
      this.Z().c("getEntities");
      int _snowman = afm.c((_snowman.a - 2.0) / 16.0);
      int _snowmanx = afm.f((_snowman.d + 2.0) / 16.0);
      int _snowmanxx = afm.c((_snowman.c - 2.0) / 16.0);
      int _snowmanxxx = afm.f((_snowman.f + 2.0) / 16.0);
      List<T> _snowmanxxxx = Lists.newArrayList();
      cfz _snowmanxxxxx = this.H();

      for (int _snowmanxxxxxx = _snowman; _snowmanxxxxxx < _snowmanx; _snowmanxxxxxx++) {
         for (int _snowmanxxxxxxx = _snowmanxx; _snowmanxxxxxxx < _snowmanxxx; _snowmanxxxxxxx++) {
            cgh _snowmanxxxxxxxx = _snowmanxxxxx.a(_snowmanxxxxxx, _snowmanxxxxxxx, false);
            if (_snowmanxxxxxxxx != null) {
               _snowmanxxxxxxxx.a(_snowman, _snowman, _snowmanxxxx, _snowman);
            }
         }
      }

      return _snowmanxxxx;
   }

   @Override
   public <T extends aqa> List<T> b(Class<? extends T> var1, dci var2, @Nullable Predicate<? super T> var3) {
      this.Z().c("getLoadedEntities");
      int _snowman = afm.c((_snowman.a - 2.0) / 16.0);
      int _snowmanx = afm.f((_snowman.d + 2.0) / 16.0);
      int _snowmanxx = afm.c((_snowman.c - 2.0) / 16.0);
      int _snowmanxxx = afm.f((_snowman.f + 2.0) / 16.0);
      List<T> _snowmanxxxx = Lists.newArrayList();
      cfz _snowmanxxxxx = this.H();

      for (int _snowmanxxxxxx = _snowman; _snowmanxxxxxx < _snowmanx; _snowmanxxxxxx++) {
         for (int _snowmanxxxxxxx = _snowmanxx; _snowmanxxxxxxx < _snowmanxxx; _snowmanxxxxxxx++) {
            cgh _snowmanxxxxxxxx = _snowmanxxxxx.a(_snowmanxxxxxx, _snowmanxxxxxxx);
            if (_snowmanxxxxxxxx != null) {
               _snowmanxxxxxxxx.a(_snowman, _snowman, _snowmanxxxx, _snowman);
            }
         }
      }

      return _snowmanxxxx;
   }

   @Nullable
   public abstract aqa a(int var1);

   public void b(fx var1, ccj var2) {
      if (this.C(_snowman)) {
         this.n(_snowman).s();
      }
   }

   @Override
   public int t_() {
      return 63;
   }

   public int q(fx var1) {
      int _snowman = 0;
      _snowman = Math.max(_snowman, this.c(_snowman.c(), gc.a));
      if (_snowman >= 15) {
         return _snowman;
      } else {
         _snowman = Math.max(_snowman, this.c(_snowman.b(), gc.b));
         if (_snowman >= 15) {
            return _snowman;
         } else {
            _snowman = Math.max(_snowman, this.c(_snowman.d(), gc.c));
            if (_snowman >= 15) {
               return _snowman;
            } else {
               _snowman = Math.max(_snowman, this.c(_snowman.e(), gc.d));
               if (_snowman >= 15) {
                  return _snowman;
               } else {
                  _snowman = Math.max(_snowman, this.c(_snowman.f(), gc.e));
                  if (_snowman >= 15) {
                     return _snowman;
                  } else {
                     _snowman = Math.max(_snowman, this.c(_snowman.g(), gc.f));
                     return _snowman >= 15 ? _snowman : _snowman;
                  }
               }
            }
         }
      }
   }

   public boolean a(fx var1, gc var2) {
      return this.b(_snowman, _snowman) > 0;
   }

   public int b(fx var1, gc var2) {
      ceh _snowman = this.d_(_snowman);
      int _snowmanx = _snowman.b(this, _snowman, _snowman);
      return _snowman.g(this, _snowman) ? Math.max(_snowmanx, this.q(_snowman)) : _snowmanx;
   }

   public boolean r(fx var1) {
      if (this.b(_snowman.c(), gc.a) > 0) {
         return true;
      } else if (this.b(_snowman.b(), gc.b) > 0) {
         return true;
      } else if (this.b(_snowman.d(), gc.c) > 0) {
         return true;
      } else if (this.b(_snowman.e(), gc.d) > 0) {
         return true;
      } else {
         return this.b(_snowman.f(), gc.e) > 0 ? true : this.b(_snowman.g(), gc.f) > 0;
      }
   }

   public int s(fx var1) {
      int _snowman = 0;

      for (gc _snowmanx : a) {
         int _snowmanxx = this.b(_snowman.a(_snowmanx), _snowmanx);
         if (_snowmanxx >= 15) {
            return 15;
         }

         if (_snowmanxx > _snowman) {
            _snowman = _snowmanxx;
         }
      }

      return _snowman;
   }

   public void S() {
   }

   public long T() {
      return this.u.e();
   }

   public long U() {
      return this.u.f();
   }

   public boolean a(bfw var1, fx var2) {
      return true;
   }

   public void a(aqa var1, byte var2) {
   }

   public void a(fx var1, buo var2, int var3, int var4) {
      this.d_(_snowman).a(this, _snowman, _snowman, _snowman);
   }

   @Override
   public cyd h() {
      return this.u;
   }

   public brt V() {
      return this.u.q();
   }

   public float b(float var1) {
      return afm.g(_snowman, this.r, this.s) * this.d(_snowman);
   }

   public void c(float var1) {
      this.r = _snowman;
      this.s = _snowman;
   }

   public float d(float var1) {
      return afm.g(_snowman, this.p, this.q);
   }

   public void e(float var1) {
      this.p = _snowman;
      this.q = _snowman;
   }

   public boolean W() {
      return this.k().b() && !this.k().c() ? (double)this.b(1.0F) > 0.9 : false;
   }

   public boolean X() {
      return (double)this.d(1.0F) > 0.2;
   }

   public boolean t(fx var1) {
      if (!this.X()) {
         return false;
      } else if (!this.e(_snowman)) {
         return false;
      } else if (this.a(chn.a.e, _snowman).v() > _snowman.v()) {
         return false;
      } else {
         bsv _snowman = this.v(_snowman);
         return _snowman.c() == bsv.e.b && _snowman.a(_snowman) >= 0.15F;
      }
   }

   public boolean u(fx var1) {
      bsv _snowman = this.v(_snowman);
      return _snowman.d();
   }

   @Nullable
   public abstract cxx a(String var1);

   public abstract void a(cxx var1);

   public abstract int t();

   public void b(int var1, fx var2, int var3) {
   }

   public m a(l var1) {
      m _snowman = _snowman.a("Affected level", 1);
      _snowman.a("All players", () -> this.x().size() + " total; " + this.x());
      _snowman.a("Chunk stats", this.H()::e);
      _snowman.a("Level dimension", () -> this.Y().a().toString());

      try {
         this.u.a(_snowman);
      } catch (Throwable var4) {
         _snowman.a("Level Data Unobtainable", var4);
      }

      return _snowman;
   }

   public abstract void a(int var1, fx var2, int var3);

   public void a(double var1, double var3, double var5, double var7, double var9, double var11, @Nullable md var13) {
   }

   public abstract ddn G();

   public void c(fx var1, buo var2) {
      for (gc _snowman : gc.c.a) {
         fx _snowmanx = _snowman.a(_snowman);
         if (this.C(_snowmanx)) {
            ceh _snowmanxx = this.d_(_snowmanx);
            if (_snowmanxx.a(bup.fu)) {
               _snowmanxx.a(this, _snowmanx, _snowman, _snowman, false);
            } else if (_snowmanxx.g(this, _snowmanx)) {
               _snowmanx = _snowmanx.a(_snowman);
               _snowmanxx = this.d_(_snowmanx);
               if (_snowmanxx.a(bup.fu)) {
                  _snowmanxx.a(this, _snowmanx, _snowman, _snowman, false);
               }
            }
         }
      }
   }

   @Override
   public aos d(fx var1) {
      long _snowman = 0L;
      float _snowmanx = 0.0F;
      if (this.C(_snowman)) {
         _snowmanx = this.af();
         _snowman = this.n(_snowman).q();
      }

      return new aos(this.ad(), this.U(), _snowman, _snowmanx);
   }

   @Override
   public int c() {
      return this.d;
   }

   public void c(int var1) {
   }

   @Override
   public cfu f() {
      return this.z;
   }

   public void a(oj<?> var1) {
      throw new UnsupportedOperationException("Can't send packets to server unless you're on the client.");
   }

   @Override
   public chd k() {
      return this.x;
   }

   public vj<brx> Y() {
      return this.B;
   }

   @Override
   public Random u_() {
      return this.t;
   }

   @Override
   public boolean a(fx var1, Predicate<ceh> var2) {
      return _snowman.test(this.d_(_snowman));
   }

   public abstract bor o();

   public abstract aen p();

   public fx a(int var1, int var2, int var3, int var4) {
      this.n = this.n * 3 + 1013904223;
      int _snowman = this.n >> 2;
      return new fx(_snowman + (_snowman & 15), _snowman + (_snowman >> 16 & _snowman), _snowman + (_snowman >> 8 & 15));
   }

   public boolean q() {
      return false;
   }

   public anw Z() {
      return this.y.get();
   }

   public Supplier<anw> aa() {
      return this.y;
   }

   @Override
   public bsx d() {
      return this.A;
   }

   public final boolean ab() {
      return this.c;
   }
}
