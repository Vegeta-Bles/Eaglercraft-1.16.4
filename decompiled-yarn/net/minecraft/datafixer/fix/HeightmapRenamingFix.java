package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;

public class HeightmapRenamingFix extends DataFix {
   public HeightmapRenamingFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.CHUNK);
      OpticFinder<?> _snowmanx = _snowman.findField("Level");
      return this.fixTypeEverywhereTyped(
         "HeightmapRenamingFix", _snowman, _snowmanxx -> _snowmanxx.updateTyped(_snowman, _snowmanxxx -> _snowmanxxx.update(DSL.remainderFinder(), this::renameHeightmapTags))
      );
   }

   private Dynamic<?> renameHeightmapTags(Dynamic<?> _snowman) {
      Optional<? extends Dynamic<?>> _snowmanx = _snowman.get("Heightmaps").result();
      if (!_snowmanx.isPresent()) {
         return _snowman;
      } else {
         Dynamic<?> _snowmanxx = (Dynamic<?>)_snowmanx.get();
         Optional<? extends Dynamic<?>> _snowmanxxx = _snowmanxx.get("LIQUID").result();
         if (_snowmanxxx.isPresent()) {
            _snowmanxx = _snowmanxx.remove("LIQUID");
            _snowmanxx = _snowmanxx.set("WORLD_SURFACE_WG", _snowmanxxx.get());
         }

         Optional<? extends Dynamic<?>> _snowmanxxxx = _snowmanxx.get("SOLID").result();
         if (_snowmanxxxx.isPresent()) {
            _snowmanxx = _snowmanxx.remove("SOLID");
            _snowmanxx = _snowmanxx.set("OCEAN_FLOOR_WG", _snowmanxxxx.get());
            _snowmanxx = _snowmanxx.set("OCEAN_FLOOR", _snowmanxxxx.get());
         }

         Optional<? extends Dynamic<?>> _snowmanxxxxx = _snowmanxx.get("LIGHT").result();
         if (_snowmanxxxxx.isPresent()) {
            _snowmanxx = _snowmanxx.remove("LIGHT");
            _snowmanxx = _snowmanxx.set("LIGHT_BLOCKING", _snowmanxxxxx.get());
         }

         Optional<? extends Dynamic<?>> _snowmanxxxxxx = _snowmanxx.get("RAIN").result();
         if (_snowmanxxxxxx.isPresent()) {
            _snowmanxx = _snowmanxx.remove("RAIN");
            _snowmanxx = _snowmanxx.set("MOTION_BLOCKING", _snowmanxxxxxx.get());
            _snowmanxx = _snowmanxx.set("MOTION_BLOCKING_NO_LEAVES", _snowmanxxxxxx.get());
         }

         return _snowman.set("Heightmaps", _snowmanxx);
      }
   }
}
