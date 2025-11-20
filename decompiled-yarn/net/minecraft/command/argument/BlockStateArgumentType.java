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
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.tag.BlockTags;

public class BlockStateArgumentType implements ArgumentType<BlockStateArgument> {
   private static final Collection<String> EXAMPLES = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "foo{bar=baz}");

   public BlockStateArgumentType() {
   }

   public static BlockStateArgumentType blockState() {
      return new BlockStateArgumentType();
   }

   public BlockStateArgument parse(StringReader _snowman) throws CommandSyntaxException {
      BlockArgumentParser _snowmanx = new BlockArgumentParser(_snowman, false).parse(true);
      return new BlockStateArgument(_snowmanx.getBlockState(), _snowmanx.getBlockProperties().keySet(), _snowmanx.getNbtData());
   }

   public static BlockStateArgument getBlockState(CommandContext<ServerCommandSource> context, String name) {
      return (BlockStateArgument)context.getArgument(name, BlockStateArgument.class);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      StringReader _snowman = new StringReader(builder.getInput());
      _snowman.setCursor(builder.getStart());
      BlockArgumentParser _snowmanx = new BlockArgumentParser(_snowman, false);

      try {
         _snowmanx.parse(true);
      } catch (CommandSyntaxException var6) {
      }

      return _snowmanx.getSuggestions(builder, BlockTags.getTagGroup());
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
