package net.minecraft.entity.ai.brain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Brain<E extends LivingEntity> {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Supplier<Codec<Brain<E>>> codecSupplier;
   private final Map<MemoryModuleType<?>, Optional<? extends Memory<?>>> memories = Maps.newHashMap();
   private final Map<SensorType<? extends Sensor<? super E>>, Sensor<? super E>> sensors = Maps.newLinkedHashMap();
   private final Map<Integer, Map<Activity, Set<Task<? super E>>>> tasks = Maps.newTreeMap();
   private Schedule schedule = Schedule.EMPTY;
   private final Map<Activity, Set<Pair<MemoryModuleType<?>, MemoryModuleState>>> requiredActivityMemories = Maps.newHashMap();
   private final Map<Activity, Set<MemoryModuleType<?>>> forgettingActivityMemories = Maps.newHashMap();
   private Set<Activity> coreActivities = Sets.newHashSet();
   private final Set<Activity> possibleActivities = Sets.newHashSet();
   private Activity defaultActivity = Activity.IDLE;
   private long activityStartTime = -9999L;

   public static <E extends LivingEntity> Brain.Profile<E> createProfile(
      Collection<? extends MemoryModuleType<?>> memoryModules, Collection<? extends SensorType<? extends Sensor<? super E>>> sensors
   ) {
      return new Brain.Profile<>(memoryModules, sensors);
   }

   public static <E extends LivingEntity> Codec<Brain<E>> createBrainCodec(
      final Collection<? extends MemoryModuleType<?>> memoryModules, final Collection<? extends SensorType<? extends Sensor<? super E>>> sensors
   ) {
      final MutableObject<Codec<Brain<E>>> codecReference = new MutableObject<>();
      codecReference.setValue(
         (new MapCodec<Brain<E>>() {
               public <T> Stream<T> keys(DynamicOps<T> dynamicOps) {
                  return memoryModules.stream()
                     .flatMap(module -> Util.stream(module.getCodec().map(codec -> Registry.MEMORY_MODULE_TYPE.getId((MemoryModuleType<?>)module))))
                     .map(id -> (T)dynamicOps.createString(id.toString()));
               }

               public <T> DataResult<Brain<E>> decode(DynamicOps<T> dynamicOps, MapLike<T> mapLike) {
                  MutableObject<DataResult<Builder<Brain.MemoryEntry<?>>>> builderResult = new MutableObject<>(
                     DataResult.success(ImmutableList.<Brain.MemoryEntry<?>>builder())
                  );
                  mapLike.entries().forEach(pair -> {
                     DataResult<MemoryModuleType<?>> moduleResult = Registry.MEMORY_MODULE_TYPE.parse(dynamicOps, pair.getFirst());
                     DataResult<? extends Brain.MemoryEntry<?>> entryResult = moduleResult.flatMap(module -> this.method_28320(module, dynamicOps, pair.getSecond()));
                     builderResult.setValue(
                        builderResult.getValue().flatMap(builder -> entryResult.map(entry -> {
                              builder.add(entry);
                              return builder;
                           }))
                     );
                  });
                  ImmutableList<Brain.MemoryEntry<?>> immutableList = builderResult.getValue()
                     .resultOrPartial(Brain.LOGGER::error)
                     .map(Builder::build)
                     .orElseGet(ImmutableList::of);
                  return DataResult.success(new Brain<>(memoryModules, sensors, immutableList, codecReference::getValue));
               }

               private <T, U> DataResult<Brain.MemoryEntry<U>> method_28320(MemoryModuleType<U> type, DynamicOps<T> dynamicOps, T object) {
                  return type.getCodec()
                     .map(codec -> codec.parse(dynamicOps, object))
                     .orElseGet(() -> DataResult.error("No codec for memory: " + type))
                     .map(memory -> new Brain.MemoryEntry<>(type, Optional.of(memory)));
               }

               public <T> RecordBuilder<T> encode(Brain<E> brain, DynamicOps<T> dynamicOps, RecordBuilder<T> recordBuilder) {
                  brain.streamMemories().forEach(memoryEntry -> memoryEntry.serialize(dynamicOps, recordBuilder));
                  return recordBuilder;
               }
            })
            .fieldOf("memories")
            .codec()
      );
      return codecReference.getValue();
   }

   public Brain(
      Collection<? extends MemoryModuleType<?>> memories,
      Collection<? extends SensorType<? extends Sensor<? super E>>> sensors,
      ImmutableList<Brain.MemoryEntry<?>> memoryEntries,
      Supplier<Codec<Brain<E>>> codecSupplier
   ) {
      this.codecSupplier = codecSupplier;

      for (MemoryModuleType<?> lv : memories) {
         this.memories.put(lv, Optional.empty());
      }

      for (SensorType<? extends Sensor<? super E>> lv2 : sensors) {
         this.sensors.put(lv2, (Sensor<? super E>)lv2.create());
      }

      for (Sensor<? super E> lv3 : this.sensors.values()) {
         for (MemoryModuleType<?> lv4 : lv3.getOutputMemoryModules()) {
            this.memories.put(lv4, Optional.empty());
         }
      }

      UnmodifiableIterator var11 = memoryEntries.iterator();

      while (var11.hasNext()) {
         Brain.MemoryEntry<?> lv5 = (Brain.MemoryEntry<?>)var11.next();
         lv5.apply(this);
      }
   }

   public <T> DataResult<T> encode(DynamicOps<T> ops) {
      return this.codecSupplier.get().encodeStart(ops, this);
   }

   private Stream<Brain.MemoryEntry<?>> streamMemories() {
      return this.memories.entrySet().stream().map(entry -> Brain.MemoryEntry.of(entry.getKey(), entry.getValue()));
   }

   public boolean hasMemoryModule(MemoryModuleType<?> type) {
      return this.isMemoryInState(type, MemoryModuleState.VALUE_PRESENT);
   }

   public <U> void forget(MemoryModuleType<U> type) {
      this.remember(type, Optional.empty());
   }

   public <U> void remember(MemoryModuleType<U> type, @Nullable U value) {
      this.remember(type, Optional.ofNullable(value));
   }

   public <U> void remember(MemoryModuleType<U> type, U value, long startTime) {
      this.setMemory(type, Optional.of(Memory.timed(value, startTime)));
   }

   public <U> void remember(MemoryModuleType<U> type, Optional<? extends U> value) {
      this.setMemory(type, value.map(Memory::method_28355));
   }

   private <U> void setMemory(MemoryModuleType<U> type, Optional<? extends Memory<?>> memory) {
      if (this.memories.containsKey(type)) {
         if (memory.isPresent() && this.isEmptyCollection(memory.get().getValue())) {
            this.forget(type);
         } else {
            this.memories.put(type, memory);
         }
      }
   }

   @SuppressWarnings("unchecked")
   public <U> Optional<U> getOptionalMemory(MemoryModuleType<U> type) {
      Optional<? extends Memory<?>> optional = this.memories.get(type);
      if (optional == null) {
         return Optional.empty();
      }

      return optional.map(memory -> (U)memory.getValue());
   }

   public <U> boolean method_29519(MemoryModuleType<U> arg, U object) {
      return !this.hasMemoryModule(arg) ? false : this.getOptionalMemory(arg).filter(object2 -> object2.equals(object)).isPresent();
   }

   public boolean isMemoryInState(MemoryModuleType<?> type, MemoryModuleState state) {
      Optional<? extends Memory<?>> optional = this.memories.get(type);
      return optional == null
         ? false
         : state == MemoryModuleState.REGISTERED
            || state == MemoryModuleState.VALUE_PRESENT && optional.isPresent()
            || state == MemoryModuleState.VALUE_ABSENT && !optional.isPresent();
   }

   public Schedule getSchedule() {
      return this.schedule;
   }

   public void setSchedule(Schedule schedule) {
      this.schedule = schedule;
   }

   public void setCoreActivities(Set<Activity> coreActivities) {
      this.coreActivities = coreActivities;
   }

   @Deprecated
   public List<Task<? super E>> getRunningTasks() {
      List<Task<? super E>> list = new ObjectArrayList();

      for (Map<Activity, Set<Task<? super E>>> map : this.tasks.values()) {
         for (Set<Task<? super E>> set : map.values()) {
            for (Task<? super E> lv : set) {
               if (lv.getStatus() == Task.Status.RUNNING) {
                  list.add(lv);
               }
            }
         }
      }

      return list;
   }

   public void resetPossibleActivities() {
      this.resetPossibleActivities(this.defaultActivity);
   }

   public Optional<Activity> getFirstPossibleNonCoreActivity() {
      for (Activity lv : this.possibleActivities) {
         if (!this.coreActivities.contains(lv)) {
            return Optional.of(lv);
         }
      }

      return Optional.empty();
   }

   public void doExclusively(Activity activity) {
      if (this.canDoActivity(activity)) {
         this.resetPossibleActivities(activity);
      } else {
         this.resetPossibleActivities();
      }
   }

   private void resetPossibleActivities(Activity except) {
      if (!this.hasActivity(except)) {
         this.forgetIrrelevantMemories(except);
         this.possibleActivities.clear();
         this.possibleActivities.addAll(this.coreActivities);
         this.possibleActivities.add(except);
      }
   }

   private void forgetIrrelevantMemories(Activity except) {
      for (Activity lv : this.possibleActivities) {
         if (lv != except) {
            Set<MemoryModuleType<?>> set = this.forgettingActivityMemories.get(lv);
            if (set != null) {
               for (MemoryModuleType<?> lv2 : set) {
                  this.forget(lv2);
               }
            }
         }
      }
   }

   public void refreshActivities(long timeOfDay, long time) {
      if (time - this.activityStartTime > 20L) {
         this.activityStartTime = time;
         Activity lv = this.getSchedule().getActivityForTime((int)(timeOfDay % 24000L));
         if (!this.possibleActivities.contains(lv)) {
            this.doExclusively(lv);
         }
      }
   }

   public void resetPossibleActivities(List<Activity> list) {
      for (Activity lv : list) {
         if (this.canDoActivity(lv)) {
            this.resetPossibleActivities(lv);
            break;
         }
      }
   }

   public void setDefaultActivity(Activity activity) {
      this.defaultActivity = activity;
   }

   public void setTaskList(Activity activity, int begin, ImmutableList<? extends Task<? super E>> list) {
      this.setTaskList(activity, this.indexTaskList(begin, list));
   }

   public void setTaskList(Activity activity, int begin, ImmutableList<? extends Task<? super E>> tasks, MemoryModuleType<?> memoryType) {
      Set<Pair<MemoryModuleType<?>, MemoryModuleState>> set = ImmutableSet.of(Pair.of(memoryType, MemoryModuleState.VALUE_PRESENT));
      Set<MemoryModuleType<?>> set2 = ImmutableSet.of(memoryType);
      this.setTaskList(activity, this.indexTaskList(begin, tasks), set, set2);
   }

   public void setTaskList(Activity activity, ImmutableList<? extends Pair<Integer, ? extends Task<? super E>>> indexedTasks) {
      this.setTaskList(activity, indexedTasks, ImmutableSet.of(), Sets.newHashSet());
   }

   public void setTaskList(
      Activity activity,
      ImmutableList<? extends Pair<Integer, ? extends Task<? super E>>> indexedTasks,
      Set<Pair<MemoryModuleType<?>, MemoryModuleState>> requiredMemories
   ) {
      this.setTaskList(activity, indexedTasks, requiredMemories, Sets.newHashSet());
   }

   private void setTaskList(
      Activity activity,
      ImmutableList<? extends Pair<Integer, ? extends Task<? super E>>> indexedTasks,
      Set<Pair<MemoryModuleType<?>, MemoryModuleState>> requiredMemories,
      Set<MemoryModuleType<?>> forgettingMemories
   ) {
      this.requiredActivityMemories.put(activity, requiredMemories);
      if (!forgettingMemories.isEmpty()) {
         this.forgettingActivityMemories.put(activity, forgettingMemories);
      }

      UnmodifiableIterator var5 = indexedTasks.iterator();

      while (var5.hasNext()) {
         Pair<Integer, ? extends Task<? super E>> pair = (Pair<Integer, ? extends Task<? super E>>)var5.next();
         this.tasks
            .computeIfAbsent((Integer)pair.getFirst(), integer -> Maps.newHashMap())
            .computeIfAbsent(activity, arg -> Sets.newLinkedHashSet())
            .add((Task<? super E>)pair.getSecond());
      }
   }

   public boolean hasActivity(Activity activity) {
      return this.possibleActivities.contains(activity);
   }

   public Brain<E> copy() {
      Brain<E> lv = new Brain<>(this.memories.keySet(), this.sensors.keySet(), ImmutableList.of(), this.codecSupplier);

      for (Entry<MemoryModuleType<?>, Optional<? extends Memory<?>>> entry : this.memories.entrySet()) {
         MemoryModuleType<?> lv2 = entry.getKey();
         if (entry.getValue().isPresent()) {
            lv.memories.put(lv2, entry.getValue());
         }
      }

      return lv;
   }

   public void tick(ServerWorld world, E entity) {
      this.tickMemories();
      this.tickSensors(world, entity);
      this.startTasks(world, entity);
      this.updateTasks(world, entity);
   }

   private void tickSensors(ServerWorld world, E entity) {
      for (Sensor<? super E> lv : this.sensors.values()) {
         lv.tick(world, entity);
      }
   }

   private void tickMemories() {
      for (Entry<MemoryModuleType<?>, Optional<? extends Memory<?>>> entry : this.memories.entrySet()) {
         if (entry.getValue().isPresent()) {
            Memory<?> lv = (Memory<?>)entry.getValue().get();
            lv.tick();
            if (lv.isExpired()) {
               this.forget(entry.getKey());
            }
         }
      }
   }

   public void stopAllTasks(ServerWorld world, E entity) {
      long l = entity.world.getTime();

      for (Task<? super E> lv : this.getRunningTasks()) {
         lv.stop(world, entity, l);
      }
   }

   private void startTasks(ServerWorld world, E entity) {
      long l = world.getTime();

      for (Map<Activity, Set<Task<? super E>>> map : this.tasks.values()) {
         for (Entry<Activity, Set<Task<? super E>>> entry : map.entrySet()) {
            Activity lv = entry.getKey();
            if (this.possibleActivities.contains(lv)) {
               for (Task<? super E> lv2 : entry.getValue()) {
                  if (lv2.getStatus() == Task.Status.STOPPED) {
                     lv2.tryStarting(world, entity, l);
                  }
               }
            }
         }
      }
   }

   private void updateTasks(ServerWorld world, E entity) {
      long l = world.getTime();

      for (Task<? super E> lv : this.getRunningTasks()) {
         lv.tick(world, entity, l);
      }
   }

   private boolean canDoActivity(Activity activity) {
      if (!this.requiredActivityMemories.containsKey(activity)) {
         return false;
      } else {
         for (Pair<MemoryModuleType<?>, MemoryModuleState> pair : this.requiredActivityMemories.get(activity)) {
            MemoryModuleType<?> lv = (MemoryModuleType<?>)pair.getFirst();
            MemoryModuleState lv2 = (MemoryModuleState)pair.getSecond();
            if (!this.isMemoryInState(lv, lv2)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean isEmptyCollection(Object value) {
      return value instanceof Collection && ((Collection)value).isEmpty();
   }

   ImmutableList<? extends Pair<Integer, ? extends Task<? super E>>> indexTaskList(int begin, ImmutableList<? extends Task<? super E>> tasks) {
      int j = begin;
      Builder<Pair<Integer, ? extends Task<? super E>>> builder = ImmutableList.builder();
      UnmodifiableIterator var5 = tasks.iterator();

      while (var5.hasNext()) {
         Task<? super E> lv = (Task<? super E>)var5.next();
         builder.add(Pair.of(j++, lv));
      }

      return builder.build();
   }

   static final class MemoryEntry<U> {
      private final MemoryModuleType<U> type;
      private final Optional<? extends Memory<U>> data;

      private static <U> Brain.MemoryEntry<U> of(MemoryModuleType<U> type, Optional<? extends Memory<?>> data) {
         return new Brain.MemoryEntry<>(type, (Optional<? extends Memory<U>>)data);
      }

      private MemoryEntry(MemoryModuleType<U> type, Optional<? extends Memory<U>> data) {
         this.type = type;
         this.data = data;
      }

      private void apply(Brain<?> brain) {
         brain.setMemory(this.type, this.data);
      }

      public <T> void serialize(DynamicOps<T> ops, RecordBuilder<T> builder) {
         this.type
            .getCodec()
            .ifPresent(codec -> this.data.ifPresent(arg -> builder.add(Registry.MEMORY_MODULE_TYPE.encodeStart(ops, this.type), codec.encodeStart(ops, arg))));
      }
   }

   public static final class Profile<E extends LivingEntity> {
      private final Collection<? extends MemoryModuleType<?>> memoryModules;
      private final Collection<? extends SensorType<? extends Sensor<? super E>>> sensors;
      private final Codec<Brain<E>> codec;

      private Profile(Collection<? extends MemoryModuleType<?>> memoryModules, Collection<? extends SensorType<? extends Sensor<? super E>>> sensors) {
         this.memoryModules = memoryModules;
         this.sensors = sensors;
         this.codec = Brain.createBrainCodec(memoryModules, sensors);
      }

      public Brain<E> deserialize(Dynamic<?> data) {
         return this.codec
            .parse(data)
            .resultOrPartial(Brain.LOGGER::error)
            .orElseGet(() -> new Brain<>(this.memoryModules, this.sensors, ImmutableList.of(), () -> this.codec));
      }
   }
}
