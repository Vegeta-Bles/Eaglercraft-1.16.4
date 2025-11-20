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
      Iterator<VillagerGossips.Reputation> iterator = this.entityReputation.values().iterator();

      while (iterator.hasNext()) {
         VillagerGossips.Reputation lv = iterator.next();
         lv.decay();
         if (lv.isObsolete()) {
            iterator.remove();
         }
      }
   }

   private Stream<VillagerGossips.GossipEntry> entries() {
      return this.entityReputation.entrySet().stream().flatMap(entry -> entry.getValue().entriesFor(entry.getKey()));
   }

   private Collection<VillagerGossips.GossipEntry> pickGossips(Random random, int count) {
      List<VillagerGossips.GossipEntry> list = this.entries().collect(Collectors.toList());
      if (list.isEmpty()) {
         return Collections.emptyList();
      } else {
         int[] is = new int[list.size()];
         int j = 0;

         for (int k = 0; k < list.size(); k++) {
            VillagerGossips.GossipEntry lv = list.get(k);
            j += Math.abs(lv.getValue());
            is[k] = j - 1;
         }

         Set<VillagerGossips.GossipEntry> set = Sets.newIdentityHashSet();

         for (int l = 0; l < count; l++) {
            int m = random.nextInt(j);
            int n = Arrays.binarySearch(is, m);
            set.add(list.get(n < 0 ? -n - 1 : n));
         }

         return set;
      }
   }

   private VillagerGossips.Reputation getReputationFor(UUID target) {
      return this.entityReputation.computeIfAbsent(target, uUID -> new VillagerGossips.Reputation());
   }

   public void shareGossipFrom(VillagerGossips from, Random random, int count) {
      Collection<VillagerGossips.GossipEntry> collection = from.pickGossips(random, count);
      collection.forEach(arg -> {
         int i = arg.value - arg.type.shareDecrement;
         if (i >= 2) {
            this.getReputationFor(arg.target).associatedGossip.mergeInt(arg.type, i, VillagerGossips::max);
         }
      });
   }

   public int getReputationFor(UUID target, Predicate<VillageGossipType> gossipTypeFilter) {
      VillagerGossips.Reputation lv = this.entityReputation.get(target);
      return lv != null ? lv.getValueFor(gossipTypeFilter) : 0;
   }

   public void startGossip(UUID target, VillageGossipType type, int value) {
      VillagerGossips.Reputation lv = this.getReputationFor(target);
      lv.associatedGossip.mergeInt(type, value, (integer, integer2) -> this.mergeReputation(type, integer, integer2));
      lv.clamp(type);
      if (lv.isObsolete()) {
         this.entityReputation.remove(target);
      }
   }

   public <T> Dynamic<T> serialize(DynamicOps<T> dynamicOps) {
      return new Dynamic(dynamicOps, dynamicOps.createList(this.entries().map(arg -> arg.serialize(dynamicOps)).map(Dynamic::getValue)));
   }

   public void deserialize(Dynamic<?> dynamic) {
      dynamic.asStream()
         .map(VillagerGossips.GossipEntry::deserialize)
         .flatMap(dataResult -> Util.stream(dataResult.result()))
         .forEach(arg -> this.getReputationFor(arg.target).associatedGossip.put(arg.type, arg.value));
   }

   private static int max(int left, int right) {
      return Math.max(left, right);
   }

   private int mergeReputation(VillageGossipType type, int left, int right) {
      int k = left + right;
      return k > type.maxValue ? Math.max(type.maxValue, left) : k;
   }

   static class GossipEntry {
      public final UUID target;
      public final VillageGossipType type;
      public final int value;

      public GossipEntry(UUID uUID, VillageGossipType arg, int i) {
         this.target = uUID;
         this.type = arg;
         this.value = i;
      }

      public int getValue() {
         return this.value * this.type.multiplier;
      }

      @Override
      public String toString() {
         return "GossipEntry{target=" + this.target + ", type=" + this.type + ", value=" + this.value + '}';
      }

      public <T> Dynamic<T> serialize(DynamicOps<T> dynamicOps) {
         return new Dynamic(
            dynamicOps,
            dynamicOps.createMap(
               ImmutableMap.of(
                  dynamicOps.createString("Target"),
                  DynamicSerializableUuid.CODEC.encodeStart(dynamicOps, this.target).result().orElseThrow(RuntimeException::new),
                  dynamicOps.createString("Type"),
                  dynamicOps.createString(this.type.key),
                  dynamicOps.createString("Value"),
                  dynamicOps.createInt(this.value)
               )
            )
         );
      }

      public static DataResult<VillagerGossips.GossipEntry> deserialize(Dynamic<?> dynamic) {
         return DataResult.unbox(
            DataResult.instance()
               .group(
                  dynamic.get("Target").read(DynamicSerializableUuid.CODEC),
                  dynamic.get("Type").asString().map(VillageGossipType::byKey),
                  dynamic.get("Value").asNumber().map(Number::intValue)
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
            .filter(entry -> gossipTypeFilter.test((VillageGossipType)entry.getKey()))
            .mapToInt(entry -> entry.getIntValue() * ((VillageGossipType)entry.getKey()).multiplier)
            .sum();
      }

      public Stream<VillagerGossips.GossipEntry> entriesFor(UUID target) {
         return this.associatedGossip
            .object2IntEntrySet()
            .stream()
            .map(entry -> new VillagerGossips.GossipEntry(target, (VillageGossipType)entry.getKey(), entry.getIntValue()));
      }

      public void decay() {
         ObjectIterator<Entry<VillageGossipType>> objectIterator = this.associatedGossip.object2IntEntrySet().iterator();

         while (objectIterator.hasNext()) {
            Entry<VillageGossipType> entry = (Entry<VillageGossipType>)objectIterator.next();
            int i = entry.getIntValue() - ((VillageGossipType)entry.getKey()).decay;
            if (i < 2) {
               objectIterator.remove();
            } else {
               entry.setValue(i);
            }
         }
      }

      public boolean isObsolete() {
         return this.associatedGossip.isEmpty();
      }

      public void clamp(VillageGossipType gossipType) {
         int i = this.associatedGossip.getInt(gossipType);
         if (i > gossipType.maxValue) {
            this.associatedGossip.put(gossipType, gossipType.maxValue);
         }

         if (i < 2) {
            this.remove(gossipType);
         }
      }

      public void remove(VillageGossipType gossipType) {
         this.associatedGossip.removeInt(gossipType);
      }
   }
}
