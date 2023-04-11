package me.melontini.recipebookispain.mixin;

import me.melontini.recipebookispain.RecipeBookIsPainClient;
import net.minecraft.client.gui.screen.recipebook.RecipeGroupButtonWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemGroup;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.melontini.recipebookispain.RecipeBookIsPainClient.rbip$renderOwo;
import static me.melontini.recipebookispain.RecipeBookIsPainClient.renderCracker;

@Mixin(RecipeGroupButtonWidget.class)
public abstract class RecipeGroupButtonMixin extends ToggleButtonWidget {
    @Shadow
    @Final
    private RecipeBookGroup category;

    public RecipeGroupButtonMixin(int x, int y, int width, int height, boolean toggled) {
        super(x, y, width, height, toggled);
    }

    @Inject(at = @At("HEAD"), method = "renderIcons", cancellable = true)
    private void rbip$render(MatrixStack matrices, ItemRenderer itemRenderer, CallbackInfo ci) {
        ItemGroup group = RecipeBookIsPainClient.RECIPE_BOOK_GROUP_TO_ITEM_GROUP.get(this.category);
        if (group == null) return;

        int i = this.toggled ? -2 : 0;

        if (RecipeBookIsPainClient.isOwOLoaded) {
            if (rbip$renderOwo(matrices, i, (RecipeGroupButtonWidget) (Object) this, group)) {
                ci.cancel();
                return;
            }
        }
        if (RecipeBookIsPainClient.isCrackerContentLoaded) {
            if (renderCracker(matrices, i, (RecipeGroupButtonWidget) (Object) this, group)) {
                ci.cancel();
            }
        }
    }
}