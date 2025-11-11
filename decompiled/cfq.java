import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.Set;
import java.util.stream.Stream;

public class cfq {
   private static final Set<cfq> i = new ObjectArraySet();
   public static final cfq a = a(new cfq("oak"));
   public static final cfq b = a(new cfq("spruce"));
   public static final cfq c = a(new cfq("birch"));
   public static final cfq d = a(new cfq("acacia"));
   public static final cfq e = a(new cfq("jungle"));
   public static final cfq f = a(new cfq("dark_oak"));
   public static final cfq g = a(new cfq("crimson"));
   public static final cfq h = a(new cfq("warped"));
   private final String j;

   protected cfq(String var1) {
      this.j = _snowman;
   }

   private static cfq a(cfq var0) {
      i.add(_snowman);
      return _snowman;
   }

   public static Stream<cfq> a() {
      return i.stream();
   }

   public String b() {
      return this.j;
   }
}
