import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class aix extends DataFix {
   public aix(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   private Dynamic<?> a(Dynamic<?> var1) {
      Optional<? extends Dynamic<?>> _snowman = _snowman.get("display").result();
      if (_snowman.isPresent()) {
         Dynamic<?> _snowmanx = (Dynamic<?>)_snowman.get();
         Optional<String> _snowmanxx = _snowmanx.get("Name").asString().result();
         if (_snowmanxx.isPresent()) {
            _snowmanx = _snowmanx.set("Name", _snowmanx.createString(nr.a.a(new oe(_snowmanxx.get()))));
         } else {
            Optional<String> _snowmanxxx = _snowmanx.get("LocName").asString().result();
            if (_snowmanxxx.isPresent()) {
               _snowmanx = _snowmanx.set("Name", _snowmanx.createString(nr.a.a(new of(_snowmanxxx.get()))));
               _snowmanx = _snowmanx.remove("LocName");
            }
         }

         return _snowman.set("display", _snowmanx);
      } else {
         return _snowman;
      }
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.l);
      OpticFinder<?> _snowmanx = _snowman.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemCustomNameToComponentFix", _snowman, var2x -> var2x.updateTyped(_snowman, var1x -> var1x.update(DSL.remainderFinder(), this::a))
      );
   }
}
