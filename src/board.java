import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class board extends JPanel implements ActionListener {
    private Image apple;
    private Image dot;
    private Image head;
    private final int Dot_Size = 10;
    private final int ALL_DOT = 900;
    private final int Random_position = 45;

    private int apple_x;
    private int apple_y;

    private final int x[]= new int[ALL_DOT];
    private final int y[]= new int[ALL_DOT];

    
    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;
    
    private Timer timer;

    private int dots;
    board (){
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(600,600));//sets the size of panel
        
        setFocusable(true);//key listener works only if set focusable is true
        loadimage();
        initgame();
    }
    public  void  loadimage(){
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("iconsforsnakegame\\apple.png"));//apple
        apple = i1.getImage();
        
        ImageIcon i2 = new ImageIcon(ClassLoader.getSystemResource("iconsforsnakegame\\green_dot.png"));//dot
        dot = i2.getImage();
        
        ImageIcon i3 = new ImageIcon(ClassLoader.getSystemResource("iconsforsnakegame\\red_dot.png"));//head
        head = i3.getImage();
    }

    public void initgame(){// this is for making snake by arranging the dots one after another
        dots = 3;
        for (int z = 0; z < dots; z++) {
            x[z]=50-z*Dot_Size;
            y[z]=50;
        }
        locateApple();

        timer=new Timer(140,this);
        timer.start();
    }

    public void locateApple(){
        int r =(int)(Math.random()*Random_position);
        apple_x = r*Dot_Size;

        int r1=(int)(Math.random()*Random_position);
        apple_y = r1*Dot_Size;
        System.out.println(apple_x);
        System.out.println(apple_y);
//        apple_x = 100;
//        apple_y = 100;
    }


    public static void main(String[] args) {
        new board ();
    }
    
    public void checkApple(){
        if (x[0]==apple_x&& y[0]==apple_y){
            dots++;
            locateApple();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if (inGame){
            g.drawImage(apple,apple_x,apple_y,this);
            for (int z = 0; z < dots; z++) {
                if (z==0){
                    g.drawImage(head,x[z],y[z],this);
                }
                else {
                    g.drawImage(dot,x[z],y[z],this);
                }
            }
            Toolkit.getDefaultToolkit().sync();
        }
        else {
            gameOver(g);
        }
    }
    public void gameOver(Graphics g){
        String msg = "Game Over";
        Font font = new Font("SAN_SERIF",Font.BOLD,18);
        FontMetrics metrics = getFontMetrics(font);
        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(msg,300- metrics.stringWidth(msg)/2,150);
    }

    public void checkCollision(){
        for (int z = dots; z >0; z--) {
            if ((z>4)&& (x[0]==x[z])&& (y[0]==y[z])){
                inGame=false;
            }
        }
        
        if (y[0]>600){
            inGame=false;
        }
        if (x[0]>600 ){
            inGame=false;
        }
        if (x[0]<0){
            inGame=false;
        }
        if (y[0]<0){
            inGame=false;
        }
        
        if (!inGame){
            timer.stop();
        }
    }

    public void move(){
        for (int z = dots; z >0 ; z--) {
            x[z]=x[z-1];
            y[z]=y[z-1];
        }
        if(leftDirection){
           x[0]-=Dot_Size; 
        }
        if(rightDirection){
            x[0]+=Dot_Size;
        }
        if(upDirection){
            y[0]-=Dot_Size;
        }
        if(downDirection){
            y[0]+=Dot_Size;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame){
            checkApple();
            checkCollision();
            move();
        }
        repaint();
    }
    
    private class TAdapter extends KeyAdapter {//to add controls to the snake
        public void keyPressed(KeyEvent e){
            int key = e.getKeyCode();
            if (key==KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection =true;
                upDirection=false;
                downDirection =false;
                
            }
            if (key==KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection =true;
                upDirection=false;
                downDirection =false;

            }
            if (key==KeyEvent.VK_UP && (!downDirection)){
                leftDirection =false;
                upDirection=true;
                rightDirection =false;

            }
            if (key==KeyEvent.VK_DOWN && (!upDirection)){
                leftDirection =false;
                rightDirection =false;
                downDirection =true;
            }
        }
        
    }
}
