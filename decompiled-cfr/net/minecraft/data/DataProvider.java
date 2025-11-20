/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.hash.HashFunction
 *  com.google.common.hash.Hashing
 *  com.google.gson.Gson
 *  com.google.gson.JsonElement
 */
package net.minecraft.data;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Objects;
import net.minecraft.data.DataCache;

public interface DataProvider {
    public static final HashFunction SHA1 = Hashing.sha1();

    public void run(DataCache var1) throws IOException;

    public String getName();

    public static void writeToPath(Gson gson, DataCache cache, JsonElement output, Path path2) throws IOException {
        Path path2;
        String string = gson.toJson(output);
        _snowman = SHA1.hashUnencodedChars((CharSequence)string).toString();
        if (!Objects.equals(cache.getOldSha1(path2), _snowman) || !Files.exists(path2, new LinkOption[0])) {
            Files.createDirectories(path2.getParent(), new FileAttribute[0]);
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path2, new OpenOption[0]);){
                bufferedWriter.write(string);
            }
        }
        cache.updateSha1(path2, _snowman);
    }
}

