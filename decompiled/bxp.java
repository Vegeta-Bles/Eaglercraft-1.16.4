import com.google.common.collect.Maps;
import java.util.Map;

public class bxp extends buo {
   private final buo a;
   private static final Map<buo, buo> b = Maps.newIdentityHashMap();

   public bxp(buo var1, ceg.c var2) {
      super(_snowman);
      this.a = _snowman;
      b.put(_snowman, this);
   }

   public buo c() {
      return this.a;
   }

   public static boolean h(ceh var0) {
      return b.containsKey(_snowman.b());
   }

   private void a(aag var1, fx var2) {
      bdx _snowman = aqe.au.a(_snowman);
      _snowman.b((double)_snowman.u() + 0.5, (double)_snowman.v(), (double)_snowman.w() + 0.5, 0.0F, 0.0F);
      _snowman.c(_snowman);
      _snowman.G();
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, bmb var4) {
      super.a(_snowman, _snowman, _snowman, _snowman);
      if (_snowman.V().b(brt.f) && bpu.a(bpw.u, _snowman) == 0) {
         this.a(_snowman, _snowman);
      }
   }

   @Override
   public void a(brx var1, fx var2, brp var3) {
      if (_snowman instanceof aag) {
         this.a((aag)_snowman, _snowman);
      }
   }

   public static ceh c(buo var0) {
      return b.get(_snowman).n();
   }
}
