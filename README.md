# poker-api

## received events

### chat
- /app/message `{author, message}`

### room
- /app/room/im-here `{name}`
- /app/room/im-here-too `{name}`
- /app/room/topic-changed `{title}`
- /app/room/i-left `{name}`

## generated events

### chat
- /topic/chat `{author, message}`
 
### room
- /topic/room/im-here `{name}`
- /topic/room/im-here-too `{name}`
- /topic/room/topic-changed `{title}`
- /topic/room/i-left `{name}`
