import java.util.Locale;
import javax.annotation.Nullable;

public class djx {
   private final djz a;
   private boolean b;
   private final deh c = new deh();
   private long d = -1L;
   private long e = -1L;
   private long f = -1L;
   private boolean g;

   public djx(djz var1) {
      this.a = _snowman;
   }

   private void a(String var1, Object... var2) {
      this.a.j.c().a(new oe("").a(new of("debug.prefix").a(new k[]{k.o, k.r})).c(" ").a(new of(_snowman, _snowman)));
   }

   private void b(String var1, Object... var2) {
      this.a.j.c().a(new oe("").a(new of("debug.prefix").a(new k[]{k.m, k.r})).c(" ").a(new of(_snowman, _snowman)));
   }

   private boolean b(int var1) {
      if (this.d > 0L && this.d < x.b() - 100L) {
         return true;
      } else {
         switch (_snowman) {
            case 65:
               this.a.e.e();
               this.a("debug.reload_chunks.message");
               return true;
            case 66: {
               boolean _snowman = !this.a.ac().a();
               this.a.ac().b(_snowman);
               this.a(_snowman ? "debug.show_hitboxes.on" : "debug.show_hitboxes.off");
               return true;
            }
            case 67:
               if (this.a.s.eO()) {
                  return false;
               } else {
                  dwu _snowmanx = this.a.s.e;
                  if (_snowmanx == null) {
                     return false;
                  }

                  this.a("debug.copy_location.message");
                  this.a(
                     String.format(
                        Locale.ROOT,
                        "/execute in %s run tp @s %.2f %.2f %.2f %.2f %.2f",
                        this.a.s.l.Y().a(),
                        this.a.s.cD(),
                        this.a.s.cE(),
                        this.a.s.cH(),
                        this.a.s.p,
                        this.a.s.q
                     )
                  );
                  return true;
               }
            case 68:
               if (this.a.j != null) {
                  this.a.j.c().a(false);
               }

               return true;
            case 70:
               dkc.q.a(this.a.k, afm.a((double)(this.a.k.b + (dot.y() ? -1 : 1)), dkc.q.c(), dkc.q.d()));
               this.a("debug.cycle_renderdistance.message", this.a.k.b);
               return true;
            case 71: {
               boolean _snowman = this.a.i.b();
               this.a(_snowman ? "debug.chunk_boundaries.on" : "debug.chunk_boundaries.off");
               return true;
            }
            case 72:
               this.a.k.p = !this.a.k.p;
               this.a(this.a.k.p ? "debug.advanced_tooltips.on" : "debug.advanced_tooltips.off");
               this.a.k.b();
               return true;
            case 73:
               if (!this.a.s.eO()) {
                  this.a(this.a.s.k(2), !dot.y());
               }

               return true;
            case 78:
               if (!this.a.s.k(2)) {
                  this.a("debug.creative_spectator.error");
               } else if (!this.a.s.a_()) {
                  this.a.s.f("/gamemode spectator");
               } else {
                  this.a.s.f("/gamemode " + this.a.q.k().b());
               }

               return true;
            case 80:
               this.a.k.q = !this.a.k.q;
               this.a.k.b();
               this.a(this.a.k.q ? "debug.pause_focus.on" : "debug.pause_focus.off");
               return true;
            case 81: {
               this.a("debug.help.message");
               dlk _snowman = this.a.j.c();
               _snowman.a(new of("debug.reload_chunks.help"));
               _snowman.a(new of("debug.show_hitboxes.help"));
               _snowman.a(new of("debug.copy_location.help"));
               _snowman.a(new of("debug.clear_chat.help"));
               _snowman.a(new of("debug.cycle_renderdistance.help"));
               _snowman.a(new of("debug.chunk_boundaries.help"));
               _snowman.a(new of("debug.advanced_tooltips.help"));
               _snowman.a(new of("debug.inspect.help"));
               _snowman.a(new of("debug.creative_spectator.help"));
               _snowman.a(new of("debug.pause_focus.help"));
               _snowman.a(new of("debug.help.help"));
               _snowman.a(new of("debug.reload_resourcepacks.help"));
               _snowman.a(new of("debug.pause.help"));
               _snowman.a(new of("debug.gamemodes.help"));
               return true;
            }
            case 84:
               this.a("debug.reload_resourcepacks.message");
               this.a.j();
               return true;
            case 293:
               if (!this.a.s.k(2)) {
                  this.a("debug.gamemodes.error");
               } else {
                  this.a.a(new dpn());
               }

               return true;
            default:
               return false;
         }
      }
   }

