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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
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

   public void setSelected(@Nullable MultiplayerServerListWidget.Entry arg) {
      super.setSelected(arg);
      if (this.getSelected() instanceof MultiplayerServerListWidget.ServerEntry) {
         NarratorManager.INSTANCE
            .narrate(new TranslatableText("narrator.select", ((MultiplayerServerListWidget.ServerEntry)this.getSelected()).server.name).getString());
      }

      this.screen.updateButtonActivationStates();
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      MultiplayerServerListWidget.Entry lv = this.getSelected();
      return lv != null && lv.keyPressed(keyCode, scanCode, modifiers) || super.keyPressed(keyCode, scanCode, modifiers);
   }

   @Override
   protected void moveSelection(EntryListWidget.MoveDirection direction) {
      this.moveSelectionIf(direction, arg -> !(arg instanceof MultiplayerServerListWidget.ScanningEntry));
   }

   public void setServers(ServerList servers) {
      this.servers.clear();

      for (int i = 0; i < servers.size(); i++) {
         this.servers.add(new MultiplayerServerListWidget.ServerEntry(this.screen, servers.get(i)));
      }

      this.updateEntries();
   }

   public void setLanServers(List<LanServerInfo> lanServers) {
      this.lanServers.clear();

      for (LanServerInfo lv : lanServers) {
         this.lanServers.add(new MultiplayerServerListWidget.LanServerEntry(this.screen, lv));
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

   @Environment(EnvType.CLIENT)
   public abstract static class Entry extends AlwaysSelectedEntryListWidget.Entry<MultiplayerServerListWidget.Entry> {
      public Entry() {
      }
   }

   @Environment(EnvType.CLIENT)
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

   @Environment(EnvType.CLIENT)
   public static class ScanningEntry extends MultiplayerServerListWidget.Entry {
      private final MinecraftClient client = MinecraftClient.getInstance();

      public ScanningEntry() {
      }

      @Override
      public void render(
         MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta
      ) {
         int p = y + entryHeight / 2 - 9 / 2;
         this.client
            .textRenderer
            .draw(
               matrices,
               MultiplayerServerListWidget.field_26581,
               (float)(this.client.currentScreen.width / 2 - this.client.textRenderer.getWidth(MultiplayerServerListWidget.field_26581) / 2),
               (float)p,
               16777215
            );
         String string;
         switch ((int)(Util.getMeasuringTimeMs() / 300L % 4L)) {
            case 0:
            default:
               string = "O o o";
               break;
            case 1:
            case 3:
               string = "o O o";
               break;
            case 2:
               string = "o o O";
         }

         this.client
            .textRenderer
            .draw(matrices, string, (float)(this.client.currentScreen.width / 2 - this.client.textRenderer.getWidth(string) / 2), (float)(p + 9), 8421504);
      }
   }

   @Environment(EnvType.CLIENT)
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
               } catch (UnknownHostException var2x) {
                  this.server.ping = -1L;
                  this.server.label = MultiplayerServerListWidget.field_26582;
               } catch (Exception var3x) {
                  this.server.ping = -1L;
                  this.server.label = MultiplayerServerListWidget.field_26583;
               }
            });
         }

         boolean bl2 = this.server.protocolVersion != SharedConstants.getGameVersion().getProtocolVersion();
         this.client.textRenderer.draw(matrices, this.server.name, (float)(x + 32 + 3), (float)(y + 1), 16777215);
         List<OrderedText> list = this.client.textRenderer.wrapLines(this.server.label, entryWidth - 32 - 2);

         for (int p = 0; p < Math.min(list.size(), 2); p++) {
            this.client.textRenderer.draw(matrices, list.get(p), (float)(x + 32 + 3), (float)(y + 12 + 9 * p), 8421504);
         }

         Text lv = (Text)(bl2 ? this.server.version.shallowCopy().formatted(Formatting.RED) : this.server.playerCountLabel);
         int q = this.client.textRenderer.getWidth(lv);
         this.client.textRenderer.draw(matrices, lv, (float)(x + entryWidth - q - 15 - 2), (float)(y + 1), 8421504);
         int r = 0;
         int s;
         List<Text> list2;
         Text lv2;
         if (bl2) {
            s = 5;
            lv2 = MultiplayerServerListWidget.field_26849;
            list2 = this.server.playerListSummary;
         } else if (this.server.online && this.server.ping != -2L) {
            if (this.server.ping < 0L) {
               s = 5;
            } else if (this.server.ping < 150L) {
               s = 0;
            } else if (this.server.ping < 300L) {
               s = 1;
            } else if (this.server.ping < 600L) {
               s = 2;
            } else if (this.server.ping < 1000L) {
               s = 3;
            } else {
               s = 4;
            }

            if (this.server.ping < 0L) {
               lv2 = MultiplayerServerListWidget.field_26586;
               list2 = Collections.emptyList();
            } else {
               lv2 = new TranslatableText("multiplayer.status.ping", this.server.ping);
               list2 = this.server.playerListSummary;
            }
         } else {
            r = 1;
            s = (int)(Util.getMeasuringTimeMs() / 100L + (long)(index * 2) & 7L);
            if (s > 4) {
               s = 8 - s;
            }

            lv2 = MultiplayerServerListWidget.field_26587;
            list2 = Collections.emptyList();
         }

         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
         DrawableHelper.drawTexture(matrices, x + entryWidth - 15, y, (float)(r * 10), (float)(176 + s * 8), 10, 8, 256, 256);
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
            this.draw(matrices, x, y, MultiplayerServerListWidget.UNKNOWN_SERVER_TEXTURE);
         }

         int aa = mouseX - x;
         int ab = mouseY - y;
         if (aa >= entryWidth - 15 && aa <= entryWidth - 5 && ab >= 0 && ab <= 8) {
            this.screen.setTooltip(Collections.singletonList(lv2));
         } else if (aa >= entryWidth - q - 15 - 2 && aa <= entryWidth - 15 - 2 && ab >= 0 && ab <= 8) {
            this.screen.setTooltip(list2);
         }

         if (this.client.options.touchscreen || hovered) {
            this.client.getTextureManager().bindTexture(MultiplayerServerListWidget.SERVER_SELECTION_TEXTURE);
            DrawableHelper.fill(matrices, x, y, x + 32, y + 32, -1601138544);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int ac = mouseX - x;
            int ad = mouseY - y;
            if (this.method_20136()) {
               if (ac < 32 && ac > 16) {
                  DrawableHelper.drawTexture(matrices, x, y, 0.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  DrawableHelper.drawTexture(matrices, x, y, 0.0F, 0.0F, 32, 32, 256, 256);
               }
            }

            if (index > 0) {
               if (ac < 16 && ad < 16) {
                  DrawableHelper.drawTexture(matrices, x, y, 96.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  DrawableHelper.drawTexture(matrices, x, y, 96.0F, 0.0F, 32, 32, 256, 256);
               }
            }

            if (index < this.screen.getServerList().size() - 1) {
               if (ac < 16 && ad > 16) {
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

      protected void draw(MatrixStack arg, int i, int j, Identifier arg2) {
         this.client.getTextureManager().bindTexture(arg2);
         RenderSystem.enableBlend();
         DrawableHelper.drawTexture(arg, i, j, 0.0F, 0.0F, 32, 32, 32, 32);
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
               NativeImage lv = NativeImage.read(string);
               Validate.validState(lv.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
               Validate.validState(lv.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
               if (this.icon == null) {
                  this.icon = new NativeImageBackedTexture(lv);
               } else {
                  this.icon.setImage(lv);
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
            MultiplayerServerListWidget lv = this.screen.serverListWidget;
            int l = lv.children().indexOf(this);
            if (keyCode == 264 && l < this.screen.getServerList().size() - 1 || keyCode == 265 && l > 0) {
               this.swapEntries(l, keyCode == 264 ? l + 1 : l - 1);
               return true;
            }
         }

         return super.keyPressed(keyCode, scanCode, modifiers);
      }

      private void swapEntries(int i, int j) {
         this.screen.getServerList().swapEntries(i, j);
         this.screen.serverListWidget.setServers(this.screen.getServerList());
         MultiplayerServerListWidget.Entry lv = this.screen.serverListWidget.children().get(j);
         this.screen.serverListWidget.setSelected(lv);
         MultiplayerServerListWidget.this.ensureVisible(lv);
      }

      @Override
      public boolean mouseClicked(double mouseX, double mouseY, int button) {
         double f = mouseX - (double)MultiplayerServerListWidget.this.getRowLeft();
         double g = mouseY - (double)MultiplayerServerListWidget.this.getRowTop(MultiplayerServerListWidget.this.children().indexOf(this));
         if (f <= 32.0) {
            if (f < 32.0 && f > 16.0 && this.method_20136()) {
               this.screen.select(this);
               this.screen.connect();
               return true;
            }

            int j = this.screen.serverListWidget.children().indexOf(this);
            if (f < 16.0 && g < 16.0 && j > 0) {
               this.swapEntries(j, j - 1);
               return true;
            }

            if (f < 16.0 && g > 16.0 && j < this.screen.getServerList().size() - 1) {
               this.swapEntries(j, j + 1);
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
