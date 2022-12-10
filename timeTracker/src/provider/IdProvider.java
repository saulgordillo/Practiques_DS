package provider;

public class IdProvider {
  private static IdProvider uniqueIdProvider = null;
  int id;

  private IdProvider() {
    id = 0;
  }

  /**
   * @return Instance of the IdProvider
   */
  public static IdProvider getInstance() {
    if (uniqueIdProvider == null) {
      uniqueIdProvider = new IdProvider();
    }

    return uniqueIdProvider;
  }

  public int getId() {
    id = id + 1;
    return id;
  }
}
