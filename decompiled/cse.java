import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import javax.annotation.Nullable;

public class cse extends csy {
   public static final Codec<cse> a = ceh.b.xmap(ceg.a::b, buo::n).listOf().fieldOf("blocks").xmap(cse::new, var0 -> var0.e).codec();
   public static final cse b = new cse(ImmutableList.of(bup.mY));
   public static final cse c = new cse(ImmutableList.of(bup.a));
   public static final cse d = new cse(ImmutableList.of(bup.a, bup.mY));
   private final ImmutableList<buo> e;

   public cse(List<buo> var1) {
      this.e = ImmutableList.copyOf(_snowman);
   }

   @Nullable
   @Override
   public ctb.c a(brz var1, fx var2, fx var3, ctb.c var4, ctb.c var5, csx var6) {
      return this.e.contains(_snowman.b.b()) ? null : _snowman;
   }

   @Override
   protected cta<?> a() {
      return cta.a;
   }
}