   private void a(boolean var1, boolean var2) {
      dcl _snowman = this.a.v;
      if (_snowman != null) {
         switch (_snowman.c()) {
            case b:
               fx _snowmanxxx = ((dcj)_snowman).a();
               ceh _snowmanxxxx = this.a.s.l.d_(_snowmanxxx);
               if (_snowman) {
                  if (_snowman) {
                     this.a.s.e.l().a(_snowmanxxx, var3x -> {
                        this.a(_snowman, _snowman, var3x);
                        this.a("debug.inspect.server.block");
                     });
                  } else {
                     ccj _snowmanxxxxx = this.a.s.l.c(_snowmanxxx);
                     md _snowmanxxxxxx = _snowmanxxxxx != null ? _snowmanxxxxx.a(new md()) : null;
                     this.a(_snowmanxxxx, _snowmanxxx, _snowmanxxxxxx);
                     this.a("debug.inspect.client.block");
                  }
               } else {
                  this.a(_snowmanxxxx, _snowmanxxx, null);
                  this.a("debug.inspect.client.block");
               }
               break;
            case c:
               aqa _snowmanx = ((dck)_snowman).a();
               vk _snowmanxx = gm.S.b(_snowmanx.X());
               if (_snowman) {
                  if (_snowman) {
                     this.a.s.e.l().a(_snowmanx.Y(), var3x -> {
                        this.a(_snowman, _snowman.cA(), var3x);
                        this.a("debug.inspect.server.entity");
                     });
                  } else {
                     md _snowmanxxx = _snowmanx.e(new md());
                     this.a(_snowmanxx, _snowmanx.cA(), _snowmanxxx);
                     this.a("debug.inspect.client.entity");
                  }
               } else {
                  this.a(_snowmanxx, _snowmanx.cA(), null);
                  this.a("debug.inspect.client.entity");
               }
         }
      }
   }

   private void a(ceh var1, fx var2, @Nullable md var3) {
      if (_snowman != null) {
         _snowman.r("x");
         _snowman.r("y");
         _snowman.r("z");
         _snowman.r("id");
      }

      StringBuilder _snowman = new StringBuilder(ei.a(_snowman));
      if (_snowman != null) {
         _snowman.append(_snowman);
      }

      String _snowmanx = String.format(Locale.ROOT, "/setblock %d %d %d %s", _snowman.u(), _snowman.v(), _snowman.w(), _snowman);
      this.a(_snowmanx);
   }

   private void a(vk var1, dcn var2, @Nullable md var3) {
      String _snowman;
      if (_snowman != null) {
         _snowman.r("UUID");
         _snowman.r("Pos");
         _snowman.r("Dimension");
         String _snowmanx = _snowman.l().getString();
         _snowman = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f %s", _snowman.toString(), _snowman.b, _snowman.c, _snowman.d, _snowmanx);
      } else {
         _snowman = String.format(Locale.ROOT, "/summon %s %.2f %.2f %.2f", _snowman.toString(), _snowman.b, _snowman.c, _snowman.d);
      }

      this.a(_snowman);
   }

