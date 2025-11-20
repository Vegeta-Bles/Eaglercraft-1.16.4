/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.network;

import com.mojang.datafixers.util.Pair;
import java.net.IDN;
import java.util.Hashtable;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

public class ServerAddress {
    private final String address;
    private final int port;

    private ServerAddress(String address, int n) {
        this.address = address;
        this.port = n;
    }

    public String getAddress() {
        try {
            return IDN.toASCII(this.address);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return "";
        }
    }

    public int getPort() {
        return this.port;
    }

    public static ServerAddress parse(String address) {
        String string;
        Object _snowman2;
        if (address == null) {
            return null;
        }
        String[] _snowman3 = address.split(":");
        if (address.startsWith("[") && (_snowman = address.indexOf("]")) > 0) {
            String string2 = address.substring(1, _snowman);
            _snowman2 = address.substring(_snowman + 1).trim();
            if (_snowman2.startsWith(":") && !_snowman2.isEmpty()) {
                _snowman2 = _snowman2.substring(1);
                _snowman3 = new String[]{string2, _snowman2};
            } else {
                _snowman3 = new String[]{string2};
            }
        }
        if (_snowman3.length > 2) {
            _snowman3 = new String[]{address};
        }
        string = _snowman3[0];
        int n = n2 = _snowman3.length > 1 ? ServerAddress.portOrDefault(_snowman3[1], 25565) : 25565;
        if (n2 == 25565) {
            _snowman2 = ServerAddress.resolveServer(string);
            string = (String)_snowman2.getFirst();
            int n2 = (Integer)_snowman2.getSecond();
        }
        return new ServerAddress(string, n2);
    }

    private static Pair<String, Integer> resolveServer(String address) {
        try {
            String string = "com.sun.jndi.dns.DnsContextFactory";
            Class.forName("com.sun.jndi.dns.DnsContextFactory");
            Hashtable<String, String> _snowman2 = new Hashtable<String, String>();
            _snowman2.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            _snowman2.put("java.naming.provider.url", "dns:");
            _snowman2.put("com.sun.jndi.dns.timeout.retries", "1");
            InitialDirContext _snowman3 = new InitialDirContext(_snowman2);
            Attributes _snowman4 = _snowman3.getAttributes("_minecraft._tcp." + address, new String[]{"SRV"});
            Attribute _snowman5 = _snowman4.get("srv");
            if (_snowman5 != null) {
                String[] stringArray = _snowman5.get().toString().split(" ", 4);
                return Pair.of((Object)stringArray[3], (Object)ServerAddress.portOrDefault(stringArray[2], 25565));
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return Pair.of((Object)address, (Object)25565);
    }

    private static int portOrDefault(String port, int def) {
        try {
            return Integer.parseInt(port.trim());
        }
        catch (Exception exception) {
            return def;
        }
    }
}

