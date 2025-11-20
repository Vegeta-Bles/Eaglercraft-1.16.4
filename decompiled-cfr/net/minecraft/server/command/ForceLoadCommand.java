/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Joiner
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  it.unimi.dsi.fastutil.longs.LongSet
 */
package net.minecraft.server.command;

import com.google.common.base.Joiner;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.ColumnPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ColumnPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class ForceLoadCommand {
    private static final Dynamic2CommandExceptionType TOO_BIG_EXCEPTION = new Dynamic2CommandExceptionType((object, object2) -> new TranslatableText("commands.forceload.toobig", object, object2));
    private static final Dynamic2CommandExceptionType QUERY_FAILURE_EXCEPTION = new Dynamic2CommandExceptionType((object, object2) -> new TranslatableText("commands.forceload.query.failure", object, object2));
    private static final SimpleCommandExceptionType ADDED_FAILURE_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("commands.forceload.added.failure"));
    private static final SimpleCommandExceptionType REMOVED_FAILURE_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("commands.forceload.removed.failure"));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("forceload").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))).then(CommandManager.literal("add").then(((RequiredArgumentBuilder)CommandManager.argument("from", ColumnPosArgumentType.columnPos()).executes(commandContext -> ForceLoadCommand.executeChange((ServerCommandSource)commandContext.getSource(), ColumnPosArgumentType.getColumnPos((CommandContext<ServerCommandSource>)commandContext, "from"), ColumnPosArgumentType.getColumnPos((CommandContext<ServerCommandSource>)commandContext, "from"), true))).then(CommandManager.argument("to", ColumnPosArgumentType.columnPos()).executes(commandContext -> ForceLoadCommand.executeChange((ServerCommandSource)commandContext.getSource(), ColumnPosArgumentType.getColumnPos((CommandContext<ServerCommandSource>)commandContext, "from"), ColumnPosArgumentType.getColumnPos((CommandContext<ServerCommandSource>)commandContext, "to"), true)))))).then(((LiteralArgumentBuilder)CommandManager.literal("remove").then(((RequiredArgumentBuilder)CommandManager.argument("from", ColumnPosArgumentType.columnPos()).executes(commandContext -> ForceLoadCommand.executeChange((ServerCommandSource)commandContext.getSource(), ColumnPosArgumentType.getColumnPos((CommandContext<ServerCommandSource>)commandContext, "from"), ColumnPosArgumentType.getColumnPos((CommandContext<ServerCommandSource>)commandContext, "from"), false))).then(CommandManager.argument("to", ColumnPosArgumentType.columnPos()).executes(commandContext -> ForceLoadCommand.executeChange((ServerCommandSource)commandContext.getSource(), ColumnPosArgumentType.getColumnPos((CommandContext<ServerCommandSource>)commandContext, "from"), ColumnPosArgumentType.getColumnPos((CommandContext<ServerCommandSource>)commandContext, "to"), false))))).then(CommandManager.literal("all").executes(commandContext -> ForceLoadCommand.executeRemoveAll((ServerCommandSource)commandContext.getSource()))))).then(((LiteralArgumentBuilder)CommandManager.literal("query").executes(commandContext -> ForceLoadCommand.executeQuery((ServerCommandSource)commandContext.getSource()))).then(CommandManager.argument("pos", ColumnPosArgumentType.columnPos()).executes(commandContext -> ForceLoadCommand.executeQuery((ServerCommandSource)commandContext.getSource(), ColumnPosArgumentType.getColumnPos((CommandContext<ServerCommandSource>)commandContext, "pos"))))));
    }

    private static int executeQuery(ServerCommandSource source, ColumnPos pos) throws CommandSyntaxException {
        ChunkPos chunkPos = new ChunkPos(pos.x >> 4, pos.z >> 4);
        ServerWorld _snowman2 = source.getWorld();
        RegistryKey<World> _snowman3 = _snowman2.getRegistryKey();
        boolean _snowman4 = _snowman2.getForcedChunks().contains(chunkPos.toLong());
        if (_snowman4) {
            source.sendFeedback(new TranslatableText("commands.forceload.query.success", chunkPos, _snowman3.getValue()), false);
            return 1;
        }
        throw QUERY_FAILURE_EXCEPTION.create((Object)chunkPos, (Object)_snowman3.getValue());
    }

    private static int executeQuery(ServerCommandSource source) {
        ServerWorld serverWorld = source.getWorld();
        RegistryKey<World> _snowman2 = serverWorld.getRegistryKey();
        LongSet _snowman3 = serverWorld.getForcedChunks();
        int _snowman4 = _snowman3.size();
        if (_snowman4 > 0) {
            String string = Joiner.on((String)", ").join(_snowman3.stream().sorted().map(ChunkPos::new).map(ChunkPos::toString).iterator());
            if (_snowman4 == 1) {
                source.sendFeedback(new TranslatableText("commands.forceload.list.single", _snowman2.getValue(), string), false);
            } else {
                source.sendFeedback(new TranslatableText("commands.forceload.list.multiple", _snowman4, _snowman2.getValue(), string), false);
            }
        } else {
            source.sendError(new TranslatableText("commands.forceload.added.none", _snowman2.getValue()));
        }
        return _snowman4;
    }

    private static int executeRemoveAll(ServerCommandSource source) {
        ServerWorld serverWorld = source.getWorld();
        RegistryKey<World> _snowman2 = serverWorld.getRegistryKey();
        LongSet _snowman3 = serverWorld.getForcedChunks();
        _snowman3.forEach(l -> serverWorld.setChunkForced(ChunkPos.getPackedX(l), ChunkPos.getPackedZ(l), false));
        source.sendFeedback(new TranslatableText("commands.forceload.removed.all", _snowman2.getValue()), true);
        return 0;
    }

    private static int executeChange(ServerCommandSource source, ColumnPos from, ColumnPos to, boolean forceLoaded) throws CommandSyntaxException {
        int n;
        int n2 = Math.min(from.x, to.x);
        _snowman = Math.min(from.z, to.z);
        _snowman = Math.max(from.x, to.x);
        _snowman = Math.max(from.z, to.z);
        if (n2 < -30000000 || _snowman < -30000000 || _snowman >= 30000000 || _snowman >= 30000000) {
            throw BlockPosArgumentType.OUT_OF_WORLD_EXCEPTION.create();
        }
        _snowman = _snowman >> 4;
        _snowman = n2 >> 4;
        _snowman = _snowman >> 4;
        _snowman = _snowman >> 4;
        long _snowman2 = ((long)(_snowman - _snowman) + 1L) * ((long)(_snowman - _snowman) + 1L);
        if (_snowman2 > 256L) {
            throw TOO_BIG_EXCEPTION.create((Object)256, (Object)_snowman2);
        }
        ServerWorld _snowman3 = source.getWorld();
        RegistryKey<World> _snowman4 = _snowman3.getRegistryKey();
        ChunkPos _snowman5 = null;
        n = 0;
        for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
            for (_snowman = _snowman; _snowman <= _snowman; ++_snowman) {
                boolean bl = _snowman3.setChunkForced(_snowman, _snowman, forceLoaded);
                if (!bl) continue;
                ++n;
                if (_snowman5 != null) continue;
                _snowman5 = new ChunkPos(_snowman, _snowman);
            }
        }
        if (n == 0) {
            throw (forceLoaded ? ADDED_FAILURE_EXCEPTION : REMOVED_FAILURE_EXCEPTION).create();
        }
        if (n == 1) {
            source.sendFeedback(new TranslatableText("commands.forceload." + (forceLoaded ? "added" : "removed") + ".single", _snowman5, _snowman4.getValue()), true);
        } else {
            ChunkPos chunkPos = new ChunkPos(_snowman, _snowman);
            _snowman = new ChunkPos(_snowman, _snowman);
            source.sendFeedback(new TranslatableText("commands.forceload." + (forceLoaded ? "added" : "removed") + ".multiple", n, _snowman4.getValue(), chunkPos, _snowman), true);
        }
        return n;
    }
}

