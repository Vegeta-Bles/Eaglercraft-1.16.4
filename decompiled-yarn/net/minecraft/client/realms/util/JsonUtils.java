package net.minecraft.client.realms.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Date;

public class JsonUtils {
   public static String getStringOr(String key, JsonObject node, String defaultValue) {
      JsonElement _snowman = node.get(key);
      if (_snowman != null) {
         return _snowman.isJsonNull() ? defaultValue : _snowman.getAsString();
      } else {
         return defaultValue;
      }
   }

   public static int getIntOr(String key, JsonObject node, int defaultValue) {
      JsonElement _snowman = node.get(key);
      if (_snowman != null) {
         return _snowman.isJsonNull() ? defaultValue : _snowman.getAsInt();
      } else {
         return defaultValue;
      }
   }

   public static long getLongOr(String key, JsonObject node, long defaultValue) {
      JsonElement _snowman = node.get(key);
      if (_snowman != null) {
         return _snowman.isJsonNull() ? defaultValue : _snowman.getAsLong();
      } else {
         return defaultValue;
      }
   }

   public static boolean getBooleanOr(String key, JsonObject node, boolean defaultValue) {
      JsonElement _snowman = node.get(key);
      if (_snowman != null) {
         return _snowman.isJsonNull() ? defaultValue : _snowman.getAsBoolean();
      } else {
         return defaultValue;
      }
   }

   public static Date getDateOr(String key, JsonObject node) {
      JsonElement _snowman = node.get(key);
      return _snowman != null ? new Date(Long.parseLong(_snowman.getAsString())) : new Date();
   }
}
