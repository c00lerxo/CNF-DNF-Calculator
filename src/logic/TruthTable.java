/* LOGIC PROJECT */

// Negacje, generalizacja przy zakonczeniu rekurencji, uporzadkowac kod, stworzyc gleboka kopie hashmapy subformulas, by dzialala przy kazdym przejsciu
package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;



public class TruthTable {
    
 
    /* Attributes */
    private HashMap<Integer, String> subformulas;
    private HashMap<Integer, String> subformulasCopy = new HashMap();
    private HashMap<Character, Character> variablesCurrent = new HashMap();
    private HashMap<Character, ArrayList<Character>> variablesValues = new HashMap();
    
    private ArrayList<String> CNF = new ArrayList();
    private ArrayList<String> DNF = new ArrayList();
    private ArrayList<Integer> negatedList = new ArrayList();
  //  private ArrayList<Character> trueVariablesList = new ArrayList();
   // private ArrayList<Character> falseVariablesList = new ArrayList();
    private ArrayList<HashMap<Character, ArrayList<Character>>> variablesToShow = new ArrayList();
    
    private TreeSet<Character> variables;
    
    private int startingIndex;
    private int current;
    private int mainY = 0;
    private int countTrue = 0;
    private int countFalse = 0;
    
    private String mainOperator = "%";
   
    private boolean currentValue;
    
       
    public TruthTable(String formula) {
        
        FormulaParser parser = new FormulaParser();
        parser.parseFormula(formula);
        subformulas = parser.getSubformulas();
        variables = parser.getVariables();
        startingIndex = parser.getLastSubformulaIndex();
        mainY = parser.getMainY();
        mainOperator = parser.getMainOperator();
        //generateTruthValue(variables.size(), Integer.toString(startingIndex));
        copySubformulas();
        System.out.println(subformulas);
        System.out.println(subformulasCopy);
        System.out.println(variables);
        generateTruthValue(variables.size(), Integer.toString(startingIndex));
        System.out.println(CNF);
        System.out.println(DNF);
    }
    
    public ArrayList getVariablesToShow() {
        
        return variablesToShow;
    }
    
    public ArrayList getCNFArray() {
        
        return CNF;
    }
    
    public ArrayList getDNFArray() {
        
        return DNF;
    }
    
    public int getVariableSize() {
        
        return variables.size();
    }
    
    public int getCountTrue() {
        
        return countTrue;
    }
    
    public int getCountFalse() {
        
        return countFalse;
    }
    
    private void copySubformulas() {
        
         for(Integer key: subformulas.keySet())
         {
            subformulasCopy.put(key, subformulas.get(key));
         }
    }
    
