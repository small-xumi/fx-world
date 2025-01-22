package hut.natsufumij.island.app;

public record AppEvent(String channel,String appId,String event,String[] args) {
}
