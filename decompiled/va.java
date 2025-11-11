import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class va<C extends aon> implements uz<Integer> {
   protected static final Logger a = LogManager.getLogger();
   protected final bfy b = new bfy();
   protected bfv c;
   protected bjj<C> d;

   public va(bjj<C> var1) {
      this.d = _snowman;
   }

   public void a(aah var1, @Nullable boq<C> var2, boolean var3) {
      if (_snowman != null && _snowman.B().b(_snowman)) {
         this.c = _snowman.bm;
         if (this.b() || _snowman.b_()) {
            this.b.a();
            _snowman.bm.a(this.b);
            this.d.a(this.b);
            if (this.b.a(_snowman, null)) {
               this.a(_snowman, _snowman);
            } else {
               this.a();
               _snowman.b.a(new qf(_snowman.bp.b, _snowman));
            }

            _snowman.bm.X_();
         }
      }
   }

   protected void a() {
      for (int _snowman = 0; _snowman < this.d.g() * this.d.h() + 1; _snowman++) {
         if (_snowman != this.d.f() || !(this.d instanceof bip) && !(this.d instanceof biz)) {
            this.a(_snowman);
         }
      }

      this.d.e();
   }

   protected void a(int var1) {
      bmb _snowman = this.d.a(_snowman).e();
      if (!_snowman.a()) {
         for (; _snowman.E() > 0; this.d.a(_snowman).a(1)) {
            int _snowmanx = this.c.d(_snowman);
            if (_snowmanx == -1) {
               _snowmanx = this.c.h();
            }

            bmb _snowmanxx = _snowman.i();
            _snowmanxx.e(1);
            if (!this.c.c(_snowmanx, _snowmanxx)) {
               a.error("Can't find any space for item in the inventory");
            }
         }
      }
   }

   protected void a(boq<C> var1, boolean var2) {
      boolean _snowman = this.d.a(_snowman);
      int _snowmanx = this.b.b(_snowman, null);
      if (_snowman) {
         for (int _snowmanxx = 0; _snowmanxx < this.d.h() * this.d.g() + 1; _snowmanxx++) {
            if (_snowmanxx != this.d.f()) {
               bmb _snowmanxxx = this.d.a(_snowmanxx).e();
               if (!_snowmanxxx.a() && Math.min(_snowmanx, _snowmanxxx.c()) < _snowmanxxx.E() + 1) {
                  return;
               }
            }
         }
      }

      int _snowmanxxx = this.a(_snowman, _snowmanx, _snowman);
      IntList _snowmanxxxx = new IntArrayList();
      if (this.b.a(_snowman, _snowmanxxxx, _snowmanxxx)) {
         int _snowmanxxxxx = _snowmanxxx;
         IntListIterator var8 = _snowmanxxxx.iterator();

         while (var8.hasNext()) {
            int _snowmanxxxxxx = (Integer)var8.next();
            int _snowmanxxxxxxx = bfy.a(_snowmanxxxxxx).c();
            if (_snowmanxxxxxxx < _snowmanxxxxx) {
               _snowmanxxxxx = _snowmanxxxxxxx;
            }
         }

         if (this.b.a(_snowman, _snowmanxxxx, _snowmanxxxxx)) {
            this.a();
            this.a(this.d.g(), this.d.h(), this.d.f(), _snowman, _snowmanxxxx.iterator(), _snowmanxxxxx);
         }
      }
   }

   @Override
   public void a(Iterator<Integer> var1, int var2, int var3, int var4, int var5) {
      bjr _snowman = this.d.a(_snowman);
      bmb _snowmanx = bfy.a(_snowman.next());
      if (!_snowmanx.a()) {
         for (int _snowmanxx = 0; _snowmanxx < _snowman; _snowmanxx++) {
            this.a(_snowman, _snowmanx);
         }
      }
   }

   protected int a(boolean var1, int var2, boolean var3) {
      int _snowman = 1;
      if (_snowman) {
         _snowman = _snowman;
      } else if (_snowman) {
         _snowman = 64;

         for (int _snowmanx = 0; _snowmanx < this.d.g() * this.d.h() + 1; _snowmanx++) {
            if (_snowmanx != this.d.f()) {
               bmb _snowmanxx = this.d.a(_snowmanx).e();
               if (!_snowmanxx.a() && _snowman > _snowmanxx.E()) {
                  _snowman = _snowmanxx.E();
               }
            }
         }

         if (_snowman < 64) {
            _snowman++;
         }
      }

      return _snowman;
   }

   protected void a(bjr var1, bmb var2) {
      int _snowman = this.c.c(_snowman);
      if (_snowman != -1) {
         bmb _snowmanx = this.c.a(_snowman).i();
         if (!_snowmanx.a()) {
            if (_snowmanx.E() > 1) {
               this.c.a(_snowman, 1);
            } else {
               this.c.b(_snowman);
            }

            _snowmanx.e(1);
            if (_snowman.e().a()) {
               _snowman.d(_snowmanx);
            } else {
               _snowman.e().f(1);
            }
         }
      }
   }

   private boolean b() {
      List<bmb> _snowman = Lists.newArrayList();
      int _snowmanx = this.c();

      for (int _snowmanxx = 0; _snowmanxx < this.d.g() * this.d.h() + 1; _snowmanxx++) {
         if (_snowmanxx != this.d.f()) {
            bmb _snowmanxxx = this.d.a(_snowmanxx).e().i();
            if (!_snowmanxxx.a()) {
               int _snowmanxxxx = this.c.d(_snowmanxxx);
               if (_snowmanxxxx == -1 && _snowman.size() <= _snowmanx) {
                  for (bmb _snowmanxxxxx : _snowman) {
                     if (_snowmanxxxxx.a(_snowmanxxx) && _snowmanxxxxx.E() != _snowmanxxxxx.c() && _snowmanxxxxx.E() + _snowmanxxx.E() <= _snowmanxxxxx.c()) {
                        _snowmanxxxxx.f(_snowmanxxx.E());
                        _snowmanxxx.e(0);
                        break;
                     }
                  }

                  if (!_snowmanxxx.a()) {
                     if (_snowman.size() >= _snowmanx) {
                        return false;
                     }

                     _snowman.add(_snowmanxxx);
                  }
               } else if (_snowmanxxxx == -1) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   private int c() {
      int _snowman = 0;

      for (bmb _snowmanx : this.c.a) {
         if (_snowmanx.a()) {
            _snowman++;
         }
      }

      return _snowman;
   }
}
