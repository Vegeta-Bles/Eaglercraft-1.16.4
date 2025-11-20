/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.realms.dto;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.client.realms.dto.ValueObject;
import net.minecraft.client.realms.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UploadInfo
extends ValueObject {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Pattern field_26467 = Pattern.compile("^[a-zA-Z][-a-zA-Z0-9+.]+:");
    private final boolean worldClosed;
    @Nullable
    private final String token;
    private final URI uploadEndpoint;

    private UploadInfo(boolean worldClosed, @Nullable String token, URI uploadEndpoint) {
        this.worldClosed = worldClosed;
        this.token = token;
        this.uploadEndpoint = uploadEndpoint;
    }

    @Nullable
    public static UploadInfo parse(String json) {
        try {
            JsonParser jsonParser = new JsonParser();
            JsonObject _snowman2 = jsonParser.parse(json).getAsJsonObject();
            String _snowman3 = JsonUtils.getStringOr("uploadEndpoint", _snowman2, null);
            if (_snowman3 != null && (_snowman = UploadInfo.method_30862(_snowman3, _snowman = JsonUtils.getIntOr("port", _snowman2, -1))) != null) {
                boolean bl = JsonUtils.getBooleanOr("worldClosed", _snowman2, false);
                String _snowman4 = JsonUtils.getStringOr("token", _snowman2, null);
                return new UploadInfo(bl, _snowman4, _snowman);
            }
        }
        catch (Exception exception) {
            LOGGER.error("Could not parse UploadInfo: " + exception.getMessage());
        }
        return null;
    }

    @Nullable
    @VisibleForTesting
    public static URI method_30862(String string, int n) {
        Matcher matcher = field_26467.matcher(string);
        String _snowman2 = UploadInfo.method_30863(string, matcher);
        try {
            URI uRI = new URI(_snowman2);
            int _snowman3 = UploadInfo.method_30861(n, uRI.getPort());
            if (_snowman3 != uRI.getPort()) {
                return new URI(uRI.getScheme(), uRI.getUserInfo(), uRI.getHost(), _snowman3, uRI.getPath(), uRI.getQuery(), uRI.getFragment());
            }
            return uRI;
        }
        catch (URISyntaxException uRISyntaxException) {
            LOGGER.warn("Failed to parse URI {}", (Object)_snowman2, (Object)uRISyntaxException);
            return null;
        }
    }

    private static int method_30861(int n, int n2) {
        if (n != -1) {
            return n;
        }
        if (n2 != -1) {
            return n2;
        }
        return 8080;
    }

    private static String method_30863(String string, Matcher matcher) {
        if (matcher.find()) {
            return string;
        }
        return "http://" + string;
    }

    public static String createRequestContent(@Nullable String token) {
        JsonObject jsonObject = new JsonObject();
        if (token != null) {
            jsonObject.addProperty("token", token);
        }
        return jsonObject.toString();
    }

    @Nullable
    public String getToken() {
        return this.token;
    }

    public URI getUploadEndpoint() {
        return this.uploadEndpoint;
    }

    public boolean isWorldClosed() {
        return this.worldClosed;
    }
}

