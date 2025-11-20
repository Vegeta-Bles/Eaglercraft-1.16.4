/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.hash.Hashing
 *  com.google.common.util.concurrent.ThreadFactoryBuilder
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.Validate
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.gui.screen.multiplayer;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.blaze3d.systems.RenderSystem;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.client.network.LanServerInfo;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.options.ServerList;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.NarratorManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MultiplayerServerListWidget
extends AlwaysSelectedEntryListWidget<Entry> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ThreadPoolExecutor SERVER_PINGER_THREAD_POOL = new ScheduledThreadPoolExecutor(5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).setUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new UncaughtExceptionLogger(LOGGER)).build());
    private static final Identifier UNKNOWN_SERVER_TEXTURE = new Identifier("textures/misc/unknown_server.png");
    private static final Identifier SERVER_SELECTION_TEXTURE = new Identifier("textures/gui/server_selection.png");
    private static final Text field_26581 = new TranslatableText("lanServer.scanning");
    private static final Text field_26582 = new TranslatableText("multiplayer.status.cannot_resolve").formatted(Formatting.DARK_RED);
    private static final Text field_26583 = new TranslatableText("multiplayer.status.cannot_connect").formatted(Formatting.DARK_RED);
    private static final Text field_26849 = new TranslatableText("multiplayer.status.incompatible");
    private static final Text field_26586 = new TranslatableText("multiplayer.status.no_connection");
    private static final Text field_26587 = new TranslatableText("multiplayer.status.pinging");
    private final MultiplayerScreen screen;
    private final List<ServerEntry> servers = Lists.newArrayList();
    private final Entry scanningEntry = new ScanningEntry();
    private final List<LanServerEntry> lanServers = Lists.newArrayList();

    public MultiplayerServerListWidget(MultiplayerScreen screen, MinecraftClient client, int width, int height, int top, int bottom, int entryHeight) {
        super(client, width, height, top, bottom, entryHeight);
        this.screen = screen;
    }

    private void updateEntries() {
        this.clearEntries();
        this.servers.forEach(this::addEntry);
        this.addEntry(this.scanningEntry);
        this.lanServers.forEach(this::addEntry);
    }

    @Override
    public void setSelected(@Nullable Entry entry) {
        super.setSelected(entry);
        if (this.getSelected() instanceof ServerEntry) {
            NarratorManager.INSTANCE.narrate(new TranslatableText("narrator.select", ((ServerEntry)((ServerEntry)this.getSelected())).server.name).getString());
        }
        this.screen.updateButtonActivationStates();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        Entry entry = (Entry)this.getSelected();
        return entry != null && entry.keyPressed(keyCode, scanCode, modifiers) || super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected void moveSelection(EntryListWidget.MoveDirection direction) {
        this.moveSelectionIf(direction, entry -> !(entry instanceof ScanningEntry));
    }

    public void setServers(ServerList servers) {
        this.servers.clear();
        for (int i = 0; i < servers.size(); ++i) {
            this.servers.add(new ServerEntry(this.screen, servers.get(i)));
        }
        this.updateEntries();
    }

    public void setLanServers(List<LanServerInfo> lanServers) {
        this.lanServers.clear();
        for (LanServerInfo lanServerInfo : lanServers) {
            this.lanServers.add(new LanServerEntry(this.screen, lanServerInfo));
        }
        this.updateEntries();
    }

    @Override
    protected int getScrollbarPositionX() {
        return super.getScrollbarPositionX() + 30;
    }

    @Override
    public int getRowWidth() {
        return super.getRowWidth() + 85;
    }

    @Override
    protected boolean isFocused() {
        return this.screen.getFocused() == this;
    }

    public class ServerEntry
    extends Entry {
        private final MultiplayerScreen screen;
        private final MinecraftClient client;
        private final ServerInfo server;
        private final Identifier iconTextureId;
        private String iconUri;
        private NativeImageBackedTexture icon;
        private long time;

        protected ServerEntry(MultiplayerScreen screen, ServerInfo server) {
            this.screen = screen;
            this.server = server;
            this.client = MinecraftClient.getInstance();
            this.iconTextureId = new Identifier("servers/" + Hashing.sha1().hashUnencodedChars((CharSequence)server.address) + "/icon");
            this.icon = (NativeImageBackedTexture)this.client.getTextureManager().getTexture(this.iconTextureId);
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            List<Text> _snowman7;
            Text _snowman6;
            if (!this.server.online) {
                this.server.online = true;
                this.server.ping = -2L;
                this.server.label = LiteralText.EMPTY;
                this.server.playerCountLabel = LiteralText.EMPTY;
                SERVER_PINGER_THREAD_POOL.submit(() -> {
                    try {
                        this.screen.getServerListPinger().add(this.server, () -> this.client.execute(this::method_29978));
                    }
                    catch (UnknownHostException unknownHostException) {
                        this.server.ping = -1L;
                        this.server.label = field_26582;
                    }
                    catch (Exception exception) {
                        this.server.ping = -1L;
                        this.server.label = field_26583;
                    }
                });
            }
            boolean bl = this.server.protocolVersion != SharedConstants.getGameVersion().getProtocolVersion();
            this.client.textRenderer.draw(matrices, this.server.name, (float)(x + 32 + 3), (float)(y + 1), 0xFFFFFF);
            List<OrderedText> _snowman2 = this.client.textRenderer.wrapLines(this.server.label, entryWidth - 32 - 2);
            for (int i = 0; i < Math.min(_snowman2.size(), 2); ++i) {
                this.client.textRenderer.draw(matrices, _snowman2.get(i), (float)(x + 32 + 3), (float)(y + 12 + this.client.textRenderer.fontHeight * i), 0x808080);
            }
            Text _snowman3 = bl ? this.server.version.shallowCopy().formatted(Formatting.RED) : this.server.playerCountLabel;
            int _snowman4 = this.client.textRenderer.getWidth(_snowman3);
            this.client.textRenderer.draw(matrices, _snowman3, (float)(x + entryWidth - _snowman4 - 15 - 2), (float)(y + 1), 0x808080);
            int _snowman5 = 0;
            if (bl) {
                int n = 5;
                _snowman6 = field_26849;
                _snowman7 = this.server.playerListSummary;
            } else if (this.server.online && this.server.ping != -2L) {
                n = this.server.ping < 0L ? 5 : (this.server.ping < 150L ? 0 : (this.server.ping < 300L ? 1 : (this.server.ping < 600L ? 2 : (this.server.ping < 1000L ? 3 : 4))));
                if (this.server.ping < 0L) {
                    _snowman6 = field_26586;
                    _snowman7 = Collections.emptyList();
                } else {
                    _snowman6 = new TranslatableText("multiplayer.status.ping", this.server.ping);
                    _snowman7 = this.server.playerListSummary;
                }
            } else {
                _snowman5 = 1;
                n = (int)(Util.getMeasuringTimeMs() / 100L + (long)(index * 2) & 7L);
                if (n > 4) {
                    n = 8 - n;
                }
                _snowman6 = field_26587;
                _snowman7 = Collections.emptyList();
            }
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
            DrawableHelper.drawTexture(matrices, x + entryWidth - 15, y, _snowman5 * 10, 176 + n * 8, 10, 8, 256, 256);
            String string = this.server.getIcon();
            if (!Objects.equals(string, this.iconUri)) {
                if (this.method_29979(string)) {
                    this.iconUri = string;
                } else {
                    this.server.setIcon(null);
                    this.method_29978();
                }
            }
            if (this.icon != null) {
                this.draw(matrices, x, y, this.iconTextureId);
            } else {
                this.draw(matrices, x, y, UNKNOWN_SERVER_TEXTURE);
            }
            int _snowman8 = mouseX - x;
            int _snowman9 = mouseY - y;
            if (_snowman8 >= entryWidth - 15 && _snowman8 <= entryWidth - 5 && _snowman9 >= 0 && _snowman9 <= 8) {
                this.screen.setTooltip(Collections.singletonList(_snowman6));
            } else if (_snowman8 >= entryWidth - _snowman4 - 15 - 2 && _snowman8 <= entryWidth - 15 - 2 && _snowman9 >= 0 && _snowman9 <= 8) {
                this.screen.setTooltip(_snowman7);
            }
            if (this.client.options.touchscreen || hovered) {
                this.client.getTextureManager().bindTexture(SERVER_SELECTION_TEXTURE);
                DrawableHelper.fill(matrices, x, y, x + 32, y + 32, -1601138544);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                int n = mouseX - x;
                _snowman = mouseY - y;
                if (this.method_20136()) {
                    if (n < 32 && n > 16) {
                        DrawableHelper.drawTexture(matrices, x, y, 0.0f, 32.0f, 32, 32, 256, 256);
                    } else {
                        DrawableHelper.drawTexture(matrices, x, y, 0.0f, 0.0f, 32, 32, 256, 256);
                    }
                }
                if (index > 0) {
                    if (n < 16 && _snowman < 16) {
                        DrawableHelper.drawTexture(matrices, x, y, 96.0f, 32.0f, 32, 32, 256, 256);
                    } else {
                        DrawableHelper.drawTexture(matrices, x, y, 96.0f, 0.0f, 32, 32, 256, 256);
                    }
                }
                if (index < this.screen.getServerList().size() - 1) {
                    if (n < 16 && _snowman > 16) {
                        DrawableHelper.drawTexture(matrices, x, y, 64.0f, 32.0f, 32, 32, 256, 256);
                    } else {
                        DrawableHelper.drawTexture(matrices, x, y, 64.0f, 0.0f, 32, 32, 256, 256);
                    }
                }
            }
        }

        public void method_29978() {
            this.screen.getServerList().saveFile();
        }

        protected void draw(MatrixStack matrixStack, int n, int n2, Identifier identifier) {
            this.client.getTextureManager().bindTexture(identifier);
            RenderSystem.enableBlend();
            DrawableHelper.drawTexture(matrixStack, n, n2, 0.0f, 0.0f, 32, 32, 32, 32);
            RenderSystem.disableBlend();
        }

        private boolean method_20136() {
            return true;
        }

        private boolean method_29979(@Nullable String string) {
            if (string == null) {
                this.client.getTextureManager().destroyTexture(this.iconTextureId);
                if (this.icon != null && this.icon.getImage() != null) {
                    this.icon.getImage().close();
                }
                this.icon = null;
            } else {
                try {
                    NativeImage nativeImage = NativeImage.read(string);
                    Validate.validState((nativeImage.getWidth() == 64 ? 1 : 0) != 0, (String)"Must be 64 pixels wide", (Object[])new Object[0]);
                    Validate.validState((nativeImage.getHeight() == 64 ? 1 : 0) != 0, (String)"Must be 64 pixels high", (Object[])new Object[0]);
                    if (this.icon == null) {
                        this.icon = new NativeImageBackedTexture(nativeImage);
                    } else {
                        this.icon.setImage(nativeImage);
                        this.icon.upload();
                    }
                    this.client.getTextureManager().registerTexture(this.iconTextureId, this.icon);
                }
                catch (Throwable throwable) {
                    LOGGER.error("Invalid icon for server {} ({})", (Object)this.server.name, (Object)this.server.address, (Object)throwable);
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (Screen.hasShiftDown()) {
                MultiplayerServerListWidget multiplayerServerListWidget = this.screen.serverListWidget;
                int _snowman2 = multiplayerServerListWidget.children().indexOf(this);
                if (keyCode == 264 && _snowman2 < this.screen.getServerList().size() - 1 || keyCode == 265 && _snowman2 > 0) {
                    this.swapEntries(_snowman2, keyCode == 264 ? _snowman2 + 1 : _snowman2 - 1);
                    return true;
                }
            }
            return super.keyPressed(keyCode, scanCode, modifiers);
        }

        private void swapEntries(int i, int j) {
            this.screen.getServerList().swapEntries(i, j);
            this.screen.serverListWidget.setServers(this.screen.getServerList());
            Entry entry = (Entry)this.screen.serverListWidget.children().get(j);
            this.screen.serverListWidget.setSelected(entry);
            MultiplayerServerListWidget.this.ensureVisible(entry);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            double d = mouseX - (double)MultiplayerServerListWidget.this.getRowLeft();
            _snowman = mouseY - (double)MultiplayerServerListWidget.this.getRowTop(MultiplayerServerListWidget.this.children().indexOf(this));
            if (d <= 32.0) {
                if (d < 32.0 && d > 16.0 && this.method_20136()) {
                    this.screen.select(this);
                    this.screen.connect();
                    return true;
                }
                int n = this.screen.serverListWidget.children().indexOf(this);
                if (d < 16.0 && _snowman < 16.0 && n > 0) {
                    this.swapEntries(n, n - 1);
                    return true;
                }
                if (d < 16.0 && _snowman > 16.0 && n < this.screen.getServerList().size() - 1) {
                    this.swapEntries(n, n + 1);
                    return true;
                }
            }
            this.screen.select(this);
            if (Util.getMeasuringTimeMs() - this.time < 250L) {
                this.screen.connect();
            }
            this.time = Util.getMeasuringTimeMs();
            return false;
        }

        public ServerInfo getServer() {
            return this.server;
        }
    }

    public static class LanServerEntry
    extends Entry {
        private static final Text field_26588 = new TranslatableText("lanServer.title");
        private static final Text field_26589 = new TranslatableText("selectServer.hiddenAddress");
        private final MultiplayerScreen screen;
        protected final MinecraftClient client;
        protected final LanServerInfo server;
        private long time;

        protected LanServerEntry(MultiplayerScreen screen, LanServerInfo server) {
            this.screen = screen;
            this.server = server;
            this.client = MinecraftClient.getInstance();
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.client.textRenderer.draw(matrices, field_26588, (float)(x + 32 + 3), (float)(y + 1), 0xFFFFFF);
            this.client.textRenderer.draw(matrices, this.server.getMotd(), (float)(x + 32 + 3), (float)(y + 12), 0x808080);
            if (this.client.options.hideServerAddress) {
                this.client.textRenderer.draw(matrices, field_26589, (float)(x + 32 + 3), (float)(y + 12 + 11), 0x303030);
            } else {
                this.client.textRenderer.draw(matrices, this.server.getAddressPort(), (float)(x + 32 + 3), (float)(y + 12 + 11), 0x303030);
            }
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            this.screen.select(this);
            if (Util.getMeasuringTimeMs() - this.time < 250L) {
                this.screen.connect();
            }
            this.time = Util.getMeasuringTimeMs();
            return false;
        }

        public LanServerInfo getLanServerEntry() {
            return this.server;
        }
    }

    public static class ScanningEntry
    extends Entry {
        private final MinecraftClient client = MinecraftClient.getInstance();

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            String string;
            int n = y + entryHeight / 2 - this.client.textRenderer.fontHeight / 2;
            this.client.textRenderer.draw(matrices, field_26581, (float)(this.client.currentScreen.width / 2 - this.client.textRenderer.getWidth(field_26581) / 2), (float)n, 0xFFFFFF);
            switch ((int)(Util.getMeasuringTimeMs() / 300L % 4L)) {
                default: {
                    string = "O o o";
                    break;
                }
                case 1: 
                case 3: {
                    string = "o O o";
                    break;
                }
                case 2: {
                    string = "o o O";
                }
            }
            this.client.textRenderer.draw(matrices, string, (float)(this.client.currentScreen.width / 2 - this.client.textRenderer.getWidth(string) / 2), (float)(n + this.client.textRenderer.fontHeight), 0x808080);
        }
    }

    public static abstract class Entry
    extends AlwaysSelectedEntryListWidget.Entry<Entry> {
    }
}

