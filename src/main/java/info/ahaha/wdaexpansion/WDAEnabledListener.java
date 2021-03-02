package info.ahaha.wdaexpansion;

import info.ahaha.welldebugapi.EventDebugging;
import info.ahaha.welldebugapi.WellDebugAPI;
import info.ahaha.welldebugapi.event.WellDebugAPIEnabledEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.*;

import static org.bukkit.Bukkit.getServer;

public class WDAEnabledListener implements Listener {
    @EventHandler
    public void on(WellDebugAPIEnabledEvent event) {
        Thread th = new Thread(() -> {
            WellDebugAPI.addExecutorList(new EventDebugging<>(PlayerEvent.class,
                    (e) -> e.getPlayer().getName() + "/" + e.getPlayer().getLocation()));
            WellDebugAPI.addExecutorList(new EventDebugging<>(EntityEvent.class,
                    (e) -> e.getEntityType() + "/" + e.getEntity().getLocation()));
            WellDebugAPI.addExecutorList(new EventDebugging<>(BlockEvent.class,
                    (e) -> e.getBlock().getType() + "/" + e.getBlock().getLocation()));

            //Block
            WellDebugAPI.addExecutorList(new EventDebugging<>(BlockBreakEvent.class,
                    (e) -> "By " + e.getPlayer().getName()));
            WellDebugAPI.addExecutorList(new EventDebugging<>(BlockPlaceEvent.class,
                    (e) -> "By " + e.getPlayer().getName()));

            //Player
            WellDebugAPI.addExecutorList(new EventDebugging<>(PlayerTeleportEvent.class,
                    (e) -> "From " + e.getFrom() + " / " + "To " + e.getTo()));
            WellDebugAPI.addExecutorList(new EventDebugging<>(PlayerDropItemEvent.class,
                    (e) -> "Is " + e.getItemDrop().getItemStack().getType()));
            WellDebugAPI.addExecutorList(new EventDebugging<>(PlayerPickupItemEvent.class,
                    (e) -> "Is " + e.getItem().getItemStack().getType()));
            WellDebugAPI.addExecutorList(new EventDebugging<>(PlayerInteractAtEntityEvent.class,
                    (e) -> "At " + e.getRightClicked().getType()));
            WellDebugAPI.addExecutorList(new EventDebugging<>(PlayerInteractEntityEvent.class,
                    (e) -> "At " + e.getRightClicked().getType()));
            WellDebugAPI.addExecutorList(new EventDebugging<>(PlayerInteractEvent.class,
                    (e) -> {
                        if (e.getItem() != null)
                            if (e.getItem().getType() == Material.FLINT_AND_STEEL)
                                return "火打石!!";
                        throw new EventDebugging.Pass();
                    }));
            WellDebugAPI.addExecutorList(new EventDebugging<>(PlayerInteractEvent.class,
                    (e) -> {
                        if (e.hasBlock())
                            if (e.getClickedBlock().getType() == Material.TNT)
                                return "TNT!!";
                        throw new EventDebugging.Pass();
                    }));

            //Entity
            WellDebugAPI.addExecutorList(new EventDebugging<>(EntityDeathEvent.class,
                    (e) -> {
                        if (e.getEntity().getKiller() != null)
                            return e.getEntity().getType() + " kill by " + e.getEntity().getKiller().getName();
                        return String.valueOf(e.getEntity().getType());
                    }));

            getServer().getLogger().info("WDAExpansion added");
        });
        th.setDaemon(true);
        th.start();
    }
}
