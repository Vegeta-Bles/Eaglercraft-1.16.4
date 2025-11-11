import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class bhd extends cxs {
   private final Map<Integer, bhb> a = Maps.newHashMap();
   private final aag b;
   private int c;
   private int d;

   public bhd(aag var1) {
      super(a(_snowman.k()));
      this.b = _snowman;
      this.c = 1;
      this.b();
   }

   public bhb a(int var1) {
      return this.a.get(_snowman);
   }

   public void a() {
      this.d++;
      Iterator<bhb> _snowman = this.a.values().iterator();

      while (_snowman.hasNext()) {
         bhb _snowmanx = _snowman.next();
         if (this.b.V().b(brt.x)) {
            _snowmanx.n();
         }

         if (_snowmanx.d()) {
            _snowman.remove();
            this.b();
         } else {
            _snowmanx.o();
         }
      }

      if (this.d % 200 == 0) {
         this.b();
      }

      rz.a(this.b, this.a.values());
   }

   public static boolean a(bhc var0, bhb var1) {
      return _snowman != null && _snowman != null && _snowman.i() != null ? _snowman.aX() && _snowman.eZ() && _snowman.dd() <= 2400 && _snowman.l.k() == _snowman.i().k() : false;
   }

   @Nullable
   public bhb a(aah var1) {
      if (_snowman.a_()) {
         return null;
      } else if (this.b.V().b(brt.x)) {
         return null;
      } else {
         chd _snowman = _snowman.l.k();
         if (!_snowman.j()) {
            return null;
         } else {
            fx _snowmanx = _snowman.cB();
            List<azp> _snowmanxx = this.b.y().c(azr.b, _snowmanx, 64, azo.b.b).collect(Collectors.toList());
            int _snowmanxxx = 0;
            dcn _snowmanxxxx = dcn.a;

            for (azp _snowmanxxxxx : _snowmanxx) {
               fx _snowmanxxxxxx = _snowmanxxxxx.f();
               _snowmanxxxx = _snowmanxxxx.b((double)_snowmanxxxxxx.u(), (double)_snowmanxxxxxx.v(), (double)_snowmanxxxxxx.w());
               _snowmanxxx++;
            }

            fx _snowmanxxxxx;
            if (_snowmanxxx > 0) {
               _snowmanxxxx = _snowmanxxxx.a(1.0 / (double)_snowmanxxx);
               _snowmanxxxxx = new fx(_snowmanxxxx);
            } else {
               _snowmanxxxxx = _snowmanx;
            }

            bhb _snowmanxxxxxx = this.a(_snowman.u(), _snowmanxxxxx);
            boolean _snowmanxxxxxxx = false;
            if (!_snowmanxxxxxx.j()) {
               if (!this.a.containsKey(_snowmanxxxxxx.u())) {
                  this.a.put(_snowmanxxxxxx.u(), _snowmanxxxxxx);
               }

               _snowmanxxxxxxx = true;
            } else if (_snowmanxxxxxx.m() < _snowmanxxxxxx.l()) {
               _snowmanxxxxxxx = true;
            } else {
               _snowman.d(apw.E);
               _snowman.b.a(new pn(_snowman, (byte)43));
            }

            if (_snowmanxxxxxxx) {
               _snowmanxxxxxx.a((bfw)_snowman);
               _snowman.b.a(new pn(_snowman, (byte)43));
               if (!_snowmanxxxxxx.c()) {
                  _snowman.a(aea.az);
                  ac.I.a(_snowman);
               }
            }

            this.b();
            return _snowmanxxxxxx;
         }
      }
   }

   private bhb a(aag var1, fx var2) {
      bhb _snowman = _snowman.b_(_snowman);
      return _snowman != null ? _snowman : new bhb(this.e(), _snowman, _snowman);
   }

   @Override
   public void a(md var1) {
      this.c = _snowman.h("NextAvailableID");
      this.d = _snowman.h("Tick");
      mj _snowman = _snowman.d("Raids", 10);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         md _snowmanxx = _snowman.a(_snowmanx);
         bhb _snowmanxxx = new bhb(this.b, _snowmanxx);
         this.a.put(_snowmanxxx.u(), _snowmanxxx);
      }
   }

   @Override
   public md b(md var1) {
      _snowman.b("NextAvailableID", this.c);
      _snowman.b("Tick", this.d);
      mj _snowman = new mj();

      for (bhb _snowmanx : this.a.values()) {
         md _snowmanxx = new md();
         _snowmanx.a(_snowmanxx);
         _snowman.add(_snowmanxx);
      }

      _snowman.a("Raids", _snowman);
      return _snowman;
   }

   public static String a(chd var0) {
      return "raids" + _snowman.a();
   }

   private int e() {
      return ++this.c;
   }

   @Nullable
   public bhb a(fx var1, int var2) {
      bhb _snowman = null;
      double _snowmanx = (double)_snowman;

      for (bhb _snowmanxx : this.a.values()) {
         double _snowmanxxx = _snowmanxx.t().j(_snowman);
         if (_snowmanxx.v() && _snowmanxxx < _snowmanx) {
            _snowman = _snowmanxx;
            _snowmanx = _snowmanxxx;
         }
      }

      return _snowman;
   }
}
