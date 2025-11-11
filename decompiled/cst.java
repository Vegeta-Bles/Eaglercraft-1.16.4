import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class cst extends csy {
   public static final Codec<cst> a = csq.a.listOf().fieldOf("rules").xmap(cst::new, var0 -> var0.b).codec();
   private final ImmutableList<csq> b;

   public cst(List<? extends csq> var1) {
      this.b = ImmutableList.copyOf(_snowman);
   }

   @Nullable
   @Override
   public ctb.c a(brz var1, fx var2, fx var3, ctb.c var4, ctb.c var5, csx var6) {
      Random _snowman = new Random(afm.a(_snowman.a));
      ceh _snowmanx = _snowman.d_(_snowman.a);
      UnmodifiableIterator var9 = this.b.iterator();

      while (var9.hasNext()) {
         csq _snowmanxx = (csq)var9.next();
         if (_snowmanxx.a(_snowman.b, _snowmanx, _snowman.a, _snowman.a, _snowman, _snowman)) {
            return new ctb.c(_snowman.a, _snowmanxx.a(), _snowmanxx.b());
         }
      }

      return _snowman;
   }

   @Override
   protected cta<?> a() {
      return cta.e;
   }
}
