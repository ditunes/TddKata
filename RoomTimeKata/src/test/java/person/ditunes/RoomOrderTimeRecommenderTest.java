package person.ditunes;


import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class RoomOrderTimeRecommenderTest {

    RoomOrderTimeRecommender recommender = new RoomOrderTimeRecommender();

    @Test
    public void when_one_room_only_has_avaliable_time_then_return_it() {
        //given
        Room room = new Room(1L,
                new PeriodTimeInfo(LocalTime.of(17, 0), LocalTime.of(18, 0), PeriodTimeInfo.Status.avaliable)
        );
        //when
        RoomPeriodTime time = recommender.recommend(Arrays.asList(room));
        //then
        Assert.assertEquals(
                new RoomPeriodTime(1L,
                        new PeriodTimeInfo(LocalTime.of(17, 0), LocalTime.of(18, 0), PeriodTimeInfo.Status.avaliable))
                        ,time);
    }

    @Test
    public void when_one_room_only_has_occupied_then_return_null(){
        //given
        Room room = new Room(1L,
                new PeriodTimeInfo(LocalTime.of(17, 0), LocalTime.of(18, 0), PeriodTimeInfo.Status.occupied)
        );
        //when
        RoomPeriodTime time = recommender.recommend(Arrays.asList(room));
        //then
        Assert.assertNull(time);
    }

    @Test
    public void when_one_room_time_has_mult_avaliable_then_return_the_earliest_one(){
        //given
        Room room = new Room(1L,
                new PeriodTimeInfo(LocalTime.of(17, 0), LocalTime.of(18, 0), PeriodTimeInfo.Status.avaliable),
                new PeriodTimeInfo(LocalTime.of(13, 0), LocalTime.of(14, 0), PeriodTimeInfo.Status.avaliable)
        );
        //when
        RoomPeriodTime time = recommender.recommend(Arrays.asList(room));
        //then
        Assert.assertEquals(
                new RoomPeriodTime(1L,
                        new PeriodTimeInfo(LocalTime.of(13, 0), LocalTime.of(14, 0), PeriodTimeInfo.Status.avaliable))
                ,time);
    }

    @Test
    public void when_mult_rooms_all_time_eq_then_return_the_avaliable_one(){

        //given
        Room roomA = new Room(1L,
                new PeriodTimeInfo(LocalTime.of(10, 0), LocalTime.of(11, 0), PeriodTimeInfo.Status.avaliable)
        );
        Room roomB = new Room(2L,
                new PeriodTimeInfo(LocalTime.of(10, 0), LocalTime.of(11, 0), PeriodTimeInfo.Status.occupied)
        );
        //when
        RoomPeriodTime time = recommender.recommend(Arrays.asList(roomB,roomA));
        //then
        Assert.assertEquals(
                new RoomPeriodTime(1L,
                        new PeriodTimeInfo(LocalTime.of(10, 0), LocalTime.of(11, 0), PeriodTimeInfo.Status.avaliable))
                ,time);
    }



    @Test
    public void when_mult_rooms_all_avaliable_then_return_the_earliest_one(){

        //given
        Room roomA = new Room(1L,
                new PeriodTimeInfo(LocalTime.of(17, 0), LocalTime.of(18, 0), PeriodTimeInfo.Status.avaliable),
                new PeriodTimeInfo(LocalTime.of(13, 0), LocalTime.of(14, 0), PeriodTimeInfo.Status.avaliable)
        );
        Room roomB = new Room(2L,
                new PeriodTimeInfo(LocalTime.of(11, 0), LocalTime.of(12, 0), PeriodTimeInfo.Status.avaliable),
                new PeriodTimeInfo(LocalTime.of(18, 0), LocalTime.of(19, 0), PeriodTimeInfo.Status.avaliable)
        );
        //when
        RoomPeriodTime time = recommender.recommend(Arrays.asList(roomA,roomB));
        //then
        Assert.assertEquals(
                new RoomPeriodTime(2L,
                        new PeriodTimeInfo(LocalTime.of(11, 0), LocalTime.of(12, 0), PeriodTimeInfo.Status.avaliable))
                ,time);
    }

}
