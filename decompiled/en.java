import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Objects;

public class en implements em {
   private final double a;
   private final double b;
   private final double c;

   public en(double var1, double var3, double var5) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public dcn a(db var1) {
      dcm _snowman = _snowman.i();
      dcn _snowmanx = _snowman.k().a(_snowman);
      float _snowmanxx = afm.b((_snowman.j + 90.0F) * (float) (Math.PI / 180.0));
      float _snowmanxxx = afm.a((_snowman.j + 90.0F) * (float) (Math.PI / 180.0));
      float _snowmanxxxx = afm.b(-_snowman.i * (float) (Math.PI / 180.0));
      float _snowmanxxxxx = afm.a(-_snowman.i * (float) (Math.PI / 180.0));
      float _snowmanxxxxxx = afm.b((-_snowman.i + 90.0F) * (float) (Math.PI / 180.0));
      float _snowmanxxxxxxx = afm.a((-_snowman.i + 90.0F) * (float) (Math.PI / 180.0));
      dcn _snowmanxxxxxxxx = new dcn((double)(_snowmanxx * _snowmanxxxx), (double)_snowmanxxxxx, (double)(_snowmanxxx * _snowmanxxxx));
      dcn _snowmanxxxxxxxxx = new dcn((double)(_snowmanxx * _snowmanxxxxxx), (double)_snowmanxxxxxxx, (double)(_snowmanxxx * _snowmanxxxxxx));
      dcn _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.c(_snowmanxxxxxxxxx).a(-1.0);
      double _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx.b * this.c + _snowmanxxxxxxxxx.b * this.b + _snowmanxxxxxxxxxx.b * this.a;
      double _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxx.c * this.c + _snowmanxxxxxxxxx.c * this.b + _snowmanxxxxxxxxxx.c * this.a;
      double _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxx.d * this.c + _snowmanxxxxxxxxx.d * this.b + _snowmanxxxxxxxxxx.d * this.a;
      return new dcn(_snowmanx.b + _snowmanxxxxxxxxxxx, _snowmanx.c + _snowmanxxxxxxxxxxxx, _snowmanx.d + _snowmanxxxxxxxxxxxxx);
   }

   @Override
   public dcm b(db var1) {
      return dcm.a;
   }

   @Override
   public boolean a() {
      return true;
   }

   @Override
   public boolean b() {
      return true;
   }

   @Override
   public boolean c() {
      return true;
   }

   public static en a(StringReader var0) throws CommandSyntaxException {
      int _snowman = _snowman.getCursor();
      double _snowmanx = a(_snowman, _snowman);
      if (_snowman.canRead() && _snowman.peek() == ' ') {
         _snowman.skip();
         double _snowmanxx = a(_snowman, _snowman);
         if (_snowman.canRead() && _snowman.peek() == ' ') {
            _snowman.skip();
            double _snowmanxxx = a(_snowman, _snowman);
            return new en(_snowmanx, _snowmanxx, _snowmanxxx);
         } else {
            _snowman.setCursor(_snowman);
            throw er.a.createWithContext(_snowman);
         }
      } else {
         _snowman.setCursor(_snowman);
         throw er.a.createWithContext(_snowman);
      }
   }

   private static double a(StringReader var0, int var1) throws CommandSyntaxException {
      if (!_snowman.canRead()) {
         throw es.a.createWithContext(_snowman);
      } else if (_snowman.peek() != '^') {
         _snowman.setCursor(_snowman);
         throw er.b.createWithContext(_snowman);
      } else {
         _snowman.skip();
         return _snowman.canRead() && _snowman.peek() != ' ' ? _snowman.readDouble() : 0.0;
      }
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof en)) {
         return false;
      } else {
         en _snowman = (en)_snowman;
         return this.a == _snowman.a && this.b == _snowman.b && this.c == _snowman.c;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.a, this.b, this.c);
   }
}
