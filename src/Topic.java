public class Topic {
    private int TopicID;
    private String Topic;

    public Topic(int TopicID, String Topic){
        this.TopicID = TopicID;
        this.Topic = Topic;
    }

    public int getTopicID() {
        return TopicID;
    }

    public String getTopic() {
        return Topic;
    }
}
