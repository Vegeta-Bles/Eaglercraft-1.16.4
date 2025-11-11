import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

public class cy {
   private final cy.c[] a;
   private final vk b;

   public cy(vk var1, cy.c[] var2) {
      this.b = _snowman;
      this.a = _snowman;
   }

   public vk a() {
      return this.b;
   }

   public cy.c[] b() {
      return this.a;
   }

   public static cy a(vk var0, CommandDispatcher<db> var1, db var2, List<String> var3) {
      List<cy.c> _snowman = Lists.newArrayListWithCapacity(_snowman.size());

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         int _snowmanxx = _snowmanx + 1;
         String _snowmanxxx = _snowman.get(_snowmanx).trim();
         StringReader _snowmanxxxx = new StringReader(_snowmanxxx);
         if (_snowmanxxxx.canRead() && _snowmanxxxx.peek() != '#') {
            if (_snowmanxxxx.peek() == '/') {
               _snowmanxxxx.skip();
               if (_snowmanxxxx.peek() == '/') {
                  throw new IllegalArgumentException(
                     "Unknown or invalid command '" + _snowmanxxx + "' on line " + _snowmanxx + " (if_ you intended to make a comment, use '#' not '//')"
                  );
               }

               String _snowmanxxxxx = _snowmanxxxx.readUnquotedString();
               throw new IllegalArgumentException(
                  "Unknown or invalid command '" + _snowmanxxx + "' on line " + _snowmanxx + " (did you mean '" + _snowmanxxxxx + "'? Do not use a preceding forwards slash.)"
               );
            }

            try {
               ParseResults<db> _snowmanxxxxx = _snowman.parse(_snowmanxxxx, _snowman);
               if (_snowmanxxxxx.getReader().canRead()) {
                  throw dc.a(_snowmanxxxxx);
               }

               _snowman.add(new cy.b(_snowmanxxxxx));
            } catch (CommandSyntaxException var10) {
               throw new IllegalArgumentException("Whilst parsing command on line " + _snowmanxx + ": " + var10.getMessage());
            }
         }
      }

      return new cy(_snowman, _snowman.toArray(new cy.c[0]));
   }

   public static class a {
      public static final cy.a a = new cy.a((vk)null);
      @Nullable
      private final vk b;
      private boolean c;
      private Optional<cy> d = Optional.empty();

      public a(@Nullable vk var1) {
         this.b = _snowman;
      }

      public a(cy var1) {
         this.c = true;
         this.b = null;
         this.d = Optional.of(_snowman);
      }

      public Optional<cy> a(vx var1) {
         if (!this.c) {
            if (this.b != null) {
               this.d = _snowman.a(this.b);
            }

            this.c = true;
         }

         return this.d;
      }

      @Nullable
      public vk a() {
         return this.d.<vk>map(var0 -> var0.b).orElse(this.b);
      }
   }

   public static class b implements cy.c {
      private final ParseResults<db> a;

      public b(ParseResults<db> var1) {
         this.a = _snowman;
      }

      @Override
      public void a(vx var1, db var2, ArrayDeque<vx.a> var3, int var4) throws CommandSyntaxException {
         _snowman.c().execute(new ParseResults(this.a.getContext().withSource(_snowman), this.a.getReader(), this.a.getExceptions()));
      }

      @Override
      public String toString() {
         return this.a.getReader().getString();
      }
   }

   public interface c {
      void a(vx var1, db var2, ArrayDeque<vx.a> var3, int var4) throws CommandSyntaxException;
   }

   public static class d implements cy.c {
      private final cy.a a;

      public d(cy var1) {
         this.a = new cy.a(_snowman);
      }

      @Override
      public void a(vx var1, db var2, ArrayDeque<vx.a> var3, int var4) {
         this.a.a(_snowman).ifPresent(var4x -> {
            cy.c[] _snowman = var4x.b();
            int _snowmanx = _snowman - _snowman.size();
            int _snowmanxx = Math.min(_snowman.length, _snowmanx);

            for (int _snowmanxxx = _snowmanxx - 1; _snowmanxxx >= 0; _snowmanxxx--) {
               _snowman.addFirst(new vx.a(_snowman, _snowman, _snowman[_snowmanxxx]));
            }
         });
      }

      @Override
      public String toString() {
         return "function " + this.a.a();
      }
   }
}
