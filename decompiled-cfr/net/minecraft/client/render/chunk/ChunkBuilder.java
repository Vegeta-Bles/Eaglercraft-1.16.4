/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Queues
 *  com.google.common.collect.Sets
 *  com.google.common.primitives.Doubles
 *  it.unimi.dsi.fastutil.objects.ObjectArraySet
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.render.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.primitives.Doubles;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
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
import net.minecraft.client.render.chunk.BlockBufferBuilderStorage;
import net.minecraft.client.render.chunk.ChunkOcclusionData;
import net.minecraft.client.render.chunk.ChunkOcclusionDataBuilder;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
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
    private final PriorityQueue<BuiltChunk.Task> rebuildQueue = Queues.newPriorityQueue();
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
        int n = Math.max(1, (int)((double)Runtime.getRuntime().maxMemory() * 0.3) / (RenderLayer.getBlockLayers().stream().mapToInt(RenderLayer::getExpectedBufferSize).sum() * 4) - 1);
        _snowman = Runtime.getRuntime().availableProcessors();
        _snowman = is64Bits ? _snowman : Math.min(_snowman, 4);
        _snowman = Math.max(1, Math.min(_snowman, n));
        this.buffers = buffers;
        ArrayList _snowman2 = Lists.newArrayListWithExpectedSize((int)_snowman);
        try {
            for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                _snowman2.add(new BlockBufferBuilderStorage());
            }
        }
        catch (OutOfMemoryError _snowman3) {
            LOGGER.warn("Allocated only {}/{} buffers", (Object)_snowman2.size(), (Object)_snowman);
            _snowman = Math.min(_snowman2.size() * 2 / 3, _snowman2.size() - 1);
            for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                _snowman2.remove(_snowman2.size() - 1);
            }
            System.gc();
        }
        this.threadBuffers = Queues.newArrayDeque((Iterable)_snowman2);
        this.bufferCount = this.threadBuffers.size();
        this.executor = executor;
        this.mailbox = TaskExecutor.create(executor, "Chunk Renderer");
        this.mailbox.send(this::scheduleRunTasks);
    }

    public void setWorld(World world) {
        this.world = world;
    }

    private void scheduleRunTasks() {
        if (this.threadBuffers.isEmpty()) {
            return;
        }
        BuiltChunk.Task task = this.rebuildQueue.poll();
        if (task == null) {
            return;
        }
        BlockBufferBuilderStorage _snowman2 = this.threadBuffers.poll();
        this.queuedTaskCount = this.rebuildQueue.size();
        this.bufferCount = this.threadBuffers.size();
        ((CompletableFuture)CompletableFuture.runAsync(() -> {}, this.executor).thenCompose(void_ -> task.run(_snowman2))).whenComplete((result2, throwable) -> {
            Result result2;
            if (throwable != null) {
                CrashReport crashReport = CrashReport.create(throwable, "Batching chunks");
                MinecraftClient.getInstance().setCrashReport(MinecraftClient.getInstance().addDetailsToCrashReport(crashReport));
                return;
            }
            this.mailbox.send(() -> {
                if (result2 == Result.SUCCESSFUL) {
                    _snowman2.clear();
                } else {
                    _snowman2.reset();
                }
                this.threadBuffers.add(_snowman2);
                this.bufferCount = this.threadBuffers.size();
                this.scheduleRunTasks();
            });
        });
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
        boolean bl = false;
        while ((_snowman = this.uploadQueue.poll()) != null) {
            _snowman.run();
            bl = true;
        }
        return bl;
    }

    public void rebuild(BuiltChunk chunk) {
        chunk.rebuild();
    }

    public void reset() {
        this.clear();
    }

    public void send(BuiltChunk.Task task) {
        this.mailbox.send(() -> {
            this.rebuildQueue.offer(task);
            this.queuedTaskCount = this.rebuildQueue.size();
            this.scheduleRunTasks();
        });
    }

    public CompletableFuture<Void> scheduleUpload(BufferBuilder buffer, VertexBuffer glBuffer) {
        return CompletableFuture.runAsync(() -> {}, this.uploadQueue::add).thenCompose(void_ -> this.upload(buffer, glBuffer));
    }

    private CompletableFuture<Void> upload(BufferBuilder buffer, VertexBuffer glBuffer) {
        return glBuffer.submitUpload(buffer);
    }

    private void clear() {
        while (!this.rebuildQueue.isEmpty()) {
            BuiltChunk.Task task = this.rebuildQueue.poll();
            if (task == null) continue;
            task.cancel();
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

    public static class ChunkData {
        public static final ChunkData EMPTY = new ChunkData(){

            public boolean isVisibleThrough(Direction direction, Direction direction2) {
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

        public boolean isEmpty() {
            return this.empty;
        }

        public boolean isEmpty(RenderLayer layer) {
            return !this.nonEmptyLayers.contains(layer);
        }

        public List<BlockEntity> getBlockEntities() {
            return this.blockEntities;
        }

        public boolean isVisibleThrough(Direction direction, Direction direction2) {
            return this.occlusionGraph.isVisibleThrough(direction, direction2);
        }
    }

    static enum Result {
        SUCCESSFUL,
        CANCELLED;

    }

    public class BuiltChunk {
        public final AtomicReference<ChunkData> data = new AtomicReference<ChunkData>(ChunkData.EMPTY);
        @Nullable
        private RebuildTask rebuildTask;
        @Nullable
        private SortTask sortTask;
        private final Set<BlockEntity> blockEntities = Sets.newHashSet();
        private final Map<RenderLayer, VertexBuffer> buffers = RenderLayer.getBlockLayers().stream().collect(Collectors.toMap(renderLayer -> renderLayer, renderLayer -> new VertexBuffer(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL)));
        public Box boundingBox;
        private int rebuildFrame = -1;
        private boolean needsRebuild = true;
        private final BlockPos.Mutable origin = new BlockPos.Mutable(-1, -1, -1);
        private final BlockPos.Mutable[] neighborPositions = Util.make(new BlockPos.Mutable[6], mutableArray -> {
            for (int i = 0; i < ((BlockPos.Mutable[])mutableArray).length; ++i) {
                mutableArray[i] = new BlockPos.Mutable();
            }
        });
        private boolean needsImportantRebuild;

        private boolean isChunkNonEmpty(BlockPos pos) {
            return ChunkBuilder.this.world.getChunk(pos.getX() >> 4, pos.getZ() >> 4, ChunkStatus.FULL, false) != null;
        }

        public boolean shouldBuild() {
            int n = 24;
            if (this.getSquaredCameraDistance() > 576.0) {
                return this.isChunkNonEmpty(this.neighborPositions[Direction.WEST.ordinal()]) && this.isChunkNonEmpty(this.neighborPositions[Direction.NORTH.ordinal()]) && this.isChunkNonEmpty(this.neighborPositions[Direction.EAST.ordinal()]) && this.isChunkNonEmpty(this.neighborPositions[Direction.SOUTH.ordinal()]);
            }
            return true;
        }

        public boolean setRebuildFrame(int frame) {
            if (this.rebuildFrame == frame) {
                return false;
            }
            this.rebuildFrame = frame;
            return true;
        }

        public VertexBuffer getBuffer(RenderLayer layer) {
            return this.buffers.get(layer);
        }

        public void setOrigin(int x, int y, int z) {
            if (x == this.origin.getX() && y == this.origin.getY() && z == this.origin.getZ()) {
                return;
            }
            this.clear();
            this.origin.set(x, y, z);
            this.boundingBox = new Box(x, y, z, x + 16, y + 16, z + 16);
            for (Direction direction : Direction.values()) {
                this.neighborPositions[direction.ordinal()].set(this.origin).move(direction, 16);
            }
        }

        protected double getSquaredCameraDistance() {
            Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
            double _snowman2 = this.boundingBox.minX + 8.0 - camera.getPos().x;
            double _snowman3 = this.boundingBox.minY + 8.0 - camera.getPos().y;
            double _snowman4 = this.boundingBox.minZ + 8.0 - camera.getPos().z;
            return _snowman2 * _snowman2 + _snowman3 * _snowman3 + _snowman4 * _snowman4;
        }

        private void beginBufferBuilding(BufferBuilder buffer) {
            buffer.begin(7, VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL);
        }

        public ChunkData getData() {
            return this.data.get();
        }

        private void clear() {
            this.cancel();
            this.data.set(ChunkData.EMPTY);
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
            boolean bl = this.needsRebuild;
            this.needsRebuild = true;
            this.needsImportantRebuild = important | (bl && this.needsImportantRebuild);
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
            ChunkData chunkData = this.getData();
            if (this.sortTask != null) {
                this.sortTask.cancel();
            }
            if (!chunkData.initializedLayers.contains(layer)) {
                return false;
            }
            this.sortTask = new SortTask(this.getSquaredCameraDistance(), chunkData);
            chunkRenderer.send(this.sortTask);
            return true;
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

        public Task createRebuildTask() {
            this.cancel();
            BlockPos blockPos = this.origin.toImmutable();
            boolean _snowman2 = true;
            ChunkRendererRegion _snowman3 = ChunkRendererRegion.create(ChunkBuilder.this.world, blockPos.add(-1, -1, -1), blockPos.add(16, 16, 16), 1);
            this.rebuildTask = new RebuildTask(this.getSquaredCameraDistance(), _snowman3);
            return this.rebuildTask;
        }

        public void scheduleRebuild(ChunkBuilder chunkRenderer) {
            Task task = this.createRebuildTask();
            chunkRenderer.send(task);
        }

        private void setNoCullingBlockEntities(Set<BlockEntity> noCullingBlockEntities) {
            HashSet hashSet = Sets.newHashSet(noCullingBlockEntities);
            _snowman = Sets.newHashSet(this.blockEntities);
            hashSet.removeAll(this.blockEntities);
            _snowman.removeAll(noCullingBlockEntities);
            this.blockEntities.clear();
            this.blockEntities.addAll(noCullingBlockEntities);
            ChunkBuilder.this.worldRenderer.updateNoCullingBlockEntities(_snowman, hashSet);
        }

        public void rebuild() {
            Task task = this.createRebuildTask();
            task.run(ChunkBuilder.this.buffers);
        }

        abstract class Task
        implements Comparable<Task> {
            protected final double distance;
            protected final AtomicBoolean cancelled = new AtomicBoolean(false);

            public Task(double d) {
                this.distance = d;
            }

            public abstract CompletableFuture<Result> run(BlockBufferBuilderStorage var1);

            public abstract void cancel();

            @Override
            public int compareTo(Task task) {
                return Doubles.compare((double)this.distance, (double)task.distance);
            }

            @Override
            public /* synthetic */ int compareTo(Object object) {
                return this.compareTo((Task)object);
            }
        }

        class SortTask
        extends Task {
            private final ChunkData data;

            public SortTask(double d, ChunkData chunkData) {
                super(d);
                this.data = chunkData;
            }

            @Override
            public CompletableFuture<Result> run(BlockBufferBuilderStorage buffers) {
                if (this.cancelled.get()) {
                    return CompletableFuture.completedFuture(Result.CANCELLED);
                }
                if (!BuiltChunk.this.shouldBuild()) {
                    this.cancelled.set(true);
                    return CompletableFuture.completedFuture(Result.CANCELLED);
                }
                if (this.cancelled.get()) {
                    return CompletableFuture.completedFuture(Result.CANCELLED);
                }
                Vec3d vec3d = ChunkBuilder.this.getCameraPosition();
                float _snowman2 = (float)vec3d.x;
                float _snowman3 = (float)vec3d.y;
                float _snowman4 = (float)vec3d.z;
                BufferBuilder.State _snowman5 = this.data.bufferState;
                if (_snowman5 == null || !this.data.nonEmptyLayers.contains(RenderLayer.getTranslucent())) {
                    return CompletableFuture.completedFuture(Result.CANCELLED);
                }
                BufferBuilder _snowman6 = buffers.get(RenderLayer.getTranslucent());
                BuiltChunk.this.beginBufferBuilding(_snowman6);
                _snowman6.restoreState(_snowman5);
                _snowman6.sortQuads(_snowman2 - (float)BuiltChunk.this.origin.getX(), _snowman3 - (float)BuiltChunk.this.origin.getY(), _snowman4 - (float)BuiltChunk.this.origin.getZ());
                this.data.bufferState = _snowman6.popState();
                _snowman6.end();
                if (this.cancelled.get()) {
                    return CompletableFuture.completedFuture(Result.CANCELLED);
                }
                CompletionStage _snowman7 = ChunkBuilder.this.scheduleUpload(buffers.get(RenderLayer.getTranslucent()), BuiltChunk.this.getBuffer(RenderLayer.getTranslucent())).thenApply(void_ -> Result.CANCELLED);
                return ((CompletableFuture)_snowman7).handle((result, throwable) -> {
                    if (throwable != null && !(throwable instanceof CancellationException) && !(throwable instanceof InterruptedException)) {
                        MinecraftClient.getInstance().setCrashReport(CrashReport.create(throwable, "Rendering chunk"));
                    }
                    return this.cancelled.get() ? Result.CANCELLED : Result.SUCCESSFUL;
                });
            }

            @Override
            public void cancel() {
                this.cancelled.set(true);
            }
        }

        class RebuildTask
        extends Task {
            @Nullable
            protected ChunkRendererRegion region;

            public RebuildTask(double d, @Nullable ChunkRendererRegion chunkRendererRegion) {
                super(d);
                this.region = chunkRendererRegion;
            }

            @Override
            public CompletableFuture<Result> run(BlockBufferBuilderStorage buffers) {
                if (this.cancelled.get()) {
                    return CompletableFuture.completedFuture(Result.CANCELLED);
                }
                if (!BuiltChunk.this.shouldBuild()) {
                    this.region = null;
                    BuiltChunk.this.scheduleRebuild(false);
                    this.cancelled.set(true);
                    return CompletableFuture.completedFuture(Result.CANCELLED);
                }
                if (this.cancelled.get()) {
                    return CompletableFuture.completedFuture(Result.CANCELLED);
                }
                Vec3d vec3d = ChunkBuilder.this.getCameraPosition();
                float _snowman2 = (float)vec3d.x;
                float _snowman3 = (float)vec3d.y;
                float _snowman4 = (float)vec3d.z;
                ChunkData _snowman5 = new ChunkData();
                Set<BlockEntity> _snowman6 = this.render(_snowman2, _snowman3, _snowman4, _snowman5, buffers);
                BuiltChunk.this.setNoCullingBlockEntities(_snowman6);
                if (this.cancelled.get()) {
                    return CompletableFuture.completedFuture(Result.CANCELLED);
                }
                ArrayList _snowman7 = Lists.newArrayList();
                _snowman5.initializedLayers.forEach(renderLayer -> _snowman7.add(ChunkBuilder.this.scheduleUpload(buffers.get((RenderLayer)renderLayer), BuiltChunk.this.getBuffer((RenderLayer)renderLayer))));
                return Util.combine(_snowman7).handle((list, throwable) -> {
                    if (throwable != null && !(throwable instanceof CancellationException) && !(throwable instanceof InterruptedException)) {
                        MinecraftClient.getInstance().setCrashReport(CrashReport.create(throwable, "Rendering chunk"));
                    }
                    if (this.cancelled.get()) {
                        return Result.CANCELLED;
                    }
                    BuiltChunk.this.data.set(_snowman5);
                    return Result.SUCCESSFUL;
                });
            }

            private Set<BlockEntity> render(float cameraX, float cameraY, float cameraZ, ChunkData data, BlockBufferBuilderStorage buffers) {
                boolean bl = true;
                BlockPos _snowman2 = BuiltChunk.this.origin.toImmutable();
                BlockPos _snowman3 = _snowman2.add(15, 15, 15);
                ChunkOcclusionDataBuilder _snowman4 = new ChunkOcclusionDataBuilder();
                HashSet _snowman5 = Sets.newHashSet();
                ChunkRendererRegion _snowman6 = this.region;
                this.region = null;
                MatrixStack _snowman7 = new MatrixStack();
                if (_snowman6 != null) {
                    BlockModelRenderer.enableBrightnessCache();
                    Random random = new Random();
                    BlockRenderManager _snowman8 = MinecraftClient.getInstance().getBlockRenderManager();
                    for (BlockPos blockPos : BlockPos.iterate(_snowman2, _snowman3)) {
                        BufferBuilder _snowman10;
                        BlockState blockState = _snowman6.getBlockState(blockPos);
                        Block _snowman9 = blockState.getBlock();
                        if (blockState.isOpaqueFullCube(_snowman6, blockPos)) {
                            _snowman4.markClosed(blockPos);
                        }
                        if (_snowman9.hasBlockEntity() && (_snowman = _snowman6.getBlockEntity(blockPos, WorldChunk.CreationType.CHECK)) != null) {
                            this.addBlockEntity(data, _snowman5, _snowman);
                        }
                        if (!((FluidState)(_snowman = _snowman6.getFluidState(blockPos))).isEmpty()) {
                            RenderLayer renderLayer = RenderLayers.getFluidLayer((FluidState)_snowman);
                            _snowman10 = buffers.get(renderLayer);
                            if (data.initializedLayers.add(renderLayer)) {
                                BuiltChunk.this.beginBufferBuilding(_snowman10);
                            }
                            if (_snowman8.renderFluid(blockPos, _snowman6, _snowman10, (FluidState)_snowman)) {
                                data.empty = false;
                                data.nonEmptyLayers.add(renderLayer);
                            }
                        }
                        if (blockState.getRenderType() == BlockRenderType.INVISIBLE) continue;
                        renderLayer = RenderLayers.getBlockLayer(blockState);
                        _snowman10 = buffers.get(renderLayer);
                        if (data.initializedLayers.add(renderLayer)) {
                            BuiltChunk.this.beginBufferBuilding(_snowman10);
                        }
                        _snowman7.push();
                        _snowman7.translate(blockPos.getX() & 0xF, blockPos.getY() & 0xF, blockPos.getZ() & 0xF);
                        if (_snowman8.renderBlock(blockState, blockPos, _snowman6, _snowman7, _snowman10, true, random)) {
                            data.empty = false;
                            data.nonEmptyLayers.add(renderLayer);
                        }
                        _snowman7.pop();
                    }
                    if (data.nonEmptyLayers.contains(RenderLayer.getTranslucent())) {
                        BufferBuilder bufferBuilder = buffers.get(RenderLayer.getTranslucent());
                        bufferBuilder.sortQuads(cameraX - (float)_snowman2.getX(), cameraY - (float)_snowman2.getY(), cameraZ - (float)_snowman2.getZ());
                        data.bufferState = bufferBuilder.popState();
                    }
                    data.initializedLayers.stream().map(buffers::get).forEach(BufferBuilder::end);
                    BlockModelRenderer.disableBrightnessCache();
                }
                data.occlusionGraph = _snowman4.build();
                return _snowman5;
            }

            private <E extends BlockEntity> void addBlockEntity(ChunkData data, Set<BlockEntity> blockEntities, E blockEntity) {
                BlockEntityRenderer<E> blockEntityRenderer = BlockEntityRenderDispatcher.INSTANCE.get(blockEntity);
                if (blockEntityRenderer != null) {
                    data.blockEntities.add(blockEntity);
                    if (blockEntityRenderer.rendersOutsideBoundingBox(blockEntity)) {
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
    }
}

