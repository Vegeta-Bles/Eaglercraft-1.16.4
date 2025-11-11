import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

public class crg {
   private static final Map<String, String> a = x.a(Maps.newHashMap(), var0 -> {
      var0.put("Village", "Village");
      var0.put("Mineshaft", "Mineshaft");
      var0.put("Mansion", "Mansion");
      var0.put("Igloo", "Temple");
      var0.put("Desert_Pyramid", "Temple");
      var0.put("Jungle_Pyramid", "Temple");
      var0.put("Swamp_Hut", "Temple");
      var0.put("Stronghold", "Stronghold");
      var0.put("Monument", "Monument");
      var0.put("Fortress", "Fortress");
      var0.put("EndCity", "EndCity");
   });
   private static final Map<String, String> b = x.a(Maps.newHashMap(), var0 -> {
      var0.put("Iglu", "Igloo");
      var0.put("TeDP", "Desert_Pyramid");
      var0.put("TeJP", "Jungle_Pyramid");
      var0.put("TeSH", "Swamp_Hut");
   });
   private final boolean c;
   private final Map<String, Long2ObjectMap<md>> d = Maps.newHashMap();
   private final Map<String, crt> e = Maps.newHashMap();
   private final List<String> f;
   private final List<String> g;

   public crg(@Nullable cyc var1, List<String> var2, List<String> var3) {
      this.f = _snowman;
      this.g = _snowman;
      this.a(_snowman);
      boolean _snowman = false;

      for (String _snowmanx : this.g) {
         _snowman |= this.d.get(_snowmanx) != null;
      }

      this.c = _snowman;
   }

   public void a(long var1) {
      for (String _snowman : this.f) {
         crt _snowmanx = this.e.get(_snowman);
         if (_snowmanx != null && _snowmanx.c(_snowman)) {
            _snowmanx.d(_snowman);
            _snowmanx.b();
         }
      }
   }

   public md a(md var1) {
      md _snowman = _snowman.p("Level");
      brd _snowmanx = new brd(_snowman.h("xPos"), _snowman.h("zPos"));
      if (this.a(_snowmanx.b, _snowmanx.c)) {
         _snowman = this.a(_snowman, _snowmanx);
      }

      md _snowmanxx = _snowman.p("Structures");
      md _snowmanxxx = _snowmanxx.p("References");

      for (String _snowmanxxxx : this.g) {
         cla<?> _snowmanxxxxx = (cla<?>)cla.a.get(_snowmanxxxx.toLowerCase(Locale.ROOT));
         if (!_snowmanxxx.c(_snowmanxxxx, 12) && _snowmanxxxxx != null) {
            int _snowmanxxxxxx = 8;
            LongList _snowmanxxxxxxx = new LongArrayList();

            for (int _snowmanxxxxxxxx = _snowmanx.b - 8; _snowmanxxxxxxxx <= _snowmanx.b + 8; _snowmanxxxxxxxx++) {
               for (int _snowmanxxxxxxxxx = _snowmanx.c - 8; _snowmanxxxxxxxxx <= _snowmanx.c + 8; _snowmanxxxxxxxxx++) {
                  if (this.a(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxx)) {
                     _snowmanxxxxxxx.add(brd.a(_snowmanxxxxxxxx, _snowmanxxxxxxxxx));
                  }
               }
            }

            _snowmanxxx.c(_snowmanxxxx, _snowmanxxxxxxx);
         }
      }

      _snowmanxx.a("References", _snowmanxxx);
      _snowman.a("Structures", _snowmanxx);
      _snowman.a("Level", _snowman);
      return _snowman;
   }

   private boolean a(int var1, int var2, String var3) {
      return !this.c ? false : this.d.get(_snowman) != null && this.e.get(a.get(_snowman)).b(brd.a(_snowman, _snowman));
   }

   private boolean a(int var1, int var2) {
      if (!this.c) {
         return false;
      } else {
         for (String _snowman : this.g) {
            if (this.d.get(_snowman) != null && this.e.get(a.get(_snowman)).c(brd.a(_snowman, _snowman))) {
               return true;
            }
         }

         return false;
      }
   }

