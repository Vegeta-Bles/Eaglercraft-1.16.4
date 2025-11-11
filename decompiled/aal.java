import java.util.Comparator;

public class aal<T> {
   private final String i;
   private final Comparator<T> j;
   private final long k;
   public static final aal<afx> a = a("start", (var0, var1) -> 0);
   public static final aal<afx> b = a("dragon", (var0, var1) -> 0);
   public static final aal<brd> c = a("player", Comparator.comparingLong(brd::a));
   public static final aal<brd> d = a("forced", Comparator.comparingLong(brd::a));
   public static final aal<brd> e = a("light", Comparator.comparingLong(brd::a));
   public static final aal<fx> f = a("portal", gr::i, 300);
   public static final aal<Integer> g = a("post_teleport", Integer::compareTo, 5);
   public static final aal<brd> h = a("unknown", Comparator.comparingLong(brd::a), 1);

   public static <T> aal<T> a(String var0, Comparator<T> var1) {
      return new aal<>(_snowman, _snowman, 0L);
   }

   public static <T> aal<T> a(String var0, Comparator<T> var1, int var2) {
      return new aal<>(_snowman, _snowman, (long)_snowman);
   }

   protected aal(String var1, Comparator<T> var2, long var3) {
      this.i = _snowman;
      this.j = _snowman;
      this.k = _snowman;
   }

   @Override
   public String toString() {
      return this.i;
   }

   public Comparator<T> a() {
      return this.j;
   }

   public long b() {
      return this.k;
   }
}
