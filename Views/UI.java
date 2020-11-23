package Views;

import Actions.AttackAction;
import Board.Board;
import Board.OccupantChangedEvent;
import Board.OccupantCountChangedEvent;
import Board.Territory;
import Controllers.RiskController;
import Dice.DiceState;
import Dice.RollEvent;
import Game.GameEvent;
import Game.GameState;
import Game.GameStateEvent;
import Player.*;
//import sun.java2d.SunGraphics2D;
import SetUp.GameSetup;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;

/**
 * @author Golan, Tan
 */
public class UI extends JFrame implements MouseListener, RiskView {

    private List<ViewListener> viewListeners;

    //GameState gameState = new GameState();
    private JPanel attackDice;
    private JPanel defendDice;
    private JPanel na;
    private JPanel sa;
    private JPanel eu;
    private JPanel af;
    private JPanel as;
    private JPanel au;
    private JPanel players;
    private JPanel winner;

    JLabel whoTurn;

    int numbOfTroopsToMove = 0;

    private boolean test1, test2, test3 = false;

    int countriesClicked;
    int numberOfPlayers;

    private JButton r1;
    private JButton r2;
    private JButton r3;
    private JButton d1;
    private JButton d2;
    private JButton roll;
    private JButton pass;
    private JButton conquest;
    private JButton placement;
    private JButton fortify;

    private JLabel phase;
    private JLabel unplacedTroops;

    Set<String> colors;
    HashMap<String, JComponent> countries = new HashMap<String, JComponent>();
    HashMap<String, String> playerAndColor =  new HashMap<String, String>();
    List<String> playernames;

    private ImageIcon attackDiceIcon1 = new ImageIcon(getClass().getResource("/Views/R1.png"));
    private ImageIcon attackDiceIcon2 = new ImageIcon(getClass().getResource("/Views/R2.png"));
    private ImageIcon attackDiceIcon3 = new ImageIcon(getClass().getResource("/Views/R3.png"));
    private ImageIcon attackDiceIcon4 = new ImageIcon(getClass().getResource("/Views/R4.png"));
    private ImageIcon attackDiceIcon5 = new ImageIcon(getClass().getResource("/Views/R5.png"));
    private ImageIcon attackDiceIcon6 = new ImageIcon(getClass().getResource("/Views/R6.png"));

    private ImageIcon defendDiceIcon1 = new ImageIcon(getClass().getResource("/Views/W1.png"));
    private ImageIcon defendDiceIcon2 = new ImageIcon(getClass().getResource("/Views/W2.png"));
    private ImageIcon defendDiceIcon3 = new ImageIcon(getClass().getResource("/Views/W3.png"));
    private ImageIcon defendDiceIcon4 = new ImageIcon(getClass().getResource("/Views/W4.png"));
    private ImageIcon defendDiceIcon5 = new ImageIcon(getClass().getResource("/Views/W5.png"));
    private ImageIcon defendDiceIcon6 = new ImageIcon(getClass().getResource("/Views/W6.png"));

    private String attackCommand = "attack";
    private String passCommand = "pass";
    private String conquestCommand = "conquest";
    private String placeCommand = "place";
    private String fortifyCommand = "fortify";

    List<JLabel> countriesSelected;

    GameState model;


    public UI() {
        colors = new HashSet<String>();
        colors.add("Y");
        colors.add("C");
        colors.add("G");
        colors.add("W");
        colors.add("GR");
        colors.add("O");
        viewListeners = new ArrayList<>();
        playernames = new ArrayList<>();
        countriesSelected = new ArrayList<>();

        setUp();

        // MVC
        GameSetup gst = new GameSetup(playernames);
        model = gst.getGameState();
        model.addRiskView(this);
        setCountriesColor();
        roll.setActionCommand(attackCommand);
        roll.addActionListener(new RiskController(model, this));
        pass.setActionCommand(passCommand);
        pass.addActionListener(new RiskController(model, this));
        conquest.setActionCommand(conquestCommand);
        conquest.addActionListener(new RiskController(model,this));
        placement.setActionCommand(placeCommand);
        placement.addActionListener(new RiskController(model,this));
        fortify.setActionCommand(fortifyCommand);
        fortify.addActionListener(new RiskController(model,this));

        phase = new JLabel("The current phase of the game is:" + model.getFlow().getTurnPart().toString());
        unplacedTroops = new JLabel("You have this many troops to place:" + model.getPlayers().getPlayer(model.getFlow().getCurrentTurnIndex()).getUnplacedTroops());
        players.add(phase);
        players.add(unplacedTroops);

    }

