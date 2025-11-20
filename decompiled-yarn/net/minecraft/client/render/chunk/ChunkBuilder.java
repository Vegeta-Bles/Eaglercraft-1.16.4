package net.minecraft.client.render.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.primitives.Doubles;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.thread.TaskExecutor;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkBuilder {
   private static final Logger LOGGER = LogManager.getLogger();
   private final PriorityQueue<ChunkBuilder.BuiltChunk.Task> rebuildQueue = Queues.newPriorityQueue();
   private final Queue<BlockBufferBuilderStorage> threadBuffers;
   private final Queue<Runnable> uploadQueue = Queues.newConcurrentLinkedQueue();
   private volatile int queuedTaskCount;
   private volatile int bufferCount;
   private final BlockBufferBuilderStorage buffers;
   private final TaskExecutor<Runnable> mailbox;
   private final Executor executor;
   private World world;
   private final WorldRenderer worldRenderer;
   private Vec3d cameraPosition = Vec3d.ZERO;

   public ChunkBuilder(World world, WorldRenderer worldRenderer, Executor executor, boolean is64Bits, BlockBufferBuilderStorage buffers) {
      this.world = world;
      this.worldRenderer = worldRenderer;
      int _snowman = Math.max(
         1,
         (int)((double)Runtime.getRuntime().maxMemory() * 0.3) / (RenderLayer.getBlockLayers().stream().mapToInt(RenderLayer::getExpectedBufferSize).sum() * 4)
            - 1
      );
      int _snowmanx = Runtime.getRuntime().availableProcessors();
      int _snowmanxx = is64Bits ? _snowmanx : Math.min(_snowmanx, 4);
      int _snowmanxxx = Math.max(1, Math.min(_snowmanxx, _snowman));
      this.buffers = buffers;
      List<BlockBufferBuilderStorage> _snowmanxxxx = Lists.newArrayListWithExpectedSize(_snowmanxxx);

      try {
         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxx; _snowmanxxxxx++) {
            _snowmanxxxx.add(new BlockBufferBuilderStorage());
         }
      } catch (OutOfMemoryError var14) {
         LOGGER.warn("Allocated only {}/{} buffers", _snowmanxxxx.size(), _snowmanxxx);
         int _snowmanxxxxx = Math.min(_snowmanxxxx.size() * 2 / 3, _snowmanxxxx.size() - 1);

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx; _snowmanxxxxxx++) {
            _snowmanxxxx.remove(_snowmanxxxx.size() - 1);
         }

         System.gc();
      }

      this.threadBuffers = Queues.newArrayDeque(_snowmanxxxx);
      this.bufferCount = this.threadBuffers.size();
      this.executor = executor;
      this.mailbox = TaskExecutor.create(executor, "Chunk Renderer");
      this.mailbox.send(this::scheduleRunTasks);
   }

   public void setWorld(World world) {
      this.world = world;
   }

   private void scheduleRunTasks() {
      if (!this.threadBuffers.isEmpty()) {
         ChunkBuilder.BuiltChunk.Task _snowman = this.rebuildQueue.poll();
         if (_snowman != null) {
            BlockBufferBuilderStorage _snowmanx = this.threadBuffers.poll();
            this.queuedTaskCount = this.rebuildQueue.size();
            this.bufferCount = this.threadBuffers.size();
            CompletableFuture.runAsync(() -> {
            }, this.executor).thenCompose(_snowmanxx -> _snowman.run(_snowman)).whenComplete((_snowmanxx, _snowmanxxx) -> {
               if (_snowmanxxx != null) {
                  CrashReport _snowmanxxxx = CrashReport.create(_snowmanxxx, "Batching chunks");
                  MinecraftClient.getInstance().setCrashReport(MinecraftClient.getInstance().addDetailsToCrashReport(_snowmanxxxx));
               } else {
                  this.mailbox.send(() -> {
                     if (_snowmanx == ChunkBuilder.Result.SUCCESSFUL) {
                        _snowman.clear();
                     } else {
                        _snowman.reset();
                     }

                     this.threadBuffers.add(_snowman);
                     this.bufferCount = this.threadBuffers.size();
                     this.scheduleRunTasks();
                  });
               }
            });
         }
      }
   }

   public String getDebugString() {
      return String.format("pC: %03d, pU: %02d, aB: %02d", this.queuedTaskCount, this.uploadQueue.size(), this.bufferCount);
   }

   public void setCameraPosition(Vec3d cameraPosition) {
      this.cameraPosition = cameraPosition;
   }

   public Vec3d getCameraPosition() {
      return this.cameraPosition;
   }

   public boolean upload() {
      boolean _snowman;
      Runnable _snowmanx;
      for (_snowman = false; (_snowmanx = this.uploadQueue.poll()) != null; _snowman = true) {
         _snowmanx.run();
      }

      return _snowman;
   }

   public void rebuild(ChunkBuilder.BuiltChunk chunk) {
      chunk.rebuild();
   }

   public void reset() {
      this.clear();
   }

   public void send(ChunkBuilder.BuiltChunk.Task task) {
      this.mailbox.send(() -> {
         this.rebuildQueue.offer(task);
         this.queuedTaskCount = this.rebuildQueue.size();
         this.scheduleRunTasks();
      });
   }

   public CompletableFuture<Void> scheduleUpload(BufferBuilder buffer, VertexBuffer glBuffer) {
      return CompletableFuture.runAsync(() -> {
      }, this.uploadQueue::add).thenCompose(_snowmanxx -> this.upload(buffer, glBuffer));
   }

   private CompletableFuture<Void> upload(BufferBuilder buffer, VertexBuffer glBuffer) {
      return glBuffer.submitUpload(buffer);
   }

   private void clear() {
      while (!this.rebuildQueue.isEmpty()) {
         ChunkBuilder.BuiltChunk.Task _snowman = this.rebuildQueue.poll();
         if (_snowman != null) {
            _snowman.cancel();
         }
      }

      this.queuedTaskCount = 0;
   }

   public boolean isEmpty() {
      return this.queuedTaskCount == 0 && this.uploadQueue.isEmpty();
   }

   public void stop() {
      this.clear();
      this.mailbox.close();
      this.threadBuffers.clear();
   }

   public class BuiltChunk {
      public final AtomicReference<ChunkBuilder.ChunkData> data = new AtomicReference<>(ChunkBuilder.ChunkData.EMPTY);
      @Nullable
      private ChunkBuilder.BuiltChunk.RebuildTask rebuildTask;
      @Nullable
      private ChunkBuilder.BuiltChunk.SortTask sortTask;
      private final Set<BlockEntity> blockEntities = Sets.newHashSet();
      private final Map<RenderLayer, VertexBuffer> buffers = RenderLayer.getBlockLayers()
         .stream()
         .collect(Collectors.toMap(_snowmanx -> _snowmanx, _snowmanx -> new VertexBuffer(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL)));
      public Box boundingBox;
      private int rebuildFrame = -1;
      private boolean needsRebuild = true;
      private final BlockPos.Mutable origin = new BlockPos.Mutable(-1, -1, -1);
      private final BlockPos.Mutable[] neighborPositions = Util.make(new BlockPos.Mutable[6], _snowmanx -> {
         for (int _snowmanx = 0; _snowmanx < _snowmanx.length; _snowmanx++) {
            _snowmanx[_snowmanx] = new BlockPos.Mutable();
         }
      });
      private boolean needsImportantRebuild;

      public BuiltChunk() {
      }

      private boolean isChunkNonEmpty(BlockPos pos) {
         return ChunkBuilder.this.world.getChunk(pos.getX() >> 4, pos.getZ() >> 4, ChunkStatus.FULL, false) != null;
      }

      public boolean shouldBuild() {
         int _snowman = 24;
         return !(this.getSquaredCameraDistance() > 576.0)
            ? true
            : this.isChunkNonEmpty(this.neighborPositions[Direction.WEST.ordinal()])
               && this.isChunkNonEmpty(this.neighborPositions[Direction.NORTH.ordinal()])
               && this.isChunkNonEmpty(this.neighborPositions[Direction.EAST.ordinal()])
               && this.isChunkNonEmpty(this.neighborPositions[Direction.SOUTH.ordinal()]);
      }

      public boolean setRebuildFrame(int frame) {
         if (this.rebuildFrame == frame) {
            return false;
         } else {
            this.rebuildFrame = frame;
            return true;
         }
      }

      public VertexBuffer getBuffer(RenderLayer layer) {
         return this.buffers.get(layer);
      }

      public void setOrigin(int x, int y, int z) {
         if (x != this.origin.getX() || y != this.origin.getY() || z != this.origin.getZ()) {
            this.clear();
            this.origin.set(x, y, z);
            this.boundingBox = new Box((double)x, (double)y, (double)z, (double)(x + 16), (double)(y + 16), (double)(z + 16));

            for (Direction _snowman : Direction.values()) {
               this.neighborPositions[_snowman.ordinal()].set(this.origin).move(_snowman, 16);
            }
         }
      }

      protected double getSquaredCameraDistance() {
         Camera _snowman = MinecraftClient.getInstance().gameRenderer.getCamera();
         double _snowmanx = this.boundingBox.minX + 8.0 - _snowman.getPos().x;
         double _snowmanxx = this.boundingBox.minY + 8.0 - _snowman.getPos().y;
         double _snowmanxxx = this.boundingBox.minZ + 8.0 - _snowman.getPos().z;
         return _snowmanx * _snowmanx + _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx;
      }

      private void beginBufferBuilding(BufferBuilder buffer) {
         buffer.begin(7, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL);
      }

      public ChunkBuilder.ChunkData getData() {
         return this.data.get();
      }

      private void clear() {
         this.cancel();
         this.data.set(ChunkBuilder.ChunkData.EMPTY);
         this.needsRebuild = true;
      }

      public void delete() {
         this.clear();
         this.buffers.values().forEach(VertexBuffer::close);
      }

      public BlockPos getOrigin() {
         return this.origin;
      }

      public void scheduleRebuild(boolean important) {
         boolean _snowman = this.needsRebuild;
         this.needsRebuild = true;
         this.needsImportantRebuild = important | (_snowman && this.needsImportantRebuild);
      }

      public void cancelRebuild() {
         this.needsRebuild = false;
         this.needsImportantRebuild = false;
      }

      public boolean needsRebuild() {
         return this.needsRebuild;
      }

      public boolean needsImportantRebuild() {
         return this.needsRebuild && this.needsImportantRebuild;
      }

      public BlockPos getNeighborPosition(Direction direction) {
         return this.neighborPositions[direction.ordinal()];
      }

      public boolean scheduleSort(RenderLayer layer, ChunkBuilder chunkRenderer) {
         ChunkBuilder.ChunkData _snowman = this.getData();
         if (this.sortTask != null) {
            this.sortTask.cancel();
         }

         if (!_snowman.initializedLayers.contains(layer)) {
            return false;
         } else {
            this.sortTask = new ChunkBuilder.BuiltChunk.SortTask(this.getSquaredCameraDistance(), _snowman);
            chunkRenderer.send(this.sortTask);
            return true;
         }
      }

      protected void cancel() {
         if (this.rebuildTask != null) {
            this.rebuildTask.cancel();
            this.rebuildTask = null;
         }

         if (this.sortTask != null) {
            this.sortTask.cancel();
            this.sortTask = null;
         }
      }

      public ChunkBuilder.BuiltChunk.Task createRebuildTask() {
         this.cancel();
         BlockPos _snowman = this.origin.toImmutable();
         int _snowmanx = 1;
         ChunkRendererRegion _snowmanxx = ChunkRendererRegion.create(ChunkBuilder.this.world, _snowman.add(-1, -1, -1), _snowman.add(16, 16, 16), 1);
         this.rebuildTask = new ChunkBuilder.BuiltChunk.RebuildTask(this.getSquaredCameraDistance(), _snowmanxx);
         return this.rebuildTask;
      }

      public void scheduleRebuild(ChunkBuilder chunkRenderer) {
         ChunkBuilder.BuiltChunk.Task _snowman = this.createRebuildTask();
         chunkRenderer.send(_snowman);
      }

      private void setNoCullingBlockEntities(Set<BlockEntity> noCullingBlockEntities) {
         Set<BlockEntity> _snowman = Sets.newHashSet(noCullingBlockEntities);
         Set<BlockEntity> _snowmanx = Sets.newHashSet(this.blockEntities);
         _snowman.removeAll(this.blockEntities);
         _snowmanx.removeAll(noCullingBlockEntities);
         this.blockEntities.clear();
         this.blockEntities.addAll(noCullingBlockEntities);
         ChunkBuilder.this.worldRenderer.updateNoCullingBlockEntities(_snowmanx, _snowman);
      }

      public void rebuild() {
         ChunkBuilder.BuiltChunk.Task _snowman = this.createRebuildTask();
         _snowman.run(ChunkBuilder.this.buffers);
      }

      class RebuildTask extends ChunkBuilder.BuiltChunk.Task {
         @Nullable
         protected ChunkRendererRegion region;

         public RebuildTask(double var2, ChunkRendererRegion var4) {
            super(_snowman);
            this.region = _snowman;
         }

         @Override
         public CompletableFuture<ChunkBuilder.Result> run(BlockBufferBuilderStorage buffers) {
            if (this.cancelled.get()) {
               return CompletableFuture.completedFuture(ChunkBuilder.Result.CANCELLED);
            } else if (!BuiltChunk.this.shouldBuild()) {
               this.region = null;
               BuiltChunk.this.scheduleRebuild(false);
               this.cancelled.set(true);
               return CompletableFuture.completedFuture(ChunkBuilder.Result.CANCELLED);
            } else if (this.cancelled.get()) {
               return CompletableFuture.completedFuture(ChunkBuilder.Result.CANCELLED);
            } else {
               Vec3d _snowman = ChunkBuilder.this.getCameraPosition();
               float _snowmanx = (float)_snowman.x;
               float _snowmanxx = (float)_snowman.y;
               float _snowmanxxx = (float)_snowman.z;
               ChunkBuilder.ChunkData _snowmanxxxx = new ChunkBuilder.ChunkData();
               Set<BlockEntity> _snowmanxxxxx = this.render(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, buffers);
               BuiltChunk.this.setNoCullingBlockEntities(_snowmanxxxxx);
               if (this.cancelled.get()) {
                  return CompletableFuture.completedFuture(ChunkBuilder.Result.CANCELLED);
               } else {
                  List<CompletableFuture<Void>> _snowmanxxxxxx = Lists.newArrayList();
                  _snowmanxxxx.initializedLayers
                     .forEach(_snowmanxxxxxxx -> _snowman.add(ChunkBuilder.this.scheduleUpload(buffers.get(_snowmanxxxxxxx), BuiltChunk.this.getBuffer(_snowmanxxxxxxx))));
                  return Util.combine(_snowmanxxxxxx).handle((_snowmanxxxxxxx, _snowmanxxxxxxxx) -> {
                     if (_snowmanxxxxxxxx != null && !(_snowmanxxxxxxxx instanceof CancellationException) && !(_snowmanxxxxxxxx instanceof InterruptedException)) {
                        MinecraftClient.getInstance().setCrashReport(CrashReport.create(_snowmanxxxxxxxx, "Rendering chunk"));
                     }

                     if (this.cancelled.get()) {
                        return ChunkBuilder.Result.CANCELLED;
                     } else {
                        BuiltChunk.this.data.set(_snowman);
                        return ChunkBuilder.Result.SUCCESSFUL;
                     }
                  });
               }
            }
         }

         private Set<BlockEntity> render(float cameraX, float cameraY, float cameraZ, ChunkBuilder.ChunkData data, BlockBufferBuilderStorage buffers) {
            int _snowman = 1;
            BlockPos _snowmanx = BuiltChunk.this.origin.toImmutable();
            BlockPos _snowmanxx = _snowmanx.add(15, 15, 15);
            ChunkOcclusionDataBuilder _snowmanxxx = new ChunkOcclusionDataBuilder();
            Set<BlockEntity> _snowmanxxxx = Sets.newHashSet();
            ChunkRendererRegion _snowmanxxxxx = this.region;
            this.region = null;
            MatrixStack _snowmanxxxxxx = new MatrixStack();
            if (_snowmanxxxxx != null) {
               BlockModelRenderer.enableBrightnessCache();
               Random _snowmanxxxxxxx = new Random();
               BlockRenderManager _snowmanxxxxxxxx = MinecraftClient.getInstance().getBlockRenderManager();

               for (BlockPos _snowmanxxxxxxxxx : BlockPos.iterate(_snowmanx, _snowmanxx)) {
                  BlockState _snowmanxxxxxxxxxx = _snowmanxxxxx.getBlockState(_snowmanxxxxxxxxx);
                  Block _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.getBlock();
                  if (_snowmanxxxxxxxxxx.isOpaqueFullCube(_snowmanxxxxx, _snowmanxxxxxxxxx)) {
                     _snowmanxxx.markClosed(_snowmanxxxxxxxxx);
                  }

                  if (_snowmanxxxxxxxxxxx.hasBlockEntity()) {
                     BlockEntity _snowmanxxxxxxxxxxxx = _snowmanxxxxx.getBlockEntity(_snowmanxxxxxxxxx, WorldChunk.CreationType.CHECK);
                     if (_snowmanxxxxxxxxxxxx != null) {
                        this.addBlockEntity(data, _snowmanxxxx, _snowmanxxxxxxxxxxxx);
                     }
                  }

                  FluidState _snowmanxxxxxxxxxxxx = _snowmanxxxxx.getFluidState(_snowmanxxxxxxxxx);
                  if (!_snowmanxxxxxxxxxxxx.isEmpty()) {
                     RenderLayer _snowmanxxxxxxxxxxxxx = RenderLayers.getFluidLayer(_snowmanxxxxxxxxxxxx);
                     BufferBuilder _snowmanxxxxxxxxxxxxxx = buffers.get(_snowmanxxxxxxxxxxxxx);
                     if (data.initializedLayers.add(_snowmanxxxxxxxxxxxxx)) {
                        BuiltChunk.this.beginBufferBuilding(_snowmanxxxxxxxxxxxxxx);
                     }

                     if (_snowmanxxxxxxxx.renderFluid(_snowmanxxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx)) {
                        data.empty = false;
                        data.nonEmptyLayers.add(_snowmanxxxxxxxxxxxxx);
                     }
                  }

                  if (_snowmanxxxxxxxxxx.getRenderType() != BlockRenderType.INVISIBLE) {
                     RenderLayer _snowmanxxxxxxxxxxxxxxx = RenderLayers.getBlockLayer(_snowmanxxxxxxxxxx);
                     BufferBuilder _snowmanxxxxxxxxxxxxxxxx = buffers.get(_snowmanxxxxxxxxxxxxxxx);
                     if (data.initializedLayers.add(_snowmanxxxxxxxxxxxxxxx)) {
                        BuiltChunk.this.beginBufferBuilding(_snowmanxxxxxxxxxxxxxxxx);
                     }

                     _snowmanxxxxxx.push();
                     _snowmanxxxxxx.translate((double)(_snowmanxxxxxxxxx.getX() & 15), (double)(_snowmanxxxxxxxxx.getY() & 15), (double)(_snowmanxxxxxxxxx.getZ() & 15));
                     if (_snowmanxxxxxxxx.renderBlock(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxxxxx, true, _snowmanxxxxxxx)) {
                        data.empty = false;
                        data.nonEmptyLayers.add(_snowmanxxxxxxxxxxxxxxx);
                     }

                     _snowmanxxxxxx.pop();
                  }
               }

               if (data.nonEmptyLayers.contains(RenderLayer.getTranslucent())) {
                  BufferBuilder _snowmanxxxxxxxxx = buffers.get(RenderLayer.getTranslucent());
                  _snowmanxxxxxxxxx.sortQuads(cameraX - (float)_snowmanx.getX(), cameraY - (float)_snowmanx.getY(), cameraZ - (float)_snowmanx.getZ());
                  data.bufferState = _snowmanxxxxxxxxx.popState();
               }

               data.initializedLayers.stream().map(buffers::get).forEach(BufferBuilder::end);
               BlockModelRenderer.disableBrightnessCache();
            }

            data.occlusionGraph = _snowmanxxx.build();
            return _snowmanxxxx;
         }

         private <E extends BlockEntity> void addBlockEntity(ChunkBuilder.ChunkData data, Set<BlockEntity> blockEntities, E blockEntity) {
            BlockEntityRenderer<E> _snowman = BlockEntityRenderDispatcher.INSTANCE.get(blockEntity);
            if (_snowman != null) {
               data.blockEntities.add(blockEntity);
               if (_snowman.rendersOutsideBoundingBox(blockEntity)) {
                  blockEntities.add(blockEntity);
               }
            }
         }

         @Override
         public void cancel() {
            this.region = null;
            if (this.cancelled.compareAndSet(false, true)) {
               BuiltChunk.this.scheduleRebuild(false);
            }
         }
      }

      class SortTask extends ChunkBuilder.BuiltChunk.Task {
         private final ChunkBuilder.ChunkData data;

         public SortTask(double var2, ChunkBuilder.ChunkData var4) {
            super(_snowman);
            this.data = _snowman;
         }

         @Override
         public CompletableFuture<ChunkBuilder.Result> run(BlockBufferBuilderStorage buffers) {
            if (this.cancelled.get()) {
               return CompletableFuture.completedFuture(ChunkBuilder.Result.CANCELLED);
            } else if (!BuiltChunk.this.shouldBuild()) {
               this.cancelled.set(true);
               return CompletableFuture.completedFuture(ChunkBuilder.Result.CANCELLED);
            } else if (this.cancelled.get()) {
               return CompletableFuture.completedFuture(ChunkBuilder.Result.CANCELLED);
            } else {
               Vec3d _snowman = ChunkBuilder.this.getCameraPosition();
               float _snowmanx = (float)_snowman.x;
               float _snowmanxx = (float)_snowman.y;
               float _snowmanxxx = (float)_snowman.z;
               BufferBuilder.State _snowmanxxxx = this.data.bufferState;
               if (_snowmanxxxx != null && this.data.nonEmptyLayers.contains(RenderLayer.getTranslucent())) {
                  BufferBuilder _snowmanxxxxx = buffers.get(RenderLayer.getTranslucent());
                  BuiltChunk.this.beginBufferBuilding(_snowmanxxxxx);
                  _snowmanxxxxx.restoreState(_snowmanxxxx);
                  _snowmanxxxxx.sortQuads(
                     _snowmanx - (float)BuiltChunk.this.origin.getX(), _snowmanxx - (float)BuiltChunk.this.origin.getY(), _snowmanxxx - (float)BuiltChunk.this.origin.getZ()
                  );
                  this.data.bufferState = _snowmanxxxxx.popState();
                  _snowmanxxxxx.end();
                  if (this.cancelled.get()) {
                     return CompletableFuture.completedFuture(ChunkBuilder.Result.CANCELLED);
                  } else {
                     CompletableFuture<ChunkBuilder.Result> _snowmanxxxxxx = ChunkBuilder.this.scheduleUpload(
                           buffers.get(RenderLayer.getTranslucent()), BuiltChunk.this.getBuffer(RenderLayer.getTranslucent())
                        )
                        .thenApply(_snowmanxxxxxxx -> ChunkBuilder.Result.CANCELLED);
                     return _snowmanxxxxxx.handle((_snowmanxxxxxxx, _snowmanxxxxxxxx) -> {
                        if (_snowmanxxxxxxxx != null && !(_snowmanxxxxxxxx instanceof CancellationException) && !(_snowmanxxxxxxxx instanceof InterruptedException)) {
                           MinecraftClient.getInstance().setCrashReport(CrashReport.create(_snowmanxxxxxxxx, "Rendering chunk"));
                        }

                        return this.cancelled.get() ? ChunkBuilder.Result.CANCELLED : ChunkBuilder.Result.SUCCESSFUL;
                     });
                  }
               } else {
                  return CompletableFuture.completedFuture(ChunkBuilder.Result.CANCELLED);
               }
            }
         }

         @Override
         public void cancel() {
            this.cancelled.set(true);
         }
      }

      abstract class Task implements Comparable<ChunkBuilder.BuiltChunk.Task> {
         protected final double distance;
         protected final AtomicBoolean cancelled = new AtomicBoolean(false);

         public Task(double var2) {
            this.distance = _snowman;
         }

         public abstract CompletableFuture<ChunkBuilder.Result> run(BlockBufferBuilderStorage buffers);

         public abstract void cancel();

         public int compareTo(ChunkBuilder.BuiltChunk.Task _snowman) {
            return Doubles.compare(this.distance, _snowman.distance);
         }
      }
   }

   public static class ChunkData {
      public static final ChunkBuilder.ChunkData EMPTY = new ChunkBuilder.ChunkData() {
         @Override
         public boolean isVisibleThrough(Direction _snowman, Direction _snowman) {
            return false;
         }
      };
      private final Set<RenderLayer> nonEmptyLayers = new ObjectArraySet();
      private final Set<RenderLayer> initializedLayers = new ObjectArraySet();
      private boolean empty = true;
      private final List<BlockEntity> blockEntities = Lists.newArrayList();
      private ChunkOcclusionData occlusionGraph = new ChunkOcclusionData();
      @Nullable
      private BufferBuilder.State bufferState;

      public ChunkData() {
      }

      public boolean isEmpty() {
         return this.empty;
      }

      public boolean isEmpty(RenderLayer layer) {
         return !this.nonEmptyLayers.contains(layer);
      }

      public List<BlockEntity> getBlockEntities() {
         return this.blockEntities;
      }

      public boolean isVisibleThrough(Direction _snowman, Direction _snowman) {
         return this.occlusionGraph.isVisibleThrough(_snowman, _snowman);
      }
   }

   static enum Result {
      SUCCESSFUL,
      CANCELLED;

      private Result() {
      }
   }
}
