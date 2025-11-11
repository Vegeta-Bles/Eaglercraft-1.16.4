import com.google.common.collect.Lists;
import java.util.List;

public class cee {
   private final brx a;
   private final fx b;
   private final boolean c;
   private final fx d;
   private final gc e;
   private final List<fx> f = Lists.newArrayList();
   private final List<fx> g = Lists.newArrayList();
   private final gc h;

   public cee(brx var1, fx var2, gc var3, boolean var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.h = _snowman;
      this.c = _snowman;
      if (_snowman) {
         this.e = _snowman;
         this.d = _snowman.a(_snowman);
      } else {
         this.e = _snowman.f();
         this.d = _snowman.a(_snowman, 2);
      }
   }

   public boolean a() {
      this.f.clear();
      this.g.clear();
      ceh _snowman = this.a.d_(this.d);
      if (!cea.a(_snowman, this.a, this.d, this.e, false, this.h)) {
         if (this.c && _snowman.k() == cvc.b) {
            this.g.add(this.d);
            return true;
         } else {
            return false;
         }
      } else if (!this.a(this.d, this.e)) {
         return false;
      } else {
         for (int _snowmanx = 0; _snowmanx < this.f.size(); _snowmanx++) {
            fx _snowmanxx = this.f.get(_snowmanx);
            if (a(this.a.d_(_snowmanxx).b()) && !this.a(_snowmanxx)) {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean a(buo var0) {
      return _snowman == bup.gn || _snowman == bup.ne;
   }

   private static boolean a(buo var0, buo var1) {
      if (_snowman == bup.ne && _snowman == bup.gn) {
         return false;
      } else {
         return _snowman == bup.gn && _snowman == bup.ne ? false : a(_snowman) || a(_snowman);
      }
   }

   private boolean a(fx var1, gc var2) {
      ceh _snowman = this.a.d_(_snowman);
      buo _snowmanx = _snowman.b();
      if (_snowman.g()) {
         return true;
      } else if (!cea.a(_snowman, this.a, _snowman, this.e, false, _snowman)) {
         return true;
      } else if (_snowman.equals(this.b)) {
         return true;
      } else if (this.f.contains(_snowman)) {
         return true;
      } else {
         int _snowmanxx = 1;
         if (_snowmanxx + this.f.size() > 12) {
            return false;
         } else {
            while (a(_snowmanx)) {
               fx _snowmanxxx = _snowman.a(this.e.f(), _snowmanxx);
               buo _snowmanxxxx = _snowmanx;
               _snowman = this.a.d_(_snowmanxxx);
               _snowmanx = _snowman.b();
               if (_snowman.g() || !a(_snowmanxxxx, _snowmanx) || !cea.a(_snowman, this.a, _snowmanxxx, this.e, false, this.e.f()) || _snowmanxxx.equals(this.b)) {
                  break;
               }

               if (++_snowmanxx + this.f.size() > 12) {
                  return false;
               }
            }

            int _snowmanxxxxx = 0;

            for (int _snowmanxxxxxx = _snowmanxx - 1; _snowmanxxxxxx >= 0; _snowmanxxxxxx--) {
               this.f.add(_snowman.a(this.e.f(), _snowmanxxxxxx));
               _snowmanxxxxx++;
            }

            int _snowmanxxxxxx = 1;

            while (true) {
               fx _snowmanxxxxxxx = _snowman.a(this.e, _snowmanxxxxxx);
               int _snowmanxxxxxxxx = this.f.indexOf(_snowmanxxxxxxx);
               if (_snowmanxxxxxxxx > -1) {
                  this.a(_snowmanxxxxx, _snowmanxxxxxxxx);

                  for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx <= _snowmanxxxxxxxx + _snowmanxxxxx; _snowmanxxxxxxxxx++) {
                     fx _snowmanxxxxxxxxxx = this.f.get(_snowmanxxxxxxxxx);
                     if (a(this.a.d_(_snowmanxxxxxxxxxx).b()) && !this.a(_snowmanxxxxxxxxxx)) {
                        return false;
                     }
                  }

                  return true;
               }

               _snowman = this.a.d_(_snowmanxxxxxxx);
               if (_snowman.g()) {
                  return true;
               }

               if (!cea.a(_snowman, this.a, _snowmanxxxxxxx, this.e, true, this.e) || _snowmanxxxxxxx.equals(this.b)) {
                  return false;
               }

               if (_snowman.k() == cvc.b) {
                  this.g.add(_snowmanxxxxxxx);
                  return true;
               }

               if (this.f.size() >= 12) {
                  return false;
               }

               this.f.add(_snowmanxxxxxxx);
               _snowmanxxxxx++;
               _snowmanxxxxxx++;
            }
         }
      }
   }

   private void a(int var1, int var2) {
      List<fx> _snowman = Lists.newArrayList();
      List<fx> _snowmanx = Lists.newArrayList();
      List<fx> _snowmanxx = Lists.newArrayList();
      _snowman.addAll(this.f.subList(0, _snowman));
      _snowmanx.addAll(this.f.subList(this.f.size() - _snowman, this.f.size()));
      _snowmanxx.addAll(this.f.subList(_snowman, this.f.size() - _snowman));
      this.f.clear();
      this.f.addAll(_snowman);
      this.f.addAll(_snowmanx);
      this.f.addAll(_snowmanxx);
   }

   private boolean a(fx var1) {
      ceh _snowman = this.a.d_(_snowman);

      for (gc _snowmanx : gc.values()) {
         if (_snowmanx.n() != this.e.n()) {
            fx _snowmanxx = _snowman.a(_snowmanx);
            ceh _snowmanxxx = this.a.d_(_snowmanxx);
            if (a(_snowmanxxx.b(), _snowman.b()) && !this.a(_snowmanxx, _snowmanx)) {
               return false;
            }
         }
      }

      return true;
   }

   public List<fx> c() {
      return this.f;
   }

   public List<fx> d() {
      return this.g;
   }
}
