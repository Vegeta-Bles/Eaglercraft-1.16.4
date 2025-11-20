package net.minecraft.client.realms;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsError {
   private static final Logger LOGGER = LogManager.getLogger();
   private final String errorMessage;
   private final int errorCode;

   private RealmsError(String errorMessage, int errorCode) {
      this.errorMessage = errorMessage;
      this.errorCode = errorCode;
   }

   public static RealmsError create(String error) {
      try {
         JsonParser _snowman = new JsonParser();
         JsonObject _snowmanx = _snowman.parse(error).getAsJsonObject();
         String _snowmanxx = JsonUtils.getStringOr("errorMsg", _snowmanx, "");
         int _snowmanxxx = JsonUtils.getIntOr("errorCode", _snowmanx, -1);
         return new RealmsError(_snowmanxx, _snowmanxxx);
      } catch (Exception var5) {
         LOGGER.error("Could not parse RealmsError: " + var5.getMessage());
         LOGGER.error("The error was: " + error);
         return new RealmsError("Failed to parse response from server", -1);
      }
   }

   public String getErrorMessage() {
      return this.errorMessage;
   }

   public int getErrorCode() {
      return this.errorCode;
   }
}
