import java.awt.*;
import javax.swing.JPanel;

/**
 *
 * @author Thunder
 */
public class Table extends JPanel{
    
    public BlackJackGame parent;
    
    Table(BlackJackGame b){
        parent=b;
        setLayout(new GridLayout(2,5));
        for(int i=0;i<5;i++){
            parent.player[4-i]=new Card(-1,-1, this);
            add(parent.player[4-i],0,i);
            parent.pc[i]=new Card(-1,-1,this);
            add(parent.pc[i],1,i);
        }
    }
    
    void reset(){
        this.revalidate();
        for(int i=0;i<5;i++){
            parent.player[4-i].setCard(-1, -1);
            parent.pc[i].setCard(-1,-1);
        }
    }
}
