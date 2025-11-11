import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class eae implements aci, AutoCloseable {
   private static final Logger b = LogManager.getLogger();
   private static final vk c = new vk("textures/environment/moon_phases.png");
   private static final vk d = new vk("textures/environment/sun.png");
   private static final vk e = new vk("textures/environment/clouds.png");
   private static final vk f = new vk("textures/environment/end_sky.png");
   private static final vk g = new vk("textures/misc/forcefield.png");
   private static final vk h = new vk("textures/environment/rain.png");
   private static final vk i = new vk("textures/environment/snow.png");
   public static final gc[] a = gc.values();
   private final djz j;
   private final ekd k;
   private final eet l;
   private final eam m;
   private dwt n;
   private Set<ecu.c> o = Sets.newLinkedHashSet();
   private final ObjectList<eae.a> p = new ObjectArrayList(69696);
   private final Set<ccj> q = Sets.newHashSet();
   private eat r;
   private final dfr s = dfk.k;
   @Nullable
   private dfp t;
   @Nullable
   private dfp u;
   @Nullable
   private dfp v;
   private boolean w = true;
   @Nullable
   private dfp x;
   private final eap y = new eap(100);
   private int z;
   private final Int2ObjectMap<zq> A = new Int2ObjectOpenHashMap();
   private final Long2ObjectMap<SortedSet<zq>> B = new Long2ObjectOpenHashMap();
   private final Map<fx, emt> C = Maps.newHashMap();
   @Nullable
   private deg D;
   @Nullable
   private eaj E;
   @Nullable
   private deg F;
   @Nullable
   private deg G;
   @Nullable
   private deg H;
   @Nullable
   private deg I;
   @Nullable
   private deg J;
   @Nullable
   private eaj K;
   private double L = Double.MIN_VALUE;
   private double M = Double.MIN_VALUE;
   private double N = Double.MIN_VALUE;
   private int O = Integer.MIN_VALUE;
   private int P = Integer.MIN_VALUE;
   private int Q = Integer.MIN_VALUE;
   private double R = Double.MIN_VALUE;
   private double S = Double.MIN_VALUE;
   private double T = Double.MIN_VALUE;
   private double U = Double.MIN_VALUE;
   private double V = Double.MIN_VALUE;
   private int W = Integer.MIN_VALUE;
   private int X = Integer.MIN_VALUE;
   private int Y = Integer.MIN_VALUE;
   private dcn Z = dcn.a;
   private djn aa;
   private ecu ab;
   private final dfr ac = dfk.h;
   private int ad = -1;
   private int ae;
   private int af;
   private boolean ag;
   @Nullable
   private ecz ah;
   private final h[] ai = new h[8];
   private final dfu aj = new dfu(0.0, 0.0, 0.0);
   private double ak;
   private double al;
   private double am;
   private boolean an = true;
   private int ao;
   private int ap;
   private final float[] aq = new float[1024];
   private final float[] ar = new float[1024];

   public eae(djz var1, eam var2) {
      this.j = _snowman;
      this.l = _snowman.ac();
      this.m = _snowman;
      this.k = _snowman.M();

      for (int _snowman = 0; _snowman < 32; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 32; _snowmanx++) {
            float _snowmanxx = (float)(_snowmanx - 16);
            float _snowmanxxx = (float)(_snowman - 16);
            float _snowmanxxxx = afm.c(_snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx);
            this.aq[_snowman << 5 | _snowmanx] = -_snowmanxxx / _snowmanxxxx;
            this.ar[_snowman << 5 | _snowmanx] = _snowmanxx / _snowmanxxxx;
         }
      }

      this.z();
      this.y();
      this.x();
   }

   private void a(eaf var1, float var2, double var3, double var5, double var7) {
      float _snowman = this.j.r.d(_snowman);
      if (!(_snowman <= 0.0F)) {
         _snowman.c();
         brx _snowmanx = this.j.r;
         int _snowmanxx = afm.c(_snowman);
         int _snowmanxxx = afm.c(_snowman);
         int _snowmanxxxx = afm.c(_snowman);
         dfo _snowmanxxxxx = dfo.a();
         dfh _snowmanxxxxxx = _snowmanxxxxx.c();
         RenderSystem.enableAlphaTest();
         RenderSystem.disableCull();
         RenderSystem.normal3f(0.0F, 1.0F, 0.0F);
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.defaultAlphaFunc();
         RenderSystem.enableDepthTest();
         int _snowmanxxxxxxx = 5;
         if (djz.z()) {
            _snowmanxxxxxxx = 10;
         }

         RenderSystem.depthMask(djz.A());
         int _snowmanxxxxxxxx = -1;
         float _snowmanxxxxxxxxx = (float)this.z + _snowman;
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         fx.a _snowmanxxxxxxxxxx = new fx.a();

         for (int _snowmanxxxxxxxxxxx = _snowmanxxxx - _snowmanxxxxxxx; _snowmanxxxxxxxxxxx <= _snowmanxxxx + _snowmanxxxxxxx; _snowmanxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxx = _snowmanxx - _snowmanxxxxxxx; _snowmanxxxxxxxxxxxx <= _snowmanxx + _snowmanxxxxxxx; _snowmanxxxxxxxxxxxx++) {
               int _snowmanxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxx - _snowmanxxxx + 16) * 32 + _snowmanxxxxxxxxxxxx - _snowmanxx + 16;
               double _snowmanxxxxxxxxxxxxxx = (double)this.aq[_snowmanxxxxxxxxxxxxx] * 0.5;
               double _snowmanxxxxxxxxxxxxxxx = (double)this.ar[_snowmanxxxxxxxxxxxxx] * 0.5;
               _snowmanxxxxxxxxxx.d(_snowmanxxxxxxxxxxxx, 0, _snowmanxxxxxxxxxxx);
               bsv _snowmanxxxxxxxxxxxxxxxx = _snowmanx.v(_snowmanxxxxxxxxxx);
               if (_snowmanxxxxxxxxxxxxxxxx.c() != bsv.e.a) {
                  int _snowmanxxxxxxxxxxxxxxxxx = _snowmanx.a(chn.a.e, _snowmanxxxxxxxxxx).v();
                  int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxx - _snowmanxxxxxxx;
                  int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxx + _snowmanxxxxxxx;
                  if (_snowmanxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx;
                  }

                  if (_snowmanxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx;
                  }

                  int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxxxxxxxx < _snowmanxxx) {
                     _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxx;
                  }

                  if (_snowmanxxxxxxxxxxxxxxxxxx != _snowmanxxxxxxxxxxxxxxxxxxx) {
                     Random _snowmanxxxxxxxxxxxxxxxxxxxxx = new Random(
                        (long)(_snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx * 3121 + _snowmanxxxxxxxxxxxx * 45238971 ^ _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx * 418711 + _snowmanxxxxxxxxxxx * 13761)
                     );
                     _snowmanxxxxxxxxxx.d(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx);
                     float _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxxxxxxxxxxx >= 0.15F) {
                        if (_snowmanxxxxxxxx != 0) {
                           if (_snowmanxxxxxxxx >= 0) {
                              _snowmanxxxxx.b();
                           }

                           _snowmanxxxxxxxx = 0;
                           this.j.M().a(h);
                           _snowmanxxxxxx.a(7, dfk.j);
                        }

                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = this.z
                              + _snowmanxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx * 3121
                              + _snowmanxxxxxxxxxxxx * 45238971
                              + _snowmanxxxxxxxxxxx * _snowmanxxxxxxxxxxx * 418711
                              + _snowmanxxxxxxxxxxx * 13761
                           & 31;
                        float _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = -((float)_snowmanxxxxxxxxxxxxxxxxxxxxxxx + _snowman) / 32.0F * (3.0F + _snowmanxxxxxxxxxxxxxxxxxxxxx.nextFloat());
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = (double)((float)_snowmanxxxxxxxxxxxx + 0.5F) - _snowman;
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)((float)_snowmanxxxxxxxxxxx + 0.5F) - _snowman;
                        float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.a(
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx
                           )
                           / (float)_snowmanxxxxxxx;
                        float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ((1.0F - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.5F + 0.5F) * _snowman;
                        _snowmanxxxxxxxxxx.d(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx);
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = a(_snowmanx, _snowmanxxxxxxxxxx);
                        _snowmanxxxxxx.a(
                              (double)_snowmanxxxxxxxxxxxx - _snowman - _snowmanxxxxxxxxxxxxxx + 0.5,
                              (double)_snowmanxxxxxxxxxxxxxxxxxxx - _snowman,
                              (double)_snowmanxxxxxxxxxxx - _snowman - _snowmanxxxxxxxxxxxxxxx + 0.5
                           )
                           .a(0.0F, (float)_snowmanxxxxxxxxxxxxxxxxxx * 0.25F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx)
                           .a(1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .d();
                        _snowmanxxxxxx.a(
                              (double)_snowmanxxxxxxxxxxxx - _snowman + _snowmanxxxxxxxxxxxxxx + 0.5,
                              (double)_snowmanxxxxxxxxxxxxxxxxxxx - _snowman,
                              (double)_snowmanxxxxxxxxxxx - _snowman + _snowmanxxxxxxxxxxxxxxx + 0.5
                           )
                           .a(1.0F, (float)_snowmanxxxxxxxxxxxxxxxxxx * 0.25F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx)
                           .a(1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .d();
                        _snowmanxxxxxx.a(
                              (double)_snowmanxxxxxxxxxxxx - _snowman + _snowmanxxxxxxxxxxxxxx + 0.5,
                              (double)_snowmanxxxxxxxxxxxxxxxxxx - _snowman,
                              (double)_snowmanxxxxxxxxxxx - _snowman + _snowmanxxxxxxxxxxxxxxx + 0.5
                           )
                           .a(1.0F, (float)_snowmanxxxxxxxxxxxxxxxxxxx * 0.25F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx)
                           .a(1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .d();
                        _snowmanxxxxxx.a(
                              (double)_snowmanxxxxxxxxxxxx - _snowman - _snowmanxxxxxxxxxxxxxx + 0.5,
                              (double)_snowmanxxxxxxxxxxxxxxxxxx - _snowman,
                              (double)_snowmanxxxxxxxxxxx - _snowman - _snowmanxxxxxxxxxxxxxxx + 0.5
                           )
                           .a(0.0F, (float)_snowmanxxxxxxxxxxxxxxxxxxx * 0.25F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx)
                           .a(1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .d();
                     } else {
                        if (_snowmanxxxxxxxx != 1) {
                           if (_snowmanxxxxxxxx >= 0) {
                              _snowmanxxxxx.b();
                           }

                           _snowmanxxxxxxxx = 1;
                           this.j.M().a(i);
                           _snowmanxxxxxx.a(7, dfk.j);
                        }

                        float _snowmanxxxxxxxxxxxxxxxxxxxxxxx = -((float)(this.z & 511) + _snowman) / 512.0F;
                        float _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = (float)(
                           _snowmanxxxxxxxxxxxxxxxxxxxxx.nextDouble() + (double)_snowmanxxxxxxxxx * 0.01 * (double)((float)_snowmanxxxxxxxxxxxxxxxxxxxxx.nextGaussian())
                        );
                        float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = (float)(
                           _snowmanxxxxxxxxxxxxxxxxxxxxx.nextDouble() + (double)(_snowmanxxxxxxxxx * (float)_snowmanxxxxxxxxxxxxxxxxxxxxx.nextGaussian()) * 0.001
                        );
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)((float)_snowmanxxxxxxxxxxxx + 0.5F) - _snowman;
                        double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = (double)((float)_snowmanxxxxxxxxxxx + 0.5F) - _snowman;
                        float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = afm.a(
                              _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx
                           )
                           / (float)_snowmanxxxxxxx;
                        float _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = ((1.0F - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx) * 0.3F + 0.5F) * _snowman;
                        _snowmanxxxxxxxxxx.d(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx);
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = a(_snowmanx, _snowmanxxxxxxxxxx);
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx >> 16 & 65535;
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx & 65535) * 3;
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 3 + 240) / 4;
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx * 3 + 240) / 4;
                        _snowmanxxxxxx.a(
                              (double)_snowmanxxxxxxxxxxxx - _snowman - _snowmanxxxxxxxxxxxxxx + 0.5,
                              (double)_snowmanxxxxxxxxxxxxxxxxxxx - _snowman,
                              (double)_snowmanxxxxxxxxxxx - _snowman - _snowmanxxxxxxxxxxxxxxx + 0.5
                           )
                           .a(0.0F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxxxxxxxx * 0.25F + _snowmanxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .a(1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .d();
                        _snowmanxxxxxx.a(
                              (double)_snowmanxxxxxxxxxxxx - _snowman + _snowmanxxxxxxxxxxxxxx + 0.5,
                              (double)_snowmanxxxxxxxxxxxxxxxxxxx - _snowman,
                              (double)_snowmanxxxxxxxxxxx - _snowman + _snowmanxxxxxxxxxxxxxxx + 0.5
                           )
                           .a(1.0F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxxxxxxxx * 0.25F + _snowmanxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .a(1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .d();
                        _snowmanxxxxxx.a(
                              (double)_snowmanxxxxxxxxxxxx - _snowman + _snowmanxxxxxxxxxxxxxx + 0.5,
                              (double)_snowmanxxxxxxxxxxxxxxxxxx - _snowman,
                              (double)_snowmanxxxxxxxxxxx - _snowman + _snowmanxxxxxxxxxxxxxxx + 0.5
                           )
                           .a(1.0F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxxxxxxxxx * 0.25F + _snowmanxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .a(1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .d();
                        _snowmanxxxxxx.a(
                              (double)_snowmanxxxxxxxxxxxx - _snowman - _snowmanxxxxxxxxxxxxxx + 0.5,
                              (double)_snowmanxxxxxxxxxxxxxxxxxx - _snowman,
                              (double)_snowmanxxxxxxxxxxx - _snowman - _snowmanxxxxxxxxxxxxxxx + 0.5
                           )
                           .a(0.0F + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, (float)_snowmanxxxxxxxxxxxxxxxxxxx * 0.25F + _snowmanxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .a(1.0F, 1.0F, 1.0F, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
                           .d();
                     }
                  }
               }
            }
         }

         if (_snowmanxxxxxxxx >= 0) {
            _snowmanxxxxx.b();
         }

         RenderSystem.enableCull();
         RenderSystem.disableBlend();
         RenderSystem.defaultAlphaFunc();
         RenderSystem.disableAlphaTest();
         _snowman.b();
      }
   }

   public void a(djk var1) {
      float _snowman = this.j.r.d(1.0F) / (djz.z() ? 1.0F : 2.0F);
      if (!(_snowman <= 0.0F)) {
         Random _snowmanx = new Random((long)this.z * 312987231L);
         brz _snowmanxx = this.j.r;
         fx _snowmanxxx = new fx(_snowman.b());
         fx _snowmanxxxx = null;
         int _snowmanxxxxx = (int)(100.0F * _snowman * _snowman) / (this.j.k.aT == dke.b ? 2 : 1);

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx; _snowmanxxxxxx++) {
            int _snowmanxxxxxxx = _snowmanx.nextInt(21) - 10;
            int _snowmanxxxxxxxx = _snowmanx.nextInt(21) - 10;
            fx _snowmanxxxxxxxxx = _snowmanxx.a(chn.a.e, _snowmanxxx.b(_snowmanxxxxxxx, 0, _snowmanxxxxxxxx)).c();
            bsv _snowmanxxxxxxxxxx = _snowmanxx.v(_snowmanxxxxxxxxx);
            if (_snowmanxxxxxxxxx.v() > 0
               && _snowmanxxxxxxxxx.v() <= _snowmanxxx.v() + 10
               && _snowmanxxxxxxxxx.v() >= _snowmanxxx.v() - 10
               && _snowmanxxxxxxxxxx.c() == bsv.e.b
               && _snowmanxxxxxxxxxx.a(_snowmanxxxxxxxxx) >= 0.15F) {
               _snowmanxxxx = _snowmanxxxxxxxxx;
               if (this.j.k.aT == dke.c) {
                  break;
               }

               double _snowmanxxxxxxxxxxx = _snowmanx.nextDouble();
               double _snowmanxxxxxxxxxxxx = _snowmanx.nextDouble();
               ceh _snowmanxxxxxxxxxxxxx = _snowmanxx.d_(_snowmanxxxxxxxxx);
               cux _snowmanxxxxxxxxxxxxxx = _snowmanxx.b(_snowmanxxxxxxxxx);
               ddh _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.k(_snowmanxx, _snowmanxxxxxxxxx);
               double _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.b(gc.a.b, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
               double _snowmanxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxx.a(_snowmanxx, _snowmanxxxxxxxxx);
               double _snowmanxxxxxxxxxxxxxxxxxx = Math.max(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx);
               hf _snowmanxxxxxxxxxxxxxxxxxxx = !_snowmanxxxxxxxxxxxxxx.a(aef.c) && !_snowmanxxxxxxxxxxxxx.a(bup.iJ) && !buy.g(_snowmanxxxxxxxxxxxxx) ? hh.R : hh.S;
               this.j
                  .r
                  .a(
                     _snowmanxxxxxxxxxxxxxxxxxxx,
                     (double)_snowmanxxxxxxxxx.u() + _snowmanxxxxxxxxxxx,
                     (double)_snowmanxxxxxxxxx.v() + _snowmanxxxxxxxxxxxxxxxxxx,
                     (double)_snowmanxxxxxxxxx.w() + _snowmanxxxxxxxxxxxx,
                     0.0,
                     0.0,
                     0.0
                  );
            }
         }

         if (_snowmanxxxx != null && _snowmanx.nextInt(3) < this.ap++) {
            this.ap = 0;
            if (_snowmanxxxx.v() > _snowmanxxx.v() + 1 && _snowmanxx.a(chn.a.e, _snowmanxxx).v() > afm.d((float)_snowmanxxx.v())) {
               this.j.r.a(_snowmanxxxx, adq.qE, adr.d, 0.1F, 0.5F, false);
            } else {
               this.j.r.a(_snowmanxxxx, adq.qD, adr.d, 0.2F, 1.0F, false);
            }
         }
      }
   }

   @Override
   public void close() {
      if (this.E != null) {
         this.E.close();
      }

      if (this.K != null) {
         this.K.close();
      }
   }

   @Override
   public void a(ach var1) {
      this.k.a(g);
      RenderSystem.texParameter(3553, 10242, 10497);
      RenderSystem.texParameter(3553, 10243, 10497);
      RenderSystem.bindTexture(0);
      this.a();
      if (djz.A()) {
         this.v();
      }
   }

   public void a() {
      if (this.E != null) {
         this.E.close();
      }

      vk _snowman = new vk("shaders/post/entity_outline.json");

      try {
         this.E = new eaj(this.j.M(), this.j.N(), this.j.f(), _snowman);
         this.E.a(this.j.aD().k(), this.j.aD().l());
         this.D = this.E.a("final");
      } catch (IOException var3) {
         b.warn("Failed to load shader: {}", _snowman, var3);
         this.E = null;
         this.D = null;
      } catch (JsonSyntaxException var4) {
         b.warn("Failed to parse shader: {}", _snowman, var4);
         this.E = null;
         this.D = null;
      }
   }

   private void v() {
      this.w();
      vk _snowman = new vk("shaders/post/transparency.json");

      try {
         eaj _snowmanx = new eaj(this.j.M(), this.j.N(), this.j.f(), _snowman);
         _snowmanx.a(this.j.aD().k(), this.j.aD().l());
         deg _snowmanxx = _snowmanx.a("translucent");
         deg _snowmanxxx = _snowmanx.a("itemEntity");
         deg _snowmanxxxx = _snowmanx.a("particles");
         deg _snowmanxxxxx = _snowmanx.a("weather");
         deg _snowmanxxxxxx = _snowmanx.a("clouds");
         this.K = _snowmanx;
         this.F = _snowmanxx;
         this.G = _snowmanxxx;
         this.H = _snowmanxxxx;
         this.I = _snowmanxxxxx;
         this.J = _snowmanxxxxxx;
      } catch (Exception var9) {
         String _snowmanxxxxxxx = var9 instanceof JsonSyntaxException ? "parse" : "load";
         String _snowmanxxxxxxxx = "Failed to " + _snowmanxxxxxxx + " shader: " + _snowman;
         eae.b _snowmanxxxxxxxxx = new eae.b(_snowmanxxxxxxxx, var9);
         if (this.j.O().d().size() > 1) {
            nr _snowmanxxxxxxxxxx;
            try {
               _snowmanxxxxxxxxxx = new oe(this.j.N().a(_snowman).d());
            } catch (IOException var8) {
               _snowmanxxxxxxxxxx = null;
            }

            this.j.k.f = djt.b;
            this.j.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
         } else {
            l _snowmanxxxxxxxxxx = this.j.c(new l(_snowmanxxxxxxxx, _snowmanxxxxxxxxx));
            this.j.k.f = djt.b;
            this.j.k.b();
            b.fatal(_snowmanxxxxxxxx, _snowmanxxxxxxxxx);
            this.j.m();
            djz.b(_snowmanxxxxxxxxxx);
         }
      }
   }

   private void w() {
      if (this.K != null) {
         this.K.close();
         this.F.a();
         this.G.a();
         this.H.a();
         this.I.a();
         this.J.a();
         this.K = null;
         this.F = null;
         this.G = null;
         this.H = null;
         this.I = null;
         this.J = null;
      }
   }

   public void b() {
      if (this.d()) {
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(dem.r.l, dem.j.j, dem.r.o, dem.j.e);
         this.D.c(this.j.aD().k(), this.j.aD().l(), false);
         RenderSystem.disableBlend();
      }
   }

   protected boolean d() {
      return this.D != null && this.E != null && this.j.s != null;
   }

   private void x() {
      dfo _snowman = dfo.a();
      dfh _snowmanx = _snowman.c();
      if (this.v != null) {
         this.v.close();
      }

      this.v = new dfp(this.s);
      this.a(_snowmanx, -16.0F, true);
      _snowmanx.c();
      this.v.a(_snowmanx);
   }

   private void y() {
      dfo _snowman = dfo.a();
      dfh _snowmanx = _snowman.c();
      if (this.u != null) {
         this.u.close();
      }

      this.u = new dfp(this.s);
      this.a(_snowmanx, 16.0F, false);
      _snowmanx.c();
      this.u.a(_snowmanx);
   }

   private void a(dfh var1, float var2, boolean var3) {
      int _snowman = 64;
      int _snowmanx = 6;
      _snowman.a(7, dfk.k);

      for (int _snowmanxx = -384; _snowmanxx <= 384; _snowmanxx += 64) {
         for (int _snowmanxxx = -384; _snowmanxxx <= 384; _snowmanxxx += 64) {
            float _snowmanxxxx = (float)_snowmanxx;
            float _snowmanxxxxx = (float)(_snowmanxx + 64);
            if (_snowman) {
               _snowmanxxxxx = (float)_snowmanxx;
               _snowmanxxxx = (float)(_snowmanxx + 64);
            }

            _snowman.a((double)_snowmanxxxx, (double)_snowman, (double)_snowmanxxx).d();
            _snowman.a((double)_snowmanxxxxx, (double)_snowman, (double)_snowmanxxx).d();
            _snowman.a((double)_snowmanxxxxx, (double)_snowman, (double)(_snowmanxxx + 64)).d();
            _snowman.a((double)_snowmanxxxx, (double)_snowman, (double)(_snowmanxxx + 64)).d();
         }
      }
   }

   private void z() {
      dfo _snowman = dfo.a();
      dfh _snowmanx = _snowman.c();
      if (this.t != null) {
         this.t.close();
      }

      this.t = new dfp(this.s);
      this.a(_snowmanx);
      _snowmanx.c();
      this.t.a(_snowmanx);
   }

   private void a(dfh var1) {
      Random _snowman = new Random(10842L);
      _snowman.a(7, dfk.k);

      for (int _snowmanx = 0; _snowmanx < 1500; _snowmanx++) {
         double _snowmanxx = (double)(_snowman.nextFloat() * 2.0F - 1.0F);
         double _snowmanxxx = (double)(_snowman.nextFloat() * 2.0F - 1.0F);
         double _snowmanxxxx = (double)(_snowman.nextFloat() * 2.0F - 1.0F);
         double _snowmanxxxxx = (double)(0.15F + _snowman.nextFloat() * 0.1F);
         double _snowmanxxxxxx = _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx;
         if (_snowmanxxxxxx < 1.0 && _snowmanxxxxxx > 0.01) {
            _snowmanxxxxxx = 1.0 / Math.sqrt(_snowmanxxxxxx);
            _snowmanxx *= _snowmanxxxxxx;
            _snowmanxxx *= _snowmanxxxxxx;
            _snowmanxxxx *= _snowmanxxxxxx;
            double _snowmanxxxxxxx = _snowmanxx * 100.0;
            double _snowmanxxxxxxxx = _snowmanxxx * 100.0;
            double _snowmanxxxxxxxxx = _snowmanxxxx * 100.0;
            double _snowmanxxxxxxxxxx = Math.atan2(_snowmanxx, _snowmanxxxx);
            double _snowmanxxxxxxxxxxx = Math.sin(_snowmanxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxx = Math.cos(_snowmanxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxx = Math.atan2(Math.sqrt(_snowmanxx * _snowmanxx + _snowmanxxxx * _snowmanxxxx), _snowmanxxx);
            double _snowmanxxxxxxxxxxxxxx = Math.sin(_snowmanxxxxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxxxx = Math.cos(_snowmanxxxxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxxxxx = _snowman.nextDouble() * Math.PI * 2.0;
            double _snowmanxxxxxxxxxxxxxxxxx = Math.sin(_snowmanxxxxxxxxxxxxxxxx);
            double _snowmanxxxxxxxxxxxxxxxxxx = Math.cos(_snowmanxxxxxxxxxxxxxxxx);

            for (int _snowmanxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxxxxxxxxx++) {
               double _snowmanxxxxxxxxxxxxxxxxxxxx = 0.0;
               double _snowmanxxxxxxxxxxxxxxxxxxxxx = (double)((_snowmanxxxxxxxxxxxxxxxxxxx & 2) - 1) * _snowmanxxxxx;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxx = (double)((_snowmanxxxxxxxxxxxxxxxxxxx + 1 & 2) - 1) * _snowmanxxxxx;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxx = 0.0;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxx;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxx + 0.0 * _snowmanxxxxxxxxxxxxxxx;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = 0.0 * _snowmanxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxx;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx;
               double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxx;
               _snowman.a(_snowmanxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx).d();
            }
         }
      }
   }

   public void a(@Nullable dwt var1) {
      this.L = Double.MIN_VALUE;
      this.M = Double.MIN_VALUE;
      this.N = Double.MIN_VALUE;
      this.O = Integer.MIN_VALUE;
      this.P = Integer.MIN_VALUE;
      this.Q = Integer.MIN_VALUE;
      this.l.a(_snowman);
      this.n = _snowman;
      if (_snowman != null) {
         this.e();
      } else {
         this.o.clear();
         this.p.clear();
         if (this.r != null) {
            this.r.a();
            this.r = null;
         }

         if (this.ab != null) {
            this.ab.g();
         }

         this.ab = null;
         this.q.clear();
      }
   }

   public void e() {
      if (this.n != null) {
         if (djz.A()) {
            this.v();
         } else {
            this.w();
         }

         this.n.i();
         if (this.ab == null) {
            this.ab = new ecu(this.n, this, x.f(), this.j.S(), this.m.a());
         } else {
            this.ab.a(this.n);
         }

         this.an = true;
         this.w = true;
         eab.a(djz.z());
         this.ad = this.j.k.b;
         if (this.r != null) {
            this.r.a();
         }

         this.f();
         synchronized (this.q) {
            this.q.clear();
         }

         this.r = new eat(this.ab, this.n, this.j.k.b, this);
         if (this.n != null) {
            aqa _snowman = this.j.aa();
            if (_snowman != null) {
               this.r.a(_snowman.cD(), _snowman.cH());
            }
         }
      }
   }

   protected void f() {
      this.o.clear();
      this.ab.e();
   }

   public void a(int var1, int var2) {
      this.o();
      if (this.E != null) {
         this.E.a(_snowman, _snowman);
      }

      if (this.K != null) {
         this.K.a(_snowman, _snowman);
      }
   }

   public String g() {
      int _snowman = this.r.f.length;
      int _snowmanx = this.h();
      return String.format("C: %d/%d %sD: %d, %s", _snowmanx, _snowman, this.j.E ? "(s) " : "", this.ad, this.ab == null ? "null" : this.ab.b());
   }

   protected int h() {
      int _snowman = 0;
      ObjectListIterator var2 = this.p.iterator();

      while (var2.hasNext()) {
         eae.a _snowmanx = (eae.a)var2.next();
         if (!_snowmanx.b.c().a()) {
            _snowman++;
         }
      }

      return _snowman;
   }

   public String i() {
      return "E: " + this.ae + "/" + this.n.j() + ", B: " + this.af;
   }

   private void a(djk var1, ecz var2, boolean var3, int var4, boolean var5) {
      dcn _snowman = _snowman.b();
      if (this.j.k.b != this.ad) {
         this.e();
      }

      this.n.Z().a("camera");
      double _snowmanx = this.j.s.cD() - this.L;
      double _snowmanxx = this.j.s.cE() - this.M;
      double _snowmanxxx = this.j.s.cH() - this.N;
      if (this.O != this.j.s.V || this.P != this.j.s.W || this.Q != this.j.s.X || _snowmanx * _snowmanx + _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx > 16.0) {
         this.L = this.j.s.cD();
         this.M = this.j.s.cE();
         this.N = this.j.s.cH();
         this.O = this.j.s.V;
         this.P = this.j.s.W;
         this.Q = this.j.s.X;
         this.r.a(this.j.s.cD(), this.j.s.cH());
      }

      this.ab.a(_snowman);
      this.n.Z().b("cull");
      this.j.au().b("culling");
      fx _snowmanxxxx = _snowman.c();
      ecu.c _snowmanxxxxx = this.r.a(_snowmanxxxx);
      int _snowmanxxxxxx = 16;
      fx _snowmanxxxxxxx = new fx(afm.c(_snowman.b / 16.0) * 16, afm.c(_snowman.c / 16.0) * 16, afm.c(_snowman.d / 16.0) * 16);
      float _snowmanxxxxxxxx = _snowman.d();
      float _snowmanxxxxxxxxx = _snowman.e();
      this.an = this.an || !this.o.isEmpty() || _snowman.b != this.R || _snowman.c != this.S || _snowman.d != this.T || (double)_snowmanxxxxxxxx != this.U || (double)_snowmanxxxxxxxxx != this.V;
      this.R = _snowman.b;
      this.S = _snowman.c;
      this.T = _snowman.d;
      this.U = (double)_snowmanxxxxxxxx;
      this.V = (double)_snowmanxxxxxxxxx;
      this.j.au().b("update");
      if (!_snowman && this.an) {
         this.an = false;
         this.p.clear();
         Queue<eae.a> _snowmanxxxxxxxxxx = Queues.newArrayDeque();
         aqa.b(afm.a((double)this.j.k.b / 8.0, 1.0, 2.5) * (double)this.j.k.c);
         boolean _snowmanxxxxxxxxxxx = this.j.E;
         if (_snowmanxxxxx != null) {
            if (_snowman && this.n.d_(_snowmanxxxx).i(this.n, _snowmanxxxx)) {
               _snowmanxxxxxxxxxxx = false;
            }

            _snowmanxxxxx.a(_snowman);
            _snowmanxxxxxxxxxx.add(new eae.a(_snowmanxxxxx, null, 0));
         } else {
            int _snowmanxxxxxxxxxxxx = _snowmanxxxx.v() > 0 ? 248 : 8;
            int _snowmanxxxxxxxxxxxxx = afm.c(_snowman.b / 16.0) * 16;
            int _snowmanxxxxxxxxxxxxxx = afm.c(_snowman.d / 16.0) * 16;
            List<eae.a> _snowmanxxxxxxxxxxxxxxx = Lists.newArrayList();

            for (int _snowmanxxxxxxxxxxxxxxxx = -this.ad; _snowmanxxxxxxxxxxxxxxxx <= this.ad; _snowmanxxxxxxxxxxxxxxxx++) {
               for (int _snowmanxxxxxxxxxxxxxxxxx = -this.ad; _snowmanxxxxxxxxxxxxxxxxx <= this.ad; _snowmanxxxxxxxxxxxxxxxxx++) {
                  ecu.c _snowmanxxxxxxxxxxxxxxxxxx = this.r
                     .a(new fx(_snowmanxxxxxxxxxxxxx + (_snowmanxxxxxxxxxxxxxxxx << 4) + 8, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx + (_snowmanxxxxxxxxxxxxxxxxx << 4) + 8));
                  if (_snowmanxxxxxxxxxxxxxxxxxx != null && _snowman.a(_snowmanxxxxxxxxxxxxxxxxxx.b)) {
                     _snowmanxxxxxxxxxxxxxxxxxx.a(_snowman);
                     _snowmanxxxxxxxxxxxxxxx.add(new eae.a(_snowmanxxxxxxxxxxxxxxxxxx, null, 0));
                  }
               }
            }

            _snowmanxxxxxxxxxxxxxxx.sort(Comparator.comparingDouble(var1x -> _snowman.j(var1x.b.e().b(8, 8, 8))));
            _snowmanxxxxxxxxxx.addAll(_snowmanxxxxxxxxxxxxxxx);
         }

         this.j.au().a("iteration");

         while (!_snowmanxxxxxxxxxx.isEmpty()) {
            eae.a _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx.poll();
            ecu.c _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.b;
            gc _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.c;
            this.p.add(_snowmanxxxxxxxxxxxx);

            for (gc _snowmanxxxxxxxxxxxxxxx : a) {
               ecu.c _snowmanxxxxxxxxxxxxxxxx = this.a(_snowmanxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
               if ((!_snowmanxxxxxxxxxxx || !_snowmanxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxx.f()))
                  && (!_snowmanxxxxxxxxxxx || _snowmanxxxxxxxxxxxxxx == null || _snowmanxxxxxxxxxxxxx.c().a(_snowmanxxxxxxxxxxxxxx.f(), _snowmanxxxxxxxxxxxxxxx))
                  && _snowmanxxxxxxxxxxxxxxxx != null
                  && _snowmanxxxxxxxxxxxxxxxx.a()
                  && _snowmanxxxxxxxxxxxxxxxx.a(_snowman)
                  && _snowman.a(_snowmanxxxxxxxxxxxxxxxx.b)) {
                  eae.a _snowmanxxxxxxxxxxxxxxxxxx = new eae.a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx.e + 1);
                  _snowmanxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxx.d, _snowmanxxxxxxxxxxxxxxx);
                  _snowmanxxxxxxxxxx.add(_snowmanxxxxxxxxxxxxxxxxxx);
               }
            }
         }

         this.j.au().c();
      }

      this.j.au().b("rebuildNear");
      Set<ecu.c> _snowmanxxxxxxxxxx = this.o;
      this.o = Sets.newLinkedHashSet();
      ObjectListIterator var31 = this.p.iterator();

      while (var31.hasNext()) {
         eae.a _snowmanxxxxxxxxxxx = (eae.a)var31.next();
         ecu.c _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.b;
         if (_snowmanxxxxxxxxxxxx.g() || _snowmanxxxxxxxxxx.contains(_snowmanxxxxxxxxxxxx)) {
            this.an = true;
            fx _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.e().b(8, 8, 8);
            boolean _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.j(_snowmanxxxx) < 768.0;
            if (!_snowmanxxxxxxxxxxxx.h() && !_snowmanxxxxxxxxxxxxxx) {
               this.o.add(_snowmanxxxxxxxxxxxx);
            } else {
               this.j.au().a("build near");
               this.ab.a(_snowmanxxxxxxxxxxxx);
               _snowmanxxxxxxxxxxxx.f();
               this.j.au().c();
            }
         }
      }

      this.o.addAll(_snowmanxxxxxxxxxx);
      this.j.au().c();
   }

   @Nullable
   private ecu.c a(fx var1, ecu.c var2, gc var3) {
      fx _snowman = _snowman.a(_snowman);
      if (afm.a(_snowman.u() - _snowman.u()) > this.ad * 16) {
         return null;
      } else if (_snowman.v() < 0 || _snowman.v() >= 256) {
         return null;
      } else {
         return afm.a(_snowman.w() - _snowman.w()) > this.ad * 16 ? null : this.r.a(_snowman);
      }
   }

   private void a(b var1, b var2, double var3, double var5, double var7, ecz var9) {
      this.ah = _snowman;
      b _snowman = _snowman.d();
      _snowman.a(_snowman);
      _snowman.c();
      this.aj.a = _snowman;
      this.aj.b = _snowman;
      this.aj.c = _snowman;
      this.ai[0] = new h(-1.0F, -1.0F, -1.0F, 1.0F);
      this.ai[1] = new h(1.0F, -1.0F, -1.0F, 1.0F);
      this.ai[2] = new h(1.0F, 1.0F, -1.0F, 1.0F);
      this.ai[3] = new h(-1.0F, 1.0F, -1.0F, 1.0F);
      this.ai[4] = new h(-1.0F, -1.0F, 1.0F, 1.0F);
      this.ai[5] = new h(1.0F, -1.0F, 1.0F, 1.0F);
      this.ai[6] = new h(1.0F, 1.0F, 1.0F, 1.0F);
      this.ai[7] = new h(-1.0F, 1.0F, 1.0F, 1.0F);

      for (int _snowmanx = 0; _snowmanx < 8; _snowmanx++) {
         this.ai[_snowmanx].a(_snowman);
         this.ai[_snowmanx].f();
      }
   }

   public void a(dfm var1, float var2, long var3, boolean var5, djk var6, dzz var7, eaf var8, b var9) {
      ecd.a.a(this.n, this.j.M(), this.j.g, _snowman, this.j.v);
      this.l.a(this.n, _snowman, this.j.u);
      anw _snowman = this.n.Z();
      _snowman.b("light_updates");
      this.j.r.n().l().a(Integer.MAX_VALUE, true, true);
      dcn _snowmanx = _snowman.b();
      double _snowmanxx = _snowmanx.a();
      double _snowmanxxx = _snowmanx.b();
      double _snowmanxxxx = _snowmanx.c();
      b _snowmanxxxxx = _snowman.c().a();
      _snowman.b("culling");
      boolean _snowmanxxxxxx = this.ah != null;
      ecz _snowmanxxxxxxx;
      if (_snowmanxxxxxx) {
         _snowmanxxxxxxx = this.ah;
         _snowmanxxxxxxx.a(this.aj.a, this.aj.b, this.aj.c);
      } else {
         _snowmanxxxxxxx = new ecz(_snowmanxxxxx, _snowman);
         _snowmanxxxxxxx.a(_snowmanxx, _snowmanxxx, _snowmanxxxx);
      }

      this.j.au().b("captureFrustum");
      if (this.ag) {
         this.a(_snowmanxxxxx, _snowman, _snowmanx.b, _snowmanx.c, _snowmanx.d, _snowmanxxxxxx ? new ecz(_snowmanxxxxx, _snowman) : _snowmanxxxxxxx);
         this.ag = false;
      }

      _snowman.b("clear");
      dzy.a(_snowman, _snowman, this.j.r, this.j.k.b, _snowman.b(_snowman));
      RenderSystem.clear(16640, djz.a);
      float _snowmanxxxxxxxx = _snowman.j();
      boolean _snowmanxxxxxxxxx = this.j.r.a().a(afm.c(_snowmanxx), afm.c(_snowmanxxx)) || this.j.j.i().e();
      if (this.j.k.b >= 4) {
         dzy.a(_snowman, dzy.a.a, _snowmanxxxxxxxx, _snowmanxxxxxxxxx);
         _snowman.b("sky");
         this.a(_snowman, _snowman);
      }

      _snowman.b("fog");
      dzy.a(_snowman, dzy.a.b, Math.max(_snowmanxxxxxxxx - 16.0F, 32.0F), _snowmanxxxxxxxxx);
      _snowman.b("terrain_setup");
      this.a(_snowman, _snowmanxxxxxxx, _snowmanxxxxxx, this.ao++, this.j.s.a_());
      _snowman.b("updatechunks");
      int _snowmanxxxxxxxxxx = 30;
      int _snowmanxxxxxxxxxxx = this.j.k.d;
      long _snowmanxxxxxxxxxxxx = 33333333L;
      long _snowmanxxxxxxxxxxxxx;
      if ((double)_snowmanxxxxxxxxxxx == dkc.l.d()) {
         _snowmanxxxxxxxxxxxxx = 0L;
      } else {
         _snowmanxxxxxxxxxxxxx = (long)(1000000000 / _snowmanxxxxxxxxxxx);
      }

      long _snowmanxxxxxxxxxxxxxx = x.c() - _snowman;
      long _snowmanxxxxxxxxxxxxxxx = this.y.a(_snowmanxxxxxxxxxxxxxx);
      long _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx * 3L / 2L;
      long _snowmanxxxxxxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, 33333333L);
      this.a(_snowman + _snowmanxxxxxxxxxxxxxxxxx);
      _snowman.b("terrain");
      this.a(eao.c(), _snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx);
      this.a(eao.d(), _snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx);
      this.a(eao.e(), _snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx);
      if (this.n.a().e()) {
         dep.a(_snowman.c().a());
      } else {
         dep.b(_snowman.c().a());
      }

      _snowman.b("entities");
      this.ae = 0;
      this.af = 0;
      if (this.G != null) {
         this.G.b(djz.a);
         this.G.a(this.j.f());
         this.j.f().a(false);
      }

      if (this.I != null) {
         this.I.b(djz.a);
      }

      if (this.d()) {
         this.D.b(djz.a);
         this.j.f().a(false);
      }

      boolean _snowmanxxxxxxxxxxxxxxxxxx = false;
      eag.a _snowmanxxxxxxxxxxxxxxxxxxx = this.m.b();

      for (aqa _snowmanxxxxxxxxxxxxxxxxxxxx : this.n.b()) {
         if ((this.l.a(_snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxx, _snowmanxx, _snowmanxxx, _snowmanxxxx) || _snowmanxxxxxxxxxxxxxxxxxxxx.y(this.j.s))
            && (_snowmanxxxxxxxxxxxxxxxxxxxx != _snowman.g() || _snowman.i() || _snowman.g() instanceof aqm && ((aqm)_snowman.g()).em())
            && (!(_snowmanxxxxxxxxxxxxxxxxxxxx instanceof dzm) || _snowman.g() == _snowmanxxxxxxxxxxxxxxxxxxxx)) {
            this.ae++;
            if (_snowmanxxxxxxxxxxxxxxxxxxxx.K == 0) {
               _snowmanxxxxxxxxxxxxxxxxxxxx.D = _snowmanxxxxxxxxxxxxxxxxxxxx.cD();
               _snowmanxxxxxxxxxxxxxxxxxxxx.E = _snowmanxxxxxxxxxxxxxxxxxxxx.cE();
               _snowmanxxxxxxxxxxxxxxxxxxxx.F = _snowmanxxxxxxxxxxxxxxxxxxxx.cH();
            }

            eag _snowmanxxxxxxxxxxxxxxxxxxxxx;
            if (this.d() && this.j.b(_snowmanxxxxxxxxxxxxxxxxxxxx)) {
               _snowmanxxxxxxxxxxxxxxxxxx = true;
               eah _snowmanxxxxxxxxxxxxxxxxxxxxxx = this.m.d();
               _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx.U();
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 255;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxx >> 16 & 0xFF;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxx >> 8 & 0xFF;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxx & 0xFF;
               _snowmanxxxxxxxxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 255);
            } else {
               _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx;
            }

            this.a(_snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowman, _snowman, _snowmanxxxxxxxxxxxxxxxxxxxxx);
         }
      }

      this.a(_snowman);
      _snowmanxxxxxxxxxxxxxxxxxxx.a(eao.b(ekb.d));
      _snowmanxxxxxxxxxxxxxxxxxxx.a(eao.c(ekb.d));
      _snowmanxxxxxxxxxxxxxxxxxxx.a(eao.d(ekb.d));
      _snowmanxxxxxxxxxxxxxxxxxxx.a(eao.i(ekb.d));
      _snowman.b("blockentities");
      ObjectListIterator var53 = this.p.iterator();

      while (var53.hasNext()) {
         eae.a _snowmanxxxxxxxxxxxxxxxxxxxxx = (eae.a)var53.next();
         List<ccj> _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx.b.c().b();
         if (!_snowmanxxxxxxxxxxxxxxxxxxxxxx.isEmpty()) {
            for (ccj _snowmanxxxxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxxxxxxx) {
               fx _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxx.o();
               eag _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx;
               _snowman.a();
               _snowman.a((double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.u() - _snowmanxx, (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.v() - _snowmanxxx, (double)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.w() - _snowmanxxxx);
               SortedSet<zq> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = (SortedSet<zq>)this.B.get(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx.a());
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.isEmpty()) {
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.last().c();
                  if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx >= 0) {
                     dfm.a _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.c();
                     dfq _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new dfn(
                        this.m.c().getBuffer(els.k.get(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx)), _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(), _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b()
                     );
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = var2x -> {
                        dfq _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.getBuffer(var2x);
                        return var2x.A() ? dft.a(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) : _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                     };
                  }
               }

               ecd.a.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx);
               _snowman.b();
            }
         }
      }

      synchronized (this.q) {
         for (ccj _snowmanxxxxxxxxxxxxxxxxxxxxx : this.q) {
            fx _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxx.o();
            _snowman.a();
            _snowman.a((double)_snowmanxxxxxxxxxxxxxxxxxxxxxx.u() - _snowmanxx, (double)_snowmanxxxxxxxxxxxxxxxxxxxxxx.v() - _snowmanxxx, (double)_snowmanxxxxxxxxxxxxxxxxxxxxxx.w() - _snowmanxxxx);
            ecd.a.a(_snowmanxxxxxxxxxxxxxxxxxxxxx, _snowman, _snowman, _snowmanxxxxxxxxxxxxxxxxxxx);
            _snowman.b();
         }
      }

      this.a(_snowman);
      _snowmanxxxxxxxxxxxxxxxxxxx.a(eao.c());
      _snowmanxxxxxxxxxxxxxxxxxxx.a(ear.g());
      _snowmanxxxxxxxxxxxxxxxxxxx.a(ear.h());
      _snowmanxxxxxxxxxxxxxxxxxxx.a(ear.c());
      _snowmanxxxxxxxxxxxxxxxxxxx.a(ear.d());
      _snowmanxxxxxxxxxxxxxxxxxxx.a(ear.e());
      _snowmanxxxxxxxxxxxxxxxxxxx.a(ear.f());
      this.m.d().a();
      if (_snowmanxxxxxxxxxxxxxxxxxx) {
         this.E.a(_snowman);
         this.j.f().a(false);
      }

      _snowman.b("destroyProgress");
      ObjectIterator var55 = this.B.long2ObjectEntrySet().iterator();

      while (var55.hasNext()) {
         Entry<SortedSet<zq>> _snowmanxxxxxxxxxxxxxxxxxxxxx = (Entry<SortedSet<zq>>)var55.next();
         fx _snowmanxxxxxxxxxxxxxxxxxxxxxx = fx.e(_snowmanxxxxxxxxxxxxxxxxxxxxx.getLongKey());
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxxxxxxxxx.u() - _snowmanxx;
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxxxxxxxxx.v() - _snowmanxxx;
         double _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxxxxxxxxxxxx.w() - _snowmanxxxx;
         if (!(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxx
                  + _snowmanxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxx
                  + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx
               > 1024.0
         )) {
            SortedSet<zq> _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = (SortedSet<zq>)_snowmanxxxxxxxxxxxxxxxxxxxxx.getValue();
            if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx != null && !_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.isEmpty()) {
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx.last().c();
               _snowman.a();
               _snowman.a((double)_snowmanxxxxxxxxxxxxxxxxxxxxxx.u() - _snowmanxx, (double)_snowmanxxxxxxxxxxxxxxxxxxxxxx.v() - _snowmanxxx, (double)_snowmanxxxxxxxxxxxxxxxxxxxxxx.w() - _snowmanxxxx);
               dfm.a _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.c();
               dfq _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new dfn(
                  this.m.c().getBuffer(els.k.get(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx)), _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(), _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b()
               );
               this.j.ab().a(this.n.d_(_snowmanxxxxxxxxxxxxxxxxxxxxxx), _snowmanxxxxxxxxxxxxxxxxxxxxxx, this.n, _snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
               _snowman.b();
            }
         }
      }

      this.a(_snowman);
      dcl _snowmanxxxxxxxxxxxxxxxxxxxxx = this.j.v;
      if (_snowman && _snowmanxxxxxxxxxxxxxxxxxxxxx != null && _snowmanxxxxxxxxxxxxxxxxxxxxx.c() == dcl.a.b) {
         _snowman.b("outline");
         fx _snowmanxxxxxxxxxxxxxxxxxxxxxx = ((dcj)_snowmanxxxxxxxxxxxxxxxxxxxxx).a();
         ceh _snowmanxxxxxxxxxxxxxxxxxxxxxxx = this.n.d_(_snowmanxxxxxxxxxxxxxxxxxxxxxx);
         if (!_snowmanxxxxxxxxxxxxxxxxxxxxxxx.g() && this.n.f().a(_snowmanxxxxxxxxxxxxxxxxxxxxxx)) {
            dfq _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxx.getBuffer(eao.t());
            this.a(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxxxxxx, _snowman.g(), _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx);
         }
      }

      RenderSystem.pushMatrix();
      RenderSystem.multMatrix(_snowman.c().a());
      this.j.i.a(_snowman, _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
      RenderSystem.popMatrix();
      _snowmanxxxxxxxxxxxxxxxxxxx.a(ear.j());
      _snowmanxxxxxxxxxxxxxxxxxxx.a(ear.a());
      _snowmanxxxxxxxxxxxxxxxxxxx.a(ear.b());
      _snowmanxxxxxxxxxxxxxxxxxxx.a(eao.k());
      _snowmanxxxxxxxxxxxxxxxxxxx.a(eao.l());
      _snowmanxxxxxxxxxxxxxxxxxxx.a(eao.n());
      _snowmanxxxxxxxxxxxxxxxxxxx.a(eao.o());
      _snowmanxxxxxxxxxxxxxxxxxxx.a(eao.m());
      _snowmanxxxxxxxxxxxxxxxxxxx.a(eao.p());
      _snowmanxxxxxxxxxxxxxxxxxxx.a(eao.q());
      _snowmanxxxxxxxxxxxxxxxxxxx.a(eao.j());
      this.m.c().a();
      if (this.K != null) {
         _snowmanxxxxxxxxxxxxxxxxxxx.a(eao.t());
         _snowmanxxxxxxxxxxxxxxxxxxx.a();
         this.F.b(djz.a);
         this.F.a(this.j.f());
         _snowman.b("translucent");
         this.a(eao.f(), _snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx);
         _snowman.b("string");
         this.a(eao.s(), _snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx);
         this.H.b(djz.a);
         this.H.a(this.j.f());
         ean.Q.a();
         _snowman.b("particles");
         this.j.f.a(_snowman, _snowmanxxxxxxxxxxxxxxxxxxx, _snowman, _snowman, _snowman);
         ean.Q.b();
      } else {
         _snowman.b("translucent");
         this.a(eao.f(), _snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx);
         _snowmanxxxxxxxxxxxxxxxxxxx.a(eao.t());
         _snowmanxxxxxxxxxxxxxxxxxxx.a();
         _snowman.b("string");
         this.a(eao.s(), _snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx);
         _snowman.b("particles");
         this.j.f.a(_snowman, _snowmanxxxxxxxxxxxxxxxxxxx, _snowman, _snowman, _snowman);
      }

      RenderSystem.pushMatrix();
      RenderSystem.multMatrix(_snowman.c().a());
      if (this.j.k.e() != djn.a) {
         if (this.K != null) {
            this.J.b(djz.a);
            ean.S.a();
            _snowman.b("clouds");
            this.a(_snowman, _snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx);
            ean.S.b();
         } else {
            _snowman.b("clouds");
            this.a(_snowman, _snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx);
         }
      }

      if (this.K != null) {
         ean.R.a();
         _snowman.b("weather");
         this.a(_snowman, _snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx);
         this.c(_snowman);
         ean.R.b();
         this.K.a(_snowman);
         this.j.f().a(false);
      } else {
         RenderSystem.depthMask(false);
         _snowman.b("weather");
         this.a(_snowman, _snowman, _snowmanxx, _snowmanxxx, _snowmanxxxx);
         this.c(_snowman);
         RenderSystem.depthMask(true);
      }

      this.b(_snowman);
      RenderSystem.shadeModel(7424);
      RenderSystem.depthMask(true);
      RenderSystem.disableBlend();
      RenderSystem.popMatrix();
      dzy.a();
   }

   private void a(dfm var1) {
      if (!_snowman.d()) {
         throw new IllegalStateException("Pose stack not empty");
      }
   }

   private void a(aqa var1, double var2, double var4, double var6, float var8, dfm var9, eag var10) {
      double _snowman = afm.d((double)_snowman, _snowman.D, _snowman.cD());
      double _snowmanx = afm.d((double)_snowman, _snowman.E, _snowman.cE());
      double _snowmanxx = afm.d((double)_snowman, _snowman.F, _snowman.cH());
      float _snowmanxxx = afm.g(_snowman, _snowman.r, _snowman.p);
      this.l.a(_snowman, _snowman - _snowman, _snowmanx - _snowman, _snowmanxx - _snowman, _snowmanxxx, _snowman, _snowman, _snowman, this.l.a(_snowman, _snowman));
   }

   private void a(eao var1, dfm var2, double var3, double var5, double var7) {
      _snowman.a();
      if (_snowman == eao.f()) {
         this.j.au().a("translucent_sort");
         double _snowman = _snowman - this.ak;
         double _snowmanx = _snowman - this.al;
         double _snowmanxx = _snowman - this.am;
         if (_snowman * _snowman + _snowmanx * _snowmanx + _snowmanxx * _snowmanxx > 1.0) {
            this.ak = _snowman;
            this.al = _snowman;
            this.am = _snowman;
            int _snowmanxxx = 0;
            ObjectListIterator var16 = this.p.iterator();

            while (var16.hasNext()) {
               eae.a _snowmanxxxx = (eae.a)var16.next();
               if (_snowmanxxx < 15 && _snowmanxxxx.b.a(_snowman, this.ab)) {
                  _snowmanxxx++;
               }
            }
         }

         this.j.au().c();
      }

      this.j.au().a("filterempty");
      this.j.au().b(() -> "render_" + _snowman);
      boolean _snowman = _snowman != eao.f();
      ObjectListIterator<eae.a> _snowmanx = this.p.listIterator(_snowman ? 0 : this.p.size());

      while (_snowman ? _snowmanx.hasNext() : _snowmanx.hasPrevious()) {
         eae.a _snowmanxx = _snowman ? (eae.a)_snowmanx.next() : (eae.a)_snowmanx.previous();
         ecu.c _snowmanxxx = _snowmanxx.b;
         if (!_snowmanxxx.c().a(_snowman)) {
            dfp _snowmanxxxx = _snowmanxxx.a(_snowman);
            _snowman.a();
            fx _snowmanxxxxx = _snowmanxxx.e();
            _snowman.a((double)_snowmanxxxxx.u() - _snowman, (double)_snowmanxxxxx.v() - _snowman, (double)_snowmanxxxxx.w() - _snowman);
            _snowmanxxxx.a();
            this.ac.a(0L);
            _snowmanxxxx.a(_snowman.c().a(), 7);
            _snowman.b();
         }
      }

      dfp.b();
      RenderSystem.clearCurrentColor();
      this.ac.d();
      this.j.au().c();
      _snowman.b();
   }

   private void b(djk var1) {
      dfo _snowman = dfo.a();
      dfh _snowmanx = _snowman.c();
      if (this.j.C || this.j.D) {
         double _snowmanxx = _snowman.b().a();
         double _snowmanxxx = _snowman.b().b();
         double _snowmanxxxx = _snowman.b().c();
         RenderSystem.depthMask(true);
         RenderSystem.disableCull();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableTexture();

         for (ObjectListIterator var10 = this.p.iterator(); var10.hasNext(); RenderSystem.popMatrix()) {
            eae.a _snowmanxxxxx = (eae.a)var10.next();
            ecu.c _snowmanxxxxxx = _snowmanxxxxx.b;
            RenderSystem.pushMatrix();
            fx _snowmanxxxxxxx = _snowmanxxxxxx.e();
            RenderSystem.translated((double)_snowmanxxxxxxx.u() - _snowmanxx, (double)_snowmanxxxxxxx.v() - _snowmanxxx, (double)_snowmanxxxxxxx.w() - _snowmanxxxx);
            if (this.j.C) {
               _snowmanx.a(1, dfk.l);
               RenderSystem.lineWidth(10.0F);
               int _snowmanxxxxxxxx = _snowmanxxxxx.e == 0 ? 0 : afm.f((float)_snowmanxxxxx.e / 50.0F, 0.9F, 0.9F);
               int _snowmanxxxxxxxxx = _snowmanxxxxxxxx >> 16 & 0xFF;
               int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx >> 8 & 0xFF;
               int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx & 0xFF;
               gc _snowmanxxxxxxxxxxxx = _snowmanxxxxx.c;
               if (_snowmanxxxxxxxxxxxx != null) {
                  _snowmanx.a(8.0, 8.0, 8.0).a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, 255).d();
                  _snowmanx.a((double)(8 - 16 * _snowmanxxxxxxxxxxxx.i()), (double)(8 - 16 * _snowmanxxxxxxxxxxxx.j()), (double)(8 - 16 * _snowmanxxxxxxxxxxxx.k()))
                     .a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, 255)
                     .d();
               }

               _snowman.b();
               RenderSystem.lineWidth(1.0F);
            }

            if (this.j.D && !_snowmanxxxxxx.c().a()) {
               _snowmanx.a(1, dfk.l);
               RenderSystem.lineWidth(10.0F);
               int _snowmanxxxxxxxx = 0;

               for (gc _snowmanxxxxxxxxx : a) {
                  for (gc _snowmanxxxxxxxxxx : a) {
                     boolean _snowmanxxxxxxxxxxx = _snowmanxxxxxx.c().a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
                     if (!_snowmanxxxxxxxxxxx) {
                        _snowmanxxxxxxxx++;
                        _snowmanx.a((double)(8 + 8 * _snowmanxxxxxxxxx.i()), (double)(8 + 8 * _snowmanxxxxxxxxx.j()), (double)(8 + 8 * _snowmanxxxxxxxxx.k())).a(1, 0, 0, 1).d();
                        _snowmanx.a((double)(8 + 8 * _snowmanxxxxxxxxxx.i()), (double)(8 + 8 * _snowmanxxxxxxxxxx.j()), (double)(8 + 8 * _snowmanxxxxxxxxxx.k())).a(1, 0, 0, 1).d();
                     }
                  }
               }

               _snowman.b();
               RenderSystem.lineWidth(1.0F);
               if (_snowmanxxxxxxxx > 0) {
                  _snowmanx.a(7, dfk.l);
                  float _snowmanxxxxxxxxx = 0.5F;
                  float _snowmanxxxxxxxxxxx = 0.2F;
                  _snowmanx.a(0.5, 15.5, 0.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(15.5, 15.5, 0.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(15.5, 15.5, 15.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(0.5, 15.5, 15.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(0.5, 0.5, 15.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(15.5, 0.5, 15.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(15.5, 0.5, 0.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(0.5, 0.5, 0.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(0.5, 15.5, 0.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(0.5, 15.5, 15.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(0.5, 0.5, 15.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(0.5, 0.5, 0.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(15.5, 0.5, 0.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(15.5, 0.5, 15.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(15.5, 15.5, 15.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(15.5, 15.5, 0.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(0.5, 0.5, 0.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(15.5, 0.5, 0.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(15.5, 15.5, 0.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(0.5, 15.5, 0.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(0.5, 15.5, 15.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(15.5, 15.5, 15.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(15.5, 0.5, 15.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowmanx.a(0.5, 0.5, 15.5).a(0.9F, 0.9F, 0.0F, 0.2F).d();
                  _snowman.b();
               }
            }
         }

         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
         RenderSystem.enableCull();
         RenderSystem.enableTexture();
      }

      if (this.ah != null) {
         RenderSystem.disableCull();
         RenderSystem.disableTexture();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.lineWidth(10.0F);
         RenderSystem.pushMatrix();
         RenderSystem.translatef((float)(this.aj.a - _snowman.b().b), (float)(this.aj.b - _snowman.b().c), (float)(this.aj.c - _snowman.b().d));
         RenderSystem.depthMask(true);
         _snowmanx.a(7, dfk.l);
         this.a(_snowmanx, 0, 1, 2, 3, 0, 1, 1);
         this.a(_snowmanx, 4, 5, 6, 7, 1, 0, 0);
         this.a(_snowmanx, 0, 1, 5, 4, 1, 1, 0);
         this.a(_snowmanx, 2, 3, 7, 6, 0, 0, 1);
         this.a(_snowmanx, 0, 4, 7, 3, 0, 1, 0);
         this.a(_snowmanx, 1, 5, 6, 2, 1, 0, 1);
         _snowman.b();
         RenderSystem.depthMask(false);
         _snowmanx.a(1, dfk.k);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.a(_snowmanx, 0);
         this.a(_snowmanx, 1);
         this.a(_snowmanx, 1);
         this.a(_snowmanx, 2);
         this.a(_snowmanx, 2);
         this.a(_snowmanx, 3);
         this.a(_snowmanx, 3);
         this.a(_snowmanx, 0);
         this.a(_snowmanx, 4);
         this.a(_snowmanx, 5);
         this.a(_snowmanx, 5);
         this.a(_snowmanx, 6);
         this.a(_snowmanx, 6);
         this.a(_snowmanx, 7);
         this.a(_snowmanx, 7);
         this.a(_snowmanx, 4);
         this.a(_snowmanx, 0);
         this.a(_snowmanx, 4);
         this.a(_snowmanx, 1);
         this.a(_snowmanx, 5);
         this.a(_snowmanx, 2);
         this.a(_snowmanx, 6);
         this.a(_snowmanx, 3);
         this.a(_snowmanx, 7);
         _snowman.b();
         RenderSystem.popMatrix();
         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
         RenderSystem.enableCull();
         RenderSystem.enableTexture();
         RenderSystem.lineWidth(1.0F);
      }
   }

   private void a(dfq var1, int var2) {
      _snowman.a((double)this.ai[_snowman].a(), (double)this.ai[_snowman].b(), (double)this.ai[_snowman].c()).d();
   }

   private void a(dfq var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      float _snowman = 0.25F;
      _snowman.a((double)this.ai[_snowman].a(), (double)this.ai[_snowman].b(), (double)this.ai[_snowman].c()).a((float)_snowman, (float)_snowman, (float)_snowman, 0.25F).d();
      _snowman.a((double)this.ai[_snowman].a(), (double)this.ai[_snowman].b(), (double)this.ai[_snowman].c()).a((float)_snowman, (float)_snowman, (float)_snowman, 0.25F).d();
      _snowman.a((double)this.ai[_snowman].a(), (double)this.ai[_snowman].b(), (double)this.ai[_snowman].c()).a((float)_snowman, (float)_snowman, (float)_snowman, 0.25F).d();
      _snowman.a((double)this.ai[_snowman].a(), (double)this.ai[_snowman].b(), (double)this.ai[_snowman].c()).a((float)_snowman, (float)_snowman, (float)_snowman, 0.25F).d();
   }

   public void l() {
      this.z++;
      if (this.z % 20 == 0) {
         Iterator<zq> _snowman = this.A.values().iterator();

         while (_snowman.hasNext()) {
            zq _snowmanx = _snowman.next();
            int _snowmanxx = _snowmanx.d();
            if (this.z - _snowmanxx > 400) {
               _snowman.remove();
               this.a(_snowmanx);
            }
         }
      }
   }

   private void a(zq var1) {
      long _snowman = _snowman.b().a();
      Set<zq> _snowmanx = (Set<zq>)this.B.get(_snowman);
      _snowmanx.remove(_snowman);
      if (_snowmanx.isEmpty()) {
         this.B.remove(_snowman);
      }
   }

   private void b(dfm var1) {
      RenderSystem.disableAlphaTest();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.depthMask(false);
      this.k.a(f);
      dfo _snowman = dfo.a();
      dfh _snowmanx = _snowman.c();

      for (int _snowmanxx = 0; _snowmanxx < 6; _snowmanxx++) {
         _snowman.a();
         if (_snowmanxx == 1) {
            _snowman.a(g.b.a(90.0F));
         }

         if (_snowmanxx == 2) {
            _snowman.a(g.b.a(-90.0F));
         }

         if (_snowmanxx == 3) {
            _snowman.a(g.b.a(180.0F));
         }

         if (_snowmanxx == 4) {
            _snowman.a(g.f.a(90.0F));
         }

         if (_snowmanxx == 5) {
            _snowman.a(g.f.a(-90.0F));
         }

         b _snowmanxxx = _snowman.c().a();
         _snowmanx.a(7, dfk.p);
         _snowmanx.a(_snowmanxxx, -100.0F, -100.0F, -100.0F).a(0.0F, 0.0F).a(40, 40, 40, 255).d();
         _snowmanx.a(_snowmanxxx, -100.0F, -100.0F, 100.0F).a(0.0F, 16.0F).a(40, 40, 40, 255).d();
         _snowmanx.a(_snowmanxxx, 100.0F, -100.0F, 100.0F).a(16.0F, 16.0F).a(40, 40, 40, 255).d();
         _snowmanx.a(_snowmanxxx, 100.0F, -100.0F, -100.0F).a(16.0F, 0.0F).a(40, 40, 40, 255).d();
         _snowman.b();
         _snowman.b();
      }

      RenderSystem.depthMask(true);
      RenderSystem.enableTexture();
      RenderSystem.disableBlend();
      RenderSystem.enableAlphaTest();
   }

   public void a(dfm var1, float var2) {
      if (this.j.r.a().c() == dzv.d.c) {
         this.b(_snowman);
      } else if (this.j.r.a().c() == dzv.d.b) {
         RenderSystem.disableTexture();
         dcn _snowman = this.n.a(this.j.h.k().c(), _snowman);
         float _snowmanx = (float)_snowman.b;
         float _snowmanxx = (float)_snowman.c;
         float _snowmanxxx = (float)_snowman.d;
         dzy.b();
         dfh _snowmanxxxx = dfo.a().c();
         RenderSystem.depthMask(false);
         RenderSystem.enableFog();
         RenderSystem.color3f(_snowmanx, _snowmanxx, _snowmanxxx);
         this.u.a();
         this.s.a(0L);
         this.u.a(_snowman.c().a(), 7);
         dfp.b();
         this.s.d();
         RenderSystem.disableFog();
         RenderSystem.disableAlphaTest();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         float[] _snowmanxxxxx = this.n.a().a(this.n.f(_snowman), _snowman);
         if (_snowmanxxxxx != null) {
            RenderSystem.disableTexture();
            RenderSystem.shadeModel(7425);
            _snowman.a();
            _snowman.a(g.b.a(90.0F));
            float _snowmanxxxxxx = afm.a(this.n.a(_snowman)) < 0.0F ? 180.0F : 0.0F;
            _snowman.a(g.f.a(_snowmanxxxxxx));
            _snowman.a(g.f.a(90.0F));
            float _snowmanxxxxxxx = _snowmanxxxxx[0];
            float _snowmanxxxxxxxx = _snowmanxxxxx[1];
            float _snowmanxxxxxxxxx = _snowmanxxxxx[2];
            b _snowmanxxxxxxxxxx = _snowman.c().a();
            _snowmanxxxx.a(6, dfk.l);
            _snowmanxxxx.a(_snowmanxxxxxxxxxx, 0.0F, 100.0F, 0.0F).a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxx[3]).d();
            int _snowmanxxxxxxxxxxx = 16;

            for (int _snowmanxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxx <= 16; _snowmanxxxxxxxxxxxx++) {
               float _snowmanxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxx * (float) (Math.PI * 2) / 16.0F;
               float _snowmanxxxxxxxxxxxxxx = afm.a(_snowmanxxxxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxxxxx = afm.b(_snowmanxxxxxxxxxxxxx);
               _snowmanxxxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx * 120.0F, _snowmanxxxxxxxxxxxxxxx * 120.0F, -_snowmanxxxxxxxxxxxxxxx * 40.0F * _snowmanxxxxx[3])
                  .a(_snowmanxxxxx[0], _snowmanxxxxx[1], _snowmanxxxxx[2], 0.0F)
                  .d();
            }

            _snowmanxxxx.c();
            dfi.a(_snowmanxxxx);
            _snowman.b();
            RenderSystem.shadeModel(7424);
         }

         RenderSystem.enableTexture();
         RenderSystem.blendFuncSeparate(dem.r.l, dem.j.e, dem.r.e, dem.j.n);
         _snowman.a();
         float _snowmanxxxxxx = 1.0F - this.n.d(_snowman);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, _snowmanxxxxxx);
         _snowman.a(g.d.a(-90.0F));
         _snowman.a(g.b.a(this.n.f(_snowman) * 360.0F));
         b _snowmanxxxxxxx = _snowman.c().a();
         float _snowmanxxxxxxxx = 30.0F;
         this.k.a(d);
         _snowmanxxxx.a(7, dfk.n);
         _snowmanxxxx.a(_snowmanxxxxxxx, -_snowmanxxxxxxxx, 100.0F, -_snowmanxxxxxxxx).a(0.0F, 0.0F).d();
         _snowmanxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, 100.0F, -_snowmanxxxxxxxx).a(1.0F, 0.0F).d();
         _snowmanxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, 100.0F, _snowmanxxxxxxxx).a(1.0F, 1.0F).d();
         _snowmanxxxx.a(_snowmanxxxxxxx, -_snowmanxxxxxxxx, 100.0F, _snowmanxxxxxxxx).a(0.0F, 1.0F).d();
         _snowmanxxxx.c();
         dfi.a(_snowmanxxxx);
         _snowmanxxxxxxxx = 20.0F;
         this.k.a(c);
         int _snowmanxxxxxxxxx = this.n.ag();
         int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx % 4;
         int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx / 4 % 2;
         float _snowmanxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxx + 0) / 4.0F;
         float _snowmanxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxx + 0) / 2.0F;
         float _snowmanxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxx + 1) / 4.0F;
         float _snowmanxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxx + 1) / 2.0F;
         _snowmanxxxx.a(7, dfk.n);
         _snowmanxxxx.a(_snowmanxxxxxxx, -_snowmanxxxxxxxx, -100.0F, _snowmanxxxxxxxx).a(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx).d();
         _snowmanxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, -100.0F, _snowmanxxxxxxxx).a(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx).d();
         _snowmanxxxx.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, -100.0F, -_snowmanxxxxxxxx).a(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx).d();
         _snowmanxxxx.a(_snowmanxxxxxxx, -_snowmanxxxxxxxx, -100.0F, -_snowmanxxxxxxxx).a(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx).d();
         _snowmanxxxx.c();
         dfi.a(_snowmanxxxx);
         RenderSystem.disableTexture();
         float _snowmanxxxxxxxxxxxxxxxx = this.n.i(_snowman) * _snowmanxxxxxx;
         if (_snowmanxxxxxxxxxxxxxxxx > 0.0F) {
            RenderSystem.color4f(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
            this.t.a();
            this.s.a(0L);
            this.t.a(_snowman.c().a(), 7);
            dfp.b();
            this.s.d();
         }

         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableBlend();
         RenderSystem.enableAlphaTest();
         RenderSystem.enableFog();
         _snowman.b();
         RenderSystem.disableTexture();
         RenderSystem.color3f(0.0F, 0.0F, 0.0F);
         double _snowmanxxxxxxxxxxxxxxxxx = this.j.s.j(_snowman).c - this.n.w().g();
         if (_snowmanxxxxxxxxxxxxxxxxx < 0.0) {
            _snowman.a();
            _snowman.a(0.0, 12.0, 0.0);
            this.v.a();
            this.s.a(0L);
            this.v.a(_snowman.c().a(), 7);
            dfp.b();
            this.s.d();
            _snowman.b();
         }

         if (this.n.a().b()) {
            RenderSystem.color3f(_snowmanx * 0.2F + 0.04F, _snowmanxx * 0.2F + 0.04F, _snowmanxxx * 0.6F + 0.1F);
         } else {
            RenderSystem.color3f(_snowmanx, _snowmanxx, _snowmanxxx);
         }

         RenderSystem.enableTexture();
         RenderSystem.depthMask(true);
         RenderSystem.disableFog();
      }
   }

   public void a(dfm var1, float var2, double var3, double var5, double var7) {
      float _snowman = this.n.a().a();
      if (!Float.isNaN(_snowman)) {
         RenderSystem.disableCull();
         RenderSystem.enableBlend();
         RenderSystem.enableAlphaTest();
         RenderSystem.enableDepthTest();
         RenderSystem.defaultAlphaFunc();
         RenderSystem.blendFuncSeparate(dem.r.l, dem.j.j, dem.r.e, dem.j.j);
         RenderSystem.enableFog();
         RenderSystem.depthMask(true);
         float _snowmanx = 12.0F;
         float _snowmanxx = 4.0F;
         double _snowmanxxx = 2.0E-4;
         double _snowmanxxxx = (double)(((float)this.z + _snowman) * 0.03F);
         double _snowmanxxxxx = (_snowman + _snowmanxxxx) / 12.0;
         double _snowmanxxxxxx = (double)(_snowman - (float)_snowman + 0.33F);
         double _snowmanxxxxxxx = _snowman / 12.0 + 0.33F;
         _snowmanxxxxx -= (double)(afm.c(_snowmanxxxxx / 2048.0) * 2048);
         _snowmanxxxxxxx -= (double)(afm.c(_snowmanxxxxxxx / 2048.0) * 2048);
         float _snowmanxxxxxxxx = (float)(_snowmanxxxxx - (double)afm.c(_snowmanxxxxx));
         float _snowmanxxxxxxxxx = (float)(_snowmanxxxxxx / 4.0 - (double)afm.c(_snowmanxxxxxx / 4.0)) * 4.0F;
         float _snowmanxxxxxxxxxx = (float)(_snowmanxxxxxxx - (double)afm.c(_snowmanxxxxxxx));
         dcn _snowmanxxxxxxxxxxx = this.n.h(_snowman);
         int _snowmanxxxxxxxxxxxx = (int)Math.floor(_snowmanxxxxx);
         int _snowmanxxxxxxxxxxxxx = (int)Math.floor(_snowmanxxxxxx / 4.0);
         int _snowmanxxxxxxxxxxxxxx = (int)Math.floor(_snowmanxxxxxxx);
         if (_snowmanxxxxxxxxxxxx != this.W || _snowmanxxxxxxxxxxxxx != this.X || _snowmanxxxxxxxxxxxxxx != this.Y || this.j.k.e() != this.aa || this.Z.g(_snowmanxxxxxxxxxxx) > 2.0E-4) {
            this.W = _snowmanxxxxxxxxxxxx;
            this.X = _snowmanxxxxxxxxxxxxx;
            this.Y = _snowmanxxxxxxxxxxxxxx;
            this.Z = _snowmanxxxxxxxxxxx;
            this.aa = this.j.k.e();
            this.w = true;
         }

         if (this.w) {
            this.w = false;
            dfh _snowmanxxxxxxxxxxxxxxx = dfo.a().c();
            if (this.x != null) {
               this.x.close();
            }

            this.x = new dfp(dfk.s);
            this.a(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxxxx);
            _snowmanxxxxxxxxxxxxxxx.c();
            this.x.a(_snowmanxxxxxxxxxxxxxxx);
         }

         this.k.a(e);
         _snowman.a();
         _snowman.a(12.0F, 1.0F, 12.0F);
         _snowman.a((double)(-_snowmanxxxxxxxx), (double)_snowmanxxxxxxxxx, (double)(-_snowmanxxxxxxxxxx));
         if (this.x != null) {
            this.x.a();
            dfk.s.a(0L);
            int _snowmanxxxxxxxxxxxxxxx = this.aa == djn.c ? 0 : 1;

            for (int _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxx < 2; _snowmanxxxxxxxxxxxxxxxx++) {
               if (_snowmanxxxxxxxxxxxxxxxx == 0) {
                  RenderSystem.colorMask(false, false, false, false);
               } else {
                  RenderSystem.colorMask(true, true, true, true);
               }

               this.x.a(_snowman.c().a(), 7);
            }

            dfp.b();
            dfk.s.d();
         }

         _snowman.b();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.disableAlphaTest();
         RenderSystem.enableCull();
         RenderSystem.disableBlend();
         RenderSystem.disableFog();
      }
   }

   private void a(dfh var1, double var2, double var4, double var6, dcn var8) {
      float _snowman = 4.0F;
      float _snowmanx = 0.00390625F;
      int _snowmanxx = 8;
      int _snowmanxxx = 4;
      float _snowmanxxxx = 9.765625E-4F;
      float _snowmanxxxxx = (float)afm.c(_snowman) * 0.00390625F;
      float _snowmanxxxxxx = (float)afm.c(_snowman) * 0.00390625F;
      float _snowmanxxxxxxx = (float)_snowman.b;
      float _snowmanxxxxxxxx = (float)_snowman.c;
      float _snowmanxxxxxxxxx = (float)_snowman.d;
      float _snowmanxxxxxxxxxx = _snowmanxxxxxxx * 0.9F;
      float _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx * 0.9F;
      float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx * 0.9F;
      float _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxx * 0.7F;
      float _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxx * 0.7F;
      float _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx * 0.7F;
      float _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxx * 0.8F;
      float _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx * 0.8F;
      float _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxx * 0.8F;
      _snowman.a(7, dfk.s);
      float _snowmanxxxxxxxxxxxxxxxxxxx = (float)Math.floor(_snowman / 4.0) * 4.0F;
      if (this.aa == djn.c) {
         for (int _snowmanxxxxxxxxxxxxxxxxxxxx = -3; _snowmanxxxxxxxxxxxxxxxxxxxx <= 4; _snowmanxxxxxxxxxxxxxxxxxxxx++) {
            for (int _snowmanxxxxxxxxxxxxxxxxxxxxx = -3; _snowmanxxxxxxxxxxxxxxxxxxxxx <= 4; _snowmanxxxxxxxxxxxxxxxxxxxxx++) {
               float _snowmanxxxxxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxxxxxxxx * 8);
               float _snowmanxxxxxxxxxxxxxxxxxxxxxxx = (float)(_snowmanxxxxxxxxxxxxxxxxxxxxx * 8);
               if (_snowmanxxxxxxxxxxxxxxxxxxx > -5.0F) {
                  _snowman.a((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F))
                     .a((_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxxx)
                     .a(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, 0.8F)
                     .b(0.0F, -1.0F, 0.0F)
                     .d();
                  _snowman.a((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F))
                     .a((_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxxx)
                     .a(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, 0.8F)
                     .b(0.0F, -1.0F, 0.0F)
                     .d();
                  _snowman.a((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F))
                     .a((_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxxx)
                     .a(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, 0.8F)
                     .b(0.0F, -1.0F, 0.0F)
                     .d();
                  _snowman.a((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F))
                     .a((_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxxx)
                     .a(_snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, 0.8F)
                     .b(0.0F, -1.0F, 0.0F)
                     .d();
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxx <= 5.0F) {
                  _snowman.a((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F - 9.765625E-4F), (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F))
                     .a((_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxxx)
                     .a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.8F)
                     .b(0.0F, 1.0F, 0.0F)
                     .d();
                  _snowman.a((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F - 9.765625E-4F), (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F))
                     .a((_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxxx)
                     .a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.8F)
                     .b(0.0F, 1.0F, 0.0F)
                     .d();
                  _snowman.a((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F - 9.765625E-4F), (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F))
                     .a((_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxxx)
                     .a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.8F)
                     .b(0.0F, 1.0F, 0.0F)
                     .d();
                  _snowman.a((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F), (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F - 9.765625E-4F), (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F))
                     .a((_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxx, (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxxx)
                     .a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.8F)
                     .b(0.0F, 1.0F, 0.0F)
                     .d();
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxx > -1) {
                  for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx < 8; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx++) {
                     _snowman.a(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                        )
                        .a(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.8F)
                        .b(-1.0F, 0.0F, 0.0F)
                        .d();
                     _snowman.a(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                        )
                        .a(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.8F)
                        .b(-1.0F, 0.0F, 0.0F)
                        .d();
                     _snowman.a(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .a(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.8F)
                        .b(-1.0F, 0.0F, 0.0F)
                        .d();
                     _snowman.a(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .a(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.8F)
                        .b(-1.0F, 0.0F, 0.0F)
                        .d();
                  }
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxx <= 1) {
                  for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx < 8; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx++) {
                     _snowman.a(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                        )
                        .a(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.8F)
                        .b(1.0F, 0.0F, 0.0F)
                        .d();
                     _snowman.a(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F)
                        )
                        .a(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.8F)
                        .b(1.0F, 0.0F, 0.0F)
                        .d();
                     _snowman.a(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .a(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.8F)
                        .b(1.0F, 0.0F, 0.0F)
                        .d();
                     _snowman.a(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .a(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, 0.8F)
                        .b(1.0F, 0.0F, 0.0F)
                        .d();
                  }
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxx > -1) {
                  for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx < 8; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx++) {
                     _snowman.a(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .a(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.8F)
                        .b(0.0F, 0.0F, -1.0F)
                        .d();
                     _snowman.a(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .a(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.8F)
                        .b(0.0F, 0.0F, -1.0F)
                        .d();
                     _snowman.a(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .a(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.8F)
                        .b(0.0F, 0.0F, -1.0F)
                        .d();
                     _snowman.a(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.0F)
                        )
                        .a(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.8F)
                        .b(0.0F, 0.0F, -1.0F)
                        .d();
                  }
               }

               if (_snowmanxxxxxxxxxxxxxxxxxxxxx <= 1) {
                  for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx < 8; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx++) {
                     _snowman.a(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F)
                        )
                        .a(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.8F)
                        .b(0.0F, 0.0F, 1.0F)
                        .d();
                     _snowman.a(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 4.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F)
                        )
                        .a(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.8F)
                        .b(0.0F, 0.0F, 1.0F)
                        .d();
                     _snowman.a(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F)
                        )
                        .a(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + 8.0F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.8F)
                        .b(0.0F, 0.0F, 1.0F)
                        .d();
                     _snowman.a(
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxx + 0.0F),
                           (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 1.0F - 9.765625E-4F)
                        )
                        .a(
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxx + 0.0F) * 0.00390625F + _snowmanxxxxx,
                           (_snowmanxxxxxxxxxxxxxxxxxxxxxxx + (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0.5F) * 0.00390625F + _snowmanxxxxxx
                        )
                        .a(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0.8F)
                        .b(0.0F, 0.0F, 1.0F)
                        .d();
                  }
               }
            }
         }
      } else {
         int _snowmanxxxxxxxxxxxxxxxxxxxx = 1;
         int _snowmanxxxxxxxxxxxxxxxxxxxxx = 32;

         for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = -32; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx < 32; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx += 32) {
            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = -32; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx < 32; _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx += 32) {
               _snowman.a((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0), (double)_snowmanxxxxxxxxxxxxxxxxxxx, (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 32))
                  .a((float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0) * 0.00390625F + _snowmanxxxxx, (float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 32) * 0.00390625F + _snowmanxxxxxx)
                  .a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.8F)
                  .b(0.0F, -1.0F, 0.0F)
                  .d();
               _snowman.a((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 32), (double)_snowmanxxxxxxxxxxxxxxxxxxx, (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 32))
                  .a((float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 32) * 0.00390625F + _snowmanxxxxx, (float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 32) * 0.00390625F + _snowmanxxxxxx)
                  .a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.8F)
                  .b(0.0F, -1.0F, 0.0F)
                  .d();
               _snowman.a((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 32), (double)_snowmanxxxxxxxxxxxxxxxxxxx, (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 0))
                  .a((float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 32) * 0.00390625F + _snowmanxxxxx, (float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 0) * 0.00390625F + _snowmanxxxxxx)
                  .a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.8F)
                  .b(0.0F, -1.0F, 0.0F)
                  .d();
               _snowman.a((double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0), (double)_snowmanxxxxxxxxxxxxxxxxxxx, (double)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 0))
                  .a((float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx + 0) * 0.00390625F + _snowmanxxxxx, (float)(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx + 0) * 0.00390625F + _snowmanxxxxxx)
                  .a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.8F)
                  .b(0.0F, -1.0F, 0.0F)
                  .d();
            }
         }
      }
   }

   private void a(long var1) {
      this.an = this.an | this.ab.d();
      long _snowman = x.c();
      int _snowmanx = 0;
      if (!this.o.isEmpty()) {
         Iterator<ecu.c> _snowmanxx = this.o.iterator();

         while (_snowmanxx.hasNext()) {
            ecu.c _snowmanxxx = _snowmanxx.next();
            if (_snowmanxxx.h()) {
               this.ab.a(_snowmanxxx);
            } else {
               _snowmanxxx.a(this.ab);
            }

            _snowmanxxx.f();
            _snowmanxx.remove();
            _snowmanx++;
            long _snowmanxxxx = x.c();
            long _snowmanxxxxx = _snowmanxxxx - _snowman;
            long _snowmanxxxxxx = _snowmanxxxxx / (long)_snowmanx;
            long _snowmanxxxxxxx = _snowman - _snowmanxxxx;
            if (_snowmanxxxxxxx < _snowmanxxxxxx) {
               break;
            }
         }
      }
   }

   private void c(djk var1) {
      dfh _snowman = dfo.a().c();
      cfu _snowmanx = this.n.f();
      double _snowmanxx = (double)(this.j.k.b * 16);
      if (!(_snowman.b().b < _snowmanx.g() - _snowmanxx) || !(_snowman.b().b > _snowmanx.e() + _snowmanxx) || !(_snowman.b().d < _snowmanx.h() - _snowmanxx) || !(_snowman.b().d > _snowmanx.f() + _snowmanxx)) {
         double _snowmanxxx = 1.0 - _snowmanx.b(_snowman.b().b, _snowman.b().d) / _snowmanxx;
         _snowmanxxx = Math.pow(_snowmanxxx, 4.0);
         double _snowmanxxxx = _snowman.b().b;
         double _snowmanxxxxx = _snowman.b().c;
         double _snowmanxxxxxx = _snowman.b().d;
         RenderSystem.enableBlend();
         RenderSystem.enableDepthTest();
         RenderSystem.blendFuncSeparate(dem.r.l, dem.j.e, dem.r.e, dem.j.n);
         this.k.a(g);
         RenderSystem.depthMask(djz.A());
         RenderSystem.pushMatrix();
         int _snowmanxxxxxxx = _snowmanx.d().a();
         float _snowmanxxxxxxxx = (float)(_snowmanxxxxxxx >> 16 & 0xFF) / 255.0F;
         float _snowmanxxxxxxxxx = (float)(_snowmanxxxxxxx >> 8 & 0xFF) / 255.0F;
         float _snowmanxxxxxxxxxx = (float)(_snowmanxxxxxxx & 0xFF) / 255.0F;
         RenderSystem.color4f(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, (float)_snowmanxxx);
         RenderSystem.polygonOffset(-3.0F, -3.0F);
         RenderSystem.enablePolygonOffset();
         RenderSystem.defaultAlphaFunc();
         RenderSystem.enableAlphaTest();
         RenderSystem.disableCull();
         float _snowmanxxxxxxxxxxx = (float)(x.b() % 3000L) / 3000.0F;
         float _snowmanxxxxxxxxxxxx = 0.0F;
         float _snowmanxxxxxxxxxxxxx = 0.0F;
         float _snowmanxxxxxxxxxxxxxx = 128.0F;
         _snowman.a(7, dfk.n);
         double _snowmanxxxxxxxxxxxxxxx = Math.max((double)afm.c(_snowmanxxxxxx - _snowmanxx), _snowmanx.f());
         double _snowmanxxxxxxxxxxxxxxxx = Math.min((double)afm.f(_snowmanxxxxxx + _snowmanxx), _snowmanx.h());
         if (_snowmanxxxx > _snowmanx.g() - _snowmanxx) {
            float _snowmanxxxxxxxxxxxxxxxxx = 0.0F;

            for (double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx += 0.5F) {
               double _snowmanxxxxxxxxxxxxxxxxxxx = Math.min(1.0, _snowmanxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxxxxxxxxx * 0.5F;
               this.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanx.g(), 256, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + 0.0F);
               this.a(
                  _snowman,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanx.g(),
                  256,
                  _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + 0.0F
               );
               this.a(
                  _snowman,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanx.g(),
                  0,
                  _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + 128.0F
               );
               this.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanx.g(), 0, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + 128.0F);
               _snowmanxxxxxxxxxxxxxxxxxx++;
            }
         }

         if (_snowmanxxxx < _snowmanx.e() + _snowmanxx) {
            float _snowmanxxxxxxxxxxxxxxxxx = 0.0F;

            for (double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx += 0.5F) {
               double _snowmanxxxxxxxxxxxxxxxxxxx = Math.min(1.0, _snowmanxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxxxxxxxxx * 0.5F;
               this.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanx.e(), 256, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + 0.0F);
               this.a(
                  _snowman,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanx.e(),
                  256,
                  _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + 0.0F
               );
               this.a(
                  _snowman,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanx.e(),
                  0,
                  _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + 128.0F
               );
               this.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanx.e(), 0, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + 128.0F);
               _snowmanxxxxxxxxxxxxxxxxxx++;
            }
         }

         _snowmanxxxxxxxxxxxxxxx = Math.max((double)afm.c(_snowmanxxxx - _snowmanxx), _snowmanx.e());
         _snowmanxxxxxxxxxxxxxxxx = Math.min((double)afm.f(_snowmanxxxx + _snowmanxx), _snowmanx.g());
         if (_snowmanxxxxxx > _snowmanx.h() - _snowmanxx) {
            float _snowmanxxxxxxxxxxxxxxxxx = 0.0F;

            for (double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx += 0.5F) {
               double _snowmanxxxxxxxxxxxxxxxxxxx = Math.min(1.0, _snowmanxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxxxxxxxxx * 0.5F;
               this.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 256, _snowmanx.h(), _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + 0.0F);
               this.a(
                  _snowman,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                  256,
                  _snowmanx.h(),
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + 0.0F
               );
               this.a(
                  _snowman,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                  0,
                  _snowmanx.h(),
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + 128.0F
               );
               this.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0, _snowmanx.h(), _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + 128.0F);
               _snowmanxxxxxxxxxxxxxxxxxx++;
            }
         }

         if (_snowmanxxxxxx < _snowmanx.f() + _snowmanxx) {
            float _snowmanxxxxxxxxxxxxxxxxx = 0.0F;

            for (double _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx += 0.5F) {
               double _snowmanxxxxxxxxxxxxxxxxxxx = Math.min(1.0, _snowmanxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxx);
               float _snowmanxxxxxxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxxxxxxxxx * 0.5F;
               this.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 256, _snowmanx.f(), _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + 0.0F);
               this.a(
                  _snowman,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                  256,
                  _snowmanx.f(),
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + 0.0F
               );
               this.a(
                  _snowman,
                  _snowmanxxxx,
                  _snowmanxxxxx,
                  _snowmanxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx,
                  0,
                  _snowmanx.f(),
                  _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxx + 128.0F
               );
               this.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 0, _snowmanx.f(), _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx + 128.0F);
               _snowmanxxxxxxxxxxxxxxxxxx++;
            }
         }

         _snowman.c();
         dfi.a(_snowman);
         RenderSystem.enableCull();
         RenderSystem.disableAlphaTest();
         RenderSystem.polygonOffset(0.0F, 0.0F);
         RenderSystem.disablePolygonOffset();
         RenderSystem.enableAlphaTest();
         RenderSystem.disableBlend();
         RenderSystem.popMatrix();
         RenderSystem.depthMask(true);
      }
   }

   private void a(dfh var1, double var2, double var4, double var6, double var8, int var10, double var11, float var13, float var14) {
      _snowman.a(_snowman - _snowman, (double)_snowman - _snowman, _snowman - _snowman).a(_snowman, _snowman).d();
   }

   private void a(dfm var1, dfq var2, aqa var3, double var4, double var6, double var8, fx var10, ceh var11) {
      b(_snowman, _snowman, _snowman.a(this.n, _snowman, dcs.a(_snowman)), (double)_snowman.u() - _snowman, (double)_snowman.v() - _snowman, (double)_snowman.w() - _snowman, 0.0F, 0.0F, 0.0F, 0.4F);
   }

   public static void a(dfm var0, dfq var1, ddh var2, double var3, double var5, double var7, float var9, float var10, float var11, float var12) {
      List<dci> _snowman = _snowman.d();
      int _snowmanx = afm.f((double)_snowman.size() / 3.0);

      for (int _snowmanxx = 0; _snowmanxx < _snowman.size(); _snowmanxx++) {
         dci _snowmanxxx = _snowman.get(_snowmanxx);
         float _snowmanxxxx = ((float)_snowmanxx % (float)_snowmanx + 1.0F) / (float)_snowmanx;
         float _snowmanxxxxx = (float)(_snowmanxx / _snowmanx);
         float _snowmanxxxxxx = _snowmanxxxx * (float)(_snowmanxxxxx == 0.0F ? 1 : 0);
         float _snowmanxxxxxxx = _snowmanxxxx * (float)(_snowmanxxxxx == 1.0F ? 1 : 0);
         float _snowmanxxxxxxxx = _snowmanxxxx * (float)(_snowmanxxxxx == 2.0F ? 1 : 0);
         b(_snowman, _snowman, dde.a(_snowmanxxx.d(0.0, 0.0, 0.0)), _snowman, _snowman, _snowman, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, 1.0F);
      }
   }

   private static void b(dfm var0, dfq var1, ddh var2, double var3, double var5, double var7, float var9, float var10, float var11, float var12) {
      b _snowman = _snowman.c().a();
      _snowman.a((var12x, var14, var16, var18, var20, var22) -> {
         _snowman.a(_snowman, (float)(var12x + _snowman), (float)(var14 + _snowman), (float)(var16 + _snowman)).a(_snowman, _snowman, _snowman, _snowman).d();
         _snowman.a(_snowman, (float)(var18 + _snowman), (float)(var20 + _snowman), (float)(var22 + _snowman)).a(_snowman, _snowman, _snowman, _snowman).d();
      });
   }

   public static void a(dfm var0, dfq var1, dci var2, float var3, float var4, float var5, float var6) {
      a(_snowman, _snowman, _snowman.a, _snowman.b, _snowman.c, _snowman.d, _snowman.e, _snowman.f, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static void a(
      dfm var0, dfq var1, double var2, double var4, double var6, double var8, double var10, double var12, float var14, float var15, float var16, float var17
   ) {
      a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public static void a(
      dfm var0,
      dfq var1,
      double var2,
      double var4,
      double var6,
      double var8,
      double var10,
      double var12,
      float var14,
      float var15,
      float var16,
      float var17,
      float var18,
      float var19,
      float var20
   ) {
      b _snowman = _snowman.c().a();
      float _snowmanx = (float)_snowman;
      float _snowmanxx = (float)_snowman;
      float _snowmanxxx = (float)_snowman;
      float _snowmanxxxx = (float)_snowman;
      float _snowmanxxxxx = (float)_snowman;
      float _snowmanxxxxxx = (float)_snowman;
      _snowman.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanx, _snowmanxxxxx, _snowmanxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxxxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanx, _snowmanxxxxx, _snowmanxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanx, _snowmanxxxxx, _snowmanxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanx, _snowmanxxxxx, _snowmanxxxxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanx, _snowmanxxxxx, _snowmanxxxxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxxxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanx, _snowmanxx, _snowmanxxxxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxxxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxxxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanx, _snowmanxxxxx, _snowmanxxxxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxxxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxx).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx).a(_snowman, _snowman, _snowman, _snowman).d();
   }

   public static void a(
      dfh var0, double var1, double var3, double var5, double var7, double var9, double var11, float var13, float var14, float var15, float var16
   ) {
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
      _snowman.a(_snowman, _snowman, _snowman).a(_snowman, _snowman, _snowman, _snowman).d();
   }

   public void a(brc var1, fx var2, ceh var3, ceh var4, int var5) {
      this.a(_snowman, (_snowman & 8) != 0);
   }

   private void a(fx var1, boolean var2) {
      for (int _snowman = _snowman.w() - 1; _snowman <= _snowman.w() + 1; _snowman++) {
         for (int _snowmanx = _snowman.u() - 1; _snowmanx <= _snowman.u() + 1; _snowmanx++) {
            for (int _snowmanxx = _snowman.v() - 1; _snowmanxx <= _snowman.v() + 1; _snowmanxx++) {
               this.a(_snowmanx >> 4, _snowmanxx >> 4, _snowman >> 4, _snowman);
            }
         }
      }
   }

   public void a(int var1, int var2, int var3, int var4, int var5, int var6) {
      for (int _snowman = _snowman - 1; _snowman <= _snowman + 1; _snowman++) {
         for (int _snowmanx = _snowman - 1; _snowmanx <= _snowman + 1; _snowmanx++) {
            for (int _snowmanxx = _snowman - 1; _snowmanxx <= _snowman + 1; _snowmanxx++) {
               this.b(_snowmanx >> 4, _snowmanxx >> 4, _snowman >> 4);
            }
         }
      }
   }

   public void a(fx var1, ceh var2, ceh var3) {
      if (this.j.ar().a(_snowman, _snowman)) {
         this.a(_snowman.u(), _snowman.v(), _snowman.w(), _snowman.u(), _snowman.v(), _snowman.w());
      }
   }

   public void a(int var1, int var2, int var3) {
      for (int _snowman = _snowman - 1; _snowman <= _snowman + 1; _snowman++) {
         for (int _snowmanx = _snowman - 1; _snowmanx <= _snowman + 1; _snowmanx++) {
            for (int _snowmanxx = _snowman - 1; _snowmanxx <= _snowman + 1; _snowmanxx++) {
               this.b(_snowmanx, _snowmanxx, _snowman);
            }
         }
      }
   }

   public void b(int var1, int var2, int var3) {
      this.a(_snowman, _snowman, _snowman, false);
   }

   private void a(int var1, int var2, int var3, boolean var4) {
      this.r.a(_snowman, _snowman, _snowman, _snowman);
   }

   public void a(@Nullable adp var1, fx var2) {
      emt _snowman = this.C.get(_snowman);
      if (_snowman != null) {
         this.j.W().b(_snowman);
         this.C.remove(_snowman);
      }

      if (_snowman != null) {
         bmq _snowmanx = bmq.a(_snowman);
         if (_snowmanx != null) {
            this.j.j.a(_snowmanx.g());
         }

         emt var5 = emp.a(_snowman, (double)_snowman.u(), (double)_snowman.v(), (double)_snowman.w());
         this.C.put(_snowman, var5);
         this.j.W().a(var5);
      }

      this.a(this.n, _snowman, _snowman != null);
   }

   private void a(brx var1, fx var2, boolean var3) {
      for (aqm _snowman : _snowman.a(aqm.class, new dci(_snowman).g(3.0))) {
         _snowman.a(_snowman, _snowman);
      }
   }

   public void a(hf var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
      this.a(_snowman, _snowman, false, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public void a(hf var1, boolean var2, boolean var3, double var4, double var6, double var8, double var10, double var12, double var14) {
      try {
         this.b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } catch (Throwable var19) {
         l _snowman = l.a(var19, "Exception while adding particle");
         m _snowmanx = _snowman.a("Particle being added");
         _snowmanx.a("ID", gm.V.b(_snowman.b()));
         _snowmanx.a("Parameters", _snowman.a());
         _snowmanx.a("Position", () -> m.a(_snowman, _snowman, _snowman));
         throw new u(_snowman);
      }
   }

   private <T extends hf> void a(T var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      this.a(_snowman, _snowman.b().c(), _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   private dyg b(hf var1, boolean var2, double var3, double var5, double var7, double var9, double var11, double var13) {
      return this.b(_snowman, _snowman, false, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Nullable
   private dyg b(hf var1, boolean var2, boolean var3, double var4, double var6, double var8, double var10, double var12, double var14) {
      djk _snowman = this.j.h.k();
      if (this.j != null && _snowman.h() && this.j.f != null) {
         dke _snowmanx = this.a(_snowman);
         if (_snowman) {
            return this.j.f.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         } else if (_snowman.b().c(_snowman, _snowman, _snowman) > 1024.0) {
            return null;
         } else {
            return _snowmanx == dke.c ? null : this.j.f.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         }
      } else {
         return null;
      }
   }

   private dke a(boolean var1) {
      dke _snowman = this.j.k.aT;
      if (_snowman && _snowman == dke.c && this.n.t.nextInt(10) == 0) {
         _snowman = dke.b;
      }

      if (_snowman == dke.b && this.n.t.nextInt(3) == 0) {
         _snowman = dke.c;
      }

      return _snowman;
   }

   public void m() {
   }

   public void a(int var1, fx var2, int var3) {
      switch (_snowman) {
         case 1023:
         case 1028:
         case 1038:
            djk _snowman = this.j.h.k();
            if (_snowman.h()) {
               double _snowmanx = (double)_snowman.u() - _snowman.b().b;
               double _snowmanxx = (double)_snowman.v() - _snowman.b().c;
               double _snowmanxxx = (double)_snowman.w() - _snowman.b().d;
               double _snowmanxxxx = Math.sqrt(_snowmanx * _snowmanx + _snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx);
               double _snowmanxxxxx = _snowman.b().b;
               double _snowmanxxxxxx = _snowman.b().c;
               double _snowmanxxxxxxx = _snowman.b().d;
               if (_snowmanxxxx > 0.0) {
                  _snowmanxxxxx += _snowmanx / _snowmanxxxx * 2.0;
                  _snowmanxxxxxx += _snowmanxx / _snowmanxxxx * 2.0;
                  _snowmanxxxxxxx += _snowmanxxx / _snowmanxxxx * 2.0;
               }

               if (_snowman == 1023) {
                  this.n.a(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, adq.qZ, adr.f, 1.0F, 1.0F, false);
               } else if (_snowman == 1038) {
                  this.n.a(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, adq.dL, adr.f, 1.0F, 1.0F, false);
               } else {
                  this.n.a(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, adq.dq, adr.f, 5.0F, 1.0F, false);
               }
            }
      }
   }

   public void a(bfw var1, int var2, fx var3, int var4) {
      Random _snowman = this.n.t;
      switch (_snowman) {
         case 1000:
            this.n.a(_snowman, adq.cA, adr.e, 1.0F, 1.0F, false);
            break;
         case 1001:
            this.n.a(_snowman, adq.cB, adr.e, 1.0F, 1.2F, false);
            break;
         case 1002:
            this.n.a(_snowman, adq.cC, adr.e, 1.0F, 1.2F, false);
            break;
         case 1003:
            this.n.a(_snowman, adq.dx, adr.g, 1.0F, 1.2F, false);
            break;
         case 1004:
            this.n.a(_snowman, adq.ef, adr.g, 1.0F, 1.2F, false);
            break;
         case 1005:
            this.n.a(_snowman, adq.gw, adr.e, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1006:
            this.n.a(_snowman, adq.rk, adr.e, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1007:
            this.n.a(_snowman, adq.rm, adr.e, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1008:
            this.n.a(_snowman, adq.dY, adr.e, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1009:
            this.n.a(_snowman, adq.ej, adr.e, 0.5F, 2.6F + (_snowman.nextFloat() - _snowman.nextFloat()) * 0.8F, false);
            break;
         case 1010:
            if (blx.b(_snowman) instanceof bmq) {
               this.a(((bmq)blx.b(_snowman)).v(), _snowman);
            } else {
               this.a(null, _snowman);
            }
            break;
         case 1011:
            this.n.a(_snowman, adq.gv, adr.e, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1012:
            this.n.a(_snowman, adq.rj, adr.e, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1013:
            this.n.a(_snowman, adq.rl, adr.e, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1014:
            this.n.a(_snowman, adq.dX, adr.e, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1015:
            this.n.a(_snowman, adq.eW, adr.f, 10.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1016:
            this.n.a(_snowman, adq.eV, adr.f, 10.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1017:
            this.n.a(_snowman, adq.dv, adr.f, 10.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1018:
            this.n.a(_snowman, adq.aP, adr.f, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1019:
            this.n.a(_snowman, adq.rI, adr.f, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1020:
            this.n.a(_snowman, adq.rJ, adr.f, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1021:
            this.n.a(_snowman, adq.rK, adr.f, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1022:
            this.n.a(_snowman, adq.qR, adr.f, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1024:
            this.n.a(_snowman, adq.qU, adr.f, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1025:
            this.n.a(_snowman, adq.at, adr.g, 0.05F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1026:
            this.n.a(_snowman, adq.rS, adr.f, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1027:
            this.n.a(_snowman, adq.rZ, adr.g, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1029:
            this.n.a(_snowman, adq.C, adr.e, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1030:
            this.n.a(_snowman, adq.I, adr.e, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1031:
            this.n.a(_snowman, adq.F, adr.e, 0.3F, this.n.t.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1032:
            this.j.W().a(emp.b(adq.lO, _snowman.nextFloat() * 0.4F + 0.8F, 0.25F));
            break;
         case 1033:
            this.n.a(_snowman, adq.bN, adr.e, 1.0F, 1.0F, false);
            break;
         case 1034:
            this.n.a(_snowman, adq.bM, adr.e, 1.0F, 1.0F, false);
            break;
         case 1035:
            this.n.a(_snowman, adq.bd, adr.e, 1.0F, 1.0F, false);
            break;
         case 1036:
            this.n.a(_snowman, adq.gD, adr.e, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1037:
            this.n.a(_snowman, adq.gE, adr.e, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1039:
            this.n.a(_snowman, adq.kI, adr.f, 0.3F, this.n.t.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1040:
            this.n.a(_snowman, adq.rL, adr.g, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1041:
            this.n.a(_snowman, adq.gk, adr.g, 2.0F, (_snowman.nextFloat() - _snowman.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1042:
            this.n.a(_snowman, adq.fr, adr.e, 1.0F, this.n.t.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1043:
            this.n.a(_snowman, adq.aX, adr.e, 1.0F, this.n.t.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1044:
            this.n.a(_snowman, adq.on, adr.e, 1.0F, this.n.t.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1500:
            bvk.a(this.n, _snowman, _snowman > 0);
            break;
         case 1501:
            this.n.a(_snowman, adq.gX, adr.e, 0.5F, 2.6F + (_snowman.nextFloat() - _snowman.nextFloat()) * 0.8F, false);

            for (int _snowmanxx = 0; _snowmanxx < 8; _snowmanxx++) {
               this.n.a(hh.L, (double)_snowman.u() + _snowman.nextDouble(), (double)_snowman.v() + 1.2, (double)_snowman.w() + _snowman.nextDouble(), 0.0, 0.0, 0.0);
            }
            break;
         case 1502:
            this.n.a(_snowman, adq.mw, adr.e, 0.5F, 2.6F + (_snowman.nextFloat() - _snowman.nextFloat()) * 0.8F, false);

            for (int _snowmanxx = 0; _snowmanxx < 5; _snowmanxx++) {
               double _snowmanxxx = (double)_snowman.u() + _snowman.nextDouble() * 0.6 + 0.2;
               double _snowmanxxxx = (double)_snowman.v() + _snowman.nextDouble() * 0.6 + 0.2;
               double _snowmanxxxxx = (double)_snowman.w() + _snowman.nextDouble() * 0.6 + 0.2;
               this.n.a(hh.S, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
            break;
         case 1503:
            this.n.a(_snowman, adq.dK, adr.e, 1.0F, 1.0F, false);

            for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
               double _snowmanxxx = (double)_snowman.u() + (5.0 + _snowman.nextDouble() * 6.0) / 16.0;
               double _snowmanxxxx = (double)_snowman.v() + 0.8125;
               double _snowmanxxxxx = (double)_snowman.w() + (5.0 + _snowman.nextDouble() * 6.0) / 16.0;
               this.n.a(hh.S, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
            break;
         case 2000:
            gc _snowmanxx = gc.a(_snowman);
            int _snowmanxxx = _snowmanxx.i();
            int _snowmanxxxx = _snowmanxx.j();
            int _snowmanxxxxx = _snowmanxx.k();
            double _snowmanxxxxxx = (double)_snowman.u() + (double)_snowmanxxx * 0.6 + 0.5;
            double _snowmanxxxxxxxx = (double)_snowman.v() + (double)_snowmanxxxx * 0.6 + 0.5;
            double _snowmanxxxxxxxxx = (double)_snowman.w() + (double)_snowmanxxxxx * 0.6 + 0.5;

            for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 10; _snowmanxxxxxxxxxx++) {
               double _snowmanxxxxxxxxxxx = _snowman.nextDouble() * 0.2 + 0.01;
               double _snowmanxxxxxxxxxxxx = _snowmanxxxxxx + (double)_snowmanxxx * 0.01 + (_snowman.nextDouble() - 0.5) * (double)_snowmanxxxxx * 0.5;
               double _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxx + (double)_snowmanxxxx * 0.01 + (_snowman.nextDouble() - 0.5) * (double)_snowmanxxxx * 0.5;
               double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxx + (double)_snowmanxxxxx * 0.01 + (_snowman.nextDouble() - 0.5) * (double)_snowmanxxx * 0.5;
               double _snowmanxxxxxxxxxxxxxxx = (double)_snowmanxxx * _snowmanxxxxxxxxxxx + _snowman.nextGaussian() * 0.01;
               double _snowmanxxxxxxxxxxxxxxxx = (double)_snowmanxxxx * _snowmanxxxxxxxxxxx + _snowman.nextGaussian() * 0.01;
               double _snowmanxxxxxxxxxxxxxxxxx = (double)_snowmanxxxxx * _snowmanxxxxxxxxxxx + _snowman.nextGaussian() * 0.01;
               this.a(hh.S, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxx);
            }
            break;
         case 2001:
            ceh _snowmanx = buo.a(_snowman);
            if (!_snowmanx.g()) {
               cae _snowmanxx = _snowmanx.o();
               this.n.a(_snowman, _snowmanxx.c(), adr.e, (_snowmanxx.a() + 1.0F) / 2.0F, _snowmanxx.b() * 0.8F, false);
            }

            this.j.f.a(_snowman, _snowmanx);
            break;
         case 2002:
         case 2007:
            dcn _snowmanxx = dcn.c(_snowman);

            for (int _snowmanxxx = 0; _snowmanxxx < 8; _snowmanxxx++) {
               this.a(new he(hh.I, new bmb(bmd.qj)), _snowmanxx.b, _snowmanxx.c, _snowmanxx.d, _snowman.nextGaussian() * 0.15, _snowman.nextDouble() * 0.2, _snowman.nextGaussian() * 0.15);
            }

            float _snowmanxxx = (float)(_snowman >> 16 & 0xFF) / 255.0F;
            float _snowmanxxxx = (float)(_snowman >> 8 & 0xFF) / 255.0F;
            float _snowmanxxxxx = (float)(_snowman >> 0 & 0xFF) / 255.0F;
            hf _snowmanxxxxxx = _snowman == 2007 ? hh.H : hh.p;

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 100; _snowmanxxxxxxx++) {
               double _snowmanxxxxxxxx = _snowman.nextDouble() * 4.0;
               double _snowmanxxxxxxxxx = _snowman.nextDouble() * Math.PI * 2.0;
               double _snowmanxxxxxxxxxx = Math.cos(_snowmanxxxxxxxxx) * _snowmanxxxxxxxx;
               double _snowmanxxxxxxxxxxx = 0.01 + _snowman.nextDouble() * 0.5;
               double _snowmanxxxxxxxxxxxx = Math.sin(_snowmanxxxxxxxxx) * _snowmanxxxxxxxx;
               dyg _snowmanxxxxxxxxxxxxx = this.b(
                  _snowmanxxxxxx, _snowmanxxxxxx.b().c(), _snowmanxx.b + _snowmanxxxxxxxxxx * 0.1, _snowmanxx.c + 0.3, _snowmanxx.d + _snowmanxxxxxxxxxxxx * 0.1, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx
               );
               if (_snowmanxxxxxxxxxxxxx != null) {
                  float _snowmanxxxxxxxxxxxxxx = 0.75F + _snowman.nextFloat() * 0.25F;
                  _snowmanxxxxxxxxxxxxx.a(_snowmanxxx * _snowmanxxxxxxxxxxxxxx, _snowmanxxxx * _snowmanxxxxxxxxxxxxxx, _snowmanxxxxx * _snowmanxxxxxxxxxxxxxx);
                  _snowmanxxxxxxxxxxxxx.c((float)_snowmanxxxxxxxx);
               }
            }

            this.n.a(_snowman, adq.oE, adr.g, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 2003:
            double _snowmanxx = (double)_snowman.u() + 0.5;
            double _snowmanxxx = (double)_snowman.v();
            double _snowmanxxxx = (double)_snowman.w() + 0.5;

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < 8; _snowmanxxxxx++) {
               this.a(new he(hh.I, new bmb(bmd.nD)), _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowman.nextGaussian() * 0.15, _snowman.nextDouble() * 0.2, _snowman.nextGaussian() * 0.15);
            }

            for (double _snowmanxxxxx = 0.0; _snowmanxxxxx < Math.PI * 2; _snowmanxxxxx += Math.PI / 20) {
               this.a(hh.Q, _snowmanxx + Math.cos(_snowmanxxxxx) * 5.0, _snowmanxxx - 0.4, _snowmanxxxx + Math.sin(_snowmanxxxxx) * 5.0, Math.cos(_snowmanxxxxx) * -5.0, 0.0, Math.sin(_snowmanxxxxx) * -5.0);
               this.a(hh.Q, _snowmanxx + Math.cos(_snowmanxxxxx) * 5.0, _snowmanxxx - 0.4, _snowmanxxxx + Math.sin(_snowmanxxxxx) * 5.0, Math.cos(_snowmanxxxxx) * -7.0, 0.0, Math.sin(_snowmanxxxxx) * -7.0);
            }
            break;
         case 2004:
            for (int _snowmanxx = 0; _snowmanxx < 20; _snowmanxx++) {
               double _snowmanxxx = (double)_snowman.u() + 0.5 + (_snowman.nextDouble() - 0.5) * 2.0;
               double _snowmanxxxx = (double)_snowman.v() + 0.5 + (_snowman.nextDouble() - 0.5) * 2.0;
               double _snowmanxxxxx = (double)_snowman.w() + 0.5 + (_snowman.nextDouble() - 0.5) * 2.0;
               this.n.a(hh.S, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
               this.n.a(hh.A, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 0.0, 0.0, 0.0);
            }
            break;
         case 2005:
            bkj.a(this.n, _snowman, _snowman);
            break;
         case 2006:
            for (int _snowmanx = 0; _snowmanx < 200; _snowmanx++) {
               float _snowmanxx = _snowman.nextFloat() * 4.0F;
               float _snowmanxxx = _snowman.nextFloat() * (float) (Math.PI * 2);
               double _snowmanxxxx = (double)(afm.b(_snowmanxxx) * _snowmanxx);
               double _snowmanxxxxx = 0.01 + _snowman.nextDouble() * 0.5;
               double _snowmanxxxxxx = (double)(afm.a(_snowmanxxx) * _snowmanxx);
               dyg _snowmanxxxxxxx = this.b(hh.i, false, (double)_snowman.u() + _snowmanxxxx * 0.1, (double)_snowman.v() + 0.3, (double)_snowman.w() + _snowmanxxxxxx * 0.1, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
               if (_snowmanxxxxxxx != null) {
                  _snowmanxxxxxxx.c(_snowmanxx);
               }
            }

            if (_snowman == 1) {
               this.n.a(_snowman, adq.dr, adr.f, 1.0F, _snowman.nextFloat() * 0.1F + 0.9F, false);
            }
            break;
         case 2008:
            this.n.a(hh.w, (double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, 0.0, 0.0, 0.0);
            break;
         case 2009:
            for (int _snowmanxx = 0; _snowmanxx < 8; _snowmanxx++) {
               this.n.a(hh.f, (double)_snowman.u() + _snowman.nextDouble(), (double)_snowman.v() + 1.2, (double)_snowman.w() + _snowman.nextDouble(), 0.0, 0.0, 0.0);
            }
            break;
         case 3000:
            this.n.a(hh.v, true, (double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5, 0.0, 0.0, 0.0);
            this.n.a(_snowman, adq.dJ, adr.e, 10.0F, (1.0F + (this.n.t.nextFloat() - this.n.t.nextFloat()) * 0.2F) * 0.7F, false);
            break;
         case 3001:
            this.n.a(_snowman, adq.dt, adr.f, 64.0F, 0.8F + this.n.t.nextFloat() * 0.3F, false);
      }
   }

   public void b(int var1, fx var2, int var3) {
      if (_snowman >= 0 && _snowman < 10) {
         zq _snowman = (zq)this.A.get(_snowman);
         if (_snowman != null) {
            this.a(_snowman);
         }

         if (_snowman == null || _snowman.b().u() != _snowman.u() || _snowman.b().v() != _snowman.v() || _snowman.b().w() != _snowman.w()) {
            _snowman = new zq(_snowman, _snowman);
            this.A.put(_snowman, _snowman);
         }

         _snowman.a(_snowman);
         _snowman.b(this.z);
         ((SortedSet)this.B.computeIfAbsent(_snowman.b().a(), var0 -> Sets.newTreeSet())).add(_snowman);
      } else {
         zq _snowmanx = (zq)this.A.remove(_snowman);
         if (_snowmanx != null) {
            this.a(_snowmanx);
         }
      }
   }

   public boolean n() {
      return this.o.isEmpty() && this.ab.f();
   }

   public void o() {
      this.an = true;
      this.w = true;
   }

   public void a(Collection<ccj> var1, Collection<ccj> var2) {
      synchronized (this.q) {
         this.q.removeAll(_snowman);
         this.q.addAll(_snowman);
      }
   }

   public static int a(bra var0, fx var1) {
      return a(_snowman, _snowman.d_(_snowman), _snowman);
   }

   public static int a(bra var0, ceh var1, fx var2) {
      if (_snowman.e(_snowman, _snowman)) {
         return 15728880;
      } else {
         int _snowman = _snowman.a(bsf.a, _snowman);
         int _snowmanx = _snowman.a(bsf.b, _snowman);
         int _snowmanxx = _snowman.f();
         if (_snowmanx < _snowmanxx) {
            _snowmanx = _snowmanxx;
         }

         return _snowman << 20 | _snowmanx << 4;
      }
   }

   @Nullable
   public deg p() {
      return this.D;
   }

   @Nullable
   public deg q() {
      return this.F;
   }

   @Nullable
   public deg r() {
      return this.G;
   }

   @Nullable
   public deg s() {
      return this.H;
   }

   @Nullable
   public deg t() {
      return this.I;
   }

   @Nullable
   public deg u() {
      return this.J;
   }

   class a {
      private final ecu.c b;
      private final gc c;
      private byte d;
      private final int e;

      private a(ecu.c var2, gc var3, @Nullable int var4) {
         this.b = _snowman;
         this.c = _snowman;
         this.e = _snowman;
      }

      public void a(byte var1, gc var2) {
         this.d = (byte)(this.d | _snowman | 1 << _snowman.ordinal());
      }

      public boolean a(gc var1) {
         return (this.d & 1 << _snowman.ordinal()) > 0;
      }
   }

   public static class b extends RuntimeException {
      public b(String var1, Throwable var2) {
         super(_snowman, _snowman);
      }
   }
}
