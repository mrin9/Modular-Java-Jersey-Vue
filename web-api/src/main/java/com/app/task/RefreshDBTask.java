package com.app.task;

import com.app.DatabaseService;

import java.util.Date;

public class RefreshDBTask  implements Runnable {

    @Override
    public void run() {

        DatabaseService.initDB();
        //System.out.print("\n\n" + Thread.currentThread().getName()+" START: "+new Date());
        //Thread.sleep(6000);
        //System.out.print(" ...... " + Thread.currentThread().getName()+" DONE: "+new Date());

    }

}
