/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.realms.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.gui.RealmsDataFetcher;
import net.minecraft.client.realms.gui.screen.RealmsScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class RealmsNotificationsScreen
extends RealmsScreen {
    private static final Identifier INVITE_ICON = new Identifier("realms", "textures/gui/realms/invite_icon.png");
    private static final Identifier TRIAL_ICON = new Identifier("realms", "textures/gui/realms/trial_icon.png");
    private static final Identifier field_22700 = new Identifier("realms", "textures/gui/realms/news_notification_mainscreen.png");
    private static final RealmsDataFetcher REALMS_DATA_FETCHER = new RealmsDataFetcher();
    private volatile int numberOfPendingInvites;
    private static boolean checkedMcoAvailability;
    private static boolean trialAvailable;
    private static boolean validClient;
    private static boolean hasUnreadNews;

    @Override
    public void init() {
        this.checkIfMcoEnabled();
        this.client.keyboard.setRepeatEvents(true);
    }

    @Override
    public void tick() {
        if (!(this.method_25169() && this.method_25170() && validClient || REALMS_DATA_FETCHER.isStopped())) {
            REALMS_DATA_FETCHER.stop();
            return;
        }
        if (!validClient || !this.method_25169()) {
            return;
        }
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

    private boolean method_25169() {
        return this.client.options.realmsNotifications;
    }

    private boolean method_25170() {
        return this.client.currentScreen instanceof TitleScreen;
    }

    private void checkIfMcoEnabled() {
        if (!checkedMcoAvailability) {
            checkedMcoAvailability = true;
            new Thread(this, "Realms Notification Availability checker #1"){
                final /* synthetic */ RealmsNotificationsScreen field_19930;
                {
                    this.field_19930 = realmsNotificationsScreen;
                    super(string);
                }

                public void run() {
                    RealmsClient realmsClient = RealmsClient.createRealmsClient();
                    try {
                        RealmsClient.CompatibleVersionResponse compatibleVersionResponse = realmsClient.clientCompatible();
                        if (compatibleVersionResponse != RealmsClient.CompatibleVersionResponse.COMPATIBLE) {
                            return;
                        }
                    }
                    catch (RealmsServiceException realmsServiceException) {
                        if (realmsServiceException.httpResultCode != 401) {
                            RealmsNotificationsScreen.method_21296(false);
                        }
                        return;
                    }
                    RealmsNotificationsScreen.method_21297(true);
                }
            }.start();
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
        int n = this.numberOfPendingInvites;
        _snowman = 24;
        _snowman = this.height / 4 + 48;
        _snowman = this.width / 2 + 80;
        _snowman = _snowman + 48 + 2;
        _snowman = 0;
        if (hasUnreadNews) {
            this.client.getTextureManager().bindTexture(field_22700);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.pushMatrix();
            RenderSystem.scalef(0.4f, 0.4f, 0.4f);
            DrawableHelper.drawTexture(matrices, (int)((double)(_snowman + 2 - _snowman) * 2.5), (int)((double)_snowman * 2.5), 0.0f, 0.0f, 40, 40, 40, 40);
            RenderSystem.popMatrix();
            _snowman += 14;
        }
        if (n != 0) {
            this.client.getTextureManager().bindTexture(INVITE_ICON);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            DrawableHelper.drawTexture(matrices, _snowman - _snowman, _snowman - 6, 0.0f, 0.0f, 15, 25, 31, 25);
            _snowman += 16;
        }
        if (trialAvailable) {
            this.client.getTextureManager().bindTexture(TRIAL_ICON);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            _snowman = 0;
            if ((Util.getMeasuringTimeMs() / 800L & 1L) == 1L) {
                _snowman = 8;
            }
            DrawableHelper.drawTexture(matrices, _snowman + 4 - _snowman, _snowman + 4, 0.0f, _snowman, 8, 8, 8, 16);
        }
    }

    @Override
    public void removed() {
        REALMS_DATA_FETCHER.stop();
    }

    static /* synthetic */ boolean method_21296(boolean bl) {
        checkedMcoAvailability = bl;
        return checkedMcoAvailability;
    }

    static /* synthetic */ boolean method_21297(boolean bl) {
        validClient = bl;
        return validClient;
    }
}

