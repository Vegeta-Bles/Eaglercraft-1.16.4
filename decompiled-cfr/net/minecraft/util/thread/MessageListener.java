/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Either
 */
package net.minecraft.util.thread;

import com.mojang.datafixers.util.Either;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public interface MessageListener<Msg>
extends AutoCloseable {
    public String getName();

    public void send(Msg var1);

    @Override
    default public void close() {
    }

    default public <Source> CompletableFuture<Source> ask(Function<? super MessageListener<Source>, ? extends Msg> messageProvider) {
        CompletableFuture completableFuture = new CompletableFuture();
        Msg _snowman2 = messageProvider.apply(MessageListener.create("ask future procesor handle", completableFuture::complete));
        this.send(_snowman2);
        return completableFuture;
    }

    default public <Source> CompletableFuture<Source> method_27918(Function<? super MessageListener<Either<Source, Exception>>, ? extends Msg> function) {
        CompletableFuture completableFuture = new CompletableFuture();
        Msg _snowman2 = function.apply(MessageListener.create("ask future procesor handle", either -> {
            either.ifLeft(completableFuture::complete);
            either.ifRight(completableFuture::completeExceptionally);
        }));
        this.send(_snowman2);
        return completableFuture;
    }

    public static <Msg> MessageListener<Msg> create(String name, Consumer<Msg> action) {
        return new MessageListener<Msg>(name, action){
            final /* synthetic */ String field_17276;
            final /* synthetic */ Consumer field_17277;
            {
                this.field_17276 = string;
                this.field_17277 = consumer;
            }

            public String getName() {
                return this.field_17276;
            }

            public void send(Msg message) {
                this.field_17277.accept(message);
            }

            public String toString() {
                return this.field_17276;
            }
        };
    }
}

