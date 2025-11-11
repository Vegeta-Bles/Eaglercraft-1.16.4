import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum cwc implements cwn, cwt {
   a;

   private static final Logger b = LogManager.getLogger();
   private static final Int2IntMap c = x.a(new Int2IntOpenHashMap(), var0 -> {
      var0.put(1, 129);
      var0.put(2, 130);
      var0.put(3, 131);
      var0.put(4, 132);
      var0.put(5, 133);
      var0.put(6, 134);
      var0.put(12, 140);
      var0.put(21, 149);
      var0.put(23, 151);
      var0.put(27, 155);
      var0.put(28, 156);
      var0.put(29, 157);
      var0.put(30, 158);
      var0.put(32, 160);
      var0.put(33, 161);
      var0.put(34, 162);
      var0.put(35, 163);
      var0.put(36, 164);
      var0.put(37, 165);
      var0.put(38, 166);
      var0.put(39, 167);
   });

   private cwc() {
   }

   @Override
   public int a(cvk var1, cvf var2, cvf var3, int var4, int var5) {
      int _snowman = _snowman.a(this.a(_snowman + 1), this.b(_snowman + 1));
      int _snowmanx = _snowman.a(this.a(_snowman + 1), this.b(_snowman + 1));
      if (_snowman > 255) {
         b.debug("old! {}", _snowman);
      }

      int _snowmanxx = (_snowmanx - 2) % 29;
      if (!cvx.b(_snowman) && _snowmanx >= 2 && _snowmanxx == 1) {
         return c.getOrDefault(_snowman, _snowman);
      } else {
         if (_snowman.a(3) == 0 || _snowmanxx == 0) {
            int _snowmanxxx = _snowman;
            if (_snowman == 2) {
               _snowmanxxx = 17;
            } else if (_snowman == 4) {
               _snowmanxxx = 18;
            } else if (_snowman == 27) {
               _snowmanxxx = 28;
            } else if (_snowman == 29) {
               _snowmanxxx = 1;
            } else if (_snowman == 5) {
               _snowmanxxx = 19;
            } else if (_snowman == 32) {
               _snowmanxxx = 33;
            } else if (_snowman == 30) {
               _snowmanxxx = 31;
            } else if (_snowman == 1) {
               _snowmanxxx = _snowman.a(3) == 0 ? 18 : 4;
            } else if (_snowman == 12) {
               _snowmanxxx = 13;
            } else if (_snowman == 21) {
               _snowmanxxx = 22;
            } else if (_snowman == 168) {
               _snowmanxxx = 169;
            } else if (_snowman == 0) {
               _snowmanxxx = 24;
            } else if (_snowman == 45) {
               _snowmanxxx = 48;
            } else if (_snowman == 46) {
               _snowmanxxx = 49;
            } else if (_snowman == 10) {
               _snowmanxxx = 50;
            } else if (_snowman == 3) {
               _snowmanxxx = 34;
            } else if (_snowman == 35) {
               _snowmanxxx = 36;
            } else if (cvx.a(_snowman, 38)) {
               _snowmanxxx = 37;
            } else if ((_snowman == 24 || _snowman == 48 || _snowman == 49 || _snowman == 50) && _snowman.a(3) == 0) {
               _snowmanxxx = _snowman.a(2) == 0 ? 1 : 4;
            }

            if (_snowmanxx == 0 && _snowmanxxx != _snowman) {
               _snowmanxxx = c.getOrDefault(_snowmanxxx, _snowman);
            }

            if (_snowmanxxx != _snowman) {
               int _snowmanxxxx = 0;
               if (cvx.a(_snowman.a(this.a(_snowman + 1), this.b(_snowman + 0)), _snowman)) {
                  _snowmanxxxx++;
               }

               if (cvx.a(_snowman.a(this.a(_snowman + 2), this.b(_snowman + 1)), _snowman)) {
                  _snowmanxxxx++;
               }

               if (cvx.a(_snowman.a(this.a(_snowman + 0), this.b(_snowman + 1)), _snowman)) {
                  _snowmanxxxx++;
               }

               if (cvx.a(_snowman.a(this.a(_snowman + 1), this.b(_snowman + 2)), _snowman)) {
                  _snowmanxxxx++;
               }

               if (_snowmanxxxx >= 3) {
                  return _snowmanxxx;
               }
            }
         }

         return _snowman;
      }
   }
}
