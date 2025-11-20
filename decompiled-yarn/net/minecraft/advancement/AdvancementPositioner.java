package net.minecraft.advancement;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

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

   public AdvancementPositioner(
      Advancement advancement, @Nullable AdvancementPositioner parent, @Nullable AdvancementPositioner previousSibling, int childrenSize, int depth
   ) {
      if (advancement.getDisplay() == null) {
         throw new IllegalArgumentException("Can't position an invisible advancement!");
      } else {
         this.advancement = advancement;
         this.parent = parent;
         this.previousSibling = previousSibling;
         this.childrenSize = childrenSize;
         this.optionalLast = this;
         this.depth = depth;
         this.row = -1.0F;
         AdvancementPositioner _snowman = null;

         for (Advancement _snowmanx : advancement.getChildren()) {
            _snowman = this.findChildrenRecursively(_snowmanx, _snowman);
         }
      }
   }

   @Nullable
   private AdvancementPositioner findChildrenRecursively(Advancement _snowman, @Nullable AdvancementPositioner lastChild) {
      if (_snowman.getDisplay() != null) {
         lastChild = new AdvancementPositioner(_snowman, this, lastChild, this.children.size() + 1, this.depth + 1);
         this.children.add(lastChild);
      } else {
         for (Advancement _snowmanx : _snowman.getChildren()) {
            lastChild = this.findChildrenRecursively(_snowmanx, lastChild);
         }
      }

      return lastChild;
   }

   private void calculateRecursively() {
      if (this.children.isEmpty()) {
         if (this.previousSibling != null) {
            this.row = this.previousSibling.row + 1.0F;
         } else {
            this.row = 0.0F;
         }
      } else {
         AdvancementPositioner _snowman = null;

         for (AdvancementPositioner _snowmanx : this.children) {
            _snowmanx.calculateRecursively();
            _snowman = _snowmanx.onFinishCalculation(_snowman == null ? _snowmanx : _snowman);
         }

         this.onFinishChildrenCalculation();
         float _snowmanx = (this.children.get(0).row + this.children.get(this.children.size() - 1).row) / 2.0F;
         if (this.previousSibling != null) {
            this.row = this.previousSibling.row + 1.0F;
            this.relativeRowInSiblings = this.row - _snowmanx;
         } else {
            this.row = _snowmanx;
         }
      }
   }

   private float findMinRowRecursively(float deltaRow, int depth, float minRow) {
      this.row += deltaRow;
      this.depth = depth;
      if (this.row < minRow) {
         minRow = this.row;
      }

      for (AdvancementPositioner _snowman : this.children) {
         minRow = _snowman.findMinRowRecursively(deltaRow + this.relativeRowInSiblings, depth + 1, minRow);
      }

      return minRow;
   }

   private void increaseRowRecursively(float deltaRow) {
      this.row += deltaRow;

      for (AdvancementPositioner _snowman : this.children) {
         _snowman.increaseRowRecursively(deltaRow);
      }
   }

   private void onFinishChildrenCalculation() {
      float _snowman = 0.0F;
      float _snowmanx = 0.0F;

      for (int _snowmanxx = this.children.size() - 1; _snowmanxx >= 0; _snowmanxx--) {
         AdvancementPositioner _snowmanxxx = this.children.get(_snowmanxx);
         _snowmanxxx.row += _snowman;
         _snowmanxxx.relativeRowInSiblings += _snowman;
         _snowmanx += _snowmanxxx.field_1266;
         _snowman += _snowmanxxx.field_1265 + _snowmanx;
      }
   }

   @Nullable
   private AdvancementPositioner getFirstChild() {
      if (this.substituteChild != null) {
         return this.substituteChild;
      } else {
         return !this.children.isEmpty() ? this.children.get(0) : null;
      }
   }

   @Nullable
   private AdvancementPositioner getLastChild() {
      if (this.substituteChild != null) {
         return this.substituteChild;
      } else {
         return !this.children.isEmpty() ? this.children.get(this.children.size() - 1) : null;
      }
   }

   private AdvancementPositioner onFinishCalculation(AdvancementPositioner last) {
      if (this.previousSibling == null) {
         return last;
      } else {
         AdvancementPositioner _snowman = this;
         AdvancementPositioner _snowmanx = this;
         AdvancementPositioner _snowmanxx = this.previousSibling;
         AdvancementPositioner _snowmanxxx = this.parent.children.get(0);
         float _snowmanxxxx = this.relativeRowInSiblings;
         float _snowmanxxxxx = this.relativeRowInSiblings;
         float _snowmanxxxxxx = _snowmanxx.relativeRowInSiblings;

         float _snowmanxxxxxxx;
         for (_snowmanxxxxxxx = _snowmanxxx.relativeRowInSiblings; _snowmanxx.getLastChild() != null && _snowman.getFirstChild() != null; _snowmanxxxxx += _snowmanx.relativeRowInSiblings) {
            _snowmanxx = _snowmanxx.getLastChild();
            _snowman = _snowman.getFirstChild();
            _snowmanxxx = _snowmanxxx.getFirstChild();
            _snowmanx = _snowmanx.getLastChild();
            _snowmanx.optionalLast = this;
            float _snowmanxxxxxxxx = _snowmanxx.row + _snowmanxxxxxx - (_snowman.row + _snowmanxxxx) + 1.0F;
            if (_snowmanxxxxxxxx > 0.0F) {
               _snowmanxx.getLast(this, last).pushDown(this, _snowmanxxxxxxxx);
               _snowmanxxxx += _snowmanxxxxxxxx;
               _snowmanxxxxx += _snowmanxxxxxxxx;
            }

            _snowmanxxxxxx += _snowmanxx.relativeRowInSiblings;
            _snowmanxxxx += _snowman.relativeRowInSiblings;
            _snowmanxxxxxxx += _snowmanxxx.relativeRowInSiblings;
         }

         if (_snowmanxx.getLastChild() != null && _snowmanx.getLastChild() == null) {
            _snowmanx.substituteChild = _snowmanxx.getLastChild();
            _snowmanx.relativeRowInSiblings += _snowmanxxxxxx - _snowmanxxxxx;
         } else {
            if (_snowman.getFirstChild() != null && _snowmanxxx.getFirstChild() == null) {
               _snowmanxxx.substituteChild = _snowman.getFirstChild();
               _snowmanxxx.relativeRowInSiblings += _snowmanxxxx - _snowmanxxxxxxx;
            }

            last = this;
         }

         return last;
      }
   }

   private void pushDown(AdvancementPositioner _snowman, float extraRowDistance) {
      float _snowmanx = (float)(_snowman.childrenSize - this.childrenSize);
      if (_snowmanx != 0.0F) {
         _snowman.field_1266 -= extraRowDistance / _snowmanx;
         this.field_1266 += extraRowDistance / _snowmanx;
      }

      _snowman.field_1265 += extraRowDistance;
      _snowman.row += extraRowDistance;
      _snowman.relativeRowInSiblings += extraRowDistance;
   }

   private AdvancementPositioner getLast(AdvancementPositioner _snowman, AdvancementPositioner _snowman) {
      return this.optionalLast != null && _snowman.parent.children.contains(this.optionalLast) ? this.optionalLast : _snowman;
   }

   private void apply() {
      if (this.advancement.getDisplay() != null) {
         this.advancement.getDisplay().setPosition((float)this.depth, this.row);
      }

      if (!this.children.isEmpty()) {
         for (AdvancementPositioner _snowman : this.children) {
            _snowman.apply();
         }
      }
   }

   public static void arrangeForTree(Advancement root) {
      if (root.getDisplay() == null) {
         throw new IllegalArgumentException("Can't position children of an invisible root!");
      } else {
         AdvancementPositioner _snowman = new AdvancementPositioner(root, null, null, 1, 0);
         _snowman.calculateRecursively();
         float _snowmanx = _snowman.findMinRowRecursively(0.0F, 0, _snowman.row);
         if (_snowmanx < 0.0F) {
            _snowman.increaseRowRecursively(-_snowmanx);
         }

         _snowman.apply();
      }
   }
}
