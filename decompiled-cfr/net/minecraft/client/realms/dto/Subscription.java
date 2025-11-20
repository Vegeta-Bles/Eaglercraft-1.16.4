/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.realms.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.realms.dto.ValueObject;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Subscription
extends ValueObject {
    private static final Logger LOGGER = LogManager.getLogger();
    public long startDate;
    public int daysLeft;
    public SubscriptionType type = SubscriptionType.NORMAL;

    public static Subscription parse(String json) {
        Subscription subscription = new Subscription();
        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject _snowman2 = jsonParser.parse(json).getAsJsonObject();
            subscription.startDate = JsonUtils.getLongOr("startDate", _snowman2, 0L);
            subscription.daysLeft = JsonUtils.getIntOr("daysLeft", _snowman2, 0);
            subscription.type = Subscription.typeFrom(JsonUtils.getStringOr("subscriptionType", _snowman2, SubscriptionType.NORMAL.name()));
        }
        catch (Exception exception) {
            LOGGER.error("Could not parse Subscription: " + exception.getMessage());
        }
        return subscription;
    }

    private static SubscriptionType typeFrom(String subscriptionType) {
        try {
            return SubscriptionType.valueOf(subscriptionType);
        }
        catch (Exception exception) {
            return SubscriptionType.NORMAL;
        }
    }

    public static enum SubscriptionType {
        NORMAL,
        RECURRING;

    }
}

