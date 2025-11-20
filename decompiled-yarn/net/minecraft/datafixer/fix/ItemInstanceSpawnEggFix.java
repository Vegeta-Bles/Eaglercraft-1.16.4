package net.minecraft.datafixer.fix;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class ItemInstanceSpawnEggFix extends DataFix {
   private static final Map<String, String> ENTITY_SPAWN_EGGS = (Map<String, String>)DataFixUtils.make(Maps.newHashMap(), _snowman -> {
      _snowman.put("minecraft:bat", "minecraft:bat_spawn_egg");
      _snowman.put("minecraft:blaze", "minecraft:blaze_spawn_egg");
      _snowman.put("minecraft:cave_spider", "minecraft:cave_spider_spawn_egg");
      _snowman.put("minecraft:chicken", "minecraft:chicken_spawn_egg");
      _snowman.put("minecraft:cow", "minecraft:cow_spawn_egg");
      _snowman.put("minecraft:creeper", "minecraft:creeper_spawn_egg");
      _snowman.put("minecraft:donkey", "minecraft:donkey_spawn_egg");
      _snowman.put("minecraft:elder_guardian", "minecraft:elder_guardian_spawn_egg");
      _snowman.put("minecraft:enderman", "minecraft:enderman_spawn_egg");
      _snowman.put("minecraft:endermite", "minecraft:endermite_spawn_egg");
      _snowman.put("minecraft:evocation_illager", "minecraft:evocation_illager_spawn_egg");
      _snowman.put("minecraft:ghast", "minecraft:ghast_spawn_egg");
      _snowman.put("minecraft:guardian", "minecraft:guardian_spawn_egg");
      _snowman.put("minecraft:horse", "minecraft:horse_spawn_egg");
      _snowman.put("minecraft:husk", "minecraft:husk_spawn_egg");
      _snowman.put("minecraft:llama", "minecraft:llama_spawn_egg");
      _snowman.put("minecraft:magma_cube", "minecraft:magma_cube_spawn_egg");
      _snowman.put("minecraft:mooshroom", "minecraft:mooshroom_spawn_egg");
      _snowman.put("minecraft:mule", "minecraft:mule_spawn_egg");
      _snowman.put("minecraft:ocelot", "minecraft:ocelot_spawn_egg");
      _snowman.put("minecraft:pufferfish", "minecraft:pufferfish_spawn_egg");
      _snowman.put("minecraft:parrot", "minecraft:parrot_spawn_egg");
      _snowman.put("minecraft:pig", "minecraft:pig_spawn_egg");
      _snowman.put("minecraft:polar_bear", "minecraft:polar_bear_spawn_egg");
      _snowman.put("minecraft:rabbit", "minecraft:rabbit_spawn_egg");
      _snowman.put("minecraft:sheep", "minecraft:sheep_spawn_egg");
      _snowman.put("minecraft:shulker", "minecraft:shulker_spawn_egg");
      _snowman.put("minecraft:silverfish", "minecraft:silverfish_spawn_egg");
      _snowman.put("minecraft:skeleton", "minecraft:skeleton_spawn_egg");
      _snowman.put("minecraft:skeleton_horse", "minecraft:skeleton_horse_spawn_egg");
      _snowman.put("minecraft:slime", "minecraft:slime_spawn_egg");
      _snowman.put("minecraft:spider", "minecraft:spider_spawn_egg");
      _snowman.put("minecraft:squid", "minecraft:squid_spawn_egg");
      _snowman.put("minecraft:stray", "minecraft:stray_spawn_egg");
      _snowman.put("minecraft:turtle", "minecraft:turtle_spawn_egg");
      _snowman.put("minecraft:vex", "minecraft:vex_spawn_egg");
      _snowman.put("minecraft:villager", "minecraft:villager_spawn_egg");
      _snowman.put("minecraft:vindication_illager", "minecraft:vindication_illager_spawn_egg");
      _snowman.put("minecraft:witch", "minecraft:witch_spawn_egg");
      _snowman.put("minecraft:wither_skeleton", "minecraft:wither_skeleton_spawn_egg");
      _snowman.put("minecraft:wolf", "minecraft:wolf_spawn_egg");
      _snowman.put("minecraft:zombie", "minecraft:zombie_spawn_egg");
      _snowman.put("minecraft:zombie_horse", "minecraft:zombie_horse_spawn_egg");
      _snowman.put("minecraft:zombie_pigman", "minecraft:zombie_pigman_spawn_egg");
      _snowman.put("minecraft:zombie_villager", "minecraft:zombie_villager_spawn_egg");
   });

   public ItemInstanceSpawnEggFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
      OpticFinder<Pair<String, String>> _snowmanx = DSL.fieldFinder(
         "id", DSL.named(TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType())
      );
      OpticFinder<String> _snowmanxx = DSL.fieldFinder("id", IdentifierNormalizingSchema.getIdentifierType());
      OpticFinder<?> _snowmanxxx = _snowman.findField("tag");
      OpticFinder<?> _snowmanxxxx = _snowmanxxx.type().findField("EntityTag");
      return this.fixTypeEverywhereTyped("ItemInstanceSpawnEggFix", _snowman, _snowmanxxxxx -> {
         Optional<Pair<String, String>> _snowmanxxxxxx = _snowmanxxxxx.getOptional(_snowman);
         if (_snowmanxxxxxx.isPresent() && Objects.equals(_snowmanxxxxxx.get().getSecond(), "minecraft:spawn_egg")) {
            Typed<?> _snowmanx = _snowmanxxxxx.getOrCreateTyped(_snowman);
            Typed<?> _snowmanxx = _snowmanx.getOrCreateTyped(_snowman);
            Optional<String> _snowmanxxx = _snowmanxx.getOptional(_snowman);
            if (_snowmanxxx.isPresent()) {
               return _snowmanxxxxx.set(_snowman, Pair.of(TypeReferences.ITEM_NAME.typeName(), ENTITY_SPAWN_EGGS.getOrDefault(_snowmanxxx.get(), "minecraft:pig_spawn_egg")));
            }
         }

         return _snowmanxxxxx;
      });
   }
}
