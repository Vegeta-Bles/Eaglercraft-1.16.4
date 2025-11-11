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

public class aka extends DataFix {
   public aka(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   private Dynamic<?> a(Dynamic<?> var1) {
      Optional<? extends Dynamic<?>> _snowman = _snowman.get("display").result();
      if (_snowman.isPresent()) {
         Dynamic<?> _snowmanx = (Dynamic<?>)_snowman.get();
         Optional<String> _snowmanxx = _snowmanx.get("Name").asString().result();
         if (_snowmanxx.isPresent()) {
            String _snowmanxxx = _snowmanxx.get();
            _snowmanxxx = _snowmanxxx.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
            _snowmanx = _snowmanx.set("Name", _snowmanx.createString(_snowmanxxx));
         }

         return _snowman.set("display", _snowmanx);
      } else {
         return _snowman;
      }
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.l);
      OpticFinder<Pair<String, String>> _snowmanx = DSL.fieldFinder("id", DSL.named(akn.r.typeName(), aln.a()));
      OpticFinder<?> _snowmanxx = _snowman.findField("tag");
      return this.fixTypeEverywhereTyped("OminousBannerRenameFix", _snowman, var3x -> {
         Optional<Pair<String, String>> _snowmanxxx = var3x.getOptional(_snowman);
         if (_snowmanxxx.isPresent() && Objects.equals(_snowmanxxx.get().getSecond(), "minecraft:white_banner")) {
            Optional<? extends Typed<?>> _snowmanx = var3x.getOptionalTyped(_snowman);
            if (_snowmanx.isPresent()) {
               Typed<?> _snowmanxx = (Typed<?>)_snowmanx.get();
               Dynamic<?> _snowmanxxx = (Dynamic<?>)_snowmanxx.get(DSL.remainderFinder());
               return var3x.set(_snowman, _snowmanxx.set(DSL.remainderFinder(), this.a(_snowmanxxx)));
            }
         }

         return var3x;
      });
   }
}
