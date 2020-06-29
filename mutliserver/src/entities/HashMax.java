package entities;

import organization.OrganizationWithUId;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Допсущность взятия, сортированной
 * коллекции организаций
 * @param <K> ключи отображения
 * @param <V> элементы отображения
 */
public class HashMax<K, V extends OrganizationWithUId> extends ConcurrentHashMap {
    public HashMax(){
        super();
    }

    /**
     * Вот эта вот херня была придумана Укропами,
     * была взята прям с боем.
     * Метод сортировки элементов по имени
     * @return отображение, хранящее порядок элементов
     */
    public synchronized LinkedHashMap<Integer, OrganizationWithUId>  sortByNameOfOrganization() {
        ArrayList<OrganizationWithUId> organizationArrayList = new ArrayList<>();
        LinkedHashMap<Integer, OrganizationWithUId> newOrganizationHashMap = new LinkedHashMap<>();
        Iterator<Entry<Integer, OrganizationWithUId>> iter1 = ((ConcurrentHashMap<Integer,OrganizationWithUId>)this).entrySet().iterator();
        while (iter1.hasNext()) {
            Entry<Integer, OrganizationWithUId> tempEntry = iter1.next();
            organizationArrayList.add(tempEntry.getValue());
        }

        organizationArrayList.sort(new Comparator<OrganizationWithUId>() {
            @Override
            public int compare(OrganizationWithUId o1, OrganizationWithUId o2) {
                return o1.getName().compareTo(o2.getName());
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
