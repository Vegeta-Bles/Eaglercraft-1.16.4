package net.minecraft.command;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
   Collection<String> getPlayerNames();

   default Collection<String> getEntitySuggestions() {
      return Collections.emptyList();
   }

   Collection<String> getTeamNames();

   Collection<Identifier> getSoundIds();

   Stream<Identifier> getRecipeIds();

   CompletableFuture<Suggestions> getCompletions(CommandContext<CommandSource> context, SuggestionsBuilder builder);

   default Collection<CommandSource.RelativePosition> getBlockPositionSuggestions() {
      return Collections.singleton(CommandSource.RelativePosition.ZERO_WORLD);
   }

   default Collection<CommandSource.RelativePosition> getPositionSuggestions() {
      return Collections.singleton(CommandSource.RelativePosition.ZERO_WORLD);
   }

   Set<RegistryKey<World>> getWorldKeys();

   DynamicRegistryManager getRegistryManager();

   boolean hasPermissionLevel(int level);

   static <T> void forEachMatching(Iterable<T> candidates, String _snowman, Function<T, Identifier> identifier, Consumer<T> action) {
      boolean _snowmanx = _snowman.indexOf(58) > -1;

      for (T _snowmanxx : candidates) {
         Identifier _snowmanxxx = identifier.apply(_snowmanxx);
         if (_snowmanx) {
            String _snowmanxxxx = _snowmanxxx.toString();
            if (method_27136(_snowman, _snowmanxxxx)) {
               action.accept(_snowmanxx);
            }
         } else if (method_27136(_snowman, _snowmanxxx.getNamespace()) || _snowmanxxx.getNamespace().equals("minecraft") && method_27136(_snowman, _snowmanxxx.getPath())) {
            action.accept(_snowmanxx);
         }
      }
   }

   static <T> void forEachMatching(Iterable<T> candidates, String _snowman, String _snowman, Function<T, Identifier> identifier, Consumer<T> action) {
      if (_snowman.isEmpty()) {
         candidates.forEach(action);
      } else {
         String _snowmanxx = Strings.commonPrefix(_snowman, _snowman);
         if (!_snowmanxx.isEmpty()) {
            String _snowmanxxx = _snowman.substring(_snowmanxx.length());
            forEachMatching(candidates, _snowmanxxx, identifier, action);
         }
      }
   }

   static CompletableFuture<Suggestions> suggestIdentifiers(Iterable<Identifier> candidates, SuggestionsBuilder builder, String _snowman) {
      String _snowmanx = builder.getRemaining().toLowerCase(Locale.ROOT);
      forEachMatching(candidates, _snowmanx, _snowman, _snowmanxx -> _snowmanxx, _snowmanxx -> builder.suggest(_snowman + _snowmanxx));
      return builder.buildFuture();
   }

   static CompletableFuture<Suggestions> suggestIdentifiers(Iterable<Identifier> candidates, SuggestionsBuilder builder) {
      String _snowman = builder.getRemaining().toLowerCase(Locale.ROOT);
      forEachMatching(candidates, _snowman, _snowmanx -> _snowmanx, _snowmanx -> builder.suggest(_snowmanx.toString()));
      return builder.buildFuture();
   }

   static <T> CompletableFuture<Suggestions> suggestFromIdentifier(
      Iterable<T> candidates, SuggestionsBuilder builder, Function<T, Identifier> identifier, Function<T, Message> tooltip
   ) {
      String _snowman = builder.getRemaining().toLowerCase(Locale.ROOT);
      forEachMatching(candidates, _snowman, identifier, _snowmanxxx -> builder.suggest(identifier.apply(_snowmanxxx).toString(), tooltip.apply(_snowmanxxx)));
      return builder.buildFuture();
   }

   static CompletableFuture<Suggestions> suggestIdentifiers(Stream<Identifier> _snowman, SuggestionsBuilder builder) {
      return suggestIdentifiers(_snowman::iterator, builder);
   }

   static <T> CompletableFuture<Suggestions> suggestFromIdentifier(
      Stream<T> candidates, SuggestionsBuilder builder, Function<T, Identifier> identifier, Function<T, Message> tooltip
   ) {
      return suggestFromIdentifier(candidates::iterator, builder, identifier, tooltip);
   }

   static CompletableFuture<Suggestions> suggestPositions(
      String _snowman, Collection<CommandSource.RelativePosition> candidates, SuggestionsBuilder builder, Predicate<String> _snowman
   ) {
      List<String> _snowmanxx = Lists.newArrayList();
      if (Strings.isNullOrEmpty(_snowman)) {
         for (CommandSource.RelativePosition _snowmanxxx : candidates) {
            String _snowmanxxxx = _snowmanxxx.x + " " + _snowmanxxx.y + " " + _snowmanxxx.z;
            if (_snowman.test(_snowmanxxxx)) {
               _snowmanxx.add(_snowmanxxx.x);
               _snowmanxx.add(_snowmanxxx.x + " " + _snowmanxxx.y);
               _snowmanxx.add(_snowmanxxxx);
            }
         }
      } else {
         String[] _snowmanxxxx = _snowman.split(" ");
         if (_snowmanxxxx.length == 1) {
            for (CommandSource.RelativePosition _snowmanxxxxx : candidates) {
               String _snowmanxxxxxx = _snowmanxxxx[0] + " " + _snowmanxxxxx.y + " " + _snowmanxxxxx.z;
               if (_snowman.test(_snowmanxxxxxx)) {
                  _snowmanxx.add(_snowmanxxxx[0] + " " + _snowmanxxxxx.y);
                  _snowmanxx.add(_snowmanxxxxxx);
               }
            }
         } else if (_snowmanxxxx.length == 2) {
            for (CommandSource.RelativePosition _snowmanxxxxxx : candidates) {
               String _snowmanxxxxxxx = _snowmanxxxx[0] + " " + _snowmanxxxx[1] + " " + _snowmanxxxxxx.z;
               if (_snowman.test(_snowmanxxxxxxx)) {
                  _snowmanxx.add(_snowmanxxxxxxx);
               }
            }
         }
      }

      return suggestMatching(_snowmanxx, builder);
   }

   static CompletableFuture<Suggestions> suggestColumnPositions(
      String _snowman, Collection<CommandSource.RelativePosition> _snowman, SuggestionsBuilder _snowman, Predicate<String> _snowman
   ) {
      List<String> _snowmanxxxx = Lists.newArrayList();
      if (Strings.isNullOrEmpty(_snowman)) {
         for (CommandSource.RelativePosition _snowmanxxxxx : _snowman) {
            String _snowmanxxxxxx = _snowmanxxxxx.x + " " + _snowmanxxxxx.z;
            if (_snowman.test(_snowmanxxxxxx)) {
               _snowmanxxxx.add(_snowmanxxxxx.x);
               _snowmanxxxx.add(_snowmanxxxxxx);
            }
         }
      } else {
         String[] _snowmanxxxxxx = _snowman.split(" ");
         if (_snowmanxxxxxx.length == 1) {
            for (CommandSource.RelativePosition _snowmanxxxxxxx : _snowman) {
               String _snowmanxxxxxxxx = _snowmanxxxxxx[0] + " " + _snowmanxxxxxxx.z;
               if (_snowman.test(_snowmanxxxxxxxx)) {
                  _snowmanxxxx.add(_snowmanxxxxxxxx);
               }
            }
         }
      }

      return suggestMatching(_snowmanxxxx, _snowman);
   }

   static CompletableFuture<Suggestions> suggestMatching(Iterable<String> _snowman, SuggestionsBuilder _snowman) {
      String _snowmanxx = _snowman.getRemaining().toLowerCase(Locale.ROOT);

      for (String _snowmanxxx : _snowman) {
         if (method_27136(_snowmanxx, _snowmanxxx.toLowerCase(Locale.ROOT))) {
            _snowman.suggest(_snowmanxxx);
         }
      }

      return _snowman.buildFuture();
   }

   static CompletableFuture<Suggestions> suggestMatching(Stream<String> _snowman, SuggestionsBuilder _snowman) {
      String _snowmanxx = _snowman.getRemaining().toLowerCase(Locale.ROOT);
      _snowman.filter(_snowmanxxx -> method_27136(_snowman, _snowmanxxx.toLowerCase(Locale.ROOT))).forEach(_snowman::suggest);
      return _snowman.buildFuture();
   }

   static CompletableFuture<Suggestions> suggestMatching(String[] _snowman, SuggestionsBuilder _snowman) {
      String _snowmanxx = _snowman.getRemaining().toLowerCase(Locale.ROOT);

      for (String _snowmanxxx : _snowman) {
         if (method_27136(_snowmanxx, _snowmanxxx.toLowerCase(Locale.ROOT))) {
            _snowman.suggest(_snowmanxxx);
         }
      }

      return _snowman.buildFuture();
   }

   static boolean method_27136(String _snowman, String _snowman) {
      for (int _snowmanxx = 0; !_snowman.startsWith(_snowman, _snowmanxx); _snowmanxx++) {
         _snowmanxx = _snowman.indexOf(95, _snowmanxx);
         if (_snowmanxx < 0) {
            return false;
         }
      }

      return true;
   }

   public static class RelativePosition {
      public static final CommandSource.RelativePosition ZERO_LOCAL = new CommandSource.RelativePosition("^", "^", "^");
      public static final CommandSource.RelativePosition ZERO_WORLD = new CommandSource.RelativePosition("~", "~", "~");
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
