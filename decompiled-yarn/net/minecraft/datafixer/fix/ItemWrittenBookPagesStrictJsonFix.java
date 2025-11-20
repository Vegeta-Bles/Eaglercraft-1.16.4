package net.minecraft.datafixer.fix;

import com.google.gson.JsonParseException;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.JsonHelper;
import org.apache.commons.lang3.StringUtils;

public class ItemWrittenBookPagesStrictJsonFix extends DataFix {
   public ItemWrittenBookPagesStrictJsonFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public Dynamic<?> fixBookPages(Dynamic<?> _snowman) {
      return _snowman.update("pages", _snowmanxx -> (Dynamic)DataFixUtils.orElse(_snowmanxx.asStreamOpt().map(_snowmanxxx -> _snowmanxxx.map(_snowmanxxxx -> {
               if (!_snowmanxxxx.asString().result().isPresent()) {
                  return _snowmanxxxx;
               } else {
                  String _snowmanx = _snowmanxxxx.asString("");
                  Text _snowmanxx = null;
                  if (!"null".equals(_snowmanx) && !StringUtils.isEmpty(_snowmanx)) {
                     if (_snowmanx.charAt(0) == '"' && _snowmanx.charAt(_snowmanx.length() - 1) == '"' || _snowmanx.charAt(0) == '{' && _snowmanx.charAt(_snowmanx.length() - 1) == '}') {
                        try {
                           _snowmanxx = JsonHelper.deserialize(BlockEntitySignTextStrictJsonFix.GSON, _snowmanx, Text.class, true);
                           if (_snowmanxx == null) {
                              _snowmanxx = LiteralText.EMPTY;
                           }
                        } catch (JsonParseException var6) {
                        }

                        if (_snowmanxx == null) {
                           try {
                              _snowmanxx = Text.Serializer.fromJson(_snowmanx);
                           } catch (JsonParseException var5) {
                           }
                        }

                        if (_snowmanxx == null) {
                           try {
                              _snowmanxx = Text.Serializer.fromLenientJson(_snowmanx);
                           } catch (JsonParseException var4) {
                           }
                        }

                        if (_snowmanxx == null) {
                           _snowmanxx = new LiteralText(_snowmanx);
                        }
                     } else {
                        _snowmanxx = new LiteralText(_snowmanx);
                     }
                  } else {
                     _snowmanxx = LiteralText.EMPTY;
                  }

                  return _snowmanxxxx.createString(Text.Serializer.toJson(_snowmanxx));
               }
            })).map(_snowman::createList).result(), _snowman.emptyList()));
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
      OpticFinder<?> _snowmanx = _snowman.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemWrittenBookPagesStrictJsonFix", _snowman, _snowmanxx -> _snowmanxx.updateTyped(_snowman, _snowmanxxx -> _snowmanxxx.update(DSL.remainderFinder(), this::fixBookPages))
      );
   }
}
