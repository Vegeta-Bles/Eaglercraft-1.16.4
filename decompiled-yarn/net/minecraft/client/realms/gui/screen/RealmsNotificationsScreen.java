package net.minecraft.client.realms.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.gui.RealmsDataFetcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class RealmsNotificationsScreen extends RealmsScreen {
   private static final Identifier INVITE_ICON = new Identifier("realms", "textures/gui/realms/invite_icon.png");
   private static final Identifier TRIAL_ICON = new Identifier("realms", "textures/gui/realms/trial_icon.png");
   private static final Identifier field_22700 = new Identifier("realms", "textures/gui/realms/news_notification_mainscreen.png");
   private static final RealmsDataFetcher REALMS_DATA_FETCHER = new RealmsDataFetcher();
   private volatile int numberOfPendingInvites;
   private static boolean checkedMcoAvailability;
   private static boolean trialAvailable;
   private static boolean validClient;
   private static boolean hasUnreadNews;

   public RealmsNotificationsScreen() {
   }

   @Override
   public void init() {
      this.checkIfMcoEnabled();
      this.client.keyboard.setRepeatEvents(true);
   }

   @Override
   public void tick() {
      if ((!this.method_25169() || !this.method_25170() || !validClient) && !REALMS_DATA_FETCHER.isStopped()) {
         REALMS_DATA_FETCHER.stop();
      } else if (validClient && this.method_25169()) {
         REALMS_DATA_FETCHER.initWithSpecificTaskList();
         if (REALMS_DATA_FETCHER.isFetchedSinceLastTry(RealmsDataFetcher.Task.PENDING_INVITE)) {
            this.numberOfPendingInvites = REALMS_DATA_FETCHER.getPendingInvitesCount();
         }

         if (REALMS_DATA_FETCHER.isFetchedSinceLastTry(RealmsDataFetcher.Task.TRIAL_AVAILABLE)) {
            trialAvailable = REALMS_DATA_FETCHER.isTrialAvailable();
         }

         if (REALMS_DATA_FETCHER.isFetchedSinceLastTry(RealmsDataFetcher.Task.UNREAD_NEWS)) {
            hasUnreadNews = REALMS_DATA_FETCHER.hasUnreadNews();
         }

         REALMS_DATA_FETCHER.markClean();
      }
   }

   private boolean method_25169() {
      return this.client.options.realmsNotifications;
   }

   private boolean method_25170() {
      return this.client.currentScreen instanceof TitleScreen;
   }

   private void checkIfMcoEnabled() {
      if (!checkedMcoAvailability) {
         checkedMcoAvailability = true;
         (new Thread("Realms Notification Availability checker #1") {
            @Override
            public void run() {
               RealmsClient _snowman = RealmsClient.createRealmsClient();

               try {
                  RealmsClient.CompatibleVersionResponse _snowmanx = _snowman.clientCompatible();
                  if (_snowmanx != RealmsClient.CompatibleVersionResponse.COMPATIBLE) {
                     return;
                  }
               } catch (RealmsServiceException var3) {
                  if (var3.httpResultCode != 401) {
                     RealmsNotificationsScreen.checkedMcoAvailability = false;
                  }

                  return;
               }

               RealmsNotificationsScreen.validClient = true;
            }
         }).start();
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      if (validClient) {
         this.drawIcons(matrices, mouseX, mouseY);
      }

      super.render(matrices, mouseX, mouseY, delta);
   }

   private void drawIcons(MatrixStack matrices, int mouseX, int mouseY) {
      int _snowman = this.numberOfPendingInvites;
      int _snowmanx = 24;
      int _snowmanxx = this.height / 4 + 48;
      int _snowmanxxx = this.width / 2 + 80;
      int _snowmanxxxx = _snowmanxx + 48 + 2;
      int _snowmanxxxxx = 0;
      if (hasUnreadNews) {
         this.client.getTextureManager().bindTexture(field_22700);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.pushMatrix();
         RenderSystem.scalef(0.4F, 0.4F, 0.4F);
         DrawableHelper.drawTexture(matrices, (int)((double)(_snowmanxxx + 2 - _snowmanxxxxx) * 2.5), (int)((double)_snowmanxxxx * 2.5), 0.0F, 0.0F, 40, 40, 40, 40);
         RenderSystem.popMatrix();
         _snowmanxxxxx += 14;
      }

      if (_snowman != 0) {
         this.client.getTextureManager().bindTexture(INVITE_ICON);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         DrawableHelper.drawTexture(matrices, _snowmanxxx - _snowmanxxxxx, _snowmanxxxx - 6, 0.0F, 0.0F, 15, 25, 31, 25);
         _snowmanxxxxx += 16;
      }

      if (trialAvailable) {
         this.client.getTextureManager().bindTexture(TRIAL_ICON);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int _snowmanxxxxxx = 0;
         if ((Util.getMeasuringTimeMs() / 800L & 1L) == 1L) {
            _snowmanxxxxxx = 8;
         }

         DrawableHelper.drawTexture(matrices, _snowmanxxx + 4 - _snowmanxxxxx, _snowmanxxxx + 4, 0.0F, (float)_snowmanxxxxxx, 8, 8, 8, 16);
      }
   }

   @Override
   public void removed() {
      REALMS_DATA_FETCHER.stop();
   }
}
