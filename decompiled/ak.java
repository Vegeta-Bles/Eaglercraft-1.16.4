import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class ak {
   private final y a;
   private final ak b;
   private final ak c;
   private final int d;
   private final List<ak> e = Lists.newArrayList();
   private ak f;
   private ak g;
   private int h;
   private float i;
   private float j;
   private float k;
   private float l;

   public ak(y var1, @Nullable ak var2, @Nullable ak var3, int var4, int var5) {
      if (_snowman.c() == null) {
         throw new IllegalArgumentException("Can't position an invisible advancement!");
      } else {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.f = this;
         this.h = _snowman;
         this.i = -1.0F;
         ak _snowman = null;

         for (y _snowmanx : _snowman.e()) {
            _snowman = this.a(_snowmanx, _snowman);
         }
      }
   }

   @Nullable
   private ak a(y var1, @Nullable ak var2) {
      if (_snowman.c() != null) {
         _snowman = new ak(_snowman, this, _snowman, this.e.size() + 1, this.h + 1);
         this.e.add(_snowman);
      } else {
         for (y _snowman : _snowman.e()) {
            _snowman = this.a(_snowman, _snowman);
         }
      }

      return _snowman;
   }

   private void a() {
      if (this.e.isEmpty()) {
         if (this.c != null) {
            this.i = this.c.i + 1.0F;
         } else {
            this.i = 0.0F;
         }
      } else {
         ak _snowman = null;

         for (ak _snowmanx : this.e) {
            _snowmanx.a();
            _snowman = _snowmanx.a(_snowman == null ? _snowmanx : _snowman);
         }

         this.b();
         float _snowmanx = (this.e.get(0).i + this.e.get(this.e.size() - 1).i) / 2.0F;
         if (this.c != null) {
            this.i = this.c.i + 1.0F;
            this.j = this.i - _snowmanx;
         } else {
            this.i = _snowmanx;
         }
      }
   }

   private float a(float var1, int var2, float var3) {
      this.i += _snowman;
      this.h = _snowman;
      if (this.i < _snowman) {
         _snowman = this.i;
      }

      for (ak _snowman : this.e) {
         _snowman = _snowman.a(_snowman + this.j, _snowman + 1, _snowman);
      }

      return _snowman;
   }

   private void a(float var1) {
      this.i += _snowman;

      for (ak _snowman : this.e) {
         _snowman.a(_snowman);
      }
   }

   private void b() {
      float _snowman = 0.0F;
      float _snowmanx = 0.0F;

      for (int _snowmanxx = this.e.size() - 1; _snowmanxx >= 0; _snowmanxx--) {
         ak _snowmanxxx = this.e.get(_snowmanxx);
         _snowmanxxx.i += _snowman;
         _snowmanxxx.j += _snowman;
         _snowmanx += _snowmanxxx.k;
         _snowman += _snowmanxxx.l + _snowmanx;
      }
   }

   @Nullable
   private ak c() {
      if (this.g != null) {
         return this.g;
      } else {
         return !this.e.isEmpty() ? this.e.get(0) : null;
      }
   }

   @Nullable
   private ak d() {
      if (this.g != null) {
         return this.g;
      } else {
         return !this.e.isEmpty() ? this.e.get(this.e.size() - 1) : null;
      }
   }

   private ak a(ak var1) {
      if (this.c == null) {
         return _snowman;
      } else {
         ak _snowman = this;
         ak _snowmanx = this;
         ak _snowmanxx = this.c;
         ak _snowmanxxx = this.b.e.get(0);
         float _snowmanxxxx = this.j;
         float _snowmanxxxxx = this.j;
         float _snowmanxxxxxx = _snowmanxx.j;

         float _snowmanxxxxxxx;
         for (_snowmanxxxxxxx = _snowmanxxx.j; _snowmanxx.d() != null && _snowman.c() != null; _snowmanxxxxx += _snowmanx.j) {
            _snowmanxx = _snowmanxx.d();
            _snowman = _snowman.c();
            _snowmanxxx = _snowmanxxx.c();
            _snowmanx = _snowmanx.d();
            _snowmanx.f = this;
            float _snowmanxxxxxxxx = _snowmanxx.i + _snowmanxxxxxx - (_snowman.i + _snowmanxxxx) + 1.0F;
            if (_snowmanxxxxxxxx > 0.0F) {
               _snowmanxx.a(this, _snowman).a(this, _snowmanxxxxxxxx);
               _snowmanxxxx += _snowmanxxxxxxxx;
               _snowmanxxxxx += _snowmanxxxxxxxx;
            }

            _snowmanxxxxxx += _snowmanxx.j;
            _snowmanxxxx += _snowman.j;
            _snowmanxxxxxxx += _snowmanxxx.j;
         }

         if (_snowmanxx.d() != null && _snowmanx.d() == null) {
            _snowmanx.g = _snowmanxx.d();
            _snowmanx.j += _snowmanxxxxxx - _snowmanxxxxx;
         } else {
            if (_snowman.c() != null && _snowmanxxx.c() == null) {
               _snowmanxxx.g = _snowman.c();
               _snowmanxxx.j += _snowmanxxxx - _snowmanxxxxxxx;
            }

            _snowman = this;
         }

         return _snowman;
      }
   }

   private void a(ak var1, float var2) {
      float _snowman = (float)(_snowman.d - this.d);
      if (_snowman != 0.0F) {
         _snowman.k -= _snowman / _snowman;
         this.k += _snowman / _snowman;
      }

      _snowman.l += _snowman;
      _snowman.i += _snowman;
      _snowman.j += _snowman;
   }

   private ak a(ak var1, ak var2) {
      return this.f != null && _snowman.b.e.contains(this.f) ? this.f : _snowman;
   }

   private void e() {
      if (this.a.c() != null) {
         this.a.c().a((float)this.h, this.i);
      }

      if (!this.e.isEmpty()) {
         for (ak _snowman : this.e) {
            _snowman.e();
         }
      }
   }

   public static void a(y var0) {
      if (_snowman.c() == null) {
         throw new IllegalArgumentException("Can't position children of an invisible root!");
      } else {
         ak _snowman = new ak(_snowman, null, null, 1, 0);
         _snowman.a();
         float _snowmanx = _snowman.a(0.0F, 0, _snowman.i);
         if (_snowmanx < 0.0F) {
            _snowman.a(-_snowmanx);
         }

         _snowman.e();
      }
   }
}
