/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.realms.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.realms.dto.ValueObject;
import net.minecraft.client.realms.dto.WorldTemplate;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldTemplatePaginatedList
extends ValueObject {
    private static final Logger LOGGER = LogManager.getLogger();
    public List<WorldTemplate> templates;
    public int page;
    public int size;
    public int total;

    public WorldTemplatePaginatedList() {
    }

    public WorldTemplatePaginatedList(int size) {
        this.templates = Collections.emptyList();
        this.page = 0;
        this.size = size;
        this.total = -1;
    }

    public static WorldTemplatePaginatedList parse(String json) {
        WorldTemplatePaginatedList worldTemplatePaginatedList = new WorldTemplatePaginatedList();
        worldTemplatePaginatedList.templates = Lists.newArrayList();
        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject _snowman2 = jsonParser.parse(json).getAsJsonObject();
            if (_snowman2.get("templates").isJsonArray()) {
                Iterator iterator = _snowman2.get("templates").getAsJsonArray().iterator();
                while (iterator.hasNext()) {
                    worldTemplatePaginatedList.templates.add(WorldTemplate.parse(((JsonElement)iterator.next()).getAsJsonObject()));
                }
            }
            worldTemplatePaginatedList.page = JsonUtils.getIntOr("page", _snowman2, 0);
            worldTemplatePaginatedList.size = JsonUtils.getIntOr("size", _snowman2, 0);
            worldTemplatePaginatedList.total = JsonUtils.getIntOr("total", _snowman2, 0);
        }
        catch (Exception exception) {
            LOGGER.error("Could not parse WorldTemplatePaginatedList: " + exception.getMessage());
        }
        return worldTemplatePaginatedList;
    }
}

