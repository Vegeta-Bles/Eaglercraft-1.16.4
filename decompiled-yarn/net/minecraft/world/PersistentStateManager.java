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
      T _snowman = this.get(factory, id);
      if (_snowman != null) {
         return _snowman;
      } else {
         T _snowmanx = (T)factory.get();
         this.set(_snowmanx);
         return _snowmanx;
      }
   }

   @Nullable
   public <T extends PersistentState> T get(Supplier<T> factory, String id) {
      PersistentState _snowman = this.loadedStates.get(id);
      if (_snowman == null && !this.loadedStates.containsKey(id)) {
         _snowman = this.readFromFile(factory, id);
         this.loadedStates.put(id, _snowman);
      }

      return (T)_snowman;
   }

   @Nullable
   private <T extends PersistentState> T readFromFile(Supplier<T> factory, String id) {
      try {
         File _snowman = this.getFile(id);
         if (_snowman.exists()) {
            T _snowmanx = (T)factory.get();
            CompoundTag _snowmanxx = this.readTag(id, SharedConstants.getGameVersion().getWorldVersion());
            _snowmanx.fromTag(_snowmanxx.getCompound("data"));
            return _snowmanx;
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
      File _snowman = this.getFile(id);

      CompoundTag var61;
      try (
         FileInputStream _snowmanx = new FileInputStream(_snowman);
         PushbackInputStream _snowmanxx = new PushbackInputStream(_snowmanx, 2);
      ) {
         CompoundTag _snowmanxxx;
         if (this.isCompressed(_snowmanxx)) {
            _snowmanxxx = NbtIo.readCompressed(_snowmanxx);
         } else {
            try (DataInputStream _snowmanxxxx = new DataInputStream(_snowmanxx)) {
               _snowmanxxx = NbtIo.read(_snowmanxxxx);
            }
         }

         int _snowmanxxxx = _snowmanxxx.contains("DataVersion", 99) ? _snowmanxxx.getInt("DataVersion") : 1343;
         var61 = NbtHelper.update(this.dataFixer, DataFixTypes.SAVED_DATA, _snowmanxxx, _snowmanxxxx, dataVersion);
      }

      return var61;
   }

   private boolean isCompressed(PushbackInputStream _snowman) throws IOException {
      byte[] _snowmanx = new byte[2];
      boolean _snowmanxx = false;
      int _snowmanxxx = _snowman.read(_snowmanx, 0, 2);
      if (_snowmanxxx == 2) {
         int _snowmanxxxx = (_snowmanx[1] & 255) << 8 | _snowmanx[0] & 255;
         if (_snowmanxxxx == 35615) {
            _snowmanxx = true;
         }
      }

      if (_snowmanxxx != 0) {
         _snowman.unread(_snowmanx, 0, _snowmanxxx);
      }

      return _snowmanxx;
   }

   public void save() {
      for (PersistentState _snowman : this.loadedStates.values()) {
         if (_snowman != null) {
            _snowman.save(this.getFile(_snowman.getId()));
         }
      }
   }
}
