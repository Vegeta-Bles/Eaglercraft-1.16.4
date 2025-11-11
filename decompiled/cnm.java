import com.mojang.serialization.Codec;

public class cnm<P extends cnl> {
   public static final cnm<cnh> a = a("blob_foliage_placer", cnh.a);
   public static final cnm<cnq> b = a("spruce_foliage_placer", cnq.a);
   public static final cnm<cnp> c = a("pine_foliage_placer", cnp.a);
   public static final cnm<cng> d = a("acacia_foliage_placer", cng.a);
   public static final cnm<cni> e = a("bush_foliage_placer", cni.c);
   public static final cnm<cnk> f = a("fancy_foliage_placer", cnk.c);
   public static final cnm<cnn> g = a("jungle_foliage_placer", cnn.a);
   public static final cnm<cno> h = a("mega_pine_foliage_placer", cno.a);
   public static final cnm<cnj> i = a("dark_oak_foliage_placer", cnj.a);
   private final Codec<P> j;

   private static <P extends cnl> cnm<P> a(String var0, Codec<P> var1) {
      return gm.a(gm.aX, _snowman, new cnm<>(_snowman));
   }

   private cnm(Codec<P> var1) {
      this.j = _snowman;
   }

   public Codec<P> a() {
      return this.j;
   }
}
