/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Streams
 *  org.apache.commons.lang3.mutable.MutableInt
 */
package net.minecraft.test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.test.FailureLoggingTestCompletionListener;
import net.minecraft.test.GameTest;
import net.minecraft.test.GameTestBatch;
import net.minecraft.test.PositionedException;
import net.minecraft.test.StructureTestUtil;
import net.minecraft.test.TestCompletionListener;
import net.minecraft.test.TestFunction;
import net.minecraft.test.TestFunctions;
import net.minecraft.test.TestListener;
import net.minecraft.test.TestManager;
import net.minecraft.test.TestRunner;
import net.minecraft.text.LiteralText;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.mutable.MutableInt;

public class TestUtil {
    public static TestCompletionListener field_20573 = new FailureLoggingTestCompletionListener();

    public static void startTest(GameTest gameTest, BlockPos blockPos, TestManager testManager) {
        gameTest.startCountdown();
        testManager.start(gameTest);
        gameTest.addListener(new TestListener(){

            public void onStarted(GameTest test) {
                TestUtil.method_22202(test, Blocks.LIGHT_GRAY_STAINED_GLASS);
            }

            public void onFailed(GameTest test) {
                TestUtil.method_22202(test, test.isRequired() ? Blocks.RED_STAINED_GLASS : Blocks.ORANGE_STAINED_GLASS);
                TestUtil.method_22204(test, Util.getInnermostMessage(test.getThrowable()));
                TestUtil.method_22219(test);
            }
        });
        gameTest.init(blockPos, 2);
    }

    public static Collection<GameTest> runTestBatches(Collection<GameTestBatch> batches, BlockPos pos, BlockRotation blockRotation, ServerWorld serverWorld, TestManager testManager, int n) {
        TestRunner testRunner = new TestRunner(batches, pos, blockRotation, serverWorld, testManager, n);
        testRunner.run();
        return testRunner.getTests();
    }

    public static Collection<GameTest> runTestFunctions(Collection<TestFunction> testFunctions, BlockPos pos, BlockRotation blockRotation, ServerWorld serverWorld, TestManager testManager, int n) {
        return TestUtil.runTestBatches(TestUtil.createBatches(testFunctions), pos, blockRotation, serverWorld, testManager, n);
    }

    public static Collection<GameTestBatch> createBatches(Collection<TestFunction> testFunctions) {
        HashMap hashMap = Maps.newHashMap();
        testFunctions.forEach(testFunction -> {
            String string2 = testFunction.getBatchId();
            Collection _snowman2 = hashMap.computeIfAbsent(string2, string -> Lists.newArrayList());
            _snowman2.add(testFunction);
        });
        return hashMap.keySet().stream().flatMap(string -> {
            Collection collection = (Collection)hashMap.get(string);
            Consumer<ServerWorld> _snowman2 = TestFunctions.getWorldSetter(string);
            MutableInt _snowman3 = new MutableInt();
            return Streams.stream((Iterable)Iterables.partition((Iterable)collection, (int)100)).map(list -> new GameTestBatch(string + ":" + _snowman3.incrementAndGet(), collection, _snowman2));
        }).collect(Collectors.toList());
    }

    private static void handleTestFail(GameTest test) {
        Throwable throwable = test.getThrowable();
        String _snowman2 = (test.isRequired() ? "" : "(optional) ") + test.getStructurePath() + " failed! " + Util.getInnermostMessage(throwable);
        TestUtil.sendMessage(test.getWorld(), test.isRequired() ? Formatting.RED : Formatting.YELLOW, _snowman2);
        if (throwable instanceof PositionedException) {
            PositionedException positionedException = (PositionedException)throwable;
            TestUtil.addDebugMarker(test.getWorld(), positionedException.getPos(), positionedException.getDebugMessage());
        }
        field_20573.onTestFailed(test);
    }

