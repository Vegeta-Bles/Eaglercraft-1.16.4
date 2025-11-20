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
      return RecordCodecBuilder.create(
            _snowmanx -> _snowmanx.group(
                     RecordCodecBuilder.point(updateListener),
                     Codec.BOOL.optionalFieldOf("Valid", false).forGetter(_snowmanxx -> _snowmanxx.valid),
                     PointOfInterest.createCodec(updateListener)
                        .listOf()
                        .fieldOf("Records")
                        .forGetter(_snowmanxx -> ImmutableList.copyOf(_snowmanxx.pointsOfInterestByPos.values()))
                  )
                  .apply(_snowmanx, PointOfInterestSet::new)
         )
         .orElseGet(Util.method_29188("Failed to read POI section: ", LOGGER::error), () -> new PointOfInterestSet(updateListener, false, ImmutableList.of()));
   }

   public PointOfInterestSet(Runnable updateListener) {
      this(updateListener, true, ImmutableList.of());
   }

   private PointOfInterestSet(Runnable updateListener, boolean _snowman, List<PointOfInterest> _snowman) {
      this.updateListener = updateListener;
      this.valid = _snowman;
      _snowman.forEach(this::add);
   }

   public Stream<PointOfInterest> get(Predicate<PointOfInterestType> _snowman, PointOfInterestStorage.OccupationStatus _snowman) {
      return this.pointsOfInterestByType
         .entrySet()
         .stream()
         .filter(_snowmanxxx -> _snowman.test((PointOfInterestType)_snowmanxxx.getKey()))
         .flatMap(_snowmanxx -> ((Set)_snowmanxx.getValue()).stream())
         .filter(_snowman.getPredicate());
   }

   public void add(BlockPos pos, PointOfInterestType type) {
      if (this.add(new PointOfInterest(pos, type, this.updateListener))) {
         LOGGER.debug("Added POI of type {} @ {}", new Supplier[]{() -> type, () -> pos});
         this.updateListener.run();
      }
   }

   private boolean add(PointOfInterest poi) {
      BlockPos _snowman = poi.getPos();
      PointOfInterestType _snowmanx = poi.getType();
      short _snowmanxx = ChunkSectionPos.packLocal(_snowman);
      PointOfInterest _snowmanxxx = (PointOfInterest)this.pointsOfInterestByPos.get(_snowmanxx);
      if (_snowmanxxx != null) {
         if (_snowmanx.equals(_snowmanxxx.getType())) {
            return false;
         } else {
            throw (IllegalStateException)Util.throwOrPause(new IllegalStateException("POI data mismatch: already registered at " + _snowman));
         }
      } else {
         this.pointsOfInterestByPos.put(_snowmanxx, poi);
         this.pointsOfInterestByType.computeIfAbsent(_snowmanx, _snowmanxxxx -> Sets.newHashSet()).add(poi);
         return true;
      }
   }

   public void remove(BlockPos pos) {
      PointOfInterest _snowman = (PointOfInterest)this.pointsOfInterestByPos.remove(ChunkSectionPos.packLocal(pos));
      if (_snowman == null) {
         LOGGER.error("POI data mismatch: never registered at " + pos);
      } else {
         this.pointsOfInterestByType.get(_snowman.getType()).remove(_snowman);
         LOGGER.debug("Removed POI of type {} @ {}", new Supplier[]{_snowman::getType, _snowman::getPos});
         this.updateListener.run();
      }
   }

   public boolean releaseTicket(BlockPos pos) {
      PointOfInterest _snowman = (PointOfInterest)this.pointsOfInterestByPos.get(ChunkSectionPos.packLocal(pos));
      if (_snowman == null) {
         throw (IllegalStateException)Util.throwOrPause(new IllegalStateException("POI never registered at " + pos));
      } else {
         boolean _snowmanx = _snowman.releaseTicket();
         this.updateListener.run();
         return _snowmanx;
      }
   }

   public boolean test(BlockPos pos, Predicate<PointOfInterestType> predicate) {
      short _snowman = ChunkSectionPos.packLocal(pos);
      PointOfInterest _snowmanx = (PointOfInterest)this.pointsOfInterestByPos.get(_snowman);
      return _snowmanx != null && predicate.test(_snowmanx.getType());
   }

   public Optional<PointOfInterestType> getType(BlockPos pos) {
      short _snowman = ChunkSectionPos.packLocal(pos);
      PointOfInterest _snowmanx = (PointOfInterest)this.pointsOfInterestByPos.get(_snowman);
      return _snowmanx != null ? Optional.of(_snowmanx.getType()) : Optional.empty();
   }

   public void updatePointsOfInterest(Consumer<BiConsumer<BlockPos, PointOfInterestType>> _snowman) {
      if (!this.valid) {
         Short2ObjectMap<PointOfInterest> _snowmanx = new Short2ObjectOpenHashMap(this.pointsOfInterestByPos);
         this.clear();
         _snowman.accept((_snowmanxxxx, _snowmanxxx) -> {
            short _snowmanxxx = ChunkSectionPos.packLocal(_snowmanxxxx);
            PointOfInterest _snowmanxxx = (PointOfInterest)_snowman.computeIfAbsent(_snowmanxxx, _snowmanxxxxxx -> new PointOfInterest(_snowmanxxxx, _snowmanxxx, this.updateListener));
            this.add(_snowmanxxx);
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
