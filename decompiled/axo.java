import java.util.EnumSet;
import java.util.List;

public class axo extends axx {
   private final bai a;
   private aqm b;
   private final azg c = new azg().a(64.0);

   public axo(bai var1) {
      super(_snowman, false, true);
      this.a = _snowman;
      this.a(EnumSet.of(avv.a.d));
   }

   @Override
   public boolean a() {
      dci _snowman = this.a.cc().c(10.0, 8.0, 10.0);
      List<aqm> _snowmanx = this.a.l.a(bfj.class, this.c, this.a, _snowman);
      List<bfw> _snowmanxx = this.a.l.a(this.c, this.a, _snowman);

      for (aqm _snowmanxxx : _snowmanx) {
         bfj _snowmanxxxx = (bfj)_snowmanxxx;

         for (bfw _snowmanxxxxx : _snowmanxx) {
            int _snowmanxxxxxx = _snowmanxxxx.g(_snowmanxxxxx);
            if (_snowmanxxxxxx <= -100) {
               this.b = _snowmanxxxxx;
            }
         }
      }

      return this.b == null ? false : !(this.b instanceof bfw) || !this.b.a_() && !((bfw)this.b).b_();
   }

   @Override
   public void c() {
      this.a.h(this.b);
      super.c();
   }
}
