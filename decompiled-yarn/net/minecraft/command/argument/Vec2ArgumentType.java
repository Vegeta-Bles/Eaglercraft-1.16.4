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
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class Vec2ArgumentType implements ArgumentType<PosArgument> {
   private static final Collection<String> EXAMPLES = Arrays.asList("0 0", "~ ~", "0.1 -0.5", "~1 ~-2");
   public static final SimpleCommandExceptionType INCOMPLETE_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("argument.pos2d.incomplete"));
   private final boolean centerIntegers;

   public Vec2ArgumentType(boolean centerIntegers) {
      this.centerIntegers = centerIntegers;
   }

   public static Vec2ArgumentType vec2() {
      return new Vec2ArgumentType(true);
   }

   public static Vec2f getVec2(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      Vec3d _snowman = ((PosArgument)context.getArgument(name, PosArgument.class)).toAbsolutePos((ServerCommandSource)context.getSource());
      return new Vec2f((float)_snowman.x, (float)_snowman.z);
   }

   public PosArgument parse(StringReader _snowman) throws CommandSyntaxException {
      int _snowmanx = _snowman.getCursor();
      if (!_snowman.canRead()) {
         throw INCOMPLETE_EXCEPTION.createWithContext(_snowman);
      } else {
         CoordinateArgument _snowmanxx = CoordinateArgument.parse(_snowman, this.centerIntegers);
         if (_snowman.canRead() && _snowman.peek() == ' ') {
            _snowman.skip();
            CoordinateArgument _snowmanxxx = CoordinateArgument.parse(_snowman, this.centerIntegers);
            return new DefaultPosArgument(_snowmanxx, new CoordinateArgument(true, 0.0), _snowmanxxx);
         } else {
            _snowman.setCursor(_snowmanx);
            throw INCOMPLETE_EXCEPTION.createWithContext(_snowman);
         }
      }
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

         return CommandSource.suggestColumnPositions(_snowman, _snowmanx, builder, CommandManager.getCommandValidator(this::parse));
      }
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
