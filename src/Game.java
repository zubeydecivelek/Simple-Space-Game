import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Game extends JPanel implements KeyListener, ActionListener {
    Timer timer = new Timer( 95,this);

    private int passingTime = 0;
    private int usedFire = 0;

    private BufferedImage image;

    private ArrayList<Fire> fireArrayList = new ArrayList<Fire>();

    private int fireDirY = 1;

    private int ballX = 0;
    private int ballDirX = 2;

    private int spaceShipX = 0;
    private int spaceShipDirX = 20;

    public Game(){
        try {
            image = ImageIO.read(new FileImageInputStream(new File("spaceShip.png")));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        setBackground(Color.BLACK);
        timer.start();
    }

    public boolean Control(){
        for (Fire fire : fireArrayList){
            if (new Rectangle(fire.getX(),fire.getY(),10,20).intersects(new Rectangle(ballX,0,20,20))){
                return true;
            }
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        passingTime += 5;

        g.setColor(Color.RED);
        g.fillOval(ballX,0,20,20);
        g.drawImage(image,spaceShipX,490,image.getWidth()/10,image.getHeight()/10,this);

        fireArrayList.removeIf(fire -> fire.getY() < 0);

        g.setColor(Color.BLUE);


        for (Fire fire:fireArrayList){
            g.fillRect(fire.getX(),fire.getY(),10,20);
        }

        if (Control()){
            timer.stop();
            String message = "You win!\n You used " + usedFire + " fire\n Passing Time:" + passingTime/1000.0 + " seconds";
            JOptionPane.showMessageDialog(this,message);
            System.exit(0);
        }

    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Fire fire:fireArrayList){
            fire.setY(fire.getY() - fireDirY);
        }


        ballX +=ballDirX;
        if (ballX >= 750 || ballX <= 0){
            ballDirX = -ballDirX;
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_LEFT){
            if (spaceShipX <= 0){
                spaceShipX = 0;
            }
            else{
                spaceShipX -= spaceShipDirX;
            }
        }
        else if (keyCode == KeyEvent.VK_RIGHT){
            if (spaceShipX >=750){
                spaceShipX = 750;
            }
            else{
                spaceShipX += spaceShipDirX;
            }
        }
        else if (keyCode == KeyEvent.VK_SPACE){
            fireArrayList.add(new Fire(spaceShipX + 15, 470));
            usedFire++;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
