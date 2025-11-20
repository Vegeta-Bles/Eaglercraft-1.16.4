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
      long _snowman = ChunkPos.toLong(pos.getRegionX(), pos.getRegionZ());
      RegionFile _snowmanx = (RegionFile)this.cachedRegionFiles.getAndMoveToFirst(_snowman);
      if (_snowmanx != null) {
         return _snowmanx;
      } else {
         if (this.cachedRegionFiles.size() >= 256) {
            ((RegionFile)this.cachedRegionFiles.removeLast()).close();
         }

         if (!this.directory.exists()) {
            this.directory.mkdirs();
         }

         File _snowmanxx = new File(this.directory, "r." + pos.getRegionX() + "." + pos.getRegionZ() + ".mca");
         RegionFile _snowmanxxx = new RegionFile(_snowmanxx, this.directory, this.dsync);
         this.cachedRegionFiles.putAndMoveToFirst(_snowman, _snowmanxxx);
         return _snowmanxxx;
      }
   }

   @Nullable
   public CompoundTag getTagAt(ChunkPos pos) throws IOException {
      RegionFile _snowman = this.getRegionFile(pos);

      Object var5;
      try (DataInputStream _snowmanx = _snowman.getChunkInputStream(pos)) {
         if (_snowmanx != null) {
            return NbtIo.read(_snowmanx);
         }

         var5 = null;
      }

      return (CompoundTag)var5;
   }

   protected void write(ChunkPos pos, CompoundTag tag) throws IOException {
      RegionFile _snowman = this.getRegionFile(pos);

      try (DataOutputStream _snowmanx = _snowman.getChunkOutputStream(pos)) {
         NbtIo.write(tag, _snowmanx);
      }
   }

   @Override
   public void close() throws IOException {
      ThrowableDeliverer<IOException> _snowman = new ThrowableDeliverer<>();
      ObjectIterator var2 = this.cachedRegionFiles.values().iterator();

      while (var2.hasNext()) {
         RegionFile _snowmanx = (RegionFile)var2.next();

         try {
            _snowmanx.close();
         } catch (IOException var5) {
            _snowman.add(var5);
         }
      }

      _snowman.deliver();
   }

   public void method_26982() throws IOException {
      ObjectIterator var1 = this.cachedRegionFiles.values().iterator();

      while (var1.hasNext()) {
         RegionFile _snowman = (RegionFile)var1.next();
         _snowman.method_26981();
      }
   }
}
