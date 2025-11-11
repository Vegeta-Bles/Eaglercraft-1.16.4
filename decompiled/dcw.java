public abstract class dcw {
   private static final gc.a[] d = gc.a.values();
   protected final int a;
   protected final int b;
   protected final int c;

   protected dcw(int var1, int var2, int var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public boolean a(fv var1, int var2, int var3, int var4) {
      return this.c(_snowman.a(_snowman, _snowman, _snowman, gc.a.a), _snowman.a(_snowman, _snowman, _snowman, gc.a.b), _snowman.a(_snowman, _snowman, _snowman, gc.a.c));
   }

   public boolean c(int var1, int var2, int var3) {
      if (_snowman < 0 || _snowman < 0 || _snowman < 0) {
         return false;
      } else {
         return _snowman < this.a && _snowman < this.b && _snowman < this.c ? this.b(_snowman, _snowman, _snowman) : false;
      }
   }

   public boolean b(fv var1, int var2, int var3, int var4) {
      return this.b(_snowman.a(_snowman, _snowman, _snowman, gc.a.a), _snowman.a(_snowman, _snowman, _snowman, gc.a.b), _snowman.a(_snowman, _snowman, _snowman, gc.a.c));
   }

   public abstract boolean b(int var1, int var2, int var3);

   public abstract void a(int var1, int var2, int var3, boolean var4, boolean var5);

   public boolean a() {
      for (gc.a _snowman : d) {
         if (this.a(_snowman) >= this.b(_snowman)) {
            return true;
         }
      }

      return false;
   }

   public abstract int a(gc.a var1);

   public abstract int b(gc.a var1);

   public int b(gc.a var1, int var2, int var3) {
      if (_snowman >= 0 && _snowman >= 0) {
         gc.a _snowman = fv.b.a(_snowman);
         gc.a _snowmanx = fv.c.a(_snowman);
         if (_snowman < this.c(_snowman) && _snowman < this.c(_snowmanx)) {
            int _snowmanxx = this.c(_snowman);
            fv _snowmanxxx = fv.a(gc.a.a, _snowman);

            for (int _snowmanxxxx = _snowmanxx - 1; _snowmanxxxx >= 0; _snowmanxxxx--) {
               if (this.b(_snowmanxxx, _snowmanxxxx, _snowman, _snowman)) {
                  return _snowmanxxxx + 1;
               }
            }

            return 0;
         } else {
            return 0;
         }
      } else {
         return 0;
      }
   }

   public int c(gc.a var1) {
      return _snowman.a(this.a, this.b, this.c);
   }

   public int b() {
      return this.c(gc.a.a);
   }

   public int c() {
      return this.c(gc.a.b);
   }

   public int d() {
      return this.c(gc.a.c);
   }

   public void a(dcw.b var1, boolean var2) {
      this.a(_snowman, fv.a, _snowman);
      this.a(_snowman, fv.b, _snowman);
      this.a(_snowman, fv.c, _snowman);
   }

   private void a(dcw.b var1, fv var2, boolean var3) {
      fv _snowman = _snowman.a();
      int _snowmanx = this.c(_snowman.a(gc.a.a));
      int _snowmanxx = this.c(_snowman.a(gc.a.b));
      int _snowmanxxx = this.c(_snowman.a(gc.a.c));

      for (int _snowmanxxxx = 0; _snowmanxxxx <= _snowmanx; _snowmanxxxx++) {
         for (int _snowmanxxxxx = 0; _snowmanxxxxx <= _snowmanxx; _snowmanxxxxx++) {
            int _snowmanxxxxxx = -1;

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx <= _snowmanxxx; _snowmanxxxxxxx++) {
               int _snowmanxxxxxxxx = 0;
               int _snowmanxxxxxxxxx = 0;

               for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx <= 1; _snowmanxxxxxxxxxx++) {
                  for (int _snowmanxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxx <= 1; _snowmanxxxxxxxxxxx++) {
                     if (this.a(_snowman, _snowmanxxxx + _snowmanxxxxxxxxxx - 1, _snowmanxxxxx + _snowmanxxxxxxxxxxx - 1, _snowmanxxxxxxx)) {
                        _snowmanxxxxxxxx++;
                        _snowmanxxxxxxxxx ^= _snowmanxxxxxxxxxx ^ _snowmanxxxxxxxxxxx;
                     }
                  }
               }

               if (_snowmanxxxxxxxx == 1 || _snowmanxxxxxxxx == 3 || _snowmanxxxxxxxx == 2 && (_snowmanxxxxxxxxx & 1) == 0) {
                  if (_snowman) {
                     if (_snowmanxxxxxx == -1) {
                        _snowmanxxxxxx = _snowmanxxxxxxx;
                     }
                  } else {
                     _snowman.consume(
                        _snowman.a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx, gc.a.a),
                        _snowman.a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx, gc.a.b),
                        _snowman.a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx, gc.a.c),
                        _snowman.a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx + 1, gc.a.a),
                        _snowman.a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx + 1, gc.a.b),
                        _snowman.a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx + 1, gc.a.c)
                     );
                  }
               } else if (_snowmanxxxxxx != -1) {
                  _snowman.consume(
                     _snowman.a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, gc.a.a),
                     _snowman.a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, gc.a.b),
                     _snowman.a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, gc.a.c),
                     _snowman.a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx, gc.a.a),
                     _snowman.a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx, gc.a.b),
                     _snowman.a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxxx, gc.a.c)
                  );
                  _snowmanxxxxxx = -1;
               }
            }
         }
      }
   }

   protected boolean a(int var1, int var2, int var3, int var4) {
      for (int _snowman = _snowman; _snowman < _snowman; _snowman++) {
         if (!this.c(_snowman, _snowman, _snowman)) {
            return false;
         }
      }

      return true;
   }

   protected void a(int var1, int var2, int var3, int var4, boolean var5) {
      for (int _snowman = _snowman; _snowman < _snowman; _snowman++) {
         this.a(_snowman, _snowman, _snowman, false, _snowman);
      }
   }

   protected boolean a(int var1, int var2, int var3, int var4, int var5) {
      for (int _snowman = _snowman; _snowman < _snowman; _snowman++) {
         if (!this.a(_snowman, _snowman, _snowman, _snowman)) {
            return false;
         }
      }

      return true;
   }

   public void b(dcw.b var1, boolean var2) {
      dcw _snowman = new dcq(this);

      for (int _snowmanx = 0; _snowmanx <= this.a; _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx <= this.b; _snowmanxx++) {
            int _snowmanxxx = -1;

            for (int _snowmanxxxx = 0; _snowmanxxxx <= this.c; _snowmanxxxx++) {
               if (_snowman.c(_snowmanx, _snowmanxx, _snowmanxxxx)) {
                  if (_snowman) {
                     if (_snowmanxxx == -1) {
                        _snowmanxxx = _snowmanxxxx;
                     }
                  } else {
                     _snowman.consume(_snowmanx, _snowmanxx, _snowmanxxxx, _snowmanx + 1, _snowmanxx + 1, _snowmanxxxx + 1);
                  }
               } else if (_snowmanxxx != -1) {
                  int _snowmanxxxxx = _snowmanx;
                  int _snowmanxxxxxx = _snowmanx;
                  int _snowmanxxxxxxx = _snowmanxx;
                  int _snowmanxxxxxxxx = _snowmanxx;
                  _snowman.a(_snowmanxxx, _snowmanxxxx, _snowmanx, _snowmanxx, false);

                  while (_snowman.a(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx - 1, _snowmanxxxxxxx)) {
                     _snowman.a(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx - 1, _snowmanxxxxxxx, false);
                     _snowmanxxxxx--;
                  }

                  while (_snowman.a(_snowmanxxx, _snowmanxxxx, _snowmanxxxxxx + 1, _snowmanxxxxxxx)) {
                     _snowman.a(_snowmanxxx, _snowmanxxxx, _snowmanxxxxxx + 1, _snowmanxxxxxxx, false);
                     _snowmanxxxxxx++;
                  }

                  while (_snowman.a(_snowmanxxxxx, _snowmanxxxxxx + 1, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxxx - 1)) {
                     for (int _snowmanxxxxxxxxx = _snowmanxxxxx; _snowmanxxxxxxxxx <= _snowmanxxxxxx; _snowmanxxxxxxxxx++) {
                        _snowman.a(_snowmanxxx, _snowmanxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxx - 1, false);
                     }

                     _snowmanxxxxxxx--;
                  }

                  while (_snowman.a(_snowmanxxxxx, _snowmanxxxxxx + 1, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxxxx + 1)) {
                     for (int _snowmanxxxxxxxxx = _snowmanxxxxx; _snowmanxxxxxxxxx <= _snowmanxxxxxx; _snowmanxxxxxxxxx++) {
                        _snowman.a(_snowmanxxx, _snowmanxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx + 1, false);
                     }

                     _snowmanxxxxxxxx++;
                  }

                  _snowman.consume(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxx, _snowmanxxxxxx + 1, _snowmanxxxxxxxx + 1, _snowmanxxxx);
                  _snowmanxxx = -1;
               }
            }
         }
      }
   }

   public void a(dcw.a var1) {
      this.a(_snowman, fv.a);
      this.a(_snowman, fv.b);
      this.a(_snowman, fv.c);
   }

   private void a(dcw.a var1, fv var2) {
      fv _snowman = _snowman.a();
      gc.a _snowmanx = _snowman.a(gc.a.c);
      int _snowmanxx = this.c(_snowman.a(gc.a.a));
      int _snowmanxxx = this.c(_snowman.a(gc.a.b));
      int _snowmanxxxx = this.c(_snowmanx);
      gc _snowmanxxxxx = gc.a(_snowmanx, gc.b.b);
      gc _snowmanxxxxxx = gc.a(_snowmanx, gc.b.a);

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxx; _snowmanxxxxxxx++) {
         for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxx; _snowmanxxxxxxxx++) {
            boolean _snowmanxxxxxxxxx = false;

            for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx <= _snowmanxxxx; _snowmanxxxxxxxxxx++) {
               boolean _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx != _snowmanxxxx && this.b(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx);
               if (!_snowmanxxxxxxxxx && _snowmanxxxxxxxxxxx) {
                  _snowman.consume(
                     _snowmanxxxxx,
                     _snowman.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx, gc.a.a),
                     _snowman.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx, gc.a.b),
                     _snowman.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx, gc.a.c)
                  );
               }

               if (_snowmanxxxxxxxxx && !_snowmanxxxxxxxxxxx) {
                  _snowman.consume(
                     _snowmanxxxxxx,
                     _snowman.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx - 1, gc.a.a),
                     _snowman.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx - 1, gc.a.b),
                     _snowman.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx - 1, gc.a.c)
                  );
               }

               _snowmanxxxxxxxxx = _snowmanxxxxxxxxxxx;
            }
         }
      }
   }

   public interface a {
      void consume(gc var1, int var2, int var3, int var4);
   }

   public interface b {
      void consume(int var1, int var2, int var3, int var4, int var5, int var6);
   }
}
