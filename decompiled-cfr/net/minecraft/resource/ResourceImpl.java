/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  javax.annotation.Nullable
 *  org.apache.commons.io.IOUtils
 */
package net.minecraft.resource;

import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import net.minecraft.resource.Resource;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.apache.commons.io.IOUtils;

public class ResourceImpl
implements Resource {
    private final String packName;
    private final Identifier id;
    private final InputStream inputStream;
    private final InputStream metaInputStream;
    private boolean readMetadata;
    private JsonObject metadata;

    public ResourceImpl(String packName, Identifier id, InputStream inputStream, @Nullable InputStream metaInputStream) {
        this.packName = packName;
        this.id = id;
        this.inputStream = inputStream;
        this.metaInputStream = metaInputStream;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public InputStream getInputStream() {
        return this.inputStream;
    }

    public boolean hasMetadata() {
        return this.metaInputStream != null;
    }

    @Override
    @Nullable
    public <T> T getMetadata(ResourceMetadataReader<T> metaReader) {
        Object object;
        if (!this.hasMetadata()) {
            return null;
        }
        if (this.metadata == null && !this.readMetadata) {
            this.readMetadata = true;
            object = null;
            try {
                object = new BufferedReader(new InputStreamReader(this.metaInputStream, StandardCharsets.UTF_8));
                this.metadata = JsonHelper.deserialize((Reader)object);
            }
            finally {
                IOUtils.closeQuietly((Reader)object);
            }
        }
        if (this.metadata == null) {
            return null;
        }
        object = metaReader.getKey();
        return this.metadata.has((String)object) ? (T)metaReader.fromJson(JsonHelper.getObject(this.metadata, (String)object)) : null;
    }

    @Override
    public String getResourcePackName() {
        return this.packName;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceImpl)) {
            return false;
        }
        ResourceImpl resourceImpl = (ResourceImpl)o;
        if (this.id != null ? !this.id.equals(resourceImpl.id) : resourceImpl.id != null) {
            return false;
        }
        return !(this.packName != null ? !this.packName.equals(resourceImpl.packName) : resourceImpl.packName != null);
    }

    public int hashCode() {
        int n = this.packName != null ? this.packName.hashCode() : 0;
        n = 31 * n + (this.id != null ? this.id.hashCode() : 0);
        return n;
    }

    @Override
    public void close() throws IOException {
        this.inputStream.close();
        if (this.metaInputStream != null) {
            this.metaInputStream.close();
        }
    }
}

