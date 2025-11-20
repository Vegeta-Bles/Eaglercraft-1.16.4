/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.datafixers.util.Pair
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.test.GameTest;
import net.minecraft.test.GameTestBatch;
import net.minecraft.test.StructureTestUtil;
import net.minecraft.test.TestFunction;
import net.minecraft.test.TestListener;
import net.minecraft.test.TestManager;
import net.minecraft.test.TestSet;
import net.minecraft.test.TestUtil;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestRunner {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BlockPos pos;
    private final ServerWorld world;
    private final TestManager testManager;
    private final int sizeZ;
    private final List<GameTest> tests = Lists.newArrayList();
    private final Map<GameTest, BlockPos> field_25300 = Maps.newHashMap();
    private final List<Pair<GameTestBatch, Collection<GameTest>>> batches = Lists.newArrayList();
    private TestSet currentBatchTests;
    private int currentBatchIndex = 0;
    private BlockPos.Mutable reusablePos;

    public TestRunner(Collection<GameTestBatch> collection, BlockPos pos, BlockRotation blockRotation, ServerWorld serverWorld, TestManager testManager, int n) {
        this.reusablePos = pos.mutableCopy();
        this.pos = pos;
        this.world = serverWorld;
        this.testManager = testManager;
        this.sizeZ = n;
        collection.forEach(gameTestBatch2 -> {
            GameTestBatch gameTestBatch2;
            ArrayList arrayList = Lists.newArrayList();
            Collection<TestFunction> _snowman2 = gameTestBatch2.getTestFunctions();
            for (TestFunction testFunction : _snowman2) {
                GameTest gameTest = new GameTest(testFunction, blockRotation, serverWorld);
                arrayList.add(gameTest);
                this.tests.add(gameTest);
            }
            this.batches.add((Pair<GameTestBatch, Collection<GameTest>>)Pair.of((Object)gameTestBatch2, (Object)arrayList));
        });
    }

    public List<GameTest> getTests() {
        return this.tests;
    }

    public void run() {
        this.runBatch(0);
    }

    private void runBatch(int index) {
        this.currentBatchIndex = index;
        this.currentBatchTests = new TestSet();
        if (index >= this.batches.size()) {
            return;
        }
        Pair<GameTestBatch, Collection<GameTest>> pair = this.batches.get(this.currentBatchIndex);
        GameTestBatch _snowman2 = (GameTestBatch)pair.getFirst();
        Collection _snowman3 = (Collection)pair.getSecond();
        this.method_29401(_snowman3);
        _snowman2.setWorld(this.world);
        String _snowman4 = _snowman2.getId();
        LOGGER.info("Running test batch '" + _snowman4 + "' (" + _snowman3.size() + " tests)...");
        _snowman3.forEach(gameTest -> {
            this.currentBatchTests.add((GameTest)gameTest);
            this.currentBatchTests.addListener(new TestListener(this){
                final /* synthetic */ TestRunner field_20557;
                {
                    this.field_20557 = testRunner;
                }

                public void onStarted(GameTest test) {
                }

                public void onFailed(GameTest test) {
                    TestRunner.method_22158(this.field_20557, test);
                }
            });
            BlockPos blockPos = this.field_25300.get(gameTest);
            TestUtil.startTest(gameTest, blockPos, this.testManager);
        });
    }

    private void onTestCompleted(GameTest test) {
        if (this.currentBatchTests.isDone()) {
            this.runBatch(this.currentBatchIndex + 1);
        }
    }

    private void method_29401(Collection<GameTest> collection) {
        int n = 0;
        Box _snowman2 = new Box(this.reusablePos);
        for (GameTest gameTest : collection) {
            BlockPos blockPos = new BlockPos(this.reusablePos);
            StructureBlockBlockEntity _snowman3 = StructureTestUtil.method_22250(gameTest.getStructureName(), blockPos, gameTest.method_29402(), 2, this.world, true);
            Box _snowman4 = StructureTestUtil.getStructureBoundingBox(_snowman3);
            gameTest.setPos(_snowman3.getPos());
            this.field_25300.put(gameTest, new BlockPos(this.reusablePos));
            _snowman2 = _snowman2.union(_snowman4);
            this.reusablePos.move((int)_snowman4.getXLength() + 5, 0, 0);
            if (n++ % this.sizeZ != this.sizeZ - 1) continue;
            this.reusablePos.move(0, 0, (int)_snowman2.getZLength() + 6);
            this.reusablePos.setX(this.pos.getX());
            _snowman2 = new Box(this.reusablePos);
        }
    }

    static /* synthetic */ void method_22158(TestRunner testRunner, GameTest gameTest) {
        testRunner.onTestCompleted(gameTest);
    }
}

