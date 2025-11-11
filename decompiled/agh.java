import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Map;

public class agh extends DataFix {
   private static final Map<String, String> a = ImmutableMap.builder()
      .put("generic.maxHealth", "generic.max_health")
      .put("Max Health", "generic.max_health")
      .put("zombie.spawnReinforcements", "zombie.spawn_reinforcements")
      .put("Spawn Reinforcements Chance", "zombie.spawn_reinforcements")
      .put("horse.jumpStrength", "horse.jump_strength")
      .put("Jump Strength", "horse.jump_strength")
      .put("generic.followRange", "generic.follow_range")
      .put("Follow Range", "generic.follow_range")
      .put("generic.knockbackResistance", "generic.knockback_resistance")
      .put("Knockback Resistance", "generic.knockback_resistance")
      .put("generic.movementSpeed", "generic.movement_speed")
      .put("Movement Speed", "generic.movement_speed")
      .put("generic.flyingSpeed", "generic.flying_speed")
      .put("Flying Speed", "generic.flying_speed")
      .put("generic.attackDamage", "generic.attack_damage")
      .put("generic.attackKnockback", "generic.attack_knockback")
      .put("generic.attackSpeed", "generic.attack_speed")
      .put("generic.armorToughness", "generic.armor_toughness")
      .build();

   public agh(Schema var1) {
      super(_snowman, false);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.l);
      OpticFinder<?> _snowmanx = _snowman.findField("tag");
      return TypeRewriteRule.seq(
         this.fixTypeEverywhereTyped("Rename ItemStack Attributes", _snowman, var1x -> var1x.updateTyped(_snowman, agh::a)),
         new TypeRewriteRule[]{
            this.fixTypeEverywhereTyped("Rename Entity Attributes", this.getInputSchema().getType(akn.p), agh::b),
            this.fixTypeEverywhereTyped("Rename Player Attributes", this.getInputSchema().getType(akn.b), agh::b)
         }
      );
   }

   private static Dynamic<?> a(Dynamic<?> var0) {
      return (Dynamic<?>)DataFixUtils.orElse(_snowman.asString().result().map(var0x -> a.getOrDefault(var0x, var0x)).map(_snowman::createString), _snowman);
   }

   private static Typed<?> a(Typed<?> var0) {
      return _snowman.update(
         DSL.remainderFinder(),
         var0x -> var0x.update(
               "AttributeModifiers",
               var0xx -> (Dynamic)DataFixUtils.orElse(
                     var0xx.asStreamOpt().result().map(var0xxx -> var0xxx.map(var0xxxx -> var0xxxx.update("AttributeName", agh::a))).map(var0xx::createList),
                     var0xx
                  )
            )
      );
   }

   private static Typed<?> b(Typed<?> var0) {
      return _snowman.update(
         DSL.remainderFinder(),
         var0x -> var0x.update(
               "Attributes",
               var0xx -> (Dynamic)DataFixUtils.orElse(
                     var0xx.asStreamOpt().result().map(var0xxx -> var0xxx.map(var0xxxx -> var0xxxx.update("Name", agh::a))).map(var0xx::createList), var0xx
                  )
            )
      );
   }
}
