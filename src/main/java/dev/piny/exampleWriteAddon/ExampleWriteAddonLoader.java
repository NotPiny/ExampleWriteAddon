package dev.piny.exampleWriteAddon;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;

class ExampleWriteAddonLoader implements PluginLoader {

    @Override
    public void classloader(final PluginClasspathBuilder builder) {
        // Add dynamically loaded libraries here
    }
}
