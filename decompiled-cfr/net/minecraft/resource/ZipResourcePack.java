/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Sets
 *  org.apache.commons.io.IOUtils
 */
package net.minecraft.resource;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.resource.AbstractFileResourcePack;
import net.minecraft.resource.ResourceNotFoundException;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;

public class ZipResourcePack
extends AbstractFileResourcePack {
    public static final Splitter TYPE_NAMESPACE_SPLITTER = Splitter.on((char)'/').omitEmptyStrings().limit(3);
    private ZipFile file;

    public ZipResourcePack(File file) {
        super(file);
    }

    private ZipFile getZipFile() throws IOException {
        if (this.file == null) {
            this.file = new ZipFile(this.base);
        }
        return this.file;
    }

    @Override
    protected InputStream openFile(String name) throws IOException {
        ZipFile zipFile = this.getZipFile();
        ZipEntry _snowman2 = zipFile.getEntry(name);
        if (_snowman2 == null) {
            throw new ResourceNotFoundException(this.base, name);
        }
        return zipFile.getInputStream(_snowman2);
    }

    @Override
    public boolean containsFile(String name) {
        try {
            return this.getZipFile().getEntry(name) != null;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    @Override
    public Set<String> getNamespaces(ResourceType type) {
        try {
            ZipFile zipFile = this.getZipFile();
        }
        catch (IOException iOException) {
            return Collections.emptySet();
        }
        Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
        HashSet _snowman2 = Sets.newHashSet();
        while (enumeration.hasMoreElements()) {
            ZipEntry zipEntry = enumeration.nextElement();
            String _snowman3 = zipEntry.getName();
            if (!_snowman3.startsWith(type.getDirectory() + "/") || (_snowman = Lists.newArrayList((Iterable)TYPE_NAMESPACE_SPLITTER.split((CharSequence)_snowman3))).size() <= 1) continue;
            String _snowman4 = (String)_snowman.get(1);
            if (_snowman4.equals(_snowman4.toLowerCase(Locale.ROOT))) {
                _snowman2.add(_snowman4);
                continue;
            }
            this.warnNonLowerCaseNamespace(_snowman4);
        }
        return _snowman2;
    }

    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    @Override
    public void close() {
        if (this.file != null) {
            IOUtils.closeQuietly((Closeable)this.file);
            this.file = null;
        }
    }

    @Override
    public Collection<Identifier> findResources(ResourceType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter) {
        try {
            ZipFile zipFile = this.getZipFile();
        }
        catch (IOException iOException) {
            return Collections.emptySet();
        }
        Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
        ArrayList _snowman2 = Lists.newArrayList();
        String _snowman3 = type.getDirectory() + "/" + namespace + "/";
        String _snowman4 = _snowman3 + prefix + "/";
        while (enumeration.hasMoreElements()) {
            ZipEntry zipEntry = enumeration.nextElement();
            if (zipEntry.isDirectory() || (_snowman = zipEntry.getName()).endsWith(".mcmeta") || !_snowman.startsWith(_snowman4) || (_snowman = (_snowman = _snowman.substring(_snowman3.length())).split("/")).length < maxDepth + 1 || !pathFilter.test(_snowman[_snowman.length - 1])) continue;
            _snowman2.add(new Identifier(namespace, _snowman));
        }
        return _snowman2;
    }
}

