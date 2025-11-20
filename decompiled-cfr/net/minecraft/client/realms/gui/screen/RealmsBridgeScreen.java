/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.realms.gui.screen;

import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.client.realms.gui.screen.RealmsNotificationsScreen;
import net.minecraft.client.realms.gui.screen.RealmsScreen;

public class RealmsBridgeScreen
extends RealmsScreen {
    private Screen previousScreen;

    public void switchToRealms(Screen parentScreen) {
        this.previousScreen = parentScreen;
        MinecraftClient.getInstance().openScreen(new RealmsMainScreen(this));
    }

    @Nullable
    public RealmsScreen getNotificationScreen(Screen parentScreen) {
        this.previousScreen = parentScreen;
        return new RealmsNotificationsScreen();
    }

    @Override
    public void init() {
        MinecraftClient.getInstance().openScreen(this.previousScreen);
    }
}

