/**
 * Main Class
 *
 * Made by Harsh Kalyani (@hkaly)
 */

//Import libraries
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.sound.sampled.Clip;

public class Nibbles extends JPanel implements ActionListener{
    //Declare global variables

    //Players with highscores
    static JLabel first, second, third;

    //All buttons
    JButton play, reset, highscores, instructions, settings, quit, menu, slow, normal, fast, mute, unmute;

    //Needed to switch screens
    CardLayout myCardLayout = new CardLayout();

    //Needed to read pressed keys
    static keyAdapter ka = new keyAdapter();

    public static void main(String[] args){
        Nibbles content = new Nibbles();

        //Create window
        JFrame window = new JFrame("nibbles.");
        window.setContentPane(content);

        //Set window dimensions as 900 x 600 pixels
        window.setSize(900,600);

        //Add key listeners to window
        window.addKeyListener(ka);

        //Allow key input
        window.setFocusable(true);

        //Center window
        window.setLocationRelativeTo(null);

        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public Nibbles(){
        //Allow player to see one screen at a time
        setLayout(myCardLayout);

        //Home screen
        home();

        //Instructions screen
        instructions();

        //Settings screen
        settings();

        //Highscores screen
        highscores();
    }

    //Home screen
    public void home(){
        //Add background music
        Board.music("assets/audio/music.wav");

        //Create screen panel
        JPanel mainMenu = new JPanel();
        mainMenu.setBackground(Color.decode("#f0f7da"));
        mainMenu.setLayout(null);

        //Create and style game title
        JLabel nibble = new JLabel("nibbles.");
        nibble.setFont(new Font("Poppins", Font.PLAIN, 175));
        nibble.setForeground(Color.decode("#234d20"));
        nibble.setBounds(150,0,645,175);

        //Create and style name input for player
        JLabel nameBelow = new JLabel("enter your name:");
        nameBelow.setFont(new Font("Poppins", Font.PLAIN, 30));
        nameBelow.setForeground(Color.decode("#77ab59"));
        nameBelow.setBounds(160,160,500,35);

        //Create and style empty textfield for player name
        Board.currentPlayer = new JTextField("");
        Board.currentPlayer.setFont(new Font("Poppins", Font.PLAIN, 30));
        Board.currentPlayer.setBackground(Color.decode("#c9df8a"));
        Board.currentPlayer.setForeground(Color.decode("#77ab59"));
        Board.currentPlayer.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        Board.currentPlayer.addActionListener(this);
        Board.currentPlayer.setActionCommand("playername");
        Board.currentPlayer.setBounds(390,166,150,30);

        //Create and style new panel for buttons; use BoxLayout manager for organization
        JPanel mnu = new JPanel();
        mnu.setBackground(Color.decode("#f0f7da"));
        mnu.setBounds(140,200,500,500);
        mnu.setLayout(new BoxLayout(mnu, BoxLayout.Y_AXIS));
        addButton("play", mnu);
        addButton("highscores", mnu);
        addButton("instructions", mnu);
        addButton("settings", mnu);
        addButton("quit", mnu);

        //Add everything to the screen
        mainMenu.add(nibble);
        mainMenu.add(nameBelow);
        mainMenu.add(Board.currentPlayer);
        mainMenu.add(mnu);

        //Add action command to panel
        add("mainMenu",mainMenu);
    }

    //This method is used to create the buttons on the Home screen
    public void addButton(String text, JPanel parent){
        JButton mnu = new JButton(text);
        mnu.setFont(new Font("Poppins", Font.PLAIN, 40));
        mnu.setForeground(Color.decode("#36802d"));        
        mnu.setBackground(Color.decode("#f0f7da"));
        mnu.setBorderPainted(false);

        //Set action command to button
        mnu.setActionCommand(text);
        mnu.addActionListener(this);

        //Left alignment
        mnu.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(mnu);
    }

    //Instructions screen
    public void instructions(){
        //Create screen panel
        JPanel instruct = new JPanel();
        instruct.setBackground(Color.decode("#f0f7da"));
        instruct.setLayout(null);

        //Create and style screen title
        JLabel instructions = new JLabel("instructions");
        instructions.setFont(new Font("Poppins", Font.PLAIN, 100));
        instructions.setForeground(Color.decode("#234d20"));
        instructions.setBounds(150,35,800,100);

        //Create and style goal subtitle
        JLabel goal = new JLabel("goal:");
        goal.setFont(new Font("Poppins", Font.PLAIN, 50));
        goal.setForeground(Color.decode("#36802d"));
        goal.setBounds(150,150,250,60);

        //Create and style goal
        JLabel goal1 = new JLabel("eat as many nibbles as possible");
        goal1.setFont(new Font("Poppins", Font.PLAIN, 30));
        goal1.setForeground(Color.decode("#36802d"));
        goal1.setBounds(160,225,700,40);

        //Create and style controls subtitle
        JLabel controls = new JLabel("controls:");
        controls.setFont(new Font("Poppins", Font.PLAIN, 50));
        controls.setForeground(Color.decode("#36802d"));
        controls.setBounds(150,325,450,60);

        //Create and style controls
        JLabel control1 = new JLabel("use wasd or the arrow keys to move");
        control1.setFont(new Font("Poppins", Font.PLAIN, 30));
        control1.setForeground(Color.decode("#36802d"));
        control1.setBounds(160,400,700,40);

        //Create and style menu button
        menu = new JButton("menu");
        menu.setFont(new Font("Poppins", Font.PLAIN, 20));
        menu.setForeground(Color.decode("#36802d"));
        menu.setBackground(Color.decode("#f0f7da"));
        menu.setActionCommand("menu");
        menu.setBorderPainted(false);
        menu.addActionListener(this);
        menu.setBounds(100,475,85,30);

        //Add all elements to screen
        instruct.add(instructions);
        instruct.add(goal);
        instruct.add(goal1);
        instruct.add(controls);
        instruct.add(control1);
        instruct.add(menu);

        //Add action command to panel
        add("instruct",instruct);
    }

    //Settings screen
    public void settings(){
        //Create screen panel
        JPanel options = new JPanel();
        options.setBackground(Color.decode("#f0f7da"));
        options.setLayout(null);

        //Create and style screen title
        JLabel setting = new JLabel("settings");
        setting.setFont(new Font("Poppins", Font.PLAIN, 100));
        setting.setForeground(Color.decode("#234d20"));
        setting.setBounds(150,35,700,145);

        //Create and style subtitle
        JLabel speed = new JLabel("speed:");
        speed.setFont(new Font("Poppins", Font.PLAIN, 50));
        speed.setForeground(Color.decode("#36802d"));
        speed.setBounds(150,175,250,60);

        //Create and style slow speed button
        slow = new JButton("slow");
        slow.setFont(new Font("Poppins", Font.PLAIN, 30));
        slow.setForeground(Color.decode("#36802d"));
        slow.setBackground(Color.decode("#c9df8a"));
        slow.setActionCommand("slow");
        slow.addActionListener(this);
        slow.setBounds(150,240,100,40);

        //Create and style default speed button
        normal = new JButton("default");
        normal.setFont(new Font("Poppins", Font.PLAIN, 30));
        normal.setForeground(Color.decode("#c9df8a"));
        normal.setBackground(Color.decode("#36802d"));
        normal.setActionCommand("normal");
        normal.addActionListener(this);
        normal.setBounds(275,240,125,40);

        //Create and style fast speed button
        fast = new JButton("fast");
        fast.setFont(new Font("Poppins", Font.PLAIN, 30));
        fast.setForeground(Color.decode("#36802d"));
        fast.setBackground(Color.decode("#c9df8a"));
        fast.setActionCommand("fast");
        fast.addActionListener(this);
        fast.setBounds(425,240,100,40);

        //Create and style audio subtitle
        JLabel audio = new JLabel("audio:");
        audio.setFont(new Font("Poppins", Font.PLAIN, 50));
        audio.setForeground(Color.decode("#36802d"));
        audio.setBounds(150,325,250,60);

        ///Create and style mute background music button
        mute = new JButton("music");
        mute.setFont(new Font("Poppins", Font.PLAIN, 30));
        mute.setForeground(Color.decode("#c9df8a"));
        mute.setBackground(Color.decode("#36802d"));
        mute.setActionCommand("mute");
        mute.addActionListener(this);
        mute.setBounds(150,390,125,40);

        //Create and style menu button
        menu = new JButton("menu");
        menu.setFont(new Font("Poppins", Font.PLAIN, 20));
        menu.setForeground(Color.decode("#36802d"));
        menu.setBackground(Color.decode("#f0f7da"));
        menu.setActionCommand("menu");
        menu.setBorderPainted(false);
        menu.addActionListener(this);
        menu.setBounds(100,475,85,30);

        //Add all elements to screen
        options.add(setting);
        options.add(speed);
        options.add(slow);
        options.add(normal);
        options.add(fast);
        options.add(audio);
        options.add(mute);
        options.add(menu);

        //Add action command to panel
        add("options",options);
    }

    //Highscores screen
    public void highscores(){
        //Create screen panel
        JPanel scores = new JPanel();
        scores.setBackground(Color.decode("#f0f7da"));
        scores.setLayout(null);

        //Create and style screen title
        JLabel highscore = new JLabel("highscores");
        highscore.setFont(new Font("Poppins", Font.PLAIN, 100));
        highscore.setForeground(Color.decode("#234d20"));
        highscore.setBounds(150,35,700,145);

        //First place{
        //Add a gold image
        JLabel gold = new JLabel(createImageIcon("assets/image/gold.png"));
        gold.setBounds(120,150,128,128);

        //Add and style 1st place name
        first = new JLabel(Board.hsplayer1 + " - " + Board.score1);
        first.setFont(new Font("Poppins", Font.PLAIN, 50));
        first.setForeground(new Color(255,215,0));//Gold
        first.setBounds(250,162,500,100);

        //Second place{
        //Add a silver image
        JLabel silver = new JLabel(createImageIcon("assets/image/silver.png"));
        silver.setBounds(120,250,128,128);

        //Add and style 2nd place name
        second = new JLabel(Board.hsplayer2 + " - " + Board.score2);
        second.setFont(new Font("Poppins", Font.PLAIN, 50));
        second.setForeground(new Color(192,192,192));//Silver
        second.setBounds(250,272,500,100);

        //Third place{
        //Add the bronze image
        JLabel bronze = new JLabel(createImageIcon("assets/image/bronze.png"));
        bronze.setBounds(120,350,128,128);

        //Add and style the 3rd place name
        third = new JLabel(Board.hsplayer3 + " - " + Board.score3);
        third.setFont(new Font("Poppins", Font.PLAIN, 50));
        third.setForeground(new Color(210,105,30));//Bronze
        third.setBounds(250,372,500,100);

        //Create and style menu button
        JButton menu = new JButton("menu");
        menu.setFont(new Font("Poppins", Font.PLAIN, 20));
        menu.setForeground(Color.decode("#36802d"));
        menu.setBackground(Color.decode("#f0f7da"));
        menu.setActionCommand("menu");
        menu.setBorderPainted(false);
        menu.addActionListener(this);
        menu.setBounds(100,475,85,30);

        //Add all elements to screen
        scores.add(highscore);
        scores.add(gold);
        scores.add(first);
        scores.add(silver);
        scores.add(second);
        scores.add(bronze);
        scores.add(third);
        scores.add(menu);

        //Add action command to panel
        add("scores",scores);
    }

    public void singleplayer(){
        //Create screen panel
        JPanel single = new JPanel();
        single.setBackground(Color.decode("#f0f7da"));
        single.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();

        //Create and style score label
        JLabel newscore = new JLabel("score: 0");
        newscore.setFont(new Font("Poppins", Font.PLAIN, 20));
        newscore.setForeground(Color.decode("#36802d"));
        gc.anchor = GridBagConstraints.LINE_END;
        gc.gridx = 0;
        gc.gridy = 1;
        single.add(newscore, gc);

        //Create and style menu button
        menu = new JButton("menu");
        menu.setFont(new Font("Poppins", Font.PLAIN, 20));
        menu.setForeground(Color.decode("#36802d"));
        menu.setBackground(Color.decode("#f0f7da"));
        menu.setActionCommand("menu");
        menu.addActionListener(this);
        menu.setBorderPainted(false);
        gc.anchor = GridBagConstraints.LINE_START;
        gc.gridy = 1;
        single.add(menu, gc);

        //Create and style reset button
        reset = new JButton("reset");
        reset.setFont(new Font("Poppins", Font.PLAIN, 20));
        reset.setForeground(Color.decode("#36802d"));
        reset.setBackground(Color.decode("#f0f7da"));
        reset.setActionCommand("reset");
        reset.addActionListener(this);
        reset.setBorderPainted(false);
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridy = 1;
        single.add(reset, gc);

        //Add Board class to panel
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridy = 0;
        gc.gridx = 0;
        single.add(new Board(newscore), gc);

        //Add action command to panel
        add("single",single);
    }

    public static class keyAdapter extends KeyAdapter{
        boolean right = true;
        boolean left = false;
        boolean up = false;
        boolean down = false;
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if (((key == KeyEvent.VK_LEFT) || (key == KeyEvent.VK_A)) && (!right)){
                Board.left();
                left = true;
                up = false;
                down = false;
            }

            if (((key == KeyEvent.VK_RIGHT) || (key == KeyEvent.VK_D)) && (!left)){
                Board.right();
                right = true;
                up = false;
                down = false;
            }

            if (((key == KeyEvent.VK_UP) || (key == KeyEvent.VK_W)) && (!down)){
                Board.up();
                up = true;
                right = false;
                left = false;
            }

            if (((key == KeyEvent.VK_DOWN) || (key == KeyEvent.VK_S)) && (!up)){
                Board.down();
                down = true;
                right = false;
                left = false;
            }
        }
    }

