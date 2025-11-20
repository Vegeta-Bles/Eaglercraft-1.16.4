package net.minecraft.client.realms.task;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.realms.RealmsConnection;
import net.minecraft.client.realms.dto.RealmsServer;
import net.minecraft.client.realms.dto.RealmsServerAddress;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class RealmsConnectTask extends LongRunningTask {
   private final RealmsConnection realmsConnect;
   private final RealmsServer field_26922;
   private final RealmsServerAddress a;

   public RealmsConnectTask(Screen lastScreen, RealmsServer arg2, RealmsServerAddress arg3) {
      this.field_26922 = arg2;
      this.a = arg3;
      this.realmsConnect = new RealmsConnection(lastScreen);
   }

   @Override
   public void run() {
      this.setTitle(new TranslatableText("mco.connect.connecting"));
      net.minecraft.client.realms.RealmsServerAddress lv = net.minecraft.client.realms.RealmsServerAddress.parseString(this.a.address);
      this.realmsConnect.connect(this.field_26922, lv.getHost(), lv.getPort());
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
