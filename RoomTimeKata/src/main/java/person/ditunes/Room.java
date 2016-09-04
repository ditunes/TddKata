package person.ditunes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by linhan on 16/9/3.
 */
public class Room {

    List<RoomPeriodTime> periods;

    public Room(Long id, final PeriodTimeInfo... periodTimeStatus) {
        periods = Arrays.stream(periodTimeStatus).map((period) -> {
            return new RoomPeriodTime(id, period);
        }).collect(
                Collectors.toCollection(() -> {
                            return new ArrayList<RoomPeriodTime>();
                        }
                )
        );
    }

    public List<RoomPeriodTime> getPeriods() {
        return periods;
    }
}