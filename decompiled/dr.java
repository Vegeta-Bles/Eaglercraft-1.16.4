import com.google.common.collect.Lists;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class dr implements ArgumentType<dr.h> {
   private static final Collection<String> c = Arrays.asList("foo", "foo.bar", "foo[0]", "[0]", "[]", "{foo=bar}");
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("arguments.nbtpath.node.invalid"));
   public static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("arguments.nbtpath.nothing_found", var0));

   public dr() {
   }

   public static dr a() {
      return new dr();
   }

   public static dr.h a(CommandContext<db> var0, String var1) {
      return (dr.h)_snowman.getArgument(_snowman, dr.h.class);
   }

   public dr.h a(StringReader var1) throws CommandSyntaxException {
      List<dr.i> _snowman = Lists.newArrayList();
      int _snowmanx = _snowman.getCursor();
      Object2IntMap<dr.i> _snowmanxx = new Object2IntOpenHashMap();
      boolean _snowmanxxx = true;

      while (_snowman.canRead() && _snowman.peek() != ' ') {
         dr.i _snowmanxxxx = a(_snowman, _snowmanxxx);
         _snowman.add(_snowmanxxxx);
         _snowmanxx.put(_snowmanxxxx, _snowman.getCursor() - _snowmanx);
         _snowmanxxx = false;
         if (_snowman.canRead()) {
            char _snowmanxxxxx = _snowman.peek();
            if (_snowmanxxxxx != ' ' && _snowmanxxxxx != '[' && _snowmanxxxxx != '{') {
               _snowman.expect('.');
            }
         }
      }

      return new dr.h(_snowman.getString().substring(_snowmanx, _snowman.getCursor()), _snowman.toArray(new dr.i[0]), _snowmanxx);
   }

   private static dr.i a(StringReader var0, boolean var1) throws CommandSyntaxException {
      switch (_snowman.peek()) {
         case '"': {
            String _snowman = _snowman.readString();
            return a(_snowman, _snowman);
         }
         case '[': {
            _snowman.skip();
            int _snowman = _snowman.peek();
            if (_snowman == 123) {
               md _snowmanx = new mu(_snowman).f();
               _snowman.expect(']');
               return new dr.e(_snowmanx);
            } else {
               if (_snowman == 93) {
                  _snowman.skip();
                  return dr.a.a;
               }

               int _snowmanx = _snowman.readInt();
               _snowman.expect(']');
               return new dr.c(_snowmanx);
            }
         }
         case '{': {
            if (!_snowman) {
               throw a.createWithContext(_snowman);
            }

            md _snowman = new mu(_snowman).f();
            return new dr.g(_snowman);
         }
         default: {
            String _snowman = b(_snowman);
            return a(_snowman, _snowman);
         }
      }
   }

   private static dr.i a(StringReader var0, String var1) throws CommandSyntaxException {
      if (_snowman.canRead() && _snowman.peek() == '{') {
         md _snowman = new mu(_snowman).f();
         return new dr.f(_snowman, _snowman);
      } else {
         return new dr.b(_snowman);
      }
   }

   private static String b(StringReader var0) throws CommandSyntaxException {
      int _snowman = _snowman.getCursor();

      while (_snowman.canRead() && a(_snowman.peek())) {
         _snowman.skip();
      }

      if (_snowman.getCursor() == _snowman) {
         throw a.createWithContext(_snowman);
      } else {
         return _snowman.getString().substring(_snowman, _snowman.getCursor());
      }
   }

   public Collection<String> getExamples() {
      return c;
   }

   private static boolean a(char var0) {
      return _snowman != ' ' && _snowman != '"' && _snowman != '[' && _snowman != ']' && _snowman != '.' && _snowman != '{' && _snowman != '}';
   }

   private static Predicate<mt> b(md var0) {
      return var1 -> mp.a(_snowman, var1, true);
   }

   static class a implements dr.i {
      public static final dr.a a = new dr.a();

      private a() {
      }

      @Override
      public void a(mt var1, List<mt> var2) {
         if (_snowman instanceof mc) {
            _snowman.addAll((mc)_snowman);
         }
      }

      @Override
      public void a(mt var1, Supplier<mt> var2, List<mt> var3) {
         if (_snowman instanceof mc) {
            mc<?> _snowman = (mc<?>)_snowman;
            if (_snowman.isEmpty()) {
               mt _snowmanx = _snowman.get();
               if (_snowman.b(0, _snowmanx)) {
                  _snowman.add(_snowmanx);
               }
            } else {
               _snowman.addAll((Collection<? extends mt>)_snowman);
            }
         }
      }

      @Override
      public mt a() {
         return new mj();
      }

      @Override
      public int a(mt var1, Supplier<mt> var2) {
         if (!(_snowman instanceof mc)) {
            return 0;
         } else {
            mc<?> _snowman = (mc<?>)_snowman;
            int _snowmanx = _snowman.size();
            if (_snowmanx == 0) {
               _snowman.b(0, _snowman.get());
               return 1;
            } else {
               mt _snowmanxx = _snowman.get();
               int _snowmanxxx = _snowmanx - (int)_snowman.stream().filter(_snowmanxx::equals).count();
               if (_snowmanxxx == 0) {
                  return 0;
               } else {
                  _snowman.clear();
                  if (!_snowman.b(0, _snowmanxx)) {
                     return 0;
                  } else {
                     for (int _snowmanxxxx = 1; _snowmanxxxx < _snowmanx; _snowmanxxxx++) {
                        _snowman.b(_snowmanxxxx, _snowman.get());
                     }

                     return _snowmanxxx;
                  }
               }
            }
         }
      }

      @Override
      public int a(mt var1) {
         if (_snowman instanceof mc) {
            mc<?> _snowman = (mc<?>)_snowman;
            int _snowmanx = _snowman.size();
            if (_snowmanx > 0) {
               _snowman.clear();
               return _snowmanx;
            }
         }

         return 0;
      }
   }

   static class b implements dr.i {
      private final String a;

      public b(String var1) {
         this.a = _snowman;
      }

      @Override
      public void a(mt var1, List<mt> var2) {
         if (_snowman instanceof md) {
            mt _snowman = ((md)_snowman).c(this.a);
            if (_snowman != null) {
               _snowman.add(_snowman);
            }
         }
      }

      @Override
      public void a(mt var1, Supplier<mt> var2, List<mt> var3) {
         if (_snowman instanceof md) {
            md _snowman = (md)_snowman;
            mt _snowmanx;
            if (_snowman.e(this.a)) {
               _snowmanx = _snowman.c(this.a);
            } else {
               _snowmanx = _snowman.get();
               _snowman.a(this.a, _snowmanx);
            }

            _snowman.add(_snowmanx);
         }
      }

      @Override
      public mt a() {
         return new md();
      }

      @Override
      public int a(mt var1, Supplier<mt> var2) {
         if (_snowman instanceof md) {
            md _snowman = (md)_snowman;
            mt _snowmanx = _snowman.get();
            mt _snowmanxx = _snowman.a(this.a, _snowmanx);
            if (!_snowmanx.equals(_snowmanxx)) {
               return 1;
            }
         }

         return 0;
      }

      @Override
      public int a(mt var1) {
         if (_snowman instanceof md) {
            md _snowman = (md)_snowman;
            if (_snowman.e(this.a)) {
               _snowman.r(this.a);
               return 1;
            }
         }

         return 0;
      }
   }

   static class c implements dr.i {
      private final int a;

      public c(int var1) {
         this.a = _snowman;
      }

      @Override
      public void a(mt var1, List<mt> var2) {
         if (_snowman instanceof mc) {
            mc<?> _snowman = (mc<?>)_snowman;
            int _snowmanx = _snowman.size();
            int _snowmanxx = this.a < 0 ? _snowmanx + this.a : this.a;
            if (0 <= _snowmanxx && _snowmanxx < _snowmanx) {
               _snowman.add(_snowman.get(_snowmanxx));
            }
         }
      }

      @Override
      public void a(mt var1, Supplier<mt> var2, List<mt> var3) {
         this.a(_snowman, _snowman);
      }

      @Override
      public mt a() {
         return new mj();
      }

      @Override
      public int a(mt var1, Supplier<mt> var2) {
         if (_snowman instanceof mc) {
            mc<?> _snowman = (mc<?>)_snowman;
            int _snowmanx = _snowman.size();
            int _snowmanxx = this.a < 0 ? _snowmanx + this.a : this.a;
            if (0 <= _snowmanxx && _snowmanxx < _snowmanx) {
               mt _snowmanxxx = _snowman.get(_snowmanxx);
               mt _snowmanxxxx = _snowman.get();
               if (!_snowmanxxxx.equals(_snowmanxxx) && _snowman.a(_snowmanxx, _snowmanxxxx)) {
                  return 1;
               }
            }
         }

         return 0;
      }

      @Override
      public int a(mt var1) {
         if (_snowman instanceof mc) {
            mc<?> _snowman = (mc<?>)_snowman;
            int _snowmanx = _snowman.size();
            int _snowmanxx = this.a < 0 ? _snowmanx + this.a : this.a;
            if (0 <= _snowmanxx && _snowmanxx < _snowmanx) {
               _snowman.c(_snowmanxx);
               return 1;
            }
         }

         return 0;
      }
   }

   static class e implements dr.i {
      private final md a;
      private final Predicate<mt> b;

      public e(md var1) {
         this.a = _snowman;
         this.b = dr.b(_snowman);
      }

      @Override
      public void a(mt var1, List<mt> var2) {
         if (_snowman instanceof mj) {
            mj _snowman = (mj)_snowman;
            _snowman.stream().filter(this.b).forEach(_snowman::add);
         }
      }

      @Override
      public void a(mt var1, Supplier<mt> var2, List<mt> var3) {
         MutableBoolean _snowman = new MutableBoolean();
         if (_snowman instanceof mj) {
            mj _snowmanx = (mj)_snowman;
            _snowmanx.stream().filter(this.b).forEach(var2x -> {
               _snowman.add(var2x);
               _snowman.setTrue();
            });
            if (_snowman.isFalse()) {
               md _snowmanxx = this.a.g();
               _snowmanx.add(_snowmanxx);
               _snowman.add(_snowmanxx);
            }
         }
      }

      @Override
      public mt a() {
         return new mj();
      }

      @Override
      public int a(mt var1, Supplier<mt> var2) {
         int _snowman = 0;
         if (_snowman instanceof mj) {
            mj _snowmanx = (mj)_snowman;
            int _snowmanxx = _snowmanx.size();
            if (_snowmanxx == 0) {
               _snowmanx.add(_snowman.get());
               _snowman++;
            } else {
               for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
                  mt _snowmanxxxx = _snowmanx.k(_snowmanxxx);
                  if (this.b.test(_snowmanxxxx)) {
                     mt _snowmanxxxxx = _snowman.get();
                     if (!_snowmanxxxxx.equals(_snowmanxxxx) && _snowmanx.a(_snowmanxxx, _snowmanxxxxx)) {
                        _snowman++;
                     }
                  }
               }
            }
         }

         return _snowman;
      }

      @Override
      public int a(mt var1) {
         int _snowman = 0;
         if (_snowman instanceof mj) {
            mj _snowmanx = (mj)_snowman;

            for (int _snowmanxx = _snowmanx.size() - 1; _snowmanxx >= 0; _snowmanxx--) {
               if (this.b.test(_snowmanx.k(_snowmanxx))) {
                  _snowmanx.c(_snowmanxx);
                  _snowman++;
               }
            }
         }

         return _snowman;
      }
   }

   static class f implements dr.i {
      private final String a;
      private final md b;
      private final Predicate<mt> c;

      public f(String var1, md var2) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = dr.b(_snowman);
      }

      @Override
      public void a(mt var1, List<mt> var2) {
         if (_snowman instanceof md) {
            mt _snowman = ((md)_snowman).c(this.a);
            if (this.c.test(_snowman)) {
               _snowman.add(_snowman);
            }
         }
      }

      @Override
      public void a(mt var1, Supplier<mt> var2, List<mt> var3) {
         if (_snowman instanceof md) {
            md _snowman = (md)_snowman;
            mt _snowmanx = _snowman.c(this.a);
            if (_snowmanx == null) {
               mt var6 = this.b.g();
               _snowman.a(this.a, var6);
               _snowman.add(var6);
            } else if (this.c.test(_snowmanx)) {
               _snowman.add(_snowmanx);
            }
         }
      }

      @Override
      public mt a() {
         return new md();
      }

      @Override
      public int a(mt var1, Supplier<mt> var2) {
         if (_snowman instanceof md) {
            md _snowman = (md)_snowman;
            mt _snowmanx = _snowman.c(this.a);
            if (this.c.test(_snowmanx)) {
               mt _snowmanxx = _snowman.get();
               if (!_snowmanxx.equals(_snowmanx)) {
                  _snowman.a(this.a, _snowmanxx);
                  return 1;
               }
            }
         }

         return 0;
      }

      @Override
      public int a(mt var1) {
         if (_snowman instanceof md) {
            md _snowman = (md)_snowman;
            mt _snowmanx = _snowman.c(this.a);
            if (this.c.test(_snowmanx)) {
               _snowman.r(this.a);
               return 1;
            }
         }

         return 0;
      }
   }

   static class g implements dr.i {
      private final Predicate<mt> a;

      public g(md var1) {
         this.a = dr.b(_snowman);
      }

      @Override
      public void a(mt var1, List<mt> var2) {
         if (_snowman instanceof md && this.a.test(_snowman)) {
            _snowman.add(_snowman);
         }
      }

      @Override
      public void a(mt var1, Supplier<mt> var2, List<mt> var3) {
         this.a(_snowman, _snowman);
      }

      @Override
      public mt a() {
         return new md();
      }

      @Override
      public int a(mt var1, Supplier<mt> var2) {
         return 0;
      }

      @Override
      public int a(mt var1) {
         return 0;
      }
   }

   public static class h {
      private final String a;
      private final Object2IntMap<dr.i> b;
      private final dr.i[] c;

      public h(String var1, dr.i[] var2, Object2IntMap<dr.i> var3) {
         this.a = _snowman;
         this.c = _snowman;
         this.b = _snowman;
      }

      public List<mt> a(mt var1) throws CommandSyntaxException {
         List<mt> _snowman = Collections.singletonList(_snowman);

         for (dr.i _snowmanx : this.c) {
            _snowman = _snowmanx.a(_snowman);
            if (_snowman.isEmpty()) {
               throw this.a(_snowmanx);
            }
         }

         return _snowman;
      }

      public int b(mt var1) {
         List<mt> _snowman = Collections.singletonList(_snowman);

         for (dr.i _snowmanx : this.c) {
            _snowman = _snowmanx.a(_snowman);
            if (_snowman.isEmpty()) {
               return 0;
            }
         }

         return _snowman.size();
      }

      private List<mt> d(mt var1) throws CommandSyntaxException {
         List<mt> _snowman = Collections.singletonList(_snowman);

         for (int _snowmanx = 0; _snowmanx < this.c.length - 1; _snowmanx++) {
            dr.i _snowmanxx = this.c[_snowmanx];
            int _snowmanxxx = _snowmanx + 1;
            _snowman = _snowmanxx.a(_snowman, this.c[_snowmanxxx]::a);
            if (_snowman.isEmpty()) {
               throw this.a(_snowmanxx);
            }
         }

         return _snowman;
      }

      public List<mt> a(mt var1, Supplier<mt> var2) throws CommandSyntaxException {
         List<mt> _snowman = this.d(_snowman);
         dr.i _snowmanx = this.c[this.c.length - 1];
         return _snowmanx.a(_snowman, _snowman);
      }

      private static int a(List<mt> var0, Function<mt, Integer> var1) {
         return _snowman.stream().map(_snowman).reduce(0, (var0x, var1x) -> var0x + var1x);
      }

      public int b(mt var1, Supplier<mt> var2) throws CommandSyntaxException {
         List<mt> _snowman = this.d(_snowman);
         dr.i _snowmanx = this.c[this.c.length - 1];
         return a(_snowman, var2x -> _snowman.a(var2x, _snowman));
      }

      public int c(mt var1) {
         List<mt> _snowman = Collections.singletonList(_snowman);

         for (int _snowmanx = 0; _snowmanx < this.c.length - 1; _snowmanx++) {
            _snowman = this.c[_snowmanx].a(_snowman);
         }

         dr.i _snowmanx = this.c[this.c.length - 1];
         return a(_snowman, _snowmanx::a);
      }

      private CommandSyntaxException a(dr.i var1) {
         int _snowman = this.b.getInt(_snowman);
         return dr.b.create(this.a.substring(0, _snowman));
      }

      @Override
      public String toString() {
         return this.a;
      }
   }

   interface i {
      void a(mt var1, List<mt> var2);

      void a(mt var1, Supplier<mt> var2, List<mt> var3);

      mt a();

      int a(mt var1, Supplier<mt> var2);

      int a(mt var1);

      default List<mt> a(List<mt> var1) {
         return this.a(_snowman, this::a);
      }

      default List<mt> a(List<mt> var1, Supplier<mt> var2) {
         return this.a(_snowman, (var2x, var3) -> this.a(var2x, _snowman, var3));
      }

      default List<mt> a(List<mt> var1, BiConsumer<mt, List<mt>> var2) {
         List<mt> _snowman = Lists.newArrayList();

         for (mt _snowmanx : _snowman) {
            _snowman.accept(_snowmanx, _snowman);
         }

         return _snowman;
      }
   }
}
