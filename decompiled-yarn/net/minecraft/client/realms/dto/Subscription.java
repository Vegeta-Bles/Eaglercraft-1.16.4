package net.minecraft.client.realms.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Subscription extends ValueObject {
   private static final Logger LOGGER = LogManager.getLogger();
   public long startDate;
   public int daysLeft;
   public Subscription.SubscriptionType type = Subscription.SubscriptionType.NORMAL;

   public Subscription() {
   }

   public static Subscription parse(String json) {
      Subscription _snowman = new Subscription();

      try {
         JsonParser _snowmanx = new JsonParser();
         JsonObject _snowmanxx = _snowmanx.parse(json).getAsJsonObject();
         _snowman.startDate = JsonUtils.getLongOr("startDate", _snowmanxx, 0L);
         _snowman.daysLeft = JsonUtils.getIntOr("daysLeft", _snowmanxx, 0);
         _snowman.type = typeFrom(JsonUtils.getStringOr("subscriptionType", _snowmanxx, Subscription.SubscriptionType.NORMAL.name()));
      } catch (Exception var4) {
         LOGGER.error("Could not parse Subscription: " + var4.getMessage());
      }

      return _snowman;
   }

   private static Subscription.SubscriptionType typeFrom(String subscriptionType) {
      try {
         return Subscription.SubscriptionType.valueOf(subscriptionType);
      } catch (Exception var2) {
         return Subscription.SubscriptionType.NORMAL;
      }
   }

   public static enum SubscriptionType {
      NORMAL,
      RECURRING;

      private SubscriptionType() {
      }
   }
}
