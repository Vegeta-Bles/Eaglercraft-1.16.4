import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dfw extends eoo {
   private static final Logger a = LogManager.getLogger();
   private static final vk b = new vk("realms", "textures/gui/realms/on_icon.png");
   private static final vk c = new vk("realms", "textures/gui/realms/off_icon.png");
   private static final vk p = new vk("realms", "textures/gui/realms/expired_icon.png");
   private static final vk q = new vk("realms", "textures/gui/realms/expires_soon_icon.png");
   private static final vk r = new vk("realms", "textures/gui/realms/leave_icon.png");
   private static final vk s = new vk("realms", "textures/gui/realms/invitation_icons.png");
   private static final vk t = new vk("realms", "textures/gui/realms/invite_icon.png");
   private static final vk u = new vk("realms", "textures/gui/realms/world_icon.png");
   private static final vk v = new vk("realms", "textures/gui/title/realms.png");
   private static final vk w = new vk("realms", "textures/gui/realms/configure_icon.png");
   private static final vk x = new vk("realms", "textures/gui/realms/questionmark.png");
   private static final vk y = new vk("realms", "textures/gui/realms/news_icon.png");
   private static final vk z = new vk("realms", "textures/gui/realms/popup.png");
   private static final vk A = new vk("realms", "textures/gui/realms/darken.png");
   private static final vk B = new vk("realms", "textures/gui/realms/cross_icon.png");
   private static final vk C = new vk("realms", "textures/gui/realms/trial_icon.png");
   private static final vk D = new vk("minecraft", "textures/gui/widgets.png");
   private static final nr E = new of("mco.invites.nopending");
   private static final nr F = new of("mco.invites.pending");
   private static final List<nr> G = ImmutableList.of(new of("mco.trial.message.line1"), new of("mco.trial.message.line2"));
   private static final nr H = new of("mco.selectServer.uninitialized");
   private static final nr I = new of("mco.selectServer.expiredList");
   private static final nr J = new of("mco.selectServer.expiredRenew");
   private static final nr K = new of("mco.selectServer.expiredTrial");
   private static final nr L = new of("mco.selectServer.expiredSubscribe");
   private static final nr M = new of("mco.selectServer.minigame").c(" ");
   private static final nr N = new of("mco.selectServer.popup");
   private static final nr O = new of("mco.selectServer.expired");
   private static final nr P = new of("mco.selectServer.expires.soon");
   private static final nr Q = new of("mco.selectServer.expires.day");
   private static final nr R = new of("mco.selectServer.open");
   private static final nr S = new of("mco.selectServer.closed");
   private static final nr T = new of("mco.selectServer.leave");
   private static final nr U = new of("mco.selectServer.configure");
   private static final nr V = new of("mco.selectServer.info");
   private static final nr W = new of("mco.news");
   private static List<vk> X = ImmutableList.of();
   private static final dhl Y = new dhl();
   private static boolean Z;
   private static int aa = -1;
   private static volatile boolean ab;
   private static volatile boolean ac;
   private static volatile boolean ad;
   private static dot ae;
   private static boolean af;
   private final RateLimiter ag;
   private boolean ah;
   private final dot ai;
   private volatile dfw.f aj;
   private long ak = -1L;
   private dlj al;
   private dlj am;
   private dlj an;
   private dlj ao;
   private dlj ap;
   private List<nr> aq;
   private List<dgq> ar = Lists.newArrayList();
   private volatile int as;
   private int at;
   private boolean au;
   private boolean av;
   private boolean aw;
   private volatile boolean ax;
   private volatile boolean ay;
   private volatile boolean az;
   private volatile boolean aA;
   private volatile String aB;
   private int aC;
   private int aD;
   private boolean aE;
   private List<dfv> aF;
   private int aG;
   private ReentrantLock aH = new ReentrantLock();
   private dlu aI = dlu.a;
   private dfw.c aJ;
   private dlj aK;
   private dlj aL;
   private dlj aM;
   private dlj aN;
   private dlj aO;
   private dlj aP;

   public dfw(dot var1) {
      this.ai = _snowman;
      this.ag = RateLimiter.create(0.016666668F);
   }

   private boolean B() {
      if (C() && this.au) {
         if (this.ax && !this.ay) {
            return true;
         } else {
            for (dgq _snowman : this.ar) {
               if (_snowman.g.equals(this.i.J().b())) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean aq_() {
      if (!C() || !this.au) {
         return false;
      } else if (this.av) {
         return true;
      } else {
         return this.ax && !this.ay && this.ar.isEmpty() ? true : this.ar.isEmpty();
      }
   }

   @Override
   public void b() {
      this.aF = Lists.newArrayList(
         new dfv[]{new dfv(new char[]{'3', '2', '1', '4', '5', '6'}, () -> Z = !Z), new dfv(new char[]{'9', '8', '7', '1', '2', '3'}, () -> {
            if (dgb.a == dgb.b.b) {
               this.M();
            } else {
               this.K();
            }
         }), new dfv(new char[]{'9', '8', '7', '4', '5', '6'}, () -> {
            if (dgb.a == dgb.b.c) {
               this.M();
            } else {
               this.L();
            }
         })}
      );
      if (ae != null) {
         this.i.a(ae);
      } else {
         this.aH = new ReentrantLock();
         if (ad && !C()) {
            this.J();
         }

         this.H();
         this.I();
         if (!this.ah) {
            this.i.d(false);
         }

         this.i.m.a(true);
         if (C()) {
            Y.e();
         }

         this.az = false;
         if (C() && this.au) {
            this.c();
         }

         this.aj = new dfw.f();
         if (aa != -1) {
            this.aj.a((double)aa);
         }

         this.d(this.aj);
         this.c(this.aj);
         this.aI = dlu.a(this.o, N, 100);
      }
   }

   private static boolean C() {
      return ac && ab;
   }

   public void c() {
      this.ap = this.a((dlj)(new dlj(this.k / 2 - 202, this.l - 32, 90, 20, new of("mco.selectServer.leave"), var1x -> this.g(this.a(this.ak)))));
      this.ao = this.a((dlj)(new dlj(this.k / 2 - 190, this.l - 32, 90, 20, new of("mco.selectServer.configure"), var1x -> this.f(this.a(this.ak)))));
      this.al = this.a((dlj)(new dlj(this.k / 2 - 93, this.l - 32, 90, 20, new of("mco.selectServer.play"), var1x -> {
         dgq _snowman = this.a(this.ak);
         if (_snowman != null) {
            this.a(_snowman, this);
         }
      })));
      this.am = this.a((dlj)(new dlj(this.k / 2 + 4, this.l - 32, 90, 20, nq.h, var1x -> {
         if (!this.aw) {
            this.i.a(this.ai);
         }
      })));
      this.an = this.a((dlj)(new dlj(this.k / 2 + 100, this.l - 32, 90, 20, new of("mco.selectServer.expiredRenew"), var1x -> this.G())));
      this.aL = this.a(new dfw.e());
      this.aM = this.a(new dfw.d());
      this.aK = this.a(new dfw.h());
      this.aP = this.a(new dfw.a());
      this.aN = this.a((dlj)(new dlj(this.k / 2 + 52, this.R() + 137 - 20, 98, 20, new of("mco.selectServer.trial"), var1x -> {
         if (this.ax && !this.ay) {
            x.i().a("https://aka.ms/startjavarealmstrial");
            this.i.a(this.ai);
         }
      })));
      this.aO = this.a(
         (dlj)(new dlj(this.k / 2 + 52, this.R() + 160 - 20, 98, 20, new of("mco.selectServer.buy"), var0 -> x.i().a("https://aka.ms/BuyJavaRealms")))
      );
      dgq _snowman = this.a(this.ak);
      this.a(_snowman);
   }

   private void a(@Nullable dgq var1) {
      this.al.o = this.b(_snowman) && !this.aq_();
      this.an.p = this.c(_snowman);
      this.ao.p = this.d(_snowman);
      this.ap.p = this.e(_snowman);
      boolean _snowman = this.aq_() && this.ax && !this.ay;
      this.aN.p = _snowman;
      this.aN.o = _snowman;
      this.aO.p = this.aq_();
      this.aP.p = this.aq_() && this.av;
      this.an.o = !this.aq_();
      this.ao.o = !this.aq_();
      this.ap.o = !this.aq_();
      this.aM.o = true;
      this.aL.o = true;
      this.am.o = true;
      this.aK.o = !this.aq_();
   }

   private boolean D() {
      return (!this.aq_() || this.av) && C() && this.au;
   }

   private boolean b(@Nullable dgq var1) {
      return _snowman != null && !_snowman.j && _snowman.e == dgq.b.b;
   }

   private boolean c(@Nullable dgq var1) {
      return _snowman != null && _snowman.j && this.i(_snowman);
   }

   private boolean d(@Nullable dgq var1) {
      return _snowman != null && this.i(_snowman);
   }

   private boolean e(@Nullable dgq var1) {
      return _snowman != null && !this.i(_snowman);
   }

   @Override
   public void d() {
      super.d();
      this.aw = false;
      this.at++;
      this.aG--;
      if (this.aG < 0) {
         this.aG = 0;
      }

      if (C()) {
         Y.b();
         if (Y.a(dhl.d.a)) {
            List<dgq> _snowman = Y.f();
            this.aj.a();
            boolean _snowmanx = !this.au;
            if (_snowmanx) {
               this.au = true;
            }

            if (_snowman != null) {
               boolean _snowmanxx = false;

               for (dgq _snowmanxxx : _snowman) {
                  if (this.j(_snowmanxxx)) {
                     _snowmanxx = true;
                  }
               }

               this.ar = _snowman;
               if (this.B()) {
                  this.aj.a((dfw.b)(new dfw.i()));
               }

               for (dgq _snowmanxxxx : this.ar) {
                  this.aj.a(new dfw.g(_snowmanxxxx));
               }

               if (!af && _snowmanxx) {
                  af = true;
                  this.E();
               }
            }

            if (_snowmanx) {
               this.c();
            }
         }

         if (Y.a(dhl.d.b)) {
            this.as = Y.g();
            if (this.as > 0 && this.ag.tryAcquire(1)) {
               eoj.a(ekx.a("mco.configure.world.invite.narration", this.as));
            }
         }

         if (Y.a(dhl.d.c) && !this.ay) {
            boolean _snowmanxx = Y.h();
            if (_snowmanxx != this.ax && this.aq_()) {
               this.ax = _snowmanxx;
               this.az = false;
            } else {
               this.ax = _snowmanxx;
            }
         }

         if (Y.a(dhl.d.d)) {
            dgv _snowmanxx = Y.i();

            for (dgu _snowmanxxxx : _snowmanxx.a) {
               for (dgq _snowmanxxxxx : this.ar) {
                  if (_snowmanxxxxx.a == _snowmanxxxx.a) {
                     _snowmanxxxxx.a(_snowmanxxxx);
                     break;
                  }
               }
            }
         }

         if (Y.a(dhl.d.e)) {
            this.aA = Y.j();
            this.aB = Y.k();
         }

         Y.d();
         if (this.aq_()) {
            this.aD++;
         }

         if (this.aK != null) {
            this.aK.p = this.D();
         }
      }
   }

   private void E() {
      new Thread(() -> {
         List<dgz> _snowman = dga.a();
         dgb _snowmanx = dgb.a();
         dgm _snowmanxx = new dgm();
         _snowmanxx.a = _snowman;
         _snowmanxx.b = this.F();

         try {
            _snowmanx.a(_snowmanxx);
         } catch (Throwable var5) {
            a.warn("Could not send ping result to Realms: ", var5);
         }
      }).start();
   }

   private List<Long> F() {
      List<Long> _snowman = Lists.newArrayList();

      for (dgq _snowmanx : this.ar) {
         if (this.j(_snowmanx)) {
            _snowman.add(_snowmanx.a);
         }
      }

      return _snowman;
   }

   @Override
   public void e() {
      this.i.m.a(false);
      this.N();
   }

   private void G() {
      dgq _snowman = this.a(this.ak);
      if (_snowman != null) {
         String _snowmanx = "https://aka.ms/ExtendJavaRealms?subscriptionId="
            + _snowman.b
            + "&profileId="
            + this.i.J().b()
            + "&ref="
            + (_snowman.k ? "expiredTrial" : "expiredRealm");
         this.i.m.a(_snowmanx);
         x.i().a(_snowmanx);
      }
   }

   private void H() {
      if (!ad) {
         ad = true;
         (new Thread("MCO Compatability Checker #1") {
            @Override
            public void run() {
               dgb _snowman = dgb.a();

               try {
                  dgb.a _snowmanx = _snowman.i();
                  if (_snowmanx == dgb.a.b) {
                     dfw.ae = new dhr(dfw.this.ai, true);
                     dfw.this.i.execute(() -> dfw.this.i.a(dfw.ae));
                     return;
                  }

                  if (_snowmanx == dgb.a.c) {
                     dfw.ae = new dhr(dfw.this.ai, false);
                     dfw.this.i.execute(() -> dfw.this.i.a(dfw.ae));
                     return;
                  }

                  dfw.this.J();
               } catch (dhi var3) {
                  dfw.ad = false;
                  dfw.a.error("Couldn't connect to realms", var3);
                  if (var3.a == 401) {
                     dfw.ae = new dhw(new of("mco.error.invalid.session.title"), new of("mco.error.invalid.session.message"), dfw.this.ai);
                     dfw.this.i.execute(() -> dfw.this.i.a(dfw.ae));
                  } else {
                     dfw.this.i.execute(() -> dfw.this.i.a(new dhw(var3, dfw.this.ai)));
                  }
               }
            }
         }).start();
      }
   }

   private void I() {
   }

   private void J() {
      (new Thread("MCO Compatability Checker #1") {
         @Override
         public void run() {
            dgb _snowman = dgb.a();

            try {
               Boolean _snowmanx = _snowman.g();
               if (_snowmanx) {
                  dfw.a.info("Realms is available for this user");
                  dfw.ab = true;
               } else {
                  dfw.a.info("Realms is not available for this user");
                  dfw.ab = false;
                  dfw.this.i.execute(() -> dfw.this.i.a(new dib(dfw.this.ai)));
               }

               dfw.ac = true;
            } catch (dhi var3) {
               dfw.a.error("Couldn't connect to realms", var3);
               dfw.this.i.execute(() -> dfw.this.i.a(new dhw(var3, dfw.this.ai)));
            }
         }
      }).start();
   }

   private void K() {
      if (dgb.a != dgb.b.b) {
         (new Thread("MCO Stage Availability Checker #1") {
            @Override
            public void run() {
               dgb _snowman = dgb.a();

               try {
                  Boolean _snowmanx = _snowman.h();
                  if (_snowmanx) {
                     dgb.b();
                     dfw.a.info("Switched to stage");
                     dfw.Y.e();
                  }
               } catch (dhi var3) {
                  dfw.a.error("Couldn't connect to Realms: " + var3);
               }
            }
         }).start();
      }
   }

   private void L() {
      if (dgb.a != dgb.b.c) {
         (new Thread("MCO Local Availability Checker #1") {
            @Override
            public void run() {
               dgb _snowman = dgb.a();

               try {
                  Boolean _snowmanx = _snowman.h();
                  if (_snowmanx) {
                     dgb.d();
                     dfw.a.info("Switched to local");
                     dfw.Y.e();
                  }
               } catch (dhi var3) {
                  dfw.a.error("Couldn't connect to Realms: " + var3);
               }
            }
         }).start();
      }
   }

   private void M() {
      dgb.c();
      Y.e();
   }

   private void N() {
      Y.l();
   }

   private void f(dgq var1) {
      if (this.i.J().b().equals(_snowman.g) || Z) {
         this.O();
         this.i.a(new dhs(this, _snowman.a));
      }
   }

   private void g(@Nullable dgq var1) {
      if (_snowman != null && !this.i.J().b().equals(_snowman.g)) {
         this.O();
         nr _snowman = new of("mco.configure.world.leave.question.line1");
         nr _snowmanx = new of("mco.configure.world.leave.question.line2");
         this.i.a(new dhy(this::d, dhy.a.b, _snowman, _snowmanx, true));
      }
   }

   private void O() {
      aa = (int)this.aj.m();
   }

   @Nullable
   private dgq a(long var1) {
      for (dgq _snowman : this.ar) {
         if (_snowman.a == _snowman) {
            return _snowman;
         }
      }

      return null;
   }

   private void d(boolean var1) {
      if (_snowman) {
         (new Thread("Realms-leave-server") {
            @Override
            public void run() {
               try {
                  dgq _snowman = dfw.this.a(dfw.this.ak);
                  if (_snowman != null) {
                     dgb _snowmanx = dgb.a();
                     _snowmanx.c(_snowman.a);
                     dfw.this.i.execute(() -> dfw.this.h(_snowman));
                  }
               } catch (dhi var3) {
                  dfw.a.error("Couldn't configure world");
                  dfw.this.i.execute(() -> dfw.this.i.a(new dhw(var3, dfw.this)));
               }
            }
         }).start();
      }

      this.i.a(this);
   }

   private void h(dgq var1) {
      Y.a(_snowman);
      this.ar.remove(_snowman);
      this.aj.au_().removeIf(var1x -> var1x instanceof dfw.g && ((dfw.g)var1x).c.a == this.ak);
      this.aj.b(null);
      this.a(null);
      this.ak = -1L;
      this.al.o = false;
   }

   public void ar_() {
      this.ak = -1L;
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.aF.forEach(dfv::a);
         this.P();
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   private void P() {
      if (this.aq_() && this.av) {
         this.av = false;
      } else {
         this.i.a(this.ai);
      }
   }

   @Override
   public boolean a(char var1, int var2) {
      this.aF.forEach(var1x -> var1x.a(_snowman));
      return true;
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.aJ = dfw.c.a;
      this.aq = null;
      this.a(_snowman);
      this.aj.a(_snowman, _snowman, _snowman, _snowman);
      this.a(_snowman, this.k / 2 - 50, 7);
      if (dgb.a == dgb.b.b) {
         this.c(_snowman);
      }

      if (dgb.a == dgb.b.c) {
         this.b(_snowman);
      }

      if (this.aq_()) {
         this.b(_snowman, _snowman, _snowman);
      } else {
         if (this.az) {
            this.a(null);
            if (!this.e.contains(this.aj)) {
               this.e.add(this.aj);
            }

            dgq _snowman = this.a(this.ak);
            this.al.o = this.b(_snowman);
         }

         this.az = false;
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
      if (this.aq != null) {
         this.a(_snowman, this.aq, _snowman, _snowman);
      }

      if (this.ax && !this.ay && this.aq_()) {
         this.i.M().a(C);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int _snowman = 8;
         int _snowmanx = 8;
         int _snowmanxx = 0;
         if ((x.b() / 800L & 1L) == 1L) {
            _snowmanxx = 8;
         }

         dkw.a(_snowman, this.aN.l + this.aN.h() - 8 - 4, this.aN.m + this.aN.e() / 2 - 4, 0.0F, (float)_snowmanxx, 8, 8, 8, 16);
      }
   }

   private void a(dfm var1, int var2, int var3) {
      this.i.M().a(v);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.pushMatrix();
      RenderSystem.scalef(0.5F, 0.5F, 0.5F);
      dkw.a(_snowman, _snowman * 2, _snowman * 2 - 5, 0.0F, 0.0F, 200, 50, 200, 50);
      RenderSystem.popMatrix();
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (this.a(_snowman, _snowman) && this.av) {
         this.av = false;
         this.aw = true;
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   private boolean a(double var1, double var3) {
      int _snowman = this.Q();
      int _snowmanx = this.R();
      return _snowman < (double)(_snowman - 5) || _snowman > (double)(_snowman + 315) || _snowman < (double)(_snowmanx - 5) || _snowman > (double)(_snowmanx + 171);
   }

   private void b(dfm var1, int var2, int var3) {
      int _snowman = this.Q();
      int _snowmanx = this.R();
      if (!this.az) {
         this.aC = 0;
         this.aD = 0;
         this.aE = true;
         this.a(null);
         if (this.e.contains(this.aj)) {
            dmi _snowmanxx = this.aj;
            if (!this.e.remove(_snowmanxx)) {
               a.error("Unable to remove widget: " + _snowmanxx);
            }
         }

         eoj.a(N.getString());
      }

      if (this.au) {
         this.az = true;
      }

      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.7F);
      RenderSystem.enableBlend();
      this.i.M().a(A);
      int _snowmanxx = 0;
      int _snowmanxxx = 32;
      dkw.a(_snowman, 0, 32, 0.0F, 0.0F, this.k, this.l - 40 - 32, 310, 166);
      RenderSystem.disableBlend();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.i.M().a(z);
      dkw.a(_snowman, _snowman, _snowmanx, 0.0F, 0.0F, 310, 166, 310, 166);
      if (!X.isEmpty()) {
         this.i.M().a(X.get(this.aC));
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         dkw.a(_snowman, _snowman + 7, _snowmanx + 7, 0.0F, 0.0F, 195, 152, 195, 152);
         if (this.aD % 95 < 5) {
            if (!this.aE) {
               this.aC = (this.aC + 1) % X.size();
               this.aE = true;
            }
         } else {
            this.aE = false;
         }
      }

      this.aI.c(_snowman, this.k / 2 + 52, _snowmanx + 7, 10, 5000268);
   }

   private int Q() {
      return (this.k - 310) / 2;
   }

   private int R() {
      return this.l / 2 - 80;
   }

   private void a(dfm var1, int var2, int var3, int var4, int var5, boolean var6, boolean var7) {
      int _snowman = this.as;
      boolean _snowmanx = this.c((double)_snowman, (double)_snowman);
      boolean _snowmanxx = _snowman && _snowman;
      if (_snowmanxx) {
         float _snowmanxxx = 0.25F + (1.0F + afm.a((float)this.at * 0.5F)) * 0.25F;
         int _snowmanxxxx = 0xFF000000 | (int)(_snowmanxxx * 64.0F) << 16 | (int)(_snowmanxxx * 64.0F) << 8 | (int)(_snowmanxxx * 64.0F) << 0;
         this.a(_snowman, _snowman - 2, _snowman - 2, _snowman + 18, _snowman + 18, _snowmanxxxx, _snowmanxxxx);
         _snowmanxxxx = 0xFF000000 | (int)(_snowmanxxx * 255.0F) << 16 | (int)(_snowmanxxx * 255.0F) << 8 | (int)(_snowmanxxx * 255.0F) << 0;
         this.a(_snowman, _snowman - 2, _snowman - 2, _snowman + 18, _snowman - 1, _snowmanxxxx, _snowmanxxxx);
         this.a(_snowman, _snowman - 2, _snowman - 2, _snowman - 1, _snowman + 18, _snowmanxxxx, _snowmanxxxx);
         this.a(_snowman, _snowman + 17, _snowman - 2, _snowman + 18, _snowman + 18, _snowmanxxxx, _snowmanxxxx);
         this.a(_snowman, _snowman - 2, _snowman + 17, _snowman + 18, _snowman + 18, _snowmanxxxx, _snowmanxxxx);
      }

      this.i.M().a(t);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      boolean _snowmanxxx = _snowman && _snowman;
      float _snowmanxxxx = _snowmanxxx ? 16.0F : 0.0F;
      dkw.a(_snowman, _snowman, _snowman - 6, _snowmanxxxx, 0.0F, 15, 25, 31, 25);
      boolean _snowmanxxxxx = _snowman && _snowman != 0;
      if (_snowmanxxxxx) {
         int _snowmanxxxxxx = (Math.min(_snowman, 6) - 1) * 8;
         int _snowmanxxxxxxx = (int)(Math.max(0.0F, Math.max(afm.a((float)(10 + this.at) * 0.57F), afm.b((float)this.at * 0.35F))) * -6.0F);
         this.i.M().a(s);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         float _snowmanxxxxxxxx = _snowmanx ? 8.0F : 0.0F;
         dkw.a(_snowman, _snowman + 4, _snowman + 4 + _snowmanxxxxxxx, (float)_snowmanxxxxxx, _snowmanxxxxxxxx, 8, 8, 48, 16);
      }

      int _snowmanxxxxxx = _snowman + 12;
      boolean _snowmanxxxxxxx = _snowman && _snowmanx;
      if (_snowmanxxxxxxx) {
         nr _snowmanxxxxxxxx = _snowman == 0 ? E : F;
         int _snowmanxxxxxxxxx = this.o.a(_snowmanxxxxxxxx);
         this.a(_snowman, _snowmanxxxxxx - 3, _snowman - 3, _snowmanxxxxxx + _snowmanxxxxxxxxx + 3, _snowman + 8 + 3, -1073741824, -1073741824);
         this.o.a(_snowman, _snowmanxxxxxxxx, (float)_snowmanxxxxxx, (float)_snowman, -1);
      }
   }

   private boolean c(double var1, double var3) {
      int _snowman = this.k / 2 + 50;
      int _snowmanx = this.k / 2 + 66;
      int _snowmanxx = 11;
      int _snowmanxxx = 23;
      if (this.as != 0) {
         _snowman -= 3;
         _snowmanx += 3;
         _snowmanxx -= 5;
         _snowmanxxx += 5;
      }

      return (double)_snowman <= _snowman && _snowman <= (double)_snowmanx && (double)_snowmanxx <= _snowman && _snowman <= (double)_snowmanxxx;
   }

   public void a(dgq var1, dot var2) {
      if (_snowman != null) {
         try {
            if (!this.aH.tryLock(1L, TimeUnit.SECONDS)) {
               return;
            }

            if (this.aH.getHoldCount() > 1) {
               return;
            }
         } catch (InterruptedException var4) {
            return;
         }

         this.ah = true;
         this.i.a(new dhz(_snowman, new diz(this, _snowman, _snowman, this.aH)));
      }
   }

   private boolean i(dgq var1) {
      return _snowman.g != null && _snowman.g.equals(this.i.J().b());
   }

   private boolean j(dgq var1) {
      return this.i(_snowman) && !_snowman.j;
   }

   private void c(dfm var1, int var2, int var3, int var4, int var5) {
      this.i.M().a(p);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      dkw.a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 10, 28, 10, 28);
      if (_snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 27 && _snowman < this.l - 40 && _snowman > 32 && !this.aq_()) {
         this.a(O);
      }
   }

   private void b(dfm var1, int var2, int var3, int var4, int var5, int var6) {
      this.i.M().a(q);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.at % 20 < 10) {
         dkw.a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 10, 28, 20, 28);
      } else {
         dkw.a(_snowman, _snowman, _snowman, 10.0F, 0.0F, 10, 28, 20, 28);
      }

      if (_snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 27 && _snowman < this.l - 40 && _snowman > 32 && !this.aq_()) {
         if (_snowman <= 0) {
            this.a(P);
         } else if (_snowman == 1) {
            this.a(Q);
         } else {
            this.a(new of("mco.selectServer.expires.days", _snowman));
         }
      }
   }

   private void d(dfm var1, int var2, int var3, int var4, int var5) {
      this.i.M().a(b);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      dkw.a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 10, 28, 10, 28);
      if (_snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 27 && _snowman < this.l - 40 && _snowman > 32 && !this.aq_()) {
         this.a(R);
      }
   }

   private void e(dfm var1, int var2, int var3, int var4, int var5) {
      this.i.M().a(c);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      dkw.a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 10, 28, 10, 28);
      if (_snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 27 && _snowman < this.l - 40 && _snowman > 32 && !this.aq_()) {
         this.a(S);
      }
   }

   private void f(dfm var1, int var2, int var3, int var4, int var5) {
      boolean _snowman = false;
      if (_snowman >= _snowman && _snowman <= _snowman + 28 && _snowman >= _snowman && _snowman <= _snowman + 28 && _snowman < this.l - 40 && _snowman > 32 && !this.aq_()) {
         _snowman = true;
      }

      this.i.M().a(r);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float _snowmanx = _snowman ? 28.0F : 0.0F;
      dkw.a(_snowman, _snowman, _snowman, _snowmanx, 0.0F, 28, 28, 56, 28);
      if (_snowman) {
         this.a(T);
         this.aJ = dfw.c.c;
      }
   }

   private void g(dfm var1, int var2, int var3, int var4, int var5) {
      boolean _snowman = false;
      if (_snowman >= _snowman && _snowman <= _snowman + 28 && _snowman >= _snowman && _snowman <= _snowman + 28 && _snowman < this.l - 40 && _snowman > 32 && !this.aq_()) {
         _snowman = true;
      }

      this.i.M().a(w);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float _snowmanx = _snowman ? 28.0F : 0.0F;
      dkw.a(_snowman, _snowman, _snowman, _snowmanx, 0.0F, 28, 28, 56, 28);
      if (_snowman) {
         this.a(U);
         this.aJ = dfw.c.d;
      }
   }

   protected void a(dfm var1, List<nr> var2, int var3, int var4) {
      if (!_snowman.isEmpty()) {
         int _snowman = 0;
         int _snowmanx = 0;

         for (nr _snowmanxx : _snowman) {
            int _snowmanxxx = this.o.a(_snowmanxx);
            if (_snowmanxxx > _snowmanx) {
               _snowmanx = _snowmanxxx;
            }
         }

         int _snowmanxxx = _snowman - _snowmanx - 5;
         int _snowmanxxxx = _snowman;
         if (_snowmanxxx < 0) {
            _snowmanxxx = _snowman + 12;
         }

         for (nr _snowmanxxxxx : _snowman) {
            int _snowmanxxxxxx = _snowmanxxxx - (_snowman == 0 ? 3 : 0) + _snowman;
            this.a(_snowman, _snowmanxxx - 3, _snowmanxxxxxx, _snowmanxxx + _snowmanx + 3, _snowmanxxxx + 8 + 3 + _snowman, -1073741824, -1073741824);
            this.o.a(_snowman, _snowmanxxxxx, (float)_snowmanxxx, (float)(_snowmanxxxx + _snowman), 16777215);
            _snowman += 10;
         }
      }
   }

   private void a(dfm var1, int var2, int var3, int var4, int var5, boolean var6) {
      boolean _snowman = false;
      if (_snowman >= _snowman && _snowman <= _snowman + 20 && _snowman >= _snowman && _snowman <= _snowman + 20) {
         _snowman = true;
      }

      this.i.M().a(x);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float _snowmanx = _snowman ? 20.0F : 0.0F;
      dkw.a(_snowman, _snowman, _snowman, _snowmanx, 0.0F, 20, 20, 40, 20);
      if (_snowman) {
         this.a(V);
      }
   }

   private void a(dfm var1, int var2, int var3, boolean var4, int var5, int var6, boolean var7, boolean var8) {
      boolean _snowman = false;
      if (_snowman >= _snowman && _snowman <= _snowman + 20 && _snowman >= _snowman && _snowman <= _snowman + 20) {
         _snowman = true;
      }

      this.i.M().a(y);
      if (_snowman) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         RenderSystem.color4f(0.5F, 0.5F, 0.5F, 1.0F);
      }

      boolean _snowmanx = _snowman && _snowman;
      float _snowmanxx = _snowmanx ? 20.0F : 0.0F;
      dkw.a(_snowman, _snowman, _snowman, _snowmanxx, 0.0F, 20, 20, 40, 20);
      if (_snowman && _snowman) {
         this.a(W);
      }

      if (_snowman && _snowman) {
         int _snowmanxxx = _snowman ? 0 : (int)(Math.max(0.0F, Math.max(afm.a((float)(10 + this.at) * 0.57F), afm.b((float)this.at * 0.35F))) * -6.0F);
         this.i.M().a(s);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         dkw.a(_snowman, _snowman + 10, _snowman + 2 + _snowmanxxx, 40.0F, 0.0F, 8, 8, 48, 16);
      }
   }

   private void b(dfm var1) {
      String _snowman = "LOCAL!";
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.pushMatrix();
      RenderSystem.translatef((float)(this.k / 2 - 25), 20.0F, 0.0F);
      RenderSystem.rotatef(-20.0F, 0.0F, 0.0F, 1.0F);
      RenderSystem.scalef(1.5F, 1.5F, 1.5F);
      this.o.b(_snowman, "LOCAL!", 0.0F, 0.0F, 8388479);
      RenderSystem.popMatrix();
   }

   private void c(dfm var1) {
      String _snowman = "STAGE!";
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.pushMatrix();
      RenderSystem.translatef((float)(this.k / 2 - 25), 20.0F, 0.0F);
      RenderSystem.rotatef(-20.0F, 0.0F, 0.0F, 1.0F);
      RenderSystem.scalef(1.5F, 1.5F, 1.5F);
      this.o.b(_snowman, "STAGE!", 0.0F, 0.0F, -256);
      RenderSystem.popMatrix();
   }

   public dfw g() {
      dfw _snowman = new dfw(this.ai);
      _snowman.b(this.i, this.k, this.l);
      return _snowman;
   }

   public static void a(ach var0) {
      Collection<vk> _snowman = _snowman.a("textures/gui/images", var0x -> var0x.endsWith(".png"));
      X = _snowman.stream().filter(var0x -> var0x.b().equals("realms")).collect(ImmutableList.toImmutableList());
   }

   private void a(nr... var1) {
      this.aq = Arrays.asList(_snowman);
   }

   private void a(dlj var1) {
      this.i.a(new dic(this.ai));
   }

   class a extends dlj {
      public a() {
         super(dfw.this.Q() + 4, dfw.this.R() + 4, 12, 12, new of("mco.selectServer.close"), var1x -> dfw.this.P());
      }

      @Override
      public void b(dfm var1, int var2, int var3, float var4) {
         dfw.this.i.M().a(dfw.B);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         float _snowman = this.g() ? 12.0F : 0.0F;
         a(_snowman, this.l, this.m, 0.0F, _snowman, 12, 12, 12, 24);
         if (this.b((double)_snowman, (double)_snowman)) {
            dfw.this.a(this.i());
         }
      }
   }

   abstract class b extends dlv.a<dfw.b> {
      private b() {
      }
   }

   static enum c {
      a,
      b,
      c,
      d;

      private c() {
      }
   }

   class d extends dlj {
      public d() {
         super(dfw.this.k - 62, 6, 20, 20, oe.d, var1x -> {
            if (dfw.this.aB != null) {
               x.i().a(dfw.this.aB);
               if (dfw.this.aA) {
                  diq.a _snowman = diq.a();
                  _snowman.b = false;
                  dfw.this.aA = false;
                  diq.a(_snowman);
               }
            }
         });
         this.a(new of("mco.news"));
      }

      @Override
      public void b(dfm var1, int var2, int var3, float var4) {
         dfw.this.a(_snowman, _snowman, _snowman, dfw.this.aA, this.l, this.m, this.g(), this.o);
      }
   }

   class e extends dlj implements dmc {
      public e() {
         super(dfw.this.k / 2 + 47, 6, 22, 22, oe.d, var1x -> dfw.this.a(var1x));
      }

      @Override
      public void d() {
         this.a(new of(dfw.this.as == 0 ? "mco.invites.nopending" : "mco.invites.pending"));
      }

      @Override
      public void b(dfm var1, int var2, int var3, float var4) {
         dfw.this.a(_snowman, _snowman, _snowman, this.l, this.m, this.g(), this.o);
      }
   }

   class f extends eon<dfw.b> {
      private boolean o;

      public f() {
         super(dfw.this.k, dfw.this.l, 32, dfw.this.l - 40, 36);
      }

      @Override
      public void a() {
         super.a();
         this.o = false;
      }

      public int a(dfw.b var1) {
         this.o = true;
         return this.a((dfw.b)_snowman);
      }

      @Override
      public boolean b() {
         return dfw.this.aw_() == this;
      }

      @Override
      public boolean a(int var1, int var2, int var3) {
         if (_snowman != 257 && _snowman != 32 && _snowman != 335) {
            return super.a(_snowman, _snowman, _snowman);
         } else {
            dlv.a _snowman = this.h();
            return _snowman == null ? super.a(_snowman, _snowman, _snowman) : _snowman.a(0.0, 0.0, 0);
         }
      }

      @Override
      public boolean a(double var1, double var3, int var5) {
         if (_snowman == 0 && _snowman < (double)this.e() && _snowman >= (double)this.i && _snowman <= (double)this.j) {
            int _snowman = dfw.this.aj.q();
            int _snowmanx = this.e();
            int _snowmanxx = (int)Math.floor(_snowman - (double)this.i) - this.n + (int)this.m() - 4;
            int _snowmanxxx = _snowmanxx / this.c;
            if (_snowman >= (double)_snowman && _snowman <= (double)_snowmanx && _snowmanxxx >= 0 && _snowmanxx >= 0 && _snowmanxxx < this.l()) {
               this.a(_snowmanxx, _snowmanxxx, _snowman, _snowman, this.d);
               dfw.this.aG = dfw.this.aG + 7;
               this.a(_snowmanxxx);
            }

            return true;
         } else {
            return super.a(_snowman, _snowman, _snowman);
         }
      }

      @Override
      public void a(int var1) {
         this.j(_snowman);
         if (_snowman != -1) {
            dgq _snowman;
            if (this.o) {
               if (_snowman == 0) {
                  _snowman = null;
               } else {
                  if (_snowman - 1 >= dfw.this.ar.size()) {
                     dfw.this.ak = -1L;
                     return;
                  }

                  _snowman = dfw.this.ar.get(_snowman - 1);
               }
            } else {
               if (_snowman >= dfw.this.ar.size()) {
                  dfw.this.ak = -1L;
                  return;
               }

               _snowman = dfw.this.ar.get(_snowman);
            }

            dfw.this.a(_snowman);
            if (_snowman == null) {
               dfw.this.ak = -1L;
            } else if (_snowman.e == dgq.b.c) {
               dfw.this.ak = -1L;
            } else {
               dfw.this.ak = _snowman.a;
               if (dfw.this.aG >= 10 && dfw.this.al.o) {
                  dfw.this.a(dfw.this.a(dfw.this.ak), dfw.this);
               }
            }
         }
      }

      public void b(@Nullable dfw.b var1) {
         super.a(_snowman);
         int _snowman = this.au_().indexOf(_snowman);
         if (this.o && _snowman == 0) {
            eoj.a(ekx.a("mco.trial.message.line1"), ekx.a("mco.trial.message.line2"));
         } else if (!this.o || _snowman > 0) {
            dgq _snowmanx = dfw.this.ar.get(_snowman - (this.o ? 1 : 0));
            dfw.this.ak = _snowmanx.a;
            dfw.this.a(_snowmanx);
            if (_snowmanx.e == dgq.b.c) {
               eoj.a(ekx.a("mco.selectServer.uninitialized") + ekx.a("mco.gui.button"));
            } else {
               eoj.a(ekx.a("narrator.select", _snowmanx.c));
            }
         }
      }

      @Override
      public void a(int var1, int var2, double var3, double var5, int var7) {
         if (this.o) {
            if (_snowman == 0) {
               dfw.this.av = true;
               return;
            }

            _snowman--;
         }

         if (_snowman < dfw.this.ar.size()) {
            dgq _snowman = dfw.this.ar.get(_snowman);
            if (_snowman != null) {
               if (_snowman.e == dgq.b.c) {
                  dfw.this.ak = -1L;
                  djz.C().a(new dhu(_snowman, dfw.this));
               } else {
                  dfw.this.ak = _snowman.a;
               }

               if (dfw.this.aJ == dfw.c.d) {
                  dfw.this.ak = _snowman.a;
                  dfw.this.f(_snowman);
               } else if (dfw.this.aJ == dfw.c.c) {
                  dfw.this.ak = _snowman.a;
                  dfw.this.g(_snowman);
               } else if (dfw.this.aJ == dfw.c.b) {
                  dfw.this.G();
               }
            }
         }
      }

      @Override
      public int c() {
         return this.l() * 36;
      }

      @Override
      public int d() {
         return 300;
      }
   }

   class g extends dfw.b {
      private final dgq c;

      public g(dgq var2) {
         this.c = _snowman;
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.a(this.c, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public boolean a(double var1, double var3, int var5) {
         if (this.c.e == dgq.b.c) {
            dfw.this.ak = -1L;
            dfw.this.i.a(new dhu(this.c, dfw.this));
         } else {
            dfw.this.ak = this.c.a;
         }

         return true;
      }

      private void a(dgq var1, dfm var2, int var3, int var4, int var5, int var6) {
         this.b(_snowman, _snowman, _snowman + 36, _snowman, _snowman, _snowman);
      }

      private void b(dgq var1, dfm var2, int var3, int var4, int var5, int var6) {
         if (_snowman.e == dgq.b.c) {
            dfw.this.i.M().a(dfw.u);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableAlphaTest();
            dkw.a(_snowman, _snowman + 10, _snowman + 6, 0.0F, 0.0F, 40, 20, 40, 20);
            float _snowman = 0.5F + (1.0F + afm.a((float)dfw.this.at * 0.25F)) * 0.25F;
            int _snowmanx = 0xFF000000 | (int)(127.0F * _snowman) << 16 | (int)(255.0F * _snowman) << 8 | (int)(127.0F * _snowman);
            dkw.a(_snowman, dfw.this.o, dfw.H, _snowman + 10 + 40 + 75, _snowman + 12, _snowmanx);
         } else {
            int _snowman = 225;
            int _snowmanx = 2;
            if (_snowman.j) {
               dfw.this.c(_snowman, _snowman + 225 - 14, _snowman + 2, _snowman, _snowman);
            } else if (_snowman.e == dgq.b.a) {
               dfw.this.e(_snowman, _snowman + 225 - 14, _snowman + 2, _snowman, _snowman);
            } else if (dfw.this.i(_snowman) && _snowman.l < 7) {
               dfw.this.b(_snowman, _snowman + 225 - 14, _snowman + 2, _snowman, _snowman, _snowman.l);
            } else if (_snowman.e == dgq.b.b) {
               dfw.this.d(_snowman, _snowman + 225 - 14, _snowman + 2, _snowman, _snowman);
            }

            if (!dfw.this.i(_snowman) && !dfw.Z) {
               dfw.this.f(_snowman, _snowman + 225, _snowman + 2, _snowman, _snowman);
            } else {
               dfw.this.g(_snowman, _snowman + 225, _snowman + 2, _snowman, _snowman);
            }

            if (!"0".equals(_snowman.r.a)) {
               String _snowmanxx = k.h + "" + _snowman.r.a;
               dfw.this.o.b(_snowman, _snowmanxx, (float)(_snowman + 207 - dfw.this.o.b(_snowmanxx)), (float)(_snowman + 3), 8421504);
               if (_snowman >= _snowman + 207 - dfw.this.o.b(_snowmanxx) && _snowman <= _snowman + 207 && _snowman >= _snowman + 1 && _snowman <= _snowman + 10 && _snowman < dfw.this.l - 40 && _snowman > 32 && !dfw.this.aq_()) {
                  dfw.this.a(new oe(_snowman.r.b));
               }
            }

            if (dfw.this.i(_snowman) && _snowman.j) {
               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               RenderSystem.enableBlend();
               dfw.this.i.M().a(dfw.D);
               RenderSystem.blendFunc(dem.r.l, dem.j.j);
               nr _snowmanxx;
               nr _snowmanxxx;
               if (_snowman.k) {
                  _snowmanxx = dfw.K;
                  _snowmanxxx = dfw.L;
               } else {
                  _snowmanxx = dfw.I;
                  _snowmanxxx = dfw.J;
               }

               int _snowmanxxxx = dfw.this.o.a(_snowmanxxx) + 17;
               int _snowmanxxxxx = 16;
               int _snowmanxxxxxx = _snowman + dfw.this.o.a(_snowmanxx) + 8;
               int _snowmanxxxxxxx = _snowman + 13;
               boolean _snowmanxxxxxxxx = false;
               if (_snowman >= _snowmanxxxxxx && _snowman < _snowmanxxxxxx + _snowmanxxxx && _snowman > _snowmanxxxxxxx && _snowman <= _snowmanxxxxxxx + 16 & _snowman < dfw.this.l - 40 && _snowman > 32 && !dfw.this.aq_()) {
                  _snowmanxxxxxxxx = true;
                  dfw.this.aJ = dfw.c.b;
               }

               int _snowmanxxxxxxxxx = _snowmanxxxxxxxx ? 2 : 1;
               dkw.a(_snowman, _snowmanxxxxxx, _snowmanxxxxxxx, 0.0F, (float)(46 + _snowmanxxxxxxxxx * 20), _snowmanxxxx / 2, 8, 256, 256);
               dkw.a(_snowman, _snowmanxxxxxx + _snowmanxxxx / 2, _snowmanxxxxxxx, (float)(200 - _snowmanxxxx / 2), (float)(46 + _snowmanxxxxxxxxx * 20), _snowmanxxxx / 2, 8, 256, 256);
               dkw.a(_snowman, _snowmanxxxxxx, _snowmanxxxxxxx + 8, 0.0F, (float)(46 + _snowmanxxxxxxxxx * 20 + 12), _snowmanxxxx / 2, 8, 256, 256);
               dkw.a(_snowman, _snowmanxxxxxx + _snowmanxxxx / 2, _snowmanxxxxxxx + 8, (float)(200 - _snowmanxxxx / 2), (float)(46 + _snowmanxxxxxxxxx * 20 + 12), _snowmanxxxx / 2, 8, 256, 256);
               RenderSystem.disableBlend();
               int _snowmanxxxxxxxxxx = _snowman + 11 + 5;
               int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx ? 16777120 : 16777215;
               dfw.this.o.b(_snowman, _snowmanxx, (float)(_snowman + 2), (float)(_snowmanxxxxxxxxxx + 1), 15553363);
               dkw.a(_snowman, dfw.this.o, _snowmanxxx, _snowmanxxxxxx + _snowmanxxxx / 2, _snowmanxxxxxxxxxx + 1, _snowmanxxxxxxxxxxx);
            } else {
               if (_snowman.m == dgq.c.b) {
                  int _snowmanxxxx = 13413468;
                  int _snowmanxxxxx = dfw.this.o.a(dfw.M);
                  dfw.this.o.b(_snowman, dfw.M, (float)(_snowman + 2), (float)(_snowman + 12), 13413468);
                  dfw.this.o.b(_snowman, _snowman.c(), (float)(_snowman + 2 + _snowmanxxxxx), (float)(_snowman + 12), 7105644);
               } else {
                  dfw.this.o.b(_snowman, _snowman.a(), (float)(_snowman + 2), (float)(_snowman + 12), 7105644);
               }

               if (!dfw.this.i(_snowman)) {
                  dfw.this.o.b(_snowman, _snowman.f, (float)(_snowman + 2), (float)(_snowman + 12 + 11), 5000268);
               }
            }

            dfw.this.o.b(_snowman, _snowman.b(), (float)(_snowman + 2), (float)(_snowman + 1), 16777215);
            dir.a(_snowman.g, () -> {
               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               dkw.a(_snowman, _snowman - 36, _snowman, 32, 32, 8.0F, 8.0F, 8, 8, 64, 64);
               dkw.a(_snowman, _snowman - 36, _snowman, 32, 32, 40.0F, 8.0F, 8, 8, 64, 64);
            });
         }
      }
   }

   class h extends dlj {
      public h() {
         super(dfw.this.k - 37, 6, 20, 20, new of("mco.selectServer.info"), var1x -> dfw.this.av = !dfw.this.av);
      }

      @Override
      public void b(dfm var1, int var2, int var3, float var4) {
         dfw.this.a(_snowman, _snowman, _snowman, this.l, this.m, this.g());
      }
   }

   class i extends dfw.b {
      private i() {
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public boolean a(double var1, double var3, int var5) {
         dfw.this.av = true;
         return true;
      }

      private void a(dfm var1, int var2, int var3, int var4, int var5, int var6) {
         int _snowman = _snowman + 8;
         int _snowmanx = 0;
         boolean _snowmanxx = false;
         if (_snowman <= _snowman && _snowman <= (int)dfw.this.aj.m() && _snowman <= _snowman && _snowman <= _snowman + 32) {
            _snowmanxx = true;
         }

         int _snowmanxxx = 8388479;
         if (_snowmanxx && !dfw.this.aq_()) {
            _snowmanxxx = 6077788;
         }

         for (nr _snowmanxxxx : dfw.G) {
            dkw.a(_snowman, dfw.this.o, _snowmanxxxx, dfw.this.k / 2, _snowman + _snowmanx, _snowmanxxx);
            _snowmanx += 10;
         }
      }
   }
}
