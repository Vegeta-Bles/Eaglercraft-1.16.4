import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;

public class ns {
   public static nx a(nx var0, ob var1) {
      if (_snowman.g()) {
         return _snowman;
      } else {
         ob _snowman = _snowman.c();
         if (_snowman.g()) {
            return _snowman.a(_snowman);
         } else {
            return _snowman.equals(_snowman) ? _snowman : _snowman.a(_snowman.a(_snowman));
         }
      }
   }

   public static nx a(@Nullable db var0, nr var1, @Nullable aqa var2, int var3) throws CommandSyntaxException {
      if (_snowman > 100) {
         return _snowman.e();
      } else {
         nx _snowman = _snowman instanceof nt ? ((nt)_snowman).a(_snowman, _snowman, _snowman + 1) : _snowman.g();

         for (nr _snowmanx : _snowman.b()) {
            _snowman.a(a(_snowman, _snowmanx, _snowman, _snowman + 1));
         }

         return _snowman.c(a(_snowman, _snowman.c(), _snowman, _snowman));
      }
   }

   private static ob a(@Nullable db var0, ob var1, @Nullable aqa var2, int var3) throws CommandSyntaxException {
      nv _snowman = _snowman.i();
      if (_snowman != null) {
         nr _snowmanx = _snowman.a(nv.a.a);
         if (_snowmanx != null) {
            nv _snowmanxx = new nv(nv.a.a, a(_snowman, _snowmanx, _snowman, _snowman + 1));
            return _snowman.a(_snowmanxx);
         }
      }

      return _snowman;
   }

   public static nr a(GameProfile var0) {
      if (_snowman.getName() != null) {
         return new oe(_snowman.getName());
      } else {
         return _snowman.getId() != null ? new oe(_snowman.getId().toString()) : new oe("(unknown)");
      }
   }

   public static nr a(Collection<String> var0) {
      return a(_snowman, var0x -> new oe(var0x).a(k.k));
   }

   public static <T extends Comparable<T>> nr a(Collection<T> var0, Function<T, nr> var1) {
      if (_snowman.isEmpty()) {
         return oe.d;
      } else if (_snowman.size() == 1) {
         return _snowman.apply(_snowman.iterator().next());
      } else {
         List<T> _snowman = Lists.newArrayList(_snowman);
         _snowman.sort(Comparable::compareTo);
         return b(_snowman, _snowman);
      }
   }

   public static <T> nx b(Collection<T> var0, Function<T, nr> var1) {
      if (_snowman.isEmpty()) {
         return new oe("");
      } else if (_snowman.size() == 1) {
         return _snowman.apply(_snowman.iterator().next()).e();
      } else {
         nx _snowman = new oe("");
         boolean _snowmanx = true;

         for (T _snowmanxx : _snowman) {
            if (!_snowmanx) {
               _snowman.a(new oe(", ").a(k.h));
            }

            _snowman.a(_snowman.apply(_snowmanxx));
            _snowmanx = false;
         }

         return _snowman;
      }
   }

   public static nx a(nr var0) {
      return new of("chat.square_brackets", _snowman);
   }

   public static nr a(Message var0) {
      return (nr)(_snowman instanceof nr ? (nr)_snowman : new oe(_snowman.getString()));
   }
}
