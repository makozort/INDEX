package com.makozort.item;


import com.makozort.networking.BookPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
net.minecraft.world.item.Item

public class PDAItem extends Item {
    public PDAItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) return super.use(world, user, hand);


        ServerPlayNetworking.send((ServerPlayerEntity) user, new BookPayload("hi"));
        return super.use(world, user, hand);
    }
}
