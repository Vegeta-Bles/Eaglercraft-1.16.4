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

public class MultiplayerServerListWidget extends AlwaysSelectedEntryListWidget<MultiplayerServerListWidget.Entry> {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ThreadPoolExecutor SERVER_PINGER_THREAD_POOL = new ScheduledThreadPoolExecutor(
      5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER)).build()
   );
   private static final Identifier UNKNOWN_SERVER_TEXTURE = new Identifier("textures/misc/unknown_server.png");
   private static final Identifier SERVER_SELECTION_TEXTURE = new Identifier("textures/gui/server_selection.png");
   private static final Text field_26581 = new TranslatableText("lanServer.scanning");
   private static final Text field_26582 = new TranslatableText("multiplayer.status.cannot_resolve").formatted(Formatting.DARK_RED);
   private static final Text field_26583 = new TranslatableText("multiplayer.status.cannot_connect").formatted(Formatting.DARK_RED);
   private static final Text field_26849 = new TranslatableText("multiplayer.status.incompatible");
   private static final Text field_26586 = new TranslatableText("multiplayer.status.no_connection");
   private static final Text field_26587 = new TranslatableText("multiplayer.status.pinging");
   private final MultiplayerScreen screen;
   private final List<MultiplayerServerListWidget.ServerEntry> servers = Lists.newArrayList();
   private final MultiplayerServerListWidget.Entry scanningEntry = new MultiplayerServerListWidget.ScanningEntry();
   private final List<MultiplayerServerListWidget.LanServerEntry> lanServers = Lists.newArrayList();

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

   public void setSelected(@Nullable MultiplayerServerListWidget.Entry _snowman) {
      super.setSelected(_snowman);
      if (this.getSelected() instanceof MultiplayerServerListWidget.ServerEntry) {
         NarratorManager.INSTANCE
            .narrate(new TranslatableText("narrator.select", ((MultiplayerServerListWidget.ServerEntry)this.getSelected()).server.name).getString());
      }

      this.screen.updateButtonActivationStates();
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      MultiplayerServerListWidget.Entry _snowman = this.getSelected();
      return _snowman != null && _snowman.keyPressed(keyCode, scanCode, modifiers) || super.keyPressed(keyCode, scanCode, modifiers);
   }

   @Override
   protected void moveSelection(EntryListWidget.MoveDirection direction) {
      this.moveSelectionIf(direction, _snowman -> !(_snowman instanceof MultiplayerServerListWidget.ScanningEntry));
   }

   public void setServers(ServerList servers) {
      this.servers.clear();

      for (int _snowman = 0; _snowman < servers.size(); _snowman++) {
         this.servers.add(new MultiplayerServerListWidget.ServerEntry(this.screen, servers.get(_snowman)));
      }

      this.updateEntries();
   }

   public void setLanServers(List<LanServerInfo> lanServers) {
      this.lanServers.clear();

      for (LanServerInfo _snowman : lanServers) {
         this.lanServers.add(new MultiplayerServerListWidget.LanServerEntry(this.screen, _snowman));
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

   public abstract static class Entry extends AlwaysSelectedEntryListWidget.Entry<MultiplayerServerListWidget.Entry> {
      public Entry() {
      }
   }

   public static class LanServerEntry extends MultiplayerServerListWidget.Entry {
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
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         this.client.textRenderer.draw(matrices, field_26588, (float)(x + 32 + 3), (float)(y + 1), 16777215);
         this.client.textRenderer.draw(matrices, this.server.getMotd(), (float)(x + 32 + 3), (float)(y + 12), 8421504);
         if (this.client.options.hideServerAddress) {
            this.client.textRenderer.draw(matrices, field_26589, (float)(x + 32 + 3), (float)(y + 12 + 11), 3158064);
         } else {
            this.client.textRenderer.draw(matrices, this.server.getAddressPort(), (float)(x + 32 + 3), (float)(y + 12 + 11), 3158064);
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

   public static class ScanningEntry extends MultiplayerServerListWidget.Entry {
      private final MinecraftClient client = MinecraftClient.getInstance();

      public ScanningEntry() {
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         int _snowman = y + entryHeight / 2 - 9 / 2;
         this.client
            .textRenderer
            .draw(
               matrices,
               MultiplayerServerListWidget.field_26581,
               (float)(this.client.currentScreen.width / 2 - this.client.textRenderer.getWidth(MultiplayerServerListWidget.field_26581) / 2),
               (float)_snowman,
               16777215
            );
         String _snowmanx;
         switch ((int)(Util.getMeasuringTimeMs() / 300L % 4L)) {
            case 0:
            default:
               _snowmanx = "O o o";
               break;
            case 1:
            case 3:
               _snowmanx = "o O o";
               break;
            case 2:
               _snowmanx = "o o O";
         }

         this.client
            .textRenderer
            .draw(matrices, _snowmanx, (float)(this.client.currentScreen.width / 2 - this.client.textRenderer.getWidth(_snowmanx) / 2), (float)(_snowman + 9), 8421504);
      }
   }

   public class ServerEntry extends MultiplayerServerListWidget.Entry {
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
         this.iconTextureId = new Identifier("servers/" + Hashing.sha1().hashUnencodedChars(server.address) + "/icon");
         this.icon = (NativeImageBackedTexture)this.client.getTextureManager().getTexture(this.iconTextureId);
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         if (!this.server.online) {
            this.server.online = true;
            this.server.ping = -2L;
            this.server.label = LiteralText.EMPTY;
            this.server.playerCountLabel = LiteralText.EMPTY;
            MultiplayerServerListWidget.SERVER_PINGER_THREAD_POOL.submit(() -> {
               try {
                  this.screen.getServerListPinger().add(this.server, () -> this.client.execute(this::method_29978));
               } catch (UnknownHostException var2) {
                  this.server.ping = -1L;
                  this.server.label = MultiplayerServerListWidget.field_26582;
               } catch (Exception var3) {
                  this.server.ping = -1L;
                  this.server.label = MultiplayerServerListWidget.field_26583;
               }
            });
         }

         boolean _snowman = this.server.protocolVersion != SharedConstants.getGameVersion().getProtocolVersion();
         this.client.textRenderer.draw(matrices, this.server.name, (float)(x + 32 + 3), (float)(y + 1), 16777215);
         List<OrderedText> _snowmanx = this.client.textRenderer.wrapLines(this.server.label, entryWidth - 32 - 2);

         for (int _snowmanxx = 0; _snowmanxx < Math.min(_snowmanx.size(), 2); _snowmanxx++) {
            this.client.textRenderer.draw(matrices, _snowmanx.get(_snowmanxx), (float)(x + 32 + 3), (float)(y + 12 + 9 * _snowmanxx), 8421504);
         }

         Text _snowmanxx = (Text)(_snowman ? this.server.version.shallowCopy().formatted(Formatting.RED) : this.server.playerCountLabel);
         int _snowmanxxx = this.client.textRenderer.getWidth(_snowmanxx);
         this.client.textRenderer.draw(matrices, _snowmanxx, (float)(x + entryWidth - _snowmanxxx - 15 - 2), (float)(y + 1), 8421504);
         int _snowmanxxxx = 0;
         int _snowmanxxxxx;
         List<Text> _snowmanxxxxxx;
         Text _snowmanxxxxxxx;
         if (_snowman) {
            _snowmanxxxxx = 5;
            _snowmanxxxxxxx = MultiplayerServerListWidget.field_26849;
            _snowmanxxxxxx = this.server.playerListSummary;
         } else if (this.server.online && this.server.ping != -2L) {
            if (this.server.ping < 0L) {
               _snowmanxxxxx = 5;
            } else if (this.server.ping < 150L) {
               _snowmanxxxxx = 0;
            } else if (this.server.ping < 300L) {
               _snowmanxxxxx = 1;
            } else if (this.server.ping < 600L) {
               _snowmanxxxxx = 2;
            } else if (this.server.ping < 1000L) {
               _snowmanxxxxx = 3;
            } else {
               _snowmanxxxxx = 4;
            }

            if (this.server.ping < 0L) {
               _snowmanxxxxxxx = MultiplayerServerListWidget.field_26586;
               _snowmanxxxxxx = Collections.emptyList();
            } else {
               _snowmanxxxxxxx = new TranslatableText("multiplayer.status.ping", this.server.ping);
               _snowmanxxxxxx = this.server.playerListSummary;
            }
         } else {
            _snowmanxxxx = 1;
            _snowmanxxxxx = (int)(Util.getMeasuringTimeMs() / 100L + (long)(index * 2) & 7L);
            if (_snowmanxxxxx > 4) {
               _snowmanxxxxx = 8 - _snowmanxxxxx;
            }

            _snowmanxxxxxxx = MultiplayerServerListWidget.field_26587;
            _snowmanxxxxxx = Collections.emptyList();
         }

         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
         DrawableHelper.drawTexture(matrices, x + entryWidth - 15, y, (float)(_snowmanxxxx * 10), (float)(176 + _snowmanxxxxx * 8), 10, 8, 256, 256);
         String _snowmanxxxxxxxx = this.server.getIcon();
         if (!Objects.equals(_snowmanxxxxxxxx, this.iconUri)) {
            if (this.method_29979(_snowmanxxxxxxxx)) {
               this.iconUri = _snowmanxxxxxxxx;
            } else {
               this.server.setIcon(null);
               this.method_29978();
            }
         }

         if (this.icon != null) {
            this.draw(matrices, x, y, this.iconTextureId);
         } else {
            this.draw(matrices, x, y, MultiplayerServerListWidget.UNKNOWN_SERVER_TEXTURE);
         }

         int _snowmanxxxxxxxxx = mouseX - x;
         int _snowmanxxxxxxxxxx = mouseY - y;
         if (_snowmanxxxxxxxxx >= entryWidth - 15 && _snowmanxxxxxxxxx <= entryWidth - 5 && _snowmanxxxxxxxxxx >= 0 && _snowmanxxxxxxxxxx <= 8) {
            this.screen.setTooltip(Collections.singletonList(_snowmanxxxxxxx));
         } else if (_snowmanxxxxxxxxx >= entryWidth - _snowmanxxx - 15 - 2 && _snowmanxxxxxxxxx <= entryWidth - 15 - 2 && _snowmanxxxxxxxxxx >= 0 && _snowmanxxxxxxxxxx <= 8) {
            this.screen.setTooltip(_snowmanxxxxxx);
         }

         if (this.client.options.touchscreen || hovered) {
            this.client.getTextureManager().bindTexture(MultiplayerServerListWidget.SERVER_SELECTION_TEXTURE);
            DrawableHelper.fill(matrices, x, y, x + 32, y + 32, -1601138544);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int _snowmanxxxxxxxxxxx = mouseX - x;
            int _snowmanxxxxxxxxxxxx = mouseY - y;
            if (this.method_20136()) {
               if (_snowmanxxxxxxxxxxx < 32 && _snowmanxxxxxxxxxxx > 16) {
                  DrawableHelper.drawTexture(matrices, x, y, 0.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  DrawableHelper.drawTexture(matrices, x, y, 0.0F, 0.0F, 32, 32, 256, 256);
               }
            }

            if (index > 0) {
               if (_snowmanxxxxxxxxxxx < 16 && _snowmanxxxxxxxxxxxx < 16) {
                  DrawableHelper.drawTexture(matrices, x, y, 96.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  DrawableHelper.drawTexture(matrices, x, y, 96.0F, 0.0F, 32, 32, 256, 256);
               }
            }

            if (index < this.screen.getServerList().size() - 1) {
               if (_snowmanxxxxxxxxxxx < 16 && _snowmanxxxxxxxxxxxx > 16) {
                  DrawableHelper.drawTexture(matrices, x, y, 64.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  DrawableHelper.drawTexture(matrices, x, y, 64.0F, 0.0F, 32, 32, 256, 256);
               }
            }
         }
      }

      public void method_29978() {
         this.screen.getServerList().saveFile();
      }

      protected void draw(MatrixStack _snowman, int _snowman, int _snowman, Identifier _snowman) {
         this.client.getTextureManager().bindTexture(_snowman);
         RenderSystem.enableBlend();
         DrawableHelper.drawTexture(_snowman, _snowman, _snowman, 0.0F, 0.0F, 32, 32, 32, 32);
         RenderSystem.disableBlend();
      }

      private boolean method_20136() {
         return true;
      }

      private boolean method_29979(@Nullable String _snowman) {
         if (_snowman == null) {
            this.client.getTextureManager().destroyTexture(this.iconTextureId);
            if (this.icon != null && this.icon.getImage() != null) {
               this.icon.getImage().close();
            }

            this.icon = null;
         } else {
            try {
               NativeImage _snowmanx = NativeImage.read(_snowman);
               Validate.validState(_snowmanx.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
               Validate.validState(_snowmanx.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
               if (this.icon == null) {
                  this.icon = new NativeImageBackedTexture(_snowmanx);
               } else {
                  this.icon.setImage(_snowmanx);
                  this.icon.upload();
               }

               this.client.getTextureManager().registerTexture(this.iconTextureId, this.icon);
            } catch (Throwable var3) {
               MultiplayerServerListWidget.LOGGER.error("Invalid icon for server {} ({})", this.server.name, this.server.address, var3);
               return false;
            }
         }

         return true;
      }

      @Override
      public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
         if (Screen.hasShiftDown()) {
            MultiplayerServerListWidget _snowman = this.screen.serverListWidget;
            int _snowmanx = _snowman.children().indexOf(this);
            if (keyCode == 264 && _snowmanx < this.screen.getServerList().size() - 1 || keyCode == 265 && _snowmanx > 0) {
               this.swapEntries(_snowmanx, keyCode == 264 ? _snowmanx + 1 : _snowmanx - 1);
               return true;
            }
         }

         return super.keyPressed(keyCode, scanCode, modifiers);
      }

      private void swapEntries(int i, int j) {
         this.screen.getServerList().swapEntries(i, j);
         this.screen.serverListWidget.setServers(this.screen.getServerList());
         MultiplayerServerListWidget.Entry _snowman = this.screen.serverListWidget.children().get(j);
         this.screen.serverListWidget.setSelected(_snowman);
         MultiplayerServerListWidget.this.ensureVisible(_snowman);
      }

      @Override
      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         double _snowman = mouseX - (double)MultiplayerServerListWidget.this.getRowLeft();
         double _snowmanx = mouseY - (double)MultiplayerServerListWidget.this.getRowTop(MultiplayerServerListWidget.this.children().indexOf(this));
         if (_snowman <= 32.0) {
            if (_snowman < 32.0 && _snowman > 16.0 && this.method_20136()) {
               this.screen.select(this);
               this.screen.connect();
               return true;
            }

            int _snowmanxx = this.screen.serverListWidget.children().indexOf(this);
            if (_snowman < 16.0 && _snowmanx < 16.0 && _snowmanxx > 0) {
               this.swapEntries(_snowmanxx, _snowmanxx - 1);
               return true;
            }

            if (_snowman < 16.0 && _snowmanx > 16.0 && _snowmanxx < this.screen.getServerList().size() - 1) {
               this.swapEntries(_snowmanxx, _snowmanxx + 1);
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
}
