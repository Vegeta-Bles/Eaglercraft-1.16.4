package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class ItemCustomNameToComponentFix extends DataFix {
   public ItemCustomNameToComponentFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   private Dynamic<?> fixCustomName(Dynamic<?> _snowman) {
      Optional<? extends Dynamic<?>> _snowmanx = _snowman.get("display").result();
      if (_snowmanx.isPresent()) {
         Dynamic<?> _snowmanxx = (Dynamic<?>)_snowmanx.get();
         Optional<String> _snowmanxxx = _snowmanxx.get("Name").asString().result();
         if (_snowmanxxx.isPresent()) {
            _snowmanxx = _snowmanxx.set("Name", _snowmanxx.createString(Text.Serializer.toJson(new LiteralText(_snowmanxxx.get()))));
         } else {
            Optional<String> _snowmanxxxx = _snowmanxx.get("LocName").asString().result();
            if (_snowmanxxxx.isPresent()) {
               _snowmanxx = _snowmanxx.set("Name", _snowmanxx.createString(Text.Serializer.toJson(new TranslatableText(_snowmanxxxx.get()))));
               _snowmanxx = _snowmanxx.remove("LocName");
            }
         }

         return _snowman.set("display", _snowmanxx);
      } else {
         return _snowman;
      }
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
      OpticFinder<?> _snowmanx = _snowman.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemCustomNameToComponentFix", _snowman, _snowmanxx -> _snowmanxx.updateTyped(_snowman, _snowmanxxx -> _snowmanxxx.update(DSL.remainderFinder(), this::fixCustomName))
      );
   }
}
