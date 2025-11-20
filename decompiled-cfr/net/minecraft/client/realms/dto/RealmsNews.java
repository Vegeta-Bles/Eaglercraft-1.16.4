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

public class RealmsNews
extends ValueObject {
    private static final Logger LOGGER = LogManager.getLogger();
    public String newsLink;

    public static RealmsNews parse(String json) {
        RealmsNews realmsNews = new RealmsNews();
        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject _snowman2 = jsonParser.parse(json).getAsJsonObject();
            realmsNews.newsLink = JsonUtils.getStringOr("newsLink", _snowman2, null);
        }
        catch (Exception exception) {
            LOGGER.error("Could not parse RealmsNews: " + exception.getMessage());
        }
        return realmsNews;
    }
}

