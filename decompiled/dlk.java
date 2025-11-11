import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Deque;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dlk extends dkw {
   private static final Logger a = LogManager.getLogger();
   private final djz b;
   private final List<String> c = Lists.newArrayList();
   private final List<dju<nr>> d = Lists.newArrayList();
   private final List<dju<afa>> e = Lists.newArrayList();
   private final Deque<nr> i = Queues.newArrayDeque();
   private int j;
   private boolean k;
   private long l = 0L;

   public dlk(djz var1) {
      this.b = _snowman;
   }

   public void a(dfm var1, int var2) {
      if (!this.h()) {
         this.k();
         int _snowman = this.g();
         int _snowmanx = this.e.size();
         if (_snowmanx > 0) {
            boolean _snowmanxx = false;
            if (this.i()) {
               _snowmanxx = true;
            }

            double _snowmanxxx = this.f();
            int _snowmanxxxx = afm.f((double)this.d() / _snowmanxxx);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(2.0F, 8.0F, 0.0F);
            RenderSystem.scaled(_snowmanxxx, _snowmanxxx, 1.0);
            double _snowmanxxxxx = this.b.k.k * 0.9F + 0.1F;
            double _snowmanxxxxxx = this.b.k.m;
            double _snowmanxxxxxxx = 9.0 * (this.b.k.l + 1.0);
            double _snowmanxxxxxxxx = -8.0 * (this.b.k.l + 1.0) + 4.0 * this.b.k.l;
            int _snowmanxxxxxxxxx = 0;

            for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx + this.j < this.e.size() && _snowmanxxxxxxxxxx < _snowman; _snowmanxxxxxxxxxx++) {
               dju<afa> _snowmanxxxxxxxxxxx = this.e.get(_snowmanxxxxxxxxxx + this.j);
               if (_snowmanxxxxxxxxxxx != null) {
                  int _snowmanxxxxxxxxxxxx = _snowman - _snowmanxxxxxxxxxxx.b();
                  if (_snowmanxxxxxxxxxxxx < 200 || _snowmanxx) {
                     double _snowmanxxxxxxxxxxxxx = _snowmanxx ? 1.0 : a(_snowmanxxxxxxxxxxxx);
                     int _snowmanxxxxxxxxxxxxxx = (int)(255.0 * _snowmanxxxxxxxxxxxxx * _snowmanxxxxx);
                     int _snowmanxxxxxxxxxxxxxxx = (int)(255.0 * _snowmanxxxxxxxxxxxxx * _snowmanxxxxxx);
                     _snowmanxxxxxxxxx++;
                     if (_snowmanxxxxxxxxxxxxxx > 3) {
                        int _snowmanxxxxxxxxxxxxxxxx = 0;
                        double _snowmanxxxxxxxxxxxxxxxxx = (double)(-_snowmanxxxxxxxxxx) * _snowmanxxxxxxx;
                        _snowman.a();
                        _snowman.a(0.0, 0.0, 50.0);
                        a(_snowman, -2, (int)(_snowmanxxxxxxxxxxxxxxxxx - _snowmanxxxxxxx), 0 + _snowmanxxxx + 4, (int)_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx << 24);
                        RenderSystem.enableBlend();
                        _snowman.a(0.0, 0.0, 50.0);
                        this.b.g.a(_snowman, _snowmanxxxxxxxxxxx.a(), 0.0F, (float)((int)(_snowmanxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxx)), 16777215 + (_snowmanxxxxxxxxxxxxxx << 24));
                        RenderSystem.disableAlphaTest();
                        RenderSystem.disableBlend();
                        _snowman.b();
                     }
                  }
               }
            }

            if (!this.i.isEmpty()) {
               int _snowmanxxxxxxxxxxx = (int)(128.0 * _snowmanxxxxx);
               int _snowmanxxxxxxxxxxxx = (int)(255.0 * _snowmanxxxxxx);
               _snowman.a();
               _snowman.a(0.0, 0.0, 50.0);
               a(_snowman, -2, 0, _snowmanxxxx + 4, 9, _snowmanxxxxxxxxxxxx << 24);
               RenderSystem.enableBlend();
               _snowman.a(0.0, 0.0, 50.0);
               this.b.g.a(_snowman, new of("chat.queue", this.i.size()), 0.0F, 1.0F, 16777215 + (_snowmanxxxxxxxxxxx << 24));
               _snowman.b();
               RenderSystem.disableAlphaTest();
               RenderSystem.disableBlend();
            }

            if (_snowmanxx) {
               int _snowmanxxxxxxxxxxx = 9;
               RenderSystem.translatef(-3.0F, 0.0F, 0.0F);
               int _snowmanxxxxxxxxxxxx = _snowmanx * _snowmanxxxxxxxxxxx + _snowmanx;
               int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxx;
               int _snowmanxxxxxxxxxxxxxx = this.j * _snowmanxxxxxxxxxxxxx / _snowmanx;
               int _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxx / _snowmanxxxxxxxxxxxx;
               if (_snowmanxxxxxxxxxxxx != _snowmanxxxxxxxxxxxxx) {
                  int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx > 0 ? 170 : 96;
                  int _snowmanxxxxxxxxxxxxxxxxx = this.k ? 13382451 : 3355562;
                  a(_snowman, 0, -_snowmanxxxxxxxxxxxxxx, 2, -_snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx + (_snowmanxxxxxxxxxxxxxxxx << 24));
                  a(_snowman, 2, -_snowmanxxxxxxxxxxxxxx, 1, -_snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxx, 13421772 + (_snowmanxxxxxxxxxxxxxxxx << 24));
               }
            }

            RenderSystem.popMatrix();
         }
      }
   }

   private boolean h() {
      return this.b.k.j == bfu.c;
   }

   private static double a(int var0) {
      double _snowman = (double)_snowman / 200.0;
      _snowman = 1.0 - _snowman;
      _snowman *= 10.0;
      _snowman = afm.a(_snowman, 0.0, 1.0);
      return _snowman * _snowman;
   }

   public void a(boolean var1) {
      this.i.clear();
      this.e.clear();
      this.d.clear();
      if (_snowman) {
         this.c.clear();
      }
   }

   public void a(nr var1) {
      this.a(_snowman, 0);
   }

   private void a(nr var1, int var2) {
      this.a(_snowman, _snowman, this.b.j.d(), false);
      a.info("[CHAT] {}", _snowman.getString().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n"));
   }

   private void a(nr var1, int var2, int var3, boolean var4) {
      if (_snowman != 0) {
         this.b(_snowman);
      }

      int _snowman = afm.c((double)this.d() / this.f());
      List<afa> _snowmanx = dln.a(_snowman, _snowman, this.b.g);
      boolean _snowmanxx = this.i();

      for (afa _snowmanxxx : _snowmanx) {
         if (_snowmanxx && this.j > 0) {
            this.k = true;
            this.a(1.0);
         }

         this.e.add(0, new dju<>(_snowman, _snowmanxxx, _snowman));
      }

      while (this.e.size() > 100) {
         this.e.remove(this.e.size() - 1);
      }

      if (!_snowman) {
         this.d.add(0, new dju<>(_snowman, _snowman, _snowman));

         while (this.d.size() > 100) {
            this.d.remove(this.d.size() - 1);
         }
      }
   }

   public void a() {
      this.e.clear();
      this.c();

      for (int _snowman = this.d.size() - 1; _snowman >= 0; _snowman--) {
         dju<nr> _snowmanx = this.d.get(_snowman);
         this.a(_snowmanx.a(), _snowmanx.c(), _snowmanx.b(), true);
      }
   }

   public List<String> b() {
      return this.c;
   }

   public void a(String var1) {
      if (this.c.isEmpty() || !this.c.get(this.c.size() - 1).equals(_snowman)) {
         this.c.add(_snowman);
      }
   }

   public void c() {
      this.j = 0;
      this.k = false;
   }

   public void a(double var1) {
      this.j = (int)((double)this.j + _snowman);
      int _snowman = this.e.size();
      if (this.j > _snowman - this.g()) {
         this.j = _snowman - this.g();
      }

      if (this.j <= 0) {
         this.j = 0;
         this.k = false;
      }
   }

   public boolean a(double var1, double var3) {
      if (this.i() && !this.b.k.aI && !this.h() && !this.i.isEmpty()) {
         double _snowman = _snowman - 2.0;
         double _snowmanx = (double)this.b.aD().p() - _snowman - 40.0;
         if (_snowman <= (double)afm.c((double)this.d() / this.f()) && _snowmanx < 0.0 && _snowmanx > (double)afm.c(-9.0 * this.f())) {
            this.a(this.i.remove());
            this.l = System.currentTimeMillis();
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   @Nullable
   public ob b(double var1, double var3) {
      if (this.i() && !this.b.k.aI && !this.h()) {
         double _snowman = _snowman - 2.0;
         double _snowmanx = (double)this.b.aD().p() - _snowman - 40.0;
         _snowman = (double)afm.c(_snowman / this.f());
         _snowmanx = (double)afm.c(_snowmanx / (this.f() * (this.b.k.l + 1.0)));
         if (!(_snowman < 0.0) && !(_snowmanx < 0.0)) {
            int _snowmanxx = Math.min(this.g(), this.e.size());
            if (_snowman <= (double)afm.c((double)this.d() / this.f()) && _snowmanx < (double)(9 * _snowmanxx + _snowmanxx)) {
               int _snowmanxxx = (int)(_snowmanx / 9.0 + (double)this.j);
               if (_snowmanxxx >= 0 && _snowmanxxx < this.e.size()) {
                  dju<afa> _snowmanxxxx = this.e.get(_snowmanxxx);
                  return this.b.g.b().a(_snowmanxxxx.a(), (int)_snowman);
               }
            }

            return null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private boolean i() {
      return this.b.y instanceof dnq;
   }

   private void b(int var1) {
      this.e.removeIf(var1x -> var1x.c() == _snowman);
      this.d.removeIf(var1x -> var1x.c() == _snowman);
   }

   public int d() {
      return b(this.b.k.w);
   }

   public int e() {
      return c((this.i() ? this.b.k.y : this.b.k.x) / (this.b.k.l + 1.0));
   }

   public double f() {
      return this.b.k.v;
   }

   public static int b(double var0) {
      int _snowman = 320;
      int _snowmanx = 40;
      return afm.c(_snowman * 280.0 + 40.0);
   }

   public static int c(double var0) {
      int _snowman = 180;
      int _snowmanx = 20;
      return afm.c(_snowman * 160.0 + 20.0);
   }

   public int g() {
      return this.e() / 9;
   }

   private long j() {
      return (long)(this.b.k.z * 1000.0);
   }

   private void k() {
      if (!this.i.isEmpty()) {
         long _snowman = System.currentTimeMillis();
         if (_snowman - this.l >= this.j()) {
            this.a(this.i.remove());
            this.l = _snowman;
         }
      }
   }

   public void b(nr var1) {
      if (this.b.k.z <= 0.0) {
         this.a(_snowman);
      } else {
         long _snowman = System.currentTimeMillis();
         if (_snowman - this.l >= this.j()) {
            this.a(_snowman);
            this.l = _snowman;
         } else {
            this.i.add(_snowman);
         }
      }
   }
}
