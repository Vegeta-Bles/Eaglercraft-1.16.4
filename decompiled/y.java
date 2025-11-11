import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.apache.commons.lang3.ArrayUtils;

public class y {
   private final y a;
   private final ah b;
   private final ab c;
   private final vk d;
   private final Map<String, ad> e;
   private final String[][] f;
   private final Set<y> g = Sets.newLinkedHashSet();
   private final nr h;

   public y(vk var1, @Nullable y var2, @Nullable ah var3, ab var4, Map<String, ad> var5, String[][] var6) {
      this.d = _snowman;
      this.b = _snowman;
      this.e = ImmutableMap.copyOf(_snowman);
      this.a = _snowman;
      this.c = _snowman;
      this.f = _snowman;
      if (_snowman != null) {
         _snowman.a(this);
      }

      if (_snowman == null) {
         this.h = new oe(_snowman.toString());
      } else {
         nr _snowman = _snowman.a();
         k _snowmanx = _snowman.e().c();
         nr _snowmanxx = ns.a(_snowman.e(), ob.a.a(_snowmanx)).c("\n").a(_snowman.b());
         nr _snowmanxxx = _snowman.e().a(var1x -> var1x.a(new nv(nv.a.a, _snowman)));
         this.h = ns.a(_snowmanxxx).a(_snowmanx);
      }
   }

   public y.a a() {
      return new y.a(this.a == null ? null : this.a.h(), this.b, this.c, this.e, this.f);
   }

   @Nullable
   public y b() {
      return this.a;
   }

   @Nullable
   public ah c() {
      return this.b;
   }

   public ab d() {
      return this.c;
   }

   @Override
   public String toString() {
      return "SimpleAdvancement{id="
         + this.h()
         + ", parent="
         + (this.a == null ? "null" : this.a.h())
         + ", display="
         + this.b
         + ", rewards="
         + this.c
         + ", criteria="
         + this.e
         + ", requirements="
         + Arrays.deepToString(this.f)
         + '}';
   }

   public Iterable<y> e() {
      return this.g;
   }

   public Map<String, ad> f() {
      return this.e;
   }

   public int g() {
      return this.f.length;
   }

   public void a(y var1) {
      this.g.add(_snowman);
   }

