import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class bow implements boi {
   private final vk a;
   private final String b;
   private final bmb c;
   private final gj<bon> d;

   public bow(vk var1, String var2, bmb var3, gj<bon> var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   @Override
   public vk f() {
      return this.a;
   }

   @Override
   public bos<?> ag_() {
      return bos.b;
   }

   @Override
   public String d() {
      return this.b;
   }

   @Override
   public bmb c() {
      return this.c;
   }

   @Override
   public gj<bon> a() {
      return this.d;
   }

   public boolean a(bio var1, brx var2) {
      bfy _snowman = new bfy();
      int _snowmanx = 0;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.Z_(); _snowmanxx++) {
         bmb _snowmanxxx = _snowman.a(_snowmanxx);
         if (!_snowmanxxx.a()) {
            _snowmanx++;
            _snowman.a(_snowmanxxx, 1);
         }
      }

      return _snowmanx == this.d.size() && _snowman.a(this, null);
   }

   public bmb a(bio var1) {
      return this.c.i();
   }

   @Override
   public boolean a(int var1, int var2) {
      return _snowman * _snowman >= this.d.size();
   }

   public static class a implements bos<bow> {
      public a() {
      }

      public bow b(vk var1, JsonObject var2) {
         String _snowman = afd.a(_snowman, "group", "");
         gj<bon> _snowmanx = a(afd.u(_snowman, "ingredients"));
         if (_snowmanx.isEmpty()) {
            throw new JsonParseException("No ingredients for shapeless recipe");
         } else if (_snowmanx.size() > 9) {
            throw new JsonParseException("Too many ingredients for shapeless recipe");
         } else {
            bmb _snowmanxx = bov.a(afd.t(_snowman, "result"));
            return new bow(_snowman, _snowman, _snowmanxx, _snowmanx);
         }
      }

      private static gj<bon> a(JsonArray var0) {
         gj<bon> _snowman = gj.a();

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            bon _snowmanxx = bon.a(_snowman.get(_snowmanx));
            if (!_snowmanxx.d()) {
               _snowman.add(_snowmanxx);
            }
         }

         return _snowman;
      }

      public bow b(vk var1, nf var2) {
         String _snowman = _snowman.e(32767);
         int _snowmanx = _snowman.i();
         gj<bon> _snowmanxx = gj.a(_snowmanx, bon.a);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
            _snowmanxx.set(_snowmanxxx, bon.b(_snowman));
         }

         bmb _snowmanxxx = _snowman.n();
         return new bow(_snowman, _snowman, _snowmanxxx, _snowmanxx);
      }

      public void a(nf var1, bow var2) {
         _snowman.a(_snowman.b);
         _snowman.d(_snowman.d.size());

         for (bon _snowman : _snowman.d) {
            _snowman.a(_snowman);
         }

         _snowman.a(_snowman.c);
      }
   }
}
