/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.widget;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.client.util.math.MatrixStack;

public class ButtonListWidget
extends ElementListWidget<ButtonEntry> {
    public ButtonListWidget(MinecraftClient minecraftClient, int n, int n2, int n3, int n4, int n5) {
        super(minecraftClient, n, n2, n3, n4, n5);
        this.centerListVertically = false;
    }

    public int addSingleOptionEntry(Option option) {
        return this.addEntry(ButtonEntry.create(this.client.options, this.width, option));
    }

    public void addOptionEntry(Option firstOption, @Nullable Option secondOption) {
        this.addEntry(ButtonEntry.create(this.client.options, this.width, firstOption, secondOption));
    }

    public void addAll(Option[] options) {
        for (int i = 0; i < options.length; i += 2) {
            this.addOptionEntry(options[i], i < options.length - 1 ? options[i + 1] : null);
        }
    }

    @Override
    public int getRowWidth() {
        return 400;
    }

    @Override
    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 32;
    }

    @Nullable
    public AbstractButtonWidget getButtonFor(Option option) {
        for (ButtonEntry buttonEntry : this.children()) {
            for (AbstractButtonWidget abstractButtonWidget : buttonEntry.buttons) {
                if (!(abstractButtonWidget instanceof OptionButtonWidget) || ((OptionButtonWidget)abstractButtonWidget).getOption() != option) continue;
                return abstractButtonWidget;
            }
        }
        return null;
    }

    public Optional<AbstractButtonWidget> getHoveredButton(double mouseX, double mouseY) {
        for (ButtonEntry buttonEntry : this.children()) {
            for (AbstractButtonWidget abstractButtonWidget : buttonEntry.buttons) {
                if (!abstractButtonWidget.isMouseOver(mouseX, mouseY)) continue;
                return Optional.of(abstractButtonWidget);
            }
        }
        return Optional.empty();
    }

    public static class ButtonEntry
    extends ElementListWidget.Entry<ButtonEntry> {
        private final List<AbstractButtonWidget> buttons;

        private ButtonEntry(List<AbstractButtonWidget> buttons) {
            this.buttons = buttons;
        }

        public static ButtonEntry create(GameOptions options, int width, Option option) {
            return new ButtonEntry((List<AbstractButtonWidget>)ImmutableList.of((Object)option.createButton(options, width / 2 - 155, 0, 310)));
        }

        public static ButtonEntry create(GameOptions options, int width, Option firstOption, @Nullable Option secondOption) {
            AbstractButtonWidget abstractButtonWidget = firstOption.createButton(options, width / 2 - 155, 0, 150);
            if (secondOption == null) {
                return new ButtonEntry((List<AbstractButtonWidget>)ImmutableList.of((Object)abstractButtonWidget));
            }
            return new ButtonEntry((List<AbstractButtonWidget>)ImmutableList.of((Object)abstractButtonWidget, (Object)secondOption.createButton(options, width / 2 - 155 + 160, 0, 150)));
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.buttons.forEach(button -> {
                button.y = y;
                button.render(matrices, mouseX, mouseY, tickDelta);
            });
        }

        @Override
        public List<? extends Element> children() {
            return this.buttons;
        }
    }
}

