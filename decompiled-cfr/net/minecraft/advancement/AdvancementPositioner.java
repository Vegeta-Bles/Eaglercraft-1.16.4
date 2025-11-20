/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.advancement;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancement.Advancement;

public class AdvancementPositioner {
    private final Advancement advancement;
    private final AdvancementPositioner parent;
    private final AdvancementPositioner previousSibling;
    private final int childrenSize;
    private final List<AdvancementPositioner> children = Lists.newArrayList();
    private AdvancementPositioner optionalLast;
    private AdvancementPositioner substituteChild;
    private int depth;
    private float row;
    private float relativeRowInSiblings;
    private float field_1266;
    private float field_1265;

    public AdvancementPositioner(Advancement advancement, @Nullable AdvancementPositioner parent, @Nullable AdvancementPositioner previousSibling, int childrenSize, int depth) {
        if (advancement.getDisplay() == null) {
            throw new IllegalArgumentException("Can't position an invisible advancement!");
        }
        this.advancement = advancement;
        this.parent = parent;
        this.previousSibling = previousSibling;
        this.childrenSize = childrenSize;
        this.optionalLast = this;
        this.depth = depth;
        this.row = -1.0f;
        AdvancementPositioner advancementPositioner = null;
        for (Advancement advancement2 : advancement.getChildren()) {
            advancementPositioner = this.findChildrenRecursively(advancement2, advancementPositioner);
        }
    }

    @Nullable
    private AdvancementPositioner findChildrenRecursively(Advancement advancement, @Nullable AdvancementPositioner lastChild) {
        if (advancement.getDisplay() != null) {
            lastChild = new AdvancementPositioner(advancement, this, lastChild, this.children.size() + 1, this.depth + 1);
            this.children.add(lastChild);
        } else {
            for (Advancement advancement2 : advancement.getChildren()) {
                lastChild = this.findChildrenRecursively(advancement2, lastChild);
            }
        }
        return lastChild;
    }

    private void calculateRecursively() {
        if (this.children.isEmpty()) {
            this.row = this.previousSibling != null ? this.previousSibling.row + 1.0f : 0.0f;
            return;
        }
        AdvancementPositioner advancementPositioner = null;
        for (AdvancementPositioner advancementPositioner2 : this.children) {
            advancementPositioner2.calculateRecursively();
            advancementPositioner = advancementPositioner2.onFinishCalculation(advancementPositioner == null ? advancementPositioner2 : advancementPositioner);
        }
        this.onFinishChildrenCalculation();
        float _snowman2 = (this.children.get((int)0).row + this.children.get((int)(this.children.size() - 1)).row) / 2.0f;
        if (this.previousSibling != null) {
            this.row = this.previousSibling.row + 1.0f;
            this.relativeRowInSiblings = this.row - _snowman2;
        } else {
            this.row = _snowman2;
        }
    }

    private float findMinRowRecursively(float deltaRow, int depth, float minRow) {
        this.row += deltaRow;
        this.depth = depth;
        if (this.row < minRow) {
            minRow = this.row;
        }
        for (AdvancementPositioner advancementPositioner : this.children) {
            minRow = advancementPositioner.findMinRowRecursively(deltaRow + this.relativeRowInSiblings, depth + 1, minRow);
        }
        return minRow;
    }

    private void increaseRowRecursively(float deltaRow) {
        this.row += deltaRow;
        for (AdvancementPositioner advancementPositioner : this.children) {
            advancementPositioner.increaseRowRecursively(deltaRow);
        }
    }

    private void onFinishChildrenCalculation() {
        float f = 0.0f;
        _snowman = 0.0f;
        for (int i = this.children.size() - 1; i >= 0; --i) {
            AdvancementPositioner advancementPositioner = this.children.get(i);
            advancementPositioner.row += f;
            advancementPositioner.relativeRowInSiblings += f;
            f += advancementPositioner.field_1265 + (_snowman += advancementPositioner.field_1266);
        }
    }

    @Nullable
    private AdvancementPositioner getFirstChild() {
        if (this.substituteChild != null) {
            return this.substituteChild;
        }
        if (!this.children.isEmpty()) {
            return this.children.get(0);
        }
        return null;
    }

