import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.server.MinecraftServer;

public class nz extends nn implements nt {
   private final String d;
   @Nullable
   private final fc e;
   private final String f;

   @Nullable
   private static fc d(String var0) {
      try {
         return new fd(new StringReader(_snowman)).t();
      } catch (CommandSyntaxException var2) {
         return null;
      }
   }

   public nz(String var1, String var2) {
      this(_snowman, d(_snowman), _snowman);
   }

   private nz(String var1, @Nullable fc var2, String var3) {
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   public String h() {
      return this.d;
   }

   public String j() {
      return this.f;
   }

   private String a(db var1) throws CommandSyntaxException {
      if (this.e != null) {
         List<? extends aqa> _snowman = this.e.b(_snowman);
         if (!_snowman.isEmpty()) {
            if (_snowman.size() != 1) {
               throw dk.a.create();
            }

            return _snowman.get(0).bU();
         }
      }

      return this.d;
   }

   private String a(String var1, db var2) {
      MinecraftServer _snowman = _snowman.j();
      if (_snowman != null) {
         ddn _snowmanx = _snowman.aH();
         ddk _snowmanxx = _snowmanx.d(this.f);
         if (_snowmanx.b(_snowman, _snowmanxx)) {
            ddm _snowmanxxx = _snowmanx.c(_snowman, _snowmanxx);
            return Integer.toString(_snowmanxxx.b());
         }
      }

      return "";
   }

   public nz k() {
      return new nz(this.d, this.e, this.f);
   }

   @Override
   public nx a(@Nullable db var1, @Nullable aqa var2, int var3) throws CommandSyntaxException {
      if (_snowman == null) {
         return new oe("");
      } else {
         String _snowman = this.a(_snowman);
         String _snowmanx = _snowman != null && _snowman.equals("*") ? _snowman.bU() : _snowman;
         return new oe(this.a(_snowmanx, _snowman));
      }
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof nz)) {
         return false;
      } else {
         nz _snowman = (nz)_snowman;
         return this.d.equals(_snowman.d) && this.f.equals(_snowman.f) && super.equals(_snowman);
      }
   }

   @Override
   public String toString() {
      return "ScoreComponent{name='" + this.d + '\'' + "objective='" + this.f + '\'' + ", siblings=" + this.a + ", style=" + this.c() + '}';
   }
}
