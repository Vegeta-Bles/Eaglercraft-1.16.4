package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Lists;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.io.IOException;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;

public class CommandSuggestionsS2CPacket implements Packet<ClientPlayPacketListener> {
   private int completionId;
   private Suggestions suggestions;

   public CommandSuggestionsS2CPacket() {
   }

   public CommandSuggestionsS2CPacket(int completionId, Suggestions suggestions) {
      this.completionId = completionId;
      this.suggestions = suggestions;
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      this.completionId = buf.readVarInt();
      int _snowman = buf.readVarInt();
      int _snowmanx = buf.readVarInt();
      StringRange _snowmanxx = StringRange.between(_snowman, _snowman + _snowmanx);
      int _snowmanxxx = buf.readVarInt();
      List<Suggestion> _snowmanxxxx = Lists.newArrayListWithCapacity(_snowmanxxx);

      for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxx; _snowmanxxxxx++) {
         String _snowmanxxxxxx = buf.readString(32767);
         Text _snowmanxxxxxxx = buf.readBoolean() ? buf.readText() : null;
         _snowmanxxxx.add(new Suggestion(_snowmanxx, _snowmanxxxxxx, _snowmanxxxxxxx));
      }

      this.suggestions = new Suggestions(_snowmanxx, _snowmanxxxx);
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      buf.writeVarInt(this.completionId);
      buf.writeVarInt(this.suggestions.getRange().getStart());
      buf.writeVarInt(this.suggestions.getRange().getLength());
      buf.writeVarInt(this.suggestions.getList().size());

      for (Suggestion _snowman : this.suggestions.getList()) {
         buf.writeString(_snowman.getText());
         buf.writeBoolean(_snowman.getTooltip() != null);
         if (_snowman.getTooltip() != null) {
            buf.writeText(Texts.toText(_snowman.getTooltip()));
         }
      }
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onCommandSuggestions(this);
   }

   public int getCompletionId() {
      return this.completionId;
   }

   public Suggestions getSuggestions() {
      return this.suggestions;
   }
}
