import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class aiu extends DataFix {
   public aiu(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.c);
      OpticFinder<?> _snowmanx = _snowman.findField("Level");
      return this.fixTypeEverywhereTyped("HeightmapRenamingFix", _snowman, var2x -> var2x.updateTyped(_snowman, var1x -> var1x.update(DSL.remainderFinder(), this::a)));
   }

   private Dynamic<?> a(Dynamic<?> var1) {
      Optional<? extends Dynamic<?>> _snowman = _snowman.get("Heightmaps").result();
      if (!_snowman.isPresent()) {
         return _snowman;
      } else {
         Dynamic<?> _snowmanx = (Dynamic<?>)_snowman.get();
         Optional<? extends Dynamic<?>> _snowmanxx = _snowmanx.get("LIQUID").result();
         if (_snowmanxx.isPresent()) {
            _snowmanx = _snowmanx.remove("LIQUID");
            _snowmanx = _snowmanx.set("WORLD_SURFACE_WG", _snowmanxx.get());
         }

         Optional<? extends Dynamic<?>> _snowmanxxx = _snowmanx.get("SOLID").result();
         if (_snowmanxxx.isPresent()) {
            _snowmanx = _snowmanx.remove("SOLID");
            _snowmanx = _snowmanx.set("OCEAN_FLOOR_WG", _snowmanxxx.get());
            _snowmanx = _snowmanx.set("OCEAN_FLOOR", _snowmanxxx.get());
         }

         Optional<? extends Dynamic<?>> _snowmanxxxx = _snowmanx.get("LIGHT").result();
         if (_snowmanxxxx.isPresent()) {
            _snowmanx = _snowmanx.remove("LIGHT");
            _snowmanx = _snowmanx.set("LIGHT_BLOCKING", _snowmanxxxx.get());
         }

         Optional<? extends Dynamic<?>> _snowmanxxxxx = _snowmanx.get("RAIN").result();
         if (_snowmanxxxxx.isPresent()) {
            _snowmanx = _snowmanx.remove("RAIN");
            _snowmanx = _snowmanx.set("MOTION_BLOCKING", _snowmanxxxxx.get());
            _snowmanx = _snowmanx.set("MOTION_BLOCKING_NO_LEAVES", _snowmanxxxxx.get());
         }

         return _snowman.set("Heightmaps", _snowmanx);
      }
   }
}