    private void makeDecision(String subformula) {
        

        char leftSide = '0';
        char rightSide = '0';
        String operator = "^";
        
        int operatorPosition = -1;
        int x = 0;
        int y = 0;
        
        boolean leftNegated = false;
        boolean rightNegated = false;
        boolean subformulaNegated = false;
                
        System.out.println(subformula);

        for(OperatorsEnum op: OperatorsEnum.values())
        {
           // System.out.println("Sprawdzam po kolei, czy podformula zawiera operator...");
            if(subformula.contains(op.getOperator()))
            {
                System.out.println("Podformula zawiera operator " + op.getOperator());
                operator = op.getOperator();
                operatorPosition = subformula.indexOf(operator);
                x = op.getX();
                y = op.getY();
                leftSide = subformula.charAt(operatorPosition + x);
                rightSide = subformula.charAt(operatorPosition + y);
                
                try {
                    leftNegated = checkLeftNegation(subformula, operatorPosition+x);
                    if(leftNegated)
                        System.out.println("Znalazlem negacje po lewo!");

                } catch(Exception e) { System.out.println("Wyjatek w 69"); }
                
                try {
                    rightNegated = checkRightNegation(subformula, operatorPosition+y);
                    if(rightNegated)
                    {
                        y++;
                        rightSide = subformula.charAt(operatorPosition + y);
                        System.out.println("Znalazlem negacje!: " + subformula.charAt(operatorPosition + y - 1) + " " + rightSide);
                    }
                    
                } catch(Exception e) { System.out.println("Wyjatek w 69"); }
                
                try {
                    subformulaNegated = checkSubformulaNegation(subformula);
                    
                    System.out.println("negacja przed nawiasem " + subformulaNegated);
                } catch(Exception e) { System.out.println("Wyjatek w 69"); }
                
            }
            
          /*  System.out.println(subformulasCopy);
            
            String test = (String) subformulasCopy.get(startingIndex);
            char test1 = subformulasCopy.get(startingIndex).charAt(2 + mainY);
            System.out.println(mainY);
            System.out.println(mainOperator);*/
            
      /* try {
              
            if(Integer.parseInt(subformula) == startingIndex)
                current = startingIndex;
          }
          catch (Exception e) { System.out.println("Nie jestesmy w startowej formule"); }
          */
            try {
                
                char leftValue = subformulasCopy.get(startingIndex).charAt(0);
                char rightValue = subformulasCopy.get(startingIndex).charAt(2 + mainY);
                boolean negated = false;
                
                


                if((leftValue == 'T' || leftValue == 'F')
                   && (rightValue == 'T' || rightValue == 'F')) // dziala, tylko uogolnic
                {
                     System.out.println("Koncowa formula jest: " + checkValue(mainOperator, leftValue, rightValue));
                     System.out.println("Jestem w #1");
                     currentValue = checkValue(mainOperator, leftValue, rightValue);
                     return;
                }
                
                if((Character.isLetter(leftValue) && leftValue != 'T' && leftValue != 'F'
                       && (rightValue == 'T' || rightValue == 'F')))
                {
                    currentValue = checkValue(mainOperator, variablesCurrent.get(leftValue),
                                                rightValue);
                    System.out.println("Koncowa formula jest " + currentValue);
                    System.out.println("Jestem w #2");
                    return;
                }
                 
                if((Character.isLetter(rightValue) && rightValue != 'T' && rightValue != 'F'
                        && (leftValue == 'T' || leftValue == 'F')))
                {
                    
                    currentValue = checkValue(mainOperator, leftValue,
                            variablesCurrent.get(rightValue));
                    System.out.println("Koncowa formula jest " + currentValue);
                    System.out.println("Jestem w #3");
                    return;
                }
                
                
            } catch (Exception e) {  System.out.println("To jeszcze nie konec..");  }
            
             if(subformulasCopy.get(startingIndex).length() <= 1)
             {
               
                try {
                
                    char oneValue = subformulasCopy.get(startingIndex).charAt(0);

                    if(oneValue == 'T')
                    {
                        System.out.println("Koncowa formula jest true" );
                        currentValue = true;
                        return;
                    }
                    if(oneValue == 'F')
                    {
                        currentValue = false;
                        System.out.println("Koncowa formula jest false");
                        return;
                    }
            } catch (Exception e) { System.out.println("To jeszce nie koniec..."); }
             }
             
            
            /*
            try {
                char oneValue = subformulasCopy.get(startingIndex).charAt(0);
                
                if(oneValue == 'T')
                    currentValue = true;
                else
                    currentValue = false;
            } catch (Exception e) { System.out.println("Ale bez sensu..."); }*/
            }
        
        
        
 
        System.out.println("Leftside: " + leftSide);
        System.out.println("Rightside: " + rightSide);
        
        if(subformula.contains(operator))
        {
            System.out.println("Zatem podformula zawiera operator " + operator);
            

            if(Character.isLetter(leftSide) && leftSide != 'T' && leftSide != 'F')
            {
                System.out.println("Po lewo jest zmienna " + leftSide);
                char value = 'O';
                for(Character s: variables)
                {
                    //zmiany
                    if(s.equals(leftSide))
                    {
                        value = variablesCurrent.get(leftSide);
                        System.out.println(subformula);
                        
                        System.out.println("jestem tutaj!");
                        if(subformulaNegated)
                        {
                            System.out.println("Tu tez!");
                            subformula = subformula.substring(2, subformula.length()-1);
                            negatedList.add(current);
                            System.out.println(negatedList);
                            System.out.println(subformula);
                        }
                        
                        if(leftNegated)
                        {
                            System.out.println("I tu tez!");
                            subformula = subformula.substring(1, subformula.length());
                            
                            if(value == 'T')
                                value = 'F';
                            else
                                value = 'T';
                            System.out.println(subformula);
                        }
/*                            
                        if(leftNegated)
                        {
                            if(subformula.indexOf('(') == -1)
                                subformula = subformula.substring(1, subformula.length());
                            
                            else
                            if(subformulaNegated)
                                subformula = subformula.substring(2, subformula.length());
                            
                           if(value == 'T')
                                value = 'F';
                           else
                                value = 'T';
                        }   */     
                    }
                }
 
               subformula = subformula.replace(leftSide, value);
               System.out.println(subformula);
              
               System.out.println("Nasza podformula po zastapieniu wartoscia logiczna z variablesCurrent wyglada tak: " + subformula);
               System.out.println(current);
               makeDecision(subformula);
               

            }
            
            else
            if(Character.isLetter(rightSide) && rightSide != 'T' && rightSide != 'F')
            {
                char value = 'O';
                System.out.println("Po prawo jest zmienna " + rightSide);


                for(Character s: variables)
                    if(s.equals(rightSide))
                    {
                        
                        value = variablesCurrent.get(rightSide);
                        if(rightNegated)
                        {
                            StringBuilder builder = new StringBuilder(subformula);
                            builder.deleteCharAt(operatorPosition+y-1);
                            subformula = builder.toString();
                            if(value == 'T')
                                value = 'F';
                            else
                                value = 'T';
                        }
                    }
                
                subformula = subformula.replace(rightSide, value);
                System.out.println("Nasza podformula po zastapieniu wartoscia logiczna z variablesCurrent wyglada tak: " + subformula);
                makeDecision(subformula);
            }
            
            
            else
            if(Character.isDigit(leftSide))
            {
                System.out.println("Po lewej jest cyfra" + leftSide + ", wiec ja rozbijamy.");
                current = Character.getNumericValue(leftSide);
                System.out.println("Nowy current z lewej strony: " + current);
                makeDecision((String) subformulasCopy.get(Character.getNumericValue(leftSide)));
            }
            
            else
            if(Character.isDigit(rightSide))
            {
               
                System.out.println("Po prawej jest cyfra" + rightSide + ", wiec ja rozbijamy.");
               
                current = Character.getNumericValue(rightSide);
                System.out.println("Nowy current z prawej strony: " + current);
                makeDecision((String) subformulasCopy.get(Character.getNumericValue(rightSide)));
            }
            
               
            else
            if((leftSide == 'T' || leftSide == 'F') && (rightSide == 'T' || rightSide == 'F'))
            {
                System.out.println("OK, nasza podformula ma zarowno cos po lewej, jak i cos po prawej, wiec jej wartosc logiczna to:");
                
                System.out.println("Current jest rowny: " + current);
                
                boolean value = checkValue(operator, leftSide, rightSide);
                
                if(currentNegated())
                {
                    if(value == true)
                        value = false;
                    else
                        value = true;
                }
                System.out.println(value);
               // current = Character.getNumericValue(leftSide);
                System.out.println("Wkladam " + changeBoolean(value) + " do " + current);
                subformulasCopy.put(current, changeBoolean(value));
                changeSubformula(current, changeBoolean(value));
                System.out.println(subformulasCopy);
         
              
               makeDecision(Integer.toString(startingIndex));
            }
            else
            if(Character.isLetter(leftSide) && (rightSide == 'T' || rightSide == 'F'))
            {
                System.out.println("Po prawo T albo F");
                System.out.println("Po lewo jest zmienna " + leftSide);
                char value = 'O';
                for(Character s: variables)
                    if(s.equals(leftSide))
                        value = variablesCurrent.get(leftSide);   
 
               subformula = subformula.replace(leftSide, value);
            //   if(leftNegated)
              //     subformula.charAt(leftSide-1) = '';
              System.out.println("current="+current);
               System.out.println("Nasza podformula po zastapieniu wartoscia logiczna z variablesCurrent wyglada tak: " + subformula);
               makeDecision(subformula);
            }
            else
            if((leftSide == 'T' || leftSide == 'F') && Character.isLetter(rightSide))
            {
                System.out.println("Po lewo T albo F");
                 char value = 'O';
                System.out.println("Po prawo jest zmienna " + rightSide);
                
                 System.out.println("current="+current);


                for(Character s: variables)
                    if(s.equals(rightSide))
                        value = variablesCurrent.get(rightSide);
                
                subformula = subformula.replace(rightSide, value);
                System.out.println("Nasza podformula po zastapieniu wartoscia logiczna z variablesCurrent wyglada tak: " + subformula);
                makeDecision(subformula);
            }
                
        }
        else
        {
            System.out.println("Tylko jedna cyfra, nie znaleziono operatora, wiec ja rozbijmy");
            System.out.println(subformula);
            System.out.println(searchFirstDigit(subformula));
            current = Character.getNumericValue(searchFirstDigit(subformula));
            makeDecision((String) subformulasCopy.get(Character.getNumericValue(searchFirstDigit(subformula))));
        }
    
    }
    
