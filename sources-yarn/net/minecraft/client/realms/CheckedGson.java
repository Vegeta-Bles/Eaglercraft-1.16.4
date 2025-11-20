package net.minecraft.client.realms;

import com.google.gson.Gson;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class CheckedGson {
   private final Gson GSON = new Gson();

   public CheckedGson() {
   }

   public String toJson(RealmsSerializable serializable) {
      return this.GSON.toJson(serializable);
   }

   public <T extends RealmsSerializable> T fromJson(String json, Class<T> type) {
      return (T)this.GSON.fromJson(json, type);
   }
}
