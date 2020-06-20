package entities;

import java.util.*;

/**
 * Допсущность взятия, сортированной
 * коллекции организаций
 * @param <K> ключи отображения
 * @param <V> элементы отображения
 */
public class HashMax<K, V extends OrganizationWithUId> extends HashMap {
    /**
     * Вот эта вот херня была придумана Укропами,
     * была взята прям с боем.
     * Метод сортировки элементов по имени
     * @return отображение, хранящее порядок элементов
     */
    public LinkedHashMap<Integer, OrganizationWithUId>  sortByNameOfOrganization() {
        ArrayList<OrganizationWithUId> organizationArrayList = new ArrayList<>();
        LinkedHashMap<Integer, OrganizationWithUId> newOrganizationHashMap = new LinkedHashMap<>();
        Iterator<Entry<Integer, OrganizationWithUId>> iter1 = ((HashMap<Integer,OrganizationWithUId>)this).entrySet().iterator();
        while (iter1.hasNext()) {
            organizationArrayList.add(iter1.next().getValue());
        }

        organizationArrayList.sort(new Comparator<OrganizationWithUId>() {
            @Override
            public int compare(OrganizationWithUId o1, OrganizationWithUId o2) {
                return o1.getOrganization().getName().compareTo(o2.getOrganization().getName());
            }
        });

        Iterator<OrganizationWithUId> iter2 = organizationArrayList.iterator();
        while (iter2.hasNext()) {
            OrganizationWithUId organization = iter2.next();
            newOrganizationHashMap.put(organization.getKey(),organization);
        }
        return newOrganizationHashMap;
    }
}
