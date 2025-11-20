package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;

public class ItemStackEnchantmentFix extends DataFix {
   private static final Int2ObjectMap<String> ID_TO_ENCHANTMENTS_MAP = (Int2ObjectMap<String>)DataFixUtils.make(new Int2ObjectOpenHashMap(), _snowman -> {
      _snowman.put(0, "minecraft:protection");
      _snowman.put(1, "minecraft:fire_protection");
      _snowman.put(2, "minecraft:feather_falling");
      _snowman.put(3, "minecraft:blast_protection");
      _snowman.put(4, "minecraft:projectile_protection");
      _snowman.put(5, "minecraft:respiration");
      _snowman.put(6, "minecraft:aqua_affinity");
      _snowman.put(7, "minecraft:thorns");
      _snowman.put(8, "minecraft:depth_strider");
      _snowman.put(9, "minecraft:frost_walker");
      _snowman.put(10, "minecraft:binding_curse");
      _snowman.put(16, "minecraft:sharpness");
      _snowman.put(17, "minecraft:smite");
      _snowman.put(18, "minecraft:bane_of_arthropods");
      _snowman.put(19, "minecraft:knockback");
      _snowman.put(20, "minecraft:fire_aspect");
      _snowman.put(21, "minecraft:looting");
      _snowman.put(22, "minecraft:sweeping");
      _snowman.put(32, "minecraft:efficiency");
      _snowman.put(33, "minecraft:silk_touch");
      _snowman.put(34, "minecraft:unbreaking");
      _snowman.put(35, "minecraft:fortune");
      _snowman.put(48, "minecraft:power");
      _snowman.put(49, "minecraft:punch");
      _snowman.put(50, "minecraft:flame");
      _snowman.put(51, "minecraft:infinity");
      _snowman.put(61, "minecraft:luck_of_the_sea");
      _snowman.put(62, "minecraft:lure");
      _snowman.put(65, "minecraft:loyalty");
      _snowman.put(66, "minecraft:impaling");
      _snowman.put(67, "minecraft:riptide");
      _snowman.put(68, "minecraft:channeling");
      _snowman.put(70, "minecraft:mending");
      _snowman.put(71, "minecraft:vanishing_curse");
   });

   public ItemStackEnchantmentFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
      OpticFinder<?> _snowmanx = _snowman.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemStackEnchantmentFix", _snowman, _snowmanxx -> _snowmanxx.updateTyped(_snowman, _snowmanxxx -> _snowmanxxx.update(DSL.remainderFinder(), this::fixEnchantments))
      );
   }

   private Dynamic<?> fixEnchantments(Dynamic<?> _snowman) {
      Optional<? extends Dynamic<?>> _snowmanx = _snowman.get("ench")
         .asStreamOpt()
         .map(_snowmanxx -> _snowmanxx.map(_snowmanxxx -> _snowmanxxx.set("id", _snowmanxxx.createString((String)ID_TO_ENCHANTMENTS_MAP.getOrDefault(_snowmanxxx.get("id").asInt(0), "null")))))
         .map(_snowman::createList)
         .result();
      if (_snowmanx.isPresent()) {
         _snowman = _snowman.remove("ench").set("Enchantments", _snowmanx.get());
      }

      return _snowman.update(
         "StoredEnchantments",
         _snowmanxx -> (Dynamic)DataFixUtils.orElse(
               _snowmanxx.asStreamOpt()
                  .map(
                     _snowmanxxx -> _snowmanxxx.map(
                           _snowmanxxxx -> _snowmanxxxx.set("id", _snowmanxxxx.createString((String)ID_TO_ENCHANTMENTS_MAP.getOrDefault(_snowmanxxxx.get("id").asInt(0), "null")))
                        )
                  )
                  .map(_snowmanxx::createList)
                  .result(),
               _snowmanxx
            )
      );
   }
}