    public void setUp() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2, 5));

        attackDice = new JPanel();
        defendDice = new JPanel();
        na = new JPanel();
        sa = new JPanel();
        eu = new JPanel();
        af = new JPanel();
        as = new JPanel();
        au = new JPanel();
        players = new JPanel();
        winner = new JPanel();

        attackDice.setLayout(new GridBagLayout());

        na.setLayout(new GridBagLayout());
        sa.setLayout(new GridBagLayout());
        eu.setLayout(new GridBagLayout());
        af.setLayout(new GridBagLayout());
        as.setLayout(new GridBagLayout());
        au.setLayout(new GridBagLayout());



        winner.setLayout(new GridBagLayout());

        this.add(attackDice);
        this.add(na);
        this.add(eu);
        this.add(as);
        this.add(players);
        this.add(defendDice);
        this.add(sa);
        this.add(af);
        this.add(au);
        this.add(winner);

        while (!test1 || !test2) {

            try {
                test1 = true;
                numberOfPlayers = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of players:"));
            }

            // Get an integer
            catch (Exception e) {
                test1 = false;
                JOptionPane.showMessageDialog(this, "Please enter an integer.", "Inane error",
                        JOptionPane.ERROR_MESSAGE);
            }

            // Get an integer 2-6
            if (numberOfPlayers > 6 || numberOfPlayers < 2) {
                JOptionPane.showMessageDialog(this, "Please make sure there are only 2-6 players.", "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                test2 = false;
            } else {
                test2 = true;
            }
        }

        for (int i = 1; i<numberOfPlayers+1;i++)
        {
            test3 = false;
            String name = JOptionPane.showInputDialog("Enter player" + i + "'s name:");
            playernames.add(name);
            while(!test3)
            {
                String color = JOptionPane.showInputDialog("Enter player" + i + "'s colour(Y for Yellow, C for Cyan, G for Green, " +
                        "W for White, GR for Gray and, O for orange):");
                if(!(colors.contains(color)))
                {
                    JOptionPane.showMessageDialog(this, "Please enter a valid colour.", "Inane error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Player"+i+", your colour is " + getColorName(color) + ".", "Colour set",
                            JOptionPane.INFORMATION_MESSAGE);
                    playerAndColor.put(name,color);
                    colors.remove(color);
                    test3 = true;
                }

                int yesNoButton = JOptionPane.YES_NO_OPTION;
                int isAI = JOptionPane.showConfirmDialog(this, "Click Yes or No", "are you AI?", yesNoButton);
                if (isAI == JOptionPane.YES_OPTION) {
                    //
                }
                else if (isAI == JOptionPane.NO_OPTION) {
                    //
                }

            }
        }

        createWinner();
        createAttackDice();
        createDefendDice();
        createPlayers();
        createNA();
        na.setBackground(Color.getHSBColor(33, 56, 53));

        createSA();
        sa.setBackground(Color.getHSBColor(64, 72, 80));

        createEU();
        eu.setBackground(new Color(42, 115, 178));

        createAS();
        as.setBackground(new Color(102, 255, 102));

        createAF();
        af.setBackground(new Color(225, 139, 52));

        createAU();
        au.setBackground(new Color(255, 102, 225));

        for(String s : countries.keySet())
        {
            setNumberOfTroops(s,3);
        }


        this.setVisible(true);
    }

    private void buttonTransparent(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    public void createPlayers()
    {


        for(String p : playernames)
        {
            JLabel pc = new JLabel(p +":"+getColorName(playerAndColor.get(p)));
            players.add(pc);
        }

       // players.add(phase);

    }

    public void createWinner()
    {
        roll = new JButton("Attack");

        placement = new JButton("Place troops");

        fortify = new JButton("Fortify");
        //roll.addMouseListener(this);

        pass = new JButton("Pass");

        conquest = new JButton("Conquest");

        //pass.addMouseListener(this);

        whoTurn = new JLabel("It is " + playernames.get(0)+"'s turn.");
        players.add(whoTurn);

        addComp(winner, roll, 0, 0, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE);
        addComp(winner, pass, 0, 0, 1, 1, GridBagConstraints.PAGE_END, GridBagConstraints.NONE);
        addComp(winner,conquest,0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(winner,fortify,0, 0, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE);
        addComp(winner,placement,0, 0, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE);
    }

    public void createAttackDice()
    {
        r1 = new JButton();
        r2 = new JButton();
        r3 = new JButton();



        r1.setIcon(attackDiceIcon1);
        buttonTransparent(r1);

        r2.setIcon(attackDiceIcon2);
        buttonTransparent(r2);

        r3.setIcon(attackDiceIcon3);
        buttonTransparent(r3);


        addComp(attackDice, r1, 0, 0, 0, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE);
        addComp(attackDice, r2, 0, 0, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        addComp(attackDice, r3, 0, 0, 0, 0, GridBagConstraints.LINE_END, GridBagConstraints.NONE);

    }

    public void createDefendDice()
    {
        d1 = new JButton();
        d2 = new JButton();



        d1.setIcon(defendDiceIcon1);
        buttonTransparent(d1);
        d1.setBorder(BorderFactory.createLineBorder(Color.black,2));
        d1.setBorderPainted(true);

        d2.setIcon(defendDiceIcon2);
        buttonTransparent(d2);
        d2.setBorder(BorderFactory.createLineBorder(Color.black,2));
        d2.setBorderPainted(true);

        defendDice.add(d1);
        defendDice.add(d2);
    }



    public void createNA() {
        JLabel alaska = new JLabel("ALASKA");
        alaska.setSize(20, 20);
        alaska.setOpaque(true);
        alaska.addMouseListener(this);
        countries.put("ALASKA", alaska);
        addComp(na, alaska, 0, 0, 0, 0, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);

        JLabel northWestTerritory = new JLabel("NORTHWEST TERRITORY");
        northWestTerritory.setSize(25, 25);
        northWestTerritory.setOpaque(true);
        northWestTerritory.addMouseListener(this);
        countries.put("NORTHWESTTERRITORY", northWestTerritory);
        addComp(na, northWestTerritory, 5, 0, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.NONE);

        JLabel greenland = new JLabel("GREENLAND");
        greenland.setSize(30, 30);
        greenland.setOpaque(true);
        greenland.addMouseListener(this);
        countries.put("GREENLAND", greenland);
        addComp(na, greenland, 30, 0, 0, 1, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE);

        JLabel alberta = new JLabel("ALBERTA");
        alberta.setSize(25, 25);
        alberta.setOpaque(true);
        alberta.addMouseListener(this);
        countries.put("ALBERTA", alberta);
        addComp(na, alberta, 0, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        JLabel ontario = new JLabel("ONTARIO");
        ontario.setSize(30, 30);
        ontario.setOpaque(true);
        ontario.addMouseListener(this);
        countries.put("ONTARIO", ontario);
        addComp(na, ontario, 10, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        JLabel quebec = new JLabel("QUEBEC");
        quebec.setSize(20, 20);
        quebec.setOpaque(true);
        quebec.addMouseListener(this);
        addComp(na, quebec, 30, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        countries.put("QUEBEC", quebec);

        JLabel westernUS = new JLabel("WESTERN US");
        westernUS.setSize(20, 20);
        westernUS.setOpaque(true);
        westernUS.addMouseListener(this);
        addComp(na, westernUS, 10, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        countries.put("WESTERNUNITEDSTATES", westernUS);

        JLabel easternUS = new JLabel("EASTERN US");
        easternUS.setSize(20, 20);
        easternUS.setOpaque(true);
        easternUS.addMouseListener(this);
        addComp(na, easternUS, 20, 2, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);
        countries.put("EASTERNUNITEDSTATES", easternUS);

        JLabel centralAmerica = new JLabel("CENTRAL AMERICA");
        centralAmerica.setSize(20, 20);
        centralAmerica.setOpaque(true);
        centralAmerica.addMouseListener(this);
        addComp(na, centralAmerica, 15, 3, 0, 1, GridBagConstraints.SOUTH, GridBagConstraints.NONE);
        countries.put("CENTRALAMERICA", centralAmerica);
    }

    public void createSA() {
        JLabel venezuela = new JLabel("VENEZUELA");
        venezuela.setSize(20, 20);
        venezuela.setOpaque(true);
        venezuela.addMouseListener(this);
        countries.put("VENEZUELA", venezuela);
        addComp(sa, venezuela, 0, 0, 0, 0, GridBagConstraints.NORTH, GridBagConstraints.NONE);

        JLabel peru = new JLabel("PERU");
        peru.setSize(20, 20);
        peru.setOpaque(true);
        peru.addMouseListener(this);
        countries.put("PERU", peru);
        addComp(sa, peru, 0, 0, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        JLabel brazil = new JLabel("BRAZIL");
        brazil.setSize(20, 20);
        brazil.setOpaque(true);
        brazil.addMouseListener(this);
        countries.put("BRAZIL", brazil);
        addComp(sa, brazil, 20, 0, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        JLabel argentina = new JLabel("ARGENTINA");
        argentina.setSize(20, 20);
        argentina.setOpaque(true);
        argentina.addMouseListener(this);
        countries.put("ARGENTINA", argentina);
        addComp(sa, argentina, 0, 0, 0, 0, GridBagConstraints.SOUTH, GridBagConstraints.NONE);
    }

    public void createEU() {
        JLabel iceland = new JLabel("ICELAND");
        iceland.setSize(20, 20);
        iceland.setOpaque(true);
        iceland.addMouseListener(this);
        countries.put("ICELAND", iceland);
        addComp(eu, iceland, 0, 0, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);

        JLabel greatBritain = new JLabel("GREAT BRITAIN");
        greatBritain.setSize(20, 20);
        greatBritain.setOpaque(true);
        greatBritain.addMouseListener(this);
        countries.put("GREATBRITAIN", greatBritain);
        addComp(eu, greatBritain, 0, 0, 1, 0, GridBagConstraints.LINE_START, GridBagConstraints.NONE);

        JLabel westernEurope = new JLabel("WESTERN EUROPE");
        westernEurope.setSize(20, 20);
        westernEurope.setOpaque(true);
        westernEurope.addMouseListener(this);
        countries.put("WESTERNEUROPE", westernEurope);
        addComp(eu, westernEurope, 0, 0, 1, 0, GridBagConstraints.SOUTHWEST, GridBagConstraints.NONE);

        JLabel scandinavia = new JLabel("SCANDINAVIA");
        scandinavia.setSize(20, 20);
        scandinavia.setOpaque(true);
        scandinavia.addMouseListener(this);
        countries.put("SCANDINAVIA", scandinavia);
        addComp(eu, scandinavia, 15, 0, 1, 0, GridBagConstraints.NORTH, GridBagConstraints.NONE);

        JLabel northernEurope = new JLabel("NORTHERN EUROPE");
        northernEurope.setSize(20, 20);
        northernEurope.setOpaque(true);
        northernEurope.addMouseListener(this);
        countries.put("NORTHERNEUROPE", northernEurope);
        addComp(eu, northernEurope, 15, 0, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        JLabel southernEurope = new JLabel("SOUTHERN EUROPE");
        southernEurope.setSize(20, 20);
        southernEurope.setOpaque(true);
        southernEurope.addMouseListener(this);
        countries.put("SOUTHERNEUROPE", southernEurope);
        addComp(eu, southernEurope, 15, 0, 1, 0, GridBagConstraints.SOUTH, GridBagConstraints.NONE);

        JLabel ukraine = new JLabel("UKRAINE");
        ukraine.setSize(20, 20);
        ukraine.setOpaque(true);
        ukraine.addMouseListener(this);
        countries.put("UKRAINE", ukraine);
        addComp(eu, ukraine, 30, 0, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.NONE);
    }

    public void createAS() {
        JLabel ural = new JLabel("URAL");
        ural.setSize(20, 20);
        ural.setOpaque(true);
        ural.addMouseListener(this);
        countries.put("URAL", ural);
        addComp(as, ural, 0, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE);

        JLabel afghanistan = new JLabel("AFGHANISTAN");
        afghanistan.setSize(20, 20);
        afghanistan.setOpaque(true);
        afghanistan.addMouseListener(this);
        countries.put("AFGHANISTAN", afghanistan);
        addComp(as, afghanistan, 0, 2, 0, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE);

        JLabel middleEast = new JLabel("MIDDLE EAST");
        middleEast.setSize(20, 20);
        middleEast.setOpaque(true);
        middleEast.addMouseListener(this);
        countries.put("MIDDLEEAST", middleEast);
        addComp(as, middleEast, 0, 3, 1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE);

        JLabel china = new JLabel("CHINA");
        china.setSize(20, 20);
        china.setOpaque(true);
        china.addMouseListener(this);
        countries.put("CHINA", china);
        addComp(as, china, 1, 3, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);

        JLabel siberia = new JLabel("SIBERIA");
        siberia.setSize(20, 20);
        siberia.setOpaque(true);
        siberia.addMouseListener(this);
        countries.put("SIBERIA", siberia);
        addComp(as, siberia, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        JLabel mongolia = new JLabel("MONGOLIA");
        mongolia.setSize(20, 20);
        mongolia.setOpaque(true);
        mongolia.addMouseListener(this);
        countries.put("MONGOLIA", mongolia);
        addComp(as, mongolia, 1, 2, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);

        JLabel yakutsk = new JLabel("YAKUTSK");
        yakutsk.setSize(20, 20);
        yakutsk.setOpaque(true);
        yakutsk.addMouseListener(this);
        countries.put("YAKUTSK", yakutsk);
        addComp(as, yakutsk, 1, 0, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);

        JLabel irkutsk = new JLabel("IRKUTSK");
        irkutsk.setSize(20, 20);
        irkutsk.setOpaque(true);
        irkutsk.addMouseListener(this);
        countries.put("IRKUTSK", irkutsk);
        addComp(as, irkutsk, 1, 1, 1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE);


        JLabel kamchatka = new JLabel("KAMCHATKA");
        kamchatka.setSize(20, 20);
        kamchatka.setOpaque(true);
        kamchatka.addMouseListener(this);
        countries.put("KAMCHATKA", kamchatka);
        addComp(as, kamchatka, 1, 0, 1, 1, GridBagConstraints.FIRST_LINE_END, GridBagConstraints.NONE);

        JLabel japan = new JLabel("JAPAN");
        japan.setSize(20, 20);
        japan.setOpaque(true);
        japan.addMouseListener(this);
        countries.put("JAPAN", japan);
        addComp(as, japan, 1, 1, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE);


        JLabel india = new JLabel("INDIA");
        india.setSize(20, 20);
        india.setOpaque(true);
        india.addMouseListener(this);
        countries.put("INDIA", india);
        addComp(as, india, 0, 3, 0, 1, GridBagConstraints.SOUTH, GridBagConstraints.NONE);

        JLabel siam = new JLabel("SIAM");
        siam.setSize(20, 20);
        siam.setOpaque(true);
        siam.addMouseListener(this);
        countries.put("SIAM", siam);
        addComp(as, siam, 1, 3, 0, 1, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE);

    }

    public void createAF() {
        JLabel northAfrica = new JLabel("NORTH AFRICA");
        northAfrica.setSize(20, 20);
        northAfrica.setOpaque(true);
        northAfrica.addMouseListener(this);
        countries.put("NORTHAFRICA", northAfrica);
        addComp(af, northAfrica, 0, 0, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.NONE);

        JLabel egypt = new JLabel("EGYPT");
        egypt.setSize(20, 20);
        egypt.setOpaque(true);
        egypt.addMouseListener(this);
        countries.put("EGYPT", egypt);
        addComp(af, egypt, 0, 0, 1, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE);

        JLabel congo = new JLabel("CONGO");
        congo.setSize(20, 20);
        congo.setOpaque(true);
        congo.addMouseListener(this);
        countries.put("CONGO", congo);
        addComp(af, congo, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        JLabel eastAfrica = new JLabel("EAST AFRICA");
        eastAfrica.setSize(20, 20);
        eastAfrica.setOpaque(true);
        eastAfrica.addMouseListener(this);
        countries.put("EASTAFRICA", eastAfrica);
        addComp(af, eastAfrica, 0, 0, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE);

        JLabel madagascar = new JLabel("MADAGASCAR");
        madagascar.setSize(20, 20);
        madagascar.setOpaque(true);
        madagascar.addMouseListener(this);
        countries.put("MADAGASCAR", madagascar);
        addComp(af, madagascar, 0, 0, 1, 1, GridBagConstraints.LAST_LINE_END, GridBagConstraints.NONE);

        JLabel southAfrica = new JLabel("SOUTH AFRICA");
        southAfrica.setSize(20, 20);
        southAfrica.setOpaque(true);
        southAfrica.addMouseListener(this);
        countries.put("SOUTHAFRICA", southAfrica);
        addComp(af, southAfrica, 0, 0, 1, 1, GridBagConstraints.PAGE_END, GridBagConstraints.NONE);
    }

    public void createAU() {
        JLabel indonesia = new JLabel("INDONESIA");
        indonesia.setSize(20, 20);
        indonesia.setOpaque(true);
        indonesia.addMouseListener(this);
        countries.put("INDONESIA", indonesia);
        addComp(au, indonesia, 0, 0, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE);

        JLabel newGuinea = new JLabel("NEW GUINEA");
        newGuinea.setSize(20, 20);
        newGuinea.setOpaque(true);
        newGuinea.addMouseListener(this);
        countries.put("NEWGUINEA", newGuinea);
        addComp(au, newGuinea, 0, 0, 1, 1, GridBagConstraints.LINE_END, GridBagConstraints.NONE);

        JLabel westernAustralia = new JLabel("WESTERN AUSTRALIA");
        westernAustralia.setSize(20, 20);
        westernAustralia.setOpaque(true);
        westernAustralia.addMouseListener(this);
        countries.put("WESTERNAUSTRALIA", westernAustralia);
        addComp(au, westernAustralia, 0, 0, 1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE);

        JLabel easternAustralia = new JLabel("EASTERN AUSTRALIA");
        easternAustralia.setSize(20, 20);
        easternAustralia.setOpaque(true);
        easternAustralia.addMouseListener(this);
        countries.put("EASTERNAUSTRALIA", easternAustralia);
        addComp(au, easternAustralia, 0, 0, 1, 1, GridBagConstraints.LAST_LINE_END, GridBagConstraints.NONE);
    }


    private void addBorder(JComponent comp, Color color, int thickness) {
        Border border = new LineBorder(color, thickness);

        comp.setBorder(border);
    }


    public void setNumberOfTroops(String name, int troops)
    {
        JLabel l = (JLabel) countries.get(name);
        String s = l.getText();
        s = "<html>"+s+"<br>Troops:"+troops+"</html>";
        l.setText(s);

    }

    public String getStringfromLabel(JLabel l)
    {
        Set<String> c = countries.keySet();

        for(String s : c)
        {
            if(countries.get(s).equals(l))
            {
                return s;
            }
        }
        return null;
    }

    public int getNumberOfTroops(String name)
    {
        JLabel l = (JLabel) countries.get(name);
        return Integer.parseInt(l.getText());
    }

    public void setColor(String name,String color)
    {
        JLabel l = (JLabel) countries.get(name);

        switch (color)
        {
            case "Y" :
                l.setBackground(Color.YELLOW);
                break;

            case "C" :
                l.setBackground(Color.CYAN);
                break;

            case "G" :
                l.setBackground(Color.GREEN);
                break;

            case "W" :
                l.setBackground(Color.WHITE);
                break;

            case "GR" :
                l.setBackground(Color.GRAY);
                break;

            case "O" :
                l.setBackground(Color.ORANGE);
                break;


        }
    }

    public String getColorName(String color)
    {
        String colour = "";
        switch (color)
        {
            case "Y" :
                colour = "Yellow";
                break;

            case "C" :
                colour = "Cyan";
                break;

            case "G" :
                colour = "Green";
                break;

            case "W" :
                colour = "White";
                break;

            case "GR" :
                colour = "Gray";
                break;

            case "O" :
                colour = "Orange";
                break;
        }
        return colour;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();
        JLabel l;
        JButton b;


        if(source instanceof JLabel)
        {
            l = (JLabel) source;

            if ((l.getBorder() == null))
            {
                if(countriesClicked>1){
                    JOptionPane.showMessageDialog(this, "You can only have 2 countries selected at a time.", "Inane error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    l.setBorder(BorderFactory.createLineBorder(Color.red, 2));
                    countriesClicked++;
                    countriesSelected.add(l);

                }
            }
            else if(!(l.getBorder()==null))
            {
                l.setBorder(null);
                countriesClicked--;
                countriesSelected.remove(l);
            }

        }

        if(source instanceof JButton)
        {
            b = (JButton) source;

            if(b.getText().equals("Pass"))
            {
                for(ViewListener v: viewListeners)
                {
                    v.passButtonPressed();
                }
            }
            else if(b.getText().equals("Attack"))
            {
                Set<String> places = new HashSet<String>();
                if(countriesClicked == 2)
                {
                    for(JLabel c : countriesSelected)
                    {
                        places.add(getStringfromLabel(c));
                    }
                    for(ViewListener v: viewListeners)
                    {
                        v.attackButtonPressed(places);
                    }

                }

            }

        }

    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }



    @Override
    public void occupantChanged(OccupantChangedEvent oce) {
      Territory t =  (Territory) oce.getSource();
      String tName = t.getName();
      String cur = oce.getCurrentOccupant();

      setColor(tName,playerAndColor.get(cur));

      boolean test4 = false;
      boolean test5 = false;

        int numbOfTroops = 0;
        while (!test4 || !test5) {

            try {
                test4 = true;
                numbOfTroops = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of troops you want to move:"));
            }

            // Get an integer
            catch (Exception e) {
                test4 = false;
                JOptionPane.showMessageDialog(this, "Please enter an integer.", "Inane error",
                        JOptionPane.ERROR_MESSAGE);
            }

            // Get an integer 2-6
            if (numbOfTroops > t.getNumberOfOccupants()-1 || numbOfTroops < 1) {
                JOptionPane.showMessageDialog(this, "Please make sure you are moving an appropriate amount of troops.", "Inane error",
                        JOptionPane.ERROR_MESSAGE);
                test5 = false;
            } else {
                test5 = true;
                JOptionPane.showInputDialog("Please click the move button to move the troops!");
            }

        }
      setNumberOfTroops(tName,t.getNumberOfOccupants());

        for(ViewListener v: viewListeners)
        {
            v.numberOfTroopsChanged(numbOfTroops,tName);
        }
    }

    @Override
    public void occupantCountChanged(OccupantCountChangedEvent oce) {

    }

    @Override
    public void diceRolled(RollEvent e) {

    }

    @Override
    public void gamePartChanged(GameEvent e) {

    }

    @Override
    public void troopPlaced(TroopPlacedEvent e) {

    }

    @Override
    public void actionRequested(ActionRequestEvent e) {

    }

    @Override
    public void feedbackDispatched(FeedbackDispatchEvent e) {
       // System.out.println(e.getFeedback());
    }

    @Override
    public void actionDispatched(ActionDispatchedEvent e) {

    }

    @Override
    public void playerRemoved(String player) {
        //Remove player
        //propt with a message saying user is gone
    }

    private void addComp(JPanel thePanel, JComponent comp, int xPos, int yPos, int compWidth, int compHeight, int place,
                         int stretch) {
        GridBagConstraints gridConstraints = new GridBagConstraints();

        gridConstraints.gridx = xPos;

        gridConstraints.gridy = yPos;

        gridConstraints.gridwidth = compWidth;

        gridConstraints.gridheight = compHeight;

        gridConstraints.weightx = 100;

        gridConstraints.weighty = 100;

        gridConstraints.insets = new Insets(5, 5, 5, 5);

        gridConstraints.anchor = place;

        gridConstraints.fill = stretch;

        thePanel.add(comp, gridConstraints);

    }

    public void setCountriesColor()
    {
            for(Territory t : model.getBoard().getTerritories().values()) {
                String p =  t.getOccupantName();
                setColor(t.getName(),playerAndColor.get(p));
            }
    }

    public static void main(String[] args) {
         new UI();

    }



    public List<JLabel> getCountriesSelected() {
        return countriesSelected;
    }


    /**
     *
     * @return
     * @author Tan
     */
    public String getOriginTarget() {

        String text = this.getCountriesSelected().get(0).getText();
        //String text = getStringfromLabel(this.getCountriesSelected().get(0));
        String name = "";
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '>') {
                for (int j = i + 1; j < text.length(); j++) {
                    if (text.charAt(j) != '<') {
                        name += "" + text.charAt(j);
                    } else if (text.charAt(j) == '<') {
                        return name;
                    }
                }

            }
        }
        return text;
    }



    /**
     *
     * @return
     * @author Tan
     */
    public String getDestinationTarget() {

        String text = this.getCountriesSelected().get(1).getText();

        String name = "";
        for (int i=0; i<text.length(); i++) {
            if (text.charAt(i) == '>') {
                for (int j=i+1; j<text.length(); j++) {
                    if (text.charAt(j) != '<') {
                        name += "" + text.charAt(j);
                    }
                    else if (text.charAt(j) == '<') {
                        return name;
                    }
                }

            }

        }
        return text;
    }

    /**
     *
     * @param diceNumber
     * @param roll
     * @author Tan
     */
    public void setAttackDice(int diceNumber, int roll)
    {
        switch(diceNumber)
        {
            case 1:
                changeAttackIcon(r1, roll);
                break;
            case 2:
                changeAttackIcon(r2, roll);
                break;
            case 3:
                changeAttackIcon(r3, roll);
                break;
        }
    }

    /**
     *
     * @param dice
     * @param number
     * @author Tan
     */
    public void changeAttackIcon(JButton dice, int number) {
        switch(number)
        {
            case 1:
                dice.setIcon(attackDiceIcon1);
                break;
            case 2:
                dice.setIcon(attackDiceIcon2);
                break;
            case 3:
                dice.setIcon(attackDiceIcon3);
                break;
            case 4:
                dice.setIcon(attackDiceIcon4);
                break;
            case 5:
                dice.setIcon(attackDiceIcon5);
                break;
            case 6:
                dice.setIcon(attackDiceIcon6);
                break;
        }
    }

    /**
     *
     * @param diceNumber
     * @param roll
     * @author Tan
     */
    public void setDefendDice(int diceNumber, int roll)
    {

        switch(diceNumber)
        {
            case 1:
                changeDefendIcon(d1, roll);
                break;
            case 2:
                changeDefendIcon(d2, roll);
                break;
        }
    }

    /**
     *
     * @param dice
     * @param number
     * @author Tan
     */
    public void changeDefendIcon(JButton dice, int number) {
        switch(number)
        {
            case 1:
                dice.setIcon(defendDiceIcon1);
                break;
            case 2:
                dice.setIcon(defendDiceIcon2);
                break;
            case 3:
                dice.setIcon(defendDiceIcon3);
                break;
            case 4:
                dice.setIcon(defendDiceIcon4);
                break;
            case 5:
                dice.setIcon(defendDiceIcon5);
                break;
            case 6:
                dice.setIcon(defendDiceIcon6);
                break;
        }
    }

    public void changeNumberOfTroops(String country, int troops, boolean origin) {
        JLabel l = (JLabel) countries.get(country);
        if(origin) {
            l.setText("<html>" + this.getOriginTarget() + "<br>Troops:" + troops + "</html>");
        }
        if(!origin)
        {
            l.setText("<html>" + this.getDestinationTarget() + "<br>Troops:" + troops + "</html>");
        }
    }

    /**
     *
     * @param e
     * @author Tan
     */
    @Override
    public void handleAttack(GameStateEvent e) {
        GameState gs = (GameState) e.getSource();
        phase.setText("The current phase of the game is:" + gs.getFlow().getTurnPart().toString());
        String origin = gs.getBoard().getOriginTarget().getName();
        String destination = gs.getBoard().getDestinationTarget().getName();
        int troopInOrigin = gs.getBoard().getOriginTarget().getNumberOfOccupants();
        int troopInDestination = gs.getBoard().getDestinationTarget().getNumberOfOccupants();
        String attacker = gs.getAttackDice().getRoller();
        String defender = gs.getDefendDice().getRoller();

        // display the dice
        ArrayList<Integer> attackResult = gs.getAttackDice().getResult();
        ArrayList<Integer> defendResult = gs.getDefendDice().getResult();
        for (int i=1; i<=attackResult.size(); i++) {
            setAttackDice(i, attackResult.get(i-1));
        }
        for (int j=1; j<=defendResult.size(); j++) {
            setDefendDice(j, defendResult.get(j-1));
        }

        // update the number of troops
        changeNumberOfTroops(origin, troopInOrigin, true);
        changeNumberOfTroops(destination, troopInDestination, false);
        if(troopInDestination == 0)
        {
            Territory t = model.getBoard().getTerritories().get(origin);
            setColor(destination,playerAndColor.get(gs.getAttackDice().getRoller()));

            boolean test4 = false;
            boolean test5 = false;


            while (!test4 || !test5) {

                try {
                    test4 = true;
                    numbOfTroopsToMove = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of troops you want to move:"));
                }

                // Get an integer
                catch (Exception ex) {
                    test4 = false;
                    JOptionPane.showMessageDialog(this, "Please enter an integer.", "Inane error",
                            JOptionPane.ERROR_MESSAGE);
                }

                // Get an integer 2-6
                if (numbOfTroopsToMove > t.getNumberOfOccupants() - 1 || numbOfTroopsToMove < 1) {
                    JOptionPane.showMessageDialog(this, "Please make sure you are moving an appropriate amount of troops.", "Inane error",
                            JOptionPane.ERROR_MESSAGE);
                    test5 = false;
                } else {
                    test5 = true;
                    JOptionPane.showMessageDialog(this,"Please click the Conquest Button!");
                }
            }
        }

        // check if a player is eliminated

        // check if there is a winner
        if (gs.getWinner()!=null) {
            JOptionPane.showMessageDialog(null, "the winner is " + gs.getWinner());
        }


    }

    public int getNumbOfTroopsToMove() {
        return numbOfTroopsToMove;
    }


    /**
     *
     * @param e
     * @author Tan
     */

    @Override
    public void handlePass(GameStateEvent e) {
        GameState gs = (GameState) e.getSource();
        String name = gs.getPlayers().getPlayer(gs.getFlow().getCurrentTurnIndex()).getName();
        JOptionPane.showMessageDialog(null, "your turn " + name);
        whoTurn.setText("It is " + name +"'s turn.");
        unplacedTroops.setText("You have this many troops to place:" + gs.getPlayers().getPlayer(gs.getFlow().getCurrentTurnIndex()).getUnplacedTroops());
        phase.setText("The current phase of the game is:" + gs.getFlow().getTurnPart().toString());
    }

    /**
     *
     * @param e
     * @author Tan
     */
    @Override
    public void handleConquest(GameStateEvent e) {
        GameState gs = (GameState) e.getSource();
        changeNumberOfTroops(gs.getBoard().getDestinationTarget().getName(), gs.getBoard().getDestinationTarget().getNumberOfOccupants(),false);
        changeNumberOfTroops(gs.getBoard().getOriginTarget().getName(), gs.getBoard().getOriginTarget().getNumberOfOccupants(),true);
        phase.setText("The current phase of the game is:" + gs.getFlow().getTurnPart().toString());
    }

    @Override
    public void handlePlacement(GameStateEvent e) {
        GameState gs = (GameState) e.getSource();
        String country = e.getPlacementTarget();
        changeNumberOfTroops(country,gs.getBoard().getTerritory(country).getNumberOfOccupants(),true);
        unplacedTroops.setText("You have this many troops to place:" + gs.getPlayers().getPlayer(gs.getFlow().getCurrentTurnIndex()).getUnplacedTroops());
    }

    @Override
    public void handleFortify(GameStateEvent e) {
        GameState gs = (GameState) e.getSource();
        phase.setText("The current phase of the game is:" + gs.getFlow().getTurnPart().toString());
        String origin = gs.getBoard().getOriginTarget().getName();
        String destination = gs.getBoard().getDestinationTarget().getName();
        int troopInOrigin = gs.getBoard().getOriginTarget().getNumberOfOccupants();
        int troopInDestination = gs.getBoard().getDestinationTarget().getNumberOfOccupants();
        changeNumberOfTroops(origin, troopInOrigin, true);
        changeNumberOfTroops(destination, troopInDestination, false);

    }
}
