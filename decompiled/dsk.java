import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.DataResult.PartialResult;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

public class dsk implements dmc, dmf {
   private static final Logger b = LogManager.getLogger();
   private static final nr c = new of("generator.custom");
   private static final nr d = new of("generator.amplified.info");
   private static final nr e = new of("selectWorld.mapFeatures.info");
   private dlu f = dlu.a;
   private dku g;
   private int h;
   private dlq i;
   private dlj j;
   public dlj a;
   private dlj k;
   private dlj l;
   private dlj m;
   private gn.b n;
   private chw o;
   private Optional<dsl> p;
   private OptionalLong q;

   public dsk(gn.b var1, chw var2, Optional<dsl> var3, OptionalLong var4) {
      this.n = _snowman;
      this.o = _snowman;
      this.p = _snowman;
      this.q = _snowman;
   }

   public void a(final dsf var1, djz var2, dku var3) {
      this.g = _snowman;
      this.h = _snowman.k;
      this.i = new dlq(this.g, this.h / 2 - 100, 60, 200, 20, new of("selectWorld.enterSeed"));
      this.i.a(a(this.q));
      this.i.a(var1x -> this.q = this.f());
      _snowman.d(this.i);
      int _snowman = this.h / 2 - 155;
      int _snowmanx = this.h / 2 + 5;
      this.j = _snowman.a(new dlj(_snowman, 100, 150, 20, new of("selectWorld.mapFeatures"), var1x -> {
         this.o = this.o.k();
         var1x.c(250);
      }) {
         @Override
         public nr i() {
            return nq.a(super.i(), dsk.this.o.b());
         }

         @Override
         protected nx c() {
            return super.c().c(". ").a(new of("selectWorld.mapFeatures.info"));
         }
      });
      this.j.p = false;
      this.k = _snowman.a(new dlj(_snowmanx, 100, 150, 20, new of("selectWorld.mapType"), var2x -> {
         while (this.p.isPresent()) {
            int _snowmanxx = dsl.c.indexOf(this.p.get()) + 1;
            if (_snowmanxx >= dsl.c.size()) {
               _snowmanxx = 0;
            }

            dsl _snowmanx = dsl.c.get(_snowmanxx);
            this.p = Optional.of(_snowmanx);
            this.o = _snowmanx.a(this.n, this.o.a(), this.o.b(), this.o.c());
            if (!this.o.g() || dot.y()) {
               break;
            }
         }

         _snowman.h();
         var2x.c(250);
      }) {
         @Override
         public nr i() {
            return super.i().e().c(" ").a(dsk.this.p.map(dsl::a).orElse(dsk.c));
         }

         @Override
         protected nx c() {
            return Objects.equals(dsk.this.p, Optional.of(dsl.b)) ? super.c().c(". ").a(dsk.d) : super.c();
         }
      });
      this.k.p = false;
      this.k.o = this.p.isPresent();
      this.l = _snowman.a(new dlj(_snowmanx, 120, 150, 20, new of("selectWorld.customizeType"), var3x -> {
         dsl.a _snowmanxx = dsl.d.get(this.p);
         if (_snowmanxx != null) {
            _snowman.a(_snowmanxx.createEditScreen(_snowman, this.o));
         }
      }));
      this.l.p = false;
      this.a = _snowman.a(new dlj(_snowman, 151, 150, 20, new of("selectWorld.bonusItems"), var1x -> {
         this.o = this.o.l();
         var1x.c(250);
      }) {
         @Override
         public nr i() {
            return nq.a(super.i(), dsk.this.o.c() && !_snowman.a);
         }
      });
      this.a.p = false;
      this.m = _snowman.a(
         new dlj(
            _snowman,
            185,
            150,
            20,
            new of("selectWorld.import_worldgen_settings"),
            var3x -> {
               of _snowmanxx = new of("selectWorld.import_worldgen_settings.select_file");
               String _snowmanx = TinyFileDialogs.tinyfd_openFileDialog(_snowmanxx.getString(), null, null, null, false);
               if (_snowmanx != null) {
                  gn.b _snowmanxx = gn.b();
                  abw _snowmanxxx = new abw(new abz(), new abt(_snowman.k().toFile(), abx.c));

                  vz _snowmanxxxx;
                  try {
                     MinecraftServer.a(_snowmanxxx, _snowman.b, false);
                     CompletableFuture<vz> _snowmanxxxxx = vz.a(_snowmanxxx.f(), dc.a.c, 2, x.f(), _snowman);
                     _snowman.c(_snowmanxxxxx::isDone);
                     _snowmanxxxx = _snowmanxxxxx.get();
                  } catch (ExecutionException | InterruptedException var25) {
                     b.error("Error loading data packs when importing world settings", var25);
                     nr _snowmanxxxxxx = new of("selectWorld.import_worldgen_settings.failure");
                     nr _snowmanxxxxxxx = new oe(var25.getMessage());
                     _snowman.an().a(dmp.a(_snowman, dmp.a.d, _snowmanxxxxxx, _snowmanxxxxxxx));
                     _snowmanxxx.close();
                     return;
                  }

                  vh<JsonElement> _snowmanxxxxx = vh.a(JsonOps.INSTANCE, _snowmanxxxx.h(), _snowmanxx);
                  JsonParser _snowmanxxxxxx = new JsonParser();

                  DataResult<chw> _snowmanxxxxxxx;
                  try (BufferedReader _snowmanxxxxxxxx = Files.newBufferedReader(Paths.get(_snowmanx))) {
                     JsonElement _snowmanxxxxxxxxx = _snowmanxxxxxx.parse(_snowmanxxxxxxxx);
                     _snowmanxxxxxxx = chw.a.parse(_snowmanxxxxx, _snowmanxxxxxxxxx);
                  } catch (JsonIOException | JsonSyntaxException | IOException var27) {
                     _snowmanxxxxxxx = DataResult.error("Failed to parse file: " + var27.getMessage());
                  }

                  if (_snowmanxxxxxxx.error().isPresent()) {
                     nr _snowmanxxxxxxxx = new of("selectWorld.import_worldgen_settings.failure");
                     String _snowmanxxxxxxxxx = ((PartialResult)_snowmanxxxxxxx.error().get()).message();
                     b.error("Error parsing world settings: {}", _snowmanxxxxxxxxx);
                     nr _snowmanxxxxxxxxxx = new oe(_snowmanxxxxxxxxx);
                     _snowman.an().a(dmp.a(_snowman, dmp.a.d, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx));
                  }

                  _snowmanxxxx.close();
                  Lifecycle _snowmanxxxxxxxx = _snowmanxxxxxxx.lifecycle();
                  _snowmanxxxxxxx.resultOrPartial(b::error)
                     .ifPresent(
                        var5x -> {
                           BooleanConsumer _snowmanxxxxxxxxx = var5xx -> {
                              _snowman.a(_snowman);
                              if (var5xx) {
                                 this.a(_snowman, var5x);
                              }
                           };
                           if (_snowman == Lifecycle.stable()) {
                              this.a(_snowman, var5x);
                           } else if (_snowman == Lifecycle.experimental()) {
                              _snowman.a(
                                 new dns(
                                    _snowmanxxxxxxxxx,
                                    new of("selectWorld.import_worldgen_settings.experimental.title"),
                                    new of("selectWorld.import_worldgen_settings.experimental.question")
                                 )
                              );
                           } else {
                              _snowman.a(
                                 new dns(
                                    _snowmanxxxxxxxxx,
                                    new of("selectWorld.import_worldgen_settings.deprecated.title"),
                                    new of("selectWorld.import_worldgen_settings.deprecated.question")
                                 )
                              );
                           }
                        }
                     );
               }
            }
         )
      );
      this.m.p = false;
      this.f = dlu.a(_snowman, d, this.k.h());
   }

