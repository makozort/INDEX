package com.makozort.reg;

import com.makozort.PDA;
import com.makozort.item.PDAItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {


    public static final Item PDA_ITEM = register(
            // Ignore the food component for now, we'll cover it later in the food section.
            new PDAItem(new Item.Settings()),
            "pda"
    );

    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = Identifier.of(PDA.MOD_ID, id);

        // Register the item.
        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static void initialize() {
    }
}