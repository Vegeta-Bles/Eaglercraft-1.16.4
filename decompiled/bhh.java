import com.google.common.collect.Maps;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class bhh {
   public static final bhh a = a("empty").a(0, bhf.b).a();
   public static final bhh b = a("simple").a(5000, bhf.c).a(11000, bhf.e).a();
   public static final bhh c = a("villager_baby").a(10, bhf.b).a(3000, bhf.d).a(6000, bhf.b).a(10000, bhf.d).a(12000, bhf.e).a();
   public static final bhh d = a("villager_default").a(10, bhf.b).a(2000, bhf.c).a(9000, bhf.f).a(11000, bhf.b).a(12000, bhf.e).a();
   private final Map<bhf, bhj> e = Maps.newHashMap();

   public bhh() {
   }

   protected static bhi a(String var0) {
      bhh _snowman = gm.a(gm.am, _snowman, new bhh());
      return new bhi(_snowman);
   }

   protected void a(bhf var1) {
      if (!this.e.containsKey(_snowman)) {
         this.e.put(_snowman, new bhj());
      }
   }

   protected bhj b(bhf var1) {
      return this.e.get(_snowman);
   }

   protected List<bhj> c(bhf var1) {
      return this.e.entrySet().stream().filter(var1x -> var1x.getKey() != _snowman).map(Entry::getValue).collect(Collectors.toList());
   }

   public bhf a(int var1) {
      return this.e.entrySet().stream().max(Comparator.comparingDouble(var1x -> (double)var1x.getValue().a(_snowman))).map(Entry::getKey).orElse(bhf.b);
   }
}
