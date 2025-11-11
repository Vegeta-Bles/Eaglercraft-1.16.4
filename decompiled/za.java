import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class za {
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new of("commands.data.merge.failed"));
   private static final DynamicCommandExceptionType e = new DynamicCommandExceptionType(var0 -> new of("commands.data.get.invalid", var0));
   private static final DynamicCommandExceptionType f = new DynamicCommandExceptionType(var0 -> new of("commands.data.get.unknown", var0));
   private static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(new of("commands.data.get.multiple"));
   private static final DynamicCommandExceptionType h = new DynamicCommandExceptionType(var0 -> new of("commands.data.modify.expected_list", var0));
   private static final DynamicCommandExceptionType i = new DynamicCommandExceptionType(var0 -> new of("commands.data.modify.expected_object", var0));
   private static final DynamicCommandExceptionType j = new DynamicCommandExceptionType(var0 -> new of("commands.data.modify.invalid_index", var0));
   public static final List<Function<String, za.c>> a = ImmutableList.of(zb.a, yy.a, zc.a);
   public static final List<za.c> b = a.stream().map(var0 -> var0.apply("target")).collect(ImmutableList.toImmutableList());
   public static final List<za.c> c = a.stream().map(var0 -> var0.apply("source")).collect(ImmutableList.toImmutableList());

   public static void a(CommandDispatcher<db> var0) {
      LiteralArgumentBuilder<db> _snowman = (LiteralArgumentBuilder<db>)dc.a("data").requires(var0x -> var0x.c(2));

      for (za.c _snowmanx : b) {
         ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)_snowman.then(
                     _snowmanx.a(
                        dc.a("merge"), var1x -> var1x.then(dc.a("nbt", dh.a()).executes(var1xx -> a((db)var1xx.getSource(), _snowman.a(var1xx), dh.a(var1xx, "nbt"))))
                     )
                  ))
                  .then(
                     _snowmanx.a(
                        dc.a("get"),
                        var1x -> var1x.executes(var1xx -> a((db)var1xx.getSource(), _snowman.a(var1xx)))
                              .then(
                                 ((RequiredArgumentBuilder)dc.a("path", dr.a())
                                       .executes(var1xx -> b((db)var1xx.getSource(), _snowman.a(var1xx), dr.a(var1xx, "path"))))
                                    .then(
                                       dc.a("scale", DoubleArgumentType.doubleArg())
                                          .executes(
                                             var1xx -> a(
                                                   (db)var1xx.getSource(), _snowman.a(var1xx), dr.a(var1xx, "path"), DoubleArgumentType.getDouble(var1xx, "scale")
                                                )
                                          )
                                    )
                              )
                     )
                  ))
               .then(
                  _snowmanx.a(
                     dc.a("remove"), var1x -> var1x.then(dc.a("path", dr.a()).executes(var1xx -> a((db)var1xx.getSource(), _snowman.a(var1xx), dr.a(var1xx, "path"))))
                  )
               ))
            .then(
               a(
                  (BiConsumer<ArgumentBuilder<db, ?>, za.b>)((var0x, var1x) -> var0x.then(
                           dc.a("insert").then(dc.a("index", IntegerArgumentType.integer()).then(var1x.create((var0xx, var1xx, var2, var3x) -> {
                              int _snowmanxx = IntegerArgumentType.getInteger(var0xx, "index");
                              return a(_snowmanxx, var1xx, var2, var3x);
                           })))
                        )
                        .then(dc.a("prepend").then(var1x.create((var0xx, var1xx, var2, var3x) -> a(0, var1xx, var2, var3x))))
                        .then(dc.a("append").then(var1x.create((var0xx, var1xx, var2, var3x) -> a(-1, var1xx, var2, var3x))))
                        .then(dc.a("set").then(var1x.create((var0xx, var1xx, var2, var3x) -> var2.b(var1xx, ((mt)Iterables.getLast(var3x))::c))))
                        .then(dc.a("merge").then(var1x.create((var0xx, var1xx, var2, var3x) -> {
                           Collection<mt> _snowmanxx = var2.a(var1xx, md::new);
                           int _snowmanx = 0;

                           for (mt _snowmanxx : _snowmanxx) {
                              if (!(_snowmanxx instanceof md)) {
                                 throw i.create(_snowmanxx);
                              }

                              md _snowmanxxx = (md)_snowmanxx;
                              md _snowmanxxxx = _snowmanxxx.g();

                              for (mt _snowmanxxxxx : var3x) {
                                 if (!(_snowmanxxxxx instanceof md)) {
                                    throw i.create(_snowmanxxxxx);
                                 }

                                 _snowmanxxx.a((md)_snowmanxxxxx);
                              }

                              _snowmanx += _snowmanxxxx.equals(_snowmanxxx) ? 0 : 1;
                           }

                           return _snowmanx;
                        }))))
               )
            );
      }

      _snowman.register(_snowman);
   }

   private static int a(int var0, md var1, dr.h var2, List<mt> var3) throws CommandSyntaxException {
      Collection<mt> _snowman = _snowman.a(_snowman, mj::new);
      int _snowmanx = 0;

      for (mt _snowmanxx : _snowman) {
         if (!(_snowmanxx instanceof mc)) {
            throw h.create(_snowmanxx);
         }

         boolean _snowmanxxx = false;
         mc<?> _snowmanxxxx = (mc<?>)_snowmanxx;
         int _snowmanxxxxx = _snowman < 0 ? _snowmanxxxx.size() + _snowman + 1 : _snowman;

         for (mt _snowmanxxxxxx : _snowman) {
            try {
               if (_snowmanxxxx.b(_snowmanxxxxx, _snowmanxxxxxx.c())) {
                  _snowmanxxxxx++;
                  _snowmanxxx = true;
               }
            } catch (IndexOutOfBoundsException var14) {
               throw j.create(_snowmanxxxxx);
            }
         }

         _snowmanx += _snowmanxxx ? 1 : 0;
      }

      return _snowmanx;
   }

   private static ArgumentBuilder<db, ?> a(BiConsumer<ArgumentBuilder<db, ?>, za.b> var0) {
      LiteralArgumentBuilder<db> _snowman = dc.a("modify");

      for (za.c _snowmanx : b) {
         _snowmanx.a(_snowman, var2 -> {
            ArgumentBuilder<db, ?> _snowmanxx = dc.a("targetPath", dr.a());

            for (za.c _snowmanx : c) {
               _snowman.accept(_snowmanxx, var2x -> _snowman.a(dc.a("from"), var3x -> var3x.executes(var3xx -> {
                        List<mt> _snowmanxx = Collections.singletonList(_snowman.a(var3xx).a());
                        return a(var3xx, _snowman, var2x, _snowmanxx);
                     }).then(dc.a("sourcePath", dr.a()).executes(var3xx -> {
                        yz _snowmanxx = _snowman.a(var3xx);
                        dr.h _snowmanx = dr.a(var3xx, "sourcePath");
                        List<mt> _snowmanxx = _snowmanx.a(_snowmanxx.a());
                        return a(var3xx, _snowman, var2x, _snowmanxx);
                     }))));
            }

            _snowman.accept(_snowmanxx, var1x -> dc.a("value").then(dc.a("value", ds.a()).executes(var2x -> {
                  List<mt> _snowmanx = Collections.singletonList(ds.a(var2x, "value"));
                  return a(var2x, _snowman, var1x, _snowmanx);
               })));
            return var2.then(_snowmanxx);
         });
      }

      return _snowman;
   }

   private static int a(CommandContext<db> var0, za.c var1, za.a var2, List<mt> var3) throws CommandSyntaxException {
      yz _snowman = _snowman.a(_snowman);
      dr.h _snowmanx = dr.a(_snowman, "targetPath");
      md _snowmanxx = _snowman.a();
      int _snowmanxxx = _snowman.modify(_snowman, _snowmanxx, _snowmanx, _snowman);
      if (_snowmanxxx == 0) {
         throw d.create();
      } else {
         _snowman.a(_snowmanxx);
         ((db)_snowman.getSource()).a(_snowman.b(), true);
         return _snowmanxxx;
      }
   }

   private static int a(db var0, yz var1, dr.h var2) throws CommandSyntaxException {
      md _snowman = _snowman.a();
      int _snowmanx = _snowman.c(_snowman);
      if (_snowmanx == 0) {
         throw d.create();
      } else {
         _snowman.a(_snowman);
         _snowman.a(_snowman.b(), true);
         return _snowmanx;
      }
   }

   private static mt a(dr.h var0, yz var1) throws CommandSyntaxException {
      Collection<mt> _snowman = _snowman.a(_snowman.a());
      Iterator<mt> _snowmanx = _snowman.iterator();
      mt _snowmanxx = _snowmanx.next();
      if (_snowmanx.hasNext()) {
         throw g.create();
      } else {
         return _snowmanxx;
      }
   }

   private static int b(db var0, yz var1, dr.h var2) throws CommandSyntaxException {
      mt _snowman = a(_snowman, _snowman);
      int _snowmanx;
      if (_snowman instanceof mq) {
         _snowmanx = afm.c(((mq)_snowman).i());
      } else if (_snowman instanceof mc) {
         _snowmanx = ((mc)_snowman).size();
      } else if (_snowman instanceof md) {
         _snowmanx = ((md)_snowman).e();
      } else {
         if (!(_snowman instanceof ms)) {
            throw f.create(_snowman.toString());
         }

         _snowmanx = _snowman.f_().length();
      }

      _snowman.a(_snowman.a(_snowman), false);
      return _snowmanx;
   }

   private static int a(db var0, yz var1, dr.h var2, double var3) throws CommandSyntaxException {
      mt _snowman = a(_snowman, _snowman);
      if (!(_snowman instanceof mq)) {
         throw e.create(_snowman.toString());
      } else {
         int _snowmanx = afm.c(((mq)_snowman).i() * _snowman);
         _snowman.a(_snowman.a(_snowman, _snowman, _snowmanx), false);
         return _snowmanx;
      }
   }

   private static int a(db var0, yz var1) throws CommandSyntaxException {
      _snowman.a(_snowman.a((mt)_snowman.a()), false);
      return 1;
   }

   private static int a(db var0, yz var1, md var2) throws CommandSyntaxException {
      md _snowman = _snowman.a();
      md _snowmanx = _snowman.g().a(_snowman);
      if (_snowman.equals(_snowmanx)) {
         throw d.create();
      } else {
         _snowman.a(_snowmanx);
         _snowman.a(_snowman.b(), true);
         return 1;
      }
   }

   interface a {
      int modify(CommandContext<db> var1, md var2, dr.h var3, List<mt> var4) throws CommandSyntaxException;
   }

   interface b {
      ArgumentBuilder<db, ?> create(za.a var1);
   }

   public interface c {
      yz a(CommandContext<db> var1) throws CommandSyntaxException;

      ArgumentBuilder<db, ?> a(ArgumentBuilder<db, ?> var1, Function<ArgumentBuilder<db, ?>, ArgumentBuilder<db, ?>> var2);
   }
}
