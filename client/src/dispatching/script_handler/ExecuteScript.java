package dispatching.script_handler;

import communication.Segment;
import data_section.enumSection.Markers;
import dispatching.Dispatcher;
import instructions.rotten.base.*;
import organization.Junker;
import organization.Organization;
import entities.organizationFactory.OrganizationBuilder;
import instructions.rotten.RawDecree;
import instructions.rotten.extended.*;


import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class ExecuteScript {
    private String executed = "";
    private final Dispatcher dispatcher;
    private OrganizationBuilder organizationBuilder;

//    public void Execution(String add) { executed +=  "{ "  + add + " }\n"; }
    public ExecuteScript(Dispatcher dispatcher,OrganizationBuilder organizationBuilder)  {
        this.dispatcher = dispatcher;
        this.organizationBuilder = organizationBuilder;
    }
//    public LilyShell(Resolver controller, String filename, Invoker ctrl) {
//        super(controller, filename, ctrl);
//    }
//
//    public LilyShell(Resolver controller, Invoker ctrl) {
//        super(controller, ctrl);
//    }

    public void read(ArrayList<String> script, SocketChannel socketChannel) {

        ArrayList<String> tempScriptParts = script;
        int capacity = tempScriptParts.size();
        ArrayDeque<String> scriptParticles = new ArrayDeque<>(capacity);
        for (int i = 0; i < capacity; i++) {
            scriptParticles.add(tempScriptParts.get(i));
        }
        if (!dispatcher.switchOrder()) {
            dispatcher.switchOrder();
        };
        for(int i = 0; i < capacity; i = capacity - scriptParticles.size()) {
            RawDecree parsedCommand = parse(scriptParticles.poll(), scriptParticles);
            if (parsedCommand == null) {
                System.err.println("Не удалось разобрать строку "  + scriptParticles.peekFirst() );
            } else {
                Segment segment = new Segment(socketChannel,Markers.CONFIRMING);
                segment.setCommandData(parsedCommand);
                dispatcher.giveOrder(segment);
            }
        }
        dispatcher.switchOrder();
    }


    private RawDecree parse(String line, ArrayDeque<String> scriptParts) {
        // если строка пустая то и команду определить нельзя
        if (line == null || line.isEmpty()) return null;
        // иначе делим по пробелам
        String[] lineParts = line.split(" ");
        // проверяем смогли ли поделить
//        if (lineParts == null || lineParts.length == 0)
//            return null;
//        else {
            // создаем определитель команд
        CommandDefiner cmdDeaf = new CommandDefiner(scriptParts);
        switch (lineParts.length) {
            // если длина массива одын, то команда без аргументов
            case 1: return cmdDeaf.define(0, lineParts);
            // если длина массива два, то это либо команда с одним аргументом,
            // либо с двумя
            case 2: return cmdDeaf.define(1, lineParts);
            case 3: return cmdDeaf.define(1, lineParts);
            // иначе какая-то неопределенная команда
            default: return null;
        }

    }

    /**
     * Подкласс ответственный за определение команды и сбор данных об объекте организации.
     */
    class CommandDefiner {
        protected final SortedSet<String> junkedCommands = new TreeSet<String>() {
            {
                add("insert");
                add("update");
                add("remove_lower");
                add("replace_if_lower");
                add("replace_if_greater");
            }
        };
        private ArrayDeque<String> scriptParts;

        public CommandDefiner(ArrayDeque<String> scriptParts) {
            this.scriptParts = scriptParts;
        }

        public RawDecree define(int argc, String[] command_name) {
            switch (argc) {
                case 0: return defineWane(command_name[0]);
                case 1: return defineArgumented(command_name);
                default: return null;
            }
        }

        private RawDecree defineWane(String command_name) {
            // проверяем строку на пустоту, ради свича
            if (command_name == null || command_name.isEmpty()) return null;
            // тут все норм, свич юзает иквалз и все хорошо
            // солнышко светит, команды определяются
            switch (command_name) {
                case "sign_out": {
                    return new RawSignOut();
                }
                case "help": {
//                    executed += "Выполнена команда: " + command_name + "\n";
                    return new RawHelp();
                }
                case "info": {
//                    executed += "Выполнена команда: " + command_name + "\n";
                    return new RawInfo();
                }
                case "clear": {
//                    executed += "Выполнена команда: " + command_name + "\n";
                    return new RawClear();
                }
                case "show": {
//                    executed += "Выполнена команда: " + command_name + "\n";
                    return new RawShow();
                }
                case "sum_of_annual_turnover": {
//                    executed += "Выполнена команда: " + command_name + "\n";
                    return new RawSumOfAnnualTurnover();
                }
                case "max_by_creation_date": {
//                    executed += "Выполнена команда: " + command_name + "\n";
                    return new RawMaxByDate();
                }
                case "remove_lower": {
                    ParamDefiner prmDeaf = new ParamDefiner(scriptParts);
                    Organization element = organizationBuilder.make(prmDeaf.define());
                    if (element != null) {
//                        executed += "Выполнена команда: " + command_name + "\n";
                       return new RawRemoveLower(element);
                    } else return null;
                }
                // тут солнышко скроется, команда не настроится
                default: return null;
            }
        }

        private RawDecree defineArgumented(String[] command_n_args) {
            String command_name = command_n_args[0]; // взяли название команды
            // взяли доп аргумент
            String argument = command_n_args[1];
            if (command_name == null || command_name.isEmpty()) return null;
            try {
                if (!junkedCommands.contains(command_name)) {
                    switch (command_name) {
                        case "sign_in": {
                            return new RawSignIn(command_n_args[1],command_n_args[2]);
                        }
                        case "sign_up": {
                            return new RawSignUp(command_n_args[1],command_n_args[2]);
                        }
                        case "remove_key": {
                            Integer key = Integer.valueOf(argument); // переводим аргумент в число
                            if (key == null) return null; // если вышло null, то и команда не определена
//                            executed += "Выполнена команда: " + command_name + "\n";
                            return new RawRemoveKey(key); // иначе вернуть нормальную команду
                        }
                        case "execute_script": {
//                            executed += "Выполнена команда: " + command_name + "\n";
                            //return new RawExecuteScript();
                        } case "filter_contains_name": {
//                            executed += "Выполнена команда: " + command_name + "\n";
                            return new RawFilterContainsName(argument);
                        } default:
                            return null;
                    }
                } else {
                    // начало обработки команды с элементом
                    ParamDefiner prmDeaf = new ParamDefiner(scriptParts);
                    Organization element = organizationBuilder.make(prmDeaf.define());
                    if (element == null) return null;
                    else {
                        switch (command_name) {
                            case "insert": {
                                Integer key = Integer.valueOf(argument);
                                if (key == null) return null;
//                                executed += "Выполнена команда: " + command_name + "\n";
                                return new RawInsert(key, element);
                            }
                            case "update": {
                                Integer id = Integer.valueOf(argument);
                                if (id == null) return null;
//                                executed += "Выполнена команда: " + command_name + "\n";
                                return new RawUpdate(id, element);
                            }
                            case "replace_if_lower": {
                                Integer key = Integer.valueOf(argument);
                                if (key == null) return null;
//                                executed += "Выполнена команда: " + command_name + "\n";
                                return new RawReplaceIfLower(key, element);
                            }
                            case "replace_if_greater": {
                                Integer key = Integer.valueOf(argument);
                                if (key == null) return null;
//                                executed += "Выполнена команда: " + command_name + "\n";
                                return new RawReplaceIfGreater(key, element);
                            }
                            default: return null;
                        }
                    }
                }
            } catch (NumberFormatException e) { return null; }
        }
    }

    /**
     * Внутренний класс по преобразованию данных об объекте в удобную форму для последующей обработки.
     */
    class ParamDefiner {
        private final ArrayDeque<String> scriptParts;
        public ParamDefiner(ArrayDeque<String> scriptParts) {
            this.scriptParts = scriptParts;
        }

        private boolean checkFilling(Object[] parameters) {
            boolean flag = false;
            for (Object param : parameters) {
                if (param == null) {
                    flag = true;
                    return flag;
                }
            }
            return flag;
        }

        /**
         * Основной метод преобразования строковых данных к пригодному для обработки виду.
         * @return Junker
         */
        public Junker define() {
            String name = null, fullname = null, type = null; // тип может быть null'ом
            Float annualTurnover = null;
            Integer employeesCount = null;
            Junker coordinates = null, address = null; // адрес может быть null'ом
            Object[] params = new Object[] { name, fullname, annualTurnover, employeesCount, coordinates};
            boolean typeCheck =false, addressCheck = false;

            String smParameter = null;
            while (checkFilling(params) || !typeCheck || !addressCheck) {
                if (scriptParts.isEmpty()) break;
                smParameter = scriptParts.poll(); //<---------------------------------------------------------
                String[] prmParts = smParameter.split(":");
                if (prmParts == null || prmParts.length == 0 || prmParts.length > 2) continue;
                String field = prmParts[0].trim();
                if (field == null || field.isEmpty()) continue;
                switch (field) {
                    case "org.name": {
                        if (prmParts.length == 1) continue;
                        name = prmParts[1].trim();
                        params[0] = name;
                        break;
                    }
                    case "org.fullname": {
                        if (prmParts.length == 1) continue;
                        fullname = prmParts[1].trim();
                        params[1] = fullname;
                        break;
                    }
                    case "org.type": {
                        if (prmParts.length == 1) continue;
                        type = prmParts[1].trim();
                        break;
                    }
                    case "org.annual-turnover": {
                        if (prmParts.length == 1) continue;
                        try { annualTurnover = Float.valueOf(prmParts[1].trim()); } catch (NumberFormatException e) { continue; }
                        params[2] = annualTurnover;
                        break;
                    }
                    case "org.employees-count": {
                        if (prmParts.length == 1) continue;
                        try { employeesCount = Integer.valueOf(prmParts[1].trim()); } catch (NumberFormatException e) { continue; }
                        params[3] = employeesCount;
                        break;
                    }
                    case "org.address": {
                        address = defAddress();
                        break;
                    }
                    case "org.coordinates": {
                        coordinates = defCoordinates();
                        params[4] = coordinates;
                        break;
                    }
                    default:
                        if (!checkFilling(params)) {
                            typeCheck = true;
                            addressCheck = true;
                        }
                        if (!scriptParts.peekFirst().contains(":")) break;
                        continue;
                }
            }
            return new Junker(new long[]{employeesCount}, new double[]{annualTurnover}, new String[]{name, fullname, type}, new Junker[]{coordinates, address});
        }

        public Junker defAddress() {
            String zipCode = null;
            Junker town = null;
            String input = scriptParts.poll();
            zipCode = parseParam("addr.zipCode", input);
            if (scriptParts.isEmpty()) return new Junker(null, null, new String[]{zipCode}, null);
            town = defLocation();
            return new Junker(null, null, new String[]{zipCode}, new Junker[]{town});
        }

        public Junker defCoordinates() {
            Integer x = null; Float y = null;
            try {
                if (scriptParts.isEmpty() ) return null;
                String input = scriptParts.poll();
                String arg = parseParam("coord.x", input);
                x = Integer.valueOf(arg);
                input = scriptParts.poll();
                arg = parseParam("coord.y", input);
                y = Float.valueOf(arg);
            } catch (NumberFormatException ex) { return null; }
            return new Junker(new long[]{x}, new double[]{y}, null, null);
        }

        public Junker defLocation() {
            Long x = null, y = null; Double z = null;
            try {
                for (int i = 0; i < 3; ++i)
                    if (scriptParts.isEmpty()) break;
                    else {
                        String input = scriptParts.poll();
                        switch (i) {
                            case 0: {
                                String arg = parseParam("loc.x", input);
                                x = Long.valueOf(arg);
                                break;
                            }
                            case 1: {
                                String arg = parseParam("loc.y", input);
                                y = Long.valueOf(arg);
                                break;
                            }
                            case 2: {
                                String arg = parseParam("loc.z", input);
                                z = Double.valueOf(arg);
                                break;
                            }
                            default: return null;
                        }
                    }
            } catch (NumberFormatException ex) { return null; }
            if (y == null) {
                y = 0l;
            }
            if (z == null) {
                z = 0.0;
            }
            return new Junker(new long[]{x, y}, new double[]{z}, null, null);
        }

        private String parseParam(String goalp, String parsy) {
            if (parsy == null || parsy.isEmpty()) return null;
            String[] params = parsy.split(":");
            if (params == null | params.length != 2) return null;
            if (params[0].equals(goalp))
                return params[1];
            else return null;
        }
    }
}