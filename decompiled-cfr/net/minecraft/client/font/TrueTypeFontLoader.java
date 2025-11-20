/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.stb.STBTTFontinfo
 *  org.lwjgl.stb.STBTruetype
 *  org.lwjgl.system.MemoryUtil
 */
package net.minecraft.client.font;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.FontLoader;
import net.minecraft.client.font.TrueTypeFont;
import net.minecraft.client.texture.TextureUtil;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryUtil;

public class TrueTypeFontLoader
implements FontLoader {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Identifier filename;
    private final float size;
    private final float oversample;
    private final float shiftX;
    private final float shiftY;
    private final String excludedCharacters;

    public TrueTypeFontLoader(Identifier filename, float size, float oversample, float shiftX, float shiftY, String excludedCharacters) {
        this.filename = filename;
        this.size = size;
        this.oversample = oversample;
        this.shiftX = shiftX;
        this.shiftY = shiftY;
        this.excludedCharacters = excludedCharacters;
    }

    public static FontLoader fromJson(JsonObject json) {
        float _snowman3;
        Object object;
        float _snowman2 = 0.0f;
        _snowman3 = 0.0f;
        if (json.has("shift")) {
            object = json.getAsJsonArray("shift");
            if (object.size() != 2) {
                throw new JsonParseException("Expected 2 elements in 'shift', found " + object.size());
            }
            _snowman2 = JsonHelper.asFloat(object.get(0), "shift[0]");
            _snowman3 = JsonHelper.asFloat(object.get(1), "shift[1]");
        }
        object = new StringBuilder();
        if (json.has("skip")) {
            JsonElement _snowman4 = json.get("skip");
            if (_snowman4.isJsonArray()) {
                JsonArray jsonArray = JsonHelper.asArray(_snowman4, "skip");
                for (int i = 0; i < jsonArray.size(); ++i) {
                    ((StringBuilder)object).append(JsonHelper.asString(jsonArray.get(i), "skip[" + i + "]"));
                }
            } else {
                ((StringBuilder)object).append(JsonHelper.asString(_snowman4, "skip"));
            }
        }
        return new TrueTypeFontLoader(new Identifier(JsonHelper.getString(json, "file")), JsonHelper.getFloat(json, "size", 11.0f), JsonHelper.getFloat(json, "oversample", 1.0f), _snowman2, _snowman3, ((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    @Nullable
    public Font load(ResourceManager manager) {
        STBTTFontinfo sTBTTFontinfo = null;
        ByteBuffer _snowman2 = null;
        try (Resource _snowman3 = manager.getResource(new Identifier(this.filename.getNamespace(), "font/" + this.filename.getPath()));){
            LOGGER.debug("Loading font {}", (Object)this.filename);
            sTBTTFontinfo = STBTTFontinfo.malloc();
            _snowman2 = TextureUtil.readAllToByteBuffer(_snowman3.getInputStream());
            _snowman2.flip();
            LOGGER.debug("Reading font {}", (Object)this.filename);
            if (!STBTruetype.stbtt_InitFont((STBTTFontinfo)sTBTTFontinfo, (ByteBuffer)_snowman2)) {
                throw new IOException("Invalid ttf");
            }
            TrueTypeFont trueTypeFont = new TrueTypeFont(_snowman2, sTBTTFontinfo, this.size, this.oversample, this.shiftX, this.shiftY, this.excludedCharacters);
            return trueTypeFont;
        }
        catch (Exception exception) {
            LOGGER.error("Couldn't load truetype font {}", (Object)this.filename, (Object)exception);
            if (sTBTTFontinfo != null) {
                sTBTTFontinfo.free();
            }
            MemoryUtil.memFree(_snowman2);
            return null;
        }
    }
}

