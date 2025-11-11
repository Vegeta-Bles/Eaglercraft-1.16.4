import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class bov implements boi {
   private final int a;
   private final int b;
   private final gj<bon> c;
   private final bmb d;
   private final vk e;
   private final String f;

   public bov(vk var1, String var2, int var3, int var4, gj<bon> var5, bmb var6) {
      this.e = _snowman;
      this.f = _snowman;
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   @Override
   public vk f() {
      return this.e;
   }

   @Override
   public bos<?> ag_() {
      return bos.a;
   }

   @Override
   public String d() {
      return this.f;
   }

   @Override
   public bmb c() {
      return this.d;
   }

   @Override
   public gj<bon> a() {
      return this.c;
   }

   @Override
   public boolean a(int var1, int var2) {
      return _snowman >= this.a && _snowman >= this.b;
   }

   public boolean a(bio var1, brx var2) {
      for (int _snowman = 0; _snowman <= _snowman.g() - this.a; _snowman++) {
         for (int _snowmanx = 0; _snowmanx <= _snowman.f() - this.b; _snowmanx++) {
            if (this.a(_snowman, _snowman, _snowmanx, true)) {
               return true;
            }

            if (this.a(_snowman, _snowman, _snowmanx, false)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean a(bio var1, int var2, int var3, boolean var4) {
      for (int _snowman = 0; _snowman < _snowman.g(); _snowman++) {
         for (int _snowmanx = 0; _snowmanx < _snowman.f(); _snowmanx++) {
            int _snowmanxx = _snowman - _snowman;
            int _snowmanxxx = _snowmanx - _snowman;
            bon _snowmanxxxx = bon.a;
            if (_snowmanxx >= 0 && _snowmanxxx >= 0 && _snowmanxx < this.a && _snowmanxxx < this.b) {
               if (_snowman) {
                  _snowmanxxxx = this.c.get(this.a - _snowmanxx - 1 + _snowmanxxx * this.a);
               } else {
                  _snowmanxxxx = this.c.get(_snowmanxx + _snowmanxxx * this.a);
               }
            }

            if (!_snowmanxxxx.a(_snowman.a(_snowman + _snowmanx * _snowman.g()))) {
               return false;
            }
         }
      }

      return true;
   }

   public bmb a(bio var1) {
      return this.c().i();
   }

   public int i() {
      return this.a;
   }

   public int j() {
      return this.b;
   }

   private static gj<bon> b(String[] var0, Map<String, bon> var1, int var2, int var3) {
      gj<bon> _snowman = gj.a(_snowman * _snowman, bon.a);
      Set<String> _snowmanx = Sets.newHashSet(_snowman.keySet());
      _snowmanx.remove(" ");

      for (int _snowmanxx = 0; _snowmanxx < _snowman.length; _snowmanxx++) {
         for (int _snowmanxxx = 0; _snowmanxxx < _snowman[_snowmanxx].length(); _snowmanxxx++) {
            String _snowmanxxxx = _snowman[_snowmanxx].substring(_snowmanxxx, _snowmanxxx + 1);
            bon _snowmanxxxxx = _snowman.get(_snowmanxxxx);
            if (_snowmanxxxxx == null) {
               throw new JsonSyntaxException("Pattern references symbol '" + _snowmanxxxx + "' but it's not defined in the key");
            }

            _snowmanx.remove(_snowmanxxxx);
            _snowman.set(_snowmanxxx + _snowman * _snowmanxx, _snowmanxxxxx);
         }
      }

      if (!_snowmanx.isEmpty()) {
         throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + _snowmanx);
      } else {
         return _snowman;
      }
   }

   @VisibleForTesting
   static String[] a(String... var0) {
      int _snowman = Integer.MAX_VALUE;
      int _snowmanx = 0;
      int _snowmanxx = 0;
      int _snowmanxxx = 0;

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman.length; _snowmanxxxx++) {
         String _snowmanxxxxx = _snowman[_snowmanxxxx];
         _snowman = Math.min(_snowman, a(_snowmanxxxxx));
         int _snowmanxxxxxx = b(_snowmanxxxxx);
         _snowmanx = Math.max(_snowmanx, _snowmanxxxxxx);
         if (_snowmanxxxxxx < 0) {
            if (_snowmanxx == _snowmanxxxx) {
               _snowmanxx++;
            }

            _snowmanxxx++;
         } else {
            _snowmanxxx = 0;
         }
      }

      if (_snowman.length == _snowmanxxx) {
         return new String[0];
      } else {
         String[] _snowmanxxxxx = new String[_snowman.length - _snowmanxxx - _snowmanxx];

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx.length; _snowmanxxxxxx++) {
            _snowmanxxxxx[_snowmanxxxxxx] = _snowman[_snowmanxxxxxx + _snowmanxx].substring(_snowman, _snowmanx + 1);
         }

         return _snowmanxxxxx;
      }
   }

   private static int a(String var0) {
      int _snowman = 0;

      while (_snowman < _snowman.length() && _snowman.charAt(_snowman) == ' ') {
         _snowman++;
      }

      return _snowman;
   }

   private static int b(String var0) {
      int _snowman = _snowman.length() - 1;

      while (_snowman >= 0 && _snowman.charAt(_snowman) == ' ') {
         _snowman--;
      }

      return _snowman;
   }

   private static String[] b(JsonArray var0) {
      String[] _snowman = new String[_snowman.size()];
      if (_snowman.length > 3) {
         throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
      } else if (_snowman.length == 0) {
         throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
      } else {
         for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
            String _snowmanxx = afd.a(_snowman.get(_snowmanx), "pattern[" + _snowmanx + "]");
            if (_snowmanxx.length() > 3) {
               throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
            }

            if (_snowmanx > 0 && _snowman[0].length() != _snowmanxx.length()) {
               throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
            }

            _snowman[_snowmanx] = _snowmanxx;
         }

         return _snowman;
      }
   }

   private static Map<String, bon> c(JsonObject var0) {
      Map<String, bon> _snowman = Maps.newHashMap();

      for (Entry<String, JsonElement> _snowmanx : _snowman.entrySet()) {
         if (_snowmanx.getKey().length() != 1) {
            throw new JsonSyntaxException("Invalid key entry: '" + _snowmanx.getKey() + "' is an invalid symbol (must be 1 character only).");
         }

         if (" ".equals(_snowmanx.getKey())) {
            throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
         }

         _snowman.put(_snowmanx.getKey(), bon.a(_snowmanx.getValue()));
      }

      _snowman.put(" ", bon.a);
      return _snowman;
   }

   public static bmb a(JsonObject var0) {
      String _snowman = afd.h(_snowman, "item");
      blx _snowmanx = gm.T.b(new vk(_snowman)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + _snowman + "'"));
      if (_snowman.has("data")) {
         throw new JsonParseException("Disallowed data tag found");
      } else {
         int _snowmanxx = afd.a(_snowman, "count", 1);
         return new bmb(_snowmanx, _snowmanxx);
      }
   }

   public static class a implements bos<bov> {
      public a() {
      }

      public bov b(vk var1, JsonObject var2) {
         String _snowman = afd.a(_snowman, "group", "");
         Map<String, bon> _snowmanx = bov.c(afd.t(_snowman, "key"));
         String[] _snowmanxx = bov.a(bov.b(afd.u(_snowman, "pattern")));
         int _snowmanxxx = _snowmanxx[0].length();
         int _snowmanxxxx = _snowmanxx.length;
         gj<bon> _snowmanxxxxx = bov.b(_snowmanxx, _snowmanx, _snowmanxxx, _snowmanxxxx);
         bmb _snowmanxxxxxx = bov.a(afd.t(_snowman, "result"));
         return new bov(_snowman, _snowman, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      }

      public bov b(vk var1, nf var2) {
         int _snowman = _snowman.i();
         int _snowmanx = _snowman.i();
         String _snowmanxx = _snowman.e(32767);
         gj<bon> _snowmanxxx = gj.a(_snowman * _snowmanx, bon.a);

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx.size(); _snowmanxxxx++) {
            _snowmanxxx.set(_snowmanxxxx, bon.b(_snowman));
         }

         bmb _snowmanxxxx = _snowman.n();
         return new bov(_snowman, _snowmanxx, _snowman, _snowmanx, _snowmanxxx, _snowmanxxxx);
      }

      public void a(nf var1, bov var2) {
         _snowman.d(_snowman.a);
         _snowman.d(_snowman.b);
         _snowman.a(_snowman.f);

         for (bon _snowman : _snowman.c) {
            _snowman.a(_snowman);
         }

         _snowman.a(_snowman.d);
      }
   }
}
