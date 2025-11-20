package net.minecraft.client.resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.util.Identifier;

public class DirectResourceIndex extends ResourceIndex {
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

   @Override
   public Collection<Identifier> getFilesRecursively(String _snowman, String _snowman, int _snowman, Predicate<String> _snowman) {
      Path _snowmanxxxx = this.assetDir.toPath().resolve(_snowman);

      try (Stream<Path> _snowmanxxxxx = Files.walk(_snowmanxxxx.resolve(_snowman), _snowman)) {
         return _snowmanxxxxx.filter(_snowmanxxxxxx -> Files.isRegularFile(_snowmanxxxxxx))
            .filter(_snowmanxxxxxx -> !_snowmanxxxxxx.endsWith(".mcmeta"))
            .filter(_snowmanxxxxxx -> _snowman.test(_snowmanxxxxxx.getFileName().toString()))
            .map(_snowmanxxxxxx -> new Identifier(_snowman, _snowman.relativize(_snowmanxxxxxx).toString().replaceAll("\\\\", "/")))
            .collect(Collectors.toList());
      } catch (NoSuchFileException var21) {
      } catch (IOException var22) {
         LOGGER.warn("Unable to getFiles on {}", _snowman, var22);
      }

      return Collections.emptyList();
   }
}
