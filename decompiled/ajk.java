import com.google.gson.JsonParseException;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import org.apache.commons.lang3.StringUtils;

public class ajk extends DataFix {
   public ajk(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public Dynamic<?> a(Dynamic<?> var1) {
      return _snowman.update("pages", var1x -> (Dynamic)DataFixUtils.orElse(var1x.asStreamOpt().map(var0x -> var0x.map(var0xx -> {
               if (!var0xx.asString().result().isPresent()) {
                  return (Dynamic)var0xx;
               } else {
                  String _snowman = var0xx.asString("");
                  nr _snowmanx = null;
                  if (!"null".equals(_snowman) && !StringUtils.isEmpty(_snowman)) {
                     if (_snowman.charAt(0) == '"' && _snowman.charAt(_snowman.length() - 1) == '"' || _snowman.charAt(0) == '{' && _snowman.charAt(_snowman.length() - 1) == '}') {
                        try {
                           _snowmanx = afd.a(agu.a, _snowman, nr.class, true);
                           if (_snowmanx == null) {
                              _snowmanx = oe.d;
                           }
                        } catch (JsonParseException var6) {
                        }

                        if (_snowmanx == null) {
                           try {
                              _snowmanx = nr.a.a(_snowman);
                           } catch (JsonParseException var5) {
                           }
                        }

                        if (_snowmanx == null) {
                           try {
                              _snowmanx = nr.a.b(_snowman);
                           } catch (JsonParseException var4) {
                           }
                        }

                        if (_snowmanx == null) {
                           _snowmanx = new oe(_snowman);
                        }
                     } else {
                        _snowmanx = new oe(_snowman);
                     }
                  } else {
                     _snowmanx = oe.d;
                  }

                  return var0xx.createString(nr.a.a(_snowmanx));
               }
            })).map(_snowman::createList).result(), _snowman.emptyList()));
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.l);
      OpticFinder<?> _snowmanx = _snowman.findField("tag");
      return this.fixTypeEverywhereTyped(
         "ItemWrittenBookPagesStrictJsonFix", _snowman, var2x -> var2x.updateTyped(_snowman, var1x -> var1x.update(DSL.remainderFinder(), this::a))
      );
   }
}
