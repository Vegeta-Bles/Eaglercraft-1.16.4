import com.google.common.collect.Maps;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dc {
   private static final Logger a = LogManager.getLogger();
   private final CommandDispatcher<db> b = new CommandDispatcher();

   public dc(dc.a var1) {
      wf.a(this.b);
      wg.a(this.b);
      wx.a(this.b);
      wk.a(this.b);
      wl.a(this.b);
      wm.a(this.b);
      za.a(this.b);
      wn.a(this.b);
      wp.a(this.b);
      ws.a(this.b);
      wt.a(this.b);
      wu.a(this.b);
      wv.a(this.b);
      ww.a(this.b);
      wy.a(this.b);
      wz.a(this.b);
      xa.a(this.b);
      xb.a(this.b);
      xc.a(this.b);
      xd.a(this.b);
      xe.a(this.b);
      xf.a(this.b);
      xg.a(this.b);
      xh.a(this.b);
      xi.a(this.b);
      xk.a(this.b);
      xj.a(this.b);
      xl.a(this.b);
      xm.a(this.b);
      xq.a(this.b);
      xr.a(this.b);
      xv.a(this.b);
      xu.a(this.b);
      xw.a(this.b);
      ya.a(this.b);
      yb.a(this.b);
      yc.a(this.b);
      yd.a(this.b, _snowman != dc.a.c);
      ye.a(this.b);
      yg.a(this.b);
      yh.a(this.b);
      yi.a(this.b);
      yj.a(this.b);
      yl.a(this.b);
      ym.a(this.b);
      yn.a(this.b);
      yo.a(this.b);
      yp.a(this.b);
      yq.a(this.b);
      yr.a(this.b);
      ys.a(this.b);
      yt.a(this.b);
      yu.a(this.b);
      yv.a(this.b);
      yx.a(this.b);
      if (w.d) {
         lt.a(this.b);
      }

      if (_snowman.e) {
         wh.a(this.b);
         wi.a(this.b);
         wj.a(this.b);
         wo.a(this.b);
         xn.a(this.b);
         xo.a(this.b);
         xp.a(this.b);
         xx.a(this.b);
         xy.a(this.b);
         xz.a(this.b);
         yf.a(this.b);
         yk.a(this.b);
         yw.a(this.b);
      }

      if (_snowman.d) {
         xs.a(this.b);
      }

      this.b
         .findAmbiguities(
            (var1x, var2, var3, var4) -> a.warn("Ambiguity between arguments {} and {} with inputs: {}", this.b.getPath(var2), this.b.getPath(var3), var4)
         );
      this.b.setConsumer((var0, var1x, var2) -> ((db)var0.getSource()).a(var0, var1x, var2));
   }

   public int a(db var1, String var2) {
      StringReader _snowman = new StringReader(_snowman);
      if (_snowman.canRead() && _snowman.peek() == '/') {
         _snowman.skip();
      }

      _snowman.j().aQ().a(_snowman);

      byte var20;
      try {
         return this.b.execute(_snowman, _snowman);
      } catch (cz var13) {
         _snowman.a(var13.a());
         return 0;
      } catch (CommandSyntaxException var14) {
         _snowman.a(ns.a(var14.getRawMessage()));
         if (var14.getInput() != null && var14.getCursor() >= 0) {
            int _snowmanx = Math.min(var14.getInput().length(), var14.getCursor());
            nx _snowmanxx = new oe("").a(k.h).a(var1x -> var1x.a(new np(np.a.d, _snowman)));
            if (_snowmanx > 10) {
               _snowmanxx.c("...");
            }

            _snowmanxx.c(var14.getInput().substring(Math.max(0, _snowmanx - 10), _snowmanx));
            if (_snowmanx < var14.getInput().length()) {
               nr _snowmanxxx = new oe(var14.getInput().substring(_snowmanx)).a(new k[]{k.m, k.t});
               _snowmanxx.a(_snowmanxxx);
            }

            _snowmanxx.a(new of("command.context.here").a(new k[]{k.m, k.u}));
            _snowman.a(_snowmanxx);
         }

         return 0;
      } catch (Exception var15) {
         nx _snowmanxxx = new oe(var15.getMessage() == null ? var15.getClass().getName() : var15.getMessage());
         if (a.isDebugEnabled()) {
            a.error("Command exception: {}", _snowman, var15);
            StackTraceElement[] _snowmanxxxx = var15.getStackTrace();

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < Math.min(_snowmanxxxx.length, 3); _snowmanxxxxx++) {
               _snowmanxxx.c("\n\n").c(_snowmanxxxx[_snowmanxxxxx].getMethodName()).c("\n ").c(_snowmanxxxx[_snowmanxxxxx].getFileName()).c(":").c(String.valueOf(_snowmanxxxx[_snowmanxxxxx].getLineNumber()));
            }
         }

         _snowman.a(new of("command.failed").a(var1x -> var1x.a(new nv(nv.a.a, _snowman))));
         if (w.d) {
            _snowman.a(new oe(x.d(var15)));
            a.error("'" + _snowman + "' threw an exception", var15);
         }

         var20 = 0;
      } finally {
         _snowman.j().aQ().c();
      }

      return var20;
   }

   public void a(aah var1) {
      Map<CommandNode<db>, CommandNode<dd>> _snowman = Maps.newHashMap();
      RootCommandNode<dd> _snowmanx = new RootCommandNode();
      _snowman.put(this.b.getRoot(), _snowmanx);
      this.a(this.b.getRoot(), _snowmanx, _snowman.cw(), _snowman);
      _snowman.b.a(new pd(_snowmanx));
   }

   private void a(CommandNode<db> var1, CommandNode<dd> var2, db var3, Map<CommandNode<db>, CommandNode<dd>> var4) {
      for (CommandNode<db> _snowman : _snowman.getChildren()) {
         if (_snowman.canUse(_snowman)) {
            ArgumentBuilder<dd, ?> _snowmanx = _snowman.createBuilder();
            _snowmanx.requires(var0 -> true);
            if (_snowmanx.getCommand() != null) {
               _snowmanx.executes(var0 -> 0);
            }

            if (_snowmanx instanceof RequiredArgumentBuilder) {
               RequiredArgumentBuilder<dd, ?> _snowmanxx = (RequiredArgumentBuilder<dd, ?>)_snowmanx;
               if (_snowmanxx.getSuggestionsProvider() != null) {
                  _snowmanxx.suggests(fm.b(_snowmanxx.getSuggestionsProvider()));
               }
            }

            if (_snowmanx.getRedirect() != null) {
               _snowmanx.redirect(_snowman.get(_snowmanx.getRedirect()));
            }

            CommandNode<dd> _snowmanxx = _snowmanx.build();
            _snowman.put(_snowman, _snowmanxx);
            _snowman.addChild(_snowmanxx);
            if (!_snowman.getChildren().isEmpty()) {
               this.a(_snowman, _snowmanxx, _snowman, _snowman);
            }
         }
      }
   }

   public static LiteralArgumentBuilder<db> a(String var0) {
      return LiteralArgumentBuilder.literal(_snowman);
   }

   public static <T> RequiredArgumentBuilder<db, T> a(String var0, ArgumentType<T> var1) {
      return RequiredArgumentBuilder.argument(_snowman, _snowman);
   }

   public static Predicate<String> a(dc.b var0) {
      return var1 -> {
         try {
            _snowman.parse(new StringReader(var1));
            return true;
         } catch (CommandSyntaxException var3) {
            return false;
         }
      };
   }

   public CommandDispatcher<db> a() {
      return this.b;
   }

   @Nullable
   public static <S> CommandSyntaxException a(ParseResults<S> var0) {
      if (!_snowman.getReader().canRead()) {
         return null;
      } else if (_snowman.getExceptions().size() == 1) {
         return (CommandSyntaxException)_snowman.getExceptions().values().iterator().next();
      } else {
         return _snowman.getContext().getRange().isEmpty()
            ? CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().createWithContext(_snowman.getReader())
            : CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().createWithContext(_snowman.getReader());
      }
   }

   public static void b() {
      RootCommandNode<db> _snowman = new dc(dc.a.a).a().getRoot();
      Set<ArgumentType<?>> _snowmanx = fk.a(_snowman);
      Set<ArgumentType<?>> _snowmanxx = _snowmanx.stream().filter(var0x -> !fk.a((ArgumentType<?>)var0x)).collect(Collectors.toSet());
      if (!_snowmanxx.isEmpty()) {
         a.warn("Missing type registration for following arguments:\n {}", _snowmanxx.stream().map(var0x -> "\t" + var0x).collect(Collectors.joining(",\n")));
         throw new IllegalStateException("Unregistered argument types");
      }
   }

   public static enum a {
      a(true, true),
      b(false, true),
      c(true, false);

      private final boolean d;
      private final boolean e;

      private a(boolean var3, boolean var4) {
         this.d = _snowman;
         this.e = _snowman;
      }
   }

   @FunctionalInterface
   public interface b {
      void parse(StringReader var1) throws CommandSyntaxException;
   }
}
