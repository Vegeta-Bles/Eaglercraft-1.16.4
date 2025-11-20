/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.data.dev;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import javax.annotation.Nullable;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NbtProvider
implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private final DataGenerator root;

    public NbtProvider(DataGenerator dataGenerator) {
        this.root = dataGenerator;
    }

    @Override
    public void run(DataCache cache) throws IOException {
        Path path2 = this.root.getOutput();
        for (Path path4 : this.root.getInputs()) {
            Files.walk(path4, new FileVisitOption[0]).filter(path -> path.toString().endsWith(".nbt")).forEach(path3 -> NbtProvider.convertNbtToSnbt(path3, this.getLocation(path4, (Path)path3), path2));
        }
    }

    @Override
    public String getName() {
        return "NBT to SNBT";
    }

    private String getLocation(Path targetPath, Path rootPath) {
        String string = targetPath.relativize(rootPath).toString().replaceAll("\\\\", "/");
        return string.substring(0, string.length() - ".nbt".length());
    }

    @Nullable
    public static Path convertNbtToSnbt(Path inputPath, String location, Path outputPath) {
        try {
            CompoundTag compoundTag = NbtIo.readCompressed(Files.newInputStream(inputPath, new OpenOption[0]));
            Text _snowman2 = compoundTag.toText("    ", 0);
            String _snowman3 = _snowman2.getString() + "\n";
            Path _snowman4 = outputPath.resolve(location + ".snbt");
            Files.createDirectories(_snowman4.getParent(), new FileAttribute[0]);
            try (BufferedWriter _snowman5 = Files.newBufferedWriter(_snowman4, new OpenOption[0]);){
                _snowman5.write(_snowman3);
            }
            LOGGER.info("Converted {} from NBT to SNBT", (Object)location);
            return _snowman4;
        }
        catch (IOException iOException) {
            LOGGER.error("Couldn't convert {} from NBT to SNBT at {}", (Object)location, (Object)inputPath, (Object)iOException);
            return null;
        }
    }
}

