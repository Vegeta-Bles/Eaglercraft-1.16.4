import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;

public class bnf extends bni implements bno {
   private final float a;
   private final Multimap<arg, arj> b;

   public bnf(bnh var1, int var2, float var3, blx.a var4) {
      super(_snowman, _snowman);
      this.a = (float)_snowman + _snowman.c();
      Builder<arg, arj> _snowman = ImmutableMultimap.builder();
      _snowman.put(arl.f, new arj(f, "Weapon modifier", (double)this.a, arj.a.a));
      _snowman.put(arl.h, new arj(g, "Weapon modifier", (double)_snowman, arj.a.a));
      this.b = _snowman.build();
   }

   public float f() {
      return this.a;
   }

   @Override
   public boolean a(ceh var1, brx var2, fx var3, bfw var4) {
      return !_snowman.b_();
   }

   @Override
   public float a(bmb var1, ceh var2) {
      if (_snowman.a(bup.aQ)) {
         return 15.0F;
      } else {
         cva _snowman = _snowman.c();
         return _snowman != cva.e && _snowman != cva.g && _snowman != cva.O && !_snowman.a(aed.I) && _snowman != cva.P ? 1.0F : 1.5F;
      }
   }

   @Override
   public boolean a(bmb var1, aqm var2, aqm var3) {
      _snowman.a(1, _snowman, var0 -> var0.c(aqf.a));
      return true;
   }

   @Override
   public boolean a(bmb var1, brx var2, ceh var3, fx var4, aqm var5) {
      if (_snowman.h(_snowman, _snowman) != 0.0F) {
         _snowman.a(2, _snowman, var0 -> var0.c(aqf.a));
      }

      return true;
   }

   @Override
   public boolean b(ceh var1) {
      return _snowman.a(bup.aQ);
   }

   @Override
   public Multimap<arg, arj> a(aqf var1) {
      return _snowman == aqf.a ? this.b : super.a(_snowman);
   }
}
