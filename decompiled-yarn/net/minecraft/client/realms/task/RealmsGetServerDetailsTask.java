package net.minecraft.client.realms.task;

import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.dto.RealmsServerAddress;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.exception.RetryCallException;
import net.minecraft.client.realms.gui.screen.RealmsBrokenWorldScreen;
import net.minecraft.client.realms.gui.screen.RealmsGenericErrorScreen;
import net.minecraft.client.realms.gui.screen.RealmsLongConfirmationScreen;
import net.minecraft.client.realms.gui.screen.RealmsLongRunningMcoTaskScreen;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.client.realms.gui.screen.RealmsTermsScreen;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class RealmsGetServerDetailsTask extends LongRunningTask {
   private final RealmsServer server;
   private final Screen lastScreen;
   private final RealmsMainScreen mainScreen;
   private final ReentrantLock connectLock;

   public RealmsGetServerDetailsTask(RealmsMainScreen mainScreen, Screen lastScreen, RealmsServer server, ReentrantLock connectLock) {
      this.lastScreen = lastScreen;
      this.mainScreen = mainScreen;
      this.server = server;
      this.connectLock = connectLock;
   }

   @Override
   public void run() {
      this.setTitle(new TranslatableText("mco.connect.connecting"));
      RealmsClient _snowman = RealmsClient.createRealmsClient();
      boolean _snowmanx = false;
      boolean _snowmanxx = false;
      int _snowmanxxx = 5;
      RealmsServerAddress _snowmanxxxx = null;
      boolean _snowmanxxxxx = false;
      boolean _snowmanxxxxxx = false;

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 40 && !this.aborted(); _snowmanxxxxxxx++) {
         try {
            _snowmanxxxx = _snowman.join(this.server.id);
            _snowmanx = true;
         } catch (RetryCallException var11) {
            _snowmanxxx = var11.delaySeconds;
         } catch (RealmsServiceException var12) {
            if (var12.errorCode == 6002) {
               _snowmanxxxxx = true;
            } else if (var12.errorCode == 6006) {
               _snowmanxxxxxx = true;
            } else {
               _snowmanxx = true;
               this.error(var12.toString());
               LOGGER.error("Couldn't connect to world", var12);
            }
            break;
         } catch (Exception var13) {
            _snowmanxx = true;
            LOGGER.error("Couldn't connect to world", var13);
            this.error(var13.getLocalizedMessage());
            break;
         }

         if (_snowmanx) {
            break;
         }

         this.sleep(_snowmanxxx);
      }

      if (_snowmanxxxxx) {
         setScreen(new RealmsTermsScreen(this.lastScreen, this.mainScreen, this.server));
      } else if (_snowmanxxxxxx) {
         if (this.server.ownerUUID.equals(MinecraftClient.getInstance().getSession().getUuid())) {
            setScreen(new RealmsBrokenWorldScreen(this.lastScreen, this.mainScreen, this.server.id, this.server.worldType == RealmsServer.WorldType.MINIGAME));
         } else {
            setScreen(
               new RealmsGenericErrorScreen(
                  new TranslatableText("mco.brokenworld.nonowner.title"), new TranslatableText("mco.brokenworld.nonowner.error"), this.lastScreen
               )
            );
         }
      } else if (!this.aborted() && !_snowmanxx) {
         if (_snowmanx) {
            RealmsServerAddress _snowmanxxxxxxx = _snowmanxxxx;
            if (_snowmanxxxxxxx.resourcePackUrl != null && _snowmanxxxxxxx.resourcePackHash != null) {
               Text _snowmanxxxxxxxx = new TranslatableText("mco.configure.world.resourcepack.question.line1");
               Text _snowmanxxxxxxxxx = new TranslatableText("mco.configure.world.resourcepack.question.line2");
               setScreen(
                  new RealmsLongConfirmationScreen(
                     _snowmanxxxxxxxxxx -> {
                        try {
                           if (_snowmanxxxxxxxxxx) {
                              Function<Throwable, Void> _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxxxx -> {
                                 MinecraftClient.getInstance().getResourcePackDownloader().clear();
                                 LOGGER.error(_snowmanxxxxxxxxxxxx);
                                 setScreen(new RealmsGenericErrorScreen(new LiteralText("Failed to download resource pack!"), this.lastScreen));
                                 return null;
                              };

                              try {
                                 MinecraftClient.getInstance()
                                    .getResourcePackDownloader()
                                    .download(_snowman.resourcePackUrl, _snowman.resourcePackHash)
                                    .thenRun(
                                       () -> this.setScreen(
                                             new RealmsLongRunningMcoTaskScreen(this.lastScreen, new RealmsConnectTask(this.lastScreen, this.server, _snowman))
                                          )
                                    )
                                    .exceptionally(_snowmanxxxxxxxxxxx);
                              } catch (Exception var8x) {
                                 _snowmanxxxxxxxxxxx.apply(var8x);
                              }
                           } else {
                              setScreen(this.lastScreen);
                           }
                        } finally {
                           if (this.connectLock != null && this.connectLock.isHeldByCurrentThread()) {
                              this.connectLock.unlock();
                           }
                        }
                     },
                     RealmsLongConfirmationScreen.Type.Info,
                     _snowmanxxxxxxxx,
                     _snowmanxxxxxxxxx,
                     true
                  )
               );
            } else {
               this.setScreen(new RealmsLongRunningMcoTaskScreen(this.lastScreen, new RealmsConnectTask(this.lastScreen, this.server, _snowmanxxxxxxx)));
            }
         } else {
            this.error(new TranslatableText("mco.errorMessage.connectionFailure"));
         }
      }
   }

   private void sleep(int sleepTimeSeconds) {
      try {
         Thread.sleep((long)(sleepTimeSeconds * 1000));
      } catch (InterruptedException var3) {
         LOGGER.warn(var3.getLocalizedMessage());
      }
   }
}
