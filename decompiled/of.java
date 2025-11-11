import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public class of extends nn implements nt {
   private static final Object[] d = new Object[0];
   private static final nu e = nu.b("%");
   private static final nu f = nu.b("null");
   private final String g;
   private final Object[] h;
   @Nullable
   private ly i;
   private final List<nu> j = Lists.newArrayList();
   private static final Pattern k = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");

   public of(String var1) {
      this.g = _snowman;
      this.h = d;
   }

   public of(String var1, Object... var2) {
      this.g = _snowman;
      this.h = _snowman;
   }

   private void k() {
      ly _snowman = ly.a();
      if (_snowman != this.i) {
         this.i = _snowman;
         this.j.clear();
         String _snowmanx = _snowman.a(this.g);

         try {
            this.d(_snowmanx);
         } catch (og var4) {
            this.j.clear();
            this.j.add(nu.b(_snowmanx));
         }
      }
   }

   private void d(String var1) {
      Matcher _snowman = k.matcher(_snowman);

      try {
         int _snowmanx = 0;
         int _snowmanxx = 0;

         while (_snowman.find(_snowmanxx)) {
            int _snowmanxxx = _snowman.start();
            int _snowmanxxxx = _snowman.end();
            if (_snowmanxxx > _snowmanxx) {
               String _snowmanxxxxx = _snowman.substring(_snowmanxx, _snowmanxxx);
               if (_snowmanxxxxx.indexOf(37) != -1) {
                  throw new IllegalArgumentException();
               }

               this.j.add(nu.b(_snowmanxxxxx));
            }

            String _snowmanxxxxx = _snowman.group(2);
            String _snowmanxxxxxx = _snowman.substring(_snowmanxxx, _snowmanxxxx);
            if ("%".equals(_snowmanxxxxx) && "%%".equals(_snowmanxxxxxx)) {
               this.j.add(e);
            } else {
               if (!"s".equals(_snowmanxxxxx)) {
                  throw new og(this, "Unsupported format: '" + _snowmanxxxxxx + "'");
               }

               String _snowmanxxxxxxx = _snowman.group(1);
               int _snowmanxxxxxxxx = _snowmanxxxxxxx != null ? Integer.parseInt(_snowmanxxxxxxx) - 1 : _snowmanx++;
               if (_snowmanxxxxxxxx < this.h.length) {
                  this.j.add(this.b(_snowmanxxxxxxxx));
               }
            }

            _snowmanxx = _snowmanxxxx;
         }

         if (_snowmanxx < _snowman.length()) {
            String _snowmanxxxxx = _snowman.substring(_snowmanxx);
            if (_snowmanxxxxx.indexOf(37) != -1) {
               throw new IllegalArgumentException();
            }

            this.j.add(nu.b(_snowmanxxxxx));
         }
      } catch (IllegalArgumentException var11) {
         throw new og(this, var11);
      }
   }

   private nu b(int var1) {
      if (_snowman >= this.h.length) {
         throw new og(this, _snowman);
      } else {
         Object _snowman = this.h[_snowman];
         if (_snowman instanceof nr) {
            return (nr)_snowman;
         } else {
            return _snowman == null ? f : nu.b(_snowman.toString());
         }
      }
   }

   public of h() {
      return new of(this.g, this.h);
   }

   @Override
   public <T> Optional<T> b(nu.b<T> var1, ob var2) {
      this.k();

      for (nu _snowman : this.j) {
         Optional<T> _snowmanx = _snowman.a(_snowman, _snowman);
         if (_snowmanx.isPresent()) {
            return _snowmanx;
         }
      }

      return Optional.empty();
   }

   @Override
   public <T> Optional<T> b(nu.a<T> var1) {
      this.k();

      for (nu _snowman : this.j) {
         Optional<T> _snowmanx = _snowman.a(_snowman);
         if (_snowmanx.isPresent()) {
            return _snowmanx;
         }
      }

      return Optional.empty();
   }

   @Override
   public nx a(@Nullable db var1, @Nullable aqa var2, int var3) throws CommandSyntaxException {
      Object[] _snowman = new Object[this.h.length];

      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         Object _snowmanxx = this.h[_snowmanx];
         if (_snowmanxx instanceof nr) {
            _snowman[_snowmanx] = ns.a(_snowman, (nr)_snowmanxx, _snowman, _snowman);
         } else {
            _snowman[_snowmanx] = _snowmanxx;
         }
      }

      return new of(this.g, _snowman);
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof of)) {
         return false;
      } else {
         of _snowman = (of)_snowman;
         return Arrays.equals(this.h, _snowman.h) && this.g.equals(_snowman.g) && super.equals(_snowman);
      }
   }

   @Override
   public int hashCode() {
      int _snowman = super.hashCode();
      _snowman = 31 * _snowman + this.g.hashCode();
      return 31 * _snowman + Arrays.hashCode(this.h);
   }

   @Override
   public String toString() {
      return "TranslatableComponent{key='" + this.g + '\'' + ", args=" + Arrays.toString(this.h) + ", siblings=" + this.a + ", style=" + this.c() + '}';
   }

   public String i() {
      return this.g;
   }

   public Object[] j() {
      return this.h;
   }
}
