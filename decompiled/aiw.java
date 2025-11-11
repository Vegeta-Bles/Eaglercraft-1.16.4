import com.google.common.collect.ImmutableMap;
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
import java.util.stream.Stream;

public class aiw extends DataFix {
   public aiw(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.l);
      OpticFinder<Pair<String, String>> _snowmanx = DSL.fieldFinder("id", DSL.named(akn.r.typeName(), aln.a()));
      OpticFinder<?> _snowmanxx = _snowman.findField("tag");
      OpticFinder<?> _snowmanxxx = _snowmanxx.type().findField("BlockEntityTag");
      return this.fixTypeEverywhereTyped(
         "ItemBannerColorFix",
         _snowman,
         var3x -> {
            Optional<Pair<String, String>> _snowmanxxxx = var3x.getOptional(_snowman);
            if (_snowmanxxxx.isPresent() && Objects.equals(_snowmanxxxx.get().getSecond(), "minecraft:banner")) {
               Dynamic<?> _snowmanx = (Dynamic<?>)var3x.get(DSL.remainderFinder());
               Optional<? extends Typed<?>> _snowmanxx = var3x.getOptionalTyped(_snowman);
               if (_snowmanxx.isPresent()) {
                  Typed<?> _snowmanxxx = (Typed<?>)_snowmanxx.get();
                  Optional<? extends Typed<?>> _snowmanxxxx = _snowmanxxx.getOptionalTyped(_snowman);
                  if (_snowmanxxxx.isPresent()) {
                     Typed<?> _snowmanxxxxx = (Typed<?>)_snowmanxxxx.get();
                     Dynamic<?> _snowmanxxxxxx = (Dynamic<?>)_snowmanxxx.get(DSL.remainderFinder());
                     Dynamic<?> _snowmanxxxxxxx = (Dynamic<?>)_snowmanxxxxx.getOrCreate(DSL.remainderFinder());
                     if (_snowmanxxxxxxx.get("Base").asNumber().result().isPresent()) {
                        _snowmanx = _snowmanx.set("Damage", _snowmanx.createShort((short)(_snowmanxxxxxxx.get("Base").asInt(0) & 15)));
                        Optional<? extends Dynamic<?>> _snowmanxxxxxxxx = _snowmanxxxxxx.get("display").result();
                        if (_snowmanxxxxxxxx.isPresent()) {
                           Dynamic<?> _snowmanxxxxxxxxx = (Dynamic<?>)_snowmanxxxxxxxx.get();
                           Dynamic<?> _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.createMap(
                              ImmutableMap.of(_snowmanxxxxxxxxx.createString("Lore"), _snowmanxxxxxxxxx.createList(Stream.of(_snowmanxxxxxxxxx.createString("(+NBT"))))
                           );
                           if (Objects.equals(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx)) {
                              return var3x.set(DSL.remainderFinder(), _snowmanx);
                           }
                        }

                        _snowmanxxxxxxx.remove("Base");
                        return var3x.set(DSL.remainderFinder(), _snowmanx).set(_snowman, _snowmanxxx.set(_snowman, _snowmanxxxxx.set(DSL.remainderFinder(), _snowmanxxxxxxx)));
                     }
                  }
               }

               return var3x.set(DSL.remainderFinder(), _snowmanx);
            } else {
               return var3x;
            }
         }
      );
   }
}
