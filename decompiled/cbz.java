import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public abstract class cbz extends ccd implements ape, bjl, bju, cdm {
   private static final int[] g = new int[]{0};
   private static final int[] h = new int[]{2, 1};
   private static final int[] i = new int[]{1};
   protected gj<bmb> a = gj.a(3, bmb.b);
   private int j;
   private int k;
   private int l;
   private int m;
   protected final bil b = new bil() {
      @Override
      public int a(int var1) {
         switch (_snowman) {
            case 0:
               return cbz.this.j;
            case 1:
               return cbz.this.k;
            case 2:
               return cbz.this.l;
            case 3:
               return cbz.this.m;
            default:
               return 0;
         }
      }

      @Override
      public void a(int var1, int var2) {
         switch (_snowman) {
            case 0:
               cbz.this.j = _snowman;
               break;
            case 1:
               cbz.this.k = _snowman;
               break;
            case 2:
               cbz.this.l = _snowman;
               break;
            case 3:
               cbz.this.m = _snowman;
         }
      }

      @Override
      public int a() {
         return 4;
      }
   };
   private final Object2IntOpenHashMap<vk> n = new Object2IntOpenHashMap();
   protected final bot<? extends boc> c;

   protected cbz(cck<?> var1, bot<? extends boc> var2) {
      super(_snowman);
      this.c = _snowman;
   }

   public static Map<blx, Integer> f() {
      Map<blx, Integer> _snowman = Maps.newLinkedHashMap();
      a(_snowman, bmd.lM, 20000);
      a(_snowman, bup.gS, 16000);
      a(_snowman, bmd.nr, 2400);
      a(_snowman, bmd.ke, 1600);
      a(_snowman, bmd.kf, 1600);
      a(_snowman, aeg.q, 300);
      a(_snowman, aeg.c, 300);
      a(_snowman, aeg.i, 300);
      a(_snowman, aeg.j, 150);
      a(_snowman, aeg.m, 300);
      a(_snowman, aeg.l, 300);
      a(_snowman, bup.cJ, 300);
      a(_snowman, bup.in, 300);
      a(_snowman, bup.im, 300);
      a(_snowman, bup.io, 300);
      a(_snowman, bup.iq, 300);
      a(_snowman, bup.ip, 300);
      a(_snowman, bup.dQ, 300);
      a(_snowman, bup.ii, 300);
      a(_snowman, bup.ih, 300);
      a(_snowman, bup.ij, 300);
      a(_snowman, bup.il, 300);
      a(_snowman, bup.ik, 300);
      a(_snowman, bup.aw, 300);
      a(_snowman, bup.bI, 300);
      a(_snowman, bup.lY, 300);
      a(_snowman, bup.cI, 300);
      a(_snowman, bup.bR, 300);
      a(_snowman, bup.fr, 300);
      a(_snowman, bup.bV, 300);
      a(_snowman, bup.fv, 300);
      a(_snowman, aeg.z, 300);
      a(_snowman, bmd.kc, 300);
      a(_snowman, bmd.mi, 300);
      a(_snowman, bup.cg, 300);
      a(_snowman, aeg.U, 200);
      a(_snowman, bmd.km, 200);
      a(_snowman, bmd.kl, 200);
      a(_snowman, bmd.kp, 200);
      a(_snowman, bmd.ko, 200);
      a(_snowman, bmd.kn, 200);
      a(_snowman, aeg.h, 200);
      a(_snowman, aeg.S, 1200);
      a(_snowman, aeg.b, 100);
      a(_snowman, aeg.e, 100);
      a(_snowman, bmd.kP, 100);
      a(_snowman, aeg.o, 100);
      a(_snowman, bmd.kQ, 100);
      a(_snowman, aeg.g, 67);
      a(_snowman, bup.ke, 4001);
      a(_snowman, bmd.qQ, 300);
      a(_snowman, bup.kY, 50);
      a(_snowman, bup.aT, 100);
      a(_snowman, bup.lQ, 400);
      a(_snowman, bup.lR, 300);
      a(_snowman, bup.lS, 300);
      a(_snowman, bup.lV, 300);
      a(_snowman, bup.lW, 300);
      a(_snowman, bup.lZ, 300);
      a(_snowman, bup.na, 300);
      return _snowman;
   }

   private static boolean b(blx var0) {
      return aeg.Q.a(_snowman);
   }

   private static void a(Map<blx, Integer> var0, ael<blx> var1, int var2) {
      for (blx _snowman : _snowman.b()) {
         if (!b(_snowman)) {
            _snowman.put(_snowman, _snowman);
         }
      }
   }

   private static void a(Map<blx, Integer> var0, brw var1, int var2) {
      blx _snowman = _snowman.h();
      if (b(_snowman)) {
         if (w.d) {
            throw (IllegalStateException)x.c(
               new IllegalStateException(
                  "A developer tried to explicitly make fire resistant item " + _snowman.h(null).getString() + " a furnace fuel. That will not work!"
               )
            );
         }
      } else {
         _snowman.put(_snowman, _snowman);
      }
   }

   private boolean j() {
      return this.j > 0;
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.a = gj.a(this.Z_(), bmb.b);
      aoo.b(_snowman, this.a);
      this.j = _snowman.g("BurnTime");
      this.l = _snowman.g("CookTime");
      this.m = _snowman.g("CookTimeTotal");
      this.k = this.a(this.a.get(1));
      md _snowman = _snowman.p("RecipesUsed");

      for (String _snowmanx : _snowman.d()) {
         this.n.put(new vk(_snowmanx), _snowman.h(_snowmanx));
      }
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      _snowman.a("BurnTime", (short)this.j);
      _snowman.a("CookTime", (short)this.l);
      _snowman.a("CookTimeTotal", (short)this.m);
      aoo.a(_snowman, this.a);
      md _snowman = new md();
      this.n.forEach((var1x, var2x) -> _snowman.b(var1x.toString(), var2x));
      _snowman.a("RecipesUsed", _snowman);
      return _snowman;
   }

   @Override
   public void aj_() {
      boolean _snowman = this.j();
      boolean _snowmanx = false;
      if (this.j()) {
         this.j--;
      }

      if (!this.d.v) {
         bmb _snowmanxx = this.a.get(1);
         if (this.j() || !_snowmanxx.a() && !this.a.get(0).a()) {
            boq<?> _snowmanxxx = (boq<?>)this.d.o().a(this.c, this, this.d).orElse(null);
            if (!this.j() && this.b(_snowmanxxx)) {
               this.j = this.a(_snowmanxx);
               this.k = this.j;
               if (this.j()) {
                  _snowmanx = true;
                  if (!_snowmanxx.a()) {
                     blx _snowmanxxxx = _snowmanxx.b();
                     _snowmanxx.g(1);
                     if (_snowmanxx.a()) {
                        blx _snowmanxxxxx = _snowmanxxxx.o();
                        this.a.set(1, _snowmanxxxxx == null ? bmb.b : new bmb(_snowmanxxxxx));
                     }
                  }
               }
            }

            if (this.j() && this.b(_snowmanxxx)) {
               this.l++;
               if (this.l == this.m) {
                  this.l = 0;
                  this.m = this.h();
                  this.c(_snowmanxxx);
                  _snowmanx = true;
               }
            } else {
               this.l = 0;
            }
         } else if (!this.j() && this.l > 0) {
            this.l = afm.a(this.l - 2, 0, this.m);
         }

         if (_snowman != this.j()) {
            _snowmanx = true;
            this.d.a(this.e, this.d.d_(this.e).a(bto.b, Boolean.valueOf(this.j())), 3);
         }
      }

      if (_snowmanx) {
         this.X_();
      }
   }

   protected boolean b(@Nullable boq<?> var1) {
      if (!this.a.get(0).a() && _snowman != null) {
         bmb _snowman = _snowman.c();
         if (_snowman.a()) {
            return false;
         } else {
            bmb _snowmanx = this.a.get(2);
            if (_snowmanx.a()) {
               return true;
            } else if (!_snowmanx.a(_snowman)) {
               return false;
            } else {
               return _snowmanx.E() < this.V_() && _snowmanx.E() < _snowmanx.c() ? true : _snowmanx.E() < _snowman.c();
            }
         }
      } else {
         return false;
      }
   }

   private void c(@Nullable boq<?> var1) {
      if (_snowman != null && this.b(_snowman)) {
         bmb _snowman = this.a.get(0);
         bmb _snowmanx = _snowman.c();
         bmb _snowmanxx = this.a.get(2);
         if (_snowmanxx.a()) {
            this.a.set(2, _snowmanx.i());
         } else if (_snowmanxx.b() == _snowmanx.b()) {
            _snowmanxx.f(1);
         }

         if (!this.d.v) {
            this.a(_snowman);
         }

         if (_snowman.b() == bup.ao.h() && !this.a.get(1).a() && this.a.get(1).b() == bmd.lK) {
            this.a.set(1, new bmb(bmd.lL));
         }

         _snowman.g(1);
      }
   }

   protected int a(bmb var1) {
      if (_snowman.a()) {
         return 0;
      } else {
         blx _snowman = _snowman.b();
         return f().getOrDefault(_snowman, 0);
      }
   }

   protected int h() {
      return this.d.o().a(this.c, this, this.d).map(boc::e).orElse(200);
   }

   public static boolean b(bmb var0) {
      return f().containsKey(_snowman.b());
   }

   @Override
   public int[] a(gc var1) {
      if (_snowman == gc.a) {
         return h;
      } else {
         return _snowman == gc.b ? g : i;
      }
   }

   @Override
   public boolean a(int var1, bmb var2, @Nullable gc var3) {
      return this.b(_snowman, _snowman);
   }

   @Override
   public boolean b(int var1, bmb var2, gc var3) {
      if (_snowman == gc.a && _snowman == 1) {
         blx _snowman = _snowman.b();
         if (_snowman != bmd.lL && _snowman != bmd.lK) {
            return false;
         }
      }

      return true;
   }

   @Override
   public int Z_() {
      return this.a.size();
   }

   @Override
   public boolean c() {
      for (bmb _snowman : this.a) {
         if (!_snowman.a()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public bmb a(int var1) {
      return this.a.get(_snowman);
   }

   @Override
   public bmb a(int var1, int var2) {
      return aoo.a(this.a, _snowman, _snowman);
   }

   @Override
   public bmb b(int var1) {
      return aoo.a(this.a, _snowman);
   }

   @Override
   public void a(int var1, bmb var2) {
      bmb _snowman = this.a.get(_snowman);
      boolean _snowmanx = !_snowman.a() && _snowman.a(_snowman) && bmb.a(_snowman, _snowman);
      this.a.set(_snowman, _snowman);
      if (_snowman.E() > this.V_()) {
         _snowman.e(this.V_());
      }

      if (_snowman == 0 && !_snowmanx) {
         this.m = this.h();
         this.l = 0;
         this.X_();
      }
   }

   @Override
   public boolean a(bfw var1) {
      return this.d.c(this.e) != this ? false : _snowman.h((double)this.e.u() + 0.5, (double)this.e.v() + 0.5, (double)this.e.w() + 0.5) <= 64.0;
   }

   @Override
   public boolean b(int var1, bmb var2) {
      if (_snowman == 2) {
         return false;
      } else if (_snowman != 1) {
         return true;
      } else {
         bmb _snowman = this.a.get(1);
         return b(_snowman) || _snowman.b() == bmd.lK && _snowman.b() != bmd.lK;
      }
   }

   @Override
   public void Y_() {
      this.a.clear();
   }

   @Override
   public void a(@Nullable boq<?> var1) {
      if (_snowman != null) {
         vk _snowman = _snowman.f();
         this.n.addTo(_snowman, 1);
      }
   }

   @Nullable
   @Override
   public boq<?> ak_() {
      return null;
   }

   @Override
   public void b(bfw var1) {
   }

   public void d(bfw var1) {
      List<boq<?>> _snowman = this.a(_snowman.l, _snowman.cA());
      _snowman.a(_snowman);
      this.n.clear();
   }

   public List<boq<?>> a(brx var1, dcn var2) {
      List<boq<?>> _snowman = Lists.newArrayList();
      ObjectIterator var4 = this.n.object2IntEntrySet().iterator();

      while (var4.hasNext()) {
         Entry<vk> _snowmanx = (Entry<vk>)var4.next();
         _snowman.o().a((vk)_snowmanx.getKey()).ifPresent(var4x -> {
            _snowman.add((boq<?>)var4x);
            a(_snowman, _snowman, _snowman.getIntValue(), ((boc)var4x).b());
         });
      }

      return _snowman;
   }

   private static void a(brx var0, dcn var1, int var2, float var3) {
      int _snowman = afm.d((float)_snowman * _snowman);
      float _snowmanx = afm.h((float)_snowman * _snowman);
      if (_snowmanx != 0.0F && Math.random() < (double)_snowmanx) {
         _snowman++;
      }

      while (_snowman > 0) {
         int _snowmanxx = aqg.a(_snowman);
         _snowman -= _snowmanxx;
         _snowman.c(new aqg(_snowman, _snowman.b, _snowman.c, _snowman.d, _snowmanxx));
      }
   }

   @Override
   public void a(bfy var1) {
      for (bmb _snowman : this.a) {
         _snowman.b(_snowman);
      }
   }
}
