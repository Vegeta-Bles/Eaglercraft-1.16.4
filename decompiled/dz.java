import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class dz implements ArgumentType<dz.a> {
   public static final SuggestionProvider<db> a = (var0, var1) -> {
      StringReader _snowman = new StringReader(var1.getInput());
      _snowman.setCursor(var1.getStart());
      fd _snowmanx = new fd(_snowman);

      try {
         _snowmanx.t();
      } catch (CommandSyntaxException var5) {
      }

      return _snowmanx.a(var1, var1x -> dd.b(((db)var0.getSource()).l(), var1x));
   };
   private static final Collection<String> b = Arrays.asList("Player", "0123", "*", "@e");
   private static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new of("argument.scoreHolder.empty"));
   private final boolean d;

   public dz(boolean var1) {
      this.d = _snowman;
   }

   public static String a(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return b(_snowman, _snowman).iterator().next();
   }

   public static Collection<String> b(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return a(_snowman, _snowman, Collections::emptyList);
   }

   public static Collection<String> c(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return a(_snowman, _snowman, ((db)_snowman.getSource()).j().aH()::e);
   }

   public static Collection<String> a(CommandContext<db> var0, String var1, Supplier<Collection<String>> var2) throws CommandSyntaxException {
      Collection<String> _snowman = ((dz.a)_snowman.getArgument(_snowman, dz.a.class)).getNames((db)_snowman.getSource(), _snowman);
      if (_snowman.isEmpty()) {
         throw dk.d.create();
      } else {
         return _snowman;
      }
   }

   public static dz a() {
      return new dz(false);
   }

   public static dz b() {
      return new dz(true);
   }

   public dz.a a(StringReader var1) throws CommandSyntaxException {
      if (_snowman.canRead() && _snowman.peek() == '@') {
         fd _snowman = new fd(_snowman);
         fc _snowmanx = _snowman.t();
         if (!this.d && _snowmanx.a() > 1) {
            throw dk.a.create();
         } else {
            return new dz.b(_snowmanx);
         }
      } else {
         int _snowman = _snowman.getCursor();

         while (_snowman.canRead() && _snowman.peek() != ' ') {
            _snowman.skip();
         }

         String _snowmanx = _snowman.getString().substring(_snowman, _snowman.getCursor());
         if (_snowmanx.equals("*")) {
            return (var0, var1x) -> {
               Collection<String> _snowmanxx = var1x.get();
               if (_snowmanxx.isEmpty()) {
                  throw c.create();
               } else {
                  return _snowmanxx;
               }
            };
         } else {
            Collection<String> _snowmanxx = Collections.singleton(_snowmanx);
            return (var1x, var2x) -> _snowman;
         }
      }
   }

   public Collection<String> getExamples() {
      return b;
   }

   @FunctionalInterface
   public interface a {
      Collection<String> getNames(db var1, Supplier<Collection<String>> var2) throws CommandSyntaxException;
   }

   public static class b implements dz.a {
      private final fc a;

      public b(fc var1) {
         this.a = _snowman;
      }

      @Override
      public Collection<String> getNames(db var1, Supplier<Collection<String>> var2) throws CommandSyntaxException {
         List<? extends aqa> _snowman = this.a.b(_snowman);
         if (_snowman.isEmpty()) {
            throw dk.d.create();
         } else {
            List<String> _snowmanx = Lists.newArrayList();

            for (aqa _snowmanxx : _snowman) {
               _snowmanx.add(_snowmanxx.bU());
            }

            return _snowmanx;
         }
      }
   }

   public static class c implements fj<dz> {
      public c() {
      }

      public void a(dz var1, nf var2) {
         byte _snowman = 0;
         if (_snowman.d) {
            _snowman = (byte)(_snowman | 1);
         }

         _snowman.writeByte(_snowman);
      }

      public dz a(nf var1) {
         byte _snowman = _snowman.readByte();
         boolean _snowmanx = (_snowman & 1) != 0;
         return new dz(_snowmanx);
      }

      public void a(dz var1, JsonObject var2) {
         _snowman.addProperty("amount", _snowman.d ? "multiple" : "single");
      }
   }
}
