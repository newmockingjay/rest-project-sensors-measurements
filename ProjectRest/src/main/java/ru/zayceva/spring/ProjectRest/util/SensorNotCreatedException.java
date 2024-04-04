package ru.zayceva.spring.ProjectRest.util;

public class SensorNotCreatedException extends RuntimeException{
    public SensorNotCreatedException(String msg){
        super(msg);
    }
}
