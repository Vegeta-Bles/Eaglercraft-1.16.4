package net.minecraft.world.poi;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BooleanSupplier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.block.BlockState;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.SectionDistanceLevelPropagator;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.storage.SerializingRegionBasedStorage;

public class PointOfInterestStorage extends SerializingRegionBasedStorage<PointOfInterestSet> {
   private final PointOfInterestStorage.PointOfInterestDistanceTracker pointOfInterestDistanceTracker;
   private final LongSet preloadedChunks = new LongOpenHashSet();

   public PointOfInterestStorage(File _snowman, DataFixer _snowman, boolean _snowman) {
      super(_snowman, PointOfInterestSet::createCodec, PointOfInterestSet::new, _snowman, DataFixTypes.POI_CHUNK, _snowman);
      this.pointOfInterestDistanceTracker = new PointOfInterestStorage.PointOfInterestDistanceTracker();
   }

   public void add(BlockPos pos, PointOfInterestType type) {
      this.getOrCreate(ChunkSectionPos.from(pos).asLong()).add(pos, type);
   }

   public void remove(BlockPos pos) {
      this.getOrCreate(ChunkSectionPos.from(pos).asLong()).remove(pos);
   }

   public long count(Predicate<PointOfInterestType> typePredicate, BlockPos pos, int radius, PointOfInterestStorage.OccupationStatus occupationStatus) {
      return this.getInCircle(typePredicate, pos, radius, occupationStatus).count();
   }

   public boolean hasTypeAt(PointOfInterestType type, BlockPos pos) {
      Optional<PointOfInterestType> _snowman = this.getOrCreate(ChunkSectionPos.from(pos).asLong()).getType(pos);
      return _snowman.isPresent() && _snowman.get().equals(type);
   }

   public Stream<PointOfInterest> getInSquare(Predicate<PointOfInterestType> typePredicate, BlockPos pos, int radius, PointOfInterestStorage.OccupationStatus _snowman) {
      int _snowmanx = Math.floorDiv(radius, 16) + 1;
      return ChunkPos.stream(new ChunkPos(pos), _snowmanx).flatMap(_snowmanxx -> this.getInChunk(typePredicate, _snowmanxx, _snowman)).filter(_snowmanxxx -> {
         BlockPos _snowmanxxx = _snowmanxxx.getPos();
         return Math.abs(_snowmanxxx.getX() - pos.getX()) <= radius && Math.abs(_snowmanxxx.getZ() - pos.getZ()) <= radius;
      });
   }

   public Stream<PointOfInterest> getInCircle(
      Predicate<PointOfInterestType> typePredicate, BlockPos pos, int radius, PointOfInterestStorage.OccupationStatus occupationStatus
   ) {
      int _snowman = radius * radius;
      return this.getInSquare(typePredicate, pos, radius, occupationStatus).filter(_snowmanxx -> _snowmanxx.getPos().getSquaredDistance(pos) <= (double)_snowman);
   }

   public Stream<PointOfInterest> getInChunk(Predicate<PointOfInterestType> _snowman, ChunkPos _snowman, PointOfInterestStorage.OccupationStatus occupationStatus) {
      return IntStream.range(0, 16)
         .boxed()
         .map(_snowmanxx -> this.get(ChunkSectionPos.from(_snowman, _snowmanxx).asLong()))
         .filter(Optional::isPresent)
         .flatMap(_snowmanxxx -> ((PointOfInterestSet)_snowmanxxx.get()).get(_snowman, occupationStatus));
   }

   public Stream<BlockPos> getPositions(
      Predicate<PointOfInterestType> typePredicate,
      Predicate<BlockPos> posPredicate,
      BlockPos pos,
      int radius,
      PointOfInterestStorage.OccupationStatus occupationStatus
   ) {
      return this.getInCircle(typePredicate, pos, radius, occupationStatus).map(PointOfInterest::getPos).filter(posPredicate);
   }

