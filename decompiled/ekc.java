import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nullable;

public class ekc implements AutoCloseable {
   private final ekb b;
   private final ekc.a c;
   private final elc d;
   protected final det[] a;
   private final int[] e;
   private final int[] f;
   @Nullable
   private final ekc.b g;
   private final int h;
   private final int i;
   private final float j;
   private final float k;
   private final float l;
   private final float m;
   private int n;
   private int o;

   protected ekc(ekb var1, ekc.a var2, int var3, int var4, int var5, int var6, int var7, det var8) {
      this.b = _snowman;
      elc _snowman = _snowman.d;
      int _snowmanx = _snowman.b;
      int _snowmanxx = _snowman.c;
      this.h = _snowman;
      this.i = _snowman;
      this.j = (float)_snowman / (float)_snowman;
      this.k = (float)(_snowman + _snowmanx) / (float)_snowman;
      this.l = (float)_snowman / (float)_snowman;
      this.m = (float)(_snowman + _snowmanxx) / (float)_snowman;
      int _snowmanxxx = _snowman.a() / _snowman.b(_snowmanx);
      int _snowmanxxxx = _snowman.b() / _snowman.a(_snowmanxx);
      if (_snowman.a() > 0) {
         int _snowmanxxxxx = _snowman.d().stream().max(Integer::compareTo).get() + 1;
         this.e = new int[_snowmanxxxxx];
         this.f = new int[_snowmanxxxxx];
         Arrays.fill(this.e, -1);
         Arrays.fill(this.f, -1);

         for (int _snowmanxxxxxx : _snowman.d()) {
            if (_snowmanxxxxxx >= _snowmanxxx * _snowmanxxxx) {
               throw new RuntimeException("invalid frameindex " + _snowmanxxxxxx);
            }

            int _snowmanxxxxxxx = _snowmanxxxxxx / _snowmanxxx;
            int _snowmanxxxxxxxx = _snowmanxxxxxx % _snowmanxxx;
            this.e[_snowmanxxxxxx] = _snowmanxxxxxxxx;
            this.f[_snowmanxxxxxx] = _snowmanxxxxxxx;
         }
      } else {
         List<elb> _snowmanxxxxx = Lists.newArrayList();
         int _snowmanxxxxxx = _snowmanxxx * _snowmanxxxx;
         this.e = new int[_snowmanxxxxxx];
         this.f = new int[_snowmanxxxxxx];

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxx; _snowmanxxxxxxx++) {
            for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxx; _snowmanxxxxxxxx++) {
               int _snowmanxxxxxxxxx = _snowmanxxxxxxx * _snowmanxxx + _snowmanxxxxxxxx;
               this.e[_snowmanxxxxxxxxx] = _snowmanxxxxxxxx;
               this.f[_snowmanxxxxxxxxx] = _snowmanxxxxxxx;
               _snowmanxxxxx.add(new elb(_snowmanxxxxxxxxx, -1));
            }
         }

