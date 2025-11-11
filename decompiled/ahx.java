import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Objects;

public class ahx extends DataFix {
   private static final List<String> a = Lists.newArrayList(new String[]{"MinecartRideable", "MinecartChest", "MinecartFurnace"});

   public ahx(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      TaggedChoiceType<String> _snowman = this.getInputSchema().findChoiceType(akn.p);
      TaggedChoiceType<String> _snowmanx = this.getOutputSchema().findChoiceType(akn.p);
      return this.fixTypeEverywhere(
         "EntityMinecartIdentifiersFix",
         _snowman,
         _snowmanx,
         var2x -> var3 -> {
               if (!Objects.equals(var3.getFirst(), "Minecart")) {
                  return var3;
               } else {
                  Typed<? extends Pair<String, ?>> _snowmanxx = (Typed<? extends Pair<String, ?>>)_snowman.point(var2x, "Minecart", var3.getSecond())
                     .orElseThrow(IllegalStateException::new);
                  Dynamic<?> _snowmanx = (Dynamic<?>)_snowmanxx.getOrCreate(DSL.remainderFinder());
                  int _snowmanxx = _snowmanx.get("Type").asInt(0);
                  String _snowmanxxx;
                  if (_snowmanxx > 0 && _snowmanxx < a.size()) {
                     _snowmanxxx = a.get(_snowmanxx);
                  } else {
                     _snowmanxxx = "MinecartRideable";
                  }

                  return Pair.of(
                     _snowmanxxx,
                     _snowmanxx.write()
                        .map(var2xx -> ((Type)_snowman.types().get(_snowman)).read(var2xx))
                        .result()
                        .orElseThrow(() -> new IllegalStateException("Could not read the new minecart."))
                  );
               }
            }
      );
   }
}
