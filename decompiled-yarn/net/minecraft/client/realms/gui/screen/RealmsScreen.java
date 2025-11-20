package net.minecraft.client.realms.gui.screen;

import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TickableElement;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.realms.RealmsLabel;
import net.minecraft.client.util.NarratorManager;

public abstract class RealmsScreen extends Screen {
   public RealmsScreen() {
      super(NarratorManager.EMPTY);
   }

   protected static int row(int index) {
      return 40 + index * 13;
   }

   @Override
   public void tick() {
      for (AbstractButtonWidget _snowman : this.buttons) {
         if (_snowman instanceof TickableElement) {
            ((TickableElement)_snowman).tick();
         }
      }
   }

   public void narrateLabels() {
      List<String> _snowman = this.children
         .stream()
         .filter(RealmsLabel.class::isInstance)
         .map(RealmsLabel.class::cast)
         .map(RealmsLabel::getText)
         .collect(Collectors.toList());
      Realms.narrateNow(_snowman);
   }
}
