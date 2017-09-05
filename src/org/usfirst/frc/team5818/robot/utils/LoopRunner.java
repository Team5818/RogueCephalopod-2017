package org.usfirst.frc.team5818.robot.utils;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Notifier;

public class LoopRunner implements Runnable{

    private final ArrayList<FastLoop> loopList;
    private boolean running = false;

    public LoopRunner(ArrayList<FastLoop> loops) {
        loopList = loops;
    }
    
    public void start() {
        for (FastLoop loop : loopList) {
            loop.start();
        }
        running = true;
    }
    
    public void update() {
        for (FastLoop loop : loopList) {
            loop.update();
        }
    }
    
    public boolean isFinished() {
        boolean all_finished = true;
        for (FastLoop loop : loopList) {
            if (!loop.isFinished()) {
                all_finished = false;
            }
        }
        return all_finished;
    }



    public void done() {
        for (FastLoop loop : loopList) {
            loop.done();
        }
    }


    @Override
    public void run() {
        start();
        while(running) {
            update();
            running = !isFinished();
        }
        done();
    }
    
    public void startThread() {
        Notifier notifier = new Notifier(this);
        notifier.startPeriodic(.005);
    }
}