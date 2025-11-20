package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.command.argument.TestClassArgumentType;
import net.minecraft.command.argument.TestFunctionArgumentType;
import net.minecraft.data.dev.NbtProvider;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.test.GameTest;
import net.minecraft.test.StructureTestUtil;
import net.minecraft.test.TestFunction;
import net.minecraft.test.TestFunctions;
import net.minecraft.test.TestListener;
import net.minecraft.test.TestManager;
import net.minecraft.test.TestSet;
import net.minecraft.test.TestUtil;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import org.apache.commons.io.IOUtils;

public class TestCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal(
                                             "test"
                                          )
                                          .then(CommandManager.literal("runthis").executes(_snowman -> executeRunThis((ServerCommandSource)_snowman.getSource()))))
                                       .then(CommandManager.literal("runthese").executes(_snowman -> executeRunThese((ServerCommandSource)_snowman.getSource()))))
                                    .then(
                                       ((LiteralArgumentBuilder)CommandManager.literal("runfailed")
                                             .executes(_snowman -> method_29411((ServerCommandSource)_snowman.getSource(), false, 0, 8)))
                                          .then(
                                             ((RequiredArgumentBuilder)CommandManager.argument("onlyRequiredTests", BoolArgumentType.bool())
                                                   .executes(
                                                      _snowman -> method_29411(
                                                            (ServerCommandSource)_snowman.getSource(), BoolArgumentType.getBool(_snowman, "onlyRequiredTests"), 0, 8
                                                         )
                                                   ))
                                                .then(
                                                   ((RequiredArgumentBuilder)CommandManager.argument("rotationSteps", IntegerArgumentType.integer())
                                                         .executes(
                                                            _snowman -> method_29411(
                                                                  (ServerCommandSource)_snowman.getSource(),
                                                                  BoolArgumentType.getBool(_snowman, "onlyRequiredTests"),
                                                                  IntegerArgumentType.getInteger(_snowman, "rotationSteps"),
                                                                  8
                                                               )
                                                         ))
                                                      .then(
                                                         CommandManager.argument("testsPerRow", IntegerArgumentType.integer())
                                                            .executes(
                                                               _snowman -> method_29411(
                                                                     (ServerCommandSource)_snowman.getSource(),
                                                                     BoolArgumentType.getBool(_snowman, "onlyRequiredTests"),
                                                                     IntegerArgumentType.getInteger(_snowman, "rotationSteps"),
                                                                     IntegerArgumentType.getInteger(_snowman, "testsPerRow")
                                                                  )
                                                            )
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    CommandManager.literal("run")
                                       .then(
                                          ((RequiredArgumentBuilder)CommandManager.argument("testName", TestFunctionArgumentType.testFunction())
                                                .executes(
                                                   _snowman -> executeRun((ServerCommandSource)_snowman.getSource(), TestFunctionArgumentType.getFunction(_snowman, "testName"), 0)
                                                ))
                                             .then(
                                                CommandManager.argument("rotationSteps", IntegerArgumentType.integer())
                                                   .executes(
                                                      _snowman -> executeRun(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            TestFunctionArgumentType.getFunction(_snowman, "testName"),
                                                            IntegerArgumentType.getInteger(_snowman, "rotationSteps")
                                                         )
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 ((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("runall")
                                          .executes(_snowman -> executeRunAll((ServerCommandSource)_snowman.getSource(), 0, 8)))
                                       .then(
                                          ((RequiredArgumentBuilder)CommandManager.argument("testClassName", TestClassArgumentType.testClass())
                                                .executes(
                                                   _snowman -> executeRunAll(
                                                         (ServerCommandSource)_snowman.getSource(), TestClassArgumentType.getTestClass(_snowman, "testClassName"), 0, 8
                                                      )
                                                ))
                                             .then(
                                                ((RequiredArgumentBuilder)CommandManager.argument("rotationSteps", IntegerArgumentType.integer())
                                                      .executes(
                                                         _snowman -> executeRunAll(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               TestClassArgumentType.getTestClass(_snowman, "testClassName"),
                                                               IntegerArgumentType.getInteger(_snowman, "rotationSteps"),
                                                               8
                                                            )
                                                      ))
                                                   .then(
                                                      CommandManager.argument("testsPerRow", IntegerArgumentType.integer())
                                                         .executes(
                                                            _snowman -> executeRunAll(
                                                                  (ServerCommandSource)_snowman.getSource(),
                                                                  TestClassArgumentType.getTestClass(_snowman, "testClassName"),
                                                                  IntegerArgumentType.getInteger(_snowman, "rotationSteps"),
                                                                  IntegerArgumentType.getInteger(_snowman, "testsPerRow")
                                                               )
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       ((RequiredArgumentBuilder)CommandManager.argument("rotationSteps", IntegerArgumentType.integer())
                                             .executes(
                                                _snowman -> executeRunAll((ServerCommandSource)_snowman.getSource(), IntegerArgumentType.getInteger(_snowman, "rotationSteps"), 8)
                                             ))
                                          .then(
                                             CommandManager.argument("testsPerRow", IntegerArgumentType.integer())
                                                .executes(
                                                   _snowman -> executeRunAll(
                                                         (ServerCommandSource)_snowman.getSource(),
                                                         IntegerArgumentType.getInteger(_snowman, "rotationSteps"),
                                                         IntegerArgumentType.getInteger(_snowman, "testsPerRow")
                                                      )
                                                )
                                          )
                                    )
                              ))
                           .then(
                              CommandManager.literal("export")
                                 .then(
                                    CommandManager.argument("testName", StringArgumentType.word())
                                       .executes(_snowman -> executeExport((ServerCommandSource)_snowman.getSource(), StringArgumentType.getString(_snowman, "testName")))
                                 )
                           ))
                        .then(CommandManager.literal("exportthis").executes(_snowman -> method_29413((ServerCommandSource)_snowman.getSource()))))
                     .then(
                        CommandManager.literal("import")
                           .then(
                              CommandManager.argument("testName", StringArgumentType.word())
                                 .executes(_snowman -> executeImport((ServerCommandSource)_snowman.getSource(), StringArgumentType.getString(_snowman, "testName")))
                           )
                     ))
                  .then(
                     ((LiteralArgumentBuilder)CommandManager.literal("pos").executes(_snowman -> executePos((ServerCommandSource)_snowman.getSource(), "pos")))
                        .then(
                           CommandManager.argument("var", StringArgumentType.word())
                              .executes(_snowman -> executePos((ServerCommandSource)_snowman.getSource(), StringArgumentType.getString(_snowman, "var")))
                        )
                  ))
               .then(
                  CommandManager.literal("create")
                     .then(
                        ((RequiredArgumentBuilder)CommandManager.argument("testName", StringArgumentType.word())
                              .executes(_snowman -> executeCreate((ServerCommandSource)_snowman.getSource(), StringArgumentType.getString(_snowman, "testName"), 5, 5, 5)))
                           .then(
                              ((RequiredArgumentBuilder)CommandManager.argument("width", IntegerArgumentType.integer())
                                    .executes(
                                       _snowman -> executeCreate(
                                             (ServerCommandSource)_snowman.getSource(),
                                             StringArgumentType.getString(_snowman, "testName"),
                                             IntegerArgumentType.getInteger(_snowman, "width"),
                                             IntegerArgumentType.getInteger(_snowman, "width"),
                                             IntegerArgumentType.getInteger(_snowman, "width")
                                          )
                                    ))
                                 .then(
                                    CommandManager.argument("height", IntegerArgumentType.integer())
                                       .then(
                                          CommandManager.argument("depth", IntegerArgumentType.integer())
                                             .executes(
                                                _snowman -> executeCreate(
                                                      (ServerCommandSource)_snowman.getSource(),
                                                      StringArgumentType.getString(_snowman, "testName"),
                                                      IntegerArgumentType.getInteger(_snowman, "width"),
                                                      IntegerArgumentType.getInteger(_snowman, "height"),
                                                      IntegerArgumentType.getInteger(_snowman, "depth")
                                                   )
                                             )
                                       )
                                 )
                           )
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)CommandManager.literal("clearall").executes(_snowman -> executeClearAll((ServerCommandSource)_snowman.getSource(), 200)))
                  .then(
                     CommandManager.argument("radius", IntegerArgumentType.integer())
                        .executes(_snowman -> executeClearAll((ServerCommandSource)_snowman.getSource(), IntegerArgumentType.getInteger(_snowman, "radius")))
                  )
            )
      );
   }

   private static int executeCreate(ServerCommandSource source, String structure, int x, int y, int z) {
      if (x <= 48 && y <= 48 && z <= 48) {
         ServerWorld _snowman = source.getWorld();
         BlockPos _snowmanx = new BlockPos(source.getPosition());
         BlockPos _snowmanxx = new BlockPos(_snowmanx.getX(), source.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, _snowmanx).getY(), _snowmanx.getZ() + 3);
         StructureTestUtil.createTestArea(structure.toLowerCase(), _snowmanxx, new BlockPos(x, y, z), BlockRotation.NONE, _snowman);

         for (int _snowmanxxx = 0; _snowmanxxx < x; _snowmanxxx++) {
            for (int _snowmanxxxx = 0; _snowmanxxxx < z; _snowmanxxxx++) {
               BlockPos _snowmanxxxxx = new BlockPos(_snowmanxx.getX() + _snowmanxxx, _snowmanxx.getY() + 1, _snowmanxx.getZ() + _snowmanxxxx);
               Block _snowmanxxxxxx = Blocks.POLISHED_ANDESITE;
               BlockStateArgument _snowmanxxxxxxx = new BlockStateArgument(_snowmanxxxxxx.getDefaultState(), Collections.EMPTY_SET, null);
               _snowmanxxxxxxx.setBlockState(_snowman, _snowmanxxxxx, 2);
            }
         }

         StructureTestUtil.placeStartButton(_snowmanxx, new BlockPos(1, 0, -1), BlockRotation.NONE, _snowman);
         return 0;
      } else {
         throw new IllegalArgumentException("The structure must be less than 48 blocks big in each axis");
      }
   }

   private static int executePos(ServerCommandSource source, String variableName) throws CommandSyntaxException {
      BlockHitResult _snowman = (BlockHitResult)source.getPlayer().raycast(10.0, 1.0F, false);
      BlockPos _snowmanx = _snowman.getBlockPos();
      ServerWorld _snowmanxx = source.getWorld();
      Optional<BlockPos> _snowmanxxx = StructureTestUtil.findContainingStructureBlock(_snowmanx, 15, _snowmanxx);
      if (!_snowmanxxx.isPresent()) {
         _snowmanxxx = StructureTestUtil.findContainingStructureBlock(_snowmanx, 200, _snowmanxx);
      }

      if (!_snowmanxxx.isPresent()) {
         source.sendError(new LiteralText("Can't find a structure block that contains the targeted pos " + _snowmanx));
         return 0;
      } else {
         StructureBlockBlockEntity _snowmanxxxx = (StructureBlockBlockEntity)_snowmanxx.getBlockEntity(_snowmanxxx.get());
         BlockPos _snowmanxxxxx = _snowmanx.subtract(_snowmanxxx.get());
         String _snowmanxxxxxx = _snowmanxxxxx.getX() + ", " + _snowmanxxxxx.getY() + ", " + _snowmanxxxxx.getZ();
         String _snowmanxxxxxxx = _snowmanxxxx.getStructurePath();
         Text _snowmanxxxxxxxx = new LiteralText(_snowmanxxxxxx)
            .setStyle(
               Style.EMPTY
                  .withBold(true)
                  .withColor(Formatting.GREEN)
                  .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("Click to copy to clipboard")))
                  .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "final BlockPos " + variableName + " = new BlockPos(" + _snowmanxxxxxx + ");"))
            );
         source.sendFeedback(new LiteralText("Position relative to " + _snowmanxxxxxxx + ": ").append(_snowmanxxxxxxxx), false);
         DebugInfoSender.addGameTestMarker(_snowmanxx, new BlockPos(_snowmanx), _snowmanxxxxxx, -2147418368, 10000);
         return 1;
      }
   }

   private static int executeRunThis(ServerCommandSource source) {
      BlockPos _snowman = new BlockPos(source.getPosition());
      ServerWorld _snowmanx = source.getWorld();
      BlockPos _snowmanxx = StructureTestUtil.findNearestStructureBlock(_snowman, 15, _snowmanx);
      if (_snowmanxx == null) {
         sendMessage(_snowmanx, "Couldn't find any structure block within 15 radius", Formatting.RED);
         return 0;
      } else {
         TestUtil.clearDebugMarkers(_snowmanx);
         run(_snowmanx, _snowmanxx, null);
         return 1;
      }
   }

   private static int executeRunThese(ServerCommandSource source) {
      BlockPos _snowman = new BlockPos(source.getPosition());
      ServerWorld _snowmanx = source.getWorld();
      Collection<BlockPos> _snowmanxx = StructureTestUtil.findStructureBlocks(_snowman, 200, _snowmanx);
      if (_snowmanxx.isEmpty()) {
         sendMessage(_snowmanx, "Couldn't find any structure blocks within 200 block radius", Formatting.RED);
         return 1;
      } else {
         TestUtil.clearDebugMarkers(_snowmanx);
         sendMessage(source, "Running " + _snowmanxx.size() + " tests...");
         TestSet _snowmanxxx = new TestSet();
         _snowmanxx.forEach(_snowmanxxxx -> run(_snowman, _snowmanxxxx, _snowman));
         return 1;
      }
   }

   private static void run(ServerWorld world, BlockPos pos, @Nullable TestSet tests) {
      StructureBlockBlockEntity _snowman = (StructureBlockBlockEntity)world.getBlockEntity(pos);
      String _snowmanx = _snowman.getStructurePath();
      TestFunction _snowmanxx = TestFunctions.getTestFunctionOrThrow(_snowmanx);
      GameTest _snowmanxxx = new GameTest(_snowmanxx, _snowman.getRotation(), world);
      if (tests != null) {
         tests.add(_snowmanxxx);
         _snowmanxxx.addListener(new TestCommand.Listener(world, tests));
      }

      setWorld(_snowmanxx, world);
      Box _snowmanxxxx = StructureTestUtil.getStructureBoundingBox(_snowman);
      BlockPos _snowmanxxxxx = new BlockPos(_snowmanxxxx.minX, _snowmanxxxx.minY, _snowmanxxxx.minZ);
      TestUtil.startTest(_snowmanxxx, _snowmanxxxxx, TestManager.INSTANCE);
   }

   private static void onCompletion(ServerWorld world, TestSet tests) {
      if (tests.isDone()) {
         sendMessage(world, "GameTest done! " + tests.getTestCount() + " tests were run", Formatting.WHITE);
         if (tests.failed()) {
            sendMessage(world, "" + tests.getFailedRequiredTestCount() + " required tests failed :(", Formatting.RED);
         } else {
            sendMessage(world, "All required tests passed :)", Formatting.GREEN);
         }

         if (tests.hasFailedOptionalTests()) {
            sendMessage(world, "" + tests.getFailedOptionalTestCount() + " optional tests failed", Formatting.GRAY);
         }
      }
   }

   private static int executeClearAll(ServerCommandSource source, int radius) {
      ServerWorld _snowman = source.getWorld();
      TestUtil.clearDebugMarkers(_snowman);
      BlockPos _snowmanx = new BlockPos(
         source.getPosition().x,
         (double)source.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, new BlockPos(source.getPosition())).getY(),
         source.getPosition().z
      );
      TestUtil.clearTests(_snowman, _snowmanx, TestManager.INSTANCE, MathHelper.clamp(radius, 0, 1024));
      return 1;
   }

   private static int executeRun(ServerCommandSource source, TestFunction testFunction, int _snowman) {
      ServerWorld _snowmanx = source.getWorld();
      BlockPos _snowmanxx = new BlockPos(source.getPosition());
      int _snowmanxxx = source.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, _snowmanxx).getY();
      BlockPos _snowmanxxxx = new BlockPos(_snowmanxx.getX(), _snowmanxxx, _snowmanxx.getZ() + 3);
      TestUtil.clearDebugMarkers(_snowmanx);
      setWorld(testFunction, _snowmanx);
      BlockRotation _snowmanxxxxx = StructureTestUtil.method_29408(_snowman);
      GameTest _snowmanxxxxxx = new GameTest(testFunction, _snowmanxxxxx, _snowmanx);
      TestUtil.startTest(_snowmanxxxxxx, _snowmanxxxx, TestManager.INSTANCE);
      return 1;
   }

   private static void setWorld(TestFunction testFunction, ServerWorld world) {
      Consumer<ServerWorld> _snowman = TestFunctions.getWorldSetter(testFunction.getBatchId());
      if (_snowman != null) {
         _snowman.accept(world);
      }
   }

   private static int executeRunAll(ServerCommandSource source, int _snowman, int _snowman) {
      TestUtil.clearDebugMarkers(source.getWorld());
      Collection<TestFunction> _snowmanxx = TestFunctions.getTestFunctions();
      sendMessage(source, "Running all " + _snowmanxx.size() + " tests...");
      TestFunctions.method_29406();
      run(source, _snowmanxx, _snowman, _snowman);
      return 1;
   }

   private static int executeRunAll(ServerCommandSource source, String testClass, int _snowman, int _snowman) {
      Collection<TestFunction> _snowmanxx = TestFunctions.getTestFunctions(testClass);
      TestUtil.clearDebugMarkers(source.getWorld());
      sendMessage(source, "Running " + _snowmanxx.size() + " tests from " + testClass + "...");
      TestFunctions.method_29406();
      run(source, _snowmanxx, _snowman, _snowman);
      return 1;
   }

   private static int method_29411(ServerCommandSource _snowman, boolean _snowman, int _snowman, int _snowman) {
      Collection<TestFunction> _snowmanxxxx;
      if (_snowman) {
         _snowmanxxxx = TestFunctions.method_29405().stream().filter(TestFunction::isRequired).collect(Collectors.toList());
      } else {
         _snowmanxxxx = TestFunctions.method_29405();
      }

      if (_snowmanxxxx.isEmpty()) {
         sendMessage(_snowman, "No failed tests to rerun");
         return 0;
      } else {
         TestUtil.clearDebugMarkers(_snowman.getWorld());
         sendMessage(_snowman, "Rerunning " + _snowmanxxxx.size() + " failed tests (" + (_snowman ? "only required tests" : "including optional tests") + ")");
         run(_snowman, _snowmanxxxx, _snowman, _snowman);
         return 1;
      }
   }

   private static void run(ServerCommandSource source, Collection<TestFunction> testFunctions, int _snowman, int _snowman) {
      BlockPos _snowmanxx = new BlockPos(source.getPosition());
      BlockPos _snowmanxxx = new BlockPos(_snowmanxx.getX(), source.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, _snowmanxx).getY(), _snowmanxx.getZ() + 3);
      ServerWorld _snowmanxxxx = source.getWorld();
      BlockRotation _snowmanxxxxx = StructureTestUtil.method_29408(_snowman);
      Collection<GameTest> _snowmanxxxxxx = TestUtil.runTestFunctions(testFunctions, _snowmanxxx, _snowmanxxxxx, _snowmanxxxx, TestManager.INSTANCE, _snowman);
      TestSet _snowmanxxxxxxx = new TestSet(_snowmanxxxxxx);
      _snowmanxxxxxxx.addListener(new TestCommand.Listener(_snowmanxxxx, _snowmanxxxxxxx));
      _snowmanxxxxxxx.method_29407(_snowmanxxxxxxxx -> TestFunctions.method_29404(_snowmanxxxxxxxx.method_29403()));
   }

   private static void sendMessage(ServerCommandSource source, String message) {
      source.sendFeedback(new LiteralText(message), false);
   }

   private static int method_29413(ServerCommandSource _snowman) {
      BlockPos _snowmanx = new BlockPos(_snowman.getPosition());
      ServerWorld _snowmanxx = _snowman.getWorld();
      BlockPos _snowmanxxx = StructureTestUtil.findNearestStructureBlock(_snowmanx, 15, _snowmanxx);
      if (_snowmanxxx == null) {
         sendMessage(_snowmanxx, "Couldn't find any structure block within 15 radius", Formatting.RED);
         return 0;
      } else {
         StructureBlockBlockEntity _snowmanxxxx = (StructureBlockBlockEntity)_snowmanxx.getBlockEntity(_snowmanxxx);
         String _snowmanxxxxx = _snowmanxxxx.getStructurePath();
         return executeExport(_snowman, _snowmanxxxxx);
      }
   }

   private static int executeExport(ServerCommandSource source, String structure) {
      Path _snowman = Paths.get(StructureTestUtil.testStructuresDirectoryName);
      Identifier _snowmanx = new Identifier("minecraft", structure);
      Path _snowmanxx = source.getWorld().getStructureManager().getStructurePath(_snowmanx, ".nbt");
      Path _snowmanxxx = NbtProvider.convertNbtToSnbt(_snowmanxx, structure, _snowman);
      if (_snowmanxxx == null) {
         sendMessage(source, "Failed to export " + _snowmanxx);
         return 1;
      } else {
         try {
            Files.createDirectories(_snowmanxxx.getParent());
         } catch (IOException var7) {
            sendMessage(source, "Could not create folder " + _snowmanxxx.getParent());
            var7.printStackTrace();
            return 1;
         }

         sendMessage(source, "Exported " + structure + " to " + _snowmanxxx.toAbsolutePath());
         return 0;
      }
   }

   private static int executeImport(ServerCommandSource _snowman, String structure) {
      Path _snowmanx = Paths.get(StructureTestUtil.testStructuresDirectoryName, structure + ".snbt");
      Identifier _snowmanxx = new Identifier("minecraft", structure);
      Path _snowmanxxx = _snowman.getWorld().getStructureManager().getStructurePath(_snowmanxx, ".nbt");

      try {
         BufferedReader _snowmanxxxx = Files.newBufferedReader(_snowmanx);
         String _snowmanxxxxx = IOUtils.toString(_snowmanxxxx);
         Files.createDirectories(_snowmanxxx.getParent());

         try (OutputStream _snowmanxxxxxx = Files.newOutputStream(_snowmanxxx)) {
            NbtIo.writeCompressed(StringNbtReader.parse(_snowmanxxxxx), _snowmanxxxxxx);
         }

         sendMessage(_snowman, "Imported to " + _snowmanxxx.toAbsolutePath());
         return 0;
      } catch (CommandSyntaxException | IOException var20) {
         System.err.println("Failed to load structure " + structure);
         var20.printStackTrace();
         return 1;
      }
   }

   private static void sendMessage(ServerWorld world, String message, Formatting formatting) {
      world.getPlayers(_snowman -> true).forEach(_snowmanxx -> _snowmanxx.sendSystemMessage(new LiteralText(formatting + message), Util.NIL_UUID));
   }

   static class Listener implements TestListener {
      private final ServerWorld world;
      private final TestSet tests;

      public Listener(ServerWorld world, TestSet tests) {
         this.world = world;
         this.tests = tests;
      }

      @Override
      public void onStarted(GameTest test) {
      }

      @Override
      public void onFailed(GameTest test) {
         TestCommand.onCompletion(this.world, this.tests);
      }
   }
}
