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
      (object, object2, object3, object4) -> new TranslatableText("commands.spreadplayers.failed.teams", object, object2, object3, object4)
   );
   private static final Dynamic4CommandExceptionType FAILED_ENTITIES_EXCEPTION = new Dynamic4CommandExceptionType(
      (object, object2, object3, object4) -> new TranslatableText("commands.spreadplayers.failed.entities", object, object2, object3, object4)
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("spreadplayers").requires(arg -> arg.hasPermissionLevel(2)))
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
                                                commandContext -> execute(
                                                      (ServerCommandSource)commandContext.getSource(),
                                                      Vec2ArgumentType.getVec2(commandContext, "center"),
                                                      FloatArgumentType.getFloat(commandContext, "spreadDistance"),
                                                      FloatArgumentType.getFloat(commandContext, "maxRange"),
                                                      256,
                                                      BoolArgumentType.getBool(commandContext, "respectTeams"),
                                                      EntityArgumentType.getEntities(commandContext, "targets")
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
                                                         commandContext -> execute(
                                                               (ServerCommandSource)commandContext.getSource(),
                                                               Vec2ArgumentType.getVec2(commandContext, "center"),
                                                               FloatArgumentType.getFloat(commandContext, "spreadDistance"),
                                                               FloatArgumentType.getFloat(commandContext, "maxRange"),
                                                               IntegerArgumentType.getInteger(commandContext, "maxHeight"),
                                                               BoolArgumentType.getBool(commandContext, "respectTeams"),
                                                               EntityArgumentType.getEntities(commandContext, "targets")
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
      ServerCommandSource source, Vec2f center, float spreadDistance, float maxRange, int i, boolean bl, Collection<? extends Entity> collection
   ) throws CommandSyntaxException {
      Random random = new Random();
      double d = (double)(center.x - maxRange);
      double e = (double)(center.y - maxRange);
      double h = (double)(center.x + maxRange);
      double j = (double)(center.y + maxRange);
      SpreadPlayersCommand.Pile[] lvs = makePiles(random, bl ? getPileCountRespectingTeams(collection) : collection.size(), d, e, h, j);
      spread(center, (double)spreadDistance, source.getWorld(), random, d, e, h, j, i, lvs, bl);
      double k = getMinDistance(collection, source.getWorld(), lvs, i, bl);
      source.sendFeedback(
         new TranslatableText(
            "commands.spreadplayers.success." + (bl ? "teams" : "entities"), lvs.length, center.x, center.y, String.format(Locale.ROOT, "%.2f", k)
         ),
         true
      );
      return lvs.length;
   }

   private static int getPileCountRespectingTeams(Collection<? extends Entity> entities) {
      Set<AbstractTeam> set = Sets.newHashSet();

      for (Entity lv : entities) {
         if (lv instanceof PlayerEntity) {
            set.add(lv.getScoreboardTeam());
         } else {
            set.add(null);
         }
      }

      return set.size();
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
      int i,
      SpreadPlayersCommand.Pile[] args,
      boolean bl
   ) throws CommandSyntaxException {
      boolean bl2 = true;
      double j = Float.MAX_VALUE;

      int k;
      for (k = 0; k < 10000 && bl2; k++) {
         bl2 = false;
         j = Float.MAX_VALUE;

         for (int l = 0; l < args.length; l++) {
            SpreadPlayersCommand.Pile lv = args[l];
            int m = 0;
            SpreadPlayersCommand.Pile lv2 = new SpreadPlayersCommand.Pile();

            for (int n = 0; n < args.length; n++) {
               if (l != n) {
                  SpreadPlayersCommand.Pile lv3 = args[n];
                  double o = lv.getDistance(lv3);
                  j = Math.min(o, j);
                  if (o < spreadDistance) {
                     m++;
                     lv2.x = lv2.x + (lv3.x - lv.x);
                     lv2.z = lv2.z + (lv3.z - lv.z);
                  }
               }
            }

            if (m > 0) {
               lv2.x = lv2.x / (double)m;
               lv2.z = lv2.z / (double)m;
               double p = (double)lv2.absolute();
               if (p > 0.0) {
                  lv2.normalize();
                  lv.subtract(lv2);
               } else {
                  lv.setPileLocation(random, minX, minZ, maxX, maxZ);
               }

               bl2 = true;
            }

            if (lv.clamp(minX, minZ, maxX, maxZ)) {
               bl2 = true;
            }
         }

         if (!bl2) {
            for (SpreadPlayersCommand.Pile lv4 : args) {
               if (!lv4.isSafe(world, i)) {
                  lv4.setPileLocation(random, minX, minZ, maxX, maxZ);
                  bl2 = true;
               }
            }
         }
      }

      if (j == Float.MAX_VALUE) {
         j = 0.0;
      }

      if (k >= 10000) {
         if (bl) {
            throw FAILED_TEAMS_EXCEPTION.create(args.length, center.x, center.y, String.format(Locale.ROOT, "%.2f", j));
         } else {
            throw FAILED_ENTITIES_EXCEPTION.create(args.length, center.x, center.y, String.format(Locale.ROOT, "%.2f", j));
         }
      }
   }

   private static double getMinDistance(Collection<? extends Entity> entities, ServerWorld world, SpreadPlayersCommand.Pile[] piles, int i, boolean bl) {
      double d = 0.0;
      int j = 0;
      Map<AbstractTeam, SpreadPlayersCommand.Pile> map = Maps.newHashMap();

      for (Entity lv : entities) {
         SpreadPlayersCommand.Pile lv3;
         if (bl) {
            AbstractTeam lv2 = lv instanceof PlayerEntity ? lv.getScoreboardTeam() : null;
            if (!map.containsKey(lv2)) {
               map.put(lv2, piles[j++]);
            }

            lv3 = map.get(lv2);
         } else {
            lv3 = piles[j++];
         }

         lv.teleport((double)MathHelper.floor(lv3.x) + 0.5, (double)lv3.getY(world, i), (double)MathHelper.floor(lv3.z) + 0.5);
         double e = Double.MAX_VALUE;

         for (SpreadPlayersCommand.Pile lv5 : piles) {
            if (lv3 != lv5) {
               double f = lv3.getDistance(lv5);
               e = Math.min(f, e);
            }
         }

         d += e;
      }

      return entities.size() < 2 ? 0.0 : d / (double)entities.size();
   }

   private static SpreadPlayersCommand.Pile[] makePiles(Random random, int count, double minX, double minZ, double maxX, double maxZ) {
      SpreadPlayersCommand.Pile[] lvs = new SpreadPlayersCommand.Pile[count];

      for (int j = 0; j < lvs.length; j++) {
         SpreadPlayersCommand.Pile lv = new SpreadPlayersCommand.Pile();
         lv.setPileLocation(random, minX, minZ, maxX, maxZ);
         lvs[j] = lv;
      }

      return lvs;
   }

   static class Pile {
      private double x;
      private double z;

      Pile() {
      }

      double getDistance(SpreadPlayersCommand.Pile other) {
         double d = this.x - other.x;
         double e = this.z - other.z;
         return Math.sqrt(d * d + e * e);
      }

      void normalize() {
         double d = (double)this.absolute();
         this.x /= d;
         this.z /= d;
      }

      float absolute() {
         return MathHelper.sqrt(this.x * this.x + this.z * this.z);
      }

      public void subtract(SpreadPlayersCommand.Pile other) {
         this.x = this.x - other.x;
         this.z = this.z - other.z;
      }

      public boolean clamp(double minX, double minZ, double maxX, double maxZ) {
         boolean bl = false;
         if (this.x < minX) {
            this.x = minX;
            bl = true;
         } else if (this.x > maxX) {
            this.x = maxX;
            bl = true;
         }

         if (this.z < minZ) {
            this.z = minZ;
            bl = true;
         } else if (this.z > maxZ) {
            this.z = maxZ;
            bl = true;
         }

         return bl;
      }

      public int getY(BlockView blockView, int i) {
         BlockPos.Mutable lv = new BlockPos.Mutable(this.x, (double)(i + 1), this.z);
         boolean bl = blockView.getBlockState(lv).isAir();
         lv.move(Direction.DOWN);
         boolean bl2 = blockView.getBlockState(lv).isAir();

         while (lv.getY() > 0) {
            lv.move(Direction.DOWN);
            boolean bl3 = blockView.getBlockState(lv).isAir();
            if (!bl3 && bl2 && bl) {
               return lv.getY() + 1;
            }

            bl = bl2;
            bl2 = bl3;
         }

         return i + 1;
      }

      public boolean isSafe(BlockView world, int i) {
         BlockPos lv = new BlockPos(this.x, (double)(this.getY(world, i) - 1), this.z);
         BlockState lv2 = world.getBlockState(lv);
         Material lv3 = lv2.getMaterial();
         return lv.getY() < i && !lv3.isLiquid() && lv3 != Material.FIRE;
      }

      public void setPileLocation(Random random, double minX, double minZ, double maxX, double maxZ) {
         this.x = MathHelper.nextDouble(random, minX, maxX);
         this.z = MathHelper.nextDouble(random, minZ, maxZ);
      }
   }
}
