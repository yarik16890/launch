package com.addon.modulartech.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("modulartech");

    public static void init() {
        INSTANCE.registerMessage(ModeSwitchMessage.Handler.class, ModeSwitchMessage.class, 0, Side.SERVER);
        INSTANCE.registerMessage(IntegratorRunMessage.Handler.class, IntegratorRunMessage.class, 1, Side.SERVER);
        INSTANCE.registerMessage(SelectModuleMessage.Handler.class, SelectModuleMessage.class, 2, Side.SERVER);
    }
}
