package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.ParticleArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

public class ParticleCommand {
   private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.particle.failed"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("particle").requires(_snowman -> _snowman.hasPermissionLevel(2)))
            .then(
               ((RequiredArgumentBuilder)CommandManager.argument("name", ParticleArgumentType.particle())
                     .executes(
                        _snowman -> execute(
                              (ServerCommandSource)_snowman.getSource(),
                              ParticleArgumentType.getParticle(_snowman, "name"),
                              ((ServerCommandSource)_snowman.getSource()).getPosition(),
                              Vec3d.ZERO,
                              0.0F,
                              0,
                              false,
                              ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPlayerManager().getPlayerList()
                           )
                     ))
                  .then(
                     ((RequiredArgumentBuilder)CommandManager.argument("pos", Vec3ArgumentType.vec3())
                           .executes(
                              _snowman -> execute(
                                    (ServerCommandSource)_snowman.getSource(),
                                    ParticleArgumentType.getParticle(_snowman, "name"),
                                    Vec3ArgumentType.getVec3(_snowman, "pos"),
                                    Vec3d.ZERO,
                                    0.0F,
                                    0,
                                    false,
                                    ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPlayerManager().getPlayerList()
                                 )
                           ))
                        .then(
                           CommandManager.argument("delta", Vec3ArgumentType.vec3(false))
                              .then(
                                 CommandManager.argument("speed", FloatArgumentType.floatArg(0.0F))
                                    .then(
                                       ((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("count", IntegerArgumentType.integer(0))
                                                .executes(
                                                   _snowman -> execute(
                                                         (ServerCommandSource)_snowman.getSource(),
                                                         ParticleArgumentType.getParticle(_snowman, "name"),
                                                         Vec3ArgumentType.getVec3(_snowman, "pos"),
                                                         Vec3ArgumentType.getVec3(_snowman, "delta"),
                                                         FloatArgumentType.getFloat(_snowman, "speed"),
                                                         IntegerArgumentType.getInteger(_snowman, "count"),
                                                         false,
                                                         ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPlayerManager().getPlayerList()
                                                      )
                                                ))
                                             .then(
                                                ((LiteralArgumentBuilder)CommandManager.literal("force")
                                                      .executes(
                                                         _snowman -> execute(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               ParticleArgumentType.getParticle(_snowman, "name"),
                                                               Vec3ArgumentType.getVec3(_snowman, "pos"),
                                                               Vec3ArgumentType.getVec3(_snowman, "delta"),
                                                               FloatArgumentType.getFloat(_snowman, "speed"),
                                                               IntegerArgumentType.getInteger(_snowman, "count"),
                                                               true,
                                                               ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPlayerManager().getPlayerList()
                                                            )
                                                      ))
                                                   .then(
                                                      CommandManager.argument("viewers", EntityArgumentType.players())
                                                         .executes(
                                                            _snowman -> execute(
                                                                  (ServerCommandSource)_snowman.getSource(),
                                                                  ParticleArgumentType.getParticle(_snowman, "name"),
                                                                  Vec3ArgumentType.getVec3(_snowman, "pos"),
                                                                  Vec3ArgumentType.getVec3(_snowman, "delta"),
                                                                  FloatArgumentType.getFloat(_snowman, "speed"),
                                                                  IntegerArgumentType.getInteger(_snowman, "count"),
                                                                  true,
                                                                  EntityArgumentType.getPlayers(_snowman, "viewers")
                                                               )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             ((LiteralArgumentBuilder)CommandManager.literal("normal")
                                                   .executes(
                                                      _snowman -> execute(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            ParticleArgumentType.getParticle(_snowman, "name"),
                                                            Vec3ArgumentType.getVec3(_snowman, "pos"),
                                                            Vec3ArgumentType.getVec3(_snowman, "delta"),
                                                            FloatArgumentType.getFloat(_snowman, "speed"),
                                                            IntegerArgumentType.getInteger(_snowman, "count"),
                                                            false,
                                                            ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPlayerManager().getPlayerList()
                                                         )
                                                   ))
                                                .then(
                                                   CommandManager.argument("viewers", EntityArgumentType.players())
                                                      .executes(
                                                         _snowman -> execute(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               ParticleArgumentType.getParticle(_snowman, "name"),
                                                               Vec3ArgumentType.getVec3(_snowman, "pos"),
                                                               Vec3ArgumentType.getVec3(_snowman, "delta"),
                                                               FloatArgumentType.getFloat(_snowman, "speed"),
                                                               IntegerArgumentType.getInteger(_snowman, "count"),
                                                               false,
                                                               EntityArgumentType.getPlayers(_snowman, "viewers")
                                                            )
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
      ServerCommandSource source,
      ParticleEffect parameters,
      Vec3d pos,
      Vec3d delta,
      float speed,
      int count,
      boolean force,
      Collection<ServerPlayerEntity> viewers
   ) throws CommandSyntaxException {
      int _snowman = 0;

      for (ServerPlayerEntity _snowmanx : viewers) {
         if (source.getWorld().spawnParticles(_snowmanx, parameters, force, pos.x, pos.y, pos.z, count, delta.x, delta.y, delta.z, (double)speed)) {
            _snowman++;
         }
      }

      if (_snowman == 0) {
         throw FAILED_EXCEPTION.create();
      } else {
         source.sendFeedback(new TranslatableText("commands.particle.success", Registry.PARTICLE_TYPE.getId(parameters.getType()).toString()), true);
         return _snowman;
      }
   }
}
