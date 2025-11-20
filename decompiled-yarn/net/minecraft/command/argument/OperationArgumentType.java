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
import java.util.concurrent.CompletableFuture;
import net.minecraft.command.CommandSource;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;

public class OperationArgumentType implements ArgumentType<OperationArgumentType.Operation> {
   private static final Collection<String> EXAMPLES = Arrays.asList("=", ">", "<");
   private static final SimpleCommandExceptionType INVALID_OPERATION = new SimpleCommandExceptionType(new TranslatableText("arguments.operation.invalid"));
   private static final SimpleCommandExceptionType DIVISION_ZERO_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("arguments.operation.div0"));

   public OperationArgumentType() {
   }

   public static OperationArgumentType operation() {
      return new OperationArgumentType();
   }

   public static OperationArgumentType.Operation getOperation(CommandContext<ServerCommandSource> _snowman, String _snowman) throws CommandSyntaxException {
      return (OperationArgumentType.Operation)_snowman.getArgument(_snowman, OperationArgumentType.Operation.class);
   }

   public OperationArgumentType.Operation parse(StringReader _snowman) throws CommandSyntaxException {
      if (!_snowman.canRead()) {
         throw INVALID_OPERATION.create();
      } else {
         int _snowmanx = _snowman.getCursor();

         while (_snowman.canRead() && _snowman.peek() != ' ') {
            _snowman.skip();
         }

         return getOperator(_snowman.getString().substring(_snowmanx, _snowman.getCursor()));
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      return CommandSource.suggestMatching(new String[]{"=", "+=", "-=", "*=", "/=", "%=", "<", ">", "><"}, builder);
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   private static OperationArgumentType.Operation getOperator(String _snowman) throws CommandSyntaxException {
      return (OperationArgumentType.Operation)(_snowman.equals("><") ? (_snowmanx, _snowmanxxx) -> {
         int _snowmanxx = _snowmanx.getScore();
         _snowmanx.setScore(_snowmanxxx.getScore());
         _snowmanxxx.setScore(_snowmanxx);
      } : getIntOperator(_snowman));
   }

   private static OperationArgumentType.IntOperator getIntOperator(String _snowman) throws CommandSyntaxException {
      switch (_snowman) {
         case "=":
            return (_snowmanx, _snowmanxx) -> _snowmanxx;
         case "+=":
            return (_snowmanx, _snowmanxx) -> _snowmanx + _snowmanxx;
         case "-=":
            return (_snowmanx, _snowmanxx) -> _snowmanx - _snowmanxx;
         case "*=":
            return (_snowmanx, _snowmanxx) -> _snowmanx * _snowmanxx;
         case "/=":
            return (_snowmanx, _snowmanxx) -> {
               if (_snowmanxx == 0) {
                  throw DIVISION_ZERO_EXCEPTION.create();
               } else {
                  return MathHelper.floorDiv(_snowmanx, _snowmanxx);
               }
            };
         case "%=":
            return (_snowmanx, _snowmanxx) -> {
               if (_snowmanxx == 0) {
                  throw DIVISION_ZERO_EXCEPTION.create();
               } else {
                  return MathHelper.floorMod(_snowmanx, _snowmanxx);
               }
            };
         case "<":
            return Math::min;
         case ">":
            return Math::max;
         default:
            throw INVALID_OPERATION.create();
      }
   }

   @FunctionalInterface
   interface IntOperator extends OperationArgumentType.Operation {
      int apply(int var1, int var2) throws CommandSyntaxException;

      @Override
      default void apply(ScoreboardPlayerScore _snowman, ScoreboardPlayerScore _snowman) throws CommandSyntaxException {
         _snowman.setScore(this.apply(_snowman.getScore(), _snowman.getScore()));
      }
   }

   @FunctionalInterface
   public interface Operation {
      void apply(ScoreboardPlayerScore var1, ScoreboardPlayerScore var2) throws CommandSyntaxException;
   }
}
