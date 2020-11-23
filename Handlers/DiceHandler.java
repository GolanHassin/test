package Handlers;


import Dice.DiceListener;
import Dice.RollEvent;

import Actions.*;
import Core.StateHandler;
import Dice.DiceListener;
import Dice.RollEvent;
import Game.GameState;
import Player.PlayerState;

import javax.swing.*;

/**
 * @author David
 * Used for when dice is called and internal events need to be dealt with
 */
public class DiceHandler extends StateHandler<GameState> implements DiceListener {
    /**
     * called when a dice is rolled
     * @param e the dice event
     */
    public void diceRolled(RollEvent e) {
    }

}