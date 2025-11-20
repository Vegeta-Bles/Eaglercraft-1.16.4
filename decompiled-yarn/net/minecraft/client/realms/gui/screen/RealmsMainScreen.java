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

   public RealmsMainScreen(Screen _snowman) {
      this.lastScreen = _snowman;
      this.rateLimiter = RateLimiter.create(0.016666668F);
   }

   private boolean shouldShowMessageInList() {
      if (hasParentalConsent() && this.hasFetchedServers) {
         if (this.trialsAvailable && !this.createdTrial) {
            return true;
         } else {
            for (RealmsServer _snowman : this.realmsServers) {
               if (_snowman.ownerUUID.equals(this.client.getSession().getUuid())) {
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
            _snowman -> this.leaveClicked(this.findServer(this.selectedServerId))
         )
      );
      this.configureButton = this.addButton(
         new ButtonWidget(
            this.width / 2 - 190,
            this.height - 32,
            90,
            20,
            new TranslatableText("mco.selectServer.configure"),
            _snowman -> this.configureClicked(this.findServer(this.selectedServerId))
         )
      );
      this.playButton = this.addButton(new ButtonWidget(this.width / 2 - 93, this.height - 32, 90, 20, new TranslatableText("mco.selectServer.play"), _snowman -> {
         RealmsServer _snowmanx = this.findServer(this.selectedServerId);
         if (_snowmanx != null) {
            this.play(_snowmanx, this);
         }
      }));
      this.backButton = this.addButton(new ButtonWidget(this.width / 2 + 4, this.height - 32, 90, 20, ScreenTexts.BACK, _snowman -> {
         if (!this.justClosedPopup) {
            this.client.openScreen(this.lastScreen);
         }
      }));
      this.renewButton = this.addButton(
         new ButtonWidget(this.width / 2 + 100, this.height - 32, 90, 20, new TranslatableText("mco.selectServer.expiredRenew"), _snowman -> this.onRenew())
      );
      this.pendingInvitesButton = this.addButton(new RealmsMainScreen.PendingInvitesButton());
      this.newsButton = this.addButton(new RealmsMainScreen.NewsButton());
      this.showPopupButton = this.addButton(new RealmsMainScreen.ShowPopupButton());
      this.closeButton = this.addButton(new RealmsMainScreen.CloseButton());
      this.createTrialButton = this.addButton(
         new ButtonWidget(this.width / 2 + 52, this.popupY0() + 137 - 20, 98, 20, new TranslatableText("mco.selectServer.trial"), _snowman -> {
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
            _snowman -> Util.getOperatingSystem().open("https://aka.ms/BuyJavaRealms")
         )
      );
      RealmsServer _snowman = this.findServer(this.selectedServerId);
      this.updateButtonStates(_snowman);
   }

   private void updateButtonStates(@Nullable RealmsServer server) {
      this.playButton.active = this.shouldPlayButtonBeActive(server) && !this.shouldShowPopup();
      this.renewButton.visible = this.shouldRenewButtonBeActive(server);
      this.configureButton.visible = this.shouldConfigureButtonBeVisible(server);
      this.leaveButton.visible = this.shouldLeaveButtonBeVisible(server);
      boolean _snowman = this.shouldShowPopup() && this.trialsAvailable && !this.createdTrial;
      this.createTrialButton.visible = _snowman;
      this.createTrialButton.active = _snowman;
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
            List<RealmsServer> _snowman = realmsDataFetcher.getServers();
            this.realmSelectionList.clear();
            boolean _snowmanx = !this.hasFetchedServers;
            if (_snowmanx) {
               this.hasFetchedServers = true;
            }

            if (_snowman != null) {
               boolean _snowmanxx = false;

               for (RealmsServer _snowmanxxx : _snowman) {
                  if (this.method_25001(_snowmanxxx)) {
                     _snowmanxx = true;
                  }
               }

               this.realmsServers = _snowman;
               if (this.shouldShowMessageInList()) {
                  this.realmSelectionList.method_30161(new RealmsMainScreen.RealmSelectionListTrialEntry());
               }

               for (RealmsServer _snowmanxxxx : this.realmsServers) {
                  this.realmSelectionList.addEntry(new RealmsMainScreen.RealmSelectionListEntry(_snowmanxxxx));
               }

               if (!regionsPinged && _snowmanxx) {
                  regionsPinged = true;
                  this.pingRegions();
               }
            }

            if (_snowmanx) {
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
            boolean _snowmanxx = realmsDataFetcher.isTrialAvailable();
            if (_snowmanxx != this.trialsAvailable && this.shouldShowPopup()) {
               this.trialsAvailable = _snowmanxx;
               this.showingPopup = false;
            } else {
               this.trialsAvailable = _snowmanxx;
            }
         }

         if (realmsDataFetcher.isFetchedSinceLastTry(RealmsDataFetcher.Task.LIVE_STATS)) {
            RealmsServerPlayerLists _snowmanxx = realmsDataFetcher.getLivestats();

            for (RealmsServerPlayerList _snowmanxxxx : _snowmanxx.servers) {
               for (RealmsServer _snowmanxxxxx : this.realmsServers) {
                  if (_snowmanxxxxx.id == _snowmanxxxx.serverId) {
                     _snowmanxxxxx.updateServerPing(_snowmanxxxx);
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
         List<RegionPingResult> _snowman = Ping.pingAllRegions();
         RealmsClient _snowmanx = RealmsClient.createRealmsClient();
         PingResult _snowmanxx = new PingResult();
         _snowmanxx.pingResults = _snowman;
         _snowmanxx.worldIds = this.getOwnedNonExpiredWorldIds();

         try {
            _snowmanx.sendPingResults(_snowmanxx);
         } catch (Throwable var5) {
            LOGGER.warn("Could not send ping result to Realms: ", var5);
         }
      }).start();
   }

   private List<Long> getOwnedNonExpiredWorldIds() {
      List<Long> _snowman = Lists.newArrayList();

      for (RealmsServer _snowmanx : this.realmsServers) {
         if (this.method_25001(_snowmanx)) {
            _snowman.add(_snowmanx.id);
         }
      }

      return _snowman;
   }

   @Override
   public void removed() {
      this.client.keyboard.setRepeatEvents(false);
      this.stopRealmsFetcher();
   }

   private void onRenew() {
      RealmsServer _snowman = this.findServer(this.selectedServerId);
      if (_snowman != null) {
         String _snowmanx = "https://aka.ms/ExtendJavaRealms?subscriptionId="
            + _snowman.remoteSubscriptionId
            + "&profileId="
            + this.client.getSession().getUuid()
            + "&ref="
            + (_snowman.expiredTrial ? "expiredTrial" : "expiredRealm");
         this.client.keyboard.setClipboard(_snowmanx);
         Util.getOperatingSystem().open(_snowmanx);
      }
   }

   private void checkClientCompatability() {
      if (!checkedClientCompatability) {
         checkedClientCompatability = true;
         (new Thread("MCO Compatability Checker #1") {
               @Override
               public void run() {
                  RealmsClient _snowman = RealmsClient.createRealmsClient();

                  try {
                     RealmsClient.CompatibleVersionResponse _snowmanx = _snowman.clientCompatible();
                     if (_snowmanx == RealmsClient.CompatibleVersionResponse.OUTDATED) {
                        RealmsMainScreen.realmsGenericErrorScreen = new RealmsClientOutdatedScreen(RealmsMainScreen.this.lastScreen, true);
                        RealmsMainScreen.this.client.execute(() -> RealmsMainScreen.this.client.openScreen(RealmsMainScreen.realmsGenericErrorScreen));
                        return;
                     }

                     if (_snowmanx == RealmsClient.CompatibleVersionResponse.OTHER) {
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
               RealmsClient _snowman = RealmsClient.createRealmsClient();

               try {
                  Boolean _snowmanx = _snowman.mcoEnabled();
                  if (_snowmanx) {
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
               RealmsClient _snowman = RealmsClient.createRealmsClient();

               try {
                  Boolean _snowmanx = _snowman.stageAvailable();
                  if (_snowmanx) {
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
               RealmsClient _snowman = RealmsClient.createRealmsClient();

               try {
                  Boolean _snowmanx = _snowman.stageAvailable();
                  if (_snowmanx) {
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

   private void configureClicked(RealmsServer _snowman) {
      if (this.client.getSession().getUuid().equals(_snowman.ownerUUID) || overrideConfigure) {
         this.saveListScrollPosition();
         this.client.openScreen(new RealmsConfigureWorldScreen(this, _snowman.id));
      }
   }

   private void leaveClicked(@Nullable RealmsServer selectedServer) {
      if (selectedServer != null && !this.client.getSession().getUuid().equals(selectedServer.ownerUUID)) {
         this.saveListScrollPosition();
         Text _snowman = new TranslatableText("mco.configure.world.leave.question.line1");
         Text _snowmanx = new TranslatableText("mco.configure.world.leave.question.line2");
         this.client.openScreen(new RealmsLongConfirmationScreen(this::method_24991, RealmsLongConfirmationScreen.Type.Info, _snowman, _snowmanx, true));
      }
   }

   private void saveListScrollPosition() {
      lastScrollYPosition = (int)this.realmSelectionList.getScrollAmount();
   }

   @Nullable
   private RealmsServer findServer(long id) {
      for (RealmsServer _snowman : this.realmsServers) {
         if (_snowman.id == id) {
            return _snowman;
         }
      }

      return null;
   }

   private void method_24991(boolean _snowman) {
      if (_snowman) {
         (new Thread("Realms-leave-server") {
               @Override
               public void run() {
                  try {
                     RealmsServer _snowman = RealmsMainScreen.this.findServer(RealmsMainScreen.this.selectedServerId);
                     if (_snowman != null) {
                        RealmsClient _snowmanx = RealmsClient.createRealmsClient();
                        _snowmanx.uninviteMyselfFrom(_snowman.id);
                        RealmsMainScreen.this.client.execute(() -> RealmsMainScreen.this.method_31174(_snowman));
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

   private void method_31174(RealmsServer _snowman) {
      realmsDataFetcher.removeItem(_snowman);
      this.realmsServers.remove(_snowman);
      this.realmSelectionList
         .children()
         .removeIf(
            _snowmanx -> _snowmanx instanceof RealmsMainScreen.RealmSelectionListEntry
                  && ((RealmsMainScreen.RealmSelectionListEntry)_snowmanx).mServerData.id == this.selectedServerId
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
      this.keyCombos.forEach(_snowmanx -> _snowmanx.keyPressed(chr));
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

            RealmsServer _snowman = this.findServer(this.selectedServerId);
            this.playButton.active = this.shouldPlayButtonBeActive(_snowman);
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
         int _snowman = 8;
         int _snowmanx = 8;
         int _snowmanxx = 0;
         if ((Util.getMeasuringTimeMs() / 800L & 1L) == 1L) {
            _snowmanxx = 8;
         }

         DrawableHelper.drawTexture(
            matrices,
            this.createTrialButton.x + this.createTrialButton.getWidth() - 8 - 4,
            this.createTrialButton.y + this.createTrialButton.getHeight() / 2 - 4,
            0.0F,
            (float)_snowmanxx,
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
      int _snowman = this.popupX0();
      int _snowmanx = this.popupY0();
      return xm < (double)(_snowman - 5) || xm > (double)(_snowman + 315) || ym < (double)(_snowmanx - 5) || ym > (double)(_snowmanx + 171);
   }

   private void drawPopup(MatrixStack matrices, int mouseX, int mouseY) {
      int _snowman = this.popupX0();
      int _snowmanx = this.popupY0();
      if (!this.showingPopup) {
         this.carouselIndex = 0;
         this.carouselTick = 0;
         this.hasSwitchedCarouselImage = true;
         this.updateButtonStates(null);
         if (this.children.contains(this.realmSelectionList)) {
            Element _snowmanxx = this.realmSelectionList;
            if (!this.children.remove(_snowmanxx)) {
               LOGGER.error("Unable to remove widget: " + _snowmanxx);
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
      int _snowmanxx = 0;
      int _snowmanxxx = 32;
      DrawableHelper.drawTexture(matrices, 0, 32, 0.0F, 0.0F, this.width, this.height - 40 - 32, 310, 166);
      RenderSystem.disableBlend();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.client.getTextureManager().bindTexture(POPUP);
      DrawableHelper.drawTexture(matrices, _snowman, _snowmanx, 0.0F, 0.0F, 310, 166, 310, 166);
      if (!IMAGES.isEmpty()) {
         this.client.getTextureManager().bindTexture(IMAGES.get(this.carouselIndex));
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         DrawableHelper.drawTexture(matrices, _snowman + 7, _snowmanx + 7, 0.0F, 0.0F, 195, 152, 195, 152);
         if (this.carouselTick % 95 < 5) {
            if (!this.hasSwitchedCarouselImage) {
               this.carouselIndex = (this.carouselIndex + 1) % IMAGES.size();
               this.hasSwitchedCarouselImage = true;
            }
         } else {
            this.hasSwitchedCarouselImage = false;
         }
      }

      this.field_26466.method_30896(matrices, this.width / 2 + 52, _snowmanx + 7, 10, 5000268);
   }

   private int popupX0() {
      return (this.width - 310) / 2;
   }

   private int popupY0() {
      return this.height / 2 - 80;
   }

   private void drawInvitationPendingIcon(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman, boolean _snowman, boolean _snowman) {
      int _snowmanxxxxxxx = this.numberOfPendingInvites;
      boolean _snowmanxxxxxxxx = this.inPendingInvitationArea((double)_snowman, (double)_snowman);
      boolean _snowmanxxxxxxxxx = _snowman && _snowman;
      if (_snowmanxxxxxxxxx) {
         float _snowmanxxxxxxxxxx = 0.25F + (1.0F + MathHelper.sin((float)this.animTick * 0.5F)) * 0.25F;
         int _snowmanxxxxxxxxxxx = 0xFF000000 | (int)(_snowmanxxxxxxxxxx * 64.0F) << 16 | (int)(_snowmanxxxxxxxxxx * 64.0F) << 8 | (int)(_snowmanxxxxxxxxxx * 64.0F) << 0;
         this.fillGradient(_snowman, _snowman - 2, _snowman - 2, _snowman + 18, _snowman + 18, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxx);
         _snowmanxxxxxxxxxxx = 0xFF000000 | (int)(_snowmanxxxxxxxxxx * 255.0F) << 16 | (int)(_snowmanxxxxxxxxxx * 255.0F) << 8 | (int)(_snowmanxxxxxxxxxx * 255.0F) << 0;
         this.fillGradient(_snowman, _snowman - 2, _snowman - 2, _snowman + 18, _snowman - 1, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxx);
         this.fillGradient(_snowman, _snowman - 2, _snowman - 2, _snowman - 1, _snowman + 18, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxx);
         this.fillGradient(_snowman, _snowman + 17, _snowman - 2, _snowman + 18, _snowman + 18, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxx);
         this.fillGradient(_snowman, _snowman - 2, _snowman + 17, _snowman + 18, _snowman + 18, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxx);
      }

      this.client.getTextureManager().bindTexture(INVITE_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      boolean _snowmanxxxxxxxxxx = _snowman && _snowman;
      float _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx ? 16.0F : 0.0F;
      DrawableHelper.drawTexture(_snowman, _snowman, _snowman - 6, _snowmanxxxxxxxxxxx, 0.0F, 15, 25, 31, 25);
      boolean _snowmanxxxxxxxxxxxx = _snowman && _snowmanxxxxxxx != 0;
      if (_snowmanxxxxxxxxxxxx) {
         int _snowmanxxxxxxxxxxxxx = (Math.min(_snowmanxxxxxxx, 6) - 1) * 8;
         int _snowmanxxxxxxxxxxxxxx = (int)(
            Math.max(0.0F, Math.max(MathHelper.sin((float)(10 + this.animTick) * 0.57F), MathHelper.cos((float)this.animTick * 0.35F))) * -6.0F
         );
         this.client.getTextureManager().bindTexture(INVITATION_ICON);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         float _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxx ? 8.0F : 0.0F;
         DrawableHelper.drawTexture(_snowman, _snowman + 4, _snowman + 4 + _snowmanxxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, 8, 8, 48, 16);
      }

      int _snowmanxxxxxxxxxxxxx = _snowman + 12;
      boolean _snowmanxxxxxxxxxxxxxx = _snowman && _snowmanxxxxxxxx;
      if (_snowmanxxxxxxxxxxxxxx) {
         Text _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxx == 0 ? field_26447 : field_26448;
         int _snowmanxxxxxxxxxxxxxxxx = this.textRenderer.getWidth(_snowmanxxxxxxxxxxxxxxx);
         this.fillGradient(_snowman, _snowmanxxxxxxxxxxxxx - 3, _snowman - 3, _snowmanxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxx + 3, _snowman + 8 + 3, -1073741824, -1073741824);
         this.textRenderer.drawWithShadow(_snowman, _snowmanxxxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxxx, (float)_snowman, -1);
      }
   }

   private boolean inPendingInvitationArea(double xm, double ym) {
      int _snowman = this.width / 2 + 50;
      int _snowmanx = this.width / 2 + 66;
      int _snowmanxx = 11;
      int _snowmanxxx = 23;
      if (this.numberOfPendingInvites != 0) {
         _snowman -= 3;
         _snowmanx += 3;
         _snowmanxx -= 5;
         _snowmanxxx += 5;
      }

      return (double)_snowman <= xm && xm <= (double)_snowmanx && (double)_snowmanxx <= ym && ym <= (double)_snowmanxxx;
   }

   public void play(RealmsServer _snowman, Screen _snowman) {
      if (_snowman != null) {
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
         this.client.openScreen(new RealmsLongRunningMcoTaskScreen(_snowman, new RealmsGetServerDetailsTask(this, _snowman, _snowman, this.connectLock)));
      }
   }

   private boolean isSelfOwnedServer(RealmsServer serverData) {
      return serverData.ownerUUID != null && serverData.ownerUUID.equals(this.client.getSession().getUuid());
   }

   private boolean method_25001(RealmsServer _snowman) {
      return this.isSelfOwnedServer(_snowman) && !_snowman.expired;
   }

   private void drawExpired(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      this.client.getTextureManager().bindTexture(EXPIRED_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      DrawableHelper.drawTexture(_snowman, _snowman, _snowman, 0.0F, 0.0F, 10, 28, 10, 28);
      if (_snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 27 && _snowman < this.height - 40 && _snowman > 32 && !this.shouldShowPopup()) {
         this.method_27452(field_26457);
      }
   }

   private void method_24987(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      this.client.getTextureManager().bindTexture(EXPIRES_SOON_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      if (this.animTick % 20 < 10) {
         DrawableHelper.drawTexture(_snowman, _snowman, _snowman, 0.0F, 0.0F, 10, 28, 20, 28);
      } else {
         DrawableHelper.drawTexture(_snowman, _snowman, _snowman, 10.0F, 0.0F, 10, 28, 20, 28);
      }

      if (_snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 27 && _snowman < this.height - 40 && _snowman > 32 && !this.shouldShowPopup()) {
         if (_snowman <= 0) {
            this.method_27452(field_26458);
         } else if (_snowman == 1) {
            this.method_27452(field_26459);
         } else {
            this.method_27452(new TranslatableText("mco.selectServer.expires.days", _snowman));
         }
      }
   }

   private void drawOpen(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      this.client.getTextureManager().bindTexture(ON_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      DrawableHelper.drawTexture(_snowman, _snowman, _snowman, 0.0F, 0.0F, 10, 28, 10, 28);
      if (_snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 27 && _snowman < this.height - 40 && _snowman > 32 && !this.shouldShowPopup()) {
         this.method_27452(field_26460);
      }
   }

   private void drawClose(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      this.client.getTextureManager().bindTexture(OFF_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      DrawableHelper.drawTexture(_snowman, _snowman, _snowman, 0.0F, 0.0F, 10, 28, 10, 28);
      if (_snowman >= _snowman && _snowman <= _snowman + 9 && _snowman >= _snowman && _snowman <= _snowman + 27 && _snowman < this.height - 40 && _snowman > 32 && !this.shouldShowPopup()) {
         this.method_27452(field_26461);
      }
   }

   private void drawLeave(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      boolean _snowmanxxxxx = false;
      if (_snowman >= _snowman && _snowman <= _snowman + 28 && _snowman >= _snowman && _snowman <= _snowman + 28 && _snowman < this.height - 40 && _snowman > 32 && !this.shouldShowPopup()) {
         _snowmanxxxxx = true;
      }

      this.client.getTextureManager().bindTexture(LEAVE_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float _snowmanxxxxxx = _snowmanxxxxx ? 28.0F : 0.0F;
      DrawableHelper.drawTexture(_snowman, _snowman, _snowman, _snowmanxxxxxx, 0.0F, 28, 28, 56, 28);
      if (_snowmanxxxxx) {
         this.method_27452(field_26462);
         this.hoverState = RealmsMainScreen.HoverState.LEAVE;
      }
   }

   private void drawConfigure(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
      boolean _snowmanxxxxx = false;
      if (_snowman >= _snowman && _snowman <= _snowman + 28 && _snowman >= _snowman && _snowman <= _snowman + 28 && _snowman < this.height - 40 && _snowman > 32 && !this.shouldShowPopup()) {
         _snowmanxxxxx = true;
      }

      this.client.getTextureManager().bindTexture(CONFIGURE_ICON);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float _snowmanxxxxxx = _snowmanxxxxx ? 28.0F : 0.0F;
      DrawableHelper.drawTexture(_snowman, _snowman, _snowman, _snowmanxxxxxx, 0.0F, 28, 28, 56, 28);
      if (_snowmanxxxxx) {
         this.method_27452(field_26463);
         this.hoverState = RealmsMainScreen.HoverState.CONFIGURE;
      }
   }

   protected void renderMousehoverTooltip(MatrixStack _snowman, List<Text> _snowman, int _snowman, int _snowman) {
      if (!_snowman.isEmpty()) {
         int _snowmanxxxx = 0;
         int _snowmanxxxxx = 0;

         for (Text _snowmanxxxxxx : _snowman) {
            int _snowmanxxxxxxx = this.textRenderer.getWidth(_snowmanxxxxxx);
            if (_snowmanxxxxxxx > _snowmanxxxxx) {
               _snowmanxxxxx = _snowmanxxxxxxx;
            }
         }

         int _snowmanxxxxxxx = _snowman - _snowmanxxxxx - 5;
         int _snowmanxxxxxxxx = _snowman;
         if (_snowmanxxxxxxx < 0) {
            _snowmanxxxxxxx = _snowman + 12;
         }

         for (Text _snowmanxxxxxxxxx : _snowman) {
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx - (_snowmanxxxx == 0 ? 3 : 0) + _snowmanxxxx;
            this.fillGradient(_snowman, _snowmanxxxxxxx - 3, _snowmanxxxxxxxxxx, _snowmanxxxxxxx + _snowmanxxxxx + 3, _snowmanxxxxxxxx + 8 + 3 + _snowmanxxxx, -1073741824, -1073741824);
            this.textRenderer.drawWithShadow(_snowman, _snowmanxxxxxxxxx, (float)_snowmanxxxxxxx, (float)(_snowmanxxxxxxxx + _snowmanxxxx), 16777215);
            _snowmanxxxx += 10;
         }
      }
   }

   private void renderMoreInfo(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman, boolean _snowman) {
      boolean _snowmanxxxxxx = false;
      if (_snowman >= _snowman && _snowman <= _snowman + 20 && _snowman >= _snowman && _snowman <= _snowman + 20) {
         _snowmanxxxxxx = true;
      }

      this.client.getTextureManager().bindTexture(QUESTIONMARK);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      float _snowmanxxxxxxx = _snowman ? 20.0F : 0.0F;
      DrawableHelper.drawTexture(_snowman, _snowman, _snowman, _snowmanxxxxxxx, 0.0F, 20, 20, 40, 20);
      if (_snowmanxxxxxx) {
         this.method_27452(field_26464);
      }
   }

   private void renderNews(MatrixStack _snowman, int _snowman, int _snowman, boolean _snowman, int _snowman, int _snowman, boolean _snowman, boolean _snowman) {
      boolean _snowmanxxxxxxxx = false;
      if (_snowman >= _snowman && _snowman <= _snowman + 20 && _snowman >= _snowman && _snowman <= _snowman + 20) {
         _snowmanxxxxxxxx = true;
      }

      this.client.getTextureManager().bindTexture(NEWS_ICON);
      if (_snowman) {
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         RenderSystem.color4f(0.5F, 0.5F, 0.5F, 1.0F);
      }

      boolean _snowmanxxxxxxxxx = _snowman && _snowman;
      float _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx ? 20.0F : 0.0F;
      DrawableHelper.drawTexture(_snowman, _snowman, _snowman, _snowmanxxxxxxxxxx, 0.0F, 20, 20, 40, 20);
      if (_snowmanxxxxxxxx && _snowman) {
         this.method_27452(field_26465);
      }

      if (_snowman && _snowman) {
         int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx
            ? 0
            : (int)(Math.max(0.0F, Math.max(MathHelper.sin((float)(10 + this.animTick) * 0.57F), MathHelper.cos((float)this.animTick * 0.35F))) * -6.0F);
         this.client.getTextureManager().bindTexture(INVITATION_ICON);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         DrawableHelper.drawTexture(_snowman, _snowman + 10, _snowman + 2 + _snowmanxxxxxxxxxxx, 40.0F, 0.0F, 8, 8, 48, 16);
      }
   }

   private void renderLocal(MatrixStack _snowman) {
      String _snowmanx = "LOCAL!";
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.pushMatrix();
      RenderSystem.translatef((float)(this.width / 2 - 25), 20.0F, 0.0F);
      RenderSystem.rotatef(-20.0F, 0.0F, 0.0F, 1.0F);
      RenderSystem.scalef(1.5F, 1.5F, 1.5F);
      this.textRenderer.draw(_snowman, "LOCAL!", 0.0F, 0.0F, 8388479);
      RenderSystem.popMatrix();
   }

   private void renderStage(MatrixStack _snowman) {
      String _snowmanx = "STAGE!";
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.pushMatrix();
      RenderSystem.translatef((float)(this.width / 2 - 25), 20.0F, 0.0F);
      RenderSystem.rotatef(-20.0F, 0.0F, 0.0F, 1.0F);
      RenderSystem.scalef(1.5F, 1.5F, 1.5F);
      this.textRenderer.draw(_snowman, "STAGE!", 0.0F, 0.0F, -256);
      RenderSystem.popMatrix();
   }

   public RealmsMainScreen newScreen() {
      RealmsMainScreen _snowman = new RealmsMainScreen(this.lastScreen);
      _snowman.init(this.client, this.width, this.height);
      return _snowman;
   }

   public static void method_23765(ResourceManager manager) {
      Collection<Identifier> _snowman = manager.findResources("textures/gui/images", _snowmanx -> _snowmanx.endsWith(".png"));
      IMAGES = _snowman.stream().filter(_snowmanx -> _snowmanx.getNamespace().equals("realms")).collect(ImmutableList.toImmutableList());
   }

   private void method_27452(Text... _snowman) {
      this.toolTip = Arrays.asList(_snowman);
   }

   private void method_24985(ButtonWidget _snowman) {
      this.client.openScreen(new RealmsPendingInvitesScreen(this.lastScreen));
   }

   class CloseButton extends ButtonWidget {
      public CloseButton() {
         super(
            RealmsMainScreen.this.popupX0() + 4,
            RealmsMainScreen.this.popupY0() + 4,
            12,
            12,
            new TranslatableText("mco.selectServer.close"),
            _snowmanx -> RealmsMainScreen.this.onClosePopup()
         );
      }

      @Override
      public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
         RealmsMainScreen.this.client.getTextureManager().bindTexture(RealmsMainScreen.CROSS_ICON);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         float _snowman = this.isHovered() ? 12.0F : 0.0F;
         drawTexture(matrices, this.x, this.y, 0.0F, _snowman, 12, 12, 12, 24);
         if (this.isMouseOver((double)mouseX, (double)mouseY)) {
            RealmsMainScreen.this.method_27452(this.getMessage());
         }
      }
   }

   abstract class Entry extends AlwaysSelectedEntryListWidget.Entry<RealmsMainScreen.Entry> {
      private Entry() {
      }
   }

   static enum HoverState {
      NONE,
      EXPIRED,
      LEAVE,
      CONFIGURE;

      private HoverState() {
      }
   }

   class NewsButton extends ButtonWidget {
      public NewsButton() {
         super(RealmsMainScreen.this.width - 62, 6, 20, 20, LiteralText.EMPTY, _snowmanx -> {
            if (RealmsMainScreen.this.newsLink != null) {
               Util.getOperatingSystem().open(RealmsMainScreen.this.newsLink);
               if (RealmsMainScreen.this.hasUnreadNews) {
                  RealmsPersistence.RealmsPersistenceData _snowmanxx = RealmsPersistence.readFile();
                  _snowmanxx.hasUnreadNews = false;
                  RealmsMainScreen.this.hasUnreadNews = false;
                  RealmsPersistence.writeFile(_snowmanxx);
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

   class PendingInvitesButton extends ButtonWidget implements TickableElement {
      public PendingInvitesButton() {
         super(RealmsMainScreen.this.width / 2 + 47, 6, 22, 22, LiteralText.EMPTY, _snowmanx -> RealmsMainScreen.this.method_24985(_snowmanx));
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

      public int method_30161(RealmsMainScreen.Entry _snowman) {
         this.field_25723 = true;
         return this.addEntry(_snowman);
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
            AlwaysSelectedEntryListWidget.Entry _snowman = this.getSelected();
            return _snowman == null ? super.keyPressed(keyCode, scanCode, modifiers) : _snowman.mouseClicked(0.0, 0.0, 0);
         }
      }

      @Override
      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         if (button == 0 && mouseX < (double)this.getScrollbarPositionX() && mouseY >= (double)this.top && mouseY <= (double)this.bottom) {
            int _snowman = RealmsMainScreen.this.realmSelectionList.getRowLeft();
            int _snowmanx = this.getScrollbarPositionX();
            int _snowmanxx = (int)Math.floor(mouseY - (double)this.top) - this.headerHeight + (int)this.getScrollAmount() - 4;
            int _snowmanxxx = _snowmanxx / this.itemHeight;
            if (mouseX >= (double)_snowman && mouseX <= (double)_snowmanx && _snowmanxxx >= 0 && _snowmanxx >= 0 && _snowmanxxx < this.getItemCount()) {
               this.itemClicked(_snowmanxx, _snowmanxxx, mouseX, mouseY, this.width);
               RealmsMainScreen.this.clicks = RealmsMainScreen.this.clicks + 7;
               this.setSelected(_snowmanxxx);
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
            RealmsServer _snowman;
            if (this.field_25723) {
               if (index == 0) {
                  _snowman = null;
               } else {
                  if (index - 1 >= RealmsMainScreen.this.realmsServers.size()) {
                     RealmsMainScreen.this.selectedServerId = -1L;
                     return;
                  }

                  _snowman = RealmsMainScreen.this.realmsServers.get(index - 1);
               }
            } else {
               if (index >= RealmsMainScreen.this.realmsServers.size()) {
                  RealmsMainScreen.this.selectedServerId = -1L;
                  return;
               }

               _snowman = RealmsMainScreen.this.realmsServers.get(index);
            }

            RealmsMainScreen.this.updateButtonStates(_snowman);
            if (_snowman == null) {
               RealmsMainScreen.this.selectedServerId = -1L;
            } else if (_snowman.state == RealmsServer.State.UNINITIALIZED) {
               RealmsMainScreen.this.selectedServerId = -1L;
            } else {
               RealmsMainScreen.this.selectedServerId = _snowman.id;
               if (RealmsMainScreen.this.clicks >= 10 && RealmsMainScreen.this.playButton.active) {
                  RealmsMainScreen.this.play(RealmsMainScreen.this.findServer(RealmsMainScreen.this.selectedServerId), RealmsMainScreen.this);
               }
            }
         }
      }

      public void setSelected(@Nullable RealmsMainScreen.Entry _snowman) {
         super.setSelected(_snowman);
         int _snowmanx = this.children().indexOf(_snowman);
         if (this.field_25723 && _snowmanx == 0) {
            Realms.narrateNow(I18n.translate("mco.trial.message.line1"), I18n.translate("mco.trial.message.line2"));
         } else if (!this.field_25723 || _snowmanx > 0) {
            RealmsServer _snowmanxx = RealmsMainScreen.this.realmsServers.get(_snowmanx - (this.field_25723 ? 1 : 0));
            RealmsMainScreen.this.selectedServerId = _snowmanxx.id;
            RealmsMainScreen.this.updateButtonStates(_snowmanxx);
            if (_snowmanxx.state == RealmsServer.State.UNINITIALIZED) {
               Realms.narrateNow(I18n.translate("mco.selectServer.uninitialized") + I18n.translate("mco.gui.button"));
            } else {
               Realms.narrateNow(I18n.translate("narrator.select", _snowmanxx.name));
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
            RealmsServer _snowman = RealmsMainScreen.this.realmsServers.get(selectionIndex);
            if (_snowman != null) {
               if (_snowman.state == RealmsServer.State.UNINITIALIZED) {
                  RealmsMainScreen.this.selectedServerId = -1L;
                  MinecraftClient.getInstance().openScreen(new RealmsCreateRealmScreen(_snowman, RealmsMainScreen.this));
               } else {
                  RealmsMainScreen.this.selectedServerId = _snowman.id;
               }

               if (RealmsMainScreen.this.hoverState == RealmsMainScreen.HoverState.CONFIGURE) {
                  RealmsMainScreen.this.selectedServerId = _snowman.id;
                  RealmsMainScreen.this.configureClicked(_snowman);
               } else if (RealmsMainScreen.this.hoverState == RealmsMainScreen.HoverState.LEAVE) {
                  RealmsMainScreen.this.selectedServerId = _snowman.id;
                  RealmsMainScreen.this.leaveClicked(_snowman);
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

      private void method_20945(RealmsServer _snowman, MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
         this.renderMcoServerItem(_snowman, _snowman, _snowman + 36, _snowman, _snowman, _snowman);
      }

      private void renderMcoServerItem(RealmsServer serverData, MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
         if (serverData.state == RealmsServer.State.UNINITIALIZED) {
            RealmsMainScreen.this.client.getTextureManager().bindTexture(RealmsMainScreen.WORLD_ICON);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.enableAlphaTest();
            DrawableHelper.drawTexture(_snowman, _snowman + 10, _snowman + 6, 0.0F, 0.0F, 40, 20, 40, 20);
            float _snowmanxxxxx = 0.5F + (1.0F + MathHelper.sin((float)RealmsMainScreen.this.animTick * 0.25F)) * 0.25F;
            int _snowmanxxxxxx = 0xFF000000 | (int)(127.0F * _snowmanxxxxx) << 16 | (int)(255.0F * _snowmanxxxxx) << 8 | (int)(127.0F * _snowmanxxxxx);
            DrawableHelper.drawCenteredText(_snowman, RealmsMainScreen.this.textRenderer, RealmsMainScreen.field_26450, _snowman + 10 + 40 + 75, _snowman + 12, _snowmanxxxxxx);
         } else {
            int _snowmanxxxxx = 225;
            int _snowmanxxxxxx = 2;
            if (serverData.expired) {
               RealmsMainScreen.this.drawExpired(_snowman, _snowman + 225 - 14, _snowman + 2, _snowman, _snowman);
            } else if (serverData.state == RealmsServer.State.CLOSED) {
               RealmsMainScreen.this.drawClose(_snowman, _snowman + 225 - 14, _snowman + 2, _snowman, _snowman);
            } else if (RealmsMainScreen.this.isSelfOwnedServer(serverData) && serverData.daysLeft < 7) {
               RealmsMainScreen.this.method_24987(_snowman, _snowman + 225 - 14, _snowman + 2, _snowman, _snowman, serverData.daysLeft);
            } else if (serverData.state == RealmsServer.State.OPEN) {
               RealmsMainScreen.this.drawOpen(_snowman, _snowman + 225 - 14, _snowman + 2, _snowman, _snowman);
            }

            if (!RealmsMainScreen.this.isSelfOwnedServer(serverData) && !RealmsMainScreen.overrideConfigure) {
               RealmsMainScreen.this.drawLeave(_snowman, _snowman + 225, _snowman + 2, _snowman, _snowman);
            } else {
               RealmsMainScreen.this.drawConfigure(_snowman, _snowman + 225, _snowman + 2, _snowman, _snowman);
            }

            if (!"0".equals(serverData.serverPing.nrOfPlayers)) {
               String _snowmanxxxxxxx = Formatting.GRAY + "" + serverData.serverPing.nrOfPlayers;
               RealmsMainScreen.this.textRenderer
                  .draw(_snowman, _snowmanxxxxxxx, (float)(_snowman + 207 - RealmsMainScreen.this.textRenderer.getWidth(_snowmanxxxxxxx)), (float)(_snowman + 3), 8421504);
               if (_snowman >= _snowman + 207 - RealmsMainScreen.this.textRenderer.getWidth(_snowmanxxxxxxx)
                  && _snowman <= _snowman + 207
                  && _snowman >= _snowman + 1
                  && _snowman <= _snowman + 10
                  && _snowman < RealmsMainScreen.this.height - 40
                  && _snowman > 32
                  && !RealmsMainScreen.this.shouldShowPopup()) {
                  RealmsMainScreen.this.method_27452(new LiteralText(serverData.serverPing.playerList));
               }
            }

            if (RealmsMainScreen.this.isSelfOwnedServer(serverData) && serverData.expired) {
               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               RenderSystem.enableBlend();
               RealmsMainScreen.this.client.getTextureManager().bindTexture(RealmsMainScreen.WIDGETS);
               RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
               Text _snowmanxxxxxxx;
               Text _snowmanxxxxxxxx;
               if (serverData.expiredTrial) {
                  _snowmanxxxxxxx = RealmsMainScreen.field_26453;
                  _snowmanxxxxxxxx = RealmsMainScreen.field_26454;
               } else {
                  _snowmanxxxxxxx = RealmsMainScreen.field_26451;
                  _snowmanxxxxxxxx = RealmsMainScreen.field_26452;
               }

               int _snowmanxxxxxxxxx = RealmsMainScreen.this.textRenderer.getWidth(_snowmanxxxxxxxx) + 17;
               int _snowmanxxxxxxxxxx = 16;
               int _snowmanxxxxxxxxxxx = _snowman + RealmsMainScreen.this.textRenderer.getWidth(_snowmanxxxxxxx) + 8;
               int _snowmanxxxxxxxxxxxx = _snowman + 13;
               boolean _snowmanxxxxxxxxxxxxx = false;
               if (_snowman >= _snowmanxxxxxxxxxxx
                  && _snowman < _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxx
                  && _snowman > _snowmanxxxxxxxxxxxx
                  && _snowman <= _snowmanxxxxxxxxxxxx + 16 & _snowman < RealmsMainScreen.this.height - 40
                  && _snowman > 32
                  && !RealmsMainScreen.this.shouldShowPopup()) {
                  _snowmanxxxxxxxxxxxxx = true;
                  RealmsMainScreen.this.hoverState = RealmsMainScreen.HoverState.EXPIRED;
               }

               int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx ? 2 : 1;
               DrawableHelper.drawTexture(_snowman, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.0F, (float)(46 + _snowmanxxxxxxxxxxxxxx * 20), _snowmanxxxxxxxxx / 2, 8, 256, 256);
               DrawableHelper.drawTexture(
                  _snowman,
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxx / 2,
                  _snowmanxxxxxxxxxxxx,
                  (float)(200 - _snowmanxxxxxxxxx / 2),
                  (float)(46 + _snowmanxxxxxxxxxxxxxx * 20),
                  _snowmanxxxxxxxxx / 2,
                  8,
                  256,
                  256
               );
               DrawableHelper.drawTexture(_snowman, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx + 8, 0.0F, (float)(46 + _snowmanxxxxxxxxxxxxxx * 20 + 12), _snowmanxxxxxxxxx / 2, 8, 256, 256);
               DrawableHelper.drawTexture(
                  _snowman,
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxx / 2,
                  _snowmanxxxxxxxxxxxx + 8,
                  (float)(200 - _snowmanxxxxxxxxx / 2),
                  (float)(46 + _snowmanxxxxxxxxxxxxxx * 20 + 12),
                  _snowmanxxxxxxxxx / 2,
                  8,
                  256,
                  256
               );
               RenderSystem.disableBlend();
               int _snowmanxxxxxxxxxxxxxxx = _snowman + 11 + 5;
               int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx ? 16777120 : 16777215;
               RealmsMainScreen.this.textRenderer.draw(_snowman, _snowmanxxxxxxx, (float)(_snowman + 2), (float)(_snowmanxxxxxxxxxxxxxxx + 1), 15553363);
               DrawableHelper.drawCenteredText(
                  _snowman, RealmsMainScreen.this.textRenderer, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxx / 2, _snowmanxxxxxxxxxxxxxxx + 1, _snowmanxxxxxxxxxxxxxxxx
               );
            } else {
               if (serverData.worldType == RealmsServer.WorldType.MINIGAME) {
                  int _snowmanxxxxxxxxx = 13413468;
                  int _snowmanxxxxxxxxxx = RealmsMainScreen.this.textRenderer.getWidth(RealmsMainScreen.field_26455);
                  RealmsMainScreen.this.textRenderer.draw(_snowman, RealmsMainScreen.field_26455, (float)(_snowman + 2), (float)(_snowman + 12), 13413468);
                  RealmsMainScreen.this.textRenderer.draw(_snowman, serverData.getMinigameName(), (float)(_snowman + 2 + _snowmanxxxxxxxxxx), (float)(_snowman + 12), 7105644);
               } else {
                  RealmsMainScreen.this.textRenderer.draw(_snowman, serverData.getDescription(), (float)(_snowman + 2), (float)(_snowman + 12), 7105644);
               }

               if (!RealmsMainScreen.this.isSelfOwnedServer(serverData)) {
                  RealmsMainScreen.this.textRenderer.draw(_snowman, serverData.owner, (float)(_snowman + 2), (float)(_snowman + 12 + 11), 5000268);
               }
            }

            RealmsMainScreen.this.textRenderer.draw(_snowman, serverData.getName(), (float)(_snowman + 2), (float)(_snowman + 1), 16777215);
            RealmsTextureManager.withBoundFace(serverData.ownerUUID, () -> {
               RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
               DrawableHelper.drawTexture(_snowman, _snowman - 36, _snowman, 32, 32, 8.0F, 8.0F, 8, 8, 64, 64);
               DrawableHelper.drawTexture(_snowman, _snowman - 36, _snowman, 32, 32, 40.0F, 8.0F, 8, 8, 64, 64);
            });
         }
      }
   }

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

      private void renderTrialItem(MatrixStack _snowman, int index, int x, int y, int mouseX, int mouseY) {
         int _snowmanx = y + 8;
         int _snowmanxx = 0;
         boolean _snowmanxxx = false;
         if (x <= mouseX && mouseX <= (int)RealmsMainScreen.this.realmSelectionList.getScrollAmount() && y <= mouseY && mouseY <= y + 32) {
            _snowmanxxx = true;
         }

         int _snowmanxxxx = 8388479;
         if (_snowmanxxx && !RealmsMainScreen.this.shouldShowPopup()) {
            _snowmanxxxx = 6077788;
         }

         for (Text _snowmanxxxxx : RealmsMainScreen.field_26449) {
            DrawableHelper.drawCenteredText(_snowman, RealmsMainScreen.this.textRenderer, _snowmanxxxxx, RealmsMainScreen.this.width / 2, _snowmanx + _snowmanxx, _snowmanxxxx);
            _snowmanxx += 10;
         }
      }
   }

   class ShowPopupButton extends ButtonWidget {
      public ShowPopupButton() {
         super(
            RealmsMainScreen.this.width - 37,
            6,
            20,
            20,
            new TranslatableText("mco.selectServer.info"),
            _snowmanx -> RealmsMainScreen.this.popupOpenedByUser = !RealmsMainScreen.this.popupOpenedByUser
         );
      }

      @Override
      public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
         RealmsMainScreen.this.renderMoreInfo(matrices, mouseX, mouseY, this.x, this.y, this.isHovered());
      }
   }
}
