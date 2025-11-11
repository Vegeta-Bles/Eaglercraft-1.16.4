import com.google.common.collect.Lists;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;

public class dkj {
   private final dkj.f a;

   public dkj(dkj.f var1) {
      this.a = _snowman;
   }

   public float a(@Nullable String var1) {
      if (_snowman == null) {
         return 0.0F;
      } else {
         MutableFloat _snowman = new MutableFloat();
         afr.c(_snowman, ob.a, (var2x, var3, var4) -> {
            _snowman.add(this.a.getWidth(var4, var3));
            return true;
         });
         return _snowman.floatValue();
      }
   }

   public float a(nu var1) {
      MutableFloat _snowman = new MutableFloat();
      afr.a(_snowman, ob.a, (var2x, var3, var4) -> {
         _snowman.add(this.a.getWidth(var4, var3));
         return true;
      });
      return _snowman.floatValue();
   }

   public float a(afa var1) {
      MutableFloat _snowman = new MutableFloat();
      _snowman.accept((var2x, var3, var4) -> {
         _snowman.add(this.a.getWidth(var4, var3));
         return true;
      });
      return _snowman.floatValue();
   }

   public int a(String var1, int var2, ob var3) {
      dkj.e _snowman = new dkj.e((float)_snowman);
      afr.a(_snowman, _snowman, _snowman);
      return _snowman.a();
   }

   public String b(String var1, int var2, ob var3) {
      return _snowman.substring(0, this.a(_snowman, _snowman, _snowman));
   }

   public String c(String var1, int var2, ob var3) {
      MutableFloat _snowman = new MutableFloat();
      MutableInt _snowmanx = new MutableInt(_snowman.length());
      afr.b(_snowman, _snowman, (var4x, var5x, var6) -> {
         float _snowmanxx = _snowman.addAndGet(this.a.getWidth(var6, var5x));
         if (_snowmanxx > (float)_snowman) {
            return false;
         } else {
            _snowman.setValue(var4x);
            return true;
         }
      });
      return _snowman.substring(_snowmanx.intValue());
   }

   @Nullable
   public ob a(nu var1, int var2) {
      dkj.e _snowman = new dkj.e((float)_snowman);
      return _snowman.<ob>a((var1x, var2x) -> afr.c(var2x, var1x, _snowman) ? Optional.empty() : Optional.of(var1x), ob.a).orElse(null);
   }

   @Nullable
   public ob a(afa var1, int var2) {
      dkj.e _snowman = new dkj.e((float)_snowman);
      MutableObject<ob> _snowmanx = new MutableObject();
      _snowman.accept((var2x, var3x, var4x) -> {
         if (!_snowman.accept(var2x, var3x, var4x)) {
            _snowman.setValue(var3x);
            return false;
         } else {
            return true;
         }
      });
      return (ob)_snowmanx.getValue();
   }

   public nu a(nu var1, int var2, ob var3) {
      final dkj.e _snowman = new dkj.e((float)_snowman);
      return _snowman.a(new nu.b<nu>() {
         private final djo c = new djo();

         @Override
         public Optional<nu> accept(ob var1, String var2) {
            _snowman.b();
            if (!afr.c(_snowman, _snowman, _snowman)) {
               String _snowman = _snowman.substring(0, _snowman.a());
               if (!_snowman.isEmpty()) {
                  this.c.a(nu.a(_snowman, _snowman));
               }

               return Optional.of(this.c.b());
            } else {
               if (!_snowman.isEmpty()) {
                  this.c.a(nu.a(_snowman, _snowman));
               }

               return Optional.empty();
            }
         }
      }, _snowman).orElse(_snowman);
   }

   public static int a(String var0, int var1, int var2, boolean var3) {
      int _snowman = _snowman;
      boolean _snowmanx = _snowman < 0;
      int _snowmanxx = Math.abs(_snowman);

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
         if (_snowmanx) {
            while (_snowman && _snowman > 0 && (_snowman.charAt(_snowman - 1) == ' ' || _snowman.charAt(_snowman - 1) == '\n')) {
               _snowman--;
            }

            while (_snowman > 0 && _snowman.charAt(_snowman - 1) != ' ' && _snowman.charAt(_snowman - 1) != '\n') {
               _snowman--;
            }
         } else {
            int _snowmanxxxx = _snowman.length();
            int _snowmanxxxxx = _snowman.indexOf(32, _snowman);
            int _snowmanxxxxxx = _snowman.indexOf(10, _snowman);
            if (_snowmanxxxxx == -1 && _snowmanxxxxxx == -1) {
               _snowman = -1;
            } else if (_snowmanxxxxx != -1 && _snowmanxxxxxx != -1) {
               _snowman = Math.min(_snowmanxxxxx, _snowmanxxxxxx);
            } else if (_snowmanxxxxx != -1) {
               _snowman = _snowmanxxxxx;
            } else {
               _snowman = _snowmanxxxxxx;
            }

            if (_snowman == -1) {
               _snowman = _snowmanxxxx;
            } else {
               while (_snowman && _snowman < _snowmanxxxx && (_snowman.charAt(_snowman) == ' ' || _snowman.charAt(_snowman) == '\n')) {
                  _snowman++;
               }
            }
         }
      }

