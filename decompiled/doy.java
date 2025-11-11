import com.google.common.util.concurrent.Runnables;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class doy extends dot {
   private static final Logger b = LogManager.getLogger();
   public static final dzu a = new dzu(new vk("textures/gui/title/background/panorama"));
   private static final vk c = new vk("textures/gui/title/background/panorama_overlay.png");
   private static final vk p = new vk("textures/gui/accessibility.png");
   private final boolean q;
   @Nullable
   private String r;
   private dlj s;
   private static final vk t = new vk("textures/gui/title/minecraft.png");
   private static final vk u = new vk("textures/gui/title/edition.png");
   private boolean v;
   private dot w;
   private int x;
   private int y;
   private final eai z = new eai(a);
   private final boolean A;
   private long B;

   public doy() {
      this(false);
   }

   public doy(boolean var1) {
      super(new of("narrator.screen.title"));
      this.A = _snowman;
      this.q = (double)new Random().nextFloat() < 1.0E-4;
   }

   private boolean h() {
      return this.i.k.T && this.w != null;
   }

   @Override
   public void d() {
      if (this.h()) {
         this.w.d();
      }
   }

   public static CompletableFuture<Void> a(ekd var0, Executor var1) {
      return CompletableFuture.allOf(_snowman.a(t, _snowman), _snowman.a(u, _snowman), _snowman.a(c, _snowman), a.a(_snowman, _snowman));
   }

   @Override
   public boolean ay_() {
      return false;
   }

   @Override
   public boolean as_() {
      return false;
   }

   @Override
   protected void b() {
      if (this.r == null) {
         this.r = this.i.az().a();
      }

      this.x = this.o.b("Copyright Mojang AB. Do not distribute!");
      this.y = this.k - this.x - 2;
      int _snowman = 24;
      int _snowmanx = this.l / 4 + 48;
      if (this.i.v()) {
         this.c(_snowmanx, 24);
      } else {
         this.b(_snowmanx, 24);
      }

      this.a(
         new dlr(
            this.k / 2 - 124,
            _snowmanx + 72 + 12,
            20,
            20,
            0,
            106,
            20,
            dlj.i,
            256,
            256,
            var1x -> this.i.a(new dof(this, this.i.k, this.i.R())),
            new of("narrator.button.language")
         )
      );
      this.a(new dlj(this.k / 2 - 100, _snowmanx + 72 + 12, 98, 20, new of("menu.options"), var1x -> this.i.a(new dok(this, this.i.k))));
      this.a(new dlj(this.k / 2 + 2, _snowmanx + 72 + 12, 98, 20, new of("menu.quit"), var1x -> this.i.n()));
      this.a(
         new dlr(
            this.k / 2 + 104, _snowmanx + 72 + 12, 20, 20, 0, 0, 20, p, 32, 64, var1x -> this.i.a(new dnm(this, this.i.k)), new of("narrator.button.accessibility")
         )
      );
      this.i.d(false);
      if (this.i.k.T && !this.v) {
         eok _snowmanxx = new eok();
         this.w = _snowmanxx.b(this);
         this.v = true;
      }

      if (this.h()) {
         this.w.b(this.i, this.k, this.l);
      }
   }

   private void b(int var1, int var2) {
      this.a(new dlj(this.k / 2 - 100, _snowman, 200, 20, new of("menu.singleplayer"), var1x -> this.i.a(new dsj(this))));
      boolean _snowman = this.i.s();
      dlj.b _snowmanx = _snowman ? dlj.s : (var1x, var2x, var3x, var4x) -> {
         if (!var1x.o) {
            this.c(var2x, this.i.g.b(new of("title.multiplayer.disabled"), Math.max(this.k / 2 - 43, 170)), var3x, var4x);
         }
      };
      this.a(new dlj(this.k / 2 - 100, _snowman + _snowman * 1, 200, 20, new of("menu.multiplayer"), var1x -> {
         dot _snowmanxx = (dot)(this.i.k.ad ? new drc(this) : new drd(this));
         this.i.a(_snowmanxx);
      }, _snowmanx)).o = _snowman;
      this.a(new dlj(this.k / 2 - 100, _snowman + _snowman * 2, 200, 20, new of("menu.online"), var1x -> this.k(), _snowmanx)).o = _snowman;
   }

   private void c(int var1, int var2) {
      boolean _snowman = this.i();
      this.a(new dlj(this.k / 2 - 100, _snowman, 200, 20, new of("menu.playdemo"), var2x -> {
         if (_snowman) {
            this.i.a("Demo_World");
         } else {
            gn.b _snowmanx = gn.b();
            this.i.a("Demo_World", MinecraftServer.c, _snowmanx, chw.a(_snowmanx));
         }
      }));
      this.s = this.a(
         new dlj(
            this.k / 2 - 100,
            _snowman + _snowman * 1,
            200,
            20,
            new of("menu.resetdemo"),
            var1x -> {
               cyg _snowmanx = this.i.k();

               try (cyg.a _snowmanx = _snowmanx.c("Demo_World")) {
                  cyh _snowmanxx = _snowmanx.d();
                  if (_snowmanxx != null) {
                     this.i
                        .a(
                           new dns(
                              this::c,
                              new of("selectWorld.deleteQuestion"),
                              new of("selectWorld.deleteWarning", _snowmanxx.b()),
                              new of("selectWorld.deleteButton"),
                              nq.d
                           )
                        );
                  }
               } catch (IOException var16) {
                  dmp.a(this.i, "Demo_World");
                  b.warn("Failed to access demo world", var16);
               }
            }
         )
      );
      this.s.o = _snowman;
   }

   private boolean i() {
      try (cyg.a _snowman = this.i.k().c("Demo_World")) {
         return _snowman.d() != null;
      } catch (IOException var15) {
         dmp.a(this.i, "Demo_World");
         b.warn("Failed to read demo world data", var15);
         return false;
      }
   }

   private void k() {
      eok _snowman = new eok();
      _snowman.a(this);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      if (this.B == 0L && this.A) {
         this.B = x.b();
      }

      float _snowman = this.A ? (float)(x.b() - this.B) / 1000.0F : 1.0F;
      a(_snowman, 0, 0, this.k, this.l, -1);
      this.z.a(_snowman, afm.a(_snowman, 0.0F, 1.0F));
      int _snowmanx = 274;
      int _snowmanxx = this.k / 2 - 137;
      int _snowmanxxx = 30;
      this.i.M().a(c);
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(dem.r.l, dem.j.j);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.A ? (float)afm.f(afm.a(_snowman, 0.0F, 1.0F)) : 1.0F);
      a(_snowman, 0, 0, this.k, this.l, 0.0F, 0.0F, 16, 128, 16, 128);
      float _snowmanxxxx = this.A ? afm.a(_snowman - 1.0F, 0.0F, 1.0F) : 1.0F;
      int _snowmanxxxxx = afm.f(_snowmanxxxx * 255.0F) << 24;
      if ((_snowmanxxxxx & -67108864) != 0) {
         this.i.M().a(t);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, _snowmanxxxx);
         if (this.q) {
            this.a(_snowmanxx, 30, (var2x, var3x) -> {
               this.b(_snowman, var2x + 0, var3x, 0, 0, 99, 44);
               this.b(_snowman, var2x + 99, var3x, 129, 0, 27, 44);
               this.b(_snowman, var2x + 99 + 26, var3x, 126, 0, 3, 44);
               this.b(_snowman, var2x + 99 + 26 + 3, var3x, 99, 0, 26, 44);
               this.b(_snowman, var2x + 155, var3x, 0, 45, 155, 44);
            });
         } else {
            this.a(_snowmanxx, 30, (var2x, var3x) -> {
               this.b(_snowman, var2x + 0, var3x, 0, 0, 155, 44);
               this.b(_snowman, var2x + 155, var3x, 0, 45, 155, 44);
            });
         }

         this.i.M().a(u);
         a(_snowman, _snowmanxx + 88, 67, 0.0F, 0.0F, 98, 14, 128, 16);
         if (this.r != null) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef((float)(this.k / 2 + 90), 70.0F, 0.0F);
            RenderSystem.rotatef(-20.0F, 0.0F, 0.0F, 1.0F);
            float _snowmanxxxxxx = 1.8F - afm.e(afm.a((float)(x.b() % 1000L) / 1000.0F * (float) (Math.PI * 2)) * 0.1F);
            _snowmanxxxxxx = _snowmanxxxxxx * 100.0F / (float)(this.o.b(this.r) + 32);
            RenderSystem.scalef(_snowmanxxxxxx, _snowmanxxxxxx, _snowmanxxxxxx);
            a(_snowman, this.o, this.r, 0, -8, 16776960 | _snowmanxxxxx);
            RenderSystem.popMatrix();
         }

         String _snowmanxxxxxx = "Minecraft " + w.a().getName();
         if (this.i.v()) {
            _snowmanxxxxxx = _snowmanxxxxxx + " Demo";
         } else {
            _snowmanxxxxxx = _snowmanxxxxxx + ("release".equalsIgnoreCase(this.i.h()) ? "" : "/" + this.i.h());
         }

         if (this.i.d()) {
            _snowmanxxxxxx = _snowmanxxxxxx + ekx.a("menu.modded");
         }

         b(_snowman, this.o, _snowmanxxxxxx, 2, this.l - 10, 16777215 | _snowmanxxxxx);
         b(_snowman, this.o, "Copyright Mojang AB. Do not distribute!", this.y, this.l - 10, 16777215 | _snowmanxxxxx);
         if (_snowman > this.y && _snowman < this.y + this.x && _snowman > this.l - 10 && _snowman < this.l) {
            a(_snowman, this.y, this.l - 1, this.y + this.x, this.l, 16777215 | _snowmanxxxxx);
         }

         for (dlh _snowmanxxxxxxx : this.m) {
            _snowmanxxxxxxx.a(_snowmanxxxx);
         }

         super.a(_snowman, _snowman, _snowman, _snowman);
         if (this.h() && _snowmanxxxx >= 1.0F) {
            this.w.a(_snowman, _snowman, _snowman, _snowman);
         }
      }
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (super.a(_snowman, _snowman, _snowman)) {
         return true;
      } else if (this.h() && this.w.a(_snowman, _snowman, _snowman)) {
         return true;
      } else {
         if (_snowman > (double)this.y && _snowman < (double)(this.y + this.x) && _snowman > (double)(this.l - 10) && _snowman < (double)this.l) {
            this.i.a(new dpa(false, Runnables.doNothing()));
         }

         return false;
      }
   }

   @Override
   public void e() {
      if (this.w != null) {
         this.w.e();
      }
   }

   private void c(boolean var1) {
      if (_snowman) {
         try (cyg.a _snowman = this.i.k().c("Demo_World")) {
            _snowman.g();
         } catch (IOException var15) {
            dmp.b(this.i, "Demo_World");
            b.warn("Failed to delete demo world", var15);
         }
      }

      this.i.a(this);
   }
}
