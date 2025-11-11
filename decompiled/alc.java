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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class alc extends DataFix {
   private static final Logger a = LogManager.getLogger();

   public alc(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getOutputSchema().getType(akn.c);
      Type<?> _snowmanx = _snowman.findFieldType("Level");
      Type<?> _snowmanxx = _snowmanx.findFieldType("TileEntities");
      if (!(_snowmanxx instanceof ListType)) {
         throw new IllegalStateException("Tile entity type is not a list type.");
      } else {
         ListType<?> _snowmanxxx = (ListType<?>)_snowmanxx;
         OpticFinder<? extends List<?>> _snowmanxxxx = DSL.fieldFinder("TileEntities", _snowmanxxx);
         Type<?> _snowmanxxxxx = this.getInputSchema().getType(akn.c);
         OpticFinder<?> _snowmanxxxxxx = _snowmanxxxxx.findField("Level");
         OpticFinder<?> _snowmanxxxxxxx = _snowmanxxxxxx.type().findField("Sections");
         Type<?> _snowmanxxxxxxxx = _snowmanxxxxxxx.type();
         if (!(_snowmanxxxxxxxx instanceof ListType)) {
            throw new IllegalStateException("Expecting sections to be a list.");
         } else {
            Type<?> _snowmanxxxxxxxxx = ((ListType)_snowmanxxxxxxxx).getElement();
            OpticFinder<?> _snowmanxxxxxxxxxx = DSL.typeFinder(_snowmanxxxxxxxxx);
            return TypeRewriteRule.seq(
               new age(this.getOutputSchema(), "AddTrappedChestFix", akn.k).makeRule(),
               this.fixTypeEverywhereTyped("Trapped Chest fix", _snowmanxxxxx, var5x -> var5x.updateTyped(_snowman, var4x -> {
                     Optional<? extends Typed<?>> _snowmanxxxxxxxxxxx = var4x.getOptionalTyped(_snowman);
                     if (!_snowmanxxxxxxxxxxx.isPresent()) {
                        return var4x;
                     } else {
                        List<? extends Typed<?>> _snowmanx = _snowmanxxxxxxxxxxx.get().getAllTyped(_snowman);
                        IntSet _snowmanxx = new IntOpenHashSet();

                        for (Typed<?> _snowmanxxx : _snowmanx) {
                           alc.a _snowmanxxxx = new alc.a(_snowmanxxx, this.getInputSchema());
                           if (!_snowmanxxxx.b()) {
                              for (int _snowmanxxxxx = 0; _snowmanxxxxx < 4096; _snowmanxxxxx++) {
                                 int _snowmanxxxxxx = _snowmanxxxx.c(_snowmanxxxxx);
                                 if (_snowmanxxxx.a(_snowmanxxxxxx)) {
                                    _snowmanxx.add(_snowmanxxxx.c() << 12 | _snowmanxxxxx);
                                 }
                              }
                           }
                        }

                        Dynamic<?> _snowmanxxxx = (Dynamic<?>)var4x.get(DSL.remainderFinder());
                        int _snowmanxxxxxx = _snowmanxxxx.get("xPos").asInt(0);
                        int _snowmanxxxxxxx = _snowmanxxxx.get("zPos").asInt(0);
                        TaggedChoiceType<String> _snowmanxxxxxxxx = this.getInputSchema().findChoiceType(akn.k);
                        return var4x.updateTyped(_snowman, var4xx -> var4xx.updateTyped(_snowman.finder(), var4xxx -> {
                              Dynamic<?> _snowmanxxxxxxxxx = (Dynamic<?>)var4xxx.getOrCreate(DSL.remainderFinder());
                              int _snowmanx = _snowmanxxxxxxxxx.get("x").asInt(0) - (_snowman << 4);
                              int _snowmanxx = _snowmanxxxxxxxxx.get("y").asInt(0);
                              int _snowmanxxx = _snowmanxxxxxxxxx.get("z").asInt(0) - (_snowman << 4);
                              return _snowman.contains(ajn.a(_snowmanx, _snowmanxx, _snowmanxxx)) ? var4xxx.update(_snowman.finder(), var0x -> var0x.mapFirst(var0xx -> {
                                    if (!Objects.equals(var0xx, "minecraft:chest")) {
                                       a.warn("Block Entity was expected to be a chest");
                                    }

                                    return "minecraft:trapped_chest";
                                 })) : var4xxx;
                           }));
                     }
                  }))
            );
         }
      }
   }

   public static final class a extends ajn.b {
      @Nullable
      private IntSet e;

      public a(Typed<?> var1, Schema var2) {
         super(_snowman, _snowman);
      }

      @Override
      protected boolean a() {
         this.e = new IntOpenHashSet();

         for (int _snowman = 0; _snowman < this.b.size(); _snowman++) {
            Dynamic<?> _snowmanx = this.b.get(_snowman);
            String _snowmanxx = _snowmanx.get("Name").asString("");
            if (Objects.equals(_snowmanxx, "minecraft:trapped_chest")) {
               this.e.add(_snowman);
            }
         }

         return this.e.isEmpty();
      }

      public boolean a(int var1) {
         return this.e.contains(_snowman);
      }
   }
}
