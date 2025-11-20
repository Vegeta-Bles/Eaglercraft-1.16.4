/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.realms.gui.screen;

import java.text.DateFormat;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.realms.RealmsClient;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.dto.Subscription;
import net.minecraft.client.realms.exception.RealmsServiceException;
import net.minecraft.client.realms.gui.screen.RealmsGenericErrorScreen;
import net.minecraft.client.realms.gui.screen.RealmsLongConfirmationScreen;
import net.minecraft.client.realms.gui.screen.RealmsScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsSubscriptionInfoScreen
extends RealmsScreen {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Text subscriptionTitle = new TranslatableText("mco.configure.world.subscription.title");
    private static final Text subscriptionStartLabelText = new TranslatableText("mco.configure.world.subscription.start");
    private static final Text timeLeftLabelText = new TranslatableText("mco.configure.world.subscription.timeleft");
    private static final Text daysLeftLabelText = new TranslatableText("mco.configure.world.subscription.recurring.daysleft");
    private static final Text field_26517 = new TranslatableText("mco.configure.world.subscription.expired");
    private static final Text field_26518 = new TranslatableText("mco.configure.world.subscription.less_than_a_day");
    private static final Text field_26519 = new TranslatableText("mco.configure.world.subscription.month");
    private static final Text field_26520 = new TranslatableText("mco.configure.world.subscription.months");
    private static final Text field_26521 = new TranslatableText("mco.configure.world.subscription.day");
    private static final Text field_26522 = new TranslatableText("mco.configure.world.subscription.days");
    private final Screen parent;
    private final RealmsServer serverData;
    private final Screen mainScreen;
    private Text daysLeft;
    private String startDate;
    private Subscription.SubscriptionType type;

    public RealmsSubscriptionInfoScreen(Screen parent, RealmsServer serverData, Screen mainScreen) {
        this.parent = parent;
        this.serverData = serverData;
        this.mainScreen = mainScreen;
    }

    @Override
    public void init() {
        this.getSubscription(this.serverData.id);
        Realms.narrateNow(subscriptionTitle.getString(), subscriptionStartLabelText.getString(), this.startDate, timeLeftLabelText.getString(), this.daysLeft.getString());
        this.client.keyboard.setRepeatEvents(true);
        this.addButton(new ButtonWidget(this.width / 2 - 100, RealmsSubscriptionInfoScreen.row(6), 200, 20, new TranslatableText("mco.configure.world.subscription.extend"), buttonWidget -> {
            String string = "https://aka.ms/ExtendJavaRealms?subscriptionId=" + this.serverData.remoteSubscriptionId + "&profileId=" + this.client.getSession().getUuid();
            this.client.keyboard.setClipboard(string);
            Util.getOperatingSystem().open(string);
        }));
        this.addButton(new ButtonWidget(this.width / 2 - 100, RealmsSubscriptionInfoScreen.row(12), 200, 20, ScreenTexts.BACK, buttonWidget -> this.client.openScreen(this.parent)));
        if (this.serverData.expired) {
            this.addButton(new ButtonWidget(this.width / 2 - 100, RealmsSubscriptionInfoScreen.row(10), 200, 20, new TranslatableText("mco.configure.world.delete.button"), buttonWidget -> {
                TranslatableText translatableText = new TranslatableText("mco.configure.world.delete.question.line1");
                _snowman = new TranslatableText("mco.configure.world.delete.question.line2");
                this.client.openScreen(new RealmsLongConfirmationScreen(this::method_25271, RealmsLongConfirmationScreen.Type.Warning, translatableText, _snowman, true));
            }));
        }
    }

    private void method_25271(boolean bl) {
        if (bl) {
            new Thread(this, "Realms-delete-realm"){
                final /* synthetic */ RealmsSubscriptionInfoScreen field_20164;
                {
                    this.field_20164 = realmsSubscriptionInfoScreen;
                    super(string);
                }

                public void run() {
                    try {
                        RealmsClient realmsClient = RealmsClient.createRealmsClient();
                        realmsClient.deleteWorld(RealmsSubscriptionInfoScreen.method_21501((RealmsSubscriptionInfoScreen)this.field_20164).id);
                    }
                    catch (RealmsServiceException realmsServiceException) {
                        RealmsSubscriptionInfoScreen.method_21498().error("Couldn't delete world");
                        RealmsSubscriptionInfoScreen.method_21498().error((Object)realmsServiceException);
                    }
                    RealmsSubscriptionInfoScreen.method_25267(this.field_20164).execute(() -> RealmsSubscriptionInfoScreen.method_25272(this.field_20164).openScreen(RealmsSubscriptionInfoScreen.method_25269(this.field_20164)));
                }
            }.start();
        }
        this.client.openScreen(this);
    }

    private void getSubscription(long worldId) {
        RealmsClient realmsClient = RealmsClient.createRealmsClient();
        try {
            Subscription subscription = realmsClient.subscriptionFor(worldId);
            this.daysLeft = this.daysLeftPresentation(subscription.daysLeft);
            this.startDate = RealmsSubscriptionInfoScreen.localPresentation(subscription.startDate);
            this.type = subscription.type;
        }
        catch (RealmsServiceException realmsServiceException) {
            LOGGER.error("Couldn't get subscription");
            this.client.openScreen(new RealmsGenericErrorScreen(realmsServiceException, this.parent));
        }
    }

    private static String localPresentation(long l) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getDefault());
        gregorianCalendar.setTimeInMillis(l);
        return DateFormat.getDateTimeInstance().format(gregorianCalendar.getTime());
    }

    @Override
    public void removed() {
        this.client.keyboard.setRepeatEvents(false);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            this.client.openScreen(this.parent);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        int n = this.width / 2 - 100;
        RealmsSubscriptionInfoScreen.drawCenteredText(matrices, this.textRenderer, subscriptionTitle, this.width / 2, 17, 0xFFFFFF);
        this.textRenderer.draw(matrices, subscriptionStartLabelText, (float)n, (float)RealmsSubscriptionInfoScreen.row(0), 0xA0A0A0);
        this.textRenderer.draw(matrices, this.startDate, (float)n, (float)RealmsSubscriptionInfoScreen.row(1), 0xFFFFFF);
        if (this.type == Subscription.SubscriptionType.NORMAL) {
            this.textRenderer.draw(matrices, timeLeftLabelText, (float)n, (float)RealmsSubscriptionInfoScreen.row(3), 0xA0A0A0);
        } else if (this.type == Subscription.SubscriptionType.RECURRING) {
            this.textRenderer.draw(matrices, daysLeftLabelText, (float)n, (float)RealmsSubscriptionInfoScreen.row(3), 0xA0A0A0);
        }
        this.textRenderer.draw(matrices, this.daysLeft, (float)n, (float)RealmsSubscriptionInfoScreen.row(4), 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }

    private Text daysLeftPresentation(int daysLeft) {
        if (daysLeft < 0 && this.serverData.expired) {
            return field_26517;
        }
        if (daysLeft <= 1) {
            return field_26518;
        }
        int n = daysLeft / 30;
        _snowman = daysLeft % 30;
        LiteralText _snowman2 = new LiteralText("");
        if (n > 0) {
            _snowman2.append(Integer.toString(n)).append(" ");
            if (n == 1) {
                _snowman2.append(field_26519);
            } else {
                _snowman2.append(field_26520);
            }
        }
        if (_snowman > 0) {
            if (n > 0) {
                _snowman2.append(", ");
            }
            _snowman2.append(Integer.toString(_snowman)).append(" ");
            if (_snowman == 1) {
                _snowman2.append(field_26521);
            } else {
                _snowman2.append(field_26522);
            }
        }
        return _snowman2;
    }

    static /* synthetic */ RealmsServer method_21501(RealmsSubscriptionInfoScreen realmsSubscriptionInfoScreen) {
        return realmsSubscriptionInfoScreen.serverData;
    }

    static /* synthetic */ Logger method_21498() {
        return LOGGER;
    }

    static /* synthetic */ MinecraftClient method_25267(RealmsSubscriptionInfoScreen realmsSubscriptionInfoScreen) {
        return realmsSubscriptionInfoScreen.client;
    }

    static /* synthetic */ Screen method_25269(RealmsSubscriptionInfoScreen realmsSubscriptionInfoScreen) {
        return realmsSubscriptionInfoScreen.mainScreen;
    }

    static /* synthetic */ MinecraftClient method_25272(RealmsSubscriptionInfoScreen realmsSubscriptionInfoScreen) {
        return realmsSubscriptionInfoScreen.client;
    }
}