    public void actionPerformed(ActionEvent e){
        if (e.getActionCommand().equals("quit")){
            //Terminate program
            System.exit(0);
        }
        if (e.getActionCommand().equals("settings")){
            //Show settings screen
            myCardLayout.show(this, "options");
        }
        if (e.getActionCommand().equals("instructions")){
            //Show instructions screen
            myCardLayout.show(this, "instruct");
        }
        if (e.getActionCommand().equals("highscores")){
            //Show highscores screen
            highscores();
            myCardLayout.show(this, "scores");
        }
        if (e.getActionCommand().equals("play")){
            //Play Singleplayer game
            singleplayer();
            myCardLayout.show(this, "single");
        }
        if (e.getActionCommand().equals("menu")){
            //Return to home screen
            myCardLayout.show(this, "mainMenu");
        }
        if (e.getActionCommand().equals("reset")){
            //Reset game
            singleplayer();
            myCardLayout.show(this, "single");
        }
        if (e.getActionCommand().equals("mute")){
            //Pause background audio
            Board.clip.stop();

            //Swap foreground and background colours to show if selected
            mute.setForeground(Color.decode("#36802d"));
            mute.setBackground(Color.decode("#c9df8a"));
            mute.setActionCommand("unmute");
        }
        if (e.getActionCommand().equals("unmute")){
            //Play background audio
            Board.clip.start();

            //Loop clip
            Board.clip.loop(Clip.LOOP_CONTINUOUSLY);

            //Swap foreground and background colours to show if selected
            mute.setForeground(Color.decode("#c9df8a"));
            mute.setBackground(Color.decode("#36802d"));
            mute.setActionCommand("mute");
        }
        if (e.getActionCommand().equals("slow")){
            //Increase time delay
            Board.speed = 80;

            //Reduce score step since the game is easier to play
            Board.increase = 1;

            //Show player that they have selected this button
            slow.setForeground(Color.decode("#c9df8a"));
            slow.setBackground(Color.decode("#36802d"));

            //Unselect all other buttons
            fast.setForeground(Color.decode("#36802d"));
            fast.setBackground(Color.decode("#c9df8a"));
            normal.setForeground(Color.decode("#36802d"));
            normal.setBackground(Color.decode("#c9df8a"));
        }
        if (e.getActionCommand().equals("normal")){
            //Default time delay
            Board.speed = 50;

            //Change score step to default speed
            Board.increase = 3;

            //Show player that they have selected this button
            normal.setForeground(Color.decode("#c9df8a"));
            normal.setBackground(Color.decode("#36802d"));

            //Unselect all other buttons
            slow.setForeground(Color.decode("#36802d"));
            slow.setBackground(Color.decode("#c9df8a"));
            fast.setForeground(Color.decode("#36802d"));
            fast.setBackground(Color.decode("#c9df8a"));
        }
        if (e.getActionCommand().equals("fast")){
            //Decrease time delay
            Board.speed = 30;

            //Increase score step since the game is harder to play
            Board.increase = 5;

            //Show the player that they have selected this button
            fast.setForeground(Color.decode("#c9df8a"));
            fast.setBackground(Color.decode("#36802d"));

            //Unselect all other buttons
            slow.setForeground(Color.decode("#36802d"));
            slow.setBackground(Color.decode("#c9df8a"));
            normal.setForeground(Color.decode("#36802d"));
            normal.setBackground(Color.decode("#c9df8a"));
        }
    }

    protected static ImageIcon createImageIcon(String path){
        java.net.URL imgURL = Nibbles.class.getResource(path);
        if (imgURL != null){
            return new ImageIcon(imgURL);
        }
        else{
            System.err.println( "Couldn't find file: " + path);
            return null;
        }
    }
}
