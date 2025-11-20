package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List.ListType;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.datafixer.TypeReferences;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddTrappedChestFix extends DataFix {
   private static final Logger LOGGER = LogManager.getLogger();

   public AddTrappedChestFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getOutputSchema().getType(TypeReferences.CHUNK);
      Type<?> _snowmanx = _snowman.findFieldType("Level");
      Type<?> _snowmanxx = _snowmanx.findFieldType("TileEntities");
      if (!(_snowmanxx instanceof ListType)) {
         throw new IllegalStateException("Tile entity type is not a list type.");
      } else {
         ListType<?> _snowmanxxx = (ListType<?>)_snowmanxx;
         OpticFinder<? extends List<?>> _snowmanxxxx = DSL.fieldFinder("TileEntities", _snowmanxxx);
         Type<?> _snowmanxxxxx = this.getInputSchema().getType(TypeReferences.CHUNK);
         OpticFinder<?> _snowmanxxxxxx = _snowmanxxxxx.findField("Level");
         OpticFinder<?> _snowmanxxxxxxx = _snowmanxxxxxx.type().findField("Sections");
         Type<?> _snowmanxxxxxxxx = _snowmanxxxxxxx.type();
         if (!(_snowmanxxxxxxxx instanceof ListType)) {
            throw new IllegalStateException("Expecting sections to be a list.");
         } else {
            Type<?> _snowmanxxxxxxxxx = ((ListType)_snowmanxxxxxxxx).getElement();
            OpticFinder<?> _snowmanxxxxxxxxxx = DSL.typeFinder(_snowmanxxxxxxxxx);
            return TypeRewriteRule.seq(
               new ChoiceTypesFix(this.getOutputSchema(), "AddTrappedChestFix", TypeReferences.BLOCK_ENTITY).makeRule(),
               this.fixTypeEverywhereTyped(
                  "Trapped Chest fix",
                  _snowmanxxxxx,
                  _snowmanxxxxxxxxxxx -> _snowmanxxxxxxxxxxx.updateTyped(
                        _snowman,
                        _snowmanxxxxxxxxxxxxxxxx -> {
                           Optional<? extends Typed<?>> _snowmanxxxx = _snowmanxxxxxxxxxxxxxxxx.getOptionalTyped(_snowman);
                           if (!_snowmanxxxx.isPresent()) {
                              return _snowmanxxxxxxxxxxxxxxxx;
                           } else {
                              List<? extends Typed<?>> _snowmanxxxxx = _snowmanxxxx.get().getAllTyped(_snowman);
                              IntSet _snowmanxxxxxx = new IntOpenHashSet();

                              for (Typed<?> _snowmanxxxxxxx : _snowmanxxxxx) {
                                 AddTrappedChestFix.ListFixer _snowmanxxxxxxxxxxxxxxxx = new AddTrappedChestFix.ListFixer(_snowmanxxxxxxx, this.getInputSchema());
                                 if (!_snowmanxxxxxxxxxxxxxxxx.isFixed()) {
                                    for (int _snowmanxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxx < 4096; _snowmanxxxxxxxxxxxxxxxxx++) {
                                       int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.needsFix(_snowmanxxxxxxxxxxxxxxxxx);
                                       if (_snowmanxxxxxxxxxxxxxxxx.isTarget(_snowmanxxxxxxxxxxxxxxxxxx)) {
                                          _snowmanxxxxxx.add(_snowmanxxxxxxxxxxxxxxxx.method_5077() << 12 | _snowmanxxxxxxxxxxxxxxxxx);
                                       }
                                    }
                                 }
                              }

                              Dynamic<?> _snowmanxxxxxxxxxxxxxxxx = (Dynamic<?>)_snowmanxxxxxxxxxxxxxxxx.get(DSL.remainderFinder());
                              int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.get("xPos").asInt(0);
                              int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.get("zPos").asInt(0);
                              TaggedChoiceType<String> _snowmanxxxxxxxxxxxxxxxxxxxx = this.getInputSchema().findChoiceType(TypeReferences.BLOCK_ENTITY);
                              return _snowmanxxxxxxxxxxxxxxxx.updateTyped(
                                 _snowman,
                                 _snowmanxxxxxxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxxxxxxx.updateTyped(
                                       _snowmanxxxxx.finder(),
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -> {
                                          Dynamic<?> _snowmanxxxxx = (Dynamic<?>)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.getOrCreate(DSL.remainderFinder());
                                          int _snowmanxxxxxx = _snowmanxxxxx.get("x").asInt(0) - (_snowmanxxxxxxx << 4);
                                          int _snowmanxxxxxxx = _snowmanxxxxx.get("y").asInt(0);
                                          int _snowmanxxxxxxxx = _snowmanxxxxx.get("z").asInt(0) - (_snowmanxxxxxx << 4);
                                          return _snowmanxxxxxxxxxxxxx.contains(LeavesFix.method_5051(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx))
                                             ? _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.update(
                                                _snowmanxxxxx.finder(),
                                                _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.mapFirst(
                                                      _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx -> {
                                                         if (!Objects.equals(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "minecraft:chest")) {
                                                            LOGGER.warn("Block Entity was expected to be a chest");
                                                         }

                                                         return "minecraft:trapped_chest";
                                                      }
                                                   )
                                             )
                                             : _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                                       }
                                    )
                              );
                           }
                        }
                     )
               )
            );
         }
      }
   }

   public static final class ListFixer extends LeavesFix.ListFixer {
      @Nullable
      private IntSet targets;

      public ListFixer(Typed<?> _snowman, Schema _snowman) {
         super(_snowman, _snowman);
      }

      @Override
      protected boolean needsFix() {
         this.targets = new IntOpenHashSet();

         for (int _snowman = 0; _snowman < this.properties.size(); _snowman++) {
            Dynamic<?> _snowmanx = this.properties.get(_snowman);
            String _snowmanxx = _snowmanx.get("Name").asString("");
            if (Objects.equals(_snowmanxx, "minecraft:trapped_chest")) {
               this.targets.add(_snowman);
            }
         }

         return this.targets.isEmpty();
      }

      public boolean isTarget(int index) {
         return this.targets.contains(index);
      }
   }
}
