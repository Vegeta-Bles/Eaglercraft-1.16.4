/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.OpticFinder
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.datafixers.types.Type
 *  com.mojang.datafixers.util.Either
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.Dynamic
 */
package net.minecraft.datafixer.fix;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.datafixer.TypeReferences;

public class EntityEquipmentToArmorAndHandFix
extends DataFix {
    public EntityEquipmentToArmorAndHandFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    public TypeRewriteRule makeRule() {
        return this.fixEquipment(this.getInputSchema().getTypeRaw(TypeReferences.ITEM_STACK));
    }

    private <IS> TypeRewriteRule fixEquipment(Type<IS> type) {
        Type type2 = DSL.and((Type)DSL.optional((Type)DSL.field((String)"Equipment", (Type)DSL.list(type))), (Type)DSL.remainderType());
        _snowman = DSL.and((Type)DSL.optional((Type)DSL.field((String)"ArmorItems", (Type)DSL.list(type))), (Type)DSL.optional((Type)DSL.field((String)"HandItems", (Type)DSL.list(type))), (Type)DSL.remainderType());
        OpticFinder _snowman2 = DSL.typeFinder((Type)type2);
        OpticFinder _snowman3 = DSL.fieldFinder((String)"Equipment", (Type)DSL.list(type));
        return this.fixTypeEverywhereTyped("EntityEquipmentToArmorAndHandFix", this.getInputSchema().getType(TypeReferences.ENTITY), this.getOutputSchema().getType(TypeReferences.ENTITY), typed -> {
            Either _snowman4;
            Object object;
            Either either = Either.right((Object)DSL.unit());
            _snowman4 = Either.right((Object)DSL.unit());
            Dynamic _snowman2 = (Dynamic)typed.getOrCreate(DSL.remainderFinder());
            Optional _snowman3 = typed.getOptional(_snowman3);
            if (_snowman3.isPresent()) {
                Object object2 = (List)_snowman3.get();
                _snowman = ((Pair)type.read(_snowman2.emptyMap()).result().orElseThrow(() -> new IllegalStateException("Could not parse newly created empty itemstack."))).getFirst();
                if (!object2.isEmpty()) {
                    either = Either.left((Object)Lists.newArrayList((Object[])new Object[]{object2.get(0), _snowman}));
                }
                if (object2.size() > 1) {
                    object = Lists.newArrayList((Object[])new Object[]{_snowman, _snowman, _snowman, _snowman});
                    for (int i = 1; i < Math.min(object2.size(), 5); ++i) {
                        object.set(i - 1, object2.get(i));
                    }
                    _snowman4 = Either.left((Object)object);
                }
            }
            object2 = _snowman2;
            _snowman = _snowman2.get("DropChances").asStreamOpt().result();
            if (((Optional)_snowman).isPresent()) {
                Dynamic _snowman5;
                object = Stream.concat((Stream)((Optional)_snowman).get(), Stream.generate(() -> EntityEquipmentToArmorAndHandFix.method_15701((Dynamic)object2))).iterator();
                float f = ((Dynamic)object.next()).asFloat(0.0f);
                if (!_snowman2.get("HandDropChances").result().isPresent()) {
                    _snowman5 = _snowman2.createList(Stream.of(Float.valueOf(f), Float.valueOf(0.0f)).map(arg_0 -> ((Dynamic)_snowman2).createFloat(arg_0)));
                    _snowman2 = _snowman2.set("HandDropChances", _snowman5);
                }
                if (!_snowman2.get("ArmorDropChances").result().isPresent()) {
                    _snowman5 = _snowman2.createList(Stream.of(Float.valueOf(((Dynamic)object.next()).asFloat(0.0f)), Float.valueOf(((Dynamic)object.next()).asFloat(0.0f)), Float.valueOf(((Dynamic)object.next()).asFloat(0.0f)), Float.valueOf(((Dynamic)object.next()).asFloat(0.0f))).map(arg_0 -> ((Dynamic)_snowman2).createFloat(arg_0)));
                    _snowman2 = _snowman2.set("ArmorDropChances", _snowman5);
                }
                _snowman2 = _snowman2.remove("DropChances");
            }
            return typed.set(_snowman2, _snowman, (Object)Pair.of((Object)either, (Object)Pair.of((Object)_snowman4, (Object)_snowman2)));
        });
    }

    private static /* synthetic */ Dynamic method_15701(Dynamic dynamic) {
        return dynamic.createInt(0);
    }
}

