package net.minecraft.resource;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultResourcePack implements ResourcePack {
   public static Path resourcePath;
   private static final Logger LOGGER = LogManager.getLogger();
   public static Class<?> resourceClass;
   private static final Map<ResourceType, FileSystem> typeToFileSystem = Util.make(Maps.newHashMap(), _snowman -> {
      synchronized (DefaultResourcePack.class) {
         for (ResourceType _snowmanx : ResourceType.values()) {
            URL _snowmanxx = DefaultResourcePack.class.getResource("/" + _snowmanx.getDirectory() + "/.mcassetsroot");

            try {
               URI _snowmanxxx = _snowmanxx.toURI();
               if ("jar".equals(_snowmanxxx.getScheme())) {
                  FileSystem _snowmanxxxx;
                  try {
                     _snowmanxxxx = FileSystems.getFileSystem(_snowmanxxx);
                  } catch (FileSystemNotFoundException var11) {
                     _snowmanxxxx = FileSystems.newFileSystem(_snowmanxxx, Collections.emptyMap());
                  }

                  _snowman.put(_snowmanx, _snowmanxxxx);
               }
            } catch (IOException | URISyntaxException var12) {
               LOGGER.error("Couldn't get a list of all vanilla resources", var12);
            }
         }
      }
   });
   public final Set<String> namespaces;

   public DefaultResourcePack(String... namespaces) {
      this.namespaces = ImmutableSet.copyOf(namespaces);
   }

   @Override
   public InputStream openRoot(String fileName) throws IOException {
      if (!fileName.contains("/") && !fileName.contains("\\")) {
         if (resourcePath != null) {
            Path _snowman = resourcePath.resolve(fileName);
            if (Files.exists(_snowman)) {
               return Files.newInputStream(_snowman);
            }
         }

         return this.getInputStream(fileName);
      } else {
         throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
      }
   }

   @Override
   public InputStream open(ResourceType type, Identifier id) throws IOException {
      InputStream _snowman = this.findInputStream(type, id);
      if (_snowman != null) {
         return _snowman;
      } else {
         throw new FileNotFoundException(id.getPath());
      }
   }

   @Override
   public Collection<Identifier> findResources(ResourceType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter) {
      Set<Identifier> _snowman = Sets.newHashSet();
      if (resourcePath != null) {
         try {
            getIdentifiers(_snowman, maxDepth, namespace, resourcePath.resolve(type.getDirectory()), prefix, pathFilter);
         } catch (IOException var15) {
         }

         if (type == ResourceType.CLIENT_RESOURCES) {
            Enumeration<URL> _snowmanx = null;

            try {
               _snowmanx = resourceClass.getClassLoader().getResources(type.getDirectory() + "/");
            } catch (IOException var14) {
            }

            while (_snowmanx != null && _snowmanx.hasMoreElements()) {
               try {
                  URI _snowmanxx = _snowmanx.nextElement().toURI();
                  if ("file".equals(_snowmanxx.getScheme())) {
                     getIdentifiers(_snowman, maxDepth, namespace, Paths.get(_snowmanxx), prefix, pathFilter);
                  }
               } catch (IOException | URISyntaxException var13) {
               }
            }
         }
      }

      try {
         URL _snowmanx = DefaultResourcePack.class.getResource("/" + type.getDirectory() + "/.mcassetsroot");
         if (_snowmanx == null) {
            LOGGER.error("Couldn't find .mcassetsroot, cannot load vanilla resources");
            return _snowman;
         }

         URI _snowmanxx = _snowmanx.toURI();
         if ("file".equals(_snowmanxx.getScheme())) {
            URL _snowmanxxx = new URL(_snowmanx.toString().substring(0, _snowmanx.toString().length() - ".mcassetsroot".length()));
            Path _snowmanxxxx = Paths.get(_snowmanxxx.toURI());
            getIdentifiers(_snowman, maxDepth, namespace, _snowmanxxxx, prefix, pathFilter);
         } else if ("jar".equals(_snowmanxx.getScheme())) {
            Path _snowmanxxx = typeToFileSystem.get(type).getPath("/" + type.getDirectory());
            getIdentifiers(_snowman, maxDepth, "minecraft", _snowmanxxx, prefix, pathFilter);
         } else {
            LOGGER.error("Unsupported scheme {} trying to list vanilla resources (NYI?)", _snowmanxx);
         }
      } catch (NoSuchFileException | FileNotFoundException var11) {
      } catch (IOException | URISyntaxException var12) {
         LOGGER.error("Couldn't get a list of all vanilla resources", var12);
      }

      return _snowman;
   }

   private static void getIdentifiers(Collection<Identifier> _snowman, int maxDepth, String namespace, Path _snowman, String searchLocation, Predicate<String> _snowman) throws IOException {
      Path _snowmanxxx = _snowman.resolve(namespace);

      try (Stream<Path> _snowmanxxxx = Files.walk(_snowmanxxx.resolve(searchLocation), maxDepth)) {
         _snowmanxxxx.filter(_snowmanxxxxx -> !_snowmanxxxxx.endsWith(".mcmeta") && Files.isRegularFile(_snowmanxxxxx) && _snowman.test(_snowmanxxxxx.getFileName().toString()))
            .map(_snowmanxxxxx -> new Identifier(namespace, _snowman.relativize(_snowmanxxxxx).toString().replaceAll("\\\\", "/")))
            .forEach(_snowman::add);
      }
   }

   @Nullable
   protected InputStream findInputStream(ResourceType type, Identifier id) {
      String _snowman = getPath(type, id);
      if (resourcePath != null) {
         Path _snowmanx = resourcePath.resolve(type.getDirectory() + "/" + id.getNamespace() + "/" + id.getPath());
         if (Files.exists(_snowmanx)) {
            try {
               return Files.newInputStream(_snowmanx);
            } catch (IOException var7) {
            }
         }
      }

      try {
         URL _snowmanx = DefaultResourcePack.class.getResource(_snowman);
         return isValidUrl(_snowman, _snowmanx) ? _snowmanx.openStream() : null;
      } catch (IOException var6) {
         return DefaultResourcePack.class.getResourceAsStream(_snowman);
      }
   }

   private static String getPath(ResourceType type, Identifier id) {
      return "/" + type.getDirectory() + "/" + id.getNamespace() + "/" + id.getPath();
   }

   private static boolean isValidUrl(String fileName, @Nullable URL url) throws IOException {
      return url != null && (url.getProtocol().equals("jar") || DirectoryResourcePack.isValidPath(new File(url.getFile()), fileName));
   }

   @Nullable
   protected InputStream getInputStream(String path) {
      return DefaultResourcePack.class.getResourceAsStream("/" + path);
   }

   @Override
   public boolean contains(ResourceType type, Identifier id) {
      String _snowman = getPath(type, id);
      if (resourcePath != null) {
         Path _snowmanx = resourcePath.resolve(type.getDirectory() + "/" + id.getNamespace() + "/" + id.getPath());
         if (Files.exists(_snowmanx)) {
            return true;
         }
      }

      try {
         URL _snowmanx = DefaultResourcePack.class.getResource(_snowman);
         return isValidUrl(_snowman, _snowmanx);
      } catch (IOException var5) {
         return false;
      }
   }

   @Override
   public Set<String> getNamespaces(ResourceType type) {
      return this.namespaces;
   }

   @Nullable
   @Override
   public <T> T parseMetadata(ResourceMetadataReader<T> metaReader) throws IOException {
      try (InputStream _snowman = this.openRoot("pack.mcmeta")) {
         return AbstractFileResourcePack.parseMetadata(metaReader, _snowman);
      } catch (FileNotFoundException | RuntimeException var16) {
         return null;
      }
   }

   @Override
   public String getName() {
      return "Default";
   }

   @Override
   public void close() {
   }
}
