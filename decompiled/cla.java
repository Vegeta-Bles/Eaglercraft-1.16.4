import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class cla<C extends cma> {
   public static final BiMap<String, cla<?>> a = HashBiMap.create();
   private static final Map<cla<?>, chm.b> u = Maps.newHashMap();
   private static final Logger v = LogManager.getLogger();
   public static final cla<cmc> b = a("Pillager_Outpost", new ckj(cmc.a), chm.b.e);
   public static final cla<cme> c = a("Mineshaft", new ckb(cme.a), chm.b.d);
   public static final cla<cmh> d = a("Mansion", new clk(cmh.a), chm.b.e);
   public static final cla<cmh> e = a("Jungle_Pyramid", new cjy(cmh.a), chm.b.e);
   public static final cla<cmh> f = a("Desert_Pyramid", new cje(cmh.a), chm.b.e);
   public static final cla<cmh> g = a("Igloo", new cjw(cmh.a), chm.b.e);
   public static final cla<cmr> h = a("Ruined_Portal", new ckp(cmr.a), chm.b.e);
   public static final cla<cms> i = a("Shipwreck", new cks(cms.a), chm.b.e);
   public static final clc j = a("Swamp_Hut", new clc(cmh.a), chm.b.e);
   public static final cla<cmh> k = a("Stronghold", new ckz(cmh.a), chm.b.f);
   public static final cla<cmh> l = a("Monument", new ckh(cmh.a), chm.b.e);
   public static final cla<cmi> m = a("Ocean_Ruin", new crm(cmi.a), chm.b.e);
   public static final cla<cmh> n = a("Fortress", new cke(cmh.a), chm.b.h);
   public static final cla<cmh> o = a("EndCity", new cjh(cmh.a), chm.b.e);
   public static final cla<cmk> p = a("Buried_Treasure", new cit(cmk.b), chm.b.d);
   public static final cla<cmc> q = a("Village", new clf(cmc.a), chm.b.e);
   public static final cla<cmh> r = a("Nether_Fossil", new crj(cmh.a), chm.b.h);
   public static final cla<cmc> s = a("Bastion_Remnant", new cio(cmc.a), chm.b.e);
   public static final List<cla<?>> t = ImmutableList.of(b, q, r);
   private static final vk w = new vk("jigsaw");
   private static final Map<vk, vk> x = ImmutableMap.builder()
      .put(new vk("nvi"), w)
      .put(new vk("pcp"), w)
      .put(new vk("bastionremnant"), w)
      .put(new vk("runtime"), w)
      .build();
   private final Codec<ciw<C, cla<C>>> y;

   private static <F extends cla<?>> F a(String var0, F var1, chm.b var2) {
      a.put(_snowman.toLowerCase(Locale.ROOT), _snowman);
      u.put(_snowman, _snowman);
      return gm.a(gm.aG, _snowman.toLowerCase(Locale.ROOT), _snowman);
   }

   public cla(Codec<C> var1) {
      this.y = _snowman.fieldOf("config").xmap(var1x -> new ciw<>(this, (C)var1x), var0 -> var0.e).codec();
   }

   public chm.b f() {
      return u.get(this);
   }

   public static void g() {
   }

   @Nullable
   public static crv<?> a(csw var0, md var1, long var2) {
      String _snowman = _snowman.l("id");
      if ("INVALID".equals(_snowman)) {
         return crv.a;
      } else {
         cla<?> _snowmanx = gm.aG.a(new vk(_snowman.toLowerCase(Locale.ROOT)));
         if (_snowmanx == null) {
            v.error("Unknown feature id: {}", _snowman);
            return null;
         } else {
            int _snowmanxx = _snowman.h("ChunkX");
            int _snowmanxxx = _snowman.h("ChunkZ");
            int _snowmanxxxx = _snowman.h("references");
            cra _snowmanxxxxx = _snowman.e("BB") ? new cra(_snowman.n("BB")) : cra.a();
            mj _snowmanxxxxxx = _snowman.d("Children", 10);

            try {
               crv<?> _snowmanxxxxxxx = _snowmanx.a(_snowmanxx, _snowmanxxx, _snowmanxxxxx, _snowmanxxxx, _snowman);

               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxxx.size(); _snowmanxxxxxxxx++) {
                  md _snowmanxxxxxxxxx = _snowmanxxxxxx.a(_snowmanxxxxxxxx);
                  String _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.l("id").toLowerCase(Locale.ROOT);
                  vk _snowmanxxxxxxxxxxx = new vk(_snowmanxxxxxxxxxx);
                  vk _snowmanxxxxxxxxxxxx = x.getOrDefault(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxx);
                  clb _snowmanxxxxxxxxxxxxx = gm.aI.a(_snowmanxxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxxxx == null) {
                     v.error("Unknown structure piece id: {}", _snowmanxxxxxxxxxxxx);
                  } else {
                     try {
                        cru _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.load(_snowman, _snowmanxxxxxxxxx);
                        _snowmanxxxxxxx.d().add(_snowmanxxxxxxxxxxxxxx);
                     } catch (Exception var19) {
                        v.error("Exception loading structure piece with id {}", _snowmanxxxxxxxxxxxx, var19);
                     }
                  }
               }

               return _snowmanxxxxxxx;
            } catch (Exception var20) {
               v.error("Failed Start with id {}", _snowman, var20);
               return null;
            }
         }
      }
   }

   public Codec<ciw<C, cla<C>>> h() {
      return this.y;
   }

   public ciw<C, ? extends cla<C>> a(C var1) {
      return new ciw<>(this, _snowman);
   }

   @Nullable
   public fx a(brz var1, bsn var2, fx var3, int var4, boolean var5, long var6, cmy var8) {
      int _snowman = _snowman.a();
      int _snowmanx = _snowman.u() >> 4;
      int _snowmanxx = _snowman.w() >> 4;
      int _snowmanxxx = 0;

      for (chx _snowmanxxxx = new chx(); _snowmanxxx <= _snowman; _snowmanxxx++) {
         for (int _snowmanxxxxx = -_snowmanxxx; _snowmanxxxxx <= _snowmanxxx; _snowmanxxxxx++) {
            boolean _snowmanxxxxxx = _snowmanxxxxx == -_snowmanxxx || _snowmanxxxxx == _snowmanxxx;

            for (int _snowmanxxxxxxx = -_snowmanxxx; _snowmanxxxxxxx <= _snowmanxxx; _snowmanxxxxxxx++) {
               boolean _snowmanxxxxxxxx = _snowmanxxxxxxx == -_snowmanxxx || _snowmanxxxxxxx == _snowmanxxx;
               if (_snowmanxxxxxx || _snowmanxxxxxxxx) {
                  int _snowmanxxxxxxxxx = _snowmanx + _snowman * _snowmanxxxxx;
                  int _snowmanxxxxxxxxxx = _snowmanxx + _snowman * _snowmanxxxxxxx;
                  brd _snowmanxxxxxxxxxxx = this.a(_snowman, _snowman, _snowmanxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
                  cfw _snowmanxxxxxxxxxxxx = _snowman.a(_snowmanxxxxxxxxxxx.b, _snowmanxxxxxxxxxxx.c, cga.b);
                  crv<?> _snowmanxxxxxxxxxxxxx = _snowman.a(gp.a(_snowmanxxxxxxxxxxxx.g(), 0), this, _snowmanxxxxxxxxxxxx);
                  if (_snowmanxxxxxxxxxxxxx != null && _snowmanxxxxxxxxxxxxx.e()) {
                     if (_snowman && _snowmanxxxxxxxxxxxxx.h()) {
                        _snowmanxxxxxxxxxxxxx.i();
                        return _snowmanxxxxxxxxxxxxx.a();
                     }

                     if (!_snowman) {
                        return _snowmanxxxxxxxxxxxxx.a();
                     }
                  }

                  if (_snowmanxxx == 0) {
                     break;
                  }
               }
            }

            if (_snowmanxxx == 0) {
               break;
            }
         }
      }

      return null;
   }

   protected boolean b() {
      return true;
   }

   public final brd a(cmy var1, long var2, chx var4, int var5, int var6) {
      int _snowman = _snowman.a();
      int _snowmanx = _snowman.b();
      int _snowmanxx = Math.floorDiv(_snowman, _snowman);
      int _snowmanxxx = Math.floorDiv(_snowman, _snowman);
      _snowman.a(_snowman, _snowmanxx, _snowmanxxx, _snowman.c());
      int _snowmanxxxx;
      int _snowmanxxxxx;
      if (this.b()) {
         _snowmanxxxx = _snowman.nextInt(_snowman - _snowmanx);
         _snowmanxxxxx = _snowman.nextInt(_snowman - _snowmanx);
      } else {
         _snowmanxxxx = (_snowman.nextInt(_snowman - _snowmanx) + _snowman.nextInt(_snowman - _snowmanx)) / 2;
         _snowmanxxxxx = (_snowman.nextInt(_snowman - _snowmanx) + _snowman.nextInt(_snowman - _snowmanx)) / 2;
      }

      return new brd(_snowmanxx * _snowman + _snowmanxxxx, _snowmanxxx * _snowman + _snowmanxxxxx);
   }

   protected boolean a(cfy var1, bsy var2, long var3, chx var5, int var6, int var7, bsv var8, brd var9, C var10) {
      return true;
   }

   private crv<C> a(int var1, int var2, cra var3, int var4, long var5) {
      return this.a().create(this, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public crv<?> a(gn var1, cfy var2, bsy var3, csw var4, long var5, brd var7, bsv var8, int var9, chx var10, cmy var11, C var12) {
      brd _snowman = this.a(_snowman, _snowman, _snowman, _snowman.b, _snowman.c);
      if (_snowman.b == _snowman.b && _snowman.c == _snowman.c && this.a(_snowman, _snowman, _snowman, _snowman, _snowman.b, _snowman.c, _snowman, _snowman, _snowman)) {
         crv<C> _snowmanx = this.a(_snowman.b, _snowman.c, cra.a(), _snowman, _snowman);
         _snowmanx.a(_snowman, _snowman, _snowman, _snowman.b, _snowman.c, _snowman, _snowman);
         if (_snowmanx.e()) {
            return _snowmanx;
         }
      }

      return crv.a;
   }

   public abstract cla.a<C> a();

   public String i() {
      return (String)a.inverse().get(this);
   }

   public List<btg.c> c() {
      return ImmutableList.of();
   }

   public List<btg.c> j() {
      return ImmutableList.of();
   }

   public interface a<C extends cma> {
      crv<C> create(cla<C> var1, int var2, int var3, cra var4, int var5, long var6);
   }
}
