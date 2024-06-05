package com.AudensTopDownShooterServer.SupportClasses.GameClasses;

import com.AudensTopDownShooterServer.SupportClasses.PhysicsClasses.LineCollider;
import com.AudensTopDownShooterServer.SupportClasses.PhysicsClasses.Vector2;

public class Wall {
    private Vector2 start;
    private Vector2 end;

    public Wall(Vector2 Start, Vector2 End)
    {
        start = Start;
        end = End;
    }

    public Vector2 getStart() {
        return start;
    }
    public void setStart(Vector2 start) {
        this.start = start;
    }
    public Vector2 getEnd() {
        return end;
    }
    public void setEnd(Vector2 end) {
        this.end = end;
    }

    public LineCollider getCollider(){
        return new LineCollider(start.getX(), start.getY(), end.getX(), end.getY());
    }
}

