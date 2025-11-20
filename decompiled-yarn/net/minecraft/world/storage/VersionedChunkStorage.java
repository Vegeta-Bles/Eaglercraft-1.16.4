package net.minecraft.world.storage;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.FeatureUpdater;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class VersionedChunkStorage implements AutoCloseable {
   private final StorageIoWorker worker;
   protected final DataFixer dataFixer;
   @Nullable
   private FeatureUpdater featureUpdater;

   public VersionedChunkStorage(File _snowman, DataFixer _snowman, boolean _snowman) {
      this.dataFixer = _snowman;
      this.worker = new StorageIoWorker(_snowman, _snowman, "chunk");
   }

   public CompoundTag updateChunkTag(RegistryKey<World> _snowman, Supplier<PersistentStateManager> persistentStateManagerFactory, CompoundTag tag) {
      int _snowmanx = getDataVersion(tag);
      int _snowmanxx = 1493;
      if (_snowmanx < 1493) {
         tag = NbtHelper.update(this.dataFixer, DataFixTypes.CHUNK, tag, _snowmanx, 1493);
         if (tag.getCompound("Level").getBoolean("hasLegacyStructureData")) {
            if (this.featureUpdater == null) {
               this.featureUpdater = FeatureUpdater.create(_snowman, persistentStateManagerFactory.get());
            }

            tag = this.featureUpdater.getUpdatedReferences(tag);
         }
      }

      tag = NbtHelper.update(this.dataFixer, DataFixTypes.CHUNK, tag, Math.max(1493, _snowmanx));
      if (_snowmanx < SharedConstants.getGameVersion().getWorldVersion()) {
         tag.putInt("DataVersion", SharedConstants.getGameVersion().getWorldVersion());
      }

      return tag;
   }

   public static int getDataVersion(CompoundTag tag) {
      return tag.contains("DataVersion", 99) ? tag.getInt("DataVersion") : -1;
   }

   @Nullable
   public CompoundTag getNbt(ChunkPos _snowman) throws IOException {
      return this.worker.getNbt(_snowman);
   }

   public void setTagAt(ChunkPos _snowman, CompoundTag _snowman) {
      this.worker.setResult(_snowman, _snowman);
      if (this.featureUpdater != null) {
         this.featureUpdater.markResolved(_snowman.toLong());
      }
   }

   public void completeAll() {
      this.worker.completeAll().join();
   }

   @Override
   public void close() throws IOException {
      this.worker.close();
   }
}
