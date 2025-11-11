import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;

public class elx implements elo {
   protected final List<eba> a;
   protected final Map<gc, List<eba>> b;
   protected final boolean c;
   protected final boolean d;
   protected final boolean e;
   protected final ekc f;
   protected final ebm g;
   protected final ebk h;

   public elx(List<eba> var1, Map<gc, List<eba>> var2, boolean var3, boolean var4, boolean var5, ekc var6, ebm var7, ebk var8) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
   }

   @Override
   public List<eba> a(@Nullable ceh var1, @Nullable gc var2, Random var3) {
      return _snowman == null ? this.a : this.b.get(_snowman);
   }

   @Override
   public boolean a() {
      return this.c;
   }

   @Override
   public boolean b() {
      return this.d;
   }

   @Override
   public boolean c() {
      return this.e;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public ekc e() {
      return this.f;
   }

   @Override
   public ebm f() {
      return this.g;
   }

   @Override
   public ebk g() {
      return this.h;
   }

   public static class a {
      private final List<eba> a = Lists.newArrayList();
      private final Map<gc, List<eba>> b = Maps.newEnumMap(gc.class);
      private final ebk c;
      private final boolean d;
      private ekc e;
      private final boolean f;
      private final boolean g;
      private final ebm h;

      public a(ebf var1, ebk var2, boolean var3) {
         this(_snowman.b(), _snowman.c().a(), _snowman, _snowman.h(), _snowman);
      }

      private a(boolean var1, boolean var2, boolean var3, ebm var4, ebk var5) {
         for (gc _snowman : gc.values()) {
            this.b.put(_snowman, Lists.newArrayList());
         }

         this.c = _snowman;
         this.d = _snowman;
         this.f = _snowman;
         this.g = _snowman;
         this.h = _snowman;
      }

      public elx.a a(gc var1, eba var2) {
         this.b.get(_snowman).add(_snowman);
         return this;
      }

      public elx.a a(eba var1) {
         this.a.add(_snowman);
         return this;
      }

      public elx.a a(ekc var1) {
         this.e = _snowman;
         return this;
      }

      public elo b() {
         if (this.e == null) {
            throw new RuntimeException("Missing particle!");
         } else {
            return new elx(this.a, this.b, this.d, this.f, this.g, this.e, this.h, this.c);
         }
      }
   }
}
