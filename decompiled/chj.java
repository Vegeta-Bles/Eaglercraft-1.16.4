import com.mojang.serialization.Codec;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class chj extends cfy {
   public static final Codec<chj> d = vg.a(gm.ay).xmap(chj::new, chj::g).stable().codec();
   private static final List<ceh> g = StreamSupport.stream(gm.Q.spliterator(), false).flatMap(var0 -> var0.m().a().stream()).collect(Collectors.toList());
   private static final int h = afm.f(afm.c((float)g.size()));
   private static final int i = afm.f((float)g.size() / (float)h);
   protected static final ceh e = bup.a.n();
   protected static final ceh f = bup.go.n();
   private final gm<bsv> j;

   public chj(gm<bsv> var1) {
      super(new btd(_snowman.d(btb.b)), new chv(false));
      this.j = _snowman;
   }

   public gm<bsv> g() {
      return this.j;
   }

   @Override
   protected Codec<? extends cfy> a() {
      return d;
   }

   @Override
   public cfy a(long var1) {
      return this;
   }

   @Override
   public void a(aam var1, cfw var2) {
   }

   @Override
   public void a(long var1, bsx var3, cfw var4, chm.a var5) {
   }

   @Override
   public void a(aam var1, bsn var2) {
      fx.a _snowman = new fx.a();
      int _snowmanx = _snowman.a();
      int _snowmanxx = _snowman.b();

      for (int _snowmanxxx = 0; _snowmanxxx < 16; _snowmanxxx++) {
         for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
            int _snowmanxxxxx = (_snowmanx << 4) + _snowmanxxx;
            int _snowmanxxxxxx = (_snowmanxx << 4) + _snowmanxxxx;
            _snowman.a(_snowman.d(_snowmanxxxxx, 60, _snowmanxxxxxx), f, 2);
            ceh _snowmanxxxxxxx = b(_snowmanxxxxx, _snowmanxxxxxx);
            if (_snowmanxxxxxxx != null) {
               _snowman.a(_snowman.d(_snowmanxxxxx, 70, _snowmanxxxxxx), _snowmanxxxxxxx, 2);
            }
         }
      }
   }

   @Override
   public void a(bry var1, bsn var2, cfw var3) {
   }

   @Override
   public int a(int var1, int var2, chn.a var3) {
      return 0;
   }

   @Override
   public brc a(int var1, int var2) {
      return new bsh(new ceh[0]);
   }

   public static ceh b(int var0, int var1) {
      ceh _snowman = e;
      if (_snowman > 0 && _snowman > 0 && _snowman % 2 != 0 && _snowman % 2 != 0) {
         _snowman /= 2;
         _snowman /= 2;
         if (_snowman <= h && _snowman <= i) {
            int _snowmanx = afm.a(_snowman * h + _snowman);
            if (_snowmanx < g.size()) {
               _snowman = g.get(_snowmanx);
            }
         }
      }

      return _snowman;
   }
}
