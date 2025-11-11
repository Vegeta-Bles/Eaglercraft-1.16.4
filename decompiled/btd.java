import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;

public class btd extends bsy {
   public static final Codec<btd> e = bsv.d.fieldOf("biome").xmap(btd::new, var0 -> var0.f).stable().codec();
   private final Supplier<bsv> f;

   public btd(bsv var1) {
      this(() -> _snowman);
   }

   public btd(Supplier<bsv> var1) {
      super(ImmutableList.of(_snowman.get()));
      this.f = _snowman;
   }

   @Override
   protected Codec<? extends bsy> a() {
      return e;
   }

   @Override
   public bsy a(long var1) {
      return this;
   }

   @Override
   public bsv b(int var1, int var2, int var3) {
      return this.f.get();
   }

   @Nullable
   @Override
   public fx a(int var1, int var2, int var3, int var4, int var5, Predicate<bsv> var6, Random var7, boolean var8) {
      if (_snowman.test(this.f.get())) {
         return _snowman ? new fx(_snowman, _snowman, _snowman) : new fx(_snowman - _snowman + _snowman.nextInt(_snowman * 2 + 1), _snowman, _snowman - _snowman + _snowman.nextInt(_snowman * 2 + 1));
      } else {
         return null;
      }
   }

   @Override
   public Set<bsv> a(int var1, int var2, int var3, int var4) {
      return Sets.newHashSet(new bsv[]{this.f.get()});
   }
}
