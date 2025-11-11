import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.blaze3d.systems.RenderSystem;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dre extends dlv<dre.a> {
   private static final Logger a = LogManager.getLogger();
   private static final ThreadPoolExecutor o = new ScheduledThreadPoolExecutor(
      5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).setUncaughtExceptionHandler(new o(a)).build()
   );
   private static final vk p = new vk("textures/misc/unknown_server.png");
   private static final vk q = new vk("textures/gui/server_selection.png");
   private static final nr r = new of("lanServer.scanning");
   private static final nr s = new of("multiplayer.status.cannot_resolve").a(k.e);
   private static final nr t = new of("multiplayer.status.cannot_connect").a(k.e);
   private static final nr u = new of("multiplayer.status.incompatible");
   private static final nr v = new of("multiplayer.status.no_connection");
   private static final nr w = new of("multiplayer.status.pinging");
   private final drc x;
   private final List<dre.d> y = Lists.newArrayList();
   private final dre.a z = new dre.b();
   private final List<dre.c> A = Lists.newArrayList();

   public dre(drc var1, djz var2, int var3, int var4, int var5, int var6, int var7) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.x = _snowman;
   }

   private void C() {
      this.k();
      this.y.forEach(this::b);
      this.b(this.z);
      this.A.forEach(this::b);
   }

   public void a(@Nullable dre.a var1) {
      super.a(_snowman);
      if (this.h() instanceof dre.d) {
         dkz.b.a(new of("narrator.select", ((dre.d)this.h()).d.a).getString());
      }

      this.x.i();
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      dre.a _snowman = this.h();
      return _snowman != null && _snowman.a(_snowman, _snowman, _snowman) || super.a(_snowman, _snowman, _snowman);
   }

   @Override
   protected void a(dlf.b var1) {
      this.a(_snowman, var0 -> !(var0 instanceof dre.b));
   }

   public void a(dxa var1) {
      this.y.clear();

      for (int _snowman = 0; _snowman < _snowman.c(); _snowman++) {
         this.y.add(new dre.d(this.x, _snowman.a(_snowman)));
      }

      this.C();
   }

   public void a(List<enh> var1) {
      this.A.clear();

      for (enh _snowman : _snowman) {
         this.A.add(new dre.c(this.x, _snowman));
      }

      this.C();
   }

   @Override
   protected int e() {
      return super.e() + 30;
   }

   @Override
   public int d() {
      return super.d() + 85;
   }

   @Override
   protected boolean b() {
      return this.x.aw_() == this;
   }

   public abstract static class a extends dlv.a<dre.a> {
      public a() {
      }
   }

   public static class b extends dre.a {
      private final djz a = djz.C();

      public b() {
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         int _snowman = _snowman + _snowman / 2 - 9 / 2;
         this.a.g.b(_snowman, dre.r, (float)(this.a.y.k / 2 - this.a.g.a(dre.r) / 2), (float)_snowman, 16777215);
         String _snowmanx;
         switch ((int)(x.b() / 300L % 4L)) {
            case 0:
            default:
               _snowmanx = "O o o";
               break;
            case 1:
            case 3:
               _snowmanx = "o O o";
               break;
            case 2:
               _snowmanx = "o o O";
         }

         this.a.g.b(_snowman, _snowmanx, (float)(this.a.y.k / 2 - this.a.g.b(_snowmanx) / 2), (float)(_snowman + 9), 8421504);
      }
   }

   public static class c extends dre.a {
      private static final nr c = new of("lanServer.title");
      private static final nr d = new of("selectServer.hiddenAddress");
      private final drc e;
      protected final djz a;
      protected final enh b;
      private long f;

      protected c(drc var1, enh var2) {
         this.e = _snowman;
         this.b = _snowman;
         this.a = djz.C();
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.a.g.b(_snowman, c, (float)(_snowman + 32 + 3), (float)(_snowman + 1), 16777215);
         this.a.g.b(_snowman, this.b.a(), (float)(_snowman + 32 + 3), (float)(_snowman + 12), 8421504);
         if (this.a.k.o) {
            this.a.g.b(_snowman, d, (float)(_snowman + 32 + 3), (float)(_snowman + 12 + 11), 3158064);
         } else {
            this.a.g.b(_snowman, this.b.b(), (float)(_snowman + 32 + 3), (float)(_snowman + 12 + 11), 3158064);
         }
      }

      @Override
      public boolean a(double var1, double var3, int var5) {
         this.e.a(this);
         if (x.b() - this.f < 250L) {
            this.e.h();
         }

         this.f = x.b();
         return false;
      }

      public enh a() {
         return this.b;
      }
   }

   public class d extends dre.a {
      private final drc b;
      private final djz c;
      private final dwz d;
      private final vk e;
      private String f;
      private ejs g;
      private long h;

      protected d(drc var2, dwz var3) {
         this.b = _snowman;
         this.d = _snowman;
         this.c = djz.C();
         this.e = new vk("servers/" + Hashing.sha1().hashUnencodedChars(_snowman.b) + "/icon");
         this.g = (ejs)this.c.M().b(this.e);
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         if (!this.d.h) {
            this.d.h = true;
            this.d.e = -2L;
            this.d.d = oe.d;
            this.d.c = oe.d;
            dre.o.submit(() -> {
               try {
                  this.b.k().a(this.d, () -> this.c.execute(this::a));
               } catch (UnknownHostException var2x) {
                  this.d.e = -1L;
                  this.d.d = dre.s;
               } catch (Exception var3x) {
                  this.d.e = -1L;
                  this.d.d = dre.t;
               }
            });
         }

         boolean _snowman = this.d.f != w.a().getProtocolVersion();
         this.c.g.b(_snowman, this.d.a, (float)(_snowman + 32 + 3), (float)(_snowman + 1), 16777215);
         List<afa> _snowmanx = this.c.g.b(this.d.d, _snowman - 32 - 2);

         for (int _snowmanxx = 0; _snowmanxx < Math.min(_snowmanx.size(), 2); _snowmanxx++) {
            this.c.g.b(_snowman, _snowmanx.get(_snowmanxx), (float)(_snowman + 32 + 3), (float)(_snowman + 12 + 9 * _snowmanxx), 8421504);
         }

         nr _snowmanxx = (nr)(_snowman ? this.d.g.e().a(k.m) : this.d.c);
         int _snowmanxxx = this.c.g.a(_snowmanxx);
         this.c.g.b(_snowman, _snowmanxx, (float)(_snowman + _snowman - _snowmanxxx - 15 - 2), (float)(_snowman + 1), 8421504);
         int _snowmanxxxx = 0;
         int _snowmanxxxxx;
         List<nr> _snowmanxxxxxx;
         nr _snowmanxxxxxxx;
         if (_snowman) {
            _snowmanxxxxx = 5;
            _snowmanxxxxxxx = dre.u;
            _snowmanxxxxxx = this.d.i;
         } else if (this.d.h && this.d.e != -2L) {
            if (this.d.e < 0L) {
               _snowmanxxxxx = 5;
            } else if (this.d.e < 150L) {
               _snowmanxxxxx = 0;
            } else if (this.d.e < 300L) {
               _snowmanxxxxx = 1;
            } else if (this.d.e < 600L) {
               _snowmanxxxxx = 2;
            } else if (this.d.e < 1000L) {
               _snowmanxxxxx = 3;
            } else {
               _snowmanxxxxx = 4;
            }

            if (this.d.e < 0L) {
               _snowmanxxxxxxx = dre.v;
               _snowmanxxxxxx = Collections.emptyList();
            } else {
               _snowmanxxxxxxx = new of("multiplayer.status.ping", this.d.e);
               _snowmanxxxxxx = this.d.i;
            }
         } else {
            _snowmanxxxx = 1;
            _snowmanxxxxx = (int)(x.b() / 100L + (long)(_snowman * 2) & 7L);
            if (_snowmanxxxxx > 4) {
               _snowmanxxxxx = 8 - _snowmanxxxxx;
            }

            _snowmanxxxxxxx = dre.w;
            _snowmanxxxxxx = Collections.emptyList();
         }

         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.c.M().a(dkw.h);
         dkw.a(_snowman, _snowman + _snowman - 15, _snowman, (float)(_snowmanxxxx * 10), (float)(176 + _snowmanxxxxx * 8), 10, 8, 256, 256);
         String _snowmanxxxxxxxx = this.d.c();
         if (!Objects.equals(_snowmanxxxxxxxx, this.f)) {
            if (this.a(_snowmanxxxxxxxx)) {
               this.f = _snowmanxxxxxxxx;
            } else {
               this.d.a(null);
               this.a();
            }
         }

         if (this.g != null) {
            this.a(_snowman, _snowman, _snowman, this.e);
         } else {
            this.a(_snowman, _snowman, _snowman, dre.p);
         }

         int _snowmanxxxxxxxxx = _snowman - _snowman;
         int _snowmanxxxxxxxxxx = _snowman - _snowman;
         if (_snowmanxxxxxxxxx >= _snowman - 15 && _snowmanxxxxxxxxx <= _snowman - 5 && _snowmanxxxxxxxxxx >= 0 && _snowmanxxxxxxxxxx <= 8) {
            this.b.b(Collections.singletonList(_snowmanxxxxxxx));
         } else if (_snowmanxxxxxxxxx >= _snowman - _snowmanxxx - 15 - 2 && _snowmanxxxxxxxxx <= _snowman - 15 - 2 && _snowmanxxxxxxxxxx >= 0 && _snowmanxxxxxxxxxx <= 8) {
            this.b.b(_snowmanxxxxxx);
         }

         if (this.c.k.Y || _snowman) {
            this.c.M().a(dre.q);
            dkw.a(_snowman, _snowman, _snowman, _snowman + 32, _snowman + 32, -1601138544);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int _snowmanxxxxxxxxxxx = _snowman - _snowman;
            int _snowmanxxxxxxxxxxxx = _snowman - _snowman;
            if (this.c()) {
               if (_snowmanxxxxxxxxxxx < 32 && _snowmanxxxxxxxxxxx > 16) {
                  dkw.a(_snowman, _snowman, _snowman, 0.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  dkw.a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 32, 32, 256, 256);
               }
            }

            if (_snowman > 0) {
               if (_snowmanxxxxxxxxxxx < 16 && _snowmanxxxxxxxxxxxx < 16) {
                  dkw.a(_snowman, _snowman, _snowman, 96.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  dkw.a(_snowman, _snowman, _snowman, 96.0F, 0.0F, 32, 32, 256, 256);
               }
            }

            if (_snowman < this.b.l().c() - 1) {
               if (_snowmanxxxxxxxxxxx < 16 && _snowmanxxxxxxxxxxxx > 16) {
                  dkw.a(_snowman, _snowman, _snowman, 64.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  dkw.a(_snowman, _snowman, _snowman, 64.0F, 0.0F, 32, 32, 256, 256);
               }
            }
         }
      }

      public void a() {
         this.b.l().b();
      }

      protected void a(dfm var1, int var2, int var3, vk var4) {
         this.c.M().a(_snowman);
         RenderSystem.enableBlend();
         dkw.a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 32, 32, 32, 32);
         RenderSystem.disableBlend();
      }

      private boolean c() {
         return true;
      }

      private boolean a(@Nullable String var1) {
         if (_snowman == null) {
            this.c.M().c(this.e);
            if (this.g != null && this.g.e() != null) {
               this.g.e().close();
            }

            this.g = null;
         } else {
            try {
               det _snowman = det.a(_snowman);
               Validate.validState(_snowman.a() == 64, "Must be 64 pixels wide", new Object[0]);
               Validate.validState(_snowman.b() == 64, "Must be 64 pixels high", new Object[0]);
               if (this.g == null) {
                  this.g = new ejs(_snowman);
               } else {
                  this.g.a(_snowman);
                  this.g.a();
               }

               this.c.M().a(this.e, this.g);
            } catch (Throwable var3) {
               dre.a.error("Invalid icon for server {} ({})", this.d.a, this.d.b, var3);
               return false;
            }
         }

         return true;
      }

      @Override
      public boolean a(int var1, int var2, int var3) {
         if (dot.y()) {
            dre _snowman = this.b.a;
            int _snowmanx = _snowman.au_().indexOf(this);
            if (_snowman == 264 && _snowmanx < this.b.l().c() - 1 || _snowman == 265 && _snowmanx > 0) {
               this.a(_snowmanx, _snowman == 264 ? _snowmanx + 1 : _snowmanx - 1);
               return true;
            }
         }

         return super.a(_snowman, _snowman, _snowman);
      }

      private void a(int var1, int var2) {
         this.b.l().a(_snowman, _snowman);
         this.b.a.a(this.b.l());
         dre.a _snowman = this.b.a.au_().get(_snowman);
         this.b.a.a(_snowman);
         dre.this.d(_snowman);
      }

      @Override
      public boolean a(double var1, double var3, int var5) {
         double _snowman = _snowman - (double)dre.this.q();
         double _snowmanx = _snowman - (double)dre.this.h(dre.this.au_().indexOf(this));
         if (_snowman <= 32.0) {
            if (_snowman < 32.0 && _snowman > 16.0 && this.c()) {
               this.b.a(this);
               this.b.h();
               return true;
            }

            int _snowmanxx = this.b.a.au_().indexOf(this);
            if (_snowman < 16.0 && _snowmanx < 16.0 && _snowmanxx > 0) {
               this.a(_snowmanxx, _snowmanxx - 1);
               return true;
            }

            if (_snowman < 16.0 && _snowmanx > 16.0 && _snowmanxx < this.b.l().c() - 1) {
               this.a(_snowmanxx, _snowmanxx + 1);
               return true;
            }
         }

         this.b.a(this);
         if (x.b() - this.h < 250L) {
            this.b.h();
         }

         this.h = x.b();
         return false;
      }

      public dwz b() {
         return this.d;
      }
   }
}
