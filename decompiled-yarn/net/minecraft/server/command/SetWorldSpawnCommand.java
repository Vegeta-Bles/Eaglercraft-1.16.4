package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.command.argument.AngleArgumentType;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class SetWorldSpawnCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("setworldspawn")
                  .requires(_snowman -> _snowman.hasPermissionLevel(2)))
               .executes(_snowman -> execute((ServerCommandSource)_snowman.getSource(), new BlockPos(((ServerCommandSource)_snowman.getSource()).getPosition()), 0.0F)))
            .then(
               ((RequiredArgumentBuilder)CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                     .executes(_snowman -> execute((ServerCommandSource)_snowman.getSource(), BlockPosArgumentType.getBlockPos(_snowman, "pos"), 0.0F)))
                  .then(
                     CommandManager.argument("angle", AngleArgumentType.angle())
                        .executes(
                           _snowman -> execute((ServerCommandSource)_snowman.getSource(), BlockPosArgumentType.getBlockPos(_snowman, "pos"), AngleArgumentType.getAngle(_snowman, "angle"))
                        )
                  )
            )
      );
   }

   private static int execute(ServerCommandSource source, BlockPos pos, float angle) {
      source.getWorld().setSpawnPos(pos, angle);
      source.sendFeedback(new TranslatableText("commands.setworldspawn.success", pos.getX(), pos.getY(), pos.getZ(), angle), true);
      return 1;
   }
}
