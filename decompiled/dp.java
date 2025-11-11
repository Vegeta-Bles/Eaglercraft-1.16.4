import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;

public class dp implements ArgumentType<dp.a> {
   private static final Collection<String> a = Arrays.asList("Hello world!", "foo", "@e", "Hello @p :)");

   public dp() {
   }

   public static dp a() {
      return new dp();
   }

   public static nr a(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return ((dp.a)_snowman.getArgument(_snowman, dp.a.class)).a((db)_snowman.getSource(), ((db)_snowman.getSource()).c(2));
   }

   public dp.a a(StringReader var1) throws CommandSyntaxException {
      return dp.a.a(_snowman, true);
   }

   public Collection<String> getExamples() {
      return a;
   }

   public static class a {
      private final String a;
      private final dp.b[] b;

      public a(String var1, dp.b[] var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public nr a(db var1, boolean var2) throws CommandSyntaxException {
         if (this.b.length != 0 && _snowman) {
            nx _snowman = new oe(this.a.substring(0, this.b[0].a()));
            int _snowmanx = this.b[0].a();

            for (dp.b _snowmanxx : this.b) {
               nr _snowmanxxx = _snowmanxx.a(_snowman);
               if (_snowmanx < _snowmanxx.a()) {
                  _snowman.c(this.a.substring(_snowmanx, _snowmanxx.a()));
               }

               if (_snowmanxxx != null) {
                  _snowman.a(_snowmanxxx);
               }

               _snowmanx = _snowmanxx.b();
            }

            if (_snowmanx < this.a.length()) {
               _snowman.c(this.a.substring(_snowmanx, this.a.length()));
            }

            return _snowman;
         } else {
            return new oe(this.a);
         }
      }

      public static dp.a a(StringReader var0, boolean var1) throws CommandSyntaxException {
         String _snowman = _snowman.getString().substring(_snowman.getCursor(), _snowman.getTotalLength());
         if (!_snowman) {
            _snowman.setCursor(_snowman.getTotalLength());
            return new dp.a(_snowman, new dp.b[0]);
         } else {
            List<dp.b> _snowmanx = Lists.newArrayList();
            int _snowmanxx = _snowman.getCursor();

            while (true) {
               int _snowmanxxx;
               fc _snowmanxxxx;
               while (true) {
                  if (!_snowman.canRead()) {
                     return new dp.a(_snowman, _snowmanx.toArray(new dp.b[_snowmanx.size()]));
                  }

                  if (_snowman.peek() == '@') {
                     _snowmanxxx = _snowman.getCursor();

                     try {
                        fd _snowmanxxxxx = new fd(_snowman);
                        _snowmanxxxx = _snowmanxxxxx.t();
                        break;
                     } catch (CommandSyntaxException var8) {
                        if (var8.getType() != fd.d && var8.getType() != fd.b) {
                           throw var8;
                        }

                        _snowman.setCursor(_snowmanxxx + 1);
                     }
                  } else {
                     _snowman.skip();
                  }
               }

               _snowmanx.add(new dp.b(_snowmanxxx - _snowmanxx, _snowman.getCursor() - _snowmanxx, _snowmanxxxx));
            }
         }
      }
   }

   public static class b {
      private final int a;
      private final int b;
      private final fc c;

      public b(int var1, int var2, fc var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public int a() {
         return this.a;
      }

      public int b() {
         return this.b;
      }

      @Nullable
      public nr a(db var1) throws CommandSyntaxException {
         return fc.a(this.c.b(_snowman));
      }
   }
}
