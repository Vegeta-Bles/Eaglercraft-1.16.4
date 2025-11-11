import java.util.List;
import javax.annotation.Nullable;

public class bmg extends bng {
   public bmg(blx.a var1) {
      super(_snowman);
   }

   @Override
   public void a(bmb var1, @Nullable brx var2, List<nr> var3, bnl var4) {
      bnv.a(_snowman, _snowman, 0.25F);
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      _snowman.a(null, _snowman.cD(), _snowman.cE(), _snowman.cH(), adq.he, adr.g, 0.5F, 0.4F / (h.nextFloat() * 0.4F + 0.8F));
      return super.a(_snowman, _snowman, _snowman);
   }
}
