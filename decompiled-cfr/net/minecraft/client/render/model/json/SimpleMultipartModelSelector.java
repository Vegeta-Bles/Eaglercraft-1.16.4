/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.MoreObjects
 *  com.google.common.base.Splitter
 */
package net.minecraft.client.render.model.json;

import com.google.common.base.MoreObjects;
import com.google.common.base.Splitter;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.json.MultipartModelSelector;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;

public class SimpleMultipartModelSelector
implements MultipartModelSelector {
    private static final Splitter VALUE_SPLITTER = Splitter.on((char)'|').omitEmptyStrings();
    private final String key;
    private final String valueString;

    public SimpleMultipartModelSelector(String key, String valueString) {
        this.key = key;
        this.valueString = valueString;
    }

    @Override
    public Predicate<BlockState> getPredicate(StateManager<Block, BlockState> stateManager) {
        Predicate<BlockState> _snowman4;
        Property<?> property = stateManager.getProperty(this.key);
        if (property == null) {
            throw new RuntimeException(String.format("Unknown property '%s' on '%s'", this.key, stateManager.getOwner().toString()));
        }
        String _snowman2 = this.valueString;
        boolean bl = _snowman = !_snowman2.isEmpty() && _snowman2.charAt(0) == '!';
        if (_snowman) {
            _snowman2 = _snowman2.substring(1);
        }
        if ((list = VALUE_SPLITTER.splitToList((CharSequence)_snowman2)).isEmpty()) {
            throw new RuntimeException(String.format("Empty value '%s' for property '%s' on '%s'", this.valueString, this.key, stateManager.getOwner().toString()));
        }
        if (list.size() == 1) {
            _snowman4 = this.createPredicate(stateManager, property, _snowman2);
        } else {
            List list;
            List _snowman3 = list.stream().map(string -> this.createPredicate(stateManager, property, (String)string)).collect(Collectors.toList());
            _snowman4 = blockState -> _snowman3.stream().anyMatch(predicate -> predicate.test(blockState));
        }
        return _snowman ? _snowman4.negate() : _snowman4;
    }

    private Predicate<BlockState> createPredicate(StateManager<Block, BlockState> stateFactory, Property<?> property, String valueString) {
        Optional<?> optional = property.parse(valueString);
        if (!optional.isPresent()) {
            throw new RuntimeException(String.format("Unknown value '%s' for property '%s' on '%s' in '%s'", valueString, this.key, stateFactory.getOwner().toString(), this.valueString));
        }
        return blockState -> blockState.get(property).equals(optional.get());
    }

    public String toString() {
        return MoreObjects.toStringHelper((Object)this).add("key", (Object)this.key).add("value", (Object)this.valueString).toString();
    }
}

