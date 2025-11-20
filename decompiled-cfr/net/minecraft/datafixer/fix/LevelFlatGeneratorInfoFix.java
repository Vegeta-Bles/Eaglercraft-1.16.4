/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.base.Splitter
 *  com.mojang.datafixers.DSL
 *  com.mojang.datafixers.DataFix
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.TypeRewriteRule
 *  com.mojang.datafixers.schemas.Schema
 *  com.mojang.serialization.Dynamic
 *  org.apache.commons.lang3.math.NumberUtils
 */
package net.minecraft.datafixer.fix;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.BlockStateFlattening;
import net.minecraft.datafixer.fix.EntityBlockStateFix;
import org.apache.commons.lang3.math.NumberUtils;

public class LevelFlatGeneratorInfoFix
extends DataFix {
    private static final Splitter SPLIT_ON_SEMICOLON = Splitter.on((char)';').limit(5);
    private static final Splitter SPLIT_ON_COMMA = Splitter.on((char)',');
    private static final Splitter SPLIT_ON_LOWER_X = Splitter.on((char)'x').limit(2);
    private static final Splitter SPLIT_ON_ASTERISK = Splitter.on((char)'*').limit(2);
    private static final Splitter SPLIT_ON_COLON = Splitter.on((char)':').limit(3);

    public LevelFlatGeneratorInfoFix(Schema outputSchema, boolean changesType) {
        super(outputSchema, changesType);
    }

    public TypeRewriteRule makeRule() {
        return this.fixTypeEverywhereTyped("LevelFlatGeneratorInfoFix", this.getInputSchema().getType(TypeReferences.LEVEL), typed -> typed.update(DSL.remainderFinder(), this::fixGeneratorOptions));
    }

    private Dynamic<?> fixGeneratorOptions(Dynamic<?> dynamic2) {
        if (dynamic2.get("generatorName").asString("").equalsIgnoreCase("flat")) {
            return dynamic2.update("generatorOptions", dynamic -> (Dynamic)DataFixUtils.orElse((Optional)dynamic.asString().map(this::fixFlatGeneratorOptions).map(arg_0 -> ((Dynamic)dynamic).createString(arg_0)).result(), (Object)dynamic));
        }
        return dynamic2;
    }

    @VisibleForTesting
    String fixFlatGeneratorOptions(String generatorOptions) {
        String _snowman3;
        if (generatorOptions.isEmpty()) {
            return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
        }
        Iterator iterator = SPLIT_ON_SEMICOLON.split((CharSequence)generatorOptions).iterator();
        String _snowman2 = (String)iterator.next();
        if (iterator.hasNext()) {
            int n = NumberUtils.toInt((String)_snowman2, (int)0);
            _snowman3 = (String)iterator.next();
        } else {
            n = 0;
            _snowman3 = _snowman2;
        }
        if (n < 0 || n > 3) {
            return "minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block;1;village";
        }
        StringBuilder stringBuilder = new StringBuilder();
        Splitter _snowman4 = n < 3 ? SPLIT_ON_LOWER_X : SPLIT_ON_ASTERISK;
        stringBuilder.append(StreamSupport.stream(SPLIT_ON_COMMA.split((CharSequence)_snowman3).spliterator(), false).map(string -> {
            String _snowman2;
            List list = _snowman4.splitToList((CharSequence)string);
            if (list.size() == 2) {
                int n2 = NumberUtils.toInt((String)((String)list.get(0)));
                _snowman2 = (String)list.get(1);
            } else {
                n2 = 1;
                _snowman2 = (String)list.get(0);
            }
            List list2 = SPLIT_ON_COLON.splitToList((CharSequence)_snowman2);
            int _snowman3 = ((String)list2.get(0)).equals("minecraft") ? 1 : 0;
            String _snowman4 = (String)list2.get(_snowman3);
            int _snowman5 = n == 3 ? EntityBlockStateFix.getNumericalBlockId("minecraft:" + _snowman4) : NumberUtils.toInt((String)_snowman4, (int)0);
            int _snowman6 = _snowman3 + 1;
            int _snowman7 = list2.size() > _snowman6 ? NumberUtils.toInt((String)((String)list2.get(_snowman6)), (int)0) : 0;
            return (n2 == 1 ? "" : n2 + "*") + BlockStateFlattening.lookupState(_snowman5 << 4 | _snowman7).get("Name").asString("");
        }).collect(Collectors.joining(",")));
        while (iterator.hasNext()) {
            stringBuilder.append(';').append((String)iterator.next());
        }
        return stringBuilder.toString();
    }
}

