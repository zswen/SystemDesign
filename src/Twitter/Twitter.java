package Twitter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

class Twitter {
    private static int timeStamp = 0;
    private Map<Integer, User> userMap;
 

    /** Initialize your data structure here. */
    public Twitter() {
        userMap = new HashMap<Integer, User>();
    }
    
    /** Compose a new tweet. */
    public void postTweet(int userId, int tweetId, String content) {
        if(!userMap.containsKey(userId)) { // 加入 user database
            User u = new User(userId);
            userMap.put(userId, u);
        }
        userMap.get(userId).post(tweetId, Twitter.timeStamp++, content); 

    }
    
    /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> res = new LinkedList<Integer>();
        if(!userMap.containsKey(userId)) return res; // userId invalid ,直接返回空
        Set<Integer> users = userMap.get(userId).followed;
        Comparator<Tweet> comp = new Comparator<Tweet>() {
            public int compare(Tweet tw1, Tweet tw2) {
                return tw2.time - tw1.time;
            }
        };
        PriorityQueue<Tweet> queue = new PriorityQueue<Tweet>(users.size(), comp);
        for(int user : users) {
            Tweet t = userMap.get(user).tweet_head;
            if(t != null) queue.add(t);
        }
        int n = 0;
        while(!queue.isEmpty() && n < 10) {
            Tweet t = queue.poll();
            res.add(t.id);
            n++;
            if(t.next != null) queue.add(t.next);
        }
        return res;

    }
    
    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId)) {
            User u = new User(followerId);
            userMap.put(followerId, u);
        }
        if(!userMap.containsKey(followeeId)) {
            User u = new User(followeeId);
            userMap.put(followeeId, u);
        }
        userMap.get(followerId).follow(followeeId);
    }
    
    /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
    public void unfollow(int followerId, int followeeId) {
        if(!userMap.containsKey(followerId) || followerId == followeeId) return;
        userMap.get(followerId).unfollow(followeeId);
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */