/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.io.Files
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.Lifecycle
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.world.level.storage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.datafixer.Schemas;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resource.DataPackSettings;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.FileNameUtil;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.Util;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.World;
import net.minecraft.world.WorldSaveHandler;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import net.minecraft.world.level.storage.AnvilLevelStorage;
import net.minecraft.world.level.storage.LevelStorageException;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraft.world.level.storage.SaveVersionInfo;
import net.minecraft.world.level.storage.SessionLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LevelStorage {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder().appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD).appendLiteral('-').appendValue(ChronoField.MONTH_OF_YEAR, 2).appendLiteral('-').appendValue(ChronoField.DAY_OF_MONTH, 2).appendLiteral('_').appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral('-').appendValue(ChronoField.MINUTE_OF_HOUR, 2).appendLiteral('-').appendValue(ChronoField.SECOND_OF_MINUTE, 2).toFormatter();
    private static final ImmutableList<String> GENERATOR_OPTION_KEYS = ImmutableList.of((Object)"RandomSeed", (Object)"generatorName", (Object)"generatorOptions", (Object)"generatorVersion", (Object)"legacy_custom_options", (Object)"MapFeatures", (Object)"BonusChest");
    private final Path savesDirectory;
    private final Path backupsDirectory;
    private final DataFixer dataFixer;

    public LevelStorage(Path savesDirectory, Path backupsDirectory, DataFixer dataFixer) {
        this.dataFixer = dataFixer;
        try {
            Files.createDirectories(Files.exists(savesDirectory, new LinkOption[0]) ? savesDirectory.toRealPath(new LinkOption[0]) : savesDirectory, new FileAttribute[0]);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        this.savesDirectory = savesDirectory;
        this.backupsDirectory = backupsDirectory;
    }

    public static LevelStorage create(Path path) {
        return new LevelStorage(path, path.resolve("../backups"), Schemas.getFixer());
    }

    private static <T> Pair<GeneratorOptions, Lifecycle> readGeneratorProperties(Dynamic<T> dynamic, DataFixer dataFixer, int n) {
        String _snowman32;
        Dynamic _snowman2 = dynamic.get("WorldGenSettings").orElseEmptyMap();
        for (String _snowman32 : GENERATOR_OPTION_KEYS) {
            Optional optional = dynamic.get(_snowman32).result();
            if (!optional.isPresent()) continue;
            _snowman2 = _snowman2.set(_snowman32, (Dynamic)optional.get());
        }
        Dynamic dynamic2 = dataFixer.update(TypeReferences.CHUNK_GENERATOR_SETTINGS, _snowman2, n, SharedConstants.getGameVersion().getWorldVersion());
        _snowman32 = GeneratorOptions.CODEC.parse(dynamic2);
        return Pair.of((Object)_snowman32.resultOrPartial(Util.method_29188("WorldGenSettings: ", arg_0 -> ((Logger)LOGGER).error(arg_0))).orElseGet(() -> {
            Registry registry = (Registry)RegistryLookupCodec.of(Registry.DIMENSION_TYPE_KEY).codec().parse(dynamic2).resultOrPartial(Util.method_29188("Dimension type registry: ", arg_0 -> ((Logger)LOGGER).error(arg_0))).orElseThrow(() -> new IllegalStateException("Failed to get dimension registry"));
            _snowman = (Registry)RegistryLookupCodec.of(Registry.BIOME_KEY).codec().parse(dynamic2).resultOrPartial(Util.method_29188("Biome registry: ", arg_0 -> ((Logger)LOGGER).error(arg_0))).orElseThrow(() -> new IllegalStateException("Failed to get biome registry"));
            _snowman = (Registry)RegistryLookupCodec.of(Registry.NOISE_SETTINGS_WORLDGEN).codec().parse(dynamic2).resultOrPartial(Util.method_29188("Noise settings registry: ", arg_0 -> ((Logger)LOGGER).error(arg_0))).orElseThrow(() -> new IllegalStateException("Failed to get noise settings registry"));
            return GeneratorOptions.getDefaultOptions(registry, _snowman, _snowman);
        }), (Object)_snowman32.lifecycle());
    }

    private static DataPackSettings parseDataPackSettings(Dynamic<?> dynamic) {
        return DataPackSettings.CODEC.parse(dynamic).resultOrPartial(arg_0 -> ((Logger)LOGGER).error(arg_0)).orElse(DataPackSettings.SAFE_MODE);
    }

    public List<LevelSummary> getLevelList() throws LevelStorageException {
        if (!Files.isDirectory(this.savesDirectory, new LinkOption[0])) {
            throw new LevelStorageException(new TranslatableText("selectWorld.load_folder_access").getString());
        }
        ArrayList arrayList = Lists.newArrayList();
        for (File file : _snowman = this.savesDirectory.toFile().listFiles()) {
            if (!file.isDirectory()) continue;
            try {
                boolean bl = SessionLock.isLocked(file.toPath());
            }
            catch (Exception exception) {
                LOGGER.warn("Failed to read {} lock", (Object)file, (Object)exception);
                continue;
            }
            LevelSummary levelSummary = this.readLevelProperties(file, this.createLevelDataParser(file, bl));
            if (levelSummary == null) continue;
            arrayList.add(levelSummary);
        }
        return arrayList;
    }

    private int getCurrentVersion() {
        return 19133;
    }

    @Nullable
    private <T> T readLevelProperties(File file, BiFunction<File, DataFixer, T> levelDataParser) {
        if (!file.exists()) {
            return null;
        }
        _snowman = new File(file, "level.dat");
        if (_snowman.exists() && (_snowman = levelDataParser.apply(_snowman, this.dataFixer)) != null) {
            return _snowman;
        }
        _snowman = new File(file, "level.dat_old");
        if (_snowman.exists()) {
            return levelDataParser.apply(_snowman, this.dataFixer);
        }
        return null;
    }

    @Nullable
    private static DataPackSettings readDataPackSettings(File file, DataFixer dataFixer) {
        try {
            CompoundTag compoundTag = NbtIo.readCompressed(file);
            _snowman = compoundTag.getCompound("Data");
            _snowman.remove("Player");
            int _snowman2 = _snowman.contains("DataVersion", 99) ? _snowman.getInt("DataVersion") : -1;
            Dynamic _snowman3 = dataFixer.update(DataFixTypes.LEVEL.getTypeReference(), new Dynamic((DynamicOps)NbtOps.INSTANCE, (Object)_snowman), _snowman2, SharedConstants.getGameVersion().getWorldVersion());
            return _snowman3.get("DataPacks").result().map(LevelStorage::parseDataPackSettings).orElse(DataPackSettings.SAFE_MODE);
        }
        catch (Exception exception) {
            LOGGER.error("Exception reading {}", (Object)file, (Object)exception);
            return null;
        }
    }

    private static BiFunction<File, DataFixer, LevelProperties> createLevelDataParser(DynamicOps<Tag> dynamicOps, DataPackSettings dataPackSettings) {
        return (file, dataFixer) -> {
            try {
                CompoundTag compoundTag = NbtIo.readCompressed(file);
                _snowman = compoundTag.getCompound("Data");
                _snowman = _snowman.contains("Player", 10) ? _snowman.getCompound("Player") : null;
                _snowman.remove("Player");
                int _snowman2 = _snowman.contains("DataVersion", 99) ? _snowman.getInt("DataVersion") : -1;
                Dynamic _snowman3 = dataFixer.update(DataFixTypes.LEVEL.getTypeReference(), new Dynamic(dynamicOps, (Object)_snowman), _snowman2, SharedConstants.getGameVersion().getWorldVersion());
                Pair<GeneratorOptions, Lifecycle> _snowman4 = LevelStorage.readGeneratorProperties(_snowman3, dataFixer, _snowman2);
                SaveVersionInfo _snowman5 = SaveVersionInfo.fromDynamic(_snowman3);
                LevelInfo _snowman6 = LevelInfo.fromDynamic(_snowman3, dataPackSettings);
                return LevelProperties.readProperties((Dynamic<Tag>)_snowman3, dataFixer, _snowman2, _snowman, _snowman6, _snowman5, (GeneratorOptions)_snowman4.getFirst(), (Lifecycle)_snowman4.getSecond());
            }
            catch (Exception exception) {
                LOGGER.error("Exception reading {}", file, (Object)exception);
                return null;
            }
        };
    }

    private BiFunction<File, DataFixer, LevelSummary> createLevelDataParser(File file, boolean locked) {
        return (file2, dataFixer) -> {
            try {
                CompoundTag compoundTag = NbtIo.readCompressed(file2);
                _snowman = compoundTag.getCompound("Data");
                _snowman.remove("Player");
                int _snowman2 = _snowman.contains("DataVersion", 99) ? _snowman.getInt("DataVersion") : -1;
                Dynamic _snowman3 = dataFixer.update(DataFixTypes.LEVEL.getTypeReference(), new Dynamic((DynamicOps)NbtOps.INSTANCE, (Object)_snowman), _snowman2, SharedConstants.getGameVersion().getWorldVersion());
                SaveVersionInfo _snowman4 = SaveVersionInfo.fromDynamic(_snowman3);
                int _snowman5 = _snowman4.getLevelFormatVersion();
                if (_snowman5 == 19132 || _snowman5 == 19133) {
                    boolean bl2 = _snowman5 != this.getCurrentVersion();
                    File _snowman6 = new File(file, "icon.png");
                    DataPackSettings _snowman7 = _snowman3.get("DataPacks").result().map(LevelStorage::parseDataPackSettings).orElse(DataPackSettings.SAFE_MODE);
                    LevelInfo _snowman8 = LevelInfo.fromDynamic(_snowman3, _snowman7);
                    return new LevelSummary(_snowman8, _snowman4, file.getName(), bl2, locked, _snowman6);
                }
                return null;
            }
            catch (Exception exception) {
                LOGGER.error("Exception reading {}", file2, (Object)exception);
                return null;
            }
        };
    }

    public boolean isLevelNameValid(String name) {
        try {
            Path path = this.savesDirectory.resolve(name);
            Files.createDirectory(path, new FileAttribute[0]);
            Files.deleteIfExists(path);
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    public boolean levelExists(String name) {
        return Files.isDirectory(this.savesDirectory.resolve(name), new LinkOption[0]);
    }

    public Path getSavesDirectory() {
        return this.savesDirectory;
    }

    public Path getBackupsDirectory() {
        return this.backupsDirectory;
    }

    public Session createSession(String directoryName) throws IOException {
        return new Session(directoryName);
    }

    public class Session
    implements AutoCloseable {
        private final SessionLock lock;
        private final Path directory;
        private final String directoryName;
        private final Map<WorldSavePath, Path> paths = Maps.newHashMap();

        public Session(String directoryName) throws IOException {
            this.directoryName = directoryName;
            this.directory = LevelStorage.this.savesDirectory.resolve(directoryName);
            this.lock = SessionLock.create(this.directory);
        }

        public String getDirectoryName() {
            return this.directoryName;
        }

        public Path getDirectory(WorldSavePath savePath) {
            return this.paths.computeIfAbsent(savePath, path -> this.directory.resolve(path.getRelativePath()));
        }

        public File getWorldDirectory(RegistryKey<World> key) {
            return DimensionType.getSaveDirectory(key, this.directory.toFile());
        }

        private void checkValid() {
            if (!this.lock.isValid()) {
                throw new IllegalStateException("Lock is no longer valid");
            }
        }

        public WorldSaveHandler createSaveHandler() {
            this.checkValid();
            return new WorldSaveHandler(this, LevelStorage.this.dataFixer);
        }

        public boolean needsConversion() {
            LevelSummary levelSummary = this.getLevelSummary();
            return levelSummary != null && levelSummary.method_29586().getLevelFormatVersion() != LevelStorage.this.getCurrentVersion();
        }

        public boolean convert(ProgressListener progressListener) {
            this.checkValid();
            return AnvilLevelStorage.convertLevel(this, progressListener);
        }

        @Nullable
        public LevelSummary getLevelSummary() {
            this.checkValid();
            return (LevelSummary)LevelStorage.this.readLevelProperties(this.directory.toFile(), LevelStorage.this.createLevelDataParser(this.directory.toFile(), false));
        }

        @Nullable
        public SaveProperties readLevelProperties(DynamicOps<Tag> dynamicOps, DataPackSettings dataPackSettings) {
            this.checkValid();
            return (SaveProperties)LevelStorage.this.readLevelProperties(this.directory.toFile(), LevelStorage.createLevelDataParser((DynamicOps<Tag>)dynamicOps, dataPackSettings));
        }

        @Nullable
        public DataPackSettings getDataPackSettings() {
            this.checkValid();
            return (DataPackSettings)LevelStorage.this.readLevelProperties(this.directory.toFile(), (file, dataFixer) -> LevelStorage.readDataPackSettings(file, dataFixer));
        }

        public void backupLevelDataFile(DynamicRegistryManager dynamicRegistryManager, SaveProperties saveProperties) {
            this.backupLevelDataFile(dynamicRegistryManager, saveProperties, null);
        }

        public void backupLevelDataFile(DynamicRegistryManager dynamicRegistryManager, SaveProperties saveProperties, @Nullable CompoundTag compoundTag) {
            File file = this.directory.toFile();
            CompoundTag _snowman2 = saveProperties.cloneWorldTag(dynamicRegistryManager, compoundTag);
            CompoundTag _snowman3 = new CompoundTag();
            _snowman3.put("Data", _snowman2);
            try {
                _snowman = File.createTempFile("level", ".dat", file);
                NbtIo.writeCompressed(_snowman3, _snowman);
                _snowman = new File(file, "level.dat_old");
                _snowman = new File(file, "level.dat");
                Util.backupAndReplace(_snowman, _snowman, _snowman);
            }
            catch (Exception _snowman4) {
                LOGGER.error("Failed to save level {}", (Object)file, (Object)_snowman4);
            }
        }

        public File getIconFile() {
            this.checkValid();
            return this.directory.resolve("icon.png").toFile();
        }

        public void deleteSessionLock() throws IOException {
            this.checkValid();
            final Path path = this.directory.resolve("session.lock");
            for (int i = 1; i <= 5; ++i) {
                LOGGER.info("Attempt {}...", (Object)i);
                try {
                    Files.walkFileTree(this.directory, (FileVisitor<? super Path>)new SimpleFileVisitor<Path>(){

                        @Override
                        public FileVisitResult visitFile(Path path2, BasicFileAttributes basicFileAttributes) throws IOException {
                            if (!path2.equals(path)) {
                                LOGGER.debug("Deleting {}", (Object)path2);
                                Files.delete(path2);
                            }
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path path2, IOException iOException) throws IOException {
                            if (iOException != null) {
                                throw iOException;
                            }
                            if (path2.equals(Session.this.directory)) {
                                Session.this.lock.close();
                                Files.deleteIfExists(path);
                            }
                            Files.delete(path2);
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public /* synthetic */ FileVisitResult postVisitDirectory(Object object, IOException iOException) throws IOException {
                            return this.postVisitDirectory((Path)object, iOException);
                        }

                        @Override
                        public /* synthetic */ FileVisitResult visitFile(Object object, BasicFileAttributes basicFileAttributes) throws IOException {
                            return this.visitFile((Path)object, basicFileAttributes);
                        }
                    });
                    break;
                }
                catch (IOException iOException) {
                    if (i < 5) {
                        LOGGER.warn("Failed to delete {}", (Object)this.directory, (Object)iOException);
                        try {
                            Thread.sleep(500L);
                        }
                        catch (InterruptedException interruptedException) {}
                        continue;
                    }
                    throw iOException;
                }
            }
        }

        public void save(String name) throws IOException {
            this.checkValid();
            File file = new File(LevelStorage.this.savesDirectory.toFile(), this.directoryName);
            if (!file.exists()) {
                return;
            }
            _snowman = new File(file, "level.dat");
            if (_snowman.exists()) {
                CompoundTag compoundTag = NbtIo.readCompressed(_snowman);
                _snowman = compoundTag.getCompound("Data");
                _snowman.putString("LevelName", name);
                NbtIo.writeCompressed(compoundTag, _snowman);
            }
        }

        public long createBackup() throws IOException {
            this.checkValid();
            String string = LocalDateTime.now().format(TIME_FORMATTER) + "_" + this.directoryName;
            Path _snowman2 = LevelStorage.this.getBackupsDirectory();
            try {
                Files.createDirectories(Files.exists(_snowman2, new LinkOption[0]) ? _snowman2.toRealPath(new LinkOption[0]) : _snowman2, new FileAttribute[0]);
            }
            catch (IOException _snowman3) {
                throw new RuntimeException(_snowman3);
            }
            Path _snowman4 = _snowman2.resolve(FileNameUtil.getNextUniqueName(_snowman2, string, ".zip"));
            try (final ZipOutputStream _snowman5 = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(_snowman4, new OpenOption[0])));){
                final Path path = Paths.get(this.directoryName, new String[0]);
                Files.walkFileTree(this.directory, (FileVisitor<? super Path>)new SimpleFileVisitor<Path>(){

                    @Override
                    public FileVisitResult visitFile(Path path2, BasicFileAttributes basicFileAttributes) throws IOException {
                        if (path2.endsWith("session.lock")) {
                            return FileVisitResult.CONTINUE;
                        }
                        String string = path.resolve(Session.this.directory.relativize(path2)).toString().replace('\\', '/');
                        ZipEntry _snowman2 = new ZipEntry(string);
                        _snowman5.putNextEntry(_snowman2);
                        com.google.common.io.Files.asByteSource((File)path2.toFile()).copyTo((OutputStream)_snowman5);
                        _snowman5.closeEntry();
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public /* synthetic */ FileVisitResult visitFile(Object object, BasicFileAttributes basicFileAttributes) throws IOException {
                        return this.visitFile((Path)object, basicFileAttributes);
                    }
                });
            }
            return Files.size(_snowman4);
        }

        @Override
        public void close() throws IOException {
            this.lock.close();
        }
    }
}

