package net.minecraft.datafixer.schema;

import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Const.PrimitiveType;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import net.minecraft.util.Identifier;

public class IdentifierNormalizingSchema extends Schema {
   public static final PrimitiveCodec<String> CODEC = new PrimitiveCodec<String>() {
      public <T> DataResult<String> read(DynamicOps<T> _snowman, T _snowman) {
         return _snowman.getStringValue(_snowman).map(IdentifierNormalizingSchema::normalize);
      }

      public <T> T write(DynamicOps<T> _snowman, String _snowman) {
         return (T)_snowman.createString(_snowman);
      }

      @Override
      public String toString() {
         return "NamespacedString";
      }
   };
   private static final Type<String> IDENTIFIER_TYPE = new PrimitiveType(CODEC);

   public IdentifierNormalizingSchema(int versionKey, Schema parent) {
      super(versionKey, parent);
   }

   public static String normalize(String id) {
      Identifier _snowman = Identifier.tryParse(id);
      return _snowman != null ? _snowman.toString() : id;
   }

   public static Type<String> getIdentifierType() {
      return IDENTIFIER_TYPE;
   }

   public Type<?> getChoiceType(TypeReference _snowman, String _snowman) {
      return super.getChoiceType(_snowman, normalize(_snowman));
   }
}
