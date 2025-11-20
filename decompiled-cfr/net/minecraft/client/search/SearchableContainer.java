/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.search;

import net.minecraft.client.search.Searchable;

public interface SearchableContainer<T>
extends Searchable<T> {
    public void add(T var1);

    public void clear();

    public void reload();
}