    private static void createBeacon(GameTest test, Block glass) {
        ServerWorld serverWorld = test.getWorld();
        BlockPos _snowman2 = test.getPos();
        BlockPos _snowman3 = new BlockPos(-1, -1, -1);
        BlockPos _snowman4 = Structure.transformAround(_snowman2.add(_snowman3), BlockMirror.NONE, test.method_29402(), _snowman2);
        serverWorld.setBlockState(_snowman4, Blocks.BEACON.getDefaultState().rotate(test.method_29402()));
        BlockPos _snowman5 = _snowman4.add(0, 1, 0);
        serverWorld.setBlockState(_snowman5, glass.getDefaultState());
        for (int i = -1; i <= 1; ++i) {
            for (_snowman = -1; _snowman <= 1; ++_snowman) {
                BlockPos blockPos = _snowman4.add(i, -1, _snowman);
                serverWorld.setBlockState(blockPos, Blocks.IRON_BLOCK.getDefaultState());
            }
        }
    }

    private static void createLectern(GameTest test, String message) {
        ServerWorld serverWorld = test.getWorld();
        BlockPos _snowman2 = test.getPos();
        BlockPos _snowman3 = new BlockPos(-1, 1, -1);
        BlockPos _snowman4 = Structure.transformAround(_snowman2.add(_snowman3), BlockMirror.NONE, test.method_29402(), _snowman2);
        serverWorld.setBlockState(_snowman4, Blocks.LECTERN.getDefaultState().rotate(test.method_29402()));
        BlockState _snowman5 = serverWorld.getBlockState(_snowman4);
        ItemStack _snowman6 = TestUtil.createBook(test.getStructurePath(), test.isRequired(), message);
        LecternBlock.putBookIfAbsent(serverWorld, _snowman4, _snowman5, _snowman6);
    }

    private static ItemStack createBook(String structureName, boolean required, String message) {
        ItemStack itemStack = new ItemStack(Items.WRITABLE_BOOK);
        ListTag _snowman2 = new ListTag();
        StringBuffer _snowman3 = new StringBuffer();
        Arrays.stream(structureName.split("\\.")).forEach(string -> _snowman3.append((String)string).append('\n'));
        if (!required) {
            _snowman3.append("(optional)\n");
        }
        _snowman3.append("-------------------\n");
        _snowman2.add(StringTag.of(_snowman3.toString() + message));
        itemStack.putSubTag("pages", _snowman2);
        return itemStack;
    }

    private static void sendMessage(ServerWorld world, Formatting formatting, String message) {
        world.getPlayers(serverPlayerEntity -> true).forEach(serverPlayerEntity -> serverPlayerEntity.sendSystemMessage(new LiteralText(message).formatted(formatting), Util.NIL_UUID));
    }

    public static void clearDebugMarkers(ServerWorld world) {
        DebugInfoSender.clearGameTestMarkers(world);
    }

    private static void addDebugMarker(ServerWorld world, BlockPos pos, String message) {
        DebugInfoSender.addGameTestMarker(world, pos, message, -2130771968, Integer.MAX_VALUE);
    }

    public static void clearTests(ServerWorld world, BlockPos pos, TestManager testManager, int radius) {
        testManager.clear();
        BlockPos blockPos2 = pos.add(-radius, 0, -radius);
        _snowman = pos.add(radius, 0, radius);
        BlockPos.stream(blockPos2, _snowman).filter(blockPos -> world.getBlockState((BlockPos)blockPos).isOf(Blocks.STRUCTURE_BLOCK)).forEach(blockPos -> {
            StructureBlockBlockEntity structureBlockBlockEntity = (StructureBlockBlockEntity)world.getBlockEntity((BlockPos)blockPos);
            BlockPos _snowman2 = structureBlockBlockEntity.getPos();
            BlockBox _snowman3 = StructureTestUtil.method_29410(structureBlockBlockEntity);
            StructureTestUtil.clearArea(_snowman3, _snowman2.getY(), world);
        });
    }

    static /* synthetic */ void method_22202(GameTest gameTest, Block block) {
        TestUtil.createBeacon(gameTest, block);
    }

    static /* synthetic */ void method_22204(GameTest gameTest, String string) {
        TestUtil.createLectern(gameTest, string);
    }

    static /* synthetic */ void method_22219(GameTest gameTest) {
        TestUtil.handleTestFail(gameTest);
    }
}

