package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;

public class CommandTreeS2CPacket implements Packet<ClientPlayPacketListener> {
   private RootCommandNode<CommandSource> commandTree;

   public CommandTreeS2CPacket() {
   }

   public CommandTreeS2CPacket(RootCommandNode<CommandSource> commandTree) {
      this.commandTree = commandTree;
   }

   @Override
   public void read(PacketByteBuf buf) throws IOException {
      CommandTreeS2CPacket.CommandNodeData[] _snowman = new CommandTreeS2CPacket.CommandNodeData[buf.readVarInt()];

      for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
         _snowman[_snowmanx] = readCommandNode(buf);
      }

      method_30946(_snowman);
      this.commandTree = (RootCommandNode<CommandSource>)_snowman[buf.readVarInt()].node;
   }

   @Override
   public void write(PacketByteBuf buf) throws IOException {
      Object2IntMap<CommandNode<CommandSource>> _snowman = method_30944(this.commandTree);
      CommandNode<CommandSource>[] _snowmanx = method_30945(_snowman);
      buf.writeVarInt(_snowmanx.length);

      for (CommandNode<CommandSource> _snowmanxx : _snowmanx) {
         writeNode(buf, _snowmanxx, _snowman);
      }

      buf.writeVarInt(_snowman.get(this.commandTree));
   }

   private static void method_30946(CommandTreeS2CPacket.CommandNodeData[] _snowman) {
      List<CommandTreeS2CPacket.CommandNodeData> _snowmanx = Lists.newArrayList(_snowman);

      while (!_snowmanx.isEmpty()) {
         boolean _snowmanxx = _snowmanx.removeIf(_snowmanxxx -> _snowmanxxx.build(_snowman));
         if (!_snowmanxx) {
            throw new IllegalStateException("Server sent an impossible command tree");
         }
      }
   }

   private static Object2IntMap<CommandNode<CommandSource>> method_30944(RootCommandNode<CommandSource> _snowman) {
      Object2IntMap<CommandNode<CommandSource>> _snowmanx = new Object2IntOpenHashMap();
      Queue<CommandNode<CommandSource>> _snowmanxx = Queues.newArrayDeque();
      _snowmanxx.add(_snowman);

      CommandNode<CommandSource> _snowmanxxx;
      while ((_snowmanxxx = _snowmanxx.poll()) != null) {
         if (!_snowmanx.containsKey(_snowmanxxx)) {
            int _snowmanxxxx = _snowmanx.size();
            _snowmanx.put(_snowmanxxx, _snowmanxxxx);
            _snowmanxx.addAll(_snowmanxxx.getChildren());
            if (_snowmanxxx.getRedirect() != null) {
               _snowmanxx.add(_snowmanxxx.getRedirect());
            }
         }
      }

      return _snowmanx;
   }

   private static CommandNode<CommandSource>[] method_30945(Object2IntMap<CommandNode<CommandSource>> _snowman) {
      CommandNode<CommandSource>[] _snowmanx = new CommandNode[_snowman.size()];
      ObjectIterator var2 = Object2IntMaps.fastIterable(_snowman).iterator();

      while (var2.hasNext()) {
         Entry<CommandNode<CommandSource>> _snowmanxx = (Entry<CommandNode<CommandSource>>)var2.next();
         _snowmanx[_snowmanxx.getIntValue()] = (CommandNode<CommandSource>)_snowmanxx.getKey();
      }

      return _snowmanx;
   }

   private static CommandTreeS2CPacket.CommandNodeData readCommandNode(PacketByteBuf _snowman) {
      byte _snowmanx = _snowman.readByte();
      int[] _snowmanxx = _snowman.readIntArray();
      int _snowmanxxx = (_snowmanx & 8) != 0 ? _snowman.readVarInt() : 0;
      ArgumentBuilder<CommandSource, ?> _snowmanxxxx = readArgumentBuilder(_snowman, _snowmanx);
      return new CommandTreeS2CPacket.CommandNodeData(_snowmanxxxx, _snowmanx, _snowmanxxx, _snowmanxx);
   }

   @Nullable
   private static ArgumentBuilder<CommandSource, ?> readArgumentBuilder(PacketByteBuf _snowman, byte _snowman) {
      int _snowmanxx = _snowman & 3;
      if (_snowmanxx == 2) {
         String _snowmanxxx = _snowman.readString(32767);
         ArgumentType<?> _snowmanxxxx = ArgumentTypes.fromPacket(_snowman);
         if (_snowmanxxxx == null) {
            return null;
         } else {
            RequiredArgumentBuilder<CommandSource, ?> _snowmanxxxxx = RequiredArgumentBuilder.argument(_snowmanxxx, _snowmanxxxx);
            if ((_snowman & 16) != 0) {
               _snowmanxxxxx.suggests(SuggestionProviders.byId(_snowman.readIdentifier()));
            }

            return _snowmanxxxxx;
         }
      } else {
         return _snowmanxx == 1 ? LiteralArgumentBuilder.literal(_snowman.readString(32767)) : null;
      }
   }

   private static void writeNode(PacketByteBuf _snowman, CommandNode<CommandSource> _snowman, Map<CommandNode<CommandSource>, Integer> _snowman) {
      byte _snowmanxxx = 0;
      if (_snowman.getRedirect() != null) {
         _snowmanxxx = (byte)(_snowmanxxx | 8);
      }

      if (_snowman.getCommand() != null) {
         _snowmanxxx = (byte)(_snowmanxxx | 4);
      }

      if (_snowman instanceof RootCommandNode) {
         _snowmanxxx = (byte)(_snowmanxxx | 0);
      } else if (_snowman instanceof ArgumentCommandNode) {
         _snowmanxxx = (byte)(_snowmanxxx | 2);
         if (((ArgumentCommandNode)_snowman).getCustomSuggestions() != null) {
            _snowmanxxx = (byte)(_snowmanxxx | 16);
         }
      } else {
         if (!(_snowman instanceof LiteralCommandNode)) {
            throw new UnsupportedOperationException("Unknown node type " + _snowman);
         }

         _snowmanxxx = (byte)(_snowmanxxx | 1);
      }

      _snowman.writeByte(_snowmanxxx);
      _snowman.writeVarInt(_snowman.getChildren().size());

      for (CommandNode<CommandSource> _snowmanxxxx : _snowman.getChildren()) {
         _snowman.writeVarInt(_snowman.get(_snowmanxxxx));
      }

      if (_snowman.getRedirect() != null) {
         _snowman.writeVarInt(_snowman.get(_snowman.getRedirect()));
      }

      if (_snowman instanceof ArgumentCommandNode) {
         ArgumentCommandNode<CommandSource, ?> _snowmanxxxx = (ArgumentCommandNode<CommandSource, ?>)_snowman;
         _snowman.writeString(_snowmanxxxx.getName());
         ArgumentTypes.toPacket(_snowman, _snowmanxxxx.getType());
         if (_snowmanxxxx.getCustomSuggestions() != null) {
            _snowman.writeIdentifier(SuggestionProviders.computeName(_snowmanxxxx.getCustomSuggestions()));
         }
      } else if (_snowman instanceof LiteralCommandNode) {
         _snowman.writeString(((LiteralCommandNode)_snowman).getLiteral());
      }
   }

   public void apply(ClientPlayPacketListener _snowman) {
      _snowman.onCommandTree(this);
   }

   public RootCommandNode<CommandSource> getCommandTree() {
      return this.commandTree;
   }

   static class CommandNodeData {
      @Nullable
      private final ArgumentBuilder<CommandSource, ?> argumentBuilder;
      private final byte flags;
      private final int redirectNodeIndex;
      private final int[] childNodeIndices;
      @Nullable
      private CommandNode<CommandSource> node;

      private CommandNodeData(@Nullable ArgumentBuilder<CommandSource, ?> argumentBuilder, byte flags, int redirectNodeIndex, int[] childNodeIndices) {
         this.argumentBuilder = argumentBuilder;
         this.flags = flags;
         this.redirectNodeIndex = redirectNodeIndex;
         this.childNodeIndices = childNodeIndices;
      }

      public boolean build(CommandTreeS2CPacket.CommandNodeData[] previousNodes) {
         if (this.node == null) {
            if (this.argumentBuilder == null) {
               this.node = new RootCommandNode();
            } else {
               if ((this.flags & 8) != 0) {
                  if (previousNodes[this.redirectNodeIndex].node == null) {
                     return false;
                  }

                  this.argumentBuilder.redirect(previousNodes[this.redirectNodeIndex].node);
               }

               if ((this.flags & 4) != 0) {
                  this.argumentBuilder.executes(_snowman -> 0);
               }

               this.node = this.argumentBuilder.build();
            }
         }

         for (int _snowman : this.childNodeIndices) {
            if (previousNodes[_snowman].node == null) {
               return false;
            }
         }

         for (int _snowmanx : this.childNodeIndices) {
            CommandNode<CommandSource> _snowmanxx = previousNodes[_snowmanx].node;
            if (!(_snowmanxx instanceof RootCommandNode)) {
               this.node.addChild(_snowmanxx);
            }
         }

         return true;
      }
   }
}
