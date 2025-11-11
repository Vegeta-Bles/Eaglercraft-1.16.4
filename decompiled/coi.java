import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public abstract class coi {
   public static final Codec<coi> e = gm.be.dispatch("element_type", coi::a, coj::codec);
   @Nullable
   private volatile cok.a a;

   protected static <E extends coi> RecordCodecBuilder<E, cok.a> d() {
      return cok.a.c.fieldOf("projection").forGetter(coi::e);
   }

   protected coi(cok.a var1) {
      this.a = _snowman;
   }

   public abstract List<ctb.c> a(csw var1, fx var2, bzm var3, Random var4);

   public abstract cra a(csw var1, fx var2, bzm var3);

   public abstract boolean a(csw var1, bsr var2, bsn var3, cfy var4, fx var5, fx var6, bzm var7, cra var8, Random var9, boolean var10);

   public abstract coj<?> a();

   public void a(bry var1, ctb.c var2, fx var3, bzm var4, Random var5, cra var6) {
   }

   public coi a(cok.a var1) {
      this.a = _snowman;
      return this;
   }

   public cok.a e() {
      cok.a _snowman = this.a;
      if (_snowman == null) {
         throw new IllegalStateException();
      } else {
         return _snowman;
      }
   }

   public int f() {
      return 1;
   }

   public static Function<cok.a, cob> g() {
      return var0 -> cob.b;
   }

   public static Function<cok.a, cof> a(String var0) {
      return var1 -> new cof(Either.left(new vk(_snowman)), () -> kl.a, var1);
   }

   public static Function<cok.a, cof> a(String var0, csz var1) {
      return var2 -> new cof(Either.left(new vk(_snowman)), () -> _snowman, var2);
   }

   public static Function<cok.a, coh> b(String var0) {
      return var1 -> new coh(Either.left(new vk(_snowman)), () -> kl.a, var1);
   }

   public static Function<cok.a, coh> b(String var0, csz var1) {
      return var2 -> new coh(Either.left(new vk(_snowman)), () -> _snowman, var2);
   }

   public static Function<cok.a, coc> a(civ<?, ?> var0) {
      return var1 -> new coc(() -> _snowman, var1);
   }

   public static Function<cok.a, cog> a(List<Function<cok.a, ? extends coi>> var0) {
      return var1 -> new cog(_snowman.stream().map(var1x -> var1x.apply(var1)).collect(Collectors.toList()), var1);
   }
}
