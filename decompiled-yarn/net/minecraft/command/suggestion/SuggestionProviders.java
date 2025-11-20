package net.minecraft.command.suggestion;

import com.google.common.collect.Maps;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.EntityType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class SuggestionProviders {
   private static final Map<Identifier, SuggestionProvider<CommandSource>> REGISTRY = Maps.newHashMap();
   private static final Identifier ASK_SERVER_NAME = new Identifier("ask_server");
   public static final SuggestionProvider<CommandSource> ASK_SERVER = register(ASK_SERVER_NAME, (_snowman, _snowmanx) -> ((CommandSource)_snowman.getSource()).getCompletions(_snowman, _snowmanx));
   public static final SuggestionProvider<ServerCommandSource> ALL_RECIPES = register(
      new Identifier("all_recipes"), (_snowman, _snowmanx) -> CommandSource.suggestIdentifiers(((CommandSource)_snowman.getSource()).getRecipeIds(), _snowmanx)
   );
   public static final SuggestionProvider<ServerCommandSource> AVAILABLE_SOUNDS = register(
      new Identifier("available_sounds"), (_snowman, _snowmanx) -> CommandSource.suggestIdentifiers(((CommandSource)_snowman.getSource()).getSoundIds(), _snowmanx)
   );
   public static final SuggestionProvider<ServerCommandSource> ALL_BIOMES = register(
      new Identifier("available_biomes"),
      (_snowman, _snowmanx) -> CommandSource.suggestIdentifiers(((CommandSource)_snowman.getSource()).getRegistryManager().get(Registry.BIOME_KEY).getIds(), _snowmanx)
   );
   public static final SuggestionProvider<ServerCommandSource> SUMMONABLE_ENTITIES = register(
      new Identifier("summonable_entities"),
      (_snowman, _snowmanx) -> CommandSource.suggestFromIdentifier(
            Registry.ENTITY_TYPE.stream().filter(EntityType::isSummonable),
            _snowmanx,
            EntityType::getId,
            _snowmanxx -> new TranslatableText(Util.createTranslationKey("entity", EntityType.getId(_snowmanxx)))
         )
   );

   public static <S extends CommandSource> SuggestionProvider<S> register(Identifier name, SuggestionProvider<CommandSource> provider) {
      if (REGISTRY.containsKey(name)) {
         throw new IllegalArgumentException("A command suggestion provider is already registered with the name " + name);
      } else {
         REGISTRY.put(name, provider);
         return new SuggestionProviders.LocalProvider(name, provider);
      }
   }

   public static SuggestionProvider<CommandSource> byId(Identifier id) {
      return REGISTRY.getOrDefault(id, ASK_SERVER);
   }

   public static Identifier computeName(SuggestionProvider<CommandSource> provider) {
      return provider instanceof SuggestionProviders.LocalProvider ? ((SuggestionProviders.LocalProvider)provider).name : ASK_SERVER_NAME;
   }

   public static SuggestionProvider<CommandSource> getLocalProvider(SuggestionProvider<CommandSource> provider) {
      return provider instanceof SuggestionProviders.LocalProvider ? provider : ASK_SERVER;
   }

   public static class LocalProvider implements SuggestionProvider<CommandSource> {
      private final SuggestionProvider<CommandSource> provider;
      private final Identifier name;

      public LocalProvider(Identifier name, SuggestionProvider<CommandSource> _snowman) {
         this.provider = _snowman;
         this.name = name;
      }

      public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSource> _snowman, SuggestionsBuilder _snowman) throws CommandSyntaxException {
         return this.provider.getSuggestions(_snowman, _snowman);
      }
   }
}
