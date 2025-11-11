import java.util.ArrayList;
import javax.annotation.Nullable;

public class bqw extends ArrayList<bqv> {
   public bqw() {
   }

   public bqw(md var1) {
      mj _snowman = _snowman.d("Recipes", 10);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         this.add(new bqv(_snowman.a(_snowmanx)));
      }
   }

   @Nullable
   public bqv a(bmb var1, bmb var2, int var3) {
      if (_snowman > 0 && _snowman < this.size()) {
         bqv _snowman = this.get(_snowman);
         return _snowman.a(_snowman, _snowman) ? _snowman : null;
      } else {
         for (int _snowman = 0; _snowman < this.size(); _snowman++) {
            bqv _snowmanx = this.get(_snowman);
            if (_snowmanx.a(_snowman, _snowman)) {
               return _snowmanx;
            }
         }

         return null;
      }
   }

   public void a(nf var1) {
      _snowman.writeByte((byte)(this.size() & 0xFF));

      for (int _snowman = 0; _snowman < this.size(); _snowman++) {
         bqv _snowmanx = this.get(_snowman);
         _snowman.a(_snowmanx.a());
         _snowman.a(_snowmanx.d());
         bmb _snowmanxx = _snowmanx.c();
         _snowman.writeBoolean(!_snowmanxx.a());
         if (!_snowmanxx.a()) {
            _snowman.a(_snowmanxx);
         }

         _snowman.writeBoolean(_snowmanx.p());
         _snowman.writeInt(_snowmanx.g());
         _snowman.writeInt(_snowmanx.i());
         _snowman.writeInt(_snowmanx.o());
         _snowman.writeInt(_snowmanx.m());
         _snowman.writeFloat(_snowmanx.n());
         _snowman.writeInt(_snowmanx.k());
      }
   }

   public static bqw b(nf var0) {
      bqw _snowman = new bqw();
      int _snowmanx = _snowman.readByte() & 255;

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         bmb _snowmanxxx = _snowman.n();
         bmb _snowmanxxxx = _snowman.n();
         bmb _snowmanxxxxx = bmb.b;
         if (_snowman.readBoolean()) {
            _snowmanxxxxx = _snowman.n();
         }

         boolean _snowmanxxxxxx = _snowman.readBoolean();
         int _snowmanxxxxxxx = _snowman.readInt();
         int _snowmanxxxxxxxx = _snowman.readInt();
         int _snowmanxxxxxxxxx = _snowman.readInt();
         int _snowmanxxxxxxxxxx = _snowman.readInt();
         float _snowmanxxxxxxxxxxx = _snowman.readFloat();
         int _snowmanxxxxxxxxxxxx = _snowman.readInt();
         bqv _snowmanxxxxxxxxxxxxx = new bqv(_snowmanxxx, _snowmanxxxxx, _snowmanxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
         if (_snowmanxxxxxx) {
            _snowmanxxxxxxxxxxxxx.q();
         }

         _snowmanxxxxxxxxxxxxx.b(_snowmanxxxxxxxxxx);
         _snowman.add(_snowmanxxxxxxxxxxxxx);
      }

      return _snowman;
   }

   public md a() {
      md _snowman = new md();
      mj _snowmanx = new mj();

      for (int _snowmanxx = 0; _snowmanxx < this.size(); _snowmanxx++) {
         bqv _snowmanxxx = this.get(_snowmanxx);
         _snowmanx.add(_snowmanxxx.t());
      }

      _snowman.a("Recipes", _snowmanx);
      return _snowman;
   }
}
