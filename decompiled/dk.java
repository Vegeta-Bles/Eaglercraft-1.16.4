import com.google.common.collect.Iterables;
import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class dk implements ArgumentType<fc> {
   private static final Collection<String> g = Arrays.asList("Player", "0123", "@e", "@e[type=foo]", "dd12be42-52a9-4a91-a8a1-11c01849e498");
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("argument.entity.toomany"));
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("argument.player.toomany"));
   public static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new of("argument.player.entities"));
   public static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new of("argument.entity.notfound.entity"));
   public static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new of("argument.entity.notfound.player"));
   public static final SimpleCommandExceptionType f = new SimpleCommandExceptionType(new of("argument.entity.selector.not_allowed"));
   private final boolean h;
   private final boolean i;

   protected dk(boolean var1, boolean var2) {
      this.h = _snowman;
      this.i = _snowman;
   }

   public static dk a() {
      return new dk(true, false);
   }

   public static aqa a(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return ((fc)_snowman.getArgument(_snowman, fc.class)).a((db)_snowman.getSource());
   }

   public static dk b() {
      return new dk(false, false);
   }

   public static Collection<? extends aqa> b(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      Collection<? extends aqa> _snowman = c(_snowman, _snowman);
      if (_snowman.isEmpty()) {
         throw d.create();
      } else {
         return _snowman;
      }
   }

   public static Collection<? extends aqa> c(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return ((fc)_snowman.getArgument(_snowman, fc.class)).b((db)_snowman.getSource());
   }

   public static Collection<aah> d(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return ((fc)_snowman.getArgument(_snowman, fc.class)).d((db)_snowman.getSource());
   }

   public static dk c() {
      return new dk(true, true);
   }

   public static aah e(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return ((fc)_snowman.getArgument(_snowman, fc.class)).c((db)_snowman.getSource());
   }

   public static dk d() {
      return new dk(false, true);
   }

   public static Collection<aah> f(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      List<aah> _snowman = ((fc)_snowman.getArgument(_snowman, fc.class)).d((db)_snowman.getSource());
      if (_snowman.isEmpty()) {
         throw e.create();
      } else {
         return _snowman;
      }
   }

   public fc a(StringReader var1) throws CommandSyntaxException {
      int _snowman = 0;
      fd _snowmanx = new fd(_snowman);
      fc _snowmanxx = _snowmanx.t();
      if (_snowmanxx.a() > 1 && this.h) {
         if (this.i) {
            _snowman.setCursor(0);
            throw b.createWithContext(_snowman);
         } else {
            _snowman.setCursor(0);
            throw a.createWithContext(_snowman);
         }
      } else if (_snowmanxx.b() && this.i && !_snowmanxx.c()) {
         _snowman.setCursor(0);
         throw c.createWithContext(_snowman);
      } else {
         return _snowmanxx;
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      if (_snowman.getSource() instanceof dd) {
         StringReader _snowman = new StringReader(_snowman.getInput());
         _snowman.setCursor(_snowman.getStart());
         dd _snowmanx = (dd)_snowman.getSource();
         fd _snowmanxx = new fd(_snowman, _snowmanx.c(2));

         try {
            _snowmanxx.t();
         } catch (CommandSyntaxException var7) {
         }

         return _snowmanxx.a(_snowman, var2x -> {
            Collection<String> _snowmanxxx = _snowman.l();
            Iterable<String> _snowmanx = (Iterable<String>)(this.i ? _snowmanxxx : Iterables.concat(_snowmanxxx, _snowman.r()));
            dd.b(_snowmanx, var2x);
         });
      } else {
         return Suggestions.empty();
      }
   }

   public Collection<String> getExamples() {
      return g;
   }

   public static class a implements fj<dk> {
      public a() {
      }

      public void a(dk var1, nf var2) {
         byte _snowman = 0;
         if (_snowman.h) {
            _snowman = (byte)(_snowman | 1);
         }

         if (_snowman.i) {
            _snowman = (byte)(_snowman | 2);
         }

         _snowman.writeByte(_snowman);
      }

      public dk a(nf var1) {
         byte _snowman = _snowman.readByte();
         return new dk((_snowman & 1) != 0, (_snowman & 2) != 0);
      }

      public void a(dk var1, JsonObject var2) {
         _snowman.addProperty("amount", _snowman.h ? "single" : "multiple");
         _snowman.addProperty("type", _snowman.i ? "players" : "entities");
      }
   }
}
