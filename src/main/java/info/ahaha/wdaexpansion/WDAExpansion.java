package info.ahaha.wdaexpansion;

import org.bukkit.plugin.java.JavaPlugin;

public final class WDAExpansion extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new WDAEnabledListener(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
