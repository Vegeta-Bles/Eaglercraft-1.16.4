import com.google.common.collect.Lists;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cyj {
   private static final Logger a = LogManager.getLogger();

   static boolean a(cyg.a var0, afn var1) {
      _snowman.a(0);
      List<File> _snowman = Lists.newArrayList();
      List<File> _snowmanx = Lists.newArrayList();
      List<File> _snowmanxx = Lists.newArrayList();
      File _snowmanxxx = _snowman.a(brx.g);
      File _snowmanxxxx = _snowman.a(brx.h);
      File _snowmanxxxxx = _snowman.a(brx.i);
      a.info("Scanning folders...");
      a(_snowmanxxx, _snowman);
      if (_snowmanxxxx.exists()) {
         a(_snowmanxxxx, _snowmanx);
      }

      if (_snowmanxxxxx.exists()) {
         a(_snowmanxxxxx, _snowmanxx);
      }

      int _snowmanxxxxxx = _snowman.size() + _snowmanx.size() + _snowmanxx.size();
      a.info("Total conversion count is {}", _snowmanxxxxxx);
      gn.b _snowmanxxxxxxx = gn.b();
      vh<mt> _snowmanxxxxxxxx = vh.a(mo.a, ach.a.a, _snowmanxxxxxxx);
      cyn _snowmanxxxxxxxxx = _snowman.a(_snowmanxxxxxxxx, brk.a);
      long _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx != null ? _snowmanxxxxxxxxx.A().a() : 0L;
      gm<bsv> _snowmanxxxxxxxxxxx = _snowmanxxxxxxx.b(gm.ay);
      bsy _snowmanxxxxxxxxxxxx;
      if (_snowmanxxxxxxxxx != null && _snowmanxxxxxxxxx.A().h()) {
         _snowmanxxxxxxxxxxxx = new btd(_snowmanxxxxxxxxxxx.d(btb.b));
      } else {
         _snowmanxxxxxxxxxxxx = new btj(_snowmanxxxxxxxxxx, false, false, _snowmanxxxxxxxxxxx);
      }

      a(_snowmanxxxxxxx, new File(_snowmanxxx, "region"), _snowman, _snowmanxxxxxxxxxxxx, 0, _snowmanxxxxxx, _snowman);
      a(_snowmanxxxxxxx, new File(_snowmanxxxx, "region"), _snowmanx, new btd(_snowmanxxxxxxxxxxx.d(btb.i)), _snowman.size(), _snowmanxxxxxx, _snowman);
      a(_snowmanxxxxxxx, new File(_snowmanxxxxx, "region"), _snowmanxx, new btd(_snowmanxxxxxxxxxxx.d(btb.j)), _snowman.size() + _snowmanx.size(), _snowmanxxxxxx, _snowman);
      a(_snowman);
      _snowman.a(_snowmanxxxxxxx, _snowmanxxxxxxxxx);
      return true;
   }

   private static void a(cyg.a var0) {
      File _snowman = _snowman.a(cye.e).toFile();
      if (!_snowman.exists()) {
         a.warn("Unable to create level.dat_mcr backup");
      } else {
         File _snowmanx = new File(_snowman.getParent(), "level.dat_mcr");
         if (!_snowman.renameTo(_snowmanx)) {
            a.warn("Unable to create level.dat_mcr backup");
         }
      }
   }

   private static void a(gn.b var0, File var1, Iterable<File> var2, bsy var3, int var4, int var5, afn var6) {
      for (File _snowman : _snowman) {
         a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman++;
         int _snowmanx = (int)Math.round(100.0 * (double)_snowman / (double)_snowman);
         _snowman.a(_snowmanx);
      }
   }

   private static void a(gn.b var0, File var1, File var2, bsy var3, int var4, int var5, afn var6) {
      String _snowman = _snowman.getName();

      try (
         cgy _snowmanx = new cgy(_snowman, _snowman, true);
         cgy _snowmanxx = new cgy(new File(_snowman, _snowman.substring(0, _snowman.length() - ".mcr".length()) + ".mca"), _snowman, true);
      ) {
         for (int _snowmanxxx = 0; _snowmanxxx < 32; _snowmanxxx++) {
            for (int _snowmanxxxx = 0; _snowmanxxxx < 32; _snowmanxxxx++) {
               brd _snowmanxxxxx = new brd(_snowmanxxx, _snowmanxxxx);
               if (_snowmanx.d(_snowmanxxxxx) && !_snowmanxx.d(_snowmanxxxxx)) {
                  md _snowmanxxxxxx;
                  try (DataInputStream _snowmanxxxxxxx = _snowmanx.a(_snowmanxxxxx)) {
                     if (_snowmanxxxxxxx == null) {
                        a.warn("Failed to fetch input stream for chunk {}", _snowmanxxxxx);
                        continue;
                     }

                     _snowmanxxxxxx = mn.a((DataInput)_snowmanxxxxxxx);
                  } catch (IOException var107) {
                     a.warn("Failed to read data for chunk {}", _snowmanxxxxx, var107);
                     continue;
                  }

                  md _snowmanxxxxxxx = _snowmanxxxxxx.p("Level");
                  cgw.a _snowmanxxxxxxxx = cgw.a(_snowmanxxxxxxx);
                  md _snowmanxxxxxxxxx = new md();
                  md _snowmanxxxxxxxxxx = new md();
                  _snowmanxxxxxxxxx.a("Level", _snowmanxxxxxxxxxx);
                  cgw.a(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx, _snowman);

                  try (DataOutputStream _snowmanxxxxxxxxxxx = _snowmanxx.c(_snowmanxxxxx)) {
                     mn.a(_snowmanxxxxxxxxx, (DataOutput)_snowmanxxxxxxxxxxx);
                  }
               }
            }

            int _snowmanxxxxx = (int)Math.round(100.0 * (double)(_snowman * 1024) / (double)(_snowman * 1024));
            int _snowmanxxxxxx = (int)Math.round(100.0 * (double)((_snowmanxxx + 1) * 32 + _snowman * 1024) / (double)(_snowman * 1024));
            if (_snowmanxxxxxx > _snowmanxxxxx) {
               _snowman.a(_snowmanxxxxxx);
            }
         }
      } catch (IOException var112) {
         a.error("Failed to upgrade region file {}", _snowman, var112);
      }
   }

   private static void a(File var0, Collection<File> var1) {
      File _snowman = new File(_snowman, "region");
      File[] _snowmanx = _snowman.listFiles((var0x, var1x) -> var1x.endsWith(".mcr"));
      if (_snowmanx != null) {
         Collections.addAll(_snowman, _snowmanx);
      }
   }
}
