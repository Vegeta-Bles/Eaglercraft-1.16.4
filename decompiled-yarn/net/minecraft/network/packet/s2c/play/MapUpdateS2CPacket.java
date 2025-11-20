package net.minecraft.network.packet.s2c.play;

import java.io.IOException;
import java.util.Collection;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;

public class MapUpdateS2CPacket implements Packet<ClientPlayPacketListener> {
   private int id;
   private byte scale;
   private boolean showIcons;
   private boolean locked;
   private MapIcon[] icons;
   private int startX;
   private int startZ;
   private int width;
   private int height;
   private byte[] colors;

   public MapUpdateS2CPacket() {
   }

   public MapUpdateS2CPacket(
      int id, byte scale, boolean showIcons, boolean locked, Collection<MapIcon> icons, byte[] mapColors, int startX, int startZ, int width, int height
   ) {
      this.id = id;
      this.scale = scale;
      this.showIcons = showIcons;
      this.locked = locked;
      this.icons = icons.toArray(new MapIcon[icons.size()]);
      this.startX = startX;
      this.startZ = startZ;
      this.width = width;
      this.height = height;
      this.colors = new byte[width * height];

      for (int _snowman = 0; _snowman < width; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < height; _snowmanx++) {
            this.colors[_snowman + _snowmanx * width] = mapColors[startX + _snowman + (startZ + _snowmanx) * 128];
         }
      }
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.id = buf.readVarInt();
      this.scale = buf.readByte();
      this.showIcons = buf.readBoolean();
      this.locked = buf.readBoolean();
      this.icons = new MapIcon[buf.readVarInt()];

      for (int _snowman = 0; _snowman < this.icons.length; _snowman++) {
         MapIcon.Type _snowmanx = buf.readEnumConstant(MapIcon.Type.class);
         this.icons[_snowman] = new MapIcon(_snowmanx, buf.readByte(), buf.readByte(), (byte)(buf.readByte() & 15), buf.readBoolean() ? buf.readText() : null);
      }

      this.width = buf.readUnsignedByte();
      if (this.width > 0) {
         this.height = buf.readUnsignedByte();
         this.startX = buf.readUnsignedByte();
         this.startZ = buf.readUnsignedByte();
         this.colors = buf.readByteArray();
      }
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeVarInt(this.id);
      buf.writeByte(this.scale);
      buf.writeBoolean(this.showIcons);
      buf.writeBoolean(this.locked);
      buf.writeVarInt(this.icons.length);

      for (MapIcon _snowman : this.icons) {
         buf.writeEnumConstant(_snowman.getType());
         buf.writeByte(_snowman.getX());
         buf.writeByte(_snowman.getZ());
         buf.writeByte(_snowman.getRotation() & 15);
         if (_snowman.getText() != null) {
            buf.writeBoolean(true);
            buf.writeText(_snowman.getText());
         } else {
            buf.writeBoolean(false);
         }
      }

      buf.writeByte(this.width);
      if (this.width > 0) {
         buf.writeByte(this.height);
         buf.writeByte(this.startX);
         buf.writeByte(this.startZ);
         buf.writeByteArray(this.colors);
      }
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onMapUpdate(this);
   }

   public int getId() {
      return this.id;
   }

   public void apply(MapState _snowman) {
      _snowman.scale = this.scale;
      _snowman.showIcons = this.showIcons;
      _snowman.locked = this.locked;
      _snowman.icons.clear();

      for (int _snowmanx = 0; _snowmanx < this.icons.length; _snowmanx++) {
         MapIcon _snowmanxx = this.icons[_snowmanx];
         _snowman.icons.put("icon-" + _snowmanx, _snowmanxx);
      }

      for (int _snowmanx = 0; _snowmanx < this.width; _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx < this.height; _snowmanxx++) {
            _snowman.colors[this.startX + _snowmanx + (this.startZ + _snowmanxx) * 128] = this.colors[_snowmanx + _snowmanxx * this.width];
         }
      }
   }
}
