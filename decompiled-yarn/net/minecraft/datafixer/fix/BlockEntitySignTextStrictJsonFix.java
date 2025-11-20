package net.minecraft.datafixer.fix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.lang.reflect.Type;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;
import org.apache.commons.lang3.StringUtils;

public class BlockEntitySignTextStrictJsonFix extends ChoiceFix {
   public static final Gson GSON = new GsonBuilder().registerTypeAdapter(Text.class, new JsonDeserializer<Text>() {
      public MutableText deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         if (_snowman.isJsonPrimitive()) {
            return new LiteralText(_snowman.getAsString());
         } else if (_snowman.isJsonArray()) {
            JsonArray _snowmanxxx = _snowman.getAsJsonArray();
            MutableText _snowmanxxxx = null;

            for (JsonElement _snowmanxxxxx : _snowmanxxx) {
               MutableText _snowmanxxxxxx = this.deserialize(_snowmanxxxxx, _snowmanxxxxx.getClass(), _snowman);
               if (_snowmanxxxx == null) {
                  _snowmanxxxx = _snowmanxxxxxx;
               } else {
                  _snowmanxxxx.append(_snowmanxxxxxx);
               }
            }

            return _snowmanxxxx;
         } else {
            throw new JsonParseException("Don't know how to turn " + _snowman + " into a Component");
         }
      }
   }).create();

   public BlockEntitySignTextStrictJsonFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType, "BlockEntitySignTextStrictJsonFix", TypeReferences.BLOCK_ENTITY, "Sign");
   }

   private Dynamic<?> fix(Dynamic<?> _snowman, String lineName) {
      String _snowmanx = _snowman.get(lineName).asString("");
      Text _snowmanxx = null;
      if (!"null".equals(_snowmanx) && !StringUtils.isEmpty(_snowmanx)) {
         if (_snowmanx.charAt(0) == '"' && _snowmanx.charAt(_snowmanx.length() - 1) == '"' || _snowmanx.charAt(0) == '{' && _snowmanx.charAt(_snowmanx.length() - 1) == '}') {
            try {
               _snowmanxx = JsonHelper.deserialize(GSON, _snowmanx, Text.class, true);
               if (_snowmanxx == null) {
                  _snowmanxx = LiteralText.EMPTY;
               }
            } catch (JsonParseException var8) {
            }

            if (_snowmanxx == null) {
               try {
                  _snowmanxx = Text.Serializer.fromJson(_snowmanx);
               } catch (JsonParseException var7) {
               }
            }

            if (_snowmanxx == null) {
               try {
                  _snowmanxx = Text.Serializer.fromLenientJson(_snowmanx);
               } catch (JsonParseException var6) {
               }
            }

            if (_snowmanxx == null) {
               _snowmanxx = new LiteralText(_snowmanx);
            }
         } else {
            _snowmanxx = new LiteralText(_snowmanx);
         }
      } else {
         _snowmanxx = LiteralText.EMPTY;
      }

      return _snowman.set(lineName, _snowman.createString(Text.Serializer.toJson(_snowmanxx)));
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      return inputType.update(DSL.remainderFinder(), _snowman -> {
         _snowman = this.fix(_snowman, "Text1");
         _snowman = this.fix(_snowman, "Text2");
         _snowman = this.fix(_snowman, "Text3");
         return this.fix(_snowman, "Text4");
      });
   }
}
