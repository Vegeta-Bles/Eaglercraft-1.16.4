import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class cjq implements cma {
   public static final Codec<cjq> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               ceh.b.fieldOf("valid_base_block").forGetter(var0x -> var0x.f),
               ceh.b.fieldOf("stem_state").forGetter(var0x -> var0x.g),
               ceh.b.fieldOf("hat_state").forGetter(var0x -> var0x.h),
               ceh.b.fieldOf("decor_state").forGetter(var0x -> var0x.i),
               Codec.BOOL.fieldOf("planted").orElse(false).forGetter(var0x -> var0x.j)
            )
            .apply(var0, cjq::new)
   );
   public static final cjq b = new cjq(bup.mu.n(), bup.mq.n(), bup.iK.n(), bup.mw.n(), true);
   public static final cjq c;
   public static final cjq d = new cjq(bup.ml.n(), bup.mh.n(), bup.mn.n(), bup.mw.n(), true);
   public static final cjq e;
   public final ceh f;
   public final ceh g;
   public final ceh h;
   public final ceh i;
   public final boolean j;

   public cjq(ceh var1, ceh var2, ceh var3, ceh var4, boolean var5) {
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
   }

   static {
      c = new cjq(b.f, b.g, b.h, b.i, false);
      e = new cjq(d.f, d.g, d.h, d.i, false);
   }
}
