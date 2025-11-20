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

public class RealmsServerAddress
extends ValueObject {
    private static final Logger LOGGER = LogManager.getLogger();
    public String address;
    public String resourcePackUrl;
    public String resourcePackHash;

    public static RealmsServerAddress parse(String json) {
        JsonParser jsonParser = new JsonParser();
        RealmsServerAddress _snowman2 = new RealmsServerAddress();
        try {
            JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
            _snowman2.address = JsonUtils.getStringOr("address", jsonObject, null);
            _snowman2.resourcePackUrl = JsonUtils.getStringOr("resourcePackUrl", jsonObject, null);
            _snowman2.resourcePackHash = JsonUtils.getStringOr("resourcePackHash", jsonObject, null);
        }
        catch (Exception exception) {
            LOGGER.error("Could not parse RealmsServerAddress: " + exception.getMessage());
        }
        return _snowman2;
    }
}

