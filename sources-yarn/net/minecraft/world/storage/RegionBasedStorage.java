package net.minecraft.world.storage;

import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.ThrowableDeliverer;
import net.minecraft.util.math.ChunkPos;

public final class RegionBasedStorage implements AutoCloseable {
   private final Long2ObjectLinkedOpenHashMap<RegionFile> cachedRegionFiles = new Long2ObjectLinkedOpenHashMap();
   private final File directory;
   private final boolean dsync;

   RegionBasedStorage(File directory, boolean dsync) {
      this.directory = directory;
      this.dsync = dsync;
   }

   private RegionFile getRegionFile(ChunkPos pos) throws IOException {
      long l = ChunkPos.toLong(pos.getRegionX(), pos.getRegionZ());
      RegionFile lv = (RegionFile)this.cachedRegionFiles.getAndMoveToFirst(l);
      if (lv != null) {
         return lv;
      } else {
         if (this.cachedRegionFiles.size() >= 256) {
            ((RegionFile)this.cachedRegionFiles.removeLast()).close();
         }

         if (!this.directory.exists()) {
            this.directory.mkdirs();
         }

         File file = new File(this.directory, "r." + pos.getRegionX() + "." + pos.getRegionZ() + ".mca");
         RegionFile lv2 = new RegionFile(file, this.directory, this.dsync);
         this.cachedRegionFiles.putAndMoveToFirst(l, lv2);
         return lv2;
      }
   }

   @Nullable
   public CompoundTag getTagAt(ChunkPos pos) throws IOException {
      RegionFile lv = this.getRegionFile(pos);

      Object var5;
      try (DataInputStream dataInputStream = lv.getChunkInputStream(pos)) {
         if (dataInputStream != null) {
            return NbtIo.read(dataInputStream);
         }

         var5 = null;
      }

      return (CompoundTag)var5;
   }

   protected void write(ChunkPos pos, CompoundTag tag) throws IOException {
      RegionFile lv = this.getRegionFile(pos);

      try (DataOutputStream dataOutputStream = lv.getChunkOutputStream(pos)) {
         NbtIo.write(tag, dataOutputStream);
      }
   }

   @Override
   public void close() throws IOException {
      ThrowableDeliverer<IOException> lv = new ThrowableDeliverer<>();
      ObjectIterator var2 = this.cachedRegionFiles.values().iterator();

      while (var2.hasNext()) {
         RegionFile lv2 = (RegionFile)var2.next();

         try {
            lv2.close();
         } catch (IOException var5) {
            lv.add(var5);
         }
      }

      lv.deliver();
   }

   public void method_26982() throws IOException {
      ObjectIterator var1 = this.cachedRegionFiles.values().iterator();

      while (var1.hasNext()) {
         RegionFile lv = (RegionFile)var1.next();
         lv.method_26981();
      }
   }
}
