/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.util;

import java.util.Locale;
import net.minecraft.util.Identifier;

public class ModelIdentifier
extends Identifier {
    private final String variant;

    protected ModelIdentifier(String[] stringArray) {
        super(stringArray);
        this.variant = stringArray[2].toLowerCase(Locale.ROOT);
    }

    public ModelIdentifier(String string) {
        this(ModelIdentifier.split(string));
    }

    public ModelIdentifier(Identifier id, String variant) {
        this(id.toString(), variant);
    }

    public ModelIdentifier(String string, String string2) {
        this(ModelIdentifier.split(string + '#' + string2));
    }

    protected static String[] split(String id) {
        String[] stringArray = new String[]{null, id, ""};
        int _snowman2 = id.indexOf(35);
        String _snowman3 = id;
        if (_snowman2 >= 0) {
            stringArray[2] = id.substring(_snowman2 + 1, id.length());
            if (_snowman2 > 1) {
                _snowman3 = id.substring(0, _snowman2);
            }
        }
        System.arraycopy(Identifier.split(_snowman3, ':'), 0, stringArray, 0, 2);
        return stringArray;
    }

    public String getVariant() {
        return this.variant;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof ModelIdentifier && super.equals(object)) {
            ModelIdentifier modelIdentifier = (ModelIdentifier)object;
            return this.variant.equals(modelIdentifier.variant);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 31 * super.hashCode() + this.variant.hashCode();
    }

    @Override
    public String toString() {
        return super.toString() + '#' + this.variant;
    }
}

