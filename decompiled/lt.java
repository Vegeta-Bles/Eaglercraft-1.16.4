import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;

public class lt {
   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a(
                                             "test"
                                          )
                                          .then(dc.a("runthis").executes(var0x -> a((db)var0x.getSource()))))
                                       .then(dc.a("runthese").executes(var0x -> b((db)var0x.getSource()))))
                                    .then(
                                       ((LiteralArgumentBuilder)dc.a("runfailed").executes(var0x -> a((db)var0x.getSource(), false, 0, 8)))
                                          .then(
                                             ((RequiredArgumentBuilder)dc.a("onlyRequiredTests", BoolArgumentType.bool())
                                                   .executes(var0x -> a((db)var0x.getSource(), BoolArgumentType.getBool(var0x, "onlyRequiredTests"), 0, 8)))
                                                .then(
                                                   ((RequiredArgumentBuilder)dc.a("rotationSteps", IntegerArgumentType.integer())
                                                         .executes(
                                                            var0x -> a(
                                                                  (db)var0x.getSource(),
                                                                  BoolArgumentType.getBool(var0x, "onlyRequiredTests"),
                                                                  IntegerArgumentType.getInteger(var0x, "rotationSteps"),
                                                                  8
                                                               )
                                                         ))
                                                      .then(
                                                         dc.a("testsPerRow", IntegerArgumentType.integer())
                                                            .executes(
                                                               var0x -> a(
                                                                     (db)var0x.getSource(),
                                                                     BoolArgumentType.getBool(var0x, "onlyRequiredTests"),
                                                                     IntegerArgumentType.getInteger(var0x, "rotationSteps"),
                                                                     IntegerArgumentType.getInteger(var0x, "testsPerRow")
                                                                  )
                                                            )
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    dc.a("run")
                                       .then(
                                          ((RequiredArgumentBuilder)dc.a("testName", lv.a())
                                                .executes(var0x -> a((db)var0x.getSource(), lv.a(var0x, "testName"), 0)))
                                             .then(
                                                dc.a("rotationSteps", IntegerArgumentType.integer())
                                                   .executes(
                                                      var0x -> a(
                                                            (db)var0x.getSource(),
                                                            lv.a(var0x, "testName"),
                                                            IntegerArgumentType.getInteger(var0x, "rotationSteps")
                                                         )
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 ((LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("runall").executes(var0x -> a((db)var0x.getSource(), 0, 8)))
                                       .then(
                                          ((RequiredArgumentBuilder)dc.a("testClassName", ls.a())
                                                .executes(var0x -> a((db)var0x.getSource(), ls.a(var0x, "testClassName"), 0, 8)))
                                             .then(
                                                ((RequiredArgumentBuilder)dc.a("rotationSteps", IntegerArgumentType.integer())
                                                      .executes(
                                                         var0x -> a(
                                                               (db)var0x.getSource(),
                                                               ls.a(var0x, "testClassName"),
                                                               IntegerArgumentType.getInteger(var0x, "rotationSteps"),
                                                               8
                                                            )
                                                      ))
                                                   .then(
                                                      dc.a("testsPerRow", IntegerArgumentType.integer())
                                                         .executes(
                                                            var0x -> a(
                                                                  (db)var0x.getSource(),
                                                                  ls.a(var0x, "testClassName"),
                                                                  IntegerArgumentType.getInteger(var0x, "rotationSteps"),
                                                                  IntegerArgumentType.getInteger(var0x, "testsPerRow")
                                                               )
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       ((RequiredArgumentBuilder)dc.a("rotationSteps", IntegerArgumentType.integer())
                                             .executes(var0x -> a((db)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "rotationSteps"), 8)))
                                          .then(
                                             dc.a("testsPerRow", IntegerArgumentType.integer())
                                                .executes(
                                                   var0x -> a(
                                                         (db)var0x.getSource(),
                                                         IntegerArgumentType.getInteger(var0x, "rotationSteps"),
                                                         IntegerArgumentType.getInteger(var0x, "testsPerRow")
                                                      )
                                                )
                                          )
                                    )
                              ))
                           .then(
                              dc.a("export")
                                 .then(
                                    dc.a("testName", StringArgumentType.word())
                                       .executes(var0x -> c((db)var0x.getSource(), StringArgumentType.getString(var0x, "testName")))
                                 )
                           ))
                        .then(dc.a("exportthis").executes(var0x -> c((db)var0x.getSource()))))
                     .then(
                        dc.a("import")
                           .then(
                              dc.a("testName", StringArgumentType.word())
                                 .executes(var0x -> d((db)var0x.getSource(), StringArgumentType.getString(var0x, "testName")))
                           )
                     ))
                  .then(
                     ((LiteralArgumentBuilder)dc.a("pos").executes(var0x -> a((db)var0x.getSource(), "pos")))
                        .then(dc.a("var", StringArgumentType.word()).executes(var0x -> a((db)var0x.getSource(), StringArgumentType.getString(var0x, "var"))))
                  ))
               .then(
                  dc.a("create")
                     .then(
                        ((RequiredArgumentBuilder)dc.a("testName", StringArgumentType.word())
                              .executes(var0x -> a((db)var0x.getSource(), StringArgumentType.getString(var0x, "testName"), 5, 5, 5)))
                           .then(
                              ((RequiredArgumentBuilder)dc.a("width", IntegerArgumentType.integer())
                                    .executes(
                                       var0x -> a(
                                             (db)var0x.getSource(),
                                             StringArgumentType.getString(var0x, "testName"),
                                             IntegerArgumentType.getInteger(var0x, "width"),
                                             IntegerArgumentType.getInteger(var0x, "width"),
                                             IntegerArgumentType.getInteger(var0x, "width")
                                          )
                                    ))
                                 .then(
                                    dc.a("height", IntegerArgumentType.integer())
                                       .then(
                                          dc.a("depth", IntegerArgumentType.integer())
                                             .executes(
                                                var0x -> a(
                                                      (db)var0x.getSource(),
                                                      StringArgumentType.getString(var0x, "testName"),
                                                      IntegerArgumentType.getInteger(var0x, "width"),
                                                      IntegerArgumentType.getInteger(var0x, "height"),
                                                      IntegerArgumentType.getInteger(var0x, "depth")
                                                   )
                                             )
                                       )
                                 )
                           )
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)dc.a("clearall").executes(var0x -> a((db)var0x.getSource(), 200)))
                  .then(
                     dc.a("radius", IntegerArgumentType.integer()).executes(var0x -> a((db)var0x.getSource(), IntegerArgumentType.getInteger(var0x, "radius")))
                  )
            )
      );
   }

   private static int a(db var0, String var1, int var2, int var3, int var4) {
      if (_snowman <= 48 && _snowman <= 48 && _snowman <= 48) {
         aag _snowman = _snowman.e();
         fx _snowmanx = new fx(_snowman.d());
         fx _snowmanxx = new fx(_snowmanx.u(), _snowman.e().a(chn.a.b, _snowmanx).v(), _snowmanx.w() + 3);
         lq.a(_snowman.toLowerCase(), _snowmanxx, new fx(_snowman, _snowman, _snowman), bzm.a, _snowman);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowman; _snowmanxxx++) {
            for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman; _snowmanxxxx++) {
               fx _snowmanxxxxx = new fx(_snowmanxx.u() + _snowmanxxx, _snowmanxx.v() + 1, _snowmanxx.w() + _snowmanxxxx);
               buo _snowmanxxxxxx = bup.h;
               ef _snowmanxxxxxxx = new ef(_snowmanxxxxxx.n(), Collections.EMPTY_SET, null);
               _snowmanxxxxxxx.a(_snowman, _snowmanxxxxx, 2);
            }
         }

         lq.a(_snowmanxx, new fx(1, 0, -1), bzm.a, _snowman);
         return 0;
      } else {
         throw new IllegalArgumentException("The structure must be less than 48 blocks big in each axis");
      }
   }

   private static int a(db var0, String var1) throws CommandSyntaxException {
      dcj _snowman = (dcj)_snowman.h().a(10.0, 1.0F, false);
      fx _snowmanx = _snowman.a();
      aag _snowmanxx = _snowman.e();
      Optional<fx> _snowmanxxx = lq.a(_snowmanx, 15, _snowmanxx);
      if (!_snowmanxxx.isPresent()) {
         _snowmanxxx = lq.a(_snowmanx, 200, _snowmanxx);
      }

      if (!_snowmanxxx.isPresent()) {
         _snowman.a(new oe("Can't find a structure block that contains the targeted pos " + _snowmanx));
         return 0;
      } else {
         cdj _snowmanxxxx = (cdj)_snowmanxx.c(_snowmanxxx.get());
         fx _snowmanxxxxx = _snowmanx.b(_snowmanxxx.get());
         String _snowmanxxxxxx = _snowmanxxxxx.u() + ", " + _snowmanxxxxx.v() + ", " + _snowmanxxxxx.w();
         String _snowmanxxxxxxx = _snowmanxxxx.f();
         nr _snowmanxxxxxxxx = new oe(_snowmanxxxxxx)
            .a(
               ob.a
                  .a(true)
                  .a(k.k)
                  .a(new nv(nv.a.a, new oe("Click to copy to clipboard")))
                  .a(new np(np.a.f, "final BlockPos " + _snowman + " = new BlockPos(" + _snowmanxxxxxx + ");"))
            );
         _snowman.a(new oe("Position relative to " + _snowmanxxxxxxx + ": ").a(_snowmanxxxxxxxx), false);
         rz.a(_snowmanxx, new fx(_snowmanx), _snowmanxxxxxx, -2147418368, 10000);
         return 1;
      }
   }

   private static int a(db var0) {
      fx _snowman = new fx(_snowman.d());
      aag _snowmanx = _snowman.e();
      fx _snowmanxx = lq.b(_snowman, 15, _snowmanx);
      if (_snowmanxx == null) {
         a(_snowmanx, "Couldn't find any structure block within 15 radius", k.m);
         return 0;
      } else {
         li.a(_snowmanx);
         a(_snowmanx, _snowmanxx, null);
         return 1;
      }
   }

   private static int b(db var0) {
      fx _snowman = new fx(_snowman.d());
      aag _snowmanx = _snowman.e();
      Collection<fx> _snowmanxx = lq.c(_snowman, 200, _snowmanx);
      if (_snowmanxx.isEmpty()) {
         a(_snowmanx, "Couldn't find any structure blocks within 200 block radius", k.m);
         return 1;
      } else {
         li.a(_snowmanx);
         b(_snowman, "Running " + _snowmanxx.size() + " tests...");
         lp _snowmanxxx = new lp();
         _snowmanxx.forEach(var2x -> a(_snowman, var2x, _snowman));
         return 1;
      }
   }

   private static void a(aag var0, fx var1, @Nullable lp var2) {
      cdj _snowman = (cdj)_snowman.c(_snowman);
      String _snowmanx = _snowman.f();
      lu _snowmanxx = lh.e(_snowmanx);
      lf _snowmanxxx = new lf(_snowmanxx, _snowman.l(), _snowman);
      if (_snowman != null) {
         _snowman.a(_snowmanxxx);
         _snowmanxxx.a(new lt.a(_snowman, _snowman));
      }

      a(_snowmanxx, _snowman);
      dci _snowmanxxxx = lq.a(_snowman);
      fx _snowmanxxxxx = new fx(_snowmanxxxx.a, _snowmanxxxx.b, _snowmanxxxx.c);
      li.a(_snowmanxxx, _snowmanxxxxx, ll.a);
   }

   private static void b(aag var0, lp var1) {
      if (_snowman.i()) {
         a(_snowman, "GameTest done! " + _snowman.h() + " tests were run", k.p);
         if (_snowman.d()) {
            a(_snowman, "" + _snowman.a() + " required tests failed :(", k.m);
         } else {
            a(_snowman, "All required tests passed :)", k.k);
         }

         if (_snowman.e()) {
            a(_snowman, "" + _snowman.b() + " optional tests failed", k.h);
         }
      }
   }

   private static int a(db var0, int var1) {
      aag _snowman = _snowman.e();
      li.a(_snowman);
      fx _snowmanx = new fx(_snowman.d().b, (double)_snowman.e().a(chn.a.b, new fx(_snowman.d())).v(), _snowman.d().d);
      li.a(_snowman, _snowmanx, ll.a, afm.a(_snowman, 0, 1024));
      return 1;
   }

   private static int a(db var0, lu var1, int var2) {
      aag _snowman = _snowman.e();
      fx _snowmanx = new fx(_snowman.d());
      int _snowmanxx = _snowman.e().a(chn.a.b, _snowmanx).v();
      fx _snowmanxxx = new fx(_snowmanx.u(), _snowmanxx, _snowmanx.w() + 3);
      li.a(_snowman);
      a(_snowman, _snowman);
      bzm _snowmanxxxx = lq.a(_snowman);
      lf _snowmanxxxxx = new lf(_snowman, _snowmanxxxx, _snowman);
      li.a(_snowmanxxxxx, _snowmanxxx, ll.a);
      return 1;
   }

   private static void a(lu var0, aag var1) {
      Consumer<aag> _snowman = lh.c(_snowman.e());
      if (_snowman != null) {
         _snowman.accept(_snowman);
      }
   }

   private static int a(db var0, int var1, int var2) {
      li.a(_snowman.e());
      Collection<lu> _snowman = lh.a();
      b(_snowman, "Running all " + _snowman.size() + " tests...");
      lh.d();
      a(_snowman, _snowman, _snowman, _snowman);
      return 1;
   }

   private static int a(db var0, String var1, int var2, int var3) {
      Collection<lu> _snowman = lh.a(_snowman);
      li.a(_snowman.e());
      b(_snowman, "Running " + _snowman.size() + " tests from " + _snowman + "...");
      lh.d();
      a(_snowman, _snowman, _snowman, _snowman);
      return 1;
   }

   private static int a(db var0, boolean var1, int var2, int var3) {
      Collection<lu> _snowman;
      if (_snowman) {
         _snowman = lh.c().stream().filter(lu::d).collect(Collectors.toList());
      } else {
         _snowman = lh.c();
      }

      if (_snowman.isEmpty()) {
         b(_snowman, "No failed tests to rerun");
         return 0;
      } else {
         li.a(_snowman.e());
         b(_snowman, "Rerunning " + _snowman.size() + " failed tests (" + (_snowman ? "only required tests" : "including optional tests") + ")");
         a(_snowman, _snowman, _snowman, _snowman);
         return 1;
      }
   }

   private static void a(db var0, Collection<lu> var1, int var2, int var3) {
      fx _snowman = new fx(_snowman.d());
      fx _snowmanx = new fx(_snowman.u(), _snowman.e().a(chn.a.b, _snowman).v(), _snowman.w() + 3);
      aag _snowmanxx = _snowman.e();
      bzm _snowmanxxx = lq.a(_snowman);
      Collection<lf> _snowmanxxxx = li.b(_snowman, _snowmanx, _snowmanxxx, _snowmanxx, ll.a, _snowman);
      lp _snowmanxxxxx = new lp(_snowmanxxxx);
      _snowmanxxxxx.a(new lt.a(_snowmanxx, _snowmanxxxxx));
      _snowmanxxxxx.a(var0x -> lh.a(var0x.u()));
   }

   private static void b(db var0, String var1) {
      _snowman.a(new oe(_snowman), false);
   }

   private static int c(db var0) {
      fx _snowman = new fx(_snowman.d());
      aag _snowmanx = _snowman.e();
      fx _snowmanxx = lq.b(_snowman, 15, _snowmanx);
      if (_snowmanxx == null) {
         a(_snowmanx, "Couldn't find any structure block within 15 radius", k.m);
         return 0;
      } else {
         cdj _snowmanxxx = (cdj)_snowmanx.c(_snowmanxx);
         String _snowmanxxxx = _snowmanxxx.f();
         return c(_snowman, _snowmanxxxx);
      }
   }

   private static int c(db var0, String var1) {
      Path _snowman = Paths.get(lq.a);
      vk _snowmanx = new vk("minecraft", _snowman);
      Path _snowmanxx = _snowman.e().n().a(_snowmanx, ".nbt");
      Path _snowmanxxx = jo.a(_snowmanxx, _snowman, _snowman);
      if (_snowmanxxx == null) {
         b(_snowman, "Failed to export " + _snowmanxx);
         return 1;
      } else {
         try {
            Files.createDirectories(_snowmanxxx.getParent());
         } catch (IOException var7) {
            b(_snowman, "Could not create folder " + _snowmanxxx.getParent());
            var7.printStackTrace();
            return 1;
         }

         b(_snowman, "Exported " + _snowman + " to " + _snowmanxxx.toAbsolutePath());
         return 0;
      }
   }

   private static int d(db var0, String var1) {
      Path _snowman = Paths.get(lq.a, _snowman + ".snbt");
      vk _snowmanx = new vk("minecraft", _snowman);
      Path _snowmanxx = _snowman.e().n().a(_snowmanx, ".nbt");

      try {
         BufferedReader _snowmanxxx = Files.newBufferedReader(_snowman);
         String _snowmanxxxx = IOUtils.toString(_snowmanxxx);
         Files.createDirectories(_snowmanxx.getParent());

         try (OutputStream _snowmanxxxxx = Files.newOutputStream(_snowmanxx)) {
            mn.a(mu.a(_snowmanxxxx), _snowmanxxxxx);
         }

         b(_snowman, "Imported to " + _snowmanxx.toAbsolutePath());
         return 0;
      } catch (CommandSyntaxException | IOException var20) {
         System.err.println("Failed to load structure " + _snowman);
         var20.printStackTrace();
         return 1;
      }
   }

   private static void a(aag var0, String var1, k var2) {
      _snowman.a(var0x -> true).forEach(var2x -> var2x.a(new oe(_snowman + _snowman), x.b));
   }

   static class a implements lg {
      private final aag a;
      private final lp b;

      public a(aag var1, lp var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      @Override
      public void a(lf var1) {
      }

      @Override
      public void c(lf var1) {
         lt.b(this.a, this.b);
      }
   }
}
