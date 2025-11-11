import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

public class dpb extends dot implements dpc {
   private static final nr c = new of("multiplayer.downloadingStats");
   protected final dot a;
   private dpb.a p;
   private dpb.b q;
   private dpb.c r;
   private final aeb s;
   @Nullable
   private dlv<?> t;
   private boolean u = true;

   public dpb(dot var1, aeb var2) {
      super(new of("gui.stats"));
      this.a = _snowman;
      this.s = _snowman;
   }

   @Override
   protected void b() {
      this.u = true;
      this.i.w().a(new sf(sf.a.b));
   }

   public void h() {
      this.p = new dpb.a(this.i);
      this.q = new dpb.b(this.i);
      this.r = new dpb.c(this.i);
   }

   public void i() {
      this.a((dlj)(new dlj(this.k / 2 - 120, this.l - 52, 80, 20, new of("stat.generalButton"), var1x -> this.a(this.p))));
      dlj _snowman = this.a((dlj)(new dlj(this.k / 2 - 40, this.l - 52, 80, 20, new of("stat.itemsButton"), var1x -> this.a(this.q))));
      dlj _snowmanx = this.a((dlj)(new dlj(this.k / 2 + 40, this.l - 52, 80, 20, new of("stat.mobsButton"), var1x -> this.a(this.r))));
      this.a((dlj)(new dlj(this.k / 2 - 100, this.l - 28, 200, 20, nq.c, var1x -> this.i.a(this.a))));
      if (this.q.au_().isEmpty()) {
         _snowman.o = false;
      }

      if (this.r.au_().isEmpty()) {
         _snowmanx.o = false;
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      if (this.u) {
         this.a(_snowman);
         a(_snowman, this.o, c, this.k / 2, this.l / 2, 16777215);
         a(_snowman, this.o, b[(int)(x.b() / 150L % (long)b.length)], this.k / 2, this.l / 2 + 9 * 2, 16777215);
      } else {
         this.l().a(_snowman, _snowman, _snowman, _snowman);
         a(_snowman, this.o, this.d, this.k / 2, 20, 16777215);
         super.a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   @Override
   public void k() {
      if (this.u) {
         this.h();
         this.i();
         this.a(this.p);
         this.u = false;
      }
   }

   @Override
   public boolean ay_() {
      return !this.u;
   }

   @Nullable
   public dlv<?> l() {
      return this.t;
   }

   public void a(@Nullable dlv<?> var1) {
      this.e.remove(this.p);
      this.e.remove(this.q);
      this.e.remove(this.r);
      if (_snowman != null) {
         this.e.add(0, _snowman);
         this.t = _snowman;
      }
   }

   private static String b(adx<vk> var0) {
      return "stat." + _snowman.b().toString().replace(':', '.');
   }

   private int a(int var1) {
      return 115 + 40 * _snowman;
   }

   private void a(dfm var1, int var2, int var3, blx var4) {
      this.c(_snowman, _snowman + 1, _snowman + 1, 0, 0);
      RenderSystem.enableRescaleNormal();
      this.j.a(_snowman.r(), _snowman + 2, _snowman + 2);
      RenderSystem.disableRescaleNormal();
   }

   private void c(dfm var1, int var2, int var3, int var4, int var5) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.i.M().a(g);
      a(_snowman, _snowman, _snowman, this.v(), (float)_snowman, (float)_snowman, 18, 18, 128, 128);
   }

   class a extends dlv<dpb.a.a> {
      public a(djz var2) {
         super(_snowman, dpb.this.k, dpb.this.l, 32, dpb.this.l - 64, 10);
         ObjectArrayList<adx<vk>> _snowman = new ObjectArrayList(aea.i.iterator());
         _snowman.sort(Comparator.comparing(var0 -> ekx.a(dpb.b((adx<vk>)var0))));
         ObjectListIterator var4 = _snowman.iterator();

         while (var4.hasNext()) {
            adx<vk> _snowmanx = (adx<vk>)var4.next();
            this.b(new dpb.a.a(_snowmanx));
         }
      }

      @Override
      protected void a(dfm var1) {
         dpb.this.a(_snowman);
      }

      class a extends dlv.a<dpb.a.a> {
         private final adx<vk> b;
         private final nr c;

         private a(adx<vk> var2) {
            this.b = _snowman;
            this.c = new of(dpb.b(_snowman));
         }

         @Override
         public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
            dkw.b(_snowman, dpb.this.o, this.c, _snowman + 2, _snowman + 1, _snowman % 2 == 0 ? 16777215 : 9474192);
            String _snowman = this.b.a(dpb.this.s.a(this.b));
            dkw.b(_snowman, dpb.this.o, _snowman, _snowman + 2 + 213 - dpb.this.o.b(_snowman), _snowman + 1, _snowman % 2 == 0 ? 16777215 : 9474192);
         }
      }
   }

   class b extends dlv<dpb.b.b> {
      protected final List<adz<buo>> a;
      protected final List<adz<blx>> o;
      private final int[] v = new int[]{3, 4, 1, 2, 5, 6};
      protected int p = -1;
      protected final List<blx> q;
      protected final Comparator<blx> r = new dpb.b.a();
      @Nullable
      protected adz<?> s;
      protected int t;

      public b(djz var2) {
         super(_snowman, dpb.this.k, dpb.this.l, 32, dpb.this.l - 64, 20);
         this.a = Lists.newArrayList();
         this.a.add(aea.a);
         this.o = Lists.newArrayList(new adz[]{aea.d, aea.b, aea.c, aea.e, aea.f});
         this.a(true, 20);
         Set<blx> _snowman = Sets.newIdentityHashSet();

         for (blx _snowmanx : gm.T) {
            boolean _snowmanxx = false;

            for (adz<blx> _snowmanxxx : this.o) {
               if (_snowmanxxx.a(_snowmanx) && dpb.this.s.a(_snowmanxxx.b(_snowmanx)) > 0) {
                  _snowmanxx = true;
               }
            }

            if (_snowmanxx) {
               _snowman.add(_snowmanx);
            }
         }

         for (buo _snowmanx : gm.Q) {
            boolean _snowmanxx = false;

            for (adz<buo> _snowmanxxxx : this.a) {
               if (_snowmanxxxx.a(_snowmanx) && dpb.this.s.a(_snowmanxxxx.b(_snowmanx)) > 0) {
                  _snowmanxx = true;
               }
            }

            if (_snowmanxx) {
               _snowman.add(_snowmanx.h());
            }
         }

         _snowman.remove(bmd.a);
         this.q = Lists.newArrayList(_snowman);

         for (int _snowmanx = 0; _snowmanx < this.q.size(); _snowmanx++) {
            this.b(new dpb.b.b());
         }
      }

      @Override
      protected void a(dfm var1, int var2, int var3, dfo var4) {
         if (!this.b.l.b()) {
            this.p = -1;
         }

         for (int _snowman = 0; _snowman < this.v.length; _snowman++) {
            dpb.this.c(_snowman, _snowman + dpb.this.a(_snowman) - 18, _snowman + 1, 0, this.p == _snowman ? 0 : 18);
         }

         if (this.s != null) {
            int _snowman = dpb.this.a(this.b(this.s)) - 36;
            int _snowmanx = this.t == 1 ? 2 : 1;
            dpb.this.c(_snowman, _snowman + _snowman, _snowman + 1, 18 * _snowmanx, 0);
         }

         for (int _snowman = 0; _snowman < this.v.length; _snowman++) {
            int _snowmanx = this.p == _snowman ? 1 : 0;
            dpb.this.c(_snowman, _snowman + dpb.this.a(_snowman) - 18 + _snowmanx, _snowman + 1 + _snowmanx, 18 * this.v[_snowman], 18);
         }
      }

      @Override
      public int d() {
         return 375;
      }

      @Override
      protected int e() {
         return this.d / 2 + 140;
      }

      @Override
      protected void a(dfm var1) {
         dpb.this.a(_snowman);
      }

      @Override
      protected void a(int var1, int var2) {
         this.p = -1;

         for (int _snowman = 0; _snowman < this.v.length; _snowman++) {
            int _snowmanx = _snowman - dpb.this.a(_snowman);
            if (_snowmanx >= -36 && _snowmanx <= 0) {
               this.p = _snowman;
               break;
            }
         }

         if (this.p >= 0) {
            this.a(this.a(this.p));
            this.b.W().a(emp.a(adq.pF, 1.0F));
         }
      }

      private adz<?> a(int var1) {
         return _snowman < this.a.size() ? this.a.get(_snowman) : this.o.get(_snowman - this.a.size());
      }

      private int b(adz<?> var1) {
         int _snowman = this.a.indexOf(_snowman);
         if (_snowman >= 0) {
            return _snowman;
         } else {
            int _snowmanx = this.o.indexOf(_snowman);
            return _snowmanx >= 0 ? _snowmanx + this.a.size() : -1;
         }
      }

      @Override
      protected void a(dfm var1, int var2, int var3) {
         if (_snowman >= this.i && _snowman <= this.j) {
            dpb.b.b _snowman = this.a((double)_snowman, (double)_snowman);
            int _snowmanx = (this.d - this.d()) / 2;
            if (_snowman != null) {
               if (_snowman < _snowmanx + 40 || _snowman > _snowmanx + 40 + 20) {
                  return;
               }

               blx _snowmanxx = this.q.get(this.au_().indexOf(_snowman));
               this.a(_snowman, this.a(_snowmanxx), _snowman, _snowman);
            } else {
               nr _snowmanxx = null;
               int _snowmanxxx = _snowman - _snowmanx;

               for (int _snowmanxxxx = 0; _snowmanxxxx < this.v.length; _snowmanxxxx++) {
                  int _snowmanxxxxx = dpb.this.a(_snowmanxxxx);
                  if (_snowmanxxx >= _snowmanxxxxx - 18 && _snowmanxxx <= _snowmanxxxxx) {
                     _snowmanxx = this.a(_snowmanxxxx).d();
                     break;
                  }
               }

               this.a(_snowman, _snowmanxx, _snowman, _snowman);
            }
         }
      }

      protected void a(dfm var1, @Nullable nr var2, int var3, int var4) {
         if (_snowman != null) {
            int _snowman = _snowman + 12;
            int _snowmanx = _snowman - 12;
            int _snowmanxx = dpb.this.o.a(_snowman);
            this.a(_snowman, _snowman - 3, _snowmanx - 3, _snowman + _snowmanxx + 3, _snowmanx + 8 + 3, -1073741824, -1073741824);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, 400.0F);
            dpb.this.o.a(_snowman, _snowman, (float)_snowman, (float)_snowmanx, -1);
            RenderSystem.popMatrix();
         }
      }

      protected nr a(blx var1) {
         return _snowman.l();
      }

      protected void a(adz<?> var1) {
         if (_snowman != this.s) {
            this.s = _snowman;
            this.t = -1;
         } else if (this.t == -1) {
            this.t = 1;
         } else {
            this.s = null;
            this.t = 0;
         }

         this.q.sort(this.r);
      }

      class a implements Comparator<blx> {
         private a() {
         }

         public int a(blx var1, blx var2) {
            int _snowman;
            int _snowmanx;
            if (b.this.s == null) {
               _snowman = 0;
               _snowmanx = 0;
            } else if (b.this.a.contains(b.this.s)) {
               adz<buo> _snowmanxx = (adz<buo>)b.this.s;
               _snowman = _snowman instanceof bkh ? dpb.this.s.a(_snowmanxx, ((bkh)_snowman).e()) : -1;
               _snowmanx = _snowman instanceof bkh ? dpb.this.s.a(_snowmanxx, ((bkh)_snowman).e()) : -1;
            } else {
               adz<blx> _snowmanxx = (adz<blx>)b.this.s;
               _snowman = dpb.this.s.a(_snowmanxx, _snowman);
               _snowmanx = dpb.this.s.a(_snowmanxx, _snowman);
            }

            return _snowman == _snowmanx ? b.this.t * Integer.compare(blx.a(_snowman), blx.a(_snowman)) : b.this.t * Integer.compare(_snowman, _snowmanx);
         }
      }

      class b extends dlv.a<dpb.b.b> {
         private b() {
         }

         @Override
         public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
            blx _snowman = dpb.this.q.q.get(_snowman);
            dpb.this.a(_snowman, _snowman + 40, _snowman, _snowman);

            for (int _snowmanx = 0; _snowmanx < dpb.this.q.a.size(); _snowmanx++) {
               adx<buo> _snowmanxx;
               if (_snowman instanceof bkh) {
                  _snowmanxx = dpb.this.q.a.get(_snowmanx).b(((bkh)_snowman).e());
               } else {
                  _snowmanxx = null;
               }

               this.a(_snowman, _snowmanxx, _snowman + dpb.this.a(_snowmanx), _snowman, _snowman % 2 == 0);
            }

            for (int _snowmanx = 0; _snowmanx < dpb.this.q.o.size(); _snowmanx++) {
               this.a(_snowman, dpb.this.q.o.get(_snowmanx).b(_snowman), _snowman + dpb.this.a(_snowmanx + dpb.this.q.a.size()), _snowman, _snowman % 2 == 0);
            }
         }

         protected void a(dfm var1, @Nullable adx<?> var2, int var3, int var4, boolean var5) {
            String _snowman = _snowman == null ? "-" : _snowman.a(dpb.this.s.a(_snowman));
            dkw.b(_snowman, dpb.this.o, _snowman, _snowman - dpb.this.o.b(_snowman), _snowman + 5, _snowman ? 16777215 : 9474192);
         }
      }
   }

   class c extends dlv<dpb.c.a> {
      public c(djz var2) {
         super(_snowman, dpb.this.k, dpb.this.l, 32, dpb.this.l - 64, 9 * 4);

         for (aqe<?> _snowman : gm.S) {
            if (dpb.this.s.a(aea.g.b(_snowman)) > 0 || dpb.this.s.a(aea.h.b(_snowman)) > 0) {
               this.b(new dpb.c.a(_snowman));
            }
         }
      }

      @Override
      protected void a(dfm var1) {
         dpb.this.a(_snowman);
      }

      class a extends dlv.a<dpb.c.a> {
         private final aqe<?> b;
         private final nr c;
         private final nr d;
         private final boolean e;
         private final nr f;
         private final boolean g;

         public a(aqe<?> var2) {
            this.b = _snowman;
            this.c = _snowman.g();
            int _snowman = dpb.this.s.a(aea.g.b(_snowman));
            if (_snowman == 0) {
               this.d = new of("stat_type.minecraft.killed.none", this.c);
               this.e = false;
            } else {
               this.d = new of("stat_type.minecraft.killed", _snowman, this.c);
               this.e = true;
            }

            int _snowmanx = dpb.this.s.a(aea.h.b(_snowman));
            if (_snowmanx == 0) {
               this.f = new of("stat_type.minecraft.killed_by.none", this.c);
               this.g = false;
            } else {
               this.f = new of("stat_type.minecraft.killed_by", this.c, _snowmanx);
               this.g = true;
            }
         }

         @Override
         public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
            dkw.b(_snowman, dpb.this.o, this.c, _snowman + 2, _snowman + 1, 16777215);
            dkw.b(_snowman, dpb.this.o, this.d, _snowman + 2 + 10, _snowman + 1 + 9, this.e ? 9474192 : 6316128);
            dkw.b(_snowman, dpb.this.o, this.f, _snowman + 2 + 10, _snowman + 1 + 9 * 2, this.g ? 9474192 : 6316128);
         }
      }
   }
}
