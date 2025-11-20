/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.datafixer.fix;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.datafixer.TypeReferences;

public class EntityRidingToPassengerFix
extends DataFix {
    public EntityRidingToPassengerFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    public TypeRewriteRule makeRule() {
        Schema schema = this.getInputSchema();
        _snowman = this.getOutputSchema();
        Type _snowman2 = schema.getTypeRaw(TypeReferences.ENTITY_TREE);
        Type _snowman3 = _snowman.getTypeRaw(TypeReferences.ENTITY_TREE);
        Type _snowman4 = schema.getTypeRaw(TypeReferences.ENTITY);
        return this.fixEntityTree(schema, _snowman, _snowman2, _snowman3, _snowman4);
    }

    private <OldEntityTree, NewEntityTree, Entity> TypeRewriteRule fixEntityTree(Schema inputSchema, Schema outputSchema, Type<OldEntityTree> inputEntityTreeType, Type<NewEntityTree> outputEntityTreeType, Type<Entity> inputEntityType) {
        Type type = DSL.named((String)TypeReferences.ENTITY_TREE.typeName(), (Type)DSL.and((Type)DSL.optional((Type)DSL.field((String)"Riding", inputEntityTreeType)), inputEntityType));
        _snowman = DSL.named((String)TypeReferences.ENTITY_TREE.typeName(), (Type)DSL.and((Type)DSL.optional((Type)DSL.field((String)"Passengers", (Type)DSL.list(outputEntityTreeType))), inputEntityType));
        _snowman = inputSchema.getType(TypeReferences.ENTITY_TREE);
        _snowman = outputSchema.getType(TypeReferences.ENTITY_TREE);
        if (!Objects.equals(_snowman, type)) {
            throw new IllegalStateException("Old entity type is not what was expected.");
        }
        if (!_snowman.equals((Object)_snowman, true, true)) {
            throw new IllegalStateException("New entity type is not what was expected.");
        }
        OpticFinder _snowman2 = DSL.typeFinder((Type)type);
        OpticFinder _snowman3 = DSL.typeFinder((Type)_snowman);
        OpticFinder _snowman4 = DSL.typeFinder(outputEntityTreeType);
        _snowman = inputSchema.getType(TypeReferences.PLAYER);
        _snowman = outputSchema.getType(TypeReferences.PLAYER);
        return TypeRewriteRule.seq((TypeRewriteRule)this.fixTypeEverywhere("EntityRidingToPassengerFix", type, _snowman, dynamicOps -> pair2 -> {
            Optional<Object> _snowman3 = Optional.empty();
            Pair _snowman2 = pair2;
            while (true) {
                Either either = (Either)DataFixUtils.orElse(_snowman3.map(pair -> {
                    Typed typed = (Typed)outputEntityTreeType.pointTyped(dynamicOps).orElseThrow(() -> new IllegalStateException("Could not create new entity tree"));
                    Object _snowman2 = typed.set(_snowman3, pair).getOptional(_snowman4).orElseThrow(() -> new IllegalStateException("Should always have an entity tree here"));
                    return Either.left((Object)ImmutableList.of(_snowman2));
                }), (Object)Either.right((Object)DSL.unit()));
                _snowman3 = Optional.of(Pair.of((Object)TypeReferences.ENTITY_TREE.typeName(), (Object)Pair.of((Object)either, (Object)((Pair)_snowman2.getSecond()).getSecond())));
                Optional _snowman4 = ((Either)((Pair)_snowman2.getSecond()).getFirst()).left();
                if (!_snowman4.isPresent()) break;
                _snowman2 = (Pair)new Typed(inputEntityTreeType, dynamicOps, _snowman4.get()).getOptional(_snowman2).orElseThrow(() -> new IllegalStateException("Should always have an entity here"));
            }
            return (Pair)_snowman3.orElseThrow(() -> new IllegalStateException("Should always have an entity tree here"));
        }), (TypeRewriteRule)this.writeAndRead("player RootVehicle injecter", _snowman, _snowman));
    }
}

