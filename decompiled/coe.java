import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class coe {
   private static final Logger a = LogManager.getLogger();

   public static void a(gn var0, cmc var1, coe.a var2, cfy var3, csw var4, fx var5, List<? super cro> var6, Random var7, boolean var8, boolean var9) {
      cla.g();
      gs<cok> _snowman = _snowman.b(gm.ax);
      bzm _snowmanx = bzm.a(_snowman);
      cok _snowmanxx = _snowman.c().get();
      coi _snowmanxxx = _snowmanxx.a(_snowman);
      cro _snowmanxxxx = _snowman.create(_snowman, _snowmanxxx, _snowman, _snowmanxxx.f(), _snowmanx, _snowmanxxx.a(_snowman, _snowman, _snowmanx));
      cra _snowmanxxxxx = _snowmanxxxx.g();
      int _snowmanxxxxxx = (_snowmanxxxxx.d + _snowmanxxxxx.a) / 2;
      int _snowmanxxxxxxx = (_snowmanxxxxx.f + _snowmanxxxxx.c) / 2;
      int _snowmanxxxxxxxx;
      if (_snowman) {
         _snowmanxxxxxxxx = _snowman.v() + _snowman.b(_snowmanxxxxxx, _snowmanxxxxxxx, chn.a.a);
      } else {
         _snowmanxxxxxxxx = _snowman.v();
      }

      int _snowmanxxxxxxxxx = _snowmanxxxxx.b + _snowmanxxxx.d();
      _snowmanxxxx.a(0, _snowmanxxxxxxxx - _snowmanxxxxxxxxx, 0);
      _snowman.add(_snowmanxxxx);
      if (_snowman.b() > 0) {
         int _snowmanxxxxxxxxxx = 80;
         dci _snowmanxxxxxxxxxxx = new dci(
            (double)(_snowmanxxxxxx - 80),
            (double)(_snowmanxxxxxxxx - 80),
            (double)(_snowmanxxxxxxx - 80),
            (double)(_snowmanxxxxxx + 80 + 1),
            (double)(_snowmanxxxxxxxx + 80 + 1),
            (double)(_snowmanxxxxxxx + 80 + 1)
         );
         coe.c _snowmanxxxxxxxxxxxx = new coe.c(_snowman, _snowman.b(), _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowmanxxxxxxxxxxxx.h.addLast(new coe.b(_snowmanxxxx, new MutableObject(dde.a(dde.a(_snowmanxxxxxxxxxxx), dde.a(dci.a(_snowmanxxxxx)), dcr.e)), _snowmanxxxxxxxx + 80, 0));

         while (!_snowmanxxxxxxxxxxxx.h.isEmpty()) {
            coe.b _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.h.removeFirst();
            _snowmanxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxx.a, _snowmanxxxxxxxxxxxxx.b, _snowmanxxxxxxxxxxxxx.c, _snowmanxxxxxxxxxxxxx.d, _snowman);
         }
      }
   }

   public static void a(gn var0, cro var1, int var2, coe.a var3, cfy var4, csw var5, List<? super cro> var6, Random var7) {
      gs<cok> _snowman = _snowman.b(gm.ax);
      coe.c _snowmanx = new coe.c(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowmanx.h.addLast(new coe.b(_snowman, new MutableObject(dde.a), 0, 0));

      while (!_snowmanx.h.isEmpty()) {
         coe.b _snowmanxx = _snowmanx.h.removeFirst();
         _snowmanx.a(_snowmanxx.a, _snowmanxx.b, _snowmanxx.c, _snowmanxx.d, false);
      }
   }

   public interface a {
      cro create(csw var1, coi var2, fx var3, int var4, bzm var5, cra var6);
   }

   static final class b {
      private final cro a;
      private final MutableObject<ddh> b;
      private final int c;
      private final int d;

      private b(cro var1, MutableObject<ddh> var2, int var3, int var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }
   }

   static final class c {
      private final gm<cok> a;
      private final int b;
      private final coe.a c;
      private final cfy d;
      private final csw e;
      private final List<? super cro> f;
      private final Random g;
      private final Deque<coe.b> h = Queues.newArrayDeque();

      private c(gm<cok> var1, int var2, coe.a var3, cfy var4, csw var5, List<? super cro> var6, Random var7) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
      }

      private void a(cro var1, MutableObject<ddh> var2, int var3, int var4, boolean var5) {
         coi _snowman = _snowman.b();
         fx _snowmanx = _snowman.c();
         bzm _snowmanxx = _snowman.ap_();
         cok.a _snowmanxxx = _snowman.e();
         boolean _snowmanxxxx = _snowmanxxx == cok.a.b;
         MutableObject<ddh> _snowmanxxxxx = new MutableObject();
         cra _snowmanxxxxxx = _snowman.g();
         int _snowmanxxxxxxx = _snowmanxxxxxx.b;

         label137:
         for (ctb.c _snowmanxxxxxxxx : _snowman.a(this.e, _snowmanx, _snowmanxx, this.g)) {
            gc _snowmanxxxxxxxxx = bxr.h(_snowmanxxxxxxxx.b);
            fx _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.a;
            fx _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx.a(_snowmanxxxxxxxxx);
            int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxx.v() - _snowmanxxxxxxx;
            int _snowmanxxxxxxxxxxxxx = -1;
            vk _snowmanxxxxxxxxxxxxxx = new vk(_snowmanxxxxxxxx.c.l("pool"));
            Optional<cok> _snowmanxxxxxxxxxxxxxxx = this.a.b(_snowmanxxxxxxxxxxxxxx);
            if (_snowmanxxxxxxxxxxxxxxx.isPresent() && (_snowmanxxxxxxxxxxxxxxx.get().c() != 0 || Objects.equals(_snowmanxxxxxxxxxxxxxx, kk.a.a()))) {
               vk _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.get().a();
               Optional<cok> _snowmanxxxxxxxxxxxxxxxxx = this.a.b(_snowmanxxxxxxxxxxxxxxxx);
               if (_snowmanxxxxxxxxxxxxxxxxx.isPresent() && (_snowmanxxxxxxxxxxxxxxxxx.get().c() != 0 || Objects.equals(_snowmanxxxxxxxxxxxxxxxx, kk.a.a()))) {
                  boolean _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxx.b(_snowmanxxxxxxxxxxx);
                  MutableObject<ddh> _snowmanxxxxxxxxxxxxxxxxxxx;
                  int _snowmanxxxxxxxxxxxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxxxxxxxxx) {
                     _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxx;
                     _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx;
                     if (_snowmanxxxxx.getValue() == null) {
                        _snowmanxxxxx.setValue(dde.a(dci.a(_snowmanxxxxxx)));
                     }
                  } else {
                     _snowmanxxxxxxxxxxxxxxxxxxx = _snowman;
                     _snowmanxxxxxxxxxxxxxxxxxxxx = _snowman;
                  }

                  List<coi> _snowmanxxxxxxxxxxxxxxxxxxxxx = Lists.newArrayList();
                  if (_snowman != this.b) {
                     _snowmanxxxxxxxxxxxxxxxxxxxxx.addAll(_snowmanxxxxxxxxxxxxxxx.get().b(this.g));
                  }

                  _snowmanxxxxxxxxxxxxxxxxxxxxx.addAll(_snowmanxxxxxxxxxxxxxxxxx.get().b(this.g));

                  for (coi _snowmanxxxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxxxxxx) {
                     if (_snowmanxxxxxxxxxxxxxxxxxxxxxx == cob.b) {
                        break;
                     }

                     for (bzm _snowmanxxxxxxxxxxxxxxxxxxxxxxx : bzm.b(this.g)) {
                        List<ctb.c> _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.a(this.e, fx.b, _snowmanxxxxxxxxxxxxxxxxxxxxxxx, this.g);
                        cra _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.a(this.e, fx.b, _snowmanxxxxxxxxxxxxxxxxxxxxxxx);
                        int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx;
                        if (_snowman && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx.e() <= 16) {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.stream().mapToInt(var2x -> {
                              if (!_snowman.b(var2x.a.a(bxr.h(var2x.b)))) {
                                 return 0;
                              } else {
                                 vk _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = new vk(var2x.c.l("pool"));
                                 Optional<cok> _snowmanx = this.a.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                                 Optional<cok> _snowmanxx = _snowmanx.flatMap(var1x -> this.a.b(var1x.a()));
                                 int _snowmanxxx = _snowmanx.<Integer>map(var1x -> var1x.a(this.e)).orElse(0);
                                 int _snowmanxxxx = _snowmanxx.<Integer>map(var1x -> var1x.a(this.e)).orElse(0);
                                 return Math.max(_snowmanxxx, _snowmanxxxx);
                              }
                           }).max().orElse(0);
                        } else {
                           _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = 0;
                        }

                        for (ctb.c _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxxxxxxxxxx) {
                           if (bxr.a(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx)) {
                              fx _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx.a;
                              fx _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = new fx(
                                 _snowmanxxxxxxxxxxx.u() - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.u(),
                                 _snowmanxxxxxxxxxxx.v() - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.v(),
                                 _snowmanxxxxxxxxxxx.w() - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.w()
                              );
                              cra _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.a(this.e, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxxx);
                              int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b;
                              cok.a _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.e();
                              boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx == cok.a.b;
                              int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx.v();
                              int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + bxr.h(_snowmanxxxxxxxx.b).j();
                              int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                              if (_snowmanxxxx && _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                              } else {
                                 if (_snowmanxxxxxxxxxxxxx == -1) {
                                    _snowmanxxxxxxxxxxxxx = this.d.b(_snowmanxxxxxxxxxx.u(), _snowmanxxxxxxxxxx.w(), chn.a.a);
                                 }

                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                              }

                              int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                              cra _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b(0, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 0);
                              fx _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b(0, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, 0);
                              if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx > 0) {
                                 int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = Math.max(
                                    _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx + 1, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.e - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b
                                 );
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.e = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.b
                                    + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                              }

                              if (!dde.c((ddh)_snowmanxxxxxxxxxxxxxxxxxxx.getValue(), dde.a(dci.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx).h(0.25)), dcr.c)) {
                                 _snowmanxxxxxxxxxxxxxxxxxxx.setValue(
                                    dde.b((ddh)_snowmanxxxxxxxxxxxxxxxxxxx.getValue(), dde.a(dci.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)), dcr.e)
                                 );
                                 int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.d();
                                 int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                                 if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                                    _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                       - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                                 } else {
                                    _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.f();
                                 }

                                 cro _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = this.c
                                    .create(
                                       this.e,
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                    );
                                 int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                                 if (_snowmanxxxx) {
                                    _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxx + _snowmanxxxxxxxxxxxx;
                                 } else if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx) {
                                    _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx;
                                 } else {
                                    if (_snowmanxxxxxxxxxxxxx == -1) {
                                       _snowmanxxxxxxxxxxxxx = this.d.b(_snowmanxxxxxxxxxx.u(), _snowmanxxxxxxxxxx.w(), chn.a.a);
                                    }

                                    _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx / 2;
                                 }

                                 _snowman.a(
                                    new cod(
                                       _snowmanxxxxxxxxxxx.u(),
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxxxxxxxxxx.w(),
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                    )
                                 );
                                 _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx.a(
                                    new cod(
                                       _snowmanxxxxxxxxxx.u(),
                                       _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                          - _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                                          + _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxxxxxxxxx.w(),
                                       -_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                                       _snowmanxxx
                                    )
                                 );
                                 this.f.add(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx);
                                 if (_snowman + 1 <= this.b) {
                                    this.h.addLast(new coe.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowman + 1));
                                 }
                                 continue label137;
                              }
                           }
                        }
                     }
                  }
               } else {
                  coe.a.warn("Empty or none existent fallback pool: {}", _snowmanxxxxxxxxxxxxxxxx);
               }
            } else {
               coe.a.warn("Empty or none existent pool: {}", _snowmanxxxxxxxxxxxxxx);
            }
         }
      }
   }
}
