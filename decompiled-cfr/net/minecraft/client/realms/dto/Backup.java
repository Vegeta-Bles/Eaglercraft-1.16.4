/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.realms.dto;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.realms.dto.ValueObject;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Backup
extends ValueObject {
    private static final Logger LOGGER = LogManager.getLogger();
    public String backupId;
    public Date lastModifiedDate;
    public long size;
    private boolean uploadedVersion;
    public Map<String, String> metadata = Maps.newHashMap();
    public Map<String, String> changeList = Maps.newHashMap();

    public static Backup parse(JsonElement node) {
        JsonObject jsonObject = node.getAsJsonObject();
        Backup _snowman2 = new Backup();
        try {
            _snowman2.backupId = JsonUtils.getStringOr("backupId", jsonObject, "");
            _snowman2.lastModifiedDate = JsonUtils.getDateOr("lastModifiedDate", jsonObject);
            _snowman2.size = JsonUtils.getLongOr("size", jsonObject, 0L);
            if (jsonObject.has("metadata")) {
                _snowman = jsonObject.getAsJsonObject("metadata");
                Set set = _snowman.entrySet();
                for (Map.Entry entry : set) {
                    if (((JsonElement)entry.getValue()).isJsonNull()) continue;
                    _snowman2.metadata.put(Backup.format((String)entry.getKey()), ((JsonElement)entry.getValue()).getAsString());
                }
            }
        }
        catch (Exception exception) {
            LOGGER.error("Could not parse Backup: " + exception.getMessage());
        }
        return _snowman2;
    }

    private static String format(String key) {
        String[] stringArray = key.split("_");
        StringBuilder _snowman2 = new StringBuilder();
        for (String string : stringArray) {
            if (string == null || string.length() < 1) continue;
            if ("of".equals(string)) {
                _snowman2.append(string).append(" ");
                continue;
            }
            char c = Character.toUpperCase(string.charAt(0));
            _snowman2.append(c).append(string.substring(1)).append(" ");
        }
        return _snowman2.toString();
    }

    public boolean isUploadedVersion() {
        return this.uploadedVersion;
    }

    public void setUploadedVersion(boolean uploadedVersion) {
        this.uploadedVersion = uploadedVersion;
    }
}

