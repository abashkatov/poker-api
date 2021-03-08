# poker-api

## received events

### chat
- /app/message `{author, message}`

### room
- /app/room/im-here `{name}`
- /app/room/im-here-too `{name}`
- /app/room/topic-changed `{title}`
- /app/room/i-left `{name}`
- /app/room/i-voted `{name}`
- /app/room/open-cards `{name}`
- /app/room/my-score `{name, score}`
- /app/room/re-vote `{name}`
- /app/room/new `{title}`

## generated events

### chat
- /topic/chat `{author, message}`
 
### room
- /topic/room/im-here `{name}`
- /topic/room/im-here-too `{name}`
- /topic/room/topic-changed `{title}`
- /topic/room/i-left `{name}`
- /topic/room/i-voted `{name}`
- /topic/room/open-cards `{name}`
- /topic/room/my-score `{name, score}`
- /topic/room/re-vote `{name}`
- /topic/room/re-vote `{id,title}`
