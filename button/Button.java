import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.EventQueue;
import java.awt.event.ActionListener;


public class Button extends javax.swing.JFrame
{ 
    private static final long serialVersionUID = 1L;
    Timer timer=new Timer(5, null);
    private Random d = new Random();
    Thread thread;

    final JButton button = new JButton("Kliknij mnie!");

    int x, y, xDirection, yDirection, i, j;
    

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public Button() 
    {
        setSize(screenSize); //Ustawienie rozmiaru okna 300x300
        setLocationRelativeTo(null); //Ustawienie polozenie okna
        setUndecorated(true);
        JPanel panel = new JPanel(); //Tworzenie panelu za pomoc� konstruktora bez tytulu
        panel.add(createButton()); //Dodanie przycisku do kontenera - panelu
        setContentPane(panel);
        setBackground(new Color (0f,0f,0f,0f));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Opcja przycisku zamkni�cia - wyjscie z aplikacji
    }

    private JButton createButton() 
    {

    	
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                timer.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        if(x == i || y == j)
                        {
                            timer.stop();
                        }
                        else
                            changeDirection(button);
                        }
                    });
                    
        button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                i = d.nextInt(screenSize.width-100); //Funkcja losuje zmienna miedzy 0 a 200
                j = d.nextInt(screenSize.height-50);
                x = button.getX();
                y = button.getY();
                timer.start();
            }
        });
    }
    });
    return button;
    }

    void changeDirection( JButton button)
    {
        xDirection = 1;
        yDirection = 1;

        if(x > i)
            xDirection = -1;
        if(y > j)
            yDirection = -1;
        if(x != i)
            x=x + xDirection;
        if(x!=j)
            y=y + yDirection;

    button.setLocation(x, y);
    }

    public static void main(String[] args) 
    {
      new Button().setVisible(true);
    }
    
}
