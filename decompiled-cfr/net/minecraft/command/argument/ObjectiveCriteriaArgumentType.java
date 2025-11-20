/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  com.mojang.brigadier.exceptions.DynamicCommandExceptionType
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 */
package net.minecraft.command.argument;

import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatType;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;

public class ObjectiveCriteriaArgumentType
implements ArgumentType<ScoreboardCriterion> {
    private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo.bar.baz", "minecraft:foo");
    public static final DynamicCommandExceptionType INVALID_CRITERIA_EXCEPTION = new DynamicCommandExceptionType(object -> new TranslatableText("argument.criteria.invalid", object));

    private ObjectiveCriteriaArgumentType() {
    }

    public static ObjectiveCriteriaArgumentType objectiveCriteria() {
        return new ObjectiveCriteriaArgumentType();
    }

    public static ScoreboardCriterion getCriteria(CommandContext<ServerCommandSource> commandContext, String string) {
        return (ScoreboardCriterion)commandContext.getArgument(string, ScoreboardCriterion.class);
    }

    public ScoreboardCriterion parse(StringReader stringReader) throws CommandSyntaxException {
        int n = stringReader.getCursor();
        while (stringReader.canRead() && stringReader.peek() != ' ') {
            stringReader.skip();
        }
        String _snowman2 = stringReader.getString().substring(n, stringReader.getCursor());
        return ScoreboardCriterion.createStatCriterion(_snowman2).orElseThrow(() -> {
            stringReader.setCursor(n);
            return INVALID_CRITERIA_EXCEPTION.create((Object)_snowman2);
        });
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        ArrayList arrayList = Lists.newArrayList(ScoreboardCriterion.OBJECTIVES.keySet());
        for (StatType statType : Registry.STAT_TYPE) {
            for (Object e : statType.getRegistry()) {
                String string = this.getStatName(statType, e);
                arrayList.add(string);
            }
        }
        return CommandSource.suggestMatching(arrayList, builder);
    }

    public <T> String getStatName(StatType<T> stat, Object value) {
        return Stat.getName(stat, value);
    }

    public Collection<String> getExamples() {
        return EXAMPLES;
    }

    public /* synthetic */ Object parse(StringReader stringReader) throws CommandSyntaxException {
        return this.parse(stringReader);
    }
}

