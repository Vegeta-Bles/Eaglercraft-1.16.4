import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dpa extends dot {
   private static final Logger a = LogManager.getLogger();
   private static final vk b = new vk("textures/gui/title/minecraft.png");
   private static final vk c = new vk("textures/gui/title/edition.png");
   private static final vk p = new vk("textures/misc/vignette.png");
   private static final String q = "" + k.p + k.q + k.k + k.l;
   private final boolean r;
   private final Runnable s;
   private float t;
   private List<afa> u;
   private IntSet v;
   private int w;
   private float x = 0.5F;

   public dpa(boolean var1, Runnable var2) {
      super(dkz.a);
      this.r = _snowman;
      this.s = _snowman;
      if (!_snowman) {
         this.x = 0.75F;
      }
   }

   @Override
   public void d() {
      this.i.p().a();
      this.i.W().a(false);
      float _snowman = (float)(this.w + this.l + this.l + 24) / this.x;
      if (this.t > _snowman) {
         this.h();
      }
   }

   @Override
   public void at_() {
      this.h();
   }

   private void h() {
      this.s.run();
      this.i.a(null);
   }

   @Override
   protected void b() {
      if (this.u == null) {
         this.u = Lists.newArrayList();
         this.v = new IntOpenHashSet();
         acg _snowman = null;

         try {
            int _snowmanx = 274;
            if (this.r) {
               _snowman = this.i.N().a(new vk("texts/end.txt"));
               InputStream _snowmanxx = _snowman.b();
               BufferedReader _snowmanxxx = new BufferedReader(new InputStreamReader(_snowmanxx, StandardCharsets.UTF_8));
               Random _snowmanxxxx = new Random(8124371L);

               String _snowmanxxxxx;
               while ((_snowmanxxxxx = _snowmanxxx.readLine()) != null) {
                  _snowmanxxxxx = _snowmanxxxxx.replaceAll("PLAYERNAME", this.i.J().c());

                  int _snowmanxxxxxx;
                  while ((_snowmanxxxxxx = _snowmanxxxxx.indexOf(q)) != -1) {
                     String _snowmanxxxxxxx = _snowmanxxxxx.substring(0, _snowmanxxxxxx);
                     String _snowmanxxxxxxxx = _snowmanxxxxx.substring(_snowmanxxxxxx + q.length());
                     _snowmanxxxxx = _snowmanxxxxxxx + k.p + k.q + "XXXXXXXX".substring(0, _snowmanxxxx.nextInt(4) + 3) + _snowmanxxxxxxxx;
                  }

                  this.u.addAll(this.i.g.b(new oe(_snowmanxxxxx), 274));
                  this.u.add(afa.a);
               }

               _snowmanxx.close();

               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 8; _snowmanxxxxxx++) {
                  this.u.add(afa.a);
               }
            }

            InputStream _snowmanxx = this.i.N().a(new vk("texts/credits.txt")).b();
            BufferedReader _snowmanxxx = new BufferedReader(new InputStreamReader(_snowmanxx, StandardCharsets.UTF_8));

            String _snowmanxxxx;
            while ((_snowmanxxxx = _snowmanxxx.readLine()) != null) {
               _snowmanxxxx = _snowmanxxxx.replaceAll("PLAYERNAME", this.i.J().c());
               _snowmanxxxx = _snowmanxxxx.replaceAll("\t", "    ");
               boolean _snowmanxxxxx;
               if (_snowmanxxxx.startsWith("[C]")) {
                  _snowmanxxxx = _snowmanxxxx.substring(3);
                  _snowmanxxxxx = true;
               } else {
                  _snowmanxxxxx = false;
               }

               for (afa _snowmanxxxxxx : this.i.g.b(new oe(_snowmanxxxx), 274)) {
                  if (_snowmanxxxxx) {
                     this.v.add(this.u.size());
                  }

                  this.u.add(_snowmanxxxxxx);
               }

               this.u.add(afa.a);
            }

            _snowmanxx.close();
            this.w = this.u.size() * 12;
         } catch (Exception var13) {
            a.error("Couldn't load credits", var13);
         } finally {
            IOUtils.closeQuietly(_snowman);
         }
      }
   }

   private void a(int var1, int var2, float var3) {
      this.i.M().a(dkw.f);
      int _snowman = this.k;
      float _snowmanx = -this.t * 0.5F * this.x;
      float _snowmanxx = (float)this.l - this.t * 0.5F * this.x;
      float _snowmanxxx = 0.015625F;
      float _snowmanxxxx = this.t * 0.02F;
      float _snowmanxxxxx = (float)(this.w + this.l + this.l + 24) / this.x;
      float _snowmanxxxxxx = (_snowmanxxxxx - 20.0F - this.t) * 0.005F;
      if (_snowmanxxxxxx < _snowmanxxxx) {
         _snowmanxxxx = _snowmanxxxxxx;
      }

      if (_snowmanxxxx > 1.0F) {
         _snowmanxxxx = 1.0F;
      }

      _snowmanxxxx *= _snowmanxxxx;
      _snowmanxxxx = _snowmanxxxx * 96.0F / 255.0F;
      dfo _snowmanxxxxxxx = dfo.a();
      dfh _snowmanxxxxxxxx = _snowmanxxxxxxx.c();
      _snowmanxxxxxxxx.a(7, dfk.p);
      _snowmanxxxxxxxx.a(0.0, (double)this.l, (double)this.v()).a(0.0F, _snowmanx * 0.015625F).a(_snowmanxxxx, _snowmanxxxx, _snowmanxxxx, 1.0F).d();
      _snowmanxxxxxxxx.a((double)_snowman, (double)this.l, (double)this.v()).a((float)_snowman * 0.015625F, _snowmanx * 0.015625F).a(_snowmanxxxx, _snowmanxxxx, _snowmanxxxx, 1.0F).d();
      _snowmanxxxxxxxx.a((double)_snowman, 0.0, (double)this.v()).a((float)_snowman * 0.015625F, _snowmanxx * 0.015625F).a(_snowmanxxxx, _snowmanxxxx, _snowmanxxxx, 1.0F).d();
      _snowmanxxxxxxxx.a(0.0, 0.0, (double)this.v()).a(0.0F, _snowmanxx * 0.015625F).a(_snowmanxxxx, _snowmanxxxx, _snowmanxxxx, 1.0F).d();
      _snowmanxxxxxxx.b();
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman, _snowman, _snowman);
      int _snowman = 274;
      int _snowmanx = this.k / 2 - 137;
      int _snowmanxx = this.l + 50;
      this.t += _snowman;
      float _snowmanxxx = -this.t * this.x;
      RenderSystem.pushMatrix();
      RenderSystem.translatef(0.0F, _snowmanxxx, 0.0F);
      this.i.M().a(b);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableAlphaTest();
      RenderSystem.enableBlend();
      this.a(_snowmanx, _snowmanxx, (var2x, var3x) -> {
         this.b(_snowman, var2x + 0, var3x, 0, 0, 155, 44);
         this.b(_snowman, var2x + 155, var3x, 0, 45, 155, 44);
      });
      RenderSystem.disableBlend();
      this.i.M().a(c);
      a(_snowman, _snowmanx + 88, _snowmanxx + 37, 0.0F, 0.0F, 98, 14, 128, 16);
      RenderSystem.disableAlphaTest();
      int _snowmanxxxx = _snowmanxx + 100;

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < this.u.size(); _snowmanxxxxx++) {
         if (_snowmanxxxxx == this.u.size() - 1) {
            float _snowmanxxxxxx = (float)_snowmanxxxx + _snowmanxxx - (float)(this.l / 2 - 6);
            if (_snowmanxxxxxx < 0.0F) {
               RenderSystem.translatef(0.0F, -_snowmanxxxxxx, 0.0F);
            }
         }

         if ((float)_snowmanxxxx + _snowmanxxx + 12.0F + 8.0F > 0.0F && (float)_snowmanxxxx + _snowmanxxx < (float)this.l) {
            afa _snowmanxxxxxx = this.u.get(_snowmanxxxxx);
            if (this.v.contains(_snowmanxxxxx)) {
               this.o.a(_snowman, _snowmanxxxxxx, (float)(_snowmanx + (274 - this.o.a(_snowmanxxxxxx)) / 2), (float)_snowmanxxxx, 16777215);
            } else {
               this.o.b.setSeed((long)((float)((long)_snowmanxxxxx * 4238972211L) + this.t / 4.0F));
               this.o.a(_snowman, _snowmanxxxxxx, (float)_snowmanx, (float)_snowmanxxxx, 16777215);
            }
         }

         _snowmanxxxx += 12;
      }

      RenderSystem.popMatrix();
      this.i.M().a(p);
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(dem.r.o, dem.j.k);
      int _snowmanxxxxx = this.k;
      int _snowmanxxxxxx = this.l;
      dfo _snowmanxxxxxxx = dfo.a();
      dfh _snowmanxxxxxxxx = _snowmanxxxxxxx.c();
      _snowmanxxxxxxxx.a(7, dfk.p);
      _snowmanxxxxxxxx.a(0.0, (double)_snowmanxxxxxx, (double)this.v()).a(0.0F, 1.0F).a(1.0F, 1.0F, 1.0F, 1.0F).d();
      _snowmanxxxxxxxx.a((double)_snowmanxxxxx, (double)_snowmanxxxxxx, (double)this.v()).a(1.0F, 1.0F).a(1.0F, 1.0F, 1.0F, 1.0F).d();
      _snowmanxxxxxxxx.a((double)_snowmanxxxxx, 0.0, (double)this.v()).a(1.0F, 0.0F).a(1.0F, 1.0F, 1.0F, 1.0F).d();
      _snowmanxxxxxxxx.a(0.0, 0.0, (double)this.v()).a(0.0F, 0.0F).a(1.0F, 1.0F, 1.0F, 1.0F).d();
      _snowmanxxxxxxx.b();
      RenderSystem.disableBlend();
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
