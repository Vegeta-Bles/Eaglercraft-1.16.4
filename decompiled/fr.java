import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.LongArgumentType;

public class fr implements fj<LongArgumentType> {
   public fr() {
   }

   public void a(LongArgumentType var1, nf var2) {
      boolean _snowman = _snowman.getMinimum() != Long.MIN_VALUE;
      boolean _snowmanx = _snowman.getMaximum() != Long.MAX_VALUE;
      _snowman.writeByte(fn.a(_snowman, _snowmanx));
      if (_snowman) {
         _snowman.writeLong(_snowman.getMinimum());
      }

      if (_snowmanx) {
         _snowman.writeLong(_snowman.getMaximum());
      }
   }

   public LongArgumentType a(nf var1) {
      byte _snowman = _snowman.readByte();
      long _snowmanx = fn.a(_snowman) ? _snowman.readLong() : Long.MIN_VALUE;
      long _snowmanxx = fn.b(_snowman) ? _snowman.readLong() : Long.MAX_VALUE;
      return LongArgumentType.longArg(_snowmanx, _snowmanxx);
   }

   public void a(LongArgumentType var1, JsonObject var2) {
      if (_snowman.getMinimum() != Long.MIN_VALUE) {
         _snowman.addProperty("min", _snowman.getMinimum());
      }

      if (_snowman.getMaximum() != Long.MAX_VALUE) {
         _snowman.addProperty("max", _snowman.getMaximum());
      }
   }
}
