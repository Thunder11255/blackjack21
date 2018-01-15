
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Thunder
 */

public class Card extends Panel{
    
    int suit;
    int point;
    int addMoney;
    boolean back=false;
    JPanel controlPanel;
    Table table;
    JButton addCard, stop;
    JFrame f;
    Image image = null;
    
    Card(int s, int p, Table tab){
        this(s,p);
        table = tab;    
    }
    Card(int s, int p){
        suit=s;
        point=p;
        this.setBackground(Color.white);
        controlPanel = new JPanel();
        controlPanel.setBackground(Color.red);
        addCard = new JButton("補一張牌");
        stop = new JButton("不補牌");
        controlPanel.setLayout(new BorderLayout());
        controlPanel.add(addCard,BorderLayout.CENTER);
        controlPanel.add(stop, BorderLayout.NORTH);
      
        stop.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                controlPanel.revalidate();
                table.parent.tbar.SURRENDERBtn.setEnabled(false);
                table.parent.pc[1].showFront();
                int p=table.parent.getPoints(USER.pc);
                System.out.println("point=" + p);
                
                while((p<16)&&(table.parent.currentPCCard<5)){
                    table.parent.pc[table.parent.currentPCCard++].setCard(
                        table.parent.cards.get(table.parent.currentCard++)
                    );              
                    p=table.parent.getPoints(USER.pc);
                    System.out.println("point=" + p);
                }
                
                //pc --> p
                //user --> 
                int u = table.parent.getPoints(USER.player);
                
                if(p>=21){
                    // user
                    table.parent.tbar.message.setText("閒家贏!!");
                    table.parent.allMoney += table.parent.money*2;
                    table.parent.tbar.allMoney.setText("目前您的總財產為" + table.parent.allMoney);
                    //System.out.println("USER point=" + table.parent.getPoints(USER.player) 
                    //+ "PC point=" + table.parent.getPoints(USER.pc)); 
                }
                else if(p>u){
                    table.parent.tbar.message.setText("莊家贏");
                    table.parent.allMoney -= table.parent.money;
                    table.parent.tbar.allMoney.setText("目前您的總財產為" + table.parent.allMoney);
                   // System.out.println("USER point=" + table.parent.getPoints(USER.player) 
                   //+ "PC point=" + table.parent.getPoints(USER.pc));  
                }
                else if(p==21 && u!=21){
                    table.parent.tbar.message.setText("You lose!");
                    table.parent.allMoney -= table.parent.money;
                    table.parent.tbar.allMoney.setText("目前您的總財產為" + table.parent.allMoney);
                    //System.out.println("USER point=" + table.parent.getPoints(USER.player) 
                    //+ "PC point=" + table.parent.getPoints(USER.pc));
                }
                else{
                    table.parent.tbar.message.setText("閒家贏!!");
                    table.parent.allMoney += table.parent.money*2;
                    table.parent.tbar.allMoney.setText("目前您的總財產為" + table.parent.allMoney);
                    //System.out.println("USER point=" + table.parent.getPoints(USER.player) 
                    //+ "PC point=" + table.parent.getPoints(USER.pc));
                }
                table.parent.tbar.startGameButton.setEnabled(true);
                showControlPanel(false);
                revalidate();
            }
        });
        
        addCard.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                controlPanel.revalidate();
                table.parent.tbar.SURRENDERBtn.setEnabled(false);
                f=new JFrame("JOptionPane Test");
                int mType=JOptionPane.INFORMATION_MESSAGE;
                String moneyString=JOptionPane.showInputDialog(f,"請加注","加注",mType);
                JOptionPane.showMessageDialog(f,"您輸入的是 : " + moneyString);
                if(moneyString == null || moneyString.length() == 0){addMoney = 0;}
                else{addMoney = Integer.parseInt(moneyString);}
                table.parent.money += addMoney;
                table.parent.tbar.moneytb.setText("賭金" + table.parent.money);
                table.parent.allMoney -= addMoney;
                table.parent.tbar.allMoney.setText("目前您的總財產為" + table.parent.allMoney);
                //System.out.println(table.parent.money);
                
                //System.out.println("CurrentPlayerCard=" + table.parent.currentPlayerCard);
                table.parent.player[table.parent.currentPlayerCard++].
                        setCard(table.parent.cards.get(table.parent.currentCard++));
                
                if(table.parent.getPoints(USER.player)<21 && table.parent.currentPlayerCard<5)
                {table.parent.player[table.parent.currentPlayerCard].showControlPanel(true);}
                else if(table.parent.getPoints(USER.player)>21){
                   table.parent.tbar.message.setText("莊家贏!!");
                   table.parent.tbar.allMoney.setText("目前您的總財產為" + table.parent.allMoney);
                   table.parent.pc[1].showFront();
                   table.parent.tbar.startGameButton.setEnabled(true);
                }
                else if(table.parent.getPoints(USER.player)==21){
                    if(table.parent.getPoints(USER.pc)<21){
                        table.parent.tbar.message.setText("閒家贏!!");
                        //System.out.println("USER point=" + table.parent.getPoints(USER.player) 
                        //+ "PC point=" + table.parent.getPoints(USER.pc));
                        table.parent.allMoney += table.parent.money*2;
                        table.parent.tbar.allMoney.setText("目前您的總財產為" + table.parent.allMoney);
                    }
                    else{
                        table.parent.tbar.message.setText("平手");
                        //System.out.println("USER point=" + table.parent.getPoints(USER.player) 
                        //+ "PC point=" + table.parent.getPoints(USER.pc));
                        table.parent.allMoney += table.parent.money;
                        table.parent.tbar.allMoney.setText("目前您的總財產為" + table.parent.allMoney);
                    }
                    table.parent.pc[1].showFront();
                    table.parent.tbar.startGameButton.setEnabled(true);
                }
                
                else if(table.parent.currentPlayerCard==5 && table.parent.getPoints(USER.player) <= 21){
                    table.parent.tbar.message.setText("閒家贏!!");
                    //System.out.println("USER point=" + table.parent.getPoints(USER.player) 
                    //+ "PC point=" + table.parent.getPoints(USER.pc));
                    table.parent.allMoney += table.parent.money*2;
                    table.parent.tbar.allMoney.setText("目前您的總財產為" + table.parent.allMoney);
                    table.parent.tbar.startGameButton.setEnabled(true);
                }
                else{}
                showControlPanel(false);
                revalidate();
            }
        });
    }
    
    void setCard(Card c){
        this.setCard(c.suit, c.point);
    }
    
    void setCard(int s, int p){
        suit=s;
        point=p;        
        repaint();
    }
    
    void showControlPanel(boolean b){
        if(b){
            revalidate();
            setLayout(new BorderLayout());
            add(controlPanel, BorderLayout.CENTER);      
        }
        else{
            revalidate();
            remove(controlPanel);
            revalidate();
        }
        
    }
    
    void showFront(){
        back=false;
        repaint();
    }
    void showBack(){
        back=true;
        repaint();
    }
    
    public void paint(Graphics g){
        super.paintComponents(g);
        //this.setBackground(Color.WHITE);
        String suitimage = null;
        String pointimage = null;
        String backimglocation = "src/images/peekredback.jpg";
        String nullimglocation = "src/images/null.png";
        
        System.out.println("Suit= " + suit + "Point =" + point);
        
        if((suit==-1)&&(point==-1)){}
        else if(back){
            try{
            image = ImageIO.read(new File(backimglocation));
            }
            catch (Exception ex) {
                System.out.println("No example.jpg!!");
            }
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        }
        else{
            switch(suit){
                case 0:
                    suitimage = "s";
                    break;
                case 1:
                    suitimage = "h";
                    break;
                case 2:
                    suitimage = "d";
                    break;
                case 3:
                    suitimage = "c";
                    break;
            }            
            pointimage = String.valueOf(point);
            String imgLocation = "src/images/" + suitimage + pointimage + ".png";
            try{
                image = ImageIO.read(new File(imgLocation));
            }
            catch (Exception ex) {
                System.out.println("No example.jpg!!");
            }
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        }  
        revalidate();
    }
}
