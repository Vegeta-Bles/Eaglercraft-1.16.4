/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resource.metadata;

import java.util.Collection;
import net.minecraft.client.resource.language.LanguageDefinition;
import net.minecraft.client.resource.metadata.LanguageResourceMetadataReader;

public class LanguageResourceMetadata {
    public static final LanguageResourceMetadataReader READER = new LanguageResourceMetadataReader();
    private final Collection<LanguageDefinition> definitions;

    public LanguageResourceMetadata(Collection<LanguageDefinition> collection) {
        this.definitions = collection;
    }

    public Collection<LanguageDefinition> getLanguageDefinitions() {
        return this.definitions;
    }
}

