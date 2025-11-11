import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.FloatArgumentType;

public class fp implements fj<FloatArgumentType> {
   public fp() {
   }

   public void a(FloatArgumentType var1, nf var2) {
      boolean _snowman = _snowman.getMinimum() != -Float.MAX_VALUE;
      boolean _snowmanx = _snowman.getMaximum() != Float.MAX_VALUE;
      _snowman.writeByte(fn.a(_snowman, _snowmanx));
      if (_snowman) {
         _snowman.writeFloat(_snowman.getMinimum());
      }

      if (_snowmanx) {
         _snowman.writeFloat(_snowman.getMaximum());
      }
   }

   public FloatArgumentType a(nf var1) {
      byte _snowman = _snowman.readByte();
      float _snowmanx = fn.a(_snowman) ? _snowman.readFloat() : -Float.MAX_VALUE;
      float _snowmanxx = fn.b(_snowman) ? _snowman.readFloat() : Float.MAX_VALUE;
      return FloatArgumentType.floatArg(_snowmanx, _snowmanxx);
   }

   public void a(FloatArgumentType var1, JsonObject var2) {
      if (_snowman.getMinimum() != -Float.MAX_VALUE) {
         _snowman.addProperty("min", _snowman.getMinimum());
      }

      if (_snowman.getMaximum() != Float.MAX_VALUE) {
         _snowman.addProperty("max", _snowman.getMaximum());
      }
   }
}
