package net.minecraft.world.storage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.OptionalDynamic;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SerializingRegionBasedStorage<R> implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private final StorageIoWorker worker;
   private final Long2ObjectMap<Optional<R>> loadedElements = new Long2ObjectOpenHashMap();
   private final LongLinkedOpenHashSet unsavedElements = new LongLinkedOpenHashSet();
   private final Function<Runnable, Codec<R>> codecFactory;
   private final Function<Runnable, R> factory;
   private final DataFixer dataFixer;
   private final DataFixTypes dataFixType;

   public SerializingRegionBasedStorage(
      File directory, Function<Runnable, Codec<R>> codecFactory, Function<Runnable, R> factory, DataFixer _snowman, DataFixTypes _snowman, boolean _snowman
   ) {
      this.codecFactory = codecFactory;
      this.factory = factory;
      this.dataFixer = _snowman;
      this.dataFixType = _snowman;
      this.worker = new StorageIoWorker(directory, _snowman, directory.getName());
   }

   protected void tick(BooleanSupplier shouldKeepTicking) {
      while (!this.unsavedElements.isEmpty() && shouldKeepTicking.getAsBoolean()) {
         ChunkPos _snowman = ChunkSectionPos.from(this.unsavedElements.firstLong()).toChunkPos();
         this.save(_snowman);
      }
   }

   @Nullable
   protected Optional<R> getIfLoaded(long pos) {
      return (Optional<R>)this.loadedElements.get(pos);
   }

   protected Optional<R> get(long pos) {
      ChunkSectionPos _snowman = ChunkSectionPos.from(pos);
      if (this.isPosInvalid(_snowman)) {
         return Optional.empty();
      } else {
         Optional<R> _snowmanx = this.getIfLoaded(pos);
         if (_snowmanx != null) {
            return _snowmanx;
         } else {
            this.loadDataAt(_snowman.toChunkPos());
            _snowmanx = this.getIfLoaded(pos);
            if (_snowmanx == null) {
               throw (IllegalStateException)Util.throwOrPause(new IllegalStateException());
            } else {
               return _snowmanx;
            }
         }
      }
   }

   protected boolean isPosInvalid(ChunkSectionPos pos) {
      return World.isOutOfBuildLimitVertically(ChunkSectionPos.getBlockCoord(pos.getSectionY()));
   }

   protected R getOrCreate(long pos) {
      Optional<R> _snowman = this.get(pos);
      if (_snowman.isPresent()) {
         return _snowman.get();
      } else {
         R _snowmanx = this.factory.apply(() -> this.onUpdate(pos));
         this.loadedElements.put(pos, Optional.of(_snowmanx));
         return _snowmanx;
      }
   }

   private void loadDataAt(ChunkPos _snowman) {
      this.update(_snowman, NbtOps.INSTANCE, this.loadNbt(_snowman));
   }

   @Nullable
   private CompoundTag loadNbt(ChunkPos pos) {
      try {
         return this.worker.getNbt(pos);
      } catch (IOException var3) {
         LOGGER.error("Error reading chunk {} data from disk", pos, var3);
         return null;
      }
   }

   private <T> void update(ChunkPos pos, DynamicOps<T> _snowman, @Nullable T data) {
      if (data == null) {
         for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
            this.loadedElements.put(ChunkSectionPos.from(pos, _snowmanx).asLong(), Optional.empty());
         }
      } else {
         Dynamic<T> _snowmanx = new Dynamic(_snowman, data);
         int _snowmanxx = getDataVersion(_snowmanx);
         int _snowmanxxx = SharedConstants.getGameVersion().getWorldVersion();
         boolean _snowmanxxxx = _snowmanxx != _snowmanxxx;
         Dynamic<T> _snowmanxxxxx = this.dataFixer.update(this.dataFixType.getTypeReference(), _snowmanx, _snowmanxx, _snowmanxxx);
         OptionalDynamic<T> _snowmanxxxxxx = _snowmanxxxxx.get("Sections");

         for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 16; _snowmanxxxxxxx++) {
            long _snowmanxxxxxxxx = ChunkSectionPos.from(pos, _snowmanxxxxxxx).asLong();
            Optional<R> _snowmanxxxxxxxxx = _snowmanxxxxxx.get(Integer.toString(_snowmanxxxxxxx))
               .result()
               .flatMap(_snowmanxxxxxxxxxx -> this.codecFactory.apply(() -> this.onUpdate(_snowman)).parse(_snowmanxxxxxxxxxx).resultOrPartial(LOGGER::error));
            this.loadedElements.put(_snowmanxxxxxxxx, _snowmanxxxxxxxxx);
            _snowmanxxxxxxxxx.ifPresent(_snowmanxxxxxxxxxx -> {
               this.onLoad(_snowman);
               if (_snowman) {
                  this.onUpdate(_snowman);
               }
            });
         }
      }
   }

   private void save(ChunkPos _snowman) {
      Dynamic<Tag> _snowmanx = this.method_20367(_snowman, NbtOps.INSTANCE);
      Tag _snowmanxx = (Tag)_snowmanx.getValue();
      if (_snowmanxx instanceof CompoundTag) {
         this.worker.setResult(_snowman, (CompoundTag)_snowmanxx);
      } else {
         LOGGER.error("Expected compound tag, got {}", _snowmanxx);
      }
   }

   private <T> Dynamic<T> method_20367(ChunkPos _snowman, DynamicOps<T> _snowman) {
      Map<T, T> _snowmanxx = Maps.newHashMap();

      for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
         long _snowmanxxxx = ChunkSectionPos.from(_snowman, _snowmanxxx).asLong();
         this.unsavedElements.remove(_snowmanxxxx);
         Optional<R> _snowmanxxxxx = (Optional<R>)this.loadedElements.get(_snowmanxxxx);
         if (_snowmanxxxxx != null && _snowmanxxxxx.isPresent()) {
            DataResult<T> _snowmanxxxxxx = this.codecFactory.apply(() -> this.onUpdate(_snowman)).encodeStart(_snowman, _snowmanxxxxx.get());
            String _snowmanxxxxxxx = Integer.toString(_snowmanxxx);
            _snowmanxxxxxx.resultOrPartial(LOGGER::error).ifPresent(_snowmanxxxxxxxx -> _snowman.put((T)_snowman.createString(_snowman), (T)_snowmanxxxxxxxx));
         }
      }

      return new Dynamic(
         _snowman,
         _snowman.createMap(
            ImmutableMap.of(
               _snowman.createString("Sections"), _snowman.createMap(_snowmanxx), _snowman.createString("DataVersion"), _snowman.createInt(SharedConstants.getGameVersion().getWorldVersion())
            )
         )
      );
   }

   protected void onLoad(long pos) {
   }

   protected void onUpdate(long pos) {
      Optional<R> _snowman = (Optional<R>)this.loadedElements.get(pos);
      if (_snowman != null && _snowman.isPresent()) {
         this.unsavedElements.add(pos);
      } else {
         LOGGER.warn("No data for position: {}", ChunkSectionPos.from(pos));
      }
   }

   private static int getDataVersion(Dynamic<?> _snowman) {
      return _snowman.get("DataVersion").asInt(1945);
   }

   public void method_20436(ChunkPos _snowman) {
      if (!this.unsavedElements.isEmpty()) {
         for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
            long _snowmanxx = ChunkSectionPos.from(_snowman, _snowmanx).asLong();
            if (this.unsavedElements.contains(_snowmanxx)) {
               this.save(_snowman);
               return;
            }
         }
      }
   }

   @Override
   public void close() throws IOException {
      this.worker.close();
   }
}
