import com.google.common.collect.Maps;
import java.util.Map;

public class bky extends blx {
   private static final Map<bkx, bky> a = Maps.newEnumMap(bkx.class);
   private final bkx b;

   public bky(bkx var1, blx.a var2) {
      super(_snowman);
      this.b = _snowman;
      a.put(_snowman, this);
   }

   @Override
   public aou a(bmb var1, bfw var2, aqm var3, aot var4) {
      if (_snowman instanceof bas) {
         bas _snowman = (bas)_snowman;
         if (_snowman.aX() && !_snowman.eM() && _snowman.eL() != this.b) {
            if (!_snowman.l.v) {
               _snowman.b(this.b);
               _snowman.g(1);
            }

            return aou.a(_snowman.l.v);
         }
      }

      return aou.c;
   }

   public bkx d() {
      return this.b;
   }

   public static bky a(bkx var0) {
      return a.get(_snowman);
   }
}
