import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class buo extends ceg implements brw {
   protected static final Logger l = LogManager.getLogger();
   public static final gh<ceh> m = new gh<>();
   private static final LoadingCache<ddh, Boolean> a = CacheBuilder.newBuilder().maximumSize(512L).weakKeys().build(new CacheLoader<ddh, Boolean>() {
      public Boolean a(ddh var1) {
         return !dde.c(dde.b(), _snowman, dcr.g);
      }
   });
   protected final cei<buo, ceh> n;
   private ceh b;
   @Nullable
   private String c;
   @Nullable
   private blx d;
   private static final ThreadLocal<Object2ByteLinkedOpenHashMap<buo.a>> e = ThreadLocal.withInitial(() -> {
      Object2ByteLinkedOpenHashMap<buo.a> _snowman = new Object2ByteLinkedOpenHashMap<buo.a>(2048, 0.25F) {
         protected void rehash(int var1) {
         }
      };
      _snowman.defaultReturnValue((byte)127);
      return _snowman;
   });

   public static int i(@Nullable ceh var0) {
      if (_snowman == null) {
         return 0;
      } else {
         int _snowman = m.a(_snowman);
         return _snowman == -1 ? 0 : _snowman;
      }
   }

   public static ceh a(int var0) {
      ceh _snowman = m.a(_snowman);
      return _snowman == null ? bup.a.n() : _snowman;
   }

   public static buo a(@Nullable blx var0) {
      return _snowman instanceof bkh ? ((bkh)_snowman).e() : bup.a;
   }

   public static ceh a(ceh var0, ceh var1, brx var2, fx var3) {
      ddh _snowman = dde.b(_snowman.k(_snowman, _snowman), _snowman.k(_snowman, _snowman), dcr.c).a((double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w());

      for (aqa _snowmanx : _snowman.a(null, _snowman.a())) {
         double _snowmanxx = dde.a(gc.a.b, _snowmanx.cc().d(0.0, 1.0, 0.0), Stream.of(_snowman), -1.0);
         _snowmanx.a(_snowmanx.cD(), _snowmanx.cE() + 1.0 + _snowmanxx, _snowmanx.cH());
      }

      return _snowman;
   }

   public static ddh a(double var0, double var2, double var4, double var6, double var8, double var10) {
      return dde.a(_snowman / 16.0, _snowman / 16.0, _snowman / 16.0, _snowman / 16.0, _snowman / 16.0, _snowman / 16.0);
   }

   public boolean a(ael<buo> var1) {
      return _snowman.a(this);
   }

   public boolean a(buo var1) {
      return this == _snowman;
   }

   public static ceh b(ceh var0, bry var1, fx var2) {
      ceh _snowman = _snowman;
      fx.a _snowmanx = new fx.a();

      for (gc _snowmanxx : ar) {
         _snowmanx.a(_snowman, _snowmanxx);
         _snowman = _snowman.a(_snowmanxx, _snowman.d_(_snowmanx), _snowman, _snowman, _snowmanx);
      }

      return _snowman;
   }

   public static void a(ceh var0, ceh var1, bry var2, fx var3, int var4) {
      a(_snowman, _snowman, _snowman, _snowman, _snowman, 512);
   }

   public static void a(ceh var0, ceh var1, bry var2, fx var3, int var4, int var5) {
      if (_snowman != _snowman) {
         if (_snowman.g()) {
            if (!_snowman.s_()) {
               _snowman.a(_snowman, (_snowman & 32) == 0, null, _snowman);
            }
         } else {
            _snowman.a(_snowman, _snowman, _snowman & -33, _snowman);
         }
      }
   }

   public buo(ceg.c var1) {
      super(_snowman);
      cei.a<buo, ceh> _snowman = new cei.a<>(this);
      this.a(_snowman);
      this.n = _snowman.a(buo::n, ceh::new);
      this.j(this.n.b());
   }

   public static boolean b(buo var0) {
      return _snowman instanceof bxx || _snowman == bup.go || _snowman == bup.cU || _snowman == bup.cV || _snowman == bup.dK || _snowman == bup.cK || _snowman.a(aed.au);
   }

   public boolean a_(ceh var1) {
      return this.av;
   }

   public static boolean c(ceh var0, brc var1, fx var2, gc var3) {
      fx _snowman = _snowman.a(_snowman);
      ceh _snowmanx = _snowman.d_(_snowman);
      if (_snowman.a(_snowmanx, _snowman)) {
         return false;
      } else if (_snowmanx.l()) {
         buo.a _snowmanxx = new buo.a(_snowman, _snowmanx, _snowman);
         Object2ByteLinkedOpenHashMap<buo.a> _snowmanxxx = e.get();
         byte _snowmanxxxx = _snowmanxxx.getAndMoveToFirst(_snowmanxx);
         if (_snowmanxxxx != 127) {
            return _snowmanxxxx != 0;
         } else {
            ddh _snowmanxxxxx = _snowman.a(_snowman, _snowman, _snowman);
            ddh _snowmanxxxxxx = _snowmanx.a(_snowman, _snowman, _snowman.f());
            boolean _snowmanxxxxxxx = dde.c(_snowmanxxxxx, _snowmanxxxxxx, dcr.e);
            if (_snowmanxxx.size() == 2048) {
               _snowmanxxx.removeLastByte();
            }

            _snowmanxxx.putAndMoveToFirst(_snowmanxx, (byte)(_snowmanxxxxxxx ? 1 : 0));
            return _snowmanxxxxxxx;
         }
      } else {
         return true;
      }
   }

   public static boolean c(brc var0, fx var1) {
      return _snowman.d_(_snowman).a(_snowman, _snowman, gc.b, cat.c);
   }

   public static boolean a(brz var0, fx var1, gc var2) {
      ceh _snowman = _snowman.d_(_snowman);
      return _snowman == gc.a && _snowman.a(aed.aC) ? false : _snowman.a(_snowman, _snowman, _snowman, cat.b);
   }

   public static boolean a(ddh var0, gc var1) {
      ddh _snowman = _snowman.a(_snowman);
      return a(_snowman);
   }

   public static boolean a(ddh var0) {
      return (Boolean)a.getUnchecked(_snowman);
   }

   public boolean b(ceh var1, brc var2, fx var3) {
      return !a(_snowman.j(_snowman, _snowman)) && _snowman.m().c();
   }

   public void a(ceh var1, brx var2, fx var3, Random var4) {
   }

   public void a(bry var1, fx var2, ceh var3) {
   }

   public static List<bmb> a(ceh var0, aag var1, fx var2, @Nullable ccj var3) {
      cyv.a _snowman = new cyv.a(_snowman).a(_snowman.t).a(dbc.f, dcn.a(_snowman)).a(dbc.i, bmb.b).b(dbc.h, _snowman);
      return _snowman.a(_snowman);
   }

   public static List<bmb> a(ceh var0, aag var1, fx var2, @Nullable ccj var3, @Nullable aqa var4, bmb var5) {
      cyv.a _snowman = new cyv.a(_snowman).a(_snowman.t).a(dbc.f, dcn.a(_snowman)).a(dbc.i, _snowman).b(dbc.a, _snowman).b(dbc.h, _snowman);
      return _snowman.a(_snowman);
   }

   public static void c(ceh var0, brx var1, fx var2) {
      if (_snowman instanceof aag) {
         a(_snowman, (aag)_snowman, _snowman, null).forEach(var2x -> a(_snowman, _snowman, var2x));
         _snowman.a((aag)_snowman, _snowman, bmb.b);
      }
   }

   public static void a(ceh var0, bry var1, fx var2, @Nullable ccj var3) {
      if (_snowman instanceof aag) {
         a(_snowman, (aag)_snowman, _snowman, _snowman).forEach(var2x -> a((brx)((aag)_snowman), _snowman, var2x));
         _snowman.a((aag)_snowman, _snowman, bmb.b);
      }
   }

   public static void a(ceh var0, brx var1, fx var2, @Nullable ccj var3, aqa var4, bmb var5) {
      if (_snowman instanceof aag) {
         a(_snowman, (aag)_snowman, _snowman, _snowman, _snowman, _snowman).forEach(var2x -> a(_snowman, _snowman, var2x));
         _snowman.a((aag)_snowman, _snowman, _snowman);
      }
   }

   public static void a(brx var0, fx var1, bmb var2) {
      if (!_snowman.v && !_snowman.a() && _snowman.V().b(brt.f)) {
         float _snowman = 0.5F;
         double _snowmanx = (double)(_snowman.t.nextFloat() * 0.5F) + 0.25;
         double _snowmanxx = (double)(_snowman.t.nextFloat() * 0.5F) + 0.25;
         double _snowmanxxx = (double)(_snowman.t.nextFloat() * 0.5F) + 0.25;
         bcv _snowmanxxxx = new bcv(_snowman, (double)_snowman.u() + _snowmanx, (double)_snowman.v() + _snowmanxx, (double)_snowman.w() + _snowmanxxx, _snowman);
         _snowmanxxxx.m();
         _snowman.c(_snowmanxxxx);
      }
   }

   protected void a(aag var1, fx var2, int var3) {
      if (_snowman.V().b(brt.f)) {
         while (_snowman > 0) {
            int _snowman = aqg.a(_snowman);
            _snowman -= _snowman;
            _snowman.c(new aqg(_snowman, (double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, _snowman));
         }
      }
   }

   public float f() {
      return this.au;
   }

   public void a(brx var1, fx var2, brp var3) {
   }

   public void a(brx var1, fx var2, aqa var3) {
   }

   @Nullable
   public ceh a(bny var1) {
      return this.n();
   }

   public void a(brx var1, bfw var2, fx var3, ceh var4, @Nullable ccj var5, bmb var6) {
      _snowman.b(aea.a.b(this));
      _snowman.t(0.005F);
      a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public void a(brx var1, fx var2, ceh var3, @Nullable aqm var4, bmb var5) {
   }

   public boolean ai_() {
      return !this.as.b() && !this.as.a();
   }

   public nx g() {
      return new of(this.i());
   }

   public String i() {
      if (this.c == null) {
         this.c = x.a("block", gm.Q.b(this));
      }

      return this.c;
   }

   public void a(brx var1, fx var2, aqa var3, float var4) {
      _snowman.b(_snowman, 1.0F);
   }

   public void a(brc var1, aqa var2) {
      _snowman.f(_snowman.cC().d(1.0, 0.0, 1.0));
   }

   public bmb a(brc var1, fx var2, ceh var3) {
      return new bmb(this);
   }

   public void a(bks var1, gj<bmb> var2) {
      _snowman.add(new bmb(this));
   }

   public float j() {
      return this.ax;
   }

   public float k() {
      return this.ay;
   }

   public float l() {
      return this.az;
   }

   public void a(brx var1, fx var2, ceh var3, bfw var4) {
      _snowman.a(_snowman, 2001, _snowman, i(_snowman));
      if (this.a(aed.az)) {
         bet.a(_snowman, false);
      }
   }

   public void c(brx var1, fx var2) {
   }

   public boolean a(brp var1) {
      return true;
   }

   protected void a(cei.a<buo, ceh> var1) {
   }

   public cei<buo, ceh> m() {
      return this.n;
   }

   protected final void j(ceh var1) {
      this.b = _snowman;
   }

   public final ceh n() {
      return this.b;
   }

   public cae k(ceh var1) {
      return this.aw;
   }

   @Override
   public blx h() {
      if (this.d == null) {
         this.d = blx.a(this);
      }

      return this.d;
   }

   public boolean o() {
      return this.aA;
   }

   @Override
   public String toString() {
      return "Block{" + gm.Q.b(this) + "}";
   }

   public void a(bmb var1, @Nullable brc var2, List<nr> var3, bnl var4) {
   }

   @Override
   protected buo p() {
      return this;
   }

   public static final class a {
      private final ceh a;
      private final ceh b;
      private final gc c;

      public a(ceh var1, ceh var2, gc var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (!(_snowman instanceof buo.a)) {
            return false;
         } else {
            buo.a _snowman = (buo.a)_snowman;
            return this.a == _snowman.a && this.b == _snowman.b && this.c == _snowman.c;
         }
      }

      @Override
      public int hashCode() {
         int _snowman = this.a.hashCode();
         _snowman = 31 * _snowman + this.b.hashCode();
         return 31 * _snowman + this.c.hashCode();
      }
   }
}
