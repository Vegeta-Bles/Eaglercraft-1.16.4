package net.minecraft.command.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.tag.Tag;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class FunctionArgumentType implements ArgumentType<FunctionArgumentType.FunctionArgument> {
   private static final Collection<String> EXAMPLES = Arrays.asList("foo", "foo:bar", "#foo");
   private static final DynamicCommandExceptionType UNKNOWN_FUNCTION_TAG_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("arguments.function.tag.unknown", _snowman)
   );
   private static final DynamicCommandExceptionType UNKNOWN_FUNCTION_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("arguments.function.unknown", _snowman)
   );

   public FunctionArgumentType() {
   }

   public static FunctionArgumentType function() {
      return new FunctionArgumentType();
   }

   public FunctionArgumentType.FunctionArgument parse(StringReader _snowman) throws CommandSyntaxException {
      if (_snowman.canRead() && _snowman.peek() == '#') {
         _snowman.skip();
         final Identifier _snowmanx = Identifier.fromCommandInput(_snowman);
         return new FunctionArgumentType.FunctionArgument() {
            @Override
            public Collection<CommandFunction> getFunctions(CommandContext<ServerCommandSource> _snowman) throws CommandSyntaxException {
               Tag<CommandFunction> _snowmanx = FunctionArgumentType.getFunctionTag(_snowman, _snowman);
               return _snowmanx.values();
            }

            @Override
            public Pair<Identifier, Either<CommandFunction, Tag<CommandFunction>>> getFunctionOrTag(CommandContext<ServerCommandSource> _snowman) throws CommandSyntaxException {
               return Pair.of(_snowman, Either.right(FunctionArgumentType.getFunctionTag(_snowman, _snowman)));
            }
         };
      } else {
         final Identifier _snowmanx = Identifier.fromCommandInput(_snowman);
         return new FunctionArgumentType.FunctionArgument() {
            @Override
            public Collection<CommandFunction> getFunctions(CommandContext<ServerCommandSource> _snowman) throws CommandSyntaxException {
               return Collections.singleton(FunctionArgumentType.getFunction(_snowman, _snowman));
            }

            @Override
            public Pair<Identifier, Either<CommandFunction, Tag<CommandFunction>>> getFunctionOrTag(CommandContext<ServerCommandSource> _snowman) throws CommandSyntaxException {
               return Pair.of(_snowman, Either.left(FunctionArgumentType.getFunction(_snowman, _snowman)));
            }
         };
      }
   }

   private static CommandFunction getFunction(CommandContext<ServerCommandSource> context, Identifier id) throws CommandSyntaxException {
      return ((ServerCommandSource)context.getSource())
         .getMinecraftServer()
         .getCommandFunctionManager()
         .getFunction(id)
         .orElseThrow(() -> UNKNOWN_FUNCTION_EXCEPTION.create(id.toString()));
   }

   private static Tag<CommandFunction> getFunctionTag(CommandContext<ServerCommandSource> context, Identifier id) throws CommandSyntaxException {
      Tag<CommandFunction> _snowman = ((ServerCommandSource)context.getSource()).getMinecraftServer().getCommandFunctionManager().method_29462(id);
      if (_snowman == null) {
         throw UNKNOWN_FUNCTION_TAG_EXCEPTION.create(id.toString());
      } else {
         return _snowman;
      }
   }

   public static Collection<CommandFunction> getFunctions(CommandContext<ServerCommandSource> context, String name) throws CommandSyntaxException {
      return ((FunctionArgumentType.FunctionArgument)context.getArgument(name, FunctionArgumentType.FunctionArgument.class)).getFunctions(context);
   }

   public static Pair<Identifier, Either<CommandFunction, Tag<CommandFunction>>> getFunctionOrTag(CommandContext<ServerCommandSource> _snowman, String _snowman) throws CommandSyntaxException {
      return ((FunctionArgumentType.FunctionArgument)_snowman.getArgument(_snowman, FunctionArgumentType.FunctionArgument.class)).getFunctionOrTag(_snowman);
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }

   public interface FunctionArgument {
      Collection<CommandFunction> getFunctions(CommandContext<ServerCommandSource> var1) throws CommandSyntaxException;

      Pair<Identifier, Either<CommandFunction, Tag<CommandFunction>>> getFunctionOrTag(CommandContext<ServerCommandSource> var1) throws CommandSyntaxException;
   }
}
