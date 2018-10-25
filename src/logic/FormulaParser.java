/* LOGIC AND SET THEORY PROJECT */

package logic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class FormulaParser {
    
    private HashMap<Integer, String> SubformulaHashMap = new HashMap();
    private TreeSet<Character> VariablesSet = new TreeSet();
    
    private int countSubformulas = 0;
    private int lastSubformulaIndex;
    
    /* Main method - call it to begin parsing the formula! */
    public String parseFormula(String formula) {
        
        /* Call method, which parse original formula to subformula, basing on the position of brackets */
        String newFormula = parseByBrackets(formula);
        
        /* For every logical operator, contained by OperatorsEnum, call method which parse formula by operators */
        for(OperatorsEnum op: OperatorsEnum.values())
            newFormula = parseByOperators(newFormula, op.getOperator(), op.getX(), op.getY());
       
        /* Remember index of last formula before calling checkAgain method - it's the starting point for evaluating the formula */
        lastSubformulaIndex = countSubformulas;
        
        /* Call checkAgain method to parse subformulas in brackets, which have more than 3 variables and more than 2 logical connectives */
        newFormula = checkAgain(newFormula);
        
        System.out.println(SubformulaHashMap);           
        System.out.println(VariablesSet);
        System.out.println("Koncowa cyfra, od ktorej zaczynamy wartosciowanie formuly:" + lastSubformulaIndex);
        return newFormula;
    }
    
    public HashMap getSubformulas() {
        
        return SubformulaHashMap;
    }
    
    public TreeSet getVariables() {
        
        return VariablesSet;
    }
        
   public int getLastSubformulaIndex() throws NullPointerException {
       
       return lastSubformulaIndex;
   }
   
   public String getMainOperator() {
       
       for(OperatorsEnum op: OperatorsEnum.values())
           if(SubformulaHashMap.get(lastSubformulaIndex).contains(op.getOperator()))
               return op.getOperator();
       return null;
   }
   
   public int getMainX() {
       
       for(OperatorsEnum op: OperatorsEnum.values())
           if(SubformulaHashMap.get(lastSubformulaIndex).contains(op.getOperator()))
               return op.getX();
       return 0;
   }
   
   public int getMainY() {
       
        for(OperatorsEnum op: OperatorsEnum.values())
                if(SubformulaHashMap.get(lastSubformulaIndex).contains(op.getOperator()))
                    return op.getY();
        return 0;
   }
    
    private String parseByBrackets(String formula) {
       
        int openingBracket = formula.indexOf('(');
        int closingBracket;
        String subformula;
        
        /* Check, if there is any bracket */
        if(openingBracket == -1)
            System.out.println("Nie ma zadnego nawiasu.");
        
        /* If the opening bracket has been found */
        else
        {
            System.out.println("Bracket found");
            
            /* Look for the last of the opening brackets */
            while(formula.indexOf('(', openingBracket+1) != -1)
            {
                openingBracket = formula.indexOf('(', openingBracket+1);
            }
            /* Obtain position of closing bracket closest to the opening bracket */
            closingBracket = formula.indexOf(')', openingBracket);
            
            /* Extract a subformula between brackets */
            try {
                
                checkVariable(formula);
                /* Call the method changeFormula to cut subformula and substitute it with digit, corresponding to its' place in SubformulasHashMap */
                formula = changeFormula(formula, openingBracket, closingBracket, 0, 1);
                
                /* Call parser for the remaining formula */
                return parseByBrackets(formula);
                
            } catch (Exception e) {
                System.out.println(e);
                /* If OutOfBound Exception is caught here, it means that the formula is invalid */
                System.out.println("Invalid formula!"); 
            }
        }
        
        return formula;
    }
    
    
    private String parseByOperators(String formula, String operator, int x, int y) {
        
        String subformula;
        System.out.println("Jestesmy w przestrzeni operatora " + operator);
        
        /* Obtain position of first occurence of operator, for which the method is called */
        int position = formula.indexOf(operator);
        
        char leftSide;
        char rightSide;
        
        /* If the operator has not been found... */
        if(position == -1)
            System.out.println("Nie znaleziono operatora " + operator);
        
        /* If the operator has been found... */
        else
        {
            System.out.println("Znaleziono operator " + operator + " na pozycji " + position);
            leftSide = formula.charAt(position+x); // character on the left side of logical operator
            rightSide = formula.charAt(position+y); // character on the right side of logical operator
            System.out.println("Na lewo: " + leftSide);
            System.out.println("Na prawo: " + rightSide);
            
            /* Condition block to check, if there are digits (parsed subformulas), single variables (p, q, ...), or ~ (negation) */
            if((Character.isLetter(leftSide) && Character.isLetter(rightSide))
               || (Character.isDigit(leftSide) && Character.isLetter(rightSide))
               || (Character.isLetter(leftSide) && Character.isDigit(rightSide))
               || (Character.isDigit(leftSide) && Character.isDigit(rightSide))
               || (Character.isLetter(leftSide) && rightSide == '~')
               || (Character.isDigit(leftSide) && rightSide == '~'))
            {
                /* Try to parse subformula */
                try
                {
                    formula = changeFormula(formula, position, position, x, y+1);
                }
                catch (Exception e)
                {
                    System.out.println(e); // Exception here means that probably formula was invalid (?)
                }
                
                /* Call recursively parseByOperators to parse all subformulas with the same logical operator */
                return parseByOperators(formula, operator, x, y);
            }
        }
       
       /* If no more operators have been found, return changed formula */
       return formula;
    }   
    
    /* A method, which parses original formula, put the subformula into HashMap and replace it by corresponding digit. */
    private String changeFormula(String formula, int left, int right, int x, int y) {
        
        
        /* If a character before single variable on the left side is ~, it means that variable is negated and x should be decremented in order
        for ~ to be contained inside subformula */
        
        /*try {
            if(Character.isLetter(formula.charAt(left+x)))
             {
                 System.out.println(formula.charAt(left+x));
                 VariablesSet.add(formula.charAt(left+x));
                 System.out.println("Wtiaj nowa zmienno!");
             } }
         catch (Exception e) { System.out.println("Wyjatek w 178"); }*/
        
        
        try {
            if(formula.charAt(left+x-1) == '~')
            {
                x--;
                System.out.println("No jest tylda.");
            }
        } catch(Exception e) { System.out.println("Lol"); }
        
        
        
        /* Same as above, but slightly differs as it's applied to right side */
        
        try {
            if(formula.charAt(right+y-1) == '~')
            {
                y++;
                System.out.println("Na prawo tez.");
            }
        } catch (Exception e) { System.out.println("Lol!"); }
        
        /*
        try {
            
            if(Character.isLetter(formula.charAt(right+y))) // z -1
            {
                System.out.println(formula.charAt(right+y));
                VariablesSet.add(formula.charAt(right+y));
                System.out.println("Witaj nowa zmienno!");
            } }
        catch (Exception e) { System.out.println("Wyjatek w 186"); }*/

        
       
       /* Block to check, if the number corresponding to subformula has two digits */
            try {
            if(Character.isDigit(formula.charAt(left+x-1)))
                x--;
        
            } catch(Exception e) { System.out.println("Wyjatek w 208");  }
            
            try {
          
            if(Character.isDigit(formula.charAt(right+y)))
            {
                System.out.println("Znak: " + formula.charAt(right+y));
                y++;
            }
        } catch(Exception e) { System.out.println("Wyjatek w 214"); }
            
        System.out.println("X po: " + x + " , a Y po: " + y);
        
        /* Cut the subformula from original formula */
        String subformula = formula.substring(left+x, right+y);
        System.out.println("Wycieta podformula: " + subformula);
        checkVariable(subformula);
        
        ++countSubformulas;

        System.out.println("Znaleziona podformula nr " + countSubformulas + ": " + subformula);

        /* Replace subformula with corresponding number */
        formula = formula.replace(subformula, "" + countSubformulas);
        System.out.println("Formula po zamianie: " + formula);
                

        /* Put the subformula into HashMap */
        SubformulaHashMap.put(countSubformulas, subformula);
        System.out.println("Kolekcja SubformulaHashMap po dodaniu nowej podformuly: " + SubformulaHashMap);
        
        return formula;
    }

    
    private void checkVariable(String formula) {
        
        int position = 0;
        int x = 0;
        int y = 0;
        
        for(OperatorsEnum op: OperatorsEnum.values())
        {
            if(formula.contains(op.getOperator()))
            {
                System.out.println("Sprawdzarka zmiennych - formula: " + formula);
                System.out.println("Sprawdzam czy jest zmienna formula zawiera " + op.getOperator());
                position = formula.indexOf(op.getOperator());
                x = op.getX();
                y = op.getY();
            }
        }
        
        if(Character.isLetter(formula.charAt(position+x)))
        {
            System.out.println("Dodaje do zmiennych " + formula.charAt(position+x));
            VariablesSet.add(formula.charAt(position+x));
        }
        
        if(formula.indexOf('~', position) != -1)
            y++;
                
         if(Character.isLetter(formula.charAt(position+y)))
         {
             VariablesSet.add(formula.charAt(position+y));
             System.out.println("Dodaje do zmiennych: " + formula.charAt(position+y));
         }
        
    }
    
    /* Method, which iterates through HashMap again to make sure that subformulas are properly parsed
    and all variables are added */
    private String checkAgain(String formula) {
        
        Iterator it = SubformulaHashMap.entrySet().iterator();
        List<Integer> TempList = new LinkedList(); // Temporary list, containing the indexes of formulas which need further parsing
        
        while(it.hasNext())
        {
            int operatorOccurences = 0;
            
            Map.Entry pair = (Map.Entry)it.next();
            String temp = pair.getValue().toString();
            int temp1 = (Integer) pair.getKey();
            
            for(OperatorsEnum op: OperatorsEnum.values())
            {    
                operatorOccurences += countOccurences(temp, op.getOperator());
                
                if(operatorOccurences > 1)
                {
                    System.out.println(temp1);
                    System.out.println(temp);
                    TempList.add(temp1);
                }
            }
            
        }
        
        System.out.println("Indeksy do ponownego przeszukania:" + TempList);
        
        for(Integer i: TempList)
        {
            for(OperatorsEnum op: OperatorsEnum.values())
            {
                //newFormula = parseByOperators(newFormula, op.getOperator(), op.getX(), op.getY());
                //parseByOperators(SubformulaHashMap.get(i).toString(), op.getOperator(), op.getX(), op.getY());
                formula = parseByOperators(SubformulaHashMap.get(i).toString(),
                                      op.getOperator(), op.getX(), op.getY());

                /*SubformulaHashMap.put(i, parseByOperators(SubformulaHashMap.get(i).toString(),
                                      op.getOperator(), op.getX(), op.getY()));*/
                 SubformulaHashMap.put(i, formula);
            }
            
        }
        
      /*  for(String value: SubformulaHashMap.values())
        {
            if value.indexOf('(')
        }
        */
        return formula;
    }
    
    
    /* Method from StackOverflow, which was required to some functionalities */
    private int countOccurences(String str, String word) {
        // split the string by spaces in a
        String a[] = str.split(" ");

        // search for pattern in a
        int count = 0;
        
        for (int i = 0; i < a.length; i++) 
        {
            // if match found increase count
            if (word.equals(a[i]))
                count++;
        }
 
        return count;
    }
}
