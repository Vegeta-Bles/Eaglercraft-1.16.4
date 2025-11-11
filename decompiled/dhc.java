import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class dhc {
   public dhc() {
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder("{");

      for (Field _snowmanx : this.getClass().getFields()) {
         if (!b(_snowmanx)) {
            try {
               _snowman.append(a(_snowmanx)).append("=").append(_snowmanx.get(this)).append(" ");
            } catch (IllegalAccessException var7) {
            }
         }
      }

      _snowman.deleteCharAt(_snowman.length() - 1);
      _snowman.append('}');
      return _snowman.toString();
   }

   private static String a(Field var0) {
      SerializedName _snowman = _snowman.getAnnotation(SerializedName.class);
      return _snowman != null ? _snowman.value() : _snowman.getName();
   }

   private static boolean b(Field var0) {
      return Modifier.isStatic(_snowman.getModifiers());
   }
}
