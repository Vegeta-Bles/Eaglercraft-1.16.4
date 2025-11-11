import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class cce extends ccj implements aox, cdm {
   public static final aps[][] a = new aps[][]{{apw.a, apw.c}, {apw.k, apw.h}, {apw.e}, {apw.j}};
   private static final Set<aps> b = Arrays.stream(a).flatMap(Arrays::stream).collect(Collectors.toSet());
   private List<cce.a> c = Lists.newArrayList();
   private List<cce.a> g = Lists.newArrayList();
   private int h;
   private int i = -1;
   @Nullable
   private aps j;
   @Nullable
   private aps k;
   @Nullable
   private nr l;
   private aow m = aow.a;
   private final bil n = new bil() {
      @Override
      public int a(int var1) {
         switch (_snowman) {
            case 0:
               return cce.this.h;
            case 1:
               return aps.a(cce.this.j);
            case 2:
               return aps.a(cce.this.k);
            default:
               return 0;
         }
      }

      @Override
      public void a(int var1, int var2) {
         switch (_snowman) {
            case 0:
               cce.this.h = _snowman;
               break;
            case 1:
               if (!cce.this.d.v && !cce.this.c.isEmpty()) {
                  cce.this.a(adq.ax);
               }

               cce.this.j = cce.b(_snowman);
               break;
            case 2:
               cce.this.k = cce.b(_snowman);
         }
      }

      @Override
      public int a() {
         return 3;
      }
   };

   public cce() {
      super(cck.n);
   }

   @Override
   public void aj_() {
      int _snowman = this.e.u();
      int _snowmanx = this.e.v();
      int _snowmanxx = this.e.w();
      fx _snowmanxxx;
      if (this.i < _snowmanx) {
         _snowmanxxx = this.e;
         this.g = Lists.newArrayList();
         this.i = _snowmanxxx.v() - 1;
      } else {
         _snowmanxxx = new fx(_snowman, this.i + 1, _snowmanxx);
      }

      cce.a _snowmanxxxx = this.g.isEmpty() ? null : this.g.get(this.g.size() - 1);
      int _snowmanxxxxx = this.d.a(chn.a.b, _snowman, _snowmanxx);

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 10 && _snowmanxxx.v() <= _snowmanxxxxx; _snowmanxxxxxx++) {
         ceh _snowmanxxxxxxx = this.d.d_(_snowmanxxx);
         buo _snowmanxxxxxxxx = _snowmanxxxxxxx.b();
         if (_snowmanxxxxxxxx instanceof buh) {
            float[] _snowmanxxxxxxxxx = ((buh)_snowmanxxxxxxxx).a().e();
            if (this.g.size() <= 1) {
               _snowmanxxxx = new cce.a(_snowmanxxxxxxxxx);
               this.g.add(_snowmanxxxx);
            } else if (_snowmanxxxx != null) {
               if (Arrays.equals(_snowmanxxxxxxxxx, _snowmanxxxx.a)) {
                  _snowmanxxxx.a();
               } else {
                  _snowmanxxxx = new cce.a(new float[]{(_snowmanxxxx.a[0] + _snowmanxxxxxxxxx[0]) / 2.0F, (_snowmanxxxx.a[1] + _snowmanxxxxxxxxx[1]) / 2.0F, (_snowmanxxxx.a[2] + _snowmanxxxxxxxxx[2]) / 2.0F});
                  this.g.add(_snowmanxxxx);
               }
            }
         } else {
            if (_snowmanxxxx == null || _snowmanxxxxxxx.b((brc)this.d, _snowmanxxx) >= 15 && _snowmanxxxxxxxx != bup.z) {
               this.g.clear();
               this.i = _snowmanxxxxx;
               break;
            }

            _snowmanxxxx.a();
         }

         _snowmanxxx = _snowmanxxx.b();
         this.i++;
      }

      int _snowmanxxxxxx = this.h;
      if (this.d.T() % 80L == 0L) {
         if (!this.c.isEmpty()) {
            this.a(_snowman, _snowmanx, _snowmanxx);
         }

         if (this.h > 0 && !this.c.isEmpty()) {
            this.j();
            this.a(adq.av);
         }
      }

      if (this.i >= _snowmanxxxxx) {
         this.i = -1;
         boolean _snowmanxxxxxxx = _snowmanxxxxxx > 0;
         this.c = this.g;
         if (!this.d.v) {
            boolean _snowmanxxxxxxxx = this.h > 0;
            if (!_snowmanxxxxxxx && _snowmanxxxxxxxx) {
               this.a(adq.au);

               for (aah _snowmanxxxxxxxxx : this.d
                  .a(aah.class, new dci((double)_snowman, (double)_snowmanx, (double)_snowmanxx, (double)_snowman, (double)(_snowmanx - 4), (double)_snowmanxx).c(10.0, 5.0, 10.0))) {
                  ac.l.a(_snowmanxxxxxxxxx, this);
               }
            } else if (_snowmanxxxxxxx && !_snowmanxxxxxxxx) {
               this.a(adq.aw);
            }
         }
      }
   }

   private void a(int var1, int var2, int var3) {
      this.h = 0;

      for (int _snowman = 1; _snowman <= 4; this.h = _snowman++) {
         int _snowmanx = _snowman - _snowman;
         if (_snowmanx < 0) {
            break;
         }

         boolean _snowmanxx = true;

         for (int _snowmanxxx = _snowman - _snowman; _snowmanxxx <= _snowman + _snowman && _snowmanxx; _snowmanxxx++) {
            for (int _snowmanxxxx = _snowman - _snowman; _snowmanxxxx <= _snowman + _snowman; _snowmanxxxx++) {
               if (!this.d.d_(new fx(_snowmanxxx, _snowmanx, _snowmanxxxx)).a(aed.aq)) {
                  _snowmanxx = false;
                  break;
               }
            }
         }

         if (!_snowmanxx) {
            break;
         }
      }
   }

   @Override
   public void al_() {
      this.a(adq.aw);
      super.al_();
   }

   private void j() {
      if (!this.d.v && this.j != null) {
         double _snowman = (double)(this.h * 10 + 10);
         int _snowmanx = 0;
         if (this.h >= 4 && this.j == this.k) {
            _snowmanx = 1;
         }

         int _snowmanxx = (9 + this.h * 2) * 20;
         dci _snowmanxxx = new dci(this.e).g(_snowman).b(0.0, (double)this.d.L(), 0.0);
         List<bfw> _snowmanxxxx = this.d.a(bfw.class, _snowmanxxx);

         for (bfw _snowmanxxxxx : _snowmanxxxx) {
            _snowmanxxxxx.c(new apu(this.j, _snowmanxx, _snowmanx, true, true));
         }

         if (this.h >= 4 && this.j != this.k && this.k != null) {
            for (bfw _snowmanxxxxx : _snowmanxxxx) {
               _snowmanxxxxx.c(new apu(this.k, _snowmanxx, 0, true, true));
            }
         }
      }
   }

   public void a(adp var1) {
      this.d.a(null, this.e, _snowman, adr.e, 1.0F, 1.0F);
   }

   public List<cce.a> f() {
      return (List<cce.a>)(this.h == 0 ? ImmutableList.of() : this.c);
   }

   public int h() {
      return this.h;
   }

   @Nullable
   @Override
   public ow a() {
      return new ow(this.e, 3, this.b());
   }

   @Override
   public md b() {
      return this.a(new md());
   }

   @Override
   public double i() {
      return 256.0;
   }

   @Nullable
   private static aps b(int var0) {
      aps _snowman = aps.a(_snowman);
      return b.contains(_snowman) ? _snowman : null;
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.j = b(_snowman.h("Primary"));
      this.k = b(_snowman.h("Secondary"));
      if (_snowman.c("CustomName", 8)) {
         this.l = nr.a.a(_snowman.l("CustomName"));
      }

      this.m = aow.b(_snowman);
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      _snowman.b("Primary", aps.a(this.j));
      _snowman.b("Secondary", aps.a(this.k));
      _snowman.b("Levels", this.h);
      if (this.l != null) {
         _snowman.a("CustomName", nr.a.a(this.l));
      }

      this.m.a(_snowman);
      return _snowman;
   }

   public void a(@Nullable nr var1) {
      this.l = _snowman;
   }

   @Nullable
   @Override
   public bic createMenu(int var1, bfv var2, bfw var3) {
      return ccd.a(_snowman, this.m, this.d()) ? new bif(_snowman, _snowman, this.n, bim.a(this.d, this.o())) : null;
   }

   @Override
   public nr d() {
      return (nr)(this.l != null ? this.l : new of("container.beacon"));
   }

   public static class a {
      private final float[] a;
      private int b;

      public a(float[] var1) {
         this.a = _snowman;
         this.b = 1;
      }

      protected void a() {
         this.b++;
      }

      public float[] b() {
         return this.a;
      }

      public int c() {
         return this.b;
      }
   }
}
