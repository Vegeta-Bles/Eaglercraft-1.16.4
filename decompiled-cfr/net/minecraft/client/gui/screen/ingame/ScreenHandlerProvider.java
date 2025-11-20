/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen.ingame;

import net.minecraft.screen.ScreenHandler;

public interface ScreenHandlerProvider<T extends ScreenHandler> {
    public T getScreenHandler();
}

