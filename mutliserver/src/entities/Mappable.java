package entities;

public interface Mappable<K> {

  K getKey();

  String getName();
}
