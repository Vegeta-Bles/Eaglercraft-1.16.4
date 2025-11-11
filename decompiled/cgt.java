import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.shorts.ShortList;
import it.unimi.dsi.fastutil.shorts.ShortListIterator;
import java.util.Arrays;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cgt {
   private static final Logger a = LogManager.getLogger();

   public static cgp a(aag var0, csw var1, azo var2, brd var3, md var4) {
      cfy _snowman = _snowman.i().g();
      bsy _snowmanx = _snowman.d();
      md _snowmanxx = _snowman.p("Level");
      brd _snowmanxxx = new brd(_snowmanxx.h("xPos"), _snowmanxx.h("zPos"));
      if (!Objects.equals(_snowman, _snowmanxxx)) {
         a.error("Chunk file at {} is in the wrong location; relocating. (Expected {}, got {})", _snowman, _snowman, _snowmanxxx);
      }

      cfx _snowmanxxxx = new cfx(_snowman.r().b(gm.ay), _snowman, _snowmanx, _snowmanxx.c("Biomes", 11) ? _snowmanxx.n("Biomes") : null);
      cgr _snowmanxxxxx = _snowmanxx.c("UpgradeData", 10) ? new cgr(_snowmanxx.p("UpgradeData")) : cgr.a;
      cgq<buo> _snowmanxxxxxx = new cgq<>(var0x -> var0x == null || var0x.n().g(), _snowman, _snowmanxx.d("ToBeTicked", 9));
      cgq<cuw> _snowmanxxxxxxx = new cgq<>(var0x -> var0x == null || var0x == cuy.a, _snowman, _snowmanxx.d("LiquidsToBeTicked", 9));
      boolean _snowmanxxxxxxxx = _snowmanxx.q("isLightOn");
      mj _snowmanxxxxxxxxx = _snowmanxx.d("Sections", 10);
      int _snowmanxxxxxxxxxx = 16;
      cgi[] _snowmanxxxxxxxxxxx = new cgi[16];
      boolean _snowmanxxxxxxxxxxxx = _snowman.k().b();
      cfz _snowmanxxxxxxxxxxxxx = _snowman.i();
      cuo _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.l();
      if (_snowmanxxxxxxxx) {
         _snowmanxxxxxxxxxxxxxx.b(_snowman, true);
      }

      for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < _snowmanxxxxxxxxx.size(); _snowmanxxxxxxxxxxxxxxx++) {
         md _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxx);
         int _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.f("Y");
         if (_snowmanxxxxxxxxxxxxxxxx.c("Palette", 9) && _snowmanxxxxxxxxxxxxxxxx.c("BlockStates", 12)) {
            cgi _snowmanxxxxxxxxxxxxxxxxxx = new cgi(_snowmanxxxxxxxxxxxxxxxxx << 4);
            _snowmanxxxxxxxxxxxxxxxxxx.i().a(_snowmanxxxxxxxxxxxxxxxx.d("Palette", 10), _snowmanxxxxxxxxxxxxxxxx.o("BlockStates"));
            _snowmanxxxxxxxxxxxxxxxxxx.h();
            if (!_snowmanxxxxxxxxxxxxxxxxxx.c()) {
               _snowmanxxxxxxxxxxx[_snowmanxxxxxxxxxxxxxxxxx] = _snowmanxxxxxxxxxxxxxxxxxx;
            }

            _snowman.a(_snowman, _snowmanxxxxxxxxxxxxxxxxxx);
         }

         if (_snowmanxxxxxxxx) {
            if (_snowmanxxxxxxxxxxxxxxxx.c("BlockLight", 7)) {
               _snowmanxxxxxxxxxxxxxx.a(bsf.b, gp.a(_snowman, _snowmanxxxxxxxxxxxxxxxxx), new cgb(_snowmanxxxxxxxxxxxxxxxx.m("BlockLight")), true);
            }

            if (_snowmanxxxxxxxxxxxx && _snowmanxxxxxxxxxxxxxxxx.c("SkyLight", 7)) {
               _snowmanxxxxxxxxxxxxxx.a(bsf.a, gp.a(_snowman, _snowmanxxxxxxxxxxxxxxxxx), new cgb(_snowmanxxxxxxxxxxxxxxxx.m("SkyLight")), true);
            }
         }
      }

      long _snowmanxxxxxxxxxxxxxxx = _snowmanxx.i("InhabitedTime");
      cga.a _snowmanxxxxxxxxxxxxxxxxxx = a(_snowman);
      cfw _snowmanxxxxxxxxxxxxxxxxxxx;
      if (_snowmanxxxxxxxxxxxxxxxxxx == cga.a.b) {
         bso<buo> _snowmanxxxxxxxxxxxxxxxxxxxx;
         if (_snowmanxx.c("TileTicks", 9)) {
            _snowmanxxxxxxxxxxxxxxxxxxxx = bre.a(_snowmanxx.d("TileTicks", 10), gm.Q::b, gm.Q::a);
         } else {
            _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx;
         }

         bso<cuw> _snowmanxxxxxxxxxxxxxxxxxxxxx;
         if (_snowmanxx.c("LiquidTicks", 9)) {
            _snowmanxxxxxxxxxxxxxxxxxxxxx = bre.a(_snowmanxx.d("LiquidTicks", 10), gm.O::b, gm.O::a);
         } else {
            _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx;
         }

         _snowmanxxxxxxxxxxxxxxxxxxx = new cgh(
            _snowman.E(), _snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx, var1x -> a(_snowman, var1x)
         );
      } else {
         cgp _snowmanxxxxxxxxxxxxxxxxxxxxx = new cgp(_snowman, _snowmanxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
         _snowmanxxxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxx);
         _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx;
         _snowmanxxxxxxxxxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxxxxxxx);
         _snowmanxxxxxxxxxxxxxxxxxxxxx.a(cga.a(_snowmanxx.l("Status")));
         if (_snowmanxxxxxxxxxxxxxxxxxxxxx.k().b(cga.i)) {
            _snowmanxxxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxx);
         }

         if (!_snowmanxxxxxxxx && _snowmanxxxxxxxxxxxxxxxxxxxxx.k().b(cga.j)) {
            for (fx _snowmanxxxxxxxxxxxxxxxxxxxxxx : fx.b(_snowman.d(), 0, _snowman.e(), _snowman.f(), 255, _snowman.g())) {
               if (_snowmanxxxxxxxxxxxxxxxxxxx.d_(_snowmanxxxxxxxxxxxxxxxxxxxxxx).f() != 0) {
                  _snowmanxxxxxxxxxxxxxxxxxxxxx.k(_snowmanxxxxxxxxxxxxxxxxxxxxxx);
               }
            }
         }
      }

      _snowmanxxxxxxxxxxxxxxxxxxx.b(_snowmanxxxxxxxx);
      md _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx.p("Heightmaps");
      EnumSet<chn.a> _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = EnumSet.noneOf(chn.a.class);

      for (chn.a _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxxxx.k().h()) {
         String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.b();
         if (_snowmanxxxxxxxxxxxxxxxxxxxxxxx.c(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, 12)) {
            _snowmanxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx.o(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx));
         } else {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx);
         }
      }

      chn.a(_snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
      md _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx.p("Structures");
      _snowmanxxxxxxxxxxxxxxxxxxx.a(a(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowman.C()));
      _snowmanxxxxxxxxxxxxxxxxxxx.b(a(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx));
      if (_snowmanxx.q("shouldSave")) {
         _snowmanxxxxxxxxxxxxxxxxxxx.a(true);
      }

      mj _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx.d("PostProcessing", 9);

      for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.size(); _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx++) {
         mj _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx);

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.size();
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            _snowmanxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.d(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx), _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         }
      }

      if (_snowmanxxxxxxxxxxxxxxxxxx == cga.a.b) {
         return new cgg((cgh)_snowmanxxxxxxxxxxxxxxxxxxx);
      } else {
         cgp _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (cgp)_snowmanxxxxxxxxxxxxxxxxxxx;
         mj _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx.d("Entities", 10);

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.size();
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
         }

         mj _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx.d("TileEntities", 10);

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.size();
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            md _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
            _snowmanxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
         }

         mj _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx.d("Lights", 9);

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.size();
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
         ) {
            mj _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.size();
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx++
            ) {
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.d(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx), _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
            }
         }

         md _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxx.p("CarvingMasks");

         for (String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.d()) {
            chm.a _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = chm.a.valueOf(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(
               _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, BitSet.valueOf(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.m(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx))
            );
         }

         return _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
      }
   }

   public static md a(aag var0, cfw var1) {
      brd _snowman = _snowman.g();
      md _snowmanx = new md();
      md _snowmanxx = new md();
      _snowmanx.b("DataVersion", w.a().getWorldVersion());
      _snowmanx.a("Level", _snowmanxx);
      _snowmanxx.b("xPos", _snowman.b);
      _snowmanxx.b("zPos", _snowman.c);
      _snowmanxx.a("LastUpdate", _snowman.T());
      _snowmanxx.a("InhabitedTime", _snowman.q());
      _snowmanxx.a("Status", _snowman.k().d());
      cgr _snowmanxxx = _snowman.p();
      if (!_snowmanxxx.a()) {
         _snowmanxx.a("UpgradeData", _snowmanxxx.b());
      }

      cgi[] _snowmanxxxx = _snowman.d();
      mj _snowmanxxxxx = new mj();
      cuo _snowmanxxxxxx = _snowman.i().a();
      boolean _snowmanxxxxxxx = _snowman.r();

      for (int _snowmanxxxxxxxx = -1; _snowmanxxxxxxxx < 17; _snowmanxxxxxxxx++) {
         int _snowmanxxxxxxxxx = _snowmanxxxxxxxx;
         cgi _snowmanxxxxxxxxxx = Arrays.stream(_snowmanxxxx).filter(var1x -> var1x != null && var1x.g() >> 4 == _snowman).findFirst().orElse(cgh.a);
         cgb _snowmanxxxxxxxxxxx = _snowmanxxxxxx.a(bsf.b).a(gp.a(_snowman, _snowmanxxxxxxxxx));
         cgb _snowmanxxxxxxxxxxxx = _snowmanxxxxxx.a(bsf.a).a(gp.a(_snowman, _snowmanxxxxxxxxx));
         if (_snowmanxxxxxxxxxx != cgh.a || _snowmanxxxxxxxxxxx != null || _snowmanxxxxxxxxxxxx != null) {
            md _snowmanxxxxxxxxxxxxx = new md();
            _snowmanxxxxxxxxxxxxx.a("Y", (byte)(_snowmanxxxxxxxxx & 0xFF));
            if (_snowmanxxxxxxxxxx != cgh.a) {
               _snowmanxxxxxxxxxx.i().a(_snowmanxxxxxxxxxxxxx, "Palette", "BlockStates");
            }

            if (_snowmanxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxx.c()) {
               _snowmanxxxxxxxxxxxxx.a("BlockLight", _snowmanxxxxxxxxxxx.a());
            }

            if (_snowmanxxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxxx.c()) {
               _snowmanxxxxxxxxxxxxx.a("SkyLight", _snowmanxxxxxxxxxxxx.a());
            }

            _snowmanxxxxx.add(_snowmanxxxxxxxxxxxxx);
         }
      }

      _snowmanxx.a("Sections", _snowmanxxxxx);
      if (_snowmanxxxxxxx) {
         _snowmanxx.a("isLightOn", true);
      }

      cfx _snowmanxxxxxxxxx = _snowman.i();
      if (_snowmanxxxxxxxxx != null) {
         _snowmanxx.a("Biomes", _snowmanxxxxxxxxx.a());
      }

      mj _snowmanxxxxxxxxxx = new mj();

      for (fx _snowmanxxxxxxxxxxx : _snowman.c()) {
         md _snowmanxxxxxxxxxxxx = _snowman.j(_snowmanxxxxxxxxxxx);
         if (_snowmanxxxxxxxxxxxx != null) {
            _snowmanxxxxxxxxxx.add(_snowmanxxxxxxxxxxxx);
         }
      }

      _snowmanxx.a("TileEntities", _snowmanxxxxxxxxxx);
      mj _snowmanxxxxxxxxxxxx = new mj();
      if (_snowman.k().g() == cga.a.b) {
         cgh _snowmanxxxxxxxxxxxxxx = (cgh)_snowman;
         _snowmanxxxxxxxxxxxxxx.d(false);

         for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxx.z().length; _snowmanxxxxxxxxxxxxxxx++) {
            for (aqa _snowmanxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxx.z()[_snowmanxxxxxxxxxxxxxxx]) {
               md _snowmanxxxxxxxxxxxxxxxxx = new md();
               if (_snowmanxxxxxxxxxxxxxxxx.d(_snowmanxxxxxxxxxxxxxxxxx)) {
                  _snowmanxxxxxxxxxxxxxx.d(true);
                  _snowmanxxxxxxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxxx);
               }
            }
         }
      } else {
         cgp _snowmanxxxxxxxxxxxxxx = (cgp)_snowman;
         _snowmanxxxxxxxxxxxx.addAll(_snowmanxxxxxxxxxxxxxx.y());
         _snowmanxx.a("Lights", a(_snowmanxxxxxxxxxxxxxx.w()));
         md _snowmanxxxxxxxxxxxxxxx = new md();

         for (chm.a _snowmanxxxxxxxxxxxxxxxxx : chm.a.values()) {
            BitSet _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxxxxxxxx != null) {
               _snowmanxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxx.toString(), _snowmanxxxxxxxxxxxxxxxxxx.toByteArray());
            }
         }

         _snowmanxx.a("CarvingMasks", _snowmanxxxxxxxxxxxxxxx);
      }

      _snowmanxx.a("Entities", _snowmanxxxxxxxxxxxx);
      bso<buo> _snowmanxxxxxxxxxxxxxx = _snowman.n();
      if (_snowmanxxxxxxxxxxxxxx instanceof cgq) {
         _snowmanxx.a("ToBeTicked", ((cgq)_snowmanxxxxxxxxxxxxxx).b());
      } else if (_snowmanxxxxxxxxxxxxxx instanceof bre) {
         _snowmanxx.a("TileTicks", ((bre)_snowmanxxxxxxxxxxxxxx).b());
      } else {
         _snowmanxx.a("TileTicks", _snowman.j().a(_snowman));
      }

      bso<cuw> _snowmanxxxxxxxxxxxxxxx = _snowman.o();
      if (_snowmanxxxxxxxxxxxxxxx instanceof cgq) {
         _snowmanxx.a("LiquidsToBeTicked", ((cgq)_snowmanxxxxxxxxxxxxxxx).b());
      } else if (_snowmanxxxxxxxxxxxxxxx instanceof bre) {
         _snowmanxx.a("LiquidTicks", ((bre)_snowmanxxxxxxxxxxxxxxx).b());
      } else {
         _snowmanxx.a("LiquidTicks", _snowman.r_().a(_snowman));
      }

      _snowmanxx.a("PostProcessing", a(_snowman.l()));
      md _snowmanxxxxxxxxxxxxxxxxxx = new md();

      for (Entry<chn.a, chn> _snowmanxxxxxxxxxxxxxxxxxxx : _snowman.f()) {
         if (_snowman.k().h().contains(_snowmanxxxxxxxxxxxxxxxxxxx.getKey())) {
            _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxx.getKey().b(), new mk(_snowmanxxxxxxxxxxxxxxxxxxx.getValue().a()));
         }
      }

      _snowmanxx.a("Heightmaps", _snowmanxxxxxxxxxxxxxxxxxx);
      _snowmanxx.a("Structures", a(_snowman, _snowman.h(), _snowman.v()));
      return _snowmanx;
   }

   public static cga.a a(@Nullable md var0) {
      if (_snowman != null) {
         cga _snowman = cga.a(_snowman.p("Level").l("Status"));
         if (_snowman != null) {
            return _snowman.g();
         }
      }

      return cga.a.a;
   }

   private static void a(md var0, cgh var1) {
      mj _snowman = _snowman.d("Entities", 10);
      brx _snowmanx = _snowman.x();

      for (int _snowmanxx = 0; _snowmanxx < _snowman.size(); _snowmanxx++) {
         md _snowmanxxx = _snowman.a(_snowmanxx);
         aqe.a(_snowmanxxx, _snowmanx, var1x -> {
            _snowman.a(var1x);
            return var1x;
         });
         _snowman.d(true);
      }

      mj _snowmanxx = _snowman.d("TileEntities", 10);

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.size(); _snowmanxxx++) {
         md _snowmanxxxx = _snowmanxx.a(_snowmanxxx);
         boolean _snowmanxxxxx = _snowmanxxxx.q("keepPacked");
         if (_snowmanxxxxx) {
            _snowman.a(_snowmanxxxx);
         } else {
            fx _snowmanxxxxxx = new fx(_snowmanxxxx.h("x"), _snowmanxxxx.h("y"), _snowmanxxxx.h("z"));
            ccj _snowmanxxxxxxx = ccj.b(_snowman.d_(_snowmanxxxxxx), _snowmanxxxx);
            if (_snowmanxxxxxxx != null) {
               _snowman.a(_snowmanxxxxxxx);
            }
         }
      }
   }

   private static md a(brd var0, Map<cla<?>, crv<?>> var1, Map<cla<?>, LongSet> var2) {
      md _snowman = new md();
      md _snowmanx = new md();

      for (Entry<cla<?>, crv<?>> _snowmanxx : _snowman.entrySet()) {
         _snowmanx.a(_snowmanxx.getKey().i(), _snowmanxx.getValue().a(_snowman.b, _snowman.c));
      }

      _snowman.a("Starts", _snowmanx);
      md _snowmanxx = new md();

      for (Entry<cla<?>, LongSet> _snowmanxxx : _snowman.entrySet()) {
         _snowmanxx.a(_snowmanxxx.getKey().i(), new mk(_snowmanxxx.getValue()));
      }

      _snowman.a("References", _snowmanxx);
      return _snowman;
   }

   private static Map<cla<?>, crv<?>> a(csw var0, md var1, long var2) {
      Map<cla<?>, crv<?>> _snowman = Maps.newHashMap();
      md _snowmanx = _snowman.p("Starts");

      for (String _snowmanxx : _snowmanx.d()) {
         String _snowmanxxx = _snowmanxx.toLowerCase(Locale.ROOT);
         cla<?> _snowmanxxxx = (cla<?>)cla.a.get(_snowmanxxx);
         if (_snowmanxxxx == null) {
            a.error("Unknown structure start: {}", _snowmanxxx);
         } else {
            crv<?> _snowmanxxxxx = cla.a(_snowman, _snowmanx.p(_snowmanxx), _snowman);
            if (_snowmanxxxxx != null) {
               _snowman.put(_snowmanxxxx, _snowmanxxxxx);
            }
         }
      }

      return _snowman;
   }

   private static Map<cla<?>, LongSet> a(brd var0, md var1) {
      Map<cla<?>, LongSet> _snowman = Maps.newHashMap();
      md _snowmanx = _snowman.p("References");

      for (String _snowmanxx : _snowmanx.d()) {
         _snowman.put((cla<?>)cla.a.get(_snowmanxx.toLowerCase(Locale.ROOT)), new LongOpenHashSet(Arrays.stream(_snowmanx.o(_snowmanxx)).filter(var2x -> {
            brd _snowmanxxx = new brd(var2x);
            if (_snowmanxxx.a(_snowman) > 8) {
               a.warn("Found invalid structure reference [ {} @ {} ] for chunk {}.", _snowman, _snowmanxxx, _snowman);
               return false;
            } else {
               return true;
            }
         }).toArray()));
      }

      return _snowman;
   }

   public static mj a(ShortList[] var0) {
      mj _snowman = new mj();

      for (ShortList _snowmanx : _snowman) {
         mj _snowmanxx = new mj();
         if (_snowmanx != null) {
            ShortListIterator var7 = _snowmanx.iterator();

            while (var7.hasNext()) {
               Short _snowmanxxx = (Short)var7.next();
               _snowmanxx.add(mr.a(_snowmanxxx));
            }
         }

         _snowman.add(_snowmanxx);
      }

      return _snowman;
   }
}
