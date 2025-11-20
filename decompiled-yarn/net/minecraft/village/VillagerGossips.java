package net.minecraft.village;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.DynamicSerializableUuid;

public class VillagerGossips {
   private final Map<UUID, VillagerGossips.Reputation> entityReputation = Maps.newHashMap();

   public VillagerGossips() {
   }

   public void decay() {
      Iterator<VillagerGossips.Reputation> _snowman = this.entityReputation.values().iterator();

      while (_snowman.hasNext()) {
         VillagerGossips.Reputation _snowmanx = _snowman.next();
         _snowmanx.decay();
         if (_snowmanx.isObsolete()) {
            _snowman.remove();
         }
      }
   }

   private Stream<VillagerGossips.GossipEntry> entries() {
      return this.entityReputation.entrySet().stream().flatMap(_snowman -> _snowman.getValue().entriesFor(_snowman.getKey()));
   }

   private Collection<VillagerGossips.GossipEntry> pickGossips(Random _snowman, int count) {
      List<VillagerGossips.GossipEntry> _snowmanx = this.entries().collect(Collectors.toList());
      if (_snowmanx.isEmpty()) {
         return Collections.emptyList();
      } else {
         int[] _snowmanxx = new int[_snowmanx.size()];
         int _snowmanxxx = 0;

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanx.size(); _snowmanxxxx++) {
            VillagerGossips.GossipEntry _snowmanxxxxx = _snowmanx.get(_snowmanxxxx);
            _snowmanxxx += Math.abs(_snowmanxxxxx.getValue());
            _snowmanxx[_snowmanxxxx] = _snowmanxxx - 1;
         }

         Set<VillagerGossips.GossipEntry> _snowmanxxxx = Sets.newIdentityHashSet();

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < count; _snowmanxxxxx++) {
            int _snowmanxxxxxx = _snowman.nextInt(_snowmanxxx);
            int _snowmanxxxxxxx = Arrays.binarySearch(_snowmanxx, _snowmanxxxxxx);
            _snowmanxxxx.add(_snowmanx.get(_snowmanxxxxxxx < 0 ? -_snowmanxxxxxxx - 1 : _snowmanxxxxxxx));
         }

