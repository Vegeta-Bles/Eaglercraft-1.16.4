/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 */
package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
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
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SummonCommand {
    private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("commands.summon.failed"));
    private static final SimpleCommandExceptionType field_26629 = new SimpleCommandExceptionType((Message)new TranslatableText("commands.summon.failed.uuid"));
    private static final SimpleCommandExceptionType INVALID_POSITION_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("commands.summon.invalidPosition"));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("summon").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))).then(((RequiredArgumentBuilder)CommandManager.argument("entity", EntitySummonArgumentType.entitySummon()).suggests(SuggestionProviders.SUMMONABLE_ENTITIES).executes(commandContext -> SummonCommand.execute((ServerCommandSource)commandContext.getSource(), EntitySummonArgumentType.getEntitySummon((CommandContext<ServerCommandSource>)commandContext, "entity"), ((ServerCommandSource)commandContext.getSource()).getPosition(), new CompoundTag(), true))).then(((RequiredArgumentBuilder)CommandManager.argument("pos", Vec3ArgumentType.vec3()).executes(commandContext -> SummonCommand.execute((ServerCommandSource)commandContext.getSource(), EntitySummonArgumentType.getEntitySummon((CommandContext<ServerCommandSource>)commandContext, "entity"), Vec3ArgumentType.getVec3((CommandContext<ServerCommandSource>)commandContext, "pos"), new CompoundTag(), true))).then(CommandManager.argument("nbt", NbtCompoundTagArgumentType.nbtCompound()).executes(commandContext -> SummonCommand.execute((ServerCommandSource)commandContext.getSource(), EntitySummonArgumentType.getEntitySummon((CommandContext<ServerCommandSource>)commandContext, "entity"), Vec3ArgumentType.getVec3((CommandContext<ServerCommandSource>)commandContext, "pos"), NbtCompoundTagArgumentType.getCompoundTag(commandContext, "nbt"), false))))));
    }

    private static int execute(ServerCommandSource source, Identifier entity2, Vec3d pos, CompoundTag nbt, boolean initialize) throws CommandSyntaxException {
        BlockPos blockPos = new BlockPos(pos);
        if (!World.isValid(blockPos)) {
            throw INVALID_POSITION_EXCEPTION.create();
        }
        CompoundTag _snowman2 = nbt.copy();
        _snowman2.putString("id", entity2.toString());
        ServerWorld _snowman3 = source.getWorld();
        Entity _snowman4 = EntityType.loadEntityWithPassengers(_snowman2, _snowman3, entity -> {
            entity.refreshPositionAndAngles(vec3d.x, vec3d.y, vec3d.z, entity.yaw, entity.pitch);
            return entity;
        });
        if (_snowman4 == null) {
            throw FAILED_EXCEPTION.create();
        }
        if (initialize && _snowman4 instanceof MobEntity) {
            ((MobEntity)_snowman4).initialize(source.getWorld(), source.getWorld().getLocalDifficulty(_snowman4.getBlockPos()), SpawnReason.COMMAND, null, null);
        }
        if (!_snowman3.shouldCreateNewEntityWithPassenger(_snowman4)) {
            throw field_26629.create();
        }
        source.sendFeedback(new TranslatableText("commands.summon.success", _snowman4.getDisplayName()), true);
        return 1;
    }
}

