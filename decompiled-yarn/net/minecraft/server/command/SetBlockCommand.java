package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;

public class SetBlockCommand {
   private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.setblock.failed"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("setblock").requires(_snowman -> _snowman.hasPermissionLevel(2)))
            .then(
               CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                    "block", BlockStateArgumentType.blockState()
                                 )
                                 .executes(
                                    _snowman -> execute(
                                          (ServerCommandSource)_snowman.getSource(),
                                          BlockPosArgumentType.getLoadedBlockPos(_snowman, "pos"),
                                          BlockStateArgumentType.getBlockState(_snowman, "block"),
                                          SetBlockCommand.Mode.REPLACE,
                                          null
                                       )
                                 ))
                              .then(
                                 CommandManager.literal("destroy")
                                    .executes(
                                       _snowman -> execute(
                                             (ServerCommandSource)_snowman.getSource(),
                                             BlockPosArgumentType.getLoadedBlockPos(_snowman, "pos"),
                                             BlockStateArgumentType.getBlockState(_snowman, "block"),
                                             SetBlockCommand.Mode.DESTROY,
                                             null
                                          )
                                    )
                              ))
                           .then(
                              CommandManager.literal("keep")
                                 .executes(
                                    _snowman -> execute(
                                          (ServerCommandSource)_snowman.getSource(),
                                          BlockPosArgumentType.getLoadedBlockPos(_snowman, "pos"),
                                          BlockStateArgumentType.getBlockState(_snowman, "block"),
                                          SetBlockCommand.Mode.REPLACE,
                                          _snowmanx -> _snowmanx.getWorld().isAir(_snowmanx.getBlockPos())
                                       )
                                 )
                           ))
                        .then(
                           CommandManager.literal("replace")
                              .executes(
                                 _snowman -> execute(
                                       (ServerCommandSource)_snowman.getSource(),
                                       BlockPosArgumentType.getLoadedBlockPos(_snowman, "pos"),
                                       BlockStateArgumentType.getBlockState(_snowman, "block"),
                                       SetBlockCommand.Mode.REPLACE,
                                       null
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int execute(
      ServerCommandSource source, BlockPos pos, BlockStateArgument block, SetBlockCommand.Mode mode, @Nullable Predicate<CachedBlockPosition> condition
   ) throws CommandSyntaxException {
      ServerWorld _snowman = source.getWorld();
      if (condition != null && !condition.test(new CachedBlockPosition(_snowman, pos, true))) {
         throw FAILED_EXCEPTION.create();
      } else {
         boolean _snowmanx;
         if (mode == SetBlockCommand.Mode.DESTROY) {
            _snowman.breakBlock(pos, true);
            _snowmanx = !block.getBlockState().isAir() || !_snowman.getBlockState(pos).isAir();
         } else {
            BlockEntity _snowmanxx = _snowman.getBlockEntity(pos);
            Clearable.clear(_snowmanxx);
            _snowmanx = true;
         }

         if (_snowmanx && !block.setBlockState(_snowman, pos, 2)) {
            throw FAILED_EXCEPTION.create();
         } else {
            _snowman.updateNeighbors(pos, block.getBlockState().getBlock());
            source.sendFeedback(new TranslatableText("commands.setblock.success", pos.getX(), pos.getY(), pos.getZ()), true);
            return 1;
         }
      }
   }

   public interface Filter {
      @Nullable
      BlockStateArgument filter(BlockBox box, BlockPos pos, BlockStateArgument block, ServerWorld world);
   }

   public static enum Mode {
      REPLACE,
      DESTROY;

      private Mode() {
      }
   }
}
