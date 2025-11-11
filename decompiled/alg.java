import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.function.Function;

public class alg extends ajv {
   public alg(Schema var1, boolean var2) {
      super(_snowman, _snowman, "Villager trade fix", akn.p, "minecraft:villager");
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      OpticFinder<?> _snowman = _snowman.getType().findField("Offers");
      OpticFinder<?> _snowmanx = _snowman.type().findField("Recipes");
      Type<?> _snowmanxx = _snowmanx.type();
      if (!(_snowmanxx instanceof ListType)) {
         throw new IllegalStateException("Recipes are expected to be a list.");
      } else {
         ListType<?> _snowmanxxx = (ListType<?>)_snowmanxx;
         Type<?> _snowmanxxxx = _snowmanxxx.getElement();
         OpticFinder<?> _snowmanxxxxx = DSL.typeFinder(_snowmanxxxx);
         OpticFinder<?> _snowmanxxxxxx = _snowmanxxxx.findField("buy");
         OpticFinder<?> _snowmanxxxxxxx = _snowmanxxxx.findField("buyB");
         OpticFinder<?> _snowmanxxxxxxxx = _snowmanxxxx.findField("sell");
         OpticFinder<Pair<String, String>> _snowmanxxxxxxxxx = DSL.fieldFinder("id", DSL.named(akn.r.typeName(), aln.a()));
         Function<Typed<?>, Typed<?>> _snowmanxxxxxxxxxx = var2x -> this.a(_snowman, var2x);
         return _snowman.updateTyped(
            _snowman, var6x -> var6x.updateTyped(_snowman, var5x -> var5x.updateTyped(_snowman, var4x -> var4x.updateTyped(_snowman, _snowman).updateTyped(_snowman, _snowman).updateTyped(_snowman, _snowman)))
         );
      }
   }

   private Typed<?> a(OpticFinder<Pair<String, String>> var1, Typed<?> var2) {
      return _snowman.update(_snowman, var0 -> var0.mapSecond(var0x -> Objects.equals(var0x, "minecraft:carved_pumpkin") ? "minecraft:pumpkin" : var0x));
   }
}
