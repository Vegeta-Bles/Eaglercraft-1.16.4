import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;

public class fn {
   public static void a() {
      fk.a("brigadier:bool", BoolArgumentType.class, new fl(BoolArgumentType::bool));
      fk.a("brigadier:float", FloatArgumentType.class, new fp());
      fk.a("brigadier:double", DoubleArgumentType.class, new fo());
      fk.a("brigadier:integer", IntegerArgumentType.class, new fq());
      fk.a("brigadier:long", LongArgumentType.class, new fr());
      fk.a("brigadier:string", StringArgumentType.class, new fs());
   }

   public static byte a(boolean var0, boolean var1) {
      byte _snowman = 0;
      if (_snowman) {
         _snowman = (byte)(_snowman | 1);
      }

      if (_snowman) {
         _snowman = (byte)(_snowman | 2);
      }

      return _snowman;
   }

   public static boolean a(byte var0) {
      return (_snowman & 1) != 0;
   }

   public static boolean b(byte var0) {
      return (_snowman & 2) != 0;
   }
}
