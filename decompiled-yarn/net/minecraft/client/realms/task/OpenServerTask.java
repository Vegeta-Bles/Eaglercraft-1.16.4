package net.minecraft.client.realms.task;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.exception.RetryCallException;
import net.minecraft.client.realms.gui.screen.RealmsConfigureWorldScreen;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.text.TranslatableText;

public class OpenServerTask extends LongRunningTask {
   private final RealmsServer serverData;
   private final Screen returnScreen;
   private final boolean join;
   private final RealmsMainScreen mainScreen;

   public OpenServerTask(RealmsServer realmsServer, Screen returnScreen, RealmsMainScreen mainScreen, boolean join) {
      this.serverData = realmsServer;
      this.returnScreen = returnScreen;
      this.join = join;
      this.mainScreen = mainScreen;
   }

   @Override
   public void run() {
      this.setTitle(new TranslatableText("mco.configure.world.opening"));
      RealmsClient _snowman = RealmsClient.createRealmsClient();

      for (int _snowmanx = 0; _snowmanx < 25; _snowmanx++) {
         if (this.aborted()) {
            return;
         }

         try {
            boolean _snowmanxx = _snowman.open(this.serverData.id);
            if (_snowmanxx) {
               if (this.returnScreen instanceof RealmsConfigureWorldScreen) {
                  ((RealmsConfigureWorldScreen)this.returnScreen).stateChanged();
               }

               this.serverData.state = RealmsServer.State.OPEN;
               if (this.join) {
                  this.mainScreen.play(this.serverData, this.returnScreen);
               } else {
                  setScreen(this.returnScreen);
               }
               break;
            }
         } catch (RetryCallException var4) {
            if (this.aborted()) {
               return;
            }

            pause(var4.delaySeconds);
         } catch (Exception var5) {
            if (this.aborted()) {
               return;
            }

            LOGGER.error("Failed to open server", var5);
            this.error("Failed to open the server");
         }
      }
   }
}
