/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  com.google.common.util.concurrent.RateLimiter
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.realms.gui.screen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Nullable;
import net.minecraft.class_5489;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.TickableElement;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
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
import net.minecraft.client.realms.gui.screen.RealmsClientOutdatedScreen;
import net.minecraft.client.realms.gui.screen.RealmsConfigureWorldScreen;
import net.minecraft.client.realms.gui.screen.RealmsCreateRealmScreen;
import net.minecraft.client.realms.gui.screen.RealmsGenericErrorScreen;
import net.minecraft.client.realms.gui.screen.RealmsLongConfirmationScreen;
import net.minecraft.client.realms.gui.screen.RealmsLongRunningMcoTaskScreen;
import net.minecraft.client.realms.gui.screen.RealmsParentalConsentScreen;
import net.minecraft.client.realms.gui.screen.RealmsPendingInvitesScreen;
import net.minecraft.client.realms.gui.screen.RealmsScreen;
import net.minecraft.client.realms.task.RealmsGetServerDetailsTask;
import net.minecraft.client.realms.util.RealmsPersistence;
import net.minecraft.client.realms.util.RealmsTextureManager;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.LiteralText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsMainScreen
extends RealmsScreen {
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
    private static final List<Text> field_26449 = ImmutableList.of((Object)new TranslatableText("mco.trial.message.line1"), (Object)new TranslatableText("mco.trial.message.line2"));
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
    private static int lastScrollYPosition;
    private static volatile boolean hasParentalConsent;
    private static volatile boolean checkedParentalConsent;
    private static volatile boolean checkedClientCompatability;
    private static Screen realmsGenericErrorScreen;
    private static boolean regionsPinged;
    private final RateLimiter rateLimiter;
    private boolean dontSetConnectedToRealms;
    private final Screen lastScreen;
    private volatile RealmSelectionList realmSelectionList;
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
    private HoverState hoverState;
    private ButtonWidget showPopupButton;
    private ButtonWidget pendingInvitesButton;
    private ButtonWidget newsButton;
    private ButtonWidget createTrialButton;
    private ButtonWidget buyARealmButton;
    private ButtonWidget closeButton;

    public RealmsMainScreen(Screen screen) {
        this.lastScreen = screen;
        this.rateLimiter = RateLimiter.create((double)0.01666666753590107);
    }

    private boolean shouldShowMessageInList() {
        if (!RealmsMainScreen.hasParentalConsent() || !this.hasFetchedServers) {
            return false;
        }
        if (this.trialsAvailable && !this.createdTrial) {
            return true;
        }
        for (RealmsServer realmsServer : this.realmsServers) {
            if (!realmsServer.ownerUUID.equals(this.client.getSession().getUuid())) continue;
            return false;
        }
        return true;
    }

    public boolean shouldShowPopup() {
        if (!RealmsMainScreen.hasParentalConsent() || !this.hasFetchedServers) {
            return false;
        }
        if (this.popupOpenedByUser) {
            return true;
        }
        if (this.trialsAvailable && !this.createdTrial && this.realmsServers.isEmpty()) {
            return true;
        }
        return this.realmsServers.isEmpty();
    }

    @Override
    public void init() {
        this.keyCombos = Lists.newArrayList((Object[])new KeyCombo[]{new KeyCombo(new char[]{'3', '2', '1', '4', '5', '6'}, () -> {
            overrideConfigure = !overrideConfigure;
        }), new KeyCombo(new char[]{'9', '8', '7', '1', '2', '3'}, () -> {
            if (RealmsClient.currentEnvironment == RealmsClient.Environment.STAGE) {
                this.switchToProd();
            } else {
                this.switchToStage();
            }
        }), new KeyCombo(new char[]{'9', '8', '7', '4', '5', '6'}, () -> {
            if (RealmsClient.currentEnvironment == RealmsClient.Environment.LOCAL) {
                this.switchToProd();
            } else {
                this.switchToLocal();
            }
        })});
        if (realmsGenericErrorScreen != null) {
            this.client.openScreen(realmsGenericErrorScreen);
            return;
        }
        this.connectLock = new ReentrantLock();
        if (checkedClientCompatability && !RealmsMainScreen.hasParentalConsent()) {
            this.checkParentalConsent();
        }
        this.checkClientCompatability();
        this.checkUnreadNews();
        if (!this.dontSetConnectedToRealms) {
            this.client.setConnectedToRealms(false);
        }
        this.client.keyboard.setRepeatEvents(true);
        if (RealmsMainScreen.hasParentalConsent()) {
            realmsDataFetcher.forceUpdate();
        }
        this.showingPopup = false;
        if (RealmsMainScreen.hasParentalConsent() && this.hasFetchedServers) {
            this.addButtons();
        }
        this.realmSelectionList = new RealmSelectionList();
        if (lastScrollYPosition != -1) {
            this.realmSelectionList.setScrollAmount(lastScrollYPosition);
        }
        this.addChild(this.realmSelectionList);
        this.focusOn(this.realmSelectionList);
        this.field_26466 = class_5489.method_30890(this.textRenderer, field_26456, 100);
    }

    private static boolean hasParentalConsent() {
        return checkedParentalConsent && hasParentalConsent;
    }

    public void addButtons() {
        this.leaveButton = this.addButton(new ButtonWidget(this.width / 2 - 202, this.height - 32, 90, 20, new TranslatableText("mco.selectServer.leave"), buttonWidget -> this.leaveClicked(this.findServer(this.selectedServerId))));
        this.configureButton = this.addButton(new ButtonWidget(this.width / 2 - 190, this.height - 32, 90, 20, new TranslatableText("mco.selectServer.configure"), buttonWidget -> this.configureClicked(this.findServer(this.selectedServerId))));
        this.playButton = this.addButton(new ButtonWidget(this.width / 2 - 93, this.height - 32, 90, 20, new TranslatableText("mco.selectServer.play"), buttonWidget -> {
            RealmsServer realmsServer = this.findServer(this.selectedServerId);
            if (realmsServer == null) {
                return;
            }
            this.play(realmsServer, this);
        }));
        this.backButton = this.addButton(new ButtonWidget(this.width / 2 + 4, this.height - 32, 90, 20, ScreenTexts.BACK, buttonWidget -> {
            if (!this.justClosedPopup) {
                this.client.openScreen(this.lastScreen);
            }
        }));
        this.renewButton = this.addButton(new ButtonWidget(this.width / 2 + 100, this.height - 32, 90, 20, new TranslatableText("mco.selectServer.expiredRenew"), buttonWidget -> this.onRenew()));
        this.pendingInvitesButton = this.addButton(new PendingInvitesButton());
        this.newsButton = this.addButton(new NewsButton());
        this.showPopupButton = this.addButton(new ShowPopupButton());
        this.closeButton = this.addButton(new CloseButton());
        this.createTrialButton = this.addButton(new ButtonWidget(this.width / 2 + 52, this.popupY0() + 137 - 20, 98, 20, new TranslatableText("mco.selectServer.trial"), buttonWidget -> {
            if (!this.trialsAvailable || this.createdTrial) {
                return;
            }
            Util.getOperatingSystem().open("https://aka.ms/startjavarealmstrial");
            this.client.openScreen(this.lastScreen);
        }));
        this.buyARealmButton = this.addButton(new ButtonWidget(this.width / 2 + 52, this.popupY0() + 160 - 20, 98, 20, new TranslatableText("mco.selectServer.buy"), buttonWidget -> Util.getOperatingSystem().open("https://aka.ms/BuyJavaRealms")));
        RealmsServer realmsServer = this.findServer(this.selectedServerId);
        this.updateButtonStates(realmsServer);
    }

    private void updateButtonStates(@Nullable RealmsServer server) {
        boolean bl;
        this.playButton.active = this.shouldPlayButtonBeActive(server) && !this.shouldShowPopup();
        this.renewButton.visible = this.shouldRenewButtonBeActive(server);
        this.configureButton.visible = this.shouldConfigureButtonBeVisible(server);
        this.leaveButton.visible = this.shouldLeaveButtonBeVisible(server);
        this.createTrialButton.visible = bl = this.shouldShowPopup() && this.trialsAvailable && !this.createdTrial;
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
        return (!this.shouldShowPopup() || this.popupOpenedByUser) && RealmsMainScreen.hasParentalConsent() && this.hasFetchedServers;
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
        Object object;
        super.tick();
        this.justClosedPopup = false;
        ++this.animTick;
        --this.clicks;
        if (this.clicks < 0) {
            this.clicks = 0;
        }
        if (!RealmsMainScreen.hasParentalConsent()) {
            return;
        }
        realmsDataFetcher.init();
        if (realmsDataFetcher.isFetchedSinceLastTry(RealmsDataFetcher.Task.SERVER_LIST)) {
            object = realmsDataFetcher.getServers();
            this.realmSelectionList.clear();
            boolean bl = _snowman = !this.hasFetchedServers;
            if (_snowman) {
                this.hasFetchedServers = true;
            }
            if (object != null) {
                boolean _snowman2 = false;
                Iterator<Object> iterator = object.iterator();
                while (iterator.hasNext()) {
                    RealmsServer realmsServer = (RealmsServer)iterator.next();
                    if (!this.method_25001(realmsServer)) continue;
                    _snowman2 = true;
                }
                this.realmsServers = object;
                if (this.shouldShowMessageInList()) {
                    this.realmSelectionList.method_30161(new RealmSelectionListTrialEntry());
                }
                for (RealmsServer realmsServer : this.realmsServers) {
                    this.realmSelectionList.addEntry(new RealmSelectionListEntry(realmsServer));
                }
                if (!regionsPinged && _snowman2) {
                    regionsPinged = true;
                    this.pingRegions();
                }
            }
            if (_snowman) {
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
            boolean bl = realmsDataFetcher.isTrialAvailable();
            if (bl != this.trialsAvailable && this.shouldShowPopup()) {
                this.trialsAvailable = bl;
                this.showingPopup = false;
            } else {
                this.trialsAvailable = bl;
            }
        }
        if (realmsDataFetcher.isFetchedSinceLastTry(RealmsDataFetcher.Task.LIVE_STATS)) {
            object = realmsDataFetcher.getLivestats();
            block2: for (RealmsServerPlayerList realmsServerPlayerList : ((RealmsServerPlayerLists)object).servers) {
                for (RealmsServer realmsServer : this.realmsServers) {
                    if (realmsServer.id != realmsServerPlayerList.serverId) continue;
                    realmsServer.updateServerPing(realmsServerPlayerList);
                    continue block2;
                }
            }
        }
        if (realmsDataFetcher.isFetchedSinceLastTry(RealmsDataFetcher.Task.UNREAD_NEWS)) {
            this.hasUnreadNews = realmsDataFetcher.hasUnreadNews();
            this.newsLink = realmsDataFetcher.newsLink();
        }
        realmsDataFetcher.markClean();
        if (this.shouldShowPopup()) {
            ++this.carouselTick;
        }
        if (this.showPopupButton != null) {
            this.showPopupButton.visible = this.shouldShowPopupButton();
        }
    }

    private void pingRegions() {
        new Thread(() -> {
            List<RegionPingResult> list = Ping.pingAllRegions();
            RealmsClient _snowman2 = RealmsClient.createRealmsClient();
            PingResult _snowman3 = new PingResult();
            _snowman3.pingResults = list;
            _snowman3.worldIds = this.getOwnedNonExpiredWorldIds();
            try {
                _snowman2.sendPingResults(_snowman3);
            }
            catch (Throwable _snowman4) {
                LOGGER.warn("Could not send ping result to Realms: ", _snowman4);
            }
        }).start();
    }

    private List<Long> getOwnedNonExpiredWorldIds() {
        ArrayList arrayList = Lists.newArrayList();
        for (RealmsServer realmsServer : this.realmsServers) {
            if (!this.method_25001(realmsServer)) continue;
            arrayList.add(realmsServer.id);
        }
        return arrayList;
    }

    @Override
    public void removed() {
        this.client.keyboard.setRepeatEvents(false);
        this.stopRealmsFetcher();
    }

    private void onRenew() {
        RealmsServer realmsServer = this.findServer(this.selectedServerId);
        if (realmsServer == null) {
            return;
        }
        String _snowman2 = "https://aka.ms/ExtendJavaRealms?subscriptionId=" + realmsServer.remoteSubscriptionId + "&profileId=" + this.client.getSession().getUuid() + "&ref=" + (realmsServer.expiredTrial ? "expiredTrial" : "expiredRealm");
        this.client.keyboard.setClipboard(_snowman2);
        Util.getOperatingSystem().open(_snowman2);
    }

    private void checkClientCompatability() {
        if (!checkedClientCompatability) {
            checkedClientCompatability = true;
            new Thread(this, "MCO Compatability Checker #1"){
                final /* synthetic */ RealmsMainScreen field_22565;
                {
                    this.field_22565 = realmsMainScreen;
                    super(string);
                }

                public void run() {
                    RealmsClient realmsClient = RealmsClient.createRealmsClient();
                    try {
                        RealmsClient.CompatibleVersionResponse compatibleVersionResponse = realmsClient.clientCompatible();
                        if (compatibleVersionResponse == RealmsClient.CompatibleVersionResponse.OUTDATED) {
                            RealmsMainScreen.method_24986(new RealmsClientOutdatedScreen(RealmsMainScreen.method_24983(this.field_22565), true));
                            RealmsMainScreen.method_20876(this.field_22565).execute(() -> RealmsMainScreen.method_25002(this.field_22565).openScreen(RealmsMainScreen.method_20911()));
                            return;
                        }
                        if (compatibleVersionResponse == RealmsClient.CompatibleVersionResponse.OTHER) {
                            RealmsMainScreen.method_24986(new RealmsClientOutdatedScreen(RealmsMainScreen.method_24983(this.field_22565), false));
                            RealmsMainScreen.method_20885(this.field_22565).execute(() -> RealmsMainScreen.method_25000(this.field_22565).openScreen(RealmsMainScreen.method_20911()));
                            return;
                        }
                        RealmsMainScreen.method_20910(this.field_22565);
                    }
                    catch (RealmsServiceException realmsServiceException) {
                        RealmsMainScreen.method_20881(false);
                        RealmsMainScreen.method_20908().error("Couldn't connect to realms", (Throwable)realmsServiceException);
                        if (realmsServiceException.httpResultCode == 401) {
                            RealmsMainScreen.method_24986(new RealmsGenericErrorScreen(new TranslatableText("mco.error.invalid.session.title"), new TranslatableText("mco.error.invalid.session.message"), RealmsMainScreen.method_24983(this.field_22565)));
                            RealmsMainScreen.method_24992(this.field_22565).execute(() -> RealmsMainScreen.method_24998(this.field_22565).openScreen(RealmsMainScreen.method_20911()));
                        }
                        RealmsMainScreen.method_24994(this.field_22565).execute(() -> RealmsMainScreen.method_24996(this.field_22565).openScreen(new RealmsGenericErrorScreen(realmsServiceException, RealmsMainScreen.method_24983(this.field_22565))));
                    }
                }
            }.start();
        }
    }

    private void checkUnreadNews() {
    }

    private void checkParentalConsent() {
        new Thread(this, "MCO Compatability Checker #1"){
            final /* synthetic */ RealmsMainScreen field_22566;
            {
                this.field_22566 = realmsMainScreen;
                super(string);
            }

            public void run() {
                RealmsClient realmsClient = RealmsClient.createRealmsClient();
                try {
                    Boolean bl = realmsClient.mcoEnabled();
                    if (bl.booleanValue()) {
                        RealmsMainScreen.method_20908().info("Realms is available for this user");
                        RealmsMainScreen.method_20889(true);
                    } else {
                        RealmsMainScreen.method_20908().info("Realms is not available for this user");
                        RealmsMainScreen.method_20889(false);
                        RealmsMainScreen.method_25003(this.field_22566).execute(() -> RealmsMainScreen.method_25008(this.field_22566).openScreen(new RealmsParentalConsentScreen(RealmsMainScreen.method_24983(this.field_22566))));
                    }
                    RealmsMainScreen.method_20896(true);
                }
                catch (RealmsServiceException realmsServiceException) {
                    RealmsMainScreen.method_20908().error("Couldn't connect to realms", (Throwable)realmsServiceException);
                    RealmsMainScreen.method_25005(this.field_22566).execute(() -> RealmsMainScreen.method_25006(this.field_22566).openScreen(new RealmsGenericErrorScreen(realmsServiceException, RealmsMainScreen.method_24983(this.field_22566))));
                }
            }
        }.start();
    }

    private void switchToStage() {
        if (RealmsClient.currentEnvironment != RealmsClient.Environment.STAGE) {
            new Thread(this, "MCO Stage Availability Checker #1"){
                final /* synthetic */ RealmsMainScreen field_19507;
                {
                    this.field_19507 = realmsMainScreen;
                    super(string);
                }

                public void run() {
                    RealmsClient realmsClient = RealmsClient.createRealmsClient();
                    try {
                        Boolean bl = realmsClient.stageAvailable();
                        if (bl.booleanValue()) {
                            RealmsClient.switchToStage();
                            RealmsMainScreen.method_20908().info("Switched to stage");
                            RealmsMainScreen.method_20914().forceUpdate();
                        }
                    }
                    catch (RealmsServiceException realmsServiceException) {
                        RealmsMainScreen.method_20908().error("Couldn't connect to Realms: " + realmsServiceException);
                    }
                }
            }.start();
        }
    }

    private void switchToLocal() {
        if (RealmsClient.currentEnvironment != RealmsClient.Environment.LOCAL) {
            new Thread(this, "MCO Local Availability Checker #1"){
                final /* synthetic */ RealmsMainScreen field_19508;
                {
                    this.field_19508 = realmsMainScreen;
                    super(string);
                }

                public void run() {
                    RealmsClient realmsClient = RealmsClient.createRealmsClient();
                    try {
                        Boolean bl = realmsClient.stageAvailable();
                        if (bl.booleanValue()) {
                            RealmsClient.switchToLocal();
                            RealmsMainScreen.method_20908().info("Switched to local");
                            RealmsMainScreen.method_20914().forceUpdate();
                        }
                    }
                    catch (RealmsServiceException realmsServiceException) {
                        RealmsMainScreen.method_20908().error("Couldn't connect to Realms: " + realmsServiceException);
                    }
                }
            }.start();
        }
    }

    private void switchToProd() {
        RealmsClient.switchToProd();
        realmsDataFetcher.forceUpdate();
    }

    private void stopRealmsFetcher() {
        realmsDataFetcher.stop();
    }

    private void configureClicked(RealmsServer realmsServer) {
        if (this.client.getSession().getUuid().equals(realmsServer.ownerUUID) || overrideConfigure) {
            this.saveListScrollPosition();
            this.client.openScreen(new RealmsConfigureWorldScreen(this, realmsServer.id));
        }
    }

    private void leaveClicked(@Nullable RealmsServer selectedServer) {
        if (selectedServer != null && !this.client.getSession().getUuid().equals(selectedServer.ownerUUID)) {
            this.saveListScrollPosition();
            TranslatableText translatableText = new TranslatableText("mco.configure.world.leave.question.line1");
            _snowman = new TranslatableText("mco.configure.world.leave.question.line2");
            this.client.openScreen(new RealmsLongConfirmationScreen(this::method_24991, RealmsLongConfirmationScreen.Type.Info, translatableText, _snowman, true));
        }
    }

    private void saveListScrollPosition() {
        lastScrollYPosition = (int)this.realmSelectionList.getScrollAmount();
    }

    @Nullable
    private RealmsServer findServer(long id) {
        for (RealmsServer realmsServer : this.realmsServers) {
            if (realmsServer.id != id) continue;
            return realmsServer;
        }
        return null;
    }

    private void method_24991(boolean bl) {
        if (bl) {
            new Thread(this, "Realms-leave-server"){
                final /* synthetic */ RealmsMainScreen field_19509;
                {
                    this.field_19509 = realmsMainScreen;
                    super(string);
                }

                public void run() {
                    try {
                        RealmsServer realmsServer = RealmsMainScreen.method_20861(this.field_19509, RealmsMainScreen.method_20854(this.field_19509));
                        if (realmsServer != null) {
                            RealmsClient realmsClient = RealmsClient.createRealmsClient();
                            realmsClient.uninviteMyselfFrom(realmsServer.id);
                            RealmsMainScreen.method_31175(this.field_19509).execute(() -> RealmsMainScreen.method_31173(this.field_19509, realmsServer));
                        }
                    }
                    catch (RealmsServiceException realmsServiceException) {
                        RealmsMainScreen.method_20908().error("Couldn't configure world");
                        RealmsMainScreen.method_25010(this.field_19509).execute(() -> RealmsMainScreen.method_25011(this.field_19509).openScreen(new RealmsGenericErrorScreen(realmsServiceException, (Screen)this.field_19509)));
                    }
                }
            }.start();
        }
        this.client.openScreen(this);
    }

    private void method_31174(RealmsServer realmsServer) {
        realmsDataFetcher.removeItem(realmsServer);
        this.realmsServers.remove(realmsServer);
        this.realmSelectionList.children().removeIf(entry -> entry instanceof RealmSelectionListEntry && ((RealmSelectionListEntry)((RealmSelectionListEntry)entry)).mServerData.id == this.selectedServerId);
        this.realmSelectionList.setSelected((Entry)null);
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
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
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
        this.keyCombos.forEach(keyCombo -> keyCombo.keyPressed(chr));
        return true;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.hoverState = HoverState.NONE;
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
                RealmsServer realmsServer = this.findServer(this.selectedServerId);
                this.playButton.active = this.shouldPlayButtonBeActive(realmsServer);
            }
            this.showingPopup = false;
        }
        super.render(matrices, mouseX, mouseY, delta);
        if (this.toolTip != null) {
            this.renderMousehoverTooltip(matrices, this.toolTip, mouseX, mouseY);
        }
        if (this.trialsAvailable && !this.createdTrial && this.shouldShowPopup()) {
            this.client.getTextureManager().bindTexture(TRIAL_ICON);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            int n = 8;
            _snowman = 8;
            _snowman = 0;
            if ((Util.getMeasuringTimeMs() / 800L & 1L) == 1L) {
                _snowman = 8;
            }
            DrawableHelper.drawTexture(matrices, this.createTrialButton.x + this.createTrialButton.getWidth() - 8 - 4, this.createTrialButton.y + this.createTrialButton.getHeight() / 2 - 4, 0.0f, _snowman, 8, 8, 8, 16);
        }
    }

    private void drawRealmsLogo(MatrixStack matrices, int x, int y) {
        this.client.getTextureManager().bindTexture(REALMS);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.pushMatrix();
        RenderSystem.scalef(0.5f, 0.5f, 0.5f);
        DrawableHelper.drawTexture(matrices, x * 2, y * 2 - 5, 0.0f, 0.0f, 200, 50, 200, 50);
        RenderSystem.popMatrix();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.isOutsidePopup(mouseX, mouseY) && this.popupOpenedByUser) {
            this.popupOpenedByUser = false;
            this.justClosedPopup = true;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private boolean isOutsidePopup(double xm, double ym) {
        int n = this.popupX0();
        _snowman = this.popupY0();
        return xm < (double)(n - 5) || xm > (double)(n + 315) || ym < (double)(_snowman - 5) || ym > (double)(_snowman + 171);
    }

    private void drawPopup(MatrixStack matrices, int mouseX, int mouseY) {
        int n = this.popupX0();
        _snowman = this.popupY0();
        if (!this.showingPopup) {
            this.carouselIndex = 0;
            this.carouselTick = 0;
            this.hasSwitchedCarouselImage = true;
            this.updateButtonStates(null);
            if (this.children.contains(this.realmSelectionList) && !this.children.remove(_snowman = this.realmSelectionList)) {
                LOGGER.error("Unable to remove widget: " + _snowman);
            }
            Realms.narrateNow(field_26456.getString());
        }
        if (this.hasFetchedServers) {
            this.showingPopup = true;
        }
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 0.7f);
        RenderSystem.enableBlend();
        this.client.getTextureManager().bindTexture(DARKEN);
        boolean _snowman2 = false;
        _snowman = 32;
        DrawableHelper.drawTexture(matrices, 0, 32, 0.0f, 0.0f, this.width, this.height - 40 - 32, 310, 166);
        RenderSystem.disableBlend();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(POPUP);
        DrawableHelper.drawTexture(matrices, n, _snowman, 0.0f, 0.0f, 310, 166, 310, 166);
        if (!IMAGES.isEmpty()) {
            this.client.getTextureManager().bindTexture(IMAGES.get(this.carouselIndex));
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            DrawableHelper.drawTexture(matrices, n + 7, _snowman + 7, 0.0f, 0.0f, 195, 152, 195, 152);
            if (this.carouselTick % 95 < 5) {
                if (!this.hasSwitchedCarouselImage) {
                    this.carouselIndex = (this.carouselIndex + 1) % IMAGES.size();
                    this.hasSwitchedCarouselImage = true;
                }
            } else {
                this.hasSwitchedCarouselImage = false;
            }
        }
        this.field_26466.method_30896(matrices, this.width / 2 + 52, _snowman + 7, 10, 0x4C4C4C);
    }

    private int popupX0() {
        return (this.width - 310) / 2;
    }

    private int popupY0() {
        return this.height / 2 - 80;
    }

    private void drawInvitationPendingIcon(MatrixStack matrixStack, int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
        int n5 = this.numberOfPendingInvites;
        boolean _snowman2 = this.inPendingInvitationArea(n, n2);
        boolean bl3 = _snowman = bl2 && bl;
        if (_snowman) {
            float f = 0.25f + (1.0f + MathHelper.sin((float)this.animTick * 0.5f)) * 0.25f;
            int _snowman3 = 0xFF000000 | (int)(f * 64.0f) << 16 | (int)(f * 64.0f) << 8 | (int)(f * 64.0f) << 0;
            this.fillGradient(matrixStack, n3 - 2, n4 - 2, n3 + 18, n4 + 18, _snowman3, _snowman3);
            _snowman3 = 0xFF000000 | (int)(f * 255.0f) << 16 | (int)(f * 255.0f) << 8 | (int)(f * 255.0f) << 0;
            this.fillGradient(matrixStack, n3 - 2, n4 - 2, n3 + 18, n4 - 1, _snowman3, _snowman3);
            this.fillGradient(matrixStack, n3 - 2, n4 - 2, n3 - 1, n4 + 18, _snowman3, _snowman3);
            this.fillGradient(matrixStack, n3 + 17, n4 - 2, n3 + 18, n4 + 18, _snowman3, _snowman3);
            this.fillGradient(matrixStack, n3 - 2, n4 + 17, n3 + 18, n4 + 18, _snowman3, _snowman3);
        }
        this.client.getTextureManager().bindTexture(INVITE_ICON);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        boolean bl4 = bl2 && bl;
        float _snowman4 = bl4 ? 16.0f : 0.0f;
        DrawableHelper.drawTexture(matrixStack, n3, n4 - 6, _snowman4, 0.0f, 15, 25, 31, 25);
        boolean bl5 = _snowman = bl2 && n5 != 0;
        if (_snowman) {
            int n6 = (Math.min(n5, 6) - 1) * 8;
            _snowman = (int)(Math.max(0.0f, Math.max(MathHelper.sin((float)(10 + this.animTick) * 0.57f), MathHelper.cos((float)this.animTick * 0.35f))) * -6.0f);
            this.client.getTextureManager().bindTexture(INVITATION_ICON);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            float _snowman5 = _snowman2 ? 8.0f : 0.0f;
            DrawableHelper.drawTexture(matrixStack, n3 + 4, n4 + 4 + _snowman, n6, _snowman5, 8, 8, 48, 16);
        }
        n6 = n + 12;
        _snowman = n2;
        boolean bl6 = _snowman = bl2 && _snowman2;
        if (_snowman) {
            Text _snowman6 = n5 == 0 ? field_26447 : field_26448;
            _snowman = this.textRenderer.getWidth(_snowman6);
            this.fillGradient(matrixStack, n6 - 3, _snowman - 3, n6 + _snowman + 3, _snowman + 8 + 3, -1073741824, -1073741824);
            this.textRenderer.drawWithShadow(matrixStack, _snowman6, (float)n6, (float)_snowman, -1);
        }
    }

    private boolean inPendingInvitationArea(double xm, double ym) {
        int n = this.width / 2 + 50;
        _snowman = this.width / 2 + 66;
        _snowman = 11;
        _snowman = 23;
        if (this.numberOfPendingInvites != 0) {
            n -= 3;
            _snowman += 3;
            _snowman -= 5;
            _snowman += 5;
        }
        return (double)n <= xm && xm <= (double)_snowman && (double)_snowman <= ym && ym <= (double)_snowman;
    }

    public void play(RealmsServer realmsServer, Screen screen) {
        if (realmsServer != null) {
            try {
                if (!this.connectLock.tryLock(1L, TimeUnit.SECONDS)) {
                    return;
                }
                if (this.connectLock.getHoldCount() > 1) {
                    return;
                }
            }
            catch (InterruptedException interruptedException) {
                return;
            }
            this.dontSetConnectedToRealms = true;
            this.client.openScreen(new RealmsLongRunningMcoTaskScreen(screen, new RealmsGetServerDetailsTask(this, screen, realmsServer, this.connectLock)));
        }
    }

    private boolean isSelfOwnedServer(RealmsServer serverData) {
        return serverData.ownerUUID != null && serverData.ownerUUID.equals(this.client.getSession().getUuid());
    }

    private boolean method_25001(RealmsServer realmsServer) {
        return this.isSelfOwnedServer(realmsServer) && !realmsServer.expired;
    }

    private void drawExpired(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        this.client.getTextureManager().bindTexture(EXPIRED_ICON);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        DrawableHelper.drawTexture(matrixStack, n, n2, 0.0f, 0.0f, 10, 28, 10, 28);
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 27 && n4 < this.height - 40 && n4 > 32 && !this.shouldShowPopup()) {
            this.method_27452(field_26457);
        }
    }

    private void method_24987(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5) {
        this.client.getTextureManager().bindTexture(EXPIRES_SOON_ICON);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (this.animTick % 20 < 10) {
            DrawableHelper.drawTexture(matrixStack, n, n2, 0.0f, 0.0f, 10, 28, 20, 28);
        } else {
            DrawableHelper.drawTexture(matrixStack, n, n2, 10.0f, 0.0f, 10, 28, 20, 28);
        }
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 27 && n4 < this.height - 40 && n4 > 32 && !this.shouldShowPopup()) {
            if (n5 <= 0) {
                this.method_27452(field_26458);
            } else if (n5 == 1) {
                this.method_27452(field_26459);
            } else {
                this.method_27452(new TranslatableText("mco.selectServer.expires.days", n5));
            }
        }
    }

    private void drawOpen(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        this.client.getTextureManager().bindTexture(ON_ICON);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        DrawableHelper.drawTexture(matrixStack, n, n2, 0.0f, 0.0f, 10, 28, 10, 28);
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 27 && n4 < this.height - 40 && n4 > 32 && !this.shouldShowPopup()) {
            this.method_27452(field_26460);
        }
    }

    private void drawClose(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        this.client.getTextureManager().bindTexture(OFF_ICON);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        DrawableHelper.drawTexture(matrixStack, n, n2, 0.0f, 0.0f, 10, 28, 10, 28);
        if (n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 27 && n4 < this.height - 40 && n4 > 32 && !this.shouldShowPopup()) {
            this.method_27452(field_26461);
        }
    }

    private void drawLeave(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        boolean bl = false;
        if (n3 >= n && n3 <= n + 28 && n4 >= n2 && n4 <= n2 + 28 && n4 < this.height - 40 && n4 > 32 && !this.shouldShowPopup()) {
            bl = true;
        }
        this.client.getTextureManager().bindTexture(LEAVE_ICON);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        float _snowman2 = bl ? 28.0f : 0.0f;
        DrawableHelper.drawTexture(matrixStack, n, n2, _snowman2, 0.0f, 28, 28, 56, 28);
        if (bl) {
            this.method_27452(field_26462);
            this.hoverState = HoverState.LEAVE;
        }
    }

    private void drawConfigure(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        boolean bl = false;
        if (n3 >= n && n3 <= n + 28 && n4 >= n2 && n4 <= n2 + 28 && n4 < this.height - 40 && n4 > 32 && !this.shouldShowPopup()) {
            bl = true;
        }
        this.client.getTextureManager().bindTexture(CONFIGURE_ICON);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        float _snowman2 = bl ? 28.0f : 0.0f;
        DrawableHelper.drawTexture(matrixStack, n, n2, _snowman2, 0.0f, 28, 28, 56, 28);
        if (bl) {
            this.method_27452(field_26463);
            this.hoverState = HoverState.CONFIGURE;
        }
    }

    protected void renderMousehoverTooltip(MatrixStack matrixStack, List<Text> list, int n3, int n2) {
        int n3;
        if (list.isEmpty()) {
            return;
        }
        _snowman = 0;
        _snowman = 0;
        for (Text text : list) {
            int n4 = this.textRenderer.getWidth(text);
            if (n4 <= _snowman) continue;
            _snowman = n4;
        }
        _snowman = n3 - _snowman - 5;
        _snowman = n2;
        if (_snowman < 0) {
            _snowman = n3 + 12;
        }
        for (Text text : list) {
            int n5 = _snowman - (_snowman == 0 ? 3 : 0) + _snowman;
            this.fillGradient(matrixStack, _snowman - 3, n5, _snowman + _snowman + 3, _snowman + 8 + 3 + _snowman, -1073741824, -1073741824);
            this.textRenderer.drawWithShadow(matrixStack, text, (float)_snowman, (float)(_snowman + _snowman), 0xFFFFFF);
            _snowman += 10;
        }
    }

    private void renderMoreInfo(MatrixStack matrixStack, int n, int n2, int n3, int n4, boolean bl) {
        _snowman = false;
        if (n >= n3 && n <= n3 + 20 && n2 >= n4 && n2 <= n4 + 20) {
            _snowman = true;
        }
        this.client.getTextureManager().bindTexture(QUESTIONMARK);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f = bl ? 20.0f : 0.0f;
        DrawableHelper.drawTexture(matrixStack, n3, n4, f, 0.0f, 20, 20, 40, 20);
        if (_snowman) {
            this.method_27452(field_26464);
        }
    }

    private void renderNews(MatrixStack matrixStack, int n, int n2, boolean bl, int n3, int n4, boolean bl2, boolean bl3) {
        _snowman = false;
        if (n >= n3 && n <= n3 + 20 && n2 >= n4 && n2 <= n4 + 20) {
            _snowman = true;
        }
        this.client.getTextureManager().bindTexture(NEWS_ICON);
        if (bl3) {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            RenderSystem.color4f(0.5f, 0.5f, 0.5f, 1.0f);
        }
        _snowman = bl3 && bl2;
        float f = _snowman ? 20.0f : 0.0f;
        DrawableHelper.drawTexture(matrixStack, n3, n4, f, 0.0f, 20, 20, 40, 20);
        if (_snowman && bl3) {
            this.method_27452(field_26465);
        }
        if (bl && bl3) {
            int n5 = _snowman ? 0 : (int)(Math.max(0.0f, Math.max(MathHelper.sin((float)(10 + this.animTick) * 0.57f), MathHelper.cos((float)this.animTick * 0.35f))) * -6.0f);
            this.client.getTextureManager().bindTexture(INVITATION_ICON);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            DrawableHelper.drawTexture(matrixStack, n3 + 10, n4 + 2 + n5, 40.0f, 0.0f, 8, 8, 48, 16);
        }
    }

    private void renderLocal(MatrixStack matrixStack) {
        String string = "LOCAL!";
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.pushMatrix();
        RenderSystem.translatef(this.width / 2 - 25, 20.0f, 0.0f);
        RenderSystem.rotatef(-20.0f, 0.0f, 0.0f, 1.0f);
        RenderSystem.scalef(1.5f, 1.5f, 1.5f);
        this.textRenderer.draw(matrixStack, "LOCAL!", 0.0f, 0.0f, 0x7FFF7F);
        RenderSystem.popMatrix();
    }

    private void renderStage(MatrixStack matrixStack) {
        String string = "STAGE!";
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.pushMatrix();
        RenderSystem.translatef(this.width / 2 - 25, 20.0f, 0.0f);
        RenderSystem.rotatef(-20.0f, 0.0f, 0.0f, 1.0f);
        RenderSystem.scalef(1.5f, 1.5f, 1.5f);
        this.textRenderer.draw(matrixStack, "STAGE!", 0.0f, 0.0f, -256);
        RenderSystem.popMatrix();
    }

    public RealmsMainScreen newScreen() {
        RealmsMainScreen realmsMainScreen = new RealmsMainScreen(this.lastScreen);
        realmsMainScreen.init(this.client, this.width, this.height);
        return realmsMainScreen;
    }

    public static void method_23765(ResourceManager manager) {
        Collection<Identifier> collection = manager.findResources("textures/gui/images", string -> string.endsWith(".png"));
        IMAGES = (List)collection.stream().filter(identifier -> identifier.getNamespace().equals("realms")).collect(ImmutableList.toImmutableList());
    }

    private void method_27452(Text ... textArray) {
        this.toolTip = Arrays.asList(textArray);
    }

    private void method_24985(ButtonWidget buttonWidget) {
        this.client.openScreen(new RealmsPendingInvitesScreen(this.lastScreen));
    }

    static /* synthetic */ Screen method_24986(Screen screen) {
        realmsGenericErrorScreen = screen;
        return realmsGenericErrorScreen;
    }

    static /* synthetic */ Screen method_24983(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.lastScreen;
    }

    static /* synthetic */ MinecraftClient method_20876(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.client;
    }

    static /* synthetic */ MinecraftClient method_20885(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.client;
    }

    static /* synthetic */ void method_20910(RealmsMainScreen realmsMainScreen) {
        realmsMainScreen.checkParentalConsent();
    }

    static /* synthetic */ boolean method_20881(boolean bl) {
        checkedClientCompatability = bl;
        return checkedClientCompatability;
    }

    static /* synthetic */ Logger method_20908() {
        return LOGGER;
    }

    static /* synthetic */ MinecraftClient method_24992(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.client;
    }

    static /* synthetic */ MinecraftClient method_24994(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.client;
    }

    static /* synthetic */ MinecraftClient method_24996(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.client;
    }

    static /* synthetic */ Screen method_20911() {
        return realmsGenericErrorScreen;
    }

    static /* synthetic */ MinecraftClient method_24998(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.client;
    }

    static /* synthetic */ MinecraftClient method_25000(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.client;
    }

    static /* synthetic */ MinecraftClient method_25002(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.client;
    }

    static /* synthetic */ boolean method_20889(boolean bl) {
        hasParentalConsent = bl;
        return hasParentalConsent;
    }

    static /* synthetic */ MinecraftClient method_25003(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.client;
    }

    static /* synthetic */ boolean method_20896(boolean bl) {
        checkedParentalConsent = bl;
        return checkedParentalConsent;
    }

    static /* synthetic */ MinecraftClient method_25005(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.client;
    }

    static /* synthetic */ MinecraftClient method_25006(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.client;
    }

    static /* synthetic */ MinecraftClient method_25008(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.client;
    }

    static /* synthetic */ RealmsDataFetcher method_20914() {
        return realmsDataFetcher;
    }

    static /* synthetic */ MinecraftClient method_31175(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.client;
    }

    static /* synthetic */ MinecraftClient method_25010(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.client;
    }

    static /* synthetic */ MinecraftClient method_25011(RealmsMainScreen realmsMainScreen) {
        return realmsMainScreen.client;
    }

    static /* synthetic */ void method_31173(RealmsMainScreen realmsMainScreen, RealmsServer realmsServer) {
        realmsMainScreen.method_31174(realmsServer);
    }

    static {
        lastScrollYPosition = -1;
    }

    class CloseButton
    extends ButtonWidget {
        public CloseButton() {
            super(RealmsMainScreen.this.popupX0() + 4, RealmsMainScreen.this.popupY0() + 4, 12, 12, new TranslatableText("mco.selectServer.close"), buttonWidget -> RealmsMainScreen.this.onClosePopup());
        }

        @Override
        public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            RealmsMainScreen.this.client.getTextureManager().bindTexture(CROSS_ICON);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            float f = this.isHovered() ? 12.0f : 0.0f;
            CloseButton.drawTexture(matrices, this.x, this.y, 0.0f, f, 12, 12, 12, 24);
            if (this.isMouseOver(mouseX, mouseY)) {
                RealmsMainScreen.this.method_27452(new Text[]{this.getMessage()});
            }
        }
    }

    class ShowPopupButton
    extends ButtonWidget {
        public ShowPopupButton() {
            super(RealmsMainScreen.this.width - 37, 6, 20, 20, new TranslatableText("mco.selectServer.info"), buttonWidget -> RealmsMainScreen.this.popupOpenedByUser = !RealmsMainScreen.this.popupOpenedByUser);
        }

        @Override
        public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            RealmsMainScreen.this.renderMoreInfo(matrices, mouseX, mouseY, this.x, this.y, this.isHovered());
        }
    }

    class NewsButton
    extends ButtonWidget {
        public NewsButton() {
            super(RealmsMainScreen.this.width - 62, 6, 20, 20, LiteralText.EMPTY, buttonWidget -> {
                if (RealmsMainScreen.this.newsLink == null) {
                    return;
                }
                Util.getOperatingSystem().open(RealmsMainScreen.this.newsLink);
                if (RealmsMainScreen.this.hasUnreadNews) {
                    RealmsPersistence.RealmsPersistenceData realmsPersistenceData = RealmsPersistence.readFile();
                    realmsPersistenceData.hasUnreadNews = false;
                    RealmsMainScreen.this.hasUnreadNews = false;
                    RealmsPersistence.writeFile(realmsPersistenceData);
                }
            });
            this.setMessage(new TranslatableText("mco.news"));
        }

        @Override
        public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            RealmsMainScreen.this.renderNews(matrices, mouseX, mouseY, RealmsMainScreen.this.hasUnreadNews, this.x, this.y, this.isHovered(), this.active);
        }
    }

    class PendingInvitesButton
    extends ButtonWidget
    implements TickableElement {
        public PendingInvitesButton() {
            super(RealmsMainScreen.this.width / 2 + 47, 6, 22, 22, LiteralText.EMPTY, buttonWidget -> RealmsMainScreen.this.method_24985(buttonWidget));
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

    class RealmSelectionListEntry
    extends Entry {
        private final RealmsServer mServerData;

        public RealmSelectionListEntry(RealmsServer serverData) {
            this.mServerData = serverData;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
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

        private void method_20945(RealmsServer realmsServer, MatrixStack matrixStack, int n, int n2, int n3, int n4) {
            this.renderMcoServerItem(realmsServer, matrixStack, n + 36, n2, n3, n4);
        }

        private void renderMcoServerItem(RealmsServer serverData, MatrixStack matrixStack, int n, int n2, int n3, int n4) {
            Object object;
            if (serverData.state == RealmsServer.State.UNINITIALIZED) {
                RealmsMainScreen.this.client.getTextureManager().bindTexture(WORLD_ICON);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.enableAlphaTest();
                DrawableHelper.drawTexture(matrixStack, n + 10, n2 + 6, 0.0f, 0.0f, 40, 20, 40, 20);
                float f = 0.5f + (1.0f + MathHelper.sin((float)RealmsMainScreen.this.animTick * 0.25f)) * 0.25f;
                int _snowman2 = 0xFF000000 | (int)(127.0f * f) << 16 | (int)(255.0f * f) << 8 | (int)(127.0f * f);
                DrawableHelper.drawCenteredText(matrixStack, RealmsMainScreen.this.textRenderer, field_26450, n + 10 + 40 + 75, n2 + 12, _snowman2);
                return;
            }
            int n5 = 225;
            _snowman = 2;
            if (serverData.expired) {
                RealmsMainScreen.this.drawExpired(matrixStack, n + 225 - 14, n2 + 2, n3, n4);
            } else if (serverData.state == RealmsServer.State.CLOSED) {
                RealmsMainScreen.this.drawClose(matrixStack, n + 225 - 14, n2 + 2, n3, n4);
            } else if (RealmsMainScreen.this.isSelfOwnedServer(serverData) && serverData.daysLeft < 7) {
                RealmsMainScreen.this.method_24987(matrixStack, n + 225 - 14, n2 + 2, n3, n4, serverData.daysLeft);
            } else if (serverData.state == RealmsServer.State.OPEN) {
                RealmsMainScreen.this.drawOpen(matrixStack, n + 225 - 14, n2 + 2, n3, n4);
            }
            if (!RealmsMainScreen.this.isSelfOwnedServer(serverData) && !overrideConfigure) {
                RealmsMainScreen.this.drawLeave(matrixStack, n + 225, n2 + 2, n3, n4);
            } else {
                RealmsMainScreen.this.drawConfigure(matrixStack, n + 225, n2 + 2, n3, n4);
            }
            if (!"0".equals(serverData.serverPing.nrOfPlayers)) {
                object = (Object)((Object)Formatting.GRAY) + "" + serverData.serverPing.nrOfPlayers;
                RealmsMainScreen.this.textRenderer.draw(matrixStack, (String)object, (float)(n + 207 - RealmsMainScreen.this.textRenderer.getWidth((String)object)), (float)(n2 + 3), 0x808080);
                if (n3 >= n + 207 - RealmsMainScreen.this.textRenderer.getWidth((String)object) && n3 <= n + 207 && n4 >= n2 + 1 && n4 <= n2 + 10 && n4 < RealmsMainScreen.this.height - 40 && n4 > 32 && !RealmsMainScreen.this.shouldShowPopup()) {
                    RealmsMainScreen.this.method_27452(new Text[]{new LiteralText(serverData.serverPing.playerList)});
                }
            }
            if (RealmsMainScreen.this.isSelfOwnedServer(serverData) && serverData.expired) {
                Text _snowman3;
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.enableBlend();
                RealmsMainScreen.this.client.getTextureManager().bindTexture(WIDGETS);
                RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
                if (serverData.expiredTrial) {
                    object = field_26453;
                    _snowman3 = field_26454;
                } else {
                    object = field_26451;
                    _snowman3 = field_26452;
                }
                int n6 = RealmsMainScreen.this.textRenderer.getWidth(_snowman3) + 17;
                _snowman = 16;
                _snowman = n + RealmsMainScreen.this.textRenderer.getWidth((StringVisitable)object) + 8;
                _snowman = n2 + 13;
                boolean _snowman4 = false;
                if (n3 >= _snowman && n3 < _snowman + n6 && n4 > _snowman && n4 <= _snowman + 16 & n4 < RealmsMainScreen.this.height - 40 && n4 > 32 && !RealmsMainScreen.this.shouldShowPopup()) {
                    _snowman4 = true;
                    RealmsMainScreen.this.hoverState = HoverState.EXPIRED;
                }
                _snowman = _snowman4 ? 2 : 1;
                DrawableHelper.drawTexture(matrixStack, _snowman, _snowman, 0.0f, 46 + _snowman * 20, n6 / 2, 8, 256, 256);
                DrawableHelper.drawTexture(matrixStack, _snowman + n6 / 2, _snowman, 200 - n6 / 2, 46 + _snowman * 20, n6 / 2, 8, 256, 256);
                DrawableHelper.drawTexture(matrixStack, _snowman, _snowman + 8, 0.0f, 46 + _snowman * 20 + 12, n6 / 2, 8, 256, 256);
                DrawableHelper.drawTexture(matrixStack, _snowman + n6 / 2, _snowman + 8, 200 - n6 / 2, 46 + _snowman * 20 + 12, n6 / 2, 8, 256, 256);
                RenderSystem.disableBlend();
                _snowman = n2 + 11 + 5;
                _snowman = _snowman4 ? 0xFFFFA0 : 0xFFFFFF;
                RealmsMainScreen.this.textRenderer.draw(matrixStack, (Text)object, (float)(n + 2), (float)(_snowman + 1), 15553363);
                DrawableHelper.drawCenteredText(matrixStack, RealmsMainScreen.this.textRenderer, _snowman3, _snowman + n6 / 2, _snowman + 1, _snowman);
            } else {
                if (serverData.worldType == RealmsServer.WorldType.MINIGAME) {
                    _snowman = 0xCCAC5C;
                    _snowman = RealmsMainScreen.this.textRenderer.getWidth(field_26455);
                    RealmsMainScreen.this.textRenderer.draw(matrixStack, field_26455, (float)(n + 2), (float)(n2 + 12), 0xCCAC5C);
                    RealmsMainScreen.this.textRenderer.draw(matrixStack, serverData.getMinigameName(), (float)(n + 2 + _snowman), (float)(n2 + 12), 0x6C6C6C);
                } else {
                    RealmsMainScreen.this.textRenderer.draw(matrixStack, serverData.getDescription(), (float)(n + 2), (float)(n2 + 12), 0x6C6C6C);
                }
                if (!RealmsMainScreen.this.isSelfOwnedServer(serverData)) {
                    RealmsMainScreen.this.textRenderer.draw(matrixStack, serverData.owner, (float)(n + 2), (float)(n2 + 12 + 11), 0x4C4C4C);
                }
            }
            RealmsMainScreen.this.textRenderer.draw(matrixStack, serverData.getName(), (float)(n + 2), (float)(n2 + 1), 0xFFFFFF);
            RealmsTextureManager.withBoundFace(serverData.ownerUUID, () -> {
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                DrawableHelper.drawTexture(matrixStack, n - 36, n2, 32, 32, 8.0f, 8.0f, 8, 8, 64, 64);
                DrawableHelper.drawTexture(matrixStack, n - 36, n2, 32, 32, 40.0f, 8.0f, 8, 8, 64, 64);
            });
        }
    }

    class RealmSelectionListTrialEntry
    extends Entry {
        private RealmSelectionListTrialEntry() {
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.renderTrialItem(matrices, index, x, y, mouseX, mouseY);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            RealmsMainScreen.this.popupOpenedByUser = true;
            return true;
        }

        private void renderTrialItem(MatrixStack matrixStack, int index, int x, int y, int mouseX, int mouseY) {
            int n = y + 8;
            _snowman = 0;
            boolean _snowman2 = false;
            if (x <= mouseX && mouseX <= (int)RealmsMainScreen.this.realmSelectionList.getScrollAmount() && y <= mouseY && mouseY <= y + 32) {
                _snowman2 = true;
            }
            _snowman = 0x7FFF7F;
            if (_snowman2 && !RealmsMainScreen.this.shouldShowPopup()) {
                _snowman = 6077788;
            }
            for (Text text : field_26449) {
                DrawableHelper.drawCenteredText(matrixStack, RealmsMainScreen.this.textRenderer, text, RealmsMainScreen.this.width / 2, n + _snowman, _snowman);
                _snowman += 10;
            }
        }
    }

    abstract class Entry
    extends AlwaysSelectedEntryListWidget.Entry<Entry> {
        private Entry() {
        }
    }

    class RealmSelectionList
    extends RealmsObjectSelectionList<Entry> {
        private boolean field_25723;

        public RealmSelectionList() {
            super(RealmsMainScreen.this.width, RealmsMainScreen.this.height, 32, RealmsMainScreen.this.height - 40, 36);
        }

        @Override
        public void clear() {
            super.clear();
            this.field_25723 = false;
        }

        public int method_30161(Entry entry) {
            this.field_25723 = true;
            return this.addEntry(entry);
        }

        @Override
        public boolean isFocused() {
            return RealmsMainScreen.this.getFocused() == this;
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (keyCode == 257 || keyCode == 32 || keyCode == 335) {
                AlwaysSelectedEntryListWidget.Entry entry = (AlwaysSelectedEntryListWidget.Entry)this.getSelected();
                if (entry == null) {
                    return super.keyPressed(keyCode, scanCode, modifiers);
                }
                return entry.mouseClicked(0.0, 0.0, 0);
            }
            return super.keyPressed(keyCode, scanCode, modifiers);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (button == 0 && mouseX < (double)this.getScrollbarPositionX() && mouseY >= (double)this.top && mouseY <= (double)this.bottom) {
                int n = RealmsMainScreen.this.realmSelectionList.getRowLeft();
                _snowman = this.getScrollbarPositionX();
                _snowman = (int)Math.floor(mouseY - (double)this.top) - this.headerHeight + (int)this.getScrollAmount() - 4;
                _snowman = _snowman / this.itemHeight;
                if (mouseX >= (double)n && mouseX <= (double)_snowman && _snowman >= 0 && _snowman >= 0 && _snowman < this.getItemCount()) {
                    this.itemClicked(_snowman, _snowman, mouseX, mouseY, this.width);
                    RealmsMainScreen.this.clicks = RealmsMainScreen.this.clicks + 7;
                    this.setSelected(_snowman);
                }
                return true;
            }
            return super.mouseClicked(mouseX, mouseY, button);
        }

        @Override
        public void setSelected(int index) {
            RealmsServer realmsServer;
            this.setSelectedItem(index);
            if (index == -1) {
                return;
            }
            if (this.field_25723) {
                if (index == 0) {
                    realmsServer = null;
                } else {
                    if (index - 1 >= RealmsMainScreen.this.realmsServers.size()) {
                        RealmsMainScreen.this.selectedServerId = -1L;
                        return;
                    }
                    realmsServer = (RealmsServer)RealmsMainScreen.this.realmsServers.get(index - 1);
                }
            } else {
                if (index >= RealmsMainScreen.this.realmsServers.size()) {
                    RealmsMainScreen.this.selectedServerId = -1L;
                    return;
                }
                realmsServer = (RealmsServer)RealmsMainScreen.this.realmsServers.get(index);
            }
            RealmsMainScreen.this.updateButtonStates(realmsServer);
            if (realmsServer == null) {
                RealmsMainScreen.this.selectedServerId = -1L;
                return;
            }
            if (realmsServer.state == RealmsServer.State.UNINITIALIZED) {
                RealmsMainScreen.this.selectedServerId = -1L;
                return;
            }
            RealmsMainScreen.this.selectedServerId = realmsServer.id;
            if (RealmsMainScreen.this.clicks >= 10 && ((RealmsMainScreen)RealmsMainScreen.this).playButton.active) {
                RealmsMainScreen.this.play(RealmsMainScreen.this.findServer(RealmsMainScreen.this.selectedServerId), RealmsMainScreen.this);
            }
        }

        @Override
        public void setSelected(@Nullable Entry entry) {
            super.setSelected(entry);
            int n = this.children().indexOf(entry);
            if (this.field_25723 && n == 0) {
                Realms.narrateNow(I18n.translate("mco.trial.message.line1", new Object[0]), I18n.translate("mco.trial.message.line2", new Object[0]));
            } else if (!this.field_25723 || n > 0) {
                RealmsServer realmsServer = (RealmsServer)RealmsMainScreen.this.realmsServers.get(n - (this.field_25723 ? 1 : 0));
                RealmsMainScreen.this.selectedServerId = realmsServer.id;
                RealmsMainScreen.this.updateButtonStates(realmsServer);
                if (realmsServer.state == RealmsServer.State.UNINITIALIZED) {
                    Realms.narrateNow(I18n.translate("mco.selectServer.uninitialized", new Object[0]) + I18n.translate("mco.gui.button", new Object[0]));
                } else {
                    Realms.narrateNow(I18n.translate("narrator.select", realmsServer.name));
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
                --selectionIndex;
            }
            if (selectionIndex >= RealmsMainScreen.this.realmsServers.size()) {
                return;
            }
            RealmsServer realmsServer = (RealmsServer)RealmsMainScreen.this.realmsServers.get(selectionIndex);
            if (realmsServer == null) {
                return;
            }
            if (realmsServer.state == RealmsServer.State.UNINITIALIZED) {
                RealmsMainScreen.this.selectedServerId = -1L;
                MinecraftClient.getInstance().openScreen(new RealmsCreateRealmScreen(realmsServer, RealmsMainScreen.this));
            } else {
                RealmsMainScreen.this.selectedServerId = realmsServer.id;
            }
            if (RealmsMainScreen.this.hoverState == HoverState.CONFIGURE) {
                RealmsMainScreen.this.selectedServerId = realmsServer.id;
                RealmsMainScreen.this.configureClicked(realmsServer);
            } else if (RealmsMainScreen.this.hoverState == HoverState.LEAVE) {
                RealmsMainScreen.this.selectedServerId = realmsServer.id;
                RealmsMainScreen.this.leaveClicked(realmsServer);
            } else if (RealmsMainScreen.this.hoverState == HoverState.EXPIRED) {
                RealmsMainScreen.this.onRenew();
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

        @Override
        public /* synthetic */ void setSelected(@Nullable EntryListWidget.Entry entry) {
            this.setSelected((Entry)entry);
        }
    }

    static enum HoverState {
        NONE,
        EXPIRED,
        LEAVE,
        CONFIGURE;

    }
}

