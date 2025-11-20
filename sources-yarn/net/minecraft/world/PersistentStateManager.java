package net.minecraft.world;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.Map;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtIo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersistentStateManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map<String, PersistentState> loadedStates = Maps.newHashMap();
   private final DataFixer dataFixer;
   private final File directory;

   public PersistentStateManager(File directory, DataFixer dataFixer) {
      this.dataFixer = dataFixer;
      this.directory = directory;
   }

   private File getFile(String id) {
      return new File(this.directory, id + ".dat");
   }

   public <T extends PersistentState> T getOrCreate(Supplier<T> factory, String id) {
      T lv = this.get(factory, id);
      if (lv != null) {
         return lv;
      } else {
         T lv2 = (T)factory.get();
         this.set(lv2);
         return lv2;
      }
   }

   @Nullable
   public <T extends PersistentState> T get(Supplier<T> factory, String id) {
      PersistentState lv = this.loadedStates.get(id);
      if (lv == null && !this.loadedStates.containsKey(id)) {
         lv = this.readFromFile(factory, id);
         this.loadedStates.put(id, lv);
      }

      return (T)lv;
   }

   @Nullable
   private <T extends PersistentState> T readFromFile(Supplier<T> factory, String id) {
      try {
         File file = this.getFile(id);
         if (file.exists()) {
            T lv = (T)factory.get();
            CompoundTag lv2 = this.readTag(id, SharedConstants.getGameVersion().getWorldVersion());
            lv.fromTag(lv2.getCompound("data"));
            return lv;
         }
      } catch (Exception var6) {
         LOGGER.error("Error loading saved data: {}", id, var6);
      }

      return null;
   }

   public void set(PersistentState state) {
      this.loadedStates.put(state.getId(), state);
   }

   public CompoundTag readTag(String id, int dataVersion) throws IOException {
      File file = this.getFile(id);

      CompoundTag var61;
      try (
         FileInputStream fileInputStream = new FileInputStream(file);
         PushbackInputStream pushbackInputStream = new PushbackInputStream(fileInputStream, 2);
      ) {
         CompoundTag lv;
         if (this.isCompressed(pushbackInputStream)) {
            lv = NbtIo.readCompressed(pushbackInputStream);
         } else {
            try (DataInputStream dataInputStream = new DataInputStream(pushbackInputStream)) {
               lv = NbtIo.read(dataInputStream);
            }
         }

         int j = lv.contains("DataVersion", 99) ? lv.getInt("DataVersion") : 1343;
         var61 = NbtHelper.update(this.dataFixer, DataFixTypes.SAVED_DATA, lv, j, dataVersion);
      }

      return var61;
   }

   private boolean isCompressed(PushbackInputStream pushbackInputStream) throws IOException {
      byte[] bs = new byte[2];
      boolean bl = false;
      int i = pushbackInputStream.read(bs, 0, 2);
      if (i == 2) {
         int j = (bs[1] & 255) << 8 | bs[0] & 255;
         if (j == 35615) {
            bl = true;
         }
      }

      if (i != 0) {
         pushbackInputStream.unread(bs, 0, i);
      }

      return bl;
   }

   public void save() {
      for (PersistentState lv : this.loadedStates.values()) {
         if (lv != null) {
            lv.save(this.getFile(lv.getId()));
         }
      }
   }
}
