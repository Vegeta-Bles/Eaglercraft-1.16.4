import javax.annotation.Nullable;

public class bdb extends beb {
   public bdb(aqe<? extends bdb> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public static ark.a m() {
      return beb.eK().a(arl.a, 12.0);
   }

   @Override
   public boolean B(aqa var1) {
      if (super.B(_snowman)) {
         if (_snowman instanceof aqm) {
            int _snowman = 0;
            if (this.l.ad() == aor.c) {
               _snowman = 7;
            } else if (this.l.ad() == aor.d) {
               _snowman = 15;
            }

            if (_snowman > 0) {
               ((aqm)_snowman).c(new apu(apw.s, _snowman * 20, 0));
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      return _snowman;
   }

   @Override
   protected float b(aqx var1, aqb var2) {
      return 0.45F;
   }
}