   public Stream<BlockPos> method_30957(Predicate<PointOfInterestType> _snowman, Predicate<BlockPos> _snowman, BlockPos _snowman, int _snowman, PointOfInterestStorage.OccupationStatus _snowman) {
      return this.getPositions(_snowman, _snowman, _snowman, _snowman, _snowman).sorted(Comparator.comparingDouble(_snowmanxxxxxx -> _snowmanxxxxxx.getSquaredDistance(_snowman)));
   }

   public Optional<BlockPos> getPosition(
      Predicate<PointOfInterestType> typePredicate,
      Predicate<BlockPos> posPredicate,
      BlockPos pos,
      int radius,
      PointOfInterestStorage.OccupationStatus occupationStatus
   ) {
      return this.getPositions(typePredicate, posPredicate, pos, radius, occupationStatus).findFirst();
   }

   public Optional<BlockPos> getNearestPosition(Predicate<PointOfInterestType> typePredicate, BlockPos _snowman, int _snowman, PointOfInterestStorage.OccupationStatus _snowman) {
      return this.getInCircle(typePredicate, _snowman, _snowman, _snowman).map(PointOfInterest::getPos).min(Comparator.comparingDouble(_snowmanxxxx -> _snowmanxxxx.getSquaredDistance(_snowman)));
   }

   public Optional<BlockPos> getPosition(Predicate<PointOfInterestType> typePredicate, Predicate<BlockPos> positionPredicate, BlockPos pos, int radius) {
      return this.getInCircle(typePredicate, pos, radius, PointOfInterestStorage.OccupationStatus.HAS_SPACE)
         .filter(_snowmanx -> positionPredicate.test(_snowmanx.getPos()))
         .findFirst()
         .map(_snowman -> {
            _snowman.reserveTicket();
            return _snowman.getPos();
         });
   }

   public Optional<BlockPos> getPosition(
      Predicate<PointOfInterestType> typePredicate,
      Predicate<BlockPos> positionPredicate,
      PointOfInterestStorage.OccupationStatus occupationStatus,
      BlockPos pos,
      int radius,
      Random random
   ) {
      List<PointOfInterest> _snowman = this.getInCircle(typePredicate, pos, radius, occupationStatus).collect(Collectors.toList());
      Collections.shuffle(_snowman, random);
      return _snowman.stream().filter(_snowmanx -> positionPredicate.test(_snowmanx.getPos())).findFirst().map(PointOfInterest::getPos);
   }

   public boolean releaseTicket(BlockPos pos) {
      return this.getOrCreate(ChunkSectionPos.from(pos).asLong()).releaseTicket(pos);
   }

   public boolean test(BlockPos pos, Predicate<PointOfInterestType> predicate) {
      return this.get(ChunkSectionPos.from(pos).asLong()).map(_snowmanxx -> _snowmanxx.test(pos, predicate)).orElse(false);
   }

   public Optional<PointOfInterestType> getType(BlockPos pos) {
      PointOfInterestSet _snowman = this.getOrCreate(ChunkSectionPos.from(pos).asLong());
      return _snowman.getType(pos);
   }

   public int getDistanceFromNearestOccupied(ChunkSectionPos pos) {
      this.pointOfInterestDistanceTracker.update();
      return this.pointOfInterestDistanceTracker.getLevel(pos.asLong());
   }

   private boolean isOccupied(long pos) {
      Optional<PointOfInterestSet> _snowman = this.getIfLoaded(pos);
      return _snowman == null
         ? false
         : _snowman.<Boolean>map(_snowmanx -> _snowmanx.get(PointOfInterestType.ALWAYS_TRUE, PointOfInterestStorage.OccupationStatus.IS_OCCUPIED).count() > 0L).orElse(false);
   }

   @Override
   public void tick(BooleanSupplier shouldKeepTicking) {
      super.tick(shouldKeepTicking);
      this.pointOfInterestDistanceTracker.update();
   }

   @Override
   protected void onUpdate(long pos) {
      super.onUpdate(pos);
      this.pointOfInterestDistanceTracker.update(pos, this.pointOfInterestDistanceTracker.getInitialLevel(pos), false);
   }

