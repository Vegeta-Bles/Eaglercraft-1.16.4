/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.realms.gui.screen;

import java.util.List;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.realms.RealmsObjectSelectionList;
import net.minecraft.client.util.math.MatrixStack;

public abstract class RealmsAcceptRejectButton {
    public final int width;
    public final int height;
    public final int x;
    public final int y;

    public RealmsAcceptRejectButton(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public void render(MatrixStack matrices, int n, int n2, int n3, int n4) {
        _snowman = n + this.x;
        _snowman = n2 + this.y;
        boolean bl = false;
        if (n3 >= _snowman && n3 <= _snowman + this.width && n4 >= _snowman && n4 <= _snowman + this.height) {
            bl = true;
        }
        this.render(matrices, _snowman, _snowman, bl);
    }

    protected abstract void render(MatrixStack var1, int var2, int var3, boolean var4);

    public int getRight() {
        return this.x + this.width;
    }

    public int getBottom() {
        return this.y + this.height;
    }

    public abstract void handleClick(int var1);

    public static void render(MatrixStack matrices, List<RealmsAcceptRejectButton> list, RealmsObjectSelectionList<?> realmsObjectSelectionList, int n, int n2, int n3, int n4) {
        for (RealmsAcceptRejectButton realmsAcceptRejectButton : list) {
            if (realmsObjectSelectionList.getRowWidth() <= realmsAcceptRejectButton.getRight()) continue;
            realmsAcceptRejectButton.render(matrices, n, n2, n3, n4);
        }
    }

    public static void handleClick(RealmsObjectSelectionList<?> selectionList, AlwaysSelectedEntryListWidget.Entry<?> entry, List<RealmsAcceptRejectButton> buttons, int button, double mouseX, double mouseY) {
        if (button == 0 && (_snowman = selectionList.children().indexOf(entry)) > -1) {
            selectionList.setSelected(_snowman);
            int n = selectionList.getRowLeft();
            _snowman = selectionList.getRowTop(_snowman);
            _snowman = (int)(mouseX - (double)n);
            _snowman = (int)(mouseY - (double)_snowman);
            for (RealmsAcceptRejectButton realmsAcceptRejectButton : buttons) {
                if (_snowman < realmsAcceptRejectButton.x || _snowman > realmsAcceptRejectButton.getRight() || _snowman < realmsAcceptRejectButton.y || _snowman > realmsAcceptRejectButton.getBottom()) continue;
                realmsAcceptRejectButton.handleClick(_snowman);
            }
        }
    }
}

