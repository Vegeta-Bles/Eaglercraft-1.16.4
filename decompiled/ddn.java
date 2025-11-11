import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class ddn {
   private final Map<String, ddk> a = Maps.newHashMap();
   private final Map<ddq, List<ddk>> b = Maps.newHashMap();
   private final Map<String, Map<ddk, ddm>> c = Maps.newHashMap();
   private final ddk[] d = new ddk[19];
   private final Map<String, ddl> e = Maps.newHashMap();
   private final Map<String, ddl> f = Maps.newHashMap();
   private static String[] g;

   public ddn() {
   }

   public boolean b(String var1) {
      return this.a.containsKey(_snowman);
   }

   public ddk c(String var1) {
      return this.a.get(_snowman);
   }

   @Nullable
   public ddk d(@Nullable String var1) {
      return this.a.get(_snowman);
   }

   public ddk a(String var1, ddq var2, nr var3, ddq.a var4) {
      if (_snowman.length() > 16) {
         throw new IllegalArgumentException("The objective name '" + _snowman + "' is too long!");
      } else if (this.a.containsKey(_snowman)) {
         throw new IllegalArgumentException("An objective with the name '" + _snowman + "' already exists!");
      } else {
         ddk _snowman = new ddk(this, _snowman, _snowman, _snowman, _snowman);
         this.b.computeIfAbsent(_snowman, var0 -> Lists.newArrayList()).add(_snowman);
         this.a.put(_snowman, _snowman);
         this.a(_snowman);
         return _snowman;
      }
   }

   public final void a(ddq var1, String var2, Consumer<ddm> var3) {
      this.b.getOrDefault(_snowman, Collections.emptyList()).forEach(var3x -> _snowman.accept(this.c(_snowman, var3x)));
   }

   public boolean b(String var1, ddk var2) {
      Map<ddk, ddm> _snowman = this.c.get(_snowman);
      if (_snowman == null) {
         return false;
      } else {
         ddm _snowmanx = _snowman.get(_snowman);
         return _snowmanx != null;
      }
   }

   public ddm c(String var1, ddk var2) {
      if (_snowman.length() > 40) {
         throw new IllegalArgumentException("The player name '" + _snowman + "' is too long!");
      } else {
         Map<ddk, ddm> _snowman = this.c.computeIfAbsent(_snowman, var0 -> Maps.newHashMap());
         return _snowman.computeIfAbsent(_snowman, var2x -> {
            ddm _snowmanx = new ddm(this, var2x, _snowman);
            _snowmanx.c(0);
            return _snowmanx;
         });
      }
   }

   public Collection<ddm> i(ddk var1) {
      List<ddm> _snowman = Lists.newArrayList();

      for (Map<ddk, ddm> _snowmanx : this.c.values()) {
         ddm _snowmanxx = _snowmanx.get(_snowman);
         if (_snowmanxx != null) {
            _snowman.add(_snowmanxx);
         }
      }

      _snowman.sort(ddm.a);
      return _snowman;
   }

   public Collection<ddk> c() {
      return this.a.values();
   }

   public Collection<String> d() {
      return this.a.keySet();
   }

   public Collection<String> e() {
      return Lists.newArrayList(this.c.keySet());
   }

   public void d(String var1, @Nullable ddk var2) {
      if (_snowman == null) {
         Map<ddk, ddm> _snowman = this.c.remove(_snowman);
         if (_snowman != null) {
            this.a(_snowman);
         }
      } else {
         Map<ddk, ddm> _snowman = this.c.get(_snowman);
         if (_snowman != null) {
            ddm _snowmanx = _snowman.remove(_snowman);
            if (_snowman.size() < 1) {
               Map<ddk, ddm> _snowmanxx = this.c.remove(_snowman);
               if (_snowmanxx != null) {
                  this.a(_snowman);
               }
            } else if (_snowmanx != null) {
               this.a(_snowman, _snowman);
            }
         }
      }
   }

   public Map<ddk, ddm> e(String var1) {
      Map<ddk, ddm> _snowman = this.c.get(_snowman);
      if (_snowman == null) {
         _snowman = Maps.newHashMap();
      }

      return _snowman;
   }

   public void j(ddk var1) {
      this.a.remove(_snowman.b());

      for (int _snowman = 0; _snowman < 19; _snowman++) {
         if (this.a(_snowman) == _snowman) {
            this.a(_snowman, null);
         }
      }

      List<ddk> _snowmanx = this.b.get(_snowman.c());
      if (_snowmanx != null) {
         _snowmanx.remove(_snowman);
      }

      for (Map<ddk, ddm> _snowmanxx : this.c.values()) {
         _snowmanxx.remove(_snowman);
      }

      this.c(_snowman);
   }

   public void a(int var1, @Nullable ddk var2) {
      this.d[_snowman] = _snowman;
   }

   @Nullable
   public ddk a(int var1) {
      return this.d[_snowman];
   }

   public ddl f(String var1) {
      return this.e.get(_snowman);
   }

   public ddl g(String var1) {
      if (_snowman.length() > 16) {
         throw new IllegalArgumentException("The team name '" + _snowman + "' is too long!");
      } else {
         ddl _snowman = this.f(_snowman);
         if (_snowman != null) {
            throw new IllegalArgumentException("A team with the name '" + _snowman + "' already exists!");
         } else {
            _snowman = new ddl(this, _snowman);
            this.e.put(_snowman, _snowman);
            this.a(_snowman);
            return _snowman;
         }
      }
   }

   public void d(ddl var1) {
      this.e.remove(_snowman.b());

      for (String _snowman : _snowman.g()) {
         this.f.remove(_snowman);
      }

      this.c(_snowman);
   }

   public boolean a(String var1, ddl var2) {
      if (_snowman.length() > 40) {
         throw new IllegalArgumentException("The player name '" + _snowman + "' is too long!");
      } else {
         if (this.i(_snowman) != null) {
            this.h(_snowman);
         }

         this.f.put(_snowman, _snowman);
         return _snowman.g().add(_snowman);
      }
   }

   public boolean h(String var1) {
      ddl _snowman = this.i(_snowman);
      if (_snowman != null) {
         this.b(_snowman, _snowman);
         return true;
      } else {
         return false;
      }
   }

   public void b(String var1, ddl var2) {
      if (this.i(_snowman) != _snowman) {
         throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + _snowman.b() + "'.");
      } else {
         this.f.remove(_snowman);
         _snowman.g().remove(_snowman);
      }
   }

   public Collection<String> f() {
      return this.e.keySet();
   }

   public Collection<ddl> g() {
      return this.e.values();
   }

   @Nullable
   public ddl i(String var1) {
      return this.f.get(_snowman);
   }

   public void a(ddk var1) {
   }

   public void b(ddk var1) {
   }

   public void c(ddk var1) {
   }

   public void a(ddm var1) {
   }

   public void a(String var1) {
   }

   public void a(String var1, ddk var2) {
   }

   public void a(ddl var1) {
   }

   public void b(ddl var1) {
   }

   public void c(ddl var1) {
   }

   public static String b(int var0) {
      switch (_snowman) {
         case 0:
            return "list";
         case 1:
            return "sidebar";
         case 2:
            return "belowName";
         default:
            if (_snowman >= 3 && _snowman <= 18) {
               k _snowman = k.a(_snowman - 3);
               if (_snowman != null && _snowman != k.v) {
                  return "sidebar.team." + _snowman.f();
               }
            }

            return null;
      }
   }

   public static int j(String var0) {
      if ("list".equalsIgnoreCase(_snowman)) {
         return 0;
      } else if ("sidebar".equalsIgnoreCase(_snowman)) {
         return 1;
      } else if ("belowName".equalsIgnoreCase(_snowman)) {
         return 2;
      } else {
         if (_snowman.startsWith("sidebar.team.")) {
            String _snowman = _snowman.substring("sidebar.team.".length());
            k _snowmanx = k.b(_snowman);
            if (_snowmanx != null && _snowmanx.b() >= 0) {
               return _snowmanx.b() + 3;
            }
         }

         return -1;
      }
   }

   public static String[] h() {
      if (g == null) {
         g = new String[19];

         for (int _snowman = 0; _snowman < 19; _snowman++) {
            g[_snowman] = b(_snowman);
         }
      }

      return g;
   }

   public void a(aqa var1) {
      if (_snowman != null && !(_snowman instanceof bfw) && !_snowman.aX()) {
         String _snowman = _snowman.bT();
         this.d(_snowman, null);
         this.h(_snowman);
      }
   }

   protected mj i() {
      mj _snowman = new mj();
      this.c.values().stream().map(Map::values).forEach(var1x -> var1x.stream().filter(var0x -> var0x.d() != null).forEach(var1xx -> {
            md _snowmanx = new md();
            _snowmanx.a("Name", var1xx.e());
            _snowmanx.a("Objective", var1xx.d().b());
            _snowmanx.b("Score", var1xx.b());
            _snowmanx.a("Locked", var1xx.g());
            _snowman.add(_snowmanx);
         }));
      return _snowman;
   }

   protected void a(mj var1) {
      for (int _snowman = 0; _snowman < _snowman.size(); _snowman++) {
         md _snowmanx = _snowman.a(_snowman);
         ddk _snowmanxx = this.c(_snowmanx.l("Objective"));
         String _snowmanxxx = _snowmanx.l("Name");
         if (_snowmanxxx.length() > 40) {
            _snowmanxxx = _snowmanxxx.substring(0, 40);
         }

         ddm _snowmanxxxx = this.c(_snowmanxxx, _snowmanxx);
         _snowmanxxxx.c(_snowmanx.h("Score"));
         if (_snowmanx.e("Locked")) {
            _snowmanxxxx.a(_snowmanx.q("Locked"));
         }
      }
   }
}
