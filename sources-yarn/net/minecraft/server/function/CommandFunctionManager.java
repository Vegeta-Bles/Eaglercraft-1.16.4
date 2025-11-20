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

   public CommandFunctionManager(MinecraftServer minecraftServer, FunctionLoader arg) {
      this.server = minecraftServer;
      this.field_25333 = arg;
      this.method_29773(arg);
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
         Collection<CommandFunction> collection = this.field_25333.getTags().getTagOrEmpty(LOAD_FUNCTION).values();
         this.method_29460(collection, LOAD_FUNCTION);
      }
   }

   private void method_29460(Collection<CommandFunction> collection, Identifier arg) {
      this.server.getProfiler().push(arg::toString);

      for (CommandFunction lv : collection) {
         this.execute(lv, this.getTaggedFunctionSource());
      }

      this.server.getProfiler().pop();
   }

   public int execute(CommandFunction function, ServerCommandSource source) {
      int i = this.getMaxCommandChainLength();
      if (this.executing) {
         if (this.chain.size() + this.pending.size() < i) {
            this.pending.add(new CommandFunctionManager.Entry(this, source, new CommandFunction.FunctionElement(function)));
         }

         return 0;
      } else {
         int var16;
         try {
            this.executing = true;
            int j = 0;
            CommandFunction.Element[] lvs = function.getElements();

            for (int k = lvs.length - 1; k >= 0; k--) {
               this.chain.push(new CommandFunctionManager.Entry(this, source, lvs[k]));
            }

            do {
               if (this.chain.isEmpty()) {
                  return j;
               }

               try {
                  CommandFunctionManager.Entry lv = this.chain.removeFirst();
                  this.server.getProfiler().push(lv::toString);
                  lv.execute(this.chain, i);
                  if (!this.pending.isEmpty()) {
                     Lists.reverse(this.pending).forEach(this.chain::addFirst);
                     this.pending.clear();
                  }
               } finally {
                  this.server.getProfiler().pop();
               }
            } while (++j < i);

            var16 = j;
         } finally {
            this.chain.clear();
            this.pending.clear();
            this.executing = false;
         }

         return var16;
      }
   }

   public void method_29461(FunctionLoader arg) {
      this.field_25333 = arg;
      this.method_29773(arg);
   }

   private void method_29773(FunctionLoader arg) {
      this.tickFunctions.clear();
      this.tickFunctions.addAll(arg.getTags().getTagOrEmpty(TICK_FUNCTION).values());
      this.needToRunLoadFunctions = true;
   }

   public ServerCommandSource getTaggedFunctionSource() {
      return this.server.getCommandSource().withLevel(2).withSilent();
   }

   public Optional<CommandFunction> getFunction(Identifier id) {
      return this.field_25333.get(id);
   }

   public Tag<CommandFunction> method_29462(Identifier arg) {
      return this.field_25333.getOrCreateTag(arg);
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
