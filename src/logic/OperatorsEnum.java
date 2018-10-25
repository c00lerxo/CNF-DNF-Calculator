/*
 * LOGIC
 * AND
 * SET THEORY PROJECT
 */
package logic;

 
public enum OperatorsEnum {
    
    DISJUNCTION("or", -2, 3),
    CONJUNCTION("and", -2, 4),
    IMPLICATION("->", -2, 3),
    EQUIVALENCE("<=>", -2, 4);
    
    private final String operator;
    private final int x;
    private final int y;
    
    private OperatorsEnum(String operator, int x, int y) {
        
        this.operator = operator;
        this.x = x;
        this.y = y;
    }
    
    public String getOperator() {
        return operator;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}