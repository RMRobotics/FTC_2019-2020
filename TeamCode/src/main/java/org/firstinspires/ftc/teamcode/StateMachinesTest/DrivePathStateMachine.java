package org.firstinspires.ftc.teamcode.StateMachinesTest;
/*


interface State {
    void pull(DrivePathPullChain wrapper);
}

class DrivePathPullChain {

    private State currentState;

    public DrivePathPullChain() {
        currentState = new
                Stopped();
    }

    public void setState(State s) {
        currentState = s;
    }

    public void pull(){
        currentState.pull(this);
    }

}

class NoAuto implements State { //Stops Vehicle
    public void pull(DrivePathPullChain wrapper) {
        wrapper.setState(new LowSpeed());
        System.out.println("Set speed to LOW");
        //Perform Action here
    }
}

class LowSpeed implements State { //Sets vehicle speed to zero
    public void pull(DrivePathPullChain wrapper) {
        wrapper.setState(new MediumSpeed());
        System.out.println("Set speed to MEDIUM");
        //Perform Action here
    }
}

class MediumSpeed implements State {//Sets vehicle speed to half
    public void pull(DrivePathPullChain wrapper) {
        wrapper.setState(new HighSpeed());
        System.out.println("Set speed to HIGH");
        //Perform Action here
    }
}

class HighSpeed implements State {//Sets vehicle speed to high
    public void pull(DrivePathPullChain wrapper) {
        wrapper.setState(new Stopped());
        System.out.println("Vehicle has STOPPED");
        //Perform Action here

    }
}

*/