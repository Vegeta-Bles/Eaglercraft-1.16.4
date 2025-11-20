package net.minecraft.datafixer.fix;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Objects;
import net.minecraft.datafixer.TypeReferences;

public class EntityMinecartIdentifiersFix extends DataFix {
   private static final List<String> MINECARTS = Lists.newArrayList(new String[]{"MinecartRideable", "MinecartChest", "MinecartFurnace"});

   public EntityMinecartIdentifiersFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      TaggedChoiceType<String> _snowman = this.getInputSchema().findChoiceType(TypeReferences.ENTITY);
      TaggedChoiceType<String> _snowmanx = this.getOutputSchema().findChoiceType(TypeReferences.ENTITY);
      return this.fixTypeEverywhere(
         "EntityMinecartIdentifiersFix",
         _snowman,
         _snowmanx,
         _snowmanxx -> _snowmanxxxxxxxx -> {
               if (!Objects.equals(_snowmanxxxxxxxx.getFirst(), "Minecart")) {
                  return _snowmanxxxxxxxx;
               } else {
                  Typed<? extends Pair<String, ?>> _snowmanxxxx = (Typed<? extends Pair<String, ?>>)_snowman.point(_snowmanxx, "Minecart", _snowmanxxxxxxxx.getSecond())
                     .orElseThrow(IllegalStateException::new);
                  Dynamic<?> _snowmanxxxxx = (Dynamic<?>)_snowmanxxxx.getOrCreate(DSL.remainderFinder());
                  int _snowmanxxxxxx = _snowmanxxxxx.get("Type").asInt(0);
                  String _snowmanxxxxxxx;
                  if (_snowmanxxxxxx > 0 && _snowmanxxxxxx < MINECARTS.size()) {
                     _snowmanxxxxxxx = MINECARTS.get(_snowmanxxxxxx);
                  } else {
                     _snowmanxxxxxxx = "MinecartRideable";
                  }

                  return Pair.of(
                     _snowmanxxxxxxx,
                     _snowmanxxxx.write()
                        .map(_snowmanxxxxxxxxxxxx -> ((Type)_snowman.types().get(_snowmanxxxxx)).read(_snowmanxxxxxxxxxxxx))
                        .result()
                        .orElseThrow(() -> new IllegalStateException("Could not read the new minecart."))
                  );
               }
            }
      );
   }
}
