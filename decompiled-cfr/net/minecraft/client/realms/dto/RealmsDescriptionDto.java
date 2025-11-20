/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 */
package net.minecraft.client.realms.dto;

import com.google.gson.annotations.SerializedName;
import net.minecraft.client.realms.RealmsSerializable;
import net.minecraft.client.realms.dto.ValueObject;

public class RealmsDescriptionDto
extends ValueObject
implements RealmsSerializable {
    @SerializedName(value="name")
    public String name;
    @SerializedName(value="description")
    public String description;

    public RealmsDescriptionDto(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

