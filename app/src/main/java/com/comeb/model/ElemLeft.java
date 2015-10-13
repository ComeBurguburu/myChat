package com.comeb.model;

import com.comeb.tchat.R;

/**
 * Created by côme on 24/09/2015.
 */
public class ElemLeft extends Elem {
    public ElemLeft(String p,String m){
        super(p,m);
        ResImg = R.mipmap.ic_user_blue;
    }
    @Override
    public boolean isLeft(){
        return true;
    }
}
