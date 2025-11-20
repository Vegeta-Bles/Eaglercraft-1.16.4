/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Queues
 *  com.mojang.brigadier.arguments.ArgumentType
 *  com.mojang.brigadier.builder.ArgumentBuilder
 *  com.mojang.brigadier.builder.LiteralArgumentBuilder
 *  com.mojang.brigadier.builder.RequiredArgumentBuilder
 *  com.mojang.brigadier.suggestion.SuggestionProvider
 *  com.mojang.brigadier.tree.ArgumentCommandNode
 *  com.mojang.brigadier.tree.CommandNode
 *  com.mojang.brigadier.tree.LiteralCommandNode
 *  com.mojang.brigadier.tree.RootCommandNode
 *  it.unimi.dsi.fastutil.objects.Object2IntMap
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntMaps
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.suggestion.SuggestionProviders;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;

public class CommandTreeS2CPacket
implements Packet<ClientPlayPacketListener> {
    private RootCommandNode<CommandSource> commandTree;

    public CommandTreeS2CPacket() {
    }

    public CommandTreeS2CPacket(RootCommandNode<CommandSource> commandTree) {
        this.commandTree = commandTree;
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        CommandNodeData[] commandNodeDataArray = new CommandNodeData[buf.readVarInt()];
        for (int i = 0; i < commandNodeDataArray.length; ++i) {
            commandNodeDataArray[i] = CommandTreeS2CPacket.readCommandNode(buf);
        }
        CommandTreeS2CPacket.method_30946(commandNodeDataArray);
        this.commandTree = (RootCommandNode)commandNodeDataArray[buf.readVarInt()].node;
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        Object2IntMap<CommandNode<CommandSource>> object2IntMap = CommandTreeS2CPacket.method_30944(this.commandTree);
        CommandNode<CommandSource>[] _snowman2 = CommandTreeS2CPacket.method_30945(object2IntMap);
        buf.writeVarInt(_snowman2.length);
        for (CommandNode<CommandSource> commandNode : _snowman2) {
            CommandTreeS2CPacket.writeNode(buf, commandNode, object2IntMap);
        }
        buf.writeVarInt(object2IntMap.get(this.commandTree));
    }

    private static void method_30946(CommandNodeData[] commandNodeDataArray) {
        ArrayList arrayList = Lists.newArrayList((Object[])commandNodeDataArray);
        while (!arrayList.isEmpty()) {
            boolean bl = arrayList.removeIf(commandNodeData -> commandNodeData.build(commandNodeDataArray));
            if (bl) continue;
            throw new IllegalStateException("Server sent an impossible command tree");
        }
    }

    private static Object2IntMap<CommandNode<CommandSource>> method_30944(RootCommandNode<CommandSource> rootCommandNode) {
        Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
        ArrayDeque _snowman2 = Queues.newArrayDeque();
        _snowman2.add(rootCommandNode);
        while ((_snowman = (CommandNode)_snowman2.poll()) != null) {
            if (object2IntOpenHashMap.containsKey((Object)_snowman)) continue;
            int n = object2IntOpenHashMap.size();
            object2IntOpenHashMap.put((Object)_snowman, n);
            _snowman2.addAll(_snowman.getChildren());
            if (_snowman.getRedirect() == null) continue;
            _snowman2.add(_snowman.getRedirect());
        }
        return object2IntOpenHashMap;
    }

    private static CommandNode<CommandSource>[] method_30945(Object2IntMap<CommandNode<CommandSource>> object2IntMap) {
        CommandNode[] commandNodeArray = new CommandNode[object2IntMap.size()];
        for (Object2IntMap.Entry entry : Object2IntMaps.fastIterable(object2IntMap)) {
            commandNodeArray[entry.getIntValue()] = (CommandNode)entry.getKey();
        }
        return commandNodeArray;
    }

    private static CommandNodeData readCommandNode(PacketByteBuf packetByteBuf) {
        byte by = packetByteBuf.readByte();
        int[] _snowman2 = packetByteBuf.readIntArray();
        int _snowman3 = (by & 8) != 0 ? packetByteBuf.readVarInt() : 0;
        ArgumentBuilder<CommandSource, ?> _snowman4 = CommandTreeS2CPacket.readArgumentBuilder(packetByteBuf, by);
        return new CommandNodeData(_snowman4, by, _snowman3, _snowman2);
    }

    @Nullable
    private static ArgumentBuilder<CommandSource, ?> readArgumentBuilder(PacketByteBuf packetByteBuf, byte by) {
        int n = by & 3;
        if (n == 2) {
            String string = packetByteBuf.readString(Short.MAX_VALUE);
            ArgumentType<?> _snowman2 = ArgumentTypes.fromPacket(packetByteBuf);
            if (_snowman2 == null) {
                return null;
            }
            RequiredArgumentBuilder _snowman3 = RequiredArgumentBuilder.argument((String)string, _snowman2);
            if ((by & 0x10) != 0) {
                _snowman3.suggests(SuggestionProviders.byId(packetByteBuf.readIdentifier()));
            }
            return _snowman3;
        }
        if (n == 1) {
            return LiteralArgumentBuilder.literal((String)packetByteBuf.readString(Short.MAX_VALUE));
        }
        return null;
    }

    private static void writeNode(PacketByteBuf packetByteBuf, CommandNode<CommandSource> commandNode2, Map<CommandNode<CommandSource>, Integer> map) {
        CommandNode<CommandSource> commandNode2;
        int n = 0;
        if (commandNode2.getRedirect() != null) {
            n = (byte)(n | 8);
        }
        if (commandNode2.getCommand() != null) {
            n = (byte)(n | 4);
        }
        if (commandNode2 instanceof RootCommandNode) {
            n = (byte)(n | 0);
        } else if (commandNode2 instanceof ArgumentCommandNode) {
            n = (byte)(n | 2);
            if (((ArgumentCommandNode)commandNode2).getCustomSuggestions() != null) {
                n = (byte)(n | 0x10);
            }
        } else if (commandNode2 instanceof LiteralCommandNode) {
            n = (byte)(n | 1);
        } else {
            throw new UnsupportedOperationException("Unknown node type " + commandNode2);
        }
        packetByteBuf.writeByte(n);
        packetByteBuf.writeVarInt(commandNode2.getChildren().size());
        for (CommandNode commandNode3 : commandNode2.getChildren()) {
            packetByteBuf.writeVarInt(map.get(commandNode3));
        }
        if (commandNode2.getRedirect() != null) {
            packetByteBuf.writeVarInt(map.get(commandNode2.getRedirect()));
        }
        if (commandNode2 instanceof ArgumentCommandNode) {
            ArgumentCommandNode argumentCommandNode = (ArgumentCommandNode)commandNode2;
            packetByteBuf.writeString(argumentCommandNode.getName());
            ArgumentTypes.toPacket(packetByteBuf, argumentCommandNode.getType());
            if (argumentCommandNode.getCustomSuggestions() != null) {
                packetByteBuf.writeIdentifier(SuggestionProviders.computeName((SuggestionProvider<CommandSource>)argumentCommandNode.getCustomSuggestions()));
            }
        } else if (commandNode2 instanceof LiteralCommandNode) {
            packetByteBuf.writeString(((LiteralCommandNode)commandNode2).getLiteral());
        }
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        clientPlayPacketListener.onCommandTree(this);
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

        public boolean build(CommandNodeData[] previousNodes) {
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
                        this.argumentBuilder.executes(commandContext -> 0);
                    }
                    this.node = this.argumentBuilder.build();
                }
            }
            for (int n : this.childNodeIndices) {
                if (previousNodes[n].node != null) continue;
                return false;
            }
            for (int n : this.childNodeIndices) {
                CommandNode<CommandSource> commandNode = previousNodes[n].node;
                if (commandNode instanceof RootCommandNode) continue;
                this.node.addChild(commandNode);
            }
            return true;
        }
    }
}

