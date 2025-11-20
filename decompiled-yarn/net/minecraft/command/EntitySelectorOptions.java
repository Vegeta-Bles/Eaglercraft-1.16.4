package net.minecraft.command;

import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.advancement.criterion.CriterionProgress;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.predicate.NumberRange;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
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
   private static final Map<String, EntitySelectorOptions.SelectorOption> options = Maps.newHashMap();
   public static final DynamicCommandExceptionType UNKNOWN_OPTION_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("argument.entity.options.unknown", _snowman)
   );
   public static final DynamicCommandExceptionType INAPPLICABLE_OPTION_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("argument.entity.options.inapplicable", _snowman)
   );
   public static final SimpleCommandExceptionType NEGATIVE_DISTANCE_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("argument.entity.options.distance.negative")
   );
   public static final SimpleCommandExceptionType NEGATIVE_LEVEL_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("argument.entity.options.level.negative")
   );
   public static final SimpleCommandExceptionType TOO_SMALL_LEVEL_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("argument.entity.options.limit.toosmall")
   );
   public static final DynamicCommandExceptionType IRREVERSIBLE_SORT_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("argument.entity.options.sort.irreversible", _snowman)
   );
   public static final DynamicCommandExceptionType INVALID_MODE_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("argument.entity.options.mode.invalid", _snowman)
   );
   public static final DynamicCommandExceptionType INVALID_TYPE_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("argument.entity.options.type.invalid", _snowman)
   );

   private static void putOption(String id, EntitySelectorOptions.SelectorHandler handler, Predicate<EntitySelectorReader> condition, Text description) {
      options.put(id, new EntitySelectorOptions.SelectorOption(handler, condition, description));
   }

   public static void register() {
      if (options.isEmpty()) {
         putOption("name", _snowman -> {
            int _snowmanx = _snowman.getReader().getCursor();
            boolean _snowmanxx = _snowman.readNegationCharacter();
            String _snowmanxxx = _snowman.getReader().readString();
            if (_snowman.excludesName() && !_snowmanxx) {
               _snowman.getReader().setCursor(_snowmanx);
               throw INAPPLICABLE_OPTION_EXCEPTION.createWithContext(_snowman.getReader(), "name");
            } else {
               if (_snowmanxx) {
                  _snowman.setExcludesName(true);
               } else {
                  _snowman.setSelectsName(true);
               }

               _snowman.setPredicate(_snowmanxxxx -> _snowmanxxxx.getName().getString().equals(_snowman) != _snowman);
            }
         }, _snowman -> !_snowman.selectsName(), new TranslatableText("argument.entity.options.name.description"));
         putOption("distance", _snowman -> {
            int _snowmanx = _snowman.getReader().getCursor();
            NumberRange.FloatRange _snowmanxx = NumberRange.FloatRange.parse(_snowman.getReader());
            if ((_snowmanxx.getMin() == null || !((Float)_snowmanxx.getMin() < 0.0F)) && (_snowmanxx.getMax() == null || !((Float)_snowmanxx.getMax() < 0.0F))) {
               _snowman.setDistance(_snowmanxx);
               _snowman.setLocalWorldOnly();
            } else {
               _snowman.getReader().setCursor(_snowmanx);
               throw NEGATIVE_DISTANCE_EXCEPTION.createWithContext(_snowman.getReader());
            }
         }, _snowman -> _snowman.getDistance().isDummy(), new TranslatableText("argument.entity.options.distance.description"));
         putOption("level", _snowman -> {
            int _snowmanx = _snowman.getReader().getCursor();
            NumberRange.IntRange _snowmanxx = NumberRange.IntRange.parse(_snowman.getReader());
            if ((_snowmanxx.getMin() == null || (Integer)_snowmanxx.getMin() >= 0) && (_snowmanxx.getMax() == null || (Integer)_snowmanxx.getMax() >= 0)) {
               _snowman.setLevelRange(_snowmanxx);
               _snowman.setIncludesNonPlayers(false);
            } else {
               _snowman.getReader().setCursor(_snowmanx);
               throw NEGATIVE_LEVEL_EXCEPTION.createWithContext(_snowman.getReader());
            }
         }, _snowman -> _snowman.getLevelRange().isDummy(), new TranslatableText("argument.entity.options.level.description"));
         putOption("x", _snowman -> {
            _snowman.setLocalWorldOnly();
            _snowman.setX(_snowman.getReader().readDouble());
         }, _snowman -> _snowman.getX() == null, new TranslatableText("argument.entity.options.x.description"));
         putOption("y", _snowman -> {
            _snowman.setLocalWorldOnly();
            _snowman.setY(_snowman.getReader().readDouble());
         }, _snowman -> _snowman.getY() == null, new TranslatableText("argument.entity.options.y.description"));
         putOption("z", _snowman -> {
            _snowman.setLocalWorldOnly();
            _snowman.setZ(_snowman.getReader().readDouble());
         }, _snowman -> _snowman.getZ() == null, new TranslatableText("argument.entity.options.z.description"));
         putOption("dx", _snowman -> {
            _snowman.setLocalWorldOnly();
            _snowman.setDx(_snowman.getReader().readDouble());
         }, _snowman -> _snowman.getDx() == null, new TranslatableText("argument.entity.options.dx.description"));
         putOption("dy", _snowman -> {
            _snowman.setLocalWorldOnly();
            _snowman.setDy(_snowman.getReader().readDouble());
         }, _snowman -> _snowman.getDy() == null, new TranslatableText("argument.entity.options.dy.description"));
         putOption("dz", _snowman -> {
            _snowman.setLocalWorldOnly();
            _snowman.setDz(_snowman.getReader().readDouble());
         }, _snowman -> _snowman.getDz() == null, new TranslatableText("argument.entity.options.dz.description"));
         putOption(
            "x_rotation",
            _snowman -> _snowman.setPitchRange(FloatRangeArgument.parse(_snowman.getReader(), true, MathHelper::wrapDegrees)),
            _snowman -> _snowman.getPitchRange() == FloatRangeArgument.ANY,
            new TranslatableText("argument.entity.options.x_rotation.description")
         );
         putOption(
            "y_rotation",
            _snowman -> _snowman.setYawRange(FloatRangeArgument.parse(_snowman.getReader(), true, MathHelper::wrapDegrees)),
            _snowman -> _snowman.getYawRange() == FloatRangeArgument.ANY,
            new TranslatableText("argument.entity.options.y_rotation.description")
         );
         putOption("limit", _snowman -> {
            int _snowmanx = _snowman.getReader().getCursor();
            int _snowmanxx = _snowman.getReader().readInt();
            if (_snowmanxx < 1) {
               _snowman.getReader().setCursor(_snowmanx);
               throw TOO_SMALL_LEVEL_EXCEPTION.createWithContext(_snowman.getReader());
            } else {
               _snowman.setLimit(_snowmanxx);
               _snowman.setHasLimit(true);
            }
         }, _snowman -> !_snowman.isSenderOnly() && !_snowman.hasLimit(), new TranslatableText("argument.entity.options.limit.description"));
         putOption("sort", _snowman -> {
            int _snowmanx = _snowman.getReader().getCursor();
            String _snowmanxx = _snowman.getReader().readUnquotedString();
            _snowman.setSuggestionProvider((_snowmanxxx, _snowmanxxxx) -> CommandSource.suggestMatching(Arrays.asList("nearest", "furthest", "random", "arbitrary"), _snowmanxxx));
            BiConsumer<Vec3d, List<? extends Entity>> _snowmanxxx;
            switch (_snowmanxx) {
               case "nearest":
                  _snowmanxxx = EntitySelectorReader.NEAREST;
                  break;
               case "furthest":
                  _snowmanxxx = EntitySelectorReader.FURTHEST;
                  break;
               case "random":
                  _snowmanxxx = EntitySelectorReader.RANDOM;
                  break;
               case "arbitrary":
                  _snowmanxxx = EntitySelectorReader.ARBITRARY;
                  break;
               default:
                  _snowman.getReader().setCursor(_snowmanx);
                  throw IRREVERSIBLE_SORT_EXCEPTION.createWithContext(_snowman.getReader(), _snowmanxx);
            }

            _snowman.setSorter(_snowmanxxx);
            _snowman.setHasSorter(true);
         }, _snowman -> !_snowman.isSenderOnly() && !_snowman.hasSorter(), new TranslatableText("argument.entity.options.sort.description"));
         putOption("gamemode", _snowman -> {
            _snowman.setSuggestionProvider((_snowmanxxxxxxx, _snowmanxxxxxx) -> {
               String _snowmanxxx = _snowmanxxxxxxx.getRemaining().toLowerCase(Locale.ROOT);
               boolean _snowmanxxxx = !_snowman.excludesGameMode();
               boolean _snowmanxxxxx = true;
               if (!_snowmanxxx.isEmpty()) {
                  if (_snowmanxxx.charAt(0) == '!') {
                     _snowmanxxxx = false;
                     _snowmanxxx = _snowmanxxx.substring(1);
                  } else {
                     _snowmanxxxxx = false;
                  }
               }

               for (GameMode _snowmanxxxxxx : GameMode.values()) {
                  if (_snowmanxxxxxx != GameMode.NOT_SET && _snowmanxxxxxx.getName().toLowerCase(Locale.ROOT).startsWith(_snowmanxxx)) {
                     if (_snowmanxxxxx) {
                        _snowmanxxxxxxx.suggest('!' + _snowmanxxxxxx.getName());
                     }

                     if (_snowmanxxxx) {
                        _snowmanxxxxxxx.suggest(_snowmanxxxxxx.getName());
                     }
                  }
               }

               return _snowmanxxxxxxx.buildFuture();
            });
            int _snowmanx = _snowman.getReader().getCursor();
            boolean _snowmanxx = _snowman.readNegationCharacter();
            if (_snowman.excludesGameMode() && !_snowmanxx) {
               _snowman.getReader().setCursor(_snowmanx);
               throw INAPPLICABLE_OPTION_EXCEPTION.createWithContext(_snowman.getReader(), "gamemode");
            } else {
               String _snowmanxxx = _snowman.getReader().readUnquotedString();
               GameMode _snowmanxxxx = GameMode.byName(_snowmanxxx, GameMode.NOT_SET);
               if (_snowmanxxxx == GameMode.NOT_SET) {
                  _snowman.getReader().setCursor(_snowmanx);
                  throw INVALID_MODE_EXCEPTION.createWithContext(_snowman.getReader(), _snowmanxxx);
               } else {
                  _snowman.setIncludesNonPlayers(false);
                  _snowman.setPredicate(_snowmanxxxxx -> {
                     if (!(_snowmanxxxxx instanceof ServerPlayerEntity)) {
                        return false;
                     } else {
                        GameMode _snowmanxxxxxx = ((ServerPlayerEntity)_snowmanxxxxx).interactionManager.getGameMode();
                        return _snowman ? _snowmanxxxxxx != _snowman : _snowmanxxxxxx == _snowman;
                     }
                  });
                  if (_snowmanxx) {
                     _snowman.setHasNegatedGameMode(true);
                  } else {
                     _snowman.setSelectsGameMode(true);
                  }
               }
            }
         }, _snowman -> !_snowman.selectsGameMode(), new TranslatableText("argument.entity.options.gamemode.description"));
         putOption("team", _snowman -> {
            boolean _snowmanx = _snowman.readNegationCharacter();
            String _snowmanxx = _snowman.getReader().readUnquotedString();
            _snowman.setPredicate(_snowmanxxxx -> {
               if (!(_snowmanxxxx instanceof LivingEntity)) {
                  return false;
               } else {
                  AbstractTeam _snowmanxxx = _snowmanxxxx.getScoreboardTeam();
                  String _snowmanxxxx = _snowmanxxx == null ? "" : _snowmanxxx.getName();
                  return _snowmanxxxx.equals(_snowman) != _snowman;
               }
            });
            if (_snowmanx) {
               _snowman.setExcludesTeam(true);
            } else {
               _snowman.setSelectsTeam(true);
            }
         }, _snowman -> !_snowman.selectsTeam(), new TranslatableText("argument.entity.options.team.description"));
         putOption("type", _snowman -> {
            _snowman.setSuggestionProvider((_snowmanxxx, _snowmanxx) -> {
               CommandSource.suggestIdentifiers(Registry.ENTITY_TYPE.getIds(), _snowmanxxx, String.valueOf('!'));
               CommandSource.suggestIdentifiers(EntityTypeTags.getTagGroup().getTagIds(), _snowmanxxx, "!#");
               if (!_snowman.excludesEntityType()) {
                  CommandSource.suggestIdentifiers(Registry.ENTITY_TYPE.getIds(), _snowmanxxx);
                  CommandSource.suggestIdentifiers(EntityTypeTags.getTagGroup().getTagIds(), _snowmanxxx, String.valueOf('#'));
               }

               return _snowmanxxx.buildFuture();
            });
            int _snowmanx = _snowman.getReader().getCursor();
            boolean _snowmanxx = _snowman.readNegationCharacter();
            if (_snowman.excludesEntityType() && !_snowmanxx) {
               _snowman.getReader().setCursor(_snowmanx);
               throw INAPPLICABLE_OPTION_EXCEPTION.createWithContext(_snowman.getReader(), "type");
            } else {
               if (_snowmanxx) {
                  _snowman.setExcludesEntityType();
               }

               if (_snowman.readTagCharacter()) {
                  Identifier _snowmanxxx = Identifier.fromCommandInput(_snowman.getReader());
                  _snowman.setPredicate(_snowmanxxxx -> _snowmanxxxx.getServer().getTagManager().getEntityTypes().getTagOrEmpty(_snowman).contains(_snowmanxxxx.getType()) != _snowman);
               } else {
                  Identifier _snowmanxxx = Identifier.fromCommandInput(_snowman.getReader());
                  EntityType<?> _snowmanxxxx = Registry.ENTITY_TYPE.getOrEmpty(_snowmanxxx).orElseThrow(() -> {
                     _snowman.getReader().setCursor(_snowman);
                     return INVALID_TYPE_EXCEPTION.createWithContext(_snowman.getReader(), _snowman.toString());
                  });
                  if (Objects.equals(EntityType.PLAYER, _snowmanxxxx) && !_snowmanxx) {
                     _snowman.setIncludesNonPlayers(false);
                  }

                  _snowman.setPredicate(_snowmanxxxxx -> Objects.equals(_snowman, _snowmanxxxxx.getType()) != _snowman);
                  if (!_snowmanxx) {
                     _snowman.setEntityType(_snowmanxxxx);
                  }
               }
            }
         }, _snowman -> !_snowman.selectsEntityType(), new TranslatableText("argument.entity.options.type.description"));
         putOption("tag", _snowman -> {
            boolean _snowmanx = _snowman.readNegationCharacter();
            String _snowmanxx = _snowman.getReader().readUnquotedString();
            _snowman.setPredicate(_snowmanxxx -> "".equals(_snowman) ? _snowmanxxx.getScoreboardTags().isEmpty() != _snowman : _snowmanxxx.getScoreboardTags().contains(_snowman) != _snowman);
         }, _snowman -> true, new TranslatableText("argument.entity.options.tag.description"));
         putOption("nbt", _snowman -> {
            boolean _snowmanx = _snowman.readNegationCharacter();
            CompoundTag _snowmanxx = new StringNbtReader(_snowman.getReader()).parseCompoundTag();
            _snowman.setPredicate(_snowmanxxxx -> {
               CompoundTag _snowmanxxx = _snowmanxxxx.toTag(new CompoundTag());
               if (_snowmanxxxx instanceof ServerPlayerEntity) {
                  ItemStack _snowmanxxxx = ((ServerPlayerEntity)_snowmanxxxx).inventory.getMainHandStack();
                  if (!_snowmanxxxx.isEmpty()) {
                     _snowmanxxx.put("SelectedItem", _snowmanxxxx.toTag(new CompoundTag()));
                  }
               }

               return NbtHelper.matches(_snowman, _snowmanxxx, true) != _snowman;
            });
         }, _snowman -> true, new TranslatableText("argument.entity.options.nbt.description"));
         putOption("scores", _snowman -> {
            StringReader _snowmanx = _snowman.getReader();
            Map<String, NumberRange.IntRange> _snowmanxx = Maps.newHashMap();
            _snowmanx.expect('{');
            _snowmanx.skipWhitespace();

            while (_snowmanx.canRead() && _snowmanx.peek() != '}') {
               _snowmanx.skipWhitespace();
               String _snowmanxxx = _snowmanx.readUnquotedString();
               _snowmanx.skipWhitespace();
               _snowmanx.expect('=');
               _snowmanx.skipWhitespace();
               NumberRange.IntRange _snowmanxxxx = NumberRange.IntRange.parse(_snowmanx);
               _snowmanxx.put(_snowmanxxx, _snowmanxxxx);
               _snowmanx.skipWhitespace();
               if (_snowmanx.canRead() && _snowmanx.peek() == ',') {
                  _snowmanx.skip();
               }
            }

            _snowmanx.expect('}');
            if (!_snowmanxx.isEmpty()) {
               _snowman.setPredicate(_snowmanxxxxxxxx -> {
                  Scoreboard _snowmanxx = _snowmanxxxxxxxx.getServer().getScoreboard();
                  String _snowmanxxx = _snowmanxxxxxxxx.getEntityName();

                  for (Entry<String, NumberRange.IntRange> _snowmanxxxx : _snowman.entrySet()) {
                     ScoreboardObjective _snowmanxxxxx = _snowmanxx.getNullableObjective(_snowmanxxxx.getKey());
                     if (_snowmanxxxxx == null) {
                        return false;
                     }

                     if (!_snowmanxx.playerHasObjective(_snowmanxxx, _snowmanxxxxx)) {
                        return false;
                     }

                     ScoreboardPlayerScore _snowmanxxxxxx = _snowmanxx.getPlayerScore(_snowmanxxx, _snowmanxxxxx);
                     int _snowmanxxxxxxxx = _snowmanxxxxxx.getScore();
                     if (!_snowmanxxxx.getValue().test(_snowmanxxxxxxxx)) {
                        return false;
                     }
                  }

                  return true;
               });
            }

            _snowman.setSelectsScores(true);
         }, _snowman -> !_snowman.selectsScores(), new TranslatableText("argument.entity.options.scores.description"));
         putOption("advancements", _snowman -> {
            StringReader _snowmanx = _snowman.getReader();
            Map<Identifier, Predicate<AdvancementProgress>> _snowmanxx = Maps.newHashMap();
            _snowmanx.expect('{');
            _snowmanx.skipWhitespace();

            while (_snowmanx.canRead() && _snowmanx.peek() != '}') {
               _snowmanx.skipWhitespace();
               Identifier _snowmanxxx = Identifier.fromCommandInput(_snowmanx);
               _snowmanx.skipWhitespace();
               _snowmanx.expect('=');
               _snowmanx.skipWhitespace();
               if (_snowmanx.canRead() && _snowmanx.peek() == '{') {
                  Map<String, Predicate<CriterionProgress>> _snowmanxxxx = Maps.newHashMap();
                  _snowmanx.skipWhitespace();
                  _snowmanx.expect('{');
                  _snowmanx.skipWhitespace();

                  while (_snowmanx.canRead() && _snowmanx.peek() != '}') {
                     _snowmanx.skipWhitespace();
                     String _snowmanxxxxx = _snowmanx.readUnquotedString();
                     _snowmanx.skipWhitespace();
                     _snowmanx.expect('=');
                     _snowmanx.skipWhitespace();
                     boolean _snowmanxxxxxx = _snowmanx.readBoolean();
                     _snowmanxxxx.put(_snowmanxxxxx, _snowmanxxxxxxx -> _snowmanxxxxxxx.isObtained() == _snowman);
                     _snowmanx.skipWhitespace();
                     if (_snowmanx.canRead() && _snowmanx.peek() == ',') {
                        _snowmanx.skip();
                     }
                  }

                  _snowmanx.skipWhitespace();
                  _snowmanx.expect('}');
                  _snowmanx.skipWhitespace();
                  _snowmanxx.put(_snowmanxxx, _snowmanxxxxx -> {
                     for (Entry<String, Predicate<CriterionProgress>> _snowmanxxxxxx : _snowman.entrySet()) {
                        CriterionProgress _snowmanxxxxxxx = _snowmanxxxxx.getCriterionProgress(_snowmanxxxxxx.getKey());
                        if (_snowmanxxxxxxx == null || !_snowmanxxxxxx.getValue().test(_snowmanxxxxxxx)) {
                           return false;
                        }
                     }

                     return true;
                  });
               } else {
                  boolean _snowmanxxxx = _snowmanx.readBoolean();
                  _snowmanxx.put(_snowmanxxx, _snowmanxxxxx -> _snowmanxxxxx.isDone() == _snowman);
               }

               _snowmanx.skipWhitespace();
               if (_snowmanx.canRead() && _snowmanx.peek() == ',') {
                  _snowmanx.skip();
               }
            }

            _snowmanx.expect('}');
            if (!_snowmanxx.isEmpty()) {
               _snowman.setPredicate(_snowmanxxxxxxx -> {
                  if (!(_snowmanxxxxxxx instanceof ServerPlayerEntity)) {
                     return false;
                  } else {
                     ServerPlayerEntity _snowmanxx = (ServerPlayerEntity)_snowmanxxxxxxx;
                     PlayerAdvancementTracker _snowmanxxx = _snowmanxx.getAdvancementTracker();
                     ServerAdvancementLoader _snowmanxxxx = _snowmanxx.getServer().getAdvancementLoader();

                     for (Entry<Identifier, Predicate<AdvancementProgress>> _snowmanxxxxxxx : _snowman.entrySet()) {
                        Advancement _snowmanxxxxxxxx = _snowmanxxxx.get(_snowmanxxxxxxx.getKey());
                        if (_snowmanxxxxxxxx == null || !_snowmanxxxxxxx.getValue().test(_snowmanxxx.getProgress(_snowmanxxxxxxxx))) {
                           return false;
                        }
                     }

                     return true;
                  }
               });
               _snowman.setIncludesNonPlayers(false);
            }

            _snowman.setSelectsAdvancements(true);
         }, _snowman -> !_snowman.selectsAdvancements(), new TranslatableText("argument.entity.options.advancements.description"));
         putOption(
            "predicate",
            _snowman -> {
               boolean _snowmanx = _snowman.readNegationCharacter();
               Identifier _snowmanxx = Identifier.fromCommandInput(_snowman.getReader());
               _snowman.setPredicate(
                  _snowmanxxxxx -> {
                     if (!(_snowmanxxxxx.world instanceof ServerWorld)) {
                        return false;
                     } else {
                        ServerWorld _snowmanxxx = (ServerWorld)_snowmanxxxxx.world;
                        LootCondition _snowmanxxxx = _snowmanxxx.getServer().getPredicateManager().get(_snowman);
                        if (_snowmanxxxx == null) {
                           return false;
                        } else {
                           LootContext _snowmanxxxxx = new LootContext.Builder(_snowmanxxx)
                              .parameter(LootContextParameters.THIS_ENTITY, _snowmanxxxxx)
                              .parameter(LootContextParameters.ORIGIN, _snowmanxxxxx.getPos())
                              .build(LootContextTypes.SELECTOR);
                           return _snowman ^ _snowmanxxxx.test(_snowmanxxxxx);
                        }
                     }
                  }
               );
            },
            _snowman -> true,
            new TranslatableText("argument.entity.options.predicate.description")
         );
      }
   }

   public static EntitySelectorOptions.SelectorHandler getHandler(EntitySelectorReader reader, String option, int restoreCursor) throws CommandSyntaxException {
      EntitySelectorOptions.SelectorOption _snowman = options.get(option);
      if (_snowman != null) {
         if (_snowman.condition.test(reader)) {
            return _snowman.handler;
         } else {
            throw INAPPLICABLE_OPTION_EXCEPTION.createWithContext(reader.getReader(), option);
         }
      } else {
         reader.getReader().setCursor(restoreCursor);
         throw UNKNOWN_OPTION_EXCEPTION.createWithContext(reader.getReader(), option);
      }
   }

   public static void suggestOptions(EntitySelectorReader reader, SuggestionsBuilder suggestionBuilder) {
      String _snowman = suggestionBuilder.getRemaining().toLowerCase(Locale.ROOT);

      for (Entry<String, EntitySelectorOptions.SelectorOption> _snowmanx : options.entrySet()) {
         if (_snowmanx.getValue().condition.test(reader) && _snowmanx.getKey().toLowerCase(Locale.ROOT).startsWith(_snowman)) {
            suggestionBuilder.suggest(_snowmanx.getKey() + '=', _snowmanx.getValue().description);
         }
      }
   }

   public interface SelectorHandler {
      void handle(EntitySelectorReader reader) throws CommandSyntaxException;
   }

   static class SelectorOption {
      public final EntitySelectorOptions.SelectorHandler handler;
      public final Predicate<EntitySelectorReader> condition;
      public final Text description;

      private SelectorOption(EntitySelectorOptions.SelectorHandler handler, Predicate<EntitySelectorReader> condition, Text description) {
         this.handler = handler;
         this.condition = condition;
         this.description = description;
      }
   }
}
