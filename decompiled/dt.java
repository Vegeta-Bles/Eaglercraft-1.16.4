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

public class dt implements ArgumentType<String> {
   private static final Collection<String> b = Arrays.asList("foo", "*", "012");
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> new of("arguments.objective.notFound", var0));
   private static final DynamicCommandExceptionType d = new DynamicCommandExceptionType(var0 -> new of("arguments.objective.readonly", var0));
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> new of("commands.scoreboard.objectives.add.longName", var0));

   public dt() {
   }

   public static dt a() {
      return new dt();
   }

   public static ddk a(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      String _snowman = (String)_snowman.getArgument(_snowman, String.class);
      ddn _snowmanx = ((db)_snowman.getSource()).j().aH();
      ddk _snowmanxx = _snowmanx.d(_snowman);
      if (_snowmanxx == null) {
         throw c.create(_snowman);
      } else {
         return _snowmanxx;
      }
   }

   public static ddk b(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      ddk _snowman = a(_snowman, _snowman);
      if (_snowman.c().d()) {
         throw d.create(_snowman.b());
      } else {
         return _snowman;
      }
   }

   public String a(StringReader var1) throws CommandSyntaxException {
      String _snowman = _snowman.readUnquotedString();
      if (_snowman.length() > 16) {
         throw a.create(16);
      } else {
         return _snowman;
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      if (_snowman.getSource() instanceof db) {
         return dd.b(((db)_snowman.getSource()).j().aH().d(), _snowman);
      } else if (_snowman.getSource() instanceof dd) {
         dd _snowman = (dd)_snowman.getSource();
         return _snowman.a(_snowman, _snowman);
      } else {
         return Suggestions.empty();
      }
   }

   public Collection<String> getExamples() {
      return b;
   }
}
