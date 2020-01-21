package org.firstinspires.ftc.teamcode.OOP.robot;

public class AutoSettings {


    public enum Settings{
        RED,BLUE,FOUNDATION,SKYSTONE,SIMPLE,ADVANCED
    }
    private Settings teamColor;
    private Settings fieldSide;
    private Settings complexity;


    //


    public AutoSettings(){
    }

    public Settings getTeamColor() {
        return teamColor;
    }

    public Settings getComplexity() {
        return complexity;
    }

    public Settings getFieldSide() {
        return fieldSide;
    }

    public void setComplexity(Settings complexity) {
        this.complexity = complexity;
    }

    public void setFieldSide(Settings fieldSide) {
        this.fieldSide = fieldSide;
    }

    public void setTeamColor(Settings teamColor) {
        this.teamColor = teamColor;
    }

    public void setObject(Settings object){
        if(object == Settings.BLUE || object == Settings.RED){
            setTeamColor(object);
        } else if(object == Settings.FOUNDATION || object == Settings.SKYSTONE){
            setFieldSide(object);
        }else if(object == Settings.SIMPLE || object == Settings.ADVANCED){
            setComplexity(object);
        }
    }
}
