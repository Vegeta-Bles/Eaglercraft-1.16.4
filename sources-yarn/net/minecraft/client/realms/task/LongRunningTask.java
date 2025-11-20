package net.minecraft.client.realms.task;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.realms.gui.screen.RealmsLongRunningMcoTaskScreen;
import net.minecraft.client.realms.util.Errable;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
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
      MinecraftClient lv = MinecraftClient.getInstance();
      lv.execute(() -> lv.openScreen(screen));
   }

   public void setScreen(RealmsLongRunningMcoTaskScreen longRunningMcoTaskScreen) {
      this.longRunningMcoTaskScreen = longRunningMcoTaskScreen;
   }

   @Override
   public void error(Text arg) {
      this.longRunningMcoTaskScreen.error(arg);
   }

   public void setTitle(Text arg) {
      this.longRunningMcoTaskScreen.setTitle(arg);
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
