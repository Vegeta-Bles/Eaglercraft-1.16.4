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
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("summon").requires(_snowman -> _snowman.hasPermissionLevel(2)))
            .then(
               ((RequiredArgumentBuilder)CommandManager.argument("entity", EntitySummonArgumentType.entitySummon())
                     .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                     .executes(
                        _snowman -> execute(
                              (ServerCommandSource)_snowman.getSource(),
                              EntitySummonArgumentType.getEntitySummon(_snowman, "entity"),
                              ((ServerCommandSource)_snowman.getSource()).getPosition(),
                              new CompoundTag(),
                              true
                           )
                     ))
                  .then(
                     ((RequiredArgumentBuilder)CommandManager.argument("pos", Vec3ArgumentType.vec3())
                           .executes(
                              _snowman -> execute(
                                    (ServerCommandSource)_snowman.getSource(),
                                    EntitySummonArgumentType.getEntitySummon(_snowman, "entity"),
                                    Vec3ArgumentType.getVec3(_snowman, "pos"),
                                    new CompoundTag(),
                                    true
                                 )
                           ))
                        .then(
                           CommandManager.argument("nbt", NbtCompoundTagArgumentType.nbtCompound())
                              .executes(
                                 _snowman -> execute(
                                       (ServerCommandSource)_snowman.getSource(),
                                       EntitySummonArgumentType.getEntitySummon(_snowman, "entity"),
                                       Vec3ArgumentType.getVec3(_snowman, "pos"),
                                       NbtCompoundTagArgumentType.getCompoundTag(_snowman, "nbt"),
                                       false
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int execute(ServerCommandSource source, Identifier entity, Vec3d pos, CompoundTag nbt, boolean initialize) throws CommandSyntaxException {
      BlockPos _snowman = new BlockPos(pos);
      if (!World.isValid(_snowman)) {
         throw INVALID_POSITION_EXCEPTION.create();
      } else {
         CompoundTag _snowmanx = nbt.copy();
         _snowmanx.putString("id", entity.toString());
         ServerWorld _snowmanxx = source.getWorld();
         Entity _snowmanxxx = EntityType.loadEntityWithPassengers(_snowmanx, _snowmanxx, _snowmanxxxx -> {
            _snowmanxxxx.refreshPositionAndAngles(pos.x, pos.y, pos.z, _snowmanxxxx.yaw, _snowmanxxxx.pitch);
            return _snowmanxxxx;
         });
         if (_snowmanxxx == null) {
            throw FAILED_EXCEPTION.create();
         } else {
            if (initialize && _snowmanxxx instanceof MobEntity) {
               ((MobEntity)_snowmanxxx).initialize(source.getWorld(), source.getWorld().getLocalDifficulty(_snowmanxxx.getBlockPos()), SpawnReason.COMMAND, null, null);
            }

            if (!_snowmanxx.shouldCreateNewEntityWithPassenger(_snowmanxxx)) {
               throw field_26629.create();
            } else {
               source.sendFeedback(new TranslatableText("commands.summon.success", _snowmanxxx.getDisplayName()), true);
               return 1;
            }
         }
      }
   }
}
