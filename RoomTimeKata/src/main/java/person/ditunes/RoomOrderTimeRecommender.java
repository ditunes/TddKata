package person.ditunes;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by linhan on 16/9/3.
 */
public class RoomOrderTimeRecommender {

    public RoomPeriodTime recommend(List<Room> rooms){
        return rooms.stream().reduce(new ArrayList<RoomPeriodTime>(),(list,room)->{
            list.addAll(room.getPeriods());
            return list;
        },(parallelListA,paralleListB)->{
            parallelListA.addAll(paralleListB);
            return parallelListA;
        }).stream().filter((roomPerionStatus)->{
            return roomPerionStatus.getPerionTimeInfo().getStatus() == PeriodTimeInfo.Status.avaliable;
        }).sorted((a1,a2)->{
            return a1.getPerionTimeInfo().getStartTime().compareTo(a2.getPerionTimeInfo().getStartTime());
        }).findFirst().orElse(null);
    }
}
