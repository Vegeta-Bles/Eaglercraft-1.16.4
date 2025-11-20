package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.command.argument.DefaultPosArgument;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.PosArgument;
import net.minecraft.command.argument.RotationArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TeleportCommand {
   private static final SimpleCommandExceptionType INVALID_POSITION_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.teleport.invalidPosition")
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      LiteralCommandNode<ServerCommandSource> _snowman = dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("teleport")
                     .requires(_snowmanx -> _snowmanx.hasPermissionLevel(2)))
                  .then(
                     ((RequiredArgumentBuilder)CommandManager.argument("targets", EntityArgumentType.entities())
                           .then(
                              ((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("location", Vec3ArgumentType.vec3())
                                       .executes(
                                          _snowmanx -> execute(
                                                (ServerCommandSource)_snowmanx.getSource(),
                                                EntityArgumentType.getEntities(_snowmanx, "targets"),
                                                ((ServerCommandSource)_snowmanx.getSource()).getWorld(),
                                                Vec3ArgumentType.getPosArgument(_snowmanx, "location"),
                                                null,
                                                null
                                             )
                                       ))
                                    .then(
                                       CommandManager.argument("rotation", RotationArgumentType.rotation())
                                          .executes(
                                             _snowmanx -> execute(
                                                   (ServerCommandSource)_snowmanx.getSource(),
                                                   EntityArgumentType.getEntities(_snowmanx, "targets"),
                                                   ((ServerCommandSource)_snowmanx.getSource()).getWorld(),
                                                   Vec3ArgumentType.getPosArgument(_snowmanx, "location"),
                                                   RotationArgumentType.getRotation(_snowmanx, "rotation"),
                                                   null
                                                )
                                          )
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)CommandManager.literal("facing")
                                          .then(
                                             CommandManager.literal("entity")
                                                .then(
                                                   ((RequiredArgumentBuilder)CommandManager.argument("facingEntity", EntityArgumentType.entity())
                                                         .executes(
                                                            _snowmanx -> execute(
                                                                  (ServerCommandSource)_snowmanx.getSource(),
                                                                  EntityArgumentType.getEntities(_snowmanx, "targets"),
                                                                  ((ServerCommandSource)_snowmanx.getSource()).getWorld(),
                                                                  Vec3ArgumentType.getPosArgument(_snowmanx, "location"),
                                                                  null,
                                                                  new TeleportCommand.LookTarget(
                                                                     EntityArgumentType.getEntity(_snowmanx, "facingEntity"),
                                                                     EntityAnchorArgumentType.EntityAnchor.FEET
                                                                  )
                                                               )
                                                         ))
                                                      .then(
                                                         CommandManager.argument("facingAnchor", EntityAnchorArgumentType.entityAnchor())
                                                            .executes(
                                                               _snowmanx -> execute(
                                                                     (ServerCommandSource)_snowmanx.getSource(),
                                                                     EntityArgumentType.getEntities(_snowmanx, "targets"),
                                                                     ((ServerCommandSource)_snowmanx.getSource()).getWorld(),
                                                                     Vec3ArgumentType.getPosArgument(_snowmanx, "location"),
                                                                     null,
                                                                     new TeleportCommand.LookTarget(
                                                                        EntityArgumentType.getEntity(_snowmanx, "facingEntity"),
                                                                        EntityAnchorArgumentType.getEntityAnchor(_snowmanx, "facingAnchor")
                                                                     )
                                                                  )
                                                            )
                                                      )
                                                )
                                          ))
                                       .then(
                                          CommandManager.argument("facingLocation", Vec3ArgumentType.vec3())
                                             .executes(
                                                _snowmanx -> execute(
                                                      (ServerCommandSource)_snowmanx.getSource(),
                                                      EntityArgumentType.getEntities(_snowmanx, "targets"),
                                                      ((ServerCommandSource)_snowmanx.getSource()).getWorld(),
                                                      Vec3ArgumentType.getPosArgument(_snowmanx, "location"),
                                                      null,
                                                      new TeleportCommand.LookTarget(Vec3ArgumentType.getVec3(_snowmanx, "facingLocation"))
                                                   )
                                             )
                                       )
                                 )
                           ))
                        .then(
                           CommandManager.argument("destination", EntityArgumentType.entity())
                              .executes(
                                 _snowmanx -> execute(
                                       (ServerCommandSource)_snowmanx.getSource(),
                                       EntityArgumentType.getEntities(_snowmanx, "targets"),
                                       EntityArgumentType.getEntity(_snowmanx, "destination")
                                    )
                              )
                        )
                  ))
               .then(
                  CommandManager.argument("location", Vec3ArgumentType.vec3())
                     .executes(
                        _snowmanx -> execute(
                              (ServerCommandSource)_snowmanx.getSource(),
                              Collections.singleton(((ServerCommandSource)_snowmanx.getSource()).getEntityOrThrow()),
                              ((ServerCommandSource)_snowmanx.getSource()).getWorld(),
                              Vec3ArgumentType.getPosArgument(_snowmanx, "location"),
                              DefaultPosArgument.zero(),
                              null
                           )
                     )
               ))
            .then(
               CommandManager.argument("destination", EntityArgumentType.entity())
                  .executes(
                     _snowmanx -> execute(
                           (ServerCommandSource)_snowmanx.getSource(),
                           Collections.singleton(((ServerCommandSource)_snowmanx.getSource()).getEntityOrThrow()),
                           EntityArgumentType.getEntity(_snowmanx, "destination")
                        )
                  )
            )
      );
      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("tp").requires(_snowmanx -> _snowmanx.hasPermissionLevel(2))).redirect(_snowman));
   }

   private static int execute(ServerCommandSource source, Collection<? extends Entity> targets, Entity destination) throws CommandSyntaxException {
      for (Entity _snowman : targets) {
         teleport(
            source,
            _snowman,
            (ServerWorld)destination.world,
            destination.getX(),
            destination.getY(),
            destination.getZ(),
            EnumSet.noneOf(PlayerPositionLookS2CPacket.Flag.class),
            destination.yaw,
            destination.pitch,
            null
         );
      }

      if (targets.size() == 1) {
         source.sendFeedback(
            new TranslatableText("commands.teleport.success.entity.single", targets.iterator().next().getDisplayName(), destination.getDisplayName()), true
         );
      } else {
         source.sendFeedback(new TranslatableText("commands.teleport.success.entity.multiple", targets.size(), destination.getDisplayName()), true);
      }

      return targets.size();
   }

   private static int execute(
      ServerCommandSource source,
      Collection<? extends Entity> targets,
      ServerWorld world,
      PosArgument location,
      @Nullable PosArgument rotation,
      @Nullable TeleportCommand.LookTarget facingLocation
   ) throws CommandSyntaxException {
      Vec3d _snowman = location.toAbsolutePos(source);
      Vec2f _snowmanx = rotation == null ? null : rotation.toAbsoluteRotation(source);
      Set<PlayerPositionLookS2CPacket.Flag> _snowmanxx = EnumSet.noneOf(PlayerPositionLookS2CPacket.Flag.class);
      if (location.isXRelative()) {
         _snowmanxx.add(PlayerPositionLookS2CPacket.Flag.X);
      }

      if (location.isYRelative()) {
         _snowmanxx.add(PlayerPositionLookS2CPacket.Flag.Y);
      }

      if (location.isZRelative()) {
         _snowmanxx.add(PlayerPositionLookS2CPacket.Flag.Z);
      }

      if (rotation == null) {
         _snowmanxx.add(PlayerPositionLookS2CPacket.Flag.X_ROT);
         _snowmanxx.add(PlayerPositionLookS2CPacket.Flag.Y_ROT);
      } else {
         if (rotation.isXRelative()) {
            _snowmanxx.add(PlayerPositionLookS2CPacket.Flag.X_ROT);
         }

         if (rotation.isYRelative()) {
            _snowmanxx.add(PlayerPositionLookS2CPacket.Flag.Y_ROT);
         }
      }

      for (Entity _snowmanxxx : targets) {
         if (rotation == null) {
            teleport(source, _snowmanxxx, world, _snowman.x, _snowman.y, _snowman.z, _snowmanxx, _snowmanxxx.yaw, _snowmanxxx.pitch, facingLocation);
         } else {
            teleport(source, _snowmanxxx, world, _snowman.x, _snowman.y, _snowman.z, _snowmanxx, _snowmanx.y, _snowmanx.x, facingLocation);
         }
      }

      if (targets.size() == 1) {
         source.sendFeedback(new TranslatableText("commands.teleport.success.location.single", targets.iterator().next().getDisplayName(), _snowman.x, _snowman.y, _snowman.z), true);
      } else {
         source.sendFeedback(new TranslatableText("commands.teleport.success.location.multiple", targets.size(), _snowman.x, _snowman.y, _snowman.z), true);
      }

      return targets.size();
   }

   private static void teleport(
      ServerCommandSource source,
      Entity target,
      ServerWorld world,
      double x,
      double y,
      double z,
      Set<PlayerPositionLookS2CPacket.Flag> movementFlags,
      float yaw,
      float pitch,
      @Nullable TeleportCommand.LookTarget facingLocation
   ) throws CommandSyntaxException {
      BlockPos _snowman = new BlockPos(x, y, z);
      if (!World.isValid(_snowman)) {
         throw INVALID_POSITION_EXCEPTION.create();
      } else {
         if (target instanceof ServerPlayerEntity) {
            ChunkPos _snowmanx = new ChunkPos(new BlockPos(x, y, z));
            world.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, _snowmanx, 1, target.getEntityId());
            target.stopRiding();
            if (((ServerPlayerEntity)target).isSleeping()) {
               ((ServerPlayerEntity)target).wakeUp(true, true);
            }

            if (world == target.world) {
               ((ServerPlayerEntity)target).networkHandler.teleportRequest(x, y, z, yaw, pitch, movementFlags);
            } else {
               ((ServerPlayerEntity)target).teleport(world, x, y, z, yaw, pitch);
            }

            target.setHeadYaw(yaw);
         } else {
            float _snowmanxx = MathHelper.wrapDegrees(yaw);
            float _snowmanxxx = MathHelper.wrapDegrees(pitch);
            _snowmanxxx = MathHelper.clamp(_snowmanxxx, -90.0F, 90.0F);
            if (world == target.world) {
               target.refreshPositionAndAngles(x, y, z, _snowmanxx, _snowmanxxx);
               target.setHeadYaw(_snowmanxx);
            } else {
               target.detach();
               Entity _snowmanxxxx = target;
               target = target.getType().create(world);
               if (target == null) {
                  return;
               }

               target.copyFrom(_snowmanxxxx);
               target.refreshPositionAndAngles(x, y, z, _snowmanxx, _snowmanxxx);
               target.setHeadYaw(_snowmanxx);
               world.onDimensionChanged(target);
               _snowmanxxxx.removed = true;
            }
         }

         if (facingLocation != null) {
            facingLocation.look(source, target);
         }

         if (!(target instanceof LivingEntity) || !((LivingEntity)target).isFallFlying()) {
            target.setVelocity(target.getVelocity().multiply(1.0, 0.0, 1.0));
            target.setOnGround(true);
         }

         if (target instanceof PathAwareEntity) {
            ((PathAwareEntity)target).getNavigation().stop();
         }
      }
   }

   static class LookTarget {
      private final Vec3d targetPos;
      private final Entity target;
      private final EntityAnchorArgumentType.EntityAnchor targetAnchor;

      public LookTarget(Entity target, EntityAnchorArgumentType.EntityAnchor targetAnchor) {
         this.target = target;
         this.targetAnchor = targetAnchor;
         this.targetPos = targetAnchor.positionAt(target);
      }

      public LookTarget(Vec3d targetPos) {
         this.target = null;
         this.targetPos = targetPos;
         this.targetAnchor = null;
      }

      public void look(ServerCommandSource source, Entity entity) {
         if (this.target != null) {
            if (entity instanceof ServerPlayerEntity) {
               ((ServerPlayerEntity)entity).method_14222(source.getEntityAnchor(), this.target, this.targetAnchor);
            } else {
               entity.lookAt(source.getEntityAnchor(), this.targetPos);
            }
         } else {
            entity.lookAt(source.getEntityAnchor(), this.targetPos);
         }
      }
   }
}
