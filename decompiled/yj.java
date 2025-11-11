import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class yj {
   private static final Dynamic4CommandExceptionType a = new Dynamic4CommandExceptionType(
      (var0, var1, var2, var3) -> new of("commands.spreadplayers.failed.teams", var0, var1, var2, var3)
   );
   private static final Dynamic4CommandExceptionType b = new Dynamic4CommandExceptionType(
      (var0, var1, var2, var3) -> new of("commands.spreadplayers.failed.entities", var0, var1, var2, var3)
   );

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("spreadplayers").requires(var0x -> var0x.c(2)))
            .then(
               dc.a("center", eq.a())
                  .then(
                     dc.a("spreadDistance", FloatArgumentType.floatArg(0.0F))
                        .then(
                           ((RequiredArgumentBuilder)dc.a("maxRange", FloatArgumentType.floatArg(1.0F))
                                 .then(
                                    dc.a("respectTeams", BoolArgumentType.bool())
                                       .then(
                                          dc.a("targets", dk.b())
                                             .executes(
                                                var0x -> a(
                                                      (db)var0x.getSource(),
                                                      eq.a(var0x, "center"),
                                                      FloatArgumentType.getFloat(var0x, "spreadDistance"),
                                                      FloatArgumentType.getFloat(var0x, "maxRange"),
                                                      256,
                                                      BoolArgumentType.getBool(var0x, "respectTeams"),
                                                      dk.b(var0x, "targets")
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 dc.a("under")
                                    .then(
                                       dc.a("maxHeight", IntegerArgumentType.integer(0))
                                          .then(
                                             dc.a("respectTeams", BoolArgumentType.bool())
                                                .then(
                                                   dc.a("targets", dk.b())
                                                      .executes(
                                                         var0x -> a(
                                                               (db)var0x.getSource(),
                                                               eq.a(var0x, "center"),
                                                               FloatArgumentType.getFloat(var0x, "spreadDistance"),
                                                               FloatArgumentType.getFloat(var0x, "maxRange"),
                                                               IntegerArgumentType.getInteger(var0x, "maxHeight"),
                                                               BoolArgumentType.getBool(var0x, "respectTeams"),
                                                               dk.b(var0x, "targets")
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int a(db var0, dcm var1, float var2, float var3, int var4, boolean var5, Collection<? extends aqa> var6) throws CommandSyntaxException {
      Random _snowman = new Random();
      double _snowmanx = (double)(_snowman.i - _snowman);
      double _snowmanxx = (double)(_snowman.j - _snowman);
      double _snowmanxxx = (double)(_snowman.i + _snowman);
      double _snowmanxxxx = (double)(_snowman.j + _snowman);
      yj.a[] _snowmanxxxxx = a(_snowman, _snowman ? a(_snowman) : _snowman.size(), _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
      a(_snowman, (double)_snowman, _snowman.e(), _snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowman, _snowmanxxxxx, _snowman);
      double _snowmanxxxxxx = a(_snowman, _snowman.e(), _snowmanxxxxx, _snowman, _snowman);
      _snowman.a(new of("commands.spreadplayers.success." + (_snowman ? "teams" : "entities"), _snowmanxxxxx.length, _snowman.i, _snowman.j, String.format(Locale.ROOT, "%.2f", _snowmanxxxxxx)), true);
      return _snowmanxxxxx.length;
   }

   private static int a(Collection<? extends aqa> var0) {
      Set<ddp> _snowman = Sets.newHashSet();

      for (aqa _snowmanx : _snowman) {
         if (_snowmanx instanceof bfw) {
            _snowman.add(_snowmanx.bG());
         } else {
            _snowman.add(null);
         }
      }

      return _snowman.size();
   }

   private static void a(
      dcm var0, double var1, aag var3, Random var4, double var5, double var7, double var9, double var11, int var13, yj.a[] var14, boolean var15
   ) throws CommandSyntaxException {
      boolean _snowman = true;
      double _snowmanx = Float.MAX_VALUE;

      int _snowmanxx;
      for (_snowmanxx = 0; _snowmanxx < 10000 && _snowman; _snowmanxx++) {
         _snowman = false;
         _snowmanx = Float.MAX_VALUE;

         for (int _snowmanxxx = 0; _snowmanxxx < _snowman.length; _snowmanxxx++) {
            yj.a _snowmanxxxx = _snowman[_snowmanxxx];
            int _snowmanxxxxx = 0;
            yj.a _snowmanxxxxxx = new yj.a();

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowman.length; _snowmanxxxxxxx++) {
               if (_snowmanxxx != _snowmanxxxxxxx) {
                  yj.a _snowmanxxxxxxxx = _snowman[_snowmanxxxxxxx];
                  double _snowmanxxxxxxxxx = _snowmanxxxx.a(_snowmanxxxxxxxx);
                  _snowmanx = Math.min(_snowmanxxxxxxxxx, _snowmanx);
                  if (_snowmanxxxxxxxxx < _snowman) {
                     _snowmanxxxxx++;
                     _snowmanxxxxxx.a = _snowmanxxxxxx.a + (_snowmanxxxxxxxx.a - _snowmanxxxx.a);
                     _snowmanxxxxxx.b = _snowmanxxxxxx.b + (_snowmanxxxxxxxx.b - _snowmanxxxx.b);
                  }
               }
            }

            if (_snowmanxxxxx > 0) {
               _snowmanxxxxxx.a = _snowmanxxxxxx.a / (double)_snowmanxxxxx;
               _snowmanxxxxxx.b = _snowmanxxxxxx.b / (double)_snowmanxxxxx;
               double _snowmanxxxxxxxx = (double)_snowmanxxxxxx.b();
               if (_snowmanxxxxxxxx > 0.0) {
                  _snowmanxxxxxx.a();
                  _snowmanxxxx.b(_snowmanxxxxxx);
               } else {
                  _snowmanxxxx.a(_snowman, _snowman, _snowman, _snowman, _snowman);
               }

               _snowman = true;
            }

            if (_snowmanxxxx.a(_snowman, _snowman, _snowman, _snowman)) {
               _snowman = true;
            }
         }

         if (!_snowman) {
            for (yj.a _snowmanxxx : _snowman) {
               if (!_snowmanxxx.b(_snowman, _snowman)) {
                  _snowmanxxx.a(_snowman, _snowman, _snowman, _snowman, _snowman);
                  _snowman = true;
               }
            }
         }
      }

      if (_snowmanx == Float.MAX_VALUE) {
         _snowmanx = 0.0;
      }

      if (_snowmanxx >= 10000) {
         if (_snowman) {
            throw a.create(_snowman.length, _snowman.i, _snowman.j, String.format(Locale.ROOT, "%.2f", _snowmanx));
         } else {
            throw b.create(_snowman.length, _snowman.i, _snowman.j, String.format(Locale.ROOT, "%.2f", _snowmanx));
         }
      }
   }

   private static double a(Collection<? extends aqa> var0, aag var1, yj.a[] var2, int var3, boolean var4) {
      double _snowman = 0.0;
      int _snowmanx = 0;
      Map<ddp, yj.a> _snowmanxx = Maps.newHashMap();

      for (aqa _snowmanxxx : _snowman) {
         yj.a _snowmanxxxx;
         if (_snowman) {
            ddp _snowmanxxxxx = _snowmanxxx instanceof bfw ? _snowmanxxx.bG() : null;
            if (!_snowmanxx.containsKey(_snowmanxxxxx)) {
               _snowmanxx.put(_snowmanxxxxx, _snowman[_snowmanx++]);
            }

            _snowmanxxxx = _snowmanxx.get(_snowmanxxxxx);
         } else {
            _snowmanxxxx = _snowman[_snowmanx++];
         }

         _snowmanxxx.m((double)afm.c(_snowmanxxxx.a) + 0.5, (double)_snowmanxxxx.a(_snowman, _snowman), (double)afm.c(_snowmanxxxx.b) + 0.5);
         double _snowmanxxxxx = Double.MAX_VALUE;

         for (yj.a _snowmanxxxxxx : _snowman) {
            if (_snowmanxxxx != _snowmanxxxxxx) {
               double _snowmanxxxxxxx = _snowmanxxxx.a(_snowmanxxxxxx);
               _snowmanxxxxx = Math.min(_snowmanxxxxxxx, _snowmanxxxxx);
            }
         }

         _snowman += _snowmanxxxxx;
      }

      return _snowman.size() < 2 ? 0.0 : _snowman / (double)_snowman.size();
   }

   private static yj.a[] a(Random var0, int var1, double var2, double var4, double var6, double var8) {
      yj.a[] _snowman = new yj.a[_snowman];

      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         yj.a _snowmanxx = new yj.a();
         _snowmanxx.a(_snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman[_snowmanx] = _snowmanxx;
      }

      return _snowman;
   }

   static class a {
      private double a;
      private double b;

      a() {
      }

      double a(yj.a var1) {
         double _snowman = this.a - _snowman.a;
         double _snowmanx = this.b - _snowman.b;
         return Math.sqrt(_snowman * _snowman + _snowmanx * _snowmanx);
      }

      void a() {
         double _snowman = (double)this.b();
         this.a /= _snowman;
         this.b /= _snowman;
      }

      float b() {
         return afm.a(this.a * this.a + this.b * this.b);
      }

      public void b(yj.a var1) {
         this.a = this.a - _snowman.a;
         this.b = this.b - _snowman.b;
      }

      public boolean a(double var1, double var3, double var5, double var7) {
         boolean _snowman = false;
         if (this.a < _snowman) {
            this.a = _snowman;
            _snowman = true;
         } else if (this.a > _snowman) {
            this.a = _snowman;
            _snowman = true;
         }

         if (this.b < _snowman) {
            this.b = _snowman;
            _snowman = true;
         } else if (this.b > _snowman) {
            this.b = _snowman;
            _snowman = true;
         }

         return _snowman;
      }

      public int a(brc var1, int var2) {
         fx.a _snowman = new fx.a(this.a, (double)(_snowman + 1), this.b);
         boolean _snowmanx = _snowman.d_(_snowman).g();
         _snowman.c(gc.a);
         boolean _snowmanxx = _snowman.d_(_snowman).g();

         while (_snowman.v() > 0) {
            _snowman.c(gc.a);
            boolean _snowmanxxx = _snowman.d_(_snowman).g();
            if (!_snowmanxxx && _snowmanxx && _snowmanx) {
               return _snowman.v() + 1;
            }

            _snowmanx = _snowmanxx;
            _snowmanxx = _snowmanxxx;
         }

         return _snowman + 1;
      }

      public boolean b(brc var1, int var2) {
         fx _snowman = new fx(this.a, (double)(this.a(_snowman, _snowman) - 1), this.b);
         ceh _snowmanx = _snowman.d_(_snowman);
         cva _snowmanxx = _snowmanx.c();
         return _snowman.v() < _snowman && !_snowmanxx.a() && _snowmanxx != cva.n;
      }

      public void a(Random var1, double var2, double var4, double var6, double var8) {
         this.a = afm.a(_snowman, _snowman, _snowman);
         this.b = afm.a(_snowman, _snowman, _snowman);
      }
   }
}
