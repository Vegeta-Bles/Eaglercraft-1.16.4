package net.minecraft.datafixer.fix;

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
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.math.MathHelper;

public class VillagerXpRebuildFix extends DataFix {
   private static final int[] LEVEL_TO_XP = new int[]{0, 10, 50, 100, 150};

   public static int levelToXp(int level) {
      return LEVEL_TO_XP[MathHelper.clamp(level - 1, 0, LEVEL_TO_XP.length - 1)];
   }

   public VillagerXpRebuildFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getChoiceType(TypeReferences.ENTITY, "minecraft:villager");
      OpticFinder<?> _snowmanx = DSL.namedChoice("minecraft:villager", _snowman);
      OpticFinder<?> _snowmanxx = _snowman.findField("Offers");
      Type<?> _snowmanxxx = _snowmanxx.type();
      OpticFinder<?> _snowmanxxxx = _snowmanxxx.findField("Recipes");
      ListType<?> _snowmanxxxxx = (ListType<?>)_snowmanxxxx.type();
      OpticFinder<?> _snowmanxxxxxx = _snowmanxxxxx.getElement().finder();
      return this.fixTypeEverywhereTyped(
         "Villager level and xp rebuild",
         this.getInputSchema().getType(TypeReferences.ENTITY),
         _snowmanxxxxxxx -> _snowmanxxxxxxx.updateTyped(
               _snowman,
               _snowman,
               _snowmanxxxxxxxxxxxx -> {
                  Dynamic<?> _snowmanxxxx = (Dynamic<?>)_snowmanxxxxxxxxxxxx.get(DSL.remainderFinder());
                  int _snowmanxxxxx = _snowmanxxxx.get("VillagerData").get("level").asInt(0);
                  Typed<?> _snowmanxxxxxx = _snowmanxxxxxxxxxxxx;
                  if (_snowmanxxxxx == 0 || _snowmanxxxxx == 1) {
                     int _snowmanxxxxxxx = _snowmanxxxxxxxxxxxx.getOptionalTyped(_snowman)
                        .flatMap(_snowmanxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxx.getOptionalTyped(_snowman))
                        .map(_snowmanxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxx.getAllTyped(_snowman).size())
                        .orElse(0);
                     _snowmanxxxxx = MathHelper.clamp(_snowmanxxxxxxx / 2, 1, 5);
                     if (_snowmanxxxxx > 1) {
                        _snowmanxxxxxx = method_20487(_snowmanxxxxxxxxxxxx, _snowmanxxxxx);
                     }
                  }

                  Optional<Number> _snowmanxxxxxxxx = _snowmanxxxx.get("Xp").asNumber().result();
                  if (!_snowmanxxxxxxxx.isPresent()) {
                     _snowmanxxxxxx = method_20490(_snowmanxxxxxx, _snowmanxxxxx);
                  }

                  return _snowmanxxxxxx;
               }
            )
      );
   }

   private static Typed<?> method_20487(Typed<?> _snowman, int _snowman) {
      return _snowman.update(DSL.remainderFinder(), _snowmanxxx -> _snowmanxxx.update("VillagerData", _snowmanxxxxx -> _snowmanxxxxx.set("level", _snowmanxxxxx.createInt(_snowman))));
   }

   private static Typed<?> method_20490(Typed<?> _snowman, int _snowman) {
      int _snowmanxx = levelToXp(_snowman);
      return _snowman.update(DSL.remainderFinder(), _snowmanxxx -> _snowmanxxx.set("Xp", _snowmanxxx.createInt(_snowman)));
   }
}
