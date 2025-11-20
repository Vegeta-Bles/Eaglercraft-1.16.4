package net.minecraft.client.realms.gui.screen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_5489;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.TickableElement;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.KeyCombo;
import net.minecraft.client.realms.Ping;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.RealmsObjectSelectionList;
import net.minecraft.client.realms.dto.PingResult;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.dto.RealmsServerPlayerList;
import net.minecraft.client.realms.dto.RealmsServerPlayerLists;
import net.minecraft.client.realms.dto.RegionPingResult;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.gui.RealmsDataFetcher;
import net.minecraft.client.realms.task.RealmsGetServerDetailsTask;
import net.minecraft.client.realms.util.RealmsPersistence;
import net.minecraft.client.realms.util.RealmsTextureManager;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class RealmsMainScreen extends RealmsScreen {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Identifier ON_ICON = new Identifier("realms", "textures/gui/realms/on_icon.png");
   private static final Identifier OFF_ICON = new Identifier("realms", "textures/gui/realms/off_icon.png");
   private static final Identifier EXPIRED_ICON = new Identifier("realms", "textures/gui/realms/expired_icon.png");
   private static final Identifier EXPIRES_SOON_ICON = new Identifier("realms", "textures/gui/realms/expires_soon_icon.png");
   private static final Identifier LEAVE_ICON = new Identifier("realms", "textures/gui/realms/leave_icon.png");
   private static final Identifier INVITATION_ICON = new Identifier("realms", "textures/gui/realms/invitation_icons.png");
   private static final Identifier INVITE_ICON = new Identifier("realms", "textures/gui/realms/invite_icon.png");
   private static final Identifier WORLD_ICON = new Identifier("realms", "textures/gui/realms/world_icon.png");
   private static final Identifier REALMS = new Identifier("realms", "textures/gui/title/realms.png");
   private static final Identifier CONFIGURE_ICON = new Identifier("realms", "textures/gui/realms/configure_icon.png");
   private static final Identifier QUESTIONMARK = new Identifier("realms", "textures/gui/realms/questionmark.png");
   private static final Identifier NEWS_ICON = new Identifier("realms", "textures/gui/realms/news_icon.png");
   private static final Identifier POPUP = new Identifier("realms", "textures/gui/realms/popup.png");
   private static final Identifier DARKEN = new Identifier("realms", "textures/gui/realms/darken.png");
   private static final Identifier CROSS_ICON = new Identifier("realms", "textures/gui/realms/cross_icon.png");
   private static final Identifier TRIAL_ICON = new Identifier("realms", "textures/gui/realms/trial_icon.png");
   private static final Identifier WIDGETS = new Identifier("minecraft", "textures/gui/widgets.png");
   private static final Text field_26447 = new TranslatableText("mco.invites.nopending");
   private static final Text field_26448 = new TranslatableText("mco.invites.pending");
   private static final List<Text> field_26449 = ImmutableList.of(
      new TranslatableText("mco.trial.message.line1"), new TranslatableText("mco.trial.message.line2")
   );
   private static final Text field_26450 = new TranslatableText("mco.selectServer.uninitialized");
   private static final Text field_26451 = new TranslatableText("mco.selectServer.expiredList");
   private static final Text field_26452 = new TranslatableText("mco.selectServer.expiredRenew");
   private static final Text field_26453 = new TranslatableText("mco.selectServer.expiredTrial");
   private static final Text field_26454 = new TranslatableText("mco.selectServer.expiredSubscribe");
   private static final Text field_26455 = new TranslatableText("mco.selectServer.minigame").append(" ");
   private static final Text field_26456 = new TranslatableText("mco.selectServer.popup");
   private static final Text field_26457 = new TranslatableText("mco.selectServer.expired");
   private static final Text field_26458 = new TranslatableText("mco.selectServer.expires.soon");
   private static final Text field_26459 = new TranslatableText("mco.selectServer.expires.day");
   private static final Text field_26460 = new TranslatableText("mco.selectServer.open");
   private static final Text field_26461 = new TranslatableText("mco.selectServer.closed");
   private static final Text field_26462 = new TranslatableText("mco.selectServer.leave");
   private static final Text field_26463 = new TranslatableText("mco.selectServer.configure");
   private static final Text field_26464 = new TranslatableText("mco.selectServer.info");
   private static final Text field_26465 = new TranslatableText("mco.news");
   private static List<Identifier> IMAGES = ImmutableList.of();
   private static final RealmsDataFetcher realmsDataFetcher = new RealmsDataFetcher();
   private static boolean overrideConfigure;
   private static int lastScrollYPosition = -1;
   private static volatile boolean hasParentalConsent;
   private static volatile boolean checkedParentalConsent;
   private static volatile boolean checkedClientCompatability;
   private static Screen realmsGenericErrorScreen;
   private static boolean regionsPinged;
   private final RateLimiter rateLimiter;
   private boolean dontSetConnectedToRealms;
   private final Screen lastScreen;
   private volatile RealmsMainScreen.RealmSelectionList realmSelectionList;
   private long selectedServerId = -1L;
   private ButtonWidget playButton;
   private ButtonWidget backButton;
   private ButtonWidget renewButton;
   private ButtonWidget configureButton;
   private ButtonWidget leaveButton;
   private List<Text> toolTip;
   private List<RealmsServer> realmsServers = Lists.newArrayList();
   private volatile int numberOfPendingInvites;
   private int animTick;
   private boolean hasFetchedServers;
   private boolean popupOpenedByUser;
   private boolean justClosedPopup;
   private volatile boolean trialsAvailable;
   private volatile boolean createdTrial;
   private volatile boolean showingPopup;
   private volatile boolean hasUnreadNews;
   private volatile String newsLink;
   private int carouselIndex;
   private int carouselTick;
   private boolean hasSwitchedCarouselImage;
   private List<KeyCombo> keyCombos;
   private int clicks;
   private ReentrantLock connectLock = new ReentrantLock();
   private class_5489 field_26466 = class_5489.field_26528;
   private RealmsMainScreen.HoverState hoverState;
   private ButtonWidget showPopupButton;
   private ButtonWidget pendingInvitesButton;
   private ButtonWidget newsButton;
   private ButtonWidget createTrialButton;
   private ButtonWidget buyARealmButton;
   private ButtonWidget closeButton;

   public RealmsMainScreen(Screen arg) {
      this.lastScreen = arg;
      this.rateLimiter = RateLimiter.create(0.016666668F);
   }

   private boolean shouldShowMessageInList() {
      if (hasParentalConsent() && this.hasFetchedServers) {
         if (this.trialsAvailable && !this.createdTrial) {
            return true;
         } else {
            for (RealmsServer lv : this.realmsServers) {
               if (lv.ownerUUID.equals(this.client.getSession().getUuid())) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean shouldShowPopup() {
      if (!hasParentalConsent() || !this.hasFetchedServers) {
         return false;
      } else if (this.popupOpenedByUser) {
         return true;
      } else {
         return this.trialsAvailable && !this.createdTrial && this.realmsServers.isEmpty() ? true : this.realmsServers.isEmpty();
      }
   }

   @Override
   public void init() {
      this.keyCombos = Lists.newArrayList(
         new KeyCombo[]{
            new KeyCombo(new char[]{'3', '2', '1', '4', '5', '6'}, () -> overrideConfigure = !overrideConfigure),
            new KeyCombo(new char[]{'9', '8', '7', '1', '2', '3'}, () -> {
               if (RealmsClient.currentEnvironment == RealmsClient.Environment.STAGE) {
                  this.switchToProd();
               } else {
                  this.switchToStage();
               }
            }),
            new KeyCombo(new char[]{'9', '8', '7', '4', '5', '6'}, () -> {
               if (RealmsClient.currentEnvironment == RealmsClient.Environment.LOCAL) {
                  this.switchToProd();
               } else {
                  this.switchToLocal();
               }
            })
         }
      );
      if (realmsGenericErrorScreen != null) {
         this.client.openScreen(realmsGenericErrorScreen);
      } else {
         this.connectLock = new ReentrantLock();
         if (checkedClientCompatability && !hasParentalConsent()) {
            this.checkParentalConsent();
         }

         this.checkClientCompatability();
         this.checkUnreadNews();
         if (!this.dontSetConnectedToRealms) {
            this.client.setConnectedToRealms(false);
         }

         this.client.keyboard.setRepeatEvents(true);
         if (hasParentalConsent()) {
            realmsDataFetcher.forceUpdate();
         }

         this.showingPopup = false;
         if (hasParentalConsent() && this.hasFetchedServers) {
            this.addButtons();
         }

         this.realmSelectionList = new RealmsMainScreen.RealmSelectionList();
         if (lastScrollYPosition != -1) {
            this.realmSelectionList.setScrollAmount((double)lastScrollYPosition);
         }

         this.addChild(this.realmSelectionList);
         this.focusOn(this.realmSelectionList);
         this.field_26466 = class_5489.method_30890(this.textRenderer, field_26456, 100);
      }
   }

   private static boolean hasParentalConsent() {
      return checkedParentalConsent && hasParentalConsent;
   }

   public void addButtons() {
      this.leaveButton = this.addButton(
         new ButtonWidget(
            this.width / 2 - 202,
            this.height - 32,
            90,
            20,
            new TranslatableText("mco.selectServer.leave"),
            arg -> this.leaveClicked(this.findServer(this.selectedServerId))
         )
      );
      this.configureButton = this.addButton(
         new ButtonWidget(
            this.width / 2 - 190,
            this.height - 32,
            90,
            20,
            new TranslatableText("mco.selectServer.configure"),
            arg -> this.configureClicked(this.findServer(this.selectedServerId))
         )
      );
      this.playButton = this.addButton(new ButtonWidget(this.width / 2 - 93, this.height - 32, 90, 20, new TranslatableText("mco.selectServer.play"), arg -> {
         RealmsServer lvx = this.findServer(this.selectedServerId);
         if (lvx != null) {
            this.play(lvx, this);
         }
      }));
      this.backButton = this.addButton(new ButtonWidget(this.width / 2 + 4, this.height - 32, 90, 20, ScreenTexts.BACK, arg -> {
         if (!this.justClosedPopup) {
            this.client.openScreen(this.lastScreen);
         }
      }));
      this.renewButton = this.addButton(
         new ButtonWidget(this.width / 2 + 100, this.height - 32, 90, 20, new TranslatableText("mco.selectServer.expiredRenew"), arg -> this.onRenew())
      );
      this.pendingInvitesButton = this.addButton(new RealmsMainScreen.PendingInvitesButton());
      this.newsButton = this.addButton(new RealmsMainScreen.NewsButton());
      this.showPopupButton = this.addButton(new RealmsMainScreen.ShowPopupButton());
      this.closeButton = this.addButton(new RealmsMainScreen.CloseButton());
      this.createTrialButton = this.addButton(
         new ButtonWidget(this.width / 2 + 52, this.popupY0() + 137 - 20, 98, 20, new TranslatableText("mco.selectServer.trial"), arg -> {
            if (this.trialsAvailable && !this.createdTrial) {
               Util.getOperatingSystem().open("https://aka.ms/startjavarealmstrial");
               this.client.openScreen(this.lastScreen);
            }
         })
      );
      this.buyARealmButton = this.addButton(
         new ButtonWidget(
            this.width / 2 + 52,
            this.popupY0() + 160 - 20,
            98,
            20,
            new TranslatableText("mco.selectServer.buy"),
            arg -> Util.getOperatingSystem().open("https://aka.ms/BuyJavaRealms")
         )
      );
      RealmsServer lv = this.findServer(this.selectedServerId);
      this.updateButtonStates(lv);
   }

   private void updateButtonStates(@Nullable RealmsServer server) {
      this.playButton.active = this.shouldPlayButtonBeActive(server) && !this.shouldShowPopup();
      this.renewButton.visible = this.shouldRenewButtonBeActive(server);
      this.configureButton.visible = this.shouldConfigureButtonBeVisible(server);
      this.leaveButton.visible = this.shouldLeaveButtonBeVisible(server);
      boolean bl = this.shouldShowPopup() && this.trialsAvailable && !this.createdTrial;
      this.createTrialButton.visible = bl;
      this.createTrialButton.active = bl;
      this.buyARealmButton.visible = this.shouldShowPopup();
      this.closeButton.visible = this.shouldShowPopup() && this.popupOpenedByUser;
      this.renewButton.active = !this.shouldShowPopup();
      this.configureButton.active = !this.shouldShowPopup();
      this.leaveButton.active = !this.shouldShowPopup();
      this.newsButton.active = true;
      this.pendingInvitesButton.active = true;
      this.backButton.active = true;
      this.showPopupButton.active = !this.shouldShowPopup();
   }

   private boolean shouldShowPopupButton() {
      return (!this.shouldShowPopup() || this.popupOpenedByUser) && hasParentalConsent() && this.hasFetchedServers;
   }

   private boolean shouldPlayButtonBeActive(@Nullable RealmsServer server) {
      return server != null && !server.expired && server.state == RealmsServer.State.OPEN;
   }

   private boolean shouldRenewButtonBeActive(@Nullable RealmsServer server) {
      return server != null && server.expired && this.isSelfOwnedServer(server);
   }

   private boolean shouldConfigureButtonBeVisible(@Nullable RealmsServer server) {
      return server != null && this.isSelfOwnedServer(server);
   }

   private boolean shouldLeaveButtonBeVisible(@Nullable RealmsServer server) {
      return server != null && !this.isSelfOwnedServer(server);
   }

   @Override
   public void tick() {
      super.tick();
      this.justClosedPopup = false;
      this.animTick++;
      this.clicks--;
      if (this.clicks < 0) {
         this.clicks = 0;
      }

      if (hasParentalConsent()) {
         realmsDataFetcher.init();
         if (realmsDataFetcher.isFetchedSinceLastTry(RealmsDataFetcher.Task.SERVER_LIST)) {
            List<RealmsServer> list = realmsDataFetcher.getServers();
            this.realmSelectionList.clear();
            boolean bl = !this.hasFetchedServers;
            if (bl) {
               this.hasFetchedServers = true;
            }

            if (list != null) {
               boolean bl2 = false;

               for (RealmsServer lv : list) {
                  if (this.method_25001(lv)) {
                     bl2 = true;
                  }
               }

               this.realmsServers = list;
               if (this.shouldShowMessageInList()) {
                  this.realmSelectionList.method_30161(new RealmsMainScreen.RealmSelectionListTrialEntry());
               }

               for (RealmsServer lv2 : this.realmsServers) {
                  this.realmSelectionList.addEntry(new RealmsMainScreen.RealmSelectionListEntry(lv2));
               }

               if (!regionsPinged && bl2) {
                  regionsPinged = true;
                  this.pingRegions();
               }
            }

            if (bl) {
               this.addButtons();
            }
         }

         if (realmsDataFetcher.isFetchedSinceLastTry(RealmsDataFetcher.Task.PENDING_INVITE)) {
            this.numberOfPendingInvites = realmsDataFetcher.getPendingInvitesCount();
            if (this.numberOfPendingInvites > 0 && this.rateLimiter.tryAcquire(1)) {
               Realms.narrateNow(I18n.translate("mco.configure.world.invite.narration", this.numberOfPendingInvites));
            }
         }

         if (realmsDataFetcher.isFetchedSinceLastTry(RealmsDataFetcher.Task.TRIAL_AVAILABLE) && !this.createdTrial) {
            boolean bl3 = realmsDataFetcher.isTrialAvailable();
            if (bl3 != this.trialsAvailable && this.shouldShowPopup()) {
               this.trialsAvailable = bl3;
               this.showingPopup = false;
            } else {
               this.trialsAvailable = bl3;
            }
         }

         if (realmsDataFetcher.isFetchedSinceLastTry(RealmsDataFetcher.Task.LIVE_STATS)) {
            RealmsServerPlayerLists lv3 = realmsDataFetcher.getLivestats();

            for (RealmsServerPlayerList lv4 : lv3.servers) {
               for (RealmsServer lv5 : this.realmsServers) {
                  if (lv5.id == lv4.serverId) {
                     lv5.updateServerPing(lv4);
                     break;
                  }
               }
            }
         }

         if (realmsDataFetcher.isFetchedSinceLastTry(RealmsDataFetcher.Task.UNREAD_NEWS)) {
            this.hasUnreadNews = realmsDataFetcher.hasUnreadNews();
            this.newsLink = realmsDataFetcher.newsLink();
         }

         realmsDataFetcher.markClean();
         if (this.shouldShowPopup()) {
            this.carouselTick++;
         }

         if (this.showPopupButton != null) {
            this.showPopupButton.visible = this.shouldShowPopupButton();
         }
      }
   }

   private void pingRegions() {
      new Thread(() -> {
         List<RegionPingResult> list = Ping.pingAllRegions();
         RealmsClient lv = RealmsClient.createRealmsClient();
         PingResult lv2 = new PingResult();
         lv2.pingResults = list;
         lv2.worldIds = this.getOwnedNonExpiredWorldIds();

         try {
            lv.sendPingResults(lv2);
         } catch (Throwable var5) {
            LOGGER.warn("Could not send ping result to Realms: ", var5);
         }
      }).start();
   }

   private List<Long> getOwnedNonExpiredWorldIds() {
      List<Long> list = Lists.newArrayList();

      for (RealmsServer lv : this.realmsServers) {
         if (this.method_25001(lv)) {
            list.add(lv.id);
         }
      }

      return list;
   }

   @Override
   public void removed() {
      this.client.keyboard.setRepeatEvents(false);
      this.stopRealmsFetcher();
   }

   private void onRenew() {
      RealmsServer lv = this.findServer(this.selectedServerId);
      if (lv != null) {
         String string = "https://aka.ms/ExtendJavaRealms?subscriptionId="
            + lv.remoteSubscriptionId
            + "&profileId="
            + this.client.getSession().getUuid()
            + "&ref="
            + (lv.expiredTrial ? "expiredTrial" : "expiredRealm");
         this.client.keyboard.setClipboard(string);
         Util.getOperatingSystem().open(string);
      }
   }

   private void checkClientCompatability() {
      if (!checkedClientCompatability) {
         checkedClientCompatability = true;
         (new Thread("MCO Compatability Checker #1") {
               @Override
               public void run() {
                  RealmsClient lv = RealmsClient.createRealmsClient();

                  try {
                     RealmsClient.CompatibleVersionResponse lv2 = lv.clientCompatible();
                     if (lv2 == RealmsClient.CompatibleVersionResponse.OUTDATED) {
                        RealmsMainScreen.realmsGenericErrorScreen = new RealmsClientOutdatedScreen(RealmsMainScreen.this.lastScreen, true);
                        RealmsMainScreen.this.client.execute(() -> RealmsMainScreen.this.client.openScreen(RealmsMainScreen.realmsGenericErrorScreen));
                        return;
                     }

                     if (lv2 == RealmsClient.CompatibleVersionResponse.OTHER) {
                        RealmsMainScreen.realmsGenericErrorScreen = new RealmsClientOutdatedScreen(RealmsMainScreen.this.lastScreen, false);
                        RealmsMainScreen.this.client.execute(() -> RealmsMainScreen.this.client.openScreen(RealmsMainScreen.realmsGenericErrorScreen));
                        return;
                     }

                     RealmsMainScreen.this.checkParentalConsent();
                  } catch (RealmsServiceException var3) {
                     RealmsMainScreen.checkedClientCompatability = false;
                     RealmsMainScreen.LOGGER.error("Couldn't connect to realms", var3);
                     if (var3.httpResultCode == 401) {
                        RealmsMainScreen.realmsGenericErrorScreen = new RealmsGenericErrorScreen(
                           new TranslatableText("mco.error.invalid.session.title"),
                           new TranslatableText("mco.error.invalid.session.message"),
                           RealmsMainScreen.this.lastScreen
                        );
                        RealmsMainScreen.this.client.execute(() -> RealmsMainScreen.this.client.openScreen(RealmsMainScreen.realmsGenericErrorScreen));
                     } else {
                        RealmsMainScreen.this.client
                           .execute(() -> RealmsMainScreen.this.client.openScreen(new RealmsGenericErrorScreen(var3, RealmsMainScreen.this.lastScreen)));
                     }
                  }
               }
            })
            .start();
      }
   }

   private void checkUnreadNews() {
   }

   private void checkParentalConsent() {
      (new Thread("MCO Compatability Checker #1") {
            @Override
            public void run() {
               RealmsClient lv = RealmsClient.createRealmsClient();

               try {
                  Boolean boolean_ = lv.mcoEnabled();
                  if (boolean_) {
                     RealmsMainScreen.LOGGER.info("Realms is available for this user");
                     RealmsMainScreen.hasParentalConsent = true;
                  } else {
                     RealmsMainScreen.LOGGER.info("Realms is not available for this user");
                     RealmsMainScreen.hasParentalConsent = false;
                     RealmsMainScreen.this.client
                        .execute(() -> RealmsMainScreen.this.client.openScreen(new RealmsParentalConsentScreen(RealmsMainScreen.this.lastScreen)));
                  }

                  RealmsMainScreen.checkedParentalConsent = true;
               } catch (RealmsServiceException var3) {
                  RealmsMainScreen.LOGGER.error("Couldn't connect to realms", var3);
                  RealmsMainScreen.this.client
                     .execute(() -> RealmsMainScreen.this.client.openScreen(new RealmsGenericErrorScreen(var3, RealmsMainScreen.this.lastScreen)));
               }
            }
         })
         .start();
   }

   private void switchToStage() {
      if (RealmsClient.currentEnvironment != RealmsClient.Environment.STAGE) {
         (new Thread("MCO Stage Availability Checker #1") {
            @Override
            public void run() {
               RealmsClient lv = RealmsClient.createRealmsClient();

               try {
                  Boolean boolean_ = lv.stageAvailable();
                  if (boolean_) {
                     RealmsClient.switchToStage();
                     RealmsMainScreen.LOGGER.info("Switched to stage");
                     RealmsMainScreen.realmsDataFetcher.forceUpdate();
                  }
               } catch (RealmsServiceException var3) {
                  RealmsMainScreen.LOGGER.error("Couldn't connect to Realms: " + var3);
               }
            }
         }).start();
      }
   }

   private void switchToLocal() {
      if (RealmsClient.currentEnvironment != RealmsClient.Environment.LOCAL) {
         (new Thread("MCO Local Availability Checker #1") {
            @Override
            public void run() {
               RealmsClient lv = RealmsClient.createRealmsClient();

               try {
                  Boolean boolean_ = lv.stageAvailable();
                  if (boolean_) {
                     RealmsClient.switchToLocal();
                     RealmsMainScreen.LOGGER.info("Switched to local");
                     RealmsMainScreen.realmsDataFetcher.forceUpdate();
                  }
               } catch (RealmsServiceException var3) {
                  RealmsMainScreen.LOGGER.error("Couldn't connect to Realms: " + var3);
               }
            }
         }).start();
      }
   }

   private void switchToProd() {
      RealmsClient.switchToProd();
      realmsDataFetcher.forceUpdate();
   }

   private void stopRealmsFetcher() {
      realmsDataFetcher.stop();
   }

   private void configureClicked(RealmsServer arg) {
      if (this.client.getSession().getUuid().equals(arg.ownerUUID) || overrideConfigure) {
         this.saveListScrollPosition();
         this.client.openScreen(new RealmsConfigureWorldScreen(this, arg.id));
      }
   }

   private void leaveClicked(@Nullable RealmsServer selectedServer) {
      if (selectedServer != null && !this.client.getSession().getUuid().equals(selectedServer.ownerUUID)) {
         this.saveListScrollPosition();
         Text lv = new TranslatableText("mco.configure.world.leave.question.line1");
         Text lv2 = new TranslatableText("mco.configure.world.leave.question.line2");
         this.client.openScreen(new RealmsLongConfirmationScreen(this::method_24991, RealmsLongConfirmationScreen.Type.Info, lv, lv2, true));
      }
   }

   private void saveListScrollPosition() {
      lastScrollYPosition = (int)this.realmSelectionList.getScrollAmount();
   }

   @Nullable
   private RealmsServer findServer(long id) {
      for (RealmsServer lv : this.realmsServers) {
         if (lv.id == id) {
            return lv;
         }
      }

      return null;
   }

   private void method_24991(boolean bl) {
      if (bl) {
         (new Thread("Realms-leave-server") {
               @Override
               public void run() {
                  try {
                     RealmsServer lv = RealmsMainScreen.this.findServer(RealmsMainScreen.this.selectedServerId);
                     if (lv != null) {
                        RealmsClient lv2 = RealmsClient.createRealmsClient();
                        lv2.uninviteMyselfFrom(lv.id);
                        RealmsMainScreen.this.client.execute(() -> RealmsMainScreen.this.method_31174(lv));
                     }
                  } catch (RealmsServiceException var3) {
                     RealmsMainScreen.LOGGER.error("Couldn't configure world");
                     RealmsMainScreen.this.client
                        .execute(() -> RealmsMainScreen.this.client.openScreen(new RealmsGenericErrorScreen(var3, RealmsMainScreen.this)));
                  }
               }
            })
            .start();
      }

      this.client.openScreen(this);
   }

   private void method_31174(RealmsServer arg) {
      realmsDataFetcher.removeItem(arg);
      this.realmsServers.remove(arg);
      this.realmSelectionList
         .children()
         .removeIf(
            argx -> argx instanceof RealmsMainScreen.RealmSelectionListEntry
                  && ((RealmsMainScreen.RealmSelectionListEntry)argx).mServerData.id == this.selectedServerId
         );
      this.realmSelectionList.setSelected(null);
      this.updateButtonStates(null);
      this.selectedServerId = -1L;
      this.playButton.active = false;
   }

   public void removeSelection() {
      this.selectedServerId = -1L;
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.keyCombos.forEach(KeyCombo::reset);
         this.onClosePopup();
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   private void onClosePopup() {
      if (this.shouldShowPopup() && this.popupOpenedByUser) {
         this.popupOpenedByUser = false;
      } else {
         this.client.openScreen(this.lastScreen);
      }
   }

   @Override
   public boolean charTyped(char chr, int keyCode) {
      this.keyCombos.forEach(arg -> arg.keyPressed(chr));
      return true;
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.hoverState = RealmsMainScreen.HoverState.NONE;
      this.toolTip = null;
      this.renderBackground(matrices);
      this.realmSelectionList.render(matrices, mouseX, mouseY, delta);
      this.drawRealmsLogo(matrices, this.width / 2 - 50, 7);
      if (RealmsClient.currentEnvironment == RealmsClient.Environment.STAGE) {
         this.renderStage(matrices);
      }

      if (RealmsClient.currentEnvironment == RealmsClient.Environment.LOCAL) {
         this.renderLocal(matrices);
      }

      if (this.shouldShowPopup()) {
         this.drawPopup(matrices, mouseX, mouseY);
      } else {
         if (this.showingPopup) {
            this.updateButtonStates(null);
            if (!this.children.contains(this.realmSelectionList)) {
               this.children.add(this.realmSelectionList);
            }

            RealmsServer lv = this.findServer(this.selectedServerId);
            this.playButton.active = this.shouldPlayButtonBeActive(lv);
         }

         this.showingPopup = false;
      }

      super.render(matrices, mouseX, mouseY, delta);
      if (this.toolTip != null) {
         this.renderMousehoverTooltip(matrices, this.toolTip, mouseX, mouseY);
      }

      if (this.trialsAvailable && !this.createdTrial && this.shouldShowPopup()) {
         this.client.getTextureManager().bindTexture(TRIAL_ICON);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         int k = 8;
         int l = 8;
         int m = 0;
         if ((Util.getMeasuringTimeMs() / 800L & 1L) == 1L) {
            m = 8;
         }

         DrawableHelper.drawTexture(
            matrices,
            this.createTrialButton.x + this.createTrialButton.getWidth() - 8 - 4,
            this.createTrialButton.y + this.createTrialButton.getHeight() / 2 - 4,
            0.0F,
            (float)m,
            8,
            8,
            8,
            16
         );
      }
   }

   private void drawRealmsLogo(MatrixStack matrices, int x, int y) {
      this.client.getTextureManager().bindTexture(REALMS);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.pushMatrix();
      RenderSystem.scalef(0.5F, 0.5F, 0.5F);
      DrawableHelper.drawTexture(matrices, x * 2, y * 2 - 5, 0.0F, 0.0F, 200, 50, 200, 50);
      RenderSystem.popMatrix();
   }

   @Override
   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.isOutsidePopup(mouseX, mouseY) && this.popupOpenedByUser) {
         this.popupOpenedByUser = false;
         this.justClosedPopup = true;
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   private boolean isOutsidePopup(double xm, double ym) {
      int i = this.popupX0();
      int j = this.popupY0();
      return xm < (double)(i - 5) || xm > (double)(i + 315) || ym < (double)(j - 5) || ym > (double)(j + 171);
   }

   private void drawPopup(MatrixStack matrices, int mouseX, int mouseY) {
      int k = this.popupX0();
      int l = this.popupY0();
      if (!this.showingPopup) {
         this.carouselIndex = 0;
         this.carouselTick = 0;
         this.hasSwitchedCarouselImage = true;
         this.updateButtonStates(null);
         if (this.children.contains(this.realmSelectionList)) {
            Element lv = this.realmSelectionList;
            if (!this.children.remove(lv)) {
               LOGGER.error("Unable to remove widget: " + lv);
            }
         }

         Realms.narrateNow(field_26456.getString());
      }

      if (this.hasFetchedServers) {
         this.showingPopup = true;
      }

      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.7F);
      RenderSystem.enableBlend();
      this.client.getTextureManager().bindTexture(DARKEN);
      int m = 0;
      int n = 32;
      DrawableHelper.drawTexture(matrices, 0, 32, 0.0F, 0.0F, this.width, this.height - 40 - 32, 310, 166);
      RenderSystem.disableBlend();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.client.getTextureManager().bindTexture(POPUP);
      DrawableHelper.drawTexture(matrices, k, l, 0.0F, 0.0F, 310, 166, 310, 166);
      if (!IMAGES.isEmpty()) {
         this.client.getTextureManager().bindTexture(IMAGES.get(this.carouselIndex));
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         DrawableHelper.drawTexture(matrices, k + 7, l + 7, 0.0F, 0.0F, 195, 152, 195, 152);
         if (this.carouselTick % 95 < 5) {
            if (!this.hasSwitchedCarouselImage) {
               this.carouselIndex = (this.carouselIndex + 1) % IMAGES.size();
               this.hasSwitchedCarouselImage = true;
            }
         } else {
            this.hasSwitchedCarouselImage = false;
         }
      }

      this.field_26466.method_30896(matrices, this.width / 2 + 52, l + 7, 10, 5000268);
   }

   private int popupX0() {
      return (this.width - 310) / 2;
   }

   private int popupY0() {
      return this.height / 2 - 80;
   }

   private void drawInvitationPendingIcon(MatrixStack arg, int i, int j, int k, int l, boolean bl, boolean bl2) {
      int m = this.numberOfPendingInvites;
      boolean bl3 = this.inPendingInvitationArea((double)i, (double)j);
      boolean bl4 = bl2 && bl;
      if (bl4) {
         float f = 0.25F + (1.0F + MathHelper.sin((float)this.animTick * 0.5F)) * 0.25F;
         int n = 0xFF000000 | (int)(f * 64.0F) << 16 | (int)(f * 64.0F) << 8 | (int)(f * 64.0F) << 0;
         this.fillGradient(arg, k - 2, l - 2, k + 18, l + 18, n, n);
         n = 0xFF000000 | (int)(f * 255.0F) << 16 | (int)(f * 255.0F) << 8 | (int)(f * 255.0F) << 0;
         this.fillGradient(arg, k - 2, l - 2, k + 18, l - 1, n, n);
         this.fillGradient(arg, k - 2, l - 2, k - 1, l + 18, n, n);
         this.fillGradient(arg, k + 17, l - 2, k + 18, l + 18, n, n);
         this.fillGradient(arg, k - 2, l + 17, k + 18, l + 18, n, n);
      }

      this.client.getTextureManager().bindTexture(INVITE_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      boolean bl5 = bl2 && bl;
      float g = bl5 ? 16.0F : 0.0F;
      DrawableHelper.drawTexture(arg, k, l - 6, g, 0.0F, 15, 25, 31, 25);
      boolean bl6 = bl2 && m != 0;
      if (bl6) {
         int o = (Math.min(m, 6) - 1) * 8;
         int p = (int)(Math.max(0.0F, Math.max(MathHelper.sin((float)(10 + this.animTick) * 0.57F), MathHelper.cos((float)this.animTick * 0.35F))) * -6.0F);
         this.client.getTextureManager().bindTexture(INVITATION_ICON);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         float h = bl3 ? 8.0F : 0.0F;
         DrawableHelper.drawTexture(arg, k + 4, l + 4 + p, (float)o, h, 8, 8, 48, 16);
      }

      int q = i + 12;
      boolean bl7 = bl2 && bl3;
      if (bl7) {
         Text lv = m == 0 ? field_26447 : field_26448;
         int s = this.textRenderer.getWidth(lv);
         this.fillGradient(arg, q - 3, j - 3, q + s + 3, j + 8 + 3, -1073741824, -1073741824);
         this.textRenderer.drawWithShadow(arg, lv, (float)q, (float)j, -1);
      }
   }

   private boolean inPendingInvitationArea(double xm, double ym) {
      int i = this.width / 2 + 50;
      int j = this.width / 2 + 66;
      int k = 11;
      int l = 23;
      if (this.numberOfPendingInvites != 0) {
         i -= 3;
         j += 3;
         k -= 5;
         l += 5;
      }

      return (double)i <= xm && xm <= (double)j && (double)k <= ym && ym <= (double)l;
   }

   public void play(RealmsServer arg, Screen arg2) {
      if (arg != null) {
         try {
            if (!this.connectLock.tryLock(1L, TimeUnit.SECONDS)) {
               return;
            }

            if (this.connectLock.getHoldCount() > 1) {
               return;
            }
         } catch (InterruptedException var4) {
            return;
         }

         this.dontSetConnectedToRealms = true;
         this.client.openScreen(new RealmsLongRunningMcoTaskScreen(arg2, new RealmsGetServerDetailsTask(this, arg2, arg, this.connectLock)));
      }
   }

   private boolean isSelfOwnedServer(RealmsServer serverData) {
      return serverData.ownerUUID != null && serverData.ownerUUID.equals(this.client.getSession().getUuid());
   }

   private boolean method_25001(RealmsServer arg) {
      return this.isSelfOwnedServer(arg) && !arg.expired;
   }

   private void drawExpired(MatrixStack arg, int i, int j, int k, int l) {
      this.client.getTextureManager().bindTexture(EXPIRED_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      DrawableHelper.drawTexture(arg, i, j, 0.0F, 0.0F, 10, 28, 10, 28);
      if (k >= i && k <= i + 9 && l >= j && l <= j + 27 && l < this.height - 40 && l > 32 && !this.shouldShowPopup()) {
         this.method_27452(field_26457);
      }
   }

   private void method_24987(MatrixStack arg, int i, int j, int k, int l, int m) {
      this.client.getTextureManager().bindTexture(EXPIRES_SOON_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.animTick % 20 < 10) {
         DrawableHelper.drawTexture(arg, i, j, 0.0F, 0.0F, 10, 28, 20, 28);
      } else {
         DrawableHelper.drawTexture(arg, i, j, 10.0F, 0.0F, 10, 28, 20, 28);
      }

      if (k >= i && k <= i + 9 && l >= j && l <= j + 27 && l < this.height - 40 && l > 32 && !this.shouldShowPopup()) {
         if (m <= 0) {
            this.method_27452(field_26458);
         } else if (m == 1) {
            this.method_27452(field_26459);
         } else {
            this.method_27452(new TranslatableText("mco.selectServer.expires.days", m));
         }
      }
   }

   private void drawOpen(MatrixStack arg, int i, int j, int k, int l) {
      this.client.getTextureManager().bindTexture(ON_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      DrawableHelper.drawTexture(arg, i, j, 0.0F, 0.0F, 10, 28, 10, 28);
      if (k >= i && k <= i + 9 && l >= j && l <= j + 27 && l < this.height - 40 && l > 32 && !this.shouldShowPopup()) {
         this.method_27452(field_26460);
      }
   }

   private void drawClose(MatrixStack arg, int i, int j, int k, int l) {
      this.client.getTextureManager().bindTexture(OFF_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      DrawableHelper.drawTexture(arg, i, j, 0.0F, 0.0F, 10, 28, 10, 28);
      if (k >= i && k <= i + 9 && l >= j && l <= j + 27 && l < this.height - 40 && l > 32 && !this.shouldShowPopup()) {
         this.method_27452(field_26461);
      }
   }

   private void drawLeave(MatrixStack arg, int i, int j, int k, int l) {
      boolean bl = false;
      if (k >= i && k <= i + 28 && l >= j && l <= j + 28 && l < this.height - 40 && l > 32 && !this.shouldShowPopup()) {
         bl = true;
      }

      this.client.getTextureManager().bindTexture(LEAVE_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float f = bl ? 28.0F : 0.0F;
      DrawableHelper.drawTexture(arg, i, j, f, 0.0F, 28, 28, 56, 28);
      if (bl) {
         this.method_27452(field_26462);
         this.hoverState = RealmsMainScreen.HoverState.LEAVE;
      }
   }

   private void drawConfigure(MatrixStack arg, int i, int j, int k, int l) {
      boolean bl = false;
      if (k >= i && k <= i + 28 && l >= j && l <= j + 28 && l < this.height - 40 && l > 32 && !this.shouldShowPopup()) {
         bl = true;
      }

      this.client.getTextureManager().bindTexture(CONFIGURE_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float f = bl ? 28.0F : 0.0F;
      DrawableHelper.drawTexture(arg, i, j, f, 0.0F, 28, 28, 56, 28);
      if (bl) {
         this.method_27452(field_26463);
         this.hoverState = RealmsMainScreen.HoverState.CONFIGURE;
      }
   }

   protected void renderMousehoverTooltip(MatrixStack arg, List<Text> list, int i, int j) {
      if (!list.isEmpty()) {
         int k = 0;
         int l = 0;

         for (Text lv : list) {
            int m = this.textRenderer.getWidth(lv);
            if (m > l) {
               l = m;
            }
         }

         int n = i - l - 5;
         int o = j;
         if (n < 0) {
            n = i + 12;
         }

         for (Text lv2 : list) {
            int p = o - (k == 0 ? 3 : 0) + k;
            this.fillGradient(arg, n - 3, p, n + l + 3, o + 8 + 3 + k, -1073741824, -1073741824);
            this.textRenderer.drawWithShadow(arg, lv2, (float)n, (float)(o + k), 16777215);
            k += 10;
         }
      }
   }

   private void renderMoreInfo(MatrixStack arg, int i, int j, int k, int l, boolean bl) {
      boolean bl2 = false;
      if (i >= k && i <= k + 20 && j >= l && j <= l + 20) {
         bl2 = true;
      }

      this.client.getTextureManager().bindTexture(QUESTIONMARK);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float f = bl ? 20.0F : 0.0F;
      DrawableHelper.drawTexture(arg, k, l, f, 0.0F, 20, 20, 40, 20);
      if (bl2) {
         this.method_27452(field_26464);
      }
   }

   private void renderNews(MatrixStack arg, int i, int j, boolean bl, int k, int l, boolean bl2, boolean bl3) {
      boolean bl4 = false;
      if (i >= k && i <= k + 20 && j >= l && j <= l + 20) {
         bl4 = true;
      }

      this.client.getTextureManager().bindTexture(NEWS_ICON);
      if (bl3) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         RenderSystem.color4f(0.5F, 0.5F, 0.5F, 1.0F);
      }

      boolean bl5 = bl3 && bl2;
      float f = bl5 ? 20.0F : 0.0F;
      DrawableHelper.drawTexture(arg, k, l, f, 0.0F, 20, 20, 40, 20);
      if (bl4 && bl3) {
         this.method_27452(field_26465);
      }

      if (bl && bl3) {
         int m = bl4
            ? 0
            : (int)(Math.max(0.0F, Math.max(MathHelper.sin((float)(10 + this.animTick) * 0.57F), MathHelper.cos((float)this.animTick * 0.35F))) * -6.0F);
         this.client.getTextureManager().bindTexture(INVITATION_ICON);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         DrawableHelper.drawTexture(arg, k + 10, l + 2 + m, 40.0F, 0.0F, 8, 8, 48, 16);
      }
   }

   private void renderLocal(MatrixStack arg) {
      String string = "LOCAL!";
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.pushMatrix();
      RenderSystem.translatef((float)(this.width / 2 - 25), 20.0F, 0.0F);
      RenderSystem.rotatef(-20.0F, 0.0F, 0.0F, 1.0F);
      RenderSystem.scalef(1.5F, 1.5F, 1.5F);
      this.textRenderer.draw(arg, "LOCAL!", 0.0F, 0.0F, 8388479);
      RenderSystem.popMatrix();
   }

   private void renderStage(MatrixStack arg) {
      String string = "STAGE!";
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.pushMatrix();
      RenderSystem.translatef((float)(this.width / 2 - 25), 20.0F, 0.0F);
      RenderSystem.rotatef(-20.0F, 0.0F, 0.0F, 1.0F);
      RenderSystem.scalef(1.5F, 1.5F, 1.5F);
      this.textRenderer.draw(arg, "STAGE!", 0.0F, 0.0F, -256);
      RenderSystem.popMatrix();
   }

   public RealmsMainScreen newScreen() {
      RealmsMainScreen lv = new RealmsMainScreen(this.lastScreen);
      lv.init(this.client, this.width, this.height);
      return lv;
   }

   public static void method_23765(ResourceManager manager) {
      Collection<Identifier> collection = manager.findResources("textures/gui/images", string -> string.endsWith(".png"));
      IMAGES = collection.stream().filter(arg -> arg.getNamespace().equals("realms")).collect(ImmutableList.toImmutableList());
   }

   private void method_27452(Text... args) {
      this.toolTip = Arrays.asList(args);
   }

   private void method_24985(ButtonWidget arg) {
      this.client.openScreen(new RealmsPendingInvitesScreen(this.lastScreen));
   }

   @Environment(EnvType.CLIENT)
   class CloseButton extends ButtonWidget {
      public CloseButton() {
         super(
            RealmsMainScreen.this.popupX0() + 4,
            RealmsMainScreen.this.popupY0() + 4,
            12,
            12,
            new TranslatableText("mco.selectServer.close"),
            arg2 -> RealmsMainScreen.this.onClosePopup()
         );
      }

      @Override
      public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
         RealmsMainScreen.this.client.getTextureManager().bindTexture(RealmsMainScreen.CROSS_ICON);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         float g = this.isHovered() ? 12.0F : 0.0F;
         drawTexture(matrices, this.x, this.y, 0.0F, g, 12, 12, 12, 24);
         if (this.isMouseOver((double)mouseX, (double)mouseY)) {
            RealmsMainScreen.this.method_27452(this.getMessage());
         }
      }
   }

   @Environment(EnvType.CLIENT)
   abstract class Entry extends AlwaysSelectedEntryListWidget.Entry<RealmsMainScreen.Entry> {
      private Entry() {
      }
   }

   @Environment(EnvType.CLIENT)
   static enum HoverState {
      NONE,
      EXPIRED,
      LEAVE,
      CONFIGURE;

      private HoverState() {
      }
   }

   @Environment(EnvType.CLIENT)
   class NewsButton extends ButtonWidget {
      public NewsButton() {
         super(RealmsMainScreen.this.width - 62, 6, 20, 20, LiteralText.EMPTY, arg2 -> {
            if (RealmsMainScreen.this.newsLink != null) {
               Util.getOperatingSystem().open(RealmsMainScreen.this.newsLink);
               if (RealmsMainScreen.this.hasUnreadNews) {
                  RealmsPersistence.RealmsPersistenceData lv = RealmsPersistence.readFile();
                  lv.hasUnreadNews = false;
                  RealmsMainScreen.this.hasUnreadNews = false;
                  RealmsPersistence.writeFile(lv);
               }
            }
         });
         this.setMessage(new TranslatableText("mco.news"));
      }

      @Override
      public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
         RealmsMainScreen.this.renderNews(matrices, mouseX, mouseY, RealmsMainScreen.this.hasUnreadNews, this.x, this.y, this.isHovered(), this.active);
      }
   }

   @Environment(EnvType.CLIENT)
   class PendingInvitesButton extends ButtonWidget implements TickableElement {
      public PendingInvitesButton() {
         super(RealmsMainScreen.this.width / 2 + 47, 6, 22, 22, LiteralText.EMPTY, arg2 -> RealmsMainScreen.this.method_24985(arg2));
      }

      @Override
      public void tick() {
         this.setMessage(new TranslatableText(RealmsMainScreen.this.numberOfPendingInvites == 0 ? "mco.invites.nopending" : "mco.invites.pending"));
      }

      @Override
      public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
         RealmsMainScreen.this.drawInvitationPendingIcon(matrices, mouseX, mouseY, this.x, this.y, this.isHovered(), this.active);
      }
   }

   @Environment(EnvType.CLIENT)
   class RealmSelectionList extends RealmsObjectSelectionList<RealmsMainScreen.Entry> {
      private boolean field_25723;

      public RealmSelectionList() {
         super(RealmsMainScreen.this.width, RealmsMainScreen.this.height, 32, RealmsMainScreen.this.height - 40, 36);
      }

      @Override
      public void clear() {
         super.clear();
         this.field_25723 = false;
      }

      public int method_30161(RealmsMainScreen.Entry arg) {
         this.field_25723 = true;
         return this.addEntry(arg);
      }

      @Override
      public boolean isFocused() {
         return RealmsMainScreen.this.getFocused() == this;
      }

      @Override
      public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
         if (keyCode != 257 && keyCode != 32 && keyCode != 335) {
            return super.keyPressed(keyCode, scanCode, modifiers);
         } else {
            AlwaysSelectedEntryListWidget.Entry lv = this.getSelected();
            return lv == null ? super.keyPressed(keyCode, scanCode, modifiers) : lv.mouseClicked(0.0, 0.0, 0);
         }
      }

      @Override
      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         if (button == 0 && mouseX < (double)this.getScrollbarPositionX() && mouseY >= (double)this.top && mouseY <= (double)this.bottom) {
            int j = RealmsMainScreen.this.realmSelectionList.getRowLeft();
            int k = this.getScrollbarPositionX();
            int l = (int)Math.floor(mouseY - (double)this.top) - this.headerHeight + (int)this.getScrollAmount() - 4;
            int m = l / this.itemHeight;
            if (mouseX >= (double)j && mouseX <= (double)k && m >= 0 && l >= 0 && m < this.getItemCount()) {
               this.itemClicked(l, m, mouseX, mouseY, this.width);
               RealmsMainScreen.this.clicks = RealmsMainScreen.this.clicks + 7;
               this.setSelected(m);
            }

            return true;
         } else {
            return super.mouseClicked(mouseX, mouseY, button);
         }
      }

      @Override
      public void setSelected(int index) {
         this.setSelectedItem(index);
         if (index != -1) {
            RealmsServer lv;
            if (this.field_25723) {
               if (index == 0) {
                  lv = null;
               } else {
                  if (index - 1 >= RealmsMainScreen.this.realmsServers.size()) {
                     RealmsMainScreen.this.selectedServerId = -1L;
                     return;
                  }

                  lv = RealmsMainScreen.this.realmsServers.get(index - 1);
               }
            } else {
               if (index >= RealmsMainScreen.this.realmsServers.size()) {
                  RealmsMainScreen.this.selectedServerId = -1L;
                  return;
               }

               lv = RealmsMainScreen.this.realmsServers.get(index);
            }

            RealmsMainScreen.this.updateButtonStates(lv);
            if (lv == null) {
               RealmsMainScreen.this.selectedServerId = -1L;
            } else if (lv.state == RealmsServer.State.UNINITIALIZED) {
               RealmsMainScreen.this.selectedServerId = -1L;
            } else {
               RealmsMainScreen.this.selectedServerId = lv.id;
               if (RealmsMainScreen.this.clicks >= 10 && RealmsMainScreen.this.playButton.active) {
                  RealmsMainScreen.this.play(RealmsMainScreen.this.findServer(RealmsMainScreen.this.selectedServerId), RealmsMainScreen.this);
               }
            }
         }
      }

      public void setSelected(@Nullable RealmsMainScreen.Entry arg) {
         super.setSelected(arg);
         int i = this.children().indexOf(arg);
         if (this.field_25723 && i == 0) {
            Realms.narrateNow(I18n.translate("mco.trial.message.line1"), I18n.translate("mco.trial.message.line2"));
         } else if (!this.field_25723 || i > 0) {
            RealmsServer lv = RealmsMainScreen.this.realmsServers.get(i - (this.field_25723 ? 1 : 0));
            RealmsMainScreen.this.selectedServerId = lv.id;
            RealmsMainScreen.this.updateButtonStates(lv);
            if (lv.state == RealmsServer.State.UNINITIALIZED) {
               Realms.narrateNow(I18n.translate("mco.selectServer.uninitialized") + I18n.translate("mco.gui.button"));
            } else {
               Realms.narrateNow(I18n.translate("narrator.select", lv.name));
            }
         }
      }

      @Override
      public void itemClicked(int cursorY, int selectionIndex, double mouseX, double mouseY, int listWidth) {
         if (this.field_25723) {
            if (selectionIndex == 0) {
               RealmsMainScreen.this.popupOpenedByUser = true;
               return;
            }

            selectionIndex--;
         }

         if (selectionIndex < RealmsMainScreen.this.realmsServers.size()) {
            RealmsServer lv = RealmsMainScreen.this.realmsServers.get(selectionIndex);
            if (lv != null) {
               if (lv.state == RealmsServer.State.UNINITIALIZED) {
                  RealmsMainScreen.this.selectedServerId = -1L;
                  MinecraftClient.getInstance().openScreen(new RealmsCreateRealmScreen(lv, RealmsMainScreen.this));
               } else {
                  RealmsMainScreen.this.selectedServerId = lv.id;
               }

               if (RealmsMainScreen.this.hoverState == RealmsMainScreen.HoverState.CONFIGURE) {
                  RealmsMainScreen.this.selectedServerId = lv.id;
                  RealmsMainScreen.this.configureClicked(lv);
               } else if (RealmsMainScreen.this.hoverState == RealmsMainScreen.HoverState.LEAVE) {
                  RealmsMainScreen.this.selectedServerId = lv.id;
                  RealmsMainScreen.this.leaveClicked(lv);
               } else if (RealmsMainScreen.this.hoverState == RealmsMainScreen.HoverState.EXPIRED) {
                  RealmsMainScreen.this.onRenew();
               }
            }
         }
      }

      @Override
      public int getMaxPosition() {
         return this.getItemCount() * 36;
      }

      @Override
      public int getRowWidth() {
         return 300;
      }
   }

   @Environment(EnvType.CLIENT)
   class RealmSelectionListEntry extends RealmsMainScreen.Entry {
      private final RealmsServer mServerData;

      public RealmSelectionListEntry(RealmsServer serverData) {
         this.mServerData = serverData;
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         this.method_20945(this.mServerData, matrices, x, y, mouseX, mouseY);
      }

      @Override
      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         if (this.mServerData.state == RealmsServer.State.UNINITIALIZED) {
            RealmsMainScreen.this.selectedServerId = -1L;
            RealmsMainScreen.this.client.openScreen(new RealmsCreateRealmScreen(this.mServerData, RealmsMainScreen.this));
         } else {
            RealmsMainScreen.this.selectedServerId = this.mServerData.id;
         }

         return true;
      }

      private void method_20945(RealmsServer arg, MatrixStack arg2, int i, int j, int k, int l) {
         this.renderMcoServerItem(arg, arg2, i + 36, j, k, l);
      }

      private void renderMcoServerItem(RealmsServer serverData, MatrixStack arg2, int i, int j, int k, int l) {
         if (serverData.state == RealmsServer.State.UNINITIALIZED) {
            RealmsMainScreen.this.client.getTextureManager().bindTexture(RealmsMainScreen.WORLD_ICON);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableAlphaTest();
            DrawableHelper.drawTexture(arg2, i + 10, j + 6, 0.0F, 0.0F, 40, 20, 40, 20);
            float f = 0.5F + (1.0F + MathHelper.sin((float)RealmsMainScreen.this.animTick * 0.25F)) * 0.25F;
            int m = 0xFF000000 | (int)(127.0F * f) << 16 | (int)(255.0F * f) << 8 | (int)(127.0F * f);
            DrawableHelper.drawCenteredText(arg2, RealmsMainScreen.this.textRenderer, RealmsMainScreen.field_26450, i + 10 + 40 + 75, j + 12, m);
         } else {
            int n = 225;
            int o = 2;
            if (serverData.expired) {
               RealmsMainScreen.this.drawExpired(arg2, i + 225 - 14, j + 2, k, l);
            } else if (serverData.state == RealmsServer.State.CLOSED) {
               RealmsMainScreen.this.drawClose(arg2, i + 225 - 14, j + 2, k, l);
            } else if (RealmsMainScreen.this.isSelfOwnedServer(serverData) && serverData.daysLeft < 7) {
               RealmsMainScreen.this.method_24987(arg2, i + 225 - 14, j + 2, k, l, serverData.daysLeft);
            } else if (serverData.state == RealmsServer.State.OPEN) {
               RealmsMainScreen.this.drawOpen(arg2, i + 225 - 14, j + 2, k, l);
            }

            if (!RealmsMainScreen.this.isSelfOwnedServer(serverData) && !RealmsMainScreen.overrideConfigure) {
               RealmsMainScreen.this.drawLeave(arg2, i + 225, j + 2, k, l);
            } else {
               RealmsMainScreen.this.drawConfigure(arg2, i + 225, j + 2, k, l);
            }

            if (!"0".equals(serverData.serverPing.nrOfPlayers)) {
               String string = Formatting.GRAY + "" + serverData.serverPing.nrOfPlayers;
               RealmsMainScreen.this.textRenderer
                  .draw(arg2, string, (float)(i + 207 - RealmsMainScreen.this.textRenderer.getWidth(string)), (float)(j + 3), 8421504);
               if (k >= i + 207 - RealmsMainScreen.this.textRenderer.getWidth(string)
                  && k <= i + 207
                  && l >= j + 1
                  && l <= j + 10
                  && l < RealmsMainScreen.this.height - 40
                  && l > 32
                  && !RealmsMainScreen.this.shouldShowPopup()) {
                  RealmsMainScreen.this.method_27452(new LiteralText(serverData.serverPing.playerList));
               }
            }

            if (RealmsMainScreen.this.isSelfOwnedServer(serverData) && serverData.expired) {
               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               RenderSystem.enableBlend();
               RealmsMainScreen.this.client.getTextureManager().bindTexture(RealmsMainScreen.WIDGETS);
               RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
               Text lv;
               Text lv2;
               if (serverData.expiredTrial) {
                  lv = RealmsMainScreen.field_26453;
                  lv2 = RealmsMainScreen.field_26454;
               } else {
                  lv = RealmsMainScreen.field_26451;
                  lv2 = RealmsMainScreen.field_26452;
               }

               int p = RealmsMainScreen.this.textRenderer.getWidth(lv2) + 17;
               int q = 16;
               int r = i + RealmsMainScreen.this.textRenderer.getWidth(lv) + 8;
               int s = j + 13;
               boolean bl = false;
               if (k >= r && k < r + p && l > s && l <= s + 16 & l < RealmsMainScreen.this.height - 40 && l > 32 && !RealmsMainScreen.this.shouldShowPopup()) {
                  bl = true;
                  RealmsMainScreen.this.hoverState = RealmsMainScreen.HoverState.EXPIRED;
               }

               int t = bl ? 2 : 1;
               DrawableHelper.drawTexture(arg2, r, s, 0.0F, (float)(46 + t * 20), p / 2, 8, 256, 256);
               DrawableHelper.drawTexture(arg2, r + p / 2, s, (float)(200 - p / 2), (float)(46 + t * 20), p / 2, 8, 256, 256);
               DrawableHelper.drawTexture(arg2, r, s + 8, 0.0F, (float)(46 + t * 20 + 12), p / 2, 8, 256, 256);
               DrawableHelper.drawTexture(arg2, r + p / 2, s + 8, (float)(200 - p / 2), (float)(46 + t * 20 + 12), p / 2, 8, 256, 256);
               RenderSystem.disableBlend();
               int u = j + 11 + 5;
               int v = bl ? 16777120 : 16777215;
               RealmsMainScreen.this.textRenderer.draw(arg2, lv, (float)(i + 2), (float)(u + 1), 15553363);
               DrawableHelper.drawCenteredText(arg2, RealmsMainScreen.this.textRenderer, lv2, r + p / 2, u + 1, v);
            } else {
               if (serverData.worldType == RealmsServer.WorldType.MINIGAME) {
                  int w = 13413468;
                  int x = RealmsMainScreen.this.textRenderer.getWidth(RealmsMainScreen.field_26455);
                  RealmsMainScreen.this.textRenderer.draw(arg2, RealmsMainScreen.field_26455, (float)(i + 2), (float)(j + 12), 13413468);
                  RealmsMainScreen.this.textRenderer.draw(arg2, serverData.getMinigameName(), (float)(i + 2 + x), (float)(j + 12), 7105644);
               } else {
                  RealmsMainScreen.this.textRenderer.draw(arg2, serverData.getDescription(), (float)(i + 2), (float)(j + 12), 7105644);
               }

               if (!RealmsMainScreen.this.isSelfOwnedServer(serverData)) {
                  RealmsMainScreen.this.textRenderer.draw(arg2, serverData.owner, (float)(i + 2), (float)(j + 12 + 11), 5000268);
               }
            }

            RealmsMainScreen.this.textRenderer.draw(arg2, serverData.getName(), (float)(i + 2), (float)(j + 1), 16777215);
            RealmsTextureManager.withBoundFace(serverData.ownerUUID, () -> {
               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               DrawableHelper.drawTexture(arg2, i - 36, j, 32, 32, 8.0F, 8.0F, 8, 8, 64, 64);
               DrawableHelper.drawTexture(arg2, i - 36, j, 32, 32, 40.0F, 8.0F, 8, 8, 64, 64);
            });
         }
      }
   }

   @Environment(EnvType.CLIENT)
   class RealmSelectionListTrialEntry extends RealmsMainScreen.Entry {
      private RealmSelectionListTrialEntry() {
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         this.renderTrialItem(matrices, index, x, y, mouseX, mouseY);
      }

      @Override
      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         RealmsMainScreen.this.popupOpenedByUser = true;
         return true;
      }

      private void renderTrialItem(MatrixStack arg, int index, int x, int y, int mouseX, int mouseY) {
         int n = y + 8;
         int o = 0;
         boolean bl = false;
         if (x <= mouseX && mouseX <= (int)RealmsMainScreen.this.realmSelectionList.getScrollAmount() && y <= mouseY && mouseY <= y + 32) {
            bl = true;
         }

         int p = 8388479;
         if (bl && !RealmsMainScreen.this.shouldShowPopup()) {
            p = 6077788;
         }

         for (Text lv : RealmsMainScreen.field_26449) {
            DrawableHelper.drawCenteredText(arg, RealmsMainScreen.this.textRenderer, lv, RealmsMainScreen.this.width / 2, n + o, p);
            o += 10;
         }
      }
   }

   @Environment(EnvType.CLIENT)
   class ShowPopupButton extends ButtonWidget {
      public ShowPopupButton() {
         super(
            RealmsMainScreen.this.width - 37,
            6,
            20,
            20,
            new TranslatableText("mco.selectServer.info"),
            arg2 -> RealmsMainScreen.this.popupOpenedByUser = !RealmsMainScreen.this.popupOpenedByUser
         );
      }

      @Override
      public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
         RealmsMainScreen.this.renderMoreInfo(matrices, mouseX, mouseY, this.x, this.y, this.isHovered());
      }
   }
}
