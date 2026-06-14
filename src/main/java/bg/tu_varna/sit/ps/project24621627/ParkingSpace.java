package bg.tu_varna.sit.ps.project24621627;

public class ParkingSpace {
    private int number;
    private int floor;
    private SpaceType type;
    private String access;
    private boolean busy;
    private String notes;

    public ParkingSpace(int number, int floor, SpaceType type, String access, boolean busy, String notes) {
        this.number = number;
        this.floor = floor;
        this.type = type;
        this.access = access;
        this.busy = busy;
        this.notes = notes;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public SpaceType getType() {
        return type;
    }

    public void setType(SpaceType type) {
        this.type = type;
    }
}
