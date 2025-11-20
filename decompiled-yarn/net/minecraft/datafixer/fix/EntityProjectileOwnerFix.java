package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import java.util.Arrays;
import java.util.function.Function;
import net.minecraft.datafixer.TypeReferences;

public class EntityProjectileOwnerFix extends DataFix {
   public EntityProjectileOwnerFix(Schema outputSchema) {
      super(outputSchema, false);
   }

   protected TypeRewriteRule makeRule() {
      Schema _snowman = this.getInputSchema();
      return this.fixTypeEverywhereTyped("EntityProjectileOwner", _snowman.getType(TypeReferences.ENTITY), this::fixEntities);
   }

   private Typed<?> fixEntities(Typed<?> _snowman) {
      _snowman = this.update(_snowman, "minecraft:egg", this::moveOwnerToArray);
      _snowman = this.update(_snowman, "minecraft:ender_pearl", this::moveOwnerToArray);
      _snowman = this.update(_snowman, "minecraft:experience_bottle", this::moveOwnerToArray);
      _snowman = this.update(_snowman, "minecraft:snowball", this::moveOwnerToArray);
      _snowman = this.update(_snowman, "minecraft:potion", this::moveOwnerToArray);
      _snowman = this.update(_snowman, "minecraft:potion", this::renamePotionToItem);
      _snowman = this.update(_snowman, "minecraft:llama_spit", this::moveNestedOwnerMostLeastToArray);
      _snowman = this.update(_snowman, "minecraft:arrow", this::moveFlatOwnerMostLeastToArray);
      _snowman = this.update(_snowman, "minecraft:spectral_arrow", this::moveFlatOwnerMostLeastToArray);
      return this.update(_snowman, "minecraft:trident", this::moveFlatOwnerMostLeastToArray);
   }

   private Dynamic<?> moveFlatOwnerMostLeastToArray(Dynamic<?> _snowman) {
      long _snowmanx = _snowman.get("OwnerUUIDMost").asLong(0L);
      long _snowmanxx = _snowman.get("OwnerUUIDLeast").asLong(0L);
      return this.insertOwnerUuidArray(_snowman, _snowmanx, _snowmanxx).remove("OwnerUUIDMost").remove("OwnerUUIDLeast");
   }

   private Dynamic<?> moveNestedOwnerMostLeastToArray(Dynamic<?> _snowman) {
      OptionalDynamic<?> _snowmanx = _snowman.get("Owner");
      long _snowmanxx = _snowmanx.get("OwnerUUIDMost").asLong(0L);
      long _snowmanxxx = _snowmanx.get("OwnerUUIDLeast").asLong(0L);
      return this.insertOwnerUuidArray(_snowman, _snowmanxx, _snowmanxxx).remove("Owner");
   }

   private Dynamic<?> renamePotionToItem(Dynamic<?> _snowman) {
      OptionalDynamic<?> _snowmanx = _snowman.get("Potion");
      return _snowman.set("Item", _snowmanx.orElseEmptyMap()).remove("Potion");
   }

   private Dynamic<?> moveOwnerToArray(Dynamic<?> _snowman) {
      String _snowmanx = "owner";
      OptionalDynamic<?> _snowmanxx = _snowman.get("owner");
      long _snowmanxxx = _snowmanxx.get("M").asLong(0L);
      long _snowmanxxxx = _snowmanxx.get("L").asLong(0L);
      return this.insertOwnerUuidArray(_snowman, _snowmanxxx, _snowmanxxxx).remove("owner");
   }

   private Dynamic<?> insertOwnerUuidArray(Dynamic<?> _snowman, long most, long least) {
      String _snowmanx = "OwnerUUID";
      return most != 0L && least != 0L ? _snowman.set("OwnerUUID", _snowman.createIntList(Arrays.stream(makeUuidArray(most, least)))) : _snowman;
   }

   private static int[] makeUuidArray(long most, long least) {
      return new int[]{(int)(most >> 32), (int)most, (int)(least >> 32), (int)least};
   }

   private Typed<?> update(Typed<?> _snowman, String _snowman, Function<Dynamic<?>, Dynamic<?>> _snowman) {
      Type<?> _snowmanxxx = this.getInputSchema().getChoiceType(TypeReferences.ENTITY, _snowman);
      Type<?> _snowmanxxxx = this.getOutputSchema().getChoiceType(TypeReferences.ENTITY, _snowman);
      return _snowman.updateTyped(DSL.namedChoice(_snowman, _snowmanxxx), _snowmanxxxx, _snowmanxxxxx -> _snowmanxxxxx.update(DSL.remainderFinder(), _snowman));
   }
}
