import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;

public class ahy extends DataFix {
   private static final int[][] a = new int[][]{{0, 0, 1}, {-1, 0, 0}, {0, 0, -1}, {1, 0, 0}};

   public ahy(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   private Dynamic<?> a(Dynamic<?> var1, boolean var2, boolean var3) {
      if ((_snowman || _snowman) && !_snowman.get("Facing").asNumber().result().isPresent()) {
         int _snowman;
         if (_snowman.get("Direction").asNumber().result().isPresent()) {
            _snowman = _snowman.get("Direction").asByte((byte)0) % a.length;
            int[] _snowmanx = a[_snowman];
            _snowman = _snowman.set("TileX", _snowman.createInt(_snowman.get("TileX").asInt(0) + _snowmanx[0]));
            _snowman = _snowman.set("TileY", _snowman.createInt(_snowman.get("TileY").asInt(0) + _snowmanx[1]));
            _snowman = _snowman.set("TileZ", _snowman.createInt(_snowman.get("TileZ").asInt(0) + _snowmanx[2]));
            _snowman = _snowman.remove("Direction");
            if (_snowman && _snowman.get("ItemRotation").asNumber().result().isPresent()) {
               _snowman = _snowman.set("ItemRotation", _snowman.createByte((byte)(_snowman.get("ItemRotation").asByte((byte)0) * 2)));
            }
         } else {
            _snowman = _snowman.get("Dir").asByte((byte)0) % a.length;
            _snowman = _snowman.remove("Dir");
         }

         _snowman = _snowman.set("Facing", _snowman.createByte((byte)_snowman));
      }

      return _snowman;
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getChoiceType(akn.p, "Painting");
      OpticFinder<?> _snowmanx = DSL.namedChoice("Painting", _snowman);
      Type<?> _snowmanxx = this.getInputSchema().getChoiceType(akn.p, "ItemFrame");
      OpticFinder<?> _snowmanxxx = DSL.namedChoice("ItemFrame", _snowmanxx);
      Type<?> _snowmanxxxx = this.getInputSchema().getType(akn.p);
      TypeRewriteRule _snowmanxxxxx = this.fixTypeEverywhereTyped(
         "EntityPaintingFix", _snowmanxxxx, var3x -> var3x.updateTyped(_snowman, _snowman, var1x -> var1x.update(DSL.remainderFinder(), var1xx -> this.a(var1xx, true, false)))
      );
      TypeRewriteRule _snowmanxxxxxx = this.fixTypeEverywhereTyped(
         "EntityItemFrameFix", _snowmanxxxx, var3x -> var3x.updateTyped(_snowman, _snowman, var1x -> var1x.update(DSL.remainderFinder(), var1xx -> this.a(var1xx, false, true)))
      );
      return TypeRewriteRule.seq(_snowmanxxxxx, _snowmanxxxxxx);
   }
}