   public vk h() {
      return this.d;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof y)) {
         return false;
      } else {
         y _snowman = (y)_snowman;
         return this.d.equals(_snowman.d);
      }
   }

   @Override
   public int hashCode() {
      return this.d.hashCode();
   }

   public String[][] i() {
      return this.f;
   }

   public nr j() {
      return this.h;
   }

   public static class a {
      private vk a;
      private y b;
      private ah c;
      private ab d = ab.a;
      private Map<String, ad> e = Maps.newLinkedHashMap();
      private String[][] f;
      private aj g = aj.a;

      private a(@Nullable vk var1, @Nullable ah var2, ab var3, Map<String, ad> var4, String[][] var5) {
         this.a = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
      }

      private a() {
      }

      public static y.a a() {
         return new y.a();
      }

      public y.a a(y var1) {
         this.b = _snowman;
         return this;
      }

      public y.a a(vk var1) {
         this.a = _snowman;
         return this;
      }

      public y.a a(bmb var1, nr var2, nr var3, @Nullable vk var4, ai var5, boolean var6, boolean var7, boolean var8) {
         return this.a(new ah(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
      }

      public y.a a(brw var1, nr var2, nr var3, @Nullable vk var4, ai var5, boolean var6, boolean var7, boolean var8) {
         return this.a(new ah(new bmb(_snowman.h()), _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
      }

      public y.a a(ah var1) {
         this.c = _snowman;
         return this;
      }

      public y.a a(ab.a var1) {
         return this.a(_snowman.a());
      }

      public y.a a(ab var1) {
         this.d = _snowman;
         return this;
      }

      public y.a a(String var1, ag var2) {
         return this.a(_snowman, new ad(_snowman));
      }

      public y.a a(String var1, ad var2) {
         if (this.e.containsKey(_snowman)) {
            throw new IllegalArgumentException("Duplicate criterion " + _snowman);
         } else {
            this.e.put(_snowman, _snowman);
            return this;
         }
      }

      public y.a a(aj var1) {
         this.g = _snowman;
         return this;
      }

      public boolean a(Function<vk, y> var1) {
         if (this.a == null) {
            return true;
         } else {
            if (this.b == null) {
               this.b = _snowman.apply(this.a);
            }

            return this.b != null;
         }
      }

      public y b(vk var1) {
         if (!this.a(var0 -> null)) {
            throw new IllegalStateException("Tried to build incomplete advancement!");
         } else {
            if (this.f == null) {
               this.f = this.g.createRequirements(this.e.keySet());
            }

            return new y(_snowman, this.b, this.c, this.d, this.e, this.f);
         }
      }

      public y a(Consumer<y> var1, String var2) {
         y _snowman = this.b(new vk(_snowman));
         _snowman.accept(_snowman);
         return _snowman;
      }

      public JsonObject b() {
         if (this.f == null) {
            this.f = this.g.createRequirements(this.e.keySet());
         }

         JsonObject _snowman = new JsonObject();
         if (this.b != null) {
            _snowman.addProperty("parent", this.b.h().toString());
         } else if (this.a != null) {
            _snowman.addProperty("parent", this.a.toString());
         }

         if (this.c != null) {
            _snowman.add("display", this.c.k());
         }

         _snowman.add("rewards", this.d.b());
         JsonObject _snowmanx = new JsonObject();

         for (Entry<String, ad> _snowmanxx : this.e.entrySet()) {
            _snowmanx.add(_snowmanxx.getKey(), _snowmanxx.getValue().b());
         }

         _snowman.add("criteria", _snowmanx);
         JsonArray _snowmanxx = new JsonArray();

         for (String[] _snowmanxxx : this.f) {
            JsonArray _snowmanxxxx = new JsonArray();

            for (String _snowmanxxxxx : _snowmanxxx) {
               _snowmanxxxx.add(_snowmanxxxxx);
            }

            _snowmanxx.add(_snowmanxxxx);
         }

         _snowman.add("requirements", _snowmanxx);
         return _snowman;
      }

      public void a(nf var1) {
         if (this.a == null) {
            _snowman.writeBoolean(false);
         } else {
            _snowman.writeBoolean(true);
            _snowman.a(this.a);
         }

         if (this.c == null) {
            _snowman.writeBoolean(false);
         } else {
            _snowman.writeBoolean(true);
            this.c.a(_snowman);
         }

         ad.a(this.e, _snowman);
         _snowman.d(this.f.length);

         for (String[] _snowman : this.f) {
            _snowman.d(_snowman.length);

            for (String _snowmanx : _snowman) {
               _snowman.a(_snowmanx);
            }
         }
      }

      @Override
      public String toString() {
         return "Task Advancement{parentId="
            + this.a
            + ", display="
            + this.c
            + ", rewards="
            + this.d
            + ", criteria="
            + this.e
            + ", requirements="
            + Arrays.deepToString(this.f)
            + '}';
      }

      public static y.a a(JsonObject var0, ax var1) {
         vk _snowman = _snowman.has("parent") ? new vk(afd.h(_snowman, "parent")) : null;
         ah _snowmanx = _snowman.has("display") ? ah.a(afd.t(_snowman, "display")) : null;
         ab _snowmanxx = _snowman.has("rewards") ? ab.a(afd.t(_snowman, "rewards")) : ab.a;
         Map<String, ad> _snowmanxxx = ad.b(afd.t(_snowman, "criteria"), _snowman);
         if (_snowmanxxx.isEmpty()) {
            throw new JsonSyntaxException("Advancement criteria cannot be empty");
         } else {
            JsonArray _snowmanxxxx = afd.a(_snowman, "requirements", new JsonArray());
            String[][] _snowmanxxxxx = new String[_snowmanxxxx.size()][];

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxx.size(); _snowmanxxxxxx++) {
               JsonArray _snowmanxxxxxxx = afd.n(_snowmanxxxx.get(_snowmanxxxxxx), "requirements[" + _snowmanxxxxxx + "]");
               _snowmanxxxxx[_snowmanxxxxxx] = new String[_snowmanxxxxxxx.size()];

               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxxx.size(); _snowmanxxxxxxxx++) {
                  _snowmanxxxxx[_snowmanxxxxxx][_snowmanxxxxxxxx] = afd.a(_snowmanxxxxxxx.get(_snowmanxxxxxxxx), "requirements[" + _snowmanxxxxxx + "][" + _snowmanxxxxxxxx + "]");
               }
            }

            if (_snowmanxxxxx.length == 0) {
               _snowmanxxxxx = new String[_snowmanxxx.size()][];
               int _snowmanxxxxxx = 0;

               for (String _snowmanxxxxxxx : _snowmanxxx.keySet()) {
                  _snowmanxxxxx[_snowmanxxxxxx++] = new String[]{_snowmanxxxxxxx};
               }
            }

            for (String[] _snowmanxxxxxx : _snowmanxxxxx) {
               if (_snowmanxxxxxx.length == 0 && _snowmanxxx.isEmpty()) {
                  throw new JsonSyntaxException("Requirement entry cannot be empty");
               }

               for (String _snowmanxxxxxxx : _snowmanxxxxxx) {
                  if (!_snowmanxxx.containsKey(_snowmanxxxxxxx)) {
                     throw new JsonSyntaxException("Unknown required criterion '" + _snowmanxxxxxxx + "'");
                  }
               }
            }

            for (String _snowmanxxxxxx : _snowmanxxx.keySet()) {
               boolean _snowmanxxxxxxxx = false;

               for (String[] _snowmanxxxxxxxxx : _snowmanxxxxx) {
                  if (ArrayUtils.contains(_snowmanxxxxxxxxx, _snowmanxxxxxx)) {
                     _snowmanxxxxxxxx = true;
                     break;
                  }
               }

               if (!_snowmanxxxxxxxx) {
                  throw new JsonSyntaxException(
                     "Criterion '" + _snowmanxxxxxx + "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required."
                  );
               }
            }

            return new y.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxxx);
         }
      }

      public static y.a b(nf var0) {
         vk _snowman = _snowman.readBoolean() ? _snowman.p() : null;
         ah _snowmanx = _snowman.readBoolean() ? ah.b(_snowman) : null;
         Map<String, ad> _snowmanxx = ad.c(_snowman);
         String[][] _snowmanxxx = new String[_snowman.i()][];

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx.length; _snowmanxxxx++) {
            _snowmanxxx[_snowmanxxxx] = new String[_snowman.i()];

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxx[_snowmanxxxx].length; _snowmanxxxxx++) {
               _snowmanxxx[_snowmanxxxx][_snowmanxxxxx] = _snowman.e(32767);
            }
         }

         return new y.a(_snowman, _snowmanx, ab.a, _snowmanxx, _snowmanxxx);
      }

      public Map<String, ad> c() {
         return this.e;
      }
   }
}
