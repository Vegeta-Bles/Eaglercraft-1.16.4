/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.realms.gui.screen;

import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.realms.RealmsLabel;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.gui.screen.RealmsConfigureWorldScreen;
import net.minecraft.client.realms.gui.screen.RealmsLongConfirmationScreen;
import net.minecraft.client.realms.gui.screen.RealmsScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class RealmsSettingsScreen
extends RealmsScreen {
    private static final Text field_26514 = new TranslatableText("mco.configure.world.name");
    private static final Text field_26515 = new TranslatableText("mco.configure.world.description");
    private final RealmsConfigureWorldScreen parent;
    private final RealmsServer serverData;
    private ButtonWidget doneButton;
    private TextFieldWidget descEdit;
    private TextFieldWidget nameEdit;
    private RealmsLabel titleLabel;

    public RealmsSettingsScreen(RealmsConfigureWorldScreen parent, RealmsServer serverData) {
        this.parent = parent;
        this.serverData = serverData;
    }

    @Override
    public void tick() {
        this.nameEdit.tick();
        this.descEdit.tick();
        this.doneButton.active = !this.nameEdit.getText().trim().isEmpty();
    }

    @Override
    public void init() {
        this.client.keyboard.setRepeatEvents(true);
        int n = this.width / 2 - 106;
        this.doneButton = this.addButton(new ButtonWidget(n - 2, RealmsSettingsScreen.row(12), 106, 20, new TranslatableText("mco.configure.world.buttons.done"), buttonWidget -> this.save()));
        this.addButton(new ButtonWidget(this.width / 2 + 2, RealmsSettingsScreen.row(12), 106, 20, ScreenTexts.CANCEL, buttonWidget -> this.client.openScreen(this.parent)));
        String _snowman2 = this.serverData.state == RealmsServer.State.OPEN ? "mco.configure.world.buttons.close" : "mco.configure.world.buttons.open";
        ButtonWidget _snowman3 = new ButtonWidget(this.width / 2 - 53, RealmsSettingsScreen.row(0), 106, 20, new TranslatableText(_snowman2), buttonWidget -> {
            if (this.serverData.state == RealmsServer.State.OPEN) {
                TranslatableText translatableText = new TranslatableText("mco.configure.world.close.question.line1");
                _snowman = new TranslatableText("mco.configure.world.close.question.line2");
                this.client.openScreen(new RealmsLongConfirmationScreen(bl -> {
                    if (bl) {
                        this.parent.closeTheWorld(this);
                    } else {
                        this.client.openScreen(this);
                    }
                }, RealmsLongConfirmationScreen.Type.Info, translatableText, _snowman, true));
            } else {
                this.parent.openTheWorld(false, this);
            }
        });
        this.addButton(_snowman3);
        this.nameEdit = new TextFieldWidget(this.client.textRenderer, n, RealmsSettingsScreen.row(4), 212, 20, null, new TranslatableText("mco.configure.world.name"));
        this.nameEdit.setMaxLength(32);
        this.nameEdit.setText(this.serverData.getName());
        this.addChild(this.nameEdit);
        this.focusOn(this.nameEdit);
        this.descEdit = new TextFieldWidget(this.client.textRenderer, n, RealmsSettingsScreen.row(8), 212, 20, null, new TranslatableText("mco.configure.world.description"));
        this.descEdit.setMaxLength(32);
        this.descEdit.setText(this.serverData.getDescription());
        this.addChild(this.descEdit);
        this.titleLabel = this.addChild(new RealmsLabel(new TranslatableText("mco.configure.world.settings.title"), this.width / 2, 17, 0xFFFFFF));
        this.narrateLabels();
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
        this.titleLabel.render(this, matrices);
        this.textRenderer.draw(matrices, field_26514, (float)(this.width / 2 - 106), (float)RealmsSettingsScreen.row(3), 0xA0A0A0);
        this.textRenderer.draw(matrices, field_26515, (float)(this.width / 2 - 106), (float)RealmsSettingsScreen.row(7), 0xA0A0A0);
        this.nameEdit.render(matrices, mouseX, mouseY, delta);
        this.descEdit.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    public void save() {
        this.parent.saveSettings(this.nameEdit.getText(), this.descEdit.getText());
    }
}

