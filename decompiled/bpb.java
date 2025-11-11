import com.google.gson.JsonObject;

public abstract class bpb implements boq<aon> {
   protected final bon a;
   protected final bmb b;
   private final bot<?> e;
   private final bos<?> f;
   protected final vk c;
   protected final String d;

   public bpb(bot<?> var1, bos<?> var2, vk var3, String var4, bon var5, bmb var6) {
      this.e = _snowman;
      this.f = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public bot<?> g() {
      return this.e;
   }

   @Override
   public bos<?> ag_() {
      return this.f;
   }

   @Override
   public vk f() {
      return this.c;
   }

   @Override
   public String d() {
      return this.d;
   }

   @Override
   public bmb c() {
      return this.b;
   }

   @Override
   public gj<bon> a() {
      gj<bon> _snowman = gj.a();
      _snowman.add(this.a);
      return _snowman;
   }

   @Override
   public boolean a(int var1, int var2) {
      return true;
   }

   @Override
   public bmb a(aon var1) {
      return this.b.i();
   }

   public static class a<T extends bpb> implements bos<T> {
      final bpb.a.a<T> v;

      protected a(bpb.a.a<T> var1) {
         this.v = _snowman;
      }

      public T b(vk var1, JsonObject var2) {
         String _snowman = afd.a(_snowman, "group", "");
         bon _snowmanx;
         if (afd.d(_snowman, "ingredient")) {
            _snowmanx = bon.a(afd.u(_snowman, "ingredient"));
         } else {
            _snowmanx = bon.a(afd.t(_snowman, "ingredient"));
         }

         String _snowmanxx = afd.h(_snowman, "result");
         int _snowmanxxx = afd.n(_snowman, "count");
         bmb _snowmanxxxx = new bmb(gm.T.a(new vk(_snowmanxx)), _snowmanxxx);
         return this.v.create(_snowman, _snowman, _snowmanx, _snowmanxxxx);
      }

      public T b(vk var1, nf var2) {
         String _snowman = _snowman.e(32767);
         bon _snowmanx = bon.b(_snowman);
         bmb _snowmanxx = _snowman.n();
         return this.v.create(_snowman, _snowman, _snowmanx, _snowmanxx);
      }

      public void a(nf var1, T var2) {
         _snowman.a(_snowman.d);
         _snowman.a.a(_snowman);
         _snowman.a(_snowman.b);
      }

      interface a<T extends bpb> {
         T create(vk var1, String var2, bon var3, bmb var4);
      }
   }
}
