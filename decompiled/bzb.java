import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class bzb {
   private final brx a;
   private final fx b;
   private final bug c;
   private ceh d;
   private final boolean e;
   private final List<fx> f = Lists.newArrayList();

   public bzb(brx var1, fx var2, ceh var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.d = _snowman;
      this.c = (bug)_snowman.b();
      cfk _snowman = _snowman.c(this.c.d());
      this.e = this.c.c();
      this.a(_snowman);
   }

   public List<fx> a() {
      return this.f;
   }

   private void a(cfk var1) {
      this.f.clear();
      switch (_snowman) {
         case a:
            this.f.add(this.b.d());
            this.f.add(this.b.e());
            break;
         case b:
            this.f.add(this.b.f());
            this.f.add(this.b.g());
            break;
         case c:
            this.f.add(this.b.f());
            this.f.add(this.b.g().b());
            break;
         case d:
            this.f.add(this.b.f().b());
            this.f.add(this.b.g());
            break;
         case e:
            this.f.add(this.b.d().b());
            this.f.add(this.b.e());
            break;
         case f:
            this.f.add(this.b.d());
            this.f.add(this.b.e().b());
            break;
         case g:
            this.f.add(this.b.g());
            this.f.add(this.b.e());
            break;
         case h:
            this.f.add(this.b.f());
            this.f.add(this.b.e());
            break;
         case i:
            this.f.add(this.b.f());
            this.f.add(this.b.d());
            break;
         case j:
            this.f.add(this.b.g());
            this.f.add(this.b.d());
      }
   }

   private void d() {
      for (int _snowman = 0; _snowman < this.f.size(); _snowman++) {
         bzb _snowmanx = this.b(this.f.get(_snowman));
         if (_snowmanx != null && _snowmanx.a(this)) {
            this.f.set(_snowman, _snowmanx.b);
         } else {
            this.f.remove(_snowman--);
         }
      }
   }

   private boolean a(fx var1) {
      return bug.a(this.a, _snowman) || bug.a(this.a, _snowman.b()) || bug.a(this.a, _snowman.c());
   }

   @Nullable
   private bzb b(fx var1) {
      ceh _snowman = this.a.d_(_snowman);
      if (bug.g(_snowman)) {
         return new bzb(this.a, _snowman, _snowman);
      } else {
         fx var2 = _snowman.b();
         _snowman = this.a.d_(var2);
         if (bug.g(_snowman)) {
            return new bzb(this.a, var2, _snowman);
         } else {
            var2 = _snowman.c();
            _snowman = this.a.d_(var2);
            return bug.g(_snowman) ? new bzb(this.a, var2, _snowman) : null;
         }
      }
   }

   private boolean a(bzb var1) {
      return this.c(_snowman.b);
   }

   private boolean c(fx var1) {
      for (int _snowman = 0; _snowman < this.f.size(); _snowman++) {
         fx _snowmanx = this.f.get(_snowman);
         if (_snowmanx.u() == _snowman.u() && _snowmanx.w() == _snowman.w()) {
            return true;
         }
      }

      return false;
   }

   protected int b() {
      int _snowman = 0;

      for (gc _snowmanx : gc.c.a) {
         if (this.a(this.b.a(_snowmanx))) {
            _snowman++;
         }
      }

      return _snowman;
   }

   private boolean b(bzb var1) {
      return this.a(_snowman) || this.f.size() != 2;
   }

   private void c(bzb var1) {
      this.f.add(_snowman.b);
      fx _snowman = this.b.d();
      fx _snowmanx = this.b.e();
      fx _snowmanxx = this.b.f();
      fx _snowmanxxx = this.b.g();
      boolean _snowmanxxxx = this.c(_snowman);
      boolean _snowmanxxxxx = this.c(_snowmanx);
      boolean _snowmanxxxxxx = this.c(_snowmanxx);
      boolean _snowmanxxxxxxx = this.c(_snowmanxxx);
      cfk _snowmanxxxxxxxx = null;
      if (_snowmanxxxx || _snowmanxxxxx) {
         _snowmanxxxxxxxx = cfk.a;
      }

      if (_snowmanxxxxxx || _snowmanxxxxxxx) {
         _snowmanxxxxxxxx = cfk.b;
      }

      if (!this.e) {
         if (_snowmanxxxxx && _snowmanxxxxxxx && !_snowmanxxxx && !_snowmanxxxxxx) {
            _snowmanxxxxxxxx = cfk.g;
         }

         if (_snowmanxxxxx && _snowmanxxxxxx && !_snowmanxxxx && !_snowmanxxxxxxx) {
            _snowmanxxxxxxxx = cfk.h;
         }

         if (_snowmanxxxx && _snowmanxxxxxx && !_snowmanxxxxx && !_snowmanxxxxxxx) {
            _snowmanxxxxxxxx = cfk.i;
         }

         if (_snowmanxxxx && _snowmanxxxxxxx && !_snowmanxxxxx && !_snowmanxxxxxx) {
            _snowmanxxxxxxxx = cfk.j;
         }
      }

      if (_snowmanxxxxxxxx == cfk.a) {
         if (bug.a(this.a, _snowman.b())) {
            _snowmanxxxxxxxx = cfk.e;
         }

         if (bug.a(this.a, _snowmanx.b())) {
            _snowmanxxxxxxxx = cfk.f;
         }
      }

      if (_snowmanxxxxxxxx == cfk.b) {
         if (bug.a(this.a, _snowmanxxx.b())) {
            _snowmanxxxxxxxx = cfk.c;
         }

         if (bug.a(this.a, _snowmanxx.b())) {
            _snowmanxxxxxxxx = cfk.d;
         }
      }

      if (_snowmanxxxxxxxx == null) {
         _snowmanxxxxxxxx = cfk.a;
      }

      this.d = this.d.a(this.c.d(), _snowmanxxxxxxxx);
      this.a.a(this.b, this.d, 3);
   }

   private boolean d(fx var1) {
      bzb _snowman = this.b(_snowman);
      if (_snowman == null) {
         return false;
      } else {
         _snowman.d();
         return _snowman.b(this);
      }
   }

   public bzb a(boolean var1, boolean var2, cfk var3) {
      fx _snowman = this.b.d();
      fx _snowmanx = this.b.e();
      fx _snowmanxx = this.b.f();
      fx _snowmanxxx = this.b.g();
      boolean _snowmanxxxx = this.d(_snowman);
      boolean _snowmanxxxxx = this.d(_snowmanx);
      boolean _snowmanxxxxxx = this.d(_snowmanxx);
      boolean _snowmanxxxxxxx = this.d(_snowmanxxx);
      cfk _snowmanxxxxxxxx = null;
      boolean _snowmanxxxxxxxxx = _snowmanxxxx || _snowmanxxxxx;
      boolean _snowmanxxxxxxxxxx = _snowmanxxxxxx || _snowmanxxxxxxx;
      if (_snowmanxxxxxxxxx && !_snowmanxxxxxxxxxx) {
         _snowmanxxxxxxxx = cfk.a;
      }

      if (_snowmanxxxxxxxxxx && !_snowmanxxxxxxxxx) {
         _snowmanxxxxxxxx = cfk.b;
      }

      boolean _snowmanxxxxxxxxxxx = _snowmanxxxxx && _snowmanxxxxxxx;
      boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxx && _snowmanxxxxxx;
      boolean _snowmanxxxxxxxxxxxxx = _snowmanxxxx && _snowmanxxxxxxx;
      boolean _snowmanxxxxxxxxxxxxxx = _snowmanxxxx && _snowmanxxxxxx;
      if (!this.e) {
         if (_snowmanxxxxxxxxxxx && !_snowmanxxxx && !_snowmanxxxxxx) {
            _snowmanxxxxxxxx = cfk.g;
         }

         if (_snowmanxxxxxxxxxxxx && !_snowmanxxxx && !_snowmanxxxxxxx) {
            _snowmanxxxxxxxx = cfk.h;
         }

         if (_snowmanxxxxxxxxxxxxxx && !_snowmanxxxxx && !_snowmanxxxxxxx) {
            _snowmanxxxxxxxx = cfk.i;
         }

         if (_snowmanxxxxxxxxxxxxx && !_snowmanxxxxx && !_snowmanxxxxxx) {
            _snowmanxxxxxxxx = cfk.j;
         }
      }

      if (_snowmanxxxxxxxx == null) {
         if (_snowmanxxxxxxxxx && _snowmanxxxxxxxxxx) {
            _snowmanxxxxxxxx = _snowman;
         } else if (_snowmanxxxxxxxxx) {
            _snowmanxxxxxxxx = cfk.a;
         } else if (_snowmanxxxxxxxxxx) {
            _snowmanxxxxxxxx = cfk.b;
         }

         if (!this.e) {
            if (_snowman) {
               if (_snowmanxxxxxxxxxxx) {
                  _snowmanxxxxxxxx = cfk.g;
               }

               if (_snowmanxxxxxxxxxxxx) {
                  _snowmanxxxxxxxx = cfk.h;
               }

               if (_snowmanxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxx = cfk.j;
               }

               if (_snowmanxxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxx = cfk.i;
               }
            } else {
               if (_snowmanxxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxx = cfk.i;
               }

               if (_snowmanxxxxxxxxxxxxx) {
                  _snowmanxxxxxxxx = cfk.j;
               }

               if (_snowmanxxxxxxxxxxxx) {
                  _snowmanxxxxxxxx = cfk.h;
               }

               if (_snowmanxxxxxxxxxxx) {
                  _snowmanxxxxxxxx = cfk.g;
               }
            }
         }
      }

      if (_snowmanxxxxxxxx == cfk.a) {
         if (bug.a(this.a, _snowman.b())) {
            _snowmanxxxxxxxx = cfk.e;
         }

         if (bug.a(this.a, _snowmanx.b())) {
            _snowmanxxxxxxxx = cfk.f;
         }
      }

      if (_snowmanxxxxxxxx == cfk.b) {
         if (bug.a(this.a, _snowmanxxx.b())) {
            _snowmanxxxxxxxx = cfk.c;
         }

         if (bug.a(this.a, _snowmanxx.b())) {
            _snowmanxxxxxxxx = cfk.d;
         }
      }

      if (_snowmanxxxxxxxx == null) {
         _snowmanxxxxxxxx = _snowman;
      }

      this.a(_snowmanxxxxxxxx);
      this.d = this.d.a(this.c.d(), _snowmanxxxxxxxx);
      if (_snowman || this.a.d_(this.b) != this.d) {
         this.a.a(this.b, this.d, 3);

         for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < this.f.size(); _snowmanxxxxxxxxxxxxxxx++) {
            bzb _snowmanxxxxxxxxxxxxxxxx = this.b(this.f.get(_snowmanxxxxxxxxxxxxxxx));
            if (_snowmanxxxxxxxxxxxxxxxx != null) {
               _snowmanxxxxxxxxxxxxxxxx.d();
               if (_snowmanxxxxxxxxxxxxxxxx.b(this)) {
                  _snowmanxxxxxxxxxxxxxxxx.c(this);
               }
            }
         }
      }

      return this;
   }

   public ceh c() {
      return this.d;
   }
}
