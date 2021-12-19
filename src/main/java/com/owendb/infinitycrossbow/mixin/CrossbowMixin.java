package com.owendb.infinitycrossbow.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CrossbowItem.class)
public abstract class CrossbowMixin {

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;findAmmo(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"), method = "onItemRightClick")
    private ItemStack onItemRightClickInUse(PlayerEntity instance, ItemStack bow) {
        int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow);
        if (i > 0) {
            return new ItemStack(Items.ARROW);
        }
        return instance.findAmmo(bow);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/CrossbowItem;func_220023_a(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;ZZ)Z"), method = "hasAmmo")
    private static boolean onHasAmmoInTryLoadProjectiles(LivingEntity entity, ItemStack bow, ItemStack projectile, boolean p_40866_, boolean p_40867_) {
        if (entity instanceof PlayerEntity) {
            int i = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow);
            if (i > 0) {
                addChargedProjectile(bow, new ItemStack(Items.ARROW));
                return true;
            }
        }
        return func_220023_a(entity, bow, projectile, p_40866_, p_40867_);
    }

    @Shadow
    protected static void addChargedProjectile(ItemStack p_40929_, ItemStack p_40930_) {
    }

    @Shadow
    protected static boolean func_220023_a(LivingEntity p_40863_, ItemStack p_40864_, ItemStack p_40865_, boolean p_40866_, boolean p_40867_) {
        return false;
    }

}