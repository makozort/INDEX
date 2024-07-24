package com.makozort.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketEncoder;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

import net.minecraft.network.packet.payload.CustomPayload;

public record BookPayload(String str) implements CustomPayload {
    public static final CustomPayload.Id<BookPayload> ID = new CustomPayload.Id<>(NetworkingConstants.HIGHLIGHT_PACKET_ID);
    public static final PacketCodec<ByteBuf, BookPayload> CODEC = PacketCodec.tuple(PacketCodecs.string(5),BookPayload::str, BookPayload::new);


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
