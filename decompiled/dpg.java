import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import javax.annotation.Nullable;

public class dpg extends dkw {
   private static final vk a = new vk("textures/gui/advancements/widgets.png");
   private static final int[] b = new int[]{0, 10, -10, 25, -25};
   private final dpe c;
   private final y d;
   private final ah e;
   private final afa i;
   private final int j;
   private final List<afa> k;
   private final djz l;
   private dpg m;
   private final List<dpg> n = Lists.newArrayList();
   private aa o;
   private final int p;
   private final int q;

   public dpg(dpe var1, djz var2, y var3, ah var4) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.l = _snowman;
      this.i = ly.a().a(_snowman.g.a(_snowman.a(), 163));
      this.p = afm.d(_snowman.f() * 28.0F);
      this.q = afm.d(_snowman.g() * 27.0F);
      int _snowman = _snowman.g();
      int _snowmanx = String.valueOf(_snowman).length();
      int _snowmanxx = _snowman > 1 ? _snowman.g.b("  ") + _snowman.g.b("0") * _snowmanx * 2 + _snowman.g.b("/") : 0;
      int _snowmanxxx = 29 + _snowman.g.a(this.i) + _snowmanxx;
      this.k = ly.a().a(this.a(ns.a(_snowman.b().e(), ob.a.a(_snowman.e().c())), _snowmanxxx));

      for (afa _snowmanxxxx : this.k) {
         _snowmanxxx = Math.max(_snowmanxxx, _snowman.g.a(_snowmanxxxx));
      }