         return _snowmanxxxx;
      }
   }

   private VillagerGossips.Reputation getReputationFor(UUID target) {
      return this.entityReputation.computeIfAbsent(target, _snowman -> new VillagerGossips.Reputation());
   }

   public void shareGossipFrom(VillagerGossips from, Random _snowman, int count) {
      Collection<VillagerGossips.GossipEntry> _snowmanx = from.pickGossips(_snowman, count);
      _snowmanx.forEach(_snowmanxx -> {
         int _snowmanxx = _snowmanxx.value - _snowmanxx.type.shareDecrement;
         if (_snowmanxx >= 2) {
            this.getReputationFor(_snowmanxx.target).associatedGossip.mergeInt(_snowmanxx.type, _snowmanxx, VillagerGossips::max);
         }
      });
   }

   public int getReputationFor(UUID target, Predicate<VillageGossipType> gossipTypeFilter) {
      VillagerGossips.Reputation _snowman = this.entityReputation.get(target);
      return _snowman != null ? _snowman.getValueFor(gossipTypeFilter) : 0;
   }

   public void startGossip(UUID target, VillageGossipType type, int value) {
      VillagerGossips.Reputation _snowman = this.getReputationFor(target);
      _snowman.associatedGossip.mergeInt(type, value, (_snowmanx, _snowmanxx) -> this.mergeReputation(type, _snowmanx, _snowmanxx));
      _snowman.clamp(type);
      if (_snowman.isObsolete()) {
         this.entityReputation.remove(target);
      }
   }

   public <T> Dynamic<T> serialize(DynamicOps<T> _snowman) {
      return new Dynamic(_snowman, _snowman.createList(this.entries().map(_snowmanxx -> _snowmanxx.serialize(_snowman)).map(Dynamic::getValue)));
   }

   public void deserialize(Dynamic<?> _snowman) {
      _snowman.asStream()
         .map(VillagerGossips.GossipEntry::deserialize)
         .flatMap(_snowmanx -> Util.stream(_snowmanx.result()))
         .forEach(_snowmanx -> this.getReputationFor(_snowmanx.target).associatedGossip.put(_snowmanx.type, _snowmanx.value));
   }

   private static int max(int left, int right) {
      return Math.max(left, right);
   }

   private int mergeReputation(VillageGossipType type, int left, int right) {
      int _snowman = left + right;
      return _snowman > type.maxValue ? Math.max(type.maxValue, left) : _snowman;
   }

   static class GossipEntry {
      public final UUID target;
      public final VillageGossipType type;
      public final int value;

      public GossipEntry(UUID _snowman, VillageGossipType _snowman, int _snowman) {
         this.target = _snowman;
         this.type = _snowman;
         this.value = _snowman;
      }

      public int getValue() {
         return this.value * this.type.multiplier;
      }

      @Override
      public String toString() {
         return "GossipEntry{target=" + this.target + ", type=" + this.type + ", value=" + this.value + '}';
      }

      public <T> Dynamic<T> serialize(DynamicOps<T> _snowman) {
         return new Dynamic(
            _snowman,
            _snowman.createMap(
               ImmutableMap.of(
                  _snowman.createString("Target"),
                  DynamicSerializableUuid.CODEC.encodeStart(_snowman, this.target).result().orElseThrow(RuntimeException::new),
                  _snowman.createString("Type"),
                  _snowman.createString(this.type.key),
                  _snowman.createString("Value"),
                  _snowman.createInt(this.value)
               )
            )
         );
      }

      public static DataResult<VillagerGossips.GossipEntry> deserialize(Dynamic<?> _snowman) {
         return DataResult.unbox(
            DataResult.instance()
               .group(
                  _snowman.get("Target").read(DynamicSerializableUuid.CODEC),
                  _snowman.get("Type").asString().map(VillageGossipType::byKey),
                  _snowman.get("Value").asNumber().map(Number::intValue)
               )
               .apply(DataResult.instance(), VillagerGossips.GossipEntry::new)
         );
      }
   }

   static class Reputation {
      private final Object2IntMap<VillageGossipType> associatedGossip = new Object2IntOpenHashMap();

      private Reputation() {
      }

      public int getValueFor(Predicate<VillageGossipType> gossipTypeFilter) {
         return this.associatedGossip
            .object2IntEntrySet()
            .stream()
            .filter(_snowmanx -> gossipTypeFilter.test((VillageGossipType)_snowmanx.getKey()))
            .mapToInt(_snowman -> _snowman.getIntValue() * ((VillageGossipType)_snowman.getKey()).multiplier)
            .sum();
      }

      public Stream<VillagerGossips.GossipEntry> entriesFor(UUID target) {
         return this.associatedGossip
            .object2IntEntrySet()
            .stream()
            .map(_snowmanx -> new VillagerGossips.GossipEntry(target, (VillageGossipType)_snowmanx.getKey(), _snowmanx.getIntValue()));
      }

      public void decay() {
         ObjectIterator<Entry<VillageGossipType>> _snowman = this.associatedGossip.object2IntEntrySet().iterator();

         while (_snowman.hasNext()) {
            Entry<VillageGossipType> _snowmanx = (Entry<VillageGossipType>)_snowman.next();
            int _snowmanxx = _snowmanx.getIntValue() - ((VillageGossipType)_snowmanx.getKey()).decay;
            if (_snowmanxx < 2) {
               _snowman.remove();
            } else {
               _snowmanx.setValue(_snowmanxx);
            }
         }
      }

      public boolean isObsolete() {
         return this.associatedGossip.isEmpty();
      }

      public void clamp(VillageGossipType gossipType) {
         int _snowman = this.associatedGossip.getInt(gossipType);
         if (_snowman > gossipType.maxValue) {
            this.associatedGossip.put(gossipType, gossipType.maxValue);
         }

         if (_snowman < 2) {
            this.remove(gossipType);
         }
      }

      public void remove(VillageGossipType gossipType) {
         this.associatedGossip.removeInt(gossipType);
      }
   }
}
