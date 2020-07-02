package instructions.rotten.extended;

import organization.Organization;
import instructions.rotten.IClued;
import instructions.rotten.IJunked;
import instructions.rotten.RawCommitter;

import java.io.Serializable;

/**
 * Абстрактный класс для команд, отвечающих за замены по условию.
 */
public abstract class RawReplaceIf extends RawCommitter implements IClued, IJunked, Serializable {
    protected final Integer KEY;

    /**
     * Конструктор, устанавливающий параметры
     * добавляемого объекта
     * @param junk
     */
    protected RawReplaceIf(Integer key, Organization organization) {
        super(organization);
        KEY = key;
    }


    /**
     * Возвращает "ключ" объекта.
     * @return Integer
     */
    @Override
    public final Integer Key() { return KEY; }
}
