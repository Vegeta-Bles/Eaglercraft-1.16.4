package net.minecraft.server.function;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;

public class CommandFunction {
   private final CommandFunction.Element[] elements;
   private final Identifier id;

   public CommandFunction(Identifier id, CommandFunction.Element[] elements) {
      this.id = id;
      this.elements = elements;
   }

   public Identifier getId() {
      return this.id;
   }

   public CommandFunction.Element[] getElements() {
      return this.elements;
   }

   public static CommandFunction create(Identifier id, CommandDispatcher<ServerCommandSource> _snowman, ServerCommandSource _snowman, List<String> _snowman) {
      List<CommandFunction.Element> _snowmanxxx = Lists.newArrayListWithCapacity(_snowman.size());

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.size(); _snowmanxxxx++) {
         int _snowmanxxxxx = _snowmanxxxx + 1;
         String _snowmanxxxxxx = _snowman.get(_snowmanxxxx).trim();
         StringReader _snowmanxxxxxxx = new StringReader(_snowmanxxxxxx);
         if (_snowmanxxxxxxx.canRead() && _snowmanxxxxxxx.peek() != '#') {
            if (_snowmanxxxxxxx.peek() == '/') {
               _snowmanxxxxxxx.skip();
               if (_snowmanxxxxxxx.peek() == '/') {
                  throw new IllegalArgumentException(
                     "Unknown or invalid command '" + _snowmanxxxxxx + "' on line " + _snowmanxxxxx + " (if you intended to make a comment, use '#' not '//')"
                  );
               }

               String _snowmanxxxxxxxx = _snowmanxxxxxxx.readUnquotedString();
               throw new IllegalArgumentException(
                  "Unknown or invalid command '"
                     + _snowmanxxxxxx
                     + "' on line "
                     + _snowmanxxxxx
                     + " (did you mean '"
                     + _snowmanxxxxxxxx
                     + "'? Do not use a preceding forwards slash.)"
               );
            }

            try {
               ParseResults<ServerCommandSource> _snowmanxxxxxxxx = _snowman.parse(_snowmanxxxxxxx, _snowman);
               if (_snowmanxxxxxxxx.getReader().canRead()) {
                  throw CommandManager.getException(_snowmanxxxxxxxx);
               }

               _snowmanxxx.add(new CommandFunction.CommandElement(_snowmanxxxxxxxx));
            } catch (CommandSyntaxException var10) {
               throw new IllegalArgumentException("Whilst parsing command on line " + _snowmanxxxxx + ": " + var10.getMessage());
            }
         }
      }

      return new CommandFunction(id, _snowmanxxx.toArray(new CommandFunction.Element[0]));
   }

   public static class CommandElement implements CommandFunction.Element {
      private final ParseResults<ServerCommandSource> parsed;

      public CommandElement(ParseResults<ServerCommandSource> parsed) {
         this.parsed = parsed;
      }

      @Override
      public void execute(CommandFunctionManager manager, ServerCommandSource source, ArrayDeque<CommandFunctionManager.Entry> stack, int maxChainLength) throws CommandSyntaxException {
         manager.getDispatcher().execute(new ParseResults(this.parsed.getContext().withSource(source), this.parsed.getReader(), this.parsed.getExceptions()));
      }

      @Override
      public String toString() {
         return this.parsed.getReader().getString();
      }
   }

   public interface Element {
      void execute(CommandFunctionManager manager, ServerCommandSource source, ArrayDeque<CommandFunctionManager.Entry> stack, int maxChainLength) throws CommandSyntaxException;
   }

   public static class FunctionElement implements CommandFunction.Element {
      private final CommandFunction.LazyContainer function;

      public FunctionElement(CommandFunction _snowman) {
         this.function = new CommandFunction.LazyContainer(_snowman);
      }

      @Override
      public void execute(CommandFunctionManager manager, ServerCommandSource source, ArrayDeque<CommandFunctionManager.Entry> stack, int maxChainLength) {
         this.function.get(manager).ifPresent(_snowmanxxxx -> {
            CommandFunction.Element[] _snowmanxxxxx = _snowmanxxxx.getElements();
            int _snowmanx = maxChainLength - stack.size();
            int _snowmanxx = Math.min(_snowmanxxxxx.length, _snowmanx);

            for (int _snowmanxxx = _snowmanxx - 1; _snowmanxxx >= 0; _snowmanxxx--) {
               stack.addFirst(new CommandFunctionManager.Entry(manager, source, _snowmanxxxxx[_snowmanxxx]));
            }
         });
      }

      @Override
      public String toString() {
         return "function " + this.function.getId();
      }
   }

   public static class LazyContainer {
      public static final CommandFunction.LazyContainer EMPTY = new CommandFunction.LazyContainer((Identifier)null);
      @Nullable
      private final Identifier id;
      private boolean initialized;
      private Optional<CommandFunction> function = Optional.empty();

      public LazyContainer(@Nullable Identifier id) {
         this.id = id;
      }

      public LazyContainer(CommandFunction function) {
         this.initialized = true;
         this.id = null;
         this.function = Optional.of(function);
      }

      public Optional<CommandFunction> get(CommandFunctionManager manager) {
         if (!this.initialized) {
            if (this.id != null) {
               this.function = manager.getFunction(this.id);
            }

            this.initialized = true;
         }

         return this.function;
      }

      @Nullable
      public Identifier getId() {
         return this.function.<Identifier>map(_snowman -> _snowman.id).orElse(this.id);
      }
   }
}
