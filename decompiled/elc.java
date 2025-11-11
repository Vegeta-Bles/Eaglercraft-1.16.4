import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Set;

public class elc {
   public static final eld a = new eld();
   public static final elc b = new elc(Lists.newArrayList(), -1, -1, 1, false) {
      @Override
      public Pair<Integer, Integer> a(int var1, int var2) {
         return Pair.of(_snowman, _snowman);
      }
   };
   private final List<elb> c;
   private final int d;
   private final int e;
   private final int f;
   private final boolean g;

   public elc(List<elb> var1, int var2, int var3, int var4, boolean var5) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
   }

   private static boolean b(int var0, int var1) {
      return _snowman / _snowman * _snowman == _snowman;
   }

   public Pair<Integer, Integer> a(int var1, int var2) {
      Pair<Integer, Integer> _snowman = this.c(_snowman, _snowman);
      int _snowmanx = (Integer)_snowman.getFirst();
      int _snowmanxx = (Integer)_snowman.getSecond();
      if (b(_snowman, _snowmanx) && b(_snowman, _snowmanxx)) {
         return _snowman;
      } else {
         throw new IllegalArgumentException(String.format("Image size %s,%s is not multiply of frame size %s,%s", _snowman, _snowman, _snowmanx, _snowmanxx));
      }
   }

   private Pair<Integer, Integer> c(int var1, int var2) {
      if (this.d != -1) {
         return this.e != -1 ? Pair.of(this.d, this.e) : Pair.of(this.d, _snowman);
      } else if (this.e != -1) {
         return Pair.of(_snowman, this.e);
      } else {
         int _snowman = Math.min(_snowman, _snowman);
         return Pair.of(_snowman, _snowman);
      }
   }

   public int a(int var1) {
      return this.e == -1 ? _snowman : this.e;
   }

   public int b(int var1) {
      return this.d == -1 ? _snowman : this.d;
   }

   public int a() {
      return this.c.size();
   }

   public int b() {
      return this.f;
   }

   public boolean c() {
      return this.g;
   }

   private elb f(int var1) {
      return this.c.get(_snowman);
   }

   public int c(int var1) {
      elb _snowman = this.f(_snowman);
      return _snowman.a() ? this.f : _snowman.b();
   }

   public int e(int var1) {
      return this.c.get(_snowman).c();
   }

   public Set<Integer> d() {
      Set<Integer> _snowman = Sets.newHashSet();

      for (elb _snowmanx : this.c) {
         _snowman.add(_snowmanx.c());
      }

      return _snowman;
   }
}
