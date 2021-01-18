package info.ahaha.wdaexpansion;

import info.ahaha.welldebugapi.EventDebugging;
import info.ahaha.welldebugapi.WellDebugAPI;
import info.ahaha.welldebugapi.event.WellDebugAPIEnabledEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.player.PlayerEvent;

import javax.swing.text.html.parser.Entity;

import static org.bukkit.Bukkit.getServer;

public class WDAEnabledListener implements Listener {
    @EventHandler
    public void on(WellDebugAPIEnabledEvent e) {
        Thread th = new Thread(() -> {
            WellDebugAPI.addExecutorList(new EventDebugging<>(BlockBreakEvent.class,
                    (event) -> {
                        if (event.getBlock().getType() == Material.ANCIENT_DEBRIS)
                            return "Ancient : " + event.getPlayer().getName();

                        return "by " + event.getPlayer().getName();
                    }));
            WellDebugAPI.addExecutorList(new EventDebugging<>(PlayerEvent.class,
                    (event) -> event.getPlayer().getName() + "/" + event.getPlayer().getLocation()));
            WellDebugAPI.addExecutorList(new EventDebugging<>(BlockEvent.class,
                    (event) -> event.getBlock().getType() + "/" + event.getBlock().getLocation()));

            getServer().getLogger().info("WDAExpansion added");
        });
        th.setDaemon(true);
        th.start();
    }
}
