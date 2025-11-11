import java.util.EnumSet;

public class axa extends avv {
   private final bbb a;
   private final double b;
   private double c;
   private double d;
   private double e;

   public axa(bbb var1, double var2) {
      this.a = _snowman;
      this.b = _snowman;
      this.a(EnumSet.of(avv.a.a));
   }

   @Override
   public boolean a() {
      if (!this.a.eW() && this.a.bs()) {
         dcn _snowman = azj.a(this.a, 5, 4);
         if (_snowman == null) {
            return false;
         } else {
            this.c = _snowman.b;
            this.d = _snowman.c;
            this.e = _snowman.d;
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public void c() {
      this.a.x().a(this.c, this.d, this.e, this.b);
   }

   @Override
   public boolean b() {
      return !this.a.eW() && !this.a.x().m() && this.a.bs();
   }

   @Override
   public void e() {
      if (!this.a.eW() && this.a.cY().nextInt(50) == 0) {
         aqa _snowman = this.a.cn().get(0);
         if (_snowman == null) {
            return;
         }

         if (_snowman instanceof bfw) {
            int _snowmanx = this.a.fc();
            int _snowmanxx = this.a.fj();
            if (_snowmanxx > 0 && this.a.cY().nextInt(_snowmanxx) < _snowmanx) {
               this.a.i((bfw)_snowman);
               return;
            }

            this.a.v(5);
         }

         this.a.be();
         this.a.fm();
         this.a.l.a(this.a, (byte)6);
      }
   }
}
