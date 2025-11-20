/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.resource;

import java.io.File;
import java.io.FileFilter;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.resource.DirectoryResourcePack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackProvider;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.resource.ZipResourcePack;

public class FileResourcePackProvider
implements ResourcePackProvider {
    private static final FileFilter POSSIBLE_PACK = file -> {
        boolean bl = file.isFile() && file.getName().endsWith(".zip");
        _snowman = file.isDirectory() && new File(file, "pack.mcmeta").isFile();
        return bl || _snowman;
    };
    private final File packsFolder;
    private final ResourcePackSource field_25345;

    public FileResourcePackProvider(File packsFolder, ResourcePackSource resourcePackSource) {
        this.packsFolder = packsFolder;
        this.field_25345 = resourcePackSource;
    }

    @Override
    public void register(Consumer<ResourcePackProfile> consumer, ResourcePackProfile.Factory factory) {
        if (!this.packsFolder.isDirectory()) {
            this.packsFolder.mkdirs();
        }
        if ((_snowman = this.packsFolder.listFiles(POSSIBLE_PACK)) == null) {
            return;
        }
        for (File file : _snowman) {
            String string = "file/" + file.getName();
            ResourcePackProfile _snowman2 = ResourcePackProfile.of(string, false, this.createResourcePack(file), factory, ResourcePackProfile.InsertionPosition.TOP, this.field_25345);
            if (_snowman2 == null) continue;
            consumer.accept(_snowman2);
        }
    }

    private Supplier<ResourcePack> createResourcePack(File file) {
        if (file.isDirectory()) {
            return () -> new DirectoryResourcePack(file);
        }
        return () -> new ZipResourcePack(file);
    }
}

