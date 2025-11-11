import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.List;

public class bhz {
   private final int a;
   private final float b;
   private final boolean c;
   private final boolean d;
   private final boolean e;
   private final List<Pair<apu, Float>> f;

   private bhz(int var1, float var2, boolean var3, boolean var4, boolean var5, List<Pair<apu, Float>> var6) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   public int a() {
      return this.a;
   }

   public float b() {
      return this.b;
   }

   public boolean c() {
      return this.c;
   }

   public boolean d() {
      return this.d;
   }

   public boolean e() {
      return this.e;
   }

   public List<Pair<apu, Float>> f() {
      return this.f;
   }

   public static class a {
      private int a;
      private float b;
      private boolean c;
      private boolean d;
      private boolean e;
      private final List<Pair<apu, Float>> f = Lists.newArrayList();

      public a() {
      }

      public bhz.a a(int var1) {
         this.a = _snowman;
         return this;
      }

      public bhz.a a(float var1) {
         this.b = _snowman;
         return this;
      }

      public bhz.a a() {
         this.c = true;
         return this;
      }

      public bhz.a b() {
         this.d = true;
         return this;
      }

      public bhz.a c() {
         this.e = true;
         return this;
      }

      public bhz.a a(apu var1, float var2) {
         this.f.add(Pair.of(_snowman, _snowman));
         return this;
      }

      public bhz d() {
         return new bhz(this.a, this.b, this.c, this.d, this.e, this.f);
      }
   }
}
