import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ejz {
   private static final Comparator<ejz.a> a = Comparator.<ejz.a, Integer>comparing(var0 -> -var0.c)
      .thenComparing(var0 -> -var0.b)
      .thenComparing(var0 -> var0.a.a());
   private final int b;
   private final Set<ejz.a> c = Sets.newHashSetWithExpectedSize(256);
   private final List<ejz.b> d = Lists.newArrayListWithCapacity(256);
   private int e;
   private int f;
   private final int g;
   private final int h;

   public ejz(int var1, int var2, int var3) {
      this.b = _snowman;
      this.g = _snowman;
      this.h = _snowman;
   }

   public int a() {
      return this.e;
   }

   public int b() {
      return this.f;
   }

   public void a(ekc.a var1) {
      ejz.a _snowman = new ejz.a(_snowman, this.b);
      this.c.add(_snowman);
   }

   public void c() {
      List<ejz.a> _snowman = Lists.newArrayList(this.c);
      _snowman.sort(a);

      for (ejz.a _snowmanx : _snowman) {
         if (!this.a(_snowmanx)) {
            throw new eka(_snowmanx.a, _snowman.stream().map(var0 -> var0.a).collect(ImmutableList.toImmutableList()));
         }
      }

      this.e = afm.c(this.e);
      this.f = afm.c(this.f);
   }

   public void a(ejz.c var1) {
      for (ejz.b _snowman : this.d) {
         _snowman.a(var2 -> {
            ejz.a _snowmanx = var2.a();
            ekc.a _snowmanx = _snowmanx.a;
            _snowman.load(_snowmanx, this.e, this.f, var2.b(), var2.c());
         });
      }
   }

   private static int b(int var0, int var1) {
      return (_snowman >> _snowman) + ((_snowman & (1 << _snowman) - 1) == 0 ? 0 : 1) << _snowman;
   }

   private boolean a(ejz.a var1) {
      for (ejz.b _snowman : this.d) {
         if (_snowman.a(_snowman)) {
            return true;
         }
      }

      return this.b(_snowman);
   }

   private boolean b(ejz.a var1) {
      int _snowman = afm.c(this.e);
      int _snowmanx = afm.c(this.f);
      int _snowmanxx = afm.c(this.e + _snowman.b);
      int _snowmanxxx = afm.c(this.f + _snowman.c);
      boolean _snowmanxxxx = _snowmanxx <= this.g;
      boolean _snowmanxxxxx = _snowmanxxx <= this.h;
      if (!_snowmanxxxx && !_snowmanxxxxx) {
         return false;
      } else {
         boolean _snowmanxxxxxx = _snowmanxxxx && _snowman != _snowmanxx;
         boolean _snowmanxxxxxxx = _snowmanxxxxx && _snowmanx != _snowmanxxx;
         boolean _snowmanxxxxxxxx;
         if (_snowmanxxxxxx ^ _snowmanxxxxxxx) {
            _snowmanxxxxxxxx = _snowmanxxxxxx;
         } else {
            _snowmanxxxxxxxx = _snowmanxxxx && _snowman <= _snowmanx;
         }

         ejz.b _snowmanxxxxxxxxx;
         if (_snowmanxxxxxxxx) {
            if (this.f == 0) {
               this.f = _snowman.c;
            }

            _snowmanxxxxxxxxx = new ejz.b(this.e, 0, _snowman.b, this.f);
            this.e = this.e + _snowman.b;
         } else {
            _snowmanxxxxxxxxx = new ejz.b(0, this.f, this.e, _snowman.c);
            this.f = this.f + _snowman.c;
         }

         _snowmanxxxxxxxxx.a(_snowman);
         this.d.add(_snowmanxxxxxxxxx);
         return true;
      }
   }

   static class a {
      public final ekc.a a;
      public final int b;
      public final int c;

      public a(ekc.a var1, int var2) {
         this.a = _snowman;
         this.b = ejz.b(_snowman.b(), _snowman);
         this.c = ejz.b(_snowman.c(), _snowman);
      }

      @Override
      public String toString() {
         return "Holder{width=" + this.b + ", height=" + this.c + '}';
      }
   }

   public static class b {
      private final int a;
      private final int b;
      private final int c;
      private final int d;
      private List<ejz.b> e;
      private ejz.a f;

      public b(int var1, int var2, int var3, int var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      public ejz.a a() {
         return this.f;
      }

      public int b() {
         return this.a;
      }

      public int c() {
         return this.b;
      }

      public boolean a(ejz.a var1) {
         if (this.f != null) {
            return false;
         } else {
            int _snowman = _snowman.b;
            int _snowmanx = _snowman.c;
            if (_snowman <= this.c && _snowmanx <= this.d) {
               if (_snowman == this.c && _snowmanx == this.d) {
                  this.f = _snowman;
                  return true;
               } else {
                  if (this.e == null) {
                     this.e = Lists.newArrayListWithCapacity(1);
                     this.e.add(new ejz.b(this.a, this.b, _snowman, _snowmanx));
                     int _snowmanxx = this.c - _snowman;
                     int _snowmanxxx = this.d - _snowmanx;
                     if (_snowmanxxx > 0 && _snowmanxx > 0) {
                        int _snowmanxxxx = Math.max(this.d, _snowmanxx);
                        int _snowmanxxxxx = Math.max(this.c, _snowmanxxx);
                        if (_snowmanxxxx >= _snowmanxxxxx) {
                           this.e.add(new ejz.b(this.a, this.b + _snowmanx, _snowman, _snowmanxxx));
                           this.e.add(new ejz.b(this.a + _snowman, this.b, _snowmanxx, this.d));
                        } else {
                           this.e.add(new ejz.b(this.a + _snowman, this.b, _snowmanxx, _snowmanx));
                           this.e.add(new ejz.b(this.a, this.b + _snowmanx, this.c, _snowmanxxx));
                        }
                     } else if (_snowmanxx == 0) {
                        this.e.add(new ejz.b(this.a, this.b + _snowmanx, _snowman, _snowmanxxx));
                     } else if (_snowmanxxx == 0) {
                        this.e.add(new ejz.b(this.a + _snowman, this.b, _snowmanxx, _snowmanx));
                     }
                  }

                  for (ejz.b _snowmanxx : this.e) {
                     if (_snowmanxx.a(_snowman)) {
                        return true;
                     }
                  }

                  return false;
               }
            } else {
               return false;
            }
         }
      }

      public void a(Consumer<ejz.b> var1) {
         if (this.f != null) {
            _snowman.accept(this);
         } else if (this.e != null) {
            for (ejz.b _snowman : this.e) {
               _snowman.a(_snowman);
            }
         }
      }

      @Override
      public String toString() {
         return "Slot{originX="
            + this.a
            + ", originY="
            + this.b
            + ", width="
            + this.c
            + ", height="
            + this.d
            + ", texture="
            + this.f
            + ", subSlots="
            + this.e
            + '}';
      }
   }

   public interface c {
      void load(ekc.a var1, int var2, int var3, int var4, int var5);
   }
}