    private void changeSubformula(int currently, String replacement) {
     
        LinkedList<Integer> temp = new LinkedList();
        for(Object key: subformulasCopy.keySet())
        {
            if(subformulasCopy.get(key).toString().contains(Integer.toString(currently)))
                temp.add((Integer) key);
        }
        
        for (int i = 0; i < temp.size(); i++) {
            
            String change = (String) subformulasCopy.get(temp.get(i));
            change = change.replace(Integer.toString(currently), replacement);
            subformulasCopy.put(temp.get(i), change);
        }

    }
    

    
    private boolean checkLeftNegation(String subformula, int leftSide) {
        
        System.out.println(subformula);
        if(subformula.charAt(leftSide - 1) == '~')
            System.out.println("Wykrylem lewa negacje");
        return subformula.charAt(leftSide - 1) == '~';
    }
    
    private boolean checkRightNegation(String subformula, int rightSide) {
        
        System.out.println(subformula);
       if(subformula.charAt(rightSide) == '~')
            System.out.println("Wykrylem prawa negacje");
       return subformula.charAt(rightSide) == '~';
    }
    
     private boolean currentNegated() {
       
        
        if(negatedList.contains(current))
            System.out.println("Obecna podformula jest zanegowana");
        return negatedList.contains(current);
    }
        
    

