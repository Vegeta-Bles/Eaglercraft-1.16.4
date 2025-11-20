/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.arguments.BoolArgumentType
 *  com.mojang.brigadier.arguments.FloatArgumentType
 *  com.mojang.brigadier.arguments.IntegerArgumentType
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType
 */
package net.minecraft.server.command;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.Vec2ArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.BlockView;

public class SpreadPlayersCommand {
    private static final Dynamic4CommandExceptionType FAILED_TEAMS_EXCEPTION = new Dynamic4CommandExceptionType((object, object2, object3, object4) -> new TranslatableText("commands.spreadplayers.failed.teams", object, object2, object3, object4));
    private static final Dynamic4CommandExceptionType FAILED_ENTITIES_EXCEPTION = new Dynamic4CommandExceptionType((object, object2, object3, object4) -> new TranslatableText("commands.spreadplayers.failed.entities", object, object2, object3, object4));

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("spreadplayers").requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))).then(CommandManager.argument("center", Vec2ArgumentType.vec2()).then(CommandManager.argument("spreadDistance", FloatArgumentType.floatArg((float)0.0f)).then(((RequiredArgumentBuilder)CommandManager.argument("maxRange", FloatArgumentType.floatArg((float)1.0f)).then(CommandManager.argument("respectTeams", BoolArgumentType.bool()).then(CommandManager.argument("targets", EntityArgumentType.entities()).executes(commandContext -> SpreadPlayersCommand.execute((ServerCommandSource)commandContext.getSource(), Vec2ArgumentType.getVec2((CommandContext<ServerCommandSource>)commandContext, "center"), FloatArgumentType.getFloat((CommandContext)commandContext, (String)"spreadDistance"), FloatArgumentType.getFloat((CommandContext)commandContext, (String)"maxRange"), 256, BoolArgumentType.getBool((CommandContext)commandContext, (String)"respectTeams"), EntityArgumentType.getEntities((CommandContext<ServerCommandSource>)commandContext, "targets")))))).then(CommandManager.literal("under").then(CommandManager.argument("maxHeight", IntegerArgumentType.integer((int)0)).then(CommandManager.argument("respectTeams", BoolArgumentType.bool()).then(CommandManager.argument("targets", EntityArgumentType.entities()).executes(commandContext -> SpreadPlayersCommand.execute((ServerCommandSource)commandContext.getSource(), Vec2ArgumentType.getVec2((CommandContext<ServerCommandSource>)commandContext, "center"), FloatArgumentType.getFloat((CommandContext)commandContext, (String)"spreadDistance"), FloatArgumentType.getFloat((CommandContext)commandContext, (String)"maxRange"), IntegerArgumentType.getInteger((CommandContext)commandContext, (String)"maxHeight"), BoolArgumentType.getBool((CommandContext)commandContext, (String)"respectTeams"), EntityArgumentType.getEntities((CommandContext<ServerCommandSource>)commandContext, "targets")))))))))));
    }

    private static int execute(ServerCommandSource source, Vec2f center, float spreadDistance, float maxRange, int n, boolean bl, Collection<? extends Entity> collection) throws CommandSyntaxException {
        Random random = new Random();
        double _snowman2 = center.x - maxRange;
        double _snowman3 = center.y - maxRange;
        double _snowman4 = center.x + maxRange;
        double _snowman5 = center.y + maxRange;
        Pile[] _snowman6 = SpreadPlayersCommand.makePiles(random, bl ? SpreadPlayersCommand.getPileCountRespectingTeams(collection) : collection.size(), _snowman2, _snowman3, _snowman4, _snowman5);
        SpreadPlayersCommand.spread(center, spreadDistance, source.getWorld(), random, _snowman2, _snowman3, _snowman4, _snowman5, n, _snowman6, bl);
        double _snowman7 = SpreadPlayersCommand.getMinDistance(collection, source.getWorld(), _snowman6, n, bl);
        source.sendFeedback(new TranslatableText("commands.spreadplayers.success." + (bl ? "teams" : "entities"), _snowman6.length, Float.valueOf(center.x), Float.valueOf(center.y), String.format(Locale.ROOT, "%.2f", _snowman7)), true);
        return _snowman6.length;
    }

    private static int getPileCountRespectingTeams(Collection<? extends Entity> entities) {
        HashSet hashSet = Sets.newHashSet();
        for (Entity entity : entities) {
            if (entity instanceof PlayerEntity) {
                hashSet.add(entity.getScoreboardTeam());
                continue;
            }
            hashSet.add(null);
        }
        return hashSet.size();
    }

    private static void spread(Vec2f center, double spreadDistance, ServerWorld world, Random random, double minX, double minZ, double maxX, double maxZ, int n, Pile[] pileArray, boolean bl) throws CommandSyntaxException {
        int n2;
        _snowman5 = true;
        double _snowman2 = 3.4028234663852886E38;
        for (n2 = 0; n2 < 10000 && _snowman5; ++n2) {
            boolean _snowman5 = false;
            _snowman2 = 3.4028234663852886E38;
            for (int i = 0; i < pileArray.length; ++i) {
                Pile pile = pileArray[i];
                int _snowman3 = 0;
                pile = new Pile();
                for (int j = 0; j < pileArray.length; ++j) {
                    if (i == j) continue;
                    Pile pile2 = pileArray[j];
                    double _snowman4 = pile.getDistance(pile2);
                    _snowman2 = Math.min(_snowman4, _snowman2);
                    if (!(_snowman4 < spreadDistance)) continue;
                    ++_snowman3;
                    pile.x = pile.x + (pile2.x - pile.x);
                    pile.z = pile.z + (pile2.z - pile.z);
                }
                if (_snowman3 > 0) {
                    pile.x = pile.x / (double)_snowman3;
                    pile.z = pile.z / (double)_snowman3;
                    double d = pile.absolute();
                    if (d > 0.0) {
                        pile.normalize();
                        pile.subtract(pile);
                    } else {
                        pile.setPileLocation(random, minX, minZ, maxX, maxZ);
                    }
                    _snowman5 = true;
                }
                if (!pile.clamp(minX, minZ, maxX, maxZ)) continue;
                _snowman5 = true;
            }
            if (_snowman5) continue;
            for (Pile pile : pileArray) {
                if (pile.isSafe(world, n)) continue;
                pile.setPileLocation(random, minX, minZ, maxX, maxZ);
                _snowman5 = true;
            }
        }
        if (_snowman2 == 3.4028234663852886E38) {
            _snowman2 = 0.0;
        }
        if (n2 >= 10000) {
            if (bl) {
                throw FAILED_TEAMS_EXCEPTION.create((Object)pileArray.length, (Object)Float.valueOf(center.x), (Object)Float.valueOf(center.y), (Object)String.format(Locale.ROOT, "%.2f", _snowman2));
            }
            throw FAILED_ENTITIES_EXCEPTION.create((Object)pileArray.length, (Object)Float.valueOf(center.x), (Object)Float.valueOf(center.y), (Object)String.format(Locale.ROOT, "%.2f", _snowman2));
        }
    }

    private static double getMinDistance(Collection<? extends Entity> entities, ServerWorld world, Pile[] piles, int n, boolean bl) {
        double d = 0.0;
        int _snowman2 = 0;
        HashMap _snowman3 = Maps.newHashMap();
        for (Entity entity : entities) {
            Pile pile;
            if (bl) {
                AbstractTeam abstractTeam;
                AbstractTeam abstractTeam2 = abstractTeam = entity instanceof PlayerEntity ? entity.getScoreboardTeam() : null;
                if (!_snowman3.containsKey(abstractTeam)) {
                    _snowman3.put(abstractTeam, piles[_snowman2++]);
                }
                Pile _snowman5 = (Pile)_snowman3.get(abstractTeam);
            } else {
                pile = piles[_snowman2++];
            }
            entity.teleport((double)MathHelper.floor(pile.x) + 0.5, pile.getY(world, n), (double)MathHelper.floor(pile.z) + 0.5);
            double _snowman4 = Double.MAX_VALUE;
            for (Pile pile2 : piles) {
                if (pile == pile2) continue;
                double d2 = pile.getDistance(pile2);
                _snowman4 = Math.min(d2, _snowman4);
            }
            d += _snowman4;
        }
        if (entities.size() < 2) {
            return 0.0;
        }
        return d /= (double)entities.size();
    }

    private static Pile[] makePiles(Random random, int count, double minX, double minZ, double maxX, double maxZ) {
        Pile[] pileArray = new Pile[count];
        for (int i = 0; i < pileArray.length; ++i) {
            Pile pile = new Pile();
            pile.setPileLocation(random, minX, minZ, maxX, maxZ);
            pileArray[i] = pile;
        }
        return pileArray;
    }

    static class Pile {
        private double x;
        private double z;

        Pile() {
        }

        double getDistance(Pile other) {
            double d = this.x - other.x;
            _snowman = this.z - other.z;
            return Math.sqrt(d * d + _snowman * _snowman);
        }

        void normalize() {
            double d = this.absolute();
            this.x /= d;
            this.z /= d;
        }

        float absolute() {
            return MathHelper.sqrt(this.x * this.x + this.z * this.z);
        }

        public void subtract(Pile other) {
            this.x -= other.x;
            this.z -= other.z;
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

        public int getY(BlockView blockView, int n2) {
            int n2;
            BlockPos.Mutable mutable = new BlockPos.Mutable(this.x, (double)(n2 + 1), this.z);
            boolean _snowman2 = blockView.getBlockState(mutable).isAir();
            mutable.move(Direction.DOWN);
            boolean _snowman3 = blockView.getBlockState(mutable).isAir();
            while (mutable.getY() > 0) {
                mutable.move(Direction.DOWN);
                boolean bl = blockView.getBlockState(mutable).isAir();
                if (!bl && _snowman3 && _snowman2) {
                    return mutable.getY() + 1;
                }
                _snowman2 = _snowman3;
                _snowman3 = bl;
            }
            return n2 + 1;
        }

        public boolean isSafe(BlockView world, int n) {
            BlockPos blockPos = new BlockPos(this.x, (double)(this.getY(world, n) - 1), this.z);
            BlockState _snowman2 = world.getBlockState(blockPos);
            Material _snowman3 = _snowman2.getMaterial();
            return blockPos.getY() < n && !_snowman3.isLiquid() && _snowman3 != Material.FIRE;
        }

        public void setPileLocation(Random random, double minX, double minZ, double maxX, double maxZ) {
            this.x = MathHelper.nextDouble(random, minX, maxX);
            this.z = MathHelper.nextDouble(random, minZ, maxZ);
        }
    }
}

