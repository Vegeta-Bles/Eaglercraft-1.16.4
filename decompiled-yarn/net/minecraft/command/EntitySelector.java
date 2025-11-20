package net.minecraft.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
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

   public EntitySelector(
      int count,
      boolean includesNonPlayers,
      boolean localWorldOnly,
      Predicate<Entity> basePredicate,
      NumberRange.FloatRange distance,
      Function<Vec3d, Vec3d> positionOffset,
      @Nullable Box box,
      BiConsumer<Vec3d, List<? extends Entity>> sorter,
      boolean senderOnly,
      @Nullable String playerName,
      @Nullable UUID uuid,
      @Nullable EntityType<?> type,
      boolean usesAt
   ) {
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

   private void checkSourcePermission(ServerCommandSource _snowman) throws CommandSyntaxException {
      if (this.usesAt && !_snowman.hasPermissionLevel(2)) {
         throw EntityArgumentType.NOT_ALLOWED_EXCEPTION.create();
      }
   }

   public Entity getEntity(ServerCommandSource _snowman) throws CommandSyntaxException {
      this.checkSourcePermission(_snowman);
      List<? extends Entity> _snowmanx = this.getEntities(_snowman);
      if (_snowmanx.isEmpty()) {
         throw EntityArgumentType.ENTITY_NOT_FOUND_EXCEPTION.create();
      } else if (_snowmanx.size() > 1) {
         throw EntityArgumentType.TOO_MANY_ENTITIES_EXCEPTION.create();
      } else {
         return _snowmanx.get(0);
      }
   }

   public List<? extends Entity> getEntities(ServerCommandSource _snowman) throws CommandSyntaxException {
      this.checkSourcePermission(_snowman);
      if (!this.includesNonPlayers) {
         return this.getPlayers(_snowman);
      } else if (this.playerName != null) {
         ServerPlayerEntity _snowmanx = _snowman.getMinecraftServer().getPlayerManager().getPlayer(this.playerName);
         return (List<? extends Entity>)(_snowmanx == null ? Collections.emptyList() : Lists.newArrayList(new ServerPlayerEntity[]{_snowmanx}));
      } else if (this.uuid != null) {
         for (ServerWorld _snowmanx : _snowman.getMinecraftServer().getWorlds()) {
            Entity _snowmanxx = _snowmanx.getEntity(this.uuid);
            if (_snowmanxx != null) {
               return Lists.newArrayList(new Entity[]{_snowmanxx});
            }
         }

         return Collections.emptyList();
      } else {
         Vec3d _snowmanxx = this.positionOffset.apply(_snowman.getPosition());
         Predicate<Entity> _snowmanxxx = this.getPositionPredicate(_snowmanxx);
         if (this.senderOnly) {
            return (List<? extends Entity>)(_snowman.getEntity() != null && _snowmanxxx.test(_snowman.getEntity())
               ? Lists.newArrayList(new Entity[]{_snowman.getEntity()})
               : Collections.emptyList());
         } else {
            List<Entity> _snowmanxxxx = Lists.newArrayList();
            if (this.isLocalWorldOnly()) {
               this.appendEntitiesFromWorld(_snowmanxxxx, _snowman.getWorld(), _snowmanxx, _snowmanxxx);
            } else {
               for (ServerWorld _snowmanxxxxx : _snowman.getMinecraftServer().getWorlds()) {
                  this.appendEntitiesFromWorld(_snowmanxxxx, _snowmanxxxxx, _snowmanxx, _snowmanxxx);
               }
            }

            return this.getEntities(_snowmanxx, _snowmanxxxx);
         }
      }
   }

   private void appendEntitiesFromWorld(List<Entity> _snowman, ServerWorld _snowman, Vec3d _snowman, Predicate<Entity> _snowman) {
      if (this.box != null) {
         _snowman.addAll(_snowman.getEntitiesByType((EntityType<? extends Entity>)this.type, this.box.offset(_snowman), _snowman));
      } else {
         _snowman.addAll(_snowman.getEntitiesByType(this.type, _snowman));
      }
   }

   public ServerPlayerEntity getPlayer(ServerCommandSource _snowman) throws CommandSyntaxException {
      this.checkSourcePermission(_snowman);
      List<ServerPlayerEntity> _snowmanx = this.getPlayers(_snowman);
      if (_snowmanx.size() != 1) {
         throw EntityArgumentType.PLAYER_NOT_FOUND_EXCEPTION.create();
      } else {
         return _snowmanx.get(0);
      }
   }

   public List<ServerPlayerEntity> getPlayers(ServerCommandSource _snowman) throws CommandSyntaxException {
      this.checkSourcePermission(_snowman);
      if (this.playerName != null) {
         ServerPlayerEntity _snowmanx = _snowman.getMinecraftServer().getPlayerManager().getPlayer(this.playerName);
         return (List<ServerPlayerEntity>)(_snowmanx == null ? Collections.emptyList() : Lists.newArrayList(new ServerPlayerEntity[]{_snowmanx}));
      } else if (this.uuid != null) {
         ServerPlayerEntity _snowmanx = _snowman.getMinecraftServer().getPlayerManager().getPlayer(this.uuid);
         return (List<ServerPlayerEntity>)(_snowmanx == null ? Collections.emptyList() : Lists.newArrayList(new ServerPlayerEntity[]{_snowmanx}));
      } else {
         Vec3d _snowmanx = this.positionOffset.apply(_snowman.getPosition());
         Predicate<Entity> _snowmanxx = this.getPositionPredicate(_snowmanx);
         if (this.senderOnly) {
            if (_snowman.getEntity() instanceof ServerPlayerEntity) {
               ServerPlayerEntity _snowmanxxx = (ServerPlayerEntity)_snowman.getEntity();
               if (_snowmanxx.test(_snowmanxxx)) {
                  return Lists.newArrayList(new ServerPlayerEntity[]{_snowmanxxx});
               }
            }

            return Collections.emptyList();
         } else {
            List<ServerPlayerEntity> _snowmanxxx;
            if (this.isLocalWorldOnly()) {
               _snowmanxxx = _snowman.getWorld().getPlayers(_snowmanxx::test);
            } else {
               _snowmanxxx = Lists.newArrayList();

               for (ServerPlayerEntity _snowmanxxxx : _snowman.getMinecraftServer().getPlayerManager().getPlayerList()) {
                  if (_snowmanxx.test(_snowmanxxxx)) {
                     _snowmanxxx.add(_snowmanxxxx);
                  }
               }
            }

            return this.getEntities(_snowmanx, _snowmanxxx);
         }
      }
   }

   private Predicate<Entity> getPositionPredicate(Vec3d _snowman) {
      Predicate<Entity> _snowmanx = this.basePredicate;
      if (this.box != null) {
         Box _snowmanxx = this.box.offset(_snowman);
         _snowmanx = _snowmanx.and(_snowmanxxx -> _snowman.intersects(_snowmanxxx.getBoundingBox()));
      }

      if (!this.distance.isDummy()) {
         _snowmanx = _snowmanx.and(_snowmanxx -> this.distance.testSqrt(_snowmanxx.squaredDistanceTo(_snowman)));
      }

      return _snowmanx;
   }

   private <T extends Entity> List<T> getEntities(Vec3d _snowman, List<T> _snowman) {
      if (_snowman.size() > 1) {
         this.sorter.accept(_snowman, _snowman);
      }

      return _snowman.subList(0, Math.min(this.limit, _snowman.size()));
   }

   public static MutableText getNames(List<? extends Entity> _snowman) {
      return Texts.join(_snowman, Entity::getDisplayName);
   }
}
