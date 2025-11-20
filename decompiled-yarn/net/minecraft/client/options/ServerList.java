package net.minecraft.client.options;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerList {
   private static final Logger LOGGER = LogManager.getLogger();
   private final MinecraftClient client;
   private final List<ServerInfo> servers = Lists.newArrayList();

   public ServerList(MinecraftClient client) {
      this.client = client;
      this.loadFile();
   }

   public void loadFile() {
      try {
         this.servers.clear();
         CompoundTag _snowman = NbtIo.read(new File(this.client.runDirectory, "servers.dat"));
         if (_snowman == null) {
            return;
         }

         ListTag _snowmanx = _snowman.getList("servers", 10);

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
            this.servers.add(ServerInfo.deserialize(_snowmanx.getCompound(_snowmanxx)));
         }
      } catch (Exception var4) {
         LOGGER.error("Couldn't load server list", var4);
      }
   }

   public void saveFile() {
      try {
         ListTag _snowman = new ListTag();

         for (ServerInfo _snowmanx : this.servers) {
            _snowman.add(_snowmanx.serialize());
         }

         CompoundTag _snowmanx = new CompoundTag();
         _snowmanx.put("servers", _snowman);
         File _snowmanxx = File.createTempFile("servers", ".dat", this.client.runDirectory);
         NbtIo.write(_snowmanx, _snowmanxx);
         File _snowmanxxx = new File(this.client.runDirectory, "servers.dat_old");
         File _snowmanxxxx = new File(this.client.runDirectory, "servers.dat");
         Util.backupAndReplace(_snowmanxxxx, _snowmanxx, _snowmanxxx);
      } catch (Exception var6) {
         LOGGER.error("Couldn't save server list", var6);
      }
   }

   public ServerInfo get(int _snowman) {
      return this.servers.get(_snowman);
   }

   public void remove(ServerInfo _snowman) {
      this.servers.remove(_snowman);
   }

   public void add(ServerInfo _snowman) {
      this.servers.add(_snowman);
   }

   public int size() {
      return this.servers.size();
   }

   public void swapEntries(int index1, int _snowman) {
      ServerInfo _snowmanx = this.get(index1);
      this.servers.set(index1, this.get(_snowman));
      this.servers.set(_snowman, _snowmanx);
      this.saveFile();
   }

   public void set(int index, ServerInfo _snowman) {
      this.servers.set(index, _snowman);
   }

   public static void updateServerListEntry(ServerInfo e) {
      ServerList _snowman = new ServerList(MinecraftClient.getInstance());
      _snowman.loadFile();

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         ServerInfo _snowmanxx = _snowman.get(_snowmanx);
         if (_snowmanxx.name.equals(e.name) && _snowmanxx.address.equals(e.address)) {
            _snowman.set(_snowmanx, e);
            break;
         }
      }

      _snowman.saveFile();
   }
}
