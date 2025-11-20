package net.minecraft.client.realms.task;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.realms.gui.screen.RealmsLongRunningMcoTaskScreen;
import net.minecraft.client.realms.util.Errable;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class LongRunningTask implements Errable, Runnable {
   public static final Logger LOGGER = LogManager.getLogger();
   protected RealmsLongRunningMcoTaskScreen longRunningMcoTaskScreen;

   public LongRunningTask() {
   }

   protected static void pause(int seconds) {
      try {
         Thread.sleep((long)(seconds * 1000));
      } catch (InterruptedException var2) {
         LOGGER.error("", var2);
      }
   }

   public static void setScreen(Screen screen) {
      MinecraftClient _snowman = MinecraftClient.getInstance();
      _snowman.execute(() -> _snowman.openScreen(screen));
   }

   public void setScreen(RealmsLongRunningMcoTaskScreen longRunningMcoTaskScreen) {
      this.longRunningMcoTaskScreen = longRunningMcoTaskScreen;
   }

   @Override
   public void error(Text _snowman) {
      this.longRunningMcoTaskScreen.error(_snowman);
   }

   public void setTitle(Text _snowman) {
      this.longRunningMcoTaskScreen.setTitle(_snowman);
   }

   public boolean aborted() {
      return this.longRunningMcoTaskScreen.aborted();
   }

   public void tick() {
   }

   public void init() {
   }

   public void abortTask() {
   }
}
