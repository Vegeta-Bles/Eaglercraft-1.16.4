/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.resource;

import java.io.Closeable;
import java.io.InputStream;
import javax.annotation.Nullable;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;

public interface Resource
extends Closeable {
    public Identifier getId();

    public InputStream getInputStream();

    @Nullable
    public <T> T getMetadata(ResourceMetadataReader<T> var1);

    public String getResourcePackName();
}

