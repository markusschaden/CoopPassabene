package ch.avendia.passabene.wifi;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.WifiManager;

/**
 * Created by Markus on 11.01.2015.
 */
public class CoopWifiManager {

    private WifiManager wifiManager;
    public CoopWifiManager(WifiManager wifiManager) {
        this.wifiManager = wifiManager;

    }

    public void addWifi(String username, String password) {
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.SSID = "\"" + "passabene" + "\"";
        configuration.priority = 40;
        configuration.allowedKeyManagement.clear();
        configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.IEEE8021X);
        configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_EAP);
        configuration.allowedAuthAlgorithms.clear();
        configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.LEAP);
        configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
        configuration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        configuration.allowedGroupCiphers.clear();
        configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
        configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        configuration.allowedPairwiseCiphers.clear();
        configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        configuration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.NONE);
        configuration.allowedProtocols.clear();
        configuration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        configuration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);

        WifiEnterpriseConfig enterpriseConfig = new WifiEnterpriseConfig();
        enterpriseConfig.setPassword(password);
        enterpriseConfig.setPhase2Method(WifiEnterpriseConfig.Phase2.NONE);
        enterpriseConfig.setIdentity(username);
        enterpriseConfig.setEapMethod(WifiEnterpriseConfig.Eap.TLS);
        configuration.enterpriseConfig = enterpriseConfig;

        boolean res1 = wifiManager.setWifiEnabled(true);
        int res = wifiManager.addNetwork(configuration);
        boolean saved = wifiManager.saveConfiguration();
        boolean enabled = wifiManager.enableNetwork(res, true);
    }


    public void removeWifi() {

    }

    public void checkWifi() {

    }

    public void connectToCoopWifi() {

    }
}
