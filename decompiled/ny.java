import com.google.common.base.Joiner;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ny extends nn implements nt {
   private static final Logger g = LogManager.getLogger();
   protected final boolean d;
   protected final String e;
   @Nullable
   protected final dr.h f;

   @Nullable
   private static dr.h d(String var0) {
      try {
         return new dr().a(new StringReader(_snowman));
      } catch (CommandSyntaxException var2) {
         return null;
      }
   }

   public ny(String var1, boolean var2) {
      this(_snowman, d(_snowman), _snowman);
   }

   protected ny(String var1, @Nullable dr.h var2, boolean var3) {
      this.e = _snowman;
      this.f = _snowman;
      this.d = _snowman;
   }

   protected abstract Stream<md> a(db var1) throws CommandSyntaxException;

   public String h() {
      return this.e;
   }

   public boolean i() {
      return this.d;
   }

   @Override
   public nx a(@Nullable db var1, @Nullable aqa var2, int var3) throws CommandSyntaxException {
      if (_snowman != null && this.f != null) {
         Stream<String> _snowman = this.a(_snowman).flatMap(var1x -> {
            try {
               return this.f.a(var1x).stream();
            } catch (CommandSyntaxException var3x) {
               return Stream.empty();
            }
         }).map(mt::f_);
         return (nx)(this.d ? _snowman.flatMap(var3x -> {
            try {
               nx _snowmanx = nr.a.a(var3x);
               return Stream.of(ns.a(_snowman, _snowmanx, _snowman, _snowman));
            } catch (Exception var5) {
               g.warn("Failed to parse component: " + var3x, var5);
               return Stream.of();
            }
         }).reduce((var0, var1x) -> var0.c(", ").a(var1x)).orElse(new oe("")) : new oe(Joiner.on(", ").join(_snowman.iterator())));
      } else {
         return new oe("");
      }
   }

   public static class a extends ny {
      private final String g;
      @Nullable
      private final em h;

      public a(String var1, boolean var2, String var3) {
         super(_snowman, _snowman);
         this.g = _snowman;
         this.h = this.d(this.g);
      }

      @Nullable
      private em d(String var1) {
         try {
            return ek.a().a(new StringReader(_snowman));
         } catch (CommandSyntaxException var3) {
            return null;
         }
      }

      private a(String var1, @Nullable dr.h var2, boolean var3, String var4, @Nullable em var5) {
         super(_snowman, _snowman, _snowman);
         this.g = _snowman;
         this.h = _snowman;
      }

      @Nullable
      public String j() {
         return this.g;
      }

      public ny.a k() {
         return new ny.a(this.e, this.f, this.d, this.g, this.h);
      }

      @Override
      protected Stream<md> a(db var1) {
         if (this.h != null) {
            aag _snowman = _snowman.e();
            fx _snowmanx = this.h.c(_snowman);
            if (_snowman.p(_snowmanx)) {
               ccj _snowmanxx = _snowman.c(_snowmanx);
               if (_snowmanxx != null) {
                  return Stream.of(_snowmanxx.a(new md()));
               }
            }
         }

         return Stream.empty();
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (!(_snowman instanceof ny.a)) {
            return false;
         } else {
            ny.a _snowman = (ny.a)_snowman;
            return Objects.equals(this.g, _snowman.g) && Objects.equals(this.e, _snowman.e) && super.equals(_snowman);
         }
      }

      @Override
      public String toString() {
         return "BlockPosArgument{pos='" + this.g + '\'' + "path='" + this.e + '\'' + ", siblings=" + this.a + ", style=" + this.c() + '}';
      }
   }

   public static class b extends ny {
      private final String g;
      @Nullable
      private final fc h;

      public b(String var1, boolean var2, String var3) {
         super(_snowman, _snowman);
         this.g = _snowman;
         this.h = d(_snowman);
      }

      @Nullable
      private static fc d(String var0) {
         try {
            fd _snowman = new fd(new StringReader(_snowman));
            return _snowman.t();
         } catch (CommandSyntaxException var2) {
            return null;
         }
      }

      private b(String var1, @Nullable dr.h var2, boolean var3, String var4, @Nullable fc var5) {
         super(_snowman, _snowman, _snowman);
         this.g = _snowman;
         this.h = _snowman;
      }

      public String j() {
         return this.g;
      }

      public ny.b k() {
         return new ny.b(this.e, this.f, this.d, this.g, this.h);
      }

      @Override
      protected Stream<md> a(db var1) throws CommandSyntaxException {
         if (this.h != null) {
            List<? extends aqa> _snowman = this.h.b(_snowman);
            return _snowman.stream().map(cb::b);
         } else {
            return Stream.empty();
         }
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (!(_snowman instanceof ny.b)) {
            return false;
         } else {
            ny.b _snowman = (ny.b)_snowman;
            return Objects.equals(this.g, _snowman.g) && Objects.equals(this.e, _snowman.e) && super.equals(_snowman);
         }
      }

      @Override
      public String toString() {
         return "EntityNbtComponent{selector='" + this.g + '\'' + "path='" + this.e + '\'' + ", siblings=" + this.a + ", style=" + this.c() + '}';
      }
   }

   public static class c extends ny {
      private final vk g;

      public c(String var1, boolean var2, vk var3) {
         super(_snowman, _snowman);
         this.g = _snowman;
      }

      public c(String var1, @Nullable dr.h var2, boolean var3, vk var4) {
         super(_snowman, _snowman, _snowman);
         this.g = _snowman;
      }

      public vk j() {
         return this.g;
      }

      public ny.c k() {
         return new ny.c(this.e, this.f, this.d, this.g);
      }

      @Override
      protected Stream<md> a(db var1) {
         md _snowman = _snowman.j().aI().a(this.g);
         return Stream.of(_snowman);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (!(_snowman instanceof ny.c)) {
            return false;
         } else {
            ny.c _snowman = (ny.c)_snowman;
            return Objects.equals(this.g, _snowman.g) && Objects.equals(this.e, _snowman.e) && super.equals(_snowman);
         }
      }

      @Override
      public String toString() {
         return "StorageNbtComponent{id='" + this.g + '\'' + "path='" + this.e + '\'' + ", siblings=" + this.a + ", style=" + this.c() + '}';
      }
   }
}
