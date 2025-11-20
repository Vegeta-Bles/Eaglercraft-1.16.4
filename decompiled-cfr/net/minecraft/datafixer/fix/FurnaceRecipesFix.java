/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.datafixers.util.Unit
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.datafixer.fix;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;

public class FurnaceRecipesFix
extends DataFix {
    public FurnaceRecipesFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    protected TypeRewriteRule makeRule() {
        return this.updateBlockEntities(this.getOutputSchema().getTypeRaw(TypeReferences.RECIPE));
    }

    private <R> TypeRewriteRule updateBlockEntities(Type<R> type) {
        Type type2 = DSL.and((Type)DSL.optional((Type)DSL.field((String)"RecipesUsed", (Type)DSL.and((Type)DSL.compoundList(type, (Type)DSL.intType()), (Type)DSL.remainderType()))), (Type)DSL.remainderType());
        OpticFinder _snowman2 = DSL.namedChoice((String)"minecraft:furnace", (Type)this.getInputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:furnace"));
        OpticFinder _snowman3 = DSL.namedChoice((String)"minecraft:blast_furnace", (Type)this.getInputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:blast_furnace"));
        OpticFinder _snowman4 = DSL.namedChoice((String)"minecraft:smoker", (Type)this.getInputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:smoker"));
        _snowman = this.getOutputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:furnace");
        _snowman = this.getOutputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:blast_furnace");
        _snowman = this.getOutputSchema().getChoiceType(TypeReferences.BLOCK_ENTITY, "minecraft:smoker");
        _snowman = this.getInputSchema().getType(TypeReferences.BLOCK_ENTITY);
        _snowman = this.getOutputSchema().getType(TypeReferences.BLOCK_ENTITY);
        return this.fixTypeEverywhereTyped("FurnaceRecipesFix", _snowman, _snowman, typed2 -> typed2.updateTyped(_snowman2, _snowman, typed -> this.updateBlockEntityData(type, (Type)type2, (Typed<?>)typed)).updateTyped(_snowman3, _snowman, typed -> this.updateBlockEntityData(type, (Type)type2, (Typed<?>)typed)).updateTyped(_snowman4, _snowman, typed -> this.updateBlockEntityData(type, (Type)type2, (Typed<?>)typed)));
    }

    private <R> Typed<?> updateBlockEntityData(Type<R> type, Type<Pair<Either<Pair<List<Pair<R, Integer>>, Dynamic<?>>, Unit>, Dynamic<?>>> type2, Typed<?> typed) {
        Dynamic _snowman6 = (Dynamic)typed.getOrCreate(DSL.remainderFinder());
        int _snowman2 = _snowman6.get("RecipesUsedSize").asInt(0);
        _snowman6 = _snowman6.remove("RecipesUsedSize");
        ArrayList _snowman3 = Lists.newArrayList();
        for (int i = 0; i < _snowman2; ++i) {
            String string = "RecipeLocation" + i;
            _snowman = "RecipeAmount" + i;
            Optional _snowman4 = _snowman6.get(string).result();
            int _snowman5 = _snowman6.get(_snowman).asInt(0);
            if (_snowman5 > 0) {
                _snowman4.ifPresent(dynamic -> {
                    Optional optional = type.read(dynamic).result();
                    optional.ifPresent(pair -> _snowman3.add(Pair.of((Object)pair.getFirst(), (Object)_snowman5)));
                });
            }
            _snowman6 = _snowman6.remove(string).remove(_snowman);
        }
        return typed.set(DSL.remainderFinder(), type2, (Object)Pair.of((Object)Either.left((Object)Pair.of((Object)_snowman3, (Object)_snowman6.emptyMap())), (Object)_snowman6));
    }
}

