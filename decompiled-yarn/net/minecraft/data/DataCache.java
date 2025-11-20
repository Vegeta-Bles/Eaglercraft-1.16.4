package net.minecraft.data;

import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataCache {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Path root;
   private final Path recordFile;
   private int unchanged;
   private final Map<Path, String> oldSha1 = Maps.newHashMap();
   private final Map<Path, String> newSha1 = Maps.newHashMap();
   private final Set<Path> ignores = Sets.newHashSet();

   public DataCache(Path _snowman, String _snowman) throws IOException {
      this.root = _snowman;
      Path _snowmanxx = _snowman.resolve(".cache");
      Files.createDirectories(_snowmanxx);
      this.recordFile = _snowmanxx.resolve(_snowman);
      this.files().forEach(_snowmanxxx -> {
         String var10000 = this.oldSha1.put(_snowmanxxx, "");
      });
      if (Files.isReadable(this.recordFile)) {
         IOUtils.readLines(Files.newInputStream(this.recordFile), Charsets.UTF_8).forEach(_snowmanxxx -> {
            int _snowmanxxx = _snowmanxxx.indexOf(32);
            this.oldSha1.put(_snowman.resolve(_snowmanxxx.substring(_snowmanxxx + 1)), _snowmanxxx.substring(0, _snowmanxxx));
         });
      }
   }

   public void write() throws IOException {
      this.deleteAll();

      Writer _snowman;
      try {
         _snowman = Files.newBufferedWriter(this.recordFile);
      } catch (IOException var3) {
         LOGGER.warn("Unable write cachefile {}: {}", this.recordFile, var3.toString());
         return;
      }

      IOUtils.writeLines(
         this.newSha1.entrySet().stream().map(_snowmanx -> _snowmanx.getValue() + ' ' + this.root.relativize(_snowmanx.getKey())).collect(Collectors.toList()),
         System.lineSeparator(),
         _snowman
      );
      _snowman.close();
      LOGGER.debug("Caching: cache hits: {}, created: {} removed: {}", this.unchanged, this.newSha1.size() - this.unchanged, this.oldSha1.size());
   }

   @Nullable
   public String getOldSha1(Path _snowman) {
      return this.oldSha1.get(_snowman);
   }

   public void updateSha1(Path _snowman, String _snowman) {
      this.newSha1.put(_snowman, _snowman);
      if (Objects.equals(this.oldSha1.remove(_snowman), _snowman)) {
         this.unchanged++;
      }
   }

   public boolean contains(Path _snowman) {
      return this.oldSha1.containsKey(_snowman);
   }

   public void ignore(Path _snowman) {
      this.ignores.add(_snowman);
   }

   private void deleteAll() throws IOException {
      this.files().forEach(_snowman -> {
         if (this.contains(_snowman) && !this.ignores.contains(_snowman)) {
            try {
               Files.delete(_snowman);
            } catch (IOException var3) {
               LOGGER.debug("Unable to delete: {} ({})", _snowman, var3.toString());
            }
         }
      });
   }

   private Stream<Path> files() throws IOException {
      return Files.walk(this.root).filter(_snowman -> !Objects.equals(this.recordFile, _snowman) && !Files.isDirectory(_snowman));
   }
}
