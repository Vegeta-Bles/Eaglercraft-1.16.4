package net.minecraft.server.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.BlockPredicateArgumentType;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;

public class FillCommand {
   private static final Dynamic2CommandExceptionType TOO_BIG_EXCEPTION = new Dynamic2CommandExceptionType(
      (_snowman, _snowmanx) -> new TranslatableText("commands.fill.toobig", _snowman, _snowmanx)
   );
   private static final BlockStateArgument AIR_BLOCK_ARGUMENT = new BlockStateArgument(Blocks.AIR.getDefaultState(), Collections.emptySet(), null);
   private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.fill.failed"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("fill").requires(_snowman -> _snowman.hasPermissionLevel(2)))
            .then(
               CommandManager.argument("from", BlockPosArgumentType.blockPos())
                  .then(
                     CommandManager.argument("to", BlockPosArgumentType.blockPos())
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                                "block", BlockStateArgumentType.blockState()
                                             )
                                             .executes(
                                                _snowman -> execute(
                                                      (ServerCommandSource)_snowman.getSource(),
                                                      new BlockBox(
                                                         BlockPosArgumentType.getLoadedBlockPos(_snowman, "from"), BlockPosArgumentType.getLoadedBlockPos(_snowman, "to")
                                                      ),
                                                      BlockStateArgumentType.getBlockState(_snowman, "block"),
                                                      FillCommand.Mode.REPLACE,
                                                      null
                                                   )
                                             ))
                                          .then(
                                             ((LiteralArgumentBuilder)CommandManager.literal("replace")
                                                   .executes(
                                                      _snowman -> execute(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            new BlockBox(
                                                               BlockPosArgumentType.getLoadedBlockPos(_snowman, "from"),
                                                               BlockPosArgumentType.getLoadedBlockPos(_snowman, "to")
                                                            ),
                                                            BlockStateArgumentType.getBlockState(_snowman, "block"),
                                                            FillCommand.Mode.REPLACE,
                                                            null
                                                         )
                                                   ))
                                                .then(
                                                   CommandManager.argument("filter", BlockPredicateArgumentType.blockPredicate())
                                                      .executes(
                                                         _snowman -> execute(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               new BlockBox(
                                                                  BlockPosArgumentType.getLoadedBlockPos(_snowman, "from"),
                                                                  BlockPosArgumentType.getLoadedBlockPos(_snowman, "to")
                                                               ),
                                                               BlockStateArgumentType.getBlockState(_snowman, "block"),
                                                               FillCommand.Mode.REPLACE,
                                                               BlockPredicateArgumentType.getBlockPredicate(_snowman, "filter")
                                                            )
                                                      )
                                                )
                                          ))
                                       .then(
                                          CommandManager.literal("keep")
                                             .executes(
                                                _snowman -> execute(
                                                      (ServerCommandSource)_snowman.getSource(),
                                                      new BlockBox(
                                                         BlockPosArgumentType.getLoadedBlockPos(_snowman, "from"), BlockPosArgumentType.getLoadedBlockPos(_snowman, "to")
                                                      ),
                                                      BlockStateArgumentType.getBlockState(_snowman, "block"),
                                                      FillCommand.Mode.REPLACE,
                                                      _snowmanx -> _snowmanx.getWorld().isAir(_snowmanx.getBlockPos())
                                                   )
                                             )
                                       ))
                                    .then(
                                       CommandManager.literal("outline")
                                          .executes(
                                             _snowman -> execute(
                                                   (ServerCommandSource)_snowman.getSource(),
                                                   new BlockBox(
                                                      BlockPosArgumentType.getLoadedBlockPos(_snowman, "from"), BlockPosArgumentType.getLoadedBlockPos(_snowman, "to")
                                                   ),
                                                   BlockStateArgumentType.getBlockState(_snowman, "block"),
                                                   FillCommand.Mode.OUTLINE,
                                                   null
                                                )
                                          )
                                    ))
                                 .then(
                                    CommandManager.literal("hollow")
                                       .executes(
                                          _snowman -> execute(
                                                (ServerCommandSource)_snowman.getSource(),
                                                new BlockBox(BlockPosArgumentType.getLoadedBlockPos(_snowman, "from"), BlockPosArgumentType.getLoadedBlockPos(_snowman, "to")),
                                                BlockStateArgumentType.getBlockState(_snowman, "block"),
                                                FillCommand.Mode.HOLLOW,
                                                null
                                             )
                                       )
                                 ))
                              .then(
                                 CommandManager.literal("destroy")
                                    .executes(
                                       _snowman -> execute(
                                             (ServerCommandSource)_snowman.getSource(),
                                             new BlockBox(BlockPosArgumentType.getLoadedBlockPos(_snowman, "from"), BlockPosArgumentType.getLoadedBlockPos(_snowman, "to")),
                                             BlockStateArgumentType.getBlockState(_snowman, "block"),
                                             FillCommand.Mode.DESTROY,
                                             null
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int execute(
      ServerCommandSource source, BlockBox range, BlockStateArgument block, FillCommand.Mode mode, @Nullable Predicate<CachedBlockPosition> filter
   ) throws CommandSyntaxException {
      int _snowman = range.getBlockCountX() * range.getBlockCountY() * range.getBlockCountZ();
      if (_snowman > 32768) {
         throw TOO_BIG_EXCEPTION.create(32768, _snowman);
      } else {
         List<BlockPos> _snowmanx = Lists.newArrayList();
         ServerWorld _snowmanxx = source.getWorld();
         int _snowmanxxx = 0;

         for (BlockPos _snowmanxxxx : BlockPos.iterate(range.minX, range.minY, range.minZ, range.maxX, range.maxY, range.maxZ)) {
            if (filter == null || filter.test(new CachedBlockPosition(_snowmanxx, _snowmanxxxx, true))) {
               BlockStateArgument _snowmanxxxxx = mode.filter.filter(range, _snowmanxxxx, block, _snowmanxx);
               if (_snowmanxxxxx != null) {
                  BlockEntity _snowmanxxxxxx = _snowmanxx.getBlockEntity(_snowmanxxxx);
                  Clearable.clear(_snowmanxxxxxx);
                  if (_snowmanxxxxx.setBlockState(_snowmanxx, _snowmanxxxx, 2)) {
                     _snowmanx.add(_snowmanxxxx.toImmutable());
                     _snowmanxxx++;
                  }
               }
            }
         }

         for (BlockPos _snowmanxxxxx : _snowmanx) {
            Block _snowmanxxxxxx = _snowmanxx.getBlockState(_snowmanxxxxx).getBlock();
            _snowmanxx.updateNeighbors(_snowmanxxxxx, _snowmanxxxxxx);
         }

         if (_snowmanxxx == 0) {
            throw FAILED_EXCEPTION.create();
         } else {
            source.sendFeedback(new TranslatableText("commands.fill.success", _snowmanxxx), true);
            return _snowmanxxx;
         }
      }
   }

   static enum Mode {
      REPLACE((_snowman, _snowmanx, _snowmanxx, _snowmanxxx) -> _snowmanxx),
      OUTLINE(
         (_snowman, _snowmanx, _snowmanxx, _snowmanxxx) -> _snowmanx.getX() != _snowman.minX
                  && _snowmanx.getX() != _snowman.maxX
                  && _snowmanx.getY() != _snowman.minY
                  && _snowmanx.getY() != _snowman.maxY
                  && _snowmanx.getZ() != _snowman.minZ
                  && _snowmanx.getZ() != _snowman.maxZ
               ? null
               : _snowmanxx
      ),
      HOLLOW(
         (_snowman, _snowmanx, _snowmanxx, _snowmanxxx) -> _snowmanx.getX() != _snowman.minX
                  && _snowmanx.getX() != _snowman.maxX
                  && _snowmanx.getY() != _snowman.minY
                  && _snowmanx.getY() != _snowman.maxY
                  && _snowmanx.getZ() != _snowman.minZ
                  && _snowmanx.getZ() != _snowman.maxZ
               ? FillCommand.AIR_BLOCK_ARGUMENT
               : _snowmanxx
      ),
      DESTROY((_snowman, _snowmanx, _snowmanxx, _snowmanxxx) -> {
         _snowmanxxx.breakBlock(_snowmanx, true);
         return _snowmanxx;
      });

      public final SetBlockCommand.Filter filter;

      private Mode(SetBlockCommand.Filter var3) {
         this.filter = _snowman;
      }
   }
}
