import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class jh {
   private static final Logger a = LogManager.getLogger();
   private final blx b;
   private final int c;
   private final List<String> d = Lists.newArrayList();
   private final Map<Character, bon> e = Maps.newLinkedHashMap();
   private final y.a f = y.a.a();
   private String g;

   public jh(brw var1, int var2) {
      this.b = _snowman.h();
      this.c = _snowman;
   }

   public static jh a(brw var0) {
      return a(_snowman, 1);
   }

   public static jh a(brw var0, int var1) {
      return new jh(_snowman, _snowman);
   }

   public jh a(Character var1, ael<blx> var2) {
      return this.a(_snowman, bon.a(_snowman));
   }

   public jh a(Character var1, brw var2) {
      return this.a(_snowman, bon.a(_snowman));
   }

   public jh a(Character var1, bon var2) {
      if (this.e.containsKey(_snowman)) {
         throw new IllegalArgumentException("Symbol '" + _snowman + "' is already defined!");
      } else if (_snowman == ' ') {
         throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
      } else {
         this.e.put(_snowman, _snowman);
         return this;
      }
   }

   public jh a(String var1) {
      if (!this.d.isEmpty() && _snowman.length() != this.d.get(0).length()) {
         throw new IllegalArgumentException("Pattern must be the same width on every line!");
      } else {
         this.d.add(_snowman);
         return this;
      }
   }

   public jh a(String var1, ag var2) {
      this.f.a(_snowman, _snowman);
      return this;
   }

   public jh b(String var1) {
      this.g = _snowman;
      return this;
   }

   public void a(Consumer<jf> var1) {
      this.a(_snowman, gm.T.b(this.b));
   }

   public void a(Consumer<jf> var1, String var2) {
      vk _snowman = gm.T.b(this.b);
      if (new vk(_snowman).equals(_snowman)) {
         throw new IllegalStateException("Shaped Recipe " + _snowman + " should remove its 'save' argument");
      } else {
         this.a(_snowman, new vk(_snowman));
      }
   }

   public void a(Consumer<jf> var1, vk var2) {
      this.a(_snowman);
      this.f.a(new vk("recipes/root")).a("has_the_recipe", ch.a(_snowman)).a(ab.a.c(_snowman)).a(aj.b);
      _snowman.accept(new jh.a(_snowman, this.b, this.c, this.g == null ? "" : this.g, this.d, this.e, this.f, new vk(_snowman.b(), "recipes/" + this.b.q().b() + "/" + _snowman.a())));
   }

   private void a(vk var1) {
      if (this.d.isEmpty()) {
         throw new IllegalStateException("No pattern is defined for shaped recipe " + _snowman + "!");
      } else {
         Set<Character> _snowman = Sets.newHashSet(this.e.keySet());
         _snowman.remove(' ');

         for (String _snowmanx : this.d) {
            for (int _snowmanxx = 0; _snowmanxx < _snowmanx.length(); _snowmanxx++) {
               char _snowmanxxx = _snowmanx.charAt(_snowmanxx);
               if (!this.e.containsKey(_snowmanxxx) && _snowmanxxx != ' ') {
                  throw new IllegalStateException("Pattern in recipe " + _snowman + " uses undefined symbol '" + _snowmanxxx + "'");
               }

               _snowman.remove(_snowmanxxx);
            }
         }

         if (!_snowman.isEmpty()) {
            throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + _snowman);
         } else if (this.d.size() == 1 && this.d.get(0).length() == 1) {
            throw new IllegalStateException("Shaped recipe " + _snowman + " only takes in a single item - should it be a shapeless recipe instead?");
         } else if (this.f.c().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + _snowman);
         }
      }
   }

   class a implements jf {
      private final vk b;
      private final blx c;
      private final int d;
      private final String e;
      private final List<String> f;
      private final Map<Character, bon> g;
      private final y.a h;
      private final vk i;

      public a(vk var2, blx var3, int var4, String var5, List<String> var6, Map<Character, bon> var7, y.a var8, vk var9) {
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
         this.h = _snowman;
         this.i = _snowman;
      }

      @Override
      public void a(JsonObject var1) {
         if (!this.e.isEmpty()) {
            _snowman.addProperty("group", this.e);
         }

         JsonArray _snowman = new JsonArray();

         for (String _snowmanx : this.f) {
            _snowman.add(_snowmanx);
         }

         _snowman.add("pattern", _snowman);
         JsonObject _snowmanx = new JsonObject();

         for (Entry<Character, bon> _snowmanxx : this.g.entrySet()) {
            _snowmanx.add(String.valueOf(_snowmanxx.getKey()), _snowmanxx.getValue().c());
         }

         _snowman.add("key", _snowmanx);
         JsonObject _snowmanxx = new JsonObject();
         _snowmanxx.addProperty("item", gm.T.b(this.c).toString());
         if (this.d > 1) {
            _snowmanxx.addProperty("count", this.d);
         }

         _snowman.add("result", _snowmanxx);
      }

      @Override
      public bos<?> c() {
         return bos.a;
      }

      @Override
      public vk b() {
         return this.b;
      }

      @Nullable
      @Override
      public JsonObject d() {
         return this.h.b();
      }

      @Nullable
      @Override
      public vk e() {
         return this.i;
      }
   }
}
