package net.minecraft.test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
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

   public static void startTest(GameTest _snowman, BlockPos _snowman, TestManager _snowman) {
      _snowman.startCountdown();
      _snowman.start(_snowman);
      _snowman.addListener(new TestListener() {
         @Override
         public void onStarted(GameTest test) {
            TestUtil.createBeacon(test, Blocks.LIGHT_GRAY_STAINED_GLASS);
         }

         @Override
         public void onFailed(GameTest test) {
            TestUtil.createBeacon(test, test.isRequired() ? Blocks.RED_STAINED_GLASS : Blocks.ORANGE_STAINED_GLASS);
            TestUtil.createLectern(test, Util.getInnermostMessage(test.getThrowable()));
            TestUtil.handleTestFail(test);
         }
      });
      _snowman.init(_snowman, 2);
   }

   public static Collection<GameTest> runTestBatches(Collection<GameTestBatch> batches, BlockPos pos, BlockRotation _snowman, ServerWorld _snowman, TestManager _snowman, int _snowman) {
      TestRunner _snowmanxxxx = new TestRunner(batches, pos, _snowman, _snowman, _snowman, _snowman);
      _snowmanxxxx.run();
      return _snowmanxxxx.getTests();
   }

   public static Collection<GameTest> runTestFunctions(
      Collection<TestFunction> testFunctions, BlockPos pos, BlockRotation _snowman, ServerWorld _snowman, TestManager _snowman, int _snowman
   ) {
      return runTestBatches(createBatches(testFunctions), pos, _snowman, _snowman, _snowman, _snowman);
   }

   public static Collection<GameTestBatch> createBatches(Collection<TestFunction> testFunctions) {
      Map<String, Collection<TestFunction>> _snowman = Maps.newHashMap();
      testFunctions.forEach(_snowmanx -> {
         String _snowmanx = _snowmanx.getBatchId();
         Collection<TestFunction> _snowmanxx = _snowman.computeIfAbsent(_snowmanx, _snowmanxxx -> Lists.newArrayList());
         _snowmanxx.add(_snowmanx);
      });
      return _snowman.keySet().stream().flatMap(_snowmanx -> {
         Collection<TestFunction> _snowmanx = _snowman.get(_snowmanx);
         Consumer<ServerWorld> _snowmanxx = TestFunctions.getWorldSetter(_snowmanx);
         MutableInt _snowmanxxx = new MutableInt();
         return Streams.stream(Iterables.partition(_snowmanx, 100)).map(_snowmanxxxx -> new GameTestBatch(_snowmanx + ":" + _snowman.incrementAndGet(), _snowman, _snowman));
      }).collect(Collectors.toList());
   }

   private static void handleTestFail(GameTest test) {
      Throwable _snowman = test.getThrowable();
      String _snowmanx = (test.isRequired() ? "" : "(optional) ") + test.getStructurePath() + " failed! " + Util.getInnermostMessage(_snowman);
      sendMessage(test.getWorld(), test.isRequired() ? Formatting.RED : Formatting.YELLOW, _snowmanx);
      if (_snowman instanceof PositionedException) {
         PositionedException _snowmanxx = (PositionedException)_snowman;
         addDebugMarker(test.getWorld(), _snowmanxx.getPos(), _snowmanxx.getDebugMessage());
      }

      field_20573.onTestFailed(test);
   }

   private static void createBeacon(GameTest test, Block glass) {
      ServerWorld _snowman = test.getWorld();
      BlockPos _snowmanx = test.getPos();
      BlockPos _snowmanxx = new BlockPos(-1, -1, -1);
      BlockPos _snowmanxxx = Structure.transformAround(_snowmanx.add(_snowmanxx), BlockMirror.NONE, test.method_29402(), _snowmanx);
      _snowman.setBlockState(_snowmanxxx, Blocks.BEACON.getDefaultState().rotate(test.method_29402()));
      BlockPos _snowmanxxxx = _snowmanxxx.add(0, 1, 0);
      _snowman.setBlockState(_snowmanxxxx, glass.getDefaultState());

      for (int _snowmanxxxxx = -1; _snowmanxxxxx <= 1; _snowmanxxxxx++) {
         for (int _snowmanxxxxxx = -1; _snowmanxxxxxx <= 1; _snowmanxxxxxx++) {
            BlockPos _snowmanxxxxxxx = _snowmanxxx.add(_snowmanxxxxx, -1, _snowmanxxxxxx);
            _snowman.setBlockState(_snowmanxxxxxxx, Blocks.IRON_BLOCK.getDefaultState());
         }
      }
   }

   private static void createLectern(GameTest test, String message) {
      ServerWorld _snowman = test.getWorld();
      BlockPos _snowmanx = test.getPos();
      BlockPos _snowmanxx = new BlockPos(-1, 1, -1);
      BlockPos _snowmanxxx = Structure.transformAround(_snowmanx.add(_snowmanxx), BlockMirror.NONE, test.method_29402(), _snowmanx);
      _snowman.setBlockState(_snowmanxxx, Blocks.LECTERN.getDefaultState().rotate(test.method_29402()));
      BlockState _snowmanxxxx = _snowman.getBlockState(_snowmanxxx);
      ItemStack _snowmanxxxxx = createBook(test.getStructurePath(), test.isRequired(), message);
      LecternBlock.putBookIfAbsent(_snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
   }

   private static ItemStack createBook(String structureName, boolean required, String message) {
      ItemStack _snowman = new ItemStack(Items.WRITABLE_BOOK);
      ListTag _snowmanx = new ListTag();
      StringBuffer _snowmanxx = new StringBuffer();
      Arrays.stream(structureName.split("\\.")).forEach(_snowmanxxx -> _snowman.append(_snowmanxxx).append('\n'));
      if (!required) {
         _snowmanxx.append("(optional)\n");
      }

      _snowmanxx.append("-------------------\n");
      _snowmanx.add(StringTag.of(_snowmanxx.toString() + message));
      _snowman.putSubTag("pages", _snowmanx);
      return _snowman;
   }

   private static void sendMessage(ServerWorld world, Formatting formatting, String message) {
      world.getPlayers(_snowman -> true).forEach(_snowmanxx -> _snowmanxx.sendSystemMessage(new LiteralText(message).formatted(formatting), Util.NIL_UUID));
   }

   public static void clearDebugMarkers(ServerWorld world) {
      DebugInfoSender.clearGameTestMarkers(world);
   }

   private static void addDebugMarker(ServerWorld world, BlockPos pos, String message) {
      DebugInfoSender.addGameTestMarker(world, pos, message, -2130771968, Integer.MAX_VALUE);
   }

   public static void clearTests(ServerWorld world, BlockPos pos, TestManager testManager, int radius) {
      testManager.clear();
      BlockPos _snowman = pos.add(-radius, 0, -radius);
      BlockPos _snowmanx = pos.add(radius, 0, radius);
      BlockPos.stream(_snowman, _snowmanx).filter(_snowmanxx -> world.getBlockState(_snowmanxx).isOf(Blocks.STRUCTURE_BLOCK)).forEach(_snowmanxx -> {
         StructureBlockBlockEntity _snowmanxxx = (StructureBlockBlockEntity)world.getBlockEntity(_snowmanxx);
         BlockPos _snowmanxx = _snowmanxxx.getPos();
         BlockBox _snowmanxxx = StructureTestUtil.method_29410(_snowmanxxx);
         StructureTestUtil.clearArea(_snowmanxxx, _snowmanxx.getY(), world);
      });
   }
}
