package net.minecraft.world.timer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.util.Identifier;

public class FunctionTimerCallback implements TimerCallback<MinecraftServer> {
   private final Identifier name;

   public FunctionTimerCallback(Identifier arg) {
      this.name = arg;
   }

   public void call(MinecraftServer minecraftServer, Timer<MinecraftServer> arg, long l) {
      CommandFunctionManager lv = minecraftServer.getCommandFunctionManager();
      lv.getFunction(this.name).ifPresent(arg2 -> lv.execute(arg2, lv.getTaggedFunctionSource()));
   }

   public static class Serializer extends TimerCallback.Serializer<MinecraftServer, FunctionTimerCallback> {
      public Serializer() {
         super(new Identifier("function"), FunctionTimerCallback.class);
      }

      public void serialize(CompoundTag arg, FunctionTimerCallback arg2) {
         arg.putString("Name", arg2.name.toString());
      }

      public FunctionTimerCallback deserialize(CompoundTag arg) {
         Identifier lv = new Identifier(arg.getString("Name"));
         return new FunctionTimerCallback(lv);
      }
   }
}
