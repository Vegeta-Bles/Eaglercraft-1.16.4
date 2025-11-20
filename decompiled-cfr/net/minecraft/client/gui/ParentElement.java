/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.gui.Element;

public interface ParentElement
extends Element {
    public List<? extends Element> children();

    default public Optional<Element> hoveredElement(double mouseX, double mouseY) {
        for (Element element : this.children()) {
            if (!element.isMouseOver(mouseX, mouseY)) continue;
            return Optional.of(element);
        }
        return Optional.empty();
    }

    @Override
    default public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Element element : this.children()) {
            if (!element.mouseClicked(mouseX, mouseY, button)) continue;
            this.setFocused(element);
            if (button == 0) {
                this.setDragging(true);
            }
            return true;
        }
        return false;
    }

    @Override
    default public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.setDragging(false);
        return this.hoveredElement(mouseX, mouseY).filter(element -> element.mouseReleased(mouseX, mouseY, button)).isPresent();
    }

    @Override
    default public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.getFocused() != null && this.isDragging() && button == 0) {
            return this.getFocused().mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
        return false;
    }

    public boolean isDragging();

    public void setDragging(boolean var1);

    @Override
    default public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return this.hoveredElement(mouseX, mouseY).filter(element -> element.mouseScrolled(mouseX, mouseY, amount)).isPresent();
    }

    @Override
    default public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return this.getFocused() != null && this.getFocused().keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    default public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return this.getFocused() != null && this.getFocused().keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    default public boolean charTyped(char chr, int keyCode) {
        return this.getFocused() != null && this.getFocused().charTyped(chr, keyCode);
    }

    @Nullable
    public Element getFocused();

    public void setFocused(@Nullable Element var1);

    default public void setInitialFocus(@Nullable Element element) {
        this.setFocused(element);
        element.changeFocus(true);
    }

    default public void focusOn(@Nullable Element element) {
        this.setFocused(element);
    }

    @Override
    default public boolean changeFocus(boolean lookForwards) {
        Element element = this.getFocused();
        boolean bl = _snowman = element != null;
        if (_snowman && element.changeFocus(lookForwards)) {
            return true;
        }
        List<? extends Element> _snowman2 = this.children();
        int _snowman3 = _snowman2.indexOf(element);
        int _snowman4 = _snowman && _snowman3 >= 0 ? _snowman3 + (lookForwards ? 1 : 0) : (lookForwards ? 0 : _snowman2.size());
        ListIterator<? extends Element> _snowman5 = _snowman2.listIterator(_snowman4);
        BooleanSupplier booleanSupplier = lookForwards ? _snowman5::hasNext : (_snowman = _snowman5::hasPrevious);
        Supplier<Element> supplier = lookForwards ? _snowman5::next : (_snowman = _snowman5::previous);
        while (_snowman.getAsBoolean()) {
            _snowman = _snowman.get();
            if (!_snowman.changeFocus(lookForwards)) continue;
            this.setFocused(_snowman);
            return true;
        }
        this.setFocused(null);
        return false;
    }
}

