package net.minecraft.server.command;

import com.google.common.base.Joiner;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.ColumnPosArgumentType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ColumnPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class ForceLoadCommand {
   private static final Dynamic2CommandExceptionType TOO_BIG_EXCEPTION = new Dynamic2CommandExceptionType(
      (_snowman, _snowmanx) -> new TranslatableText("commands.forceload.toobig", _snowman, _snowmanx)
   );
   private static final Dynamic2CommandExceptionType QUERY_FAILURE_EXCEPTION = new Dynamic2CommandExceptionType(
      (_snowman, _snowmanx) -> new TranslatableText("commands.forceload.query.failure", _snowman, _snowmanx)
   );
   private static final SimpleCommandExceptionType ADDED_FAILURE_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.forceload.added.failure")
   );
   private static final SimpleCommandExceptionType REMOVED_FAILURE_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.forceload.removed.failure")
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("forceload")
                     .requires(_snowman -> _snowman.hasPermissionLevel(2)))
                  .then(
                     CommandManager.literal("add")
                        .then(
                           ((RequiredArgumentBuilder)CommandManager.argument("from", ColumnPosArgumentType.columnPos())
                                 .executes(
                                    _snowman -> executeChange(
                                          (ServerCommandSource)_snowman.getSource(),
                                          ColumnPosArgumentType.getColumnPos(_snowman, "from"),
                                          ColumnPosArgumentType.getColumnPos(_snowman, "from"),
                                          true
                                       )
                                 ))
                              .then(
                                 CommandManager.argument("to", ColumnPosArgumentType.columnPos())
                                    .executes(
                                       _snowman -> executeChange(
                                             (ServerCommandSource)_snowman.getSource(),
                                             ColumnPosArgumentType.getColumnPos(_snowman, "from"),
                                             ColumnPosArgumentType.getColumnPos(_snowman, "to"),
                                             true
                                          )
                                    )
                              )
                        )
                  ))
               .then(
                  ((LiteralArgumentBuilder)CommandManager.literal("remove")
                        .then(
                           ((RequiredArgumentBuilder)CommandManager.argument("from", ColumnPosArgumentType.columnPos())
                                 .executes(
                                    _snowman -> executeChange(
                                          (ServerCommandSource)_snowman.getSource(),
                                          ColumnPosArgumentType.getColumnPos(_snowman, "from"),
                                          ColumnPosArgumentType.getColumnPos(_snowman, "from"),
                                          false
                                       )
                                 ))
                              .then(
                                 CommandManager.argument("to", ColumnPosArgumentType.columnPos())
                                    .executes(
                                       _snowman -> executeChange(
                                             (ServerCommandSource)_snowman.getSource(),
                                             ColumnPosArgumentType.getColumnPos(_snowman, "from"),
                                             ColumnPosArgumentType.getColumnPos(_snowman, "to"),
                                             false
                                          )
                                    )
                              )
                        ))
                     .then(CommandManager.literal("all").executes(_snowman -> executeRemoveAll((ServerCommandSource)_snowman.getSource())))
               ))
            .then(
               ((LiteralArgumentBuilder)CommandManager.literal("query").executes(_snowman -> executeQuery((ServerCommandSource)_snowman.getSource())))
                  .then(
                     CommandManager.argument("pos", ColumnPosArgumentType.columnPos())
                        .executes(_snowman -> executeQuery((ServerCommandSource)_snowman.getSource(), ColumnPosArgumentType.getColumnPos(_snowman, "pos")))
                  )
            )
      );
   }

   private static int executeQuery(ServerCommandSource source, ColumnPos pos) throws CommandSyntaxException {
      ChunkPos _snowman = new ChunkPos(pos.x >> 4, pos.z >> 4);
      ServerWorld _snowmanx = source.getWorld();
      RegistryKey<World> _snowmanxx = _snowmanx.getRegistryKey();
      boolean _snowmanxxx = _snowmanx.getForcedChunks().contains(_snowman.toLong());
      if (_snowmanxxx) {
         source.sendFeedback(new TranslatableText("commands.forceload.query.success", _snowman, _snowmanxx.getValue()), false);
         return 1;
      } else {
         throw QUERY_FAILURE_EXCEPTION.create(_snowman, _snowmanxx.getValue());
      }
   }

   private static int executeQuery(ServerCommandSource source) {
      ServerWorld _snowman = source.getWorld();
      RegistryKey<World> _snowmanx = _snowman.getRegistryKey();
      LongSet _snowmanxx = _snowman.getForcedChunks();
      int _snowmanxxx = _snowmanxx.size();
      if (_snowmanxxx > 0) {
         String _snowmanxxxx = Joiner.on(", ").join(_snowmanxx.stream().sorted().map(ChunkPos::new).map(ChunkPos::toString).iterator());
         if (_snowmanxxx == 1) {
            source.sendFeedback(new TranslatableText("commands.forceload.list.single", _snowmanx.getValue(), _snowmanxxxx), false);
         } else {
            source.sendFeedback(new TranslatableText("commands.forceload.list.multiple", _snowmanxxx, _snowmanx.getValue(), _snowmanxxxx), false);
         }
      } else {
         source.sendError(new TranslatableText("commands.forceload.added.none", _snowmanx.getValue()));
      }

      return _snowmanxxx;
   }

   private static int executeRemoveAll(ServerCommandSource source) {
      ServerWorld _snowman = source.getWorld();
      RegistryKey<World> _snowmanx = _snowman.getRegistryKey();
      LongSet _snowmanxx = _snowman.getForcedChunks();
      _snowmanxx.forEach(_snowmanxxx -> _snowman.setChunkForced(ChunkPos.getPackedX(_snowmanxxx), ChunkPos.getPackedZ(_snowmanxxx), false));
      source.sendFeedback(new TranslatableText("commands.forceload.removed.all", _snowmanx.getValue()), true);
      return 0;
   }

   private static int executeChange(ServerCommandSource source, ColumnPos from, ColumnPos to, boolean forceLoaded) throws CommandSyntaxException {
      int _snowman = Math.min(from.x, to.x);
      int _snowmanx = Math.min(from.z, to.z);
      int _snowmanxx = Math.max(from.x, to.x);
      int _snowmanxxx = Math.max(from.z, to.z);
      if (_snowman >= -30000000 && _snowmanx >= -30000000 && _snowmanxx < 30000000 && _snowmanxxx < 30000000) {
         int _snowmanxxxx = _snowman >> 4;
         int _snowmanxxxxx = _snowmanx >> 4;
         int _snowmanxxxxxx = _snowmanxx >> 4;
         int _snowmanxxxxxxx = _snowmanxxx >> 4;
         long _snowmanxxxxxxxx = ((long)(_snowmanxxxxxx - _snowmanxxxx) + 1L) * ((long)(_snowmanxxxxxxx - _snowmanxxxxx) + 1L);
         if (_snowmanxxxxxxxx > 256L) {
            throw TOO_BIG_EXCEPTION.create(256, _snowmanxxxxxxxx);
         } else {
            ServerWorld _snowmanxxxxxxxxx = source.getWorld();
            RegistryKey<World> _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.getRegistryKey();
            ChunkPos _snowmanxxxxxxxxxxx = null;
            int _snowmanxxxxxxxxxxxx = 0;

            for (int _snowmanxxxxxxxxxxxxx = _snowmanxxxx; _snowmanxxxxxxxxxxxxx <= _snowmanxxxxxx; _snowmanxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxx = _snowmanxxxxx; _snowmanxxxxxxxxxxxxxx <= _snowmanxxxxxxx; _snowmanxxxxxxxxxxxxxx++) {
                  boolean _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx.setChunkForced(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, forceLoaded);
                  if (_snowmanxxxxxxxxxxxxxxx) {
                     _snowmanxxxxxxxxxxxx++;
                     if (_snowmanxxxxxxxxxxx == null) {
                        _snowmanxxxxxxxxxxx = new ChunkPos(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
                     }
                  }
               }
            }

            if (_snowmanxxxxxxxxxxxx == 0) {
               throw (forceLoaded ? ADDED_FAILURE_EXCEPTION : REMOVED_FAILURE_EXCEPTION).create();
            } else {
               if (_snowmanxxxxxxxxxxxx == 1) {
                  source.sendFeedback(
                     new TranslatableText("commands.forceload." + (forceLoaded ? "added" : "removed") + ".single", _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx.getValue()), true
                  );
               } else {
                  ChunkPos _snowmanxxxxxxxxxxxxx = new ChunkPos(_snowmanxxxx, _snowmanxxxxx);
                  ChunkPos _snowmanxxxxxxxxxxxxxxx = new ChunkPos(_snowmanxxxxxx, _snowmanxxxxxxx);
                  source.sendFeedback(
                     new TranslatableText(
                        "commands.forceload." + (forceLoaded ? "added" : "removed") + ".multiple",
                        _snowmanxxxxxxxxxxxx,
                        _snowmanxxxxxxxxxx.getValue(),
                        _snowmanxxxxxxxxxxxxx,
                        _snowmanxxxxxxxxxxxxxxx
                     ),
                     true
                  );
               }

               return _snowmanxxxxxxxxxxxx;
            }
         }
      } else {
         throw BlockPosArgumentType.OUT_OF_WORLD_EXCEPTION.create();
      }
   }
}
