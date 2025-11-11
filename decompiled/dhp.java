import com.mojang.blaze3d.systems.RenderSystem;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dhp extends eoo {
   private static final Logger a = LogManager.getLogger();
   private static final vk b = new vk("realms", "textures/gui/realms/plus_icon.png");
   private static final vk c = new vk("realms", "textures/gui/realms/restore_icon.png");
   private static final nr p = new of("mco.backup.button.restore");
   private static final nr q = new of("mco.backup.changes.tooltip");
   private static final nr r = new of("mco.configure.world.backup");
   private static final nr s = new of("mco.backup.nobackups");
   private static int t = -1;
   private final dhs u;
   private List<dgg> v = Collections.emptyList();
   @Nullable
   private nr w;
   private dhp.a x;
   private int y = -1;
   private final int z;
   private dlj A;
   private dlj B;
   private dlj C;
   private Boolean D = false;
   private final dgq E;
   private eom F;

   public dhp(dhs var1, dgq var2, int var3) {
      this.u = _snowman;
      this.E = _snowman;
      this.z = _snowman;
   }

   @Override
   public void b() {
      this.i.m.a(true);
      this.x = new dhp.a();
      if (t != -1) {
         this.x.a((double)t);
      }

      (new Thread("Realms-fetch-backups") {
         @Override
         public void run() {
            dgb _snowman = dgb.a();

            try {
               List<dgg> _snowmanx = _snowman.d(dhp.this.E.a).a;
               dhp.this.i.execute(() -> {
                  dhp.this.v = _snowman;
                  dhp.this.D = dhp.this.v.isEmpty();
                  dhp.this.x.a();

                  for (dgg _snowmanxx : dhp.this.v) {
                     dhp.this.x.a(_snowmanxx);
                  }

                  dhp.this.i();
               });
            } catch (dhi var3) {
               dhp.a.error("Couldn't request backups", var3);
            }
         }
      }).start();
      this.A = this.a((dlj)(new dlj(this.k - 135, j(1), 120, 20, new of("mco.backup.button.download"), var1 -> this.n())));
      this.B = this.a((dlj)(new dlj(this.k - 135, j(3), 120, 20, new of("mco.backup.button.restore"), var1 -> this.b(this.y))));
      this.C = this.a((dlj)(new dlj(this.k - 135, j(5), 120, 20, new of("mco.backup.changes.tooltip"), var1 -> {
         this.i.a(new dho(this, this.v.get(this.y)));
         this.y = -1;
      })));
      this.a((dlj)(new dlj(this.k - 100, this.l - 35, 85, 20, nq.h, var1 -> this.i.a(this.u))));
      this.d(this.x);
      this.F = this.d(new eom(new of("mco.configure.world.backup"), this.k / 2, 12, 16777215));
      this.c(this.x);
      this.k();
      this.A();
   }

   private void i() {
      if (this.v.size() > 1) {
         for (int _snowman = 0; _snowman < this.v.size() - 1; _snowman++) {
            dgg _snowmanx = this.v.get(_snowman);
            dgg _snowmanxx = this.v.get(_snowman + 1);
            if (!_snowmanx.d.isEmpty() && !_snowmanxx.d.isEmpty()) {
               for (String _snowmanxxx : _snowmanx.d.keySet()) {
                  if (!_snowmanxxx.contains("Uploaded") && _snowmanxx.d.containsKey(_snowmanxxx)) {
                     if (!_snowmanx.d.get(_snowmanxxx).equals(_snowmanxx.d.get(_snowmanxxx))) {
                        this.a(_snowmanx, _snowmanxxx);
                     }
                  } else {
                     this.a(_snowmanx, _snowmanxxx);
                  }
               }
            }
         }
      }
   }

   private void a(dgg var1, String var2) {
      if (_snowman.contains("Uploaded")) {
         String _snowman = DateFormat.getDateTimeInstance(3, 3).format(_snowman.b);
         _snowman.e.put(_snowman, _snowman);
         _snowman.a(true);
      } else {
         _snowman.e.put(_snowman, _snowman.d.get(_snowman));
      }
   }

   private void k() {
      this.B.p = this.m();
      this.C.p = this.l();
   }

   private boolean l() {
      return this.y == -1 ? false : !this.v.get(this.y).e.isEmpty();
   }

   private boolean m() {
      return this.y == -1 ? false : !this.E.j;
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.i.a(this.u);
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   private void b(int var1) {
      if (_snowman >= 0 && _snowman < this.v.size() && !this.E.j) {
         this.y = _snowman;
         Date _snowman = this.v.get(_snowman).b;
         String _snowmanx = DateFormat.getDateTimeInstance(3, 3).format(_snowman);
         String _snowmanxx = dis.a(_snowman);
         nr _snowmanxxx = new of("mco.configure.world.restore.question.line1", _snowmanx, _snowmanxx);
         nr _snowmanxxxx = new of("mco.configure.world.restore.question.line2");
         this.i.a(new dhy(var1x -> {
            if (var1x) {
               this.p();
            } else {
               this.y = -1;
               this.i.a(this);
            }
         }, dhy.a.a, _snowmanxxx, _snowmanxxxx, true));
      }
   }

   private void n() {
      nr _snowman = new of("mco.configure.world.restore.download.question.line1");
      nr _snowmanx = new of("mco.configure.world.restore.download.question.line2");
      this.i.a(new dhy(var1x -> {
         if (var1x) {
            this.o();
         } else {
            this.i.a(this);
         }
      }, dhy.a.b, _snowman, _snowmanx, true));
   }

   private void o() {
      this.i.a(new dhz(this.u.c(), new diy(this.E.a, this.z, this.E.c + " (" + this.E.i.get(this.E.n).a(this.E.n) + ")", this)));
   }

   private void p() {
      dgg _snowman = this.v.get(this.y);
      this.y = -1;
      this.i.a(new dhz(this.u.c(), new djd(_snowman, this.E.a, this.u)));
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.w = null;
      this.a(_snowman);
      this.x.a(_snowman, _snowman, _snowman, _snowman);
      this.F.a(this, _snowman);
      this.o.b(_snowman, r, (float)((this.k - 150) / 2 - 90), 20.0F, 10526880);
      if (this.D) {
         this.o.b(_snowman, s, 20.0F, (float)(this.l / 2 - 10), 16777215);
      }

      this.A.o = !this.D;
      super.a(_snowman, _snowman, _snowman, _snowman);
      if (this.w != null) {
         this.a(_snowman, this.w, _snowman, _snowman);
      }
   }

   protected void a(dfm var1, @Nullable nr var2, int var3, int var4) {
      if (_snowman != null) {
         int _snowman = _snowman + 12;
         int _snowmanx = _snowman - 12;
         int _snowmanxx = this.o.a(_snowman);
         this.a(_snowman, _snowman - 3, _snowmanx - 3, _snowman + _snowmanxx + 3, _snowmanx + 8 + 3, -1073741824, -1073741824);
         this.o.a(_snowman, _snowman, (float)_snowman, (float)_snowmanx, 16777215);
      }
   }

   class a extends eon<dhp.b> {
      public a() {
         super(dhp.this.k - 150, dhp.this.l, 32, dhp.this.l - 15, 36);
      }

      public void a(dgg var1) {
         this.a((dhp.b)(dhp.this.new b(_snowman)));
      }

      @Override
      public int d() {
         return (int)((double)this.d * 0.93);
      }

      @Override
      public boolean b() {
         return dhp.this.aw_() == this;
      }

      @Override
      public int c() {
         return this.l() * 36;
      }

      @Override
      public void a(dfm var1) {
         dhp.this.a(_snowman);
      }

      @Override
      public boolean a(double var1, double var3, int var5) {
         if (_snowman != 0) {
            return false;
         } else if (_snowman < (double)this.e() && _snowman >= (double)this.i && _snowman <= (double)this.j) {
            int _snowman = this.d / 2 - 92;
            int _snowmanx = this.d;
            int _snowmanxx = (int)Math.floor(_snowman - (double)this.i) - this.n + (int)this.m();
            int _snowmanxxx = _snowmanxx / this.c;
            if (_snowman >= (double)_snowman && _snowman <= (double)_snowmanx && _snowmanxxx >= 0 && _snowmanxx >= 0 && _snowmanxxx < this.l()) {
               this.a(_snowmanxxx);
               this.a(_snowmanxx, _snowmanxxx, _snowman, _snowman, this.d);
            }

            return true;
         } else {
            return false;
         }
      }

      @Override
      public int e() {
         return this.d - 5;
      }

      @Override
      public void a(int var1, int var2, double var3, double var5, int var7) {
         int _snowman = this.d - 35;
         int _snowmanx = _snowman * this.c + 36 - (int)this.m();
         int _snowmanxx = _snowman + 10;
         int _snowmanxxx = _snowmanx - 3;
         if (_snowman >= (double)_snowman && _snowman <= (double)(_snowman + 9) && _snowman >= (double)_snowmanx && _snowman <= (double)(_snowmanx + 9)) {
            if (!dhp.this.v.get(_snowman).e.isEmpty()) {
               dhp.this.y = -1;
               dhp.t = (int)this.m();
               this.b.a(new dho(dhp.this, dhp.this.v.get(_snowman)));
            }
         } else if (_snowman >= (double)_snowmanxx && _snowman < (double)(_snowmanxx + 13) && _snowman >= (double)_snowmanxxx && _snowman < (double)(_snowmanxxx + 15)) {
            dhp.t = (int)this.m();
            dhp.this.b(_snowman);
         }
      }

      @Override
      public void a(int var1) {
         this.j(_snowman);
         if (_snowman != -1) {
            eoj.a(ekx.a("narrator.select", dhp.this.v.get(_snowman).b.toString()));
         }

         this.b(_snowman);
      }

      public void b(int var1) {
         dhp.this.y = _snowman;
         dhp.this.k();
      }

      public void a(@Nullable dhp.b var1) {
         super.a(_snowman);
         dhp.this.y = this.au_().indexOf(_snowman);
         dhp.this.k();
      }
   }

   class b extends dlv.a<dhp.b> {
      private final dgg b;

      public b(dgg var2) {
         this.b = _snowman;
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.a(_snowman, this.b, _snowman - 40, _snowman, _snowman, _snowman);
      }

      private void a(dfm var1, dgg var2, int var3, int var4, int var5, int var6) {
         int _snowman = _snowman.a() ? -8388737 : 16777215;
         dhp.this.o.b(_snowman, "Backup (" + dis.a(_snowman.b) + ")", (float)(_snowman + 40), (float)(_snowman + 1), _snowman);
         dhp.this.o.b(_snowman, this.a(_snowman.b), (float)(_snowman + 40), (float)(_snowman + 12), 5000268);
         int _snowmanx = dhp.this.k - 175;
         int _snowmanxx = -3;
         int _snowmanxxx = _snowmanx - 10;
         int _snowmanxxxx = 0;
         if (!dhp.this.E.j) {
            this.a(_snowman, _snowmanx, _snowman + -3, _snowman, _snowman);
         }

         if (!_snowman.e.isEmpty()) {
            this.b(_snowman, _snowmanxxx, _snowman + 0, _snowman, _snowman);
         }
      }

      private String a(Date var1) {
         return DateFormat.getDateTimeInstance(3, 3).format(_snowman);
      }

      private void a(dfm var1, int var2, int var3, int var4, int var5) {
         boolean _snowman = _snowman >= _snowman && _snowman <= _snowman + 12 && _snowman >= _snowman && _snowman <= _snowman + 14 && _snowman < dhp.this.l - 15 && _snowman > 32;
         dhp.this.i.M().a(dhp.c);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.pushMatrix();
         RenderSystem.scalef(0.5F, 0.5F, 0.5F);
         float _snowmanx = _snowman ? 28.0F : 0.0F;
         dkw.a(_snowman, _snowman * 2, _snowman * 2, 0.0F, _snowmanx, 23, 28, 23, 56);
         RenderSystem.popMatrix();
         if (_snowman) {
            dhp.this.w = dhp.p;
         }
      }

      private void b(dfm var1, int var2, int var3, int var4, int var5) {
         boolean _snowman = _snowman >= _snowman && _snowman <= _snowman + 8 && _snowman >= _snowman && _snowman <= _snowman + 8 && _snowman < dhp.this.l - 15 && _snowman > 32;
         dhp.this.i.M().a(dhp.b);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.pushMatrix();
         RenderSystem.scalef(0.5F, 0.5F, 0.5F);
         float _snowmanx = _snowman ? 15.0F : 0.0F;
         dkw.a(_snowman, _snowman * 2, _snowman * 2, 0.0F, _snowmanx, 15, 15, 15, 30);
         RenderSystem.popMatrix();
         if (_snowman) {
            dhp.this.w = dhp.q;
         }
      }
   }
}
