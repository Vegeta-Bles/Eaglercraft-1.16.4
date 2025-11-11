import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dsf extends dot {
   private static final Logger p = LogManager.getLogger();
   private static final nr q = new of("selectWorld.gameMode");
   private static final nr r = new of("selectWorld.enterSeed");
   private static final nr s = new of("selectWorld.seedInfo");
   private static final nr t = new of("selectWorld.enterName");
   private static final nr u = new of("selectWorld.resultFolder");
   private static final nr v = new of("selectWorld.allowCommands.info");
   private final dot w;
   private dlq x;
   private String y;
   private dsf.b z = dsf.b.a;
   @Nullable
   private dsf.b A;
   private aor B = aor.c;
   private aor C = aor.c;
   private boolean D;
   private boolean E;
   public boolean a;
   protected brk b;
   @Nullable
   private Path F;
   @Nullable
   private abw G;
   private boolean H;
   private dlj I;
   private dlj J;
   private dlj K;
   private dlj L;
   private dlj M;
   private dlj N;
   private dlj O;
   private nr P;
   private nr Q;
   private String R;
   private brt S = new brt();
   public final dsk c;

   public dsf(@Nullable dot var1, bsa var2, chw var3, @Nullable Path var4, brk var5, gn.b var6) {
      this(_snowman, _snowman, new dsk(_snowman, _snowman, dsl.a(_snowman), OptionalLong.of(_snowman.a())));
      this.R = _snowman.a();
      this.D = _snowman.e();
      this.E = true;
      this.B = _snowman.d();
      this.C = this.B;
      this.S.a(_snowman.f(), null);
      if (_snowman.c()) {
         this.z = dsf.b.b;
      } else if (_snowman.b().f()) {
         this.z = dsf.b.a;
      } else if (_snowman.b().e()) {
         this.z = dsf.b.c;
      }

      this.F = _snowman;
   }

   public static dsf a(@Nullable dot var0) {
      gn.b _snowman = gn.b();
      return new dsf(_snowman, brk.a, new dsk(_snowman, chw.a(_snowman.b(gm.K), _snowman.b(gm.ay), _snowman.b(gm.ar)), Optional.of(dsl.a), OptionalLong.empty()));
   }

   private dsf(@Nullable dot var1, brk var2, dsk var3) {
      super(new of("selectWorld.create"));
      this.w = _snowman;
      this.R = ekx.a("selectWorld.newWorld");
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void d() {
      this.x.a();
      this.c.d();
   }

   @Override
   protected void b() {
      this.i.m.a(true);
      this.x = new dlq(this.o, this.k / 2 - 100, 60, 200, 20, new of("selectWorld.enterName")) {
         @Override
         protected nx c() {
            return super.c().c(". ").a(new of("selectWorld.resultFolder")).c(" ").c(dsf.this.y);
         }
      };
      this.x.a(this.R);
      this.x.a(var1x -> {
         this.R = var1x;
         this.I.o = !this.x.b().isEmpty();
         this.n();
      });
      this.e.add(this.x);
      int _snowman = this.k / 2 - 155;
      int _snowmanx = this.k / 2 + 5;
      this.J = this.a(new dlj(_snowman, 100, 150, 20, oe.d, var1x -> {
         switch (this.z) {
            case a:
               this.a(dsf.b.b);
               break;
            case b:
               this.a(dsf.b.c);
               break;
            case c:
               this.a(dsf.b.a);
         }

         var1x.c(250);
      }) {
         @Override
         public nr i() {
            return new of("options.generic_value", dsf.q, new of("selectWorld.gameMode." + dsf.this.z.e));
         }

         @Override
         protected nx c() {
            return super.c().c(". ").a(dsf.this.P).c(" ").a(dsf.this.Q);
         }
      });
      this.K = this.a(new dlj(_snowmanx, 100, 150, 20, new of("options.difficulty"), var1x -> {
         this.B = this.B.d();
         this.C = this.B;
         var1x.c(250);
      }) {
         @Override
         public nr i() {
            return new of("options.difficulty").c(": ").a(dsf.this.C.b());
         }
      });
      this.O = this.a(new dlj(_snowman, 151, 150, 20, new of("selectWorld.allowCommands"), var1x -> {
         this.E = true;
         this.D = !this.D;
         var1x.c(250);
      }) {
         @Override
         public nr i() {
            return nq.a(super.i(), dsf.this.D && !dsf.this.a);
         }

         @Override
         protected nx c() {
            return super.c().c(". ").a(new of("selectWorld.allowCommands.info"));
         }
      });
      this.N = this.a(new dlj(_snowmanx, 151, 150, 20, new of("selectWorld.dataPacks"), var1x -> this.r()));
      this.M = this.a(new dlj(_snowman, 185, 150, 20, new of("selectWorld.gameRules"), var1x -> this.i.a(new dsg(this.S.b(), var1xx -> {
            this.i.a(this);
            var1xx.ifPresent(var1xxx -> this.S = var1xxx);
         }))));
      this.c.a(this, this.i, this.o);
      this.L = this.a(new dlj(_snowmanx, 185, 150, 20, new of("selectWorld.moreWorldOptions"), var1x -> this.p()));
      this.I = this.a(new dlj(_snowman, this.l - 28, 150, 20, new of("selectWorld.create"), var1x -> this.o()));
      this.I.o = !this.R.isEmpty();
      this.a(new dlj(_snowmanx, this.l - 28, 150, 20, nq.d, var1x -> this.i()));
      this.h();
      this.b(this.x);
      this.a(this.z);
      this.n();
   }

   private void m() {
      this.P = new of("selectWorld.gameMode." + this.z.e + ".line1");
      this.Q = new of("selectWorld.gameMode." + this.z.e + ".line2");
   }

   private void n() {
      this.y = this.x.b().trim();
      if (this.y.isEmpty()) {
         this.y = "World";
      }

      try {
         this.y = s.a(this.i.k().c(), this.y, "");
      } catch (Exception var4) {
         this.y = "World";

         try {
            this.y = s.a(this.i.k().c(), this.y, "");
         } catch (Exception var3) {
            throw new RuntimeException("Could not create save folder", var3);
         }
      }
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   private void o() {
      this.i.c(new dod(new of("createWorld.preparing")));
      if (this.u()) {
         this.q();
         chw _snowman = this.c.a(this.a);
         bsa _snowmanx;
         if (_snowman.g()) {
            brt _snowmanxx = new brt();
            _snowmanxx.a(brt.j).a(false, null);
            _snowmanx = new bsa(this.x.b().trim(), bru.e, false, aor.a, true, _snowmanxx, brk.a);
         } else {
            _snowmanx = new bsa(this.x.b().trim(), this.z.f, this.a, this.C, this.D && !this.a, this.S, this.b);
         }

         this.i.a(this.y, _snowmanx, this.c.b(), _snowman);
      }
   }

   private void p() {
      this.c(!this.H);
   }

   private void a(dsf.b var1) {
      if (!this.E) {
         this.D = _snowman == dsf.b.c;
      }

      if (_snowman == dsf.b.b) {
         this.a = true;
         this.O.o = false;
         this.c.a.o = false;
         this.C = aor.d;
         this.K.o = false;
      } else {
         this.a = false;
         this.O.o = true;
         this.c.a.o = true;
         this.C = this.B;
         this.K.o = true;
      }

      this.z = _snowman;
      this.m();
   }

   public void h() {
      this.c(this.H);
   }

   private void c(boolean var1) {
      this.H = _snowman;
      this.J.p = !this.H;
      this.K.p = !this.H;
      if (this.c.a()) {
         this.N.p = false;
         this.J.o = false;
         if (this.A == null) {
            this.A = this.z;
         }

         this.a(dsf.b.d);
         this.O.p = false;
      } else {
         this.J.o = true;
         if (this.A != null) {
            this.a(this.A);
         }

         this.O.p = !this.H;
         this.N.p = !this.H;
      }

      this.c.b(this.H);
      this.x.i(!this.H);
      if (this.H) {
         this.L.a(nq.c);
      } else {
         this.L.a(new of("selectWorld.moreWorldOptions"));
      }

      this.M.p = !this.H;
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (super.a(_snowman, _snowman, _snowman)) {
         return true;
      } else if (_snowman != 257 && _snowman != 335) {
         return false;
      } else {
         this.o();
         return true;
      }
   }

   @Override
   public void at_() {
      if (this.H) {
         this.c(false);
      } else {
         this.i();
      }
   }

   public void i() {
      this.i.a(this.w);
      this.q();
   }

   private void q() {
      if (this.G != null) {
         this.G.close();
      }

      this.t();
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.d, this.k / 2, 20, -1);
      if (this.H) {
         b(_snowman, this.o, r, this.k / 2 - 100, 47, -6250336);
         b(_snowman, this.o, s, this.k / 2 - 100, 85, -6250336);
         this.c.a(_snowman, _snowman, _snowman, _snowman);
      } else {
         b(_snowman, this.o, t, this.k / 2 - 100, 47, -6250336);
         b(_snowman, this.o, new oe("").a(u).c(" ").c(this.y), this.k / 2 - 100, 85, -6250336);
         this.x.a(_snowman, _snowman, _snowman, _snowman);
         b(_snowman, this.o, this.P, this.k / 2 - 150, 122, -6250336);
         b(_snowman, this.o, this.Q, this.k / 2 - 150, 134, -6250336);
         if (this.O.p) {
            b(_snowman, this.o, v, this.k / 2 - 150, 172, -6250336);
         }
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected <T extends dmi> T d(T var1) {
      return super.d(_snowman);
   }

   @Override
   protected <T extends dlh> T a(T var1) {
      return super.a(_snowman);
   }

   @Nullable
   protected Path k() {
      if (this.F == null) {
         try {
            this.F = Files.createTempDirectory("mcworld-");
         } catch (IOException var2) {
            p.warn("Failed to create temporary dir", var2);
            dmp.c(this.i, this.y);
            this.i();
         }
      }

      return this.F;
   }

   private void r() {
      Pair<File, abw> _snowman = this.A();
      if (_snowman != null) {
         this.i.a(new dri(this, (abw)_snowman.getSecond(), this::a, (File)_snowman.getFirst(), new of("dataPack.title")));
      }
   }

   private void a(abw var1) {
      List<String> _snowman = ImmutableList.copyOf(_snowman.d());
      List<String> _snowmanx = _snowman.b().stream().filter(var1x -> !_snowman.contains(var1x)).collect(ImmutableList.toImmutableList());
      brk _snowmanxx = new brk(_snowman, _snowmanx);
      if (_snowman.equals(this.b.a())) {
         this.b = _snowmanxx;
      } else {
         this.i.h(() -> this.i.a(new dod(new of("dataPack.validation.working"))));
         vz.a(_snowman.f(), dc.a.c, 2, x.f(), this.i).handle((var2x, var3x) -> {
            if (var3x != null) {
               p.warn("Failed to validate datapack", var3x);
               this.i.h(() -> this.i.a(new dns(var1x -> {
                     if (var1x) {
                        this.r();
                     } else {
                        this.b = brk.a;
                        this.i.a(this);
                     }
                  }, new of("dataPack.validation.failed"), oe.d, new of("dataPack.validation.back"), new of("dataPack.validation.reset"))));
            } else {
               this.i.h(() -> {
                  this.b = _snowman;
                  this.c.a(var2x);
                  var2x.close();
                  this.i.a(this);
               });
            }

            return null;
         });
      }
   }

   private void t() {
      if (this.F != null) {
         try (Stream<Path> _snowman = Files.walk(this.F)) {
            _snowman.sorted(Comparator.reverseOrder()).forEach(var0 -> {
               try {
                  Files.delete(var0);
               } catch (IOException var2) {
                  p.warn("Failed to remove temporary file {}", var0, var2);
               }
            });
         } catch (IOException var14) {
            p.warn("Failed to list temporary dir {}", this.F);
         }

         this.F = null;
      }
   }

   private static void a(Path var0, Path var1, Path var2) {
      try {
         x.b(_snowman, _snowman, _snowman);
      } catch (IOException var4) {
         p.warn("Failed to copy datapack file from {} to {}", _snowman, _snowman);
         throw new dsf.a(var4);
      }
   }

   private boolean u() {
      if (this.F != null) {
         try (
            cyg.a _snowman = this.i.k().c(this.y);
            Stream<Path> _snowmanx = Files.walk(this.F);
         ) {
            Path _snowmanxx = _snowman.a(cye.g);
            Files.createDirectories(_snowmanxx);
            _snowmanx.filter(var1x -> !var1x.equals(this.F)).forEach(var2 -> a(this.F, _snowman, var2));
         } catch (dsf.a | IOException var33) {
            p.warn("Failed to copy datapacks to world {}", this.y, var33);
            dmp.c(this.i, this.y);
            this.i();
            return false;
         }
      }

      return true;
   }

   @Nullable
   public static Path a(Path var0, djz var1) {
      MutableObject<Path> _snowman = new MutableObject();

      try (Stream<Path> _snowmanx = Files.walk(_snowman)) {
         _snowmanx.filter(var1x -> !var1x.equals(_snowman)).forEach(var2x -> {
            Path _snowmanxx = (Path)_snowman.getValue();
            if (_snowmanxx == null) {
               try {
                  _snowmanxx = Files.createTempDirectory("mcworld-");
               } catch (IOException var5) {
                  p.warn("Failed to create temporary dir");
                  throw new dsf.a(var5);
               }

               _snowman.setValue(_snowmanxx);
            }

            a(_snowman, _snowmanxx, var2x);
         });
      } catch (dsf.a | IOException var16) {
         p.warn("Failed to copy datapacks from world {}", _snowman, var16);
         dmp.c(_snowman, _snowman.toString());
         return null;
      }

      return (Path)_snowman.getValue();
   }

   @Nullable
   private Pair<File, abw> A() {
      Path _snowman = this.k();
      if (_snowman != null) {
         File _snowmanx = _snowman.toFile();
         if (this.G == null) {
            this.G = new abw(new abz(), new abt(_snowmanx, abx.a));
            this.G.a();
         }

         this.G.a(this.b.a());
         return Pair.of(_snowmanx, this.G);
      } else {
         return null;
      }
   }

   static class a extends RuntimeException {
      public a(Throwable var1) {
         super(_snowman);
      }
   }

   static enum b {
      a("survival", bru.b),
      b("hardcore", bru.b),
      c("creative", bru.c),
      d("spectator", bru.e);

      private final String e;
      private final bru f;

      private b(String var3, bru var4) {
         this.e = _snowman;
         this.f = _snowman;
      }
   }
}
