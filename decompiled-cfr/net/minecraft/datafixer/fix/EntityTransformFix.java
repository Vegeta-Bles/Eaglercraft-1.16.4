/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.Typed
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.types.templates.TaggedChoice$TaggedChoiceType
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.DynamicOps
 */
package net.minecraft.datafixer.fix;

import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DynamicOps;
import net.minecraft.datafixer.TypeReferences;

public abstract class EntityTransformFix
extends DataFix {
    protected final String name;

    public EntityTransformFix(String name, Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
        this.name = name;
    }

    public TypeRewriteRule makeRule() {
        TaggedChoice.TaggedChoiceType taggedChoiceType = this.getInputSchema().findChoiceType(TypeReferences.ENTITY);
        _snowman = this.getOutputSchema().findChoiceType(TypeReferences.ENTITY);
        return this.fixTypeEverywhere(this.name, (Type)taggedChoiceType, (Type)_snowman, dynamicOps -> pair -> {
            String string = (String)pair.getFirst();
            Type _snowman2 = (Type)taggedChoiceType.types().get(string);
            Pair<String, Typed<?>> _snowman3 = this.transform(string, this.makeTyped(pair.getSecond(), (DynamicOps<?>)dynamicOps, (Type)_snowman2));
            Type _snowman4 = (Type)_snowman.types().get(_snowman3.getFirst());
            if (!_snowman4.equals((Object)((Typed)_snowman3.getSecond()).getType(), true, true)) {
                throw new IllegalStateException(String.format("Dynamic type check failed: %s not equal to %s", _snowman4, ((Typed)_snowman3.getSecond()).getType()));
            }
            return Pair.of((Object)_snowman3.getFirst(), (Object)((Typed)_snowman3.getSecond()).getValue());
        });
    }

    private <A> Typed<A> makeTyped(Object object, DynamicOps<?> dynamicOps, Type<A> type) {
        return new Typed(type, dynamicOps, object);
    }

    protected abstract Pair<String, Typed<?>> transform(String var1, Typed<?> var2);
}

