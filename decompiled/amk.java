import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class amk extends aln {
   public amk(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = super.registerEntities(_snowman);
      _snowman.put("minecraft:command_block_minecart", _snowman.remove("minecraft:commandblock_minecart"));
      _snowman.put("minecraft:end_crystal", _snowman.remove("minecraft:ender_crystal"));
      _snowman.put("minecraft:snow_golem", _snowman.remove("minecraft:snowman"));
      _snowman.put("minecraft:evoker", _snowman.remove("minecraft:evocation_illager"));
      _snowman.put("minecraft:evoker_fangs", _snowman.remove("minecraft:evocation_fangs"));
      _snowman.put("minecraft:illusioner", _snowman.remove("minecraft:illusion_illager"));
      _snowman.put("minecraft:vindicator", _snowman.remove("minecraft:vindication_illager"));
      _snowman.put("minecraft:iron_golem", _snowman.remove("minecraft:villager_golem"));
      _snowman.put("minecraft:experience_orb", _snowman.remove("minecraft:xp_orb"));
      _snowman.put("minecraft:experience_bottle", _snowman.remove("minecraft:xp_bottle"));
      _snowman.put("minecraft:eye_of_ender", _snowman.remove("minecraft:eye_of_ender_signal"));
      _snowman.put("minecraft:firework_rocket", _snowman.remove("minecraft:fireworks_rocket"));
      return _snowman;
   }
}
