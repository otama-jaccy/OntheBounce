package com.kurume_nct.onthebounce.utility;

/**
 * Created by minto on 2015/08/29.
 */
public class Player {
    private int hp;

    public Player(int hp){
        this.hp = hp;
    }

    public void damaged(int damaged_value){
        this.hp -= damaged_value;
    }

    public boolean isDead(){
        return this.hp<=0;
    }

    public void cure(int cure_value){
        this.hp += cure_value;
    }
}
