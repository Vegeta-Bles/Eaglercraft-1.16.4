import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class bqz {
   private static final Logger a = LogManager.getLogger();
   private int b = 20;
   private final List<bsm> c = Lists.newArrayList();
   private bsm d = new bsm();
   private double e;
   private double f;
   private int g = 200;
   private int h = 800;
   private int i = 4;
   @Nullable
   private aqa j;
   private int k = 6;
   private int l = 16;
   private int m = 4;

   public bqz() {
   }

   @Nullable
   private vk g() {
      String _snowman = this.d.b().l("id");

      try {
         return aft.b(_snowman) ? null : new vk(_snowman);
      } catch (v var4) {
         fx _snowmanx = this.b();
         a.warn("Invalid entity id '{}' at spawner {}:[{},{},{}]", _snowman, this.a().Y().a(), _snowmanx.u(), _snowmanx.v(), _snowmanx.w());
         return null;
      }
   }

   public void a(aqe<?> var1) {
      this.d.b().a("id", gm.S.b(_snowman).toString());
   }

   private boolean h() {
      fx _snowman = this.b();
      return this.a().a((double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, (double)this.l);
   }

   public void c() {
      if (!this.h()) {
         this.f = this.e;
      } else {
         brx _snowman = this.a();
         fx _snowmanx = this.b();
         if (!(_snowman instanceof aag)) {
            double _snowmanxx = (double)_snowmanx.u() + _snowman.t.nextDouble();
            double _snowmanxxx = (double)_snowmanx.v() + _snowman.t.nextDouble();
            double _snowmanxxxx = (double)_snowmanx.w() + _snowman.t.nextDouble();
            _snowman.a(hh.S, _snowmanxx, _snowmanxxx, _snowmanxxxx, 0.0, 0.0, 0.0);
            _snowman.a(hh.A, _snowmanxx, _snowmanxxx, _snowmanxxxx, 0.0, 0.0, 0.0);
            if (this.b > 0) {
               this.b--;
            }

            this.f = this.e;
            this.e = (this.e + (double)(1000.0F / ((float)this.b + 200.0F))) % 360.0;
         } else {
            if (this.b == -1) {
               this.i();
            }

            if (this.b > 0) {
               this.b--;
               return;
            }

            boolean _snowmanxx = false;

            for (int _snowmanxxx = 0; _snowmanxxx < this.i; _snowmanxxx++) {
               md _snowmanxxxx = this.d.b();
               Optional<aqe<?>> _snowmanxxxxx = aqe.a(_snowmanxxxx);
               if (!_snowmanxxxxx.isPresent()) {
                  this.i();
                  return;
               }

               mj _snowmanxxxxxx = _snowmanxxxx.d("Pos", 6);
               int _snowmanxxxxxxx = _snowmanxxxxxx.size();
               double _snowmanxxxxxxxx = _snowmanxxxxxxx >= 1 ? _snowmanxxxxxx.h(0) : (double)_snowmanx.u() + (_snowman.t.nextDouble() - _snowman.t.nextDouble()) * (double)this.m + 0.5;
               double _snowmanxxxxxxxxx = _snowmanxxxxxxx >= 2 ? _snowmanxxxxxx.h(1) : (double)(_snowmanx.v() + _snowman.t.nextInt(3) - 1);
               double _snowmanxxxxxxxxxx = _snowmanxxxxxxx >= 3 ? _snowmanxxxxxx.h(2) : (double)_snowmanx.w() + (_snowman.t.nextDouble() - _snowman.t.nextDouble()) * (double)this.m + 0.5;
               if (_snowman.b(_snowmanxxxxx.get().a(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx))) {
                  aag _snowmanxxxxxxxxxxx = (aag)_snowman;
                  if (ard.a(_snowmanxxxxx.get(), _snowmanxxxxxxxxxxx, aqp.c, new fx(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx), _snowman.u_())) {
                     aqa _snowmanxxxxxxxxxxxx = aqe.a(_snowmanxxxx, _snowman, var6x -> {
                        var6x.b(_snowman, _snowman, _snowman, var6x.p, var6x.q);
                        return var6x;
                     });
                     if (_snowmanxxxxxxxxxxxx == null) {
                        this.i();
                        return;
                     }

                     int _snowmanxxxxxxxxxxxxx = _snowman.a(
                           _snowmanxxxxxxxxxxxx.getClass(),
                           new dci((double)_snowmanx.u(), (double)_snowmanx.v(), (double)_snowmanx.w(), (double)(_snowmanx.u() + 1), (double)(_snowmanx.v() + 1), (double)(_snowmanx.w() + 1))
                              .g((double)this.m)
                        )
                        .size();
                     if (_snowmanxxxxxxxxxxxxx >= this.k) {
                        this.i();
                        return;
                     }

                     _snowmanxxxxxxxxxxxx.b(_snowmanxxxxxxxxxxxx.cD(), _snowmanxxxxxxxxxxxx.cE(), _snowmanxxxxxxxxxxxx.cH(), _snowman.t.nextFloat() * 360.0F, 0.0F);
                     if (_snowmanxxxxxxxxxxxx instanceof aqn) {
                        aqn _snowmanxxxxxxxxxxxxxx = (aqn)_snowmanxxxxxxxxxxxx;
                        if (!_snowmanxxxxxxxxxxxxxx.a(_snowman, aqp.c) || !_snowmanxxxxxxxxxxxxxx.a(_snowman)) {
                           continue;
                        }

                        if (this.d.b().e() == 1 && this.d.b().c("id", 8)) {
                           ((aqn)_snowmanxxxxxxxxxxxx).a(_snowmanxxxxxxxxxxx, _snowman.d(_snowmanxxxxxxxxxxxx.cB()), aqp.c, null, null);
                        }
                     }

                     if (!_snowmanxxxxxxxxxxx.g(_snowmanxxxxxxxxxxxx)) {
                        this.i();
                        return;
                     }

                     _snowman.c(2004, _snowmanx, 0);
                     if (_snowmanxxxxxxxxxxxx instanceof aqn) {
                        ((aqn)_snowmanxxxxxxxxxxxx).G();
                     }

                     _snowmanxx = true;
                  }
               }
            }

            if (_snowmanxx) {
               this.i();
            }
         }
      }
   }

   private void i() {
      if (this.h <= this.g) {
         this.b = this.g;
      } else {
         this.b = this.g + this.a().t.nextInt(this.h - this.g);
      }

      if (!this.c.isEmpty()) {
         this.a(afz.a(this.a().t, this.c));
      }

      this.a(1);
   }

   public void a(md var1) {
      this.b = _snowman.g("Delay");
      this.c.clear();
      if (_snowman.c("SpawnPotentials", 9)) {
         mj _snowman = _snowman.d("SpawnPotentials", 10);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            this.c.add(new bsm(_snowman.a(_snowmanx)));
         }
      }

      if (_snowman.c("SpawnData", 10)) {
         this.a(new bsm(1, _snowman.p("SpawnData")));
      } else if (!this.c.isEmpty()) {
         this.a(afz.a(this.a().t, this.c));
      }

      if (_snowman.c("MinSpawnDelay", 99)) {
         this.g = _snowman.g("MinSpawnDelay");
         this.h = _snowman.g("MaxSpawnDelay");
         this.i = _snowman.g("SpawnCount");
      }

      if (_snowman.c("MaxNearbyEntities", 99)) {
         this.k = _snowman.g("MaxNearbyEntities");
         this.l = _snowman.g("RequiredPlayerRange");
      }

      if (_snowman.c("SpawnRange", 99)) {
         this.m = _snowman.g("SpawnRange");
      }

      if (this.a() != null) {
         this.j = null;
      }
   }

   public md b(md var1) {
      vk _snowman = this.g();
      if (_snowman == null) {
         return _snowman;
      } else {
         _snowman.a("Delay", (short)this.b);
         _snowman.a("MinSpawnDelay", (short)this.g);
         _snowman.a("MaxSpawnDelay", (short)this.h);
         _snowman.a("SpawnCount", (short)this.i);
         _snowman.a("MaxNearbyEntities", (short)this.k);
         _snowman.a("RequiredPlayerRange", (short)this.l);
         _snowman.a("SpawnRange", (short)this.m);
         _snowman.a("SpawnData", this.d.b().g());
         mj _snowmanx = new mj();
         if (this.c.isEmpty()) {
            _snowmanx.add(this.d.a());
         } else {
            for (bsm _snowmanxx : this.c) {
               _snowmanx.add(_snowmanxx.a());
            }
         }

         _snowman.a("SpawnPotentials", _snowmanx);
         return _snowman;
      }
   }

   @Nullable
   public aqa d() {
      if (this.j == null) {
         this.j = aqe.a(this.d.b(), this.a(), Function.identity());
         if (this.d.b().e() == 1 && this.d.b().c("id", 8) && this.j instanceof aqn) {
         }
      }

      return this.j;
   }

   public boolean b(int var1) {
      if (_snowman == 1 && this.a().v) {
         this.b = this.g;
         return true;
      } else {
         return false;
      }
   }

   public void a(bsm var1) {
      this.d = _snowman;
   }

   public abstract void a(int var1);

   public abstract brx a();

   public abstract fx b();

   public double e() {
      return this.e;
   }

   public double f() {
      return this.f;
   }
}
