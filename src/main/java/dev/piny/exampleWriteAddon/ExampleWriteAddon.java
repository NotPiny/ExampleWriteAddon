package dev.piny.exampleWriteAddon;

import dev.piny.write.block.GenericBlock;
import dev.piny.write.item.GenericItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.StonecuttingRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public final class ExampleWriteAddon extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        // All the methods on the GenericBlock and GenericItem objects are optional, apart from the register() method, which you must call to make it appear.
        GenericBlock exampleBlock = new GenericBlock(new NamespacedKey("example", "example_block"))
                .onPlace(event -> event.getPlayer().sendRichMessage("<green>Example block placed!"))
                .onInteract(event -> event.getPlayer().sendRichMessage("<yellow>Example block interacted!"))
                .onPlayerBreak(event -> event.getPlayer().sendRichMessage("<red>Example block broken by player!"))
                .onBlockExplodeBreak((event, instance) -> Bukkit.broadcast(Component.text("Example block broken by block explosion! (event=" + event + ", instance=" + instance + ")").color(NamedTextColor.RED)))
                .onEntityExplodeBreak((event, instance) -> Bukkit.broadcast(Component.text("Example block broken by entity explosion! (event=" + event + ", instance=" + instance + ")").color(NamedTextColor.RED)))
                .baseMaterial(Material.DIAMOND_BLOCK)
                .overrideVanillaBlockDrops(true) // This is default
                .rotatable(true) // This is default
                .onBreak((instanceData, breakReason) -> {});

        exampleBlock.register();

        ShapedRecipe recipe = new ShapedRecipe(exampleBlock.key(), exampleBlock.item().getItemStack());
        recipe.shape("DBD", "BDB", "DBD");
        recipe.setIngredient('D', Material.DEBUG_STICK);
        recipe.setIngredient('B', Material.BEDROCK);

        getServer().addRecipe(recipe, true);

        GenericBlock exampleBlockHead = new GenericBlock(new NamespacedKey("example", "example_block_head"));
        exampleBlockHead.register();

        RecipeChoice input = new RecipeChoice.ExactChoice(exampleBlock.item().getItemStack());
        StonecuttingRecipe stonecuttingRecipe = new StonecuttingRecipe(exampleBlockHead.key(), exampleBlockHead.item().getItemStack(), input);
        getServer().addRecipe(stonecuttingRecipe, true);

        new GenericItem(new NamespacedKey("example", "example_item"))
                .onInteract(event -> event.getPlayer().sendRichMessage("<blue>Example item interacted!"))
                .modify(item -> {
                    ItemStack newItem = item.withType(Material.MACE);
                    newItem.addEnchantment(Enchantment.BREACH, 4);
                    return newItem;
                })
                .maxStackSize(1)
                .register();

        new GenericItem(new NamespacedKey("example", "write_icon"))
                .register();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
