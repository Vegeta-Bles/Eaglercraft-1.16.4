import com.mojang.datafixers.DSL;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public class aji extends agd {
   public aji(Schema var1) {
      super(_snowman, akn.l);
   }

   public TypeRewriteRule makeRule() {
      OpticFinder<Pair<String, String>> _snowman = DSL.fieldFinder("id", DSL.named(akn.r.typeName(), aln.a()));
      return this.fixTypeEverywhereTyped("ItemStackUUIDFix", this.getInputSchema().getType(this.b), var2 -> {
         OpticFinder<?> _snowmanx = var2.getType().findField("tag");
         return var2.updateTyped(_snowmanx, var3x -> var3x.update(DSL.remainderFinder(), var3xx -> {
               var3xx = this.b(var3xx);
               if (var2.getOptional(_snowman).map(var0 -> "minecraft:player_head".equals(var0.getSecond())).orElse(false)) {
                  var3xx = this.c(var3xx);
               }

               return var3xx;
            }));
      });
   }

   private Dynamic<?> b(Dynamic<?> var1) {
      return _snowman.update(
         "AttributeModifiers", var1x -> _snowman.createList(var1x.asStream().map(var0x -> (Dynamic)c((Dynamic<?>)var0x, "UUID", "UUID").orElse((Dynamic<?>)var0x)))
      );
   }

   private Dynamic<?> c(Dynamic<?> var1) {
      return _snowman.update("SkullOwner", var0 -> a(var0, "Id", "Id").orElse(var0));
   }
}
