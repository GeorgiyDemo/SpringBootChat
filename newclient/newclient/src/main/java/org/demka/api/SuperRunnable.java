package org.demka.api;

import java.util.ArrayList;
import java.util.List;

public abstract class SuperRunnable implements Runnable {
    protected static List<SuperRunnable> runnableList = new ArrayList<>();
    protected Boolean exit = false;

    public void stop()
    {
        this.exit = true;
    }

    public void stopAll(){
        for (SuperRunnable r: runnableList) {
            r.stop();
        }
    }
}
