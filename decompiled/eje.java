import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.io.IOException;

public class eje<T extends aqm & bfl, M extends duc<T> & dwe> extends eit<T, M> implements aci {
   private static final Int2ObjectMap<vk> a = x.a(new Int2ObjectOpenHashMap(), var0 -> {
      var0.put(1, new vk("stone"));
      var0.put(2, new vk("iron"));
      var0.put(3, new vk("gold"));
      var0.put(4, new vk("emerald"));
      var0.put(5, new vk("diamond"));
   });
   private final Object2ObjectMap<bfo, ele.a> b = new Object2ObjectOpenHashMap();
   private final Object2ObjectMap<bfm, ele.a> c = new Object2ObjectOpenHashMap();
   private final acf d;
   private final String e;

   public eje(egk<T, M> var1, acf var2, String var3) {
      super(_snowman);
      this.d = _snowman;
      this.e = _snowman;
      _snowman.a(this);
   }

   public void a(dfm var1, eag var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10) {
      if (!_snowman.bF()) {
         bfk _snowman = _snowman.eX();
         bfo _snowmanx = _snowman.a();
         bfm _snowmanxx = _snowman.b();
         ele.a _snowmanxxx = this.a(this.b, "type", gm.ah, _snowmanx);
         ele.a _snowmanxxxx = this.a(this.c, "profession", gm.ai, _snowmanxx);
         M _snowmanxxxxx = this.aC_();
         _snowmanxxxxx.a(_snowmanxxxx == ele.a.a || _snowmanxxxx == ele.a.b && _snowmanxxx != ele.a.c);
         vk _snowmanxxxxxx = this.a("type", gm.ah.b(_snowmanx));
         a(_snowmanxxxxx, _snowmanxxxxxx, _snowman, _snowman, _snowman, _snowman, 1.0F, 1.0F, 1.0F);
         _snowmanxxxxx.a(true);
         if (_snowmanxx != bfm.a && !_snowman.w_()) {
            vk _snowmanxxxxxxx = this.a("profession", gm.ai.b(_snowmanxx));
            a(_snowmanxxxxx, _snowmanxxxxxxx, _snowman, _snowman, _snowman, _snowman, 1.0F, 1.0F, 1.0F);
            if (_snowmanxx != bfm.l) {
               vk _snowmanxxxxxxxx = this.a("profession_level", (vk)a.get(afm.a(_snowman.c(), 1, a.size())));
               a(_snowmanxxxxx, _snowmanxxxxxxxx, _snowman, _snowman, _snowman, _snowman, 1.0F, 1.0F, 1.0F);
            }
         }
      }
   }

   private vk a(String var1, vk var2) {
      return new vk(_snowman.b(), "textures/entity/" + this.e + "/" + _snowman + "/" + _snowman.a() + ".png");
   }

   public <K> ele.a a(Object2ObjectMap<K, ele.a> var1, String var2, gb<K> var3, K var4) {
      return (ele.a)_snowman.computeIfAbsent(_snowman, var4x -> {
         try (acg _snowman = this.d.a(this.a(_snowman, _snowman.b(_snowman)))) {
            ele _snowmanx = _snowman.a(ele.a);
            if (_snowmanx != null) {
               return _snowmanx.a();
            }
         } catch (IOException var21) {
         }

         return ele.a.a;
      });
   }

   @Override
   public void a(ach var1) {
      this.c.clear();
      this.b.clear();
   }
}
