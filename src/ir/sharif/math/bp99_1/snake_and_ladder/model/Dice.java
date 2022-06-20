package ir.sharif.math.bp99_1.snake_and_ladder.model;

import ir.sharif.math.bp99_1.snake_and_ladder.Save;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Dice implements Save {


    Map<Integer, Integer> diceNumber;

    /**
     * add some fields to store :
     * 1) chance of each dice number ( primary chance of each number, should be 1 )
     * currently our dice has 1 to 6.
     * 2) generate a random number
     * <p>
     * initialize these fields in constructor.
     */
    public Dice() {
        diceNumber=new HashMap<Integer,Integer>();
        for(int i=1;i<=6;i++){
            diceNumber.put(i,1);
        }
    }

    /**
     * create an algorithm generate a random number(between 1 to 6) according to the
     * chance of each dice number( you store them somewhere)
     * return the generated number
     */
    public int roll() {
        Random random=new Random();
        int sumOfChances=0;
        for(int i=1;i<=6;i++){
            sumOfChances=sumOfChances+diceNumber.get(i);
        }
        int a=random.nextInt(sumOfChances)+1;
        int q=0;
        for(int i=6;i>=0;i--){

            a=a-diceNumber.get(i);
            if(a<=0){
                q=i;
                break;
            }
        }

        return q;
    }

    /**
     * give a dice number and a chance, you should UPDATE chance
     * of that number.
     * pay attention chance of none of the numbers must not be negetive(it can be zero)
     */
    public void addChance(int number, int chance) {

        if(diceNumber.get(number)+chance>=0){diceNumber.replace(number,diceNumber.get(number)+chance);}
        if(diceNumber.get(number)+chance<0){diceNumber.replace(number,0);}
        if(diceNumber.get(number)+chance>=8){diceNumber.replace(number,8);}

    }

    public void setChance(int number,int chance){
        if(chance>=0)diceNumber.replace(number,chance);
        if(chance<0)diceNumber.replace(number,0);
    }


    /**
     * you should return the details of the dice number.
     * sth like:
     * "1 with #1 chance.
     * 2 with #2 chance.
     * 3 with #3 chance
     * .
     * .
     * . "
     * where #i is the chance of number i.
     */
    public String getDetails() {
        String details="";
        for(int i=1;i<=6;i++){
            details= details +""+ i + " with #"+ diceNumber.get(i) +" chance "+ "\n" ;
        }
        return details;
    }

    @Override
    public String save() {
        String s="";
//        s=s+"diceNumberChances:"+"\n";
        for(int i=1;i<=diceNumber.size();i++){
            s=s+""+diceNumber.get(i)+" "+"\n";
        }
        s=s+"\n";


        return s;
    }



}
