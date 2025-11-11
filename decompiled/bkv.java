import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.Set;

public class bkv extends bni implements bno {
   private final Set<buo> a;
   protected final float b;
   private final float c;
   private final Multimap<arg, arj> d;

   protected bkv(float var1, float var2, bnh var3, Set<buo> var4, blx.a var5) {
      super(_snowman, _snowman);
      this.a = _snowman;
      this.b = _snowman.b();
      this.c = _snowman + _snowman.c();
      Builder<arg, arj> _snowman = ImmutableMultimap.builder();
      _snowman.put(arl.f, new arj(f, "Tool modifier", (double)this.c, arj.a.a));
      _snowman.put(arl.h, new arj(g, "Tool modifier", (double)_snowman, arj.a.a));
      this.d = _snowman.build();
   }

   @Override
   public float a(bmb var1, ceh var2) {
      return this.a.contains(_snowman.b()) ? this.b : 1.0F;
   }

   @Override
   public boolean a(bmb var1, aqm var2, aqm var3) {
      _snowman.a(2, _snowman, var0 -> var0.c(aqf.a));
      return true;
   }

   @Override
   public boolean a(bmb var1, brx var2, ceh var3, fx var4, aqm var5) {
      if (!_snowman.v && _snowman.h(_snowman, _snowman) != 0.0F) {
         _snowman.a(1, _snowman, var0 -> var0.c(aqf.a));
      }

      return true;
   }

   @Override
   public Multimap<arg, arj> a(aqf var1) {
      return _snowman == aqf.a ? this.d : super.a(_snowman);
   }

   public float d() {
      return this.c;
   }
}
