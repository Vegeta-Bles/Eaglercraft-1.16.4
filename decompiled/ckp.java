import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class ckp extends cla<cmr> {
   private static final String[] u = new String[]{
      "ruined_portal/portal_1",
      "ruined_portal/portal_2",
      "ruined_portal/portal_3",
      "ruined_portal/portal_4",
      "ruined_portal/portal_5",
      "ruined_portal/portal_6",
      "ruined_portal/portal_7",
      "ruined_portal/portal_8",
      "ruined_portal/portal_9",
      "ruined_portal/portal_10"
   };
   private static final String[] v = new String[]{"ruined_portal/giant_portal_1", "ruined_portal/giant_portal_2", "ruined_portal/giant_portal_3"};

   public ckp(Codec<cmr> var1) {
      super(_snowman);
   }

   @Override
   public cla.a<cmr> a() {
      return ckp.a::new;
   }

   private static boolean b(fx var0, bsv var1) {
      return _snowman.a(_snowman) < 0.15F;
   }

   private static int b(Random var0, cfy var1, crp.b var2, boolean var3, int var4, int var5, cra var6) {
      int _snowman;
      if (_snowman == crp.b.f) {
         if (_snowman) {
            _snowman = a(_snowman, 32, 100);
         } else if (_snowman.nextFloat() < 0.5F) {
            _snowman = a(_snowman, 27, 29);
         } else {
            _snowman = a(_snowman, 29, 100);
         }
      } else if (_snowman == crp.b.d) {
         int _snowmanx = _snowman - _snowman;
         _snowman = b(_snowman, 70, _snowmanx);
      } else if (_snowman == crp.b.e) {
         int _snowmanx = _snowman - _snowman;
         _snowman = b(_snowman, 15, _snowmanx);
      } else if (_snowman == crp.b.b) {
         _snowman = _snowman - _snowman + a(_snowman, 2, 8);
      } else {
         _snowman = _snowman;
      }

      List<fx> _snowmanx = ImmutableList.of(new fx(_snowman.a, 0, _snowman.c), new fx(_snowman.d, 0, _snowman.c), new fx(_snowman.a, 0, _snowman.f), new fx(_snowman.d, 0, _snowman.f));
      List<brc> _snowmanxx = _snowmanx.stream().map(var1x -> _snowman.a(var1x.u(), var1x.w())).collect(Collectors.toList());
      chn.a _snowmanxxx = _snowman == crp.b.c ? chn.a.c : chn.a.a;
      fx.a _snowmanxxxx = new fx.a();

      int _snowmanxxxxx;
      for (_snowmanxxxxx = _snowman; _snowmanxxxxx > 15; _snowmanxxxxx--) {
         int _snowmanxxxxxx = 0;
         _snowmanxxxx.d(0, _snowmanxxxxx, 0);

         for (brc _snowmanxxxxxxx : _snowmanxx) {
            ceh _snowmanxxxxxxxx = _snowmanxxxxxxx.d_(_snowmanxxxx);
            if (_snowmanxxxxxxxx != null && _snowmanxxx.e().test(_snowmanxxxxxxxx)) {
               if (++_snowmanxxxxxx == 3) {
                  return _snowmanxxxxx;
               }
            }
         }
      }

      return _snowmanxxxxx;
   }

   private static int a(Random var0, int var1, int var2) {
      return _snowman.nextInt(_snowman - _snowman + 1) + _snowman;
   }

   private static int b(Random var0, int var1, int var2) {
      return _snowman < _snowman ? a(_snowman, _snowman, _snowman) : _snowman;
   }

   public static class a extends crv<cmr> {
      protected a(cla<cmr> var1, int var2, int var3, cra var4, int var5, long var6) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void a(gn var1, cfy var2, csw var3, int var4, int var5, bsv var6, cmr var7) {
         crp.a _snowman = new crp.a();
         crp.b _snowmanx;
         if (_snowman.b == ckp.b.b) {
            _snowmanx = crp.b.b;
            _snowman.d = false;
            _snowman.c = 0.0F;
         } else if (_snowman.b == ckp.b.c) {
            _snowmanx = crp.b.a;
            _snowman.d = this.d.nextFloat() < 0.5F;
            _snowman.c = 0.8F;
            _snowman.e = true;
            _snowman.f = true;
         } else if (_snowman.b == ckp.b.d) {
            _snowmanx = crp.b.c;
            _snowman.d = false;
            _snowman.c = 0.5F;
            _snowman.f = true;
         } else if (_snowman.b == ckp.b.e) {
            boolean _snowmanxx = this.d.nextFloat() < 0.5F;
            _snowmanx = _snowmanxx ? crp.b.d : crp.b.a;
            _snowman.d = _snowmanxx || this.d.nextFloat() < 0.5F;
         } else if (_snowman.b == ckp.b.f) {
            _snowmanx = crp.b.c;
            _snowman.d = false;
            _snowman.c = 0.8F;
         } else if (_snowman.b == ckp.b.g) {
            _snowmanx = crp.b.f;
            _snowman.d = this.d.nextFloat() < 0.5F;
            _snowman.c = 0.0F;
            _snowman.g = true;
         } else {
            boolean _snowmanxx = this.d.nextFloat() < 0.5F;
            _snowmanx = _snowmanxx ? crp.b.e : crp.b.a;
            _snowman.d = _snowmanxx || this.d.nextFloat() < 0.5F;
         }

         vk _snowmanxx;
         if (this.d.nextFloat() < 0.05F) {
            _snowmanxx = new vk(ckp.v[this.d.nextInt(ckp.v.length)]);
         } else {
            _snowmanxx = new vk(ckp.u[this.d.nextInt(ckp.u.length)]);
         }

         ctb _snowmanxxx = _snowman.a(_snowmanxx);
         bzm _snowmanxxxx = x.a(bzm.values(), this.d);
         byg _snowmanxxxxx = this.d.nextFloat() < 0.5F ? byg.a : byg.c;
         fx _snowmanxxxxxx = new fx(_snowmanxxx.a().u() / 2, 0, _snowmanxxx.a().w() / 2);
         fx _snowmanxxxxxxx = new brd(_snowman, _snowman).l();
         cra _snowmanxxxxxxxx = _snowmanxxx.a(_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxx, _snowmanxxxxx);
         gr _snowmanxxxxxxxxx = _snowmanxxxxxxxx.g();
         int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.u();
         int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.w();
         int _snowmanxxxxxxxxxxxx = _snowman.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, crp.a(_snowmanx)) - 1;
         int _snowmanxxxxxxxxxxxxx = ckp.b(this.d, _snowman, _snowmanx, _snowman.d, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxx.e(), _snowmanxxxxxxxx);
         fx _snowmanxxxxxxxxxxxxxx = new fx(_snowmanxxxxxxx.u(), _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxx.w());
         if (_snowman.b == ckp.b.e || _snowman.b == ckp.b.f || _snowman.b == ckp.b.a) {
            _snowman.b = ckp.b(_snowmanxxxxxxxxxxxxxx, _snowman);
         }

         this.b.add(new crp(_snowmanxxxxxxxxxxxxxx, _snowmanx, _snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx));
         this.b();
      }
   }

   public static enum b implements afs {
      a("standard"),
      b("desert"),
      c("jungle"),
      d("swamp"),
      e("mountain"),
      f("ocean"),
      g("nether");

      public static final Codec<ckp.b> h = afs.a(ckp.b::values, ckp.b::a);
      private static final Map<String, ckp.b> i = Arrays.stream(values()).collect(Collectors.toMap(ckp.b::b, var0 -> (ckp.b)var0));
      private final String j;

      private b(String var3) {
         this.j = _snowman;
      }

      public String b() {
         return this.j;
      }

      public static ckp.b a(String var0) {
         return i.get(_snowman);
      }

      @Override
      public String a() {
         return this.j;
      }
   }
}
