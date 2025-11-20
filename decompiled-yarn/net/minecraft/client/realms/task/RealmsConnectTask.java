package net.minecraft.client.realms.task;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.realms.RealmsConnection;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.dto.RealmsServerAddress;
import net.minecraft.text.TranslatableText;

public class RealmsConnectTask extends LongRunningTask {
   private final RealmsConnection realmsConnect;
   private final RealmsServer field_26922;
   private final RealmsServerAddress a;

   public RealmsConnectTask(Screen lastScreen, RealmsServer _snowman, RealmsServerAddress _snowman) {
      this.field_26922 = _snowman;
      this.a = _snowman;
      this.realmsConnect = new RealmsConnection(lastScreen);
   }

   @Override
   public void run() {
      this.setTitle(new TranslatableText("mco.connect.connecting"));
      net.minecraft.client.realms.RealmsServerAddress _snowman = net.minecraft.client.realms.RealmsServerAddress.parseString(this.a.address);
      this.realmsConnect.connect(this.field_26922, _snowman.getHost(), _snowman.getPort());
   }

   @Override
   public void abortTask() {
      this.realmsConnect.abort();
      MinecraftClient.getInstance().getResourcePackDownloader().clear();
   }

   @Override
   public void tick() {
      this.realmsConnect.tick();
   }
}
