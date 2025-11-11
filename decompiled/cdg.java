import com.google.common.collect.Iterables;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.Property;
import javax.annotation.Nullable;

public class cdg extends ccj implements cdm {
   @Nullable
   private static acq a;
   @Nullable
   private static MinecraftSessionService b;
   @Nullable
   private GameProfile c;
   private int g;
   private boolean h;

   public cdg() {
      super(cck.o);
   }

   public static void a(acq var0) {
      a = _snowman;
   }

   public static void a(MinecraftSessionService var0) {
      b = _snowman;
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      if (this.c != null) {
         md _snowman = new md();
         mp.a(_snowman, this.c);
         _snowman.a("SkullOwner", _snowman);
      }

      return _snowman;
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      if (_snowman.c("SkullOwner", 10)) {
         this.a(mp.a(_snowman.p("SkullOwner")));
      } else if (_snowman.c("ExtraType", 8)) {
         String _snowman = _snowman.l("ExtraType");
         if (!aft.b(_snowman)) {
            this.a(new GameProfile(null, _snowman));
         }
      }
   }

   @Override
   public void aj_() {
      ceh _snowman = this.p();
      if (_snowman.a(bup.fm) || _snowman.a(bup.fn)) {
         if (this.d.r(this.e)) {
            this.h = true;
            this.g++;
         } else {
            this.h = false;
         }
      }
   }

   public float a(float var1) {
      return this.h ? (float)this.g + _snowman : (float)this.g;
   }

   @Nullable
   public GameProfile d() {
      return this.c;
   }

   @Nullable
   @Override
   public ow a() {
      return new ow(this.e, 4, this.b());
   }

   @Override
   public md b() {
      return this.a(new md());
   }

   public void a(@Nullable GameProfile var1) {
      this.c = _snowman;
      this.f();
   }

   private void f() {
      this.c = b(this.c);
      this.X_();
   }

   @Nullable
   public static GameProfile b(@Nullable GameProfile var0) {
      if (_snowman != null && !aft.b(_snowman.getName())) {
         if (_snowman.isComplete() && _snowman.getProperties().containsKey("textures")) {
            return _snowman;
         } else if (a != null && b != null) {
            GameProfile _snowman = a.a(_snowman.getName());
            if (_snowman == null) {
               return _snowman;
            } else {
               Property _snowmanx = (Property)Iterables.getFirst(_snowman.getProperties().get("textures"), null);
               if (_snowmanx == null) {
                  _snowman = b.fillProfileProperties(_snowman, true);
               }

               return _snowman;
            }
         } else {
            return _snowman;
         }
      } else {
         return _snowman;
      }
   }
}
