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
      public MutableText deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         if (jsonElement.isJsonPrimitive()) {
            return new LiteralText(jsonElement.getAsString());
         } else if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            MutableText lv = null;

            for (JsonElement jsonElement2 : jsonArray) {
               MutableText lv2 = this.deserialize(jsonElement2, jsonElement2.getClass(), jsonDeserializationContext);
               if (lv == null) {
                  lv = lv2;
               } else {
                  lv.append(lv2);
               }
            }

            return lv;
         } else {
            throw new JsonParseException("Don't know how to turn " + jsonElement + " into a Component");
         }
      }
   }).create();

   public BlockEntitySignTextStrictJsonFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType, "BlockEntitySignTextStrictJsonFix", TypeReferences.BLOCK_ENTITY, "Sign");
   }

   private Dynamic<?> fix(Dynamic<?> dynamic, String lineName) {
      String string2 = dynamic.get(lineName).asString("");
      Text lv = null;
      if (!"null".equals(string2) && !StringUtils.isEmpty(string2)) {
         if (string2.charAt(0) == '"' && string2.charAt(string2.length() - 1) == '"' || string2.charAt(0) == '{' && string2.charAt(string2.length() - 1) == '}'
            )
          {
            try {
               lv = JsonHelper.deserialize(GSON, string2, Text.class, true);
               if (lv == null) {
                  lv = LiteralText.EMPTY;
               }
            } catch (JsonParseException var8) {
            }

            if (lv == null) {
               try {
                  lv = Text.Serializer.fromJson(string2);
               } catch (JsonParseException var7) {
               }
            }

            if (lv == null) {
               try {
                  lv = Text.Serializer.fromLenientJson(string2);
               } catch (JsonParseException var6) {
               }
            }

            if (lv == null) {
               lv = new LiteralText(string2);
            }
         } else {
            lv = new LiteralText(string2);
         }
      } else {
         lv = LiteralText.EMPTY;
      }

      return dynamic.set(lineName, dynamic.createString(Text.Serializer.toJson(lv)));
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      return inputType.update(DSL.remainderFinder(), dynamic -> {
         dynamic = this.fix(dynamic, "Text1");
         dynamic = this.fix(dynamic, "Text2");
         dynamic = this.fix(dynamic, "Text3");
         return this.fix(dynamic, "Text4");
      });
   }
}
