/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.realms.gui.screen;

import net.minecraft.class_5489;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.realms.Realms;
import net.minecraft.client.realms.gui.screen.RealmsScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;

public class RealmsParentalConsentScreen
extends RealmsScreen {
    private static final Text field_26491 = new TranslatableText("mco.account.privacyinfo");
    private final Screen parent;
    private class_5489 field_26492 = class_5489.field_26528;

    public RealmsParentalConsentScreen(Screen screen) {
        this.parent = screen;
    }

    @Override
    public void init() {
        Realms.narrateNow(field_26491.getString());
        TranslatableText translatableText = new TranslatableText("mco.account.update");
        Text _snowman2 = ScreenTexts.BACK;
        int _snowman3 = Math.max(this.textRenderer.getWidth(translatableText), this.textRenderer.getWidth(_snowman2)) + 30;
        _snowman = new TranslatableText("mco.account.privacy.info");
        int _snowman4 = (int)((double)this.textRenderer.getWidth(_snowman) * 1.2);
        this.addButton(new ButtonWidget(this.width / 2 - _snowman4 / 2, RealmsParentalConsentScreen.row(11), _snowman4, 20, _snowman, buttonWidget -> Util.getOperatingSystem().open("https://aka.ms/MinecraftGDPR")));
        this.addButton(new ButtonWidget(this.width / 2 - (_snowman3 + 5), RealmsParentalConsentScreen.row(13), _snowman3, 20, translatableText, buttonWidget -> Util.getOperatingSystem().open("https://aka.ms/UpdateMojangAccount")));
        this.addButton(new ButtonWidget(this.width / 2 + 5, RealmsParentalConsentScreen.row(13), _snowman3, 20, _snowman2, buttonWidget -> this.client.openScreen(this.parent)));
        this.field_26492 = class_5489.method_30890(this.textRenderer, field_26491, (int)Math.round((double)this.width * 0.9));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.field_26492.method_30889(matrices, this.width / 2, 15, 15, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
}

