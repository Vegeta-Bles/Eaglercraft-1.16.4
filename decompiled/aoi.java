import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMaps;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenCustomHashMap;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aoi {
   private static final Logger a = LogManager.getLogger();
   private static final ThreadFactory b = new ThreadFactoryBuilder().setDaemon(true).build();
   private final ImmutableSet<vj<brx>> c;
   private final boolean d;
   private final cyg.a e;
   private final Thread f;
   private final DataFixer g;
   private volatile boolean h = true;
   private volatile boolean i;
   private volatile float j;
   private volatile int k;
   private volatile int l;
   private volatile int m;
   private final Object2FloatMap<vj<brx>> n = Object2FloatMaps.synchronize(new Object2FloatOpenCustomHashMap(x.k()));
   private volatile nr o = new of("optimizeWorld.stage.counting");
   private static final Pattern p = Pattern.compile("^r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca$");
   private final cyc q;

   public aoi(cyg.a var1, DataFixer var2, ImmutableSet<vj<brx>> var3, boolean var4) {
      this.c = _snowman;
      this.d = _snowman;
      this.g = _snowman;
      this.e = _snowman;
      this.q = new cyc(new File(this.e.a(brx.g), "data"), _snowman);
      this.f = b.newThread(this::i);
      this.f.setUncaughtExceptionHandler((var1x, var2x) -> {
         a.error("Error upgrading world", var2x);
         this.o = new of("optimizeWorld.stage.failed");
         this.i = true;
      });
      this.f.start();
   }

   public void a() {
      this.h = false;

      try {
         this.f.join();
      } catch (InterruptedException var2) {
      }
   }

   private void i() {
      this.k = 0;
      Builder<vj<brx>, ListIterator<brd>> _snowman = ImmutableMap.builder();
      UnmodifiableIterator var2 = this.c.iterator();

      while (var2.hasNext()) {
         vj<brx> _snowmanx = (vj<brx>)var2.next();
         List<brd> _snowmanxx = this.b(_snowmanx);
         _snowman.put(_snowmanx, _snowmanxx.listIterator());
         this.k = this.k + _snowmanxx.size();
      }

      if (this.k == 0) {
         this.i = true;
      } else {
         float _snowmanx = (float)this.k;
         ImmutableMap<vj<brx>, ListIterator<brd>> _snowmanxx = _snowman.build();
         Builder<vj<brx>, cgu> _snowmanxxx = ImmutableMap.builder();
         UnmodifiableIterator var5 = this.c.iterator();

         while (var5.hasNext()) {
            vj<brx> _snowmanxxxx = (vj<brx>)var5.next();
            File _snowmanxxxxx = this.e.a(_snowmanxxxx);
            _snowmanxxx.put(_snowmanxxxx, new cgu(new File(_snowmanxxxxx, "region"), this.g, true));
         }

         ImmutableMap<vj<brx>, cgu> _snowmanxxxx = _snowmanxxx.build();
         long _snowmanxxxxx = x.b();
         this.o = new of("optimizeWorld.stage.upgrading");

         while (this.h) {
            boolean _snowmanxxxxxx = false;
            float _snowmanxxxxxxx = 0.0F;
            UnmodifiableIterator var10 = this.c.iterator();

            while (var10.hasNext()) {
               vj<brx> _snowmanxxxxxxxx = (vj<brx>)var10.next();
               ListIterator<brd> _snowmanxxxxxxxxx = (ListIterator<brd>)_snowmanxx.get(_snowmanxxxxxxxx);
               cgu _snowmanxxxxxxxxxx = (cgu)_snowmanxxxx.get(_snowmanxxxxxxxx);
               if (_snowmanxxxxxxxxx.hasNext()) {
                  brd _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.next();
                  boolean _snowmanxxxxxxxxxxxx = false;

                  try {
                     md _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx.e(_snowmanxxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxx != null) {
                        int _snowmanxxxxxxxxxxxxxx = cgu.a(_snowmanxxxxxxxxxxxxx);
                        md _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx.a(_snowmanxxxxxxxx, () -> this.q, _snowmanxxxxxxxxxxxxx);
                        md _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.p("Level");
                        brd _snowmanxxxxxxxxxxxxxxxxx = new brd(_snowmanxxxxxxxxxxxxxxxx.h("xPos"), _snowmanxxxxxxxxxxxxxxxx.h("zPos"));
                        if (!_snowmanxxxxxxxxxxxxxxxxx.equals(_snowmanxxxxxxxxxxx)) {
                           a.warn("Chunk {} has invalid position {}", _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx);
                        }

                        boolean _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx < w.a().getWorldVersion();
                        if (this.d) {
                           _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx || _snowmanxxxxxxxxxxxxxxxx.e("Heightmaps");
                           _snowmanxxxxxxxxxxxxxxxx.r("Heightmaps");
                           _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxx || _snowmanxxxxxxxxxxxxxxxx.e("isLightOn");
                           _snowmanxxxxxxxxxxxxxxxx.r("isLightOn");
                        }

                        if (_snowmanxxxxxxxxxxxxxxxxxx) {
                           _snowmanxxxxxxxxxx.a(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
                           _snowmanxxxxxxxxxxxx = true;
                        }
                     }
                  } catch (u var23) {
                     Throwable _snowmanxxxxxxxxxxxxxxxxxxx = var23.getCause();
                     if (!(_snowmanxxxxxxxxxxxxxxxxxxx instanceof IOException)) {
                        throw var23;
                     }

                     a.error("Error upgrading chunk {}", _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx);
                  } catch (IOException var24) {
                     a.error("Error upgrading chunk {}", _snowmanxxxxxxxxxxx, var24);
                  }

                  if (_snowmanxxxxxxxxxxxx) {
                     this.l++;
                  } else {
                     this.m++;
                  }

                  _snowmanxxxxxx = true;
               }

               float _snowmanxxxxxxxxxxx = (float)_snowmanxxxxxxxxx.nextIndex() / _snowmanx;
               this.n.put(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxx);
               _snowmanxxxxxxx += _snowmanxxxxxxxxxxx;
            }

            this.j = _snowmanxxxxxxx;
            if (!_snowmanxxxxxx) {
               this.h = false;
            }
         }

         this.o = new of("optimizeWorld.stage.finished");
         UnmodifiableIterator var31 = _snowmanxxxx.values().iterator();

         while (var31.hasNext()) {
            cgu _snowmanxxxxxx = (cgu)var31.next();

            try {
               _snowmanxxxxxx.close();
            } catch (IOException var22) {
               a.error("Error upgrading chunk", var22);
            }
         }

         this.q.a();
         _snowmanxxxxx = x.b() - _snowmanxxxxx;
         a.info("World optimizaton finished after {} ms", _snowmanxxxxx);
         this.i = true;
      }
   }

   private List<brd> b(vj<brx> var1) {
      File _snowman = this.e.a(_snowman);
      File _snowmanx = new File(_snowman, "region");
      File[] _snowmanxx = _snowmanx.listFiles((var0, var1x) -> var1x.endsWith(".mca"));
      if (_snowmanxx == null) {
         return ImmutableList.of();
      } else {
         List<brd> _snowmanxxx = Lists.newArrayList();

         for (File _snowmanxxxx : _snowmanxx) {
            Matcher _snowmanxxxxx = p.matcher(_snowmanxxxx.getName());
            if (_snowmanxxxxx.matches()) {
               int _snowmanxxxxxx = Integer.parseInt(_snowmanxxxxx.group(1)) << 5;
               int _snowmanxxxxxxx = Integer.parseInt(_snowmanxxxxx.group(2)) << 5;

               try (cgy _snowmanxxxxxxxx = new cgy(_snowmanxxxx, _snowmanx, true)) {
                  for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < 32; _snowmanxxxxxxxxx++) {
                     for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 32; _snowmanxxxxxxxxxx++) {
                        brd _snowmanxxxxxxxxxxx = new brd(_snowmanxxxxxxxxx + _snowmanxxxxxx, _snowmanxxxxxxxxxx + _snowmanxxxxxxx);
                        if (_snowmanxxxxxxxx.b(_snowmanxxxxxxxxxxx)) {
                           _snowmanxxx.add(_snowmanxxxxxxxxxxx);
                        }
                     }
                  }
               } catch (Throwable var28) {
               }
            }
         }

         return _snowmanxxx;
      }
   }

   public boolean b() {
      return this.i;
   }

   public ImmutableSet<vj<brx>> c() {
      return this.c;
   }

   public float a(vj<brx> var1) {
      return this.n.getFloat(_snowman);
   }

   public float d() {
      return this.j;
   }

   public int e() {
      return this.k;
   }

   public int f() {
      return this.l;
   }

   public int g() {
      return this.m;
   }

   public nr h() {
      return this.o;
   }
}
