import com.google.gson.JsonObject;

public class bph implements boq<aon> {
   private final bon a;
   private final bon b;
   private final bmb c;
   private final vk d;

   public bph(vk var1, bon var2, bon var3, bmb var4) {
      this.d = _snowman;
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public boolean a(aon var1, brx var2) {
      return this.a.a(_snowman.a(0)) && this.b.a(_snowman.a(1));
   }

   @Override
   public bmb a(aon var1) {
      bmb _snowman = this.c.i();
      md _snowmanx = _snowman.a(0).o();
      if (_snowmanx != null) {
         _snowman.c(_snowmanx.g());
      }

      return _snowman;
   }

   @Override
   public boolean a(int var1, int var2) {
      return _snowman * _snowman >= 2;
   }

   @Override
   public bmb c() {
      return this.c;
   }

   public boolean a(bmb var1) {
      return this.b.a(_snowman);
   }

   @Override
   public bmb h() {
      return new bmb(bup.lZ);
   }

   @Override
   public vk f() {
      return this.d;
   }

   @Override
   public bos<?> ag_() {
      return bos.u;
   }

   @Override
   public bot<?> g() {
      return bot.g;
   }

   public static class a implements bos<bph> {
      public a() {
      }

      public bph b(vk var1, JsonObject var2) {
         bon _snowman = bon.a(afd.t(_snowman, "base"));
         bon _snowmanx = bon.a(afd.t(_snowman, "addition"));
         bmb _snowmanxx = bov.a(afd.t(_snowman, "result"));
         return new bph(_snowman, _snowman, _snowmanx, _snowmanxx);
      }

      public bph b(vk var1, nf var2) {
         bon _snowman = bon.b(_snowman);
         bon _snowmanx = bon.b(_snowman);
         bmb _snowmanxx = _snowman.n();
         return new bph(_snowman, _snowman, _snowmanx, _snowmanxx);
      }

      public void a(nf var1, bph var2) {
         _snowman.a.a(_snowman);
         _snowman.b.a(_snowman);
         _snowman.a(_snowman.c);
      }
   }
}
