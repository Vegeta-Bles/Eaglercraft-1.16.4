/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
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
            JsonParser jsonParser = new JsonParser();
            JsonObject _snowman2 = jsonParser.parse(error).getAsJsonObject();
            String _snowman3 = JsonUtils.getStringOr("errorMsg", _snowman2, "");
            int _snowman4 = JsonUtils.getIntOr("errorCode", _snowman2, -1);
            return new RealmsError(_snowman3, _snowman4);
        }
        catch (Exception exception) {
            LOGGER.error("Could not parse RealmsError: " + exception.getMessage());
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

