import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.IntegerArgumentType;

public class fq implements fj<IntegerArgumentType> {
   public fq() {
   }

   public void a(IntegerArgumentType var1, nf var2) {
      boolean _snowman = _snowman.getMinimum() != Integer.MIN_VALUE;
      boolean _snowmanx = _snowman.getMaximum() != Integer.MAX_VALUE;
      _snowman.writeByte(fn.a(_snowman, _snowmanx));
      if (_snowman) {
         _snowman.writeInt(_snowman.getMinimum());
      }

      if (_snowmanx) {
         _snowman.writeInt(_snowman.getMaximum());
      }
   }

   public IntegerArgumentType a(nf var1) {
      byte _snowman = _snowman.readByte();
      int _snowmanx = fn.a(_snowman) ? _snowman.readInt() : Integer.MIN_VALUE;
      int _snowmanxx = fn.b(_snowman) ? _snowman.readInt() : Integer.MAX_VALUE;
      return IntegerArgumentType.integer(_snowmanx, _snowmanxx);
   }

   public void a(IntegerArgumentType var1, JsonObject var2) {
      if (_snowman.getMinimum() != Integer.MIN_VALUE) {
         _snowman.addProperty("min", _snowman.getMinimum());
      }

      if (_snowman.getMaximum() != Integer.MAX_VALUE) {
         _snowman.addProperty("max", _snowman.getMaximum());
      }
   }
}
