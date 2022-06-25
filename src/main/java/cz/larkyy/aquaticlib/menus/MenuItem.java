package cz.larkyy.aquaticlib.menus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;
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

    public static Builder builder(Material material) {
        return new Builder(material);
    }

    public static class Builder {

        private String title;
        private List<String> description;

        private Set<Integer> slots;
        private Material material;
        private int amount;
        private int model;
        private Consumer<ItemStack> itemFactory;

        public Builder(Material material) {
            this.material = material;

            title = "";
            description = new ArrayList<>();
            slots = new HashSet<>();
            amount = 1;
            model = -1;
            itemFactory = itemStack -> {};
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(List<String> description) {
            this.description = description;
            return this;
        }

        public Builder description(String... description) {
            this.description = Arrays.asList(description);
            return this;
        }

        public Builder slots(Set<Integer> slots) {
            this.slots = slots;
            return this;
        }

        public Builder slots(List<Integer> slots) {
            this.slots = Set.copyOf(slots);
            return this;
        }

        public Builder slots(Integer... slots) {
            this.slots = Set.of(slots);
            return this;
        }

        public Builder material(Material material) {
            this.material = material;
            return this;
        }

        public Builder amount(int amount) {
            this.amount = amount;
            return this;
        }

        public Builder model(int model) {
            this.model = model;
            return this;
        }

        public Builder itemFactory(Consumer<ItemStack> itemFactory) {
            this.itemFactory = itemFactory;
            return this;
        }

        public ItemStack build() {
            return new MenuItem(title,description,slots,material,amount,model,itemFactory).build();
        }
    }

    public static class Item extends ItemStack {

        protected MenuItem ref;

        public Item(Material type, int amount, MenuItem ref) {
            super(type, amount);
            this.ref = ref;
        }
    }

}
