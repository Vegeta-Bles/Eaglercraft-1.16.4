/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.gui.hud.spectator;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.SpectatorHud;
import net.minecraft.client.gui.hud.spectator.RootSpectatorCommandGroup;
import net.minecraft.client.gui.hud.spectator.SpectatorMenuCloseCallback;
import net.minecraft.client.gui.hud.spectator.SpectatorMenuCommand;
import net.minecraft.client.gui.hud.spectator.SpectatorMenuCommandGroup;
import net.minecraft.client.gui.hud.spectator.SpectatorMenuState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class SpectatorMenu {
    private static final SpectatorMenuCommand CLOSE_COMMAND = new CloseSpectatorMenuCommand();
    private static final SpectatorMenuCommand PREVIOUS_PAGE_COMMAND = new ChangePageSpectatorMenuCommand(-1, true);
    private static final SpectatorMenuCommand NEXT_PAGE_COMMAND = new ChangePageSpectatorMenuCommand(1, true);
    private static final SpectatorMenuCommand DISABLED_NEXT_PAGE_COMMAND = new ChangePageSpectatorMenuCommand(1, false);
    private static final Text field_26613 = new TranslatableText("spectatorMenu.close");
    private static final Text field_26614 = new TranslatableText("spectatorMenu.previous_page");
    private static final Text field_26615 = new TranslatableText("spectatorMenu.next_page");
    public static final SpectatorMenuCommand BLANK_COMMAND = new SpectatorMenuCommand(){

        public void use(SpectatorMenu menu) {
        }

        public Text getName() {
            return LiteralText.EMPTY;
        }

        public void renderIcon(MatrixStack matrixStack, float f, int n) {
        }

        public boolean isEnabled() {
            return false;
        }
    };
    private final SpectatorMenuCloseCallback closeCallback;
    private SpectatorMenuCommandGroup currentGroup = new RootSpectatorCommandGroup();
    private int selectedSlot = -1;
    private int page;

    public SpectatorMenu(SpectatorMenuCloseCallback closeCallback) {
        this.closeCallback = closeCallback;
    }

    public SpectatorMenuCommand getCommand(int slot) {
        int n = slot + this.page * 6;
        if (this.page > 0 && slot == 0) {
            return PREVIOUS_PAGE_COMMAND;
        }
        if (slot == 7) {
            if (n < this.currentGroup.getCommands().size()) {
                return NEXT_PAGE_COMMAND;
            }
            return DISABLED_NEXT_PAGE_COMMAND;
        }
        if (slot == 8) {
            return CLOSE_COMMAND;
        }
        if (n < 0 || n >= this.currentGroup.getCommands().size()) {
            return BLANK_COMMAND;
        }
        return (SpectatorMenuCommand)MoreObjects.firstNonNull((Object)this.currentGroup.getCommands().get(n), (Object)BLANK_COMMAND);
    }

    public List<SpectatorMenuCommand> getCommands() {
        ArrayList arrayList = Lists.newArrayList();
        for (int i = 0; i <= 8; ++i) {
            arrayList.add(this.getCommand(i));
        }
        return arrayList;
    }

    public SpectatorMenuCommand getSelectedCommand() {
        return this.getCommand(this.selectedSlot);
    }

    public SpectatorMenuCommandGroup getCurrentGroup() {
        return this.currentGroup;
    }

    public void useCommand(int slot) {
        SpectatorMenuCommand spectatorMenuCommand = this.getCommand(slot);
        if (spectatorMenuCommand != BLANK_COMMAND) {
            if (this.selectedSlot == slot && spectatorMenuCommand.isEnabled()) {
                spectatorMenuCommand.use(this);
            } else {
                this.selectedSlot = slot;
            }
        }
    }

    public void close() {
        this.closeCallback.close(this);
    }

    public int getSelectedSlot() {
        return this.selectedSlot;
    }

    public void selectElement(SpectatorMenuCommandGroup group) {
        this.currentGroup = group;
        this.selectedSlot = -1;
        this.page = 0;
    }

    public SpectatorMenuState getCurrentState() {
        return new SpectatorMenuState(this.currentGroup, this.getCommands(), this.selectedSlot);
    }

    static class ChangePageSpectatorMenuCommand
    implements SpectatorMenuCommand {
        private final int direction;
        private final boolean enabled;

        public ChangePageSpectatorMenuCommand(int direction, boolean enabled) {
            this.direction = direction;
            this.enabled = enabled;
        }

        @Override
        public void use(SpectatorMenu menu) {
            menu.page = menu.page + this.direction;
        }

        @Override
        public Text getName() {
            return this.direction < 0 ? field_26614 : field_26615;
        }

        @Override
        public void renderIcon(MatrixStack matrixStack, float f, int n) {
            MinecraftClient.getInstance().getTextureManager().bindTexture(SpectatorHud.SPECTATOR_TEXTURE);
            if (this.direction < 0) {
                DrawableHelper.drawTexture(matrixStack, 0, 0, 144.0f, 0.0f, 16, 16, 256, 256);
            } else {
                DrawableHelper.drawTexture(matrixStack, 0, 0, 160.0f, 0.0f, 16, 16, 256, 256);
            }
        }

        @Override
        public boolean isEnabled() {
            return this.enabled;
        }
    }

    static class CloseSpectatorMenuCommand
    implements SpectatorMenuCommand {
        private CloseSpectatorMenuCommand() {
        }

        @Override
        public void use(SpectatorMenu menu) {
            menu.close();
        }

        @Override
        public Text getName() {
            return field_26613;
        }

        @Override
        public void renderIcon(MatrixStack matrixStack, float f, int n) {
            MinecraftClient.getInstance().getTextureManager().bindTexture(SpectatorHud.SPECTATOR_TEXTURE);
            DrawableHelper.drawTexture(matrixStack, 0, 0, 128.0f, 0.0f, 16, 16, 256, 256);
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}

