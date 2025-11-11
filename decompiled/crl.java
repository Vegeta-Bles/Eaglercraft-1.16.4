import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class crl {
   static class a implements crl.i {
      private a() {
      }

      @Override
      public boolean a(crl.v var1) {
         return _snowman.c[gc.f.c()] && !_snowman.b[gc.f.c()].d;
      }

      @Override
      public crl.r a(gc var1, crl.v var2, Random var3) {
         _snowman.d = true;
         _snowman.b[gc.f.c()].d = true;
         return new crl.k(_snowman, _snowman);
      }
   }

   static class b implements crl.i {
      private b() {
      }

      @Override
      public boolean a(crl.v var1) {
         if (_snowman.c[gc.f.c()] && !_snowman.b[gc.f.c()].d && _snowman.c[gc.b.c()] && !_snowman.b[gc.b.c()].d) {
            crl.v _snowman = _snowman.b[gc.f.c()];
            return _snowman.c[gc.b.c()] && !_snowman.b[gc.b.c()].d;
         } else {
            return false;
         }
      }

      @Override
      public crl.r a(gc var1, crl.v var2, Random var3) {
         _snowman.d = true;
         _snowman.b[gc.f.c()].d = true;
         _snowman.b[gc.b.c()].d = true;
         _snowman.b[gc.f.c()].b[gc.b.c()].d = true;
         return new crl.l(_snowman, _snowman);
      }
   }

   static class c implements crl.i {
      private c() {
      }

      @Override
      public boolean a(crl.v var1) {
         return _snowman.c[gc.b.c()] && !_snowman.b[gc.b.c()].d;
      }

      @Override
      public crl.r a(gc var1, crl.v var2, Random var3) {
         _snowman.d = true;
         _snowman.b[gc.b.c()].d = true;
         return new crl.m(_snowman, _snowman);
      }
   }

   static class d implements crl.i {
      private d() {
      }

      @Override
      public boolean a(crl.v var1) {
         if (_snowman.c[gc.c.c()] && !_snowman.b[gc.c.c()].d && _snowman.c[gc.b.c()] && !_snowman.b[gc.b.c()].d) {
            crl.v _snowman = _snowman.b[gc.c.c()];
            return _snowman.c[gc.b.c()] && !_snowman.b[gc.b.c()].d;
         } else {
            return false;
         }
      }

      @Override
      public crl.r a(gc var1, crl.v var2, Random var3) {
         _snowman.d = true;
         _snowman.b[gc.c.c()].d = true;
         _snowman.b[gc.b.c()].d = true;
         _snowman.b[gc.c.c()].b[gc.b.c()].d = true;
         return new crl.n(_snowman, _snowman);
      }
   }

   static class e implements crl.i {
      private e() {
      }

      @Override
      public boolean a(crl.v var1) {
         return _snowman.c[gc.c.c()] && !_snowman.b[gc.c.c()].d;
      }

      @Override
      public crl.r a(gc var1, crl.v var2, Random var3) {
         crl.v _snowman = _snowman;
         if (!_snowman.c[gc.c.c()] || _snowman.b[gc.c.c()].d) {
            _snowman = _snowman.b[gc.d.c()];
         }

         _snowman.d = true;
         _snowman.b[gc.c.c()].d = true;
         return new crl.o(_snowman, _snowman);
      }
   }

   static class f implements crl.i {
      private f() {
      }

      @Override
      public boolean a(crl.v var1) {
         return true;
      }

      @Override
      public crl.r a(gc var1, crl.v var2, Random var3) {
         _snowman.d = true;
         return new crl.s(_snowman, _snowman, _snowman);
      }
   }

   static class g implements crl.i {
      private g() {
      }

      @Override
      public boolean a(crl.v var1) {
         return !_snowman.c[gc.e.c()] && !_snowman.c[gc.f.c()] && !_snowman.c[gc.c.c()] && !_snowman.c[gc.d.c()] && !_snowman.c[gc.b.c()];
      }

      @Override
      public crl.r a(gc var1, crl.v var2, Random var3) {
         _snowman.d = true;
         return new crl.t(_snowman, _snowman);
      }
   }

   public static class h extends crl.r {
      private crl.v p;
      private crl.v q;
      private final List<crl.r> r = Lists.newArrayList();

      public h(Random var1, int var2, int var3, gc var4) {
         super(clb.M, 0);
         this.a(_snowman);
         gc _snowman = this.i();
         if (_snowman.n() == gc.a.c) {
            this.n = new cra(_snowman, 39, _snowman, _snowman + 58 - 1, 61, _snowman + 58 - 1);
         } else {
            this.n = new cra(_snowman, 39, _snowman, _snowman + 58 - 1, 61, _snowman + 58 - 1);
         }

         List<crl.v> _snowmanx = this.a(_snowman);
         this.p.d = true;
         this.r.add(new crl.p(_snowman, this.p));
         this.r.add(new crl.j(_snowman, this.q));
         List<crl.i> _snowmanxx = Lists.newArrayList();
         _snowmanxx.add(new crl.b());
         _snowmanxx.add(new crl.d());
         _snowmanxx.add(new crl.e());
         _snowmanxx.add(new crl.a());
         _snowmanxx.add(new crl.c());
         _snowmanxx.add(new crl.g());
         _snowmanxx.add(new crl.f());

         for (crl.v _snowmanxxx : _snowmanx) {
            if (!_snowmanxxx.d && !_snowmanxxx.b()) {
               for (crl.i _snowmanxxxx : _snowmanxx) {
                  if (_snowmanxxxx.a(_snowmanxxx)) {
                     this.r.add(_snowmanxxxx.a(_snowman, _snowmanxxx, _snowman));
                     break;
                  }
               }
            }
         }

         int _snowmanxxxxx = this.n.b;
         int _snowmanxxxxxx = this.a(9, 22);
         int _snowmanxxxxxxx = this.b(9, 22);

         for (crl.r _snowmanxxxxxxxx : this.r) {
            _snowmanxxxxxxxx.g().a(_snowmanxxxxxx, _snowmanxxxxx, _snowmanxxxxxxx);
         }

         cra _snowmanxxxxxxxx = cra.a(this.a(1, 1), this.d(1), this.b(1, 1), this.a(23, 21), this.d(8), this.b(23, 21));
         cra _snowmanxxxxxxxxx = cra.a(this.a(34, 1), this.d(1), this.b(34, 1), this.a(56, 21), this.d(8), this.b(56, 21));
         cra _snowmanxxxxxxxxxx = cra.a(this.a(22, 22), this.d(13), this.b(22, 22), this.a(35, 35), this.d(17), this.b(35, 35));
         int _snowmanxxxxxxxxxxx = _snowman.nextInt();
         this.r.add(new crl.u(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx++));
         this.r.add(new crl.u(_snowman, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx++));
         this.r.add(new crl.q(_snowman, _snowmanxxxxxxxxxx));
      }

      public h(csw var1, md var2) {
         super(clb.M, _snowman);
      }

      private List<crl.v> a(Random var1) {
         crl.v[] _snowman = new crl.v[75];

         for (int _snowmanx = 0; _snowmanx < 5; _snowmanx++) {
            for (int _snowmanxx = 0; _snowmanxx < 4; _snowmanxx++) {
               int _snowmanxxx = 0;
               int _snowmanxxxx = b(_snowmanx, 0, _snowmanxx);
               _snowman[_snowmanxxxx] = new crl.v(_snowmanxxxx);
            }
         }

         for (int _snowmanx = 0; _snowmanx < 5; _snowmanx++) {
            for (int _snowmanxx = 0; _snowmanxx < 4; _snowmanxx++) {
               int _snowmanxxx = 1;
               int _snowmanxxxx = b(_snowmanx, 1, _snowmanxx);
               _snowman[_snowmanxxxx] = new crl.v(_snowmanxxxx);
            }
         }

         for (int _snowmanx = 1; _snowmanx < 4; _snowmanx++) {
            for (int _snowmanxx = 0; _snowmanxx < 2; _snowmanxx++) {
               int _snowmanxxx = 2;
               int _snowmanxxxx = b(_snowmanx, 2, _snowmanxx);
               _snowman[_snowmanxxxx] = new crl.v(_snowmanxxxx);
            }
         }

         this.p = _snowman[h];

         for (int _snowmanx = 0; _snowmanx < 5; _snowmanx++) {
            for (int _snowmanxx = 0; _snowmanxx < 5; _snowmanxx++) {
               for (int _snowmanxxx = 0; _snowmanxxx < 3; _snowmanxxx++) {
                  int _snowmanxxxx = b(_snowmanx, _snowmanxxx, _snowmanxx);
                  if (_snowman[_snowmanxxxx] != null) {
                     for (gc _snowmanxxxxx : gc.values()) {
                        int _snowmanxxxxxx = _snowmanx + _snowmanxxxxx.i();
                        int _snowmanxxxxxxx = _snowmanxxx + _snowmanxxxxx.j();
                        int _snowmanxxxxxxxx = _snowmanxx + _snowmanxxxxx.k();
                        if (_snowmanxxxxxx >= 0 && _snowmanxxxxxx < 5 && _snowmanxxxxxxxx >= 0 && _snowmanxxxxxxxx < 5 && _snowmanxxxxxxx >= 0 && _snowmanxxxxxxx < 3) {
                           int _snowmanxxxxxxxxx = b(_snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
                           if (_snowman[_snowmanxxxxxxxxx] != null) {
                              if (_snowmanxxxxxxxx == _snowmanxx) {
                                 _snowman[_snowmanxxxx].a(_snowmanxxxxx, _snowman[_snowmanxxxxxxxxx]);
                              } else {
                                 _snowman[_snowmanxxxx].a(_snowmanxxxxx.f(), _snowman[_snowmanxxxxxxxxx]);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         crl.v _snowmanx = new crl.v(1003);
         crl.v _snowmanxx = new crl.v(1001);
         crl.v _snowmanxxxx = new crl.v(1002);
         _snowman[i].a(gc.b, _snowmanx);
         _snowman[j].a(gc.d, _snowmanxx);
         _snowman[k].a(gc.d, _snowmanxxxx);
         _snowmanx.d = true;
         _snowmanxx.d = true;
         _snowmanxxxx.d = true;
         this.p.e = true;
         this.q = _snowman[b(_snowman.nextInt(4), 0, 2)];
         this.q.d = true;
         this.q.b[gc.f.c()].d = true;
         this.q.b[gc.c.c()].d = true;
         this.q.b[gc.f.c()].b[gc.c.c()].d = true;
         this.q.b[gc.b.c()].d = true;
         this.q.b[gc.f.c()].b[gc.b.c()].d = true;
         this.q.b[gc.c.c()].b[gc.b.c()].d = true;
         this.q.b[gc.f.c()].b[gc.c.c()].b[gc.b.c()].d = true;
         List<crl.v> _snowmanxxxxxx = Lists.newArrayList();

         for (crl.v _snowmanxxxxxxx : _snowman) {
            if (_snowmanxxxxxxx != null) {
               _snowmanxxxxxxx.a();
               _snowmanxxxxxx.add(_snowmanxxxxxxx);
            }
         }

         _snowmanx.a();
         Collections.shuffle(_snowmanxxxxxx, _snowman);
         int _snowmanxxxxxxxx = 1;

         for (crl.v _snowmanxxxxxxxxx : _snowmanxxxxxx) {
            int _snowmanxxxxxxxxxx = 0;
            int _snowmanxxxxxxxxxxx = 0;

            while (_snowmanxxxxxxxxxx < 2 && _snowmanxxxxxxxxxxx < 5) {
               _snowmanxxxxxxxxxxx++;
               int _snowmanxxxxxxxxxxxx = _snowman.nextInt(6);
               if (_snowmanxxxxxxxxx.c[_snowmanxxxxxxxxxxxx]) {
                  int _snowmanxxxxxxxxxxxxx = gc.a(_snowmanxxxxxxxxxxxx).f().c();
                  _snowmanxxxxxxxxx.c[_snowmanxxxxxxxxxxxx] = false;
                  _snowmanxxxxxxxxx.b[_snowmanxxxxxxxxxxxx].c[_snowmanxxxxxxxxxxxxx] = false;
                  if (_snowmanxxxxxxxxx.a(_snowmanxxxxxxxx++) && _snowmanxxxxxxxxx.b[_snowmanxxxxxxxxxxxx].a(_snowmanxxxxxxxx++)) {
                     _snowmanxxxxxxxxxx++;
                  } else {
                     _snowmanxxxxxxxxx.c[_snowmanxxxxxxxxxxxx] = true;
                     _snowmanxxxxxxxxx.b[_snowmanxxxxxxxxxxxx].c[_snowmanxxxxxxxxxxxxx] = true;
                  }
               }
            }
         }

         _snowmanxxxxxx.add(_snowmanx);
         _snowmanxxxxxx.add(_snowmanxx);
         _snowmanxxxxxx.add(_snowmanxxxx);
         return _snowmanxxxxxx;
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         int _snowman = Math.max(_snowman.t_(), 64) - this.n.b;
         this.a(_snowman, _snowman, 0, 0, 0, 58, _snowman, 58);
         this.a(false, 0, _snowman, _snowman, _snowman);
         this.a(true, 33, _snowman, _snowman, _snowman);
         this.a(_snowman, _snowman, _snowman);
         this.b(_snowman, _snowman, _snowman);
         this.c(_snowman, _snowman, _snowman);
         this.d(_snowman, _snowman, _snowman);
         this.e(_snowman, _snowman, _snowman);
         this.f(_snowman, _snowman, _snowman);

         for (int _snowmanx = 0; _snowmanx < 7; _snowmanx++) {
            int _snowmanxx = 0;

            while (_snowmanxx < 7) {
               if (_snowmanxx == 0 && _snowmanx == 3) {
                  _snowmanxx = 6;
               }

               int _snowmanxxx = _snowmanx * 9;
               int _snowmanxxxx = _snowmanxx * 9;

               for (int _snowmanxxxxx = 0; _snowmanxxxxx < 4; _snowmanxxxxx++) {
                  for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 4; _snowmanxxxxxx++) {
                     this.a(_snowman, b, _snowmanxxx + _snowmanxxxxx, 0, _snowmanxxxx + _snowmanxxxxxx, _snowman);
                     this.b(_snowman, b, _snowmanxxx + _snowmanxxxxx, -1, _snowmanxxxx + _snowmanxxxxxx, _snowman);
                  }
               }

               if (_snowmanx != 0 && _snowmanx != 6) {
                  _snowmanxx += 6;
               } else {
                  _snowmanxx++;
               }
            }
         }

         for (int _snowmanx = 0; _snowmanx < 5; _snowmanx++) {
            this.a(_snowman, _snowman, -1 - _snowmanx, 0 + _snowmanx * 2, -1 - _snowmanx, -1 - _snowmanx, 23, 58 + _snowmanx);
            this.a(_snowman, _snowman, 58 + _snowmanx, 0 + _snowmanx * 2, -1 - _snowmanx, 58 + _snowmanx, 23, 58 + _snowmanx);
            this.a(_snowman, _snowman, 0 - _snowmanx, 0 + _snowmanx * 2, -1 - _snowmanx, 57 + _snowmanx, 23, -1 - _snowmanx);
            this.a(_snowman, _snowman, 0 - _snowmanx, 0 + _snowmanx * 2, 58 + _snowmanx, 57 + _snowmanx, 23, 58 + _snowmanx);
         }

         for (crl.r _snowmanx : this.r) {
            if (_snowmanx.g().b(_snowman)) {
               _snowmanx.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
            }
         }

         return true;
      }

      private void a(boolean var1, int var2, bsr var3, Random var4, cra var5) {
         int _snowman = 24;
         if (this.a(_snowman, _snowman, 0, _snowman + 23, 20)) {
            this.a(_snowman, _snowman, _snowman + 0, 0, 0, _snowman + 24, 0, 20, a, a, false);
            this.a(_snowman, _snowman, _snowman + 0, 1, 0, _snowman + 24, 10, 20);

            for (int _snowmanx = 0; _snowmanx < 4; _snowmanx++) {
               this.a(_snowman, _snowman, _snowman + _snowmanx, _snowmanx + 1, _snowmanx, _snowman + _snowmanx, _snowmanx + 1, 20, b, b, false);
               this.a(_snowman, _snowman, _snowman + _snowmanx + 7, _snowmanx + 5, _snowmanx + 7, _snowman + _snowmanx + 7, _snowmanx + 5, 20, b, b, false);
               this.a(_snowman, _snowman, _snowman + 17 - _snowmanx, _snowmanx + 5, _snowmanx + 7, _snowman + 17 - _snowmanx, _snowmanx + 5, 20, b, b, false);
               this.a(_snowman, _snowman, _snowman + 24 - _snowmanx, _snowmanx + 1, _snowmanx, _snowman + 24 - _snowmanx, _snowmanx + 1, 20, b, b, false);
               this.a(_snowman, _snowman, _snowman + _snowmanx + 1, _snowmanx + 1, _snowmanx, _snowman + 23 - _snowmanx, _snowmanx + 1, _snowmanx, b, b, false);
               this.a(_snowman, _snowman, _snowman + _snowmanx + 8, _snowmanx + 5, _snowmanx + 7, _snowman + 16 - _snowmanx, _snowmanx + 5, _snowmanx + 7, b, b, false);
            }

            this.a(_snowman, _snowman, _snowman + 4, 4, 4, _snowman + 6, 4, 20, a, a, false);
            this.a(_snowman, _snowman, _snowman + 7, 4, 4, _snowman + 17, 4, 6, a, a, false);
            this.a(_snowman, _snowman, _snowman + 18, 4, 4, _snowman + 20, 4, 20, a, a, false);
            this.a(_snowman, _snowman, _snowman + 11, 8, 11, _snowman + 13, 8, 20, a, a, false);
            this.a(_snowman, d, _snowman + 12, 9, 12, _snowman);
            this.a(_snowman, d, _snowman + 12, 9, 15, _snowman);
            this.a(_snowman, d, _snowman + 12, 9, 18, _snowman);
            int _snowmanx = _snowman + (_snowman ? 19 : 5);
            int _snowmanxx = _snowman + (_snowman ? 5 : 19);

            for (int _snowmanxxx = 20; _snowmanxxx >= 5; _snowmanxxx -= 3) {
               this.a(_snowman, d, _snowmanx, 5, _snowmanxxx, _snowman);
            }

            for (int _snowmanxxx = 19; _snowmanxxx >= 7; _snowmanxxx -= 3) {
               this.a(_snowman, d, _snowmanxx, 5, _snowmanxxx, _snowman);
            }

            for (int _snowmanxxx = 0; _snowmanxxx < 4; _snowmanxxx++) {
               int _snowmanxxxx = _snowman ? _snowman + 24 - (17 - _snowmanxxx * 3) : _snowman + 17 - _snowmanxxx * 3;
               this.a(_snowman, d, _snowmanxxxx, 5, 5, _snowman);
            }

            this.a(_snowman, d, _snowmanxx, 5, 5, _snowman);
            this.a(_snowman, _snowman, _snowman + 11, 1, 12, _snowman + 13, 7, 12, a, a, false);
            this.a(_snowman, _snowman, _snowman + 12, 1, 11, _snowman + 12, 7, 13, a, a, false);
         }
      }

      private void a(bsr var1, Random var2, cra var3) {
         if (this.a(_snowman, 22, 5, 35, 17)) {
            this.a(_snowman, _snowman, 25, 0, 0, 32, 8, 20);

            for (int _snowman = 0; _snowman < 4; _snowman++) {
               this.a(_snowman, _snowman, 24, 2, 5 + _snowman * 4, 24, 4, 5 + _snowman * 4, b, b, false);
               this.a(_snowman, _snowman, 22, 4, 5 + _snowman * 4, 23, 4, 5 + _snowman * 4, b, b, false);
               this.a(_snowman, b, 25, 5, 5 + _snowman * 4, _snowman);
               this.a(_snowman, b, 26, 6, 5 + _snowman * 4, _snowman);
               this.a(_snowman, e, 26, 5, 5 + _snowman * 4, _snowman);
               this.a(_snowman, _snowman, 33, 2, 5 + _snowman * 4, 33, 4, 5 + _snowman * 4, b, b, false);
               this.a(_snowman, _snowman, 34, 4, 5 + _snowman * 4, 35, 4, 5 + _snowman * 4, b, b, false);
               this.a(_snowman, b, 32, 5, 5 + _snowman * 4, _snowman);
               this.a(_snowman, b, 31, 6, 5 + _snowman * 4, _snowman);
               this.a(_snowman, e, 31, 5, 5 + _snowman * 4, _snowman);
               this.a(_snowman, _snowman, 27, 6, 5 + _snowman * 4, 30, 6, 5 + _snowman * 4, a, a, false);
            }
         }
      }

      private void b(bsr var1, Random var2, cra var3) {
         if (this.a(_snowman, 15, 20, 42, 21)) {
            this.a(_snowman, _snowman, 15, 0, 21, 42, 0, 21, a, a, false);
            this.a(_snowman, _snowman, 26, 1, 21, 31, 3, 21);
            this.a(_snowman, _snowman, 21, 12, 21, 36, 12, 21, a, a, false);
            this.a(_snowman, _snowman, 17, 11, 21, 40, 11, 21, a, a, false);
            this.a(_snowman, _snowman, 16, 10, 21, 41, 10, 21, a, a, false);
            this.a(_snowman, _snowman, 15, 7, 21, 42, 9, 21, a, a, false);
            this.a(_snowman, _snowman, 16, 6, 21, 41, 6, 21, a, a, false);
            this.a(_snowman, _snowman, 17, 5, 21, 40, 5, 21, a, a, false);
            this.a(_snowman, _snowman, 21, 4, 21, 36, 4, 21, a, a, false);
            this.a(_snowman, _snowman, 22, 3, 21, 26, 3, 21, a, a, false);
            this.a(_snowman, _snowman, 31, 3, 21, 35, 3, 21, a, a, false);
            this.a(_snowman, _snowman, 23, 2, 21, 25, 2, 21, a, a, false);
            this.a(_snowman, _snowman, 32, 2, 21, 34, 2, 21, a, a, false);
            this.a(_snowman, _snowman, 28, 4, 20, 29, 4, 21, b, b, false);
            this.a(_snowman, b, 27, 3, 21, _snowman);
            this.a(_snowman, b, 30, 3, 21, _snowman);
            this.a(_snowman, b, 26, 2, 21, _snowman);
            this.a(_snowman, b, 31, 2, 21, _snowman);
            this.a(_snowman, b, 25, 1, 21, _snowman);
            this.a(_snowman, b, 32, 1, 21, _snowman);

            for (int _snowman = 0; _snowman < 7; _snowman++) {
               this.a(_snowman, c, 28 - _snowman, 6 + _snowman, 21, _snowman);
               this.a(_snowman, c, 29 + _snowman, 6 + _snowman, 21, _snowman);
            }

            for (int _snowman = 0; _snowman < 4; _snowman++) {
               this.a(_snowman, c, 28 - _snowman, 9 + _snowman, 21, _snowman);
               this.a(_snowman, c, 29 + _snowman, 9 + _snowman, 21, _snowman);
            }

            this.a(_snowman, c, 28, 12, 21, _snowman);
            this.a(_snowman, c, 29, 12, 21, _snowman);

            for (int _snowman = 0; _snowman < 3; _snowman++) {
               this.a(_snowman, c, 22 - _snowman * 2, 8, 21, _snowman);
               this.a(_snowman, c, 22 - _snowman * 2, 9, 21, _snowman);
               this.a(_snowman, c, 35 + _snowman * 2, 8, 21, _snowman);
               this.a(_snowman, c, 35 + _snowman * 2, 9, 21, _snowman);
            }

            this.a(_snowman, _snowman, 15, 13, 21, 42, 15, 21);
            this.a(_snowman, _snowman, 15, 1, 21, 15, 6, 21);
            this.a(_snowman, _snowman, 16, 1, 21, 16, 5, 21);
            this.a(_snowman, _snowman, 17, 1, 21, 20, 4, 21);
            this.a(_snowman, _snowman, 21, 1, 21, 21, 3, 21);
            this.a(_snowman, _snowman, 22, 1, 21, 22, 2, 21);
            this.a(_snowman, _snowman, 23, 1, 21, 24, 1, 21);
            this.a(_snowman, _snowman, 42, 1, 21, 42, 6, 21);
            this.a(_snowman, _snowman, 41, 1, 21, 41, 5, 21);
            this.a(_snowman, _snowman, 37, 1, 21, 40, 4, 21);
            this.a(_snowman, _snowman, 36, 1, 21, 36, 3, 21);
            this.a(_snowman, _snowman, 33, 1, 21, 34, 1, 21);
            this.a(_snowman, _snowman, 35, 1, 21, 35, 2, 21);
         }
      }

      private void c(bsr var1, Random var2, cra var3) {
         if (this.a(_snowman, 21, 21, 36, 36)) {
            this.a(_snowman, _snowman, 21, 0, 22, 36, 0, 36, a, a, false);
            this.a(_snowman, _snowman, 21, 1, 22, 36, 23, 36);

            for (int _snowman = 0; _snowman < 4; _snowman++) {
               this.a(_snowman, _snowman, 21 + _snowman, 13 + _snowman, 21 + _snowman, 36 - _snowman, 13 + _snowman, 21 + _snowman, b, b, false);
               this.a(_snowman, _snowman, 21 + _snowman, 13 + _snowman, 36 - _snowman, 36 - _snowman, 13 + _snowman, 36 - _snowman, b, b, false);
               this.a(_snowman, _snowman, 21 + _snowman, 13 + _snowman, 22 + _snowman, 21 + _snowman, 13 + _snowman, 35 - _snowman, b, b, false);
               this.a(_snowman, _snowman, 36 - _snowman, 13 + _snowman, 22 + _snowman, 36 - _snowman, 13 + _snowman, 35 - _snowman, b, b, false);
            }

            this.a(_snowman, _snowman, 25, 16, 25, 32, 16, 32, a, a, false);
            this.a(_snowman, _snowman, 25, 17, 25, 25, 19, 25, b, b, false);
            this.a(_snowman, _snowman, 32, 17, 25, 32, 19, 25, b, b, false);
            this.a(_snowman, _snowman, 25, 17, 32, 25, 19, 32, b, b, false);
            this.a(_snowman, _snowman, 32, 17, 32, 32, 19, 32, b, b, false);
            this.a(_snowman, b, 26, 20, 26, _snowman);
            this.a(_snowman, b, 27, 21, 27, _snowman);
            this.a(_snowman, e, 27, 20, 27, _snowman);
            this.a(_snowman, b, 26, 20, 31, _snowman);
            this.a(_snowman, b, 27, 21, 30, _snowman);
            this.a(_snowman, e, 27, 20, 30, _snowman);
            this.a(_snowman, b, 31, 20, 31, _snowman);
            this.a(_snowman, b, 30, 21, 30, _snowman);
            this.a(_snowman, e, 30, 20, 30, _snowman);
            this.a(_snowman, b, 31, 20, 26, _snowman);
            this.a(_snowman, b, 30, 21, 27, _snowman);
            this.a(_snowman, e, 30, 20, 27, _snowman);
            this.a(_snowman, _snowman, 28, 21, 27, 29, 21, 27, a, a, false);
            this.a(_snowman, _snowman, 27, 21, 28, 27, 21, 29, a, a, false);
            this.a(_snowman, _snowman, 28, 21, 30, 29, 21, 30, a, a, false);
            this.a(_snowman, _snowman, 30, 21, 28, 30, 21, 29, a, a, false);
         }
      }

      private void d(bsr var1, Random var2, cra var3) {
         if (this.a(_snowman, 0, 21, 6, 58)) {
            this.a(_snowman, _snowman, 0, 0, 21, 6, 0, 57, a, a, false);
            this.a(_snowman, _snowman, 0, 1, 21, 6, 7, 57);
            this.a(_snowman, _snowman, 4, 4, 21, 6, 4, 53, a, a, false);

            for (int _snowman = 0; _snowman < 4; _snowman++) {
               this.a(_snowman, _snowman, _snowman, _snowman + 1, 21, _snowman, _snowman + 1, 57 - _snowman, b, b, false);
            }

            for (int _snowman = 23; _snowman < 53; _snowman += 3) {
               this.a(_snowman, d, 5, 5, _snowman, _snowman);
            }

            this.a(_snowman, d, 5, 5, 52, _snowman);

            for (int _snowman = 0; _snowman < 4; _snowman++) {
               this.a(_snowman, _snowman, _snowman, _snowman + 1, 21, _snowman, _snowman + 1, 57 - _snowman, b, b, false);
            }

            this.a(_snowman, _snowman, 4, 1, 52, 6, 3, 52, a, a, false);
            this.a(_snowman, _snowman, 5, 1, 51, 5, 3, 53, a, a, false);
         }

         if (this.a(_snowman, 51, 21, 58, 58)) {
            this.a(_snowman, _snowman, 51, 0, 21, 57, 0, 57, a, a, false);
            this.a(_snowman, _snowman, 51, 1, 21, 57, 7, 57);
            this.a(_snowman, _snowman, 51, 4, 21, 53, 4, 53, a, a, false);

            for (int _snowman = 0; _snowman < 4; _snowman++) {
               this.a(_snowman, _snowman, 57 - _snowman, _snowman + 1, 21, 57 - _snowman, _snowman + 1, 57 - _snowman, b, b, false);
            }

            for (int _snowman = 23; _snowman < 53; _snowman += 3) {
               this.a(_snowman, d, 52, 5, _snowman, _snowman);
            }

            this.a(_snowman, d, 52, 5, 52, _snowman);
            this.a(_snowman, _snowman, 51, 1, 52, 53, 3, 52, a, a, false);
            this.a(_snowman, _snowman, 52, 1, 51, 52, 3, 53, a, a, false);
         }

         if (this.a(_snowman, 0, 51, 57, 57)) {
            this.a(_snowman, _snowman, 7, 0, 51, 50, 0, 57, a, a, false);
            this.a(_snowman, _snowman, 7, 1, 51, 50, 10, 57);

            for (int _snowman = 0; _snowman < 4; _snowman++) {
               this.a(_snowman, _snowman, _snowman + 1, _snowman + 1, 57 - _snowman, 56 - _snowman, _snowman + 1, 57 - _snowman, b, b, false);
            }
         }
      }

      private void e(bsr var1, Random var2, cra var3) {
         if (this.a(_snowman, 7, 21, 13, 50)) {
            this.a(_snowman, _snowman, 7, 0, 21, 13, 0, 50, a, a, false);
            this.a(_snowman, _snowman, 7, 1, 21, 13, 10, 50);
            this.a(_snowman, _snowman, 11, 8, 21, 13, 8, 53, a, a, false);

            for (int _snowman = 0; _snowman < 4; _snowman++) {
               this.a(_snowman, _snowman, _snowman + 7, _snowman + 5, 21, _snowman + 7, _snowman + 5, 54, b, b, false);
            }

            for (int _snowman = 21; _snowman <= 45; _snowman += 3) {
               this.a(_snowman, d, 12, 9, _snowman, _snowman);
            }
         }

         if (this.a(_snowman, 44, 21, 50, 54)) {
            this.a(_snowman, _snowman, 44, 0, 21, 50, 0, 50, a, a, false);
            this.a(_snowman, _snowman, 44, 1, 21, 50, 10, 50);
            this.a(_snowman, _snowman, 44, 8, 21, 46, 8, 53, a, a, false);

            for (int _snowman = 0; _snowman < 4; _snowman++) {
               this.a(_snowman, _snowman, 50 - _snowman, _snowman + 5, 21, 50 - _snowman, _snowman + 5, 54, b, b, false);
            }

            for (int _snowman = 21; _snowman <= 45; _snowman += 3) {
               this.a(_snowman, d, 45, 9, _snowman, _snowman);
            }
         }

         if (this.a(_snowman, 8, 44, 49, 54)) {
            this.a(_snowman, _snowman, 14, 0, 44, 43, 0, 50, a, a, false);
            this.a(_snowman, _snowman, 14, 1, 44, 43, 10, 50);

            for (int _snowman = 12; _snowman <= 45; _snowman += 3) {
               this.a(_snowman, d, _snowman, 9, 45, _snowman);
               this.a(_snowman, d, _snowman, 9, 52, _snowman);
               if (_snowman == 12 || _snowman == 18 || _snowman == 24 || _snowman == 33 || _snowman == 39 || _snowman == 45) {
                  this.a(_snowman, d, _snowman, 9, 47, _snowman);
                  this.a(_snowman, d, _snowman, 9, 50, _snowman);
                  this.a(_snowman, d, _snowman, 10, 45, _snowman);
                  this.a(_snowman, d, _snowman, 10, 46, _snowman);
                  this.a(_snowman, d, _snowman, 10, 51, _snowman);
                  this.a(_snowman, d, _snowman, 10, 52, _snowman);
                  this.a(_snowman, d, _snowman, 11, 47, _snowman);
                  this.a(_snowman, d, _snowman, 11, 50, _snowman);
                  this.a(_snowman, d, _snowman, 12, 48, _snowman);
                  this.a(_snowman, d, _snowman, 12, 49, _snowman);
               }
            }

            for (int _snowmanx = 0; _snowmanx < 3; _snowmanx++) {
               this.a(_snowman, _snowman, 8 + _snowmanx, 5 + _snowmanx, 54, 49 - _snowmanx, 5 + _snowmanx, 54, a, a, false);
            }

            this.a(_snowman, _snowman, 11, 8, 54, 46, 8, 54, b, b, false);
            this.a(_snowman, _snowman, 14, 8, 44, 43, 8, 53, a, a, false);
         }
      }

      private void f(bsr var1, Random var2, cra var3) {
         if (this.a(_snowman, 14, 21, 20, 43)) {
            this.a(_snowman, _snowman, 14, 0, 21, 20, 0, 43, a, a, false);
            this.a(_snowman, _snowman, 14, 1, 22, 20, 14, 43);
            this.a(_snowman, _snowman, 18, 12, 22, 20, 12, 39, a, a, false);
            this.a(_snowman, _snowman, 18, 12, 21, 20, 12, 21, b, b, false);

            for (int _snowman = 0; _snowman < 4; _snowman++) {
               this.a(_snowman, _snowman, _snowman + 14, _snowman + 9, 21, _snowman + 14, _snowman + 9, 43 - _snowman, b, b, false);
            }

            for (int _snowman = 23; _snowman <= 39; _snowman += 3) {
               this.a(_snowman, d, 19, 13, _snowman, _snowman);
            }
         }

         if (this.a(_snowman, 37, 21, 43, 43)) {
            this.a(_snowman, _snowman, 37, 0, 21, 43, 0, 43, a, a, false);
            this.a(_snowman, _snowman, 37, 1, 22, 43, 14, 43);
            this.a(_snowman, _snowman, 37, 12, 22, 39, 12, 39, a, a, false);
            this.a(_snowman, _snowman, 37, 12, 21, 39, 12, 21, b, b, false);

            for (int _snowman = 0; _snowman < 4; _snowman++) {
               this.a(_snowman, _snowman, 43 - _snowman, _snowman + 9, 21, 43 - _snowman, _snowman + 9, 43 - _snowman, b, b, false);
            }

            for (int _snowman = 23; _snowman <= 39; _snowman += 3) {
               this.a(_snowman, d, 38, 13, _snowman, _snowman);
            }
         }

         if (this.a(_snowman, 15, 37, 42, 43)) {
            this.a(_snowman, _snowman, 21, 0, 37, 36, 0, 43, a, a, false);
            this.a(_snowman, _snowman, 21, 1, 37, 36, 14, 43);
            this.a(_snowman, _snowman, 21, 12, 37, 36, 12, 39, a, a, false);

            for (int _snowman = 0; _snowman < 4; _snowman++) {
               this.a(_snowman, _snowman, 15 + _snowman, _snowman + 9, 43 - _snowman, 42 - _snowman, _snowman + 9, 43 - _snowman, b, b, false);
            }

            for (int _snowman = 21; _snowman <= 36; _snowman += 3) {
               this.a(_snowman, d, _snowman, 13, 38, _snowman);
            }
         }
      }
   }

   interface i {
      boolean a(crl.v var1);

      crl.r a(gc var1, crl.v var2, Random var3);
   }

   public static class j extends crl.r {
      public j(gc var1, crl.v var2) {
         super(clb.N, 1, _snowman, _snowman, 2, 2, 2);
      }

      public j(csw var1, md var2) {
         super(clb.N, _snowman);
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         this.a(_snowman, _snowman, 1, 8, 0, 14, 8, 14, a);
         int _snowman = 7;
         ceh _snowmanx = b;
         this.a(_snowman, _snowman, 0, 7, 0, 0, 7, 15, _snowmanx, _snowmanx, false);
         this.a(_snowman, _snowman, 15, 7, 0, 15, 7, 15, _snowmanx, _snowmanx, false);
         this.a(_snowman, _snowman, 1, 7, 0, 15, 7, 0, _snowmanx, _snowmanx, false);
         this.a(_snowman, _snowman, 1, 7, 15, 14, 7, 15, _snowmanx, _snowmanx, false);

         for (int _snowmanxx = 1; _snowmanxx <= 6; _snowmanxx++) {
            _snowmanx = b;
            if (_snowmanxx == 2 || _snowmanxx == 6) {
               _snowmanx = a;
            }

            for (int _snowmanxxx = 0; _snowmanxxx <= 15; _snowmanxxx += 15) {
               this.a(_snowman, _snowman, _snowmanxxx, _snowmanxx, 0, _snowmanxxx, _snowmanxx, 1, _snowmanx, _snowmanx, false);
               this.a(_snowman, _snowman, _snowmanxxx, _snowmanxx, 6, _snowmanxxx, _snowmanxx, 9, _snowmanx, _snowmanx, false);
               this.a(_snowman, _snowman, _snowmanxxx, _snowmanxx, 14, _snowmanxxx, _snowmanxx, 15, _snowmanx, _snowmanx, false);
            }

            this.a(_snowman, _snowman, 1, _snowmanxx, 0, 1, _snowmanxx, 0, _snowmanx, _snowmanx, false);
            this.a(_snowman, _snowman, 6, _snowmanxx, 0, 9, _snowmanxx, 0, _snowmanx, _snowmanx, false);
            this.a(_snowman, _snowman, 14, _snowmanxx, 0, 14, _snowmanxx, 0, _snowmanx, _snowmanx, false);
            this.a(_snowman, _snowman, 1, _snowmanxx, 15, 14, _snowmanxx, 15, _snowmanx, _snowmanx, false);
         }

         this.a(_snowman, _snowman, 6, 3, 6, 9, 6, 9, c, c, false);
         this.a(_snowman, _snowman, 7, 4, 7, 8, 5, 8, bup.bE.n(), bup.bE.n(), false);

         for (int _snowmanxx = 3; _snowmanxx <= 6; _snowmanxx += 3) {
            for (int _snowmanxxx = 6; _snowmanxxx <= 9; _snowmanxxx += 3) {
               this.a(_snowman, e, _snowmanxxx, _snowmanxx, 6, _snowman);
               this.a(_snowman, e, _snowmanxxx, _snowmanxx, 9, _snowman);
            }
         }

         this.a(_snowman, _snowman, 5, 1, 6, 5, 2, 6, b, b, false);
         this.a(_snowman, _snowman, 5, 1, 9, 5, 2, 9, b, b, false);
         this.a(_snowman, _snowman, 10, 1, 6, 10, 2, 6, b, b, false);
         this.a(_snowman, _snowman, 10, 1, 9, 10, 2, 9, b, b, false);
         this.a(_snowman, _snowman, 6, 1, 5, 6, 2, 5, b, b, false);
         this.a(_snowman, _snowman, 9, 1, 5, 9, 2, 5, b, b, false);
         this.a(_snowman, _snowman, 6, 1, 10, 6, 2, 10, b, b, false);
         this.a(_snowman, _snowman, 9, 1, 10, 9, 2, 10, b, b, false);
         this.a(_snowman, _snowman, 5, 2, 5, 5, 6, 5, b, b, false);
         this.a(_snowman, _snowman, 5, 2, 10, 5, 6, 10, b, b, false);
         this.a(_snowman, _snowman, 10, 2, 5, 10, 6, 5, b, b, false);
         this.a(_snowman, _snowman, 10, 2, 10, 10, 6, 10, b, b, false);
         this.a(_snowman, _snowman, 5, 7, 1, 5, 7, 6, b, b, false);
         this.a(_snowman, _snowman, 10, 7, 1, 10, 7, 6, b, b, false);
         this.a(_snowman, _snowman, 5, 7, 9, 5, 7, 14, b, b, false);
         this.a(_snowman, _snowman, 10, 7, 9, 10, 7, 14, b, b, false);
         this.a(_snowman, _snowman, 1, 7, 5, 6, 7, 5, b, b, false);
         this.a(_snowman, _snowman, 1, 7, 10, 6, 7, 10, b, b, false);
         this.a(_snowman, _snowman, 9, 7, 5, 14, 7, 5, b, b, false);
         this.a(_snowman, _snowman, 9, 7, 10, 14, 7, 10, b, b, false);
         this.a(_snowman, _snowman, 2, 1, 2, 2, 1, 3, b, b, false);
         this.a(_snowman, _snowman, 3, 1, 2, 3, 1, 2, b, b, false);
         this.a(_snowman, _snowman, 13, 1, 2, 13, 1, 3, b, b, false);
         this.a(_snowman, _snowman, 12, 1, 2, 12, 1, 2, b, b, false);
         this.a(_snowman, _snowman, 2, 1, 12, 2, 1, 13, b, b, false);
         this.a(_snowman, _snowman, 3, 1, 13, 3, 1, 13, b, b, false);
         this.a(_snowman, _snowman, 13, 1, 12, 13, 1, 13, b, b, false);
         this.a(_snowman, _snowman, 12, 1, 13, 12, 1, 13, b, b, false);
         return true;
      }
   }

   public static class k extends crl.r {
      public k(gc var1, crl.v var2) {
         super(clb.O, 1, _snowman, _snowman, 2, 1, 1);
      }

      public k(csw var1, md var2) {
         super(clb.O, _snowman);
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         crl.v _snowman = this.l.b[gc.f.c()];
         crl.v _snowmanx = this.l;
         if (this.l.a / 25 > 0) {
            this.a(_snowman, _snowman, 8, 0, _snowman.c[gc.a.c()]);
            this.a(_snowman, _snowman, 0, 0, _snowmanx.c[gc.a.c()]);
         }

         if (_snowmanx.b[gc.b.c()] == null) {
            this.a(_snowman, _snowman, 1, 4, 1, 7, 4, 6, a);
         }

         if (_snowman.b[gc.b.c()] == null) {
            this.a(_snowman, _snowman, 8, 4, 1, 14, 4, 6, a);
         }

         this.a(_snowman, _snowman, 0, 3, 0, 0, 3, 7, b, b, false);
         this.a(_snowman, _snowman, 15, 3, 0, 15, 3, 7, b, b, false);
         this.a(_snowman, _snowman, 1, 3, 0, 15, 3, 0, b, b, false);
         this.a(_snowman, _snowman, 1, 3, 7, 14, 3, 7, b, b, false);
         this.a(_snowman, _snowman, 0, 2, 0, 0, 2, 7, a, a, false);
         this.a(_snowman, _snowman, 15, 2, 0, 15, 2, 7, a, a, false);
         this.a(_snowman, _snowman, 1, 2, 0, 15, 2, 0, a, a, false);
         this.a(_snowman, _snowman, 1, 2, 7, 14, 2, 7, a, a, false);
         this.a(_snowman, _snowman, 0, 1, 0, 0, 1, 7, b, b, false);
         this.a(_snowman, _snowman, 15, 1, 0, 15, 1, 7, b, b, false);
         this.a(_snowman, _snowman, 1, 1, 0, 15, 1, 0, b, b, false);
         this.a(_snowman, _snowman, 1, 1, 7, 14, 1, 7, b, b, false);
         this.a(_snowman, _snowman, 5, 1, 0, 10, 1, 4, b, b, false);
         this.a(_snowman, _snowman, 6, 2, 0, 9, 2, 3, a, a, false);
         this.a(_snowman, _snowman, 5, 3, 0, 10, 3, 4, b, b, false);
         this.a(_snowman, e, 6, 2, 3, _snowman);
         this.a(_snowman, e, 9, 2, 3, _snowman);
         if (_snowmanx.c[gc.d.c()]) {
            this.a(_snowman, _snowman, 3, 1, 0, 4, 2, 0);
         }

         if (_snowmanx.c[gc.c.c()]) {
            this.a(_snowman, _snowman, 3, 1, 7, 4, 2, 7);
         }

         if (_snowmanx.c[gc.e.c()]) {
            this.a(_snowman, _snowman, 0, 1, 3, 0, 2, 4);
         }

         if (_snowman.c[gc.d.c()]) {
            this.a(_snowman, _snowman, 11, 1, 0, 12, 2, 0);
         }

         if (_snowman.c[gc.c.c()]) {
            this.a(_snowman, _snowman, 11, 1, 7, 12, 2, 7);
         }

         if (_snowman.c[gc.f.c()]) {
            this.a(_snowman, _snowman, 15, 1, 3, 15, 2, 4);
         }

         return true;
      }
   }

   public static class l extends crl.r {
      public l(gc var1, crl.v var2) {
         super(clb.P, 1, _snowman, _snowman, 2, 2, 1);
      }

      public l(csw var1, md var2) {
         super(clb.P, _snowman);
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         crl.v _snowman = this.l.b[gc.f.c()];
         crl.v _snowmanx = this.l;
         crl.v _snowmanxx = _snowmanx.b[gc.b.c()];
         crl.v _snowmanxxx = _snowman.b[gc.b.c()];
         if (this.l.a / 25 > 0) {
            this.a(_snowman, _snowman, 8, 0, _snowman.c[gc.a.c()]);
            this.a(_snowman, _snowman, 0, 0, _snowmanx.c[gc.a.c()]);
         }

         if (_snowmanxx.b[gc.b.c()] == null) {
            this.a(_snowman, _snowman, 1, 8, 1, 7, 8, 6, a);
         }

         if (_snowmanxxx.b[gc.b.c()] == null) {
            this.a(_snowman, _snowman, 8, 8, 1, 14, 8, 6, a);
         }

         for (int _snowmanxxxx = 1; _snowmanxxxx <= 7; _snowmanxxxx++) {
            ceh _snowmanxxxxx = b;
            if (_snowmanxxxx == 2 || _snowmanxxxx == 6) {
               _snowmanxxxxx = a;
            }

            this.a(_snowman, _snowman, 0, _snowmanxxxx, 0, 0, _snowmanxxxx, 7, _snowmanxxxxx, _snowmanxxxxx, false);
            this.a(_snowman, _snowman, 15, _snowmanxxxx, 0, 15, _snowmanxxxx, 7, _snowmanxxxxx, _snowmanxxxxx, false);
            this.a(_snowman, _snowman, 1, _snowmanxxxx, 0, 15, _snowmanxxxx, 0, _snowmanxxxxx, _snowmanxxxxx, false);
            this.a(_snowman, _snowman, 1, _snowmanxxxx, 7, 14, _snowmanxxxx, 7, _snowmanxxxxx, _snowmanxxxxx, false);
         }

         this.a(_snowman, _snowman, 2, 1, 3, 2, 7, 4, b, b, false);
         this.a(_snowman, _snowman, 3, 1, 2, 4, 7, 2, b, b, false);
         this.a(_snowman, _snowman, 3, 1, 5, 4, 7, 5, b, b, false);
         this.a(_snowman, _snowman, 13, 1, 3, 13, 7, 4, b, b, false);
         this.a(_snowman, _snowman, 11, 1, 2, 12, 7, 2, b, b, false);
         this.a(_snowman, _snowman, 11, 1, 5, 12, 7, 5, b, b, false);
         this.a(_snowman, _snowman, 5, 1, 3, 5, 3, 4, b, b, false);
         this.a(_snowman, _snowman, 10, 1, 3, 10, 3, 4, b, b, false);
         this.a(_snowman, _snowman, 5, 7, 2, 10, 7, 5, b, b, false);
         this.a(_snowman, _snowman, 5, 5, 2, 5, 7, 2, b, b, false);
         this.a(_snowman, _snowman, 10, 5, 2, 10, 7, 2, b, b, false);
         this.a(_snowman, _snowman, 5, 5, 5, 5, 7, 5, b, b, false);
         this.a(_snowman, _snowman, 10, 5, 5, 10, 7, 5, b, b, false);
         this.a(_snowman, b, 6, 6, 2, _snowman);
         this.a(_snowman, b, 9, 6, 2, _snowman);
         this.a(_snowman, b, 6, 6, 5, _snowman);
         this.a(_snowman, b, 9, 6, 5, _snowman);
         this.a(_snowman, _snowman, 5, 4, 3, 6, 4, 4, b, b, false);
         this.a(_snowman, _snowman, 9, 4, 3, 10, 4, 4, b, b, false);
         this.a(_snowman, e, 5, 4, 2, _snowman);
         this.a(_snowman, e, 5, 4, 5, _snowman);
         this.a(_snowman, e, 10, 4, 2, _snowman);
         this.a(_snowman, e, 10, 4, 5, _snowman);
         if (_snowmanx.c[gc.d.c()]) {
            this.a(_snowman, _snowman, 3, 1, 0, 4, 2, 0);
         }

         if (_snowmanx.c[gc.c.c()]) {
            this.a(_snowman, _snowman, 3, 1, 7, 4, 2, 7);
         }

         if (_snowmanx.c[gc.e.c()]) {
            this.a(_snowman, _snowman, 0, 1, 3, 0, 2, 4);
         }

         if (_snowman.c[gc.d.c()]) {
            this.a(_snowman, _snowman, 11, 1, 0, 12, 2, 0);
         }

         if (_snowman.c[gc.c.c()]) {
            this.a(_snowman, _snowman, 11, 1, 7, 12, 2, 7);
         }

         if (_snowman.c[gc.f.c()]) {
            this.a(_snowman, _snowman, 15, 1, 3, 15, 2, 4);
         }

         if (_snowmanxx.c[gc.d.c()]) {
            this.a(_snowman, _snowman, 3, 5, 0, 4, 6, 0);
         }

         if (_snowmanxx.c[gc.c.c()]) {
            this.a(_snowman, _snowman, 3, 5, 7, 4, 6, 7);
         }

         if (_snowmanxx.c[gc.e.c()]) {
            this.a(_snowman, _snowman, 0, 5, 3, 0, 6, 4);
         }

         if (_snowmanxxx.c[gc.d.c()]) {
            this.a(_snowman, _snowman, 11, 5, 0, 12, 6, 0);
         }

         if (_snowmanxxx.c[gc.c.c()]) {
            this.a(_snowman, _snowman, 11, 5, 7, 12, 6, 7);
         }

         if (_snowmanxxx.c[gc.f.c()]) {
            this.a(_snowman, _snowman, 15, 5, 3, 15, 6, 4);
         }

         return true;
      }
   }

   public static class m extends crl.r {
      public m(gc var1, crl.v var2) {
         super(clb.Q, 1, _snowman, _snowman, 1, 2, 1);
      }

      public m(csw var1, md var2) {
         super(clb.Q, _snowman);
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         if (this.l.a / 25 > 0) {
            this.a(_snowman, _snowman, 0, 0, this.l.c[gc.a.c()]);
         }

         crl.v _snowman = this.l.b[gc.b.c()];
         if (_snowman.b[gc.b.c()] == null) {
            this.a(_snowman, _snowman, 1, 8, 1, 6, 8, 6, a);
         }

         this.a(_snowman, _snowman, 0, 4, 0, 0, 4, 7, b, b, false);
         this.a(_snowman, _snowman, 7, 4, 0, 7, 4, 7, b, b, false);
         this.a(_snowman, _snowman, 1, 4, 0, 6, 4, 0, b, b, false);
         this.a(_snowman, _snowman, 1, 4, 7, 6, 4, 7, b, b, false);
         this.a(_snowman, _snowman, 2, 4, 1, 2, 4, 2, b, b, false);
         this.a(_snowman, _snowman, 1, 4, 2, 1, 4, 2, b, b, false);
         this.a(_snowman, _snowman, 5, 4, 1, 5, 4, 2, b, b, false);
         this.a(_snowman, _snowman, 6, 4, 2, 6, 4, 2, b, b, false);
         this.a(_snowman, _snowman, 2, 4, 5, 2, 4, 6, b, b, false);
         this.a(_snowman, _snowman, 1, 4, 5, 1, 4, 5, b, b, false);
         this.a(_snowman, _snowman, 5, 4, 5, 5, 4, 6, b, b, false);
         this.a(_snowman, _snowman, 6, 4, 5, 6, 4, 5, b, b, false);
         crl.v _snowmanx = this.l;

         for (int _snowmanxx = 1; _snowmanxx <= 5; _snowmanxx += 4) {
            int _snowmanxxx = 0;
            if (_snowmanx.c[gc.d.c()]) {
               this.a(_snowman, _snowman, 2, _snowmanxx, _snowmanxxx, 2, _snowmanxx + 2, _snowmanxxx, b, b, false);
               this.a(_snowman, _snowman, 5, _snowmanxx, _snowmanxxx, 5, _snowmanxx + 2, _snowmanxxx, b, b, false);
               this.a(_snowman, _snowman, 3, _snowmanxx + 2, _snowmanxxx, 4, _snowmanxx + 2, _snowmanxxx, b, b, false);
            } else {
               this.a(_snowman, _snowman, 0, _snowmanxx, _snowmanxxx, 7, _snowmanxx + 2, _snowmanxxx, b, b, false);
               this.a(_snowman, _snowman, 0, _snowmanxx + 1, _snowmanxxx, 7, _snowmanxx + 1, _snowmanxxx, a, a, false);
            }

            int var13 = 7;
            if (_snowmanx.c[gc.c.c()]) {
               this.a(_snowman, _snowman, 2, _snowmanxx, var13, 2, _snowmanxx + 2, var13, b, b, false);
               this.a(_snowman, _snowman, 5, _snowmanxx, var13, 5, _snowmanxx + 2, var13, b, b, false);
               this.a(_snowman, _snowman, 3, _snowmanxx + 2, var13, 4, _snowmanxx + 2, var13, b, b, false);
            } else {
               this.a(_snowman, _snowman, 0, _snowmanxx, var13, 7, _snowmanxx + 2, var13, b, b, false);
               this.a(_snowman, _snowman, 0, _snowmanxx + 1, var13, 7, _snowmanxx + 1, var13, a, a, false);
            }

            int _snowmanxxxx = 0;
            if (_snowmanx.c[gc.e.c()]) {
               this.a(_snowman, _snowman, _snowmanxxxx, _snowmanxx, 2, _snowmanxxxx, _snowmanxx + 2, 2, b, b, false);
               this.a(_snowman, _snowman, _snowmanxxxx, _snowmanxx, 5, _snowmanxxxx, _snowmanxx + 2, 5, b, b, false);
               this.a(_snowman, _snowman, _snowmanxxxx, _snowmanxx + 2, 3, _snowmanxxxx, _snowmanxx + 2, 4, b, b, false);
            } else {
               this.a(_snowman, _snowman, _snowmanxxxx, _snowmanxx, 0, _snowmanxxxx, _snowmanxx + 2, 7, b, b, false);
               this.a(_snowman, _snowman, _snowmanxxxx, _snowmanxx + 1, 0, _snowmanxxxx, _snowmanxx + 1, 7, a, a, false);
            }

            int var14 = 7;
            if (_snowmanx.c[gc.f.c()]) {
               this.a(_snowman, _snowman, var14, _snowmanxx, 2, var14, _snowmanxx + 2, 2, b, b, false);
               this.a(_snowman, _snowman, var14, _snowmanxx, 5, var14, _snowmanxx + 2, 5, b, b, false);
               this.a(_snowman, _snowman, var14, _snowmanxx + 2, 3, var14, _snowmanxx + 2, 4, b, b, false);
            } else {
               this.a(_snowman, _snowman, var14, _snowmanxx, 0, var14, _snowmanxx + 2, 7, b, b, false);
               this.a(_snowman, _snowman, var14, _snowmanxx + 1, 0, var14, _snowmanxx + 1, 7, a, a, false);
            }

            _snowmanx = _snowman;
         }

         return true;
      }
   }

   public static class n extends crl.r {
      public n(gc var1, crl.v var2) {
         super(clb.R, 1, _snowman, _snowman, 1, 2, 2);
      }

      public n(csw var1, md var2) {
         super(clb.R, _snowman);
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         crl.v _snowman = this.l.b[gc.c.c()];
         crl.v _snowmanx = this.l;
         crl.v _snowmanxx = _snowman.b[gc.b.c()];
         crl.v _snowmanxxx = _snowmanx.b[gc.b.c()];
         if (this.l.a / 25 > 0) {
            this.a(_snowman, _snowman, 0, 8, _snowman.c[gc.a.c()]);
            this.a(_snowman, _snowman, 0, 0, _snowmanx.c[gc.a.c()]);
         }

         if (_snowmanxxx.b[gc.b.c()] == null) {
            this.a(_snowman, _snowman, 1, 8, 1, 6, 8, 7, a);
         }

         if (_snowmanxx.b[gc.b.c()] == null) {
            this.a(_snowman, _snowman, 1, 8, 8, 6, 8, 14, a);
         }

         for (int _snowmanxxxx = 1; _snowmanxxxx <= 7; _snowmanxxxx++) {
            ceh _snowmanxxxxx = b;
            if (_snowmanxxxx == 2 || _snowmanxxxx == 6) {
               _snowmanxxxxx = a;
            }

            this.a(_snowman, _snowman, 0, _snowmanxxxx, 0, 0, _snowmanxxxx, 15, _snowmanxxxxx, _snowmanxxxxx, false);
            this.a(_snowman, _snowman, 7, _snowmanxxxx, 0, 7, _snowmanxxxx, 15, _snowmanxxxxx, _snowmanxxxxx, false);
            this.a(_snowman, _snowman, 1, _snowmanxxxx, 0, 6, _snowmanxxxx, 0, _snowmanxxxxx, _snowmanxxxxx, false);
            this.a(_snowman, _snowman, 1, _snowmanxxxx, 15, 6, _snowmanxxxx, 15, _snowmanxxxxx, _snowmanxxxxx, false);
         }

         for (int _snowmanxxxx = 1; _snowmanxxxx <= 7; _snowmanxxxx++) {
            ceh _snowmanxxxxx = c;
            if (_snowmanxxxx == 2 || _snowmanxxxx == 6) {
               _snowmanxxxxx = e;
            }

            this.a(_snowman, _snowman, 3, _snowmanxxxx, 7, 4, _snowmanxxxx, 8, _snowmanxxxxx, _snowmanxxxxx, false);
         }

         if (_snowmanx.c[gc.d.c()]) {
            this.a(_snowman, _snowman, 3, 1, 0, 4, 2, 0);
         }

         if (_snowmanx.c[gc.f.c()]) {
            this.a(_snowman, _snowman, 7, 1, 3, 7, 2, 4);
         }

         if (_snowmanx.c[gc.e.c()]) {
            this.a(_snowman, _snowman, 0, 1, 3, 0, 2, 4);
         }

         if (_snowman.c[gc.c.c()]) {
            this.a(_snowman, _snowman, 3, 1, 15, 4, 2, 15);
         }

         if (_snowman.c[gc.e.c()]) {
            this.a(_snowman, _snowman, 0, 1, 11, 0, 2, 12);
         }

         if (_snowman.c[gc.f.c()]) {
            this.a(_snowman, _snowman, 7, 1, 11, 7, 2, 12);
         }

         if (_snowmanxxx.c[gc.d.c()]) {
            this.a(_snowman, _snowman, 3, 5, 0, 4, 6, 0);
         }

         if (_snowmanxxx.c[gc.f.c()]) {
            this.a(_snowman, _snowman, 7, 5, 3, 7, 6, 4);
            this.a(_snowman, _snowman, 5, 4, 2, 6, 4, 5, b, b, false);
            this.a(_snowman, _snowman, 6, 1, 2, 6, 3, 2, b, b, false);
            this.a(_snowman, _snowman, 6, 1, 5, 6, 3, 5, b, b, false);
         }

         if (_snowmanxxx.c[gc.e.c()]) {
            this.a(_snowman, _snowman, 0, 5, 3, 0, 6, 4);
            this.a(_snowman, _snowman, 1, 4, 2, 2, 4, 5, b, b, false);
            this.a(_snowman, _snowman, 1, 1, 2, 1, 3, 2, b, b, false);
            this.a(_snowman, _snowman, 1, 1, 5, 1, 3, 5, b, b, false);
         }

         if (_snowmanxx.c[gc.c.c()]) {
            this.a(_snowman, _snowman, 3, 5, 15, 4, 6, 15);
         }

         if (_snowmanxx.c[gc.e.c()]) {
            this.a(_snowman, _snowman, 0, 5, 11, 0, 6, 12);
            this.a(_snowman, _snowman, 1, 4, 10, 2, 4, 13, b, b, false);
            this.a(_snowman, _snowman, 1, 1, 10, 1, 3, 10, b, b, false);
            this.a(_snowman, _snowman, 1, 1, 13, 1, 3, 13, b, b, false);
         }

         if (_snowmanxx.c[gc.f.c()]) {
            this.a(_snowman, _snowman, 7, 5, 11, 7, 6, 12);
            this.a(_snowman, _snowman, 5, 4, 10, 6, 4, 13, b, b, false);
            this.a(_snowman, _snowman, 6, 1, 10, 6, 3, 10, b, b, false);
            this.a(_snowman, _snowman, 6, 1, 13, 6, 3, 13, b, b, false);
         }

         return true;
      }
   }

   public static class o extends crl.r {
      public o(gc var1, crl.v var2) {
         super(clb.S, 1, _snowman, _snowman, 1, 1, 2);
      }

      public o(csw var1, md var2) {
         super(clb.S, _snowman);
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         crl.v _snowman = this.l.b[gc.c.c()];
         crl.v _snowmanx = this.l;
         if (this.l.a / 25 > 0) {
            this.a(_snowman, _snowman, 0, 8, _snowman.c[gc.a.c()]);
            this.a(_snowman, _snowman, 0, 0, _snowmanx.c[gc.a.c()]);
         }

         if (_snowmanx.b[gc.b.c()] == null) {
            this.a(_snowman, _snowman, 1, 4, 1, 6, 4, 7, a);
         }

         if (_snowman.b[gc.b.c()] == null) {
            this.a(_snowman, _snowman, 1, 4, 8, 6, 4, 14, a);
         }

         this.a(_snowman, _snowman, 0, 3, 0, 0, 3, 15, b, b, false);
         this.a(_snowman, _snowman, 7, 3, 0, 7, 3, 15, b, b, false);
         this.a(_snowman, _snowman, 1, 3, 0, 7, 3, 0, b, b, false);
         this.a(_snowman, _snowman, 1, 3, 15, 6, 3, 15, b, b, false);
         this.a(_snowman, _snowman, 0, 2, 0, 0, 2, 15, a, a, false);
         this.a(_snowman, _snowman, 7, 2, 0, 7, 2, 15, a, a, false);
         this.a(_snowman, _snowman, 1, 2, 0, 7, 2, 0, a, a, false);
         this.a(_snowman, _snowman, 1, 2, 15, 6, 2, 15, a, a, false);
         this.a(_snowman, _snowman, 0, 1, 0, 0, 1, 15, b, b, false);
         this.a(_snowman, _snowman, 7, 1, 0, 7, 1, 15, b, b, false);
         this.a(_snowman, _snowman, 1, 1, 0, 7, 1, 0, b, b, false);
         this.a(_snowman, _snowman, 1, 1, 15, 6, 1, 15, b, b, false);
         this.a(_snowman, _snowman, 1, 1, 1, 1, 1, 2, b, b, false);
         this.a(_snowman, _snowman, 6, 1, 1, 6, 1, 2, b, b, false);
         this.a(_snowman, _snowman, 1, 3, 1, 1, 3, 2, b, b, false);
         this.a(_snowman, _snowman, 6, 3, 1, 6, 3, 2, b, b, false);
         this.a(_snowman, _snowman, 1, 1, 13, 1, 1, 14, b, b, false);
         this.a(_snowman, _snowman, 6, 1, 13, 6, 1, 14, b, b, false);
         this.a(_snowman, _snowman, 1, 3, 13, 1, 3, 14, b, b, false);
         this.a(_snowman, _snowman, 6, 3, 13, 6, 3, 14, b, b, false);
         this.a(_snowman, _snowman, 2, 1, 6, 2, 3, 6, b, b, false);
         this.a(_snowman, _snowman, 5, 1, 6, 5, 3, 6, b, b, false);
         this.a(_snowman, _snowman, 2, 1, 9, 2, 3, 9, b, b, false);
         this.a(_snowman, _snowman, 5, 1, 9, 5, 3, 9, b, b, false);
         this.a(_snowman, _snowman, 3, 2, 6, 4, 2, 6, b, b, false);
         this.a(_snowman, _snowman, 3, 2, 9, 4, 2, 9, b, b, false);
         this.a(_snowman, _snowman, 2, 2, 7, 2, 2, 8, b, b, false);
         this.a(_snowman, _snowman, 5, 2, 7, 5, 2, 8, b, b, false);
         this.a(_snowman, e, 2, 2, 5, _snowman);
         this.a(_snowman, e, 5, 2, 5, _snowman);
         this.a(_snowman, e, 2, 2, 10, _snowman);
         this.a(_snowman, e, 5, 2, 10, _snowman);
         this.a(_snowman, b, 2, 3, 5, _snowman);
         this.a(_snowman, b, 5, 3, 5, _snowman);
         this.a(_snowman, b, 2, 3, 10, _snowman);
         this.a(_snowman, b, 5, 3, 10, _snowman);
         if (_snowmanx.c[gc.d.c()]) {
            this.a(_snowman, _snowman, 3, 1, 0, 4, 2, 0);
         }

         if (_snowmanx.c[gc.f.c()]) {
            this.a(_snowman, _snowman, 7, 1, 3, 7, 2, 4);
         }

         if (_snowmanx.c[gc.e.c()]) {
            this.a(_snowman, _snowman, 0, 1, 3, 0, 2, 4);
         }

         if (_snowman.c[gc.c.c()]) {
            this.a(_snowman, _snowman, 3, 1, 15, 4, 2, 15);
         }

         if (_snowman.c[gc.e.c()]) {
            this.a(_snowman, _snowman, 0, 1, 11, 0, 2, 12);
         }

         if (_snowman.c[gc.f.c()]) {
            this.a(_snowman, _snowman, 7, 1, 11, 7, 2, 12);
         }

         return true;
      }
   }

   public static class p extends crl.r {
      public p(gc var1, crl.v var2) {
         super(clb.T, 1, _snowman, _snowman, 1, 1, 1);
      }

      public p(csw var1, md var2) {
         super(clb.T, _snowman);
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         this.a(_snowman, _snowman, 0, 3, 0, 2, 3, 7, b, b, false);
         this.a(_snowman, _snowman, 5, 3, 0, 7, 3, 7, b, b, false);
         this.a(_snowman, _snowman, 0, 2, 0, 1, 2, 7, b, b, false);
         this.a(_snowman, _snowman, 6, 2, 0, 7, 2, 7, b, b, false);
         this.a(_snowman, _snowman, 0, 1, 0, 0, 1, 7, b, b, false);
         this.a(_snowman, _snowman, 7, 1, 0, 7, 1, 7, b, b, false);
         this.a(_snowman, _snowman, 0, 1, 7, 7, 3, 7, b, b, false);
         this.a(_snowman, _snowman, 1, 1, 0, 2, 3, 0, b, b, false);
         this.a(_snowman, _snowman, 5, 1, 0, 6, 3, 0, b, b, false);
         if (this.l.c[gc.c.c()]) {
            this.a(_snowman, _snowman, 3, 1, 7, 4, 2, 7);
         }

         if (this.l.c[gc.e.c()]) {
            this.a(_snowman, _snowman, 0, 1, 3, 1, 2, 4);
         }

         if (this.l.c[gc.f.c()]) {
            this.a(_snowman, _snowman, 6, 1, 3, 7, 2, 4);
         }

         return true;
      }
   }

   public static class q extends crl.r {
      public q(gc var1, cra var2) {
         super(clb.U, _snowman, _snowman);
      }

      public q(csw var1, md var2) {
         super(clb.U, _snowman);
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         this.a(_snowman, _snowman, 2, -1, 2, 11, -1, 11, b, b, false);
         this.a(_snowman, _snowman, 0, -1, 0, 1, -1, 11, a, a, false);
         this.a(_snowman, _snowman, 12, -1, 0, 13, -1, 11, a, a, false);
         this.a(_snowman, _snowman, 2, -1, 0, 11, -1, 1, a, a, false);
         this.a(_snowman, _snowman, 2, -1, 12, 11, -1, 13, a, a, false);
         this.a(_snowman, _snowman, 0, 0, 0, 0, 0, 13, b, b, false);
         this.a(_snowman, _snowman, 13, 0, 0, 13, 0, 13, b, b, false);
         this.a(_snowman, _snowman, 1, 0, 0, 12, 0, 0, b, b, false);
         this.a(_snowman, _snowman, 1, 0, 13, 12, 0, 13, b, b, false);

         for (int _snowman = 2; _snowman <= 11; _snowman += 3) {
            this.a(_snowman, e, 0, 0, _snowman, _snowman);
            this.a(_snowman, e, 13, 0, _snowman, _snowman);
            this.a(_snowman, e, _snowman, 0, 0, _snowman);
         }

         this.a(_snowman, _snowman, 2, 0, 3, 4, 0, 9, b, b, false);
         this.a(_snowman, _snowman, 9, 0, 3, 11, 0, 9, b, b, false);
         this.a(_snowman, _snowman, 4, 0, 9, 9, 0, 11, b, b, false);
         this.a(_snowman, b, 5, 0, 8, _snowman);
         this.a(_snowman, b, 8, 0, 8, _snowman);
         this.a(_snowman, b, 10, 0, 10, _snowman);
         this.a(_snowman, b, 3, 0, 10, _snowman);
         this.a(_snowman, _snowman, 3, 0, 3, 3, 0, 7, c, c, false);
         this.a(_snowman, _snowman, 10, 0, 3, 10, 0, 7, c, c, false);
         this.a(_snowman, _snowman, 6, 0, 10, 7, 0, 10, c, c, false);
         int _snowman = 3;

         for (int _snowmanx = 0; _snowmanx < 2; _snowmanx++) {
            for (int _snowmanxx = 2; _snowmanxx <= 8; _snowmanxx += 3) {
               this.a(_snowman, _snowman, _snowman, 0, _snowmanxx, _snowman, 2, _snowmanxx, b, b, false);
            }

            _snowman = 10;
         }

         this.a(_snowman, _snowman, 5, 0, 10, 5, 2, 10, b, b, false);
         this.a(_snowman, _snowman, 8, 0, 10, 8, 2, 10, b, b, false);
         this.a(_snowman, _snowman, 6, -1, 7, 7, -1, 8, c, c, false);
         this.a(_snowman, _snowman, 6, -1, 3, 7, -1, 4);
         this.a(_snowman, _snowman, 6, 1, 6);
         return true;
      }
   }

   public abstract static class r extends cru {
      protected static final ceh a = bup.gq.n();
      protected static final ceh b = bup.gr.n();
      protected static final ceh c = bup.gs.n();
      protected static final ceh d = b;
      protected static final ceh e = bup.gz.n();
      protected static final ceh f = bup.A.n();
      protected static final Set<buo> g = ImmutableSet.builder().add(bup.cD).add(bup.gT).add(bup.kV).add(f.b()).build();
      protected static final int h = b(2, 0, 0);
      protected static final int i = b(2, 2, 0);
      protected static final int j = b(0, 1, 0);
      protected static final int k = b(4, 1, 0);
      protected crl.v l;

      protected static final int b(int var0, int var1, int var2) {
         return _snowman * 25 + _snowman * 5 + _snowman;
      }

      public r(clb var1, int var2) {
         super(_snowman, _snowman);
      }

      public r(clb var1, gc var2, cra var3) {
         super(_snowman, 1);
         this.a(_snowman);
         this.n = _snowman;
      }

      protected r(clb var1, int var2, gc var3, crl.v var4, int var5, int var6, int var7) {
         super(_snowman, _snowman);
         this.a(_snowman);
         this.l = _snowman;
         int _snowman = _snowman.a;
         int _snowmanx = _snowman % 5;
         int _snowmanxx = _snowman / 5 % 5;
         int _snowmanxxx = _snowman / 25;
         if (_snowman != gc.c && _snowman != gc.d) {
            this.n = new cra(0, 0, 0, _snowman * 8 - 1, _snowman * 4 - 1, _snowman * 8 - 1);
         } else {
            this.n = new cra(0, 0, 0, _snowman * 8 - 1, _snowman * 4 - 1, _snowman * 8 - 1);
         }

         switch (_snowman) {
            case c:
               this.n.a(_snowmanx * 8, _snowmanxxx * 4, -(_snowmanxx + _snowman) * 8 + 1);
               break;
            case d:
               this.n.a(_snowmanx * 8, _snowmanxxx * 4, _snowmanxx * 8);
               break;
            case e:
               this.n.a(-(_snowmanxx + _snowman) * 8 + 1, _snowmanxxx * 4, _snowmanx * 8);
               break;
            default:
               this.n.a(_snowmanxx * 8, _snowmanxxx * 4, _snowmanx * 8);
         }
      }

      public r(clb var1, md var2) {
         super(_snowman, _snowman);
      }

      @Override
      protected void a(md var1) {
      }

      protected void a(bsr var1, cra var2, int var3, int var4, int var5, int var6, int var7, int var8) {
         for (int _snowman = _snowman; _snowman <= _snowman; _snowman++) {
            for (int _snowmanx = _snowman; _snowmanx <= _snowman; _snowmanx++) {
               for (int _snowmanxx = _snowman; _snowmanxx <= _snowman; _snowmanxx++) {
                  ceh _snowmanxxx = this.a(_snowman, _snowmanx, _snowman, _snowmanxx, _snowman);
                  if (!g.contains(_snowmanxxx.b())) {
                     if (this.d(_snowman) >= _snowman.t_() && _snowmanxxx != f) {
                        this.a(_snowman, bup.a.n(), _snowmanx, _snowman, _snowmanxx, _snowman);
                     } else {
                        this.a(_snowman, f, _snowmanx, _snowman, _snowmanxx, _snowman);
                     }
                  }
               }
            }
         }
      }

      protected void a(bsr var1, cra var2, int var3, int var4, boolean var5) {
         if (_snowman) {
            this.a(_snowman, _snowman, _snowman + 0, 0, _snowman + 0, _snowman + 2, 0, _snowman + 8 - 1, a, a, false);
            this.a(_snowman, _snowman, _snowman + 5, 0, _snowman + 0, _snowman + 8 - 1, 0, _snowman + 8 - 1, a, a, false);
            this.a(_snowman, _snowman, _snowman + 3, 0, _snowman + 0, _snowman + 4, 0, _snowman + 2, a, a, false);
            this.a(_snowman, _snowman, _snowman + 3, 0, _snowman + 5, _snowman + 4, 0, _snowman + 8 - 1, a, a, false);
            this.a(_snowman, _snowman, _snowman + 3, 0, _snowman + 2, _snowman + 4, 0, _snowman + 2, b, b, false);
            this.a(_snowman, _snowman, _snowman + 3, 0, _snowman + 5, _snowman + 4, 0, _snowman + 5, b, b, false);
            this.a(_snowman, _snowman, _snowman + 2, 0, _snowman + 3, _snowman + 2, 0, _snowman + 4, b, b, false);
            this.a(_snowman, _snowman, _snowman + 5, 0, _snowman + 3, _snowman + 5, 0, _snowman + 4, b, b, false);
         } else {
            this.a(_snowman, _snowman, _snowman + 0, 0, _snowman + 0, _snowman + 8 - 1, 0, _snowman + 8 - 1, a, a, false);
         }
      }

      protected void a(bsr var1, cra var2, int var3, int var4, int var5, int var6, int var7, int var8, ceh var9) {
         for (int _snowman = _snowman; _snowman <= _snowman; _snowman++) {
            for (int _snowmanx = _snowman; _snowmanx <= _snowman; _snowmanx++) {
               for (int _snowmanxx = _snowman; _snowmanxx <= _snowman; _snowmanxx++) {
                  if (this.a(_snowman, _snowmanx, _snowman, _snowmanxx, _snowman) == f) {
                     this.a(_snowman, _snowman, _snowmanx, _snowman, _snowmanxx, _snowman);
                  }
               }
            }
         }
      }

      protected boolean a(cra var1, int var2, int var3, int var4, int var5) {
         int _snowman = this.a(_snowman, _snowman);
         int _snowmanx = this.b(_snowman, _snowman);
         int _snowmanxx = this.a(_snowman, _snowman);
         int _snowmanxxx = this.b(_snowman, _snowman);
         return _snowman.a(Math.min(_snowman, _snowmanxx), Math.min(_snowmanx, _snowmanxxx), Math.max(_snowman, _snowmanxx), Math.max(_snowmanx, _snowmanxxx));
      }

      protected boolean a(bsr var1, cra var2, int var3, int var4, int var5) {
         int _snowman = this.a(_snowman, _snowman);
         int _snowmanx = this.d(_snowman);
         int _snowmanxx = this.b(_snowman, _snowman);
         if (_snowman.b(new fx(_snowman, _snowmanx, _snowmanxx))) {
            bdf _snowmanxxx = aqe.r.a(_snowman.E());
            _snowmanxxx.b(_snowmanxxx.dx());
            _snowmanxxx.b((double)_snowman + 0.5, (double)_snowmanx, (double)_snowmanxx + 0.5, 0.0F, 0.0F);
            _snowmanxxx.a(_snowman, _snowman.d(_snowmanxxx.cB()), aqp.d, null, null);
            _snowman.l(_snowmanxxx);
            return true;
         } else {
            return false;
         }
      }
   }

   public static class s extends crl.r {
      private int p;

      public s(gc var1, crl.v var2, Random var3) {
         super(clb.V, 1, _snowman, _snowman, 1, 1, 1);
         this.p = _snowman.nextInt(3);
      }

      public s(csw var1, md var2) {
         super(clb.V, _snowman);
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         if (this.l.a / 25 > 0) {
            this.a(_snowman, _snowman, 0, 0, this.l.c[gc.a.c()]);
         }

         if (this.l.b[gc.b.c()] == null) {
            this.a(_snowman, _snowman, 1, 4, 1, 6, 4, 6, a);
         }

         boolean _snowman = this.p != 0 && _snowman.nextBoolean() && !this.l.c[gc.a.c()] && !this.l.c[gc.b.c()] && this.l.c() > 1;
         if (this.p == 0) {
            this.a(_snowman, _snowman, 0, 1, 0, 2, 1, 2, b, b, false);
            this.a(_snowman, _snowman, 0, 3, 0, 2, 3, 2, b, b, false);
            this.a(_snowman, _snowman, 0, 2, 0, 0, 2, 2, a, a, false);
            this.a(_snowman, _snowman, 1, 2, 0, 2, 2, 0, a, a, false);
            this.a(_snowman, e, 1, 2, 1, _snowman);
            this.a(_snowman, _snowman, 5, 1, 0, 7, 1, 2, b, b, false);
            this.a(_snowman, _snowman, 5, 3, 0, 7, 3, 2, b, b, false);
            this.a(_snowman, _snowman, 7, 2, 0, 7, 2, 2, a, a, false);
            this.a(_snowman, _snowman, 5, 2, 0, 6, 2, 0, a, a, false);
            this.a(_snowman, e, 6, 2, 1, _snowman);
            this.a(_snowman, _snowman, 0, 1, 5, 2, 1, 7, b, b, false);
            this.a(_snowman, _snowman, 0, 3, 5, 2, 3, 7, b, b, false);
            this.a(_snowman, _snowman, 0, 2, 5, 0, 2, 7, a, a, false);
            this.a(_snowman, _snowman, 1, 2, 7, 2, 2, 7, a, a, false);
            this.a(_snowman, e, 1, 2, 6, _snowman);
            this.a(_snowman, _snowman, 5, 1, 5, 7, 1, 7, b, b, false);
            this.a(_snowman, _snowman, 5, 3, 5, 7, 3, 7, b, b, false);
            this.a(_snowman, _snowman, 7, 2, 5, 7, 2, 7, a, a, false);
            this.a(_snowman, _snowman, 5, 2, 7, 6, 2, 7, a, a, false);
            this.a(_snowman, e, 6, 2, 6, _snowman);
            if (this.l.c[gc.d.c()]) {
               this.a(_snowman, _snowman, 3, 3, 0, 4, 3, 0, b, b, false);
            } else {
               this.a(_snowman, _snowman, 3, 3, 0, 4, 3, 1, b, b, false);
               this.a(_snowman, _snowman, 3, 2, 0, 4, 2, 0, a, a, false);
               this.a(_snowman, _snowman, 3, 1, 0, 4, 1, 1, b, b, false);
            }

            if (this.l.c[gc.c.c()]) {
               this.a(_snowman, _snowman, 3, 3, 7, 4, 3, 7, b, b, false);
            } else {
               this.a(_snowman, _snowman, 3, 3, 6, 4, 3, 7, b, b, false);
               this.a(_snowman, _snowman, 3, 2, 7, 4, 2, 7, a, a, false);
               this.a(_snowman, _snowman, 3, 1, 6, 4, 1, 7, b, b, false);
            }

            if (this.l.c[gc.e.c()]) {
               this.a(_snowman, _snowman, 0, 3, 3, 0, 3, 4, b, b, false);
            } else {
               this.a(_snowman, _snowman, 0, 3, 3, 1, 3, 4, b, b, false);
               this.a(_snowman, _snowman, 0, 2, 3, 0, 2, 4, a, a, false);
               this.a(_snowman, _snowman, 0, 1, 3, 1, 1, 4, b, b, false);
            }

            if (this.l.c[gc.f.c()]) {
               this.a(_snowman, _snowman, 7, 3, 3, 7, 3, 4, b, b, false);
            } else {
               this.a(_snowman, _snowman, 6, 3, 3, 7, 3, 4, b, b, false);
               this.a(_snowman, _snowman, 7, 2, 3, 7, 2, 4, a, a, false);
               this.a(_snowman, _snowman, 6, 1, 3, 7, 1, 4, b, b, false);
            }
         } else if (this.p == 1) {
            this.a(_snowman, _snowman, 2, 1, 2, 2, 3, 2, b, b, false);
            this.a(_snowman, _snowman, 2, 1, 5, 2, 3, 5, b, b, false);
            this.a(_snowman, _snowman, 5, 1, 5, 5, 3, 5, b, b, false);
            this.a(_snowman, _snowman, 5, 1, 2, 5, 3, 2, b, b, false);
            this.a(_snowman, e, 2, 2, 2, _snowman);
            this.a(_snowman, e, 2, 2, 5, _snowman);
            this.a(_snowman, e, 5, 2, 5, _snowman);
            this.a(_snowman, e, 5, 2, 2, _snowman);
            this.a(_snowman, _snowman, 0, 1, 0, 1, 3, 0, b, b, false);
            this.a(_snowman, _snowman, 0, 1, 1, 0, 3, 1, b, b, false);
            this.a(_snowman, _snowman, 0, 1, 7, 1, 3, 7, b, b, false);
            this.a(_snowman, _snowman, 0, 1, 6, 0, 3, 6, b, b, false);
            this.a(_snowman, _snowman, 6, 1, 7, 7, 3, 7, b, b, false);
            this.a(_snowman, _snowman, 7, 1, 6, 7, 3, 6, b, b, false);
            this.a(_snowman, _snowman, 6, 1, 0, 7, 3, 0, b, b, false);
            this.a(_snowman, _snowman, 7, 1, 1, 7, 3, 1, b, b, false);
            this.a(_snowman, a, 1, 2, 0, _snowman);
            this.a(_snowman, a, 0, 2, 1, _snowman);
            this.a(_snowman, a, 1, 2, 7, _snowman);
            this.a(_snowman, a, 0, 2, 6, _snowman);
            this.a(_snowman, a, 6, 2, 7, _snowman);
            this.a(_snowman, a, 7, 2, 6, _snowman);
            this.a(_snowman, a, 6, 2, 0, _snowman);
            this.a(_snowman, a, 7, 2, 1, _snowman);
            if (!this.l.c[gc.d.c()]) {
               this.a(_snowman, _snowman, 1, 3, 0, 6, 3, 0, b, b, false);
               this.a(_snowman, _snowman, 1, 2, 0, 6, 2, 0, a, a, false);
               this.a(_snowman, _snowman, 1, 1, 0, 6, 1, 0, b, b, false);
            }

            if (!this.l.c[gc.c.c()]) {
               this.a(_snowman, _snowman, 1, 3, 7, 6, 3, 7, b, b, false);
               this.a(_snowman, _snowman, 1, 2, 7, 6, 2, 7, a, a, false);
               this.a(_snowman, _snowman, 1, 1, 7, 6, 1, 7, b, b, false);
            }

            if (!this.l.c[gc.e.c()]) {
               this.a(_snowman, _snowman, 0, 3, 1, 0, 3, 6, b, b, false);
               this.a(_snowman, _snowman, 0, 2, 1, 0, 2, 6, a, a, false);
               this.a(_snowman, _snowman, 0, 1, 1, 0, 1, 6, b, b, false);
            }

            if (!this.l.c[gc.f.c()]) {
               this.a(_snowman, _snowman, 7, 3, 1, 7, 3, 6, b, b, false);
               this.a(_snowman, _snowman, 7, 2, 1, 7, 2, 6, a, a, false);
               this.a(_snowman, _snowman, 7, 1, 1, 7, 1, 6, b, b, false);
            }
         } else if (this.p == 2) {
            this.a(_snowman, _snowman, 0, 1, 0, 0, 1, 7, b, b, false);
            this.a(_snowman, _snowman, 7, 1, 0, 7, 1, 7, b, b, false);
            this.a(_snowman, _snowman, 1, 1, 0, 6, 1, 0, b, b, false);
            this.a(_snowman, _snowman, 1, 1, 7, 6, 1, 7, b, b, false);
            this.a(_snowman, _snowman, 0, 2, 0, 0, 2, 7, c, c, false);
            this.a(_snowman, _snowman, 7, 2, 0, 7, 2, 7, c, c, false);
            this.a(_snowman, _snowman, 1, 2, 0, 6, 2, 0, c, c, false);
            this.a(_snowman, _snowman, 1, 2, 7, 6, 2, 7, c, c, false);
            this.a(_snowman, _snowman, 0, 3, 0, 0, 3, 7, b, b, false);
            this.a(_snowman, _snowman, 7, 3, 0, 7, 3, 7, b, b, false);
            this.a(_snowman, _snowman, 1, 3, 0, 6, 3, 0, b, b, false);
            this.a(_snowman, _snowman, 1, 3, 7, 6, 3, 7, b, b, false);
            this.a(_snowman, _snowman, 0, 1, 3, 0, 2, 4, c, c, false);
            this.a(_snowman, _snowman, 7, 1, 3, 7, 2, 4, c, c, false);
            this.a(_snowman, _snowman, 3, 1, 0, 4, 2, 0, c, c, false);
            this.a(_snowman, _snowman, 3, 1, 7, 4, 2, 7, c, c, false);
            if (this.l.c[gc.d.c()]) {
               this.a(_snowman, _snowman, 3, 1, 0, 4, 2, 0);
            }

            if (this.l.c[gc.c.c()]) {
               this.a(_snowman, _snowman, 3, 1, 7, 4, 2, 7);
            }

            if (this.l.c[gc.e.c()]) {
               this.a(_snowman, _snowman, 0, 1, 3, 0, 2, 4);
            }

            if (this.l.c[gc.f.c()]) {
               this.a(_snowman, _snowman, 7, 1, 3, 7, 2, 4);
            }
         }

         if (_snowman) {
            this.a(_snowman, _snowman, 3, 1, 3, 4, 1, 4, b, b, false);
            this.a(_snowman, _snowman, 3, 2, 3, 4, 2, 4, a, a, false);
            this.a(_snowman, _snowman, 3, 3, 3, 4, 3, 4, b, b, false);
         }

         return true;
      }
   }

   public static class t extends crl.r {
      public t(gc var1, crl.v var2) {
         super(clb.W, 1, _snowman, _snowman, 1, 1, 1);
      }

      public t(csw var1, md var2) {
         super(clb.W, _snowman);
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         if (this.l.a / 25 > 0) {
            this.a(_snowman, _snowman, 0, 0, this.l.c[gc.a.c()]);
         }

         if (this.l.b[gc.b.c()] == null) {
            this.a(_snowman, _snowman, 1, 4, 1, 6, 4, 6, a);
         }

         for (int _snowman = 1; _snowman <= 6; _snowman++) {
            for (int _snowmanx = 1; _snowmanx <= 6; _snowmanx++) {
               if (_snowman.nextInt(3) != 0) {
                  int _snowmanxx = 2 + (_snowman.nextInt(4) == 0 ? 0 : 1);
                  ceh _snowmanxxx = bup.ao.n();
                  this.a(_snowman, _snowman, _snowman, _snowmanxx, _snowmanx, _snowman, 3, _snowmanx, _snowmanxxx, _snowmanxxx, false);
               }
            }
         }

         this.a(_snowman, _snowman, 0, 1, 0, 0, 1, 7, b, b, false);
         this.a(_snowman, _snowman, 7, 1, 0, 7, 1, 7, b, b, false);
         this.a(_snowman, _snowman, 1, 1, 0, 6, 1, 0, b, b, false);
         this.a(_snowman, _snowman, 1, 1, 7, 6, 1, 7, b, b, false);
         this.a(_snowman, _snowman, 0, 2, 0, 0, 2, 7, c, c, false);
         this.a(_snowman, _snowman, 7, 2, 0, 7, 2, 7, c, c, false);
         this.a(_snowman, _snowman, 1, 2, 0, 6, 2, 0, c, c, false);
         this.a(_snowman, _snowman, 1, 2, 7, 6, 2, 7, c, c, false);
         this.a(_snowman, _snowman, 0, 3, 0, 0, 3, 7, b, b, false);
         this.a(_snowman, _snowman, 7, 3, 0, 7, 3, 7, b, b, false);
         this.a(_snowman, _snowman, 1, 3, 0, 6, 3, 0, b, b, false);
         this.a(_snowman, _snowman, 1, 3, 7, 6, 3, 7, b, b, false);
         this.a(_snowman, _snowman, 0, 1, 3, 0, 2, 4, c, c, false);
         this.a(_snowman, _snowman, 7, 1, 3, 7, 2, 4, c, c, false);
         this.a(_snowman, _snowman, 3, 1, 0, 4, 2, 0, c, c, false);
         this.a(_snowman, _snowman, 3, 1, 7, 4, 2, 7, c, c, false);
         if (this.l.c[gc.d.c()]) {
            this.a(_snowman, _snowman, 3, 1, 0, 4, 2, 0);
         }

         return true;
      }
   }

   public static class u extends crl.r {
      private int p;

      public u(gc var1, cra var2, int var3) {
         super(clb.X, _snowman, _snowman);
         this.p = _snowman & 1;
      }

      public u(csw var1, md var2) {
         super(clb.X, _snowman);
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         if (this.p == 0) {
            for (int _snowman = 0; _snowman < 4; _snowman++) {
               this.a(_snowman, _snowman, 10 - _snowman, 3 - _snowman, 20 - _snowman, 12 + _snowman, 3 - _snowman, 20, b, b, false);
            }

            this.a(_snowman, _snowman, 7, 0, 6, 15, 0, 16, b, b, false);
            this.a(_snowman, _snowman, 6, 0, 6, 6, 3, 20, b, b, false);
            this.a(_snowman, _snowman, 16, 0, 6, 16, 3, 20, b, b, false);
            this.a(_snowman, _snowman, 7, 1, 7, 7, 1, 20, b, b, false);
            this.a(_snowman, _snowman, 15, 1, 7, 15, 1, 20, b, b, false);
            this.a(_snowman, _snowman, 7, 1, 6, 9, 3, 6, b, b, false);
            this.a(_snowman, _snowman, 13, 1, 6, 15, 3, 6, b, b, false);
            this.a(_snowman, _snowman, 8, 1, 7, 9, 1, 7, b, b, false);
            this.a(_snowman, _snowman, 13, 1, 7, 14, 1, 7, b, b, false);
            this.a(_snowman, _snowman, 9, 0, 5, 13, 0, 5, b, b, false);
            this.a(_snowman, _snowman, 10, 0, 7, 12, 0, 7, c, c, false);
            this.a(_snowman, _snowman, 8, 0, 10, 8, 0, 12, c, c, false);
            this.a(_snowman, _snowman, 14, 0, 10, 14, 0, 12, c, c, false);

            for (int _snowman = 18; _snowman >= 7; _snowman -= 3) {
               this.a(_snowman, e, 6, 3, _snowman, _snowman);
               this.a(_snowman, e, 16, 3, _snowman, _snowman);
            }

            this.a(_snowman, e, 10, 0, 10, _snowman);
            this.a(_snowman, e, 12, 0, 10, _snowman);
            this.a(_snowman, e, 10, 0, 12, _snowman);
            this.a(_snowman, e, 12, 0, 12, _snowman);
            this.a(_snowman, e, 8, 3, 6, _snowman);
            this.a(_snowman, e, 14, 3, 6, _snowman);
            this.a(_snowman, b, 4, 2, 4, _snowman);
            this.a(_snowman, e, 4, 1, 4, _snowman);
            this.a(_snowman, b, 4, 0, 4, _snowman);
            this.a(_snowman, b, 18, 2, 4, _snowman);
            this.a(_snowman, e, 18, 1, 4, _snowman);
            this.a(_snowman, b, 18, 0, 4, _snowman);
            this.a(_snowman, b, 4, 2, 18, _snowman);
            this.a(_snowman, e, 4, 1, 18, _snowman);
            this.a(_snowman, b, 4, 0, 18, _snowman);
            this.a(_snowman, b, 18, 2, 18, _snowman);
            this.a(_snowman, e, 18, 1, 18, _snowman);
            this.a(_snowman, b, 18, 0, 18, _snowman);
            this.a(_snowman, b, 9, 7, 20, _snowman);
            this.a(_snowman, b, 13, 7, 20, _snowman);
            this.a(_snowman, _snowman, 6, 0, 21, 7, 4, 21, b, b, false);
            this.a(_snowman, _snowman, 15, 0, 21, 16, 4, 21, b, b, false);
            this.a(_snowman, _snowman, 11, 2, 16);
         } else if (this.p == 1) {
            this.a(_snowman, _snowman, 9, 3, 18, 13, 3, 20, b, b, false);
            this.a(_snowman, _snowman, 9, 0, 18, 9, 2, 18, b, b, false);
            this.a(_snowman, _snowman, 13, 0, 18, 13, 2, 18, b, b, false);
            int _snowman = 9;
            int _snowmanx = 20;
            int _snowmanxx = 5;

            for (int _snowmanxxx = 0; _snowmanxxx < 2; _snowmanxxx++) {
               this.a(_snowman, b, _snowman, 6, 20, _snowman);
               this.a(_snowman, e, _snowman, 5, 20, _snowman);
               this.a(_snowman, b, _snowman, 4, 20, _snowman);
               _snowman = 13;
            }

            this.a(_snowman, _snowman, 7, 3, 7, 15, 3, 14, b, b, false);
            int var14 = 10;

            for (int _snowmanxxx = 0; _snowmanxxx < 2; _snowmanxxx++) {
               this.a(_snowman, _snowman, var14, 0, 10, var14, 6, 10, b, b, false);
               this.a(_snowman, _snowman, var14, 0, 12, var14, 6, 12, b, b, false);
               this.a(_snowman, e, var14, 0, 10, _snowman);
               this.a(_snowman, e, var14, 0, 12, _snowman);
               this.a(_snowman, e, var14, 4, 10, _snowman);
               this.a(_snowman, e, var14, 4, 12, _snowman);
               var14 = 12;
            }

            var14 = 8;

            for (int _snowmanxxx = 0; _snowmanxxx < 2; _snowmanxxx++) {
               this.a(_snowman, _snowman, var14, 0, 7, var14, 2, 7, b, b, false);
               this.a(_snowman, _snowman, var14, 0, 14, var14, 2, 14, b, b, false);
               var14 = 14;
            }

            this.a(_snowman, _snowman, 8, 3, 8, 8, 3, 13, c, c, false);
            this.a(_snowman, _snowman, 14, 3, 8, 14, 3, 13, c, c, false);
            this.a(_snowman, _snowman, 11, 5, 13);
         }

         return true;
      }
   }

   static class v {
      private final int a;
      private final crl.v[] b = new crl.v[6];
      private final boolean[] c = new boolean[6];
      private boolean d;
      private boolean e;
      private int f;

      public v(int var1) {
         this.a = _snowman;
      }

      public void a(gc var1, crl.v var2) {
         this.b[_snowman.c()] = _snowman;
         _snowman.b[_snowman.f().c()] = this;
      }

      public void a() {
         for (int _snowman = 0; _snowman < 6; _snowman++) {
            this.c[_snowman] = this.b[_snowman] != null;
         }
      }

      public boolean a(int var1) {
         if (this.e) {
            return true;
         } else {
            this.f = _snowman;

            for (int _snowman = 0; _snowman < 6; _snowman++) {
               if (this.b[_snowman] != null && this.c[_snowman] && this.b[_snowman].f != _snowman && this.b[_snowman].a(_snowman)) {
                  return true;
               }
            }

            return false;
         }
      }

      public boolean b() {
         return this.a >= 75;
      }

      public int c() {
         int _snowman = 0;

         for (int _snowmanx = 0; _snowmanx < 6; _snowmanx++) {
            if (this.c[_snowmanx]) {
               _snowman++;
            }
         }

         return _snowman;
      }
   }
}
