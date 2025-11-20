/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.FieldFinder
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.CompoundList$CompoundListType
 *  com.mojang.datafixers.types.templates.TaggedChoice$TaggedChoiceType
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.datafixers.util.Unit
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.datafixer.fix;

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
import com.mojang.datafixers.types.templates.CompoundList;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Map;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.StructureSeparationDataFix;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class MissingDimensionFix
extends DataFix {
    public MissingDimensionFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    private static <A> Type<Pair<A, Dynamic<?>>> method_29913(String string, Type<A> type) {
        return DSL.and((Type)DSL.field((String)string, type), (Type)DSL.remainderType());
    }

    private static <A> Type<Pair<Either<A, Unit>, Dynamic<?>>> method_29915(String string, Type<A> type) {
        return DSL.and((Type)DSL.optional((Type)DSL.field((String)string, type)), (Type)DSL.remainderType());
    }

    private static <A1, A2> Type<Pair<Either<A1, Unit>, Pair<Either<A2, Unit>, Dynamic<?>>>> method_29914(String string, Type<A1> type, String string2, Type<A2> type2) {
        return DSL.and((Type)DSL.optional((Type)DSL.field((String)string, type)), (Type)DSL.optional((Type)DSL.field((String)string2, type2)), (Type)DSL.remainderType());
    }

    protected TypeRewriteRule makeRule() {
        Schema schema = this.getInputSchema();
        TaggedChoice.TaggedChoiceType _snowman2 = new TaggedChoice.TaggedChoiceType("type", DSL.string(), (Map)ImmutableMap.of((Object)"minecraft:debug", (Object)DSL.remainderType(), (Object)"minecraft:flat", MissingDimensionFix.method_29915("settings", MissingDimensionFix.method_29914("biome", schema.getType(TypeReferences.BIOME), "layers", DSL.list(MissingDimensionFix.method_29915("block", schema.getType(TypeReferences.BLOCK_NAME))))), (Object)"minecraft:noise", MissingDimensionFix.method_29914("biome_source", DSL.taggedChoiceType((String)"type", (Type)DSL.string(), (Map)ImmutableMap.of((Object)"minecraft:fixed", MissingDimensionFix.method_29913("biome", schema.getType(TypeReferences.BIOME)), (Object)"minecraft:multi_noise", (Object)DSL.list(MissingDimensionFix.method_29913("biome", schema.getType(TypeReferences.BIOME))), (Object)"minecraft:checkerboard", MissingDimensionFix.method_29913("biomes", DSL.list((Type)schema.getType(TypeReferences.BIOME))), (Object)"minecraft:vanilla_layered", (Object)DSL.remainderType(), (Object)"minecraft:the_end", (Object)DSL.remainderType())), "settings", DSL.or((Type)DSL.string(), MissingDimensionFix.method_29914("default_block", schema.getType(TypeReferences.BLOCK_NAME), "default_fluid", schema.getType(TypeReferences.BLOCK_NAME))))));
        CompoundList.CompoundListType _snowman3 = DSL.compoundList(IdentifierNormalizingSchema.getIdentifierType(), MissingDimensionFix.method_29913("generator", _snowman2));
        Type _snowman4 = DSL.and((Type)_snowman3, (Type)DSL.remainderType());
        Type _snowman5 = schema.getType(TypeReferences.CHUNK_GENERATOR_SETTINGS);
        FieldFinder _snowman6 = new FieldFinder("dimensions", _snowman4);
        if (!_snowman5.findFieldType("dimensions").equals((Object)_snowman4)) {
            throw new IllegalStateException();
        }
        OpticFinder _snowman7 = _snowman3.finder();
        return this.fixTypeEverywhereTyped("MissingDimensionFix", _snowman5, typed -> typed.updateTyped((OpticFinder)_snowman6, typed3 -> typed3.updateTyped(_snowman7, typed2 -> {
            if (!(typed2.getValue() instanceof List)) {
                throw new IllegalStateException("List exptected");
            }
            if (((List)typed2.getValue()).isEmpty()) {
                Dynamic dynamic = (Dynamic)typed.get(DSL.remainderFinder());
                Dynamic _snowman2 = this.method_29912(dynamic);
                return (Typed)DataFixUtils.orElse(_snowman3.readTyped(_snowman2).result().map(Pair::getFirst), (Object)typed2);
            }
            return typed2;
        })));
    }

    private <T> Dynamic<T> method_29912(Dynamic<T> dynamic) {
        long l = dynamic.get("seed").asLong(0L);
        return new Dynamic(dynamic.getOps(), StructureSeparationDataFix.method_29917(dynamic, l, StructureSeparationDataFix.method_29916(dynamic, l), false));
    }
}

