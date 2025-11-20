package net.minecraft.world.timer;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.primitives.UnsignedLong;
import com.mojang.serialization.Dynamic;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Timer<T> {
   private static final Logger LOGGER = LogManager.getLogger();
   private final TimerCallbackSerializer<T> callback;
   private final Queue<Timer.Event<T>> events = new PriorityQueue<>(createEventComparator());
   private UnsignedLong eventCounter = UnsignedLong.ZERO;
   private final Table<String, Long, Timer.Event<T>> eventsByName = HashBasedTable.create();

   private static <T> Comparator<Timer.Event<T>> createEventComparator() {
      return Comparator.<Timer.Event<T>>comparingLong(_snowman -> _snowman.triggerTime).thenComparing(_snowman -> _snowman.id);
   }

   public Timer(TimerCallbackSerializer<T> _snowman, Stream<Dynamic<Tag>> _snowman) {
      this(_snowman);
      this.events.clear();
      this.eventsByName.clear();
      this.eventCounter = UnsignedLong.ZERO;
      _snowman.forEach(_snowmanxx -> {
         if (!(_snowmanxx.getValue() instanceof CompoundTag)) {
            LOGGER.warn("Invalid format of events: {}", _snowmanxx);
         } else {
            this.addEvent((CompoundTag)_snowmanxx.getValue());
         }
      });
   }

   public Timer(TimerCallbackSerializer<T> timerCallbackSerializer) {
      this.callback = timerCallbackSerializer;
   }

   public void processEvents(T server, long time) {
      while (true) {
         Timer.Event<T> _snowman = this.events.peek();
         if (_snowman == null || _snowman.triggerTime > time) {
            return;
         }

         this.events.remove();
         this.eventsByName.remove(_snowman.name, time);
         _snowman.callback.call(server, this, time);
      }
   }

   public void setEvent(String name, long triggerTime, TimerCallback<T> callback) {
      if (!this.eventsByName.contains(name, triggerTime)) {
         this.eventCounter = this.eventCounter.plus(UnsignedLong.ONE);
         Timer.Event<T> _snowman = new Timer.Event<>(triggerTime, this.eventCounter, name, callback);
         this.eventsByName.put(name, triggerTime, _snowman);
         this.events.add(_snowman);
      }
   }

   public int method_22593(String _snowman) {
      Collection<Timer.Event<T>> _snowmanx = this.eventsByName.row(_snowman).values();
      _snowmanx.forEach(this.events::remove);
      int _snowmanxx = _snowmanx.size();
      _snowmanx.clear();
      return _snowmanxx;
   }

   public Set<String> method_22592() {
      return Collections.unmodifiableSet(this.eventsByName.rowKeySet());
   }

   private void addEvent(CompoundTag tag) {
      CompoundTag _snowman = tag.getCompound("Callback");
      TimerCallback<T> _snowmanx = this.callback.deserialize(_snowman);
      if (_snowmanx != null) {
         String _snowmanxx = tag.getString("Name");
         long _snowmanxxx = tag.getLong("TriggerTime");
         this.setEvent(_snowmanxx, _snowmanxxx, _snowmanx);
      }
   }

   private CompoundTag serialize(Timer.Event<T> event) {
      CompoundTag _snowman = new CompoundTag();
      _snowman.putString("Name", event.name);
      _snowman.putLong("TriggerTime", event.triggerTime);
      _snowman.put("Callback", this.callback.serialize(event.callback));
      return _snowman;
   }

   public ListTag toTag() {
      ListTag _snowman = new ListTag();
      this.events.stream().sorted(createEventComparator()).map(this::serialize).forEach(_snowman::add);
      return _snowman;
   }

   public static class Event<T> {
      public final long triggerTime;
      public final UnsignedLong id;
      public final String name;
      public final TimerCallback<T> callback;

      private Event(long triggerTime, UnsignedLong id, String name, TimerCallback<T> callback) {
         this.triggerTime = triggerTime;
         this.id = id;
         this.name = name;
         this.callback = callback;
      }
   }
}
