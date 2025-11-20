/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 */
package net.minecraft.client.realms.dto;

import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class ValueObject {
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");
        for (Field field : this.getClass().getFields()) {
            if (ValueObject.isStatic(field)) continue;
            try {
                stringBuilder.append(ValueObject.getName(field)).append("=").append(field.get(this)).append(" ");
            }
            catch (IllegalAccessException illegalAccessException) {
                // empty catch block
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    private static String getName(Field f) {
        SerializedName serializedName = f.getAnnotation(SerializedName.class);
        return serializedName != null ? serializedName.value() : f.getName();
    }

    private static boolean isStatic(Field f) {
        return Modifier.isStatic(f.getModifiers());
    }
}

