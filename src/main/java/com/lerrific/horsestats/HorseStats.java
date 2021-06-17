package com.lerrific.horsestats;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = "assets/lang/horsestats", name = "Horse Stats", version = "1.0.1")
public class HorseStats {

	public HorseStats() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void getHorseStats(EntityInteract event) {
		if (event.getEntityPlayer() != null && event.getWorld() != null && event.getTarget() instanceof AbstractHorse) {
			EntityPlayer player = event.getEntityPlayer();
			AbstractHorse horseEntity = (AbstractHorse) event.getTarget();
			if (player.isSneaking() && player.getHeldItemMainhand().getItem() == Items.STICK) {
				TextFormatting colourHealth = TextFormatting.WHITE;
				TextFormatting colourSpeed = TextFormatting.WHITE;
				TextFormatting colourJump = TextFormatting.WHITE;
				double health = horseEntity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue();
				double speed = horseEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
				double jump = horseEntity.getHorseJumpStrength();

				jump = -0.1817584952 * Math.pow(jump, 3) + 3.689713992 * Math.pow(jump, 2) + 2.128599134 * jump - 0.343930367; // https://minecraft.gamepedia.com/Horse
				speed = speed * 43;

				if (health <= 23) {
					colourHealth = TextFormatting.GREEN;
				} else if (health <= 28) {
					colourHealth = TextFormatting.YELLOW;
				} else if (health <= Double.MAX_VALUE) {
					colourHealth = TextFormatting.RED;
				}

				if (speed <= 12) {
					colourSpeed = TextFormatting.GREEN;
				} else if (speed <= 14) {
					colourSpeed = TextFormatting.YELLOW;
				} else if (speed <= Double.MAX_VALUE) {
					colourSpeed = TextFormatting.RED;
				}

				if (jump <= 2.0) {
					colourJump = TextFormatting.GREEN;
				} else if (jump <= 3.5) {
					colourJump = TextFormatting.YELLOW;
				} else if (jump <= Double.MAX_VALUE) {
					colourJump = TextFormatting.RED;
				}

				if (horseEntity instanceof EntityLlama) {
					TextFormatting colourSlots = TextFormatting.WHITE;
					double slots = ((EntityLlama) horseEntity).getStrength();

					slots = slots * 3;

					if (slots <= 9) {
						colourSlots = TextFormatting.GREEN;
					} else if (slots <= 12) {
						colourSlots = TextFormatting.YELLOW;
					} else if (slots <= Double.MAX_VALUE) {
						colourSlots = TextFormatting.RED;
					}

					player.sendStatusMessage(new TextComponentString(String.format("%s"+I18n.format("text.horsestats.thealth")+": %.0f %s"+I18n.format ("text.horsestats.tspeed")+": %.1f %s"+I18n.format("text.horsestats.tchest")+": %.1f", colourHealth, health, colourSpeed, speed, colourSlots, slots)), true);

				} else {
					player.sendStatusMessage(new TextComponentString(String.format("%s"+I18n.format("text.horsestats.thealth")+": %.0f %s"+I18n.format ("text.horsestats.tspeed")+": %.1f %s"+I18n.format("text.horsestats.tjump")+": %.1f", colourHealth, health, colourSpeed, speed, colourJump, jump)), true);
				}
			}
		}
	}
}
