import java.util.List;
import javax.annotation.Nullable;

public class bmv extends blx {
   public bmv(blx.a var1) {
      super(_snowman);
      bwa.a(this, bjy.a);
   }

   @Override
   public String f(bmb var1) {
      return _snowman.b("BlockEntityTag") != null ? this.a() + '.' + d(_snowman).c() : super.f(_snowman);
   }

   @Override
   public void a(bmb var1, @Nullable brx var2, List<nr> var3, bnl var4) {
      bke.a(_snowman, _snowman);
   }

   @Override
   public bnn d_(bmb var1) {
      return bnn.d;
   }

   @Override
   public int e_(bmb var1) {
      return 72000;
   }

   @Override
   public aov<bmb> a(brx var1, bfw var2, aot var3) {
      bmb _snowman = _snowman.b(_snowman);
      _snowman.c(_snowman);
      return aov.b(_snowman);
   }

   @Override
   public boolean a(bmb var1, bmb var2) {
      return aeg.c.a(_snowman.b()) || super.a(_snowman, _snowman);
   }

   public static bkx d(bmb var0) {
      return bkx.a(_snowman.a("BlockEntityTag").h("Base"));
   }
}
