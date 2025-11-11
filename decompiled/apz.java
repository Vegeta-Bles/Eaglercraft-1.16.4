import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class apz extends aqa {
   private static final Logger b = LogManager.getLogger();
   private static final us<Float> c = uv.a(apz.class, uu.c);
   private static final us<Integer> d = uv.a(apz.class, uu.b);
   private static final us<Boolean> e = uv.a(apz.class, uu.i);
   private static final us<hf> f = uv.a(apz.class, uu.j);
   private bnt g = bnw.a;
   private final List<apu> ag = Lists.newArrayList();
   private final Map<aqa, Integer> ah = Maps.newHashMap();
   private int ai = 600;
   private int aj = 20;
   private int ak = 20;
   private boolean al;
   private int am;
   private float an;
   private float ao;
   private aqm ap;
   private UUID aq;

   public apz(aqe<? extends apz> var1, brx var2) {
      super(_snowman, _snowman);
      this.H = true;
      this.a(3.0F);
   }

   public apz(brx var1, double var2, double var4, double var6) {
      this(aqe.a, _snowman);
      this.d(_snowman, _snowman, _snowman);
   }

   @Override
   protected void e() {
      this.ab().a(d, 0);
      this.ab().a(c, 0.5F);
      this.ab().a(e, false);
      this.ab().a(f, hh.u);
   }

   public void a(float var1) {
      if (!this.l.v) {
         this.ab().b(c, _snowman);
      }
   }

   @Override
   public void x_() {
      double _snowman = this.cD();
      double _snowmanx = this.cE();
      double _snowmanxx = this.cH();
      super.x_();
      this.d(_snowman, _snowmanx, _snowmanxx);
   }

   public float g() {
      return this.ab().a(c);
   }

   public void a(bnt var1) {
      this.g = _snowman;
      if (!this.al) {
         this.x();
      }
   }

   private void x() {
      if (this.g == bnw.a && this.ag.isEmpty()) {
         this.ab().b(d, 0);
      } else {
         this.ab().b(d, bnv.a(bnv.a(this.g, this.ag)));
      }
   }

   public void a(apu var1) {
      this.ag.add(_snowman);
      if (!this.al) {
         this.x();
      }
   }

   public int h() {
      return this.ab().a(d);
   }

   public void a(int var1) {
      this.al = true;
      this.ab().b(d, _snowman);
   }

   public hf i() {
      return this.ab().a(f);
   }

   public void a(hf var1) {
      this.ab().b(f, _snowman);
   }

   protected void a(boolean var1) {
      this.ab().b(e, _snowman);
   }

   @Override
   public boolean k() {
      return this.ab().a(e);
   }

   public int m() {
      return this.ai;
   }

   public void b(int var1) {
      this.ai = _snowman;
   }

   @Override
   public void j() {
      super.j();
      boolean _snowman = this.k();
      float _snowmanx = this.g();
      if (this.l.v) {
         hf _snowmanxx = this.i();
         if (_snowman) {
            if (this.J.nextBoolean()) {
               for (int _snowmanxxx = 0; _snowmanxxx < 2; _snowmanxxx++) {
                  float _snowmanxxxx = this.J.nextFloat() * (float) (Math.PI * 2);
                  float _snowmanxxxxx = afm.c(this.J.nextFloat()) * 0.2F;
                  float _snowmanxxxxxx = afm.b(_snowmanxxxx) * _snowmanxxxxx;
                  float _snowmanxxxxxxx = afm.a(_snowmanxxxx) * _snowmanxxxxx;
                  if (_snowmanxx.b() == hh.u) {
                     int _snowmanxxxxxxxx = this.J.nextBoolean() ? 16777215 : this.h();
                     int _snowmanxxxxxxxxx = _snowmanxxxxxxxx >> 16 & 0xFF;
                     int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx >> 8 & 0xFF;
                     int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx & 0xFF;
                     this.l
                        .b(
                           _snowmanxx,
                           this.cD() + (double)_snowmanxxxxxx,
                           this.cE(),
                           this.cH() + (double)_snowmanxxxxxxx,
                           (double)((float)_snowmanxxxxxxxxx / 255.0F),
                           (double)((float)_snowmanxxxxxxxxxx / 255.0F),
                           (double)((float)_snowmanxxxxxxxxxxx / 255.0F)
                        );
                  } else {
                     this.l.b(_snowmanxx, this.cD() + (double)_snowmanxxxxxx, this.cE(), this.cH() + (double)_snowmanxxxxxxx, 0.0, 0.0, 0.0);
                  }
               }
            }
         } else {
            float _snowmanxxxx = (float) Math.PI * _snowmanx * _snowmanx;

            for (int _snowmanxxxxx = 0; (float)_snowmanxxxxx < _snowmanxxxx; _snowmanxxxxx++) {
               float _snowmanxxxxxx = this.J.nextFloat() * (float) (Math.PI * 2);
               float _snowmanxxxxxxx = afm.c(this.J.nextFloat()) * _snowmanx;
               float _snowmanxxxxxxxx = afm.b(_snowmanxxxxxx) * _snowmanxxxxxxx;
               float _snowmanxxxxxxxxx = afm.a(_snowmanxxxxxx) * _snowmanxxxxxxx;
               if (_snowmanxx.b() == hh.u) {
                  int _snowmanxxxxxxxxxx = this.h();
                  int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx >> 16 & 0xFF;
                  int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx >> 8 & 0xFF;
                  int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx & 0xFF;
                  this.l
                     .b(
                        _snowmanxx,
                        this.cD() + (double)_snowmanxxxxxxxx,
                        this.cE(),
                        this.cH() + (double)_snowmanxxxxxxxxx,
                        (double)((float)_snowmanxxxxxxxxxxx / 255.0F),
                        (double)((float)_snowmanxxxxxxxxxxxx / 255.0F),
                        (double)((float)_snowmanxxxxxxxxxxxxx / 255.0F)
                     );
               } else {
                  this.l
                     .b(
                        _snowmanxx,
                        this.cD() + (double)_snowmanxxxxxxxx,
                        this.cE(),
                        this.cH() + (double)_snowmanxxxxxxxxx,
                        (0.5 - this.J.nextDouble()) * 0.15,
                        0.01F,
                        (0.5 - this.J.nextDouble()) * 0.15
                     );
               }
            }
         }
      } else {
         if (this.K >= this.aj + this.ai) {
            this.ad();
            return;
         }

         boolean _snowmanxx = this.K < this.aj;
         if (_snowman != _snowmanxx) {
            this.a(_snowmanxx);
         }

         if (_snowmanxx) {
            return;
         }

         if (this.ao != 0.0F) {
            _snowmanx += this.ao;
            if (_snowmanx < 0.5F) {
               this.ad();
               return;
            }

            this.a(_snowmanx);
         }

         if (this.K % 5 == 0) {
            Iterator<Entry<aqa, Integer>> _snowmanxxxx = this.ah.entrySet().iterator();

            while (_snowmanxxxx.hasNext()) {
               Entry<aqa, Integer> _snowmanxxxxxx = _snowmanxxxx.next();
               if (this.K >= _snowmanxxxxxx.getValue()) {
                  _snowmanxxxx.remove();
               }
            }

            List<apu> _snowmanxxxxxx = Lists.newArrayList();

            for (apu _snowmanxxxxxxx : this.g.a()) {
               _snowmanxxxxxx.add(new apu(_snowmanxxxxxxx.a(), _snowmanxxxxxxx.b() / 4, _snowmanxxxxxxx.c(), _snowmanxxxxxxx.d(), _snowmanxxxxxxx.e()));
            }

            _snowmanxxxxxx.addAll(this.ag);
            if (_snowmanxxxxxx.isEmpty()) {
               this.ah.clear();
            } else {
               List<aqm> _snowmanxxxxxxx = this.l.a(aqm.class, this.cc());
               if (!_snowmanxxxxxxx.isEmpty()) {
                  for (aqm _snowmanxxxxxxxx : _snowmanxxxxxxx) {
                     if (!this.ah.containsKey(_snowmanxxxxxxxx) && _snowmanxxxxxxxx.eh()) {
                        double _snowmanxxxxxxxxx = _snowmanxxxxxxxx.cD() - this.cD();
                        double _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.cH() - this.cH();
                        double _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx * _snowmanxxxxxxxxx + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx;
                        if (_snowmanxxxxxxxxxxx <= (double)(_snowmanx * _snowmanx)) {
                           this.ah.put(_snowmanxxxxxxxx, this.K + this.ak);

                           for (apu _snowmanxxxxxxxxxxxx : _snowmanxxxxxx) {
                              if (_snowmanxxxxxxxxxxxx.a().a()) {
                                 _snowmanxxxxxxxxxxxx.a().a(this, this.t(), _snowmanxxxxxxxx, _snowmanxxxxxxxxxxxx.c(), 0.5);
                              } else {
                                 _snowmanxxxxxxxx.c(new apu(_snowmanxxxxxxxxxxxx));
                              }
                           }

                           if (this.an != 0.0F) {
                              _snowmanx += this.an;
                              if (_snowmanx < 0.5F) {
                                 this.ad();
                                 return;
                              }

                              this.a(_snowmanx);
                           }

                           if (this.am != 0) {
                              this.ai = this.ai + this.am;
                              if (this.ai <= 0) {
                                 this.ad();
                                 return;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void b(float var1) {
      this.an = _snowman;
   }

   public void c(float var1) {
      this.ao = _snowman;
   }

   public void d(int var1) {
      this.aj = _snowman;
   }

   public void a(@Nullable aqm var1) {
      this.ap = _snowman;
      this.aq = _snowman == null ? null : _snowman.bS();
   }

   @Nullable
   public aqm t() {
      if (this.ap == null && this.aq != null && this.l instanceof aag) {
         aqa _snowman = ((aag)this.l).a(this.aq);
         if (_snowman instanceof aqm) {
            this.ap = (aqm)_snowman;
         }
      }

      return this.ap;
   }

   @Override
   protected void a(md var1) {
      this.K = _snowman.h("Age");
      this.ai = _snowman.h("Duration");
      this.aj = _snowman.h("WaitTime");
      this.ak = _snowman.h("ReapplicationDelay");
      this.am = _snowman.h("DurationOnUse");
      this.an = _snowman.j("RadiusOnUse");
      this.ao = _snowman.j("RadiusPerTick");
      this.a(_snowman.j("Radius"));
      if (_snowman.b("Owner")) {
         this.aq = _snowman.a("Owner");
      }

      if (_snowman.c("Particle", 8)) {
         try {
            this.a(dw.b(new StringReader(_snowman.l("Particle"))));
         } catch (CommandSyntaxException var5) {
            b.warn("Couldn't load custom particle {}", _snowman.l("Particle"), var5);
         }
      }

      if (_snowman.c("Color", 99)) {
         this.a(_snowman.h("Color"));
      }

      if (_snowman.c("Potion", 8)) {
         this.a(bnv.c(_snowman));
      }

      if (_snowman.c("Effects", 9)) {
         mj _snowman = _snowman.d("Effects", 10);
         this.ag.clear();

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            apu _snowmanxx = apu.b(_snowman.a(_snowmanx));
            if (_snowmanxx != null) {
               this.a(_snowmanxx);
            }
         }
      }
   }

   @Override
   protected void b(md var1) {
      _snowman.b("Age", this.K);
      _snowman.b("Duration", this.ai);
      _snowman.b("WaitTime", this.aj);
      _snowman.b("ReapplicationDelay", this.ak);
      _snowman.b("DurationOnUse", this.am);
      _snowman.a("RadiusOnUse", this.an);
      _snowman.a("RadiusPerTick", this.ao);
      _snowman.a("Radius", this.g());
      _snowman.a("Particle", this.i().a());
      if (this.aq != null) {
         _snowman.a("Owner", this.aq);
      }

      if (this.al) {
         _snowman.b("Color", this.h());
      }

      if (this.g != bnw.a && this.g != null) {
         _snowman.a("Potion", gm.U.b(this.g).toString());
      }

      if (!this.ag.isEmpty()) {
         mj _snowman = new mj();

         for (apu _snowmanx : this.ag) {
            _snowman.add(_snowmanx.a(new md()));
         }

         _snowman.a("Effects", _snowman);
      }
   }

   @Override
   public void a(us<?> var1) {
      if (c.equals(_snowman)) {
         this.x_();
      }

      super.a(_snowman);
   }

   @Override
   public cvc y_() {
      return cvc.d;
   }

   @Override
   public oj<?> P() {
      return new on(this);
   }

   @Override
   public aqb a(aqx var1) {
      return aqb.b(this.g() * 2.0F, 0.5F);
   }
}
