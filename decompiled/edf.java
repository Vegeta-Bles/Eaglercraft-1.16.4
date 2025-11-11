import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;

public class edf implements edh.a {
   private final djz a;
   private double b = Double.MIN_VALUE;
   private final int c = 12;
   @Nullable
   private edf.a d;

   public edf(djz var1) {
      this.a = _snowman;
   }

   @Override
   public void a(dfm var1, eag var2, double var3, double var5, double var7) {
      double _snowman = (double)x.c();
      if (_snowman - this.b > 3.0E9) {
         this.b = _snowman;
         eng _snowmanx = this.a.H();
         if (_snowmanx != null) {
            this.d = new edf.a(_snowmanx, _snowman, _snowman);
         } else {
            this.d = null;
         }
      }

      if (this.d != null) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.lineWidth(2.0F);
         RenderSystem.disableTexture();
         RenderSystem.depthMask(false);
         Map<brd, String> _snowmanx = this.d.c.getNow(null);
         double _snowmanxx = this.a.h.k().b().c * 0.85;

         for (Entry<brd, String> _snowmanxxx : this.d.b.entrySet()) {
            brd _snowmanxxxx = _snowmanxxx.getKey();
            String _snowmanxxxxx = _snowmanxxx.getValue();
            if (_snowmanx != null) {
               _snowmanxxxxx = _snowmanxxxxx + _snowmanx.get(_snowmanxxxx);
            }

            String[] _snowmanxxxxxx = _snowmanxxxxx.split("\n");
            int _snowmanxxxxxxx = 0;

            for (String _snowmanxxxxxxxx : _snowmanxxxxxx) {
               edh.a(_snowmanxxxxxxxx, (double)((_snowmanxxxx.b << 4) + 8), _snowmanxx + (double)_snowmanxxxxxxx, (double)((_snowmanxxxx.c << 4) + 8), -1, 0.15F);
               _snowmanxxxxxxx -= 2;
            }
         }

         RenderSystem.depthMask(true);
         RenderSystem.enableTexture();
         RenderSystem.disableBlend();
      }
   }

   final class a {
      private final Map<brd, String> b;
      private final CompletableFuture<Map<brd, String>> c;

      private a(eng var2, double var3, double var5) {
         dwt _snowman = edf.this.a.r;
         vj<brx> _snowmanx = _snowman.Y();
         int _snowmanxx = (int)_snowman >> 4;
         int _snowmanxxx = (int)_snowman >> 4;
         Builder<brd, String> _snowmanxxxx = ImmutableMap.builder();
         dwr _snowmanxxxxx = _snowman.n();

         for (int _snowmanxxxxxx = _snowmanxx - 12; _snowmanxxxxxx <= _snowmanxx + 12; _snowmanxxxxxx++) {
            for (int _snowmanxxxxxxx = _snowmanxxx - 12; _snowmanxxxxxxx <= _snowmanxxx + 12; _snowmanxxxxxxx++) {
               brd _snowmanxxxxxxxx = new brd(_snowmanxxxxxx, _snowmanxxxxxxx);
               String _snowmanxxxxxxxxx = "";
               cgh _snowmanxxxxxxxxxx = _snowmanxxxxx.a(_snowmanxxxxxx, _snowmanxxxxxxx, false);
               _snowmanxxxxxxxxx = _snowmanxxxxxxxxx + "Client: ";
               if (_snowmanxxxxxxxxxx == null) {
                  _snowmanxxxxxxxxx = _snowmanxxxxxxxxx + "0n/a\n";
               } else {
                  _snowmanxxxxxxxxx = _snowmanxxxxxxxxx + (_snowmanxxxxxxxxxx.t() ? " E" : "");
                  _snowmanxxxxxxxxx = _snowmanxxxxxxxxx + "\n";
               }

               _snowmanxxxx.put(_snowmanxxxxxxxx, _snowmanxxxxxxxxx);
            }
         }

         this.b = _snowmanxxxx.build();
         this.c = _snowman.a(() -> {
            aag _snowmanxxxxxx = _snowman.a(_snowman);
            if (_snowmanxxxxxx == null) {
               return ImmutableMap.of();
            } else {
               Builder<brd, String> _snowmanx = ImmutableMap.builder();
               aae _snowmanxx = _snowmanxxxxxx.i();

               for (int _snowmanxxx = _snowman - 12; _snowmanxxx <= _snowman + 12; _snowmanxxx++) {
                  for (int _snowmanxxxx = _snowman - 12; _snowmanxxxx <= _snowman + 12; _snowmanxxxx++) {
                     brd _snowmanxxxxx = new brd(_snowmanxxx, _snowmanxxxx);
                     _snowmanx.put(_snowmanxxxxx, "Server: " + _snowmanxx.b(_snowmanxxxxx));
                  }
               }

               return _snowmanx.build();
            }
         });
      }
   }
}
