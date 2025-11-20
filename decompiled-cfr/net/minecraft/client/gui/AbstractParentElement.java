/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui;

import javax.annotation.Nullable;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ParentElement;

public abstract class AbstractParentElement
extends DrawableHelper
implements ParentElement {
    @Nullable
    private Element focused;
    private boolean dragging;

    @Override
    public final boolean isDragging() {
        return this.dragging;
    }

    @Override
    public final void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    @Override
    @Nullable
    public Element getFocused() {
        return this.focused;
    }

    @Override
    public void setFocused(@Nullable Element focused) {
        this.focused = focused;
    }
}

