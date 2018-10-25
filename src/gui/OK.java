/*
wstrzykujesz wszystko @FXML
"rozkazy" obiektom najlepiej wydawać w void initialize bo jak zrobisz to wcześniej
to obiekty mogą być nullami
 */
package gui;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.ScrollPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import logic.TruthTable;

/**
 *
 * @author HOME
 */
public class OK {
    
   // ArrayList<String> cnf = new ArrayList();
    //ArrayList<String> dnf = new ArrayList();
    
    @FXML
    private Button button;
    @FXML
    TextField poletekst = new TextField();
    @FXML
    ScrollPane suwak = new ScrollPane();
    @FXML
    AnchorPane pole = new AnchorPane();

    /**
     *
     */
    @FXML
    public TextArea obszartekst = new TextArea();
    
    @FXML
    void initialize(){
    
        button.setText("Calculate");
        EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>(){ 
                @Override
                public void handle(ActionEvent event) {
                    
                    String formula = poletekst.getText();
         
                    System.out.println("Calculating...");
                    System.out.println(poletekst.getText());
                    TruthTable table = new TruthTable(formula);
                    
                    
                    obszartekst.setText(createOutput(table.getCNFArray(), table.getDNFArray(),
                                        table.getVariableSize(), table.getCountTrue(), table.getCountFalse()));
                    obszartekst.setWrapText(true);
                               
                }
     
};
       button.addEventHandler(ActionEvent.ACTION, handler); 
    }
    
    private String createOutput(ArrayList cnf, ArrayList dnf, int n, int countTrue, int countFalse) {
        
 /*       System.out.println(variablesToShow);
          for(int i = 0; i < variablesToShow.size(); i++)
        {
         
            HashMap<Character, ArrayList<Character>> hashmap = (HashMap) variablesToShow.get(i);
            System.out.println(hashmap);
            for(Character key: hashmap.keySet())
            {
                output += key + " " +  hashmap.get(key) + " ";
            }
            output += "\n";
        }
  */      
       // int k = 0;
       
       String output = "";
        
        if(countTrue > 0)
        {
            int index = 0;
            
            output += "DNF:\n";
            
            for(int i = 0; i < countTrue; i++)
            {
                output += "(";
                for(int j = 0; j < n; j++)
                {
                    
                    output += dnf.get(i+j+index);
                    //System.out.println("Indeks: "+ i+j+index);
                    System.out.println("Getuje " + (i+j+index));
                    

                    if(j != n-1)
                        output += " and ";
                   
                }
                //here
                if(i != countTrue - 1)
                    output += ") or ";
                else
                    output += ")";
                
                index+=n-1;
                System.out.println(index);
            }
            
        }
        
        if(countFalse > 0)
        {
            int index = 0;
            output += "\nCNF:\n";
            
            for(int i = 0; i < countFalse; i++)
            {
                output += "(";
                for(int j = 0; j < n; j++)
                {
                    
                    output += cnf.get(i+j+index);
                   // System.out.println("Indeks: " + i+j+index);
                    System.out.println("Getuje " + (i+j+index));
                    

                    if(j != n-1)
                        output += " or ";
                    
                    
                }
                //here
                if(i != countFalse - 1)
                    output += ") and ";
                else
                    output += ")";
                
                index+=n-1;
                System.out.println(index);
            }
        }
        
        
        
        
        /*
        if(dnf.size() > 0)
        {
            output += "DNF: \n";
            
            for(int i = 0; i < dnf.size(); i++)
            {
                output += "(";
                output += dnf.get(i);
                output += " and ";
            }
        }*/
        
       return output;
    }
     
}

