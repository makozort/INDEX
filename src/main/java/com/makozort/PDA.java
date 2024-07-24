package com.makozort;


import com.makozort.networking.BookPayload;
import com.makozort.reg.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.Map;

public class PDA implements ModInitializer {
    public static final String MOD_ID = "pda";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final Map<String, String> logmap = new HashMap<>();
    public static final Identifier DATA_READIN_TEST = Identifier.of(PDA.MOD_ID, "data/logbooks");




    @Override
    public void onInitialize() {


        ModItems.initialize();


        // In your common initializer method
        PayloadTypeRegistry.playS2C().register(BookPayload.ID, BookPayload.CODEC);


    }
}