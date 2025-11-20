package net.minecraft.server.function;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameRules;

public class CommandFunctionManager {
   private static final Identifier TICK_FUNCTION = new Identifier("tick");
   private static final Identifier LOAD_FUNCTION = new Identifier("load");
   private final MinecraftServer server;
   private boolean executing;
   private final ArrayDeque<CommandFunctionManager.Entry> chain = new ArrayDeque<>();
   private final List<CommandFunctionManager.Entry> pending = Lists.newArrayList();
   private final List<CommandFunction> tickFunctions = Lists.newArrayList();
   private boolean needToRunLoadFunctions;
   private FunctionLoader field_25333;

   public CommandFunctionManager(MinecraftServer _snowman, FunctionLoader _snowman) {
      this.server = _snowman;
      this.field_25333 = _snowman;
      this.method_29773(_snowman);
   }

   public int getMaxCommandChainLength() {
      return this.server.getGameRules().getInt(GameRules.MAX_COMMAND_CHAIN_LENGTH);
   }

   public CommandDispatcher<ServerCommandSource> getDispatcher() {
      return this.server.getCommandManager().getDispatcher();
   }

   public void tick() {
      this.method_29460(this.tickFunctions, TICK_FUNCTION);
      if (this.needToRunLoadFunctions) {
         this.needToRunLoadFunctions = false;
         Collection<CommandFunction> _snowman = this.field_25333.getTags().getTagOrEmpty(LOAD_FUNCTION).values();
         this.method_29460(_snowman, LOAD_FUNCTION);
      }
   }

   private void method_29460(Collection<CommandFunction> _snowman, Identifier _snowman) {
      this.server.getProfiler().push(_snowman::toString);

      for (CommandFunction _snowmanxx : _snowman) {
         this.execute(_snowmanxx, this.getTaggedFunctionSource());
      }

      this.server.getProfiler().pop();
   }

   public int execute(CommandFunction function, ServerCommandSource source) {
      int _snowman = this.getMaxCommandChainLength();
      if (this.executing) {
         if (this.chain.size() + this.pending.size() < _snowman) {
            this.pending.add(new CommandFunctionManager.Entry(this, source, new CommandFunction.FunctionElement(function)));
         }

         return 0;
      } else {
         int var16;
         try {
            this.executing = true;
            int _snowmanx = 0;
            CommandFunction.Element[] _snowmanxx = function.getElements();

            for (int _snowmanxxx = _snowmanxx.length - 1; _snowmanxxx >= 0; _snowmanxxx--) {
               this.chain.push(new CommandFunctionManager.Entry(this, source, _snowmanxx[_snowmanxxx]));
            }

            do {
               if (this.chain.isEmpty()) {
                  return _snowmanx;
               }

               try {
                  CommandFunctionManager.Entry _snowmanxxx = this.chain.removeFirst();
                  this.server.getProfiler().push(_snowmanxxx::toString);
                  _snowmanxxx.execute(this.chain, _snowman);
                  if (!this.pending.isEmpty()) {
                     Lists.reverse(this.pending).forEach(this.chain::addFirst);
                     this.pending.clear();
                  }
               } finally {
                  this.server.getProfiler().pop();
               }
            } while (++_snowmanx < _snowman);

            var16 = _snowmanx;
         } finally {
            this.chain.clear();
            this.pending.clear();
            this.executing = false;
         }

         return var16;
      }
   }

   public void method_29461(FunctionLoader _snowman) {
      this.field_25333 = _snowman;
      this.method_29773(_snowman);
   }

   private void method_29773(FunctionLoader _snowman) {
      this.tickFunctions.clear();
      this.tickFunctions.addAll(_snowman.getTags().getTagOrEmpty(TICK_FUNCTION).values());
      this.needToRunLoadFunctions = true;
   }

   public ServerCommandSource getTaggedFunctionSource() {
      return this.server.getCommandSource().withLevel(2).withSilent();
   }

   public Optional<CommandFunction> getFunction(Identifier id) {
      return this.field_25333.get(id);
   }

   public Tag<CommandFunction> method_29462(Identifier _snowman) {
      return this.field_25333.getOrCreateTag(_snowman);
   }

   public Iterable<Identifier> method_29463() {
      return this.field_25333.getFunctions().keySet();
   }

   public Iterable<Identifier> method_29464() {
      return this.field_25333.getTags().getTagIds();
   }

   public static class Entry {
      private final CommandFunctionManager manager;
      private final ServerCommandSource source;
      private final CommandFunction.Element element;

      public Entry(CommandFunctionManager manager, ServerCommandSource source, CommandFunction.Element element) {
         this.manager = manager;
         this.source = source;
         this.element = element;
      }

      public void execute(ArrayDeque<CommandFunctionManager.Entry> stack, int maxChainLength) {
         try {
            this.element.execute(this.manager, this.source, stack, maxChainLength);
         } catch (Throwable var4) {
         }
      }

      @Override
      public String toString() {
         return this.element.toString();
      }
   }
}
