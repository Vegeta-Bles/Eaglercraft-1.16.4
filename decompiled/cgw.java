public class cgw {
   public static cgw.a a(md var0) {
      int _snowman = _snowman.h("xPos");
      int _snowmanx = _snowman.h("zPos");
      cgw.a _snowmanxx = new cgw.a(_snowman, _snowmanx);
      _snowmanxx.g = _snowman.m("Blocks");
      _snowmanxx.f = new cgl(_snowman.m("Data"), 7);
      _snowmanxx.e = new cgl(_snowman.m("SkyLight"), 7);
      _snowmanxx.d = new cgl(_snowman.m("BlockLight"), 7);
      _snowmanxx.c = _snowman.m("HeightMap");
      _snowmanxx.b = _snowman.q("TerrainPopulated");
      _snowmanxx.h = _snowman.d("Entities", 10);
      _snowmanxx.i = _snowman.d("TileEntities", 10);
      _snowmanxx.j = _snowman.d("TileTicks", 10);

      try {
         _snowmanxx.a = _snowman.i("LastUpdate");
      } catch (ClassCastException var5) {
         _snowmanxx.a = (long)_snowman.h("LastUpdate");
      }

      return _snowmanxx;
   }

   public static void a(gn.b var0, cgw.a var1, md var2, bsy var3) {
      _snowman.b("xPos", _snowman.k);
      _snowman.b("zPos", _snowman.l);
      _snowman.a("LastUpdate", _snowman.a);
      int[] _snowman = new int[_snowman.c.length];

      for (int _snowmanx = 0; _snowmanx < _snowman.c.length; _snowmanx++) {
         _snowman[_snowmanx] = _snowman.c[_snowmanx];
      }

      _snowman.a("HeightMap", _snowman);
      _snowman.a("TerrainPopulated", _snowman.b);
      mj _snowmanx = new mj();

      for (int _snowmanxx = 0; _snowmanxx < 8; _snowmanxx++) {
         boolean _snowmanxxx = true;

         for (int _snowmanxxxx = 0; _snowmanxxxx < 16 && _snowmanxxx; _snowmanxxxx++) {
            for (int _snowmanxxxxx = 0; _snowmanxxxxx < 16 && _snowmanxxx; _snowmanxxxxx++) {
               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 16; _snowmanxxxxxx++) {
                  int _snowmanxxxxxxx = _snowmanxxxx << 11 | _snowmanxxxxxx << 7 | _snowmanxxxxx + (_snowmanxx << 4);
                  int _snowmanxxxxxxxx = _snowman.g[_snowmanxxxxxxx];
                  if (_snowmanxxxxxxxx != 0) {
                     _snowmanxxx = false;
                     break;
                  }
               }
            }
         }

         if (!_snowmanxxx) {
            byte[] _snowmanxxxx = new byte[4096];
            cgb _snowmanxxxxx = new cgb();
            cgb _snowmanxxxxxxx = new cgb();
            cgb _snowmanxxxxxxxx = new cgb();

            for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < 16; _snowmanxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 16; _snowmanxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx < 16; _snowmanxxxxxxxxxxx++) {
                     int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx << 11 | _snowmanxxxxxxxxxxx << 7 | _snowmanxxxxxxxxxx + (_snowmanxx << 4);
                     int _snowmanxxxxxxxxxxxxx = _snowman.g[_snowmanxxxxxxxxxxxx];
                     _snowmanxxxx[_snowmanxxxxxxxxxx << 8 | _snowmanxxxxxxxxxxx << 4 | _snowmanxxxxxxxxx] = (byte)(_snowmanxxxxxxxxxxxxx & 0xFF);
                     _snowmanxxxxx.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowman.f.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx + (_snowmanxx << 4), _snowmanxxxxxxxxxxx));
                     _snowmanxxxxxxx.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowman.e.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx + (_snowmanxx << 4), _snowmanxxxxxxxxxxx));
                     _snowmanxxxxxxxx.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowman.d.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx + (_snowmanxx << 4), _snowmanxxxxxxxxxxx));
                  }
               }
            }

            md _snowmanxxxxxxxxx = new md();
            _snowmanxxxxxxxxx.a("Y", (byte)(_snowmanxx & 0xFF));
            _snowmanxxxxxxxxx.a("Blocks", _snowmanxxxx);
            _snowmanxxxxxxxxx.a("Data", _snowmanxxxxx.a());
            _snowmanxxxxxxxxx.a("SkyLight", _snowmanxxxxxxx.a());
            _snowmanxxxxxxxxx.a("BlockLight", _snowmanxxxxxxxx.a());
            _snowmanx.add(_snowmanxxxxxxxxx);
         }
      }

      _snowman.a("Sections", _snowmanx);
      _snowman.a("Biomes", new cfx(_snowman.b(gm.ay), new brd(_snowman.k, _snowman.l), _snowman).a());
      _snowman.a("Entities", _snowman.h);
      _snowman.a("TileEntities", _snowman.i);
      if (_snowman.j != null) {
         _snowman.a("TileTicks", _snowman.j);
      }

      _snowman.a("convertedFromAlphaFormat", true);
   }

   public static class a {
      public long a;
      public boolean b;
      public byte[] c;
      public cgl d;
      public cgl e;
      public cgl f;
      public byte[] g;
      public mj h;
      public mj i;
      public mj j;
      public final int k;
      public final int l;

      public a(int var1, int var2) {
         this.k = _snowman;
         this.l = _snowman;
      }
   }
}