      return _snowman;
   }

   public void a(String var1, int var2, ob var3, boolean var4, dkj.d var5) {
      int _snowman = 0;
      int _snowmanx = _snowman.length();
      ob _snowmanxx = _snowman;

      while (_snowman < _snowmanx) {
         dkj.b _snowmanxxx = new dkj.b((float)_snowman);
         boolean _snowmanxxxx = afr.a(_snowman, _snowman, _snowmanxx, _snowman, _snowmanxxx);
         if (_snowmanxxxx) {
            _snowman.accept(_snowmanxx, _snowman, _snowmanx);
            break;
         }

         int _snowmanxxxxx = _snowmanxxx.a();
         char _snowmanxxxxxx = _snowman.charAt(_snowmanxxxxx);
         int _snowmanxxxxxxx = _snowmanxxxxxx != '\n' && _snowmanxxxxxx != ' ' ? _snowmanxxxxx : _snowmanxxxxx + 1;
         _snowman.accept(_snowmanxx, _snowman, _snowman ? _snowmanxxxxxxx : _snowmanxxxxx);
         _snowman = _snowmanxxxxxxx;
         _snowmanxx = _snowmanxxx.b();
      }
   }

   public List<nu> g(String var1, int var2, ob var3) {
      List<nu> _snowman = Lists.newArrayList();
      this.a(_snowman, _snowman, _snowman, false, (var2x, var3x, var4x) -> _snowman.add(nu.a(_snowman.substring(var3x, var4x), var2x)));
      return _snowman;
   }

   public List<nu> b(nu var1, int var2, ob var3) {
      List<nu> _snowman = Lists.newArrayList();
      this.a(_snowman, _snowman, _snowman, (var1x, var2x) -> _snowman.add(var1x));
      return _snowman;
   }

   public void a(nu var1, int var2, ob var3, BiConsumer<nu, Boolean> var4) {
      List<dkj.c> _snowman = Lists.newArrayList();
      _snowman.a((var1x, var2x) -> {
         if (!var2x.isEmpty()) {
            _snowman.add(new dkj.c(var2x, var1x));
         }

         return Optional.empty();
      }, _snowman);
      dkj.a _snowmanx = new dkj.a(_snowman);
      boolean _snowmanxx = true;
      boolean _snowmanxxx = false;
      boolean _snowmanxxxx = false;

      while (_snowmanxx) {
         _snowmanxx = false;
         dkj.b _snowmanxxxxx = new dkj.b((float)_snowman);

         for (dkj.c _snowmanxxxxxx : _snowmanx.a) {
            boolean _snowmanxxxxxxx = afr.a(_snowmanxxxxxx.a, 0, _snowmanxxxxxx.d, _snowman, _snowmanxxxxx);
            if (!_snowmanxxxxxxx) {
               int _snowmanxxxxxxxx = _snowmanxxxxx.a();
               ob _snowmanxxxxxxxxx = _snowmanxxxxx.b();
               char _snowmanxxxxxxxxxx = _snowmanx.a(_snowmanxxxxxxxx);
               boolean _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx == '\n';
               boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx || _snowmanxxxxxxxxxx == ' ';
               _snowmanxxx = _snowmanxxxxxxxxxxx;
               nu _snowmanxxxxxxxxxxxxx = _snowmanx.a(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx ? 1 : 0, _snowmanxxxxxxxxx);
               _snowman.accept(_snowmanxxxxxxxxxxxxx, _snowmanxxxx);
               _snowmanxxxx = !_snowmanxxxxxxxxxxx;
               _snowmanxx = true;
               break;
            }

            _snowmanxxxxx.a(_snowmanxxxxxx.a.length());
         }
      }

      nu _snowmanxxxxx = _snowmanx.a();
      if (_snowmanxxxxx != null) {
         _snowman.accept(_snowmanxxxxx, _snowmanxxxx);
      } else if (_snowmanxxx) {
         _snowman.accept(nu.c, false);
      }
   }

   static class a {
      private final List<dkj.c> a;
      private String b;

      public a(List<dkj.c> var1) {
         this.a = _snowman;
         this.b = _snowman.stream().map(var0 -> var0.a).collect(Collectors.joining());
      }

      public char a(int var1) {
         return this.b.charAt(_snowman);
      }

      public nu a(int var1, int var2, ob var3) {
         djo _snowman = new djo();
         ListIterator<dkj.c> _snowmanx = this.a.listIterator();
         int _snowmanxx = _snowman;
         boolean _snowmanxxx = false;

         while (_snowmanx.hasNext()) {
            dkj.c _snowmanxxxx = _snowmanx.next();
            String _snowmanxxxxx = _snowmanxxxx.a;
            int _snowmanxxxxxx = _snowmanxxxxx.length();
            if (!_snowmanxxx) {
               if (_snowmanxx > _snowmanxxxxxx) {
                  _snowman.a(_snowmanxxxx);
                  _snowmanx.remove();
                  _snowmanxx -= _snowmanxxxxxx;
               } else {
                  String _snowmanxxxxxxx = _snowmanxxxxx.substring(0, _snowmanxx);
                  if (!_snowmanxxxxxxx.isEmpty()) {
                     _snowman.a(nu.a(_snowmanxxxxxxx, _snowmanxxxx.d));
                  }

                  _snowmanxx += _snowman;
                  _snowmanxxx = true;
               }
            }

            if (_snowmanxxx) {
               if (_snowmanxx <= _snowmanxxxxxx) {
                  String _snowmanxxxxxxx = _snowmanxxxxx.substring(_snowmanxx);
                  if (_snowmanxxxxxxx.isEmpty()) {
                     _snowmanx.remove();
                  } else {
                     _snowmanx.set(new dkj.c(_snowmanxxxxxxx, _snowman));
                  }
                  break;
               }

               _snowmanx.remove();
               _snowmanxx -= _snowmanxxxxxx;
            }
         }

         this.b = this.b.substring(_snowman + _snowman);
         return _snowman.b();
      }

      @Nullable
      public nu a() {
         djo _snowman = new djo();
         this.a.forEach(_snowman::a);
         this.a.clear();
         return _snowman.a();
      }
   }

   class b implements afb {
      private final float b;
      private int c = -1;
      private ob d = ob.a;
      private boolean e;
      private float f;
      private int g = -1;
      private ob h = ob.a;
      private int i;
      private int j;

      public b(float var2) {
         this.b = Math.max(_snowman, 1.0F);
      }

      @Override
      public boolean accept(int var1, ob var2, int var3) {
         int _snowman = _snowman + this.j;
         switch (_snowman) {
            case 10:
               return this.a(_snowman, _snowman);
            case 32:
               this.g = _snowman;
               this.h = _snowman;
            default:
               float _snowmanx = dkj.this.a.getWidth(_snowman, _snowman);
               this.f += _snowmanx;
               if (!this.e || !(this.f > this.b)) {
                  this.e |= _snowmanx != 0.0F;
                  this.i = _snowman + Character.charCount(_snowman);
                  return true;
               } else {
                  return this.g != -1 ? this.a(this.g, this.h) : this.a(_snowman, _snowman);
               }
         }
      }

      private boolean a(int var1, ob var2) {
         this.c = _snowman;
         this.d = _snowman;
         return false;
      }

      private boolean c() {
         return this.c != -1;
      }

      public int a() {
         return this.c() ? this.c : this.i;
      }

      public ob b() {
         return this.d;
      }

      public void a(int var1) {
         this.j += _snowman;
      }
   }

   static class c implements nu {
      private final String a;
      private final ob d;

      public c(String var1, ob var2) {
         this.a = _snowman;
         this.d = _snowman;
      }

      @Override
      public <T> Optional<T> a(nu.a<T> var1) {
         return _snowman.accept(this.a);
      }

      @Override
      public <T> Optional<T> a(nu.b<T> var1, ob var2) {
         return _snowman.accept(this.d.a(_snowman), this.a);
      }
   }

   @FunctionalInterface
   public interface d {
      void accept(ob var1, int var2, int var3);
   }

   class e implements afb {
      private float b;
      private int c;

      public e(float var2) {
         this.b = _snowman;
      }

      @Override
      public boolean accept(int var1, ob var2, int var3) {
         this.b = this.b - dkj.this.a.getWidth(_snowman, _snowman);
         if (this.b >= 0.0F) {
            this.c = _snowman + Character.charCount(_snowman);
            return true;
         } else {
            return false;
         }
      }

      public int a() {
         return this.c;
      }

      public void b() {
         this.c = 0;
      }
   }

   @FunctionalInterface
   public interface f {
      float getWidth(int var1, ob var2);
   }
}
