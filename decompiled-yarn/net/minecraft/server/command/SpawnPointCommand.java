package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.command.argument.AngleArgumentType;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class SpawnPointCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("spawnpoint").requires(_snowman -> _snowman.hasPermissionLevel(2)))
               .executes(
                  _snowman -> execute(
                        (ServerCommandSource)_snowman.getSource(),
                        Collections.singleton(((ServerCommandSource)_snowman.getSource()).getPlayer()),
                        new BlockPos(((ServerCommandSource)_snowman.getSource()).getPosition()),
                        0.0F
                     )
               ))
            .then(
               ((RequiredArgumentBuilder)CommandManager.argument("targets", EntityArgumentType.players())
                     .executes(
                        _snowman -> execute(
                              (ServerCommandSource)_snowman.getSource(),
                              EntityArgumentType.getPlayers(_snowman, "targets"),
                              new BlockPos(((ServerCommandSource)_snowman.getSource()).getPosition()),
                              0.0F
                           )
                     ))
                  .then(
                     ((RequiredArgumentBuilder)CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                           .executes(
                              _snowman -> execute(
                                    (ServerCommandSource)_snowman.getSource(),
                                    EntityArgumentType.getPlayers(_snowman, "targets"),
                                    BlockPosArgumentType.getBlockPos(_snowman, "pos"),
                                    0.0F
                                 )
                           ))
                        .then(
                           CommandManager.argument("angle", AngleArgumentType.angle())
                              .executes(
                                 _snowman -> execute(
                                       (ServerCommandSource)_snowman.getSource(),
                                       EntityArgumentType.getPlayers(_snowman, "targets"),
                                       BlockPosArgumentType.getBlockPos(_snowman, "pos"),
                                       AngleArgumentType.getAngle(_snowman, "angle")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int execute(ServerCommandSource source, Collection<ServerPlayerEntity> targets, BlockPos pos, float angle) {
      RegistryKey<World> _snowman = source.getWorld().getRegistryKey();

      for (ServerPlayerEntity _snowmanx : targets) {
         _snowmanx.setSpawnPoint(_snowman, pos, angle, true, false);
      }

      String _snowmanx = _snowman.getValue().toString();
      if (targets.size() == 1) {
         source.sendFeedback(
            new TranslatableText(
               "commands.spawnpoint.success.single", pos.getX(), pos.getY(), pos.getZ(), angle, _snowmanx, targets.iterator().next().getDisplayName()
            ),
            true
         );
      } else {
         source.sendFeedback(new TranslatableText("commands.spawnpoint.success.multiple", pos.getX(), pos.getY(), pos.getZ(), angle, _snowmanx, targets.size()), true);
      }

      return targets.size();
   }
}
