package net.minecraft.world.timer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class FunctionTagTimerCallback implements TimerCallback<MinecraftServer> {
   private final Identifier name;

   public FunctionTagTimerCallback(Identifier arg) {
      this.name = arg;
   }

   public void call(MinecraftServer minecraftServer, Timer<MinecraftServer> arg, long l) {
      CommandFunctionManager lv = minecraftServer.getCommandFunctionManager();
      Tag<CommandFunction> lv2 = lv.method_29462(this.name);

      for (CommandFunction lv3 : lv2.values()) {
         lv.execute(lv3, lv.getTaggedFunctionSource());
      }
   }

   public static class Serializer extends TimerCallback.Serializer<MinecraftServer, FunctionTagTimerCallback> {
      public Serializer() {
         super(new Identifier("function_tag"), FunctionTagTimerCallback.class);
      }

      public void serialize(CompoundTag arg, FunctionTagTimerCallback arg2) {
         arg.putString("Name", arg2.name.toString());
      }

      public FunctionTagTimerCallback deserialize(CompoundTag arg) {
         Identifier lv = new Identifier(arg.getString("Name"));
         return new FunctionTagTimerCallback(lv);
      }
   }
}
