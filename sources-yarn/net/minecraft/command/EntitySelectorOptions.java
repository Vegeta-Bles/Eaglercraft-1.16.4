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
      object -> new TranslatableText("argument.entity.options.unknown", object)
   );
   public static final DynamicCommandExceptionType INAPPLICABLE_OPTION_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("argument.entity.options.inapplicable", object)
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
      object -> new TranslatableText("argument.entity.options.sort.irreversible", object)
   );
   public static final DynamicCommandExceptionType INVALID_MODE_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("argument.entity.options.mode.invalid", object)
   );
   public static final DynamicCommandExceptionType INVALID_TYPE_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("argument.entity.options.type.invalid", object)
   );

   private static void putOption(String id, EntitySelectorOptions.SelectorHandler handler, Predicate<EntitySelectorReader> condition, Text description) {
      options.put(id, new EntitySelectorOptions.SelectorOption(handler, condition, description));
   }

   public static void register() {
      if (options.isEmpty()) {
         putOption("name", arg -> {
            int i = arg.getReader().getCursor();
            boolean bl = arg.readNegationCharacter();
            String string = arg.getReader().readString();
            if (arg.excludesName() && !bl) {
               arg.getReader().setCursor(i);
               throw INAPPLICABLE_OPTION_EXCEPTION.createWithContext(arg.getReader(), "name");
            } else {
               if (bl) {
                  arg.setExcludesName(true);
               } else {
                  arg.setSelectsName(true);
               }

               arg.setPredicate(argx -> argx.getName().getString().equals(string) != bl);
            }
         }, arg -> !arg.selectsName(), new TranslatableText("argument.entity.options.name.description"));
         putOption("distance", arg -> {
            int i = arg.getReader().getCursor();
            NumberRange.FloatRange lv = NumberRange.FloatRange.parse(arg.getReader());
            if ((lv.getMin() == null || !((Float)lv.getMin() < 0.0F)) && (lv.getMax() == null || !((Float)lv.getMax() < 0.0F))) {
               arg.setDistance(lv);
               arg.setLocalWorldOnly();
            } else {
               arg.getReader().setCursor(i);
               throw NEGATIVE_DISTANCE_EXCEPTION.createWithContext(arg.getReader());
            }
         }, arg -> arg.getDistance().isDummy(), new TranslatableText("argument.entity.options.distance.description"));
         putOption("level", arg -> {
            int i = arg.getReader().getCursor();
            NumberRange.IntRange lv = NumberRange.IntRange.parse(arg.getReader());
            if ((lv.getMin() == null || (Integer)lv.getMin() >= 0) && (lv.getMax() == null || (Integer)lv.getMax() >= 0)) {
               arg.setLevelRange(lv);
               arg.setIncludesNonPlayers(false);
            } else {
               arg.getReader().setCursor(i);
               throw NEGATIVE_LEVEL_EXCEPTION.createWithContext(arg.getReader());
            }
         }, arg -> arg.getLevelRange().isDummy(), new TranslatableText("argument.entity.options.level.description"));
         putOption("x", arg -> {
            arg.setLocalWorldOnly();
            arg.setX(arg.getReader().readDouble());
         }, arg -> arg.getX() == null, new TranslatableText("argument.entity.options.x.description"));
         putOption("y", arg -> {
            arg.setLocalWorldOnly();
            arg.setY(arg.getReader().readDouble());
         }, arg -> arg.getY() == null, new TranslatableText("argument.entity.options.y.description"));
         putOption("z", arg -> {
            arg.setLocalWorldOnly();
            arg.setZ(arg.getReader().readDouble());
         }, arg -> arg.getZ() == null, new TranslatableText("argument.entity.options.z.description"));
         putOption("dx", arg -> {
            arg.setLocalWorldOnly();
            arg.setDx(arg.getReader().readDouble());
         }, arg -> arg.getDx() == null, new TranslatableText("argument.entity.options.dx.description"));
         putOption("dy", arg -> {
            arg.setLocalWorldOnly();
            arg.setDy(arg.getReader().readDouble());
         }, arg -> arg.getDy() == null, new TranslatableText("argument.entity.options.dy.description"));
         putOption("dz", arg -> {
            arg.setLocalWorldOnly();
            arg.setDz(arg.getReader().readDouble());
         }, arg -> arg.getDz() == null, new TranslatableText("argument.entity.options.dz.description"));
         putOption(
            "x_rotation",
            arg -> arg.setPitchRange(FloatRangeArgument.parse(arg.getReader(), true, MathHelper::wrapDegrees)),
            arg -> arg.getPitchRange() == FloatRangeArgument.ANY,
            new TranslatableText("argument.entity.options.x_rotation.description")
         );
         putOption(
            "y_rotation",
            arg -> arg.setYawRange(FloatRangeArgument.parse(arg.getReader(), true, MathHelper::wrapDegrees)),
            arg -> arg.getYawRange() == FloatRangeArgument.ANY,
            new TranslatableText("argument.entity.options.y_rotation.description")
         );
         putOption("limit", arg -> {
            int i = arg.getReader().getCursor();
            int j = arg.getReader().readInt();
            if (j < 1) {
               arg.getReader().setCursor(i);
               throw TOO_SMALL_LEVEL_EXCEPTION.createWithContext(arg.getReader());
            } else {
               arg.setLimit(j);
               arg.setHasLimit(true);
            }
         }, arg -> !arg.isSenderOnly() && !arg.hasLimit(), new TranslatableText("argument.entity.options.limit.description"));
         putOption(
            "sort",
            arg -> {
               int i = arg.getReader().getCursor();
               String string = arg.getReader().readUnquotedString();
               arg.setSuggestionProvider(
                  (suggestionsBuilder, consumer) -> CommandSource.suggestMatching(
                        Arrays.asList("nearest", "furthest", "random", "arbitrary"), suggestionsBuilder
                     )
               );
               BiConsumer<Vec3d, List<? extends Entity>> biConsumer;
               switch (string) {
                  case "nearest":
                     biConsumer = EntitySelectorReader.NEAREST;
                     break;
                  case "furthest":
                     biConsumer = EntitySelectorReader.FURTHEST;
                     break;
                  case "random":
                     biConsumer = EntitySelectorReader.RANDOM;
                     break;
                  case "arbitrary":
                     biConsumer = EntitySelectorReader.ARBITRARY;
                     break;
                  default:
                     arg.getReader().setCursor(i);
                     throw IRREVERSIBLE_SORT_EXCEPTION.createWithContext(arg.getReader(), string);
               }

               arg.setSorter(biConsumer);
               arg.setHasSorter(true);
            },
            arg -> !arg.isSenderOnly() && !arg.hasSorter(),
            new TranslatableText("argument.entity.options.sort.description")
         );
         putOption("gamemode", arg -> {
            arg.setSuggestionProvider((suggestionsBuilder, consumer) -> {
               String stringx = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
               boolean blx = !arg.excludesGameMode();
               boolean bl2 = true;
               if (!stringx.isEmpty()) {
                  if (stringx.charAt(0) == '!') {
                     blx = false;
                     stringx = stringx.substring(1);
                  } else {
                     bl2 = false;
                  }
               }

               for (GameMode lvx : GameMode.values()) {
                  if (lvx != GameMode.NOT_SET && lvx.getName().toLowerCase(Locale.ROOT).startsWith(stringx)) {
                     if (bl2) {
                        suggestionsBuilder.suggest('!' + lvx.getName());
                     }

                     if (blx) {
                        suggestionsBuilder.suggest(lvx.getName());
                     }
                  }
               }

               return suggestionsBuilder.buildFuture();
            });
            int i = arg.getReader().getCursor();
            boolean bl = arg.readNegationCharacter();
            if (arg.excludesGameMode() && !bl) {
               arg.getReader().setCursor(i);
               throw INAPPLICABLE_OPTION_EXCEPTION.createWithContext(arg.getReader(), "gamemode");
            } else {
               String string = arg.getReader().readUnquotedString();
               GameMode lv = GameMode.byName(string, GameMode.NOT_SET);
               if (lv == GameMode.NOT_SET) {
                  arg.getReader().setCursor(i);
                  throw INVALID_MODE_EXCEPTION.createWithContext(arg.getReader(), string);
               } else {
                  arg.setIncludesNonPlayers(false);
                  arg.setPredicate(arg2 -> {
                     if (!(arg2 instanceof ServerPlayerEntity)) {
                        return false;
                     } else {
                        GameMode lvx = ((ServerPlayerEntity)arg2).interactionManager.getGameMode();
                        return bl ? lvx != lv : lvx == lv;
                     }
                  });
                  if (bl) {
                     arg.setHasNegatedGameMode(true);
                  } else {
                     arg.setSelectsGameMode(true);
                  }
               }
            }
         }, arg -> !arg.selectsGameMode(), new TranslatableText("argument.entity.options.gamemode.description"));
         putOption("team", arg -> {
            boolean bl = arg.readNegationCharacter();
            String string = arg.getReader().readUnquotedString();
            arg.setPredicate(argx -> {
               if (!(argx instanceof LivingEntity)) {
                  return false;
               } else {
                  AbstractTeam lv = argx.getScoreboardTeam();
                  String string2 = lv == null ? "" : lv.getName();
                  return string2.equals(string) != bl;
               }
            });
            if (bl) {
               arg.setExcludesTeam(true);
            } else {
               arg.setSelectsTeam(true);
            }
         }, arg -> !arg.selectsTeam(), new TranslatableText("argument.entity.options.team.description"));
         putOption("type", arg -> {
            arg.setSuggestionProvider((suggestionsBuilder, consumer) -> {
               CommandSource.suggestIdentifiers(Registry.ENTITY_TYPE.getIds(), suggestionsBuilder, String.valueOf('!'));
               CommandSource.suggestIdentifiers(EntityTypeTags.getTagGroup().getTagIds(), suggestionsBuilder, "!#");
               if (!arg.excludesEntityType()) {
                  CommandSource.suggestIdentifiers(Registry.ENTITY_TYPE.getIds(), suggestionsBuilder);
                  CommandSource.suggestIdentifiers(EntityTypeTags.getTagGroup().getTagIds(), suggestionsBuilder, String.valueOf('#'));
               }

               return suggestionsBuilder.buildFuture();
            });
            int i = arg.getReader().getCursor();
            boolean bl = arg.readNegationCharacter();
            if (arg.excludesEntityType() && !bl) {
               arg.getReader().setCursor(i);
               throw INAPPLICABLE_OPTION_EXCEPTION.createWithContext(arg.getReader(), "type");
            } else {
               if (bl) {
                  arg.setExcludesEntityType();
               }

               if (arg.readTagCharacter()) {
                  Identifier lv = Identifier.fromCommandInput(arg.getReader());
                  arg.setPredicate(arg2 -> arg2.getServer().getTagManager().getEntityTypes().getTagOrEmpty(lv).contains(arg2.getType()) != bl);
               } else {
                  Identifier lv2 = Identifier.fromCommandInput(arg.getReader());
                  EntityType<?> lv3 = Registry.ENTITY_TYPE.getOrEmpty(lv2).orElseThrow(() -> {
                     arg.getReader().setCursor(i);
                     return INVALID_TYPE_EXCEPTION.createWithContext(arg.getReader(), lv2.toString());
                  });
                  if (Objects.equals(EntityType.PLAYER, lv3) && !bl) {
                     arg.setIncludesNonPlayers(false);
                  }

                  arg.setPredicate(arg2 -> Objects.equals(lv3, arg2.getType()) != bl);
                  if (!bl) {
                     arg.setEntityType(lv3);
                  }
               }
            }
         }, arg -> !arg.selectsEntityType(), new TranslatableText("argument.entity.options.type.description"));
         putOption("tag", arg -> {
            boolean bl = arg.readNegationCharacter();
            String string = arg.getReader().readUnquotedString();
            arg.setPredicate(argx -> "".equals(string) ? argx.getScoreboardTags().isEmpty() != bl : argx.getScoreboardTags().contains(string) != bl);
         }, arg -> true, new TranslatableText("argument.entity.options.tag.description"));
         putOption("nbt", arg -> {
            boolean bl = arg.readNegationCharacter();
            CompoundTag lv = new StringNbtReader(arg.getReader()).parseCompoundTag();
            arg.setPredicate(arg2 -> {
               CompoundTag lvx = arg2.toTag(new CompoundTag());
               if (arg2 instanceof ServerPlayerEntity) {
                  ItemStack lv2 = ((ServerPlayerEntity)arg2).inventory.getMainHandStack();
                  if (!lv2.isEmpty()) {
                     lvx.put("SelectedItem", lv2.toTag(new CompoundTag()));
                  }
               }

               return NbtHelper.matches(lv, lvx, true) != bl;
            });
         }, arg -> true, new TranslatableText("argument.entity.options.nbt.description"));
         putOption("scores", arg -> {
            StringReader stringReader = arg.getReader();
            Map<String, NumberRange.IntRange> map = Maps.newHashMap();
            stringReader.expect('{');
            stringReader.skipWhitespace();

            while (stringReader.canRead() && stringReader.peek() != '}') {
               stringReader.skipWhitespace();
               String string = stringReader.readUnquotedString();
               stringReader.skipWhitespace();
               stringReader.expect('=');
               stringReader.skipWhitespace();
               NumberRange.IntRange lv = NumberRange.IntRange.parse(stringReader);
               map.put(string, lv);
               stringReader.skipWhitespace();
               if (stringReader.canRead() && stringReader.peek() == ',') {
                  stringReader.skip();
               }
            }

            stringReader.expect('}');
            if (!map.isEmpty()) {
               arg.setPredicate(argx -> {
                  Scoreboard lvx = argx.getServer().getScoreboard();
                  String stringx = argx.getEntityName();

                  for (Entry<String, NumberRange.IntRange> entry : map.entrySet()) {
                     ScoreboardObjective lv2 = lvx.getNullableObjective(entry.getKey());
                     if (lv2 == null) {
                        return false;
                     }

                     if (!lvx.playerHasObjective(stringx, lv2)) {
                        return false;
                     }

                     ScoreboardPlayerScore lv3 = lvx.getPlayerScore(stringx, lv2);
                     int i = lv3.getScore();
                     if (!entry.getValue().test(i)) {
                        return false;
                     }
                  }

                  return true;
               });
            }

            arg.setSelectsScores(true);
         }, arg -> !arg.selectsScores(), new TranslatableText("argument.entity.options.scores.description"));
         putOption("advancements", arg -> {
            StringReader stringReader = arg.getReader();
            Map<Identifier, Predicate<AdvancementProgress>> map = Maps.newHashMap();
            stringReader.expect('{');
            stringReader.skipWhitespace();

            while (stringReader.canRead() && stringReader.peek() != '}') {
               stringReader.skipWhitespace();
               Identifier lv = Identifier.fromCommandInput(stringReader);
               stringReader.skipWhitespace();
               stringReader.expect('=');
               stringReader.skipWhitespace();
               if (stringReader.canRead() && stringReader.peek() == '{') {
                  Map<String, Predicate<CriterionProgress>> map2 = Maps.newHashMap();
                  stringReader.skipWhitespace();
                  stringReader.expect('{');
                  stringReader.skipWhitespace();

                  while (stringReader.canRead() && stringReader.peek() != '}') {
                     stringReader.skipWhitespace();
                     String string = stringReader.readUnquotedString();
                     stringReader.skipWhitespace();
                     stringReader.expect('=');
                     stringReader.skipWhitespace();
                     boolean bl = stringReader.readBoolean();
                     map2.put(string, argx -> argx.isObtained() == bl);
                     stringReader.skipWhitespace();
                     if (stringReader.canRead() && stringReader.peek() == ',') {
                        stringReader.skip();
                     }
                  }

                  stringReader.skipWhitespace();
                  stringReader.expect('}');
                  stringReader.skipWhitespace();
                  map.put(lv, argx -> {
                     for (Entry<String, Predicate<CriterionProgress>> entry : map2.entrySet()) {
                        CriterionProgress lvx = argx.getCriterionProgress(entry.getKey());
                        if (lvx == null || !entry.getValue().test(lvx)) {
                           return false;
                        }
                     }

                     return true;
                  });
               } else {
                  boolean bl2 = stringReader.readBoolean();
                  map.put(lv, argx -> argx.isDone() == bl2);
               }

               stringReader.skipWhitespace();
               if (stringReader.canRead() && stringReader.peek() == ',') {
                  stringReader.skip();
               }
            }

            stringReader.expect('}');
            if (!map.isEmpty()) {
               arg.setPredicate(argx -> {
                  if (!(argx instanceof ServerPlayerEntity)) {
                     return false;
                  } else {
                     ServerPlayerEntity lvx = (ServerPlayerEntity)argx;
                     PlayerAdvancementTracker lv2 = lvx.getAdvancementTracker();
                     ServerAdvancementLoader lv3 = lvx.getServer().getAdvancementLoader();

                     for (Entry<Identifier, Predicate<AdvancementProgress>> entry : map.entrySet()) {
                        Advancement lv4 = lv3.get(entry.getKey());
                        if (lv4 == null || !entry.getValue().test(lv2.getProgress(lv4))) {
                           return false;
                        }
                     }

                     return true;
                  }
               });
               arg.setIncludesNonPlayers(false);
            }

            arg.setSelectsAdvancements(true);
         }, arg -> !arg.selectsAdvancements(), new TranslatableText("argument.entity.options.advancements.description"));
         putOption(
            "predicate",
            arg -> {
               boolean bl = arg.readNegationCharacter();
               Identifier lv = Identifier.fromCommandInput(arg.getReader());
               arg.setPredicate(
                  arg2 -> {
                     if (!(arg2.world instanceof ServerWorld)) {
                        return false;
                     } else {
                        ServerWorld lvx = (ServerWorld)arg2.world;
                        LootCondition lv2 = lvx.getServer().getPredicateManager().get(lv);
                        if (lv2 == null) {
                           return false;
                        } else {
                           LootContext lv3 = new LootContext.Builder(lvx)
                              .parameter(LootContextParameters.THIS_ENTITY, arg2)
                              .parameter(LootContextParameters.ORIGIN, arg2.getPos())
                              .build(LootContextTypes.SELECTOR);
                           return bl ^ lv2.test(lv3);
                        }
                     }
                  }
               );
            },
            arg -> true,
            new TranslatableText("argument.entity.options.predicate.description")
         );
      }
   }

   public static EntitySelectorOptions.SelectorHandler getHandler(EntitySelectorReader reader, String option, int restoreCursor) throws CommandSyntaxException {
      EntitySelectorOptions.SelectorOption lv = options.get(option);
      if (lv != null) {
         if (lv.condition.test(reader)) {
            return lv.handler;
         } else {
            throw INAPPLICABLE_OPTION_EXCEPTION.createWithContext(reader.getReader(), option);
         }
      } else {
         reader.getReader().setCursor(restoreCursor);
         throw UNKNOWN_OPTION_EXCEPTION.createWithContext(reader.getReader(), option);
      }
   }

   public static void suggestOptions(EntitySelectorReader reader, SuggestionsBuilder suggestionBuilder) {
      String string = suggestionBuilder.getRemaining().toLowerCase(Locale.ROOT);

      for (Entry<String, EntitySelectorOptions.SelectorOption> entry : options.entrySet()) {
         if (entry.getValue().condition.test(reader) && entry.getKey().toLowerCase(Locale.ROOT).startsWith(string)) {
            suggestionBuilder.suggest(entry.getKey() + '=', entry.getValue().description);
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
