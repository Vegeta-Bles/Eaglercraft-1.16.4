/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.mojang.brigadier.ImmutableStringReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.exceptions.SimpleCommandExceptionType
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 */
package net.minecraft.command;

import com.google.common.collect.Maps;
import com.mojang.brigadier.ImmutableStringReader;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.advancement.criterion.CriterionProgress;
import net.minecraft.command.CommandSource;
import net.minecraft.command.EntitySelectorReader;
import net.minecraft.command.FloatRangeArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.predicate.NumberRange;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.ServerAdvancementLoader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameMode;

public class EntitySelectorOptions {
    private static final Map<String, SelectorOption> options = Maps.newHashMap();
    public static final DynamicCommandExceptionType UNKNOWN_OPTION_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("argument.entity.options.unknown", object));
    public static final DynamicCommandExceptionType INAPPLICABLE_OPTION_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("argument.entity.options.inapplicable", object));
    public static final SimpleCommandExceptionType NEGATIVE_DISTANCE_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("argument.entity.options.distance.negative"));
    public static final SimpleCommandExceptionType NEGATIVE_LEVEL_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("argument.entity.options.level.negative"));
    public static final SimpleCommandExceptionType TOO_SMALL_LEVEL_EXCEPTION = new SimpleCommandExceptionType((Message)new TranslatableText("argument.entity.options.limit.toosmall"));
    public static final DynamicCommandExceptionType IRREVERSIBLE_SORT_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("argument.entity.options.sort.irreversible", object));
    public static final DynamicCommandExceptionType INVALID_MODE_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("argument.entity.options.mode.invalid", object));
    public static final DynamicCommandExceptionType INVALID_TYPE_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("argument.entity.options.type.invalid", object));

    private static void putOption(String id, SelectorHandler handler, Predicate<EntitySelectorReader> condition, Text description) {
        options.put(id, new SelectorOption(handler, condition, description));
    }

    public static void register() {
        if (!options.isEmpty()) {
            return;
        }
        EntitySelectorOptions.putOption("name", entitySelectorReader -> {
            int n = entitySelectorReader.getReader().getCursor();
            boolean _snowman2 = entitySelectorReader.readNegationCharacter();
            String _snowman3 = entitySelectorReader.getReader().readString();
            if (entitySelectorReader.excludesName() && !_snowman2) {
                entitySelectorReader.getReader().setCursor(n);
                throw INAPPLICABLE_OPTION_EXCEPTION.createWithContext((ImmutableStringReader)entitySelectorReader.getReader(), (Object)"name");
            }
            if (_snowman2) {
                entitySelectorReader.setExcludesName(true);
            } else {
                entitySelectorReader.setSelectsName(true);
            }
            entitySelectorReader.setPredicate(entity -> entity.getName().getString().equals(_snowman3) != _snowman2);
        }, entitySelectorReader -> !entitySelectorReader.selectsName(), new TranslatableText("argument.entity.options.name.description"));
        EntitySelectorOptions.putOption("distance", entitySelectorReader -> {
            int n = entitySelectorReader.getReader().getCursor();
            NumberRange.FloatRange _snowman2 = NumberRange.FloatRange.parse(entitySelectorReader.getReader());
            if (_snowman2.getMin() != null && ((Float)_snowman2.getMin()).floatValue() < 0.0f || _snowman2.getMax() != null && ((Float)_snowman2.getMax()).floatValue() < 0.0f) {
                entitySelectorReader.getReader().setCursor(n);
                throw NEGATIVE_DISTANCE_EXCEPTION.createWithContext((ImmutableStringReader)entitySelectorReader.getReader());
            }
            entitySelectorReader.setDistance(_snowman2);
            entitySelectorReader.setLocalWorldOnly();
        }, entitySelectorReader -> entitySelectorReader.getDistance().isDummy(), new TranslatableText("argument.entity.options.distance.description"));
        EntitySelectorOptions.putOption("level", entitySelectorReader -> {
            int n = entitySelectorReader.getReader().getCursor();
            NumberRange.IntRange _snowman2 = NumberRange.IntRange.parse(entitySelectorReader.getReader());
            if (_snowman2.getMin() != null && (Integer)_snowman2.getMin() < 0 || _snowman2.getMax() != null && (Integer)_snowman2.getMax() < 0) {
                entitySelectorReader.getReader().setCursor(n);
                throw NEGATIVE_LEVEL_EXCEPTION.createWithContext((ImmutableStringReader)entitySelectorReader.getReader());
            }
            entitySelectorReader.setLevelRange(_snowman2);
            entitySelectorReader.setIncludesNonPlayers(false);
        }, entitySelectorReader -> entitySelectorReader.getLevelRange().isDummy(), new TranslatableText("argument.entity.options.level.description"));
        EntitySelectorOptions.putOption("x", entitySelectorReader -> {
            entitySelectorReader.setLocalWorldOnly();
            entitySelectorReader.setX(entitySelectorReader.getReader().readDouble());
        }, entitySelectorReader -> entitySelectorReader.getX() == null, new TranslatableText("argument.entity.options.x.description"));
        EntitySelectorOptions.putOption("y", entitySelectorReader -> {
            entitySelectorReader.setLocalWorldOnly();
            entitySelectorReader.setY(entitySelectorReader.getReader().readDouble());
        }, entitySelectorReader -> entitySelectorReader.getY() == null, new TranslatableText("argument.entity.options.y.description"));
        EntitySelectorOptions.putOption("z", entitySelectorReader -> {
            entitySelectorReader.setLocalWorldOnly();
            entitySelectorReader.setZ(entitySelectorReader.getReader().readDouble());
        }, entitySelectorReader -> entitySelectorReader.getZ() == null, new TranslatableText("argument.entity.options.z.description"));
        EntitySelectorOptions.putOption("dx", entitySelectorReader -> {
            entitySelectorReader.setLocalWorldOnly();
            entitySelectorReader.setDx(entitySelectorReader.getReader().readDouble());
        }, entitySelectorReader -> entitySelectorReader.getDx() == null, new TranslatableText("argument.entity.options.dx.description"));
        EntitySelectorOptions.putOption("dy", entitySelectorReader -> {
            entitySelectorReader.setLocalWorldOnly();
            entitySelectorReader.setDy(entitySelectorReader.getReader().readDouble());
        }, entitySelectorReader -> entitySelectorReader.getDy() == null, new TranslatableText("argument.entity.options.dy.description"));
        EntitySelectorOptions.putOption("dz", entitySelectorReader -> {
            entitySelectorReader.setLocalWorldOnly();
            entitySelectorReader.setDz(entitySelectorReader.getReader().readDouble());
        }, entitySelectorReader -> entitySelectorReader.getDz() == null, new TranslatableText("argument.entity.options.dz.description"));
        EntitySelectorOptions.putOption("x_rotation", entitySelectorReader -> entitySelectorReader.setPitchRange(FloatRangeArgument.parse(entitySelectorReader.getReader(), true, MathHelper::wrapDegrees)), entitySelectorReader -> entitySelectorReader.getPitchRange() == FloatRangeArgument.ANY, new TranslatableText("argument.entity.options.x_rotation.description"));
        EntitySelectorOptions.putOption("y_rotation", entitySelectorReader -> entitySelectorReader.setYawRange(FloatRangeArgument.parse(entitySelectorReader.getReader(), true, MathHelper::wrapDegrees)), entitySelectorReader -> entitySelectorReader.getYawRange() == FloatRangeArgument.ANY, new TranslatableText("argument.entity.options.y_rotation.description"));
        EntitySelectorOptions.putOption("limit", entitySelectorReader -> {
            int n = entitySelectorReader.getReader().getCursor();
            _snowman = entitySelectorReader.getReader().readInt();
            if (_snowman < 1) {
                entitySelectorReader.getReader().setCursor(n);
                throw TOO_SMALL_LEVEL_EXCEPTION.createWithContext((ImmutableStringReader)entitySelectorReader.getReader());
            }
            entitySelectorReader.setLimit(_snowman);
            entitySelectorReader.setHasLimit(true);
        }, entitySelectorReader -> !entitySelectorReader.isSenderOnly() && !entitySelectorReader.hasLimit(), new TranslatableText("argument.entity.options.limit.description"));
        EntitySelectorOptions.putOption("sort", entitySelectorReader2 -> {
            EntitySelectorReader entitySelectorReader2;
            int n = entitySelectorReader2.getReader().getCursor();
            String _snowman2 = entitySelectorReader2.getReader().readUnquotedString();
            entitySelectorReader2.setSuggestionProvider((suggestionsBuilder, consumer) -> CommandSource.suggestMatching(Arrays.asList("nearest", "furthest", "random", "arbitrary"), suggestionsBuilder));
            switch (_snowman2) {
                case "nearest": {
                    BiConsumer<Vec3d, List<? extends Entity>> biConsumer = EntitySelectorReader.NEAREST;
                    break;
                }
                case "furthest": {
                    BiConsumer<Vec3d, List<? extends Entity>> biConsumer = EntitySelectorReader.FURTHEST;
                    break;
                }
                case "random": {
                    BiConsumer<Vec3d, List<? extends Entity>> biConsumer = EntitySelectorReader.RANDOM;
                    break;
                }
                case "arbitrary": {
                    BiConsumer<Vec3d, List<? extends Entity>> biConsumer = EntitySelectorReader.ARBITRARY;
                    break;
                }
                default: {
                    entitySelectorReader2.getReader().setCursor(n);
                    throw IRREVERSIBLE_SORT_EXCEPTION.createWithContext((ImmutableStringReader)entitySelectorReader2.getReader(), (Object)_snowman2);
                }
            }
            entitySelectorReader2.setSorter(biConsumer);
            entitySelectorReader2.setHasSorter(true);
        }, entitySelectorReader -> !entitySelectorReader.isSenderOnly() && !entitySelectorReader.hasSorter(), new TranslatableText("argument.entity.options.sort.description"));
        EntitySelectorOptions.putOption("gamemode", entitySelectorReader -> {
            entitySelectorReader.setSuggestionProvider((suggestionsBuilder2, consumer) -> {
                SuggestionsBuilder suggestionsBuilder2;
                String string = suggestionsBuilder2.getRemaining().toLowerCase(Locale.ROOT);
                boolean _snowman2 = !entitySelectorReader.excludesGameMode();
                boolean _snowman3 = true;
                if (!string.isEmpty()) {
                    if (string.charAt(0) == '!') {
                        _snowman2 = false;
                        string = string.substring(1);
                    } else {
                        _snowman3 = false;
                    }
                }
                for (GameMode gameMode : GameMode.values()) {
                    if (gameMode == GameMode.NOT_SET || !gameMode.getName().toLowerCase(Locale.ROOT).startsWith(string)) continue;
                    if (_snowman3) {
                        suggestionsBuilder2.suggest('!' + gameMode.getName());
                    }
                    if (!_snowman2) continue;
                    suggestionsBuilder2.suggest(gameMode.getName());
                }
                return suggestionsBuilder2.buildFuture();
            });
            int n = entitySelectorReader.getReader().getCursor();
            boolean _snowman2 = entitySelectorReader.readNegationCharacter();
            if (entitySelectorReader.excludesGameMode() && !_snowman2) {
                entitySelectorReader.getReader().setCursor(n);
                throw INAPPLICABLE_OPTION_EXCEPTION.createWithContext((ImmutableStringReader)entitySelectorReader.getReader(), (Object)"gamemode");
            }
            String _snowman3 = entitySelectorReader.getReader().readUnquotedString();
            GameMode _snowman4 = GameMode.byName(_snowman3, GameMode.NOT_SET);
            if (_snowman4 == GameMode.NOT_SET) {
                entitySelectorReader.getReader().setCursor(n);
                throw INVALID_MODE_EXCEPTION.createWithContext((ImmutableStringReader)entitySelectorReader.getReader(), (Object)_snowman3);
            }
            entitySelectorReader.setIncludesNonPlayers(false);
            entitySelectorReader.setPredicate(entity -> {
                if (!(entity instanceof ServerPlayerEntity)) {
                    return false;
                }
                GameMode gameMode2 = ((ServerPlayerEntity)entity).interactionManager.getGameMode();
                return _snowman2 ? gameMode2 != _snowman4 : gameMode2 == _snowman4;
            });
            if (_snowman2) {
                entitySelectorReader.setHasNegatedGameMode(true);
            } else {
                entitySelectorReader.setSelectsGameMode(true);
            }
        }, entitySelectorReader -> !entitySelectorReader.selectsGameMode(), new TranslatableText("argument.entity.options.gamemode.description"));
        EntitySelectorOptions.putOption("team", entitySelectorReader -> {
            boolean bl = entitySelectorReader.readNegationCharacter();
            String _snowman2 = entitySelectorReader.getReader().readUnquotedString();
            entitySelectorReader.setPredicate(entity -> {
                if (!(entity instanceof LivingEntity)) {
                    return false;
                }
                AbstractTeam abstractTeam = entity.getScoreboardTeam();
                String _snowman2 = abstractTeam == null ? "" : abstractTeam.getName();
                return _snowman2.equals(_snowman2) != bl;
            });
            if (bl) {
                entitySelectorReader.setExcludesTeam(true);
            } else {
                entitySelectorReader.setSelectsTeam(true);
            }
        }, entitySelectorReader -> !entitySelectorReader.selectsTeam(), new TranslatableText("argument.entity.options.team.description"));
        EntitySelectorOptions.putOption("type", entitySelectorReader2 -> {
            entitySelectorReader2.setSuggestionProvider((suggestionsBuilder, consumer) -> {
                CommandSource.suggestIdentifiers(Registry.ENTITY_TYPE.getIds(), suggestionsBuilder, String.valueOf('!'));
                CommandSource.suggestIdentifiers(EntityTypeTags.getTagGroup().getTagIds(), suggestionsBuilder, "!#");
                if (!entitySelectorReader2.excludesEntityType()) {
                    CommandSource.suggestIdentifiers(Registry.ENTITY_TYPE.getIds(), suggestionsBuilder);
                    CommandSource.suggestIdentifiers(EntityTypeTags.getTagGroup().getTagIds(), suggestionsBuilder, String.valueOf('#'));
                }
                return suggestionsBuilder.buildFuture();
            });
            int n = entitySelectorReader2.getReader().getCursor();
            boolean _snowman2 = entitySelectorReader2.readNegationCharacter();
            if (entitySelectorReader2.excludesEntityType() && !_snowman2) {
                entitySelectorReader2.getReader().setCursor(n);
                throw INAPPLICABLE_OPTION_EXCEPTION.createWithContext((ImmutableStringReader)entitySelectorReader2.getReader(), (Object)"type");
            }
            if (_snowman2) {
                entitySelectorReader2.setExcludesEntityType();
            }
            if (entitySelectorReader2.readTagCharacter()) {
                Identifier identifier = Identifier.fromCommandInput(entitySelectorReader2.getReader());
                entitySelectorReader2.setPredicate(entity -> entity.getServer().getTagManager().getEntityTypes().getTagOrEmpty(identifier).contains(entity.getType()) != _snowman2);
            } else {
                EntitySelectorReader entitySelectorReader2;
                Identifier _snowman3 = Identifier.fromCommandInput(entitySelectorReader2.getReader());
                EntityType<?> _snowman4 = Registry.ENTITY_TYPE.getOrEmpty(_snowman3).orElseThrow(() -> {
                    entitySelectorReader2.getReader().setCursor(n);
                    return INVALID_TYPE_EXCEPTION.createWithContext((ImmutableStringReader)entitySelectorReader2.getReader(), (Object)_snowman3.toString());
                });
                if (Objects.equals(EntityType.PLAYER, _snowman4) && !_snowman2) {
                    entitySelectorReader2.setIncludesNonPlayers(false);
                }
                entitySelectorReader2.setPredicate(entity -> Objects.equals(_snowman4, entity.getType()) != _snowman2);
                if (!_snowman2) {
                    entitySelectorReader2.setEntityType(_snowman4);
                }
            }
        }, entitySelectorReader -> !entitySelectorReader.selectsEntityType(), new TranslatableText("argument.entity.options.type.description"));
        EntitySelectorOptions.putOption("tag", entitySelectorReader -> {
            boolean bl = entitySelectorReader.readNegationCharacter();
            String _snowman2 = entitySelectorReader.getReader().readUnquotedString();
            entitySelectorReader.setPredicate(entity -> {
                if ("".equals(_snowman2)) {
                    return entity.getScoreboardTags().isEmpty() != bl;
                }
                return entity.getScoreboardTags().contains(_snowman2) != bl;
            });
        }, entitySelectorReader -> true, new TranslatableText("argument.entity.options.tag.description"));
        EntitySelectorOptions.putOption("nbt", entitySelectorReader -> {
            boolean bl = entitySelectorReader.readNegationCharacter();
            CompoundTag _snowman2 = new StringNbtReader(entitySelectorReader.getReader()).parseCompoundTag();
            entitySelectorReader.setPredicate(entity -> {
                CompoundTag compoundTag2 = entity.toTag(new CompoundTag());
                if (entity instanceof ServerPlayerEntity && !(_snowman = ((ServerPlayerEntity)entity).inventory.getMainHandStack()).isEmpty()) {
                    compoundTag2.put("SelectedItem", _snowman.toTag(new CompoundTag()));
                }
                return NbtHelper.matches(_snowman2, compoundTag2, true) != bl;
            });
        }, entitySelectorReader -> true, new TranslatableText("argument.entity.options.nbt.description"));
        EntitySelectorOptions.putOption("scores", entitySelectorReader -> {
            StringReader stringReader = entitySelectorReader.getReader();
            HashMap _snowman2 = Maps.newHashMap();
            stringReader.expect('{');
            stringReader.skipWhitespace();
            while (stringReader.canRead() && stringReader.peek() != '}') {
                stringReader.skipWhitespace();
                String string = stringReader.readUnquotedString();
                stringReader.skipWhitespace();
                stringReader.expect('=');
                stringReader.skipWhitespace();
                NumberRange.IntRange _snowman3 = NumberRange.IntRange.parse(stringReader);
                _snowman2.put(string, _snowman3);
                stringReader.skipWhitespace();
                if (!stringReader.canRead() || stringReader.peek() != ',') continue;
                stringReader.skip();
            }
            stringReader.expect('}');
            if (!_snowman2.isEmpty()) {
                entitySelectorReader.setPredicate(entity -> {
                    ServerScoreboard serverScoreboard = entity.getServer().getScoreboard();
                    String _snowman2 = entity.getEntityName();
                    for (Map.Entry entry : _snowman2.entrySet()) {
                        ScoreboardObjective scoreboardObjective = serverScoreboard.getNullableObjective((String)entry.getKey());
                        if (scoreboardObjective == null) {
                            return false;
                        }
                        if (!serverScoreboard.playerHasObjective(_snowman2, scoreboardObjective)) {
                            return false;
                        }
                        ScoreboardPlayerScore _snowman3 = serverScoreboard.getPlayerScore(_snowman2, scoreboardObjective);
                        int _snowman4 = _snowman3.getScore();
                        if (((NumberRange.IntRange)entry.getValue()).test(_snowman4)) continue;
                        return false;
                    }
                    return true;
                });
            }
            entitySelectorReader.setSelectsScores(true);
        }, entitySelectorReader -> !entitySelectorReader.selectsScores(), new TranslatableText("argument.entity.options.scores.description"));
        EntitySelectorOptions.putOption("advancements", entitySelectorReader -> {
            StringReader stringReader = entitySelectorReader.getReader();
            HashMap _snowman2 = Maps.newHashMap();
            stringReader.expect('{');
            stringReader.skipWhitespace();
            while (stringReader.canRead() && stringReader.peek() != '}') {
                stringReader.skipWhitespace();
                Identifier identifier = Identifier.fromCommandInput(stringReader);
                stringReader.skipWhitespace();
                stringReader.expect('=');
                stringReader.skipWhitespace();
                if (stringReader.canRead() && stringReader.peek() == '{') {
                    HashMap hashMap = Maps.newHashMap();
                    stringReader.skipWhitespace();
                    stringReader.expect('{');
                    stringReader.skipWhitespace();
                    while (stringReader.canRead() && stringReader.peek() != '}') {
                        stringReader.skipWhitespace();
                        String string = stringReader.readUnquotedString();
                        stringReader.skipWhitespace();
                        stringReader.expect('=');
                        stringReader.skipWhitespace();
                        boolean _snowman3 = stringReader.readBoolean();
                        hashMap.put(string, criterionProgress -> criterionProgress.isObtained() == _snowman3);
                        stringReader.skipWhitespace();
                        if (!stringReader.canRead() || stringReader.peek() != ',') continue;
                        stringReader.skip();
                    }
                    stringReader.skipWhitespace();
                    stringReader.expect('}');
                    stringReader.skipWhitespace();
                    _snowman2.put(identifier, advancementProgress -> {
                        for (Map.Entry entry : hashMap.entrySet()) {
                            CriterionProgress criterionProgress = advancementProgress.getCriterionProgress((String)entry.getKey());
                            if (criterionProgress != null && ((Predicate)entry.getValue()).test(criterionProgress)) continue;
                            return false;
                        }
                        return true;
                    });
                } else {
                    boolean bl = stringReader.readBoolean();
                    _snowman2.put(identifier, advancementProgress -> advancementProgress.isDone() == bl);
                }
                stringReader.skipWhitespace();
                if (!stringReader.canRead() || stringReader.peek() != ',') continue;
                stringReader.skip();
            }
            stringReader.expect('}');
            if (!_snowman2.isEmpty()) {
                entitySelectorReader.setPredicate(entity -> {
                    if (!(entity instanceof ServerPlayerEntity)) {
                        return false;
                    }
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
                    PlayerAdvancementTracker _snowman2 = serverPlayerEntity.getAdvancementTracker();
                    ServerAdvancementLoader _snowman3 = serverPlayerEntity.getServer().getAdvancementLoader();
                    for (Map.Entry entry : _snowman2.entrySet()) {
                        Advancement advancement = _snowman3.get((Identifier)entry.getKey());
                        if (advancement != null && ((Predicate)entry.getValue()).test(_snowman2.getProgress(advancement))) continue;
                        return false;
                    }
                    return true;
                });
                entitySelectorReader.setIncludesNonPlayers(false);
            }
            entitySelectorReader.setSelectsAdvancements(true);
        }, entitySelectorReader -> !entitySelectorReader.selectsAdvancements(), new TranslatableText("argument.entity.options.advancements.description"));
        EntitySelectorOptions.putOption("predicate", entitySelectorReader -> {
            boolean bl = entitySelectorReader.readNegationCharacter();
            Identifier _snowman2 = Identifier.fromCommandInput(entitySelectorReader.getReader());
            entitySelectorReader.setPredicate(entity -> {
                if (!(entity.world instanceof ServerWorld)) {
                    return false;
                }
                ServerWorld serverWorld = (ServerWorld)entity.world;
                LootCondition _snowman2 = serverWorld.getServer().getPredicateManager().get(_snowman2);
                if (_snowman2 == null) {
                    return false;
                }
                LootContext _snowman3 = new LootContext.Builder(serverWorld).parameter(LootContextParameters.THIS_ENTITY, entity).parameter(LootContextParameters.ORIGIN, entity.getPos()).build(LootContextTypes.SELECTOR);
                return bl ^ _snowman2.test(_snowman3);
            });
        }, entitySelectorReader -> true, new TranslatableText("argument.entity.options.predicate.description"));
    }

    public static SelectorHandler getHandler(EntitySelectorReader reader, String option, int restoreCursor) throws CommandSyntaxException {
        SelectorOption selectorOption = options.get(option);
        if (selectorOption != null) {
            if (selectorOption.condition.test(reader)) {
                return selectorOption.handler;
            }
            throw INAPPLICABLE_OPTION_EXCEPTION.createWithContext((ImmutableStringReader)reader.getReader(), (Object)option);
        }
        reader.getReader().setCursor(restoreCursor);
        throw UNKNOWN_OPTION_EXCEPTION.createWithContext((ImmutableStringReader)reader.getReader(), (Object)option);
    }

    public static void suggestOptions(EntitySelectorReader reader, SuggestionsBuilder suggestionBuilder) {
        String string = suggestionBuilder.getRemaining().toLowerCase(Locale.ROOT);
        for (Map.Entry<String, SelectorOption> entry : options.entrySet()) {
            if (!entry.getValue().condition.test(reader) || !entry.getKey().toLowerCase(Locale.ROOT).startsWith(string)) continue;
            suggestionBuilder.suggest(entry.getKey() + '=', (Message)entry.getValue().description);
        }
    }

    static class SelectorOption {
        public final SelectorHandler handler;
        public final Predicate<EntitySelectorReader> condition;
        public final Text description;

        private SelectorOption(SelectorHandler handler, Predicate<EntitySelectorReader> condition, Text description) {
            this.handler = handler;
            this.condition = condition;
            this.description = description;
        }
    }

    public static interface SelectorHandler {
        public void handle(EntitySelectorReader var1) throws CommandSyntaxException;
    }
}

