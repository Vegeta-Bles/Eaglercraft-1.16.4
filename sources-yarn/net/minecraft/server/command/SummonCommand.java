package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.argument.EntitySummonArgumentType;
import net.minecraft.command.argument.NbtCompoundTagArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SummonCommand {
   private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.summon.failed"));
   private static final SimpleCommandExceptionType field_26629 = new SimpleCommandExceptionType(new TranslatableText("commands.summon.failed.uuid"));
   private static final SimpleCommandExceptionType INVALID_POSITION_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.summon.invalidPosition")
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("summon").requires(arg -> arg.hasPermissionLevel(2)))
            .then(
               ((RequiredArgumentBuilder)CommandManager.argument("entity", EntitySummonArgumentType.entitySummon())
                     .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                     .executes(
                        commandContext -> execute(
                              (ServerCommandSource)commandContext.getSource(),
                              EntitySummonArgumentType.getEntitySummon(commandContext, "entity"),
                              ((ServerCommandSource)commandContext.getSource()).getPosition(),
                              new CompoundTag(),
                              true
                           )
                     ))
                  .then(
                     ((RequiredArgumentBuilder)CommandManager.argument("pos", Vec3ArgumentType.vec3())
                           .executes(
                              commandContext -> execute(
                                    (ServerCommandSource)commandContext.getSource(),
                                    EntitySummonArgumentType.getEntitySummon(commandContext, "entity"),
                                    Vec3ArgumentType.getVec3(commandContext, "pos"),
                                    new CompoundTag(),
                                    true
                                 )
                           ))
                        .then(
                           CommandManager.argument("nbt", NbtCompoundTagArgumentType.nbtCompound())
                              .executes(
                                 commandContext -> execute(
                                       (ServerCommandSource)commandContext.getSource(),
                                       EntitySummonArgumentType.getEntitySummon(commandContext, "entity"),
                                       Vec3ArgumentType.getVec3(commandContext, "pos"),
                                       NbtCompoundTagArgumentType.getCompoundTag(commandContext, "nbt"),
                                       false
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int execute(ServerCommandSource source, Identifier entity, Vec3d pos, CompoundTag nbt, boolean initialize) throws CommandSyntaxException {
      BlockPos lv = new BlockPos(pos);
      if (!World.isValid(lv)) {
         throw INVALID_POSITION_EXCEPTION.create();
      } else {
         CompoundTag lv2 = nbt.copy();
         lv2.putString("id", entity.toString());
         ServerWorld lv3 = source.getWorld();
         Entity lv4 = EntityType.loadEntityWithPassengers(lv2, lv3, arg2 -> {
            arg2.refreshPositionAndAngles(pos.x, pos.y, pos.z, arg2.yaw, arg2.pitch);
            return arg2;
         });
         if (lv4 == null) {
            throw FAILED_EXCEPTION.create();
         } else {
            if (initialize && lv4 instanceof MobEntity) {
               ((MobEntity)lv4).initialize(source.getWorld(), source.getWorld().getLocalDifficulty(lv4.getBlockPos()), SpawnReason.COMMAND, null, null);
            }

            if (!lv3.shouldCreateNewEntityWithPassenger(lv4)) {
               throw field_26629.create();
            } else {
               source.sendFeedback(new TranslatableText("commands.summon.success", lv4.getDisplayName()), true);
               return 1;
            }
         }
      }
   }
}
