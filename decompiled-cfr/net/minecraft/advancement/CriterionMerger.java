/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.advancement;

import java.util.Collection;

public interface CriterionMerger {
    public static final CriterionMerger AND = collection -> {
        String[][] stringArray = new String[collection.size()][];
        int _snowman2 = 0;
        for (String string : collection) {
            stringArray[_snowman2++] = new String[]{string};
        }
        return stringArray;
    };
    public static final CriterionMerger OR = collection -> new String[][]{collection.toArray(new String[0])};

    public String[][] createRequirements(Collection<String> var1);
}

