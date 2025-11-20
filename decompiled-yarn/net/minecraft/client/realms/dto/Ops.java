package net.minecraft.client.realms.dto;

import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Set;

public class Ops extends ValueObject {
   public Set<String> ops = Sets.newHashSet();

   public Ops() {
   }

   public static Ops parse(String json) {
      Ops _snowman = new Ops();
      JsonParser _snowmanx = new JsonParser();

      try {
         JsonElement _snowmanxx = _snowmanx.parse(json);
         JsonObject _snowmanxxx = _snowmanxx.getAsJsonObject();
         JsonElement _snowmanxxxx = _snowmanxxx.get("ops");
         if (_snowmanxxxx.isJsonArray()) {
            for (JsonElement _snowmanxxxxx : _snowmanxxxx.getAsJsonArray()) {
               _snowman.ops.add(_snowmanxxxxx.getAsString());
            }
         }
      } catch (Exception var8) {
      }

      return _snowman;
   }
}
