package net.hirukarogue.alchemistsdream.fluids;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class BasePotionFluidType extends FluidType {
    private final @Nullable MobEffect entityEffect;
    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;
    private final ResourceLocation overlayTexture;
    private final int tintColor;
    private final Vector3f fogColor;

    public BasePotionFluidType(ResourceLocation stillTexture, ResourceLocation flowingTexture, ResourceLocation overlayTexture, @Nullable MobEffect potionEffect, Properties properties) {
        super(properties);
        this.entityEffect = potionEffect;
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.overlayTexture = overlayTexture;
        if (this.entityEffect == null) {
            this.tintColor = 0;
        } else {
            this.tintColor = this.entityEffect.getColor();
        }
        this.fogColor = new Vector3f(224f/255f, 56f/255f, 208f/255f);
    }

    public ResourceLocation getStillTexture() {
        return this.stillTexture;
    }

    public ResourceLocation getFlowingTexture() {
        return this.flowingTexture;
    }

    public ResourceLocation getOverlayTexture() {
        return this.overlayTexture;
    }

    public int getTintColor() {
        return this.tintColor;
    }

    public Vector3f getFogColor() {
        return this.fogColor;
    }

    public @Nullable MobEffectInstance getEntityEffect() {
        if (entityEffect == null) return null;
        return new MobEffectInstance(entityEffect);
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return tintColor;
            }

            @Override
            public ResourceLocation getStillTexture() {
                return stillTexture;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flowingTexture;
            }

            @Override
            public @Nullable ResourceLocation getOverlayTexture() {
                return overlayTexture;
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                return fogColor;
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                RenderSystem.setShaderFogStart(1f);
                RenderSystem.setShaderFogStart(6f);
            }
        });
    }

    public static class Builder{
        private final String name;
        private @Nullable MobEffect entityEffect;
        private ResourceLocation stillTexture;
        private ResourceLocation flowingTexture;
        private ResourceLocation overlayTexture;
        private Properties properties;

        public Builder(String name) {
            this.name = name;
            this.stillTexture = new ResourceLocation("block/water_still");
            this.flowingTexture = new ResourceLocation("block/water_flow");
            this.overlayTexture = new ResourceLocation("block/water_overlay");
            this.properties = Properties.create();
            this.entityEffect = null;
        }

        public static Builder create(String name) {
            return new Builder(name);
        }

        public Builder setStillTexture(ResourceLocation texture) {
            this.stillTexture = texture;
            return this;
        }

        public Builder setFlowingTexture(ResourceLocation texture) {
            this.flowingTexture = texture;
            return this;
        }

        public Builder setOverlayTexture(ResourceLocation texture) {
            this.overlayTexture = texture;
            return this;
        }

        public Builder setProperties(Properties properties) {
            this.properties = properties;
            return this;
        }

        public Builder setEntityEffect(MobEffect effect) {
            this.entityEffect = effect;
            return this;
        }

        public BasePotionFluidType build() {
            return new BasePotionFluidType(
                    stillTexture,
                    flowingTexture,
                    overlayTexture,
                    entityEffect,
                    properties);
        }
    }
}
