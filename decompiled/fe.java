import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class fe {
   private static final Map<String, fe.b> i = Maps.newHashMap();
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> new of("argument.entity.options.unknown", var0));
   public static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("argument.entity.options.inapplicable", var0));
   public static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new of("argument.entity.options.distance.negative"));
   public static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new of("argument.entity.options.level.negative"));
   public static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new of("argument.entity.options.limit.toosmall"));
   public static final DynamicCommandExceptionType f = new DynamicCommandExceptionType(var0 -> new of("argument.entity.options.sort.irreversible", var0));
   public static final DynamicCommandExceptionType g = new DynamicCommandExceptionType(var0 -> new of("argument.entity.options.mode.invalid", var0));
   public static final DynamicCommandExceptionType h = new DynamicCommandExceptionType(var0 -> new of("argument.entity.options.type.invalid", var0));

   private static void a(String var0, fe.a var1, Predicate<fd> var2, nr var3) {
      i.put(_snowman, new fe.b(_snowman, _snowman, _snowman));
   }

   public static void a() {
      if (i.isEmpty()) {
         a("name", var0 -> {
            int _snowman = var0.g().getCursor();
            boolean _snowmanx = var0.e();
            String _snowmanxx = var0.g().readString();
            if (var0.w() && !_snowmanx) {
               var0.g().setCursor(_snowman);
               throw b.createWithContext(var0.g(), "name");
            } else {
               if (_snowmanx) {
                  var0.c(true);
               } else {
                  var0.b(true);
               }

               var0.a(var2x -> var2x.R().getString().equals(_snowman) != _snowman);
            }
         }, var0 -> !var0.v(), new of("argument.entity.options.name.description"));
         a("distance", var0 -> {
            int _snowman = var0.g().getCursor();
            bz.c _snowmanx = bz.c.a(var0.g());
            if ((_snowmanx.a() == null || !((Float)_snowmanx.a() < 0.0F)) && (_snowmanx.b() == null || !((Float)_snowmanx.b() < 0.0F))) {
               var0.a(_snowmanx);
               var0.h();
            } else {
               var0.g().setCursor(_snowman);
               throw c.createWithContext(var0.g());
            }
         }, var0 -> var0.i().c(), new of("argument.entity.options.distance.description"));
         a("level", var0 -> {
            int _snowman = var0.g().getCursor();
            bz.d _snowmanx = bz.d.a(var0.g());
            if ((_snowmanx.a() == null || (Integer)_snowmanx.a() >= 0) && (_snowmanx.b() == null || (Integer)_snowmanx.b() >= 0)) {
               var0.a(_snowmanx);
               var0.a(false);
            } else {
               var0.g().setCursor(_snowman);
               throw d.createWithContext(var0.g());
            }
         }, var0 -> var0.j().c(), new of("argument.entity.options.level.description"));
         a("x", var0 -> {
            var0.h();
            var0.a(var0.g().readDouble());
         }, var0 -> var0.m() == null, new of("argument.entity.options.x.description"));
         a("y", var0 -> {
            var0.h();
            var0.b(var0.g().readDouble());
         }, var0 -> var0.n() == null, new of("argument.entity.options.y.description"));
         a("z", var0 -> {
            var0.h();
            var0.c(var0.g().readDouble());
         }, var0 -> var0.o() == null, new of("argument.entity.options.z.description"));
         a("dx", var0 -> {
            var0.h();
            var0.d(var0.g().readDouble());
         }, var0 -> var0.p() == null, new of("argument.entity.options.dx.description"));
         a("dy", var0 -> {
            var0.h();
            var0.e(var0.g().readDouble());
         }, var0 -> var0.q() == null, new of("argument.entity.options.dy.description"));
         a("dz", var0 -> {
            var0.h();
            var0.f(var0.g().readDouble());
         }, var0 -> var0.r() == null, new of("argument.entity.options.dz.description"));
         a("x_rotation", var0 -> var0.a(cu.a(var0.g(), true, afm::g)), var0 -> var0.k() == cu.a, new of("argument.entity.options.x_rotation.description"));
         a("y_rotation", var0 -> var0.b(cu.a(var0.g(), true, afm::g)), var0 -> var0.l() == cu.a, new of("argument.entity.options.y_rotation.description"));
         a("limit", var0 -> {
            int _snowman = var0.g().getCursor();
            int _snowmanx = var0.g().readInt();
            if (_snowmanx < 1) {
               var0.g().setCursor(_snowman);
               throw e.createWithContext(var0.g());
            } else {
               var0.a(_snowmanx);
               var0.d(true);
            }
         }, var0 -> !var0.u() && !var0.x(), new of("argument.entity.options.limit.description"));
         a("sort", var0 -> {
            int _snowman = var0.g().getCursor();
            String _snowmanx = var0.g().readUnquotedString();
            var0.a((var0x, var1x) -> dd.b(Arrays.asList("nearest", "furthest", "random", "arbitrary"), var0x));
            BiConsumer<dcn, List<? extends aqa>> _snowmanxx;
            switch (_snowmanx) {
               case "nearest":
                  _snowmanxx = fd.h;
                  break;
               case "furthest":
                  _snowmanxx = fd.i;
                  break;
               case "random":
                  _snowmanxx = fd.j;
                  break;
               case "arbitrary":
                  _snowmanxx = fd.g;
                  break;
               default:
                  var0.g().setCursor(_snowman);
                  throw f.createWithContext(var0.g(), _snowmanx);
            }

            var0.a(_snowmanxx);
            var0.e(true);
         }, var0 -> !var0.u() && !var0.y(), new of("argument.entity.options.sort.description"));
         a("gamemode", var0 -> {
            var0.a((var1x, var2x) -> {
               String _snowman = var1x.getRemaining().toLowerCase(Locale.ROOT);
               boolean _snowmanx = !var0.A();
               boolean _snowmanxx = true;
               if (!_snowman.isEmpty()) {
                  if (_snowman.charAt(0) == '!') {
                     _snowmanx = false;
                     _snowman = _snowman.substring(1);
                  } else {
                     _snowmanxx = false;
                  }
               }

               for (bru _snowmanxxx : bru.values()) {
                  if (_snowmanxxx != bru.a && _snowmanxxx.b().toLowerCase(Locale.ROOT).startsWith(_snowman)) {
                     if (_snowmanxx) {
                        var1x.suggest('!' + _snowmanxxx.b());
                     }

                     if (_snowmanx) {
                        var1x.suggest(_snowmanxxx.b());
                     }
                  }
               }

               return var1x.buildFuture();
            });
            int _snowman = var0.g().getCursor();
            boolean _snowmanx = var0.e();
            if (var0.A() && !_snowmanx) {
               var0.g().setCursor(_snowman);
               throw b.createWithContext(var0.g(), "gamemode");
            } else {
               String _snowmanxx = var0.g().readUnquotedString();
               bru _snowmanxxx = bru.a(_snowmanxx, bru.a);
               if (_snowmanxxx == bru.a) {
                  var0.g().setCursor(_snowman);
                  throw g.createWithContext(var0.g(), _snowmanxx);
               } else {
                  var0.a(false);
                  var0.a(var2x -> {
                     if (!(var2x instanceof aah)) {
                        return false;
                     } else {
                        bru _snowmanxxxx = ((aah)var2x).d.b();
                        return _snowman ? _snowmanxxxx != _snowman : _snowmanxxxx == _snowman;
                     }
                  });
                  if (_snowmanx) {
                     var0.g(true);
                  } else {
                     var0.f(true);
                  }
               }
            }
         }, var0 -> !var0.z(), new of("argument.entity.options.gamemode.description"));
         a("team", var0 -> {
            boolean _snowman = var0.e();
            String _snowmanx = var0.g().readUnquotedString();
            var0.a(var2x -> {
               if (!(var2x instanceof aqm)) {
                  return false;
               } else {
                  ddp _snowmanxx = var2x.bG();
                  String _snowmanx = _snowmanxx == null ? "" : _snowmanxx.b();
                  return _snowmanx.equals(_snowman) != _snowman;
               }
            });
            if (_snowman) {
               var0.i(true);
            } else {
               var0.h(true);
            }
         }, var0 -> !var0.B(), new of("argument.entity.options.team.description"));
         a("type", var0 -> {
            var0.a((var1x, var2x) -> {
               dd.a(gm.S.c(), var1x, String.valueOf('!'));
               dd.a(aee.a().b(), var1x, "!#");
               if (!var0.F()) {
                  dd.a(gm.S.c(), var1x);
                  dd.a(aee.a().b(), var1x, String.valueOf('#'));
               }

               return var1x.buildFuture();
            });
            int _snowman = var0.g().getCursor();
            boolean _snowmanx = var0.e();
            if (var0.F() && !_snowmanx) {
               var0.g().setCursor(_snowman);
               throw b.createWithContext(var0.g(), "type");
            } else {
               if (_snowmanx) {
                  var0.D();
               }

               if (var0.f()) {
                  vk _snowmanxx = vk.a(var0.g());
                  var0.a(var2x -> var2x.ch().aG().d().b(_snowman).a(var2x.X()) != _snowman);
               } else {
                  vk _snowmanxx = vk.a(var0.g());
                  aqe<?> _snowmanxxx = gm.S.b(_snowmanxx).orElseThrow(() -> {
                     var0.g().setCursor(_snowman);
                     return h.createWithContext(var0.g(), _snowman.toString());
                  });
                  if (Objects.equals(aqe.bc, _snowmanxxx) && !_snowmanx) {
                     var0.a(false);
                  }

                  var0.a(var2x -> Objects.equals(_snowman, var2x.X()) != _snowman);
                  if (!_snowmanx) {
                     var0.a(_snowmanxxx);
                  }
               }
            }
         }, var0 -> !var0.E(), new of("argument.entity.options.type.description"));
         a("tag", var0 -> {
            boolean _snowman = var0.e();
            String _snowmanx = var0.g().readUnquotedString();
            var0.a(var2x -> "".equals(_snowman) ? var2x.Z().isEmpty() != _snowman : var2x.Z().contains(_snowman) != _snowman);
         }, var0 -> true, new of("argument.entity.options.tag.description"));
         a("nbt", var0 -> {
            boolean _snowman = var0.e();
            md _snowmanx = new mu(var0.g()).f();
            var0.a(var2x -> {
               md _snowmanxx = var2x.e(new md());
               if (var2x instanceof aah) {
                  bmb _snowmanx = ((aah)var2x).bm.f();
                  if (!_snowmanx.a()) {
                     _snowmanxx.a("SelectedItem", _snowmanx.b(new md()));
                  }
               }

               return mp.a(_snowman, _snowmanxx, true) != _snowman;
            });
         }, var0 -> true, new of("argument.entity.options.nbt.description"));
         a("scores", var0 -> {
            StringReader _snowman = var0.g();
            Map<String, bz.d> _snowmanx = Maps.newHashMap();
            _snowman.expect('{');
            _snowman.skipWhitespace();

            while (_snowman.canRead() && _snowman.peek() != '}') {
               _snowman.skipWhitespace();
               String _snowmanxx = _snowman.readUnquotedString();
               _snowman.skipWhitespace();
               _snowman.expect('=');
               _snowman.skipWhitespace();
               bz.d _snowmanxxx = bz.d.a(_snowman);
               _snowmanx.put(_snowmanxx, _snowmanxxx);
               _snowman.skipWhitespace();
               if (_snowman.canRead() && _snowman.peek() == ',') {
                  _snowman.skip();
               }
            }

            _snowman.expect('}');
            if (!_snowmanx.isEmpty()) {
               var0.a(var1x -> {
                  ddn _snowmanxx = var1x.ch().aH();
                  String _snowmanx = var1x.bU();

                  for (Entry<String, bz.d> _snowmanxx : _snowman.entrySet()) {
                     ddk _snowmanxxx = _snowmanxx.d(_snowmanxx.getKey());
                     if (_snowmanxxx == null) {
                        return false;
                     }

                     if (!_snowmanxx.b(_snowmanx, _snowmanxxx)) {
                        return false;
                     }

                     ddm _snowmanxxxx = _snowmanxx.c(_snowmanx, _snowmanxxx);
                     int _snowmanxxxxx = _snowmanxxxx.b();
                     if (!_snowmanxx.getValue().d(_snowmanxxxxx)) {
                        return false;
                     }
                  }

                  return true;
               });
            }

            var0.j(true);
         }, var0 -> !var0.G(), new of("argument.entity.options.scores.description"));
         a("advancements", var0 -> {
            StringReader _snowman = var0.g();
            Map<vk, Predicate<aa>> _snowmanx = Maps.newHashMap();
            _snowman.expect('{');
            _snowman.skipWhitespace();

            while (_snowman.canRead() && _snowman.peek() != '}') {
               _snowman.skipWhitespace();
               vk _snowmanxx = vk.a(_snowman);
               _snowman.skipWhitespace();
               _snowman.expect('=');
               _snowman.skipWhitespace();
               if (_snowman.canRead() && _snowman.peek() == '{') {
                  Map<String, Predicate<ae>> _snowmanxxx = Maps.newHashMap();
                  _snowman.skipWhitespace();
                  _snowman.expect('{');
                  _snowman.skipWhitespace();

                  while (_snowman.canRead() && _snowman.peek() != '}') {
                     _snowman.skipWhitespace();
                     String _snowmanxxxx = _snowman.readUnquotedString();
                     _snowman.skipWhitespace();
                     _snowman.expect('=');
                     _snowman.skipWhitespace();
                     boolean _snowmanxxxxx = _snowman.readBoolean();
                     _snowmanxxx.put(_snowmanxxxx, var1x -> var1x.a() == _snowman);
                     _snowman.skipWhitespace();
                     if (_snowman.canRead() && _snowman.peek() == ',') {
                        _snowman.skip();
                     }
                  }

                  _snowman.skipWhitespace();
                  _snowman.expect('}');
                  _snowman.skipWhitespace();
                  _snowmanx.put(_snowmanxx, var1x -> {
                     for (Entry<String, Predicate<ae>> _snowmanxxxx : _snowman.entrySet()) {
                        ae _snowmanx = var1x.c(_snowmanxxxx.getKey());
                        if (_snowmanx == null || !_snowmanxxxx.getValue().test(_snowmanx)) {
                           return false;
                        }
                     }

                     return true;
                  });
               } else {
                  boolean _snowmanxxx = _snowman.readBoolean();
                  _snowmanx.put(_snowmanxx, var1x -> var1x.a() == _snowman);
               }

               _snowman.skipWhitespace();
               if (_snowman.canRead() && _snowman.peek() == ',') {
                  _snowman.skip();
               }
            }

            _snowman.expect('}');
            if (!_snowmanx.isEmpty()) {
               var0.a(var1x -> {
                  if (!(var1x instanceof aah)) {
                     return false;
                  } else {
                     aah _snowmanxxx = (aah)var1x;
                     vt _snowmanx = _snowmanxxx.J();
                     vv _snowmanxx = _snowmanxxx.ch().aA();

                     for (Entry<vk, Predicate<aa>> _snowmanxxx : _snowman.entrySet()) {
                        y _snowmanxxxx = _snowmanxx.a(_snowmanxxx.getKey());
                        if (_snowmanxxxx == null || !_snowmanxxx.getValue().test(_snowmanx.b(_snowmanxxxx))) {
                           return false;
                        }
                     }

                     return true;
                  }
               });
               var0.a(false);
            }

            var0.k(true);
         }, var0 -> !var0.H(), new of("argument.entity.options.advancements.description"));
         a("predicate", var0 -> {
            boolean _snowman = var0.e();
            vk _snowmanx = vk.a(var0.g());
            var0.a(var2x -> {
               if (!(var2x.l instanceof aag)) {
                  return false;
               } else {
                  aag _snowmanxx = (aag)var2x.l;
                  dbo _snowmanx = _snowmanxx.l().aK().a(_snowman);
                  if (_snowmanx == null) {
                     return false;
                  } else {
                     cyv _snowmanxx = new cyv.a(_snowmanxx).a(dbc.a, var2x).a(dbc.f, var2x.cA()).a(dbb.d);
                     return _snowman ^ _snowmanx.test(_snowmanxx);
                  }
               }
            });
         }, var0 -> true, new of("argument.entity.options.predicate.description"));
      }
   }

   public static fe.a a(fd var0, String var1, int var2) throws CommandSyntaxException {
      fe.b _snowman = i.get(_snowman);
      if (_snowman != null) {
         if (_snowman.b.test(_snowman)) {
            return _snowman.a;
         } else {
            throw b.createWithContext(_snowman.g(), _snowman);
         }
      } else {
         _snowman.g().setCursor(_snowman);
         throw a.createWithContext(_snowman.g(), _snowman);
      }
   }

   public static void a(fd var0, SuggestionsBuilder var1) {
      String _snowman = _snowman.getRemaining().toLowerCase(Locale.ROOT);

      for (Entry<String, fe.b> _snowmanx : i.entrySet()) {
         if (_snowmanx.getValue().b.test(_snowman) && _snowmanx.getKey().toLowerCase(Locale.ROOT).startsWith(_snowman)) {
            _snowman.suggest(_snowmanx.getKey() + '=', _snowmanx.getValue().c);
         }
      }
   }

   public interface a {
      void handle(fd var1) throws CommandSyntaxException;
   }

   static class b {
      public final fe.a a;
      public final Predicate<fd> b;
      public final nr c;

      private b(fe.a var1, Predicate<fd> var2, nr var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }
   }
}
