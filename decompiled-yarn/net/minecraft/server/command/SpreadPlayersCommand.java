package net.minecraft.server.command;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.Vec2ArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.BlockView;

public class SpreadPlayersCommand {
   private static final Dynamic4CommandExceptionType FAILED_TEAMS_EXCEPTION = new Dynamic4CommandExceptionType(
      (_snowman, _snowmanx, _snowmanxx, _snowmanxxx) -> new TranslatableText("commands.spreadplayers.failed.teams", _snowman, _snowmanx, _snowmanxx, _snowmanxxx)
   );
   private static final Dynamic4CommandExceptionType FAILED_ENTITIES_EXCEPTION = new Dynamic4CommandExceptionType(
      (_snowman, _snowmanx, _snowmanxx, _snowmanxxx) -> new TranslatableText("commands.spreadplayers.failed.entities", _snowman, _snowmanx, _snowmanxx, _snowmanxxx)
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("spreadplayers").requires(_snowman -> _snowman.hasPermissionLevel(2)))
            .then(
               CommandManager.argument("center", Vec2ArgumentType.vec2())
                  .then(
                     CommandManager.argument("spreadDistance", FloatArgumentType.floatArg(0.0F))
                        .then(
                           ((RequiredArgumentBuilder)CommandManager.argument("maxRange", FloatArgumentType.floatArg(1.0F))
                                 .then(
                                    CommandManager.argument("respectTeams", BoolArgumentType.bool())
                                       .then(
                                          CommandManager.argument("targets", EntityArgumentType.entities())
                                             .executes(
                                                _snowman -> execute(
                                                      (ServerCommandSource)_snowman.getSource(),
                                                      Vec2ArgumentType.getVec2(_snowman, "center"),
                                                      FloatArgumentType.getFloat(_snowman, "spreadDistance"),
                                                      FloatArgumentType.getFloat(_snowman, "maxRange"),
                                                      256,
                                                      BoolArgumentType.getBool(_snowman, "respectTeams"),
                                                      EntityArgumentType.getEntities(_snowman, "targets")
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 CommandManager.literal("under")
                                    .then(
                                       CommandManager.argument("maxHeight", IntegerArgumentType.integer(0))
                                          .then(
                                             CommandManager.argument("respectTeams", BoolArgumentType.bool())
                                                .then(
                                                   CommandManager.argument("targets", EntityArgumentType.entities())
                                                      .executes(
                                                         _snowman -> execute(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               Vec2ArgumentType.getVec2(_snowman, "center"),
                                                               FloatArgumentType.getFloat(_snowman, "spreadDistance"),
                                                               FloatArgumentType.getFloat(_snowman, "maxRange"),
                                                               IntegerArgumentType.getInteger(_snowman, "maxHeight"),
                                                               BoolArgumentType.getBool(_snowman, "respectTeams"),
                                                               EntityArgumentType.getEntities(_snowman, "targets")
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

   private static int execute(ServerCommandSource source, Vec2f center, float spreadDistance, float maxRange, int _snowman, boolean _snowman, Collection<? extends Entity> _snowman) throws CommandSyntaxException {
      Random _snowmanxxx = new Random();
      double _snowmanxxxx = (double)(center.x - maxRange);
      double _snowmanxxxxx = (double)(center.y - maxRange);
      double _snowmanxxxxxx = (double)(center.x + maxRange);
      double _snowmanxxxxxxx = (double)(center.y + maxRange);
      SpreadPlayersCommand.Pile[] _snowmanxxxxxxxx = makePiles(_snowmanxxx, _snowman ? getPileCountRespectingTeams(_snowman) : _snowman.size(), _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
      spread(center, (double)spreadDistance, source.getWorld(), _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowman, _snowmanxxxxxxxx, _snowman);
      double _snowmanxxxxxxxxx = getMinDistance(_snowman, source.getWorld(), _snowmanxxxxxxxx, _snowman, _snowman);
      source.sendFeedback(
         new TranslatableText(
            "commands.spreadplayers.success." + (_snowman ? "teams" : "entities"),
            _snowmanxxxxxxxx.length,
            center.x,
            center.y,
            String.format(Locale.ROOT, "%.2f", _snowmanxxxxxxxxx)
         ),
         true
      );
      return _snowmanxxxxxxxx.length;
   }

   private static int getPileCountRespectingTeams(Collection<? extends Entity> entities) {
      Set<AbstractTeam> _snowman = Sets.newHashSet();

      for (Entity _snowmanx : entities) {
         if (_snowmanx instanceof PlayerEntity) {
            _snowman.add(_snowmanx.getScoreboardTeam());
         } else {
            _snowman.add(null);
         }
      }

      return _snowman.size();
   }

   private static void spread(
      Vec2f center,
      double spreadDistance,
      ServerWorld world,
      Random random,
      double minX,
      double minZ,
      double maxX,
      double maxZ,
      int _snowman,
      SpreadPlayersCommand.Pile[] _snowman,
      boolean _snowman
   ) throws CommandSyntaxException {
      boolean _snowmanxxx = true;
      double _snowmanxxxx = Float.MAX_VALUE;

      int _snowmanxxxxx;
      for (_snowmanxxxxx = 0; _snowmanxxxxx < 10000 && _snowmanxxx; _snowmanxxxxx++) {
         _snowmanxxx = false;
         _snowmanxxxx = Float.MAX_VALUE;

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowman.length; _snowmanxxxxxx++) {
            SpreadPlayersCommand.Pile _snowmanxxxxxxx = _snowman[_snowmanxxxxxx];
            int _snowmanxxxxxxxx = 0;
            SpreadPlayersCommand.Pile _snowmanxxxxxxxxx = new SpreadPlayersCommand.Pile();

            for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < _snowman.length; _snowmanxxxxxxxxxx++) {
               if (_snowmanxxxxxx != _snowmanxxxxxxxxxx) {
                  SpreadPlayersCommand.Pile _snowmanxxxxxxxxxxx = _snowman[_snowmanxxxxxxxxxx];
                  double _snowmanxxxxxxxxxxxx = _snowmanxxxxxxx.getDistance(_snowmanxxxxxxxxxxx);
                  _snowmanxxxx = Math.min(_snowmanxxxxxxxxxxxx, _snowmanxxxx);
                  if (_snowmanxxxxxxxxxxxx < spreadDistance) {
                     _snowmanxxxxxxxx++;
                     _snowmanxxxxxxxxx.x = _snowmanxxxxxxxxx.x + (_snowmanxxxxxxxxxxx.x - _snowmanxxxxxxx.x);
                     _snowmanxxxxxxxxx.z = _snowmanxxxxxxxxx.z + (_snowmanxxxxxxxxxxx.z - _snowmanxxxxxxx.z);
                  }
               }
            }

            if (_snowmanxxxxxxxx > 0) {
               _snowmanxxxxxxxxx.x = _snowmanxxxxxxxxx.x / (double)_snowmanxxxxxxxx;
               _snowmanxxxxxxxxx.z = _snowmanxxxxxxxxx.z / (double)_snowmanxxxxxxxx;
               double _snowmanxxxxxxxxxxx = (double)_snowmanxxxxxxxxx.absolute();
               if (_snowmanxxxxxxxxxxx > 0.0) {
                  _snowmanxxxxxxxxx.normalize();
                  _snowmanxxxxxxx.subtract(_snowmanxxxxxxxxx);
               } else {
                  _snowmanxxxxxxx.setPileLocation(random, minX, minZ, maxX, maxZ);
               }

               _snowmanxxx = true;
            }

            if (_snowmanxxxxxxx.clamp(minX, minZ, maxX, maxZ)) {
               _snowmanxxx = true;
            }
         }

         if (!_snowmanxxx) {
            for (SpreadPlayersCommand.Pile _snowmanxxxxxx : _snowman) {
               if (!_snowmanxxxxxx.isSafe(world, _snowman)) {
                  _snowmanxxxxxx.setPileLocation(random, minX, minZ, maxX, maxZ);
                  _snowmanxxx = true;
               }
            }
         }
      }

      if (_snowmanxxxx == Float.MAX_VALUE) {
         _snowmanxxxx = 0.0;
      }

      if (_snowmanxxxxx >= 10000) {
         if (_snowman) {
            throw FAILED_TEAMS_EXCEPTION.create(_snowman.length, center.x, center.y, String.format(Locale.ROOT, "%.2f", _snowmanxxxx));
         } else {
            throw FAILED_ENTITIES_EXCEPTION.create(_snowman.length, center.x, center.y, String.format(Locale.ROOT, "%.2f", _snowmanxxxx));
         }
      }
   }

   private static double getMinDistance(Collection<? extends Entity> entities, ServerWorld world, SpreadPlayersCommand.Pile[] piles, int _snowman, boolean _snowman) {
      double _snowmanxx = 0.0;
      int _snowmanxxx = 0;
      Map<AbstractTeam, SpreadPlayersCommand.Pile> _snowmanxxxx = Maps.newHashMap();

      for (Entity _snowmanxxxxx : entities) {
         SpreadPlayersCommand.Pile _snowmanxxxxxx;
         if (_snowman) {
            AbstractTeam _snowmanxxxxxxx = _snowmanxxxxx instanceof PlayerEntity ? _snowmanxxxxx.getScoreboardTeam() : null;
            if (!_snowmanxxxx.containsKey(_snowmanxxxxxxx)) {
               _snowmanxxxx.put(_snowmanxxxxxxx, piles[_snowmanxxx++]);
            }

            _snowmanxxxxxx = _snowmanxxxx.get(_snowmanxxxxxxx);
         } else {
            _snowmanxxxxxx = piles[_snowmanxxx++];
         }

         _snowmanxxxxx.teleport((double)MathHelper.floor(_snowmanxxxxxx.x) + 0.5, (double)_snowmanxxxxxx.getY(world, _snowman), (double)MathHelper.floor(_snowmanxxxxxx.z) + 0.5);
         double _snowmanxxxxxxx = Double.MAX_VALUE;

         for (SpreadPlayersCommand.Pile _snowmanxxxxxxxx : piles) {
            if (_snowmanxxxxxx != _snowmanxxxxxxxx) {
               double _snowmanxxxxxxxxx = _snowmanxxxxxx.getDistance(_snowmanxxxxxxxx);
               _snowmanxxxxxxx = Math.min(_snowmanxxxxxxxxx, _snowmanxxxxxxx);
            }
         }

         _snowmanxx += _snowmanxxxxxxx;
      }

      return entities.size() < 2 ? 0.0 : _snowmanxx / (double)entities.size();
   }

   private static SpreadPlayersCommand.Pile[] makePiles(Random random, int count, double minX, double minZ, double maxX, double maxZ) {
      SpreadPlayersCommand.Pile[] _snowman = new SpreadPlayersCommand.Pile[count];

      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         SpreadPlayersCommand.Pile _snowmanxx = new SpreadPlayersCommand.Pile();
         _snowmanxx.setPileLocation(random, minX, minZ, maxX, maxZ);
         _snowman[_snowmanx] = _snowmanxx;
      }

      return _snowman;
   }

   static class Pile {
      private double x;
      private double z;

      Pile() {
      }

      double getDistance(SpreadPlayersCommand.Pile other) {
         double _snowman = this.x - other.x;
         double _snowmanx = this.z - other.z;
         return Math.sqrt(_snowman * _snowman + _snowmanx * _snowmanx);
      }

      void normalize() {
         double _snowman = (double)this.absolute();
         this.x /= _snowman;
         this.z /= _snowman;
      }

      float absolute() {
         return MathHelper.sqrt(this.x * this.x + this.z * this.z);
      }

      public void subtract(SpreadPlayersCommand.Pile other) {
         this.x = this.x - other.x;
         this.z = this.z - other.z;
      }

      public boolean clamp(double minX, double minZ, double maxX, double maxZ) {
         boolean _snowman = false;
         if (this.x < minX) {
            this.x = minX;
            _snowman = true;
         } else if (this.x > maxX) {
            this.x = maxX;
            _snowman = true;
         }

         if (this.z < minZ) {
            this.z = minZ;
            _snowman = true;
         } else if (this.z > maxZ) {
            this.z = maxZ;
            _snowman = true;
         }

         return _snowman;
      }

      public int getY(BlockView blockView, int _snowman) {
         BlockPos.Mutable _snowmanx = new BlockPos.Mutable(this.x, (double)(_snowman + 1), this.z);
         boolean _snowmanxx = blockView.getBlockState(_snowmanx).isAir();
         _snowmanx.move(Direction.DOWN);
         boolean _snowmanxxx = blockView.getBlockState(_snowmanx).isAir();

         while (_snowmanx.getY() > 0) {
            _snowmanx.move(Direction.DOWN);
            boolean _snowmanxxxx = blockView.getBlockState(_snowmanx).isAir();
            if (!_snowmanxxxx && _snowmanxxx && _snowmanxx) {
               return _snowmanx.getY() + 1;
            }

            _snowmanxx = _snowmanxxx;
            _snowmanxxx = _snowmanxxxx;
         }

         return _snowman + 1;
      }

      public boolean isSafe(BlockView world, int _snowman) {
         BlockPos _snowmanx = new BlockPos(this.x, (double)(this.getY(world, _snowman) - 1), this.z);
         BlockState _snowmanxx = world.getBlockState(_snowmanx);
         Material _snowmanxxx = _snowmanxx.getMaterial();
         return _snowmanx.getY() < _snowman && !_snowmanxxx.isLiquid() && _snowmanxxx != Material.FIRE;
      }

      public void setPileLocation(Random random, double minX, double minZ, double maxX, double maxZ) {
         this.x = MathHelper.nextDouble(random, minX, maxX);
         this.z = MathHelper.nextDouble(random, minZ, maxZ);
      }
   }
}
