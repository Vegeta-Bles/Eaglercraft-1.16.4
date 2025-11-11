public interface dfj extends dfq {
   dfs i();

   void e();

   void a(int var1, byte var2);

   void a(int var1, short var2);

   void a(int var1, float var2);

   @Override
   default dfq a(double var1, double var3, double var5) {
      if (this.i().a() != dfs.a.a) {
         throw new IllegalStateException();
      } else {
         this.a(0, (float)_snowman);
         this.a(4, (float)_snowman);
         this.a(8, (float)_snowman);
         this.e();
         return this;
      }
   }

   @Override
   default dfq a(int var1, int var2, int var3, int var4) {
      dfs _snowman = this.i();
      if (_snowman.b() != dfs.b.c) {
         return this;
      } else if (_snowman.a() != dfs.a.b) {
         throw new IllegalStateException();
      } else {
         this.a(0, (byte)_snowman);
         this.a(1, (byte)_snowman);
         this.a(2, (byte)_snowman);
         this.a(3, (byte)_snowman);
         this.e();
         return this;
      }
   }

   @Override
   default dfq a(float var1, float var2) {
      dfs _snowman = this.i();
      if (_snowman.b() == dfs.b.d && _snowman.c() == 0) {
         if (_snowman.a() != dfs.a.a) {
            throw new IllegalStateException();
         } else {
            this.a(0, _snowman);
            this.a(4, _snowman);
            this.e();
            return this;
         }
      } else {
         return this;
      }
   }

   @Override
   default dfq a(int var1, int var2) {
      return this.a((short)_snowman, (short)_snowman, 1);
   }

   @Override
   default dfq b(int var1, int var2) {
      return this.a((short)_snowman, (short)_snowman, 2);
   }

   default dfq a(short var1, short var2, int var3) {
      dfs _snowman = this.i();
      if (_snowman.b() != dfs.b.d || _snowman.c() != _snowman) {
         return this;
      } else if (_snowman.a() != dfs.a.e) {
         throw new IllegalStateException();
      } else {
         this.a(0, _snowman);
         this.a(2, _snowman);
         this.e();
         return this;
      }
   }

   @Override
   default dfq b(float var1, float var2, float var3) {
      dfs _snowman = this.i();
      if (_snowman.b() != dfs.b.b) {
         return this;
      } else if (_snowman.a() != dfs.a.c) {
         throw new IllegalStateException();
      } else {
         this.a(0, a(_snowman));
         this.a(1, a(_snowman));
         this.a(2, a(_snowman));
         this.e();
         return this;
      }
   }

   static byte a(float var0) {
      return (byte)((int)(afm.a(_snowman, -1.0F, 1.0F) * 127.0F) & 0xFF);
   }
}
