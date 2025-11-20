/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.predicate.NumberRange;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Texts;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class EntitySelector {
    private final int limit;
    private final boolean includesNonPlayers;
    private final boolean localWorldOnly;
    private final Predicate<Entity> basePredicate;
    private final NumberRange.FloatRange distance;
    private final Function<Vec3d, Vec3d> positionOffset;
    @Nullable
    private final Box box;
    private final BiConsumer<Vec3d, List<? extends Entity>> sorter;
    private final boolean senderOnly;
    @Nullable
    private final String playerName;
    @Nullable
    private final UUID uuid;
    @Nullable
    private final EntityType<?> type;
    private final boolean usesAt;

    public EntitySelector(int count, boolean includesNonPlayers, boolean localWorldOnly, Predicate<Entity> basePredicate, NumberRange.FloatRange distance, Function<Vec3d, Vec3d> positionOffset, @Nullable Box box, BiConsumer<Vec3d, List<? extends Entity>> sorter, boolean senderOnly, @Nullable String playerName, @Nullable UUID uuid, @Nullable EntityType<?> type, boolean usesAt) {
        this.limit = count;
        this.includesNonPlayers = includesNonPlayers;
        this.localWorldOnly = localWorldOnly;
        this.basePredicate = basePredicate;
        this.distance = distance;
        this.positionOffset = positionOffset;
        this.box = box;
        this.sorter = sorter;
        this.senderOnly = senderOnly;
        this.playerName = playerName;
        this.uuid = uuid;
        this.type = type;
        this.usesAt = usesAt;
    }

    public int getLimit() {
        return this.limit;
    }

    public boolean includesNonPlayers() {
        return this.includesNonPlayers;
    }

    public boolean isSenderOnly() {
        return this.senderOnly;
    }

    public boolean isLocalWorldOnly() {
        return this.localWorldOnly;
    }

    private void checkSourcePermission(ServerCommandSource serverCommandSource) throws CommandSyntaxException {
        if (this.usesAt && !serverCommandSource.hasPermissionLevel(2)) {
            throw EntityArgumentType.NOT_ALLOWED_EXCEPTION.create();
        }
    }

    public Entity getEntity(ServerCommandSource serverCommandSource) throws CommandSyntaxException {
        this.checkSourcePermission(serverCommandSource);
        List<? extends Entity> list = this.getEntities(serverCommandSource);
        if (list.isEmpty()) {
            throw EntityArgumentType.ENTITY_NOT_FOUND_EXCEPTION.create();
        }
        if (list.size() > 1) {
            throw EntityArgumentType.TOO_MANY_ENTITIES_EXCEPTION.create();
        }
        return list.get(0);
    }

    public List<? extends Entity> getEntities(ServerCommandSource serverCommandSource2) throws CommandSyntaxException {
        ServerCommandSource serverCommandSource2;
        this.checkSourcePermission(serverCommandSource2);
        if (!this.includesNonPlayers) {
            return this.getPlayers(serverCommandSource2);
        }
        if (this.playerName != null) {
            ServerPlayerEntity serverPlayerEntity = serverCommandSource2.getMinecraftServer().getPlayerManager().getPlayer(this.playerName);
            if (serverPlayerEntity == null) {
                return Collections.emptyList();
            }
            return Lists.newArrayList((Object[])new ServerPlayerEntity[]{serverPlayerEntity});
        }
        if (this.uuid != null) {
            for (ServerWorld _snowman2 : serverCommandSource2.getMinecraftServer().getWorlds()) {
                Entity entity = _snowman2.getEntity(this.uuid);
                if (entity == null) continue;
                return Lists.newArrayList((Object[])new Entity[]{entity});
            }
            return Collections.emptyList();
        }
        Vec3d _snowman3 = this.positionOffset.apply(serverCommandSource2.getPosition());
        Predicate<Entity> _snowman4 = this.getPositionPredicate(_snowman3);
        if (this.senderOnly) {
            if (serverCommandSource2.getEntity() != null && _snowman4.test(serverCommandSource2.getEntity())) {
                return Lists.newArrayList((Object[])new Entity[]{serverCommandSource2.getEntity()});
            }
            return Collections.emptyList();
        }
        ArrayList _snowman5 = Lists.newArrayList();
        if (this.isLocalWorldOnly()) {
            this.appendEntitiesFromWorld(_snowman5, serverCommandSource2.getWorld(), _snowman3, _snowman4);
        } else {
            for (ServerWorld serverWorld : serverCommandSource2.getMinecraftServer().getWorlds()) {
                this.appendEntitiesFromWorld(_snowman5, serverWorld, _snowman3, _snowman4);
            }
        }
        return this.getEntities(_snowman3, _snowman5);
    }

    private void appendEntitiesFromWorld(List<Entity> list, ServerWorld serverWorld, Vec3d vec3d, Predicate<Entity> predicate) {
        if (this.box != null) {
            list.addAll(serverWorld.getEntitiesByType(this.type, this.box.offset(vec3d), predicate));
        } else {
            list.addAll(serverWorld.getEntitiesByType(this.type, predicate));
        }
    }

    public ServerPlayerEntity getPlayer(ServerCommandSource serverCommandSource) throws CommandSyntaxException {
        this.checkSourcePermission(serverCommandSource);
        List<ServerPlayerEntity> list = this.getPlayers(serverCommandSource);
        if (list.size() != 1) {
            throw EntityArgumentType.PLAYER_NOT_FOUND_EXCEPTION.create();
        }
        return list.get(0);
    }

    public List<ServerPlayerEntity> getPlayers(ServerCommandSource serverCommandSource2) throws CommandSyntaxException {
        List<Object> list;
        this.checkSourcePermission(serverCommandSource2);
        if (this.playerName != null) {
            ServerPlayerEntity serverPlayerEntity = serverCommandSource2.getMinecraftServer().getPlayerManager().getPlayer(this.playerName);
            if (serverPlayerEntity == null) {
                return Collections.emptyList();
            }
            return Lists.newArrayList((Object[])new ServerPlayerEntity[]{serverPlayerEntity});
        }
        if (this.uuid != null) {
            ServerCommandSource serverCommandSource2;
            ServerPlayerEntity _snowman2 = serverCommandSource2.getMinecraftServer().getPlayerManager().getPlayer(this.uuid);
            if (_snowman2 == null) {
                return Collections.emptyList();
            }
            return Lists.newArrayList((Object[])new ServerPlayerEntity[]{_snowman2});
        }
        Vec3d vec3d = this.positionOffset.apply(serverCommandSource2.getPosition());
        Predicate<Entity> _snowman3 = this.getPositionPredicate(vec3d);
        if (this.senderOnly) {
            if (serverCommandSource2.getEntity() instanceof ServerPlayerEntity && _snowman3.test(_snowman = (ServerPlayerEntity)serverCommandSource2.getEntity())) {
                return Lists.newArrayList((Object[])new ServerPlayerEntity[]{_snowman});
            }
            return Collections.emptyList();
        }
        if (this.isLocalWorldOnly()) {
            list = serverCommandSource2.getWorld().getPlayers(_snowman3::test);
        } else {
            list = Lists.newArrayList();
            for (ServerPlayerEntity serverPlayerEntity : serverCommandSource2.getMinecraftServer().getPlayerManager().getPlayerList()) {
                if (!_snowman3.test(serverPlayerEntity)) continue;
                list.add(serverPlayerEntity);
            }
        }
        return this.getEntities(vec3d, list);
    }

    private Predicate<Entity> getPositionPredicate(Vec3d vec3d) {
        Predicate<Entity> _snowman2 = this.basePredicate;
        if (this.box != null) {
            Box box = this.box.offset(vec3d);
            _snowman2 = _snowman2.and(entity -> box.intersects(entity.getBoundingBox()));
        }
        if (!this.distance.isDummy()) {
            _snowman2 = _snowman2.and(entity -> this.distance.testSqrt(entity.squaredDistanceTo(vec3d)));
        }
        return _snowman2;
    }

    private <T extends Entity> List<T> getEntities(Vec3d vec3d, List<T> list) {
        if (list.size() > 1) {
            this.sorter.accept(vec3d, list);
        }
        return list.subList(0, Math.min(this.limit, list.size()));
    }

    public static MutableText getNames(List<? extends Entity> list) {
        return Texts.join(list, Entity::getDisplayName);
    }
}

