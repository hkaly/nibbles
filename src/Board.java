/**
 * Single-Player Game Screen
 *
 * Made by Harsh Kalyani (@hkaly)
 */

//Import libraries
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Board extends JPanel implements ActionListener{
    //Game board dimensions
    int boardSize = 500;

    //Game board area
    int boardArea = 2499;

    //Snake bodypart size
    int imageSize = 10;

    //Random nibble coordinate
    int randomNibble = 49;

    //The lower the "speed" the faster the snake is
    static int speed = 50;

    int x[] = new int[boardArea];
    int y[] = new int[boardArea];

    //Snake's body
    int dots;

    //Stores the players' name if they achieve a new highscore
    static JLabel first, second, third;

    //Top 3 scores
    static int score1 = 0, score2 = 0, score3 = 0;

    //Top 3 player's names
    static String hsplayer1 = "---NONE---";
    static String hsplayer2 = "---NONE---";
    static String hsplayer3 = "---NONE---";

    //Needed to increase score
    static JLabel newScore;
    static int points = 0;
    static int increase = 3;
    static JTextField currentPlayer;

    //Flag that helps identify what to display to the screen
    int a = 0;

    //Nibble coordinate
    int xnibble, ynibble;

    //Directions of KeyListeners
    static boolean right = true;
    static boolean left = false;
    static boolean up = false;
    static boolean down = false;

    //Allows the player to continue playing
    boolean inGame = true;

    //Displays the rule at the beginning of the game
    String rules;

    //Adjusts the speed of the snake
    static Timer timer;

    //Needed for sound effects
    static Clip clip, clip2;

    //Creates images
    Image body, nibble, head;

    public static void main(){
        Board content = new Board (newScore);

        //Create window
        JFrame window = new JFrame ("play.");
        window.setContentPane (content);
        window.setBackground(Color.decode("#f0f7da"));

        //Set window dimensions as 500 x 500 pixels
        window.setSize (500,500);

        //Center window
        window.setLocationRelativeTo(null);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    //Needed for snake movement
    public static void left(){
        left = true;
        up = false;
        down = false;
    }

    public static void right(){
        right = true;
        up = false;
        down = false;
    }

    public static void up(){
        up = true;
        right = false;
        left = false;
    }

    public static void down(){
        down = true;
        right = false;
        left = false;
    }

    public Board(JLabel newScore){
        this.newScore = newScore;
        //Set size of board
        setPreferredSize(new Dimension(boardSize, boardSize));
        right = true;
        left = false;
        up = false;
        down = false;

        setBackground(Color.decode("#77ab59"));

        setLayout(null);

        //Create and style ready message
        final JLabel readyUp = new JLabel("begin!");
        readyUp.setFont (new Font("Poppins", Font.BOLD, 50));
        readyUp.setForeground(Color.decode("#f0f7da"));
        readyUp.setBounds(175,225,500,80);
        add(readyUp);

        Timer read = new Timer(300, this);
        read.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    getImages();
                    startGame();
                    readyUp.setText("");
                }
            });
        read.setRepeats(false);
        read.start();
    }

    public void getImages(){
        //Get the snake's body (black circle with a green outline)
        body = new ImageIcon("assets/image/dot.png").getImage();

        //Get the nibbles (red circles)
        nibble = new ImageIcon("assets/image/nibble.png").getImage();

        //Get the head (green circle)
        head = new ImageIcon("assets/image/head.png").getImage();
    }

    public void startGame(){
        //Snake begins with a size of 3 (2 dots = body; 1 dot = head)
        dots = 3;

        for (int i = 0; i < dots; i++){
            //Axis = constant (what pixel the snake begins) - [spacing]
            x[i] = 0 - (i * 50);
            y[i] = 10;
        }
        //Find the nibble's (x,y) coordinates
        locateNibble();

        //Make the snake move
        timer = new Timer(speed, this);
        timer.start();
    }

    public void locateNibble(){
        //Randomized x Coordinate of the nibble
        int r = (int) (Math.random() * randomNibble);
        xnibble = ((r * imageSize));

        //Randomized y-coordinate of the nibble
        r = (int) (Math.random() * randomNibble);
        ynibble = ((r * imageSize));
    }

    public void paintComponent(Graphics g){
        //Called from repaint, reupdates game frames
        super.paintComponent(g);

        doDrawing(g);
    }

    public void doDrawing(Graphics g){
        if (inGame){
            //Draw the nibble and add an Action Listener
            g.drawImage(nibble, xnibble, ynibble, this);

            for (int i = 0; i < dots; i++){
                if (i == 0) {
                    //Draw the snake head
                    g.drawImage(head, x[i], y[i], this);
                } else{
                    //Draw the snake body
                    g.drawImage(body, x[i], y[i], this);
                }
            }
        } else{
            gameOver(g);
        }
    }

    public void checknibble(){
        //If the location of the head of the snake = the location of the nibble, extend the body by 1 and increase the score by 1
        if ((x[0] == xnibble) && (y[0] == ynibble)){
            //Play the yum sound effect
            noLoop("assets/audio/yum.wav");

            this.newScore = newScore;
            updateScore();
            dots++;

            //Change the nibble location
            locateNibble();
        }
    }

    public static void updateScore(){
        //If speed is slow
        if (increase == 1){
            points++;
        }

        //If speed is default
        if (increase == 3){
            points+=3;
        }

        //If speed is fast
        if (increase == 5){
            points+=5;
        }

        newScore.setText("score: "+points);
        return;
    }

    public void checkCollision(){
        for (int i = dots; i > 0; i--){
            //The amount of dots must be greater than 4 because the player can't die by running into themselves with a length of 4 dots
            //Perform a double check as the exact location is (x,y)
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])){
                inGame = false;
            }
        }
        //Check if snake is still in the boardArea
        if (y[0] >= boardSize){
            inGame = false;
        }

        if (y[0] < 0){
            inGame = false;
        }

        if (x[0] >= boardSize){
            inGame = false;
        }

        if (x[0] < 0){
            inGame = false;
        }

        //If the player performs a collision, the timer (allows for snake movement) stops
        if (!inGame){
            timer.stop();
        }
    }

    public void move(){
        //Shifts dot coordinates to the direction in which the snake is travelling
        for (int i = dots; i > 0; i--){
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }
        //x axis DECREASES going right
        if (left){
            x[0] -= imageSize;
        }
        //x axis INCREASES going right
        if (right){
            x[0] += imageSize;
        }
        //y axis DECREASES going right
        if (up){
            y[0] -= imageSize;
        }
        //y axis INCREASES going right
        if (down){
            y[0] += imageSize;
        }
    }

    public void gameOver(Graphics g){
        String gameOver = "game over";
        Font small = new Font("Poppins", Font.BOLD, 50);

        //Get metrics from the graphics
        FontMetrics metr = getFontMetrics(small);
        g.setColor(Color.decode("#f0f7da"));
        g.setFont(small);

        //Place the "Game Over" text in the centre of the board
        g.drawString(gameOver, (boardSize - metr.stringWidth(gameOver)) / 2, boardSize / 2);

        if (points > score3){
            updateHighscore();
            displayAchievement(g);
        }
        else{
            String tryAgain = "try again!";
            Font e = new Font("Poppins", Font.BOLD, 25);

            //Get metrics from the graphics
            g.setColor(Color.decode("#f0f7da"));
            g.setFont(small);

            //Place the "WOW..." text in the centre of the board
            g.drawString(tryAgain, (boardSize - metr.stringWidth(tryAgain)) / 2, (boardSize / 2) + 50);
        }
    }

    public void updateHighscore(){
        //Sort the player's points and scores
        if (points > score1){
            score3 = score2;
            score2 = score1;
            score1 = points;

            hsplayer3 = hsplayer2;
            hsplayer2 = hsplayer1;
            hsplayer1 = currentPlayer.getText();

            //Create a flag
            a = 1;
        }
        else if (points > score2){
            score3 = score2;
            score2 = points;

            hsplayer3 = hsplayer2;
            hsplayer2 = currentPlayer.getText();

            //Create a flag
            a = 2;
        }
        else{
            score3 = points;

            hsplayer3 = currentPlayer.getText();

            //Create a flag
            a = 3;
        }
        //Reset the points value on the board
        points = 0;
    }

    public void displayAchievement(Graphics g){
        if (a == 1){
            String achivement = "you're in first place!";
            Font small = new Font("Poppins", Font.BOLD, 25);

            //Get metrics from the graphics
            FontMetrics metr = getFontMetrics(small);
            g.setColor(new Color(255,215,0));//Gold
            g.setFont(small);

            //Place the "WOW..." text in the centre of the board
            g.drawString(achivement, (boardSize - metr.stringWidth(achivement)) / 2, (boardSize / 2) + 50);
        }
        else if (a == 2){
            String achivement = "you're in second place!";
            Font small = new Font("Poppins", Font.BOLD, 25);

            //Get metrics from the graphics
            FontMetrics metr = getFontMetrics(small);
            g.setColor(new Color(192,192,192));//Silver
            g.setFont(small);

            //Place the "WOW..." text in the centre of the board
            g.drawString(achivement, (boardSize - metr.stringWidth(achivement)) / 2, (boardSize / 2) + 50);
        }
        else{
            String achivement = "you're in third place!";
            Font small = new Font("Poppins", Font.BOLD, 25);

            //Get metrics from the graphics
            FontMetrics metr = getFontMetrics(small);
            g.setColor(new Color(210,105,30));//Bronze
            g.setFont(small);

            //Place the "WOW..." text in the centre of the board
            g.drawString(achivement, (boardSize - metr.stringWidth(achivement)) / 2, (boardSize / 2) + 50);
        }
    }

    public void actionPerformed(ActionEvent e){
        if (inGame){
            //Check for increase in size/score
            checknibble();

            //Check for death
            checkCollision();

            //Move the snake accordingly
            move();
        }
        //Repeat
        repaint();
    }

    //A method used to play audio files (compatibility is guaranteed only with .wav files)
    public static void music(String text){
        //Tries the code, but provides a "catch" to handle any exceptions
        try{
            //Opens an audio input stream
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(text));

            //Creates an audio clip reference
            clip = AudioSystem.getClip();

            //Opens the file(ais) to the clip
            clip.open(ais);

            //Loops the clip
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }

        //In case of error, the terminal outputs that the audiofile is not found
        catch(Exception e){
            System.out.println("ERROR\nAudio file could not be found!");
        }
    }

    //Same as the music method, but this method does not loop the audiofile
    public static void noLoop(String text){
        //Tries the code, but provides a "catch" to handle any exceptions
        try{
            //Opens an audio input stream
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(text));

            //Creates an audio clip reference
            clip2 = AudioSystem.getClip();

            //Opens the file(ais) to the clip
            clip2.open(ais);

            //Starts the clip
            clip2.start();
        }

        //In case of error, the terminal outputs that the audiofile is not found
        catch(Exception e){
            System.out.println("ERROR\nAudio file could not be found!");
        }
    }
}
