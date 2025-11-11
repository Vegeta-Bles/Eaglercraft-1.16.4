import java.util.Random;

public class byr extends buo {
   public byr(ceg.c var1) {
      super(_snowman);
   }

   protected int a(Random var1) {
      if (this == bup.H) {
         return afm.a(_snowman, 0, 2);
      } else if (this == bup.bT) {
         return afm.a(_snowman, 3, 7);
      } else if (this == bup.ej) {
         return afm.a(_snowman, 3, 7);
      } else if (this == bup.aq) {
         return afm.a(_snowman, 2, 5);
      } else if (this == bup.fx) {
         return afm.a(_snowman, 2, 5);
      } else {
         return this == bup.I ? afm.a(_snowman, 0, 1) : 0;
      }
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, bmb var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      if (bpu.a(bpw.u, _snowman) == 0) {
         int _snowman = this.a(_snowman.t);
         if (_snowman > 0) {
            this.a(_snowman, _snowman, _snowman);
         }
      }
   }
}
