/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.client.resource.ResourceIndex;
import net.minecraft.util.Identifier;

public class DirectResourceIndex
extends ResourceIndex {
    private final File assetDir;

    public DirectResourceIndex(File assetDir) {
        this.assetDir = assetDir;
    }

    @Override
    public File getResource(Identifier identifier) {
        return new File(this.assetDir, identifier.toString().replace(':', '/'));
    }

    @Override
    public File findFile(String path) {
        return new File(this.assetDir, path);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Collection<Identifier> getFilesRecursively(String string, String string2, int n, Predicate<String> predicate) {
        Path path3 = this.assetDir.toPath().resolve(string2);
        try (Stream<Path> _snowman22222 = Files.walk(path3.resolve(string), n, new FileVisitOption[0]);){
            Collection collection = _snowman22222.filter(path -> Files.isRegularFile(path, new LinkOption[0])).filter(path -> !path.endsWith(".mcmeta")).filter(path -> predicate.test(path.getFileName().toString())).map(path2 -> new Identifier(string2, path3.relativize((Path)path2).toString().replaceAll("\\\\", "/"))).collect(Collectors.toList());
            return collection;
        }
        catch (NoSuchFileException _snowman22222) {
            return Collections.emptyList();
        }
        catch (IOException iOException) {
            LOGGER.warn("Unable to getFiles on {}", (Object)string, (Object)iOException);
        }
        return Collections.emptyList();
    }
}

