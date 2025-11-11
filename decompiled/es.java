import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;

public class es {
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("argument.pos.missing.double"));
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("argument.pos.missing.int"));
   private final boolean c;
   private final double d;

   public es(boolean var1, double var2) {
      this.c = _snowman;
      this.d = _snowman;
   }

   public double a(double var1) {
      return this.c ? this.d + _snowman : this.d;
   }

   public static es a(StringReader var0, boolean var1) throws CommandSyntaxException {
      if (_snowman.canRead() && _snowman.peek() == '^') {
         throw er.b.createWithContext(_snowman);
      } else if (!_snowman.canRead()) {
         throw a.createWithContext(_snowman);
      } else {
         boolean _snowman = b(_snowman);
         int _snowmanx = _snowman.getCursor();
         double _snowmanxx = _snowman.canRead() && _snowman.peek() != ' ' ? _snowman.readDouble() : 0.0;
         String _snowmanxxx = _snowman.getString().substring(_snowmanx, _snowman.getCursor());
         if (_snowman && _snowmanxxx.isEmpty()) {
            return new es(true, 0.0);
         } else {
            if (!_snowmanxxx.contains(".") && !_snowman && _snowman) {
               _snowmanxx += 0.5;
            }

            return new es(_snowman, _snowmanxx);
         }
      }
   }

   public static es a(StringReader var0) throws CommandSyntaxException {
      if (_snowman.canRead() && _snowman.peek() == '^') {
         throw er.b.createWithContext(_snowman);
      } else if (!_snowman.canRead()) {
         throw b.createWithContext(_snowman);
      } else {
         boolean _snowman = b(_snowman);
         double _snowmanx;
         if (_snowman.canRead() && _snowman.peek() != ' ') {
            _snowmanx = _snowman ? _snowman.readDouble() : (double)_snowman.readInt();
         } else {
            _snowmanx = 0.0;
         }

         return new es(_snowman, _snowmanx);
      }
   }

   public static boolean b(StringReader var0) {
      boolean _snowman;
      if (_snowman.peek() == '~') {
         _snowman = true;
         _snowman.skip();
      } else {
         _snowman = false;
      }

      return _snowman;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof es)) {
         return false;
      } else {
         es _snowman = (es)_snowman;
         return this.c != _snowman.c ? false : Double.compare(_snowman.d, this.d) == 0;
      }
   }

   @Override
   public int hashCode() {
      int _snowman = this.c ? 1 : 0;
      long _snowmanx = Double.doubleToLongBits(this.d);
      return 31 * _snowman + (int)(_snowmanx ^ _snowmanx >>> 32);
   }

   public boolean a() {
      return this.c;
   }
}
