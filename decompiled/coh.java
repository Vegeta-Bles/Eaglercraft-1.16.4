import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class coh extends coi {
   private static final Codec<Either<vk, ctb>> a = Codec.of(coh::a, vk.a.map(Either::left));
   public static final Codec<coh> b = RecordCodecBuilder.create(var0 -> var0.group(c(), b(), d()).apply(var0, coh::new));
   protected final Either<vk, ctb> c;
   protected final Supplier<csz> d;

   private static <T> DataResult<T> a(Either<vk, ctb> var0, DynamicOps<T> var1, T var2) {
      Optional<vk> _snowman = _snowman.left();
      return !_snowman.isPresent() ? DataResult.error("Can not serialize a runtime pool element") : vk.a.encode(_snowman.get(), _snowman, _snowman);
   }

   protected static <E extends coh> RecordCodecBuilder<E, Supplier<csz>> b() {
      return cta.m.fieldOf("processors").forGetter(var0 -> var0.d);
   }

   protected static <E extends coh> RecordCodecBuilder<E, Either<vk, ctb>> c() {
      return a.fieldOf("location").forGetter(var0 -> var0.c);
   }

   protected coh(Either<vk, ctb> var1, Supplier<csz> var2, cok.a var3) {
      super(_snowman);
      this.c = _snowman;
      this.d = _snowman;
   }

   public coh(ctb var1) {
      this(Either.right(_snowman), () -> kl.a, cok.a.b);
   }

   private ctb a(csw var1) {
      return (ctb)this.c.map(_snowman::a, Function.identity());
   }

   public List<ctb.c> a(csw var1, fx var2, bzm var3, boolean var4) {
      ctb _snowman = this.a(_snowman);
      List<ctb.c> _snowmanx = _snowman.a(_snowman, new csx().a(_snowman), bup.mY, _snowman);
      List<ctb.c> _snowmanxx = Lists.newArrayList();

      for (ctb.c _snowmanxxx : _snowmanx) {
         if (_snowmanxxx.c != null) {
            cfo _snowmanxxxx = cfo.valueOf(_snowmanxxx.c.l("mode"));
            if (_snowmanxxxx == cfo.d) {
               _snowmanxx.add(_snowmanxxx);
            }
         }
      }

      return _snowmanxx;
   }

   @Override
   public List<ctb.c> a(csw var1, fx var2, bzm var3, Random var4) {
      ctb _snowman = this.a(_snowman);
      List<ctb.c> _snowmanx = _snowman.a(_snowman, new csx().a(_snowman), bup.mZ, true);
      Collections.shuffle(_snowmanx, _snowman);
      return _snowmanx;
   }

   @Override
   public cra a(csw var1, fx var2, bzm var3) {
      ctb _snowman = this.a(_snowman);
      return _snowman.b(new csx().a(_snowman), _snowman);
   }

   @Override
   public boolean a(csw var1, bsr var2, bsn var3, cfy var4, fx var5, fx var6, bzm var7, cra var8, Random var9, boolean var10) {
      ctb _snowman = this.a(_snowman);
      csx _snowmanx = this.a(_snowman, _snowman, _snowman);
      if (!_snowman.a(_snowman, _snowman, _snowman, _snowmanx, _snowman, 18)) {
         return false;
      } else {
         for (ctb.c _snowmanxx : ctb.a(_snowman, _snowman, _snowman, _snowmanx, this.a(_snowman, _snowman, _snowman, false))) {
            this.a(_snowman, _snowmanxx, _snowman, _snowman, _snowman, _snowman);
         }

         return true;
      }
   }

   protected csx a(bzm var1, cra var2, boolean var3) {
      csx _snowman = new csx();
      _snowman.a(_snowman);
      _snowman.a(_snowman);
      _snowman.c(true);
      _snowman.a(false);
      _snowman.a(cse.b);
      _snowman.d(true);
      if (!_snowman) {
         _snowman.a(csj.b);
      }

      this.d.get().a().forEach(_snowman::a);
      this.e().c().forEach(_snowman::a);
      return _snowman;
   }

   @Override
   public coj<?> a() {
      return coj.a;
   }

   @Override
   public String toString() {
      return "Single[" + this.c + "]";
   }
}