    private boolean checkSubformulaNegation(String subformula) {
        
        System.out.println(subformula);
        int bracketPosition = subformula.indexOf('(');
        boolean isNegated = false;
        
        if(bracketPosition != -1)
        {
            try {
                if(subformula.charAt(bracketPosition - 1) == '~')
                    isNegated = true;
            } catch(Exception e) { System.out.println("Nie ma negacji!"); }
            System.out.println("Wykrylem negacje calej formuly przed nawiasem");
        }
            
        return isNegated;
    }
            
    
    private boolean checkValue(String operator, char p, char q) {
        
        boolean pValue = true;
        boolean qValue = true;
        boolean subformulaValue;
        
        if(p == 'T')
            pValue = true;
        if(q == 'T')
            qValue = true;
        if(p == 'F')
            pValue = false;
        if(q == 'F')
            qValue = false;
        
       if(operator.equals("and"))
           return LogicalConnectives.conjunction(pValue, qValue);
       if(operator.equals("or"))
           return LogicalConnectives.disjunction(pValue, qValue);
       if(operator.equals("->"))
           return LogicalConnectives.implication(pValue, qValue);
       if(operator.equals("<=>"))
           return LogicalConnectives.equivalence(pValue, qValue);
       
       return false;
    }
    
    private String changeBoolean(boolean result) {
        if(result == true)
            return "T";
        return "F";
    }
    
