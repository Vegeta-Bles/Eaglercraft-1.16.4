import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContextBuilder;
import com.mojang.brigadier.context.ParsedArgument;
import com.mojang.brigadier.context.SuggestionContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class dlm {
   private static final Pattern a = Pattern.compile("(\\s+)");
   private static final ob b = ob.a.a(k.m);
   private static final ob c = ob.a.a(k.h);
   private static final List<ob> d = Stream.of(k.l, k.o, k.k, k.n, k.g).map(ob.a::a).collect(ImmutableList.toImmutableList());
   private final djz e;
   private final dot f;
   private final dlq g;
   private final dku h;
   private final boolean i;
   private final boolean j;
   private final int k;
   private final int l;
   private final boolean m;
   private final int n;
   private final List<afa> o = Lists.newArrayList();
   private int p;
   private int q;
   private ParseResults<dd> r;
   private CompletableFuture<Suggestions> s;
   private dlm.a t;
   private boolean u;
   private boolean v;

   public dlm(djz var1, dot var2, dlq var3, dku var4, boolean var5, boolean var6, int var7, int var8, boolean var9, int var10) {
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.m = _snowman;
      this.n = _snowman;
      _snowman.a(this::a);
   }

   public void a(boolean var1) {
      this.u = _snowman;
      if (!_snowman) {
         this.t = null;
      }
   }

   public boolean a(int var1, int var2, int var3) {
      if (this.t != null && this.t.b(_snowman, _snowman, _snowman)) {
         return true;
      } else if (this.f.aw_() == this.g && _snowman == 258) {
         this.b(true);
         return true;
      } else {
         return false;
      }
   }

   public boolean a(double var1) {
      return this.t != null && this.t.a(afm.a(_snowman, -1.0, 1.0));
   }

   public boolean a(double var1, double var3, int var5) {
      return this.t != null && this.t.a((int)_snowman, (int)_snowman, _snowman);
   }

   public void b(boolean var1) {
      if (this.s != null && this.s.isDone()) {
         Suggestions _snowman = this.s.join();
         if (!_snowman.isEmpty()) {
            int _snowmanx = 0;

            for (Suggestion _snowmanxx : _snowman.getList()) {
               _snowmanx = Math.max(_snowmanx, this.h.b(_snowmanxx.getText()));
            }

            int _snowmanxx = afm.a(this.g.o(_snowman.getRange().getStart()), 0, this.g.o(0) + this.g.o() - _snowmanx);
            int _snowmanxxx = this.m ? this.f.l - 12 : 72;
            this.t = new dlm.a(_snowmanxx, _snowmanxxx, _snowmanx, this.a(_snowman), _snowman);
         }
      }
   }

   private List<Suggestion> a(Suggestions var1) {
      String _snowman = this.g.b().substring(0, this.g.n());
      int _snowmanx = a(_snowman);
      String _snowmanxx = _snowman.substring(_snowmanx).toLowerCase(Locale.ROOT);
      List<Suggestion> _snowmanxxx = Lists.newArrayList();
      List<Suggestion> _snowmanxxxx = Lists.newArrayList();

      for (Suggestion _snowmanxxxxx : _snowman.getList()) {
         if (!_snowmanxxxxx.getText().startsWith(_snowmanxx) && !_snowmanxxxxx.getText().startsWith("minecraft:" + _snowmanxx)) {
            _snowmanxxxx.add(_snowmanxxxxx);
         } else {
            _snowmanxxx.add(_snowmanxxxxx);
         }
      }

      _snowmanxxx.addAll(_snowmanxxxx);
      return _snowmanxxx;
   }

   public void a() {
      String _snowman = this.g.b();
      if (this.r != null && !this.r.getReader().getString().equals(_snowman)) {
         this.r = null;
      }

      if (!this.v) {
         this.g.c(null);
         this.t = null;
      }

      this.o.clear();
      StringReader _snowmanx = new StringReader(_snowman);
      boolean _snowmanxx = _snowmanx.canRead() && _snowmanx.peek() == '/';
      if (_snowmanxx) {
         _snowmanx.skip();
      }

      boolean _snowmanxxx = this.i || _snowmanxx;
      int _snowmanxxxx = this.g.n();
      if (_snowmanxxx) {
         CommandDispatcher<dd> _snowmanxxxxx = this.e.s.e.i();
         if (this.r == null) {
            this.r = _snowmanxxxxx.parse(_snowmanx, this.e.s.e.b());
         }

         int _snowmanxxxxxx = this.j ? _snowmanx.getCursor() : 1;
         if (_snowmanxxxx >= _snowmanxxxxxx && (this.t == null || !this.v)) {
            this.s = _snowmanxxxxx.getCompletionSuggestions(this.r, _snowmanxxxx);
            this.s.thenRun(() -> {
               if (this.s.isDone()) {
                  this.c();
               }
            });
         }
      } else {
         String _snowmanxxxxxx = _snowman.substring(0, _snowmanxxxx);
         int _snowmanxxxxxxx = a(_snowmanxxxxxx);
         Collection<String> _snowmanxxxxxxxx = this.e.s.e.b().l();
         this.s = dd.b(_snowmanxxxxxxxx, new SuggestionsBuilder(_snowmanxxxxxx, _snowmanxxxxxxx));
      }
   }

   private static int a(String var0) {
      if (Strings.isNullOrEmpty(_snowman)) {
         return 0;
      } else {
         int _snowman = 0;
         Matcher _snowmanx = a.matcher(_snowman);

         while (_snowmanx.find()) {
            _snowman = _snowmanx.end();
         }

         return _snowman;
      }
   }

   private static afa a(CommandSyntaxException var0) {
      nr _snowman = ns.a(_snowman.getRawMessage());
      String _snowmanx = _snowman.getContext();
      return _snowmanx == null ? _snowman.f() : new of("command.context.parse_error", _snowman, _snowman.getCursor(), _snowmanx).f();
   }

   private void c() {
      if (this.g.n() == this.g.b().length()) {
         if (this.s.join().isEmpty() && !this.r.getExceptions().isEmpty()) {
            int _snowman = 0;

            for (Entry<CommandNode<dd>, CommandSyntaxException> _snowmanx : this.r.getExceptions().entrySet()) {
               CommandSyntaxException _snowmanxx = _snowmanx.getValue();
               if (_snowmanxx.getType() == CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect()) {
                  _snowman++;
               } else {
                  this.o.add(a(_snowmanxx));
               }
            }

            if (_snowman > 0) {
               this.o.add(a(CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().create()));
            }
         } else if (this.r.getReader().canRead()) {
            this.o.add(a(dc.a(this.r)));
         }
      }

      this.p = 0;
      this.q = this.f.k;
      if (this.o.isEmpty()) {
         this.a(k.h);
      }

      this.t = null;
      if (this.u && this.e.k.K) {
         this.b(false);
      }
   }

   private void a(k var1) {
      CommandContextBuilder<dd> _snowman = this.r.getContext();
      SuggestionContext<dd> _snowmanx = _snowman.findSuggestionContext(this.g.n());
      Map<CommandNode<dd>, String> _snowmanxx = this.e.s.e.i().getSmartUsage(_snowmanx.parent, this.e.s.e.b());
      List<afa> _snowmanxxx = Lists.newArrayList();
      int _snowmanxxxx = 0;
      ob _snowmanxxxxx = ob.a.a(_snowman);

      for (Entry<CommandNode<dd>, String> _snowmanxxxxxx : _snowmanxx.entrySet()) {
         if (!(_snowmanxxxxxx.getKey() instanceof LiteralCommandNode)) {
            _snowmanxxx.add(afa.a(_snowmanxxxxxx.getValue(), _snowmanxxxxx));
            _snowmanxxxx = Math.max(_snowmanxxxx, this.h.b(_snowmanxxxxxx.getValue()));
         }
      }

      if (!_snowmanxxx.isEmpty()) {
         this.o.addAll(_snowmanxxx);
         this.p = afm.a(this.g.o(_snowmanx.startPos), 0, this.g.o(0) + this.g.o() - _snowmanxxxx);
         this.q = _snowmanxxxx;
      }
   }

   private afa a(String var1, int var2) {
      return this.r != null ? a(this.r, _snowman, _snowman) : afa.a(_snowman, ob.a);
   }

   @Nullable
   private static String b(String var0, String var1) {
      return _snowman.startsWith(_snowman) ? _snowman.substring(_snowman.length()) : null;
   }

   private static afa a(ParseResults<dd> var0, String var1, int var2) {
      List<afa> _snowman = Lists.newArrayList();
      int _snowmanx = 0;
      int _snowmanxx = -1;
      CommandContextBuilder<dd> _snowmanxxx = _snowman.getContext().getLastChild();

      for (ParsedArgument<dd, ?> _snowmanxxxx : _snowmanxxx.getArguments().values()) {
         if (++_snowmanxx >= d.size()) {
            _snowmanxx = 0;
         }

         int _snowmanxxxxx = Math.max(_snowmanxxxx.getRange().getStart() - _snowman, 0);
         if (_snowmanxxxxx >= _snowman.length()) {
            break;
         }

         int _snowmanxxxxxx = Math.min(_snowmanxxxx.getRange().getEnd() - _snowman, _snowman.length());
         if (_snowmanxxxxxx > 0) {
            _snowman.add(afa.a(_snowman.substring(_snowmanx, _snowmanxxxxx), c));
            _snowman.add(afa.a(_snowman.substring(_snowmanxxxxx, _snowmanxxxxxx), d.get(_snowmanxx)));
            _snowmanx = _snowmanxxxxxx;
         }
      }

      if (_snowman.getReader().canRead()) {
         int _snowmanxxxx = Math.max(_snowman.getReader().getCursor() - _snowman, 0);
         if (_snowmanxxxx < _snowman.length()) {
            int _snowmanxxxxxx = Math.min(_snowmanxxxx + _snowman.getReader().getRemainingLength(), _snowman.length());
            _snowman.add(afa.a(_snowman.substring(_snowmanx, _snowmanxxxx), c));
            _snowman.add(afa.a(_snowman.substring(_snowmanxxxx, _snowmanxxxxxx), b));
            _snowmanx = _snowmanxxxxxx;
         }
      }

      _snowman.add(afa.a(_snowman.substring(_snowmanx), c));
      return afa.a(_snowman);
   }

   public void a(dfm var1, int var2, int var3) {
      if (this.t != null) {
         this.t.a(_snowman, _snowman, _snowman);
      } else {
         int _snowman = 0;

         for (afa _snowmanx : this.o) {
            int _snowmanxx = this.m ? this.f.l - 14 - 13 - 12 * _snowman : 72 + 12 * _snowman;
            dkw.a(_snowman, this.p - 1, _snowmanxx, this.p + this.q + 1, _snowmanxx + 12, this.n);
            this.h.a(_snowman, _snowmanx, (float)this.p, (float)(_snowmanxx + 2), -1);
            _snowman++;
         }
      }
   }

   public String b() {
      return this.t != null ? "\n" + this.t.c() : "";
   }

   public class a {
      private final eal b;
      private final String c;
      private final List<Suggestion> d;
      private int e;
      private int f;
      private dcm g = dcm.a;
      private boolean h;
      private int i;

      private a(int var2, int var3, int var4, List<Suggestion> var5, boolean var6) {
         int _snowman = _snowman - 1;
         int _snowmanx = dlm.this.m ? _snowman - 3 - Math.min(_snowman.size(), dlm.this.l) * 12 : _snowman;
         this.b = new eal(_snowman, _snowmanx, _snowman + 1, Math.min(_snowman.size(), dlm.this.l) * 12);
         this.c = dlm.this.g.b();
         this.i = _snowman ? -1 : 0;
         this.d = _snowman;
         this.b(0);
      }

      public void a(dfm var1, int var2, int var3) {
         int _snowman = Math.min(this.d.size(), dlm.this.l);
         int _snowmanx = -5592406;
         boolean _snowmanxx = this.e > 0;
         boolean _snowmanxxx = this.d.size() > this.e + _snowman;
         boolean _snowmanxxxx = _snowmanxx || _snowmanxxx;
         boolean _snowmanxxxxx = this.g.i != (float)_snowman || this.g.j != (float)_snowman;
         if (_snowmanxxxxx) {
            this.g = new dcm((float)_snowman, (float)_snowman);
         }

         if (_snowmanxxxx) {
            dkw.a(_snowman, this.b.a(), this.b.b() - 1, this.b.a() + this.b.c(), this.b.b(), dlm.this.n);
            dkw.a(_snowman, this.b.a(), this.b.b() + this.b.d(), this.b.a() + this.b.c(), this.b.b() + this.b.d() + 1, dlm.this.n);
            if (_snowmanxx) {
               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < this.b.c(); _snowmanxxxxxx++) {
                  if (_snowmanxxxxxx % 2 == 0) {
                     dkw.a(_snowman, this.b.a() + _snowmanxxxxxx, this.b.b() - 1, this.b.a() + _snowmanxxxxxx + 1, this.b.b(), -1);
                  }
               }
            }

            if (_snowmanxxx) {
               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < this.b.c(); _snowmanxxxxxxx++) {
                  if (_snowmanxxxxxxx % 2 == 0) {
                     dkw.a(_snowman, this.b.a() + _snowmanxxxxxxx, this.b.b() + this.b.d(), this.b.a() + _snowmanxxxxxxx + 1, this.b.b() + this.b.d() + 1, -1);
                  }
               }
            }
         }

         boolean _snowmanxxxxxxxx = false;

         for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowman; _snowmanxxxxxxxxx++) {
            Suggestion _snowmanxxxxxxxxxx = this.d.get(_snowmanxxxxxxxxx + this.e);
            dkw.a(_snowman, this.b.a(), this.b.b() + 12 * _snowmanxxxxxxxxx, this.b.a() + this.b.c(), this.b.b() + 12 * _snowmanxxxxxxxxx + 12, dlm.this.n);
            if (_snowman > this.b.a() && _snowman < this.b.a() + this.b.c() && _snowman > this.b.b() + 12 * _snowmanxxxxxxxxx && _snowman < this.b.b() + 12 * _snowmanxxxxxxxxx + 12) {
               if (_snowmanxxxxx) {
                  this.b(_snowmanxxxxxxxxx + this.e);
               }

               _snowmanxxxxxxxx = true;
            }

            dlm.this.h
               .a(_snowman, _snowmanxxxxxxxxxx.getText(), (float)(this.b.a() + 1), (float)(this.b.b() + 2 + 12 * _snowmanxxxxxxxxx), _snowmanxxxxxxxxx + this.e == this.f ? -256 : -5592406);
         }

         if (_snowmanxxxxxxxx) {
            Message _snowmanxxxxxxxxx = this.d.get(this.f).getTooltip();
            if (_snowmanxxxxxxxxx != null) {
               dlm.this.f.b(_snowman, ns.a(_snowmanxxxxxxxxx), _snowman, _snowman);
            }
         }
      }

      public boolean a(int var1, int var2, int var3) {
         if (!this.b.b(_snowman, _snowman)) {
            return false;
         } else {
            int _snowman = (_snowman - this.b.b()) / 12 + this.e;
            if (_snowman >= 0 && _snowman < this.d.size()) {
               this.b(_snowman);
               this.a();
            }

            return true;
         }
      }

      public boolean a(double var1) {
         int _snowman = (int)(dlm.this.e.l.e() * (double)dlm.this.e.aD().o() / (double)dlm.this.e.aD().m());
         int _snowmanx = (int)(dlm.this.e.l.f() * (double)dlm.this.e.aD().p() / (double)dlm.this.e.aD().n());
         if (this.b.b(_snowman, _snowmanx)) {
            this.e = afm.a((int)((double)this.e - _snowman), 0, Math.max(this.d.size() - dlm.this.l, 0));
            return true;
         } else {
            return false;
         }
      }

      public boolean b(int var1, int var2, int var3) {
         if (_snowman == 265) {
            this.a(-1);
            this.h = false;
            return true;
         } else if (_snowman == 264) {
            this.a(1);
            this.h = false;
            return true;
         } else if (_snowman == 258) {
            if (this.h) {
               this.a(dot.y() ? -1 : 1);
            }

            this.a();
            return true;
         } else if (_snowman == 256) {
            this.b();
            return true;
         } else {
            return false;
         }
      }

      public void a(int var1) {
         this.b(this.f + _snowman);
         int _snowman = this.e;
         int _snowmanx = this.e + dlm.this.l - 1;
         if (this.f < _snowman) {
            this.e = afm.a(this.f, 0, Math.max(this.d.size() - dlm.this.l, 0));
         } else if (this.f > _snowmanx) {
            this.e = afm.a(this.f + dlm.this.k - dlm.this.l, 0, Math.max(this.d.size() - dlm.this.l, 0));
         }
      }

      public void b(int var1) {
         this.f = _snowman;
         if (this.f < 0) {
            this.f = this.f + this.d.size();
         }

         if (this.f >= this.d.size()) {
            this.f = this.f - this.d.size();
         }

         Suggestion _snowman = this.d.get(this.f);
         dlm.this.g.c(dlm.b(dlm.this.g.b(), _snowman.apply(this.c)));
         if (dkz.b.a() && this.i != this.f) {
            dkz.b.a(this.c());
         }
      }

      public void a() {
         Suggestion _snowman = this.d.get(this.f);
         dlm.this.v = true;
         dlm.this.g.a(_snowman.apply(this.c));
         int _snowmanx = _snowman.getRange().getStart() + _snowman.getText().length();
         dlm.this.g.j(_snowmanx);
         dlm.this.g.n(_snowmanx);
         this.b(this.f);
         dlm.this.v = false;
         this.h = true;
      }

      private String c() {
         this.i = this.f;
         Suggestion _snowman = this.d.get(this.f);
         Message _snowmanx = _snowman.getTooltip();
         return _snowmanx != null
            ? ekx.a("narration.suggestion.tooltip", this.f + 1, this.d.size(), _snowman.getText(), _snowmanx.getString())
            : ekx.a("narration.suggestion", this.f + 1, this.d.size(), _snowman.getText());
      }

      public void b() {
         dlm.this.t = null;
      }
   }
}
