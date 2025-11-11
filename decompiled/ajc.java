import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;

public class ajc extends DataFix {
   public static final String[] a = new String[]{
      "minecraft:white_shulker_box",
      "minecraft:orange_shulker_box",
      "minecraft:magenta_shulker_box",
      "minecraft:light_blue_shulker_box",
      "minecraft:yellow_shulker_box",
      "minecraft:lime_shulker_box",
      "minecraft:pink_shulker_box",
      "minecraft:gray_shulker_box",
      "minecraft:silver_shulker_box",
      "minecraft:cyan_shulker_box",
      "minecraft:purple_shulker_box",
      "minecraft:blue_shulker_box",
      "minecraft:brown_shulker_box",
      "minecraft:green_shulker_box",
      "minecraft:red_shulker_box",
      "minecraft:black_shulker_box"
   };

   public ajc(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.l);
      OpticFinder<Pair<String, String>> _snowmanx = DSL.fieldFinder("id", DSL.named(akn.r.typeName(), aln.a()));
      OpticFinder<?> _snowmanxx = _snowman.findField("tag");
      OpticFinder<?> _snowmanxxx = _snowmanxx.type().findField("BlockEntityTag");
      return this.fixTypeEverywhereTyped("ItemShulkerBoxColorFix", _snowman, var3x -> {
         Optional<Pair<String, String>> _snowmanxxxx = var3x.getOptional(_snowman);
         if (_snowmanxxxx.isPresent() && Objects.equals(_snowmanxxxx.get().getSecond(), "minecraft:shulker_box")) {
            Optional<? extends Typed<?>> _snowmanx = var3x.getOptionalTyped(_snowman);
            if (_snowmanx.isPresent()) {
               Typed<?> _snowmanxx = (Typed<?>)_snowmanx.get();
               Optional<? extends Typed<?>> _snowmanxxx = _snowmanxx.getOptionalTyped(_snowman);
               if (_snowmanxxx.isPresent()) {
                  Typed<?> _snowmanxxxx = (Typed<?>)_snowmanxxx.get();
                  Dynamic<?> _snowmanxxxxx = (Dynamic<?>)_snowmanxxxx.get(DSL.remainderFinder());
                  int _snowmanxxxxxx = _snowmanxxxxx.get("Color").asInt(0);
                  _snowmanxxxxx.remove("Color");
                  return var3x.set(_snowman, _snowmanxx.set(_snowman, _snowmanxxxx.set(DSL.remainderFinder(), _snowmanxxxxx))).set(_snowman, Pair.of(akn.r.typeName(), a[_snowmanxxxxxx % 16]));
               }
            }
         }

         return var3x;
      });
   }
}
