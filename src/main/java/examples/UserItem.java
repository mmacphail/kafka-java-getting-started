package examples;

import java.util.Objects;

public class UserItem {

  private String user;
  private String item;

  public UserItem() {
  }

  public UserItem(String user, String item) {
    this.user = user;
    this.item = item;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getItem() {
    return item;
  }

  public void setItem(String item) {
    this.item = item;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserItem userItem = (UserItem) o;
    return Objects.equals(user, userItem.user) && Objects.equals(item, userItem.item);
  }

  @Override
  public int hashCode() {
    return Objects.hash(user, item);
  }

  @Override
  public String toString() {
    return "UserItem{" +
          "user='" + user + '\'' +
          ", item='" + item + '\'' +
          '}';
  }
}
