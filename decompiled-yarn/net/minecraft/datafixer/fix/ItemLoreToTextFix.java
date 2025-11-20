package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.stream.Stream;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class ItemLoreToTextFix extends DataFix {
   public ItemLoreToTextFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
      OpticFinder<?> _snowmanx = _snowman.findField("tag");
      return this.fixTypeEverywhereTyped(
         "Item Lore componentize",
         _snowman,
         _snowmanxx -> _snowmanxx.updateTyped(
               _snowman,
               _snowmanxxx -> _snowmanxxx.update(
                     DSL.remainderFinder(),
                     _snowmanxxxx -> _snowmanxxxx.update(
                           "display",
                           _snowmanxxxxx -> _snowmanxxxxx.update(
                                 "Lore",
                                 _snowmanxxxxxx -> (Dynamic)DataFixUtils.orElse(
                                       _snowmanxxxxxx.asStreamOpt().map(ItemLoreToTextFix::fixLoreTags).map(_snowmanxxxxxx::createList).result(), _snowmanxxxxxx
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static <T> Stream<Dynamic<T>> fixLoreTags(Stream<Dynamic<T>> tags) {
      return tags.map(_snowman -> (Dynamic<T>)DataFixUtils.orElse(_snowman.asString().map(ItemLoreToTextFix::componentize).map(_snowman::createString).result(), _snowman));
   }

   private static String componentize(String string) {
      return Text.Serializer.toJson(new LiteralText(string));
   }
}
