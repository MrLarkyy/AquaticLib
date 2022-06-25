package cz.larkyy.aquaticlib.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Consumer;

public class ItemBuilder {

    private String title;
    private List<String> description;

    private Material material;
    private int amount;
    private int model;
    private Consumer<ItemStack> itemFactory;

    public ItemBuilder(Material material) {
        this.material = material;

        title = "";
        description = new ArrayList<>();
        amount = 1;
        model = -1;
        itemFactory = itemStack -> {
        };
    }

    public ItemBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ItemBuilder description(List<String> description) {
        this.description = description;
        return this;
    }

    public ItemBuilder description(String... description) {
        this.description = Arrays.asList(description);
        return this;
    }

    public ItemBuilder material(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder model(int model) {
        this.model = model;
        return this;
    }

    public ItemBuilder itemFactory(Consumer<ItemStack> itemFactory) {
        this.itemFactory = itemFactory;
        return this;
    }

    public ItemStack build() {
        final var item = new ItemStack(material,amount);
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
}
