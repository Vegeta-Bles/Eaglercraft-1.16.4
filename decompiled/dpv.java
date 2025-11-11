import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public class dpv extends dot {
   public static final dpv.a a = new dpv.a() {
      @Override
      public int a() {
         return 0;
      }

      @Override
      public nu a(int var1) {
         return nu.c;
      }
   };
   public static final vk b = new vk("textures/gui/book.png");
   private dpv.a c;
   private int p;
   private List<afa> q = Collections.emptyList();
   private int r = -1;
   private nr s = oe.d;
   private dqt t;
   private dqt u;
   private final boolean v;

   public dpv(dpv.a var1) {
      this(_snowman, true);
   }

   public dpv() {
      this(a, false);
   }

   private dpv(dpv.a var1, boolean var2) {
      super(dkz.a);
      this.c = _snowman;
      this.v = _snowman;
   }

   public void a(dpv.a var1) {
      this.c = _snowman;
      this.p = afm.a(this.p, 0, _snowman.a());
      this.n();
      this.r = -1;
   }

   public boolean a(int var1) {
      int _snowman = afm.a(_snowman, 0, this.c.a() - 1);
      if (_snowman != this.p) {
         this.p = _snowman;
         this.n();
         this.r = -1;
         return true;
      } else {
         return false;
      }
   }

   protected boolean b(int var1) {
      return this.a(_snowman);
   }

   @Override
   protected void b() {
      this.i();
      this.k();
   }

   protected void i() {
      this.a((dlj)(new dlj(this.k / 2 - 100, 196, 200, 20, nq.c, var1 -> this.i.a(null))));
   }

   protected void k() {
      int _snowman = (this.k - 192) / 2;
      int _snowmanx = 2;
      this.t = this.a(new dqt(_snowman + 116, 159, true, var1x -> this.m(), this.v));
      this.u = this.a(new dqt(_snowman + 43, 159, false, var1x -> this.l(), this.v));
      this.n();
   }

   private int h() {
      return this.c.a();
   }

   protected void l() {
      if (this.p > 0) {
         this.p--;
      }

      this.n();
   }

   protected void m() {
      if (this.p < this.h() - 1) {
         this.p++;
      }

      this.n();
   }

   private void n() {
      this.t.p = this.p < this.h() - 1;
      this.u.p = this.p > 0;
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (super.a(_snowman, _snowman, _snowman)) {
         return true;
      } else {
         switch (_snowman) {
            case 266:
               this.u.b();
               return true;
            case 267:
               this.t.b();
               return true;
            default:
               return false;
         }
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.i.M().a(b);
      int _snowman = (this.k - 192) / 2;
      int _snowmanx = 2;
      this.b(_snowman, _snowman, 2, 0, 0, 192, 192);
      if (this.r != this.p) {
         nu _snowmanxx = this.c.b(this.p);
         this.q = this.o.b(_snowmanxx, 114);
         this.s = new of("book.pageIndicator", this.p + 1, Math.max(this.h(), 1));
      }

      this.r = this.p;
      int _snowmanxx = this.o.a(this.s);
      this.o.b(_snowman, this.s, (float)(_snowman - _snowmanxx + 192 - 44), 18.0F, 0);
      int _snowmanxxx = Math.min(128 / 9, this.q.size());

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx; _snowmanxxxx++) {
         afa _snowmanxxxxx = this.q.get(_snowmanxxxx);
         this.o.b(_snowman, _snowmanxxxxx, (float)(_snowman + 36), (float)(32 + _snowmanxxxx * 9), 0);
      }

      ob _snowmanxxxx = this.a((double)_snowman, (double)_snowman);
      if (_snowmanxxxx != null) {
         this.a(_snowman, _snowmanxxxx, _snowman, _snowman);
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (_snowman == 0) {
         ob _snowman = this.a(_snowman, _snowman);
         if (_snowman != null && this.a(_snowman)) {
            return true;
         }
      }

      return super.a(_snowman, _snowman, _snowman);
   }

   @Override
   public boolean a(ob var1) {
      np _snowman = _snowman.h();
      if (_snowman == null) {
         return false;
      } else if (_snowman.a() == np.a.e) {
         String _snowmanx = _snowman.b();

         try {
            int _snowmanxx = Integer.parseInt(_snowmanx) - 1;
            return this.b(_snowmanxx);
         } catch (Exception var5) {
            return false;
         }
      } else {
         boolean _snowmanx = super.a(_snowman);
         if (_snowmanx && _snowman.a() == np.a.c) {
            this.i.a(null);
         }

         return _snowmanx;
      }
   }

   @Nullable
   public ob a(double var1, double var3) {
      if (this.q.isEmpty()) {
         return null;
      } else {
         int _snowman = afm.c(_snowman - (double)((this.k - 192) / 2) - 36.0);
         int _snowmanx = afm.c(_snowman - 2.0 - 30.0);
         if (_snowman >= 0 && _snowmanx >= 0) {
            int _snowmanxx = Math.min(128 / 9, this.q.size());
            if (_snowman <= 114 && _snowmanx < 9 * _snowmanxx + _snowmanxx) {
               int _snowmanxxx = _snowmanx / 9;
               if (_snowmanxxx >= 0 && _snowmanxxx < this.q.size()) {
                  afa _snowmanxxxx = this.q.get(_snowmanxxx);
                  return this.i.g.b().a(_snowmanxxxx, _snowman);
               } else {
                  return null;
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public static List<String> a(md var0) {
      mj _snowman = _snowman.d("pages", 8).d();
      Builder<String> _snowmanx = ImmutableList.builder();

      for (int _snowmanxx = 0; _snowmanxx < _snowman.size(); _snowmanxx++) {
         _snowmanx.add(_snowman.j(_snowmanxx));
      }

      return _snowmanx.build();
   }

   public interface a {
      int a();

      nu a(int var1);

      default nu b(int var1) {
         return _snowman >= 0 && _snowman < this.a() ? this.a(_snowman) : nu.c;
      }

      static dpv.a a(bmb var0) {
         blx _snowman = _snowman.b();
         if (_snowman == bmd.oU) {
            return new dpv.c(_snowman);
         } else {
            return (dpv.a)(_snowman == bmd.oT ? new dpv.b(_snowman) : dpv.a);
         }
      }
   }

   public static class b implements dpv.a {
      private final List<String> a;

      public b(bmb var1) {
         this.a = b(_snowman);
      }

      private static List<String> b(bmb var0) {
         md _snowman = _snowman.o();
         return (List<String>)(_snowman != null ? dpv.a(_snowman) : ImmutableList.of());
      }

      @Override
      public int a() {
         return this.a.size();
      }

      @Override
      public nu a(int var1) {
         return nu.b(this.a.get(_snowman));
      }
   }

   public static class c implements dpv.a {
      private final List<String> a;

      public c(bmb var1) {
         this.a = b(_snowman);
      }

      private static List<String> b(bmb var0) {
         md _snowman = _snowman.o();
         return (List<String>)(_snowman != null && bns.a(_snowman) ? dpv.a(_snowman) : ImmutableList.of(nr.a.a(new of("book.invalid.tag").a(k.e))));
      }

      @Override
      public int a() {
         return this.a.size();
      }

      @Override
      public nu a(int var1) {
         String _snowman = this.a.get(_snowman);

         try {
            nu _snowmanx = nr.a.a(_snowman);
            if (_snowmanx != null) {
               return _snowmanx;
            }
         } catch (Exception var4) {
         }

         return nu.b(_snowman);
      }
   }
}
