package com.paycoll.test;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by cloud on 2017/5/25.
 */
public class DrewTest {
    public static void main(String[] args) {
        List<DrewModel> list = new ArrayList<>();
        Random random = new Random();
        for(int i=0;i<19;i++){
            DrewModel model = new DrewModel();
            model.setTotal(random.nextInt(15));
            list.add(model);
        }
        for(int i=0;i<100;i++){
            int k = random.nextInt(list.size());
            DrewModel drewModel = list.get(k);
            if(drewModel.getValue()<drewModel.getTotal()){
                drewModel.setValue(drewModel.getValue()+1);
            }else{
                System.out.println("out");
            }
        }
        for(DrewModel model : list){
            System.out.println("total:"+model.getTotal()+"   value:"+model.getValue());
        }
    }
}
