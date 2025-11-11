import com.google.common.collect.ImmutableList;

public class dfk {
   public static final dfs a = new dfs(0, dfs.a.a, dfs.b.a, 3);
   public static final dfs b = new dfs(0, dfs.a.b, dfs.b.c, 4);
   public static final dfs c = new dfs(0, dfs.a.a, dfs.b.d, 2);
   public static final dfs d = new dfs(1, dfs.a.e, dfs.b.d, 2);
   public static final dfs e = new dfs(2, dfs.a.e, dfs.b.d, 2);
   public static final dfs f = new dfs(0, dfs.a.c, dfs.b.b, 3);
   public static final dfs g = new dfs(0, dfs.a.c, dfs.b.e, 1);
   public static final dfr h = new dfr(ImmutableList.builder().add(a).add(b).add(c).add(e).add(f).add(g).build());
   public static final dfr i = new dfr(ImmutableList.builder().add(a).add(b).add(c).add(d).add(e).add(f).add(g).build());
   @Deprecated
   public static final dfr j = new dfr(ImmutableList.builder().add(a).add(c).add(b).add(e).build());
   public static final dfr k = new dfr(ImmutableList.builder().add(a).build());
   public static final dfr l = new dfr(ImmutableList.builder().add(a).add(b).build());
   public static final dfr m = new dfr(ImmutableList.builder().add(a).add(b).add(e).build());
   public static final dfr n = new dfr(ImmutableList.builder().add(a).add(c).build());
   public static final dfr o = new dfr(ImmutableList.builder().add(a).add(b).add(c).build());
   @Deprecated
   public static final dfr p = new dfr(ImmutableList.builder().add(a).add(c).add(b).build());
   public static final dfr q = new dfr(ImmutableList.builder().add(a).add(b).add(c).add(e).build());
   @Deprecated
   public static final dfr r = new dfr(ImmutableList.builder().add(a).add(c).add(e).add(b).build());
   @Deprecated
   public static final dfr s = new dfr(ImmutableList.builder().add(a).add(c).add(b).add(f).add(g).build());
}
