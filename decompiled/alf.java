import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class alf extends DataFix {
   private static final int[] a = new int[]{0, 10, 50, 100, 150};

   public static int a(int var0) {
      return a[afm.a(_snowman - 1, 0, a.length - 1)];
   }

   public alf(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getChoiceType(akn.p, "minecraft:villager");
      OpticFinder<?> _snowmanx = DSL.namedChoice("minecraft:villager", _snowman);
      OpticFinder<?> _snowmanxx = _snowman.findField("Offers");
      Type<?> _snowmanxxx = _snowmanxx.type();
      OpticFinder<?> _snowmanxxxx = _snowmanxxx.findField("Recipes");
      ListType<?> _snowmanxxxxx = (ListType<?>)_snowmanxxxx.type();
      OpticFinder<?> _snowmanxxxxxx = _snowmanxxxxx.getElement().finder();
      return this.fixTypeEverywhereTyped("Villager level and xp rebuild", this.getInputSchema().getType(akn.p), var5x -> var5x.updateTyped(_snowman, _snowman, var3x -> {
            Dynamic<?> _snowmanxxxxxxx = (Dynamic<?>)var3x.get(DSL.remainderFinder());
            int _snowmanx = _snowmanxxxxxxx.get("VillagerData").get("level").asInt(0);
            Typed<?> _snowmanxx = var3x;
            if (_snowmanx == 0 || _snowmanx == 1) {
               int _snowmanxxx = var3x.getOptionalTyped(_snowman).flatMap(var1x -> var1x.getOptionalTyped(_snowman)).map(var1x -> var1x.getAllTyped(_snowman).size()).orElse(0);
               _snowmanx = afm.a(_snowmanxxx / 2, 1, 5);
               if (_snowmanx > 1) {
                  _snowmanxx = a(var3x, _snowmanx);
               }
            }

            Optional<Number> _snowmanxxx = _snowmanxxxxxxx.get("Xp").asNumber().result();
            if (!_snowmanxxx.isPresent()) {
               _snowmanxx = b(_snowmanxx, _snowmanx);
            }

            return _snowmanxx;
         }));
   }

   private static Typed<?> a(Typed<?> var0, int var1) {
      return _snowman.update(DSL.remainderFinder(), var1x -> var1x.update("VillagerData", var1xx -> var1xx.set("level", var1xx.createInt(_snowman))));
   }

   private static Typed<?> b(Typed<?> var0, int var1) {
      int _snowman = a(_snowman);
      return _snowman.update(DSL.remainderFinder(), var1x -> var1x.set("Xp", var1x.createInt(_snowman)));
   }
}
