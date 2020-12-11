
package client;

public interface Event {
    void onClientConnect(String clientName, String message);

    void onClientDisconnect(String clientName, String message);

    void onMessageReceive(String clientName, String message);
    
    void onChangeRoom();
    
    void onIsMuted(String clientName,boolean isMuted);

    //void onSyncDirection(String clientName, Point direction);

    //void onSyncPosition(String clientName, Point position);

    void onGetRoom(String roomName);
}