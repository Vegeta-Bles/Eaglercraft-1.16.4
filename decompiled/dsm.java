import com.google.common.collect.ImmutableList;
import com.google.common.hash.Hashing;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dsm extends dlv<dsm.a> {
   private static final Logger a = LogManager.getLogger();
   private static final DateFormat o = new SimpleDateFormat();
   private static final vk p = new vk("textures/misc/unknown_server.png");
   private static final vk q = new vk("textures/gui/world_selection.png");
   private static final nr r = new of("selectWorld.tooltip.fromNewerVersion1").a(k.m);
   private static final nr s = new of("selectWorld.tooltip.fromNewerVersion2").a(k.m);
   private static final nr t = new of("selectWorld.tooltip.snapshot1").a(k.g);
   private static final nr u = new of("selectWorld.tooltip.snapshot2").a(k.g);
   private static final nr v = new of("selectWorld.locked").a(k.m);
   private final dsj w;
   @Nullable
   private List<cyh> x;

   public dsm(dsj var1, djz var2, int var3, int var4, int var5, int var6, int var7, Supplier<String> var8, @Nullable dsm var9) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.w = _snowman;
      if (_snowman != null) {
         this.x = _snowman.x;
      }

      this.a(_snowman, false);
   }

   public void a(Supplier<String> var1, boolean var2) {
      this.k();
      cyg _snowman = this.b.k();
      if (this.x == null || _snowman) {
         try {
            this.x = _snowman.b();
         } catch (cyf var7) {
            a.error("Couldn't load level list", var7);
            this.b.a(new doc(new of("selectWorld.unable_to_load"), new oe(var7.getMessage())));
            return;
         }

         Collections.sort(this.x);
      }

      if (this.x.isEmpty()) {
         this.b.a(dsf.a(null));
      } else {
         String _snowmanx = _snowman.get().toLowerCase(Locale.ROOT);

         for (cyh _snowmanxx : this.x) {
            if (_snowmanxx.b().toLowerCase(Locale.ROOT).contains(_snowmanx) || _snowmanxx.a().toLowerCase(Locale.ROOT).contains(_snowmanx)) {
               this.b(new dsm.a(this, _snowmanxx));
            }
         }
      }
   }

   @Override
   protected int e() {
      return super.e() + 20;
   }

   @Override
   public int d() {
      return super.d() + 50;
   }

   @Override
   protected boolean b() {
      return this.w.aw_() == this;
   }

   public void a(@Nullable dsm.a var1) {
      super.a(_snowman);
      if (_snowman != null) {
         cyh _snowman = _snowman.d;
         dkz.b
            .a(
               new of(
                     "narrator.select",
                     new of(
                        "narrator.select.world",
                        _snowman.b(),
                        new Date(_snowman.e()),
                        _snowman.h() ? new of("gameMode.hardcore") : new of("gameMode." + _snowman.g().b()),
                        _snowman.i() ? new of("selectWorld.cheats") : oe.d,
                        _snowman.j()
                     )
                  )
                  .getString()
            );
      }

      this.w.c(_snowman != null && !_snowman.d.o());
   }

   @Override
   protected void a(dlf.b var1) {
      this.a(_snowman, var0 -> !var0.d.o());
   }

   public Optional<dsm.a> f() {
      return Optional.ofNullable(this.h());
   }

   public dsj g() {
      return this.w;
   }

   public final class a extends dlv.a<dsm.a> implements AutoCloseable {
      private final djz b;
      private final dsj c;
      private final cyh d;
      private final vk e;
      private File f;
      @Nullable
      private final ejs g;
      private long h;

      public a(dsm var2, cyh var3) {
         this.c = _snowman.g();
         this.d = _snowman;
         this.b = djz.C();
         String _snowman = _snowman.a();
         this.e = new vk("minecraft", "worlds/" + x.a(_snowman, vk::b) + "/" + Hashing.sha1().hashUnencodedChars(_snowman) + "/icon");
         this.f = _snowman.c();
         if (!this.f.isFile()) {
            this.f = null;
         }

         this.g = this.g();
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         String _snowman = this.d.b();
         String _snowmanx = this.d.a() + " (" + dsm.o.format(new Date(this.d.e())) + ")";
         if (StringUtils.isEmpty(_snowman)) {
            _snowman = ekx.a("selectWorld.world") + " " + (_snowman + 1);
         }

         nr _snowmanxx = this.d.p();
         this.b.g.b(_snowman, _snowman, (float)(_snowman + 32 + 3), (float)(_snowman + 1), 16777215);
         this.b.g.b(_snowman, _snowmanx, (float)(_snowman + 32 + 3), (float)(_snowman + 9 + 3), 8421504);
         this.b.g.b(_snowman, _snowmanxx, (float)(_snowman + 32 + 3), (float)(_snowman + 9 + 9 + 3), 8421504);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.b.M().a(this.g != null ? this.e : dsm.p);
         RenderSystem.enableBlend();
         dkw.a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 32, 32, 32, 32);
         RenderSystem.disableBlend();
         if (this.b.k.Y || _snowman) {
            this.b.M().a(dsm.q);
            dkw.a(_snowman, _snowman, _snowman, _snowman + 32, _snowman + 32, -1601138544);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int _snowmanxxx = _snowman - _snowman;
            boolean _snowmanxxxx = _snowmanxxx < 32;
            int _snowmanxxxxx = _snowmanxxxx ? 32 : 0;
            if (this.d.o()) {
               dkw.a(_snowman, _snowman, _snowman, 96.0F, (float)_snowmanxxxxx, 32, 32, 256, 256);
               if (_snowmanxxxx) {
                  this.c.b(this.b.g.b(dsm.v, 175));
               }
            } else if (this.d.l()) {
               dkw.a(_snowman, _snowman, _snowman, 32.0F, (float)_snowmanxxxxx, 32, 32, 256, 256);
               if (this.d.m()) {
                  dkw.a(_snowman, _snowman, _snowman, 96.0F, (float)_snowmanxxxxx, 32, 32, 256, 256);
                  if (_snowmanxxxx) {
                     this.c.b(ImmutableList.of(dsm.r.f(), dsm.s.f()));
                  }
               } else if (!w.a().isStable()) {
                  dkw.a(_snowman, _snowman, _snowman, 64.0F, (float)_snowmanxxxxx, 32, 32, 256, 256);
                  if (_snowmanxxxx) {
                     this.c.b(ImmutableList.of(dsm.t.f(), dsm.u.f()));
                  }
               }
            } else {
               dkw.a(_snowman, _snowman, _snowman, 0.0F, (float)_snowmanxxxxx, 32, 32, 256, 256);
            }
         }
      }

      @Override
      public boolean a(double var1, double var3, int var5) {
         if (this.d.o()) {
            return true;
         } else {
            dsm.this.a(this);
            this.c.c(dsm.this.f().isPresent());
            if (_snowman - (double)dsm.this.q() <= 32.0) {
               this.a();
               return true;
            } else if (x.b() - this.h < 250L) {
               this.a();
               return true;
            } else {
               this.h = x.b();
               return false;
            }
         }
      }

      public void a() {
         if (!this.d.o()) {
            if (this.d.n()) {
               nr _snowman = new of("selectWorld.backupQuestion");
               nr _snowmanx = new of("selectWorld.backupWarning", this.d.j(), w.a().getName());
               this.b.a(new dno(this.c, (var1x, var2x) -> {
                  if (var1x) {
                     String _snowmanxx = this.d.a();

                     try (cyg.a _snowmanx = this.b.k().c(_snowmanxx)) {
                        dsh.a(_snowmanx);
                     } catch (IOException var17) {
                        dmp.a(this.b, _snowmanxx);
                        dsm.a.error("Failed to backup level {}", _snowmanxx, var17);
                     }
                  }

                  this.e();
               }, _snowman, _snowmanx, false));
            } else if (this.d.m()) {
               this.b.a(new dns(var1x -> {
                  if (var1x) {
                     try {
                        this.e();
                     } catch (Exception var3) {
                        dsm.a.error("Failure to open 'future world'", var3);
                        this.b.a(new dnn(() -> this.b.a(this.c), new of("selectWorld.futureworld.error.title"), new of("selectWorld.futureworld.error.text")));
                     }
                  } else {
                     this.b.a(this.c);
                  }
               }, new of("selectWorld.versionQuestion"), new of("selectWorld.versionWarning", this.d.j(), new of("selectWorld.versionJoinButton"), nq.d)));
            } else {
               this.e();
            }
         }
      }

      public void b() {
         this.b.a(new dns(var1 -> {
            if (var1) {
               this.b.a(new dor());
               cyg _snowman = this.b.k();
               String _snowmanx = this.d.a();

               try (cyg.a _snowmanxx = _snowman.c(_snowmanx)) {
                  _snowmanxx.g();
               } catch (IOException var17) {
                  dmp.b(this.b, _snowmanx);
                  dsm.a.error("Failed to delete world {}", _snowmanx, var17);
               }

               dsm.this.a(() -> this.c.b.b(), true);
            }

            this.b.a(this.c);
         }, new of("selectWorld.deleteQuestion"), new of("selectWorld.deleteWarning", this.d.b()), new of("selectWorld.deleteButton"), nq.d));
      }

      public void c() {
         String _snowman = this.d.a();

         try {
            cyg.a _snowmanx = this.b.k().c(_snowman);
            this.b.a(new dsh(var3x -> {
               try {
                  _snowman.close();
               } catch (IOException var5) {
                  dsm.a.error("Failed to unlock level {}", _snowman, var5);
               }

               if (var3x) {
                  dsm.this.a(() -> this.c.b.b(), true);
               }

               this.b.a(this.c);
            }, _snowmanx));
         } catch (IOException var3) {
            dmp.a(this.b, _snowman);
            dsm.a.error("Failed to access level {}", _snowman, var3);
            dsm.this.a(() -> this.c.b.b(), true);
         }
      }

      public void d() {
         this.f();
         gn.b _snowman = gn.b();

         try (
            cyg.a _snowmanx = this.b.k().c(this.d.a());
            djz.b _snowmanxx = this.b.a(_snowman, djz::a, djz::a, false, _snowmanx);
         ) {
            bsa _snowmanxxx = _snowmanxx.c().I();
            brk _snowmanxxxx = _snowmanxxx.g();
            chw _snowmanxxxxx = _snowmanxx.c().A();
            Path _snowmanxxxxxx = dsf.a(_snowmanx.a(cye.g), this.b);
            if (_snowmanxxxxx.i()) {
               this.b
                  .a(
                     new dns(
                        var6x -> this.b.a((dot)(var6x ? new dsf(this.c, _snowman, _snowman, _snowman, _snowman, _snowman) : this.c)),
                        new of("selectWorld.recreate.customized.title"),
                        new of("selectWorld.recreate.customized.text"),
                        nq.g,
                        nq.d
                     )
                  );
            } else {
               this.b.a(new dsf(this.c, _snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxx, _snowman));
            }
         } catch (Exception var37) {
            dsm.a.error("Unable to recreate world", var37);
            this.b.a(new dnn(() -> this.b.a(this.c), new of("selectWorld.recreate.error.title"), new of("selectWorld.recreate.error.text")));
         }
      }

      private void e() {
         this.b.W().a(emp.a(adq.pF, 1.0F));
         if (this.b.k().b(this.d.a())) {
            this.f();
            this.b.a(this.d.a());
         }
      }

      private void f() {
         this.b.c(new dod(new of("selectWorld.data_read")));
      }

      @Nullable
      private ejs g() {
         boolean _snowman = this.f != null && this.f.isFile();
         if (_snowman) {
            try (InputStream _snowmanx = new FileInputStream(this.f)) {
               det _snowmanxx = det.a(_snowmanx);
               Validate.validState(_snowmanxx.a() == 64, "Must be 64 pixels wide", new Object[0]);
               Validate.validState(_snowmanxx.b() == 64, "Must be 64 pixels high", new Object[0]);
               ejs _snowmanxxx = new ejs(_snowmanxx);
               this.b.M().a(this.e, _snowmanxxx);
               return _snowmanxxx;
            } catch (Throwable var18) {
               dsm.a.error("Invalid icon for world {}", this.d.a(), var18);
               this.f = null;
               return null;
            }
         } else {
            this.b.M().c(this.e);
            return null;
         }
      }

      @Override
      public void close() {
         if (this.g != null) {
            this.g.close();
         }
      }
   }
}
