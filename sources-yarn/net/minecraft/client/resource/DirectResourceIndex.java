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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
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
   public Collection<Identifier> getFilesRecursively(String string, String string2, int i, Predicate<String> predicate) {
      Path path = this.assetDir.toPath().resolve(string2);

      try (Stream<Path> stream = Files.walk(path.resolve(string), i)) {
         return stream.filter(pathx -> Files.isRegularFile(pathx))
            .filter(pathx -> !pathx.endsWith(".mcmeta"))
            .filter(pathx -> predicate.test(pathx.getFileName().toString()))
            .map(path2 -> new Identifier(string2, path.relativize(path2).toString().replaceAll("\\\\", "/")))
            .collect(Collectors.toList());
      } catch (NoSuchFileException var21) {
      } catch (IOException var22) {
         LOGGER.warn("Unable to getFiles on {}", string, var22);
      }

      return Collections.emptyList();
   }
}
