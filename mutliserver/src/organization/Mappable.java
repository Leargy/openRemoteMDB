package organization;

import java.io.Serializable;

public interface Mappable<K> extends Serializable {

  K getKey();

  String getName();
}
