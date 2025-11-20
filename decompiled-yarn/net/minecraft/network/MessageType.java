package net.minecraft.network;

public enum MessageType {
   CHAT((byte)0, false),
   SYSTEM((byte)1, true),
   GAME_INFO((byte)2, true);

   private final byte id;
   private final boolean interruptsNarration;

   private MessageType(byte id, boolean interruptsNarration) {
      this.id = id;
      this.interruptsNarration = interruptsNarration;
   }

   public byte getId() {
      return this.id;
   }

   public static MessageType byId(byte id) {
      for (MessageType _snowman : values()) {
         if (id == _snowman.id) {
            return _snowman;
         }
      }

      return CHAT;
   }

   public boolean interruptsNarration() {
      return this.interruptsNarration;
   }
}
