import java.util.List;

public class axw<T extends aqn & aqs> extends avv {
   private final T a;
   private final boolean b;
   private int c;

   public axw(T var1, boolean var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public boolean a() {
      return this.a.l.V().b(brt.G) && this.g();
   }

   private boolean g() {
      return this.a.cZ() != null && this.a.cZ().X() == aqe.bc && this.a.da() > this.c;
   }

   @Override
   public void c() {
      this.c = this.a.da();
      this.a.I_();
      if (this.b) {
         this.h().stream().filter(var1 -> var1 != this.a).map(var0 -> (aqs)var0).forEach(aqs::I_);
      }

      super.c();
   }

   private List<aqn> h() {
      double _snowman = this.a.b(arl.b);
      dci _snowmanx = dci.a(this.a.cA()).c(_snowman, 10.0, _snowman);
      return this.a.l.b((Class<? extends aqn>)this.a.getClass(), _snowmanx);
   }
}
