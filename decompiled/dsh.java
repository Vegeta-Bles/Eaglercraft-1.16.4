import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.DataResult.PartialResult;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dsh extends dot {
   private static final Logger a = LogManager.getLogger();
   private static final Gson b = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
   private static final nr c = new of("selectWorld.enterName");
   private dlj p;
   private final BooleanConsumer q;
   private dlq r;
   private final cyg.a s;

   public dsh(BooleanConsumer var1, cyg.a var2) {
      super(new of("selectWorld.edit.title"));
      this.q = _snowman;
      this.s = _snowman;
   }

   @Override
   public void d() {
      this.r.a();
   }

   @Override
   protected void b() {
      this.i.m.a(true);
      dlj _snowman = this.a((dlj)(new dlj(this.k / 2 - 100, this.l / 4 + 0 + 5, 200, 20, new of("selectWorld.edit.resetIcon"), var1x -> {
         FileUtils.deleteQuietly(this.s.f());
         var1x.o = false;
      })));
      this.a((dlj)(new dlj(this.k / 2 - 100, this.l / 4 + 24 + 5, 200, 20, new of("selectWorld.edit.openFolder"), var1x -> x.i().a(this.s.a(cye.i).toFile()))));
      this.a((dlj)(new dlj(this.k / 2 - 100, this.l / 4 + 48 + 5, 200, 20, new of("selectWorld.edit.backup"), var1x -> {
         boolean _snowmanx = a(this.s);
         this.q.accept(!_snowmanx);
      })));
      this.a((dlj)(new dlj(this.k / 2 - 100, this.l / 4 + 72 + 5, 200, 20, new of("selectWorld.edit.backupFolder"), var1x -> {
         cyg _snowmanx = this.i.k();
         Path _snowmanx = _snowmanx.d();

         try {
            Files.createDirectories(Files.exists(_snowmanx) ? _snowmanx.toRealPath() : _snowmanx);
         } catch (IOException var5) {
            throw new RuntimeException(var5);
         }

         x.i().a(_snowmanx.toFile());
      })));
      this.a(
         (dlj)(new dlj(
            this.k / 2 - 100, this.l / 4 + 96 + 5, 200, 20, new of("selectWorld.edit.optimize"), var1x -> this.i.a(new dno(this, (var1xx, var2x) -> {
                  if (var1xx) {
                     a(this.s);
                  }

                  this.i.a(dsi.a(this.i, this.q, this.i.ai(), this.s, var2x));
               }, new of("optimizeWorld.confirm.title"), new of("optimizeWorld.confirm.description"), true))
         ))
      );
      this.a((dlj)(new dlj(this.k / 2 - 100, this.l / 4 + 120 + 5, 200, 20, new of("selectWorld.edit.export_worldgen_settings"), var1x -> {
         gn.b _snowmanx = gn.b();

         DataResult<String> _snowmanx;
         try (djz.b _snowmanxx = this.i.a(_snowmanx, djz::a, djz::a, false, this.s)) {
            DynamicOps<JsonElement> _snowmanxxx = vi.a(JsonOps.INSTANCE, _snowmanx);
            DataResult<JsonElement> _snowmanxxxx = chw.a.encodeStart(_snowmanxxx, _snowmanxx.c().A());
            _snowmanx = _snowmanxxxx.flatMap(var1xx -> {
               Path _snowmanxxxxx = this.s.a(cye.i).resolve("worldgen_settings_export.json");

               try {
                  JsonWriter _snowmanx = b.newJsonWriter(Files.newBufferedWriter(_snowmanxxxxx, StandardCharsets.UTF_8));
                  Throwable var4x = null;

                  try {
                     b.toJson(var1xx, _snowmanx);
                  } catch (Throwable var14) {
                     var4x = var14;
                     throw var14;
                  } finally {
                     if (_snowmanx != null) {
                        if (var4x != null) {
                           try {
                              _snowmanx.close();
                           } catch (Throwable var13) {
                              var4x.addSuppressed(var13);
                           }
                        } else {
                           _snowmanx.close();
                        }
                     }
                  }
               } catch (JsonIOException | IOException var16) {
                  return DataResult.error("Error writing file: " + var16.getMessage());
               }

               return DataResult.success(_snowmanxxxxx.toString());
            });
         } catch (ExecutionException | InterruptedException var18) {
            _snowmanx = DataResult.error("Could not parse level data!");
         }

         nr _snowmanxx = new oe((String)_snowmanx.get().map(Function.identity(), PartialResult::message));
         nr _snowmanxxx = new of(_snowmanx.result().isPresent() ? "selectWorld.edit.export_worldgen_settings.success" : "selectWorld.edit.export_worldgen_settings.failure");
         _snowmanx.error().ifPresent(var0 -> a.error("Error exporting world settings: {}", var0));
         this.i.an().a(dmp.a(this.i, dmp.a.d, _snowmanxxx, _snowmanxx));
      })));
      this.p = this.a((dlj)(new dlj(this.k / 2 - 100, this.l / 4 + 144 + 5, 98, 20, new of("selectWorld.edit.save"), var1x -> this.h())));
      this.a((dlj)(new dlj(this.k / 2 + 2, this.l / 4 + 144 + 5, 98, 20, nq.d, var1x -> this.q.accept(false))));
      _snowman.o = this.s.f().isFile();
      cyh _snowmanx = this.s.d();
      String _snowmanxx = _snowmanx == null ? "" : _snowmanx.b();
      this.r = new dlq(this.o, this.k / 2 - 100, 38, 200, 20, new of("selectWorld.enterName"));
      this.r.a(_snowmanxx);
      this.r.a(var1x -> this.p.o = !var1x.trim().isEmpty());
      this.e.add(this.r);
      this.b(this.r);
   }

   @Override
   public void a(djz var1, int var2, int var3) {
      String _snowman = this.r.b();
      this.b(_snowman, _snowman, _snowman);
      this.r.a(_snowman);
   }

   @Override
   public void at_() {
      this.q.accept(false);
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   private void h() {
      try {
         this.s.a(this.r.b().trim());
         this.q.accept(true);
      } catch (IOException var2) {
         a.error("Failed to access world '{}'", this.s.a(), var2);
         dmp.a(this.i, this.s.a());
         this.q.accept(true);
      }
   }

   public static void a(cyg var0, String var1) {
      boolean _snowman = false;

      try (cyg.a _snowmanx = _snowman.c(_snowman)) {
         _snowman = true;
         a(_snowmanx);
      } catch (IOException var16) {
         if (!_snowman) {
            dmp.a(djz.C(), _snowman);
         }

         a.warn("Failed to create backup of level {}", _snowman, var16);
      }
   }

   public static boolean a(cyg.a var0) {
      long _snowman = 0L;
      IOException _snowmanx = null;

      try {
         _snowman = _snowman.h();
      } catch (IOException var6) {
         _snowmanx = var6;
      }

      if (_snowmanx != null) {
         nr _snowmanxx = new of("selectWorld.edit.backupFailed");
         nr _snowmanxxx = new oe(_snowmanx.getMessage());
         djz.C().an().a(new dmp(dmp.a.c, _snowmanxx, _snowmanxxx));
         return false;
      } else {
         nr _snowmanxx = new of("selectWorld.edit.backupCreated", _snowman.a());
         nr _snowmanxxx = new of("selectWorld.edit.backupSize", afm.f((double)_snowman / 1048576.0));
         djz.C().an().a(new dmp(dmp.a.c, _snowmanxx, _snowmanxxx));
         return true;
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.d, this.k / 2, 15, 16777215);
      b(_snowman, this.o, c, this.k / 2 - 100, 24, 10526880);
      this.r.a(_snowman, _snowman, _snowman, _snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
