import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.FieldFinder;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.CompoundList.CompoundListType;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.List;

public class ajt extends DataFix {
   public ajt(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   private static <A> Type<Pair<A, Dynamic<?>>> a(String var0, Type<A> var1) {
      return DSL.and(DSL.field(_snowman, _snowman), DSL.remainderType());
   }

   private static <A> Type<Pair<Either<A, Unit>, Dynamic<?>>> b(String var0, Type<A> var1) {
      return DSL.and(DSL.optional(DSL.field(_snowman, _snowman)), DSL.remainderType());
   }

   private static <A1, A2> Type<Pair<Either<A1, Unit>, Pair<Either<A2, Unit>, Dynamic<?>>>> a(String var0, Type<A1> var1, String var2, Type<A2> var3) {
      return DSL.and(DSL.optional(DSL.field(_snowman, _snowman)), DSL.optional(DSL.field(_snowman, _snowman)), DSL.remainderType());
   }

   protected TypeRewriteRule makeRule() {
      Schema _snowman = this.getInputSchema();
      TaggedChoiceType<String> _snowmanx = new TaggedChoiceType(
         "type",
         DSL.string(),
         ImmutableMap.of(
            "minecraft:debug",
            DSL.remainderType(),
            "minecraft:flat",
            b("settings", a("biome", _snowman.getType(akn.x), "layers", DSL.list(b("block", _snowman.getType(akn.q))))),
            "minecraft:noise",
            a(
               "biome_source",
               DSL.taggedChoiceType(
                  "type",
                  DSL.string(),
                  ImmutableMap.of(
                     "minecraft:fixed",
                     a("biome", _snowman.getType(akn.x)),
                     "minecraft:multi_noise",
                     DSL.list(a("biome", _snowman.getType(akn.x))),
                     "minecraft:checkerboard",
                     a("biomes", DSL.list(_snowman.getType(akn.x))),
                     "minecraft:vanilla_layered",
                     DSL.remainderType(),
                     "minecraft:the_end",
                     DSL.remainderType()
                  )
               ),
               "settings",
               DSL.or(DSL.string(), a("default_block", _snowman.getType(akn.q), "default_fluid", _snowman.getType(akn.q)))
            )
         )
      );
      CompoundListType<String, ?> _snowmanxx = DSL.compoundList(aln.a(), a("generator", _snowmanx));
      Type<?> _snowmanxxx = DSL.and(_snowmanxx, DSL.remainderType());
      Type<?> _snowmanxxxx = _snowman.getType(akn.y);
      FieldFinder<?> _snowmanxxxxx = new FieldFinder("dimensions", _snowmanxxx);
      if (!_snowmanxxxx.findFieldType("dimensions").equals(_snowmanxxx)) {
         throw new IllegalStateException();
      } else {
         OpticFinder<? extends List<? extends Pair<String, ?>>> _snowmanxxxxxx = _snowmanxx.finder();
         return this.fixTypeEverywhereTyped("MissingDimensionFix", _snowmanxxxx, var4x -> var4x.updateTyped(_snowman, var4xx -> var4xx.updateTyped(_snowman, var3x -> {
                  if (!(var3x.getValue() instanceof List)) {
                     throw new IllegalStateException("List exptected");
                  } else if (((List)var3x.getValue()).isEmpty()) {
                     Dynamic<?> _snowmanxxxxxxx = (Dynamic<?>)var4x.get(DSL.remainderFinder());
                     Dynamic<?> _snowmanx = this.a(_snowmanxxxxxxx);
                     return (Typed)DataFixUtils.orElse(_snowman.readTyped(_snowmanx).result().map(Pair::getFirst), var3x);
                  } else {
                     return var3x;
                  }
               })));
      }
   }

   private <T> Dynamic<T> a(Dynamic<T> var1) {
      long _snowman = _snowman.get("seed").asLong(0L);
      return new Dynamic(_snowman.getOps(), ali.a(_snowman, _snowman, ali.a(_snowman, _snowman), false));
   }
}
