import java.util.List;
import java.util.function.Predicate;

public class avr extends avv {
   private final azy a;
   private int b;
   private int c;

   public avr(azy var1) {
      this.a = _snowman;
      this.c = this.a(_snowman);
   }

   protected int a(azy var1) {
      return 200 + _snowman.cY().nextInt(200) % 20;
   }

   @Override
   public boolean a() {
      if (this.a.eR()) {
         return false;
      } else if (this.a.eO()) {
         return true;
      } else if (this.c > 0) {
         this.c--;
         return false;
      } else {
         this.c = this.a(this.a);
         Predicate<azy> _snowman = var0 -> var0.eQ() || !var0.eO();
         List<azy> _snowmanx = this.a.l.a((Class<? extends azy>)this.a.getClass(), this.a.cc().c(8.0, 8.0, 8.0), _snowman);
         azy _snowmanxx = _snowmanx.stream().filter(azy::eQ).findAny().orElse(this.a);
         _snowmanxx.a(_snowmanx.stream().filter(var0 -> !var0.eO()));
         return this.a.eO();
      }
   }

   @Override
   public boolean b() {
      return this.a.eO() && this.a.eS();
   }

   @Override
   public void c() {
      this.b = 0;
   }

   @Override
   public void d() {
      this.a.eP();
   }

   @Override
   public void e() {
      if (--this.b <= 0) {
         this.b = 10;
         this.a.eT();
      }
   }
}