         _snowman = new elc(_snowmanxxxxx, _snowmanx, _snowmanxx, _snowman.b(), _snowman.c());
      }

      this.c = new ekc.a(_snowman.a, _snowmanx, _snowmanxx, _snowman);
      this.d = _snowman;

      try {
         try {
            this.a = eju.a(_snowman, _snowman);
         } catch (Throwable var19) {
            l _snowmanxxxxx = l.a(var19, "Generating mipmaps for frame");
            m _snowmanxxxxxx = _snowmanxxxxx.a("Frame being iterated");
            _snowmanxxxxxx.a("First frame", () -> {
               StringBuilder _snowmanxxxxxxx = new StringBuilder();
               if (_snowmanxxxxxxx.length() > 0) {
                  _snowmanxxxxxxx.append(", ");
               }

               _snowmanxxxxxxx.append(_snowman.a()).append("x").append(_snowman.b());
               return _snowmanxxxxxxx.toString();
            });
            throw new u(_snowmanxxxxx);
         }
      } catch (Throwable var20) {
         l _snowmanxxxxx = l.a(var20, "Applying mipmap");
         m _snowmanxxxxxx = _snowmanxxxxx.a("Sprite being mipmapped");
         _snowmanxxxxxx.a("Sprite name", () -> this.l().toString());
         _snowmanxxxxxx.a("Sprite size", () -> this.f() + " x " + this.g());
         _snowmanxxxxxx.a("Sprite frames", () -> this.n() + " frames");
         _snowmanxxxxxx.a("Mipmap levels", _snowman);
         throw new u(_snowmanxxxxx);
      }

      if (_snowman.c()) {
         this.g = new ekc.b(_snowman, _snowman);
      } else {
         this.g = null;
      }
   }

   private void a(int var1) {
      int _snowman = this.e[_snowman] * this.c.b;
      int _snowmanx = this.f[_snowman] * this.c.c;
      this.a(_snowman, _snowmanx, this.a);
   }

   private void a(int var1, int var2, det[] var3) {
      for (int _snowman = 0; _snowman < this.a.length; _snowman++) {
         _snowman[_snowman].a(_snowman, this.h >> _snowman, this.i >> _snowman, _snowman >> _snowman, _snowman >> _snowman, this.c.b >> _snowman, this.c.c >> _snowman, this.a.length > 1, false);
      }
   }

   public int f() {
      return this.c.b;
   }

   public int g() {
      return this.c.c;
   }

   public float h() {
      return this.j;
   }

   public float i() {
      return this.k;
   }

   public float a(double var1) {
      float _snowman = this.k - this.j;
      return this.j + _snowman * (float)_snowman / 16.0F;
   }

   public float j() {
      return this.l;
   }

   public float k() {
      return this.m;
   }

   public float b(double var1) {
      float _snowman = this.m - this.l;
      return this.l + _snowman * (float)_snowman / 16.0F;
   }

   public vk l() {
      return this.c.a;
   }

   public ekb m() {
      return this.b;
   }

   public int n() {
      return this.e.length;
   }

   @Override
   public void close() {
      for (det _snowman : this.a) {
         if (_snowman != null) {
            _snowman.close();
         }
      }

      if (this.g != null) {
         this.g.close();
      }
   }

   @Override
   public String toString() {
      int _snowman = this.e.length;
      return "TextureAtlasSprite{name='"
         + this.c.a
         + '\''
         + ", frameCount="
         + _snowman
         + ", x="
         + this.h
         + ", y="
         + this.i
         + ", height="
         + this.c.c
         + ", width="
         + this.c.b
         + ", u0="
         + this.j
         + ", u1="
         + this.k
         + ", v0="
         + this.l
         + ", v1="
         + this.m
         + '}';
   }

   public boolean a(int var1, int var2, int var3) {
      return (this.a[0].a(_snowman + this.e[_snowman] * this.c.b, _snowman + this.f[_snowman] * this.c.c) >> 24 & 0xFF) == 0;
   }

   public void o() {
      this.a(0);
   }

   private float a() {
      float _snowman = (float)this.c.b / (this.k - this.j);
      float _snowmanx = (float)this.c.c / (this.m - this.l);
      return Math.max(_snowmanx, _snowman);
   }

   public float p() {
      return 4.0F / this.a();
   }

   public void q() {
      this.o++;
      if (this.o >= this.d.c(this.n)) {
         int _snowman = this.d.e(this.n);
         int _snowmanx = this.d.a() == 0 ? this.n() : this.d.a();
         this.n = (this.n + 1) % _snowmanx;
         this.o = 0;
         int _snowmanxx = this.d.e(this.n);
         if (_snowman != _snowmanxx && _snowmanxx >= 0 && _snowmanxx < this.n()) {
            this.a(_snowmanxx);
         }
      } else if (this.g != null) {
         if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> _snowman.a());
         } else {
            this.g.a();
         }
      }
   }

   public boolean r() {
      return this.d.a() > 1;
   }

   public dfq a(dfq var1) {
      return new eas(_snowman, this);
   }

   public static final class a {
      private final vk a;
      private final int b;
      private final int c;
      private final elc d;

      public a(vk var1, int var2, int var3, elc var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      public vk a() {
         return this.a;
      }

      public int b() {
         return this.b;
      }

      public int c() {
         return this.c;
      }
   }

   final class b implements AutoCloseable {
      private final det[] b;

      private b(ekc.a var2, int var3) {
         this.b = new det[_snowman + 1];

         for (int _snowman = 0; _snowman < this.b.length; _snowman++) {
            int _snowmanx = _snowman.b >> _snowman;
            int _snowmanxx = _snowman.c >> _snowman;
            if (this.b[_snowman] == null) {
               this.b[_snowman] = new det(_snowmanx, _snowmanxx, false);
            }
         }
      }

      private void a() {
         double _snowman = 1.0 - (double)ekc.this.o / (double)ekc.this.d.c(ekc.this.n);
         int _snowmanx = ekc.this.d.e(ekc.this.n);
         int _snowmanxx = ekc.this.d.a() == 0 ? ekc.this.n() : ekc.this.d.a();
         int _snowmanxxx = ekc.this.d.e((ekc.this.n + 1) % _snowmanxx);
         if (_snowmanx != _snowmanxxx && _snowmanxxx >= 0 && _snowmanxxx < ekc.this.n()) {
            for (int _snowmanxxxx = 0; _snowmanxxxx < this.b.length; _snowmanxxxx++) {
               int _snowmanxxxxx = ekc.this.c.b >> _snowmanxxxx;
               int _snowmanxxxxxx = ekc.this.c.c >> _snowmanxxxx;

               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxx++) {
                  for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxx++) {
                     int _snowmanxxxxxxxxx = this.a(_snowmanx, _snowmanxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxx);
                     int _snowmanxxxxxxxxxx = this.a(_snowmanxxx, _snowmanxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxx);
                     int _snowmanxxxxxxxxxxx = this.a(_snowman, _snowmanxxxxxxxxx >> 16 & 0xFF, _snowmanxxxxxxxxxx >> 16 & 0xFF);
                     int _snowmanxxxxxxxxxxxx = this.a(_snowman, _snowmanxxxxxxxxx >> 8 & 0xFF, _snowmanxxxxxxxxxx >> 8 & 0xFF);
                     int _snowmanxxxxxxxxxxxxx = this.a(_snowman, _snowmanxxxxxxxxx & 0xFF, _snowmanxxxxxxxxxx & 0xFF);
                     this.b[_snowmanxxxx].a(_snowmanxxxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx & 0xFF000000 | _snowmanxxxxxxxxxxx << 16 | _snowmanxxxxxxxxxxxx << 8 | _snowmanxxxxxxxxxxxxx);
                  }
               }
            }

            ekc.this.a(0, 0, this.b);
         }
      }

      private int a(int var1, int var2, int var3, int var4) {
         return ekc.this.a[_snowman].a(_snowman + (ekc.this.e[_snowman] * ekc.this.c.b >> _snowman), _snowman + (ekc.this.f[_snowman] * ekc.this.c.c >> _snowman));
      }

      private int a(double var1, int var3, int var4) {
         return (int)(_snowman * (double)_snowman + (1.0 - _snowman) * (double)_snowman);
      }

      @Override
      public void close() {
         for (det _snowman : this.b) {
            if (_snowman != null) {
               _snowman.close();
            }
         }
      }
   }
}