      this.j = _snowmanxxx + 3 + 5;
   }

   private static float a(dkj var0, List<nu> var1) {
      return (float)_snowman.stream().mapToDouble(_snowman::a).max().orElse(0.0);
   }

   private List<nu> a(nr var1, int var2) {
      dkj _snowman = this.l.g.b();
      List<nu> _snowmanx = null;
      float _snowmanxx = Float.MAX_VALUE;

      for (int _snowmanxxx : b) {
         List<nu> _snowmanxxxx = _snowman.b(_snowman, _snowman - _snowmanxxx, ob.a);
         float _snowmanxxxxx = Math.abs(a(_snowman, _snowmanxxxx) - (float)_snowman);
         if (_snowmanxxxxx <= 10.0F) {
            return _snowmanxxxx;
         }

         if (_snowmanxxxxx < _snowmanxx) {
            _snowmanxx = _snowmanxxxxx;
            _snowmanx = _snowmanxxxx;
         }
      }

      return _snowmanx;
   }

   @Nullable
   private dpg a(y var1) {
      do {
         _snowman = _snowman.b();
      } while (_snowman != null && _snowman.c() == null);

      return _snowman != null && _snowman.c() != null ? this.c.b(_snowman) : null;
   }

   public void a(dfm var1, int var2, int var3, boolean var4) {
      if (this.m != null) {
         int _snowman = _snowman + this.m.p + 13;
         int _snowmanx = _snowman + this.m.p + 26 + 4;
         int _snowmanxx = _snowman + this.m.q + 13;
         int _snowmanxxx = _snowman + this.p + 13;
         int _snowmanxxxx = _snowman + this.q + 13;
         int _snowmanxxxxx = _snowman ? -16777216 : -1;
         if (_snowman) {
            this.a(_snowman, _snowmanx, _snowman, _snowmanxx - 1, _snowmanxxxxx);
            this.a(_snowman, _snowmanx + 1, _snowman, _snowmanxx, _snowmanxxxxx);
            this.a(_snowman, _snowmanx, _snowman, _snowmanxx + 1, _snowmanxxxxx);
            this.a(_snowman, _snowmanxxx, _snowmanx - 1, _snowmanxxxx - 1, _snowmanxxxxx);
            this.a(_snowman, _snowmanxxx, _snowmanx - 1, _snowmanxxxx, _snowmanxxxxx);
            this.a(_snowman, _snowmanxxx, _snowmanx - 1, _snowmanxxxx + 1, _snowmanxxxxx);
            this.b(_snowman, _snowmanx - 1, _snowmanxxxx, _snowmanxx, _snowmanxxxxx);
            this.b(_snowman, _snowmanx + 1, _snowmanxxxx, _snowmanxx, _snowmanxxxxx);
         } else {
            this.a(_snowman, _snowmanx, _snowman, _snowmanxx, _snowmanxxxxx);
            this.a(_snowman, _snowmanxxx, _snowmanx, _snowmanxxxx, _snowmanxxxxx);
            this.b(_snowman, _snowmanx, _snowmanxxxx, _snowmanxx, _snowmanxxxxx);
         }
      }

      for (dpg _snowman : this.n) {
         _snowman.a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   public void a(dfm var1, int var2, int var3) {
      if (!this.e.j() || this.o != null && this.o.a()) {
         float _snowman = this.o == null ? 0.0F : this.o.c();
         dph _snowmanx;
         if (_snowman >= 1.0F) {
            _snowmanx = dph.a;
         } else {
            _snowmanx = dph.b;
         }

         this.l.M().a(a);
         this.b(_snowman, _snowman + this.p + 3, _snowman + this.q, this.e.e().b(), 128 + _snowmanx.a() * 26, 26, 26);
         this.l.ad().c(this.e.c(), _snowman + this.p + 8, _snowman + this.q + 5);
      }

      for (dpg _snowman : this.n) {
         _snowman.a(_snowman, _snowman, _snowman);
      }
   }

   public void a(aa var1) {
      this.o = _snowman;
   }

   public void a(dpg var1) {
      this.n.add(_snowman);
   }

   public void a(dfm var1, int var2, int var3, float var4, int var5, int var6) {
      boolean _snowman = _snowman + _snowman + this.p + this.j + 26 >= this.c.f().k;
      String _snowmanx = this.o == null ? null : this.o.d();
      int _snowmanxx = _snowmanx == null ? 0 : this.l.g.b(_snowmanx);
      boolean _snowmanxxx = 113 - _snowman - this.q - 26 <= 6 + this.k.size() * 9;
      float _snowmanxxxx = this.o == null ? 0.0F : this.o.c();
      int _snowmanxxxxx = afm.d(_snowmanxxxx * (float)this.j);
      dph _snowmanxxxxxx;
      dph _snowmanxxxxxxx;
      dph _snowmanxxxxxxxx;
      if (_snowmanxxxx >= 1.0F) {
         _snowmanxxxxx = this.j / 2;
         _snowmanxxxxxx = dph.a;
         _snowmanxxxxxxx = dph.a;
         _snowmanxxxxxxxx = dph.a;
      } else if (_snowmanxxxxx < 2) {
         _snowmanxxxxx = this.j / 2;
         _snowmanxxxxxx = dph.b;
         _snowmanxxxxxxx = dph.b;
         _snowmanxxxxxxxx = dph.b;
      } else if (_snowmanxxxxx > this.j - 2) {
         _snowmanxxxxx = this.j / 2;
         _snowmanxxxxxx = dph.a;
         _snowmanxxxxxxx = dph.a;
         _snowmanxxxxxxxx = dph.b;
      } else {
         _snowmanxxxxxx = dph.a;
         _snowmanxxxxxxx = dph.b;
         _snowmanxxxxxxxx = dph.b;
      }

      int _snowmanxxxxxxxxx = this.j - _snowmanxxxxx;
      this.l.M().a(a);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      int _snowmanxxxxxxxxxx = _snowman + this.q;
      int _snowmanxxxxxxxxxxx;
      if (_snowman) {
         _snowmanxxxxxxxxxxx = _snowman + this.p - this.j + 26 + 6;
      } else {
         _snowmanxxxxxxxxxxx = _snowman + this.p;
      }

      int _snowmanxxxxxxxxxxxx = 32 + this.k.size() * 9;
      if (!this.k.isEmpty()) {
         if (_snowmanxxx) {
            this.a(_snowman, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx + 26 - _snowmanxxxxxxxxxxxx, this.j, _snowmanxxxxxxxxxxxx, 10, 200, 26, 0, 52);
         } else {
            this.a(_snowman, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, this.j, _snowmanxxxxxxxxxxxx, 10, 200, 26, 0, 52);
         }
      }

      this.b(_snowman, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx, 0, _snowmanxxxxxx.a() * 26, _snowmanxxxxx, 26);
      this.b(_snowman, _snowmanxxxxxxxxxxx + _snowmanxxxxx, _snowmanxxxxxxxxxx, 200 - _snowmanxxxxxxxxx, _snowmanxxxxxxx.a() * 26, _snowmanxxxxxxxxx, 26);
      this.b(_snowman, _snowman + this.p + 3, _snowman + this.q, this.e.e().b(), 128 + _snowmanxxxxxxxx.a() * 26, 26, 26);
      if (_snowman) {
         this.l.g.a(_snowman, this.i, (float)(_snowmanxxxxxxxxxxx + 5), (float)(_snowman + this.q + 9), -1);
         if (_snowmanx != null) {
            this.l.g.a(_snowman, _snowmanx, (float)(_snowman + this.p - _snowmanxx), (float)(_snowman + this.q + 9), -1);
         }
      } else {
         this.l.g.a(_snowman, this.i, (float)(_snowman + this.p + 32), (float)(_snowman + this.q + 9), -1);
         if (_snowmanx != null) {
            this.l.g.a(_snowman, _snowmanx, (float)(_snowman + this.p + this.j - _snowmanxx - 5), (float)(_snowman + this.q + 9), -1);
         }
      }

      if (_snowmanxxx) {
         for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < this.k.size(); _snowmanxxxxxxxxxxxxx++) {
            this.l.g.b(_snowman, this.k.get(_snowmanxxxxxxxxxxxxx), (float)(_snowmanxxxxxxxxxxx + 5), (float)(_snowmanxxxxxxxxxx + 26 - _snowmanxxxxxxxxxxxx + 7 + _snowmanxxxxxxxxxxxxx * 9), -5592406);
         }
      } else {
         for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < this.k.size(); _snowmanxxxxxxxxxxxxx++) {
            this.l.g.b(_snowman, this.k.get(_snowmanxxxxxxxxxxxxx), (float)(_snowmanxxxxxxxxxxx + 5), (float)(_snowman + this.q + 9 + 17 + _snowmanxxxxxxxxxxxxx * 9), -5592406);
         }
      }

      this.l.ad().c(this.e.c(), _snowman + this.p + 8, _snowman + this.q + 5);
   }

   protected void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
      this.b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.a(_snowman, _snowman + _snowman, _snowman, _snowman - _snowman - _snowman, _snowman, _snowman + _snowman, _snowman, _snowman - _snowman - _snowman, _snowman);
      this.b(_snowman, _snowman + _snowman - _snowman, _snowman, _snowman + _snowman - _snowman, _snowman, _snowman, _snowman);
      this.b(_snowman, _snowman, _snowman + _snowman - _snowman, _snowman, _snowman + _snowman - _snowman, _snowman, _snowman);
      this.a(_snowman, _snowman + _snowman, _snowman + _snowman - _snowman, _snowman - _snowman - _snowman, _snowman, _snowman + _snowman, _snowman + _snowman - _snowman, _snowman - _snowman - _snowman, _snowman);
      this.b(_snowman, _snowman + _snowman - _snowman, _snowman + _snowman - _snowman, _snowman + _snowman - _snowman, _snowman + _snowman - _snowman, _snowman, _snowman);
      this.a(_snowman, _snowman, _snowman + _snowman, _snowman, _snowman - _snowman - _snowman, _snowman, _snowman + _snowman, _snowman, _snowman - _snowman - _snowman);
      this.a(_snowman, _snowman + _snowman, _snowman + _snowman, _snowman - _snowman - _snowman, _snowman - _snowman - _snowman, _snowman + _snowman, _snowman + _snowman, _snowman - _snowman - _snowman, _snowman - _snowman - _snowman);
      this.a(_snowman, _snowman + _snowman - _snowman, _snowman + _snowman, _snowman, _snowman - _snowman - _snowman, _snowman + _snowman - _snowman, _snowman + _snowman, _snowman, _snowman - _snowman - _snowman);
   }

   protected void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      int _snowman = 0;

      while (_snowman < _snowman) {
         int _snowmanx = _snowman + _snowman;
         int _snowmanxx = Math.min(_snowman, _snowman - _snowman);
         int _snowmanxxx = 0;

         while (_snowmanxxx < _snowman) {
            int _snowmanxxxx = _snowman + _snowmanxxx;
            int _snowmanxxxxx = Math.min(_snowman, _snowman - _snowmanxxx);
            this.b(_snowman, _snowmanx, _snowmanxxxx, _snowman, _snowman, _snowmanxx, _snowmanxxxxx);
            _snowmanxxx += _snowman;
         }

         _snowman += _snowman;
      }
   }

   public boolean a(int var1, int var2, int var3, int var4) {
      if (!this.e.j() || this.o != null && this.o.a()) {
         int _snowman = _snowman + this.p;
         int _snowmanx = _snowman + 26;
         int _snowmanxx = _snowman + this.q;
         int _snowmanxxx = _snowmanxx + 26;
         return _snowman >= _snowman && _snowman <= _snowmanx && _snowman >= _snowmanxx && _snowman <= _snowmanxxx;
      } else {
         return false;
      }
   }

   public void b() {
      if (this.m == null && this.d.b() != null) {
         this.m = this.a(this.d);
         if (this.m != null) {
            this.m.a(this);
         }
      }
   }

   public int c() {
      return this.q;
   }

   public int d() {
      return this.p;
   }
}
