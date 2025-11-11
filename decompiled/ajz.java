import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;

public class ajz extends ajv {
   public ajz(Schema var1, boolean var2) {
      super(_snowman, _snowman, "OminousBannerBlockEntityRenameFix", akn.k, "minecraft:banner");
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), this::a);
   }

   private Dynamic<?> a(Dynamic<?> var1) {
      Optional<String> _snowman = _snowman.get("CustomName").asString().result();
      if (_snowman.isPresent()) {
         String _snowmanx = _snowman.get();
         _snowmanx = _snowmanx.replace("\"translate\":\"block.minecraft.illager_banner\"", "\"translate\":\"block.minecraft.ominous_banner\"");
         return _snowman.set("CustomName", _snowman.createString(_snowmanx));
      } else {
         return _snowman;
      }
   }
}
