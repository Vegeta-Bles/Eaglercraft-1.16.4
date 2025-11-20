package net.minecraft;

import net.minecraft.client.toast.TutorialToast;

final class class_1156$class_5524 {
   private final TutorialToast field_26894;
   private final int field_26895;
   private int field_26896;

   private class_1156$class_5524(TutorialToast _snowman, int _snowman) {
      this.field_26894 = _snowman;
      this.field_26895 = _snowman;
   }

   private boolean method_31368() {
      this.field_26894.setProgress(Math.min((float)(++this.field_26896) / (float)this.field_26895, 1.0F));
      if (this.field_26896 > this.field_26895) {
         this.field_26894.hide();
         return true;
      } else {
         return false;
      }
   }
}
