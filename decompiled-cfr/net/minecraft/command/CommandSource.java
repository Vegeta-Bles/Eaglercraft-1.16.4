/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.context.CommandContext
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.suggestion.SuggestionsBuilder
 */
package net.minecraft.command;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public interface CommandSource {
    public Collection<String> getPlayerNames();

    default public Collection<String> getEntitySuggestions() {
        return Collections.emptyList();
    }

    public Collection<String> getTeamNames();

    public Collection<Identifier> getSoundIds();

    public Stream<Identifier> getRecipeIds();

    public CompletableFuture<Suggestions> getCompletions(CommandContext<CommandSource> var1, SuggestionsBuilder var2);

    default public Collection<RelativePosition> getBlockPositionSuggestions() {
        return Collections.singleton(RelativePosition.ZERO_WORLD);
    }

    default public Collection<RelativePosition> getPositionSuggestions() {
        return Collections.singleton(RelativePosition.ZERO_WORLD);
    }

    public Set<RegistryKey<World>> getWorldKeys();

    public DynamicRegistryManager getRegistryManager();

    public boolean hasPermissionLevel(int var1);

    public static <T> void forEachMatching(Iterable<T> candidates, String string2, Function<T, Identifier> identifier, Consumer<T> action) {
        boolean bl = string2.indexOf(58) > -1;
        for (T t : candidates) {
            String string2;
            Identifier identifier2 = identifier.apply(t);
            if (bl) {
                String string3 = identifier2.toString();
                if (!CommandSource.method_27136(string2, string3)) continue;
                action.accept(t);
                continue;
            }
            if (!CommandSource.method_27136(string2, identifier2.getNamespace()) && (!identifier2.getNamespace().equals("minecraft") || !CommandSource.method_27136(string2, identifier2.getPath()))) continue;
            action.accept(t);
        }
    }

    public static <T> void forEachMatching(Iterable<T> candidates, String string, String string2, Function<T, Identifier> identifier, Consumer<T> action) {
        if (string.isEmpty()) {
            candidates.forEach(action);
        } else {
            _snowman = Strings.commonPrefix((CharSequence)string, (CharSequence)string2);
            if (!_snowman.isEmpty()) {
                _snowman = string.substring(_snowman.length());
                CommandSource.forEachMatching(candidates, _snowman, identifier, action);
            }
        }
    }

    public static CompletableFuture<Suggestions> suggestIdentifiers(Iterable<Identifier> candidates, SuggestionsBuilder builder, String string) {
        _snowman = builder.getRemaining().toLowerCase(Locale.ROOT);
        CommandSource.forEachMatching(candidates, _snowman, string, identifier -> identifier, identifier -> builder.suggest(string + identifier));
        return builder.buildFuture();
    }

    public static CompletableFuture<Suggestions> suggestIdentifiers(Iterable<Identifier> candidates, SuggestionsBuilder builder) {
        String string = builder.getRemaining().toLowerCase(Locale.ROOT);
        CommandSource.forEachMatching(candidates, string, identifier -> identifier, identifier -> builder.suggest(identifier.toString()));
        return builder.buildFuture();
    }

    public static <T> CompletableFuture<Suggestions> suggestFromIdentifier(Iterable<T> candidates, SuggestionsBuilder builder, Function<T, Identifier> identifier, Function<T, Message> tooltip) {
        String string = builder.getRemaining().toLowerCase(Locale.ROOT);
        CommandSource.forEachMatching(candidates, string, identifier, object -> builder.suggest(((Identifier)identifier.apply(object)).toString(), (Message)tooltip.apply(object)));
        return builder.buildFuture();
    }

    public static CompletableFuture<Suggestions> suggestIdentifiers(Stream<Identifier> stream, SuggestionsBuilder builder) {
        return CommandSource.suggestIdentifiers(stream::iterator, builder);
    }

    public static <T> CompletableFuture<Suggestions> suggestFromIdentifier(Stream<T> candidates, SuggestionsBuilder builder, Function<T, Identifier> identifier, Function<T, Message> tooltip) {
        return CommandSource.suggestFromIdentifier(candidates::iterator, builder, identifier, tooltip);
    }

    public static CompletableFuture<Suggestions> suggestPositions(String string2, Collection<RelativePosition> candidates, SuggestionsBuilder builder, Predicate<String> predicate) {
        ArrayList arrayList;
        block4: {
            String[] _snowman2;
            block5: {
                String string2;
                block3: {
                    arrayList = Lists.newArrayList();
                    if (!Strings.isNullOrEmpty((String)string2)) break block3;
                    for (RelativePosition relativePosition : candidates) {
                        String string3 = relativePosition.x + " " + relativePosition.y + " " + relativePosition.z;
                        if (!predicate.test(string3)) continue;
                        arrayList.add(relativePosition.x);
                        arrayList.add(relativePosition.x + " " + relativePosition.y);
                        arrayList.add(string3);
                    }
                    break block4;
                }
                _snowman2 = string2.split(" ");
                if (_snowman2.length != 1) break block5;
                for (RelativePosition relativePosition : candidates) {
                    String string4 = _snowman2[0] + " " + relativePosition.y + " " + relativePosition.z;
                    if (!predicate.test(string4)) continue;
                    arrayList.add(_snowman2[0] + " " + relativePosition.y);
                    arrayList.add(string4);
                }
                break block4;
            }
            if (_snowman2.length != 2) break block4;
            for (RelativePosition relativePosition : candidates) {
                String string5 = _snowman2[0] + " " + _snowman2[1] + " " + relativePosition.z;
                if (!predicate.test(string5)) continue;
                arrayList.add(string5);
            }
        }
        return CommandSource.suggestMatching(arrayList, builder);
    }

    public static CompletableFuture<Suggestions> suggestColumnPositions(String string2, Collection<RelativePosition> collection, SuggestionsBuilder suggestionsBuilder, Predicate<String> predicate) {
        ArrayList arrayList;
        block3: {
            String string2;
            block2: {
                arrayList = Lists.newArrayList();
                if (!Strings.isNullOrEmpty((String)string2)) break block2;
                for (RelativePosition relativePosition : collection) {
                    String string3 = relativePosition.x + " " + relativePosition.z;
                    if (!predicate.test(string3)) continue;
                    arrayList.add(relativePosition.x);
                    arrayList.add(string3);
                }
                break block3;
            }
            String[] _snowman2 = string2.split(" ");
            if (_snowman2.length != 1) break block3;
            for (RelativePosition relativePosition : collection) {
                String string4 = _snowman2[0] + " " + relativePosition.z;
                if (!predicate.test(string4)) continue;
                arrayList.add(string4);
            }
        }
        return CommandSource.suggestMatching(arrayList, suggestionsBuilder);
    }

    public static CompletableFuture<Suggestions> suggestMatching(Iterable<String> iterable, SuggestionsBuilder suggestionsBuilder) {
        String string = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
        for (String string2 : iterable) {
            if (!CommandSource.method_27136(string, string2.toLowerCase(Locale.ROOT))) continue;
            suggestionsBuilder.suggest(string2);
        }
        return suggestionsBuilder.buildFuture();
    }

    public static CompletableFuture<Suggestions> suggestMatching(Stream<String> stream, SuggestionsBuilder suggestionsBuilder) {
        String string = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
        stream.filter(string2 -> CommandSource.method_27136(string, string2.toLowerCase(Locale.ROOT))).forEach(arg_0 -> ((SuggestionsBuilder)suggestionsBuilder).suggest(arg_0));
        return suggestionsBuilder.buildFuture();
    }

    public static CompletableFuture<Suggestions> suggestMatching(String[] stringArray, SuggestionsBuilder suggestionsBuilder) {
        String string = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
        for (String string2 : stringArray) {
            if (!CommandSource.method_27136(string, string2.toLowerCase(Locale.ROOT))) continue;
            suggestionsBuilder.suggest(string2);
        }
        return suggestionsBuilder.buildFuture();
    }

    public static boolean method_27136(String string, String string2) {
        int n = 0;
        while (!string2.startsWith(string, n)) {
            if ((n = string2.indexOf(95, n)) < 0) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public static class RelativePosition {
        public static final RelativePosition ZERO_LOCAL = new RelativePosition("^", "^", "^");
        public static final RelativePosition ZERO_WORLD = new RelativePosition("~", "~", "~");
        public final String x;
        public final String y;
        public final String z;

        public RelativePosition(String x, String y, String z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}

