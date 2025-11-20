package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class HangingEntityFix extends DataFix {
   private static final int[][] OFFSETS = new int[][]{{0, 0, 1}, {-1, 0, 0}, {0, 0, -1}, {1, 0, 0}};

   public HangingEntityFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   private Dynamic<?> fixDecorationPosition(Dynamic<?> _snowman, boolean isPainting, boolean isItemFrame) {
      if ((isPainting || isItemFrame) && !_snowman.get("Facing").asNumber().result().isPresent()) {
         int _snowmanx;
         if (_snowman.get("Direction").asNumber().result().isPresent()) {
            _snowmanx = _snowman.get("Direction").asByte((byte)0) % OFFSETS.length;
            int[] _snowmanxx = OFFSETS[_snowmanx];
            _snowman = _snowman.set("TileX", _snowman.createInt(_snowman.get("TileX").asInt(0) + _snowmanxx[0]));
            _snowman = _snowman.set("TileY", _snowman.createInt(_snowman.get("TileY").asInt(0) + _snowmanxx[1]));
            _snowman = _snowman.set("TileZ", _snowman.createInt(_snowman.get("TileZ").asInt(0) + _snowmanxx[2]));
            _snowman = _snowman.remove("Direction");
            if (isItemFrame && _snowman.get("ItemRotation").asNumber().result().isPresent()) {
               _snowman = _snowman.set("ItemRotation", _snowman.createByte((byte)(_snowman.get("ItemRotation").asByte((byte)0) * 2)));
            }
         } else {
            _snowmanx = _snowman.get("Dir").asByte((byte)0) % OFFSETS.length;
            _snowman = _snowman.remove("Dir");
         }

         _snowman = _snowman.set("Facing", _snowman.createByte((byte)_snowmanx));
      }

      return _snowman;
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getChoiceType(TypeReferences.ENTITY, "Painting");
      OpticFinder<?> _snowmanx = DSL.namedChoice("Painting", _snowman);
      Type<?> _snowmanxx = this.getInputSchema().getChoiceType(TypeReferences.ENTITY, "ItemFrame");
      OpticFinder<?> _snowmanxxx = DSL.namedChoice("ItemFrame", _snowmanxx);
      Type<?> _snowmanxxxx = this.getInputSchema().getType(TypeReferences.ENTITY);
      TypeRewriteRule _snowmanxxxxx = this.fixTypeEverywhereTyped(
         "EntityPaintingFix",
         _snowmanxxxx,
         _snowmanxxxxxx -> _snowmanxxxxxx.updateTyped(
               _snowman, _snowman, _snowmanxxxxxxx -> _snowmanxxxxxxx.update(DSL.remainderFinder(), _snowmanxxxxxxxx -> this.fixDecorationPosition(_snowmanxxxxxxxx, true, false))
            )
      );
      TypeRewriteRule _snowmanxxxxxx = this.fixTypeEverywhereTyped(
         "EntityItemFrameFix",
         _snowmanxxxx,
         _snowmanxxxxxxx -> _snowmanxxxxxxx.updateTyped(
               _snowman, _snowman, _snowmanxxxxxxxx -> _snowmanxxxxxxxx.update(DSL.remainderFinder(), _snowmanxxxxxxxxx -> this.fixDecorationPosition(_snowmanxxxxxxxxx, false, true))
            )
      );
      return TypeRewriteRule.seq(_snowmanxxxxx, _snowmanxxxxxx);
   }
}
