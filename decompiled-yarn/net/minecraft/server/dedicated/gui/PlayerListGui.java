package net.minecraft.server.dedicated.gui;

import java.util.Vector;
import javax.swing.JList;
import net.minecraft.server.MinecraftServer;

public class PlayerListGui extends JList<String> {
   private final MinecraftServer server;
   private int tick;

   public PlayerListGui(MinecraftServer server) {
      this.server = server;
      server.addServerGuiTickable(this::tick);
   }

   public void tick() {
      if (this.tick++ % 20 == 0) {
         Vector<String> _snowman = new Vector<>();

         for (int _snowmanx = 0; _snowmanx < this.server.getPlayerManager().getPlayerList().size(); _snowmanx++) {
            _snowman.add(this.server.getPlayerManager().getPlayerList().get(_snowmanx).getGameProfile().getName());
         }

         this.setListData(_snowman);
      }
   }
}
