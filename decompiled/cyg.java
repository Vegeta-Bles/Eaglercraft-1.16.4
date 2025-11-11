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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cyg {
   private static final Logger a = LogManager.getLogger();
   private static final DateTimeFormatter b = new DateTimeFormatterBuilder()
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
   private static final ImmutableList<String> c = ImmutableList.of(
      "RandomSeed", "generatorName", "generatorOptions", "generatorVersion", "legacy_custom_options", "MapFeatures", "BonusChest"
   );
   private final Path d;
   private final Path e;
   private final DataFixer f;

   public cyg(Path var1, Path var2, DataFixer var3) {
      this.f = _snowman;

      try {
         Files.createDirectories(Files.exists(_snowman) ? _snowman.toRealPath() : _snowman);
      } catch (IOException var5) {
         throw new RuntimeException(var5);
      }

      this.d = _snowman;
      this.e = _snowman;
   }

   public static cyg a(Path var0) {
      return new cyg(_snowman, _snowman.resolve("../backups"), agb.a());
   }

   private static <T> Pair<chw, Lifecycle> a(Dynamic<T> var0, DataFixer var1, int var2) {
      Dynamic<T> _snowman = _snowman.get("WorldGenSettings").orElseEmptyMap();
      UnmodifiableIterator var4 = c.iterator();

      while (var4.hasNext()) {
         String _snowmanx = (String)var4.next();
         Optional<? extends Dynamic<?>> _snowmanxx = _snowman.get(_snowmanx).result();
         if (_snowmanxx.isPresent()) {
            _snowman = _snowman.set(_snowmanx, _snowmanxx.get());
         }
      }

      Dynamic<T> _snowmanx = _snowman.update(akn.y, _snowman, _snowman, w.a().getWorldVersion());
      DataResult<chw> _snowmanxx = chw.a.parse(_snowmanx);
      return Pair.of(
         _snowmanxx.resultOrPartial(x.a("WorldGenSettings: ", a::error))
            .orElseGet(
               () -> {
                  gm<chd> _snowmanxxx = (gm<chd>)vg.a(gm.K)
                     .codec()
                     .parse(_snowman)
                     .resultOrPartial(x.a("Dimension type registry: ", a::error))
                     .orElseThrow(() -> new IllegalStateException("Failed to get dimension registry"));
                  gm<bsv> _snowmanx = (gm<bsv>)vg.a(gm.ay)
                     .codec()
                     .parse(_snowman)
                     .resultOrPartial(x.a("Biome registry: ", a::error))
                     .orElseThrow(() -> new IllegalStateException("Failed to get biome registry"));
                  gm<chp> _snowmanxx = (gm<chp>)vg.a(gm.ar)
                     .codec()
                     .parse(_snowman)
                     .resultOrPartial(x.a("Noise settings registry: ", a::error))
                     .orElseThrow(() -> new IllegalStateException("Failed to get noise settings registry"));
                  return chw.a(_snowmanxxx, _snowmanx, _snowmanxx);
               }
            ),
         _snowmanxx.lifecycle()
      );
   }

   private static brk a(Dynamic<?> var0) {
      return brk.b.parse(_snowman).resultOrPartial(a::error).orElse(brk.a);
   }

   public List<cyh> b() throws cyf {
      if (!Files.isDirectory(this.d)) {
         throw new cyf(new of("selectWorld.load_folder_access").getString());
      } else {
         List<cyh> _snowman = Lists.newArrayList();
         File[] _snowmanx = this.d.toFile().listFiles();

         for (File _snowmanxx : _snowmanx) {
            if (_snowmanxx.isDirectory()) {
               boolean _snowmanxxx;
               try {
                  _snowmanxxx = aex.b(_snowmanxx.toPath());
               } catch (Exception var9) {
                  a.warn("Failed to read {} lock", _snowmanxx, var9);
                  continue;
               }

               cyh _snowmanxxxx = this.a(_snowmanxx, this.a(_snowmanxx, _snowmanxxx));
               if (_snowmanxxxx != null) {
                  _snowman.add(_snowmanxxxx);
               }
            }
         }

         return _snowman;
      }
   }

   private int g() {
      return 19133;
   }

   @Nullable
   private <T> T a(File var1, BiFunction<File, DataFixer, T> var2) {
      if (!_snowman.exists()) {
         return null;
      } else {
         File _snowman = new File(_snowman, "level.dat");
         if (_snowman.exists()) {
            T _snowmanx = _snowman.apply(_snowman, this.f);
            if (_snowmanx != null) {
               return _snowmanx;
            }
         }

         _snowman = new File(_snowman, "level.dat_old");
         return _snowman.exists() ? _snowman.apply(_snowman, this.f) : null;
      }
   }

   @Nullable
   private static brk b(File var0, DataFixer var1) {
      try {
         md _snowman = mn.a(_snowman);
         md _snowmanx = _snowman.p("Data");
         _snowmanx.r("Player");
         int _snowmanxx = _snowmanx.c("DataVersion", 99) ? _snowmanx.h("DataVersion") : -1;
         Dynamic<mt> _snowmanxxx = _snowman.update(aga.a.a(), new Dynamic(mo.a, _snowmanx), _snowmanxx, w.a().getWorldVersion());
         return _snowmanxxx.get("DataPacks").result().map(cyg::a).orElse(brk.a);
      } catch (Exception var6) {
         a.error("Exception reading {}", _snowman, var6);
         return null;
      }
   }

   private static BiFunction<File, DataFixer, cyl> b(DynamicOps<mt> var0, brk var1) {
      return (var2, var3) -> {
         try {
            md _snowman = mn.a(var2);
            md _snowmanx = _snowman.p("Data");
            md _snowmanxx = _snowmanx.c("Player", 10) ? _snowmanx.p("Player") : null;
            _snowmanx.r("Player");
            int _snowmanxxx = _snowmanx.c("DataVersion", 99) ? _snowmanx.h("DataVersion") : -1;
            Dynamic<mt> _snowmanxxxx = var3.update(aga.a.a(), new Dynamic(_snowman, _snowmanx), _snowmanxxx, w.a().getWorldVersion());
            Pair<chw, Lifecycle> _snowmanxxxxx = a(_snowmanxxxx, var3, _snowmanxxx);
            cyi _snowmanxxxxxx = cyi.a(_snowmanxxxx);
            bsa _snowmanxxxxxxx = bsa.a(_snowmanxxxx, _snowman);
            return cyl.a(_snowmanxxxx, var3, _snowmanxxx, _snowmanxx, _snowmanxxxxxxx, _snowmanxxxxxx, (chw)_snowmanxxxxx.getFirst(), (Lifecycle)_snowmanxxxxx.getSecond());
         } catch (Exception var12) {
            a.error("Exception reading {}", var2, var12);
            return null;
         }
      };
   }

   private BiFunction<File, DataFixer, cyh> a(File var1, boolean var2) {
      return (var3, var4) -> {
         try {
            md _snowman = mn.a(var3);
            md _snowmanx = _snowman.p("Data");
            _snowmanx.r("Player");
            int _snowmanxx = _snowmanx.c("DataVersion", 99) ? _snowmanx.h("DataVersion") : -1;
            Dynamic<mt> _snowmanxxx = var4.update(aga.a.a(), new Dynamic(mo.a, _snowmanx), _snowmanxx, w.a().getWorldVersion());
            cyi _snowmanxxxx = cyi.a(_snowmanxxx);
            int _snowmanxxxxx = _snowmanxxxx.a();
            if (_snowmanxxxxx != 19132 && _snowmanxxxxx != 19133) {
               return null;
            } else {
               boolean _snowmanxxxxxx = _snowmanxxxxx != this.g();
               File _snowmanxxxxxxx = new File(_snowman, "icon.png");
               brk _snowmanxxxxxxxx = _snowmanxxx.get("DataPacks").result().map(cyg::a).orElse(brk.a);
               bsa _snowmanxxxxxxxxx = bsa.a(_snowmanxxx, _snowmanxxxxxxxx);
               return new cyh(_snowmanxxxxxxxxx, _snowmanxxxx, _snowman.getName(), _snowmanxxxxxx, _snowman, _snowmanxxxxxxx);
            }
         } catch (Exception var15) {
            a.error("Exception reading {}", var3, var15);
            return null;
         }
      };
   }

   public boolean a(String var1) {
      try {
         Path _snowman = this.d.resolve(_snowman);
         Files.createDirectory(_snowman);
         Files.deleteIfExists(_snowman);
         return true;
      } catch (IOException var3) {
         return false;
      }
   }

   public boolean b(String var1) {
      return Files.isDirectory(this.d.resolve(_snowman));
   }

   public Path c() {
      return this.d;
   }

   public Path d() {
      return this.e;
   }

   public cyg.a c(String var1) throws IOException {
      return new cyg.a(_snowman);
   }

   public class a implements AutoCloseable {
      private final aex b;
      private final Path c;
      private final String d;
      private final Map<cye, Path> e = Maps.newHashMap();

      public a(String var2) throws IOException {
         this.d = _snowman;
         this.c = cyg.this.d.resolve(_snowman);
         this.b = aex.a(this.c);
      }

      public String a() {
         return this.d;
      }

      public Path a(cye var1) {
         return this.e.computeIfAbsent(_snowman, var1x -> this.c.resolve(var1x.a()));
      }

      public File a(vj<brx> var1) {
         return chd.a(_snowman, this.c.toFile());
      }

      private void i() {
         if (!this.b.a()) {
            throw new IllegalStateException("Lock is no longer valid");
         }
      }

      public cyk b() {
         this.i();
         return new cyk(this, cyg.this.f);
      }

      public boolean c() {
         cyh _snowman = this.d();
         return _snowman != null && _snowman.k().a() != cyg.this.g();
      }

      public boolean a(afn var1) {
         this.i();
         return cyj.a(this, _snowman);
      }

      @Nullable
      public cyh d() {
         this.i();
         return cyg.this.a(this.c.toFile(), cyg.this.a(this.c.toFile(), false));
      }

      @Nullable
      public cyn a(DynamicOps<mt> var1, brk var2) {
         this.i();
         return cyg.this.a(this.c.toFile(), cyg.b(_snowman, _snowman));
      }

      @Nullable
      public brk e() {
         this.i();
         return cyg.this.a(this.c.toFile(), (BiFunction<File, DataFixer, brk>)((var0, var1) -> cyg.b(var0, var1)));
      }

      public void a(gn var1, cyn var2) {
         this.a(_snowman, _snowman, null);
      }

      public void a(gn var1, cyn var2, @Nullable md var3) {
         File _snowman = this.c.toFile();
         md _snowmanx = _snowman.a(_snowman, _snowman);
         md _snowmanxx = new md();
         _snowmanxx.a("Data", _snowmanx);

         try {
            File _snowmanxxx = File.createTempFile("level", ".dat", _snowman);
            mn.a(_snowmanxx, _snowmanxxx);
            File _snowmanxxxx = new File(_snowman, "level.dat_old");
            File _snowmanxxxxx = new File(_snowman, "level.dat");
            x.a(_snowmanxxxxx, _snowmanxxx, _snowmanxxxx);
         } catch (Exception var10) {
            cyg.a.error("Failed to save level {}", _snowman, var10);
         }
      }

      public File f() {
         this.i();
         return this.c.resolve("icon.png").toFile();
      }

      public void g() throws IOException {
         this.i();
         final Path _snowman = this.c.resolve("session.lock");

         for (int _snowmanx = 1; _snowmanx <= 5; _snowmanx++) {
            cyg.a.info("Attempt {}...", _snowmanx);

            try {
               Files.walkFileTree(this.c, new SimpleFileVisitor<Path>() {
                  public FileVisitResult a(Path var1x, BasicFileAttributes var2) throws IOException {
                     if (!_snowman.equals(_snowman)) {
                        cyg.a.debug("Deleting {}", _snowman);
                        Files.delete(_snowman);
                     }

                     return FileVisitResult.CONTINUE;
                  }

                  public FileVisitResult a(Path var1x, IOException var2) throws IOException {
                     if (_snowman != null) {
                        throw _snowman;
                     } else {
                        if (_snowman.equals(a.this.c)) {
                           a.this.b.close();
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

               cyg.a.warn("Failed to delete {}", this.c, var6);

               try {
                  Thread.sleep(500L);
               } catch (InterruptedException var5) {
               }
            }
         }
      }

      public void a(String var1) throws IOException {
         this.i();
         File _snowman = new File(cyg.this.d.toFile(), this.d);
         if (_snowman.exists()) {
            File _snowmanx = new File(_snowman, "level.dat");
            if (_snowmanx.exists()) {
               md _snowmanxx = mn.a(_snowmanx);
               md _snowmanxxx = _snowmanxx.p("Data");
               _snowmanxxx.a("LevelName", _snowman);
               mn.a(_snowmanxx, _snowmanx);
            }
         }
      }

      public long h() throws IOException {
         this.i();
         String _snowman = LocalDateTime.now().format(cyg.b) + "_" + this.d;
         Path _snowmanx = cyg.this.d();

         try {
            Files.createDirectories(Files.exists(_snowmanx) ? _snowmanx.toRealPath() : _snowmanx);
         } catch (IOException var16) {
            throw new RuntimeException(var16);
         }

         Path _snowmanxx = _snowmanx.resolve(s.a(_snowmanx, _snowman, ".zip"));

         try (final ZipOutputStream _snowmanxxx = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(_snowmanxx)))) {
            final Path _snowmanxxxx = Paths.get(this.d);
            Files.walkFileTree(this.c, new SimpleFileVisitor<Path>() {
               public FileVisitResult a(Path var1, BasicFileAttributes var2) throws IOException {
                  if (_snowman.endsWith("session.lock")) {
                     return FileVisitResult.CONTINUE;
                  } else {
                     String _snowman = _snowman.resolve(a.this.c.relativize(_snowman)).toString().replace('\\', '/');
                     ZipEntry _snowmanx = new ZipEntry(_snowman);
                     _snowman.putNextEntry(_snowmanx);
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
         this.b.close();
      }
   }
}
