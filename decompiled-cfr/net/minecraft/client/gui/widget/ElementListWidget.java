/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.widget;

import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.widget.EntryListWidget;

public abstract class ElementListWidget<E extends Entry<E>>
extends EntryListWidget<E> {
    public ElementListWidget(MinecraftClient minecraftClient, int n, int n2, int n3, int n4, int n5) {
        super(minecraftClient, n, n2, n3, n4, n5);
    }

    @Override
    public boolean changeFocus(boolean lookForwards) {
        boolean bl = super.changeFocus(lookForwards);
        if (bl) {
            this.ensureVisible(this.getFocused());
        }
        return bl;
    }

    @Override
    protected boolean isSelectedItem(int index) {
        return false;
    }

    public static abstract class Entry<E extends Entry<E>>
    extends EntryListWidget.Entry<E>
    implements ParentElement {
        @Nullable
        private Element focused;
        private boolean dragging;

        @Override
        public boolean isDragging() {
            return this.dragging;
        }

        @Override
        public void setDragging(boolean dragging) {
            this.dragging = dragging;
        }

        @Override
        public void setFocused(@Nullable Element focused) {
            this.focused = focused;
        }

        @Override
        @Nullable
        public Element getFocused() {
            return this.focused;
        }
    }
}