   public void a(long var1, int var3, int var4, int var5, int var6) {
      if (_snowman == this.a.aD().i()) {
         if (this.d > 0L) {
            if (!deo.a(djz.C().aD().i(), 67) || !deo.a(djz.C().aD().i(), 292)) {
               this.d = -1L;
            }
         } else if (deo.a(djz.C().aD().i(), 67) && deo.a(djz.C().aD().i(), 292)) {
            this.g = true;
            this.d = x.b();
            this.e = x.b();
            this.f = 0L;
         }

         dmh _snowman = this.a.y;
         if (_snowman == 1 && (!(this.a.y instanceof dpl) || ((dpl)_snowman).p <= x.b() - 20L)) {
            if (this.a.k.az.a(_snowman, _snowman)) {
               this.a.aD().h();
               this.a.k.Z = this.a.aD().j();
               this.a.k.b();
               return;
            }

            if (this.a.k.aw.a(_snowman, _snowman)) {
               if (dot.x()) {
               }

               dkh.a(this.a.n, this.a.aD().k(), this.a.aD().l(), this.a.f(), var1x -> this.a.execute(() -> this.a.j.c().a(var1x)));
               return;
            }
         }

         boolean _snowmanx = _snowman == null || !(_snowman.aw_() instanceof dlq) || !((dlq)_snowman.aw_()).m();
         if (_snowman != 0 && _snowman == 66 && dot.x() && _snowmanx) {
            dkc.A.a(this.a.k, 1);
            if (_snowman instanceof dov) {
               ((dov)_snowman).h();
            }
         }

         if (_snowman != null) {
            boolean[] _snowmanxx = new boolean[]{false};
            dot.a(() -> {
               if (_snowman != 1 && (_snowman != 2 || !this.b)) {
                  if (_snowman == 0) {
                     _snowman[0] = _snowman.b(_snowman, _snowman, _snowman);
                  }
               } else {
                  _snowman[0] = _snowman.a(_snowman, _snowman, _snowman);
               }
            }, "keyPressed event handler", _snowman.getClass().getCanonicalName());
            if (_snowmanxx[0]) {
               return;
            }
         }

         if (this.a.y == null || this.a.y.n) {
            deo.a _snowmanxx = deo.a(_snowman, _snowman);
            if (_snowman == 0) {
               djw.a(_snowmanxx, false);
               if (_snowman == 292) {
                  if (this.g) {
                     this.g = false;
                  } else {
                     this.a.k.aJ = !this.a.k.aJ;
                     this.a.k.aK = this.a.k.aJ && dot.y();
                     this.a.k.aL = this.a.k.aJ && dot.z();
                  }
               }
            } else {
               if (_snowman == 293 && this.a.h != null) {
                  this.a.h.b();
               }

               boolean _snowmanxxx = false;
               if (this.a.y == null) {
                  if (_snowman == 256) {
                     boolean _snowmanxxxx = deo.a(djz.C().aD().i(), 292);
                     this.a.c(_snowmanxxxx);
                  }

                  _snowmanxxx = deo.a(djz.C().aD().i(), 292) && this.b(_snowman);
                  this.g |= _snowmanxxx;
                  if (_snowman == 290) {
                     this.a.k.aI = !this.a.k.aI;
                  }
               }

               if (_snowmanxxx) {
                  djw.a(_snowmanxx, false);
               } else {
                  djw.a(_snowmanxx, true);
                  djw.a(_snowmanxx);
               }

               if (this.a.k.aK && _snowman >= 48 && _snowman <= 57) {
                  this.a.a(_snowman - 48);
               }
            }
         }
      }
   }

   private void a(long var1, int var3, int var4) {
      if (_snowman == this.a.aD().i()) {
         dmi _snowman = this.a.y;
         if (_snowman != null && this.a.aA() == null) {
            if (Character.charCount(_snowman) == 1) {
               dot.a(() -> _snowman.a((char)_snowman, _snowman), "charTyped event handler", _snowman.getClass().getCanonicalName());
            } else {
               for (char _snowmanx : Character.toChars(_snowman)) {
                  dot.a(() -> _snowman.a(_snowman, _snowman), "charTyped event handler", _snowman.getClass().getCanonicalName());
               }
            }
         }
      }
   }

   public void a(boolean var1) {
      this.b = _snowman;
   }

   public void a(long var1) {
      deo.a(
         _snowman,
         (var1x, var3, var4, var5, var6) -> this.a.execute(() -> this.a(var1x, var3, var4, var5, var6)),
         (var1x, var3, var4) -> this.a.execute(() -> this.a(var1x, var3, var4))
      );
   }

   public String a() {
      return this.c.a(this.a.aD().i(), (var1, var2) -> {
         if (var1 != 65545) {
            this.a.aD().a(var1, var2);
         }
      });
   }

   public void a(String var1) {
      this.c.a(this.a.aD().i(), _snowman);
   }

   public void b() {
      if (this.d > 0L) {
         long _snowman = x.b();
         long _snowmanx = 10000L - (_snowman - this.d);
         long _snowmanxx = _snowman - this.e;
         if (_snowmanx < 0L) {
            if (dot.x()) {
               ddt.a();
            }

            throw new u(new l("Manually triggered debug crash", new Throwable()));
         }

         if (_snowmanxx >= 1000L) {
            if (this.f == 0L) {
               this.a("debug.crash.message");
            } else {
               this.b("debug.crash.warning", afm.f((float)_snowmanx / 1000.0F));
            }

            this.e = _snowman;
            this.f++;
         }
      }
   }
}
