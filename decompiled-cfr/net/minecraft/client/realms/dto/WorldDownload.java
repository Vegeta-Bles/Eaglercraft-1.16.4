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

public class WorldDownload
extends ValueObject {
    private static final Logger LOGGER = LogManager.getLogger();
    public String downloadLink;
    public String resourcePackUrl;
    public String resourcePackHash;

    public static WorldDownload parse(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject _snowman2 = jsonParser.parse(json).getAsJsonObject();
        WorldDownload _snowman3 = new WorldDownload();
        try {
            _snowman3.downloadLink = JsonUtils.getStringOr("downloadLink", _snowman2, "");
            _snowman3.resourcePackUrl = JsonUtils.getStringOr("resourcePackUrl", _snowman2, "");
            _snowman3.resourcePackHash = JsonUtils.getStringOr("resourcePackHash", _snowman2, "");
        }
        catch (Exception _snowman4) {
            LOGGER.error("Could not parse WorldDownload: " + _snowman4.getMessage());
        }
        return _snowman3;
    }
}

