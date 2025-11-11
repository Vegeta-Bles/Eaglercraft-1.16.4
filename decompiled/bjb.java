public class bjb extends bic {
   private final aon c;
   private final bil d;

   public bjb(int var1) {
      this(_snowman, new apa(1), new bjq(1));
   }

   public bjb(int var1, aon var2, bil var3) {
      super(bje.q, _snowman);
      a(_snowman, 1);
      a(_snowman, 1);
      this.c = _snowman;
      this.d = _snowman;
      this.a(new bjr(_snowman, 0, 0, 0) {
         @Override
         public void d() {
            super.d();
            bjb.this.a(this.c);
         }
      });
      this.a(_snowman);
   }

   @Override
   public boolean a(bfw var1, int var2) {
      if (_snowman >= 100) {
         int _snowman = _snowman - 100;
         this.a(0, _snowman);
         return true;
      } else {
         switch (_snowman) {
            case 1: {
               int _snowman = this.d.a(0);
               this.a(0, _snowman - 1);
               return true;
            }
            case 2: {
               int _snowman = this.d.a(0);
               this.a(0, _snowman + 1);
               return true;
            }
            case 3: {
               if (!_snowman.eK()) {
                  return false;
               }

               bmb _snowman = this.c.b(0);
               this.c.X_();
               if (!_snowman.bm.e(_snowman)) {
                  _snowman.a(_snowman, false);
               }

               return true;
            }
            default:
               return false;
         }
      }
   }

   @Override
   public void a(int var1, int var2) {
      super.a(_snowman, _snowman);
      this.c();
   }

   @Override
   public boolean a(bfw var1) {
      return this.c.a(_snowman);
   }

   public bmb e() {
      return this.c.a(0);
   }

   public int f() {
      return this.d.a(0);
   }
}
