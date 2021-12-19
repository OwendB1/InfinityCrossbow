package com.owendb.infinitycrossbow.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.InfinityEnchantment;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class InfinityMixin {

    @Inject(at = @At("HEAD"), method = "canApply", cancellable = true)
    private void onCanApply(ItemStack itemToEnchant, CallbackInfoReturnable<Boolean> info) {
        if (itemToEnchant.getItem() instanceof CrossbowItem) {
            Enchantment e = (Enchantment) ((Object)this);
            if (e instanceof InfinityEnchantment) {
                info.setReturnValue(true);
            }
        }
    }
}
