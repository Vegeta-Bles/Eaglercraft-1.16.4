package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.tag.ItemTags;

public class ItemStackArgumentType implements ArgumentType<ItemStackArgument> {
   private static final Collection<String> EXAMPLES = Arrays.asList("stick", "minecraft:stick", "stick{foo=bar}");

   public ItemStackArgumentType() {
   }

   public static ItemStackArgumentType itemStack() {
      return new ItemStackArgumentType();
   }

   public ItemStackArgument parse(StringReader _snowman) throws CommandSyntaxException {
      ItemStringReader _snowmanx = new ItemStringReader(_snowman, false).consume();
      return new ItemStackArgument(_snowmanx.getItem(), _snowmanx.getTag());
   }

   public static <S> ItemStackArgument getItemStackArgument(CommandContext<S> context, String name) {
      return (ItemStackArgument)context.getArgument(name, ItemStackArgument.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      StringReader _snowman = new StringReader(builder.getInput());
      _snowman.setCursor(builder.getStart());
      ItemStringReader _snowmanx = new ItemStringReader(_snowman, false);

      try {
         _snowmanx.consume();
      } catch (CommandSyntaxException var6) {
      }

      return _snowmanx.getSuggestions(builder, ItemTags.getTagGroup());
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
