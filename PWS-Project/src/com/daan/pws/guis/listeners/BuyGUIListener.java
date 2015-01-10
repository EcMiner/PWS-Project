package com.daan.pws.guis.listeners;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.guis.BuyGUI;
import com.daan.pws.guis.BuyGUIPopup;

public class BuyGUIListener implements BindingExecutionDelegate {

	public BuyGUIListener() {
		SpoutManager.getKeyBindingManager().registerBinding("Button 0", Keyboard.KEY_0, "This will activate button 0 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Button 1", Keyboard.KEY_1, "This will activate button 1 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Button 2", Keyboard.KEY_2, "This will activate button 2 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Button 3", Keyboard.KEY_3, "This will activate button 3 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Button 4", Keyboard.KEY_4, "This will activate button 4 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Button 5", Keyboard.KEY_5, "This will activate button 5 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Button 6", Keyboard.KEY_6, "This will activate button 6 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Button 7", Keyboard.KEY_7, "This will activate button 7 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Button 8", Keyboard.KEY_8, "This will activate button 8 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Button 9", Keyboard.KEY_9, "This will activate button 0 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Confirm Button", Keyboard.KEY_RETURN, "This will confirm the selected button when you have the BuyGUI open", this, Main.getInstance());

		SpoutManager.getKeyBindingManager().registerBinding("Numpad Button 0", Keyboard.KEY_NUMPAD0, "This will activate button 0 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Numpad Button 1", Keyboard.KEY_NUMPAD1, "This will activate button 1 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Numpad Button 2", Keyboard.KEY_NUMPAD2, "This will activate button 2 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Numpad Button 3", Keyboard.KEY_NUMPAD3, "This will activate button 3 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Numpad Button 4", Keyboard.KEY_NUMPAD4, "This will activate button 4 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Numpad Button 5", Keyboard.KEY_NUMPAD5, "This will activate button 5 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Numpad Button 6", Keyboard.KEY_NUMPAD6, "This will activate button 6 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Numpad Button 7", Keyboard.KEY_NUMPAD7, "This will activate button 7 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Numpad Button 8", Keyboard.KEY_NUMPAD8, "This will activate button 8 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Numpad Button 9", Keyboard.KEY_NUMPAD9, "This will activate button 0 when you have the BuyGUI open", this, Main.getInstance());
		SpoutManager.getKeyBindingManager().registerBinding("Numpad Confirm Button", Keyboard.KEY_NUMPADENTER, "This will confirm the selected button when you have the BuyGUI open", this, Main.getInstance());
	}

	@Override
	public void keyPressed(KeyBindingEvent evt) {
		SpoutPlayer player = evt.getPlayer();
		if (player.getMainScreen().getActivePopup() != null && player.getMainScreen().getActivePopup() instanceof BuyGUIPopup) {
			BuyGUI buyGui = ((BuyGUIPopup) player.getMainScreen().getActivePopup()).getBuyGui();
			Keyboard key = evt.getBinding().getDefaultKey();
			switch (key) {
			case KEY_NUMPAD0:
			case KEY_0:
				buyGui.close();
				break;
			case KEY_NUMPAD1:
			case KEY_1:
				if (buyGui.hasButton(1) && buyGui.getButton(1).getButton().isEnabled()) {
					BuyGUI.fakeButtonClick(buyGui.getButton(1).getButton(), player);
				}
				break;
			case KEY_NUMPAD2:
			case KEY_2:
				if (buyGui.hasButton(2) && buyGui.getButton(2).getButton().isEnabled()) {
					BuyGUI.fakeButtonClick(buyGui.getButton(2).getButton(), player);
				}
				break;
			case KEY_NUMPAD3:
			case KEY_3:
				if (buyGui.hasButton(3) && buyGui.getButton(3).getButton().isEnabled()) {
					BuyGUI.fakeButtonClick(buyGui.getButton(3).getButton(), player);
				}
				break;
			case KEY_NUMPAD4:
			case KEY_4:
				if (buyGui.hasButton(4) && buyGui.getButton(4).getButton().isEnabled()) {
					BuyGUI.fakeButtonClick(buyGui.getButton(4).getButton(), player);
				}
				break;
			case KEY_NUMPAD5:
			case KEY_5:
				if (buyGui.hasButton(5) && buyGui.getButton(5).getButton().isEnabled()) {
					BuyGUI.fakeButtonClick(buyGui.getButton(5).getButton(), player);
				}
				break;
			case KEY_NUMPAD6:
			case KEY_6:
				if (buyGui.hasButton(6) && buyGui.getButton(6).getButton().isEnabled()) {
					BuyGUI.fakeButtonClick(buyGui.getButton(6).getButton(), player);
				}
				break;
			case KEY_NUMPAD7:
			case KEY_7:
				if (buyGui.hasButton(7) && buyGui.getButton(7).getButton().isEnabled()) {
					BuyGUI.fakeButtonClick(buyGui.getButton(7).getButton(), player);
				}
				break;
			case KEY_NUMPAD8:
			case KEY_8:
				if (buyGui.hasButton(8) && buyGui.getButton(8).getButton().isEnabled()) {
					BuyGUI.fakeButtonClick(buyGui.getButton(8).getButton(), player);
				}
				break;
			case KEY_9:
			case KEY_NUMPAD9:
				if (buyGui.hasButton(9) && buyGui.getButton(9).getButton().isEnabled()) {
					BuyGUI.fakeButtonClick(buyGui.getButton(9).getButton(), player);
				}
				break;
			case KEY_NUMPADENTER:
			case KEY_RETURN:
				if (buyGui.hasButtonSelected() && buyGui.getButtonSelected().getButton().isEnabled()) {
					BuyGUI.fakeButtonClick(buyGui.getButtonSelected().getButton(), player);
				}
				break;
			default:
				break;
			}

		}
	}

	@Override
	public void keyReleased(KeyBindingEvent evt) {

	}

}
