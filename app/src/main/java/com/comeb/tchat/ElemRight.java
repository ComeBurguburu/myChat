package com.comeb.tchat;

/**
 * Created by côme on 24/09/2015.
 */
public class ElemRight extends Elem {

    public ElemRight(String p,String m){
        super(p,m);
        ResImg = R.mipmap.ic_user_red;
    }
    @Override
    public boolean isLeft(){
        return false;
    }
}
