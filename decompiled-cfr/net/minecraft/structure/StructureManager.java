/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.DataFixer
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.structure;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.structure.Structure;
import net.minecraft.util.FileNameUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StructureManager {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Map<Identifier, Structure> structures = Maps.newHashMap();
    private final DataFixer dataFixer;
    private ResourceManager field_25189;
    private final Path generatedPath;

    public StructureManager(ResourceManager resourceManager, LevelStorage.Session session, DataFixer dataFixer) {
        this.field_25189 = resourceManager;
        this.dataFixer = dataFixer;
        this.generatedPath = session.getDirectory(WorldSavePath.GENERATED).normalize();
    }

    public Structure getStructureOrBlank(Identifier id) {
        Structure structure = this.getStructure(id);
        if (structure == null) {
            structure = new Structure();
            this.structures.put(id, structure);
        }
        return structure;
    }

    @Nullable
    public Structure getStructure(Identifier identifier2) {
        return this.structures.computeIfAbsent(identifier2, identifier -> {
            Structure structure = this.loadStructureFromFile((Identifier)identifier);
            return structure != null ? structure : this.loadStructureFromResource((Identifier)identifier);
        });
    }

    public void method_29300(ResourceManager resourceManager) {
        this.field_25189 = resourceManager;
        this.structures.clear();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    private Structure loadStructureFromResource(Identifier id) {
        Identifier identifier = new Identifier(id.getNamespace(), "structures/" + id.getPath() + ".nbt");
        try (Resource _snowman2 = this.field_25189.getResource(identifier);){
            Structure structure = this.readStructure(_snowman2.getInputStream());
            return structure;
        }
        catch (FileNotFoundException fileNotFoundException) {
            return null;
        }
        catch (Throwable throwable6) {
            LOGGER.error("Couldn't load structure {}: {}", (Object)id, (Object)throwable6.toString());
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    private Structure loadStructureFromFile(Identifier id) {
        if (!this.generatedPath.toFile().isDirectory()) {
            return null;
        }
        Path path = this.getAndCheckStructurePath(id, ".nbt");
        try (FileInputStream _snowman2 = new FileInputStream(path.toFile());){
            Structure structure = this.readStructure(_snowman2);
            return structure;
        }
        catch (FileNotFoundException fileNotFoundException) {
            return null;
        }
        catch (IOException iOException) {
            LOGGER.error("Couldn't load structure from {}", (Object)path, (Object)iOException);
            return null;
        }
    }

    private Structure readStructure(InputStream structureInputStream) throws IOException {
        CompoundTag compoundTag = NbtIo.readCompressed(structureInputStream);
        return this.createStructure(compoundTag);
    }

    public Structure createStructure(CompoundTag tag) {
        if (!tag.contains("DataVersion", 99)) {
            tag.putInt("DataVersion", 500);
        }
        Structure structure = new Structure();
        structure.fromTag(NbtHelper.update(this.dataFixer, DataFixTypes.STRUCTURE, tag, tag.getInt("DataVersion")));
        return structure;
    }

    public boolean saveStructure(Identifier id) {
        Structure structure = this.structures.get(id);
        if (structure == null) {
            return false;
        }
        Path _snowman2 = this.getAndCheckStructurePath(id, ".nbt");
        Path _snowman3 = _snowman2.getParent();
        if (_snowman3 == null) {
            return false;
        }
        try {
            Files.createDirectories(Files.exists(_snowman3, new LinkOption[0]) ? _snowman3.toRealPath(new LinkOption[0]) : _snowman3, new FileAttribute[0]);
        }
        catch (IOException _snowman4) {
            LOGGER.error("Failed to create parent directory: {}", (Object)_snowman3);
            return false;
        }
        CompoundTag _snowman5 = structure.toTag(new CompoundTag());
        try (FileOutputStream fileOutputStream = new FileOutputStream(_snowman2.toFile());){
            NbtIo.writeCompressed(_snowman5, fileOutputStream);
        }
        catch (Throwable throwable) {
            return false;
        }
        return true;
    }

    public Path getStructurePath(Identifier id, String extension) {
        try {
            Path path = this.generatedPath.resolve(id.getNamespace());
            _snowman = path.resolve("structures");
            return FileNameUtil.getResourcePath(_snowman, id.getPath(), extension);
        }
        catch (InvalidPathException invalidPathException) {
            throw new InvalidIdentifierException("Invalid resource path: " + id, invalidPathException);
        }
    }

    private Path getAndCheckStructurePath(Identifier id, String extension) {
        if (id.getPath().contains("//")) {
            throw new InvalidIdentifierException("Invalid resource path: " + id);
        }
        Path path = this.getStructurePath(id, extension);
        if (!(path.startsWith(this.generatedPath) && FileNameUtil.isNormal(path) && FileNameUtil.isAllowedName(path))) {
            throw new InvalidIdentifierException("Invalid resource path: " + path);
        }
        return path;
    }

    public void unloadStructure(Identifier id) {
        this.structures.remove(id);
    }
}