   @Override
   protected void onLoad(long pos) {
      this.pointOfInterestDistanceTracker.update(pos, this.pointOfInterestDistanceTracker.getInitialLevel(pos), false);
   }

   public void initForPalette(ChunkPos _snowman, ChunkSection _snowman) {
      ChunkSectionPos _snowmanxx = ChunkSectionPos.from(_snowman, _snowman.getYOffset() >> 4);
      Util.ifPresentOrElse(this.get(_snowmanxx.asLong()), _snowmanxxx -> _snowmanxxx.updatePointsOfInterest(_snowmanxxxxx -> {
            if (shouldScan(_snowman)) {
               this.scanAndPopulate(_snowman, _snowman, _snowmanxxxxx);
            }
         }), () -> {
         if (shouldScan(_snowman)) {
            PointOfInterestSet _snowmanxxx = this.getOrCreate(_snowman.asLong());
            this.scanAndPopulate(_snowman, _snowman, _snowmanxxx::add);
         }
      });
   }

   private static boolean shouldScan(ChunkSection _snowman) {
      return _snowman.hasAny(PointOfInterestType.REGISTERED_STATES::contains);
   }

   private void scanAndPopulate(ChunkSection _snowman, ChunkSectionPos _snowman, BiConsumer<BlockPos, PointOfInterestType> _snowman) {
      _snowman.streamBlocks()
         .forEach(
            _snowmanxxxxx -> {
               BlockState _snowmanxxx = _snowman.getBlockState(
                  ChunkSectionPos.getLocalCoord(_snowmanxxxxx.getX()), ChunkSectionPos.getLocalCoord(_snowmanxxxxx.getY()), ChunkSectionPos.getLocalCoord(_snowmanxxxxx.getZ())
               );
               PointOfInterestType.from(_snowmanxxx).ifPresent(_snowmanxxxxxxxx -> _snowman.accept(_snowmanxxxxx, _snowmanxxxxxxxx));
            }
         );
   }

   public void preloadChunks(WorldView world, BlockPos pos, int radius) {
      ChunkSectionPos.stream(new ChunkPos(pos), Math.floorDiv(radius, 16))
         .map(_snowman -> Pair.of(_snowman, this.get(_snowman.asLong())))
         .filter(_snowman -> !((Optional)_snowman.getSecond()).map(PointOfInterestSet::isValid).orElse(false))
         .map(_snowman -> ((ChunkSectionPos)_snowman.getFirst()).toChunkPos())
         .filter(_snowman -> this.preloadedChunks.add(_snowman.toLong()))
         .forEach(_snowmanx -> world.getChunk(_snowmanx.x, _snowmanx.z, ChunkStatus.EMPTY));
   }

   public static enum OccupationStatus {
      HAS_SPACE(PointOfInterest::hasSpace),
      IS_OCCUPIED(PointOfInterest::isOccupied),
      ANY(_snowman -> true);

      private final Predicate<? super PointOfInterest> predicate;

      private OccupationStatus(Predicate<? super PointOfInterest> var3) {
         this.predicate = _snowman;
      }

      public Predicate<? super PointOfInterest> getPredicate() {
         return this.predicate;
      }
   }

   final class PointOfInterestDistanceTracker extends SectionDistanceLevelPropagator {
      private final Long2ByteMap distances = new Long2ByteOpenHashMap();

      protected PointOfInterestDistanceTracker() {
         super(7, 16, 256);
         this.distances.defaultReturnValue((byte)7);
      }

      @Override
      protected int getInitialLevel(long id) {
         return PointOfInterestStorage.this.isOccupied(id) ? 0 : 7;
      }

      @Override
      protected int getLevel(long id) {
         return this.distances.get(id);
      }

      @Override
      protected void setLevel(long id, int level) {
         if (level > 6) {
            this.distances.remove(id);
         } else {
            this.distances.put(id, (byte)level);
         }
      }

      public void update() {
         super.applyPendingUpdates(Integer.MAX_VALUE);
      }
   }
}
