/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  javax.annotation.Nullable
 */
package net.minecraft.client.toast;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class SystemToast
implements Toast {
    private final Type type;
    private Text title;
    private List<OrderedText> lines;
    private long startTime;
    private boolean justUpdated;
    private final int width;

    public SystemToast(Type type, Text title, @Nullable Text description) {
        this(type, title, (List<OrderedText>)SystemToast.getTextAsList(description), 160);
    }

    public static SystemToast create(MinecraftClient client, Type type, Text title, Text description) {
        TextRenderer textRenderer = client.textRenderer;
        List<OrderedText> _snowman2 = textRenderer.wrapLines(description, 200);
        int _snowman3 = Math.max(200, _snowman2.stream().mapToInt(textRenderer::getWidth).max().orElse(200));
        return new SystemToast(type, title, _snowman2, _snowman3 + 30);
    }

    private SystemToast(Type type, Text title, List<OrderedText> lines, int width) {
        this.type = type;
        this.title = title;
        this.lines = lines;
        this.width = width;
    }

    private static ImmutableList<OrderedText> getTextAsList(@Nullable Text text) {
        return text == null ? ImmutableList.of() : ImmutableList.of((Object)text.asOrderedText());
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public Toast.Visibility draw(MatrixStack matrices, ToastManager manager, long startTime) {
        if (this.justUpdated) {
            this.startTime = startTime;
            this.justUpdated = false;
        }
        manager.getGame().getTextureManager().bindTexture(TEXTURE);
        RenderSystem.color3f(1.0f, 1.0f, 1.0f);
        int n = this.getWidth();
        _snowman = 12;
        if (n == 160 && this.lines.size() <= 1) {
            manager.drawTexture(matrices, 0, 0, 0, 64, n, this.getHeight());
        } else {
            _snowman = this.getHeight() + Math.max(0, this.lines.size() - 1) * 12;
            _snowman = 28;
            _snowman = Math.min(4, _snowman - 28);
            this.drawPart(matrices, manager, n, 0, 0, 28);
            for (_snowman = 28; _snowman < _snowman - _snowman; _snowman += 10) {
                this.drawPart(matrices, manager, n, 16, _snowman, Math.min(16, _snowman - _snowman - _snowman));
            }
            this.drawPart(matrices, manager, n, 32 - _snowman, _snowman - _snowman, _snowman);
        }
        if (this.lines == null) {
            manager.getGame().textRenderer.draw(matrices, this.title, 18.0f, 12.0f, -256);
        } else {
            manager.getGame().textRenderer.draw(matrices, this.title, 18.0f, 7.0f, -256);
            for (_snowman = 0; _snowman < this.lines.size(); ++_snowman) {
                manager.getGame().textRenderer.draw(matrices, this.lines.get(_snowman), 18.0f, (float)(18 + _snowman * 12), -1);
            }
        }
        return startTime - this.startTime < 5000L ? Toast.Visibility.SHOW : Toast.Visibility.HIDE;
    }

    private void drawPart(MatrixStack matrices, ToastManager manager, int width, int textureV, int y, int height) {
        int n = textureV == 0 ? 20 : 5;
        _snowman = Math.min(60, width - n);
        manager.drawTexture(matrices, 0, y, 0, 64 + textureV, n, height);
        for (_snowman = n; _snowman < width - _snowman; _snowman += 64) {
            manager.drawTexture(matrices, _snowman, y, 32, 64 + textureV, Math.min(64, width - _snowman - _snowman), height);
        }
        manager.drawTexture(matrices, width - _snowman, y, 160 - _snowman, 64 + textureV, _snowman, height);
    }

    public void setContent(Text title, @Nullable Text description) {
        this.title = title;
        this.lines = SystemToast.getTextAsList(description);
        this.justUpdated = true;
    }

    public Type getType() {
        return this.type;
    }

    public static void add(ToastManager manager, Type type, Text title, @Nullable Text description) {
        manager.add(new SystemToast(type, title, description));
    }

    public static void show(ToastManager manager, Type type, Text title, @Nullable Text description) {
        SystemToast systemToast = manager.getToast(SystemToast.class, (Object)type);
        if (systemToast == null) {
            SystemToast.add(manager, type, title, description);
        } else {
            systemToast.setContent(title, description);
        }
    }

    public static void addWorldAccessFailureToast(MinecraftClient client, String worldName) {
        SystemToast.add(client.getToastManager(), Type.WORLD_ACCESS_FAILURE, new TranslatableText("selectWorld.access_failure"), new LiteralText(worldName));
    }

    public static void addWorldDeleteFailureToast(MinecraftClient client, String worldName) {
        SystemToast.add(client.getToastManager(), Type.WORLD_ACCESS_FAILURE, new TranslatableText("selectWorld.delete_failure"), new LiteralText(worldName));
    }

    public static void addPackCopyFailure(MinecraftClient client, String directory) {
        SystemToast.add(client.getToastManager(), Type.PACK_COPY_FAILURE, new TranslatableText("pack.copyFailure"), new LiteralText(directory));
    }

    @Override
    public /* synthetic */ Object getType() {
        return this.getType();
    }

    public static enum Type {
        TUTORIAL_HINT,
        NARRATOR_TOGGLE,
        WORLD_BACKUP,
        WORLD_GEN_SETTINGS_TRANSFER,
        PACK_LOAD_FAILURE,
        WORLD_ACCESS_FAILURE,
        PACK_COPY_FAILURE;

    }
}

