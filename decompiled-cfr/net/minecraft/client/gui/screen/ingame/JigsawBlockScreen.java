/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen.ingame;

import net.minecraft.block.JigsawBlock;
import net.minecraft.block.entity.JigsawBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.packet.c2s.play.JigsawGeneratingC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateJigsawC2SPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class JigsawBlockScreen
extends Screen {
    private static final Text field_26564 = new TranslatableText("jigsaw_block.joint_label");
    private static final Text field_26565 = new TranslatableText("jigsaw_block.pool");
    private static final Text field_26566 = new TranslatableText("jigsaw_block.name");
    private static final Text field_26567 = new TranslatableText("jigsaw_block.target");
    private static final Text field_26568 = new TranslatableText("jigsaw_block.final_state");
    private final JigsawBlockEntity jigsaw;
    private TextFieldWidget nameField;
    private TextFieldWidget targetField;
    private TextFieldWidget poolField;
    private TextFieldWidget finalStateField;
    private int generationDepth;
    private boolean keepJigsaws = true;
    private ButtonWidget jointRotationButton;
    private ButtonWidget doneButton;
    private JigsawBlockEntity.Joint joint;

    public JigsawBlockScreen(JigsawBlockEntity jigsaw) {
        super(NarratorManager.EMPTY);
        this.jigsaw = jigsaw;
    }

    @Override
    public void tick() {
        this.nameField.tick();
        this.targetField.tick();
        this.poolField.tick();
        this.finalStateField.tick();
    }

    private void onDone() {
        this.updateServer();
        this.client.openScreen(null);
    }

    private void onCancel() {
        this.client.openScreen(null);
    }

    private void updateServer() {
        this.client.getNetworkHandler().sendPacket(new UpdateJigsawC2SPacket(this.jigsaw.getPos(), new Identifier(this.nameField.getText()), new Identifier(this.targetField.getText()), new Identifier(this.poolField.getText()), this.finalStateField.getText(), this.joint));
    }

    private void generate() {
        this.client.getNetworkHandler().sendPacket(new JigsawGeneratingC2SPacket(this.jigsaw.getPos(), this.generationDepth, this.keepJigsaws));
    }

    @Override
    public void onClose() {
        this.onCancel();
    }

    @Override
    protected void init() {
        this.client.keyboard.setRepeatEvents(true);
        this.poolField = new TextFieldWidget(this.textRenderer, this.width / 2 - 152, 20, 300, 20, new TranslatableText("jigsaw_block.pool"));
        this.poolField.setMaxLength(128);
        this.poolField.setText(this.jigsaw.getPool().toString());
        this.poolField.setChangedListener(string -> this.updateDoneButtonState());
        this.children.add(this.poolField);
        this.nameField = new TextFieldWidget(this.textRenderer, this.width / 2 - 152, 55, 300, 20, new TranslatableText("jigsaw_block.name"));
        this.nameField.setMaxLength(128);
        this.nameField.setText(this.jigsaw.getName().toString());
        this.nameField.setChangedListener(string -> this.updateDoneButtonState());
        this.children.add(this.nameField);
        this.targetField = new TextFieldWidget(this.textRenderer, this.width / 2 - 152, 90, 300, 20, new TranslatableText("jigsaw_block.target"));
        this.targetField.setMaxLength(128);
        this.targetField.setText(this.jigsaw.getTarget().toString());
        this.targetField.setChangedListener(string -> this.updateDoneButtonState());
        this.children.add(this.targetField);
        this.finalStateField = new TextFieldWidget(this.textRenderer, this.width / 2 - 152, 125, 300, 20, new TranslatableText("jigsaw_block.final_state"));
        this.finalStateField.setMaxLength(256);
        this.finalStateField.setText(this.jigsaw.getFinalState());
        this.children.add(this.finalStateField);
        this.joint = this.jigsaw.getJoint();
        int n = this.textRenderer.getWidth(field_26564) + 10;
        this.jointRotationButton = this.addButton(new ButtonWidget(this.width / 2 - 152 + n, 150, 300 - n, 20, this.getLocalizedJointName(), buttonWidget -> {
            JigsawBlockEntity.Joint[] jointArray = JigsawBlockEntity.Joint.values();
            int _snowman2 = (this.joint.ordinal() + 1) % jointArray.length;
            this.joint = jointArray[_snowman2];
            buttonWidget.setMessage(this.getLocalizedJointName());
        }));
        this.jointRotationButton.active = _snowman = JigsawBlock.getFacing(this.jigsaw.getCachedState()).getAxis().isVertical();
        this.jointRotationButton.visible = _snowman;
        this.addButton(new SliderWidget(this, this.width / 2 - 154, 180, 100, 20, LiteralText.EMPTY, 0.0){
            final /* synthetic */ JigsawBlockScreen field_24053;
            {
                this.field_24053 = jigsawBlockScreen;
                super(n, n2, n3, n4, text, d);
                this.updateMessage();
            }

            protected void updateMessage() {
                this.setMessage(new TranslatableText("jigsaw_block.levels", JigsawBlockScreen.method_27269(this.field_24053)));
            }

            protected void applyValue() {
                JigsawBlockScreen.method_27270(this.field_24053, MathHelper.floor(MathHelper.clampedLerp(0.0, 7.0, this.value)));
            }
        });
        this.addButton(new ButtonWidget(this, this.width / 2 - 50, 180, 100, 20, new TranslatableText("jigsaw_block.keep_jigsaws"), buttonWidget -> {
            this.keepJigsaws = !this.keepJigsaws;
            buttonWidget.queueNarration(250);
        }){
            final /* synthetic */ JigsawBlockScreen field_25272;
            {
                this.field_25272 = jigsawBlockScreen;
                super(n, n2, n3, n4, text, pressAction);
            }

            public Text getMessage() {
                return ScreenTexts.composeToggleText(super.getMessage(), JigsawBlockScreen.method_29348(this.field_25272));
            }
        });
        this.addButton(new ButtonWidget(this.width / 2 + 54, 180, 100, 20, new TranslatableText("jigsaw_block.generate"), buttonWidget -> {
            this.onDone();
            this.generate();
        }));
        this.doneButton = this.addButton(new ButtonWidget(this.width / 2 - 4 - 150, 210, 150, 20, ScreenTexts.DONE, buttonWidget -> this.onDone()));
        this.addButton(new ButtonWidget(this.width / 2 + 4, 210, 150, 20, ScreenTexts.CANCEL, buttonWidget -> this.onCancel()));
        this.setInitialFocus(this.poolField);
        this.updateDoneButtonState();
    }

    private void updateDoneButtonState() {
        this.doneButton.active = Identifier.isValid(this.nameField.getText()) && Identifier.isValid(this.targetField.getText()) && Identifier.isValid(this.poolField.getText());
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        String string = this.nameField.getText();
        _snowman = this.targetField.getText();
        _snowman = this.poolField.getText();
        _snowman = this.finalStateField.getText();
        int _snowman2 = this.generationDepth;
        JigsawBlockEntity.Joint _snowman3 = this.joint;
        this.init(client, width, height);
        this.nameField.setText(string);
        this.targetField.setText(_snowman);
        this.poolField.setText(_snowman);
        this.finalStateField.setText(_snowman);
        this.generationDepth = _snowman2;
        this.joint = _snowman3;
        this.jointRotationButton.setMessage(this.getLocalizedJointName());
    }

    private Text getLocalizedJointName() {
        return new TranslatableText("jigsaw_block.joint." + this.joint.asString());
    }

    @Override
    public void removed() {
        this.client.keyboard.setRepeatEvents(false);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        if (this.doneButton.active && (keyCode == 257 || keyCode == 335)) {
            this.onDone();
            return true;
        }
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        JigsawBlockScreen.drawTextWithShadow(matrices, this.textRenderer, field_26565, this.width / 2 - 153, 10, 0xA0A0A0);
        this.poolField.render(matrices, mouseX, mouseY, delta);
        JigsawBlockScreen.drawTextWithShadow(matrices, this.textRenderer, field_26566, this.width / 2 - 153, 45, 0xA0A0A0);
        this.nameField.render(matrices, mouseX, mouseY, delta);
        JigsawBlockScreen.drawTextWithShadow(matrices, this.textRenderer, field_26567, this.width / 2 - 153, 80, 0xA0A0A0);
        this.targetField.render(matrices, mouseX, mouseY, delta);
        JigsawBlockScreen.drawTextWithShadow(matrices, this.textRenderer, field_26568, this.width / 2 - 153, 115, 0xA0A0A0);
        this.finalStateField.render(matrices, mouseX, mouseY, delta);
        if (JigsawBlock.getFacing(this.jigsaw.getCachedState()).getAxis().isVertical()) {
            JigsawBlockScreen.drawTextWithShadow(matrices, this.textRenderer, field_26564, this.width / 2 - 153, 156, 0xFFFFFF);
        }
        super.render(matrices, mouseX, mouseY, delta);
    }

    static /* synthetic */ int method_27269(JigsawBlockScreen jigsawBlockScreen) {
        return jigsawBlockScreen.generationDepth;
    }

    static /* synthetic */ int method_27270(JigsawBlockScreen jigsawBlockScreen, int n) {
        jigsawBlockScreen.generationDepth = n;
        return jigsawBlockScreen.generationDepth;
    }

    static /* synthetic */ boolean method_29348(JigsawBlockScreen jigsawBlockScreen) {
        return jigsawBlockScreen.keepJigsaws;
    }
}

