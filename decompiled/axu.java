import java.util.EnumSet;

public class axu extends axx {
   private final are a;
   private aqm b;
   private int c;

   public axu(are var1) {
      super(_snowman, false);
      this.a = _snowman;
      this.a(EnumSet.of(avv.a.d));
   }

   @Override
   public boolean a() {
      if (this.a.eK() && !this.a.eO()) {
         aqm _snowman = this.a.eN();
         if (_snowman == null) {
            return false;
         } else {
            this.b = _snowman.cZ();
            int _snowmanx = _snowman.da();
            return _snowmanx != this.c && this.a(this.b, azg.a) && this.a.a(this.b, _snowman);
         }
      } else {
         return false;
      }
   }

   @Override
   public void c() {
      this.e.h(this.b);
      aqm _snowman = this.a.eN();
      if (_snowman != null) {
         this.c = _snowman.da();
      }

      super.c();
   }
}
