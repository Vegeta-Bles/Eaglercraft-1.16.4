package net.minecraft.datafixer.fix;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.Identifier;

public class EntityPaintingMotiveFix extends ChoiceFix {
   private static final Map<String, String> RENAMED_MOTIVES = (Map<String, String>)DataFixUtils.make(Maps.newHashMap(), _snowman -> {
      _snowman.put("donkeykong", "donkey_kong");
      _snowman.put("burningskull", "burning_skull");
      _snowman.put("skullandroses", "skull_and_roses");
   });

   public EntityPaintingMotiveFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType, "EntityPaintingMotiveFix", TypeReferences.ENTITY, "minecraft:painting");
   }

   public Dynamic<?> renameMotive(Dynamic<?> _snowman) {
      Optional<String> _snowmanx = _snowman.get("Motive").asString().result();
      if (_snowmanx.isPresent()) {
         String _snowmanxx = _snowmanx.get().toLowerCase(Locale.ROOT);
         return _snowman.set("Motive", _snowman.createString(new Identifier(RENAMED_MOTIVES.getOrDefault(_snowmanxx, _snowmanxx)).toString()));
      } else {
         return _snowman;
      }
   }

   @Override
   protected Typed<?> transform(Typed<?> inputType) {
      return inputType.update(DSL.remainderFinder(), this::renameMotive);
   }
}
