/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.realms.gui.screen;

import javax.annotation.Nullable;
import net.minecraft.client.realms.dto.WorldTemplate;
import net.minecraft.client.realms.gui.screen.RealmsScreen;

public abstract class RealmsScreenWithCallback
extends RealmsScreen {
    protected abstract void callback(@Nullable WorldTemplate var1);
}

