package net.minecraft.world;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import javax.annotation.Nullable;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.Util;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldSaveHandler {
   private static final Logger LOGGER = LogManager.getLogger();
   private final File playerDataDir;
   protected final DataFixer dataFixer;

   public WorldSaveHandler(LevelStorage.Session _snowman, DataFixer _snowman) {
      this.dataFixer = _snowman;
      this.playerDataDir = _snowman.getDirectory(WorldSavePath.PLAYERDATA).toFile();
      this.playerDataDir.mkdirs();
   }

   public void savePlayerData(PlayerEntity _snowman) {
      try {
         CompoundTag _snowmanx = _snowman.toTag(new CompoundTag());
         File _snowmanxx = File.createTempFile(_snowman.getUuidAsString() + "-", ".dat", this.playerDataDir);
         NbtIo.writeCompressed(_snowmanx, _snowmanxx);
         File _snowmanxxx = new File(this.playerDataDir, _snowman.getUuidAsString() + ".dat");
         File _snowmanxxxx = new File(this.playerDataDir, _snowman.getUuidAsString() + ".dat_old");
         Util.backupAndReplace(_snowmanxxx, _snowmanxx, _snowmanxxxx);
      } catch (Exception var6) {
         LOGGER.warn("Failed to save player data for {}", _snowman.getName().getString());
      }
   }

   @Nullable
   public CompoundTag loadPlayerData(PlayerEntity _snowman) {
      CompoundTag _snowmanx = null;

      try {
         File _snowmanxx = new File(this.playerDataDir, _snowman.getUuidAsString() + ".dat");
         if (_snowmanxx.exists() && _snowmanxx.isFile()) {
            _snowmanx = NbtIo.readCompressed(_snowmanxx);
         }
      } catch (Exception var4) {
         LOGGER.warn("Failed to load player data for {}", _snowman.getName().getString());
      }

      if (_snowmanx != null) {
         int _snowmanxx = _snowmanx.contains("DataVersion", 3) ? _snowmanx.getInt("DataVersion") : -1;
         _snowman.fromTag(NbtHelper.update(this.dataFixer, DataFixTypes.PLAYER, _snowmanx, _snowmanxx));
      }

      return _snowmanx;
   }

   public String[] getSavedPlayerIds() {
      String[] _snowman = this.playerDataDir.list();
      if (_snowman == null) {
         _snowman = new String[0];
      }

      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         if (_snowman[_snowmanx].endsWith(".dat")) {
            _snowman[_snowmanx] = _snowman[_snowmanx].substring(0, _snowman[_snowmanx].length() - 4);
         }
      }

      return _snowman;
   }
}
