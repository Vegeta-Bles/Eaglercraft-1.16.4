import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ecs<T extends cdl> extends ece<T> {
   public static final vk a = new vk("textures/environment/end_sky.png");
   public static final vk c = new vk("textures/entity/end_portal.png");
   private static final Random d = new Random(31100L);
   private static final List<eao> e = IntStream.range(0, 16).mapToObj(var0 -> eao.a(var0 + 1)).collect(ImmutableList.toImmutableList());

   public ecs(ecd var1) {
      super(_snowman);
   }

   public void a(T var1, float var2, dfm var3, eag var4, int var5, int var6) {
      d.setSeed(31100L);
      double _snowman = _snowman.o().a(this.b.d.b(), true);
      int _snowmanx = this.a(_snowman);
      float _snowmanxx = this.a();
      b _snowmanxxx = _snowman.c().a();
      this.a(_snowman, _snowmanxx, 0.15F, _snowmanxxx, _snowman.getBuffer(e.get(0)));

      for (int _snowmanxxxx = 1; _snowmanxxxx < _snowmanx; _snowmanxxxx++) {
         this.a(_snowman, _snowmanxx, 2.0F / (float)(18 - _snowmanxxxx), _snowmanxxx, _snowman.getBuffer(e.get(_snowmanxxxx)));
      }
   }

   private void a(T var1, float var2, float var3, b var4, dfq var5) {
      float _snowman = (d.nextFloat() * 0.5F + 0.1F) * _snowman;
      float _snowmanx = (d.nextFloat() * 0.5F + 0.4F) * _snowman;
      float _snowmanxx = (d.nextFloat() * 0.5F + 0.5F) * _snowman;
      this.a(_snowman, _snowman, _snowman, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, _snowman, _snowmanx, _snowmanxx, gc.d);
      this.a(_snowman, _snowman, _snowman, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, _snowman, _snowmanx, _snowmanxx, gc.c);
      this.a(_snowman, _snowman, _snowman, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, _snowman, _snowmanx, _snowmanxx, gc.f);
      this.a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, _snowman, _snowmanx, _snowmanxx, gc.e);
      this.a(_snowman, _snowman, _snowman, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, _snowman, _snowmanx, _snowmanxx, gc.a);
      this.a(_snowman, _snowman, _snowman, 0.0F, 1.0F, _snowman, _snowman, 1.0F, 1.0F, 0.0F, 0.0F, _snowman, _snowmanx, _snowmanxx, gc.b);
   }

   private void a(
      T var1,
      b var2,
      dfq var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      float var13,
      float var14,
      gc var15
   ) {
      if (_snowman.a(_snowman)) {
         _snowman.a(_snowman, _snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, 1.0F).d();
         _snowman.a(_snowman, _snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, 1.0F).d();
         _snowman.a(_snowman, _snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, 1.0F).d();
         _snowman.a(_snowman, _snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, 1.0F).d();
      }
   }

   protected int a(double var1) {
      if (_snowman > 36864.0) {
         return 1;
      } else if (_snowman > 25600.0) {
         return 3;
      } else if (_snowman > 16384.0) {
         return 5;
      } else if (_snowman > 9216.0) {
         return 7;
      } else if (_snowman > 4096.0) {
         return 9;
      } else if (_snowman > 1024.0) {
         return 11;
      } else if (_snowman > 576.0) {
         return 13;
      } else {
         return _snowman > 256.0 ? 14 : 15;
      }
   }

   protected float a() {
      return 0.75F;
   }
}
