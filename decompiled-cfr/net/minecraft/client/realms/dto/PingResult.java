/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.gson.annotations.SerializedName
 */
package net.minecraft.client.realms.dto;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import net.minecraft.client.realms.RealmsSerializable;
import net.minecraft.client.realms.dto.RegionPingResult;
import net.minecraft.client.realms.dto.ValueObject;

public class PingResult
extends ValueObject
implements RealmsSerializable {
    @SerializedName(value="pingResults")
    public List<RegionPingResult> pingResults = Lists.newArrayList();
    @SerializedName(value="worldIds")
    public List<Long> worldIds = Lists.newArrayList();
}

