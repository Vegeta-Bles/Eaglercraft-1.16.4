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
      Collection<? extends MemoryModuleType<?>> memoryModules, Collection<? extends SensorType<? extends Sensor<? super E>>> sensors
   ) {
      final MutableObject<Codec<Brain<E>>> _snowman = new MutableObject();
      _snowman.setValue(
         (new MapCodec<Brain<E>>() {
               public <T> Stream<T> keys(DynamicOps<T> _snowman) {
                  return memoryModules.stream()
                     .flatMap(_snowmanx -> Util.stream(_snowmanx.getCodec().map(_snowmanxxxxx -> Registry.MEMORY_MODULE_TYPE.getId(_snowmanx))))
                     .map(_snowmanxxx -> (T)_snowman.createString(_snowmanxxx.toString()));
               }

               public <T> DataResult<Brain<E>> decode(DynamicOps<T> _snowman, MapLike<T> _snowman) {
                  MutableObject<DataResult<Builder<Brain.MemoryEntry<?>>>> _snowmanxx = new MutableObject(DataResult.success(ImmutableList.builder()));
                  _snowman.entries().forEach(_snowmanxxxxx -> {
                     DataResult<MemoryModuleType<?>> _snowmanxxx = Registry.MEMORY_MODULE_TYPE.parse(_snowman, _snowmanxxxxx.getFirst());
                     DataResult<? extends Brain.MemoryEntry<?>> _snowmanxxxx = _snowmanxxx.flatMap(_snowmanxxxxxxxxx -> this.method_28320(_snowmanxxxxxxxxx, _snowman, (T)_snowmanxxxxx.getSecond()));
                     _snowman.setValue(((DataResult)_snowman.getValue()).apply2(Builder::add, _snowmanxxxx));
                  });
                  ImmutableList<Brain.MemoryEntry<?>> _snowmanxxx = ((DataResult)_snowmanxx.getValue())
                     .resultOrPartial(Brain.LOGGER::error)
                     .<ImmutableList<Brain.MemoryEntry<?>>>map(Builder::build)
                     .orElseGet(ImmutableList::of);
                  return DataResult.success(new Brain<>(memoryModules, sensors, _snowmanxxx, _snowman::getValue));
               }

               private <T, U> DataResult<Brain.MemoryEntry<U>> method_28320(MemoryModuleType<U> _snowman, DynamicOps<T> _snowman, T _snowman) {
                  return _snowman.getCodec()
                     .<DataResult>map(DataResult::success)
                     .orElseGet(() -> DataResult.error("No codec for memory: " + _snowman))
                     .flatMap(_snowmanxxxxx -> _snowmanxxxxx.parse(_snowman, _snowman))
                     .map(_snowmanxxxxx -> new Brain.MemoryEntry(_snowman, Optional.of(_snowmanxxxxx)));
               }

               public <T> RecordBuilder<T> encode(Brain<E> _snowman, DynamicOps<T> _snowman, RecordBuilder<T> _snowman) {
                  _snowman.streamMemories().forEach(_snowmanxxxxx -> _snowmanxxxxx.serialize(_snowman, _snowman));
                  return _snowman;
               }
            })
            .fieldOf("memories")
            .codec()
      );
      return (Codec<Brain<E>>)_snowman.getValue();
   }

   public Brain(
      Collection<? extends MemoryModuleType<?>> memories,
      Collection<? extends SensorType<? extends Sensor<? super E>>> sensors,
      ImmutableList<Brain.MemoryEntry<?>> memoryEntries,
      Supplier<Codec<Brain<E>>> codecSupplier
   ) {
      this.codecSupplier = codecSupplier;

      for (MemoryModuleType<?> _snowman : memories) {
         this.memories.put(_snowman, Optional.empty());
      }

      for (SensorType<? extends Sensor<? super E>> _snowman : sensors) {
         this.sensors.put(_snowman, (Sensor<? super E>)_snowman.create());
      }

      for (Sensor<? super E> _snowman : this.sensors.values()) {
         for (MemoryModuleType<?> _snowmanx : _snowman.getOutputMemoryModules()) {
            this.memories.put(_snowmanx, Optional.empty());
         }
      }

      UnmodifiableIterator var11 = memoryEntries.iterator();

      while (var11.hasNext()) {
         Brain.MemoryEntry<?> _snowman = (Brain.MemoryEntry<?>)var11.next();
         _snowman.apply(this);
      }
   }

   public <T> DataResult<T> encode(DynamicOps<T> ops) {
      return this.codecSupplier.get().encodeStart(ops, this);
   }

   private Stream<Brain.MemoryEntry<?>> streamMemories() {
      return this.memories.entrySet().stream().map(_snowman -> Brain.MemoryEntry.of(_snowman.getKey(), _snowman.getValue()));
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

   public <U> Optional<U> getOptionalMemory(MemoryModuleType<U> type) {
      return this.memories.get(type).map(Memory::getValue);
   }

   public <U> boolean method_29519(MemoryModuleType<U> _snowman, U _snowman) {
      return !this.hasMemoryModule(_snowman) ? false : this.getOptionalMemory(_snowman).filter(_snowmanxxx -> _snowmanxxx.equals(_snowman)).isPresent();
   }

   public boolean isMemoryInState(MemoryModuleType<?> type, MemoryModuleState state) {
      Optional<? extends Memory<?>> _snowman = this.memories.get(type);
      return _snowman == null
         ? false
         : state == MemoryModuleState.REGISTERED
            || state == MemoryModuleState.VALUE_PRESENT && _snowman.isPresent()
            || state == MemoryModuleState.VALUE_ABSENT && !_snowman.isPresent();
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
      List<Task<? super E>> _snowman = new ObjectArrayList();

      for (Map<Activity, Set<Task<? super E>>> _snowmanx : this.tasks.values()) {
         for (Set<Task<? super E>> _snowmanxx : _snowmanx.values()) {
            for (Task<? super E> _snowmanxxx : _snowmanxx) {
               if (_snowmanxxx.getStatus() == Task.Status.RUNNING) {
                  _snowman.add(_snowmanxxx);
               }
            }
         }
      }

      return _snowman;
   }

   public void resetPossibleActivities() {
      this.resetPossibleActivities(this.defaultActivity);
   }

   public Optional<Activity> getFirstPossibleNonCoreActivity() {
      for (Activity _snowman : this.possibleActivities) {
         if (!this.coreActivities.contains(_snowman)) {
            return Optional.of(_snowman);
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
      for (Activity _snowman : this.possibleActivities) {
         if (_snowman != except) {
            Set<MemoryModuleType<?>> _snowmanx = this.forgettingActivityMemories.get(_snowman);
            if (_snowmanx != null) {
               for (MemoryModuleType<?> _snowmanxx : _snowmanx) {
                  this.forget(_snowmanxx);
               }
            }
         }
      }
   }

   public void refreshActivities(long timeOfDay, long time) {
      if (time - this.activityStartTime > 20L) {
         this.activityStartTime = time;
         Activity _snowman = this.getSchedule().getActivityForTime((int)(timeOfDay % 24000L));
         if (!this.possibleActivities.contains(_snowman)) {
            this.doExclusively(_snowman);
         }
      }
   }

   public void resetPossibleActivities(List<Activity> _snowman) {
      for (Activity _snowmanx : _snowman) {
         if (this.canDoActivity(_snowmanx)) {
            this.resetPossibleActivities(_snowmanx);
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
      Set<Pair<MemoryModuleType<?>, MemoryModuleState>> _snowman = ImmutableSet.of(Pair.of(memoryType, MemoryModuleState.VALUE_PRESENT));
      Set<MemoryModuleType<?>> _snowmanx = ImmutableSet.of(memoryType);
      this.setTaskList(activity, this.indexTaskList(begin, tasks), _snowman, _snowmanx);
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
         Pair<Integer, ? extends Task<? super E>> _snowman = (Pair<Integer, ? extends Task<? super E>>)var5.next();
         this.tasks
            .computeIfAbsent((Integer)_snowman.getFirst(), _snowmanx -> Maps.newHashMap())
            .computeIfAbsent(activity, _snowmanx -> Sets.newLinkedHashSet())
            .add((Task<? super E>)_snowman.getSecond());
      }
   }

   public boolean hasActivity(Activity activity) {
      return this.possibleActivities.contains(activity);
   }

   public Brain<E> copy() {
      Brain<E> _snowman = new Brain<>(this.memories.keySet(), this.sensors.keySet(), ImmutableList.of(), this.codecSupplier);

      for (Entry<MemoryModuleType<?>, Optional<? extends Memory<?>>> _snowmanx : this.memories.entrySet()) {
         MemoryModuleType<?> _snowmanxx = _snowmanx.getKey();
         if (_snowmanx.getValue().isPresent()) {
            _snowman.memories.put(_snowmanxx, _snowmanx.getValue());
         }
      }

      return _snowman;
   }

   public void tick(ServerWorld world, E entity) {
      this.tickMemories();
      this.tickSensors(world, entity);
      this.startTasks(world, entity);
      this.updateTasks(world, entity);
   }

   private void tickSensors(ServerWorld world, E entity) {
      for (Sensor<? super E> _snowman : this.sensors.values()) {
         _snowman.tick(world, entity);
      }
   }

   private void tickMemories() {
      for (Entry<MemoryModuleType<?>, Optional<? extends Memory<?>>> _snowman : this.memories.entrySet()) {
         if (_snowman.getValue().isPresent()) {
            Memory<?> _snowmanx = (Memory<?>)_snowman.getValue().get();
            _snowmanx.tick();
            if (_snowmanx.isExpired()) {
               this.forget(_snowman.getKey());
            }
         }
      }
   }

   public void stopAllTasks(ServerWorld world, E entity) {
      long _snowman = entity.world.getTime();

      for (Task<? super E> _snowmanx : this.getRunningTasks()) {
         _snowmanx.stop(world, entity, _snowman);
      }
   }

   private void startTasks(ServerWorld world, E entity) {
      long _snowman = world.getTime();

      for (Map<Activity, Set<Task<? super E>>> _snowmanx : this.tasks.values()) {
         for (Entry<Activity, Set<Task<? super E>>> _snowmanxx : _snowmanx.entrySet()) {
            Activity _snowmanxxx = _snowmanxx.getKey();
            if (this.possibleActivities.contains(_snowmanxxx)) {
               for (Task<? super E> _snowmanxxxx : _snowmanxx.getValue()) {
                  if (_snowmanxxxx.getStatus() == Task.Status.STOPPED) {
                     _snowmanxxxx.tryStarting(world, entity, _snowman);
                  }
               }
            }
         }
      }
   }

   private void updateTasks(ServerWorld world, E entity) {
      long _snowman = world.getTime();

      for (Task<? super E> _snowmanx : this.getRunningTasks()) {
         _snowmanx.tick(world, entity, _snowman);
      }
   }

   private boolean canDoActivity(Activity activity) {
      if (!this.requiredActivityMemories.containsKey(activity)) {
         return false;
      } else {
         for (Pair<MemoryModuleType<?>, MemoryModuleState> _snowman : this.requiredActivityMemories.get(activity)) {
            MemoryModuleType<?> _snowmanx = (MemoryModuleType<?>)_snowman.getFirst();
            MemoryModuleState _snowmanxx = (MemoryModuleState)_snowman.getSecond();
            if (!this.isMemoryInState(_snowmanx, _snowmanxx)) {
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
      int _snowman = begin;
      Builder<Pair<Integer, ? extends Task<? super E>>> _snowmanx = ImmutableList.builder();
      UnmodifiableIterator var5 = tasks.iterator();

      while (var5.hasNext()) {
         Task<? super E> _snowmanxx = (Task<? super E>)var5.next();
         _snowmanx.add(Pair.of(_snowman++, _snowmanxx));
      }

      return _snowmanx.build();
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
            .ifPresent(_snowmanxx -> this.data.ifPresent(_snowmanxxx -> builder.add(Registry.MEMORY_MODULE_TYPE.encodeStart(ops, this.type), _snowmanxx.encodeStart(ops, _snowmanxxx))));
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