    private void generateTruthValue(int N, String subformula) {
        
        
        //int rows = (int) Math.pow(2,n);
        //TreeSet<Character> temp = new TreeSet();
        
       // for (int i=0; i<rows; i++)
        //{
            //int index = 0;
            
           // for (int j=n-1; j>=0; j--)
            //{   
                //HashMap set = new HashMap();
              //  int currentValue = ((i/(int) Math.pow(2, j)) % 2);
               // System.out.println(currentValue + " ");
                
                //subformula = Integer.toString(startingIndex);
                
          

    // number of combinations
    // using bitshift to power 2
    int NN = 1<<N;

    // array to store combinations
    boolean flips[][] = new boolean[NN][N];

    // generating an array
    // enumerating combinations
    for(int nn=0; nn<NN; ++nn) {

        // enumerating flips
        for( int n=0; n<N; ++n) {

            // using the fact that binary nn number representation
            // is what we need
            // using bitwise functions to get appropriate bit
            // and converting it to boolean with ==
            flips[nn][N-n-1] = (((nn>>n) & 1)==1);

            // this is simpler bu reversed
            //flips[nn][n] = (((nn>>n) & 1)==1);

        }

    }
          
    
    char torf;

    ArrayList<Character> varArray = new ArrayList();
    for(Character var: variables)
        varArray.add(var);
        
   // TreeSet<Character> temp = new TreeSet();
    
        for(int nn=0; nn<NN; ++nn) {

        //System.out.println(temp);

        for( int n=0; n<N; ++n) {
             if(flips[nn][n] == true)
                   torf = 'T';
             else
                   torf = 'F';
             
             variablesCurrent.put(varArray.get(n), torf);
             //for(Character var: variables)
             //{
               //  if(!temp.contains(var))
                 //{
                   // variablesCurrent.put(var, torf);
                    //temp.add(var);
                 //}
             //}
            
        }
        
          System.out.println("VariablesCurrent w tej chwili:");
          System.out.println(variablesCurrent);
          
          makeDecision(subformula);
          copySubformulas();
          if(currentValue == false)
          {
              countFalse++;
              getCNF();
              
          }
          else
          {
              countTrue++;
              getDNF();
          }
          
       /*   for(Character key: variablesCurrent.keySet())
          {
              if(variablesCurrent.get(key) == 'T')
                  trueVariablesList.add(variablesCurrent.get(key));
              else
                  falseVariablesList.add(variablesCurrent.get(key));
          }*/
       /*   
         ArrayList<Character> trueVariablesListCopy = new ArrayList();
          ArrayList<Character> falseVariablesListCopy = new ArrayList();
          HashMap<Character, ArrayList<Character>> variablesValuesCopy = new HashMap();
          
          for(int i = 0; i < trueVariablesList.size(); i++)
              trueVariablesListCopy.add(trueVariablesList.get(i));
          
          for(int i = 0; i < falseVariablesList.size(); i++)
              falseVariablesListCopy.add(falseVariablesList.get(i));
          
          for(Character key: variablesValues.keySet())
              variablesValuesCopy.put(variablesValues.)  
          
          
          
          variablesValues.put('T', trueVariablesList);
          variablesValues.put('F', falseVariablesList);
          
          variablesToShow.add(variablesValues);
          System.out.println(trueVariablesList);
          System.out.println(falseVariablesList);
          System.out.println(variablesValues);
          System.out.println(variablesToShow);
          
          trueVariablesList.clear();
          falseVariablesList.clear();
          variablesValues.clear();
       */
            }

    }
  
    
    private void getCNF() {
        
        for(Character key: variablesCurrent.keySet())
        {
            if(variablesCurrent.get(key).equals('F'))
                CNF.add(key.toString());
            else
                CNF.add("~" + key.toString());       
        }    
    }
    
    private void getDNF() {
        
        for(Character key: variablesCurrent.keySet())
        {
            if(variablesCurrent.get(key).equals('F'))
                DNF.add("~" + key.toString());   
            else
                DNF.add(key.toString());    
        }    
    }
   
    
    
    private int searchFirstDigit(String subformula) {
        
        int index = -1;
        for(int i = 0; i < subformula.length(); i++)
        {
            if(Character.isDigit(subformula.charAt(i)))
            {
                    index = subformula.charAt(i);
                    break;
            }
        }
        return index;
    }
}
