import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Random;

public class dqg extends dpp<bis> {
   private static final vk H = new vk("textures/gui/container/enchanting_table.png");
   private static final vk I = new vk("textures/entity/enchanting_table_book.png");
   private static final dto J = new dto();
   private final Random K = new Random();
   public int A;
   public float B;
   public float C;
   public float D;
   public float E;
   public float F;
   public float G;
   private bmb L = bmb.b;

   public dqg(bis var1, bfv var2, nr var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   public void d() {
      super.d();
      this.i();
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      int _snowman = (this.k - this.b) / 2;
      int _snowmanx = (this.l - this.c) / 2;

      for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
         double _snowmanxxx = _snowman - (double)(_snowman + 60);
         double _snowmanxxxx = _snowman - (double)(_snowmanx + 14 + 19 * _snowmanxx);
         if (_snowmanxxx >= 0.0 && _snowmanxxxx >= 0.0 && _snowmanxxx < 108.0 && _snowmanxxxx < 19.0 && this.t.a(this.i.s, _snowmanxx)) {
            this.i.q.a(this.t.b, _snowmanxx);
            return true;
         }
      }

      return super.a(_snowman, _snowman, _snowman);
   }

   @Override
   protected void a(dfm var1, float var2, int var3, int var4) {
      dep.c();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.i.M().a(H);
      int _snowman = (this.k - this.b) / 2;
      int _snowmanx = (this.l - this.c) / 2;
      this.b(_snowman, _snowman, _snowmanx, 0, 0, this.b, this.c);
      RenderSystem.matrixMode(5889);
      RenderSystem.pushMatrix();
      RenderSystem.loadIdentity();
      int _snowmanxx = (int)this.i.aD().s();
      RenderSystem.viewport((this.k - 320) / 2 * _snowmanxx, (this.l - 240) / 2 * _snowmanxx, 320 * _snowmanxx, 240 * _snowmanxx);
      RenderSystem.translatef(-0.34F, 0.23F, 0.0F);
      RenderSystem.multMatrix(b.a(90.0, 1.3333334F, 9.0F, 80.0F));
      RenderSystem.matrixMode(5888);
      _snowman.a();
      dfm.a _snowmanxxx = _snowman.c();
      _snowmanxxx.a().a();
      _snowmanxxx.b().c();
      _snowman.a(0.0, 3.3F, 1984.0);
      float _snowmanxxxx = 5.0F;
      _snowman.a(5.0F, 5.0F, 5.0F);
      _snowman.a(g.f.a(180.0F));
      _snowman.a(g.b.a(20.0F));
      float _snowmanxxxxx = afm.g(_snowman, this.G, this.F);
      _snowman.a((double)((1.0F - _snowmanxxxxx) * 0.2F), (double)((1.0F - _snowmanxxxxx) * 0.1F), (double)((1.0F - _snowmanxxxxx) * 0.25F));
      float _snowmanxxxxxx = -(1.0F - _snowmanxxxxx) * 90.0F - 90.0F;
      _snowman.a(g.d.a(_snowmanxxxxxx));
      _snowman.a(g.b.a(180.0F));
      float _snowmanxxxxxxx = afm.g(_snowman, this.C, this.B) + 0.25F;
      float _snowmanxxxxxxxx = afm.g(_snowman, this.C, this.B) + 0.75F;
      _snowmanxxxxxxx = (_snowmanxxxxxxx - (float)afm.b((double)_snowmanxxxxxxx)) * 1.6F - 0.3F;
      _snowmanxxxxxxxx = (_snowmanxxxxxxxx - (float)afm.b((double)_snowmanxxxxxxxx)) * 1.6F - 0.3F;
      if (_snowmanxxxxxxx < 0.0F) {
         _snowmanxxxxxxx = 0.0F;
      }

      if (_snowmanxxxxxxxx < 0.0F) {
         _snowmanxxxxxxxx = 0.0F;
      }

      if (_snowmanxxxxxxx > 1.0F) {
         _snowmanxxxxxxx = 1.0F;
      }

      if (_snowmanxxxxxxxx > 1.0F) {
         _snowmanxxxxxxxx = 1.0F;
      }

      RenderSystem.enableRescaleNormal();
      J.a(0.0F, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxx);
      eag.a _snowmanxxxxxxxxx = eag.a(dfo.a().c());
      dfq _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.getBuffer(J.a(I));
      J.a(_snowman, _snowmanxxxxxxxxxx, 15728880, ejw.a, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowmanxxxxxxxxx.a();
      _snowman.b();
      RenderSystem.matrixMode(5889);
      RenderSystem.viewport(0, 0, this.i.aD().k(), this.i.aD().l());
      RenderSystem.popMatrix();
      RenderSystem.matrixMode(5888);
      dep.d();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      dqf.a().a((long)this.t.f());
      int _snowmanxxxxxxxxxxx = this.t.e();

      for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < 3; _snowmanxxxxxxxxxxxx++) {
         int _snowmanxxxxxxxxxxxxx = _snowman + 60;
         int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx + 20;
         this.d(0);
         this.i.M().a(H);
         int _snowmanxxxxxxxxxxxxxxx = this.t.c[_snowmanxxxxxxxxxxxx];
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         if (_snowmanxxxxxxxxxxxxxxx == 0) {
            this.b(_snowman, _snowmanxxxxxxxxxxxxx, _snowmanx + 14 + 19 * _snowmanxxxxxxxxxxxx, 0, 185, 108, 19);
         } else {
            String _snowmanxxxxxxxxxxxxxxxx = "" + _snowmanxxxxxxxxxxxxxxx;
            int _snowmanxxxxxxxxxxxxxxxxx = 86 - this.o.b(_snowmanxxxxxxxxxxxxxxxx);
            nu _snowmanxxxxxxxxxxxxxxxxxx = dqf.a().a(this.o, _snowmanxxxxxxxxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxxxx = 6839882;
            if ((_snowmanxxxxxxxxxxx < _snowmanxxxxxxxxxxxx + 1 || this.i.s.bD < _snowmanxxxxxxxxxxxxxxx) && !this.i.s.bC.d) {
               this.b(_snowman, _snowmanxxxxxxxxxxxxx, _snowmanx + 14 + 19 * _snowmanxxxxxxxxxxxx, 0, 185, 108, 19);
               this.b(_snowman, _snowmanxxxxxxxxxxxxx + 1, _snowmanx + 15 + 19 * _snowmanxxxxxxxxxxxx, 16 * _snowmanxxxxxxxxxxxx, 239, 16, 16);
               this.o.a(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanx + 16 + 19 * _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxx & 16711422) >> 1);
               _snowmanxxxxxxxxxxxxxxxxxxx = 4226832;
            } else {
               int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowman - (_snowman + 60);
               int _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman - (_snowmanx + 14 + 19 * _snowmanxxxxxxxxxxxx);
               if (_snowmanxxxxxxxxxxxxxxxxxxxx >= 0 && _snowmanxxxxxxxxxxxxxxxxxxxxx >= 0 && _snowmanxxxxxxxxxxxxxxxxxxxx < 108 && _snowmanxxxxxxxxxxxxxxxxxxxxx < 19) {
                  this.b(_snowman, _snowmanxxxxxxxxxxxxx, _snowmanx + 14 + 19 * _snowmanxxxxxxxxxxxx, 0, 204, 108, 19);
                  _snowmanxxxxxxxxxxxxxxxxxxx = 16777088;
               } else {
                  this.b(_snowman, _snowmanxxxxxxxxxxxxx, _snowmanx + 14 + 19 * _snowmanxxxxxxxxxxxx, 0, 166, 108, 19);
               }

               this.b(_snowman, _snowmanxxxxxxxxxxxxx + 1, _snowmanx + 15 + 19 * _snowmanxxxxxxxxxxxx, 16 * _snowmanxxxxxxxxxxxx, 223, 16, 16);
               this.o.a(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanx + 16 + 19 * _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx);
               _snowmanxxxxxxxxxxxxxxxxxxx = 8453920;
            }

            this.o
               .a(
                  _snowman,
                  _snowmanxxxxxxxxxxxxxxxx,
                  (float)(_snowmanxxxxxxxxxxxxxx + 86 - this.o.b(_snowmanxxxxxxxxxxxxxxxx)),
                  (float)(_snowmanx + 16 + 19 * _snowmanxxxxxxxxxxxx + 7),
                  _snowmanxxxxxxxxxxxxxxxxxxx
               );
         }
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      _snowman = this.i.aj();
      this.a(_snowman);
      super.a(_snowman, _snowman, _snowman, _snowman);
      this.a(_snowman, _snowman, _snowman);
      boolean _snowman = this.i.s.bC.d;
      int _snowmanx = this.t.e();

      for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
         int _snowmanxxx = this.t.c[_snowmanxx];
         bps _snowmanxxxx = bps.c(this.t.d[_snowmanxx]);
         int _snowmanxxxxx = this.t.e[_snowmanxx];
         int _snowmanxxxxxx = _snowmanxx + 1;
         if (this.a(60, 14 + 19 * _snowmanxx, 108, 17, (double)_snowman, (double)_snowman) && _snowmanxxx > 0 && _snowmanxxxxx >= 0 && _snowmanxxxx != null) {
            List<nr> _snowmanxxxxxxx = Lists.newArrayList();
            _snowmanxxxxxxx.add(new of("container.enchant.clue", _snowmanxxxx.d(_snowmanxxxxx)).a(k.p));
            if (!_snowman) {
               _snowmanxxxxxxx.add(oe.d);
               if (this.i.s.bD < _snowmanxxx) {
                  _snowmanxxxxxxx.add(new of("container.enchant.level.requirement", this.t.c[_snowmanxx]).a(k.m));
               } else {
                  nx _snowmanxxxxxxxx;
                  if (_snowmanxxxxxx == 1) {
                     _snowmanxxxxxxxx = new of("container.enchant.lapis.one");
                  } else {
                     _snowmanxxxxxxxx = new of("container.enchant.lapis.many", _snowmanxxxxxx);
                  }

                  _snowmanxxxxxxx.add(_snowmanxxxxxxxx.a(_snowmanx >= _snowmanxxxxxx ? k.h : k.m));
                  nx _snowmanxxxxxxxxx;
                  if (_snowmanxxxxxx == 1) {
                     _snowmanxxxxxxxxx = new of("container.enchant.level.one");
                  } else {
                     _snowmanxxxxxxxxx = new of("container.enchant.level.many", _snowmanxxxxxx);
                  }

                  _snowmanxxxxxxx.add(_snowmanxxxxxxxxx.a(k.h));
               }
            }

            this.b(_snowman, _snowmanxxxxxxx, _snowman, _snowman);
            break;
         }
      }
   }

   public void i() {
      bmb _snowman = this.t.a(0).e();
      if (!bmb.b(_snowman, this.L)) {
         this.L = _snowman;

         do {
            this.D = this.D + (float)(this.K.nextInt(4) - this.K.nextInt(4));
         } while (this.B <= this.D + 1.0F && this.B >= this.D - 1.0F);
      }

      this.A++;
      this.C = this.B;
      this.G = this.F;
      boolean _snowmanx = false;

      for (int _snowmanxx = 0; _snowmanxx < 3; _snowmanxx++) {
         if (this.t.c[_snowmanxx] != 0) {
            _snowmanx = true;
         }
      }

      if (_snowmanx) {
         this.F += 0.2F;
      } else {
         this.F -= 0.2F;
      }

      this.F = afm.a(this.F, 0.0F, 1.0F);
      float _snowmanxxx = (this.D - this.B) * 0.4F;
      float _snowmanxxxx = 0.2F;
      _snowmanxxx = afm.a(_snowmanxxx, -0.2F, 0.2F);
      this.E = this.E + (_snowmanxxx - this.E) * 0.9F;
      this.B = this.B + this.E;
   }
}
