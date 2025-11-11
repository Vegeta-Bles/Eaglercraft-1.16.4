import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class boz<T extends boc> implements bos<T> {
   private final int v;
   private final boz.a<T> w;

   public boz(boz.a<T> var1, int var2) {
      this.v = _snowman;
      this.w = _snowman;
   }

   public T b(vk var1, JsonObject var2) {
      String _snowman = afd.a(_snowman, "group", "");
      JsonElement _snowmanx = (JsonElement)(afd.d(_snowman, "ingredient") ? afd.u(_snowman, "ingredient") : afd.t(_snowman, "ingredient"));
      bon _snowmanxx = bon.a(_snowmanx);
      String _snowmanxxx = afd.h(_snowman, "result");
      vk _snowmanxxxx = new vk(_snowmanxxx);
      bmb _snowmanxxxxx = new bmb(gm.T.b(_snowmanxxxx).orElseThrow(() -> new IllegalStateException("Item: " + _snowman + " does not exist")));
      float _snowmanxxxxxx = afd.a(_snowman, "experience", 0.0F);
      int _snowmanxxxxxxx = afd.a(_snowman, "cookingtime", this.v);
      return this.w.create(_snowman, _snowman, _snowmanxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
   }

   public T b(vk var1, nf var2) {
      String _snowman = _snowman.e(32767);
      bon _snowmanx = bon.b(_snowman);
      bmb _snowmanxx = _snowman.n();
      float _snowmanxxx = _snowman.readFloat();
      int _snowmanxxxx = _snowman.i();
      return this.w.create(_snowman, _snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
   }

   public void a(nf var1, T var2) {
      _snowman.a(_snowman.c);
      _snowman.d.a(_snowman);
      _snowman.a(_snowman.e);
      _snowman.writeFloat(_snowman.f);
      _snowman.d(_snowman.g);
   }

   interface a<T extends boc> {
      T create(vk var1, String var2, bon var3, bmb var4, float var5, int var6);
   }
}
