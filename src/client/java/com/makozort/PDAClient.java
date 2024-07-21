package com.makozort;

import com.makozort.networking.BookPayload;
import com.makozort.screens.CustomScreen;
import com.makozort.screens.LogEntry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class PDAClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		List<LogEntry>logs = new ArrayList<>();
		logs.add(new LogEntry("jack",Text.of("hi")));
		logs.add(new LogEntry("jack",Text.of("hi")));
		logs.add(new LogEntry("jack",Text.of("hi")));
		logs.add(new LogEntry("jack",Text.of("hi")));
		logs.add(new LogEntry("jack",Text.of("hi")));
		logs.add(new LogEntry("jack",Text.of("hi")));
		logs.add(new LogEntry("jack",Text.of("hi")));
		logs.add(new LogEntry("jack",Text.of("hi")));

		ClientPlayNetworking.registerGlobalReceiver(BookPayload.ID, (payload, context) -> {
			context.client().execute(() -> {
				MinecraftClient.getInstance().setScreen(
						new CustomScreen(null,logs)
				);

			});
		});
	}
}