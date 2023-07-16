package net.quarrymod;

import static reborncore.RebornRegistry.registerBlock;
import static reborncore.RebornRegistry.registerItem;

import java.util.Arrays;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.quarrymod.client.QuarryScreenRegistry;
import net.quarrymod.events.StackToolTipHandler;
import net.quarrymod.init.QuarryManagerContent;
import net.quarrymod.init.QuarryManagerContent.Machine;
import net.quarrymod.init.QuarryManagerContent.Upgrades;
import net.quarrymod.init.QuarryModBlockEntities;
import techreborn.TechReborn;
import techreborn.init.TRItemGroup;

public class RegistryManager {

    private static final Settings itemGroupSettings = new Settings();
    private static final RegistryKey<ItemGroup> ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(TechReborn.MOD_ID, "item_group"));

    private RegistryManager() {
    }

    public static Settings getItemGroupSettings() {
        return itemGroupSettings;
    }

    public static void Init() {
        registerBlock(QuarryManagerContent.DRILL_TUBE,
            itemGroupSettings,
            new Identifier(QuarryMod.MOD_ID, "drill_tube"));

        Arrays.stream(Machine.values()).forEach(
            value ->
                registerBlock(value.block,
                    itemGroupSettings,
                    new Identifier(QuarryMod.MOD_ID, value.name)));

        Arrays.stream(Upgrades.values()).forEach(
            value -> registerItem(value.item, new Identifier(QuarryMod.MOD_ID, value.name)));
        QuarryModBlockEntities.init();

        ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(entries -> {
            entries.add(QuarryManagerContent.DRILL_TUBE);
            for (var machine : Machine.values()) {
                entries.add(machine.block);
            }
            for (var upgrade : Upgrades.values()) {
                entries.add(upgrade.item);
            }
        });
    }

    @SuppressWarnings("MethodCallSideOnly")
    public static void ClientInit() {

        StackToolTipHandler.setup();
        QuarryScreenRegistry.init();
    }
}
