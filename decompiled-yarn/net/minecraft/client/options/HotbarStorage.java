package net.minecraft.client.options;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import net.minecraft.SharedConstants;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtIo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HotbarStorage {
   private static final Logger LOGGER = LogManager.getLogger();
   private final File file;
   private final DataFixer dataFixer;
   private final HotbarStorageEntry[] entries = new HotbarStorageEntry[9];
   private boolean loaded;

   public HotbarStorage(File _snowman, DataFixer _snowman) {
      this.file = new File(_snowman, "hotbar.nbt");
      this.dataFixer = _snowman;

      for (int _snowmanxx = 0; _snowmanxx < 9; _snowmanxx++) {
         this.entries[_snowmanxx] = new HotbarStorageEntry();
      }
   }

   private void load() {
      try {
         CompoundTag _snowman = NbtIo.read(this.file);
         if (_snowman == null) {
            return;
         }

         if (!_snowman.contains("DataVersion", 99)) {
            _snowman.putInt("DataVersion", 1343);
         }

         _snowman = NbtHelper.update(this.dataFixer, DataFixTypes.HOTBAR, _snowman, _snowman.getInt("DataVersion"));

         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.entries[_snowmanx].fromListTag(_snowman.getList(String.valueOf(_snowmanx), 10));
         }
      } catch (Exception var3) {
         LOGGER.error("Failed to load creative mode options", var3);
      }
   }

   public void save() {
      try {
         CompoundTag _snowman = new CompoundTag();
         _snowman.putInt("DataVersion", SharedConstants.getGameVersion().getWorldVersion());

         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            _snowman.put(String.valueOf(_snowmanx), this.getSavedHotbar(_snowmanx).toListTag());
         }

         NbtIo.write(_snowman, this.file);
      } catch (Exception var3) {
         LOGGER.error("Failed to save creative mode options", var3);
      }
   }

   public HotbarStorageEntry getSavedHotbar(int i) {
      if (!this.loaded) {
         this.load();
         this.loaded = true;
      }

      return this.entries[i];
   }
}
