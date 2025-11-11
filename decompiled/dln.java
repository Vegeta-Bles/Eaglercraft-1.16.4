import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;

public class dln {
   private static final afa a = afa.a(32, ob.a);

   private static String a(String var0) {
      return djz.C().k.L ? _snowman : k.a(_snowman);
   }

   public static List<afa> a(nu var0, int var1, dku var2) {
      djo _snowman = new djo();
      _snowman.a((var1x, var2x) -> {
         _snowman.a(nu.a(a(var2x), var1x));
         return Optional.empty();
      }, ob.a);
      List<afa> _snowmanx = Lists.newArrayList();
      _snowman.b().a(_snowman.b(), _snowman, ob.a, (var1x, var2x) -> {
         afa _snowmanxx = ly.a().a(var1x);
         _snowman.add(var2x ? afa.a(a, _snowmanxx) : _snowmanxx);
      });
      return (List<afa>)(_snowmanx.isEmpty() ? Lists.newArrayList(new afa[]{afa.a}) : _snowmanx);
   }
}
