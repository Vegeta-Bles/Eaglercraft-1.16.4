package net.minecraft.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.server.world.ServerWorld;
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

   public TestRunner(Collection<GameTestBatch> collection, BlockPos pos, BlockRotation arg2, ServerWorld arg3, TestManager arg4, int i) {
      this.reusablePos = pos.mutableCopy();
      this.pos = pos;
      this.world = arg3;
      this.testManager = arg4;
      this.sizeZ = i;
      collection.forEach(arg3x -> {
         Collection<GameTest> collectionx = Lists.newArrayList();

         for (TestFunction lv : arg3x.getTestFunctions()) {
            GameTest lv2 = new GameTest(lv, arg2, arg3);
            collectionx.add(lv2);
            this.tests.add(lv2);
         }

         this.batches.add(Pair.of(arg3x, collectionx));
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
      if (index < this.batches.size()) {
         Pair<GameTestBatch, Collection<GameTest>> pair = this.batches.get(this.currentBatchIndex);
         GameTestBatch lv = (GameTestBatch)pair.getFirst();
         Collection<GameTest> collection = (Collection<GameTest>)pair.getSecond();
         this.method_29401(collection);
         lv.setWorld(this.world);
         String string = lv.getId();
         LOGGER.info("Running test batch '" + string + "' (" + collection.size() + " tests)...");
         collection.forEach(arg -> {
            this.currentBatchTests.add(arg);
            this.currentBatchTests.addListener(new TestListener() {
               @Override
               public void onStarted(GameTest test) {
               }

               @Override
               public void onFailed(GameTest test) {
                  TestRunner.this.onTestCompleted(test);
               }
            });
            BlockPos lvx = this.field_25300.get(arg);
            TestUtil.startTest(arg, lvx, this.testManager);
         });
      }
   }

   private void onTestCompleted(GameTest test) {
      if (this.currentBatchTests.isDone()) {
         this.runBatch(this.currentBatchIndex + 1);
      }
   }

   private void method_29401(Collection<GameTest> collection) {
      int i = 0;
      Box lv = new Box(this.reusablePos);

      for (GameTest lv2 : collection) {
         BlockPos lv3 = new BlockPos(this.reusablePos);
         StructureBlockBlockEntity lv4 = StructureTestUtil.method_22250(lv2.getStructureName(), lv3, lv2.method_29402(), 2, this.world, true);
         Box lv5 = StructureTestUtil.getStructureBoundingBox(lv4);
         lv2.setPos(lv4.getPos());
         this.field_25300.put(lv2, new BlockPos(this.reusablePos));
         lv = lv.union(lv5);
         this.reusablePos.move((int)lv5.getXLength() + 5, 0, 0);
         if (i++ % this.sizeZ == this.sizeZ - 1) {
            this.reusablePos.move(0, 0, (int)lv.getZLength() + 6);
            this.reusablePos.setX(this.pos.getX());
            lv = new Box(this.reusablePos);
         }
      }
   }
}
