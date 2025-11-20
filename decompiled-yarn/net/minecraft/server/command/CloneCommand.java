package net.minecraft.server.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Deque;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.BlockPredicateArgumentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;

public class CloneCommand {
   private static final SimpleCommandExceptionType OVERLAP_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.clone.overlap"));
   private static final Dynamic2CommandExceptionType TOO_BIG_EXCEPTION = new Dynamic2CommandExceptionType(
      (_snowman, _snowmanx) -> new TranslatableText("commands.clone.toobig", _snowman, _snowmanx)
   );
   private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.clone.failed"));
   public static final Predicate<CachedBlockPosition> IS_AIR_PREDICATE = _snowman -> !_snowman.getBlockState().isAir();

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("clone").requires(_snowman -> _snowman.hasPermissionLevel(2)))
            .then(
               CommandManager.argument("begin", BlockPosArgumentType.blockPos())
                  .then(
                     CommandManager.argument("end", BlockPosArgumentType.blockPos())
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                          "destination", BlockPosArgumentType.blockPos()
                                       )
                                       .executes(
                                          _snowman -> execute(
                                                (ServerCommandSource)_snowman.getSource(),
                                                BlockPosArgumentType.getLoadedBlockPos(_snowman, "begin"),
                                                BlockPosArgumentType.getLoadedBlockPos(_snowman, "end"),
                                                BlockPosArgumentType.getLoadedBlockPos(_snowman, "destination"),
                                                _snowmanx -> true,
                                                CloneCommand.Mode.NORMAL
                                             )
                                       ))
                                    .then(
                                       ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("replace")
                                                   .executes(
                                                      _snowman -> execute(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            BlockPosArgumentType.getLoadedBlockPos(_snowman, "begin"),
                                                            BlockPosArgumentType.getLoadedBlockPos(_snowman, "end"),
                                                            BlockPosArgumentType.getLoadedBlockPos(_snowman, "destination"),
                                                            _snowmanx -> true,
                                                            CloneCommand.Mode.NORMAL
                                                         )
                                                   ))
                                                .then(
                                                   CommandManager.literal("force")
                                                      .executes(
                                                         _snowman -> execute(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               BlockPosArgumentType.getLoadedBlockPos(_snowman, "begin"),
                                                               BlockPosArgumentType.getLoadedBlockPos(_snowman, "end"),
                                                               BlockPosArgumentType.getLoadedBlockPos(_snowman, "destination"),
                                                               _snowmanx -> true,
                                                               CloneCommand.Mode.FORCE
                                                            )
                                                      )
                                                ))
                                             .then(
                                                CommandManager.literal("move")
                                                   .executes(
                                                      _snowman -> execute(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            BlockPosArgumentType.getLoadedBlockPos(_snowman, "begin"),
                                                            BlockPosArgumentType.getLoadedBlockPos(_snowman, "end"),
                                                            BlockPosArgumentType.getLoadedBlockPos(_snowman, "destination"),
                                                            _snowmanx -> true,
                                                            CloneCommand.Mode.MOVE
                                                         )
                                                   )
                                             ))
                                          .then(
                                             CommandManager.literal("normal")
                                                .executes(
                                                   _snowman -> execute(
                                                         (ServerCommandSource)_snowman.getSource(),
                                                         BlockPosArgumentType.getLoadedBlockPos(_snowman, "begin"),
                                                         BlockPosArgumentType.getLoadedBlockPos(_snowman, "end"),
                                                         BlockPosArgumentType.getLoadedBlockPos(_snowman, "destination"),
                                                         _snowmanx -> true,
                                                         CloneCommand.Mode.NORMAL
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("masked")
                                                .executes(
                                                   _snowman -> execute(
                                                         (ServerCommandSource)_snowman.getSource(),
                                                         BlockPosArgumentType.getLoadedBlockPos(_snowman, "begin"),
                                                         BlockPosArgumentType.getLoadedBlockPos(_snowman, "end"),
                                                         BlockPosArgumentType.getLoadedBlockPos(_snowman, "destination"),
                                                         IS_AIR_PREDICATE,
                                                         CloneCommand.Mode.NORMAL
                                                      )
                                                ))
                                             .then(
                                                CommandManager.literal("force")
                                                   .executes(
                                                      _snowman -> execute(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            BlockPosArgumentType.getLoadedBlockPos(_snowman, "begin"),
                                                            BlockPosArgumentType.getLoadedBlockPos(_snowman, "end"),
                                                            BlockPosArgumentType.getLoadedBlockPos(_snowman, "destination"),
                                                            IS_AIR_PREDICATE,
                                                            CloneCommand.Mode.FORCE
                                                         )
                                                   )
                                             ))
                                          .then(
                                             CommandManager.literal("move")
                                                .executes(
                                                   _snowman -> execute(
                                                         (ServerCommandSource)_snowman.getSource(),
                                                         BlockPosArgumentType.getLoadedBlockPos(_snowman, "begin"),
                                                         BlockPosArgumentType.getLoadedBlockPos(_snowman, "end"),
                                                         BlockPosArgumentType.getLoadedBlockPos(_snowman, "destination"),
                                                         IS_AIR_PREDICATE,
                                                         CloneCommand.Mode.MOVE
                                                      )
                                                )
                                          ))
                                       .then(
                                          CommandManager.literal("normal")
                                             .executes(
                                                _snowman -> execute(
                                                      (ServerCommandSource)_snowman.getSource(),
                                                      BlockPosArgumentType.getLoadedBlockPos(_snowman, "begin"),
                                                      BlockPosArgumentType.getLoadedBlockPos(_snowman, "end"),
                                                      BlockPosArgumentType.getLoadedBlockPos(_snowman, "destination"),
                                                      IS_AIR_PREDICATE,
                                                      CloneCommand.Mode.NORMAL
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 CommandManager.literal("filtered")
                                    .then(
                                       ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                                      "filter", BlockPredicateArgumentType.blockPredicate()
                                                   )
                                                   .executes(
                                                      _snowman -> execute(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            BlockPosArgumentType.getLoadedBlockPos(_snowman, "begin"),
                                                            BlockPosArgumentType.getLoadedBlockPos(_snowman, "end"),
                                                            BlockPosArgumentType.getLoadedBlockPos(_snowman, "destination"),
                                                            BlockPredicateArgumentType.getBlockPredicate(_snowman, "filter"),
                                                            CloneCommand.Mode.NORMAL
                                                         )
                                                   ))
                                                .then(
                                                   CommandManager.literal("force")
                                                      .executes(
                                                         _snowman -> execute(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               BlockPosArgumentType.getLoadedBlockPos(_snowman, "begin"),
                                                               BlockPosArgumentType.getLoadedBlockPos(_snowman, "end"),
                                                               BlockPosArgumentType.getLoadedBlockPos(_snowman, "destination"),
                                                               BlockPredicateArgumentType.getBlockPredicate(_snowman, "filter"),
                                                               CloneCommand.Mode.FORCE
                                                            )
                                                      )
                                                ))
                                             .then(
                                                CommandManager.literal("move")
                                                   .executes(
                                                      _snowman -> execute(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            BlockPosArgumentType.getLoadedBlockPos(_snowman, "begin"),
                                                            BlockPosArgumentType.getLoadedBlockPos(_snowman, "end"),
                                                            BlockPosArgumentType.getLoadedBlockPos(_snowman, "destination"),
                                                            BlockPredicateArgumentType.getBlockPredicate(_snowman, "filter"),
                                                            CloneCommand.Mode.MOVE
                                                         )
                                                   )
                                             ))
                                          .then(
                                             CommandManager.literal("normal")
                                                .executes(
                                                   _snowman -> execute(
                                                         (ServerCommandSource)_snowman.getSource(),
                                                         BlockPosArgumentType.getLoadedBlockPos(_snowman, "begin"),
                                                         BlockPosArgumentType.getLoadedBlockPos(_snowman, "end"),
                                                         BlockPosArgumentType.getLoadedBlockPos(_snowman, "destination"),
                                                         BlockPredicateArgumentType.getBlockPredicate(_snowman, "filter"),
                                                         CloneCommand.Mode.NORMAL
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int execute(
      ServerCommandSource source, BlockPos begin, BlockPos end, BlockPos destination, Predicate<CachedBlockPosition> filter, CloneCommand.Mode mode
   ) throws CommandSyntaxException {
      BlockBox _snowman = new BlockBox(begin, end);
      BlockPos _snowmanx = destination.add(_snowman.getDimensions());
      BlockBox _snowmanxx = new BlockBox(destination, _snowmanx);
      if (!mode.allowsOverlap() && _snowmanxx.intersects(_snowman)) {
         throw OVERLAP_EXCEPTION.create();
      } else {
         int _snowmanxxx = _snowman.getBlockCountX() * _snowman.getBlockCountY() * _snowman.getBlockCountZ();
         if (_snowmanxxx > 32768) {
            throw TOO_BIG_EXCEPTION.create(32768, _snowmanxxx);
         } else {
            ServerWorld _snowmanxxxx = source.getWorld();
            if (_snowmanxxxx.isRegionLoaded(begin, end) && _snowmanxxxx.isRegionLoaded(destination, _snowmanx)) {
               List<CloneCommand.BlockInfo> _snowmanxxxxx = Lists.newArrayList();
               List<CloneCommand.BlockInfo> _snowmanxxxxxx = Lists.newArrayList();
               List<CloneCommand.BlockInfo> _snowmanxxxxxxx = Lists.newArrayList();
               Deque<BlockPos> _snowmanxxxxxxxx = Lists.newLinkedList();
               BlockPos _snowmanxxxxxxxxx = new BlockPos(_snowmanxx.minX - _snowman.minX, _snowmanxx.minY - _snowman.minY, _snowmanxx.minZ - _snowman.minZ);

               for (int _snowmanxxxxxxxxxx = _snowman.minZ; _snowmanxxxxxxxxxx <= _snowman.maxZ; _snowmanxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxx = _snowman.minY; _snowmanxxxxxxxxxxx <= _snowman.maxY; _snowmanxxxxxxxxxxx++) {
                     for (int _snowmanxxxxxxxxxxxx = _snowman.minX; _snowmanxxxxxxxxxxxx <= _snowman.maxX; _snowmanxxxxxxxxxxxx++) {
                        BlockPos _snowmanxxxxxxxxxxxxx = new BlockPos(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxx);
                        BlockPos _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.add(_snowmanxxxxxxxxx);
                        CachedBlockPosition _snowmanxxxxxxxxxxxxxxx = new CachedBlockPosition(_snowmanxxxx, _snowmanxxxxxxxxxxxxx, false);
                        BlockState _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.getBlockState();
                        if (filter.test(_snowmanxxxxxxxxxxxxxxx)) {
                           BlockEntity _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxx.getBlockEntity(_snowmanxxxxxxxxxxxxx);
                           if (_snowmanxxxxxxxxxxxxxxxxx != null) {
                              CompoundTag _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx.toTag(new CompoundTag());
                              _snowmanxxxxxx.add(new CloneCommand.BlockInfo(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx));
                              _snowmanxxxxxxxx.addLast(_snowmanxxxxxxxxxxxxx);
                           } else if (!_snowmanxxxxxxxxxxxxxxxx.isOpaqueFullCube(_snowmanxxxx, _snowmanxxxxxxxxxxxxx) && !_snowmanxxxxxxxxxxxxxxxx.isFullCube(_snowmanxxxx, _snowmanxxxxxxxxxxxxx)) {
                              _snowmanxxxxxxx.add(new CloneCommand.BlockInfo(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, null));
                              _snowmanxxxxxxxx.addFirst(_snowmanxxxxxxxxxxxxx);
                           } else {
                              _snowmanxxxxx.add(new CloneCommand.BlockInfo(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, null));
                              _snowmanxxxxxxxx.addLast(_snowmanxxxxxxxxxxxxx);
                           }
                        }
                     }
                  }
               }

               if (mode == CloneCommand.Mode.MOVE) {
                  for (BlockPos _snowmanxxxxxxxxxx : _snowmanxxxxxxxx) {
                     BlockEntity _snowmanxxxxxxxxxxx = _snowmanxxxx.getBlockEntity(_snowmanxxxxxxxxxx);
                     Clearable.clear(_snowmanxxxxxxxxxxx);
                     _snowmanxxxx.setBlockState(_snowmanxxxxxxxxxx, Blocks.BARRIER.getDefaultState(), 2);
                  }

                  for (BlockPos _snowmanxxxxxxxxxx : _snowmanxxxxxxxx) {
                     _snowmanxxxx.setBlockState(_snowmanxxxxxxxxxx, Blocks.AIR.getDefaultState(), 3);
                  }
               }

               List<CloneCommand.BlockInfo> _snowmanxxxxxxxxxx = Lists.newArrayList();
               _snowmanxxxxxxxxxx.addAll(_snowmanxxxxx);
               _snowmanxxxxxxxxxx.addAll(_snowmanxxxxxx);
               _snowmanxxxxxxxxxx.addAll(_snowmanxxxxxxx);
               List<CloneCommand.BlockInfo> _snowmanxxxxxxxxxxx = Lists.reverse(_snowmanxxxxxxxxxx);

               for (CloneCommand.BlockInfo _snowmanxxxxxxxxxxxxx : _snowmanxxxxxxxxxxx) {
                  BlockEntity _snowmanxxxxxxxxxxxxxx = _snowmanxxxx.getBlockEntity(_snowmanxxxxxxxxxxxxx.pos);
                  Clearable.clear(_snowmanxxxxxxxxxxxxxx);
                  _snowmanxxxx.setBlockState(_snowmanxxxxxxxxxxxxx.pos, Blocks.BARRIER.getDefaultState(), 2);
               }

               int _snowmanxxxxxxxxxxxxx = 0;

               for (CloneCommand.BlockInfo _snowmanxxxxxxxxxxxxxx : _snowmanxxxxxxxxxx) {
                  if (_snowmanxxxx.setBlockState(_snowmanxxxxxxxxxxxxxx.pos, _snowmanxxxxxxxxxxxxxx.state, 2)) {
                     _snowmanxxxxxxxxxxxxx++;
                  }
               }

               for (CloneCommand.BlockInfo _snowmanxxxxxxxxxxxxxxx : _snowmanxxxxxx) {
                  BlockEntity _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxx.getBlockEntity(_snowmanxxxxxxxxxxxxxxx.pos);
                  if (_snowmanxxxxxxxxxxxxxxx.blockEntityTag != null && _snowmanxxxxxxxxxxxxxxxx != null) {
                     _snowmanxxxxxxxxxxxxxxx.blockEntityTag.putInt("x", _snowmanxxxxxxxxxxxxxxx.pos.getX());
                     _snowmanxxxxxxxxxxxxxxx.blockEntityTag.putInt("y", _snowmanxxxxxxxxxxxxxxx.pos.getY());
                     _snowmanxxxxxxxxxxxxxxx.blockEntityTag.putInt("z", _snowmanxxxxxxxxxxxxxxx.pos.getZ());
                     _snowmanxxxxxxxxxxxxxxxx.fromTag(_snowmanxxxxxxxxxxxxxxx.state, _snowmanxxxxxxxxxxxxxxx.blockEntityTag);
                     _snowmanxxxxxxxxxxxxxxxx.markDirty();
                  }

                  _snowmanxxxx.setBlockState(_snowmanxxxxxxxxxxxxxxx.pos, _snowmanxxxxxxxxxxxxxxx.state, 2);
               }

               for (CloneCommand.BlockInfo _snowmanxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxx) {
                  _snowmanxxxx.updateNeighbors(_snowmanxxxxxxxxxxxxxxx.pos, _snowmanxxxxxxxxxxxxxxx.state.getBlock());
               }

               _snowmanxxxx.getBlockTickScheduler().copyScheduledTicks(_snowman, _snowmanxxxxxxxxx);
               if (_snowmanxxxxxxxxxxxxx == 0) {
                  throw FAILED_EXCEPTION.create();
               } else {
                  source.sendFeedback(new TranslatableText("commands.clone.success", _snowmanxxxxxxxxxxxxx), true);
                  return _snowmanxxxxxxxxxxxxx;
               }
            } else {
               throw BlockPosArgumentType.UNLOADED_EXCEPTION.create();
            }
         }
      }
   }

   static class BlockInfo {
      public final BlockPos pos;
      public final BlockState state;
      @Nullable
      public final CompoundTag blockEntityTag;

      public BlockInfo(BlockPos pos, BlockState state, @Nullable CompoundTag blockEntityTag) {
         this.pos = pos;
         this.state = state;
         this.blockEntityTag = blockEntityTag;
      }
   }

   static enum Mode {
      FORCE(true),
      MOVE(true),
      NORMAL(false);

      private final boolean allowsOverlap;

      private Mode(boolean allowsOverlap) {
         this.allowsOverlap = allowsOverlap;
      }

      public boolean allowsOverlap() {
         return this.allowsOverlap;
      }
   }
}
