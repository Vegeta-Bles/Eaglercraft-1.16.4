package net.minecraft.world.level.storage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
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
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.level.LevelInfo;
import net.minecraft.world.level.LevelProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LevelStorage {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
      .appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
      .appendLiteral('-')
      .appendValue(ChronoField.MONTH_OF_YEAR, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.DAY_OF_MONTH, 2)
      .appendLiteral('_')
      .appendValue(ChronoField.HOUR_OF_DAY, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
      .appendLiteral('-')
      .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
      .toFormatter();
   private static final ImmutableList<String> GENERATOR_OPTION_KEYS = ImmutableList.of(
      "RandomSeed", "generatorName", "generatorOptions", "generatorVersion", "legacy_custom_options", "MapFeatures", "BonusChest"
   );
   private final Path savesDirectory;
   private final Path backupsDirectory;
   private final DataFixer dataFixer;

   public LevelStorage(Path savesDirectory, Path backupsDirectory, DataFixer dataFixer) {
      this.dataFixer = dataFixer;

      try {
         Files.createDirectories(Files.exists(savesDirectory) ? savesDirectory.toRealPath() : savesDirectory);
      } catch (IOException var5) {
         throw new RuntimeException(var5);
      }

      this.savesDirectory = savesDirectory;
      this.backupsDirectory = backupsDirectory;
   }

   public static LevelStorage create(Path path) {
      return new LevelStorage(path, path.resolve("../backups"), Schemas.getFixer());
   }

   private static <T> Pair<GeneratorOptions, Lifecycle> readGeneratorProperties(Dynamic<T> _snowman, DataFixer _snowman, int _snowman) {
      Dynamic<T> _snowmanxxx = _snowman.get("WorldGenSettings").orElseEmptyMap();
      UnmodifiableIterator var4 = GENERATOR_OPTION_KEYS.iterator();

      while (var4.hasNext()) {
         String _snowmanxxxx = (String)var4.next();
         Optional<? extends Dynamic<?>> _snowmanxxxxx = _snowman.get(_snowmanxxxx).result();
         if (_snowmanxxxxx.isPresent()) {
            _snowmanxxx = _snowmanxxx.set(_snowmanxxxx, _snowmanxxxxx.get());
         }
      }

      Dynamic<T> _snowmanxxxx = _snowman.update(TypeReferences.CHUNK_GENERATOR_SETTINGS, _snowmanxxx, _snowman, SharedConstants.getGameVersion().getWorldVersion());
      DataResult<GeneratorOptions> _snowmanxxxxx = GeneratorOptions.CODEC.parse(_snowmanxxxx);
      return Pair.of(
         _snowmanxxxxx.resultOrPartial(Util.method_29188("WorldGenSettings: ", LOGGER::error))
            .orElseGet(
               () -> {
                  Registry<DimensionType> _snowmanx = (Registry<DimensionType>)RegistryLookupCodec.of(Registry.DIMENSION_TYPE_KEY)
                     .codec()
                     .parse(_snowman)
                     .resultOrPartial(Util.method_29188("Dimension type registry: ", LOGGER::error))
                     .orElseThrow(() -> new IllegalStateException("Failed to get dimension registry"));
                  Registry<Biome> _snowmanxxxxxxx = (Registry<Biome>)RegistryLookupCodec.of(Registry.BIOME_KEY)
                     .codec()
                     .parse(_snowman)
                     .resultOrPartial(Util.method_29188("Biome registry: ", LOGGER::error))
                     .orElseThrow(() -> new IllegalStateException("Failed to get biome registry"));
                  Registry<ChunkGeneratorSettings> _snowmanxxxxxxxx = (Registry<ChunkGeneratorSettings>)RegistryLookupCodec.of(Registry.NOISE_SETTINGS_WORLDGEN)
                     .codec()
                     .parse(_snowman)
                     .resultOrPartial(Util.method_29188("Noise settings registry: ", LOGGER::error))
                     .orElseThrow(() -> new IllegalStateException("Failed to get noise settings registry"));
                  return GeneratorOptions.getDefaultOptions(_snowmanx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
               }
            ),
         _snowmanxxxxx.lifecycle()
      );
   }

   private static DataPackSettings parseDataPackSettings(Dynamic<?> _snowman) {
      return DataPackSettings.CODEC.parse(_snowman).resultOrPartial(LOGGER::error).orElse(DataPackSettings.SAFE_MODE);
   }

   public List<LevelSummary> getLevelList() throws LevelStorageException {
      if (!Files.isDirectory(this.savesDirectory)) {
         throw new LevelStorageException(new TranslatableText("selectWorld.load_folder_access").getString());
      } else {
         List<LevelSummary> _snowman = Lists.newArrayList();
         File[] _snowmanx = this.savesDirectory.toFile().listFiles();

         for (File _snowmanxx : _snowmanx) {
            if (_snowmanxx.isDirectory()) {
               boolean _snowmanxxx;
               try {
                  _snowmanxxx = SessionLock.isLocked(_snowmanxx.toPath());
               } catch (Exception var9) {
                  LOGGER.warn("Failed to read {} lock", _snowmanxx, var9);
                  continue;
               }

               LevelSummary _snowmanxxxx = this.readLevelProperties(_snowmanxx, this.createLevelDataParser(_snowmanxx, _snowmanxxx));
               if (_snowmanxxxx != null) {
                  _snowman.add(_snowmanxxxx);
               }
            }
         }

         return _snowman;
      }
   }

   private int getCurrentVersion() {
      return 19133;
   }

   @Nullable
   private <T> T readLevelProperties(File _snowman, BiFunction<File, DataFixer, T> levelDataParser) {
      if (!_snowman.exists()) {
         return null;
      } else {
         File _snowmanx = new File(_snowman, "level.dat");
         if (_snowmanx.exists()) {
            T _snowmanxx = levelDataParser.apply(_snowmanx, this.dataFixer);
            if (_snowmanxx != null) {
               return _snowmanxx;
            }
         }

         _snowmanx = new File(_snowman, "level.dat_old");
         return _snowmanx.exists() ? levelDataParser.apply(_snowmanx, this.dataFixer) : null;
      }
   }

   @Nullable
   private static DataPackSettings readDataPackSettings(File _snowman, DataFixer _snowman) {
      try {
         CompoundTag _snowmanxx = NbtIo.readCompressed(_snowman);
         CompoundTag _snowmanxxx = _snowmanxx.getCompound("Data");
         _snowmanxxx.remove("Player");
         int _snowmanxxxx = _snowmanxxx.contains("DataVersion", 99) ? _snowmanxxx.getInt("DataVersion") : -1;
         Dynamic<Tag> _snowmanxxxxx = _snowman.update(
            DataFixTypes.LEVEL.getTypeReference(), new Dynamic(NbtOps.INSTANCE, _snowmanxxx), _snowmanxxxx, SharedConstants.getGameVersion().getWorldVersion()
         );
         return _snowmanxxxxx.get("DataPacks").result().map(LevelStorage::parseDataPackSettings).orElse(DataPackSettings.SAFE_MODE);
      } catch (Exception var6) {
         LOGGER.error("Exception reading {}", _snowman, var6);
         return null;
      }
   }

   private static BiFunction<File, DataFixer, LevelProperties> createLevelDataParser(DynamicOps<Tag> _snowman, DataPackSettings _snowman) {
      return (_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxx) -> {
         try {
            CompoundTag _snowmanxxxx = NbtIo.readCompressed(_snowmanxxxxxxxxxxxx);
            CompoundTag _snowmanxxxxx = _snowmanxxxx.getCompound("Data");
            CompoundTag _snowmanxxxxxx = _snowmanxxxxx.contains("Player", 10) ? _snowmanxxxxx.getCompound("Player") : null;
            _snowmanxxxxx.remove("Player");
            int _snowmanxxxxxxx = _snowmanxxxxx.contains("DataVersion", 99) ? _snowmanxxxxx.getInt("DataVersion") : -1;
            Dynamic<Tag> _snowmanxxxxxxxx = _snowmanxxxxxxxxxxx.update(
               DataFixTypes.LEVEL.getTypeReference(), new Dynamic(_snowman, _snowmanxxxxx), _snowmanxxxxxxx, SharedConstants.getGameVersion().getWorldVersion()
            );
            Pair<GeneratorOptions, Lifecycle> _snowmanxxxxxxxxx = readGeneratorProperties(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxx);
            SaveVersionInfo _snowmanxxxxxxxxxx = SaveVersionInfo.fromDynamic(_snowmanxxxxxxxx);
            LevelInfo _snowmanxxxxxxxxxxx = LevelInfo.fromDynamic(_snowmanxxxxxxxx, _snowman);
            return LevelProperties.readProperties(
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxxxx,
               _snowmanxxxxxxx,
               _snowmanxxxxxx,
               _snowmanxxxxxxxxxxx,
               _snowmanxxxxxxxxxx,
               (GeneratorOptions)_snowmanxxxxxxxxx.getFirst(),
               (Lifecycle)_snowmanxxxxxxxxx.getSecond()
            );
         } catch (Exception var12) {
            LOGGER.error("Exception reading {}", _snowmanxxxxxxxxxxxx, var12);
            return null;
         }
      };
   }

   private BiFunction<File, DataFixer, LevelSummary> createLevelDataParser(File _snowman, boolean locked) {
      return (_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxx) -> {
         try {
            CompoundTag _snowmanxxxx = NbtIo.readCompressed(_snowmanxxxxxxxxxxxx);
            CompoundTag _snowmanxxxxx = _snowmanxxxx.getCompound("Data");
            _snowmanxxxxx.remove("Player");
            int _snowmanxxxxxx = _snowmanxxxxx.contains("DataVersion", 99) ? _snowmanxxxxx.getInt("DataVersion") : -1;
            Dynamic<Tag> _snowmanxxxxxxx = _snowmanxxxxxxxxxxx.update(
               DataFixTypes.LEVEL.getTypeReference(), new Dynamic(NbtOps.INSTANCE, _snowmanxxxxx), _snowmanxxxxxx, SharedConstants.getGameVersion().getWorldVersion()
            );
            SaveVersionInfo _snowmanxxxxxxxx = SaveVersionInfo.fromDynamic(_snowmanxxxxxxx);
            int _snowmanxxxxxxxxx = _snowmanxxxxxxxx.getLevelFormatVersion();
            if (_snowmanxxxxxxxxx != 19132 && _snowmanxxxxxxxxx != 19133) {
               return null;
            } else {
               boolean _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx != this.getCurrentVersion();
               File _snowmanxxxxxxxxxxx = new File(_snowman, "icon.png");
               DataPackSettings _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx.get("DataPacks").result().map(LevelStorage::parseDataPackSettings).orElse(DataPackSettings.SAFE_MODE);
               LevelInfo _snowmanxxxxxxxxxxxxx = LevelInfo.fromDynamic(_snowmanxxxxxxx, _snowmanxxxxxxxxxxxx);
               return new LevelSummary(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxx, _snowman.getName(), _snowmanxxxxxxxxxx, locked, _snowmanxxxxxxxxxxx);
            }
         } catch (Exception var15) {
            LOGGER.error("Exception reading {}", _snowmanxxxxxxxxxxxx, var15);
            return null;
         }
      };
   }

   public boolean isLevelNameValid(String name) {
      try {
         Path _snowman = this.savesDirectory.resolve(name);
         Files.createDirectory(_snowman);
         Files.deleteIfExists(_snowman);
         return true;
      } catch (IOException var3) {
         return false;
      }
   }

   public boolean levelExists(String name) {
      return Files.isDirectory(this.savesDirectory.resolve(name));
   }

   public Path getSavesDirectory() {
      return this.savesDirectory;
   }

   public Path getBackupsDirectory() {
      return this.backupsDirectory;
   }

   public LevelStorage.Session createSession(String directoryName) throws IOException {
      return new LevelStorage.Session(directoryName);
   }

   public class Session implements AutoCloseable {
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
         LevelSummary _snowman = this.getLevelSummary();
         return _snowman != null && _snowman.method_29586().getLevelFormatVersion() != LevelStorage.this.getCurrentVersion();
      }

      public boolean convert(ProgressListener progressListener) {
         this.checkValid();
         return AnvilLevelStorage.convertLevel(this, progressListener);
      }

      @Nullable
      public LevelSummary getLevelSummary() {
         this.checkValid();
         return LevelStorage.this.readLevelProperties(this.directory.toFile(), LevelStorage.this.createLevelDataParser(this.directory.toFile(), false));
      }

      @Nullable
      public SaveProperties readLevelProperties(DynamicOps<Tag> _snowman, DataPackSettings _snowman) {
         this.checkValid();
         return LevelStorage.this.readLevelProperties(this.directory.toFile(), LevelStorage.createLevelDataParser(_snowman, _snowman));
      }

      @Nullable
      public DataPackSettings getDataPackSettings() {
         this.checkValid();
         return LevelStorage.this.readLevelProperties(this.directory.toFile(), (_snowman, _snowmanx) -> LevelStorage.readDataPackSettings(_snowman, _snowmanx));
      }

      public void backupLevelDataFile(DynamicRegistryManager _snowman, SaveProperties _snowman) {
         this.backupLevelDataFile(_snowman, _snowman, null);
      }

      public void backupLevelDataFile(DynamicRegistryManager _snowman, SaveProperties _snowman, @Nullable CompoundTag _snowman) {
         File _snowmanxxx = this.directory.toFile();
         CompoundTag _snowmanxxxx = _snowman.cloneWorldTag(_snowman, _snowman);
         CompoundTag _snowmanxxxxx = new CompoundTag();
         _snowmanxxxxx.put("Data", _snowmanxxxx);

         try {
            File _snowmanxxxxxx = File.createTempFile("level", ".dat", _snowmanxxx);
            NbtIo.writeCompressed(_snowmanxxxxx, _snowmanxxxxxx);
            File _snowmanxxxxxxx = new File(_snowmanxxx, "level.dat_old");
            File _snowmanxxxxxxxx = new File(_snowmanxxx, "level.dat");
            Util.backupAndReplace(_snowmanxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
         } catch (Exception var10) {
            LevelStorage.LOGGER.error("Failed to save level {}", _snowmanxxx, var10);
         }
      }

      public File getIconFile() {
         this.checkValid();
         return this.directory.resolve("icon.png").toFile();
      }

      public void deleteSessionLock() throws IOException {
         this.checkValid();
         final Path _snowman = this.directory.resolve("session.lock");

         for (int _snowmanx = 1; _snowmanx <= 5; _snowmanx++) {
            LevelStorage.LOGGER.info("Attempt {}...", _snowmanx);

            try {
               Files.walkFileTree(this.directory, new SimpleFileVisitor<Path>() {
                  public FileVisitResult visitFile(Path _snowman, BasicFileAttributes _snowman) throws IOException {
                     if (!_snowman.equals(_snowman)) {
                        LevelStorage.LOGGER.debug("Deleting {}", _snowman);
                        Files.delete(_snowman);
                     }

                     return FileVisitResult.CONTINUE;
                  }

                  public FileVisitResult postVisitDirectory(Path _snowman, IOException _snowman) throws IOException {
                     if (_snowman != null) {
                        throw _snowman;
                     } else {
                        if (_snowman.equals(Session.this.directory)) {
                           Session.this.lock.close();
                           Files.deleteIfExists(_snowman);
                        }

                        Files.delete(_snowman);
                        return FileVisitResult.CONTINUE;
                     }
                  }
               });
               break;
            } catch (IOException var6) {
               if (_snowmanx >= 5) {
                  throw var6;
               }

               LevelStorage.LOGGER.warn("Failed to delete {}", this.directory, var6);

               try {
                  Thread.sleep(500L);
               } catch (InterruptedException var5) {
               }
            }
         }
      }

      public void save(String name) throws IOException {
         this.checkValid();
         File _snowman = new File(LevelStorage.this.savesDirectory.toFile(), this.directoryName);
         if (_snowman.exists()) {
            File _snowmanx = new File(_snowman, "level.dat");
            if (_snowmanx.exists()) {
               CompoundTag _snowmanxx = NbtIo.readCompressed(_snowmanx);
               CompoundTag _snowmanxxx = _snowmanxx.getCompound("Data");
               _snowmanxxx.putString("LevelName", name);
               NbtIo.writeCompressed(_snowmanxx, _snowmanx);
            }
         }
      }

      public long createBackup() throws IOException {
         this.checkValid();
         String _snowman = LocalDateTime.now().format(LevelStorage.TIME_FORMATTER) + "_" + this.directoryName;
         Path _snowmanx = LevelStorage.this.getBackupsDirectory();

         try {
            Files.createDirectories(Files.exists(_snowmanx) ? _snowmanx.toRealPath() : _snowmanx);
         } catch (IOException var16) {
            throw new RuntimeException(var16);
         }

         Path _snowmanxx = _snowmanx.resolve(FileNameUtil.getNextUniqueName(_snowmanx, _snowman, ".zip"));

         try (final ZipOutputStream _snowmanxxx = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(_snowmanxx)))) {
            final Path _snowmanxxxx = Paths.get(this.directoryName);
            Files.walkFileTree(this.directory, new SimpleFileVisitor<Path>() {
               public FileVisitResult visitFile(Path _snowman, BasicFileAttributes _snowman) throws IOException {
                  if (_snowman.endsWith("session.lock")) {
                     return FileVisitResult.CONTINUE;
                  } else {
                     String _snowmanxx = _snowman.resolve(Session.this.directory.relativize(_snowman)).toString().replace('\\', '/');
                     ZipEntry _snowmanxxx = new ZipEntry(_snowmanxx);
                     _snowman.putNextEntry(_snowmanxxx);
                     com.google.common.io.Files.asByteSource(_snowman.toFile()).copyTo(_snowman);
                     _snowman.closeEntry();
                     return FileVisitResult.CONTINUE;
                  }
               }
            });
         }

         return Files.size(_snowmanxx);
      }

      @Override
      public void close() throws IOException {
         this.lock.close();
      }
   }
}
