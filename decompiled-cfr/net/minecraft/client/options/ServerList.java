/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.options;

import com.google.common.collect.Lists;
import java.io.File;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerList {
    private static final Logger LOGGER = LogManager.getLogger();
    private final MinecraftClient client;
    private final List<ServerInfo> servers = Lists.newArrayList();

    public ServerList(MinecraftClient client) {
        this.client = client;
        this.loadFile();
    }

    public void loadFile() {
        try {
            this.servers.clear();
            CompoundTag compoundTag = NbtIo.read(new File(this.client.runDirectory, "servers.dat"));
            if (compoundTag == null) {
                return;
            }
            ListTag _snowman2 = compoundTag.getList("servers", 10);
            for (int i = 0; i < _snowman2.size(); ++i) {
                this.servers.add(ServerInfo.deserialize(_snowman2.getCompound(i)));
            }
        }
        catch (Exception exception) {
            LOGGER.error("Couldn't load server list", (Throwable)exception);
        }
    }

    public void saveFile() {
        try {
            ListTag listTag = new ListTag();
            for (ServerInfo serverInfo : this.servers) {
                listTag.add(serverInfo.serialize());
            }
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.put("servers", listTag);
            File file = File.createTempFile("servers", ".dat", this.client.runDirectory);
            NbtIo.write(compoundTag, file);
            File _snowman3 = new File(this.client.runDirectory, "servers.dat_old");
            File _snowman4 = new File(this.client.runDirectory, "servers.dat");
            Util.backupAndReplace(_snowman4, file, _snowman3);
        }
        catch (Exception exception) {
            LOGGER.error("Couldn't save server list", (Throwable)exception);
        }
    }

    public ServerInfo get(int n) {
        return this.servers.get(n);
    }

    public void remove(ServerInfo serverInfo) {
        this.servers.remove(serverInfo);
    }

    public void add(ServerInfo serverInfo) {
        this.servers.add(serverInfo);
    }

    public int size() {
        return this.servers.size();
    }

    public void swapEntries(int index1, int n) {
        ServerInfo serverInfo = this.get(index1);
        this.servers.set(index1, this.get(n));
        this.servers.set(n, serverInfo);
        this.saveFile();
    }

    public void set(int index, ServerInfo serverInfo) {
        this.servers.set(index, serverInfo);
    }

    public static void updateServerListEntry(ServerInfo e) {
        ServerList serverList = new ServerList(MinecraftClient.getInstance());
        serverList.loadFile();
        for (int i = 0; i < serverList.size(); ++i) {
            ServerInfo serverInfo = serverList.get(i);
            if (!serverInfo.name.equals(e.name) || !serverInfo.address.equals(e.address)) continue;
            serverList.set(i, e);
            break;
        }
        serverList.saveFile();
    }
}

