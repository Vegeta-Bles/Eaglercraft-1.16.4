/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.client.realms;

import com.google.common.collect.Lists;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.realms.dto.RegionPingResult;
import net.minecraft.util.Util;

public class Ping {
    public static List<RegionPingResult> ping(Region ... regions) {
        for (Region region : regions) {
            Ping.ping(region.endpoint);
        }
        ArrayList arrayList = Lists.newArrayList();
        for (Region region : regions) {
            arrayList.add(new RegionPingResult(region.name, Ping.ping(region.endpoint)));
        }
        arrayList.sort(Comparator.comparingInt(RegionPingResult::getPing));
        return arrayList;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static int ping(String host) {
        int n = 700;
        long _snowman2 = 0L;
        Socket _snowman3 = null;
        for (_snowman = 0; _snowman < 5; ++_snowman) {
            try {
                InetSocketAddress inetSocketAddress = new InetSocketAddress(host, 80);
                _snowman3 = new Socket();
                long _snowman4 = Ping.now();
                _snowman3.connect(inetSocketAddress, 700);
                _snowman2 += Ping.now() - _snowman4;
                Ping.close(_snowman3);
                continue;
            }
            catch (Exception exception) {
                _snowman2 += 700L;
                continue;
            }
            finally {
                Ping.close(_snowman3);
            }
        }
        return (int)((double)_snowman2 / 5.0);
    }

    private static void close(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }

    private static long now() {
        return Util.getMeasuringTimeMs();
    }

    public static List<RegionPingResult> pingAllRegions() {
        return Ping.ping(Region.values());
    }

    static enum Region {
        US_EAST_1("us-east-1", "ec2.us-east-1.amazonaws.com"),
        US_WEST_2("us-west-2", "ec2.us-west-2.amazonaws.com"),
        US_WEST_1("us-west-1", "ec2.us-west-1.amazonaws.com"),
        EU_WEST_1("eu-west-1", "ec2.eu-west-1.amazonaws.com"),
        AP_SOUTHEAST_1("ap-southeast-1", "ec2.ap-southeast-1.amazonaws.com"),
        AP_SOUTHEAST_2("ap-southeast-2", "ec2.ap-southeast-2.amazonaws.com"),
        AP_NORTHEAST_1("ap-northeast-1", "ec2.ap-northeast-1.amazonaws.com"),
        SA_EAST_1("sa-east-1", "ec2.sa-east-1.amazonaws.com");

        private final String name;
        private final String endpoint;

        private Region(String name, String endpoint) {
            this.name = name;
            this.endpoint = endpoint;
        }
    }
}

