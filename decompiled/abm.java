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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class abm implements abj {
   public static Path a;
   private static final Logger d = LogManager.getLogger();
   public static Class<?> b;
   private static final Map<abk, FileSystem> e = x.a(Maps.newHashMap(), var0 -> {
      synchronized (abm.class) {
         for (abk _snowman : abk.values()) {
            URL _snowmanx = abm.class.getResource("/" + _snowman.a() + "/.mcassetsroot");

            try {
               URI _snowmanxx = _snowmanx.toURI();
               if ("jar".equals(_snowmanxx.getScheme())) {
                  FileSystem _snowmanxxx;
                  try {
                     _snowmanxxx = FileSystems.getFileSystem(_snowmanxx);
                  } catch (FileSystemNotFoundException var11) {
                     _snowmanxxx = FileSystems.newFileSystem(_snowmanxx, Collections.emptyMap());
                  }

                  var0.put(_snowman, _snowmanxxx);
               }
            } catch (IOException | URISyntaxException var12) {
               d.error("Couldn't get a list of all vanilla resources", var12);
            }
         }
      }
   });
   public final Set<String> c;

   public abm(String... var1) {
      this.c = ImmutableSet.copyOf(_snowman);
   }

   @Override
   public InputStream b(String var1) throws IOException {
      if (!_snowman.contains("/") && !_snowman.contains("\\")) {
         if (a != null) {
            Path _snowman = a.resolve(_snowman);
            if (Files.exists(_snowman)) {
               return Files.newInputStream(_snowman);
            }
         }

         return this.a(_snowman);
      } else {
         throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
      }
   }

   @Override
   public InputStream a(abk var1, vk var2) throws IOException {
      InputStream _snowman = this.c(_snowman, _snowman);
      if (_snowman != null) {
         return _snowman;
      } else {
         throw new FileNotFoundException(_snowman.a());
      }
   }

   @Override
   public Collection<vk> a(abk var1, String var2, String var3, int var4, Predicate<String> var5) {
      Set<vk> _snowman = Sets.newHashSet();
      if (a != null) {
         try {
            a(_snowman, _snowman, _snowman, a.resolve(_snowman.a()), _snowman, _snowman);
         } catch (IOException var15) {
         }

         if (_snowman == abk.a) {
            Enumeration<URL> _snowmanx = null;

            try {
               _snowmanx = b.getClassLoader().getResources(_snowman.a() + "/");
            } catch (IOException var14) {
            }

            while (_snowmanx != null && _snowmanx.hasMoreElements()) {
               try {
                  URI _snowmanxx = _snowmanx.nextElement().toURI();
                  if ("file".equals(_snowmanxx.getScheme())) {
                     a(_snowman, _snowman, _snowman, Paths.get(_snowmanxx), _snowman, _snowman);
                  }
               } catch (IOException | URISyntaxException var13) {
               }
            }
         }
      }

      try {
         URL _snowmanx = abm.class.getResource("/" + _snowman.a() + "/.mcassetsroot");
         if (_snowmanx == null) {
            d.error("Couldn't find .mcassetsroot, cannot load vanilla resources");
            return _snowman;
         }

         URI _snowmanxx = _snowmanx.toURI();
         if ("file".equals(_snowmanxx.getScheme())) {
            URL _snowmanxxx = new URL(_snowmanx.toString().substring(0, _snowmanx.toString().length() - ".mcassetsroot".length()));
            Path _snowmanxxxx = Paths.get(_snowmanxxx.toURI());
            a(_snowman, _snowman, _snowman, _snowmanxxxx, _snowman, _snowman);
         } else if ("jar".equals(_snowmanxx.getScheme())) {
            Path _snowmanxxx = e.get(_snowman).getPath("/" + _snowman.a());
            a(_snowman, _snowman, "minecraft", _snowmanxxx, _snowman, _snowman);
         } else {
            d.error("Unsupported scheme {} trying to list vanilla resources (NYI?)", _snowmanxx);
         }
      } catch (NoSuchFileException | FileNotFoundException var11) {
      } catch (IOException | URISyntaxException var12) {
         d.error("Couldn't get a list of all vanilla resources", var12);
      }

      return _snowman;
   }

   private static void a(Collection<vk> var0, int var1, String var2, Path var3, String var4, Predicate<String> var5) throws IOException {
      Path _snowman = _snowman.resolve(_snowman);

      try (Stream<Path> _snowmanx = Files.walk(_snowman.resolve(_snowman), _snowman)) {
         _snowmanx.filter(var1x -> !var1x.endsWith(".mcmeta") && Files.isRegularFile(var1x) && _snowman.test(var1x.getFileName().toString()))
            .map(var2x -> new vk(_snowman, _snowman.relativize(var2x).toString().replaceAll("\\\\", "/")))
            .forEach(_snowman::add);
      }
   }

   @Nullable
   protected InputStream c(abk var1, vk var2) {
      String _snowman = d(_snowman, _snowman);
      if (a != null) {
         Path _snowmanx = a.resolve(_snowman.a() + "/" + _snowman.b() + "/" + _snowman.a());
         if (Files.exists(_snowmanx)) {
            try {
               return Files.newInputStream(_snowmanx);
            } catch (IOException var7) {
            }
         }
      }

      try {
         URL _snowmanx = abm.class.getResource(_snowman);
         return a(_snowman, _snowmanx) ? _snowmanx.openStream() : null;
      } catch (IOException var6) {
         return abm.class.getResourceAsStream(_snowman);
      }
   }

   private static String d(abk var0, vk var1) {
      return "/" + _snowman.a() + "/" + _snowman.b() + "/" + _snowman.a();
   }

   private static boolean a(String var0, @Nullable URL var1) throws IOException {
      return _snowman != null && (_snowman.getProtocol().equals("jar") || abi.a(new File(_snowman.getFile()), _snowman));
   }

   @Nullable
   protected InputStream a(String var1) {
      return abm.class.getResourceAsStream("/" + _snowman);
   }

   @Override
   public boolean b(abk var1, vk var2) {
      String _snowman = d(_snowman, _snowman);
      if (a != null) {
         Path _snowmanx = a.resolve(_snowman.a() + "/" + _snowman.b() + "/" + _snowman.a());
         if (Files.exists(_snowmanx)) {
            return true;
         }
      }

      try {
         URL _snowmanx = abm.class.getResource(_snowman);
         return a(_snowman, _snowmanx);
      } catch (IOException var5) {
         return false;
      }
   }

   @Override
   public Set<String> a(abk var1) {
      return this.c;
   }

   @Nullable
   @Override
   public <T> T a(abn<T> var1) throws IOException {
      try (InputStream _snowman = this.b("pack.mcmeta")) {
         return abg.a(_snowman, _snowman);
      } catch (FileNotFoundException | RuntimeException var16) {
         return null;
      }
   }

   @Override
   public String a() {
      return "Default";
   }

   @Override
   public void close() {
   }
}
