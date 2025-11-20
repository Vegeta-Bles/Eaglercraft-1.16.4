/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.text;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.EntitySelectorReader;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.ParsableText;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SelectorText
extends BaseText
implements ParsableText {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String pattern;
    @Nullable
    private final EntitySelector selector;

    public SelectorText(String pattern) {
        this.pattern = pattern;
        EntitySelector _snowman2 = null;
        try {
            EntitySelectorReader entitySelectorReader = new EntitySelectorReader(new StringReader(pattern));
            _snowman2 = entitySelectorReader.read();
        }
        catch (CommandSyntaxException commandSyntaxException) {
            LOGGER.warn("Invalid selector component: {}", (Object)pattern, (Object)commandSyntaxException.getMessage());
        }
        this.selector = _snowman2;
    }

    public String getPattern() {
        return this.pattern;
    }

    @Override
    public MutableText parse(@Nullable ServerCommandSource source, @Nullable Entity sender, int depth) throws CommandSyntaxException {
        if (source == null || this.selector == null) {
            return new LiteralText("");
        }
        return EntitySelector.getNames(this.selector.getEntities(source));
    }

    @Override
    public String asString() {
        return this.pattern;
    }

    @Override
    public SelectorText copy() {
        return new SelectorText(this.pattern);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof SelectorText) {
            SelectorText selectorText = (SelectorText)object;
            return this.pattern.equals(selectorText.pattern) && super.equals(object);
        }
        return false;
    }

    @Override
    public String toString() {
        return "SelectorComponent{pattern='" + this.pattern + '\'' + ", siblings=" + this.siblings + ", style=" + this.getStyle() + '}';
    }

    @Override
    public /* synthetic */ BaseText copy() {
        return this.copy();
    }

    @Override
    public /* synthetic */ MutableText copy() {
        return this.copy();
    }
}

