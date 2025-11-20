/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 *  org.apache.commons.io.FileUtils
 *  org.apache.commons.io.IOUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.data;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.util.Util;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SnbtProvider
implements DataProvider {
    @Nullable
    private static final Path field_24615 = null;
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGenerator root;
    private final List<Tweaker> write = Lists.newArrayList();

    public SnbtProvider(DataGenerator dataGenerator) {
        this.root = dataGenerator;
    }

    public SnbtProvider addWriter(Tweaker tweaker) {
        this.write.add(tweaker);
        return this;
    }

    private CompoundTag write(String string, CompoundTag compoundTag) {
        CompoundTag compoundTag2;
        compoundTag2 = compoundTag;
        for (Tweaker tweaker : this.write) {
            compoundTag2 = tweaker.write(string, compoundTag2);
        }
        return compoundTag2;
    }

    @Override
    public void run(DataCache cache) throws IOException {
        Path path3 = this.root.getOutput();
        ArrayList _snowman2 = Lists.newArrayList();
        for (Path path4 : this.root.getInputs()) {
            Files.walk(path4, new FileVisitOption[0]).filter(path -> path.toString().endsWith(".snbt")).forEach(path2 -> _snowman2.add(CompletableFuture.supplyAsync(() -> this.toCompressedNbt((Path)path2, this.getFileName(path4, (Path)path2)), Util.getMainWorkerExecutor())));
        }
        Util.combine(_snowman2).join().stream().filter(Objects::nonNull).forEach(compressedData -> this.write(cache, (CompressedData)compressedData, path3));
    }

    @Override
    public String getName() {
        return "SNBT -> NBT";
    }

    private String getFileName(Path root, Path file) {
        String string = root.relativize(file).toString().replaceAll("\\\\", "/");
        return string.substring(0, string.length() - ".snbt".length());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Nullable
    private CompressedData toCompressedNbt(Path path, String name) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(path);){
            String _snowman7;
            String _snowman2 = IOUtils.toString((Reader)bufferedReader);
            CompoundTag _snowman3 = this.write(name, StringNbtReader.parse(_snowman2));
            ByteArrayOutputStream _snowman4 = new ByteArrayOutputStream();
            NbtIo.writeCompressed(_snowman3, _snowman4);
            byte[] _snowman5 = _snowman4.toByteArray();
            String _snowman6 = SHA1.hashBytes(_snowman5).toString();
            if (field_24615 != null) {
                String string = _snowman3.toText("    ", 0).getString() + "\n";
            } else {
                _snowman7 = null;
            }
            CompressedData compressedData = new CompressedData(name, _snowman5, _snowman7, _snowman6);
            return compressedData;
        }
        catch (CommandSyntaxException commandSyntaxException) {
            LOGGER.error("Couldn't convert {} from SNBT to NBT at {} as it's invalid SNBT", (Object)name, (Object)path, (Object)commandSyntaxException);
            return null;
        }
        catch (IOException iOException) {
            LOGGER.error("Couldn't convert {} from SNBT to NBT at {}", (Object)name, (Object)path, (Object)iOException);
        }
        return null;
    }

    private void write(DataCache dataCache2, CompressedData compressedData, Path path2) {
        Path path2;
        if (compressedData.field_24616 != null) {
            _snowman = field_24615.resolve(compressedData.name + ".snbt");
            try {
                FileUtils.write((File)_snowman.toFile(), (CharSequence)compressedData.field_24616, (Charset)StandardCharsets.UTF_8);
            }
            catch (IOException iOException) {
                LOGGER.error("Couldn't write structure SNBT {} at {}", (Object)compressedData.name, (Object)_snowman, (Object)iOException);
            }
        }
        _snowman = path2.resolve(compressedData.name + ".nbt");
        try {
            DataCache dataCache2;
            if (!Objects.equals(dataCache2.getOldSha1(_snowman), compressedData.sha1) || !Files.exists(_snowman, new LinkOption[0])) {
                Files.createDirectories(_snowman.getParent(), new FileAttribute[0]);
                try (OutputStream outputStream = Files.newOutputStream(_snowman, new OpenOption[0]);){
                    outputStream.write(compressedData.bytes);
                }
            }
            dataCache2.updateSha1(_snowman, compressedData.sha1);
        }
        catch (IOException iOException) {
            LOGGER.error("Couldn't write structure {} at {}", (Object)compressedData.name, (Object)_snowman, (Object)iOException);
        }
    }

    @FunctionalInterface
    public static interface Tweaker {
        public CompoundTag write(String var1, CompoundTag var2);
    }

    static class CompressedData {
        private final String name;
        private final byte[] bytes;
        @Nullable
        private final String field_24616;
        private final String sha1;

        public CompressedData(String name, byte[] bytes, @Nullable String sha1, String string) {
            this.name = name;
            this.bytes = bytes;
            this.field_24616 = sha1;
            this.sha1 = string;
        }
    }
}

