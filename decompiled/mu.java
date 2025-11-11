import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.regex.Pattern;

public class mu {
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("argument.nbt.trailing"));
   public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("argument.nbt.expected.key"));
   public static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new of("argument.nbt.expected.value"));
   public static final Dynamic2CommandExceptionType d = new Dynamic2CommandExceptionType((var0, var1) -> new of("argument.nbt.list.mixed", var0, var1));
   public static final Dynamic2CommandExceptionType e = new Dynamic2CommandExceptionType((var0, var1) -> new of("argument.nbt.array.mixed", var0, var1));
   public static final DynamicCommandExceptionType f = new DynamicCommandExceptionType(var0 -> new of("argument.nbt.array.invalid", var0));
   private static final Pattern g = Pattern.compile("[-+]?(?:[0-9]+[.]|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?", 2);
   private static final Pattern h = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?d", 2);
   private static final Pattern i = Pattern.compile("[-+]?(?:[0-9]+[.]?|[0-9]*[.][0-9]+)(?:e[-+]?[0-9]+)?f", 2);
   private static final Pattern j = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)b", 2);
   private static final Pattern k = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)l", 2);
   private static final Pattern l = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)s", 2);
   private static final Pattern m = Pattern.compile("[-+]?(?:0|[1-9][0-9]*)");
   private final StringReader n;

   public static md a(String var0) throws CommandSyntaxException {
      return new mu(new StringReader(_snowman)).a();
   }

   @VisibleForTesting
   md a() throws CommandSyntaxException {
      md _snowman = this.f();
      this.n.skipWhitespace();
      if (this.n.canRead()) {
         throw a.createWithContext(this.n);
      } else {
         return _snowman;
      }
   }

   public mu(StringReader var1) {
      this.n = _snowman;
   }

   protected String b() throws CommandSyntaxException {
      this.n.skipWhitespace();
      if (!this.n.canRead()) {
         throw b.createWithContext(this.n);
      } else {
         return this.n.readString();
      }
   }

   protected mt c() throws CommandSyntaxException {
      this.n.skipWhitespace();
      int _snowman = this.n.getCursor();
      if (StringReader.isQuotedStringStart(this.n.peek())) {
         return ms.a(this.n.readQuotedString());
      } else {
         String _snowmanx = this.n.readUnquotedString();
         if (_snowmanx.isEmpty()) {
            this.n.setCursor(_snowman);
            throw c.createWithContext(this.n);
         } else {
            return this.b(_snowmanx);
         }
      }
   }

   private mt b(String var1) {
      try {
         if (i.matcher(_snowman).matches()) {
            return mg.a(Float.parseFloat(_snowman.substring(0, _snowman.length() - 1)));
         }

         if (j.matcher(_snowman).matches()) {
            return mb.a(Byte.parseByte(_snowman.substring(0, _snowman.length() - 1)));
         }

         if (k.matcher(_snowman).matches()) {
            return ml.a(Long.parseLong(_snowman.substring(0, _snowman.length() - 1)));
         }

         if (l.matcher(_snowman).matches()) {
            return mr.a(Short.parseShort(_snowman.substring(0, _snowman.length() - 1)));
         }

         if (m.matcher(_snowman).matches()) {
            return mi.a(Integer.parseInt(_snowman));
         }

         if (h.matcher(_snowman).matches()) {
            return me.a(Double.parseDouble(_snowman.substring(0, _snowman.length() - 1)));
         }

         if (g.matcher(_snowman).matches()) {
            return me.a(Double.parseDouble(_snowman));
         }

         if ("true".equalsIgnoreCase(_snowman)) {
            return mb.c;
         }

         if ("false".equalsIgnoreCase(_snowman)) {
            return mb.b;
         }
      } catch (NumberFormatException var3) {
      }

      return ms.a(_snowman);
   }

   public mt d() throws CommandSyntaxException {
      this.n.skipWhitespace();
      if (!this.n.canRead()) {
         throw c.createWithContext(this.n);
      } else {
         char _snowman = this.n.peek();
         if (_snowman == '{') {
            return this.f();
         } else {
            return _snowman == '[' ? this.e() : this.c();
         }
      }
   }

   protected mt e() throws CommandSyntaxException {
      return this.n.canRead(3) && !StringReader.isQuotedStringStart(this.n.peek(1)) && this.n.peek(2) == ';' ? this.h() : this.g();
   }

   public md f() throws CommandSyntaxException {
      this.a('{');
      md _snowman = new md();
      this.n.skipWhitespace();

      while (this.n.canRead() && this.n.peek() != '}') {
         int _snowmanx = this.n.getCursor();
         String _snowmanxx = this.b();
         if (_snowmanxx.isEmpty()) {
            this.n.setCursor(_snowmanx);
            throw b.createWithContext(this.n);
         }

         this.a(':');
         _snowman.a(_snowmanxx, this.d());
         if (!this.i()) {
            break;
         }

         if (!this.n.canRead()) {
            throw b.createWithContext(this.n);
         }
      }

      this.a('}');
      return _snowman;
   }

   private mt g() throws CommandSyntaxException {
      this.a('[');
      this.n.skipWhitespace();
      if (!this.n.canRead()) {
         throw c.createWithContext(this.n);
      } else {
         mj _snowman = new mj();
         mv<?> _snowmanx = null;

         while (this.n.peek() != ']') {
            int _snowmanxx = this.n.getCursor();
            mt _snowmanxxx = this.d();
            mv<?> _snowmanxxxx = _snowmanxxx.b();
            if (_snowmanx == null) {
               _snowmanx = _snowmanxxxx;
            } else if (_snowmanxxxx != _snowmanx) {
               this.n.setCursor(_snowmanxx);
               throw d.createWithContext(this.n, _snowmanxxxx.b(), _snowmanx.b());
            }

            _snowman.add(_snowmanxxx);
            if (!this.i()) {
               break;
            }

            if (!this.n.canRead()) {
               throw c.createWithContext(this.n);
            }
         }

         this.a(']');
         return _snowman;
      }
   }

   private mt h() throws CommandSyntaxException {
      this.a('[');
      int _snowman = this.n.getCursor();
      char _snowmanx = this.n.read();
      this.n.read();
      this.n.skipWhitespace();
      if (!this.n.canRead()) {
         throw c.createWithContext(this.n);
      } else if (_snowmanx == 'B') {
         return new ma(this.a(ma.a, mb.a));
      } else if (_snowmanx == 'L') {
         return new mk(this.a(mk.a, ml.a));
      } else if (_snowmanx == 'I') {
         return new mh(this.a(mh.a, mi.a));
      } else {
         this.n.setCursor(_snowman);
         throw f.createWithContext(this.n, String.valueOf(_snowmanx));
      }
   }

   private <T extends Number> List<T> a(mv<?> var1, mv<?> var2) throws CommandSyntaxException {
      List<T> _snowman = Lists.newArrayList();

      while (this.n.peek() != ']') {
         int _snowmanx = this.n.getCursor();
         mt _snowmanxx = this.d();
         mv<?> _snowmanxxx = _snowmanxx.b();
         if (_snowmanxxx != _snowman) {
            this.n.setCursor(_snowmanx);
            throw e.createWithContext(this.n, _snowmanxxx.b(), _snowman.b());
         }

         if (_snowman == mb.a) {
            _snowman.add((T)((mq)_snowmanxx).h());
         } else if (_snowman == ml.a) {
            _snowman.add((T)((mq)_snowmanxx).e());
         } else {
            _snowman.add((T)((mq)_snowmanxx).f());
         }

         if (!this.i()) {
            break;
         }

         if (!this.n.canRead()) {
            throw c.createWithContext(this.n);
         }
      }

      this.a(']');
      return _snowman;
   }

   private boolean i() {
      this.n.skipWhitespace();
      if (this.n.canRead() && this.n.peek() == ',') {
         this.n.skip();
         this.n.skipWhitespace();
         return true;
      } else {
         return false;
      }
   }

   private void a(char var1) throws CommandSyntaxException {
      this.n.skipWhitespace();
      this.n.expect(_snowman);
   }
}
