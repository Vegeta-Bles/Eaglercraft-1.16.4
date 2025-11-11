import com.google.common.collect.Lists;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class fc {
   private final int a;
   private final boolean b;
   private final boolean c;
   private final Predicate<aqa> d;
   private final bz.c e;
   private final Function<dcn, dcn> f;
   @Nullable
   private final dci g;
   private final BiConsumer<dcn, List<? extends aqa>> h;
   private final boolean i;
   @Nullable
   private final String j;
   @Nullable
   private final UUID k;
   @Nullable
   private final aqe<?> l;
   private final boolean m;

   public fc(
      int var1,
      boolean var2,
      boolean var3,
      Predicate<aqa> var4,
      bz.c var5,
      Function<dcn, dcn> var6,
      @Nullable dci var7,
      BiConsumer<dcn, List<? extends aqa>> var8,
      boolean var9,
      @Nullable String var10,
      @Nullable UUID var11,
      @Nullable aqe<?> var12,
      boolean var13
   ) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.m = _snowman;
   }

   public int a() {
      return this.a;
   }

   public boolean b() {
      return this.b;
   }

   public boolean c() {
      return this.i;
   }

   public boolean d() {
      return this.c;
   }

   private void e(db var1) throws CommandSyntaxException {
      if (this.m && !_snowman.c(2)) {
         throw dk.f.create();
      }
   }

   public aqa a(db var1) throws CommandSyntaxException {
      this.e(_snowman);
      List<? extends aqa> _snowman = this.b(_snowman);
      if (_snowman.isEmpty()) {
         throw dk.d.create();
      } else if (_snowman.size() > 1) {
         throw dk.a.create();
      } else {
         return _snowman.get(0);
      }
   }

   public List<? extends aqa> b(db var1) throws CommandSyntaxException {
      this.e(_snowman);
      if (!this.b) {
         return this.d(_snowman);
      } else if (this.j != null) {
         aah _snowman = _snowman.j().ae().a(this.j);
         return (List<? extends aqa>)(_snowman == null ? Collections.emptyList() : Lists.newArrayList(new aah[]{_snowman}));
      } else if (this.k != null) {
         for (aag _snowman : _snowman.j().G()) {
            aqa _snowmanx = _snowman.a(this.k);
            if (_snowmanx != null) {
               return Lists.newArrayList(new aqa[]{_snowmanx});
            }
         }

         return Collections.emptyList();
      } else {
         dcn _snowmanx = this.f.apply(_snowman.d());
         Predicate<aqa> _snowmanxx = this.a(_snowmanx);
         if (this.i) {
            return (List<? extends aqa>)(_snowman.f() != null && _snowmanxx.test(_snowman.f()) ? Lists.newArrayList(new aqa[]{_snowman.f()}) : Collections.emptyList());
         } else {
            List<aqa> _snowmanxxx = Lists.newArrayList();
            if (this.d()) {
               this.a(_snowmanxxx, _snowman.e(), _snowmanx, _snowmanxx);
            } else {
               for (aag _snowmanxxxx : _snowman.j().G()) {
                  this.a(_snowmanxxx, _snowmanxxxx, _snowmanx, _snowmanxx);
               }
            }

            return this.a(_snowmanx, _snowmanxxx);
         }
      }
   }

   private void a(List<aqa> var1, aag var2, dcn var3, Predicate<aqa> var4) {
      if (this.g != null) {
         _snowman.addAll(_snowman.a((aqe<? extends aqa>)this.l, this.g.c(_snowman), _snowman));
      } else {
         _snowman.addAll(_snowman.a(this.l, _snowman));
      }
   }

   public aah c(db var1) throws CommandSyntaxException {
      this.e(_snowman);
      List<aah> _snowman = this.d(_snowman);
      if (_snowman.size() != 1) {
         throw dk.e.create();
      } else {
         return _snowman.get(0);
      }
   }

   public List<aah> d(db var1) throws CommandSyntaxException {
      this.e(_snowman);
      if (this.j != null) {
         aah _snowman = _snowman.j().ae().a(this.j);
         return (List<aah>)(_snowman == null ? Collections.emptyList() : Lists.newArrayList(new aah[]{_snowman}));
      } else if (this.k != null) {
         aah _snowman = _snowman.j().ae().a(this.k);
         return (List<aah>)(_snowman == null ? Collections.emptyList() : Lists.newArrayList(new aah[]{_snowman}));
      } else {
         dcn _snowman = this.f.apply(_snowman.d());
         Predicate<aqa> _snowmanx = this.a(_snowman);
         if (this.i) {
            if (_snowman.f() instanceof aah) {
               aah _snowmanxx = (aah)_snowman.f();
               if (_snowmanx.test(_snowmanxx)) {
                  return Lists.newArrayList(new aah[]{_snowmanxx});
               }
            }

            return Collections.emptyList();
         } else {
            List<aah> _snowmanxx;
            if (this.d()) {
               _snowmanxx = _snowman.e().a(_snowmanx::test);
            } else {
               _snowmanxx = Lists.newArrayList();

               for (aah _snowmanxxx : _snowman.j().ae().s()) {
                  if (_snowmanx.test(_snowmanxxx)) {
                     _snowmanxx.add(_snowmanxxx);
                  }
               }
            }

            return this.a(_snowman, _snowmanxx);
         }
      }
   }

   private Predicate<aqa> a(dcn var1) {
      Predicate<aqa> _snowman = this.d;
      if (this.g != null) {
         dci _snowmanx = this.g.c(_snowman);
         _snowman = _snowman.and(var1x -> _snowman.c(var1x.cc()));
      }

      if (!this.e.c()) {
         _snowman = _snowman.and(var2x -> this.e.a(var2x.e(_snowman)));
      }

      return _snowman;
   }

   private <T extends aqa> List<T> a(dcn var1, List<T> var2) {
      if (_snowman.size() > 1) {
         this.h.accept(_snowman, _snowman);
      }

      return _snowman.subList(0, Math.min(this.a, _snowman.size()));
   }

   public static nx a(List<? extends aqa> var0) {
      return ns.b(_snowman, aqa::d);
   }
}
