/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableSet
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.resource;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.resource.AbstractFileResourcePack;
import net.minecraft.resource.DirectoryResourcePack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultResourcePack
implements ResourcePack {
    public static Path resourcePath;
    private static final Logger LOGGER;
    public static Class<?> resourceClass;
    private static final Map<ResourceType, FileSystem> typeToFileSystem;
    public final Set<String> namespaces;

    public DefaultResourcePack(String ... namespaces) {
        this.namespaces = ImmutableSet.copyOf((Object[])namespaces);
    }

    @Override
    public InputStream openRoot(String fileName) throws IOException {
        Path path;
        if (fileName.contains("/") || fileName.contains("\\")) {
            throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
        }
        if (resourcePath != null && Files.exists(path = resourcePath.resolve(fileName), new LinkOption[0])) {
            return Files.newInputStream(path, new OpenOption[0]);
        }
        return this.getInputStream(fileName);
    }

    @Override
    public InputStream open(ResourceType type, Identifier id) throws IOException {
        InputStream inputStream = this.findInputStream(type, id);
        if (inputStream != null) {
            return inputStream;
        }
        throw new FileNotFoundException(id.getPath());
    }

    @Override
    public Collection<Identifier> findResources(ResourceType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter) {
        URI _snowman2;
        Enumeration<URL> enumeration;
        HashSet hashSet = Sets.newHashSet();
        if (resourcePath != null) {
            try {
                DefaultResourcePack.getIdentifiers(hashSet, maxDepth, namespace, resourcePath.resolve(type.getDirectory()), prefix, pathFilter);
            }
            catch (IOException iOException) {
                // empty catch block
            }
            if (type == ResourceType.CLIENT_RESOURCES) {
                enumeration = null;
                try {
                    enumeration = resourceClass.getClassLoader().getResources(type.getDirectory() + "/");
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                while (enumeration != null && enumeration.hasMoreElements()) {
                    try {
                        _snowman2 = ((URL)enumeration.nextElement()).toURI();
                        if (!"file".equals(_snowman2.getScheme())) continue;
                        DefaultResourcePack.getIdentifiers(hashSet, maxDepth, namespace, Paths.get(_snowman2), prefix, pathFilter);
                    }
                    catch (IOException | URISyntaxException exception) {}
                }
            }
        }
        try {
            enumeration = DefaultResourcePack.class.getResource("/" + type.getDirectory() + "/.mcassetsroot");
            if (enumeration == null) {
                LOGGER.error("Couldn't find .mcassetsroot, cannot load vanilla resources");
                return hashSet;
            }
            _snowman2 = ((URL)((Object)enumeration)).toURI();
            if ("file".equals(_snowman2.getScheme())) {
                URL uRL = new URL(((URL)((Object)enumeration)).toString().substring(0, ((URL)((Object)enumeration)).toString().length() - ".mcassetsroot".length()));
                Path _snowman3 = Paths.get(uRL.toURI());
                DefaultResourcePack.getIdentifiers(hashSet, maxDepth, namespace, _snowman3, prefix, pathFilter);
            } else if ("jar".equals(_snowman2.getScheme())) {
                Path _snowman4 = typeToFileSystem.get((Object)type).getPath("/" + type.getDirectory(), new String[0]);
                DefaultResourcePack.getIdentifiers(hashSet, maxDepth, "minecraft", _snowman4, prefix, pathFilter);
            } else {
                LOGGER.error("Unsupported scheme {} trying to list vanilla resources (NYI?)", (Object)_snowman2);
            }
        }
        catch (FileNotFoundException | NoSuchFileException iOException) {
        }
        catch (IOException | URISyntaxException exception) {
            LOGGER.error("Couldn't get a list of all vanilla resources", (Throwable)exception);
        }
        return hashSet;
    }

    private static void getIdentifiers(Collection<Identifier> collection, int maxDepth, String namespace, Path path3, String searchLocation, Predicate<String> predicate) throws IOException {
        Path path4 = path3.resolve(namespace);
        try (Stream<Path> _snowman2 = Files.walk(path4.resolve(searchLocation), maxDepth, new FileVisitOption[0]);){
            _snowman2.filter(path -> !path.endsWith(".mcmeta") && Files.isRegularFile(path, new LinkOption[0]) && predicate.test(path.getFileName().toString())).map(path2 -> new Identifier(namespace, path4.relativize((Path)path2).toString().replaceAll("\\\\", "/"))).forEach(collection::add);
        }
    }

    @Nullable
    protected InputStream findInputStream(ResourceType type, Identifier id) {
        String string = DefaultResourcePack.getPath(type, id);
        if (resourcePath != null && Files.exists((Path)(object = resourcePath.resolve(type.getDirectory() + "/" + id.getNamespace() + "/" + id.getPath())), new LinkOption[0])) {
            try {
                return Files.newInputStream((Path)object, new OpenOption[0]);
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        try {
            Object object = DefaultResourcePack.class.getResource(string);
            if (DefaultResourcePack.isValidUrl(string, (URL)object)) {
                return ((URL)object).openStream();
            }
        }
        catch (IOException iOException) {
            return DefaultResourcePack.class.getResourceAsStream(string);
        }
        return null;
    }

    private static String getPath(ResourceType type, Identifier id) {
        return "/" + type.getDirectory() + "/" + id.getNamespace() + "/" + id.getPath();
    }

    private static boolean isValidUrl(String fileName, @Nullable URL url) throws IOException {
        return url != null && (url.getProtocol().equals("jar") || DirectoryResourcePack.isValidPath(new File(url.getFile()), fileName));
    }

    @Nullable
    protected InputStream getInputStream(String path) {
        return DefaultResourcePack.class.getResourceAsStream("/" + path);
    }

    @Override
    public boolean contains(ResourceType type, Identifier id) {
        String string = DefaultResourcePack.getPath(type, id);
        if (resourcePath != null && Files.exists((Path)(object = resourcePath.resolve(type.getDirectory() + "/" + id.getNamespace() + "/" + id.getPath())), new LinkOption[0])) {
            return true;
        }
        try {
            Object object = DefaultResourcePack.class.getResource(string);
            return DefaultResourcePack.isValidUrl(string, (URL)object);
        }
        catch (IOException iOException) {
            return false;
        }
    }

    @Override
    public Set<String> getNamespaces(ResourceType type) {
        return this.namespaces;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    @Nullable
    public <T> T parseMetadata(ResourceMetadataReader<T> metaReader) throws IOException {
        try (InputStream inputStream = this.openRoot("pack.mcmeta");){
            T t = AbstractFileResourcePack.parseMetadata(metaReader, inputStream);
            return t;
        }
        catch (FileNotFoundException | RuntimeException exception) {
            return null;
        }
    }

    @Override
    public String getName() {
        return "Default";
    }

    @Override
    public void close() {
    }

    static {
        LOGGER = LogManager.getLogger();
        typeToFileSystem = Util.make(Maps.newHashMap(), hashMap2 -> {
            Class<DefaultResourcePack> clazz = DefaultResourcePack.class;
            synchronized (DefaultResourcePack.class) {
                for (ResourceType resourceType : ResourceType.values()) {
                    URL uRL = DefaultResourcePack.class.getResource("/" + resourceType.getDirectory() + "/.mcassetsroot");
                    try {
                        HashMap hashMap2;
                        FileSystem fileSystem;
                        URI uRI = uRL.toURI();
                        if (!"jar".equals(uRI.getScheme())) continue;
                        try {
                            fileSystem = FileSystems.getFileSystem(uRI);
                        }
                        catch (FileSystemNotFoundException fileSystemNotFoundException) {
                            fileSystem = FileSystems.newFileSystem(uRI, Collections.emptyMap());
                        }
                        hashMap2.put(resourceType, fileSystem);
                    }
                    catch (IOException | URISyntaxException exception) {
                        LOGGER.error("Couldn't get a list of all vanilla resources", (Throwable)exception);
                    }
                }
                // ** MonitorExit[var1_1] (shouldn't be in output)
                return;
            }
        });
    }
}

