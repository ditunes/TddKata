
KATA描述
===

一家澡堂有 m 个房间，每个房间有 n 个时段，现在要给用户推荐「最早的可以预约的时段」。

```
function xxx(rooms) {}
Example：

rooms: [
  {
     room_id: 1,
     periods: [
        {
           time: '17:00-18:00',
           status: 'available'
        },
        {
           time: '18:00-19:00',
           status: 'occupied'
        }
     ]
  }, {
     room_id: 2,
     periods: [
        {
           time: '17:00-18:00',
           status: 'occupied'
        },
        {
           time: '18:00-19:00',
           status: 'available'
        }
     ]
  }
]
```
期望返回：

```
{
  room_id: 1,
  time: '17:00-18:00'
}
```
