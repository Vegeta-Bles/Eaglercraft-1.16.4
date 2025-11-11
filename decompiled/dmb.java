import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Iterator;
import java.util.List;

public class dmb extends dkw implements ent {
   private final djz a;
   private final List<dmb.a> b = Lists.newArrayList();
   private boolean c;

   public dmb(djz var1) {
      this.a = _snowman;
   }

   public void a(dfm var1) {
      if (!this.c && this.a.k.W) {
         this.a.W().a(this);
         this.c = true;
      } else if (this.c && !this.a.k.W) {
         this.a.W().b(this);
         this.c = false;
      }

      if (this.c && !this.b.isEmpty()) {
         RenderSystem.pushMatrix();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         dcn _snowman = new dcn(this.a.s.cD(), this.a.s.cG(), this.a.s.cH());
         dcn _snowmanx = new dcn(0.0, 0.0, -1.0).a(-this.a.s.q * (float) (Math.PI / 180.0)).b(-this.a.s.p * (float) (Math.PI / 180.0));
         dcn _snowmanxx = new dcn(0.0, 1.0, 0.0).a(-this.a.s.q * (float) (Math.PI / 180.0)).b(-this.a.s.p * (float) (Math.PI / 180.0));
         dcn _snowmanxxx = _snowmanx.c(_snowmanxx);
         int _snowmanxxxx = 0;
         int _snowmanxxxxx = 0;
         Iterator<dmb.a> _snowmanxxxxxx = this.b.iterator();

         while (_snowmanxxxxxx.hasNext()) {
            dmb.a _snowmanxxxxxxx = _snowmanxxxxxx.next();
            if (_snowmanxxxxxxx.b() + 3000L <= x.b()) {
               _snowmanxxxxxx.remove();
            } else {
               _snowmanxxxxx = Math.max(_snowmanxxxxx, this.a.g.a(_snowmanxxxxxxx.a()));
            }
         }

         _snowmanxxxxx += this.a.g.b("<") + this.a.g.b(" ") + this.a.g.b(">") + this.a.g.b(" ");

         for (dmb.a _snowmanxxxxxxx : this.b) {
            int _snowmanxxxxxxxx = 255;
            nr _snowmanxxxxxxxxx = _snowmanxxxxxxx.a();
            dcn _snowmanxxxxxxxxxx = _snowmanxxxxxxx.c().d(_snowman).d();
            double _snowmanxxxxxxxxxxx = -_snowmanxxx.b(_snowmanxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxx = -_snowmanx.b(_snowmanxxxxxxxxxx);
            boolean _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx > 0.5;
            int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxx / 2;
            int _snowmanxxxxxxxxxxxxxxx = 9;
            int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx / 2;
            float _snowmanxxxxxxxxxxxxxxxxx = 1.0F;
            int _snowmanxxxxxxxxxxxxxxxxxx = this.a.g.a(_snowmanxxxxxxxxx);
            int _snowmanxxxxxxxxxxxxxxxxxxx = afm.c(afm.b(255.0, 75.0, (double)((float)(x.b() - _snowmanxxxxxxx.b()) / 3000.0F)));
            int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx << 16 | _snowmanxxxxxxxxxxxxxxxxxxx << 8 | _snowmanxxxxxxxxxxxxxxxxxxx;
            RenderSystem.pushMatrix();
            RenderSystem.translatef(
               (float)this.a.aD().o() - (float)_snowmanxxxxxxxxxxxxxx * 1.0F - 2.0F,
               (float)(this.a.aD().p() - 30) - (float)(_snowmanxxxx * (_snowmanxxxxxxxxxxxxxxx + 1)) * 1.0F,
               0.0F
            );
            RenderSystem.scalef(1.0F, 1.0F, 1.0F);
            a(_snowman, -_snowmanxxxxxxxxxxxxxx - 1, -_snowmanxxxxxxxxxxxxxxxx - 1, _snowmanxxxxxxxxxxxxxx + 1, _snowmanxxxxxxxxxxxxxxxx + 1, this.a.k.b(0.8F));
            RenderSystem.enableBlend();
            if (!_snowmanxxxxxxxxxxxxx) {
               if (_snowmanxxxxxxxxxxx > 0.0) {
                  this.a.g.b(_snowman, ">", (float)(_snowmanxxxxxxxxxxxxxx - this.a.g.b(">")), (float)(-_snowmanxxxxxxxxxxxxxxxx), _snowmanxxxxxxxxxxxxxxxxxxxx + -16777216);
               } else if (_snowmanxxxxxxxxxxx < 0.0) {
                  this.a.g.b(_snowman, "<", (float)(-_snowmanxxxxxxxxxxxxxx), (float)(-_snowmanxxxxxxxxxxxxxxxx), _snowmanxxxxxxxxxxxxxxxxxxxx + -16777216);
               }
            }

            this.a.g.b(_snowman, _snowmanxxxxxxxxx, (float)(-_snowmanxxxxxxxxxxxxxxxxxx / 2), (float)(-_snowmanxxxxxxxxxxxxxxxx), _snowmanxxxxxxxxxxxxxxxxxxxx + -16777216);
            RenderSystem.popMatrix();
            _snowmanxxxx++;
         }

         RenderSystem.disableBlend();
         RenderSystem.popMatrix();
      }
   }

   @Override
   public void a(emt var1, env var2) {
      if (_snowman.c() != null) {
         nr _snowman = _snowman.c();
         if (!this.b.isEmpty()) {
            for (dmb.a _snowmanx : this.b) {
               if (_snowmanx.a().equals(_snowman)) {
                  _snowmanx.a(new dcn(_snowman.h(), _snowman.i(), _snowman.j()));
                  return;
               }
            }
         }

         this.b.add(new dmb.a(_snowman, new dcn(_snowman.h(), _snowman.i(), _snowman.j())));
      }
   }

   public class a {
      private final nr b;
      private long c;
      private dcn d;

      public a(nr var2, dcn var3) {
         this.b = _snowman;
         this.d = _snowman;
         this.c = x.b();
      }

      public nr a() {
         return this.b;
      }

      public long b() {
         return this.c;
      }

      public dcn c() {
         return this.d;
      }

      public void a(dcn var1) {
         this.d = _snowman;
         this.c = x.b();
      }
   }
}
