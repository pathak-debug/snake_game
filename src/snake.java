import javax.swing.*;

public class snake extends JFrame {
    snake(){
        add(new board());//adding board to window
        pack();// this is used to set the size of frame specified in the board
        setLocationRelativeTo(null);// places the window at the center of screen
        //now we should stop resizing of window for protecting functionality of game
        setResizable(false);// stops resizing
        setTitle("Snake Game");// gives title to frame

    }

    public static void main(String[] args) {
        new snake().setVisible(true);
    }
}
