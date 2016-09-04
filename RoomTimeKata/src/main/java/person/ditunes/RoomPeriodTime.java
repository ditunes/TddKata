package person.ditunes;

/**
 * Created by linhan on 16/9/3.
 */
public class RoomPeriodTime {

    private final Long roomId;

    private final PeriodTimeInfo status;

    public RoomPeriodTime(Long roomId, PeriodTimeInfo periodTimeInfo) {
        this.roomId = roomId;
        this.status = periodTimeInfo;
    }

    public Long getRoomId() {
        return roomId;
    }

    public PeriodTimeInfo getPerionTimeInfo() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoomPeriodTime)) return false;

        RoomPeriodTime that = (RoomPeriodTime) o;

        if (roomId != null ? !roomId.equals(that.roomId) : that.roomId != null) return false;
        return status != null ? status.equals(that.status) : that.status == null;

    }

    @Override
    public int hashCode() {
        int result = roomId != null ? roomId.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RoomPeriodTime{" +
                "roomId=" + roomId +
                ", status=" + status +
                '}';
    }
}