   private void a(gn.b var1, chw var2) {
      this.n = _snowman;
      this.o = _snowman;
      this.p = dsl.a(_snowman);
      this.q = OptionalLong.of(_snowman.a());
      this.i.a(a(this.q));
      this.k.o = this.p.isPresent();
   }

   @Override
   public void d() {
      this.i.a();
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      if (this.j.p) {
         this.g.a(_snowman, e, (float)(this.h / 2 - 150), 122.0F, -6250336);
      }

      this.i.a(_snowman, _snowman, _snowman, _snowman);
      if (this.p.equals(Optional.of(dsl.b))) {
         this.f.b(_snowman, this.k.l + 2, this.k.m + 22, 9, 10526880);
      }
   }

   protected void a(chw var1) {
      this.o = _snowman;
   }

   private static String a(OptionalLong var0) {
      return _snowman.isPresent() ? Long.toString(_snowman.getAsLong()) : "";
   }

   private static OptionalLong a(String var0) {
      try {
         return OptionalLong.of(Long.parseLong(_snowman));
      } catch (NumberFormatException var2) {
         return OptionalLong.empty();
      }
   }

   public chw a(boolean var1) {
      OptionalLong _snowman = this.f();
      return this.o.a(_snowman, _snowman);
   }

   private OptionalLong f() {
      String _snowman = this.i.b();
      OptionalLong _snowmanx;
      if (StringUtils.isEmpty(_snowman)) {
         _snowmanx = OptionalLong.empty();
      } else {
         OptionalLong _snowmanxx = a(_snowman);
         if (_snowmanxx.isPresent() && _snowmanxx.getAsLong() != 0L) {
            _snowmanx = _snowmanxx;
         } else {
            _snowmanx = OptionalLong.of((long)_snowman.hashCode());
         }
      }

      return _snowmanx;
   }

   public boolean a() {
      return this.o.g();
   }

   public void b(boolean var1) {
      this.k.p = _snowman;
      if (this.o.g()) {
         this.j.p = false;
         this.a.p = false;
         this.l.p = false;
         this.m.p = false;
      } else {
         this.j.p = _snowman;
         this.a.p = _snowman;
         this.l.p = _snowman && dsl.d.containsKey(this.p);
         this.m.p = _snowman;
      }

      this.i.i(_snowman);
   }

   public gn.b b() {
      return this.n;
   }

   void a(vz var1) {
      gn.b _snowman = gn.b();
      vi<JsonElement> _snowmanx = vi.a(JsonOps.INSTANCE, this.n);
      vh<JsonElement> _snowmanxx = vh.a(JsonOps.INSTANCE, _snowman.h(), _snowman);
      DataResult<chw> _snowmanxxx = chw.a.encodeStart(_snowmanx, this.o).flatMap(var1x -> chw.a.parse(_snowman, var1x));
      _snowmanxxx.resultOrPartial(x.a("Error parsing worldgen settings after loading data packs: ", b::error)).ifPresent(var2x -> {
         this.o = var2x;
         this.n = _snowman;
      });
   }
}
