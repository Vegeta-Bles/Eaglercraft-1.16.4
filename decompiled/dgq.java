import com.google.common.base.Joiner;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dgq extends dhc {
   private static final Logger s = LogManager.getLogger();
   public long a;
   public String b;
   public String c;
   public String d;
   public dgq.b e;
   public String f;
   public String g;
   public List<dgn> h;
   public Map<Integer, dgw> i;
   public boolean j;
   public boolean k;
   public int l;
   public dgq.c m;
   public int n;
   public String o;
   public int p;
   public String q;
   public dgt r = new dgt();

   public dgq() {
   }

   public String a() {
      return this.d;
   }

   public String b() {
      return this.c;
   }

   public String c() {
      return this.o;
   }

   public void a(String var1) {
      this.c = _snowman;
   }

   public void b(String var1) {
      this.d = _snowman;
   }

   public void a(dgu var1) {
      List<String> _snowman = Lists.newArrayList();
      int _snowmanx = 0;

      for (String _snowmanxx : _snowman.b) {
         if (!_snowmanxx.equals(djz.C().J().b())) {
            String _snowmanxxx = "";

            try {
               _snowmanxxx = dis.a(_snowmanxx);
            } catch (Exception var8) {
               s.error("Could not get name for " + _snowmanxx, var8);
               continue;
            }

            _snowman.add(_snowmanxxx);
            _snowmanx++;
         }
      }

      this.r.a = String.valueOf(_snowmanx);
      this.r.b = Joiner.on('\n').join(_snowman);
   }

   public static dgq a(JsonObject var0) {
      dgq _snowman = new dgq();

      try {
         _snowman.a = dip.a("id", _snowman, -1L);
         _snowman.b = dip.a("remoteSubscriptionId", _snowman, null);
         _snowman.c = dip.a("name", _snowman, null);
         _snowman.d = dip.a("motd", _snowman, null);
         _snowman.e = e(dip.a("state", _snowman, dgq.b.a.name()));
         _snowman.f = dip.a("owner", _snowman, null);
         if (_snowman.get("players") != null && _snowman.get("players").isJsonArray()) {
            _snowman.h = a(_snowman.get("players").getAsJsonArray());
            a(_snowman);
         } else {
            _snowman.h = Lists.newArrayList();
         }

         _snowman.l = dip.a("daysLeft", _snowman, 0);
         _snowman.j = dip.a("expired", _snowman, false);
         _snowman.k = dip.a("expiredTrial", _snowman, false);
         _snowman.m = f(dip.a("worldType", _snowman, dgq.c.a.name()));
         _snowman.g = dip.a("ownerUUID", _snowman, "");
         if (_snowman.get("slots") != null && _snowman.get("slots").isJsonArray()) {
            _snowman.i = b(_snowman.get("slots").getAsJsonArray());
         } else {
            _snowman.i = e();
         }

         _snowman.o = dip.a("minigameName", _snowman, null);
         _snowman.n = dip.a("activeSlot", _snowman, -1);
         _snowman.p = dip.a("minigameId", _snowman, -1);
         _snowman.q = dip.a("minigameImage", _snowman, null);
      } catch (Exception var3) {
         s.error("Could not parse McoServer: " + var3.getMessage());
      }

      return _snowman;
   }

   private static void a(dgq var0) {
      _snowman.h
         .sort(
            (var0x, var1) -> ComparisonChain.start()
                  .compareFalseFirst(var1.d(), var0x.d())
                  .compare(var0x.a().toLowerCase(Locale.ROOT), var1.a().toLowerCase(Locale.ROOT))
                  .result()
         );
   }

   private static List<dgn> a(JsonArray var0) {
      List<dgn> _snowman = Lists.newArrayList();

      for (JsonElement _snowmanx : _snowman) {
         try {
            JsonObject _snowmanxx = _snowmanx.getAsJsonObject();
            dgn _snowmanxxx = new dgn();
            _snowmanxxx.a(dip.a("name", _snowmanxx, null));
            _snowmanxxx.b(dip.a("uuid", _snowmanxx, null));
            _snowmanxxx.a(dip.a("operator", _snowmanxx, false));
            _snowmanxxx.b(dip.a("accepted", _snowmanxx, false));
            _snowmanxxx.c(dip.a("online", _snowmanxx, false));
            _snowman.add(_snowmanxxx);
         } catch (Exception var6) {
         }
      }

      return _snowman;
   }

   private static Map<Integer, dgw> b(JsonArray var0) {
      Map<Integer, dgw> _snowman = Maps.newHashMap();

      for (JsonElement _snowmanx : _snowman) {
         try {
            JsonObject _snowmanxx = _snowmanx.getAsJsonObject();
            JsonParser _snowmanxxx = new JsonParser();
            JsonElement _snowmanxxxx = _snowmanxxx.parse(_snowmanxx.get("options").getAsString());
            dgw _snowmanxxxxx;
            if (_snowmanxxxx == null) {
               _snowmanxxxxx = dgw.a();
            } else {
               _snowmanxxxxx = dgw.a(_snowmanxxxx.getAsJsonObject());
            }

            int _snowmanxxxxxx = dip.a("slotId", _snowmanxx, -1);
            _snowman.put(_snowmanxxxxxx, _snowmanxxxxx);
         } catch (Exception var9) {
         }
      }

      for (int _snowmanx = 1; _snowmanx <= 3; _snowmanx++) {
         if (!_snowman.containsKey(_snowmanx)) {
            _snowman.put(_snowmanx, dgw.b());
         }
      }

      return _snowman;
   }

   private static Map<Integer, dgw> e() {
      Map<Integer, dgw> _snowman = Maps.newHashMap();
      _snowman.put(1, dgw.b());
      _snowman.put(2, dgw.b());
      _snowman.put(3, dgw.b());
      return _snowman;
   }

   public static dgq c(String var0) {
      try {
         return a(new JsonParser().parse(_snowman).getAsJsonObject());
      } catch (Exception var2) {
         s.error("Could not parse McoServer: " + var2.getMessage());
         return new dgq();
      }
   }

   private static dgq.b e(String var0) {
      try {
         return dgq.b.valueOf(_snowman);
      } catch (Exception var2) {
         return dgq.b.a;
      }
   }

   private static dgq.c f(String var0) {
      try {
         return dgq.c.valueOf(_snowman);
      } catch (Exception var2) {
         return dgq.c.a;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.a, this.c, this.d, this.e, this.f, this.j);
   }

   @Override
   public boolean equals(Object var1) {
      if (_snowman == null) {
         return false;
      } else if (_snowman == this) {
         return true;
      } else if (_snowman.getClass() != this.getClass()) {
         return false;
      } else {
         dgq _snowman = (dgq)_snowman;
         return new EqualsBuilder()
            .append(this.a, _snowman.a)
            .append(this.c, _snowman.c)
            .append(this.d, _snowman.d)
            .append(this.e, _snowman.e)
            .append(this.f, _snowman.f)
            .append(this.j, _snowman.j)
            .append(this.m, this.m)
            .isEquals();
      }
   }

   public dgq d() {
      dgq _snowman = new dgq();
      _snowman.a = this.a;
      _snowman.b = this.b;
      _snowman.c = this.c;
      _snowman.d = this.d;
      _snowman.e = this.e;
      _snowman.f = this.f;
      _snowman.h = this.h;
      _snowman.i = this.a(this.i);
      _snowman.j = this.j;
      _snowman.k = this.k;
      _snowman.l = this.l;
      _snowman.r = new dgt();
      _snowman.r.a = this.r.a;
      _snowman.r.b = this.r.b;
      _snowman.m = this.m;
      _snowman.g = this.g;
      _snowman.o = this.o;
      _snowman.n = this.n;
      _snowman.p = this.p;
      _snowman.q = this.q;
      return _snowman;
   }

   public Map<Integer, dgw> a(Map<Integer, dgw> var1) {
      Map<Integer, dgw> _snowman = Maps.newHashMap();

      for (Entry<Integer, dgw> _snowmanx : _snowman.entrySet()) {
         _snowman.put(_snowmanx.getKey(), _snowmanx.getValue().d());
      }

      return _snowman;
   }

   public String a(int var1) {
      return this.c + " (" + this.i.get(_snowman).a(_snowman) + ")";
   }

   public dwz d(String var1) {
      return new dwz(this.c, _snowman, false);
   }

   public static class a implements Comparator<dgq> {
      private final String a;

      public a(String var1) {
         this.a = _snowman;
      }

      public int a(dgq var1, dgq var2) {
         return ComparisonChain.start()
            .compareTrueFirst(_snowman.e == dgq.b.c, _snowman.e == dgq.b.c)
            .compareTrueFirst(_snowman.k, _snowman.k)
            .compareTrueFirst(_snowman.f.equals(this.a), _snowman.f.equals(this.a))
            .compareFalseFirst(_snowman.j, _snowman.j)
            .compareTrueFirst(_snowman.e == dgq.b.b, _snowman.e == dgq.b.b)
            .compare(_snowman.a, _snowman.a)
            .result();
      }
   }

   public static enum b {
      a,
      b,
      c;

      private b() {
      }
   }

   public static enum c {
      a,
      b,
      c,
      d,
      e;

      private c() {
      }
   }
}
