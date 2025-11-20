package net.minecraft.world.poi;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;

public class PointOfInterestSet {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Short2ObjectMap<PointOfInterest> pointsOfInterestByPos = new Short2ObjectOpenHashMap();
   private final Map<PointOfInterestType, Set<PointOfInterest>> pointsOfInterestByType = Maps.newHashMap();
   private final Runnable updateListener;
   private boolean valid;

   public static Codec<PointOfInterestSet> createCodec(Runnable updateListener) {
      return RecordCodecBuilder.<PointOfInterestSet>create(
            instance -> instance.group(
                     RecordCodecBuilder.point(updateListener),
                     Codec.BOOL.optionalFieldOf("Valid", false).forGetter(set -> set.valid),
                     PointOfInterest.createCodec(updateListener)
                        .listOf()
                        .fieldOf("Records")
                        .forGetter(set -> ImmutableList.copyOf(set.pointsOfInterestByPos.values()))
                  )
                  .apply(instance, PointOfInterestSet::new)
         )
         .orElseGet(Util.method_29188("Failed to read POI section: ", LOGGER::error), () -> new PointOfInterestSet(updateListener, false, ImmutableList.of()));
   }

   public PointOfInterestSet(Runnable updateListener) {
      this(updateListener, true, ImmutableList.of());
   }

   private PointOfInterestSet(Runnable updateListener, boolean bl, List<PointOfInterest> list) {
      this.updateListener = updateListener;
      this.valid = bl;
      list.forEach(this::add);
   }

   public Stream<PointOfInterest> get(Predicate<PointOfInterestType> predicate, PointOfInterestStorage.OccupationStatus arg) {
      return this.pointsOfInterestByType
         .entrySet()
         .stream()
         .filter(entry -> predicate.test(entry.getKey()))
         .flatMap(entry -> entry.getValue().stream())
         .filter(arg.getPredicate());
   }

   public void add(BlockPos pos, PointOfInterestType type) {
      if (this.add(new PointOfInterest(pos, type, this.updateListener))) {
         LOGGER.debug("Added POI of type {} @ {}", new Supplier[]{() -> type, () -> pos});
         this.updateListener.run();
      }
   }

   private boolean add(PointOfInterest poi) {
      BlockPos lv = poi.getPos();
      PointOfInterestType lv2 = poi.getType();
      short s = ChunkSectionPos.packLocal(lv);
      PointOfInterest lv3 = (PointOfInterest)this.pointsOfInterestByPos.get(s);
      if (lv3 != null) {
         if (lv2.equals(lv3.getType())) {
            return false;
         } else {
            throw (IllegalStateException)Util.throwOrPause(new IllegalStateException("POI data mismatch: already registered at " + lv));
         }
      } else {
         this.pointsOfInterestByPos.put(s, poi);
         this.pointsOfInterestByType.computeIfAbsent(lv2, arg -> Sets.newHashSet()).add(poi);
         return true;
      }
   }

   public void remove(BlockPos pos) {
      PointOfInterest lv = (PointOfInterest)this.pointsOfInterestByPos.remove(ChunkSectionPos.packLocal(pos));
      if (lv == null) {
         LOGGER.error("POI data mismatch: never registered at " + pos);
      } else {
         this.pointsOfInterestByType.get(lv.getType()).remove(lv);
         LOGGER.debug("Removed POI of type {} @ {}", new Supplier[]{lv::getType, lv::getPos});
         this.updateListener.run();
      }
   }

   public boolean releaseTicket(BlockPos pos) {
      PointOfInterest lv = (PointOfInterest)this.pointsOfInterestByPos.get(ChunkSectionPos.packLocal(pos));
      if (lv == null) {
         throw (IllegalStateException)Util.throwOrPause(new IllegalStateException("POI never registered at " + pos));
      } else {
         boolean bl = lv.releaseTicket();
         this.updateListener.run();
         return bl;
      }
   }

   public boolean test(BlockPos pos, Predicate<PointOfInterestType> predicate) {
      short s = ChunkSectionPos.packLocal(pos);
      PointOfInterest lv = (PointOfInterest)this.pointsOfInterestByPos.get(s);
      return lv != null && predicate.test(lv.getType());
   }

   public Optional<PointOfInterestType> getType(BlockPos pos) {
      short s = ChunkSectionPos.packLocal(pos);
      PointOfInterest lv = (PointOfInterest)this.pointsOfInterestByPos.get(s);
      return lv != null ? Optional.of(lv.getType()) : Optional.empty();
   }

   public void updatePointsOfInterest(Consumer<BiConsumer<BlockPos, PointOfInterestType>> consumer) {
      if (!this.valid) {
         Short2ObjectMap<PointOfInterest> short2ObjectMap = new Short2ObjectOpenHashMap(this.pointsOfInterestByPos);
         this.clear();
         consumer.accept((arg, arg2) -> {
            short s = ChunkSectionPos.packLocal(arg);
            PointOfInterest lv = (PointOfInterest)short2ObjectMap.computeIfAbsent(s, i -> new PointOfInterest(arg, arg2, this.updateListener));
            this.add(lv);
         });
         this.valid = true;
         this.updateListener.run();
      }
   }

   private void clear() {
      this.pointsOfInterestByPos.clear();
      this.pointsOfInterestByType.clear();
   }

   boolean isValid() {
      return this.valid;
   }
}
