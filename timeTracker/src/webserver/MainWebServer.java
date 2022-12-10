package webserver;

import core.Activity;
import core.Clock;
import test.Test;

public class MainWebServer {
  public static void main(String[] args) {
    webServer();
  }

  public static void webServer() {
    final Activity root = Test.createTree();
    // implement this method that returns the tree of
    // appendix A in the practicum handout

    Clock.getInstance();
    // start your clock

    new WebServer(root);
  }
}