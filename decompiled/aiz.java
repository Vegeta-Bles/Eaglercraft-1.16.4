import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.stream.Stream;

public class aiz extends DataFix {
   public aiz(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.l);
      OpticFinder<?> _snowmanx = _snowman.findField("tag");
      return this.fixTypeEverywhereTyped(
         "Item Lore componentize",
         _snowman,
         var1x -> var1x.updateTyped(
               _snowman,
               var0x -> var0x.update(
                     DSL.remainderFinder(),
                     var0xx -> var0xx.update(
                           "display",
                           var0xxx -> var0xxx.update(
                                 "Lore",
                                 var0xxxx -> (Dynamic)DataFixUtils.orElse(var0xxxx.asStreamOpt().map(aiz::a).map(var0xxxx::createList).result(), var0xxxx)
                              )
                        )
                  )
            )
      );
   }

   private static <T> Stream<Dynamic<T>> a(Stream<Dynamic<T>> var0) {
      return _snowman.map(var0x -> (Dynamic<T>)DataFixUtils.orElse(var0x.asString().map(aiz::a).map(var0x::createString).result(), var0x));
   }

   private static String a(String var0) {
      return nr.a.a(new oe(_snowman));
   }
}
