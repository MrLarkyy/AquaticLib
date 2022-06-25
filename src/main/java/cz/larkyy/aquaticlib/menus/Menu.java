package cz.larkyy.aquaticlib.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class Menu {

    private String title;
    private int rows;

    private Controller controller;
    private Holder holder;

    private final Map<MenuItem, Consumer<Player>> items
            = new HashMap<>();

    public void destroy() {
        this.controller.unregister();
        this.holder.getInventory().getViewers().
                forEach(HumanEntity::closeInventory);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Menu menu = new Menu();

        public Builder add(String title, List<String> description, Set<Integer> slots,
                           Material material, int amount, int model,
                           Consumer<ItemStack> itemFactory,
                           Consumer<Player> callback) {
            final var menuItem = new MenuItem(title, description, slots, material, amount, model, itemFactory);
            this.menu.items.put(menuItem, callback);
            return this;
        }

        public Builder add(MenuItem menuItem, Consumer<Player> callback) {
            this.menu.items.put(menuItem, callback);
            return this;
        }

        public Menu build(JavaPlugin plugin) {
            menu.holder = new Holder(this.menu);
            {
                final var inventory = Bukkit.createInventory(menu.holder, menu.rows * 9, menu.title);
                menu.holder.inventory = inventory;
                menu.items.forEach((menuItem, playerConsumer) -> {
                    final var itemStack = menuItem.build();
                    menuItem.slots().forEach(slot -> inventory.setItem(slot,itemStack));
                });
            }
            menu.controller = new Controller();
            menu.controller.register(plugin);
            return menu;
        }
    }

    public static class Controller
            implements Listener {

        @EventHandler
        public void inventoryDragEvent(InventoryDragEvent e) {
            if (e.getInventory().getHolder() instanceof Holder) {
                e.setCancelled(true);
            }
        }

        @EventHandler
        public void inventoryMoveItemEvent(InventoryMoveItemEvent e) {
            if (e.getDestination().getHolder() instanceof Holder || e.getSource().getHolder() instanceof Holder) {
                e.setCancelled(true);
            }
        }

        @EventHandler
        public void inventoryClickEvent(InventoryClickEvent e) {
            if (e.getInventory().getHolder() instanceof Holder holder && e.getCurrentItem() instanceof MenuItem.Item menuItemStack) {
                e.setCancelled(true);

                holder.ref.items.get(menuItemStack.ref).accept((Player) e.getWhoClicked());
            }
        }

        /**
         * Registers as a bukkit listener.
         *
         * @param plugin Plugin instance.
         */
        public void register(JavaPlugin plugin) {
            Bukkit.getPluginManager().registerEvents(this, plugin);
        }

        /**
         * Unregisters a bukkit listener.
         */
        public void unregister() {
            HandlerList.unregisterAll(this);
        }

    }

    public static class Holder
            implements InventoryHolder {

        private final Menu ref;
        private Inventory inventory;

        public Holder(Menu ref) {
            this.ref = ref;
        }

        @Override
        public Inventory getInventory() {
            return inventory;
        }
    }

}
