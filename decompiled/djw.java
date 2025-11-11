import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class djw implements Comparable<djw> {
   private static final Map<String, djw> a = Maps.newHashMap();
   private static final Map<deo.a, djw> b = Maps.newHashMap();
   private static final Set<String> c = Sets.newHashSet();
   private static final Map<String, Integer> d = x.a(Maps.newHashMap(), var0 -> {
      var0.put("key.categories.movement", 1);
      var0.put("key.categories.gameplay", 2);
      var0.put("key.categories.inventory", 3);
      var0.put("key.categories.creative", 4);
      var0.put("key.categories.multiplayer", 5);
      var0.put("key.categories.ui", 6);
      var0.put("key.categories.misc", 7);
   });
   private final String e;
   private final deo.a f;
   private final String g;
   private deo.a h;
   private boolean i;
   private int j;

   public static void a(deo.a var0) {
      djw _snowman = b.get(_snowman);
      if (_snowman != null) {
         _snowman.j++;
      }
   }

   public static void a(deo.a var0, boolean var1) {
      djw _snowman = b.get(_snowman);
      if (_snowman != null) {
         _snowman.a(_snowman);
      }
   }

   public static void a() {
      for (djw _snowman : a.values()) {
         if (_snowman.h.a() == deo.b.a && _snowman.h.b() != deo.a.b()) {
            _snowman.a(deo.a(djz.C().aD().i(), _snowman.h.b()));
         }
      }
   }

   public static void b() {
      for (djw _snowman : a.values()) {
         _snowman.m();
      }
   }

   public static void c() {
      b.clear();

      for (djw _snowman : a.values()) {
         b.put(_snowman.h, _snowman);
      }
   }

   public djw(String var1, int var2, String var3) {
      this(_snowman, deo.b.a, _snowman, _snowman);
   }

   public djw(String var1, deo.b var2, int var3, String var4) {
      this.e = _snowman;
      this.h = _snowman.a(_snowman);
      this.f = this.h;
      this.g = _snowman;
      a.put(_snowman, this);
      b.put(this.h, this);
      c.add(_snowman);
   }

   public boolean d() {
      return this.i;
   }

   public String e() {
      return this.g;
   }

   public boolean f() {
      if (this.j == 0) {
         return false;
      } else {
         this.j--;
         return true;
      }
   }

   private void m() {
      this.j = 0;
      this.a(false);
   }

   public String g() {
      return this.e;
   }

   public deo.a h() {
      return this.f;
   }

   public void b(deo.a var1) {
      this.h = _snowman;
   }

   public int a(djw var1) {
      return this.g.equals(_snowman.g) ? ekx.a(this.e).compareTo(ekx.a(_snowman.e)) : d.get(this.g).compareTo(d.get(_snowman.g));
   }

   public static Supplier<nr> a(String var0) {
      djw _snowman = a.get(_snowman);
      return _snowman == null ? () -> new of(_snowman) : _snowman::j;
   }

   public boolean b(djw var1) {
      return this.h.equals(_snowman.h);
   }

   public boolean i() {
      return this.h.equals(deo.a);
   }

   public boolean a(int var1, int var2) {
      return _snowman == deo.a.b() ? this.h.a() == deo.b.b && this.h.b() == _snowman : this.h.a() == deo.b.a && this.h.b() == _snowman;
   }

   public boolean a(int var1) {
      return this.h.a() == deo.b.c && this.h.b() == _snowman;
   }

   public nr j() {
      return this.h.d();
   }

   public boolean k() {
      return this.h.equals(this.f);
   }

   public String l() {
      return this.h.c();
   }

   public void a(boolean var1) {
      this.i = _snowman;
   }
}
