import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dhq extends eoo {
   private static final Logger a = LogManager.getLogger();
   private final dot b;
   private final dfw c;
   private dgq p;
   private final long q;
   private final nr r;
   private final nr[] s = new nr[]{new of("mco.brokenworld.message.line1"), new of("mco.brokenworld.message.line2")};
   private int t;
   private int u;
   private final List<Integer> v = Lists.newArrayList();
   private int w;

   public dhq(dot var1, dfw var2, long var3, boolean var5) {
      this.b = _snowman;
      this.c = _snowman;
      this.q = _snowman;
      this.r = _snowman ? new of("mco.brokenworld.minigame.title") : new of("mco.brokenworld.title");
   }

   @Override
   public void b() {
      this.t = this.k / 2 - 150;
      this.u = this.k / 2 + 190;
      this.a(new dlj(this.u - 80 + 8, j(13) - 5, 70, 20, nq.h, var1 -> this.i()));
      if (this.p == null) {
         this.a(this.q);
      } else {
         this.h();
      }

      this.i.m.a(true);
      eoj.a(Stream.concat(Stream.of(this.r), Stream.of(this.s)).map(nr::getString).collect(Collectors.joining(" ")));
   }

   private void h() {
      for (Entry<Integer, dgw> _snowman : this.p.i.entrySet()) {
         int _snowmanx = _snowman.getKey();
         boolean _snowmanxx = _snowmanx != this.p.n || this.p.m == dgq.c.b;
         dlj _snowmanxxx;
         if (_snowmanxx) {
            _snowmanxxx = new dlj(
               this.a(_snowmanx),
               j(8),
               80,
               20,
               new of("mco.brokenworld.play"),
               var2x -> {
                  if (this.p.i.get(_snowman).n) {
                     dif _snowmanxxxx = new dif(
                        this,
                        this.p,
                        new of("mco.configure.world.switch.slot"),
                        new of("mco.configure.world.switch.slot.subtitle"),
                        10526880,
                        nq.d,
                        this::a,
                        () -> {
                           this.i.a(this);
                           this.a();
                        }
                     );
                     _snowmanxxxx.a(_snowman);
                     _snowmanxxxx.a(new of("mco.create.world.reset.title"));
                     this.i.a(_snowmanxxxx);
                  } else {
                     this.i.a(new dhz(this.b, new djf(this.p.a, _snowman, this::a)));
                  }
               }
            );
         } else {
            _snowmanxxx = new dlj(this.a(_snowmanx), j(8), 80, 20, new of("mco.brokenworld.download"), var2x -> {
               nr _snowmanxxxx = new of("mco.configure.world.restore.download.question.line1");
               nr _snowmanx = new of("mco.configure.world.restore.download.question.line2");
               this.i.a(new dhy(var2xx -> {
                  if (var2xx) {
                     this.b(_snowman);
                  } else {
                     this.i.a(this);
                  }
               }, dhy.a.b, _snowmanxxxx, _snowmanx, true));
            });
         }

         if (this.v.contains(_snowmanx)) {
            _snowmanxxx.o = false;
            _snowmanxxx.a(new of("mco.brokenworld.downloaded"));
         }

         this.a(_snowmanxxx);
         this.a(new dlj(this.a(_snowmanx), j(10), 80, 20, new of("mco.brokenworld.reset"), var2x -> {
            dif _snowmanxxxx = new dif(this, this.p, this::a, () -> {
               this.i.a(this);
               this.a();
            });
            if (_snowman != this.p.n || this.p.m == dgq.c.b) {
               _snowmanxxxx.a(_snowman);
            }

            this.i.a(_snowmanxxxx);
         }));
      }
   }

   @Override
   public void d() {
      this.w++;
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
      a(_snowman, this.o, this.r, this.k / 2, 17, 16777215);

      for (int _snowman = 0; _snowman < this.s.length; _snowman++) {
         a(_snowman, this.o, this.s[_snowman], this.k / 2, j(-1) + 3 + _snowman * 12, 10526880);
      }

      if (this.p != null) {
         for (Entry<Integer, dgw> _snowman : this.p.i.entrySet()) {
            if (_snowman.getValue().l != null && _snowman.getValue().k != -1L) {
               this.a(
                  _snowman,
                  this.a(_snowman.getKey()),
                  j(1) + 5,
                  _snowman,
                  _snowman,
                  this.p.n == _snowman.getKey() && !this.k(),
                  _snowman.getValue().a(_snowman.getKey()),
                  _snowman.getKey(),
                  _snowman.getValue().k,
                  _snowman.getValue().l,
                  _snowman.getValue().n
               );
            } else {
               this.a(
                  _snowman, this.a(_snowman.getKey()), j(1) + 5, _snowman, _snowman, this.p.n == _snowman.getKey() && !this.k(), _snowman.getValue().a(_snowman.getKey()), _snowman.getKey(), -1L, null, _snowman.getValue().n
               );
            }
         }
      }
   }

   private int a(int var1) {
      return this.t + (_snowman - 1) * 110;
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.i();
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   private void i() {
      this.i.a(this.b);
   }

   private void a(long var1) {
      new Thread(() -> {
         dgb _snowman = dgb.a();

         try {
            this.p = _snowman.a(_snowman);
            this.h();
         } catch (dhi var5) {
            a.error("Couldn't get own world");
            this.i.a(new dhw(nr.a(var5.getMessage()), this.b));
         }
      }).start();
   }

   public void a() {
      new Thread(() -> {
         dgb _snowman = dgb.a();
         if (this.p.e == dgq.b.a) {
            this.i.execute(() -> this.i.a(new dhz(this, new djb(this.p, this, this.c, true))));
         } else {
            try {
               this.c.g().a(_snowman.a(this.q), this);
            } catch (dhi var3) {
               a.error("Couldn't get own world");
               this.i.execute(() -> this.i.a(this.b));
            }
         }
      }).start();
   }

   private void b(int var1) {
      dgb _snowman = dgb.a();

      try {
         dhd _snowmanx = _snowman.b(this.p.a, _snowman);
         dhv _snowmanxx = new dhv(this, _snowmanx, this.p.a(_snowman), var2x -> {
            if (var2x) {
               this.v.add(_snowman);
               this.e.clear();
               this.h();
            } else {
               this.i.a(this);
            }
         });
         this.i.a(_snowmanxx);
      } catch (dhi var5) {
         a.error("Couldn't download world data");
         this.i.a(new dhw(var5, this));
      }
   }

   private boolean k() {
      return this.p != null && this.p.m == dgq.c.b;
   }

   private void a(dfm var1, int var2, int var3, int var4, int var5, boolean var6, String var7, int var8, long var9, String var11, boolean var12) {
      if (_snowman) {
         this.i.M().a(dhm.b);
      } else if (_snowman != null && _snowman != -1L) {
         dir.a(String.valueOf(_snowman), _snowman);
      } else if (_snowman == 1) {
         this.i.M().a(dhm.c);
      } else if (_snowman == 2) {
         this.i.M().a(dhm.d);
      } else if (_snowman == 3) {
         this.i.M().a(dhm.e);
      } else {
         dir.a(String.valueOf(this.p.p), this.p.q);
      }

      if (!_snowman) {
         RenderSystem.color4f(0.56F, 0.56F, 0.56F, 1.0F);
      } else if (_snowman) {
         float _snowman = 0.9F + 0.1F * afm.b((float)this.w * 0.2F);
         RenderSystem.color4f(_snowman, _snowman, _snowman, 1.0F);
      }

      dkw.a(_snowman, _snowman + 3, _snowman + 3, 0.0F, 0.0F, 74, 74, 74, 74);
      this.i.M().a(dhm.a);
      if (_snowman) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         RenderSystem.color4f(0.56F, 0.56F, 0.56F, 1.0F);
      }

      dkw.a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 80, 80, 80, 80);
      a(_snowman, this.o, _snowman, _snowman + 40, _snowman + 66, 16777215);
   }
}
