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
      (object, object2) -> new TranslatableText("commands.clone.toobig", object, object2)
   );
   private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.clone.failed"));
   public static final Predicate<CachedBlockPosition> IS_AIR_PREDICATE = arg -> !arg.getBlockState().isAir();

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("clone").requires(arg -> arg.hasPermissionLevel(2)))
            .then(
               CommandManager.argument("begin", BlockPosArgumentType.blockPos())
                  .then(
                     CommandManager.argument("end", BlockPosArgumentType.blockPos())
                        .then(
                           ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                          "destination", BlockPosArgumentType.blockPos()
                                       )
                                       .executes(
                                          commandContext -> execute(
                                                (ServerCommandSource)commandContext.getSource(),
                                                BlockPosArgumentType.getLoadedBlockPos(commandContext, "begin"),
                                                BlockPosArgumentType.getLoadedBlockPos(commandContext, "end"),
                                                BlockPosArgumentType.getLoadedBlockPos(commandContext, "destination"),
                                                arg -> true,
                                                CloneCommand.Mode.NORMAL
                                             )
                                       ))
                                    .then(
                                       ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("replace")
                                                   .executes(
                                                      commandContext -> execute(
                                                            (ServerCommandSource)commandContext.getSource(),
                                                            BlockPosArgumentType.getLoadedBlockPos(commandContext, "begin"),
                                                            BlockPosArgumentType.getLoadedBlockPos(commandContext, "end"),
                                                            BlockPosArgumentType.getLoadedBlockPos(commandContext, "destination"),
                                                            arg -> true,
                                                            CloneCommand.Mode.NORMAL
                                                         )
                                                   ))
                                                .then(
                                                   CommandManager.literal("force")
                                                      .executes(
                                                         commandContext -> execute(
                                                               (ServerCommandSource)commandContext.getSource(),
                                                               BlockPosArgumentType.getLoadedBlockPos(commandContext, "begin"),
                                                               BlockPosArgumentType.getLoadedBlockPos(commandContext, "end"),
                                                               BlockPosArgumentType.getLoadedBlockPos(commandContext, "destination"),
                                                               arg -> true,
                                                               CloneCommand.Mode.FORCE
                                                            )
                                                      )
                                                ))
                                             .then(
                                                CommandManager.literal("move")
                                                   .executes(
                                                      commandContext -> execute(
                                                            (ServerCommandSource)commandContext.getSource(),
                                                            BlockPosArgumentType.getLoadedBlockPos(commandContext, "begin"),
                                                            BlockPosArgumentType.getLoadedBlockPos(commandContext, "end"),
                                                            BlockPosArgumentType.getLoadedBlockPos(commandContext, "destination"),
                                                            arg -> true,
                                                            CloneCommand.Mode.MOVE
                                                         )
                                                   )
                                             ))
                                          .then(
                                             CommandManager.literal("normal")
                                                .executes(
                                                   commandContext -> execute(
                                                         (ServerCommandSource)commandContext.getSource(),
                                                         BlockPosArgumentType.getLoadedBlockPos(commandContext, "begin"),
                                                         BlockPosArgumentType.getLoadedBlockPos(commandContext, "end"),
                                                         BlockPosArgumentType.getLoadedBlockPos(commandContext, "destination"),
                                                         arg -> true,
                                                         CloneCommand.Mode.NORMAL
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("masked")
                                                .executes(
                                                   commandContext -> execute(
                                                         (ServerCommandSource)commandContext.getSource(),
                                                         BlockPosArgumentType.getLoadedBlockPos(commandContext, "begin"),
                                                         BlockPosArgumentType.getLoadedBlockPos(commandContext, "end"),
                                                         BlockPosArgumentType.getLoadedBlockPos(commandContext, "destination"),
                                                         IS_AIR_PREDICATE,
                                                         CloneCommand.Mode.NORMAL
                                                      )
                                                ))
                                             .then(
                                                CommandManager.literal("force")
                                                   .executes(
                                                      commandContext -> execute(
                                                            (ServerCommandSource)commandContext.getSource(),
                                                            BlockPosArgumentType.getLoadedBlockPos(commandContext, "begin"),
                                                            BlockPosArgumentType.getLoadedBlockPos(commandContext, "end"),
                                                            BlockPosArgumentType.getLoadedBlockPos(commandContext, "destination"),
                                                            IS_AIR_PREDICATE,
                                                            CloneCommand.Mode.FORCE
                                                         )
                                                   )
                                             ))
                                          .then(
                                             CommandManager.literal("move")
                                                .executes(
                                                   commandContext -> execute(
                                                         (ServerCommandSource)commandContext.getSource(),
                                                         BlockPosArgumentType.getLoadedBlockPos(commandContext, "begin"),
                                                         BlockPosArgumentType.getLoadedBlockPos(commandContext, "end"),
                                                         BlockPosArgumentType.getLoadedBlockPos(commandContext, "destination"),
                                                         IS_AIR_PREDICATE,
                                                         CloneCommand.Mode.MOVE
                                                      )
                                                )
                                          ))
                                       .then(
                                          CommandManager.literal("normal")
                                             .executes(
                                                commandContext -> execute(
                                                      (ServerCommandSource)commandContext.getSource(),
                                                      BlockPosArgumentType.getLoadedBlockPos(commandContext, "begin"),
                                                      BlockPosArgumentType.getLoadedBlockPos(commandContext, "end"),
                                                      BlockPosArgumentType.getLoadedBlockPos(commandContext, "destination"),
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
                                                      commandContext -> execute(
                                                            (ServerCommandSource)commandContext.getSource(),
                                                            BlockPosArgumentType.getLoadedBlockPos(commandContext, "begin"),
                                                            BlockPosArgumentType.getLoadedBlockPos(commandContext, "end"),
                                                            BlockPosArgumentType.getLoadedBlockPos(commandContext, "destination"),
                                                            BlockPredicateArgumentType.getBlockPredicate(commandContext, "filter"),
                                                            CloneCommand.Mode.NORMAL
                                                         )
                                                   ))
                                                .then(
                                                   CommandManager.literal("force")
                                                      .executes(
                                                         commandContext -> execute(
                                                               (ServerCommandSource)commandContext.getSource(),
                                                               BlockPosArgumentType.getLoadedBlockPos(commandContext, "begin"),
                                                               BlockPosArgumentType.getLoadedBlockPos(commandContext, "end"),
                                                               BlockPosArgumentType.getLoadedBlockPos(commandContext, "destination"),
                                                               BlockPredicateArgumentType.getBlockPredicate(commandContext, "filter"),
                                                               CloneCommand.Mode.FORCE
                                                            )
                                                      )
                                                ))
                                             .then(
                                                CommandManager.literal("move")
                                                   .executes(
                                                      commandContext -> execute(
                                                            (ServerCommandSource)commandContext.getSource(),
                                                            BlockPosArgumentType.getLoadedBlockPos(commandContext, "begin"),
                                                            BlockPosArgumentType.getLoadedBlockPos(commandContext, "end"),
                                                            BlockPosArgumentType.getLoadedBlockPos(commandContext, "destination"),
                                                            BlockPredicateArgumentType.getBlockPredicate(commandContext, "filter"),
                                                            CloneCommand.Mode.MOVE
                                                         )
                                                   )
                                             ))
                                          .then(
                                             CommandManager.literal("normal")
                                                .executes(
                                                   commandContext -> execute(
                                                         (ServerCommandSource)commandContext.getSource(),
                                                         BlockPosArgumentType.getLoadedBlockPos(commandContext, "begin"),
                                                         BlockPosArgumentType.getLoadedBlockPos(commandContext, "end"),
                                                         BlockPosArgumentType.getLoadedBlockPos(commandContext, "destination"),
                                                         BlockPredicateArgumentType.getBlockPredicate(commandContext, "filter"),
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
      BlockBox lv = new BlockBox(begin, end);
      BlockPos lv2 = destination.add(lv.getDimensions());
      BlockBox lv3 = new BlockBox(destination, lv2);
      if (!mode.allowsOverlap() && lv3.intersects(lv)) {
         throw OVERLAP_EXCEPTION.create();
      } else {
         int i = lv.getBlockCountX() * lv.getBlockCountY() * lv.getBlockCountZ();
         if (i > 32768) {
            throw TOO_BIG_EXCEPTION.create(32768, i);
         } else {
            ServerWorld lv4 = source.getWorld();
            if (lv4.isRegionLoaded(begin, end) && lv4.isRegionLoaded(destination, lv2)) {
               List<CloneCommand.BlockInfo> list = Lists.newArrayList();
               List<CloneCommand.BlockInfo> list2 = Lists.newArrayList();
               List<CloneCommand.BlockInfo> list3 = Lists.newArrayList();
               Deque<BlockPos> deque = Lists.newLinkedList();
               BlockPos lv5 = new BlockPos(lv3.minX - lv.minX, lv3.minY - lv.minY, lv3.minZ - lv.minZ);

               for (int j = lv.minZ; j <= lv.maxZ; j++) {
                  for (int k = lv.minY; k <= lv.maxY; k++) {
                     for (int l = lv.minX; l <= lv.maxX; l++) {
                        BlockPos lv6 = new BlockPos(l, k, j);
                        BlockPos lv7 = lv6.add(lv5);
                        CachedBlockPosition lv8 = new CachedBlockPosition(lv4, lv6, false);
                        BlockState lv9 = lv8.getBlockState();
                        if (filter.test(lv8)) {
                           BlockEntity lv10 = lv4.getBlockEntity(lv6);
                           if (lv10 != null) {
                              CompoundTag lv11 = lv10.toTag(new CompoundTag());
                              list2.add(new CloneCommand.BlockInfo(lv7, lv9, lv11));
                              deque.addLast(lv6);
                           } else if (!lv9.isOpaqueFullCube(lv4, lv6) && !lv9.isFullCube(lv4, lv6)) {
                              list3.add(new CloneCommand.BlockInfo(lv7, lv9, null));
                              deque.addFirst(lv6);
                           } else {
                              list.add(new CloneCommand.BlockInfo(lv7, lv9, null));
                              deque.addLast(lv6);
                           }
                        }
                     }
                  }
               }

               if (mode == CloneCommand.Mode.MOVE) {
                  for (BlockPos lv12 : deque) {
                     BlockEntity lv13 = lv4.getBlockEntity(lv12);
                     Clearable.clear(lv13);
                     lv4.setBlockState(lv12, Blocks.BARRIER.getDefaultState(), 2);
                  }

                  for (BlockPos lv14 : deque) {
                     lv4.setBlockState(lv14, Blocks.AIR.getDefaultState(), 3);
                  }
               }

               List<CloneCommand.BlockInfo> list4 = Lists.newArrayList();
               list4.addAll(list);
               list4.addAll(list2);
               list4.addAll(list3);
               List<CloneCommand.BlockInfo> list5 = Lists.reverse(list4);

               for (CloneCommand.BlockInfo lv15 : list5) {
                  BlockEntity lv16 = lv4.getBlockEntity(lv15.pos);
                  Clearable.clear(lv16);
                  lv4.setBlockState(lv15.pos, Blocks.BARRIER.getDefaultState(), 2);
               }

               int m = 0;

               for (CloneCommand.BlockInfo lv17 : list4) {
                  if (lv4.setBlockState(lv17.pos, lv17.state, 2)) {
                     m++;
                  }
               }

               for (CloneCommand.BlockInfo lv18 : list2) {
                  BlockEntity lv19 = lv4.getBlockEntity(lv18.pos);
                  if (lv18.blockEntityTag != null && lv19 != null) {
                     lv18.blockEntityTag.putInt("x", lv18.pos.getX());
                     lv18.blockEntityTag.putInt("y", lv18.pos.getY());
                     lv18.blockEntityTag.putInt("z", lv18.pos.getZ());
                     lv19.fromTag(lv18.state, lv18.blockEntityTag);
                     lv19.markDirty();
                  }

                  lv4.setBlockState(lv18.pos, lv18.state, 2);
               }

               for (CloneCommand.BlockInfo lv20 : list5) {
                  lv4.updateNeighbors(lv20.pos, lv20.state.getBlock());
               }

               lv4.getBlockTickScheduler().copyScheduledTicks(lv, lv5);
               if (m == 0) {
                  throw FAILED_EXCEPTION.create();
               } else {
                  source.sendFeedback(new TranslatableText("commands.clone.success", m), true);
                  return m;
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
