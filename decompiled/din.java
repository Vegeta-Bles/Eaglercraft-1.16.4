import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class din extends eoo {
   private static final Logger a = LogManager.getLogger();
   private static final ReentrantLock b = new ReentrantLock();
   private static final String[] c = new String[]{"", ".", ". .", ". . ."};
   private static final nr p = new of("mco.upload.verifying");
   private final dif q;
   private final cyh r;
   private final long s;
   private final int t;
   private final dgf u;
   private final RateLimiter v;
   private volatile nr[] w;
   private volatile nr x = new of("mco.upload.preparing");
   private volatile String y;
   private volatile boolean z;
   private volatile boolean A;
   private volatile boolean B = true;
   private volatile boolean C;
   private dlj D;
   private dlj E;
   private int F;
   private Long G;
   private Long H;
   private long I;
   private final Runnable J;

   public din(long var1, int var3, dif var4, cyh var5, Runnable var6) {
      this.s = _snowman;
      this.t = _snowman;
      this.q = _snowman;
      this.r = _snowman;
      this.u = new dgf();
      this.v = RateLimiter.create(0.1F);
      this.J = _snowman;
   }

   @Override
   public void b() {
      this.i.m.a(true);
      this.D = this.a((dlj)(new dlj(this.k / 2 - 100, this.l - 42, 200, 20, nq.h, var1 -> this.h())));
      this.D.p = false;
      this.E = this.a((dlj)(new dlj(this.k / 2 - 100, this.l - 42, 200, 20, nq.d, var1 -> this.i())));
      if (!this.C) {
         if (this.q.a == -1) {
            this.k();
         } else {
            this.q.a(() -> {
               if (!this.C) {
                  this.C = true;
                  this.i.a(this);
                  this.k();
               }
            });
         }
      }
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   private void h() {
      this.J.run();
   }

   private void i() {
      this.z = true;
      this.i.a(this.q);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      if (_snowman == 256) {
         if (this.B) {
            this.i();
         } else {
            this.h();
         }

         return true;
      } else {
         return super.a(_snowman, _snowman, _snowman);
      }
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      if (!this.A && this.u.a != 0L && this.u.a == this.u.b) {
         this.x = p;
         this.E.o = false;
      }

      a(_snowman, this.o, this.x, this.k / 2, 50, 16777215);
      if (this.B) {
         this.b(_snowman);
      }

      if (this.u.a != 0L && !this.z) {
         this.c(_snowman);
         this.d(_snowman);
      }

      if (this.w != null) {
         for (int _snowman = 0; _snowman < this.w.length; _snowman++) {
            a(_snowman, this.o, this.w[_snowman], this.k / 2, 110 + 12 * _snowman, 16711680);
         }
      }

      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   private void b(dfm var1) {
      int _snowman = this.o.a(this.x);
      this.o.b(_snowman, c[this.F / 10 % c.length], (float)(this.k / 2 + _snowman / 2 + 5), 50.0F, 16777215);
   }

   private void c(dfm var1) {
      double _snowman = Math.min((double)this.u.a / (double)this.u.b, 1.0);
      this.y = String.format(Locale.ROOT, "%.1f", _snowman * 100.0);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableTexture();
      double _snowmanx = (double)(this.k / 2 - 100);
      double _snowmanxx = 0.5;
      dfo _snowmanxxx = dfo.a();
      dfh _snowmanxxxx = _snowmanxxx.c();
      _snowmanxxxx.a(7, dfk.l);
      _snowmanxxxx.a(_snowmanx - 0.5, 95.5, 0.0).a(217, 210, 210, 255).d();
      _snowmanxxxx.a(_snowmanx + 200.0 * _snowman + 0.5, 95.5, 0.0).a(217, 210, 210, 255).d();
      _snowmanxxxx.a(_snowmanx + 200.0 * _snowman + 0.5, 79.5, 0.0).a(217, 210, 210, 255).d();
      _snowmanxxxx.a(_snowmanx - 0.5, 79.5, 0.0).a(217, 210, 210, 255).d();
      _snowmanxxxx.a(_snowmanx, 95.0, 0.0).a(128, 128, 128, 255).d();
      _snowmanxxxx.a(_snowmanx + 200.0 * _snowman, 95.0, 0.0).a(128, 128, 128, 255).d();
      _snowmanxxxx.a(_snowmanx + 200.0 * _snowman, 80.0, 0.0).a(128, 128, 128, 255).d();
      _snowmanxxxx.a(_snowmanx, 80.0, 0.0).a(128, 128, 128, 255).d();
      _snowmanxxx.b();
      RenderSystem.enableTexture();
      a(_snowman, this.o, this.y + " %", this.k / 2, 84, 16777215);
   }

   private void d(dfm var1) {
      if (this.F % 20 == 0) {
         if (this.G != null) {
            long _snowman = x.b() - this.H;
            if (_snowman == 0L) {
               _snowman = 1L;
            }

            this.I = 1000L * (this.u.a - this.G) / _snowman;
            this.a(_snowman, this.I);
         }

         this.G = this.u.a;
         this.H = x.b();
      } else {
         this.a(_snowman, this.I);
      }
   }

   private void a(dfm var1, long var2) {
      if (_snowman > 0L) {
         int _snowman = this.o.b(this.y);
         String _snowmanx = "(" + dfx.b(_snowman) + "/s)";
         this.o.b(_snowman, _snowmanx, (float)(this.k / 2 + _snowman / 2 + 15), 84.0F, 16777215);
      }
   }

   @Override
   public void d() {
      super.d();
      this.F++;
      if (this.x != null && this.v.tryAcquire(1)) {
         List<String> _snowman = Lists.newArrayList();
         _snowman.add(this.x.getString());
         if (this.y != null) {
            _snowman.add(this.y + "%");
         }

         if (this.w != null) {
            Stream.of(this.w).map(nr::getString).forEach(_snowman::add);
         }

         eoj.a(String.join(System.lineSeparator(), _snowman));
      }
   }

   private void k() {
      this.C = true;
      new Thread(
            () -> {
               File _snowman = null;
               dgb _snowmanx = dgb.a();
               long _snowmanxx = this.s;

               try {
                  if (!b.tryLock(1L, TimeUnit.SECONDS)) {
                     this.x = new of("mco.upload.close.failure");
                  } else {
                     dhb _snowmanxxx = null;

                     for (int _snowmanxxxx = 0; _snowmanxxxx < 20; _snowmanxxxx++) {
                        try {
                           if (this.z) {
                              this.l();
                              return;
                           }

                           _snowmanxxx = _snowmanx.h(_snowmanxx, div.a(_snowmanxx));
                           if (_snowmanxxx != null) {
                              break;
                           }
                        } catch (dhj var20) {
                           Thread.sleep((long)(var20.e * 1000));
                        }
                     }

                     if (_snowmanxxx == null) {
                        this.x = new of("mco.upload.close.failure");
                     } else {
                        div.a(_snowmanxx, _snowmanxxx.a());
                        if (!_snowmanxxx.c()) {
                           this.x = new of("mco.upload.close.failure");
                        } else if (this.z) {
                           this.l();
                        } else {
                           File _snowmanxxxx = new File(this.i.n.getAbsolutePath(), "saves");
                           _snowman = this.b(new File(_snowmanxxxx, this.r.a()));
                           if (this.z) {
                              this.l();
                           } else if (this.a(_snowman)) {
                              this.x = new of("mco.upload.uploading", this.r.b());
                              dfz _snowmanxxxxx = new dfz(_snowman, this.s, this.t, _snowmanxxx, this.i.J(), w.a().getName(), this.u);
                              _snowmanxxxxx.a(var3x -> {
                                 if (var3x.a >= 200 && var3x.a < 300) {
                                    this.A = true;
                                    this.x = new of("mco.upload.done");
                                    this.D.a(nq.c);
                                    div.b(_snowman);
                                 } else if (var3x.a == 400 && var3x.b != null) {
                                    this.a(new of("mco.upload.failed", var3x.b));
                                 } else {
                                    this.a(new of("mco.upload.failed", var3x.a));
                                 }
                              });

                              while (!_snowmanxxxxx.b()) {
                                 if (this.z) {
                                    _snowmanxxxxx.a();
                                    this.l();
                                    return;
                                 }

                                 try {
                                    Thread.sleep(500L);
                                 } catch (InterruptedException var19) {
                                    a.error("Failed to check Realms file upload status");
                                 }
                              }
                           } else {
                              long _snowmanxxxxx = _snowman.length();
                              dfx _snowmanxxxxxx = dfx.a(_snowmanxxxxx);
                              dfx _snowmanxxxxxxx = dfx.a(5368709120L);
                              if (dfx.b(_snowmanxxxxx, _snowmanxxxxxx).equals(dfx.b(5368709120L, _snowmanxxxxxxx)) && _snowmanxxxxxx != dfx.a) {
                                 dfx _snowmanxxxxxxxx = dfx.values()[_snowmanxxxxxx.ordinal() - 1];
                                 this.a(
                                    new of("mco.upload.size.failure.line1", this.r.b()),
                                    new of("mco.upload.size.failure.line2", dfx.b(_snowmanxxxxx, _snowmanxxxxxxxx), dfx.b(5368709120L, _snowmanxxxxxxxx))
                                 );
                              } else {
                                 this.a(
                                    new of("mco.upload.size.failure.line1", this.r.b()),
                                    new of("mco.upload.size.failure.line2", dfx.b(_snowmanxxxxx, _snowmanxxxxxx), dfx.b(5368709120L, _snowmanxxxxxxx))
                                 );
                              }
                           }
                        }
                     }
                  }
               } catch (IOException var21) {
                  this.a(new of("mco.upload.failed", var21.getMessage()));
               } catch (dhi var22) {
                  this.a(new of("mco.upload.failed", var22.toString()));
               } catch (InterruptedException var23) {
                  a.error("Could not acquire upload lock");
               } finally {
                  this.A = true;
                  if (b.isHeldByCurrentThread()) {
                     b.unlock();
                     this.B = false;
                     this.D.p = true;
                     this.E.p = false;
                     if (_snowman != null) {
                        a.debug("Deleting file " + _snowman.getAbsolutePath());
                        _snowman.delete();
                     }
                  } else {
                     return;
                  }
               }
            }
         )
         .start();
   }

   private void a(nr... var1) {
      this.w = _snowman;
   }

   private void l() {
      this.x = new of("mco.upload.cancelled");
      a.debug("Upload was cancelled");
   }

   private boolean a(File var1) {
      return _snowman.length() < 5368709120L;
   }

   private File b(File var1) throws IOException {
      TarArchiveOutputStream _snowman = null;

      File var4;
      try {
         File _snowmanx = File.createTempFile("realms-upload-file", ".tar.gz");
         _snowman = new TarArchiveOutputStream(new GZIPOutputStream(new FileOutputStream(_snowmanx)));
         _snowman.setLongFileMode(3);
         this.a(_snowman, _snowman.getAbsolutePath(), "world", true);
         _snowman.finish();
         var4 = _snowmanx;
      } finally {
         if (_snowman != null) {
            _snowman.close();
         }
      }

      return var4;
   }

   private void a(TarArchiveOutputStream var1, String var2, String var3, boolean var4) throws IOException {
      if (!this.z) {
         File _snowman = new File(_snowman);
         String _snowmanx = _snowman ? _snowman : _snowman + _snowman.getName();
         TarArchiveEntry _snowmanxx = new TarArchiveEntry(_snowman, _snowmanx);
         _snowman.putArchiveEntry(_snowmanxx);
         if (_snowman.isFile()) {
            IOUtils.copy(new FileInputStream(_snowman), _snowman);
            _snowman.closeArchiveEntry();
         } else {
            _snowman.closeArchiveEntry();
            File[] _snowmanxxx = _snowman.listFiles();
            if (_snowmanxxx != null) {
               for (File _snowmanxxxx : _snowmanxxx) {
                  this.a(_snowman, _snowmanxxxx.getAbsolutePath(), _snowmanx + "/", false);
               }
            }
         }
      }
   }
}
