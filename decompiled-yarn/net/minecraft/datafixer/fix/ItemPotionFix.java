package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class ItemPotionFix extends DataFix {
   private static final String[] ID_TO_POTIONS = (String[])DataFixUtils.make(new String[128], _snowman -> {
      _snowman[0] = "minecraft:water";
      _snowman[1] = "minecraft:regeneration";
      _snowman[2] = "minecraft:swiftness";
      _snowman[3] = "minecraft:fire_resistance";
      _snowman[4] = "minecraft:poison";
      _snowman[5] = "minecraft:healing";
      _snowman[6] = "minecraft:night_vision";
      _snowman[7] = null;
      _snowman[8] = "minecraft:weakness";
      _snowman[9] = "minecraft:strength";
      _snowman[10] = "minecraft:slowness";
      _snowman[11] = "minecraft:leaping";
      _snowman[12] = "minecraft:harming";
      _snowman[13] = "minecraft:water_breathing";
      _snowman[14] = "minecraft:invisibility";
      _snowman[15] = null;
      _snowman[16] = "minecraft:awkward";
      _snowman[17] = "minecraft:regeneration";
      _snowman[18] = "minecraft:swiftness";
      _snowman[19] = "minecraft:fire_resistance";
      _snowman[20] = "minecraft:poison";
      _snowman[21] = "minecraft:healing";
      _snowman[22] = "minecraft:night_vision";
      _snowman[23] = null;
      _snowman[24] = "minecraft:weakness";
      _snowman[25] = "minecraft:strength";
      _snowman[26] = "minecraft:slowness";
      _snowman[27] = "minecraft:leaping";
      _snowman[28] = "minecraft:harming";
      _snowman[29] = "minecraft:water_breathing";
      _snowman[30] = "minecraft:invisibility";
      _snowman[31] = null;
      _snowman[32] = "minecraft:thick";
      _snowman[33] = "minecraft:strong_regeneration";
      _snowman[34] = "minecraft:strong_swiftness";
      _snowman[35] = "minecraft:fire_resistance";
      _snowman[36] = "minecraft:strong_poison";
      _snowman[37] = "minecraft:strong_healing";
      _snowman[38] = "minecraft:night_vision";
      _snowman[39] = null;
      _snowman[40] = "minecraft:weakness";
      _snowman[41] = "minecraft:strong_strength";
      _snowman[42] = "minecraft:slowness";
      _snowman[43] = "minecraft:strong_leaping";
      _snowman[44] = "minecraft:strong_harming";
      _snowman[45] = "minecraft:water_breathing";
      _snowman[46] = "minecraft:invisibility";
      _snowman[47] = null;
      _snowman[48] = null;
      _snowman[49] = "minecraft:strong_regeneration";
      _snowman[50] = "minecraft:strong_swiftness";
      _snowman[51] = "minecraft:fire_resistance";
      _snowman[52] = "minecraft:strong_poison";
      _snowman[53] = "minecraft:strong_healing";
      _snowman[54] = "minecraft:night_vision";
      _snowman[55] = null;
      _snowman[56] = "minecraft:weakness";
      _snowman[57] = "minecraft:strong_strength";
      _snowman[58] = "minecraft:slowness";
      _snowman[59] = "minecraft:strong_leaping";
      _snowman[60] = "minecraft:strong_harming";
      _snowman[61] = "minecraft:water_breathing";
      _snowman[62] = "minecraft:invisibility";
      _snowman[63] = null;
      _snowman[64] = "minecraft:mundane";
      _snowman[65] = "minecraft:long_regeneration";
      _snowman[66] = "minecraft:long_swiftness";
      _snowman[67] = "minecraft:long_fire_resistance";
      _snowman[68] = "minecraft:long_poison";
      _snowman[69] = "minecraft:healing";
      _snowman[70] = "minecraft:long_night_vision";
      _snowman[71] = null;
      _snowman[72] = "minecraft:long_weakness";
      _snowman[73] = "minecraft:long_strength";
      _snowman[74] = "minecraft:long_slowness";
      _snowman[75] = "minecraft:long_leaping";
      _snowman[76] = "minecraft:harming";
      _snowman[77] = "minecraft:long_water_breathing";
      _snowman[78] = "minecraft:long_invisibility";
      _snowman[79] = null;
      _snowman[80] = "minecraft:awkward";
      _snowman[81] = "minecraft:long_regeneration";
      _snowman[82] = "minecraft:long_swiftness";
      _snowman[83] = "minecraft:long_fire_resistance";
      _snowman[84] = "minecraft:long_poison";
      _snowman[85] = "minecraft:healing";
      _snowman[86] = "minecraft:long_night_vision";
      _snowman[87] = null;
      _snowman[88] = "minecraft:long_weakness";
      _snowman[89] = "minecraft:long_strength";
      _snowman[90] = "minecraft:long_slowness";
      _snowman[91] = "minecraft:long_leaping";
      _snowman[92] = "minecraft:harming";
      _snowman[93] = "minecraft:long_water_breathing";
      _snowman[94] = "minecraft:long_invisibility";
      _snowman[95] = null;
      _snowman[96] = "minecraft:thick";
      _snowman[97] = "minecraft:regeneration";
      _snowman[98] = "minecraft:swiftness";
      _snowman[99] = "minecraft:long_fire_resistance";
      _snowman[100] = "minecraft:poison";
      _snowman[101] = "minecraft:strong_healing";
      _snowman[102] = "minecraft:long_night_vision";
      _snowman[103] = null;
      _snowman[104] = "minecraft:long_weakness";
      _snowman[105] = "minecraft:strength";
      _snowman[106] = "minecraft:long_slowness";
      _snowman[107] = "minecraft:leaping";
      _snowman[108] = "minecraft:strong_harming";
      _snowman[109] = "minecraft:long_water_breathing";
      _snowman[110] = "minecraft:long_invisibility";
      _snowman[111] = null;
      _snowman[112] = null;
      _snowman[113] = "minecraft:regeneration";
      _snowman[114] = "minecraft:swiftness";
      _snowman[115] = "minecraft:long_fire_resistance";
      _snowman[116] = "minecraft:poison";
      _snowman[117] = "minecraft:strong_healing";
      _snowman[118] = "minecraft:long_night_vision";
      _snowman[119] = null;
      _snowman[120] = "minecraft:long_weakness";
      _snowman[121] = "minecraft:strength";
      _snowman[122] = "minecraft:long_slowness";
      _snowman[123] = "minecraft:leaping";
      _snowman[124] = "minecraft:strong_harming";
      _snowman[125] = "minecraft:long_water_breathing";
      _snowman[126] = "minecraft:long_invisibility";
      _snowman[127] = null;
   });

   public ItemPotionFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
      OpticFinder<Pair<String, String>> _snowmanx = DSL.fieldFinder(
         "id", DSL.named(TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType())
      );
      OpticFinder<?> _snowmanxx = _snowman.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemPotionFix",
         _snowman,
         _snowmanxxx -> {
            Optional<Pair<String, String>> _snowmanxxxx = _snowmanxxx.getOptional(_snowman);
            if (_snowmanxxxx.isPresent() && Objects.equals(_snowmanxxxx.get().getSecond(), "minecraft:potion")) {
               Dynamic<?> _snowmanx = (Dynamic<?>)_snowmanxxx.get(DSL.remainderFinder());
               Optional<? extends Typed<?>> _snowmanxxx = _snowmanxxx.getOptionalTyped(_snowman);
               short _snowmanxxxx = _snowmanx.get("Damage").asShort((short)0);
               if (_snowmanxxx.isPresent()) {
                  Typed<?> _snowmanxxxxx = _snowmanxxx;
                  Dynamic<?> _snowmanxxxxxx = (Dynamic<?>)_snowmanxxx.get().get(DSL.remainderFinder());
                  Optional<String> _snowmanxxxxxxx = _snowmanxxxxxx.get("Potion").asString().result();
                  if (!_snowmanxxxxxxx.isPresent()) {
                     String _snowmanxxxxxxxx = ID_TO_POTIONS[_snowmanxxxx & 127];
                     Typed<?> _snowmanxxxxxxxxx = _snowmanxxx.get()
                        .set(DSL.remainderFinder(), _snowmanxxxxxx.set("Potion", _snowmanxxxxxx.createString(_snowmanxxxxxxxx == null ? "minecraft:water" : _snowmanxxxxxxxx)));
                     _snowmanxxxxx = _snowmanxxx.set(_snowman, _snowmanxxxxxxxxx);
                     if ((_snowmanxxxx & 16384) == 16384) {
                        _snowmanxxxxx = _snowmanxxxxx.set(_snowman, Pair.of(TypeReferences.ITEM_NAME.typeName(), "minecraft:splash_potion"));
                     }
                  }

                  if (_snowmanxxxx != 0) {
                     _snowmanx = _snowmanx.set("Damage", _snowmanx.createShort((short)0));
                  }

                  return _snowmanxxxxx.set(DSL.remainderFinder(), _snowmanx);
               }
            }

            return _snowmanxxx;
         }
      );
   }
}
