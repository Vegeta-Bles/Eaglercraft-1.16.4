import java.util.List;
import javax.annotation.Nullable;

public class bnk extends bkc {
   public bnk(blx.a var1) {
      super(_snowman);
   }

   @Override
   public bmb r() {
      return bnv.a(super.r(), bnw.D);
   }

   @Override
   public void a(bks var1, gj<bmb> var2) {
      if (this.a(_snowman)) {
         for (bnt _snowman : gm.U) {
            if (!_snowman.a().isEmpty()) {
               _snowman.add(bnv.a(new bmb(this), _snowman));
            }
         }
      }
   }

   @Override
   public void a(bmb var1, @Nullable brx var2, List<nr> var3, bnl var4) {
      bnv.a(_snowman, _snowman, 0.125F);
   }

   @Override
   public String f(bmb var1) {
      return bnv.d(_snowman).b(this.a() + ".effect.");
   }
}
