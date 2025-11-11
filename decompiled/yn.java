import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Set;

public class yn {
   private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("commands.tag.add.failed"));
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("commands.tag.remove.failed"));

   public static void a(CommandDispatcher<db> var0) {
      _snowman.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)dc.a("tag").requires(var0x -> var0x.c(2)))
            .then(
               ((RequiredArgumentBuilder)((RequiredArgumentBuilder)dc.a("targets", dk.b())
                        .then(
                           dc.a("add")
                              .then(
                                 dc.a("name", StringArgumentType.word())
                                    .executes(var0x -> a((db)var0x.getSource(), dk.b(var0x, "targets"), StringArgumentType.getString(var0x, "name")))
                              )
                        ))
                     .then(
                        dc.a("remove")
                           .then(
                              dc.a("name", StringArgumentType.word())
                                 .suggests((var0x, var1) -> dd.b(a(dk.b(var0x, "targets")), var1))
                                 .executes(var0x -> b((db)var0x.getSource(), dk.b(var0x, "targets"), StringArgumentType.getString(var0x, "name")))
                           )
                     ))
                  .then(dc.a("list").executes(var0x -> a((db)var0x.getSource(), dk.b(var0x, "targets"))))
            )
      );
   }

   private static Collection<String> a(Collection<? extends aqa> var0) {
      Set<String> _snowman = Sets.newHashSet();

      for (aqa _snowmanx : _snowman) {
         _snowman.addAll(_snowmanx.Z());
      }

      return _snowman;
   }

   private static int a(db var0, Collection<? extends aqa> var1, String var2) throws CommandSyntaxException {
      int _snowman = 0;

      for (aqa _snowmanx : _snowman) {
         if (_snowmanx.a(_snowman)) {
            _snowman++;
         }
      }

      if (_snowman == 0) {
         throw a.create();
      } else {
         if (_snowman.size() == 1) {
            _snowman.a(new of("commands.tag.add.success.single", _snowman, _snowman.iterator().next().d()), true);
         } else {
            _snowman.a(new of("commands.tag.add.success.multiple", _snowman, _snowman.size()), true);
         }

         return _snowman;
      }
   }

   private static int b(db var0, Collection<? extends aqa> var1, String var2) throws CommandSyntaxException {
      int _snowman = 0;

      for (aqa _snowmanx : _snowman) {
         if (_snowmanx.b(_snowman)) {
            _snowman++;
         }
      }

      if (_snowman == 0) {
         throw b.create();
      } else {
         if (_snowman.size() == 1) {
            _snowman.a(new of("commands.tag.remove.success.single", _snowman, _snowman.iterator().next().d()), true);
         } else {
            _snowman.a(new of("commands.tag.remove.success.multiple", _snowman, _snowman.size()), true);
         }

         return _snowman;
      }
   }

   private static int a(db var0, Collection<? extends aqa> var1) {
      Set<String> _snowman = Sets.newHashSet();

      for (aqa _snowmanx : _snowman) {
         _snowman.addAll(_snowmanx.Z());
      }

      if (_snowman.size() == 1) {
         aqa _snowmanx = _snowman.iterator().next();
         if (_snowman.isEmpty()) {
            _snowman.a(new of("commands.tag.list.single.empty", _snowmanx.d()), false);
         } else {
            _snowman.a(new of("commands.tag.list.single.success", _snowmanx.d(), _snowman.size(), ns.a(_snowman)), false);
         }
      } else if (_snowman.isEmpty()) {
         _snowman.a(new of("commands.tag.list.multiple.empty", _snowman.size()), false);
      } else {
         _snowman.a(new of("commands.tag.list.multiple.success", _snowman.size(), _snowman.size(), ns.a(_snowman)), false);
      }

      return _snowman.size();
   }
}
