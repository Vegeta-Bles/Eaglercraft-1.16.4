package net.minecraft.entity.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataTracker {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Map<Class<? extends Entity>, Integer> TRACKED_ENTITIES = Maps.newHashMap();
   private final Entity trackedEntity;
   private final Map<Integer, DataTracker.Entry<?>> entries = Maps.newHashMap();
   private final ReadWriteLock lock = new ReentrantReadWriteLock();
   private boolean empty = true;
   private boolean dirty;

   public DataTracker(Entity trackedEntity) {
      this.trackedEntity = trackedEntity;
   }

   public static <T> TrackedData<T> registerData(Class<? extends Entity> entityClass, TrackedDataHandler<T> dataHandler) {
      if (LOGGER.isDebugEnabled()) {
         try {
            Class<?> _snowman = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            if (!_snowman.equals(entityClass)) {
               LOGGER.debug("defineId called for: {} from {}", entityClass, _snowman, new RuntimeException());
            }
         } catch (ClassNotFoundException var5) {
         }
      }

      int _snowman;
      if (TRACKED_ENTITIES.containsKey(entityClass)) {
         _snowman = TRACKED_ENTITIES.get(entityClass) + 1;
      } else {
         int _snowmanx = 0;
         Class<?> _snowmanxx = entityClass;

         while (_snowmanxx != Entity.class) {
            _snowmanxx = _snowmanxx.getSuperclass();
            if (TRACKED_ENTITIES.containsKey(_snowmanxx)) {
               _snowmanx = TRACKED_ENTITIES.get(_snowmanxx) + 1;
               break;
            }
         }

         _snowman = _snowmanx;
      }

      if (_snowman > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + _snowman + "! (Max is " + 254 + ")");
      } else {
         TRACKED_ENTITIES.put(entityClass, _snowman);
         return dataHandler.create(_snowman);
      }
   }

   public <T> void startTracking(TrackedData<T> key, T initialValue) {
      int _snowman = key.getId();
      if (_snowman > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + _snowman + "! (Max is " + 254 + ")");
      } else if (this.entries.containsKey(_snowman)) {
         throw new IllegalArgumentException("Duplicate id value for " + _snowman + "!");
      } else if (TrackedDataHandlerRegistry.getId(key.getType()) < 0) {
         throw new IllegalArgumentException("Unregistered serializer " + key.getType() + " for " + _snowman + "!");
      } else {
         this.addTrackedData(key, initialValue);
      }
   }

   private <T> void addTrackedData(TrackedData<T> _snowman, T _snowman) {
      DataTracker.Entry<T> _snowmanxx = new DataTracker.Entry<>(_snowman, _snowman);
      this.lock.writeLock().lock();
      this.entries.put(_snowman.getId(), _snowmanxx);
      this.empty = false;
      this.lock.writeLock().unlock();
   }

   private <T> DataTracker.Entry<T> getEntry(TrackedData<T> _snowman) {
      this.lock.readLock().lock();

      DataTracker.Entry<T> _snowmanx;
      try {
         _snowmanx = (DataTracker.Entry<T>)this.entries.get(_snowman.getId());
      } catch (Throwable var9) {
         CrashReport _snowmanxx = CrashReport.create(var9, "Getting synched entity data");
         CrashReportSection _snowmanxxx = _snowmanxx.addElement("Synched entity data");
         _snowmanxxx.add("Data ID", _snowman);
         throw new CrashException(_snowmanxx);
      } finally {
         this.lock.readLock().unlock();
      }

      return _snowmanx;
   }

   public <T> T get(TrackedData<T> _snowman) {
      return this.getEntry(_snowman).get();
   }

   public <T> void set(TrackedData<T> key, T _snowman) {
      DataTracker.Entry<T> _snowmanx = this.getEntry(key);
      if (ObjectUtils.notEqual(_snowman, _snowmanx.get())) {
         _snowmanx.set(_snowman);
         this.trackedEntity.onTrackedDataSet(key);
         _snowmanx.setDirty(true);
         this.dirty = true;
      }
   }

   public boolean isDirty() {
      return this.dirty;
   }

   public static void entriesToPacket(List<DataTracker.Entry<?>> _snowman, PacketByteBuf _snowman) throws IOException {
      if (_snowman != null) {
         int _snowmanxx = 0;

         for (int _snowmanxxx = _snowman.size(); _snowmanxx < _snowmanxxx; _snowmanxx++) {
            writeEntryToPacket(_snowman, _snowman.get(_snowmanxx));
         }
      }

      _snowman.writeByte(255);
   }

   @Nullable
   public List<DataTracker.Entry<?>> getDirtyEntries() {
      List<DataTracker.Entry<?>> _snowman = null;
      if (this.dirty) {
         this.lock.readLock().lock();

         for (DataTracker.Entry<?> _snowmanx : this.entries.values()) {
            if (_snowmanx.isDirty()) {
               _snowmanx.setDirty(false);
               if (_snowman == null) {
                  _snowman = Lists.newArrayList();
               }

               _snowman.add(_snowmanx.copy());
            }
         }

         this.lock.readLock().unlock();
      }

      this.dirty = false;
      return _snowman;
   }

   @Nullable
   public List<DataTracker.Entry<?>> getAllEntries() {
      List<DataTracker.Entry<?>> _snowman = null;
      this.lock.readLock().lock();

      for (DataTracker.Entry<?> _snowmanx : this.entries.values()) {
         if (_snowman == null) {
            _snowman = Lists.newArrayList();
         }

         _snowman.add(_snowmanx.copy());
      }

      this.lock.readLock().unlock();
      return _snowman;
   }

   private static <T> void writeEntryToPacket(PacketByteBuf _snowman, DataTracker.Entry<T> _snowman) throws IOException {
      TrackedData<T> _snowmanxx = _snowman.getData();
      int _snowmanxxx = TrackedDataHandlerRegistry.getId(_snowmanxx.getType());
      if (_snowmanxxx < 0) {
         throw new EncoderException("Unknown serializer type " + _snowmanxx.getType());
      } else {
         _snowman.writeByte(_snowmanxx.getId());
         _snowman.writeVarInt(_snowmanxxx);
         _snowmanxx.getType().write(_snowman, _snowman.get());
      }
   }

   @Nullable
   public static List<DataTracker.Entry<?>> deserializePacket(PacketByteBuf _snowman) throws IOException {
      List<DataTracker.Entry<?>> _snowmanx = null;

      int _snowmanxx;
      while ((_snowmanxx = _snowman.readUnsignedByte()) != 255) {
         if (_snowmanx == null) {
            _snowmanx = Lists.newArrayList();
         }

         int _snowmanxxx = _snowman.readVarInt();
         TrackedDataHandler<?> _snowmanxxxx = TrackedDataHandlerRegistry.get(_snowmanxxx);
         if (_snowmanxxxx == null) {
            throw new DecoderException("Unknown serializer type " + _snowmanxxx);
         }

         _snowmanx.add(entryFromPacket(_snowman, _snowmanxx, _snowmanxxxx));
      }

      return _snowmanx;
   }

   private static <T> DataTracker.Entry<T> entryFromPacket(PacketByteBuf _snowman, int _snowman, TrackedDataHandler<T> _snowman) {
      return new DataTracker.Entry<>(_snowman.create(_snowman), _snowman.read(_snowman));
   }

   public void writeUpdatedEntries(List<DataTracker.Entry<?>> _snowman) {
      this.lock.writeLock().lock();

      for (DataTracker.Entry<?> _snowmanx : _snowman) {
         DataTracker.Entry<?> _snowmanxx = this.entries.get(_snowmanx.getData().getId());
         if (_snowmanxx != null) {
            this.copyToFrom(_snowmanxx, _snowmanx);
            this.trackedEntity.onTrackedDataSet(_snowmanx.getData());
         }
      }

      this.lock.writeLock().unlock();
      this.dirty = true;
   }

   private <T> void copyToFrom(DataTracker.Entry<T> _snowman, DataTracker.Entry<?> _snowman) {
      if (!Objects.equals(_snowman.data.getType(), _snowman.data.getType())) {
         throw new IllegalStateException(
            String.format(
               "Invalid entity data item type for field %d on entity %s: old=%s(%s), new=%s(%s)",
               _snowman.data.getId(),
               this.trackedEntity,
               _snowman.value,
               _snowman.value.getClass(),
               _snowman.value,
               _snowman.value.getClass()
            )
         );
      } else {
         _snowman.set((T)_snowman.get());
      }
   }

   public boolean isEmpty() {
      return this.empty;
   }

   public void clearDirty() {
      this.dirty = false;
      this.lock.readLock().lock();

      for (DataTracker.Entry<?> _snowman : this.entries.values()) {
         _snowman.setDirty(false);
      }

      this.lock.readLock().unlock();
   }

   public static class Entry<T> {
      private final TrackedData<T> data;
      private T value;
      private boolean dirty;

      public Entry(TrackedData<T> data, T value) {
         this.data = data;
         this.value = value;
         this.dirty = true;
      }

      public TrackedData<T> getData() {
         return this.data;
      }

      public void set(T value) {
         this.value = value;
      }

      public T get() {
         return this.value;
      }

      public boolean isDirty() {
         return this.dirty;
      }

      public void setDirty(boolean dirty) {
         this.dirty = dirty;
      }

      public DataTracker.Entry<T> copy() {
         return new DataTracker.Entry<>(this.data, this.data.getType().copy(this.value));
      }
   }
}
