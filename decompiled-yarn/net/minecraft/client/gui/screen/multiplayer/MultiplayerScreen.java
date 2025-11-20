package net.minecraft.client.gui.screen.multiplayer;

import java.util.List;
import net.minecraft.client.gui.screen.AddServerScreen;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.DirectConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.LanServerInfo;
import net.minecraft.client.network.LanServerQueryManager;
import net.minecraft.client.network.MultiplayerServerListPinger;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.options.ServerList;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MultiplayerScreen extends Screen {
   private static final Logger LOGGER = LogManager.getLogger();
   private final MultiplayerServerListPinger serverListPinger = new MultiplayerServerListPinger();
   private final Screen parent;
   protected MultiplayerServerListWidget serverListWidget;
   private ServerList serverList;
   private ButtonWidget buttonEdit;
   private ButtonWidget buttonJoin;
   private ButtonWidget buttonDelete;
   private List<Text> tooltipText;
   private ServerInfo selectedEntry;
   private LanServerQueryManager.LanServerEntryList lanServers;
   private LanServerQueryManager.LanServerDetector lanServerDetector;
   private boolean initialized;

   public MultiplayerScreen(Screen parent) {
      super(new TranslatableText("multiplayer.title"));
      this.parent = parent;
   }

   @Override
   protected void init() {
      super.init();
      this.client.keyboard.setRepeatEvents(true);
      if (this.initialized) {
         this.serverListWidget.updateSize(this.width, this.height, 32, this.height - 64);
      } else {
         this.initialized = true;
         this.serverList = new ServerList(this.client);
         this.serverList.loadFile();
         this.lanServers = new LanServerQueryManager.LanServerEntryList();

         try {
            this.lanServerDetector = new LanServerQueryManager.LanServerDetector(this.lanServers);
            this.lanServerDetector.start();
         } catch (Exception var2) {
            LOGGER.warn("Unable to start LAN server detection: {}", var2.getMessage());
         }

         this.serverListWidget = new MultiplayerServerListWidget(this, this.client, this.width, this.height, 32, this.height - 64, 36);
         this.serverListWidget.setServers(this.serverList);
      }

      this.children.add(this.serverListWidget);
      this.buttonJoin = this.addButton(
         new ButtonWidget(this.width / 2 - 154, this.height - 52, 100, 20, new TranslatableText("selectServer.select"), _snowman -> this.connect())
      );
      this.addButton(new ButtonWidget(this.width / 2 - 50, this.height - 52, 100, 20, new TranslatableText("selectServer.direct"), _snowman -> {
         this.selectedEntry = new ServerInfo(I18n.translate("selectServer.defaultName"), "", false);
         this.client.openScreen(new DirectConnectScreen(this, this::directConnect, this.selectedEntry));
      }));
      this.addButton(new ButtonWidget(this.width / 2 + 4 + 50, this.height - 52, 100, 20, new TranslatableText("selectServer.add"), _snowman -> {
         this.selectedEntry = new ServerInfo(I18n.translate("selectServer.defaultName"), "", false);
         this.client.openScreen(new AddServerScreen(this, this::addEntry, this.selectedEntry));
      }));
      this.buttonEdit = this.addButton(new ButtonWidget(this.width / 2 - 154, this.height - 28, 70, 20, new TranslatableText("selectServer.edit"), _snowman -> {
         MultiplayerServerListWidget.Entry _snowmanx = this.serverListWidget.getSelected();
         if (_snowmanx instanceof MultiplayerServerListWidget.ServerEntry) {
            ServerInfo _snowmanxx = ((MultiplayerServerListWidget.ServerEntry)_snowmanx).getServer();
            this.selectedEntry = new ServerInfo(_snowmanxx.name, _snowmanxx.address, false);
            this.selectedEntry.copyFrom(_snowmanxx);
            this.client.openScreen(new AddServerScreen(this, this::editEntry, this.selectedEntry));
         }
      }));
      this.buttonDelete = this.addButton(new ButtonWidget(this.width / 2 - 74, this.height - 28, 70, 20, new TranslatableText("selectServer.delete"), _snowman -> {
         MultiplayerServerListWidget.Entry _snowmanx = this.serverListWidget.getSelected();
         if (_snowmanx instanceof MultiplayerServerListWidget.ServerEntry) {
            String _snowmanxx = ((MultiplayerServerListWidget.ServerEntry)_snowmanx).getServer().name;
            if (_snowmanxx != null) {
               Text _snowmanxxx = new TranslatableText("selectServer.deleteQuestion");
               Text _snowmanxxxx = new TranslatableText("selectServer.deleteWarning", _snowmanxx);
               Text _snowmanxxxxx = new TranslatableText("selectServer.deleteButton");
               Text _snowmanxxxxxx = ScreenTexts.CANCEL;
               this.client.openScreen(new ConfirmScreen(this::removeEntry, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx));
            }
         }
      }));
      this.addButton(new ButtonWidget(this.width / 2 + 4, this.height - 28, 70, 20, new TranslatableText("selectServer.refresh"), _snowman -> this.refresh()));
      this.addButton(new ButtonWidget(this.width / 2 + 4 + 76, this.height - 28, 75, 20, ScreenTexts.CANCEL, _snowman -> this.client.openScreen(this.parent)));
      this.updateButtonActivationStates();
   }

   @Override
   public void tick() {
      super.tick();
      if (this.lanServers.needsUpdate()) {
         List<LanServerInfo> _snowman = this.lanServers.getServers();
         this.lanServers.markClean();
         this.serverListWidget.setLanServers(_snowman);
      }

      this.serverListPinger.tick();
   }

   @Override
   public void removed() {
      this.client.keyboard.setRepeatEvents(false);
      if (this.lanServerDetector != null) {
         this.lanServerDetector.interrupt();
         this.lanServerDetector = null;
      }

      this.serverListPinger.cancel();
   }

   private void refresh() {
      this.client.openScreen(new MultiplayerScreen(this.parent));
   }

   private void removeEntry(boolean confirmedAction) {
      MultiplayerServerListWidget.Entry _snowman = this.serverListWidget.getSelected();
      if (confirmedAction && _snowman instanceof MultiplayerServerListWidget.ServerEntry) {
         this.serverList.remove(((MultiplayerServerListWidget.ServerEntry)_snowman).getServer());
         this.serverList.saveFile();
         this.serverListWidget.setSelected(null);
         this.serverListWidget.setServers(this.serverList);
      }

      this.client.openScreen(this);
   }

   private void editEntry(boolean confirmedAction) {
      MultiplayerServerListWidget.Entry _snowman = this.serverListWidget.getSelected();
      if (confirmedAction && _snowman instanceof MultiplayerServerListWidget.ServerEntry) {
         ServerInfo _snowmanx = ((MultiplayerServerListWidget.ServerEntry)_snowman).getServer();
         _snowmanx.name = this.selectedEntry.name;
         _snowmanx.address = this.selectedEntry.address;
         _snowmanx.copyFrom(this.selectedEntry);
         this.serverList.saveFile();
         this.serverListWidget.setServers(this.serverList);
      }

      this.client.openScreen(this);
   }

   private void addEntry(boolean confirmedAction) {
      if (confirmedAction) {
         this.serverList.add(this.selectedEntry);
         this.serverList.saveFile();
         this.serverListWidget.setSelected(null);
         this.serverListWidget.setServers(this.serverList);
      }

      this.client.openScreen(this);
   }

   private void directConnect(boolean confirmedAction) {
      if (confirmedAction) {
         this.connect(this.selectedEntry);
      } else {
         this.client.openScreen(this);
      }
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (super.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else if (keyCode == 294) {
         this.refresh();
         return true;
      } else if (this.serverListWidget.getSelected() != null) {
         if (keyCode != 257 && keyCode != 335) {
            return this.serverListWidget.keyPressed(keyCode, scanCode, modifiers);
         } else {
            this.connect();
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
      this.tooltipText = null;
      this.renderBackground(matrices);
      this.serverListWidget.render(matrices, mouseX, mouseY, delta);
      drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 16777215);
      super.render(matrices, mouseX, mouseY, delta);
      if (this.tooltipText != null) {
         this.renderTooltip(matrices, this.tooltipText, mouseX, mouseY);
      }
   }

   public void connect() {
      MultiplayerServerListWidget.Entry _snowman = this.serverListWidget.getSelected();
      if (_snowman instanceof MultiplayerServerListWidget.ServerEntry) {
         this.connect(((MultiplayerServerListWidget.ServerEntry)_snowman).getServer());
      } else if (_snowman instanceof MultiplayerServerListWidget.LanServerEntry) {
         LanServerInfo _snowmanx = ((MultiplayerServerListWidget.LanServerEntry)_snowman).getLanServerEntry();
         this.connect(new ServerInfo(_snowmanx.getMotd(), _snowmanx.getAddressPort(), true));
      }
   }

   private void connect(ServerInfo entry) {
      this.client.openScreen(new ConnectScreen(this, this.client, entry));
   }

   public void select(MultiplayerServerListWidget.Entry entry) {
      this.serverListWidget.setSelected(entry);
      this.updateButtonActivationStates();
   }

   protected void updateButtonActivationStates() {
      this.buttonJoin.active = false;
      this.buttonEdit.active = false;
      this.buttonDelete.active = false;
      MultiplayerServerListWidget.Entry _snowman = this.serverListWidget.getSelected();
      if (_snowman != null && !(_snowman instanceof MultiplayerServerListWidget.ScanningEntry)) {
         this.buttonJoin.active = true;
         if (_snowman instanceof MultiplayerServerListWidget.ServerEntry) {
            this.buttonEdit.active = true;
            this.buttonDelete.active = true;
         }
      }
   }

   public MultiplayerServerListPinger getServerListPinger() {
      return this.serverListPinger;
   }

   public void setTooltip(List<Text> _snowman) {
      this.tooltipText = _snowman;
   }

   public ServerList getServerList() {
      return this.serverList;
   }
}
