package cz.larkyy.aquaticlib.menus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public record MenuItem(String title,
                       List<String> description,

                       Set<Integer> slots,
                       Material material,
                       int amount,
                       int model,
                       Consumer<ItemStack> itemFactory) {

    /**
     * Builds menu item as item stack, applies item factory.
     **/
    public ItemStack build() {
        final var item = new Item(this.material,this.amount,this);
        final var meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(title);
            meta.setLore(description);
            if (model > 0)
                meta.setCustomModelData(model);

            item.setItemMeta(meta);
        }

        this.itemFactory.accept(item);
        return item;
    }

    public static class Item extends ItemStack {

        protected MenuItem ref;

        public Item(Material type, int amount, MenuItem ref) {
            super(type, amount);
            this.ref = ref;
        }
    }

}
