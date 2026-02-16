package com.devmaster.random_events.misc;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class TooltipRomanFix {

    @SubscribeEvent
    public static void onTooltipRender(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();

        if (stack.isEnchanted()) {
            List<ITextComponent> tooltip = event.getToolTip();
            Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(stack);

            int index = 0;
            for (Map.Entry<Enchantment, Integer> entry : enchants.entrySet()) {
                Enchantment enchant = entry.getKey();
                int level = entry.getValue();
                String roman = toRoman(level);

                // Build proper display line (like vanilla does)
                IFormattableTextComponent enchText = new TranslationTextComponent(enchant.getName());
                enchText.appendString(" ").appendString(roman);
                enchText.mergeStyle(TextFormatting.GRAY);

                // Replace the vanilla "enchantment.level.x" line
                if (index + 1 < tooltip.size()) {
                    tooltip.set(index + 1, enchText);
                }
                index++;
            }
        }
    }

    private static String toRoman(int number) {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        map.put("M", 1000);
        map.put("CM", 900);
        map.put("D", 500);
        map.put("CD", 400);
        map.put("C", 100);
        map.put("XC", 90);
        map.put("L", 50);
        map.put("XL", 40);
        map.put("X", 10);
        map.put("IX", 9);
        map.put("V", 5);
        map.put("IV", 4);
        map.put("I", 1);

        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            while (number >= entry.getValue()) {
                number -= entry.getValue();
                result.append(entry.getKey());
            }
        }
        return result.toString();
    }
}