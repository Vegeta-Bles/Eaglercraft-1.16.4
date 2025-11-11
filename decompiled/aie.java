import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;

public abstract class aie extends DataFix {
   protected final String a;

   public aie(String var1, Schema var2, boolean var3) {
      super(_snowman, _snowman);
      this.a = _snowman;
   }

   public TypeRewriteRule makeRule() {
      TaggedChoiceType<String> _snowman = this.getInputSchema().findChoiceType(akn.p);
      TaggedChoiceType<String> _snowmanx = this.getOutputSchema().findChoiceType(akn.p);
      return this.fixTypeEverywhere(this.a, _snowman, _snowmanx, var3 -> var4 -> {
            String _snowmanxx = (String)var4.getFirst();
            Type<?> _snowmanx = (Type<?>)_snowman.types().get(_snowmanxx);
            Pair<String, Typed<?>> _snowmanxx = this.a(_snowmanxx, this.a(var4.getSecond(), var3, _snowmanx));
            Type<?> _snowmanxxx = (Type<?>)_snowman.types().get(_snowmanxx.getFirst());
            if (!_snowmanxxx.equals(((Typed)_snowmanxx.getSecond()).getType(), true, true)) {
               throw new IllegalStateException(String.format("Dynamic type check failed: %s not equal to %s", _snowmanxxx, ((Typed)_snowmanxx.getSecond()).getType()));
            } else {
               return Pair.of(_snowmanxx.getFirst(), ((Typed)_snowmanxx.getSecond()).getValue());
            }
         });
   }

   private <A> Typed<A> a(Object var1, DynamicOps<?> var2, Type<A> var3) {
      return new Typed(_snowman, _snowman, _snowman);
   }

   protected abstract Pair<String, Typed<?>> a(String var1, Typed<?> var2);
}
