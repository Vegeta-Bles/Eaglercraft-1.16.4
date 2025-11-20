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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
            Class<?> class2 = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
            if (!class2.equals(entityClass)) {
               LOGGER.debug("defineId called for: {} from {}", entityClass, class2, new RuntimeException());
            }
         } catch (ClassNotFoundException var5) {
         }
      }

      int i;
      if (TRACKED_ENTITIES.containsKey(entityClass)) {
         i = TRACKED_ENTITIES.get(entityClass) + 1;
      } else {
         int j = 0;
         Class<?> class3 = entityClass;

         while (class3 != Entity.class) {
            class3 = class3.getSuperclass();
            if (TRACKED_ENTITIES.containsKey(class3)) {
               j = TRACKED_ENTITIES.get(class3) + 1;
               break;
            }
         }

         i = j;
      }

      if (i > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is " + 254 + ")");
      } else {
         TRACKED_ENTITIES.put(entityClass, i);
         return dataHandler.create(i);
      }
   }

   public <T> void startTracking(TrackedData<T> key, T initialValue) {
      int i = key.getId();
      if (i > 254) {
         throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is " + 254 + ")");
      } else if (this.entries.containsKey(i)) {
         throw new IllegalArgumentException("Duplicate id value for " + i + "!");
      } else if (TrackedDataHandlerRegistry.getId(key.getType()) < 0) {
         throw new IllegalArgumentException("Unregistered serializer " + key.getType() + " for " + i + "!");
      } else {
         this.addTrackedData(key, initialValue);
      }
   }

   private <T> void addTrackedData(TrackedData<T> arg, T object) {
      DataTracker.Entry<T> lv = new DataTracker.Entry<>(arg, object);
      this.lock.writeLock().lock();
      this.entries.put(arg.getId(), lv);
      this.empty = false;
      this.lock.writeLock().unlock();
   }

   private <T> DataTracker.Entry<T> getEntry(TrackedData<T> arg) {
      this.lock.readLock().lock();

      DataTracker.Entry<T> lv;
      try {
         lv = (DataTracker.Entry<T>)this.entries.get(arg.getId());
      } catch (Throwable var9) {
         CrashReport lv2 = CrashReport.create(var9, "Getting synched entity data");
         CrashReportSection lv3 = lv2.addElement("Synched entity data");
         lv3.add("Data ID", arg);
         throw new CrashException(lv2);
      } finally {
         this.lock.readLock().unlock();
      }

      return lv;
   }

   public <T> T get(TrackedData<T> arg) {
      return this.getEntry(arg).get();
   }

   public <T> void set(TrackedData<T> key, T object) {
      DataTracker.Entry<T> lv = this.getEntry(key);
      if (ObjectUtils.notEqual(object, lv.get())) {
         lv.set(object);
         this.trackedEntity.onTrackedDataSet(key);
         lv.setDirty(true);
         this.dirty = true;
      }
   }

   public boolean isDirty() {
      return this.dirty;
   }

   public static void entriesToPacket(List<DataTracker.Entry<?>> list, PacketByteBuf arg) throws IOException {
      if (list != null) {
         int i = 0;

         for (int j = list.size(); i < j; i++) {
            writeEntryToPacket(arg, list.get(i));
         }
      }

      arg.writeByte(255);
   }

   @Nullable
   public List<DataTracker.Entry<?>> getDirtyEntries() {
      List<DataTracker.Entry<?>> list = null;
      if (this.dirty) {
         this.lock.readLock().lock();

         for (DataTracker.Entry<?> lv : this.entries.values()) {
            if (lv.isDirty()) {
               lv.setDirty(false);
               if (list == null) {
                  list = Lists.newArrayList();
               }

               list.add(lv.copy());
            }
         }

         this.lock.readLock().unlock();
      }

      this.dirty = false;
      return list;
   }

   @Nullable
   public List<DataTracker.Entry<?>> getAllEntries() {
      List<DataTracker.Entry<?>> list = null;
      this.lock.readLock().lock();

      for (DataTracker.Entry<?> lv : this.entries.values()) {
         if (list == null) {
            list = Lists.newArrayList();
         }

         list.add(lv.copy());
      }

      this.lock.readLock().unlock();
      return list;
   }

   private static <T> void writeEntryToPacket(PacketByteBuf arg, DataTracker.Entry<T> arg2) throws IOException {
      TrackedData<T> lv = arg2.getData();
      int i = TrackedDataHandlerRegistry.getId(lv.getType());
      if (i < 0) {
         throw new EncoderException("Unknown serializer type " + lv.getType());
      } else {
         arg.writeByte(lv.getId());
         arg.writeVarInt(i);
         lv.getType().write(arg, arg2.get());
      }
   }

   @Nullable
   public static List<DataTracker.Entry<?>> deserializePacket(PacketByteBuf arg) throws IOException {
      List<DataTracker.Entry<?>> list = null;

      int i;
      while ((i = arg.readUnsignedByte()) != 255) {
         if (list == null) {
            list = Lists.newArrayList();
         }

         int j = arg.readVarInt();
         TrackedDataHandler<?> lv = TrackedDataHandlerRegistry.get(j);
         if (lv == null) {
            throw new DecoderException("Unknown serializer type " + j);
         }

         list.add(entryFromPacket(arg, i, lv));
      }

      return list;
   }

   private static <T> DataTracker.Entry<T> entryFromPacket(PacketByteBuf arg, int i, TrackedDataHandler<T> arg2) {
      return new DataTracker.Entry<>(arg2.create(i), arg2.read(arg));
   }

   @Environment(EnvType.CLIENT)
   public void writeUpdatedEntries(List<DataTracker.Entry<?>> list) {
      this.lock.writeLock().lock();

      for (DataTracker.Entry<?> lv : list) {
         DataTracker.Entry<?> lv2 = this.entries.get(lv.getData().getId());
         if (lv2 != null) {
            this.copyToFrom(lv2, lv);
            this.trackedEntity.onTrackedDataSet(lv.getData());
         }
      }

      this.lock.writeLock().unlock();
      this.dirty = true;
   }

   @Environment(EnvType.CLIENT)
   private <T> void copyToFrom(DataTracker.Entry<T> arg, DataTracker.Entry<?> arg2) {
      if (!Objects.equals(arg2.data.getType(), arg.data.getType())) {
         throw new IllegalStateException(
            String.format(
               "Invalid entity data item type for field %d on entity %s: old=%s(%s), new=%s(%s)",
               arg.data.getId(),
               this.trackedEntity,
               arg.value,
               arg.value.getClass(),
               arg2.value,
               arg2.value.getClass()
            )
         );
      } else {
         arg.set((T)arg2.get());
      }
   }

   public boolean isEmpty() {
      return this.empty;
   }

   public void clearDirty() {
      this.dirty = false;
      this.lock.readLock().lock();

      for (DataTracker.Entry<?> lv : this.entries.values()) {
         lv.setDirty(false);
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
