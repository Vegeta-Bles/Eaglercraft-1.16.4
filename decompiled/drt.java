import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;

public class drt {
   private final List<boq<?>> a;
   private final boolean b;
   private final Set<boq<?>> c = Sets.newHashSet();
   private final Set<boq<?>> d = Sets.newHashSet();
   private final Set<boq<?>> e = Sets.newHashSet();

   public drt(List<boq<?>> var1) {
      this.a = ImmutableList.copyOf(_snowman);
      if (_snowman.size() <= 1) {
         this.b = true;
      } else {
         this.b = a(_snowman);
      }
   }

   private static boolean a(List<boq<?>> var0) {
      int _snowman = _snowman.size();
      bmb _snowmanx = _snowman.get(0).c();

      for (int _snowmanxx = 1; _snowmanxx < _snowman; _snowmanxx++) {
         bmb _snowmanxxx = _snowman.get(_snowmanxx).c();
         if (!bmb.c(_snowmanx, _snowmanxxx) || !bmb.a(_snowmanx, _snowmanxxx)) {
            return false;
         }
      }

      return true;
   }

   public boolean a() {
      return !this.e.isEmpty();
   }

   public void a(adt var1) {
      for (boq<?> _snowman : this.a) {
         if (_snowman.b(_snowman)) {
            this.e.add(_snowman);
         }
      }
   }

   public void a(bfy var1, int var2, int var3, adt var4) {
      for (boq<?> _snowman : this.a) {
         boolean _snowmanx = _snowman.a(_snowman, _snowman) && _snowman.b(_snowman);
         if (_snowmanx) {
            this.d.add(_snowman);
         } else {
            this.d.remove(_snowman);
         }

         if (_snowmanx && _snowman.a(_snowman, null)) {
            this.c.add(_snowman);
         } else {
            this.c.remove(_snowman);
         }
      }
   }

   public boolean a(boq<?> var1) {
      return this.c.contains(_snowman);
   }

   public boolean b() {
      return !this.c.isEmpty();
   }

   public boolean c() {
      return !this.d.isEmpty();
   }

   public List<boq<?>> d() {
      return this.a;
   }

   public List<boq<?>> a(boolean var1) {
      List<boq<?>> _snowman = Lists.newArrayList();
      Set<boq<?>> _snowmanx = _snowman ? this.c : this.d;

      for (boq<?> _snowmanxx : this.a) {
         if (_snowmanx.contains(_snowmanxx)) {
            _snowman.add(_snowmanxx);
         }
      }

      return _snowman;
   }

   public List<boq<?>> b(boolean var1) {
      List<boq<?>> _snowman = Lists.newArrayList();

      for (boq<?> _snowmanx : this.a) {
         if (this.d.contains(_snowmanx) && this.c.contains(_snowmanx) == _snowman) {
            _snowman.add(_snowmanx);
         }
      }

      return _snowman;
   }

   public boolean e() {
      return this.b;
   }
}
