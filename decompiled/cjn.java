import com.mojang.serialization.Codec;
import java.util.Random;

public class cjn extends cjl<cmh> {
   private static final vk a = new vk("fossil/spine_1");
   private static final vk ab = new vk("fossil/spine_2");
   private static final vk ac = new vk("fossil/spine_3");
   private static final vk ad = new vk("fossil/spine_4");
   private static final vk ae = new vk("fossil/spine_1_coal");
   private static final vk af = new vk("fossil/spine_2_coal");
   private static final vk ag = new vk("fossil/spine_3_coal");
   private static final vk ah = new vk("fossil/spine_4_coal");
   private static final vk ai = new vk("fossil/skull_1");
   private static final vk aj = new vk("fossil/skull_2");
   private static final vk ak = new vk("fossil/skull_3");
   private static final vk al = new vk("fossil/skull_4");
   private static final vk am = new vk("fossil/skull_1_coal");
   private static final vk an = new vk("fossil/skull_2_coal");
   private static final vk ao = new vk("fossil/skull_3_coal");
   private static final vk ap = new vk("fossil/skull_4_coal");
   private static final vk[] aq = new vk[]{a, ab, ac, ad, ai, aj, ak, al};
   private static final vk[] ar = new vk[]{ae, af, ag, ah, am, an, ao, ap};

   public cjn(Codec<cmh> var1) {
      super(_snowman);
   }

   public boolean a(bsr var1, cfy var2, Random var3, fx var4, cmh var5) {
      bzm _snowman = bzm.a(_snowman);
      int _snowmanx = _snowman.nextInt(aq.length);
      csw _snowmanxx = _snowman.E().l().aW();
      ctb _snowmanxxx = _snowmanxx.a(aq[_snowmanx]);
      ctb _snowmanxxxx = _snowmanxx.a(ar[_snowmanx]);
      brd _snowmanxxxxx = new brd(_snowman);
      cra _snowmanxxxxxx = new cra(_snowmanxxxxx.d(), 0, _snowmanxxxxx.e(), _snowmanxxxxx.f(), 256, _snowmanxxxxx.g());
      csx _snowmanxxxxxxx = new csx().a(_snowman).a(_snowmanxxxxxx).a(_snowman).a(cse.d);
      fx _snowmanxxxxxxxx = _snowmanxxx.a(_snowman);
      int _snowmanxxxxxxxxx = _snowman.nextInt(16 - _snowmanxxxxxxxx.u());
      int _snowmanxxxxxxxxxx = _snowman.nextInt(16 - _snowmanxxxxxxxx.w());
      int _snowmanxxxxxxxxxxx = 256;

      for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx < _snowmanxxxxxxxx.u(); _snowmanxxxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < _snowmanxxxxxxxx.w(); _snowmanxxxxxxxxxxxxx++) {
            _snowmanxxxxxxxxxxx = Math.min(_snowmanxxxxxxxxxxx, _snowman.a(chn.a.c, _snowman.u() + _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxx, _snowman.w() + _snowmanxxxxxxxxxxxxx + _snowmanxxxxxxxxxx));
         }
      }

      int _snowmanxxxxxxxxxxxx = Math.max(_snowmanxxxxxxxxxxx - 15 - _snowman.nextInt(10), 10);
      fx _snowmanxxxxxxxxxxxxx = _snowmanxxx.a(_snowman.b(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxx), byg.a, _snowman);
      csg _snowmanxxxxxxxxxxxxxx = new csg(0.9F);
      _snowmanxxxxxxx.b().a(_snowmanxxxxxxxxxxxxxx);
      _snowmanxxx.a(_snowman, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxx, _snowman, 4);
      _snowmanxxxxxxx.b(_snowmanxxxxxxxxxxxxxx);
      csg _snowmanxxxxxxxxxxxxxxx = new csg(0.1F);
      _snowmanxxxxxxx.b().a(_snowmanxxxxxxxxxxxxxxx);
      _snowmanxxxx.a(_snowman, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxx, _snowman, 4);
      return true;
   }
}
