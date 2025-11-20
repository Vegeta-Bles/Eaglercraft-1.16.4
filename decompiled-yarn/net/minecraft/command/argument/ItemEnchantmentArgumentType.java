package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemEnchantmentArgumentType implements ArgumentType<Enchantment> {
   private static final Collection<String> EXAMPLES = Arrays.asList("unbreaking", "silk_touch");
   public static final DynamicCommandExceptionType UNKNOWN_ENCHANTMENT_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("enchantment.unknown", _snowman)
   );

   public ItemEnchantmentArgumentType() {
   }

   public static ItemEnchantmentArgumentType itemEnchantment() {
      return new ItemEnchantmentArgumentType();
   }

   public static Enchantment getEnchantment(CommandContext<ServerCommandSource> context, String name) {
      return (Enchantment)context.getArgument(name, Enchantment.class);
   }

   public Enchantment parse(StringReader _snowman) throws CommandSyntaxException {
      Identifier _snowmanx = Identifier.fromCommandInput(_snowman);
      return Registry.ENCHANTMENT.getOrEmpty(_snowmanx).orElseThrow(() -> UNKNOWN_ENCHANTMENT_EXCEPTION.create(_snowman));
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      return CommandSource.suggestIdentifiers(Registry.ENCHANTMENT.getIds(), builder);
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
