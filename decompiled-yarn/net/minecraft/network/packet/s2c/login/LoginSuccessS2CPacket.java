package net.minecraft.network.packet.s2c.login;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.UUID;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientLoginPacketListener;
import net.minecraft.util.dynamic.DynamicSerializableUuid;

public class LoginSuccessS2CPacket implements Packet<ClientLoginPacketListener> {
   private GameProfile profile;

   public LoginSuccessS2CPacket() {
   }

   public LoginSuccessS2CPacket(GameProfile profile) {
      this.profile = profile;
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      int[] _snowman = new int[4];

      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         _snowman[_snowmanx] = buf.readInt();
      }

      UUID _snowmanx = DynamicSerializableUuid.toUuid(_snowman);
      String _snowmanxx = buf.readString(16);
      this.profile = new GameProfile(_snowmanx, _snowmanxx);
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      for (int _snowman : DynamicSerializableUuid.toIntArray(this.profile.getId())) {
         buf.writeInt(_snowman);
      }

      buf.writeString(this.profile.getName());
   }

   public void apply(ClientLoginPacketListener _snowman) {
      _snowman.onLoginSuccess(this);
   }

   public GameProfile getProfile() {
      return this.profile;
   }
}
