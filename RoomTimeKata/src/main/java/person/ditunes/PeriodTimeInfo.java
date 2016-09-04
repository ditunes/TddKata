package person.ditunes;

import java.time.LocalTime;

/**
 * Created by linhan on 16/9/3.
 */
public class PeriodTimeInfo {

    private final LocalTime startTime;

    private final LocalTime endTime;

    private Status status;

    public enum Status{
        avaliable,
        occupied
    }

    public PeriodTimeInfo(LocalTime startTime, LocalTime endTime, Status status) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    public LocalTime getStartTime() {
        return this.startTime;
    }

    public LocalTime getEndTime() {
        return this.endTime;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PeriodTimeInfo)) return false;

        PeriodTimeInfo that = (PeriodTimeInfo) o;

        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;
        return status == that.status;

    }

    @Override
    public int hashCode() {
        int result = startTime != null ? startTime.hashCode() : 0;
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PeriodTimeInfo{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", status=" + status +
                '}';
    }
}
