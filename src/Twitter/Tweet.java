package Twitter;


class Tweet{
        public int id;
        public int time;
        public Tweet next;
        public String content;
        public Tweet(int id, int timeStamp, String content) {
            this.id = id;
            time = timeStamp;
            this.content = content;
            next = null;
        }
    }