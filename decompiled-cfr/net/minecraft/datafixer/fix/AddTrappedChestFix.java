/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.List$ListType
 *  com.mojang.datafixers.types.templates.TaggedChoice$TaggedChoiceType
 *  com.mojang.serialization.Dynamic
 *  it.unimi.dsi.fastutil.ints.IntOpenHashSet
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.List;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.ChoiceTypesFix;
import net.minecraft.datafixer.fix.LeavesFix;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddTrappedChestFix
extends DataFix {
    private static final Logger LOGGER = LogManager.getLogger();

    public AddTrappedChestFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getOutputSchema().getType(TypeReferences.CHUNK);
        _snowman = type.findFieldType("Level");
        _snowman = _snowman.findFieldType("TileEntities");
        if (!(_snowman instanceof List.ListType)) {
            throw new IllegalStateException("Tile entity type is not a list type.");
        }
        List.ListType _snowman2 = (List.ListType)_snowman;
        OpticFinder _snowman3 = DSL.fieldFinder((String)"TileEntities", (Type)_snowman2);
        _snowman = this.getInputSchema().getType(TypeReferences.CHUNK);
        OpticFinder _snowman4 = _snowman.findField("Level");
        OpticFinder _snowman5 = _snowman4.type().findField("Sections");
        _snowman = _snowman5.type();
        if (!(_snowman instanceof List.ListType)) {
            throw new IllegalStateException("Expecting sections to be a list.");
        }
        _snowman = ((List.ListType)_snowman).getElement();
        OpticFinder _snowman6 = DSL.typeFinder((Type)_snowman);
        return TypeRewriteRule.seq((TypeRewriteRule)new ChoiceTypesFix(this.getOutputSchema(), "AddTrappedChestFix", TypeReferences.BLOCK_ENTITY).makeRule(), (TypeRewriteRule)this.fixTypeEverywhereTyped("Trapped Chest fix", _snowman, typed -> typed.updateTyped(_snowman4, typed2 -> {
            Typed typed2;
            Optional optional = typed2.getOptionalTyped(_snowman5);
            if (!optional.isPresent()) {
                return typed2;
            }
            List _snowman2 = ((Typed)optional.get()).getAllTyped(_snowman6);
            IntOpenHashSet _snowman3 = new IntOpenHashSet();
            for (Typed typed3 : _snowman2) {
                ListFixer listFixer = new ListFixer(typed3, this.getInputSchema());
                if (listFixer.isFixed()) continue;
                for (int i = 0; i < 4096; ++i) {
                    _snowman = listFixer.needsFix(i);
                    if (!listFixer.isTarget(_snowman)) continue;
                    _snowman3.add(listFixer.method_5077() << 12 | i);
                }
            }
            Dynamic _snowman4 = (Dynamic)typed2.get(DSL.remainderFinder());
            int _snowman5 = _snowman4.get("xPos").asInt(0);
            int _snowman6 = _snowman4.get("zPos").asInt(0);
            TaggedChoice.TaggedChoiceType _snowman7 = this.getInputSchema().findChoiceType(TypeReferences.BLOCK_ENTITY);
            return typed2.updateTyped(_snowman3, arg_0 -> AddTrappedChestFix.method_5176(_snowman7, _snowman5, _snowman6, (IntSet)_snowman3, arg_0));
        })));
    }

    private static /* synthetic */ Typed method_5176(TaggedChoice.TaggedChoiceType taggedChoiceType, int n, int n2, IntSet intSet, Typed typed2) {
        return typed2.updateTyped(taggedChoiceType.finder(), typed -> {
            Dynamic dynamic = (Dynamic)typed.getOrCreate(DSL.remainderFinder());
            int _snowman2 = dynamic.get("x").asInt(0) - (n << 4);
            if (intSet.contains(LeavesFix.method_5051(_snowman2, _snowman = dynamic.get("y").asInt(0), _snowman = dynamic.get("z").asInt(0) - (n2 << 4)))) {
                return typed.update(taggedChoiceType.finder(), pair -> pair.mapFirst(string -> {
                    if (!Objects.equals(string, "minecraft:chest")) {
                        LOGGER.warn("Block Entity was expected to be a chest");
                    }
                    return "minecraft:trapped_chest";
                }));
            }
            return typed;
        });
    }

    public static final class ListFixer
    extends LeavesFix.ListFixer {
        @Nullable
        private IntSet targets;

        public ListFixer(Typed<?> typed, Schema schema) {
            super(typed, schema);
        }

        @Override
        protected boolean needsFix() {
            this.targets = new IntOpenHashSet();
            for (int i = 0; i < this.properties.size(); ++i) {
                Dynamic dynamic = (Dynamic)this.properties.get(i);
                String _snowman2 = dynamic.get("Name").asString("");
                if (!Objects.equals(_snowman2, "minecraft:trapped_chest")) continue;
                this.targets.add(i);
            }
            return this.targets.isEmpty();
        }

        public boolean isTarget(int index) {
            return this.targets.contains(index);
        }
    }
}

