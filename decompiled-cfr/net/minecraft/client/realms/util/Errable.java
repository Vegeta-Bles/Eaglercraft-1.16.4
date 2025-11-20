/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.realms.util;

import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public interface Errable {
    public void error(Text var1);

    default public void error(String string) {
        this.error(new LiteralText(string));
    }
}

