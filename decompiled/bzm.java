import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum bzm {
   a(c.a),
   b(c.u),
   c(c.c),
   d(c.v);

   private final c e;

   private bzm(c var3) {
      this.e = _snowman;
   }

   public bzm a(bzm var1) {
      switch (_snowman) {
         case c:
            switch (this) {
               case a:
                  return c;
               case b:
                  return d;
               case c:
                  return a;
               case d:
                  return b;
            }
         case d:
            switch (this) {
               case a:
                  return d;
               case b:
                  return a;
               case c:
                  return b;
               case d:
                  return c;
            }
         case b:
            switch (this) {
               case a:
                  return b;
               case b:
                  return c;
               case c:
                  return d;
               case d:
                  return a;
            }
         default:
            return this;
      }
   }

   public c a() {
      return this.e;
   }

   public gc a(gc var1) {
      if (_snowman.n() == gc.a.b) {
         return _snowman;
      } else {
         switch (this) {
            case b:
               return _snowman.g();
            case c:
               return _snowman.f();
            case d:
               return _snowman.h();
            default:
               return _snowman;
         }
      }
   }

   public int a(int var1, int var2) {
      switch (this) {
         case b:
            return (_snowman + _snowman / 4) % _snowman;
         case c:
            return (_snowman + _snowman / 2) % _snowman;
         case d:
            return (_snowman + _snowman * 3 / 4) % _snowman;
         default:
            return _snowman;
      }
   }

   public static bzm a(Random var0) {
      return x.a(values(), _snowman);
   }

   public static List<bzm> b(Random var0) {
      List<bzm> _snowman = Lists.newArrayList(values());
      Collections.shuffle(_snowman, _snowman);
      return _snowman;
   }
}
