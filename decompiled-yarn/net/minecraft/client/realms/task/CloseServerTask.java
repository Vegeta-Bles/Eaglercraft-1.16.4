package net.minecraft.client.realms.task;

import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.exception.RetryCallException;
import net.minecraft.client.realms.gui.screen.RealmsConfigureWorldScreen;
import net.minecraft.text.TranslatableText;

public class CloseServerTask extends LongRunningTask {
   private final RealmsServer serverData;
   private final RealmsConfigureWorldScreen configureScreen;

   public CloseServerTask(RealmsServer realmsServer, RealmsConfigureWorldScreen configureWorldScreen) {
      this.serverData = realmsServer;
      this.configureScreen = configureWorldScreen;
   }

   @Override
   public void run() {
      this.setTitle(new TranslatableText("mco.configure.world.closing"));
      RealmsClient _snowman = RealmsClient.createRealmsClient();

      for (int _snowmanx = 0; _snowmanx < 25; _snowmanx++) {
         if (this.aborted()) {
            return;
         }

         try {
            boolean _snowmanxx = _snowman.close(this.serverData.id);
            if (_snowmanxx) {
               this.configureScreen.stateChanged();
               this.serverData.state = RealmsServer.State.CLOSED;
               setScreen(this.configureScreen);
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

            LOGGER.error("Failed to close server", var5);
            this.error("Failed to close the server");
         }
      }
   }
}