    @Nullable
    private AdvancementPositioner getLastChild() {
        if (this.substituteChild != null) {
            return this.substituteChild;
        }
        if (!this.children.isEmpty()) {
            return this.children.get(this.children.size() - 1);
        }
        return null;
    }

    private AdvancementPositioner onFinishCalculation(AdvancementPositioner last) {
        AdvancementPositioner advancementPositioner;
        if (this.previousSibling == null) {
            return last;
        }
        AdvancementPositioner advancementPositioner2 = this;
        _snowman = this;
        advancementPositioner = this.previousSibling;
        _snowman = this.parent.children.get(0);
        float _snowman2 = this.relativeRowInSiblings;
        float _snowman3 = this.relativeRowInSiblings;
        float _snowman4 = advancementPositioner.relativeRowInSiblings;
        float _snowman5 = _snowman.relativeRowInSiblings;
        while (advancementPositioner.getLastChild() != null && advancementPositioner2.getFirstChild() != null) {
            advancementPositioner = advancementPositioner.getLastChild();
            advancementPositioner2 = advancementPositioner2.getFirstChild();
            _snowman = _snowman.getFirstChild();
            _snowman = _snowman.getLastChild();
            _snowman.optionalLast = this;
            float f = advancementPositioner.row + _snowman4 - (advancementPositioner2.row + _snowman2) + 1.0f;
            if (f > 0.0f) {
                advancementPositioner.getLast(this, last).pushDown(this, f);
                _snowman2 += f;
                _snowman3 += f;
            }
            _snowman4 += advancementPositioner.relativeRowInSiblings;
            _snowman2 += advancementPositioner2.relativeRowInSiblings;
            _snowman5 += _snowman.relativeRowInSiblings;
            _snowman3 += _snowman.relativeRowInSiblings;
        }
        if (advancementPositioner.getLastChild() != null && _snowman.getLastChild() == null) {
            _snowman.substituteChild = advancementPositioner.getLastChild();
            _snowman.relativeRowInSiblings += _snowman4 - _snowman3;
        } else {
            if (advancementPositioner2.getFirstChild() != null && _snowman.getFirstChild() == null) {
                _snowman.substituteChild = advancementPositioner2.getFirstChild();
                _snowman.relativeRowInSiblings += _snowman2 - _snowman5;
            }
            last = this;
        }
        return last;
    }

    private void pushDown(AdvancementPositioner advancementPositioner, float extraRowDistance) {
        float f = advancementPositioner.childrenSize - this.childrenSize;
        if (f != 0.0f) {
            advancementPositioner.field_1266 -= extraRowDistance / f;
            this.field_1266 += extraRowDistance / f;
        }
        advancementPositioner.field_1265 += extraRowDistance;
        advancementPositioner.row += extraRowDistance;
        advancementPositioner.relativeRowInSiblings += extraRowDistance;
    }

    private AdvancementPositioner getLast(AdvancementPositioner advancementPositioner, AdvancementPositioner advancementPositioner2) {
        if (this.optionalLast != null && advancementPositioner.parent.children.contains(this.optionalLast)) {
            return this.optionalLast;
        }
        return advancementPositioner2;
    }

    private void apply() {
        if (this.advancement.getDisplay() != null) {
            this.advancement.getDisplay().setPosition(this.depth, this.row);
        }
        if (!this.children.isEmpty()) {
            for (AdvancementPositioner advancementPositioner : this.children) {
                advancementPositioner.apply();
            }
        }
    }

    public static void arrangeForTree(Advancement root) {
        if (root.getDisplay() == null) {
            throw new IllegalArgumentException("Can't position children of an invisible root!");
        }
        AdvancementPositioner advancementPositioner = new AdvancementPositioner(root, null, null, 1, 0);
        advancementPositioner.calculateRecursively();
        float _snowman2 = advancementPositioner.findMinRowRecursively(0.0f, 0, advancementPositioner.row);
        if (_snowman2 < 0.0f) {
            advancementPositioner.increaseRowRecursively(-_snowman2);
        }
        advancementPositioner.apply();
    }
}

