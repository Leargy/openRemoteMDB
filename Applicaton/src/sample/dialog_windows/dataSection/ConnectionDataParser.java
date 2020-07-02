package sample.dialog_windows.dataSection;

import sample.assets.exceptions.ParsingException;
import sample.dialog_windows.communication.Parcel;

public class ConnectionDataParser implements Parsing {
    private String ip;
    private String port;

    @Override
    public void pars(Parcel parcel) throws ParsingException {
        String rawData;
        rawData = parcel.getMessage();
        ip = ipPars(rawData);
        port = portPars(rawData);
        if(checkIpFormat(ip) == false) {
            throw new ParsingException("Wrong ip format!");
        }
        if (checkPortFormat(port) == false) {
            throw new ParsingException("Not available port number!");
        }

    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    private boolean checkIpFormat(String ipData) {
        if (ipData.matches("(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)"))
            return true;
        else
            return false;
    }

    private boolean checkPortFormat(String portData) {
        if(Integer.valueOf(portData) > 48654 && Integer.valueOf(portData) < 65536) return true;
        else return false;
    }

    private String ipPars(String rawData) {
        String[] v1 = rawData.split(" ");
        for (int i = 0; i < v1.length; i++) {
            if (v1[i].matches("ip=.*")) return v1[i].substring(3);
        }
        return "";
    }
    private String portPars(String rawData) {
        String[] v1 = rawData.split(" ");
        for (int i = 0; i < v1.length; i++) {
            if (v1[i].matches("port=.*")) return v1[i].substring(5);
        }
        return "";
    }
}
