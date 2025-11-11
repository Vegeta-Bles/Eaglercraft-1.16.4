import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import java.util.Map;

public class xf {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.help.failed"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("help").executes(var1 -> {
         Map<CommandNode<db>, String> _snowman = _snowman.getSmartUsage(_snowman.getRoot(), var1.getSource());

         for (String _snowmanx : _snowman.values()) {
            ((db)var1.getSource()).a(new oe("/" + _snowmanx), false);
         }

         return _snowman.size();
      })).then(dc.a("command", StringArgumentType.greedyString()).executes(var1 -> {
         ParseResults<db> _snowman = _snowman.parse(StringArgumentType.getString(var1, "command"), var1.getSource());
         if (_snowman.getContext().getNodes().isEmpty()) {
            throw a.create();
         } else {
            Map<CommandNode<db>, String> _snowmanx = _snowman.getSmartUsage(((ParsedCommandNode)Iterables.getLast(_snowman.getContext().getNodes())).getNode(), var1.getSource());

            for (String _snowmanxx : _snowmanx.values()) {
               ((db)var1.getSource()).a(new oe("/" + _snowman.getReader().getString() + " " + _snowmanxx), false);
            }

            return _snowmanx.size();
         }
      })));
   }
}
