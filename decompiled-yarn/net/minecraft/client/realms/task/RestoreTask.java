package net.minecraft.client.realms.task;

import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.dto.Backup;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.exception.RetryCallException;
import net.minecraft.client.realms.gui.screen.RealmsConfigureWorldScreen;
import net.minecraft.client.realms.gui.screen.RealmsGenericErrorScreen;
import net.minecraft.text.TranslatableText;

public class RestoreTask extends LongRunningTask {
   private final Backup backup;
   private final long worldId;
   private final RealmsConfigureWorldScreen lastScreen;

   public RestoreTask(Backup backup, long worldId, RealmsConfigureWorldScreen lastScreen) {
      this.backup = backup;
      this.worldId = worldId;
      this.lastScreen = lastScreen;
   }

   @Override
   public void run() {
      this.setTitle(new TranslatableText("mco.backup.restoring"));
      RealmsClient _snowman = RealmsClient.createRealmsClient();
      int _snowmanx = 0;

      while (_snowmanx < 25) {
         try {
            if (this.aborted()) {
               return;
            }

            _snowman.restoreWorld(this.worldId, this.backup.backupId);
            pause(1);
            if (this.aborted()) {
               return;
            }

            setScreen(this.lastScreen.getNewScreen());
            return;
         } catch (RetryCallException var4) {
            if (this.aborted()) {
               return;
            }

            pause(var4.delaySeconds);
            _snowmanx++;
         } catch (RealmsServiceException var5) {
            if (this.aborted()) {
               return;
            }

            LOGGER.error("Couldn't restore backup", var5);
            setScreen(new RealmsGenericErrorScreen(var5, this.lastScreen));
            return;
         } catch (Exception var6) {
            if (this.aborted()) {
               return;
            }

            LOGGER.error("Couldn't restore backup", var6);
            this.error(var6.getLocalizedMessage());
            return;
         }
      }
   }
}
