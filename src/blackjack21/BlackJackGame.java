
import java.awt.Frame;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Thunder
 */

enum USER {player, pc}

public class BlackJackGame {
    public int currentCard = 0;
    public int currentPlayerCard = 0;
    public int currentPCCard = 0;
    
    JFrame f;
    public int money = 0;
    public int safety = 0;
    public int allMoney = 1000;
    
    ArrayList<Card> cards = null;
    
    public int mainWinLength = 1024;
    public int mainWinWidth = 768;
    Card [] player = new Card[5];
    Card [] pc = new Card[5];
    ToolBar tbar;
    Table table;
    Frame mainWin;
    
    BlackJackGame(){
        ToolBar tbar = new ToolBar(this); 
        mainWin = new JFrame();
        mainWin.add(tbar, BorderLayout.NORTH);
        mainWin.setVisible(true);
        mainWin.setSize(mainWinLength,mainWinWidth);
        mainWin.setLocation(300,200);
        mainWin.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){System.exit(0);}});
        mainWin.setBackground(Color.WHITE);
        table = new Table(this);
        table.setBackground(Color.WHITE);
        mainWin.add(table,BorderLayout.CENTER);
        mainWin.setVisible(true);
    }
    public void newCards(){
       if(cards==null){  
           cards = new ArrayList<Card>();
           for(int i=0;i<4;i++){
                for(int j=1;j<=13;j++) cards.add(new Card(i,j));
            }
        }
    }
    
    public int getPoints(USER u){
        int points=0;
        int numOfA=0;
        if(u==USER.pc){
            for(int i=0;i<this.currentPCCard;i++){ 
               points+=pc[i].point > 10 ? 10:pc[i].point;
               if(pc[i].point==1) numOfA++;
            }
            if(numOfA>=1) points = (points+10)>21 ? points : points+10;
        }
        else if(u==USER.player){
            for(int i=0;i<this.currentPlayerCard;i++){ 
               points+=player[i].point > 10 ? 10:player[i].point;
               if(player[i].point==1) numOfA++;
            }    
            if(numOfA>=1) points = (points+10)>21 ? points : points+10;
        }
        return points;
    }
    public void shufling(){
        for(int i=0;i<52;i++){
            int p = (int)(Math.round(Math.random()*10000))%52;
            Card tempI=cards.get(i);
            Card tempP=cards.get(p);
            cards.remove(i);
            cards.add(i, tempP);
            cards.remove(p);
            cards.add(p,tempI);
        }
    }
    
    public void initial(){
        if(allMoney <=0){
            JOptionPane.showMessageDialog(f,"恭喜你 你破產瞜!!");
            JOptionPane.showMessageDialog(f,"88888888888888888");
            System.exit(0);
        }
        if(this.tbar.startGameButton.getLabel().equals("開始遊戲")){
            f=new JFrame("JOptionPane Test");
            int mType=JOptionPane.INFORMATION_MESSAGE;
            String moneyString=JOptionPane.showInputDialog(f,"請下注","下注",mType);
            JOptionPane.showMessageDialog(f,"您輸入的是 : " + moneyString);
            if(moneyString == null || moneyString.length() == 0) money = 0;
            else money = Integer.parseInt(moneyString);
            this.tbar.moneytb.setText("賭金" + money);
            allMoney -= money; 
            this.tbar.allMoney.setText("目前您的總財產為" + allMoney);
            newCards();
            shufling();
            this.tbar.startGameButton.setText("繼續遊戲");
        }
        else if(this.tbar.startGameButton.getLabel().equals("繼續遊戲")){
            f=new JFrame("JOptionPane Test");
            int mType=JOptionPane.INFORMATION_MESSAGE;
            String moneyString=JOptionPane.showInputDialog(f,"請下注","下注",mType);
            JOptionPane.showMessageDialog(f,"您輸入的是 : " + moneyString);
            if(moneyString == null || moneyString.length() == 0) money = 0;
            else money = Integer.parseInt(moneyString);
            this.tbar.moneytb.setText("賭金" + money);
            allMoney -= money; 
            this.tbar.allMoney.setText("目前您的總財產為" + allMoney);
            
            currentCard = 0;
            currentPlayerCard=0;
            currentPCCard=0;  
            safety = 0;
            this.tbar.safety.setText("保險金目前為" + safety);
            this.table.reset();
            newCards();
            shufling();
        }

        pc[0].setCard(cards.get(currentCard++));
        player[0].setCard(cards.get(currentCard++));
        System.out.println(pc[0].point);
        
        pc[1].setCard(cards.get(currentCard++));
        pc[1].showBack();
        player[1].setCard(cards.get(currentCard++));
        if(pc[0].point == 1)
        {
            f = new JFrame("JOptionPane Test");
            int mType=JOptionPane.INFORMATION_MESSAGE;
            String safetyString=JOptionPane.showInputDialog(f,"請問要不要購買保險","下注",mType);
            JOptionPane.showMessageDialog(f,"您保險金額為 : " + safetyString);
            if(safetyString == null || safetyString.length() == 0) safety = 0;
            else safety = Integer.parseInt(safetyString);
            this.tbar.safety.setText("保險金目前為" + safety);
            allMoney -= safety; 
            this.tbar.allMoney.setText("目前您的總財產為" + allMoney);
            if(pc[1].point == 10 || pc[1].point == 11 || pc[1].point == 12 || pc[1].point == 13){
                pc[1].showBack();
                JOptionPane.showMessageDialog(f,"莊家21點 所以您贏的了兩倍保險金額: " + safety*2);
                allMoney = allMoney + safety*3 + money;
                this.tbar.allMoney.setText("目前您的總財產為" + allMoney);
                this.initial();
                tbar.startGameButton.setEnabled(true);
            }
        }
        player[2].showControlPanel(true);
        currentPlayerCard=2;
        currentPCCard=2;      
    }
}