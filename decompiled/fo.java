import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class fo implements fj<DoubleArgumentType> {
   public fo() {
   }

   public void a(DoubleArgumentType var1, nf var2) {
      boolean _snowman = _snowman.getMinimum() != -Double.MAX_VALUE;
      boolean _snowmanx = _snowman.getMaximum() != Double.MAX_VALUE;
      _snowman.writeByte(fn.a(_snowman, _snowmanx));
      if (_snowman) {
         _snowman.writeDouble(_snowman.getMinimum());
      }

      if (_snowmanx) {
         _snowman.writeDouble(_snowman.getMaximum());
      }
   }

   public DoubleArgumentType a(nf var1) {
      byte _snowman = _snowman.readByte();
      double _snowmanx = fn.a(_snowman) ? _snowman.readDouble() : -Double.MAX_VALUE;
      double _snowmanxx = fn.b(_snowman) ? _snowman.readDouble() : Double.MAX_VALUE;
      return DoubleArgumentType.doubleArg(_snowmanx, _snowmanxx);
   }

   public void a(DoubleArgumentType var1, JsonObject var2) {
      if (_snowman.getMinimum() != -Double.MAX_VALUE) {
         _snowman.addProperty("min", _snowman.getMinimum());
      }

      if (_snowman.getMaximum() != Double.MAX_VALUE) {
         _snowman.addProperty("max", _snowman.getMaximum());
      }
   }
}
