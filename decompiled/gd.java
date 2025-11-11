import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Set;

public enum gd {
   a(gc.c),
   b(gc.c, gc.f),
   c(gc.f),
   d(gc.d, gc.f),
   e(gc.d),
   f(gc.d, gc.e),
   g(gc.e),
   h(gc.c, gc.e);

   private static final int i = 1 << h.ordinal();
   private static final int j = 1 << g.ordinal();
   private static final int k = 1 << f.ordinal();
   private static final int l = 1 << e.ordinal();
   private static final int m = 1 << d.ordinal();
   private static final int n = 1 << c.ordinal();
   private static final int o = 1 << b.ordinal();
   private static final int p = 1 << a.ordinal();
   private final Set<gc> q;

   private gd(gc... var3) {
      this.q = Sets.immutableEnumSet(Arrays.asList(_snowman));
   }

   public Set<gc> a() {
      return this.q;
   }
}
