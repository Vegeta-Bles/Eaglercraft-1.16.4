package net.minecraft.datafixer.schema;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class Schema1510 extends IdentifierNormalizingSchema {
   public Schema1510(int _snowman, Schema _snowman) {
      super(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = super.registerEntities(_snowman);
      _snowmanx.put("minecraft:command_block_minecart", _snowmanx.remove("minecraft:commandblock_minecart"));
      _snowmanx.put("minecraft:end_crystal", _snowmanx.remove("minecraft:ender_crystal"));
      _snowmanx.put("minecraft:snow_golem", _snowmanx.remove("minecraft:snowman"));
      _snowmanx.put("minecraft:evoker", _snowmanx.remove("minecraft:evocation_illager"));
      _snowmanx.put("minecraft:evoker_fangs", _snowmanx.remove("minecraft:evocation_fangs"));
      _snowmanx.put("minecraft:illusioner", _snowmanx.remove("minecraft:illusion_illager"));
      _snowmanx.put("minecraft:vindicator", _snowmanx.remove("minecraft:vindication_illager"));
      _snowmanx.put("minecraft:iron_golem", _snowmanx.remove("minecraft:villager_golem"));
      _snowmanx.put("minecraft:experience_orb", _snowmanx.remove("minecraft:xp_orb"));
      _snowmanx.put("minecraft:experience_bottle", _snowmanx.remove("minecraft:xp_bottle"));
      _snowmanx.put("minecraft:eye_of_ender", _snowmanx.remove("minecraft:eye_of_ender_signal"));
      _snowmanx.put("minecraft:firework_rocket", _snowmanx.remove("minecraft:fireworks_rocket"));
      return _snowmanx;
   }
}
