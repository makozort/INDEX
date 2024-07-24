package com.makozort;

import com.makozort.networking.BookPayload;
import com.makozort.screens.HomeScreen;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;


public class PDAClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		ClientPlayNetworking.registerGlobalReceiver(BookPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				MinecraftClient.getInstance().setScreen(
						new HomeScreen()
				);
			});
		});
	}
}