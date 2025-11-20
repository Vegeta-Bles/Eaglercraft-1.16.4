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

   public TestRunner(Collection<GameTestBatch> _snowman, BlockPos pos, BlockRotation _snowman, ServerWorld _snowman, TestManager _snowman, int _snowman) {
      this.reusablePos = pos.mutableCopy();
      this.pos = pos;
      this.world = _snowman;
      this.testManager = _snowman;
      this.sizeZ = _snowman;
      _snowman.forEach(_snowmanxxxxxxxx -> {
         Collection<GameTest> _snowmanxxx = Lists.newArrayList();

         for (TestFunction _snowmanxxxx : _snowmanxxxxxxxx.getTestFunctions()) {
            GameTest _snowmanxxxxx = new GameTest(_snowmanxxxx, _snowman, _snowman);
            _snowmanxxx.add(_snowmanxxxxx);
            this.tests.add(_snowmanxxxxx);
         }

         this.batches.add(Pair.of(_snowmanxxxxxxxx, _snowmanxxx));
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
         Pair<GameTestBatch, Collection<GameTest>> _snowman = this.batches.get(this.currentBatchIndex);
         GameTestBatch _snowmanx = (GameTestBatch)_snowman.getFirst();
         Collection<GameTest> _snowmanxx = (Collection<GameTest>)_snowman.getSecond();
         this.method_29401(_snowmanxx);
         _snowmanx.setWorld(this.world);
         String _snowmanxxx = _snowmanx.getId();
         LOGGER.info("Running test batch '" + _snowmanxxx + "' (" + _snowmanxx.size() + " tests)...");
         _snowmanxx.forEach(_snowmanxxxx -> {
            this.currentBatchTests.add(_snowmanxxxx);
            this.currentBatchTests.addListener(new TestListener() {
               @Override
               public void onStarted(GameTest test) {
               }

               @Override
               public void onFailed(GameTest test) {
                  TestRunner.this.onTestCompleted(test);
               }
            });
            BlockPos _snowmanx = this.field_25300.get(_snowmanxxxx);
            TestUtil.startTest(_snowmanxxxx, _snowmanx, this.testManager);
         });
      }
   }

   private void onTestCompleted(GameTest test) {
      if (this.currentBatchTests.isDone()) {
         this.runBatch(this.currentBatchIndex + 1);
      }
   }

   private void method_29401(Collection<GameTest> _snowman) {
      int _snowmanx = 0;
      Box _snowmanxx = new Box(this.reusablePos);

      for (GameTest _snowmanxxx : _snowman) {
         BlockPos _snowmanxxxx = new BlockPos(this.reusablePos);
         StructureBlockBlockEntity _snowmanxxxxx = StructureTestUtil.method_22250(_snowmanxxx.getStructureName(), _snowmanxxxx, _snowmanxxx.method_29402(), 2, this.world, true);
         Box _snowmanxxxxxx = StructureTestUtil.getStructureBoundingBox(_snowmanxxxxx);
         _snowmanxxx.setPos(_snowmanxxxxx.getPos());
         this.field_25300.put(_snowmanxxx, new BlockPos(this.reusablePos));
         _snowmanxx = _snowmanxx.union(_snowmanxxxxxx);
         this.reusablePos.move((int)_snowmanxxxxxx.getXLength() + 5, 0, 0);
         if (_snowmanx++ % this.sizeZ == this.sizeZ - 1) {
            this.reusablePos.move(0, 0, (int)_snowmanxx.getZLength() + 6);
            this.reusablePos.setX(this.pos.getX());
            _snowmanxx = new Box(this.reusablePos);
         }
      }
   }
}
