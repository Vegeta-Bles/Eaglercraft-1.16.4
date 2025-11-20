/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.font;

import javax.annotation.Nullable;
import net.minecraft.client.font.Font;
import net.minecraft.resource.ResourceManager;

public interface FontLoader {
    @Nullable
    public Font load(ResourceManager var1);
}

