/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.BoolArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.arguments.StringArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 *  org.apache.commons.io.IOUtils
 */
package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
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
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
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
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
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
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("test").then(CommandManager.literal("runthis").executes(commandContext -> TestCommand.executeRunThis((ServerCommandSource)commandContext.getSource())))).then(CommandManager.literal("runthese").executes(commandContext -> TestCommand.executeRunThese((ServerCommandSource)commandContext.getSource())))).then(((LiteralArgumentBuilder)CommandManager.literal("runfailed").executes(commandContext -> TestCommand.method_29411((ServerCommandSource)commandContext.getSource(), false, 0, 8))).then(((RequiredArgumentBuilder)CommandManager.argument("onlyRequiredTests", BoolArgumentType.bool()).executes(commandContext -> TestCommand.method_29411((ServerCommandSource)commandContext.getSource(), BoolArgumentType.getBool((CommandContext)commandContext, (String)"onlyRequiredTests"), 0, 8))).then(((RequiredArgumentBuilder)CommandManager.argument("rotationSteps", IntegerArgumentType.integer()).executes(commandContext -> TestCommand.method_29411((ServerCommandSource)commandContext.getSource(), BoolArgumentType.getBool((CommandContext)commandContext, (String)"onlyRequiredTests"), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"rotationSteps"), 8))).then(CommandManager.argument("testsPerRow", IntegerArgumentType.integer()).executes(commandContext -> TestCommand.method_29411((ServerCommandSource)commandContext.getSource(), BoolArgumentType.getBool((CommandContext)commandContext, (String)"onlyRequiredTests"), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"rotationSteps"), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"testsPerRow")))))))).then(CommandManager.literal("run").then(((RequiredArgumentBuilder)CommandManager.argument("testName", TestFunctionArgumentType.testFunction()).executes(commandContext -> TestCommand.executeRun((ServerCommandSource)commandContext.getSource(), TestFunctionArgumentType.getFunction((CommandContext<ServerCommandSource>)commandContext, "testName"), 0))).then(CommandManager.argument("rotationSteps", IntegerArgumentType.integer()).executes(commandContext -> TestCommand.executeRun((ServerCommandSource)commandContext.getSource(), TestFunctionArgumentType.getFunction((CommandContext<ServerCommandSource>)commandContext, "testName"), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"rotationSteps"))))))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("runall").executes(commandContext -> TestCommand.executeRunAll((ServerCommandSource)commandContext.getSource(), 0, 8))).then(((RequiredArgumentBuilder)CommandManager.argument("testClassName", TestClassArgumentType.testClass()).executes(commandContext -> TestCommand.executeRunAll((ServerCommandSource)commandContext.getSource(), TestClassArgumentType.getTestClass((CommandContext<ServerCommandSource>)commandContext, "testClassName"), 0, 8))).then(((RequiredArgumentBuilder)CommandManager.argument("rotationSteps", IntegerArgumentType.integer()).executes(commandContext -> TestCommand.executeRunAll((ServerCommandSource)commandContext.getSource(), TestClassArgumentType.getTestClass((CommandContext<ServerCommandSource>)commandContext, "testClassName"), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"rotationSteps"), 8))).then(CommandManager.argument("testsPerRow", IntegerArgumentType.integer()).executes(commandContext -> TestCommand.executeRunAll((ServerCommandSource)commandContext.getSource(), TestClassArgumentType.getTestClass((CommandContext<ServerCommandSource>)commandContext, "testClassName"), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"rotationSteps"), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"testsPerRow"))))))).then(((RequiredArgumentBuilder)CommandManager.argument("rotationSteps", IntegerArgumentType.integer()).executes(commandContext -> TestCommand.executeRunAll((ServerCommandSource)commandContext.getSource(), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"rotationSteps"), 8))).then(CommandManager.argument("testsPerRow", IntegerArgumentType.integer()).executes(commandContext -> TestCommand.executeRunAll((ServerCommandSource)commandContext.getSource(), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"rotationSteps"), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"testsPerRow"))))))).then(CommandManager.literal("export").then(CommandManager.argument("testName", StringArgumentType.word()).executes(commandContext -> TestCommand.executeExport((ServerCommandSource)commandContext.getSource(), StringArgumentType.getString((CommandContext)commandContext, (String)"testName")))))).then(CommandManager.literal("exportthis").executes(commandContext -> TestCommand.method_29413((ServerCommandSource)commandContext.getSource())))).then(CommandManager.literal("import").then(CommandManager.argument("testName", StringArgumentType.word()).executes(commandContext -> TestCommand.executeImport((ServerCommandSource)commandContext.getSource(), StringArgumentType.getString((CommandContext)commandContext, (String)"testName")))))).then(((LiteralArgumentBuilder)CommandManager.literal("pos").executes(commandContext -> TestCommand.executePos((ServerCommandSource)commandContext.getSource(), "pos"))).then(CommandManager.argument("var", StringArgumentType.word()).executes(commandContext -> TestCommand.executePos((ServerCommandSource)commandContext.getSource(), StringArgumentType.getString((CommandContext)commandContext, (String)"var")))))).then(CommandManager.literal("create").then(((RequiredArgumentBuilder)CommandManager.argument("testName", StringArgumentType.word()).executes(commandContext -> TestCommand.executeCreate((ServerCommandSource)commandContext.getSource(), StringArgumentType.getString((CommandContext)commandContext, (String)"testName"), 5, 5, 5))).then(((RequiredArgumentBuilder)CommandManager.argument("width", IntegerArgumentType.integer()).executes(commandContext -> TestCommand.executeCreate((ServerCommandSource)commandContext.getSource(), StringArgumentType.getString((CommandContext)commandContext, (String)"testName"), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"width"), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"width"), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"width")))).then(CommandManager.argument("height", IntegerArgumentType.integer()).then(CommandManager.argument("depth", IntegerArgumentType.integer()).executes(commandContext -> TestCommand.executeCreate((ServerCommandSource)commandContext.getSource(), StringArgumentType.getString((CommandContext)commandContext, (String)"testName"), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"width"), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"height"), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"depth"))))))))).then(((LiteralArgumentBuilder)CommandManager.literal("clearall").executes(commandContext -> TestCommand.executeClearAll((ServerCommandSource)commandContext.getSource(), 200))).then(CommandManager.argument("radius", IntegerArgumentType.integer()).executes(commandContext -> TestCommand.executeClearAll((ServerCommandSource)commandContext.getSource(), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"radius"))))));
    }

    private static int executeCreate(ServerCommandSource source, String structure, int x, int y, int z) {
        if (x > 48 || y > 48 || z > 48) {
            throw new IllegalArgumentException("The structure must be less than 48 blocks big in each axis");
        }
        ServerWorld serverWorld = source.getWorld();
        BlockPos _snowman2 = new BlockPos(source.getPosition());
        BlockPos _snowman3 = new BlockPos(_snowman2.getX(), source.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, _snowman2).getY(), _snowman2.getZ() + 3);
        StructureTestUtil.createTestArea(structure.toLowerCase(), _snowman3, new BlockPos(x, y, z), BlockRotation.NONE, serverWorld);
        for (int i = 0; i < x; ++i) {
            for (_snowman = 0; _snowman < z; ++_snowman) {
                BlockPos blockPos = new BlockPos(_snowman3.getX() + i, _snowman3.getY() + 1, _snowman3.getZ() + _snowman);
                Block _snowman4 = Blocks.POLISHED_ANDESITE;
                BlockStateArgument _snowman5 = new BlockStateArgument(_snowman4.getDefaultState(), Collections.EMPTY_SET, null);
                _snowman5.setBlockState(serverWorld, blockPos, 2);
            }
        }
        StructureTestUtil.placeStartButton(_snowman3, new BlockPos(1, 0, -1), BlockRotation.NONE, serverWorld);
        return 0;
    }

    private static int executePos(ServerCommandSource source, String variableName) throws CommandSyntaxException {
        BlockHitResult blockHitResult = (BlockHitResult)source.getPlayer().raycast(10.0, 1.0f, false);
        BlockPos _snowman2 = blockHitResult.getBlockPos();
        Optional<BlockPos> _snowman3 = StructureTestUtil.findContainingStructureBlock(_snowman2, 15, _snowman = source.getWorld());
        if (!_snowman3.isPresent()) {
            _snowman3 = StructureTestUtil.findContainingStructureBlock(_snowman2, 200, _snowman);
        }
        if (!_snowman3.isPresent()) {
            source.sendError(new LiteralText("Can't find a structure block that contains the targeted pos " + _snowman2));
            return 0;
        }
        StructureBlockBlockEntity _snowman4 = (StructureBlockBlockEntity)_snowman.getBlockEntity(_snowman3.get());
        BlockPos _snowman5 = _snowman2.subtract(_snowman3.get());
        String _snowman6 = _snowman5.getX() + ", " + _snowman5.getY() + ", " + _snowman5.getZ();
        String _snowman7 = _snowman4.getStructurePath();
        MutableText _snowman8 = new LiteralText(_snowman6).setStyle(Style.EMPTY.withBold(true).withColor(Formatting.GREEN).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new LiteralText("Click to copy to clipboard"))).withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "final BlockPos " + variableName + " = new BlockPos(" + _snowman6 + ");")));
        source.sendFeedback(new LiteralText("Position relative to " + _snowman7 + ": ").append(_snowman8), false);
        DebugInfoSender.addGameTestMarker(_snowman, new BlockPos(_snowman2), _snowman6, -2147418368, 10000);
        return 1;
    }

    private static int executeRunThis(ServerCommandSource source) {
        BlockPos blockPos = new BlockPos(source.getPosition());
        _snowman = StructureTestUtil.findNearestStructureBlock(blockPos, 15, _snowman = source.getWorld());
        if (_snowman == null) {
            TestCommand.sendMessage(_snowman, "Couldn't find any structure block within 15 radius", Formatting.RED);
            return 0;
        }
        TestUtil.clearDebugMarkers(_snowman);
        TestCommand.run(_snowman, _snowman, null);
        return 1;
    }

    private static int executeRunThese(ServerCommandSource source) {
        BlockPos blockPos2 = new BlockPos(source.getPosition());
        Collection<BlockPos> _snowman2 = StructureTestUtil.findStructureBlocks(blockPos2, 200, _snowman = source.getWorld());
        if (_snowman2.isEmpty()) {
            TestCommand.sendMessage(_snowman, "Couldn't find any structure blocks within 200 block radius", Formatting.RED);
            return 1;
        }
        TestUtil.clearDebugMarkers(_snowman);
        TestCommand.sendMessage(source, "Running " + _snowman2.size() + " tests...");
        TestSet _snowman3 = new TestSet();
        _snowman2.forEach(blockPos -> TestCommand.run(_snowman, blockPos, _snowman3));
        return 1;
    }

    private static void run(ServerWorld world, BlockPos pos, @Nullable TestSet tests) {
        StructureBlockBlockEntity structureBlockBlockEntity = (StructureBlockBlockEntity)world.getBlockEntity(pos);
        String _snowman2 = structureBlockBlockEntity.getStructurePath();
        TestFunction _snowman3 = TestFunctions.getTestFunctionOrThrow(_snowman2);
        GameTest _snowman4 = new GameTest(_snowman3, structureBlockBlockEntity.getRotation(), world);
        if (tests != null) {
            tests.add(_snowman4);
            _snowman4.addListener(new Listener(world, tests));
        }
        TestCommand.setWorld(_snowman3, world);
        Box _snowman5 = StructureTestUtil.getStructureBoundingBox(structureBlockBlockEntity);
        BlockPos _snowman6 = new BlockPos(_snowman5.minX, _snowman5.minY, _snowman5.minZ);
        TestUtil.startTest(_snowman4, _snowman6, TestManager.INSTANCE);
    }

    private static void onCompletion(ServerWorld world, TestSet tests) {
        if (tests.isDone()) {
            TestCommand.sendMessage(world, "GameTest done! " + tests.getTestCount() + " tests were run", Formatting.WHITE);
            if (tests.failed()) {
                TestCommand.sendMessage(world, "" + tests.getFailedRequiredTestCount() + " required tests failed :(", Formatting.RED);
            } else {
                TestCommand.sendMessage(world, "All required tests passed :)", Formatting.GREEN);
            }
            if (tests.hasFailedOptionalTests()) {
                TestCommand.sendMessage(world, "" + tests.getFailedOptionalTestCount() + " optional tests failed", Formatting.GRAY);
            }
        }
    }

    private static int executeClearAll(ServerCommandSource source, int radius) {
        ServerWorld serverWorld = source.getWorld();
        TestUtil.clearDebugMarkers(serverWorld);
        BlockPos _snowman2 = new BlockPos(source.getPosition().x, (double)source.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, new BlockPos(source.getPosition())).getY(), source.getPosition().z);
        TestUtil.clearTests(serverWorld, _snowman2, TestManager.INSTANCE, MathHelper.clamp(radius, 0, 1024));
        return 1;
    }

    private static int executeRun(ServerCommandSource source, TestFunction testFunction, int n) {
        ServerWorld serverWorld = source.getWorld();
        BlockPos _snowman2 = new BlockPos(source.getPosition());
        int _snowman3 = source.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, _snowman2).getY();
        BlockPos _snowman4 = new BlockPos(_snowman2.getX(), _snowman3, _snowman2.getZ() + 3);
        TestUtil.clearDebugMarkers(serverWorld);
        TestCommand.setWorld(testFunction, serverWorld);
        BlockRotation _snowman5 = StructureTestUtil.method_29408(n);
        GameTest _snowman6 = new GameTest(testFunction, _snowman5, serverWorld);
        TestUtil.startTest(_snowman6, _snowman4, TestManager.INSTANCE);
        return 1;
    }

    private static void setWorld(TestFunction testFunction, ServerWorld world) {
        Consumer<ServerWorld> consumer = TestFunctions.getWorldSetter(testFunction.getBatchId());
        if (consumer != null) {
            consumer.accept(world);
        }
    }

    private static int executeRunAll(ServerCommandSource source, int n, int n2) {
        TestUtil.clearDebugMarkers(source.getWorld());
        Collection<TestFunction> collection = TestFunctions.getTestFunctions();
        TestCommand.sendMessage(source, "Running all " + collection.size() + " tests...");
        TestFunctions.method_29406();
        TestCommand.run(source, collection, n, n2);
        return 1;
    }

    private static int executeRunAll(ServerCommandSource source, String testClass, int n, int n2) {
        Collection<TestFunction> collection = TestFunctions.getTestFunctions(testClass);
        TestUtil.clearDebugMarkers(source.getWorld());
        TestCommand.sendMessage(source, "Running " + collection.size() + " tests from " + testClass + "...");
        TestFunctions.method_29406();
        TestCommand.run(source, collection, n, n2);
        return 1;
    }

    private static int method_29411(ServerCommandSource serverCommandSource, boolean bl, int n, int n2) {
        Collection collection = bl ? (Collection)TestFunctions.method_29405().stream().filter(TestFunction::isRequired).collect(Collectors.toList()) : TestFunctions.method_29405();
        if (collection.isEmpty()) {
            TestCommand.sendMessage(serverCommandSource, "No failed tests to rerun");
            return 0;
        }
        TestUtil.clearDebugMarkers(serverCommandSource.getWorld());
        TestCommand.sendMessage(serverCommandSource, "Rerunning " + collection.size() + " failed tests (" + (bl ? "only required tests" : "including optional tests") + ")");
        TestCommand.run(serverCommandSource, collection, n, n2);
        return 1;
    }

    private static void run(ServerCommandSource source, Collection<TestFunction> testFunctions, int n, int n2) {
        BlockPos blockPos = new BlockPos(source.getPosition());
        _snowman = new BlockPos(blockPos.getX(), source.getWorld().getTopPosition(Heightmap.Type.WORLD_SURFACE, blockPos).getY(), blockPos.getZ() + 3);
        ServerWorld _snowman2 = source.getWorld();
        BlockRotation _snowman3 = StructureTestUtil.method_29408(n);
        Collection<GameTest> _snowman4 = TestUtil.runTestFunctions(testFunctions, _snowman, _snowman3, _snowman2, TestManager.INSTANCE, n2);
        TestSet _snowman5 = new TestSet(_snowman4);
        _snowman5.addListener(new Listener(_snowman2, _snowman5));
        _snowman5.method_29407(gameTest -> TestFunctions.method_29404(gameTest.method_29403()));
    }

    private static void sendMessage(ServerCommandSource source, String message) {
        source.sendFeedback(new LiteralText(message), false);
    }

    private static int method_29413(ServerCommandSource serverCommandSource) {
        BlockPos blockPos = new BlockPos(serverCommandSource.getPosition());
        _snowman = StructureTestUtil.findNearestStructureBlock(blockPos, 15, _snowman = serverCommandSource.getWorld());
        if (_snowman == null) {
            TestCommand.sendMessage(_snowman, "Couldn't find any structure block within 15 radius", Formatting.RED);
            return 0;
        }
        StructureBlockBlockEntity _snowman2 = (StructureBlockBlockEntity)_snowman.getBlockEntity(_snowman);
        String _snowman3 = _snowman2.getStructurePath();
        return TestCommand.executeExport(serverCommandSource, _snowman3);
    }

    private static int executeExport(ServerCommandSource source, String structure) {
        Path path = Paths.get(StructureTestUtil.testStructuresDirectoryName, new String[0]);
        Identifier _snowman2 = new Identifier("minecraft", structure);
        _snowman = source.getWorld().getStructureManager().getStructurePath(_snowman2, ".nbt");
        _snowman = NbtProvider.convertNbtToSnbt(_snowman, structure, path);
        if (_snowman == null) {
            TestCommand.sendMessage(source, "Failed to export " + _snowman);
            return 1;
        }
        try {
            Files.createDirectories(_snowman.getParent(), new FileAttribute[0]);
        }
        catch (IOException _snowman3) {
            TestCommand.sendMessage(source, "Could not create folder " + _snowman.getParent());
            _snowman3.printStackTrace();
            return 1;
        }
        TestCommand.sendMessage(source, "Exported " + structure + " to " + _snowman.toAbsolutePath());
        return 0;
    }

    private static int executeImport(ServerCommandSource serverCommandSource, String structure) {
        Path path = Paths.get(StructureTestUtil.testStructuresDirectoryName, structure + ".snbt");
        Identifier _snowman2 = new Identifier("minecraft", structure);
        _snowman = serverCommandSource.getWorld().getStructureManager().getStructurePath(_snowman2, ".nbt");
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(path);
            String _snowman3 = IOUtils.toString((Reader)bufferedReader);
            Files.createDirectories(_snowman.getParent(), new FileAttribute[0]);
            try (OutputStream _snowman4 = Files.newOutputStream(_snowman, new OpenOption[0]);){
                NbtIo.writeCompressed(StringNbtReader.parse(_snowman3), _snowman4);
            }
            TestCommand.sendMessage(serverCommandSource, "Imported to " + _snowman.toAbsolutePath());
            return 0;
        }
        catch (CommandSyntaxException | IOException throwable) {
            System.err.println("Failed to load structure " + structure);
            throwable.printStackTrace();
            return 1;
        }
    }

    private static void sendMessage(ServerWorld world, String message, Formatting formatting) {
        world.getPlayers(serverPlayerEntity -> true).forEach(serverPlayerEntity -> serverPlayerEntity.sendSystemMessage(new LiteralText((Object)((Object)formatting) + message), Util.NIL_UUID));
    }

    static class Listener
    implements TestListener {
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

