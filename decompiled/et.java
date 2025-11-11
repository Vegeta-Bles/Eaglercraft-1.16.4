import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class et implements em {
   private final es a;
   private final es b;
   private final es c;

   public et(es var1, es var2, es var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public dcn a(db var1) {
      dcn _snowman = _snowman.d();
      return new dcn(this.a.a(_snowman.b), this.b.a(_snowman.c), this.c.a(_snowman.d));
   }

   @Override
   public dcm b(db var1) {
      dcm _snowman = _snowman.i();
      return new dcm((float)this.a.a((double)_snowman.i), (float)this.b.a((double)_snowman.j));
   }

   @Override
   public boolean a() {
      return this.a.a();
   }

   @Override
   public boolean b() {
      return this.b.a();
   }

   @Override
   public boolean c() {
      return this.c.a();
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof et)) {
         return false;
      } else {
         et _snowman = (et)_snowman;
         if (!this.a.equals(_snowman.a)) {
            return false;
         } else {
            return !this.b.equals(_snowman.b) ? false : this.c.equals(_snowman.c);
         }
      }
   }

   public static et a(StringReader var0) throws CommandSyntaxException {
      int _snowman = _snowman.getCursor();
      es _snowmanx = es.a(_snowman);
      if (_snowman.canRead() && _snowman.peek() == ' ') {
         _snowman.skip();
         es _snowmanxx = es.a(_snowman);
         if (_snowman.canRead() && _snowman.peek() == ' ') {
            _snowman.skip();
            es _snowmanxxx = es.a(_snowman);
            return new et(_snowmanx, _snowmanxx, _snowmanxxx);
         } else {
            _snowman.setCursor(_snowman);
            throw er.a.createWithContext(_snowman);
         }
      } else {
         _snowman.setCursor(_snowman);
         throw er.a.createWithContext(_snowman);
      }
   }

   public static et a(StringReader var0, boolean var1) throws CommandSyntaxException {
      int _snowman = _snowman.getCursor();
      es _snowmanx = es.a(_snowman, _snowman);
      if (_snowman.canRead() && _snowman.peek() == ' ') {
         _snowman.skip();
         es _snowmanxx = es.a(_snowman, false);
         if (_snowman.canRead() && _snowman.peek() == ' ') {
            _snowman.skip();
            es _snowmanxxx = es.a(_snowman, _snowman);
            return new et(_snowmanx, _snowmanxx, _snowmanxxx);
         } else {
            _snowman.setCursor(_snowman);
            throw er.a.createWithContext(_snowman);
         }
      } else {
         _snowman.setCursor(_snowman);
         throw er.a.createWithContext(_snowman);
      }
   }

   public static et d() {
      return new et(new es(true, 0.0), new es(true, 0.0), new es(true, 0.0));
   }

   @Override
   public int hashCode() {
      int _snowman = this.a.hashCode();
      _snowman = 31 * _snowman + this.b.hashCode();
      return 31 * _snowman + this.c.hashCode();
   }
}
