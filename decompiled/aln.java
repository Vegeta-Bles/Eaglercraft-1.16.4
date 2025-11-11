import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Const.PrimitiveType;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;

public class aln extends Schema {
   public static final PrimitiveCodec<String> a = new PrimitiveCodec<String>() {
      public <T> DataResult<String> read(DynamicOps<T> var1, T var2) {
         return _snowman.getStringValue(_snowman).map(aln::a);
      }

      public <T> T a(DynamicOps<T> var1, String var2) {
         return (T)_snowman.createString(_snowman);
      }

      @Override
      public String toString() {
         return "NamespacedString";
      }
   };
   private static final Type<String> b = new PrimitiveType(a);

   public aln(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   public static String a(String var0) {
      vk _snowman = vk.a(_snowman);
      return _snowman != null ? _snowman.toString() : _snowman;
   }

   public static Type<String> a() {
      return b;
   }

   public Type<?> getChoiceType(TypeReference var1, String var2) {
      return super.getChoiceType(_snowman, a(_snowman));
   }
}
