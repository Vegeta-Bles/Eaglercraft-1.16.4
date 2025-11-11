import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class crx extends cru {
   private static final Logger d = LogManager.getLogger();
   protected ctb a;
   protected csx b;
   protected fx c;

   public crx(clb var1, int var2) {
      super(_snowman, _snowman);
   }

   public crx(clb var1, md var2) {
      super(_snowman, _snowman);
      this.c = new fx(_snowman.h("TPX"), _snowman.h("TPY"), _snowman.h("TPZ"));
   }

   protected void a(ctb var1, fx var2, csx var3) {
      this.a = _snowman;
      this.a(gc.c);
      this.c = _snowman;
      this.b = _snowman;
      this.n = _snowman.b(_snowman, _snowman);
   }

   @Override
   protected void a(md var1) {
      _snowman.b("TPX", this.c.u());
      _snowman.b("TPY", this.c.v());
      _snowman.b("TPZ", this.c.w());
   }

   @Override
   public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
      this.b.a(_snowman);
      this.n = this.a.b(this.b, this.c);
      if (this.a.a(_snowman, this.c, _snowman, this.b, _snowman, 2)) {
         for (ctb.c _snowman : this.a.a(this.c, this.b, bup.mY)) {
            if (_snowman.c != null) {
               cfo _snowmanx = cfo.valueOf(_snowman.c.l("mode"));
               if (_snowmanx == cfo.d) {
                  this.a(_snowman.c.l("metadata"), _snowman.a, _snowman, _snowman, _snowman);
               }
            }
         }

         for (ctb.c _snowmanx : this.a.a(this.c, this.b, bup.mZ)) {
            if (_snowmanx.c != null) {
               String _snowmanxx = _snowmanx.c.l("final_state");
               ei _snowmanxxx = new ei(new StringReader(_snowmanxx), false);
               ceh _snowmanxxxx = bup.a.n();

               try {
                  _snowmanxxx.a(true);
                  ceh _snowmanxxxxx = _snowmanxxx.b();
                  if (_snowmanxxxxx != null) {
                     _snowmanxxxx = _snowmanxxxxx;
                  } else {
                     d.error("Error while parsing blockstate {} in jigsaw block @ {}", _snowmanxx, _snowmanx.a);
                  }
               } catch (CommandSyntaxException var16) {
                  d.error("Error while parsing blockstate {} in jigsaw block @ {}", _snowmanxx, _snowmanx.a);
               }

               _snowman.a(_snowmanx.a, _snowmanxxxx, 3);
            }
         }
      }

      return true;
   }

   protected abstract void a(String var1, fx var2, bsk var3, Random var4, cra var5);

   @Override
   public void a(int var1, int var2, int var3) {
      super.a(_snowman, _snowman, _snowman);
      this.c = this.c.b(_snowman, _snowman, _snowman);
   }

   @Override
   public bzm ap_() {
      return this.b.d();
   }
}
