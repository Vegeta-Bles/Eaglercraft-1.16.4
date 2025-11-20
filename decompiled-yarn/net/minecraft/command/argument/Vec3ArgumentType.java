package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Vec3d;

public class Vec3ArgumentType implements ArgumentType<PosArgument> {
   private static final Collection<String> EXAMPLES = Arrays.asList("0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "0.1 -0.5 .9", "~0.5 ~1 ~-5");
   public static final SimpleCommandExceptionType INCOMPLETE_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("argument.pos3d.incomplete"));
   public static final SimpleCommandExceptionType MIXED_COORDINATE_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("argument.pos.mixed"));
   private final boolean centerIntegers;

   public Vec3ArgumentType(boolean centerIntegers) {
      this.centerIntegers = centerIntegers;
   }

   public static Vec3ArgumentType vec3() {
      return new Vec3ArgumentType(true);
   }

   public static Vec3ArgumentType vec3(boolean centerIntegers) {
      return new Vec3ArgumentType(centerIntegers);
   }

   public static Vec3d getVec3(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      return ((PosArgument)context.getArgument(name, PosArgument.class)).toAbsolutePos((ServerCommandSource)context.getSource());
   }

   public static PosArgument getPosArgument(CommandContext<ServerCommandSource> _snowman, String _snowman) {
      return (PosArgument)_snowman.getArgument(_snowman, PosArgument.class);
   }

   public PosArgument parse(StringReader _snowman) throws CommandSyntaxException {
      return (PosArgument)(_snowman.canRead() && _snowman.peek() == '^' ? LookingPosArgument.parse(_snowman) : DefaultPosArgument.parse(_snowman, this.centerIntegers));
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      if (!(context.getSource() instanceof CommandSource)) {
         return Suggestions.empty();
      } else {
         String _snowman = builder.getRemaining();
         Collection<CommandSource.RelativePosition> _snowmanx;
         if (!_snowman.isEmpty() && _snowman.charAt(0) == '^') {
            _snowmanx = Collections.singleton(CommandSource.RelativePosition.ZERO_LOCAL);
         } else {
            _snowmanx = ((CommandSource)context.getSource()).getPositionSuggestions();
         }

         return CommandSource.suggestPositions(_snowman, _snowmanx, builder, CommandManager.getCommandValidator(this::parse));
      }
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
