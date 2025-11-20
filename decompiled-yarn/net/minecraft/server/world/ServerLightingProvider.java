package net.minecraft.server.world;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntSupplier;
import javax.annotation.Nullable;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.thread.MessageListener;
import net.minecraft.util.thread.TaskExecutor;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.light.LightingProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerLightingProvider extends LightingProvider implements AutoCloseable {
   private static final Logger LOGGER = LogManager.getLogger();
   private final TaskExecutor<Runnable> processor;
   private final ObjectList<Pair<ServerLightingProvider.Stage, Runnable>> pendingTasks = new ObjectArrayList();
   private final ThreadedAnvilChunkStorage chunkStorage;
   private final MessageListener<ChunkTaskPrioritySystem.Task<Runnable>> executor;
   private volatile int taskBatchSize = 5;
   private final AtomicBoolean ticking = new AtomicBoolean();

   public ServerLightingProvider(
      ChunkProvider chunkProvider,
      ThreadedAnvilChunkStorage chunkStorage,
      boolean hasBlockLight,
      TaskExecutor<Runnable> processor,
      MessageListener<ChunkTaskPrioritySystem.Task<Runnable>> executor
   ) {
      super(chunkProvider, true, hasBlockLight);
      this.chunkStorage = chunkStorage;
      this.executor = executor;
      this.processor = processor;
   }

   @Override
   public void close() {
   }

   @Override
   public int doLightUpdates(int maxUpdateCount, boolean doSkylight, boolean skipEdgeLightPropagation) {
      throw (UnsupportedOperationException)Util.throwOrPause(new UnsupportedOperationException("Ran authomatically on a different thread!"));
   }

   @Override
   public void addLightSource(BlockPos pos, int level) {
      throw (UnsupportedOperationException)Util.throwOrPause(new UnsupportedOperationException("Ran authomatically on a different thread!"));
   }

   @Override
   public void checkBlock(BlockPos pos) {
      BlockPos _snowman = pos.toImmutable();
      this.enqueue(
         pos.getX() >> 4, pos.getZ() >> 4, ServerLightingProvider.Stage.POST_UPDATE, Util.debugRunnable(() -> super.checkBlock(_snowman), () -> "checkBlock " + _snowman)
      );
   }

   protected void updateChunkStatus(ChunkPos pos) {
      this.enqueue(pos.x, pos.z, () -> 0, ServerLightingProvider.Stage.PRE_UPDATE, Util.debugRunnable(() -> {
         super.setRetainData(pos, false);
         super.setColumnEnabled(pos, false);

         for (int _snowmanx = -1; _snowmanx < 17; _snowmanx++) {
            super.enqueueSectionData(LightType.BLOCK, ChunkSectionPos.from(pos, _snowmanx), null, true);
            super.enqueueSectionData(LightType.SKY, ChunkSectionPos.from(pos, _snowmanx), null, true);
         }

         for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
            super.setSectionStatus(ChunkSectionPos.from(pos, _snowmanx), true);
         }
      }, () -> "updateChunkStatus " + pos + " " + true));
   }

   @Override
   public void setSectionStatus(ChunkSectionPos pos, boolean notReady) {
      this.enqueue(
         pos.getSectionX(),
         pos.getSectionZ(),
         () -> 0,
         ServerLightingProvider.Stage.PRE_UPDATE,
         Util.debugRunnable(() -> super.setSectionStatus(pos, notReady), () -> "updateSectionStatus " + pos + " " + notReady)
      );
   }

   @Override
   public void setColumnEnabled(ChunkPos pos, boolean lightEnabled) {
      this.enqueue(
         pos.x,
         pos.z,
         ServerLightingProvider.Stage.PRE_UPDATE,
         Util.debugRunnable(() -> super.setColumnEnabled(pos, lightEnabled), () -> "enableLight " + pos + " " + lightEnabled)
      );
   }

   @Override
   public void enqueueSectionData(LightType lightType, ChunkSectionPos pos, @Nullable ChunkNibbleArray nibbles, boolean _snowman) {
      this.enqueue(
         pos.getSectionX(),
         pos.getSectionZ(),
         () -> 0,
         ServerLightingProvider.Stage.PRE_UPDATE,
         Util.debugRunnable(() -> super.enqueueSectionData(lightType, pos, nibbles, _snowman), () -> "queueData " + pos)
      );
   }

   private void enqueue(int x, int z, ServerLightingProvider.Stage stage, Runnable task) {
      this.enqueue(x, z, this.chunkStorage.getCompletedLevelSupplier(ChunkPos.toLong(x, z)), stage, task);
   }

   private void enqueue(int x, int z, IntSupplier completedLevelSupplier, ServerLightingProvider.Stage stage, Runnable task) {
      this.executor.send(ChunkTaskPrioritySystem.createMessage(() -> {
         this.pendingTasks.add(Pair.of(stage, task));
         if (this.pendingTasks.size() >= this.taskBatchSize) {
            this.runTasks();
         }
      }, ChunkPos.toLong(x, z), completedLevelSupplier));
   }

   @Override
   public void setRetainData(ChunkPos pos, boolean retainData) {
      this.enqueue(
         pos.x,
         pos.z,
         () -> 0,
         ServerLightingProvider.Stage.PRE_UPDATE,
         Util.debugRunnable(() -> super.setRetainData(pos, retainData), () -> "retainData " + pos)
      );
   }

   public CompletableFuture<Chunk> light(Chunk chunk, boolean excludeBlocks) {
      ChunkPos _snowman = chunk.getPos();
      chunk.setLightOn(false);
      this.enqueue(_snowman.x, _snowman.z, ServerLightingProvider.Stage.PRE_UPDATE, Util.debugRunnable(() -> {
         ChunkSection[] _snowmanx = chunk.getSectionArray();

         for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
            ChunkSection _snowmanxx = _snowmanx[_snowmanx];
            if (!ChunkSection.isEmpty(_snowmanxx)) {
               super.setSectionStatus(ChunkSectionPos.from(_snowman, _snowmanx), false);
            }
         }

         super.setColumnEnabled(_snowman, true);
         if (!excludeBlocks) {
            chunk.getLightSourcesStream().forEach(_snowmanxxx -> super.addLightSource(_snowmanxxx, chunk.getLuminance(_snowmanxxx)));
         }

         this.chunkStorage.releaseLightTicket(_snowman);
      }, () -> "lightChunk " + _snowman + " " + excludeBlocks));
      return CompletableFuture.supplyAsync(() -> {
         chunk.setLightOn(true);
         super.setRetainData(_snowman, false);
         return chunk;
      }, _snowmanx -> this.enqueue(_snowman.x, _snowman.z, ServerLightingProvider.Stage.POST_UPDATE, _snowmanx));
   }

   public void tick() {
      if ((!this.pendingTasks.isEmpty() || super.hasUpdates()) && this.ticking.compareAndSet(false, true)) {
         this.processor.send(() -> {
            this.runTasks();
            this.ticking.set(false);
         });
      }
   }

   private void runTasks() {
      int _snowman = Math.min(this.pendingTasks.size(), this.taskBatchSize);
      ObjectListIterator<Pair<ServerLightingProvider.Stage, Runnable>> _snowmanx = this.pendingTasks.iterator();

      int _snowmanxx;
      for (_snowmanxx = 0; _snowmanx.hasNext() && _snowmanxx < _snowman; _snowmanxx++) {
         Pair<ServerLightingProvider.Stage, Runnable> _snowmanxxx = (Pair<ServerLightingProvider.Stage, Runnable>)_snowmanx.next();
         if (_snowmanxxx.getFirst() == ServerLightingProvider.Stage.PRE_UPDATE) {
            ((Runnable)_snowmanxxx.getSecond()).run();
         }
      }

      _snowmanx.back(_snowmanxx);
      super.doLightUpdates(Integer.MAX_VALUE, true, true);

      for (int var5 = 0; _snowmanx.hasNext() && var5 < _snowman; var5++) {
         Pair<ServerLightingProvider.Stage, Runnable> _snowmanxxx = (Pair<ServerLightingProvider.Stage, Runnable>)_snowmanx.next();
         if (_snowmanxxx.getFirst() == ServerLightingProvider.Stage.POST_UPDATE) {
            ((Runnable)_snowmanxxx.getSecond()).run();
         }

         _snowmanx.remove();
      }
   }

   public void setTaskBatchSize(int taskBatchSize) {
      this.taskBatchSize = taskBatchSize;
   }

   static enum Stage {
      PRE_UPDATE,
      POST_UPDATE;

      private Stage() {
      }
   }
}
