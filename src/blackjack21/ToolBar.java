import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

/**
 *
 * @author Thunder
 */

public class ToolBar extends JPanel implements ActionListener{
    BlackJackGame parent;
    static final private String EXIT = "EXIT";
    static final private String NEWGAME = "NEW GAME";
    static final private String SURRENDER = "SURRENDER";
    public static JButton startGameButton = null;
    public static JButton SURRENDERBtn =null;
    public static JLabel message;
    public static JLabel moneytb;
    public JLabel nulllabel;
    public static JLabel safety;
    public static JLabel allMoney;
    
    public ToolBar(BlackJackGame p){
        this.setBackground(Color.decode("#FFBB00"));
        parent = p;
        message = new JLabel("遊戲尚未開始");
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        JToolBar toolBar = new JToolBar();
        toolBar.setBackground(Color.decode("#FFBB00"));
        addButtons(toolBar);
        setPreferredSize(new Dimension(450, 40));
        add(toolBar, BorderLayout.WEST);
        this.add(message);
        nulllabel = new JLabel("         ");
        this.add(nulllabel);
        moneytb = new JLabel();
        moneytb.setText("目前尚未下注"); 
        moneytb.setMaximumSize(moneytb.getPreferredSize());
        this.add(moneytb);
        nulllabel = new JLabel("         ");
        this.add(nulllabel);
        safety = new JLabel("保險金目前為" + parent.safety);
        this.add(safety);
        nulllabel = new JLabel("         ");
        this.add(nulllabel);
        allMoney = new JLabel("目前您的總財產為" + parent.allMoney);
        this.add(allMoney);
    }
    public void addButtons(JToolBar toolBar){
        JButton button = null;
        button = makeNavigationButton(EXIT, "離開","離開遊戲");
        toolBar.add(button);   
        startGameButton = makeNavigationButton(NEWGAME, "開始猶戲","開始遊戲");
        toolBar.add(startGameButton);   
        SURRENDERBtn = makeNavigationButton(SURRENDER, "大口九：香港規矩投降輸一半","投降");
        toolBar.add(SURRENDERBtn);  
    }
    public JButton makeNavigationButton(String actionCommand, String toolTipText,String altText){
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);
        button.setText(altText);
        return button;
    }
    public void actionPerformed (ActionEvent e){
        String cmd = e.getActionCommand();
        if (EXIT.equals(cmd)){
            System.exit(0);
        }
        else if(NEWGAME.equals(cmd)){
            startGameButton.setEnabled(false);
            parent.initial();
            parent.table.revalidate();
        }
        else if(SURRENDER.equals(cmd)){
            startGameButton.setEnabled(false);
            parent.allMoney += parent.money/2;
            allMoney.setText("目前您的總財產為" + parent.allMoney);
            parent.initial();
        }
    }
}
