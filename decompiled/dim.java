import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dim extends eoo {
   private static final Logger a = LogManager.getLogger();
   private static final nr b = new of("mco.terms.title");
   private static final nr c = new of("mco.terms.sentence.1");
   private static final nr p = new oe(" ").a(new of("mco.terms.sentence.2").c(ob.a.c(true)));
   private final dot q;
   private final dfw r;
   private final dgq s;
   private boolean t;
   private final String u = "https://aka.ms/MinecraftRealmsTerms";

   public dim(dot var1, dfw var2, dgq var3) {
      this.q = _snowman;
      this.r = _snowman;
      this.s = _snowman;
   }

   @Override
   public void b() {
      this.i.m.a(true);
      int _snowman = this.k / 4 - 2;
      this.a(new dlj(this.k / 4, j(12), _snowman, 20, new of("mco.terms.buttons.agree"), var1x -> this.h()));
      this.a(new dlj(this.k / 2 + 4, j(12), _snowman, 20, new of("mco.terms.buttons.disagree"), var1x -> this.i.a(this.q)));
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         this.i.a(this.q);
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   private void h() {
      dgb _snowman = dgb.a();

      try {
         _snowman.l();
         this.i.a(new dhz(this.q, new diz(this.r, this.q, this.s, new ReentrantLock())));
      } catch (dhi var3) {
         a.error("Couldn't agree to TOS");
      }
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (this.t) {
         this.i.m.a("https://aka.ms/MinecraftRealmsTerms");
         x.i().a("https://aka.ms/MinecraftRealmsTerms");
         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public String ax_() {
      return super.ax_() + ". " + c.getString() + " " + p.getString();
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, b, this.k / 2, 17, 16777215);
      this.o.b(_snowman, c, (float)(this.k / 2 - 120), (float)j(5), 16777215);
      int _snowman = this.o.a(c);
      int _snowmanx = this.k / 2 - 121 + _snowman;
      int _snowmanxx = j(5);
      int _snowmanxxx = _snowmanx + this.o.a(p) + 1;
      int _snowmanxxxx = _snowmanxx + 1 + 9;
      this.t = _snowmanx <= _snowman && _snowman <= _snowmanxxx && _snowmanxx <= _snowman && _snowman <= _snowmanxxxx;
      this.o.b(_snowman, p, (float)(this.k / 2 - 120 + _snowman), (float)j(5), this.t ? 7107012 : 3368635);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
