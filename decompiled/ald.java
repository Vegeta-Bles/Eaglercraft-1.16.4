import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class ald extends ajv {
   public ald(Schema var1, String var2) {
      super(_snowman, false, "Villager profession data fix (" + _snowman + ")", akn.p, _snowman);
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      Dynamic<?> _snowman = (Dynamic<?>)_snowman.get(DSL.remainderFinder());
      return _snowman.set(
         DSL.remainderFinder(),
         _snowman.remove("Profession")
            .remove("Career")
            .remove("CareerLevel")
            .set(
               "VillagerData",
               _snowman.createMap(
                  ImmutableMap.of(
                     _snowman.createString("type"),
                     _snowman.createString("minecraft:plains"),
                     _snowman.createString("profession"),
                     _snowman.createString(a(_snowman.get("Profession").asInt(0), _snowman.get("Career").asInt(0))),
                     _snowman.createString("level"),
                     DataFixUtils.orElse(_snowman.get("CareerLevel").result(), _snowman.createInt(1))
                  )
               )
            )
      );
   }

   private static String a(int var0, int var1) {
      if (_snowman == 0) {
         if (_snowman == 2) {
            return "minecraft:fisherman";
         } else if (_snowman == 3) {
            return "minecraft:shepherd";
         } else {
            return _snowman == 4 ? "minecraft:fletcher" : "minecraft:farmer";
         }
      } else if (_snowman == 1) {
         return _snowman == 2 ? "minecraft:cartographer" : "minecraft:librarian";
      } else if (_snowman == 2) {
         return "minecraft:cleric";
      } else if (_snowman == 3) {
         if (_snowman == 2) {
            return "minecraft:weaponsmith";
         } else {
            return _snowman == 3 ? "minecraft:toolsmith" : "minecraft:armorer";
         }
      } else if (_snowman == 4) {
         return _snowman == 2 ? "minecraft:leatherworker" : "minecraft:butcher";
      } else {
         return _snowman == 5 ? "minecraft:nitwit" : "minecraft:none";
      }
   }
}