   private md a(md var1, brd var2) {
      md _snowman = _snowman.p("Level");
      md _snowmanx = _snowman.p("Structures");
      md _snowmanxx = _snowmanx.p("Starts");

      for (String _snowmanxxx : this.g) {
         Long2ObjectMap<md> _snowmanxxxx = this.d.get(_snowmanxxx);
         if (_snowmanxxxx != null) {
            long _snowmanxxxxx = _snowman.a();
            if (this.e.get(a.get(_snowmanxxx)).c(_snowmanxxxxx)) {
               md _snowmanxxxxxx = (md)_snowmanxxxx.get(_snowmanxxxxx);
               if (_snowmanxxxxxx != null) {
                  _snowmanxx.a(_snowmanxxx, _snowmanxxxxxx);
               }
            }
         }
      }

      _snowmanx.a("Starts", _snowmanxx);
      _snowman.a("Structures", _snowmanx);
      _snowman.a("Level", _snowman);
      return _snowman;
   }

   private void a(@Nullable cyc var1) {
      if (_snowman != null) {
         for (String _snowman : this.f) {
            md _snowmanx = new md();

            try {
               _snowmanx = _snowman.a(_snowman, 1493).p("data").p("Features");
               if (_snowmanx.isEmpty()) {
                  continue;
               }
            } catch (IOException var13) {
            }

            for (String _snowmanxx : _snowmanx.d()) {
               md _snowmanxxx = _snowmanx.p(_snowmanxx);
               long _snowmanxxxx = brd.a(_snowmanxxx.h("ChunkX"), _snowmanxxx.h("ChunkZ"));
               mj _snowmanxxxxx = _snowmanxxx.d("Children", 10);
               if (!_snowmanxxxxx.isEmpty()) {
                  String _snowmanxxxxxx = _snowmanxxxxx.a(0).l("id");
                  String _snowmanxxxxxxx = b.get(_snowmanxxxxxx);
                  if (_snowmanxxxxxxx != null) {
                     _snowmanxxx.a("id", _snowmanxxxxxxx);
                  }
               }

               String _snowmanxxxxxx = _snowmanxxx.l("id");
               this.d.computeIfAbsent(_snowmanxxxxxx, var0 -> new Long2ObjectOpenHashMap()).put(_snowmanxxxx, _snowmanxxx);
            }

            String _snowmanxx = _snowman + "_index";
            crt _snowmanxxx = _snowman.a(() -> new crt(_snowman), _snowmanxx);
            if (!_snowmanxxx.a().isEmpty()) {
               this.e.put(_snowman, _snowmanxxx);
            } else {
               crt _snowmanxxxx = new crt(_snowmanxx);
               this.e.put(_snowman, _snowmanxxxx);

               for (String _snowmanxxxxx : _snowmanx.d()) {
                  md _snowmanxxxxxx = _snowmanx.p(_snowmanxxxxx);
                  _snowmanxxxx.a(brd.a(_snowmanxxxxxx.h("ChunkX"), _snowmanxxxxxx.h("ChunkZ")));
               }

               _snowmanxxxx.b();
            }
         }
      }
   }

   public static crg a(vj<brx> var0, @Nullable cyc var1) {
      if (_snowman == brx.g) {
         return new crg(
            _snowman,
            ImmutableList.of("Monument", "Stronghold", "Village", "Mineshaft", "Temple", "Mansion"),
            ImmutableList.of("Village", "Mineshaft", "Mansion", "Igloo", "Desert_Pyramid", "Jungle_Pyramid", "Swamp_Hut", "Stronghold", "Monument")
         );
      } else if (_snowman == brx.h) {
         List<String> _snowman = ImmutableList.of("Fortress");
         return new crg(_snowman, _snowman, _snowman);
      } else if (_snowman == brx.i) {
         List<String> _snowman = ImmutableList.of("EndCity");
         return new crg(_snowman, _snowman, _snowman);
      } else {
         throw new RuntimeException(String.format("Unknown dimension type : %s", _snowman));
      }
   }
}
