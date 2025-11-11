import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public class ahz extends ajv {
   private static final Map<String, String> a = (Map<String, String>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      var0.put("donkeykong", "donkey_kong");
      var0.put("burningskull", "burning_skull");
      var0.put("skullandroses", "skull_and_roses");
   });

   public ahz(Schema var1, boolean var2) {
      super(_snowman, _snowman, "EntityPaintingMotiveFix", akn.p, "minecraft:painting");
   }

   public Dynamic<?> a(Dynamic<?> var1) {
      Optional<String> _snowman = _snowman.get("Motive").asString().result();
      if (_snowman.isPresent()) {
         String _snowmanx = _snowman.get().toLowerCase(Locale.ROOT);
         return _snowman.set("Motive", _snowman.createString(new vk(a.getOrDefault(_snowmanx, _snowmanx)).toString()));
      } else {
         return _snowman;
      }
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), this::a);
   }
}
