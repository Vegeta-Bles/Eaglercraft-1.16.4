/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.io.Files
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  javax.annotation.Nullable
 *  org.apache.commons.io.IOUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.resource;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceIndex {
    protected static final Logger LOGGER = LogManager.getLogger();
    private final Map<String, File> index = Maps.newHashMap();
    private final Map<Identifier, File> field_21556 = Maps.newHashMap();

    protected ResourceIndex() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ResourceIndex(File directory, String indexName) {
        File file = new File(directory, "objects");
        _snowman = new File(directory, "indexes/" + indexName + ".json");
        BufferedReader _snowman2 = null;
        try {
            _snowman2 = Files.newReader((File)_snowman, (Charset)StandardCharsets.UTF_8);
            JsonObject jsonObject = JsonHelper.deserialize(_snowman2);
            _snowman = JsonHelper.getObject(jsonObject, "objects", null);
            if (_snowman != null) {
                for (Map.Entry entry : _snowman.entrySet()) {
                    JsonObject jsonObject2 = (JsonObject)entry.getValue();
                    String _snowman3 = (String)entry.getKey();
                    String[] _snowman4 = _snowman3.split("/", 2);
                    String _snowman5 = JsonHelper.getString(jsonObject2, "hash");
                    File _snowman6 = new File(file, _snowman5.substring(0, 2) + "/" + _snowman5);
                    if (_snowman4.length == 1) {
                        this.index.put(_snowman4[0], _snowman6);
                        continue;
                    }
                    this.field_21556.put(new Identifier(_snowman4[0], _snowman4[1]), _snowman6);
                }
            }
        }
        catch (JsonParseException jsonParseException) {
            LOGGER.error("Unable to parse resource index file: {}", (Object)_snowman);
        }
        catch (FileNotFoundException fileNotFoundException) {
            LOGGER.error("Can't find the resource index file: {}", (Object)_snowman);
        }
        finally {
            IOUtils.closeQuietly((Reader)_snowman2);
        }
    }

    @Nullable
    public File getResource(Identifier identifier) {
        return this.field_21556.get(identifier);
    }

    @Nullable
    public File findFile(String path) {
        return this.index.get(path);
    }

    public Collection<Identifier> getFilesRecursively(String string, String string2, int n, Predicate<String> predicate) {
        return this.field_21556.keySet().stream().filter(identifier -> {
            String string3 = identifier.getPath();
            return identifier.getNamespace().equals(string2) && !string3.endsWith(".mcmeta") && string3.startsWith(string + "/") && predicate.test(string3);
        }).collect(Collectors.toList());
    }
}

